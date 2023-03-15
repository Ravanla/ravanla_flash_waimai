package cn.ravanla.flash.api.controller.business;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.dao.MongoRepository;
import cn.ravanla.flash.utils.Lists;
import cn.ravanla.flash.utils.Maps;
import cn.ravanla.flash.utils.gps.Distance;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * Created on 2020/1/5 0005. todo 未完成
 * 查询餐厅的接口
 *@Author ravanla
 */

/*代码改进
* 将请求参数封装为对象：将请求参数封装成一个对象，可以减少方法的参数数量，提高代码的可读性。

* 参数校验：对参数进行校验，避免传入不合法的参数。

* 异常处理：对可能出现的异常进行处理，保证程序的健壮性。

* 分页查询：对于大量数据，可以进行分页查询，减少内存占用。

* API文档：编写API文档，方便其他开发者了解接口的使用方法和参数要求。
*
* */

@RestController
public class RestaurantController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;

    @RequestMapping(value = "/v4/restaurants", method = RequestMethod.GET)
    // 该接口接收两个参数，一个是经纬度的geohash字符串，另一个是关键字keyWord。
    public Object restaurants(@RequestParam("geohash") String geoHash, @RequestParam("keyword") String keyWord) {
        // 代码将geohash字符串拆分成经度和纬度两个参数，
        String[] geoHashArr = geoHash.split(",");
        String longitude = geoHashArr[1];
        String latitude = geoHashArr[0];

        // 并使用Maps工具类创建一个参数Map，以便向MongoDB查询符合关键字的餐厅。
        Map<String, Object> params = Maps.newHashMap("name", keyWord);
        System.out.println(Json.toJson(params));

        // 查询结果使用GeoResults类型来接收，并从中提取出GeoResult列表。
        GeoResults<Map> geoResults = mongoRepository.near(Double.valueOf(longitude), Double.valueOf(latitude),
                "shops", params);
        List<GeoResult<Map>> geoResultList = geoResults.getContent();


        List<Map> list = Lists.newArrayList();
        // 代码遍历该列表，计算每个餐厅与当前位置的距离，并将距离信息存入Map对象中。
        for (int i = 0; i < geoResultList.size(); i++) {
            Map map = geoResultList.get(i).getContent();
            Distance distance = new Distance(Double.valueOf(longitude), Double.valueOf(latitude),
                    Double.valueOf(map.get("longitude").toString()), Double.valueOf(map.get("latitude").toString()));
            map.put("distance", distance.getDistance());
            list.add(map);
        }
        return Rets.success(list);
    }
}

//@RestController
//@RequestMapping("/restaurants")
//public class RestaurantController {
//    @Autowired
//    private RestaurantService restaurantService;
//
//    @GetMapping
//    public ResponseEntity<List<Restaurant>> getRestaurants(@RequestParam(required = false) Double latitude,
//                                                           @RequestParam(required = false) Double longitude,
//                                                           @RequestParam(required = false) String name,
//                                                           @RequestParam(defaultValue = "0") int page,
//                                                           @RequestParam(defaultValue = "10") int size) {
//        // 校验参数
//        if (latitude == null || longitude == null) {
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        // 分页查询餐厅信息
//        Page<Restaurant> restaurantPage = restaurantService.findRestaurantsByLocationAndName(
//                new Point(longitude, latitude), name, PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")));
//
//        return ResponseEntity.ok()
//                .header("X-Total-Count", Long.toString(restaurantPage.getTotalElements()))
//                .body(restaurantPage.getContent());
//    }
//}

