package cn.enilu.flash.api.controller.cms;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.dictmap.CommonDict;
import cn.enilu.flash.bean.entity.cms.Banner;
import cn.enilu.flash.bean.enumeration.Permission;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.cms.BannerService;
import cn.enilu.flash.utils.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * banner管理
 */
// 这段代码是一个基于Spring Boot框架的RESTful API，用于实现管理Banner（横幅广告）的功能。
// 其中，@RestController注解表示这是一个RESTful API的控制器，
@RestController

// @RequestMapping注解指定了API的根路径。
//@RequestMapping("/banner")和@RequestMapping(method = RequestMethod.POST)是配合使用的注解。
//@RequestMapping("/banner")注解标注在类上面，表示当前类所处理的请求都是以/banner开头的；
//而@RequestMapping(method = RequestMethod.POST)注解标注在方法上面，表示该方法所处理的请求是以POST请求方式发送过来的。
//因此，当请求URL为/banner且请求方式为POST时，就会被这个控制器的save()方法所处理。
@RequestMapping("/banner")

public class BannerMgrController extends BaseController {
    // 代码中引入了BannerService接口，用于对Banner进行操作。

    @Autowired
    private BannerService bannerService;

    // 在代码中，@RequestMapping注解表示了请求的URL路径，
    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑banner", key = "title", dict = CommonDict.class)
    // @RequiresPermissions注解指定了该请求需要的权限。
    @RequiresPermissions(value = {Permission.BANNER_EDIT})
    // 在save方法中，根据是否存在ID来判断是新增还是更新Banner，并调用对应的方法进行操作。
    public Object save(@ModelAttribute @Valid Banner banner) {
        if(banner.getId()==null){
            bannerService.insert(banner);
        }else {
            bannerService.update(banner);
        }
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除banner", key = "id", dict = CommonDict.class)
    @RequiresPermissions(value = {Permission.BANNER_DEL})
    // 在remove方法中，根据ID删除对应的Banner。
    public Object remove(Long id) {
        bannerService.delete(id);
        return Rets.success();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.BANNER})
    public Object list(@RequestParam(required = false) String title) {
        SearchFilter filter = null;
        // 在list方法中，如果title参数不为空，就创建一个SearchFilter对象进行模糊查询，
        if(StringUtils.isNotEmpty(title)){
             filter =  SearchFilter.build("title", SearchFilter.Operator.LIKE,title);
        }
        // 然后调用BannerService接口中的queryAll方法获取Banner列表。
        List<Banner> list = bannerService.queryAll(filter);
        return Rets.success(list);
    }
}
