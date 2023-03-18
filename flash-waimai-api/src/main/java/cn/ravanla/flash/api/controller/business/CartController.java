package cn.ravanla.flash.api.controller.business;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.entity.front.*;
import cn.ravanla.flash.bean.entity.front.*;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.dao.MongoRepository;
import cn.ravanla.flash.service.front.IdsService;
import cn.ravanla.flash.service.front.PositionService;
import cn.ravanla.flash.utils.Lists;
import cn.ravanla.flash.utils.Maps;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created  on 2020/1/5 0005.
 *
 *@Author ravanla
 */
// 购物车控制器，处理和购物车相关的请求
@RestController
public class CartController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private IdsService idsService;
    @Autowired
    private PositionService positionService;

/*
* 这段代码似乎是用Java编写的，看起来它是一个更大系统的一部分，该系统管理餐厅或送餐服务的购物车。



* 代码首先获取一个请求有效负载，然后将其打印到控制台。
* 然后，它从有效负载中提取一些信息，例如geohash和餐馆ID。
* 接下来，它初始化Carts对象，并从MongoDB存储库中检索支付列表。
* 它还根据餐厅ID从存储库中检索Shop对象。



* 然后，代码调用positionService来获取餐厅和顾客之间的距离。
* 如果成功，它将从响应中检索交付时间，并将其设置在Carts对象中。



* 然后，代码初始化Cart对象，并用请求有效载荷中的信息填充它，例如餐厅ID、Cart中的食品和总价。
* 它还计算任何额外的费用，如配送费或包装费，并将其添加到总额中。



* 最后，代码将Carts对象保存到MongoDB存储库中，并使用Rets类将其作为响应返回。



* 总的来说，这个代码似乎负责处理购物车请求并生成一个响应，其中包括配送时间、总价和食品清单等信息。它还与MongoDB存储库和其他服务交互，以检索和存储信息。
* */

    @RequestMapping(value = "/v1/carts/checkout", method = RequestMethod.POST)
    // 这个方法接收一个HTTP请求对象，从中获取一些参数，
    // 如geohash（地理编码），restaurant_id（餐厅编号），entities（商品列表）等。
    public Object checkout(HttpServletRequest request) {

        Map data = getRequestPayload(Map.class);
        System.out.println(Json.toJson(data));
        String from = data.get("geohash").toString();
        Long restaurantId = Long.valueOf(data.get("restaurant_id").toString());
        Carts carts = new Carts();
        List<Payment> paymentList = mongoRepository.findAll(Payment.class);
        Shop shop = mongoRepository.findOne(Shop.class, restaurantId);
        String to = shop.getLatitude() + "," + shop.getLongitude();
        Map distance = positionService.getDistance(from, to);
        String deliver_time = distance != null ? distance.get("duration").toString() : "";
        carts.setDelivery_reach_time(deliver_time);
        carts.setId(idsService.getId(Ids.CART_ID));
        carts.setPayments(paymentList);
        carts.setSig(String.valueOf(Math.ceil(Math.random() * 1000000)));
        carts.setInvoice(Maps.newHashMap("status_text", "不需要开发票", "is_available", true));

        // 然后它创建一个Carts对象，用来存储购物车的信息，如支付方式，发票信息，配送时间等。
        Cart cart = new Cart();
        cart.setId(carts.getId());
        cart.setRestaurant_id(restaurantId);
        cart.setRestaurant_info(shop);
        cart.setDeliver_amount(4);
        List<List> entities = (List<List>) data.get("entities");
        List<List> groups = Lists.newArrayList(Lists.newArrayList());
        BigDecimal total = new BigDecimal(0);
        List extraList = Lists.newArrayList();
        Map extra = Maps.newHashMap(
                "description", "",
                "name", "",
                "price", 0,
                "quantity", 1,
                "type", 0);
        for (int i = 0; i < entities.get(0).size(); i++) {
            Map map = (Map) entities.get(0).get(i);
            Map items = Maps.newHashMap();
            items.put("id", Long.valueOf(map.get("id").toString()));
            Food food = mongoRepository.findOne(Food.class, Maps.newHashMap("item_id",items.get("id")));
            items.put("name", food.getName()+"("+map.get("name")+")");
            items.put("packing_fee", map.get("packing_fee"));
            items.put("price", map.get("price"));
            items.put("quantity", map.get("quantity"));
            Double amount= Double.valueOf(map.get("packing_fee").toString()) * Integer.valueOf(map.get("quantity").toString());
            if(!"0".equals(map.get("packing_fee"))){
                extra = Maps.newHashMap(
                        "description", "",
                        "name", "打包费" + "-" + ((List) map.get("specs")).get(0),
                        "price", Double.valueOf(extra.get("price").toString()) + amount,
                        "quantity", Integer.valueOf(extra.get("quantity").toString()) + Integer.valueOf(map.get("quantity").toString()),
                        "type", 0
                );
                //包装费
                total  = total.add(new BigDecimal(extra.get("price").toString()));
            }


            total = total.add(new BigDecimal(items.get("price").toString()).multiply(new BigDecimal(items.get("quantity").toString())));


            groups.get(0).add(items);
        }
        //配送费
        total = total.add(new BigDecimal(cart.getDeliver_amount()));
        extraList.add(extra);
        cart.setExtra(extraList);

        cart.setTotal(total.toPlainString());
        cart.setGroups(groups);
        carts.setCart(cart);

        // 调用了一些其他服务，
        // 如mongoRepository（MongoDB数据库操作），
        // idsService（ID生成服务），
        // positionService（位置服务）等。
        //todo 暂时不保存,开发中
        mongoRepository.save(carts, "carts");
        return Rets.success(carts);
    }

    @RequestMapping(value = "/v1/carts/{cart_id}/remarks", method = RequestMethod.GET)
    public Object remarks(@PathVariable("cart_id") Long cartId, @RequestParam(value = "sig", required = false) String sig) {
        return Rets.success(mongoRepository.findOne("remarks"));
    }
}
