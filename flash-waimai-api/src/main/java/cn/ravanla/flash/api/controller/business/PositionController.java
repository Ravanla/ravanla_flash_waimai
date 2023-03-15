package cn.ravanla.flash.api.controller.business;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.vo.business.City;
import cn.ravanla.flash.bean.vo.business.CityInfo;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.dao.MongoRepository;
import cn.ravanla.flash.service.front.PositionService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created  on 2021/12/29 0029.
 *
 *@Author ravanla
 */
@RestController
public class PositionController extends BaseController {

    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private PositionService positionService;

    @RequestMapping(value = "/v1/cities",method = RequestMethod.GET)
    // 根据type参数返回不同的城市信息，如猜测的城市，热门城市或分组城市
    public Object cities(@RequestParam("type") String type, HttpServletRequest request) {
        Map cities = mongoRepository.findOne("cities");
        Map data = (Map) cities.get("data");
        switch (type){
            case "guess":
                CityInfo cityInfo = positionService.getPostion(getIp());
                if(cityInfo!=null) {
                    String city = cityInfo.getCity();
                    if (Strings.isNullOrEmpty(city)) {
                        return Rets.failure();
                    }
                    return Rets.success(positionService.findByName(city));
                }else{
                    Rets.success();
                }

            case "hot":

                return Rets.success(data.get("hotCities"));

            case "group":
                return  Rets.success(data);


            default:
                    break;


        }
        return Rets.failure();

    }

    // 根据id参数返回指定的城市信息
    @RequestMapping(value = "/v1/cities/{id}",method = RequestMethod.GET)
    // PathVariable映射URL中的占位符，将URL中的占位符参数绑定到控制器的方法参数上。
    public Object getCity(@PathVariable("id") Integer id){
        return Rets.success(positionService.findById(id));
    }

    // 根据city_id和keyword参数返回指定城市中匹配关键词的地点信息
    @RequestMapping(value = "/v1/pois",method = RequestMethod.GET)
    public Object getPoiByCityAndKeyword(@RequestParam(value = "type",defaultValue = "search") String type,
                                         @RequestParam(value = "city_id",required = false) Integer cityId,
                                         @RequestParam(value = "keyword") String keyword){
        String cityName = null;
        if(cityId==null){
            City city = positionService.guessCity(getIp());
            cityName = city.getName();
        }else {
            Map map = positionService.findById(cityId);
            cityName = map.get("name").toString();
        }
        return Rets.success(positionService.searchPlace(cityName, keyword));

    }

    // 根据geohash参数返回指定地理位置的地点信息
    @RequestMapping(value = "/v1/position/pois",method = RequestMethod.GET)
    public Object getPoiByGeoHash(@RequestParam("geohash") String geoHash){
        System.out.println("geohash:"+geoHash);
        return Rets.success(positionService.pois(geoHash));
    }
}
