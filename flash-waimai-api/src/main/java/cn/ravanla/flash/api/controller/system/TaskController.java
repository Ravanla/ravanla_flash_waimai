package cn.ravanla.flash.api.controller.system;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.constant.factory.PageFactory;
import cn.ravanla.flash.bean.core.BussinessLog;
import cn.ravanla.flash.bean.dictmap.TaskDict;
import cn.ravanla.flash.bean.entity.system.Task;
import cn.ravanla.flash.bean.entity.system.TaskLog;
import cn.ravanla.flash.bean.enumeration.Permission;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.bean.vo.query.SearchFilter;
import cn.ravanla.flash.service.task.TaskLogService;
import cn.ravanla.flash.service.task.TaskService;
import cn.ravanla.flash.utils.StringUtils;
import cn.ravanla.flash.utils.factory.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created  on 2020/4/9 0009.
 * 系统参数
 *@Author ravanla
 */
@RestController
@RequestMapping("/task")
public class TaskController extends BaseController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskLogService taskLogService;


    /**
     * 获取定时任务管理列表
     */
    @RequestMapping(value = "/list")
    @RequiresPermissions(value = {Permission.TASK})
    public Object list(String name) {
        if(StringUtils.isNullOrEmpty(name)) {
            return Rets.success(taskService.queryAll());
        }else{
            return Rets.success(taskService.queryAllByNameLike(name));
        }
    }

    /**
     * 新增定时任务管理
     */
    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑定时任务", key = "name",dict = TaskDict.class)
    @RequiresPermissions(value = {Permission.TASK_EDIT})
    public Object add(@ModelAttribute @Valid Task task) {
        if(task.getId()==null) {
            taskService.save(task);
        }else{
            Task old = taskService.get(task.getId());
            old.setName(task.getName());
            old.setCron(task.getCron());
            old.setJobClass(task.getJobClass());
            old.setNote(task.getNote());
            old.setData(task.getData());
            taskService.update(old);
        }
        return Rets.success();
    }

    /**
     * 删除定时任务管理
     */
    @RequestMapping(method = RequestMethod.DELETE)

    @BussinessLog(value = "删除定时任务", key = "taskId",dict = TaskDict.class)
    @RequiresPermissions(value = {Permission.TASK_DEL})
    public Object delete(@RequestParam Long id) {
        taskService.delete(id);
        return Rets.success();
    }

    @RequestMapping(value = "/disable",method = RequestMethod.POST)

    @BussinessLog(value = "禁用定时任务", key = "taskId",dict = TaskDict.class)
    @RequiresPermissions(value = {Permission.TASK_EDIT})
    public Object disable(@RequestParam Long taskId  ) {
        taskService.disable(taskId);
        return Rets.success();
    }
    @RequestMapping(value = "/enable",method = RequestMethod.POST)
    @BussinessLog(value = "启用定时任务", key = "taskId",dict = TaskDict.class)
    @RequiresPermissions(value = {Permission.TASK_EDIT})
    public Object enable(@RequestParam Long taskId  ) {
        taskService.enable(taskId);
        return Rets.success();
    }


    @RequestMapping(value="/logList")
    @RequiresPermissions(value = {Permission.TASK})
    public Object logList(@RequestParam  Long taskId) {
        Page<TaskLog> page = new PageFactory<TaskLog>().defaultPage();
        page.addFilter(SearchFilter.build("idTask", SearchFilter.Operator.EQ,taskId));
        page = taskLogService.queryPage(page);
        return Rets.success(page);
    }

}
