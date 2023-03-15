package cn.ravanla.flash.api.controller.business;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.constant.factory.PageFactory;
import cn.ravanla.flash.bean.entity.front.Ids;
import cn.ravanla.flash.bean.entity.front.Menu;
import cn.ravanla.flash.bean.entity.front.Ratings;
import cn.ravanla.flash.bean.entity.front.Shop;
import cn.ravanla.flash.bean.entity.system.Cfg;
import cn.ravanla.flash.bean.enumeration.ConfigKeyEnum;
import cn.ravanla.flash.bean.vo.business.CityInfo;
import cn.ravanla.flash.bean.vo.business.ShopVo;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.dao.MongoRepository;
import cn.ravanla.flash.security.AccountInfo;
import cn.ravanla.flash.security.JwtUtil;
import cn.ravanla.flash.service.front.IdsService;
import cn.ravanla.flash.service.front.PositionService;
import cn.ravanla.flash.service.system.CfgService;
import cn.ravanla.flash.utils.*;
import cn.ravanla.flash.utils.*;
import cn.ravanla.flash.utils.factory.Page;
import cn.ravanla.flash.utils.gps.Distance;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created  on 2020/1/2 0002.
 *
 *@Author ravanla
 */
@RestController
@RequestMapping("/shopping")
public class ShopController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private IdsService idsService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private CfgService cfgService;

    @RequestMapping(value = "/restaurant/{id}", method = RequestMethod.GET)
    public Object getShop(@PathVariable("id") Long id) {
        Object data = mongoRepository.findOne(id, "shops");
        return Rets.success(data);
    }

    /**
     * 后台管理查询商户列表
     *
     * @param name        商铺名称
     * @param state       审核状态
     * @param categoryIds
     * @return
     */
    @RequestMapping(value = "listShop", method = RequestMethod.GET)
    // 用于返回商家信息的列表。
    public Object listShop(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "state", required = false) String state,
            // 类别ID
            @RequestParam(value = "restaurant_category_ids[]", required = false) Long[] categoryIds) {
        // 根据请求中是否包含用户信息，使用不同的查询参数查询数据库。
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        Page<Shop> page = new PageFactory<Shop>().defaultPage();
        if (Constants.USER_TYPE_SHOP.equals(accountInfo.getUserType())) {
            // 如果是商家本身请求，则使用商家ID查询自己的信息；否则使用请求参数进行查询。
            page = mongoRepository.queryPage(page, Shop.class, Maps.newHashMap("id", accountInfo.getUserId()));
        }else {
            Map<String, Object> params = Maps.newHashMap();
            if (StringUtils.isNotEmpty(name)) {
                params.put("name", name);
            }
            if (StringUtils.isNotEmpty(state)) {
                params.put("state", state);
            }
            page = mongoRepository.queryPage(page, Shop.class, params);
        }
        // 查询到的结果通过Page对象进行封装，使用Rets.success返回成功信息和查询结果。
        List<Shop> list = page.getRecords();

        // 最后还对查询结果进行了处理，将商家密码设置为null，以避免敏感信息的泄露。
        for(Shop shop:list){
            shop.setPassword(null);
        }

        page.setRecords(list);
        return Rets.success(page);

    }


    /**
     * 用户端查询商铺列表
     *
     * @param latitude
     * @param longitude
     * @param name        商铺名称
     * @param categoryIds
     * @return
     */
    // 获取餐厅信息
    @RequestMapping(value = "restaurants", method = RequestMethod.GET)
    public Object restaurants(
            // 经纬度
            @RequestParam(value = "latitude", required = false) String latitude,
            @RequestParam(value = "longitude", required = false) String longitude,
            // 餐厅名称
            @RequestParam(value = "name", required = false) String name,
            // 餐厅分类id数组
            @RequestParam(value = "restaurant_category_ids", required = false) Long[] categoryIds) {
//    @RequestMapping(value = "restaurants", method = RequestMethod.GET)
//    public Object restaurants(@RequestParam(value = "latitude", required = false) String latitude,
//                              @RequestParam(value = "longitude", required = false) String longitude,
//                              @RequestParam(value = "name", required = false) String name,
//                              @RequestParam(value = "restaurant_category_ids", required = false) Long[] categoryIds) {


        // 构造查询条件
        Map<String, Object> params = Maps.newHashMap();
        if (StringUtils.isNotEmpty(name)) {
            params.put("name", name);
        }
        params.put("disabled", 0);

        // 如果没有提供经纬度，直接查询所有餐厅
        if (StringUtils.isEmpty(latitude) || "undefined".equals(latitude)
                || StringUtils.isEmpty(longitude) || "undefined".equals(longitude)) {
            Page<Shop> page = new PageFactory<Shop>().defaultPage();
            return Rets.success(mongoRepository.queryPage(page, Shop.class, params));
        } else {
            // 否则查询指定经纬度范围内的餐厅
            if (categoryIds != null && categoryIds.length > 0) {
                // 如果提供了餐厅分类id，获取对应的分类名称
                Map map = (Map) mongoRepository.findOne(categoryIds[0], "categories");
                if (map != null) {
                    params.put("category", map.get("name").toString());
                }
            }
            // 查询指定经纬度范围内的餐厅
            GeoResults<Map> geoResults = mongoRepository.near(
                    Double.valueOf(longitude), Double.valueOf(latitude), "shops", params);
            Page<Map> page = new PageFactory<Map>().defaultPage();
            if (geoResults != null) {
                // 对查询结果进行加工，计算距离等信息
                List<GeoResult<Map>> geoResultList = geoResults.getContent();
                List list = Lists.newArrayList();
                for (int i = 0; i < geoResultList.size(); i++) {
                    Map map = geoResultList.get(i).getContent();

                    Distance distance = new Distance(Double.valueOf(longitude), Double.valueOf(latitude),
                            Double.valueOf(map.get("longitude").toString()), Double.valueOf(map.get("latitude").toString()));
                    map.put("distance", distance.getDistance().intValue());

                    map.put("order_lead_time", "30分钟");
                    list.add(map);
                }

                // 构造分页查询结果并返回
                page.setTotal(list.size());
                page.setRecords(list);
            }
            return Rets.success(page);
        }

    }

    @RequestMapping(value = "/restaurants/count", method = RequestMethod.GET)
    public Object countShop() {
        // 请求处理器方法是 countShop()。
        // 该方法通过调用 mongoRepository.count(Shop.class) 获取 Shop 类的对象数，
        // 然后将结果打包为一个 success 响应返回，响应结果中包含了商铺数量的统计信息。
        long count = mongoRepository.count(Shop.class);
        return Rets.success("count", count);
    }

    @RequestMapping(value = "/restaurants/{id}", method = RequestMethod.DELETE)
    public Object deleteShop(@PathVariable("id") Long id) {
        mongoRepository.delete(id, "shops");
        return Rets.success();
    }

    /**
     * 审核商铺
     *
     * @param shop
     * @return
     */
    @RequestMapping(value = "/auditShop", method = RequestMethod.POST)
    public Object auditShop(@ModelAttribute Shop shop) {
        Map<String, Object> updateMap = new HashMap<String, Object>(4);
        // 审核备注
        updateMap.put("auditRemark", shop.getAuditRemark());
        updateMap.put("state", shop.getState());
        mongoRepository.update(shop.getId(), "shops", updateMap);
        return Rets.success();
    }

    /**
     * 停用/启用商铺
     *
     * @param shop
     * @return
     */
    @RequestMapping(value = "/stopShop", method = RequestMethod.POST)
    public Object stopShop(@ModelAttribute Shop shop) {
        Map<String, Object> updateMap = new HashMap<String, Object>(2);
        updateMap.put("disabled", shop.getDisabled());
        mongoRepository.update(shop.getId(), "shops", updateMap);
        return Rets.success();
    }

    /**
     * 编辑商铺
     *
     * @param shop
     * @return
     */
    @RequestMapping(value = "/updateshop", method = RequestMethod.POST)
    public Object updateShop(@ModelAttribute @Valid Shop shop) {
        // 方法首先使用JwtUtil.getAccountInfo()方法获取用户信息，
        AccountInfo accountInfo = JwtUtil.getAccountInfo();

        Map<String, Object> updateMap = new HashMap<String, Object>(16);
        // 使用Strings.sNull()方法将所有属性转换为字符串并处理为空的情况。
        // 接下来，将商店对象的属性复制到一个名为“updateMap”的HashMap中，
        updateMap.put("name", Strings.sNull(shop.getName()));
        updateMap.put("address", Strings.sNull(shop.getAddress()));
        updateMap.put("description", Strings.sNull(shop.getDescription()));
        updateMap.put("category", Strings.sNull(shop.getCategory()));
        updateMap.put("phone", Strings.sNull(shop.getPhone()));
        updateMap.put("rating", Double.valueOf(shop.getRating()));
        updateMap.put("recent_order_num", shop.getRecent_order_num());
        updateMap.put("image_path", shop.getImage_path());
        updateMap.put("platform_rate",shop.getPlatform_rate());

        // 然后根据用户类型（商户或管理员）将商店的状态设置为“待审核”或“审核通过”。
        if(Constants.USER_TYPE_SHOP.equals(accountInfo.getUserType())){
            //商户自己修改需要审核
            updateMap.put("state", Shop.STATE_ING);
        }else{
            //管理员修改直接审核通过
            updateMap.put("state", Shop.STATE_YES);
        }


        // 最后，使用mongoRepository.update()方法更新MongoDB中的商店记录，并返回成功的响应。
        mongoRepository.update(shop.getId(), "shops", updateMap);
        return Rets.success();
    }

    @RequestMapping(value = "/addShop", method = RequestMethod.POST)
    // 前端传来的店铺信息进行处理，设置店铺的活动信息和支持服务信息，最后保存到数据库中。
    public Object addShop(@ModelAttribute @Valid ShopVo shopVo) {

        // 创建一个新的Shop对象，并使用BeanUtil将前端传来的数据进行复制。



        // 最后将Shop对象保存到数据库中。
        Shop shop = new Shop();
        BeanUtil.copyProperties(shopVo, shop);

        // 为Shop对象设置id，通过idsService获取Ids.RESTAURANT_ID。
        shop.setId(idsService.getId(Ids.RESTAURANT_ID));
        List activities = Json.fromJson(List.class, shopVo.getActivitiesJson());
        int index = 0;

        // 遍历活动列表，根据不同的活动类型设置对应的icon_color和id，并将处理后的活动列表设置到Shop对象中。
        for (int i = 0; i < activities.size(); i++) {
            Map activity = (Map) activities.get(i);
            String iconName = activity.get("icon_name").toString();
            switch (iconName) {
                case "减":
                    activity.put("icon_color", "f07373");
                    activity.put("id", index++);
                    break;
                case "特":
                    activity.put("icon_color", "EDC123");
                    activity.put("id", index++);
                    break;
                case "新":
                    activity.put("icon_color ", "70bc46");
                    activity.put("id", index++);
                    break;
                case "领":
                    activity.put("icon_color ", "E3EE0D");
                    activity.put("id ", index++);
                    break;
                default:
                    break;
            }
        }
        shop.setActivities(activities);

        // 根据前端传来的支持服务信息，构建支持服务列表，并将列表设置到Shop对象中。
        List<Map> supports = new ArrayList<Map>(4);
        if (shopVo.getBao()) {
            supports.add(buildSupport("已加入“外卖保”计划，食品安全有保障", "999999", "保", 7, "外卖保"));
        }
        if (shopVo.getZhun()) {
            supports.add(buildSupport("准时必达，超时秒赔", "57A9FF", "准", 9, "准时达"));
        }
        if (shopVo.getPiao()) {
            supports.add(buildSupport("该商家支持开发票，请在下单时填写好发票抬头", "999999", "票", 4, "开发票"));
        }
        shop.setSupports(supports);

        // 设置Shop对象的is_new属性为前端传来的news属性。
        shop.setIs_new(shopVo.getNews());

        // 根据配送方式的选项设置相关信息；
        if (shopVo.getDeliveryMode()) {
            Map<String, Object> deliveryMode = Maps.newHashMap("color", "57A9FF", "id", 1, "is_solid", true, "text", "蜂鸟专送");
            shop.setDelivery_mode(deliveryMode);
        }

        // 设置商家的配送费用和营业时间等信息；
        Map<String, String> tips = new HashMap<String, String>(2);
        tips.put("tips", "配送费约￥" + shopVo.getFloat_delivery_fee());
        shop.setPiecewise_agent_fee(tips);
        List<String> openingHours = new ArrayList<String>();
        if (Strings.isNotBlank(shopVo.getStartTime()) &&
                Strings.isNotBlank(shopVo.getEndTime())) {
            openingHours.add(shopVo.getStartTime() + "/" + shopVo.getEndTime());
        } else {
            openingHours.add("08:30/20:30");
        }
        shop.setOpening_hours(openingHours);

        // 设置商家的营业执照等证件信息；
        Map<String, String> license = new HashMap<String, String>();
        if (Strings.isNotBlank(Strings.sNull(shopVo.getBusiness_license_image()))) {
            license.put("business_license_image", shopVo.getBusiness_license_image());
        }
        if (Strings.isNotBlank(shopVo.getCatering_service_license_image())) {
            license.put("catering_service_license_image", shopVo.getCatering_service_license_image());
        }
        shop.setLicense(license);
        Map<String, String> identification = Maps.newHashMap("company_name", "",
                "identificate_agency", "",
                "identificate_date", "",
                "legal_person", "",
                "licenses_date", "",
                "licenses_number", "",
                "licenses_scope", "",
                "operation_period", "",
                "registered_address", "",
                "registered_number", "");
        shop.setIdentification(identification);

        // 设置商家的位置信息，并保存到数据库；
        CityInfo cityInfo = positionService.getPostion(getIp());
        if (cityInfo != null) {
            shop.setLatitude(Double.valueOf(cityInfo.getLat()));
            shop.setLongitude(Double.valueOf(cityInfo.getLng()));
            List locations = new LinkedList();
            locations.add(Double.valueOf(cityInfo.getLng()));
            locations.add(Double.valueOf(cityInfo.getLat()));
            shop.setLocation(locations);
        }

        // 设置商家的默认密码、状态等信息；
        shop.setPassword("123456");
        shop.setState(Shop.STATE_YES);
        shop.setUnliquidatedAmount("0");
        shop.setTotalAmount("0");

        // 保存Shop对象到MongoDB中；
        mongoRepository.save(shop);


        // 如果Ratings对象不存在，则创建一个新的Ratings对象，并保存到MongoDB中；
        Ratings ratings = mongoRepository.findOne(Ratings.class, Maps.newHashMap("restaurant_id", shop.getId()));
        if (ratings == null) {
            ratings = new Ratings(shop.getId());

            mongoRepository.save(ratings);
        }
        // 返回操作成功的响应结果。
        return Rets.success();
    }

    // 添加菜单
    @RequestMapping(value = "addcategory", method = RequestMethod.POST)
    public Object addCategory(@Valid @ModelAttribute Menu menu) {
        menu.setId(idsService.getId(Ids.CATEGORY_ID));
        System.out.println(Json.toJson(menu));
        //todo 进行处理后保存
        mongoRepository.save(menu);
        return Rets.success();
    }

    // 餐厅类别
    @RequestMapping(value = "/v2/restaurant/category", method = RequestMethod.GET)
    public Object categories() {
        return Rets.success(mongoRepository.findAll("categories"));
    }

    // 获取菜单类别
    @RequestMapping(value = "/getcategory/{id}", method = RequestMethod.GET)
    public Object getCategory(@PathVariable("id") Long restaurantId) {
        List list = mongoRepository.findAll("menus", "restaurant_id", restaurantId);
        return Rets.success("category_list", list);
    }

    // 根据ID获取详细菜单
    @RequestMapping(value = "/v2/menu/{id}", method = RequestMethod.GET)
    public Object getMenus(@PathVariable("id") Long id) {
        return Rets.success(mongoRepository.findOne(id, "menus"));
    }

    // 根据ID获取店铺的全部菜单
    @RequestMapping(value = "/v2/menu", method = RequestMethod.GET)
    public Object getMenu(@RequestParam("restaurant_id") Long restaurantId, @RequestParam(name = "allMenu", required = false) boolean allMEnu) {
        List<Map> list = mongoRepository.findAll("menus", "restaurant_id", restaurantId);
        return Rets.success(list);
    }

    /**
     * 结算
     * @param id
     * @return
     * 流程：
     * 1. 根据id查询出一个Shop对象；
     * 2. 获取该Shop对象的unliquidatedAmount和totalAmount属性值；
     * 3. 如果totalAmount为空，则将其赋值为0；
     * 4. 根据一些业务逻辑计算出platformAmount和shopAmount的值；
     * 5. 计算出最新的totalAmount值，并更新Cfg对象的值；
     * 6. 更新Shop对象的totalAmount和unliquidatedAmount值；
     * 7. 返回处理成功的信息。
     */
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Object check(@RequestParam("id") Long id) {
        // 根据id查询出一个Shop对象
        Shop shop = mongoRepository.findOne(Shop.class,id);

        // 获取该Shop对象的unliquidatedAmount和totalAmount属性值；
        String unliquidatedAmount = shop.getUnliquidatedAmount();
        String totalAmount = shop.getTotalAmount();

        // 如果totalAmount为空，则将其赋值为0
        if(StringUtils.isEmpty(totalAmount)){
            totalAmount = "0";
        }

        // 首先获取到未结算金额unliquidatedAmount和店铺平台费率shop.getPlatform_rate()
        // 将平台手续费 (platformAmount) 除以 100，再保留 2 位小数 (即除法后保留两位小数)并使用 BigDecimal.ROUND HALF UP 四舍五入方式取整
        String platformAmount =  new BigDecimal(unliquidatedAmount)
                .multiply(new BigDecimal(shop.getPlatform_rate())).divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_UP).toPlainString();
        // 用未结算金额减去平台服务费，得到店铺实际获得的金额shopAmount
        String shopAmount = new BigDecimal(unliquidatedAmount).subtract(new BigDecimal(platformAmount)).toPlainString();

        // 配置表中获取配置值，将其值与platformAmount相加后更新配置表。
        totalAmount = new BigDecimal(totalAmount).add(new BigDecimal(shopAmount)).toPlainString()+"";
        Cfg cfg = cfgService.getByCfgName(ConfigKeyEnum.SYSTEM_PLATFORM_TOTAL_AMOUNT.getValue());
        // 这是一个系统平台总金额，用于记录平台所有商家的总收入。
        cfg.setCfgValue(new BigDecimal(cfg.getCfgValue()).add(new BigDecimal(platformAmount)).toPlainString());
        cfgService.update(cfg);

        // 更新Shop对象的totalAmount和unliquidatedAmount值；
        shop.setTotalAmount(totalAmount);
        shop.setUnliquidatedAmount("0");
        mongoRepository.update(shop);

        // 返回处理成功的信息。
        return Rets.success();
    }

    private Map<String, Object> buildSupport(String description, String iconColor, String iconName, Integer id, String name) {
        Map<String, Object> map = new HashMap<String, Object>(8);
        map.put("description", description);
        map.put("icon_color", iconColor);
        map.put("icon_name", iconName);
        map.put("id", id);
        map.put("name", name);
        return map;
    }
}
