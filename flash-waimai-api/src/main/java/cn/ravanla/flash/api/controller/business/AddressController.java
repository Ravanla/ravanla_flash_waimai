package cn.ravanla.flash.api.controller.business;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.entity.front.Address;
import cn.ravanla.flash.bean.entity.front.Ids;
import cn.ravanla.flash.bean.enumeration.BizExceptionEnum;
import cn.ravanla.flash.bean.exception.ApplicationException;
import cn.ravanla.flash.bean.vo.business.City;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.dao.MongoRepository;
import cn.ravanla.flash.service.front.IdsService;
import cn.ravanla.flash.service.front.PositionService;
import cn.ravanla.flash.utils.Maps;
import cn.ravanla.flash.utils.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created  on 2020/1/5 0005.
 *
 *@Author ravanla
 */
// 使用@RestController注解标识该类为处理HTTP请求的控制器，继承了BaseController类。
@RestController
public class AddressController extends BaseController {
    // 此外，该类中还定义了一个logger属性，用于记录日志。
    private Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private MongoRepository mongoRepository;

    @Autowired
    private IdsService idsService;

    @Autowired
    private PositionService positionService;

    // address方法：处理GET /v1/users/{user_id}/addresses请求，使用PathVariable注解从URL路径中获取userId，
    // 然后调用mongoRepository的findAll方法获取该用户的地址列表，并返回给客户端。
    @RequestMapping(value = "/v1/users/{user_id}/addresses",method = RequestMethod.GET)
    public Object address(@PathVariable("user_id") Long userId){
        return Rets.success(mongoRepository.findAll(Address.class,"user_id",userId));
    }

    // save方法：处理POST /v1/users/{user_id}/addresses请求，使用PathVariable注解从URL路径中获取userId，
    // 使用PositionService的guessCity方法获取客户端IP所在城市信息，
    // 使用getRequestPayload方法获取客户端提交的Address对象，
    // 并设置其user_id、city_id和id属性，
    // 调用mongoRepository的save方法将该Address对象保存到MongoDB中，然后返回添加地址成功信息。
    @RequestMapping(value = "/v1/users/{user_id}/addresses",method =  RequestMethod.POST)
    public Object save(@PathVariable("user_id") Long userId){
        City city = positionService.guessCity(getIp());
        Address address = getRequestPayload(Address.class);
        address.setUser_id(userId);
        address.setCity_id(city.getId());
        address.setId(idsService.getId(Ids.ADDRESS_ID));
        mongoRepository.save(address);
        return Rets.success("添加地址成功");
    }

    // delete方法：处理POST /v1/users/${user_id}/addresses/${address_id}请求，
    // 使用PathVariable注解从URL路径中获取userId和addressId，
    // 使用mongoRepository的delete方法从MongoDB中删除该用户的指定地址信息，并返回删除成功信息。
    @RequestMapping(value = "/v1/users/${user_id}/addresses/${address_id}",method =  RequestMethod.POST)
    public Object delete(@PathVariable("user_id") Long userId, @PathVariable("address_id") Long addressId){
        mongoRepository.delete("addresses", Maps.newHashMap("user_id",userId,"id",addressId));
        return Rets.success("删除地址成功");
    }

    // get方法：处理GET /address/{id}请求，使用PathVariable注解从URL路径中获取id参数，
    // 使用mongoRepository的findOne方法根据该id从MongoDB中获取指定的Address对象，并返回给客户端。
    @RequestMapping(value="/address/{id}",method = RequestMethod.GET)
    public Object get(@PathVariable Long id){
        //用于记录应用程序在运行过程中的信息，以方便后续的问题排查和分析。
        logger.info("id:{}",id);
        if (ToolUtil.isEmpty(id)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
         return Rets.success(mongoRepository.findOne(Address.class,id));
    }

}
