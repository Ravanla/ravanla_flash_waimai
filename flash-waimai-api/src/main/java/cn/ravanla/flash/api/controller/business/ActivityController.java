package cn.ravanla.flash.api.controller.business;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.entity.front.Activity;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.dao.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created  on 2020/1/5 0005.
 *
 *@Author ravanla
 */

// 使用@RestController注解标注为控制器类。
// 它继承了BaseController类，并自动注入了MongoRepository对象。
@RestController
public class ActivityController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;

    // @RequestMapping注解用于将HTTP请求映射到处理程序方法上。
    // 在这里，映射了GET请求"/shopping/v1/restaurants/activity_attributes"到list()方法上。
    @RequestMapping(value = "/shopping/v1/restaurants/activity_attributes",method = RequestMethod.GET)
    // @ResponseBody 注解用于指定返回值的类型是一个 JSON 对象。
    @ResponseBody

    // list()方法使用@RequestParam注解将请求参数latitude和longitude传递给方法，
    public Object list(@RequestParam(value = "latitude",required = false) String latitude,
                       @RequestParam(value = "longitude",required = false) String longitude){
        // 并使用MongoRepository对象查询数据库中的活动数据，并返回结果。最后，将结果封装在Rets.success()中，并作为响应主体返回。
        return Rets.success(mongoRepository.findAll(Activity.class));
    }
// 在该方法中，根据请求参数latitude和longitude的值，使用MongoRepository对象从数据库中获取活动数据，
// 最终将结果封装在Rets.success()中，作为响应主体返回给客户端。
//
// 因此，该控制器类的作用是处理"/shopping/v1/restaurants/activity_attributes"接口的GET请求，并返回活动数据的JSON格式。
}
