package cn.enilu.flash.api.controller.cms;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.cms.Contacts;
import cn.enilu.flash.bean.enumeration.Permission;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.cms.ContactsService;
import cn.enilu.flash.utils.DateUtil;
import cn.enilu.flash.utils.factory.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 邀约信息管理
 */
@RestController // @RestController和@RequestMapping注释定义了一个控制器ContactsController
@RequestMapping("/contacts") // 注入了ContactsService对象 @RequestMapping注释中定义了对应的请求路径“/contacts/list”和请求方式GET

public class ContactsController extends BaseController {
    @Autowired
    private ContactsService contactsService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    // list()方法是用于返回邀约信息列表的。
    @RequiresPermissions(value = {Permission.CONTACTS})
    // 方法中定义了四个@RequestParam注释的参数，用于接收请求中的查询条件。
    public Object list(@RequestParam(required = false) String userName,
                       @RequestParam(required = false) String mobile,
                       @RequestParam(required = false) String startDate,
                       @RequestParam(required = false) String endDate

    ) {
        // 然后创建了一个Page对象，调用PageFactory类中的defaultPage()方法对分页信息进行初始化。
        Page<Contacts> page = new PageFactory<Contacts>().defaultPage();
        // 接着使用Page对象的addFilter()方法添加了4个过滤条件，分别是创建时间、用户名、手机号码和日期区间。
        page.addFilter("createTime", SearchFilter.Operator.GTE, DateUtil.parse(startDate,"yyyyMMddHHmmss"));
        page.addFilter("createTime", SearchFilter.Operator.LTE, DateUtil.parse(endDate,"yyyyMMddHHmmss"));
        page.addFilter("userName", SearchFilter.Operator.EQ,userName);
        page.addFilter("mobile", SearchFilter.Operator.EQ,mobile);
        // 最后调用ContactsService中的queryPage()方法查询符合条件的邀约信息并返回。
        page = contactsService.queryPage(page);
        return Rets.success(page);
    }
    // 实现了根据用户名、手机号码、时间区间等条件进行筛选，并使用分页进行展示。
}
