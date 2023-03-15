package cn.ravanla.flash.api.controller.system;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.constant.factory.PageFactory;
import cn.ravanla.flash.bean.entity.system.LoginLog;
import cn.ravanla.flash.bean.enumeration.Permission;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.bean.vo.query.SearchFilter;
import cn.ravanla.flash.service.system.LoginLogService;
import cn.ravanla.flash.utils.BeanUtil;
import cn.ravanla.flash.utils.DateUtil;
import cn.ravanla.flash.utils.factory.Page;
import cn.ravanla.flash.warpper.LogWarpper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 登录日志controller
 *
 *@Author ravanla
 * @version 2020/10/5 0005
 */
@RestController
@RequestMapping("/loginLog")
public class LoginLogController extends BaseController {
    @Autowired
    private LoginLogService loginlogService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.LOGIN_LOG})
    public Object list(@RequestParam(required = false) String beginTime,
                       @RequestParam(required = false) String endTime,
                       @RequestParam(required = false) String logName) {
        // 实例化Page对象，参数为LoginLog类
        Page page = new PageFactory().defaultPage();
        // 添加createTime过滤，过滤条件为大于等于beginTime给定的日期
        page.addFilter("createTime", SearchFilter.Operator.GTE, DateUtil.parseDate(beginTime));
        // 添加createTime过滤，过滤条件为小于等于endTime给定的日期
        page.addFilter("createTime", SearchFilter.Operator.LTE, DateUtil.parseDate(endTime));
        // 添加logname过滤，过滤条件为logName字段模糊匹配
        page.addFilter("logname", SearchFilter.Operator.LIKE, logName);
        // 执行查询，结果赋值给pageResult
        Page pageResult = loginlogService.queryPage(page);
        // 将查询结果转换为Map集合，并封装成LogWarpper对象
        pageResult.setRecords((List) new LogWarpper(BeanUtil.objectsToMaps(pageResult.getRecords())).warp());
        // 返回查询结果
        return Rets.success(pageResult);

    }


    /**
     * 清空日志
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @RequiresPermissions(value = {Permission.LOGIN_LOG_CLEAR})
    public Object clear() {
        loginlogService.clear();
        return Rets.success();
    }
}
