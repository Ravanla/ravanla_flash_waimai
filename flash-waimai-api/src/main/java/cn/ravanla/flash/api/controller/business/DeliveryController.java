package cn.ravanla.flash.api.controller.business;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.entity.front.Delivery;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.dao.MongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2020/1/5 0005.
 *
 *@Author ravanla
 */
@RestController
public class DeliveryController extends BaseController {
    @Autowired
    private MongoRepository mongoRepository;

    @RequestMapping(value = "/shopping/v1/restaurants/delivery_modes",method = RequestMethod.GET)
    public Object list(@RequestParam(value = "latitude",required = false) String latitude,
                       @RequestParam(value = "longitude",required = false) String longitude){
        return Rets.success(mongoRepository.findAll(Delivery.class));
    }
}
