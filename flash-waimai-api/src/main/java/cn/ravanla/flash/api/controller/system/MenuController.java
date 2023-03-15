package cn.ravanla.flash.api.controller.system;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.constant.state.MenuStatus;
import cn.ravanla.flash.bean.core.BussinessLog;
import cn.ravanla.flash.bean.dictmap.MenuDict;
import cn.ravanla.flash.bean.entity.system.Menu;
import cn.ravanla.flash.bean.enumeration.BizExceptionEnum;
import cn.ravanla.flash.bean.enumeration.Permission;
import cn.ravanla.flash.bean.exception.ApplicationException;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.bean.vo.node.MenuNode;
import cn.ravanla.flash.bean.vo.node.Node;
import cn.ravanla.flash.bean.vo.node.ZTreeNode;
import cn.ravanla.flash.service.system.LogObjectHolder;
import cn.ravanla.flash.service.system.MenuService;
import cn.ravanla.flash.service.system.impl.ConstantFactory;
import cn.ravanla.flash.utils.Maps;
import cn.ravanla.flash.utils.ToolUtil;
import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * MenuController
 *
 * @version 2020/9/12 0012
 * @Author ravanla
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(MenuController.class);
    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.MENU})
    public Object list() {
        List<MenuNode> list = menuService.getMenus();
        return Rets.success(list);
    }

    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑菜单", key = "name", dict = MenuDict.class)
    @RequiresPermissions(value = {Permission.MENU_EDIT})
    public Object save(@ModelAttribute @Valid Menu menu) {
        // 判断是否存在该编号
        // 检查菜单ID是否有值
        if (menu.getId() == null) {
            // 根据输入的菜单代码在常量工厂中查找菜单名称
            String existedMenuName = ConstantFactory.me().getMenuNameByCode(menu.getCode());
            // 检查菜单名称是否存在
            if (ToolUtil.isNotEmpty(existedMenuName)) {
                // 如果存在抛出异常
                throw new ApplicationException(BizExceptionEnum.EXISTED_THE_MENU);
            }
            // 设置菜单状态为已启用
            menu.setStatus(MenuStatus.ENABLE.getCode());
        }

        // 设置父级菜单编号
        menuService.menuSetPcode(menu);
        if (menu.getId() == null) {
            menuService.insert(menu);
        } else {
            menuService.update(menu);
        }
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除菜单", key = "id", dict = MenuDict.class)
    @RequiresPermissions(value = {Permission.MENU_DEL})
    public Object remove(@RequestParam Long id) {
        logger.info("id:{}", id);
        if (ToolUtil.isEmpty(id)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        // 演示环境不允许删除初始化的菜单
        if (id.intValue() < 70) {
            return Rets.failure("演示环境不允许删除初始菜单");
        }
        // 缓存菜单的名称
        LogObjectHolder.me().set(ConstantFactory.me().getMenuName(id));
        menuService.delMenuContainSubMenus(id);
        return Rets.success();
    }

    /**
     * 获取菜单树
     */

    @RequestMapping(value = "/menuTreeListByRoleId", method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.MENU})

    // public Object menuTreeListByRoleId(Integer roleId) {
    // List<Long> menuIds = menuService.getMenuIdsByRoleId(roleId);
    // List<ZTreeNode> roleTreeList = null;
    // if (ToolUtil.isEmpty(menuIds)) {
    // roleTreeList = menuService.menuTreeList(null);
    // } else {
    // roleTreeList = menuService.menuTreeList(menuIds);
    // }
    // List<Node> list = menuService.generateMenuTreeForRole(roleTreeList);
    //
    // // element-ui中tree控件中如果选中父节点会默认选中所有子节点，所以这里将所有非叶子节点去掉
    // Map<Long,ZTreeNode> map = cn.ravanla.flash.utils.Lists.toMap(roleTreeList,"id");
    // Map<Long,List<ZTreeNode>> group = cn.ravanla.flash.utils.Lists.group(roleTreeList,"pId");
    // for(Map.Entry<Long,List<ZTreeNode>> entry:group.entrySet()){
    // if(entry.getValue().size()>1){
    // roleTreeList.remove(map.get(entry.getKey()));
    // }
    // }
    //
    // List<Long> checkedIds = Lists.newArrayList();
    // for (ZTreeNode zTreeNode : roleTreeList) {
    // if (zTreeNode.getChecked() != null && zTreeNode.getChecked()
    // &&zTreeNode.getpId().intValue()!=0) {
    // checkedIds.add(zTreeNode.getId());
    // }
    // }
    // return Rets.success(Maps.newHashMap("treeData", list, "checkedIds", checkedIds));
    // }

    // 根据角色ID获取菜单树
    public Object menuTreeListByRoleId(Integer roleId) {
        // 根据角色ID获取菜单ID
        List menuIds = menuService.getMenuIdsByRoleId(roleId);
        // 菜单树
        List<ZTreeNode> roleTreeList = null;

        // 若菜单ID为空，则获取所有菜单树
        if (ToolUtil.isEmpty(menuIds)) {
            roleTreeList = menuService.menuTreeList(null);
        } else {
            roleTreeList = menuService.menuTreeList(menuIds);
        }

        // 生成角色菜单树
        List list = menuService.generateMenuTreeForRole(roleTreeList);

        // element-ui中tree控件中如果选中父节点会默认选中所有子节点，所以这里将所有非叶子节点去掉
        // 将菜单树按照父节点ID分组
        Map<Long, ZTreeNode> map = cn.ravanla.flash.utils.Lists.toMap(roleTreeList, "id");
        Map<Long, List<ZTreeNode>> group = cn.ravanla.flash.utils.Lists.group(roleTreeList, "pId");

        // 遍历分组，若组中包含多个节点，则移除非叶子节点
        for (Map.Entry<Long, List<ZTreeNode>> entry : group.entrySet()) {
            if (entry.getValue().size() > 1) {
                roleTreeList.remove(map.get(entry.getKey()));
            }
        }

        // 选中节点ID
        List checkedIds = Lists.newArrayList();
        // 遍历菜单树，将选中节点ID添加至checkedIds
        for (ZTreeNode zTreeNode : roleTreeList) {
            if (zTreeNode.getChecked() != null && zTreeNode.getChecked() && zTreeNode.getpId().intValue() != 0) {
                checkedIds.add(zTreeNode.getId());
            }
        }
        return Rets.success(Maps.newHashMap("treeData", list, "checkedIds", checkedIds));
    }
}
