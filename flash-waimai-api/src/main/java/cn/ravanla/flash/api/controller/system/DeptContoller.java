package cn.ravanla.flash.api.controller.system;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.core.BussinessLog;
import cn.ravanla.flash.bean.dictmap.DeptDict;
import cn.ravanla.flash.bean.entity.system.Dept;
import cn.ravanla.flash.bean.enumeration.BizExceptionEnum;
import cn.ravanla.flash.bean.enumeration.Permission;
import cn.ravanla.flash.bean.exception.ApplicationException;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.bean.vo.node.DeptNode;
import cn.ravanla.flash.service.system.DeptService;
import cn.ravanla.flash.service.system.LogObjectHolder;
import cn.ravanla.flash.utils.ToolUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * DeptContoller
 *
 *@Author ravanla
 * @version 2020/9/15 0015
 */
@RestController
@RequestMapping("/dept")
public class DeptContoller extends BaseController {
    private Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private DeptService deptService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.DEPT})
    public Object list(){
        List<DeptNode> list = deptService.queryAllNode();
        return Rets.success(list);
    }
    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑部门", key = "simplename", dict = DeptDict.class)
    @RequiresPermissions(value = {Permission.DEPT_EDIT})
    public Object save(@ModelAttribute @Valid Dept dept){
        if (ToolUtil.isOneEmpty(dept, dept.getSimplename())) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        if(dept.getId()!=null){
            Dept old = deptService.get(dept.getId());
            LogObjectHolder.me().set(old);
            old.setPid(dept.getPid());
            old.setSimplename(dept.getSimplename());
            old.setFullname(dept.getFullname());
            old.setNum(dept.getNum());
            old.setTips(dept.getTips());
            deptService.deptSetPids(old);
            deptService.update(old);
        }else {
            deptService.deptSetPids(dept);
            deptService.insert(dept);
        }
        return Rets.success();
    }
    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除部门", key = "id", dict = DeptDict.class)
    @RequiresPermissions(value = {Permission.DEPT_DEL})
    public Object remove(@RequestParam  Long id){
        logger.info("id:{}",id);
        if (ToolUtil.isEmpty(id)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        deptService.deleteDept(id);
        return Rets.success();
    }
}