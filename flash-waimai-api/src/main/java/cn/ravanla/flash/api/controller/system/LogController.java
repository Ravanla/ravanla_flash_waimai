package cn.ravanla.flash.api.controller.system;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.constant.factory.PageFactory;
import cn.ravanla.flash.bean.constant.state.BizLogType;
import cn.ravanla.flash.bean.entity.system.OperationLog;
import cn.ravanla.flash.bean.enumeration.Permission;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.bean.vo.query.SearchFilter;
import cn.ravanla.flash.service.system.OperationLogService;
import cn.ravanla.flash.utils.BeanUtil;
import cn.ravanla.flash.utils.DateUtil;
import cn.ravanla.flash.utils.HttpKit;
import cn.ravanla.flash.utils.factory.Page;
import cn.ravanla.flash.warpper.LogWarpper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * LogController
 *
 *@Author ravanla
 * @version 2020/10/5 0005
 */
@RestController
@RequestMapping("/log")
public class LogController extends BaseController {
    @Autowired
    private OperationLogService operationLogService;

    /**
     * 查询操作日志列表
     */
    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions(value = {Permission.LOG})
    public Object list(@RequestParam(required = false) String beginTime,
                       @RequestParam(required = false) String endTime,
                       @RequestParam(required = false) String logName,
                       @RequestParam(required = false) Integer logType) {
        // 创建一个page实例
        Page page = new PageFactory().defaultPage();

        // 添加创建时间大于等于beginTime过滤条件
        page.addFilter("createTime", SearchFilter.Operator.GTE, DateUtil.parseDate(beginTime));

        // 添加创建时间小于等于endTime过滤条件
        page.addFilter("createTime", SearchFilter.Operator.LTE, DateUtil.parseDate(endTime));

        // 添加logName模糊查询过滤条件
        page.addFilter( "logname", SearchFilter.Operator.LIKE, logName);

        // 如果输入了logType，则添加logType查询过滤条件
        if (logType != null) {

            page.addFilter(SearchFilter.build("logtype", SearchFilter.Operator.EQ, BizLogType.valueOf(logType)));
        }

        // 查询page
        page = operationLogService.queryPage(page);

        // 将page的记录转换为map
        page.setRecords((List) new LogWarpper(BeanUtil.objectsToMaps(page.getRecords())).warp());

        // 返回结果
        return Rets.success(page);
    }

    /**
     * 查询指定用户的操作日志列表
     */
    @RequestMapping("/queryByUser")
    @ResponseBody
    @RequiresPermissions(value = {Permission.LOG})
    public Object list() {
        Page<OperationLog> page = new Page<OperationLog>();
        page.addFilter(SearchFilter.build("userid", SearchFilter.Operator.EQ, getIdUser(HttpKit.getRequest())));
        Page<OperationLog> pageResult = operationLogService.queryPage(page);
        return Rets.success(pageResult.getRecords());
    }

    /**
     * 清空日志
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @RequiresPermissions(value = {Permission.LOG_CLEAR})
    public Object delLog() {
        operationLogService.clear();
        return Rets.success();
    }
}
