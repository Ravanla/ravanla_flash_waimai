package cn.ravanla.flash.api.controller.system;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.constant.Const;
import cn.ravanla.flash.bean.core.BussinessLog;
import cn.ravanla.flash.bean.dictmap.RoleDict;
import cn.ravanla.flash.bean.entity.system.Role;
import cn.ravanla.flash.bean.entity.system.User;
import cn.ravanla.flash.bean.enumeration.BizExceptionEnum;
import cn.ravanla.flash.bean.enumeration.Permission;
import cn.ravanla.flash.bean.exception.ApplicationException;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.bean.vo.node.Node;
import cn.ravanla.flash.bean.vo.node.ZTreeNode;
import cn.ravanla.flash.service.system.LogObjectHolder;
import cn.ravanla.flash.service.system.RoleService;
import cn.ravanla.flash.service.system.UserService;
import cn.ravanla.flash.service.system.impl.ConstantFactory;
import cn.ravanla.flash.utils.BeanUtil;
import cn.ravanla.flash.utils.Convert;
import cn.ravanla.flash.utils.Maps;
import cn.ravanla.flash.utils.ToolUtil;
import cn.ravanla.flash.warpper.RoleWarpper;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.ROLE})
    public Object list(String name) {
        logger.info("=========" + Json.toJson(roleService.get(3L)));
        List roles = null;
        if (Strings.isNullOrEmpty(name)) {
            roles = roleService.queryAll();
        } else {
            roles = roleService.findByName(name);
        }
        return Rets.success(new RoleWarpper(BeanUtil.objectsToMaps(roles)).warp());
    }

    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑角色", key = "name", dict = RoleDict.class)
    @RequiresPermissions(value = {Permission.ROLE_EDIT})
    public Object save(@Valid Role role) {
        if (role.getId() == null) {
            roleService.insert(role);
        } else {
            roleService.update(role);
        }
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除角色", key = "roleId", dict = RoleDict.class)
    @RequiresPermissions(value = {Permission.ROLE_DEL})
    public Object remove(@RequestParam Long roleId) {

        logger.info("id:{}", roleId);

        // 检查角色ID是否为空
        if (ToolUtil.isEmpty(roleId)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }

        // 检查角色ID是否小于2
        if (roleId.intValue() < 2) {
            return Rets.failure("不能删除初始角色");
        }

        // 不能删除超级管理员角色
        if (roleId.equals(Const.ADMIN_ROLE_ID)) {
            throw new ApplicationException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }

        // 缓存被删除的角色名称
        LogObjectHolder.me().set(ConstantFactory.me().getSingleRoleName(roleId));

        // 根据角色ID删除角色
        roleService.delRoleById(roleId);
        return Rets.success();
    }

    // 配置角色权限的功能
    @RequestMapping(value = "/savePermisson", method = RequestMethod.POST)
    @BussinessLog(value = "配置角色权限", key = "roleId", dict = RoleDict.class)
    @RequiresPermissions(value = {Permission.ROLE_EDIT})
    public Object setAuthority(Long roleId, String
            permissions) {
        if (ToolUtil.isOneEmpty(roleId)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }

        // 调用roleService.setAuthority()来设置权限，如果参数为空，则抛出异常，否则返回成功结果。
        roleService.setAuthority(roleId, permissions);
        return Rets.success();
    }


    /**
     * 获取角色树
     */

//    @RequestMapping(value = "/roleTreeListByIdUser", method = RequestMethod.GET)
//    @RequiresPermissions(value = {Permission.ROLE})
//    public Object roleTreeListByIdUser(Long idUser) {
//        User user = userService.get(idUser);
//        String roleIds = user.getRoleid();
//        List<ZTreeNode> roleTreeList = null;
//        if (ToolUtil.isEmpty(roleIds)) {
//            roleTreeList = roleService.roleTreeList();
//        } else {
//            Long[] roleArray = Convert.toLongArray(",", roleIds);
//            roleTreeList = roleService.roleTreeListByRoleId(roleArray);
//
//        }
//        List<Node> list = roleService.generateRoleTree(roleTreeList);
//        List<Long> checkedIds = Lists.newArrayList();
//        for (ZTreeNode zTreeNode : roleTreeList) {
//            if (zTreeNode.getChecked() != null && zTreeNode.getChecked()) {
//                checkedIds.add(zTreeNode.getId());
//            }
//        }
//        return Rets.success(Maps.newHashMap("treeData", list, "checkedIds", checkedIds));
//    }

    //    这段代码的作用是通过指定的用户id获取角色树，并将用户拥有的角色设置为选中状态。
//    注释：// 通过用户id获取角色树，并将用户拥有的角色设置为选中状态
    @RequestMapping(value = "/roleTreeListByIdUser", method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.ROLE})
    public Object roleTreeListByIdUser(Long idUser) { // 通过id获取用户信息
        User user = userService.get(idUser); // 获取用户拥有的角色id
        String roleIds = user.getRoleid(); // 定义角色树list
        List<ZTreeNode> roleTreeList = null; // 判断角色id是否为空
        if (ToolUtil.isEmpty(roleIds)) { // 若为空，获取全部角色树
            roleTreeList = roleService.roleTreeList();
        } else { // 若不为空，根据角色id获取角色树
            Long[] roleArray = Convert.toLongArray(",", roleIds);
            roleTreeList = roleService.roleTreeListByRoleId(roleArray);
        } // 生成角色树
        List list = roleService.generateRoleTree(roleTreeList); // 存储已选中的角色id
        List checkedIds = Lists.newArrayList(); // 将用户拥有的角色设置为选中
        for (ZTreeNode zTreeNode : roleTreeList) {
            if (zTreeNode.getChecked() != null && zTreeNode.getChecked()) {
                checkedIds.add(zTreeNode.getId());
            }
        }
        return Rets.success(Maps.newHashMap("treeData", list, "checkedIds", checkedIds));
    }
}
