package cn.ravanla.flash.api.controller.business;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.entity.front.Ratings;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.dao.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created  on 2020/1/5 0005.
 *
 *@Author ravanla
 */
//定义一个REST控制器类，用于处理评分相关的请求
@RestController
public class RatingController extends BaseController {
    //注入一个MongoDB数据库操作对象
    @Autowired
    private MongoRepository mongoRepository;

    //定义一个处理方法，用于返回指定餐厅的评分列表
    @RequestMapping(value = "/ugc/v2/restaurants/{restaurant_id}/ratings", method = RequestMethod.GET)
    public Object ratings(@PathVariable("restaurant_id") Long restaurantId) {
//从数据库中查询评分数据，并返回成功响应，并包含评分列表数据
        Map map = mongoRepository.findOne("ratings", "restaurant_id", restaurantId);
        return Rets.success(map.get("ratings"));
    }

    // 定义一个处理方法，用于返回指定餐厅的评分统计数据
    @RequestMapping(value = "/ugc/v2/restaurants/{restaurant_id}/ratings/scores", method = RequestMethod.GET)
    public Object score(@PathVariable("restaurant_id") Long restaurantId) {
    // 从数据库中查询评分数据，并返回评分统计数据
        Map map = mongoRepository.findOne("ratings", "restaurant_id", restaurantId);
        return map.get("scores");
    }

    // 定义一个处理方法，用于返回指定餐厅的评分标签数据
    @RequestMapping(value = "/ugc/v2/restaurants/{restaurant_id}/ratings/tags", method = RequestMethod.GET)
    public Object tags(@PathVariable("restaurant_id") Long restaurantId) {
        // 从数据库中查询评分数据，并返回成功响应，并包含评分标签数据
        Ratings ratings = mongoRepository.findOne(Ratings.class, "restaurant_id", restaurantId);
        // return map.get("tags");
        return Rets.success(ratings.getTags());
    }

}