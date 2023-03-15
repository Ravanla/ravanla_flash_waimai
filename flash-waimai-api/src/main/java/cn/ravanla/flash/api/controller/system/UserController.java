package cn.ravanla.flash.api.controller.system;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.constant.Const;
import cn.ravanla.flash.bean.constant.factory.PageFactory;
import cn.ravanla.flash.bean.constant.state.ManagerStatus;
import cn.ravanla.flash.bean.core.BussinessLog;
import cn.ravanla.flash.bean.dictmap.UserDict;
import cn.ravanla.flash.bean.dto.UserDto;
import cn.ravanla.flash.bean.entity.system.User;
import cn.ravanla.flash.bean.enumeration.BizExceptionEnum;
import cn.ravanla.flash.bean.enumeration.Permission;
import cn.ravanla.flash.bean.exception.ApplicationException;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.bean.vo.query.SearchFilter;
import cn.ravanla.flash.core.factory.UserFactory;
import cn.ravanla.flash.service.system.UserService;
import cn.ravanla.flash.utils.BeanUtil;
import cn.ravanla.flash.utils.MD5;
import cn.ravanla.flash.utils.StringUtils;
import cn.ravanla.flash.utils.ToolUtil;
import cn.ravanla.flash.utils.factory.Page;
import cn.ravanla.flash.warpper.UserWarpper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * UserController
 *
 *@Author ravanla
 * @version 2020/9/15 0015
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.USER})
    // 注释：通过两个参数account和name实现了分页查询的功能
    public Object list(@RequestParam(required = false) String account,
                       @RequestParam(required = false) String name){

        Page page = new PageFactory().defaultPage();
        if(StringUtils.isNotEmpty(name)){
            // // 使用SearchFilter.build()方法构建查询条件
            page.addFilter(SearchFilter.build("name", SearchFilter.Operator.LIKE, name));
        }
        if(StringUtils.isNotEmpty(account)){
            page.addFilter(SearchFilter.build("account", SearchFilter.Operator.LIKE, account));
        }
        page.addFilter(SearchFilter.build("status",SearchFilter.Operator.GT,0));

        // 使用userService.queryPage()方法进行查询
        page = userService.queryPage(page);

        // 将查询出的结果转换为List
        // 使用UserWarpper函数将List结果转换为Map类型
        List list = (List) new UserWarpper(BeanUtil.objectsToMaps(page.getRecords())).warp();
        page.setRecords(list);
        return Rets.success(page);
    }

    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑管理员", key = "name", dict = UserDict.class)
    @RequiresPermissions(value = {Permission.USER_EDIT})
    public Object save( @Valid UserDto user,BindingResult result){
        if(user.getId()==null) {
            // 判断账号是否重复
            User theUser = userService.findByAccount(user.getAccount());
            if (theUser != null) {
                throw new ApplicationException(BizExceptionEnum.USER_ALREADY_REG);
            }
            // 完善账号信息
            user.setSalt(ToolUtil.getRandomString(5));
            user.setPassword(MD5.md5(user.getPassword(), user.getSalt()));
            user.setStatus(ManagerStatus.OK.getCode());
            userService.insert(UserFactory.createUser(user, new User()));
        }else{
            User oldUser = userService.get(user.getId());
            userService.update(UserFactory.updateUser(user,oldUser));
        }
        return Rets.success();
    }


    @BussinessLog(value = "删除管理员", key = "userId", dict = UserDict.class)
    @RequestMapping(method = RequestMethod.DELETE)
    @RequiresPermissions(value = {Permission.USER_DEL})
    public Object remove(@RequestParam Long userId){
        if (ToolUtil.isEmpty(userId)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        if(userId.intValue()<=2){
            return Rets.failure("不能删除初始用户");
        }
        User user = userService.get(userId);
        user.setStatus(ManagerStatus.DELETED.getCode());
        userService.update(user);
        return Rets.success();
    }


//    @BussinessLog(value="设置用户角色",key="userId",dict=UserDict.class)
//    @RequestMapping(value="/setRole",method = RequestMethod.GET)
//    @RequiresPermissions(value = {Permission.USER_EDIT})
//    public Object setRole(@RequestParam("userId") Long userId, @RequestParam("roleIds") String roleIds) {
//        if (ToolUtil.isOneEmpty(userId, roleIds)) {
//            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
//        }
//        //不能修改超级管理员
//        if (userId.equals(Const.ADMIN_ID)) {
//            throw new ApplicationException(BizExceptionEnum.CANT_CHANGE_ADMIN);
//        }
//        User user = userService.get(userId);
//        user.setRoleid(roleIds);
//        userService.update(user);
//        return Rets.success();
//    }

    // 这段代码用于设置用户角色，
    // @BussinessLog实现的功能是设置用户角色，key参数为userId，dict参数为UserDict.class
    // 记录业务日志的注释，用于跟踪业务过程中的一些重要信息，如：用户输入的参数、执行结果等。这样可以方便调试和分析问题。
    @BussinessLog(value="设置用户角色",key="userId",dict=UserDict.class)
    @RequestMapping(value="/setRole",method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.USER_EDIT})
    public Object setRole(@RequestParam("userId") Long userId, @RequestParam("roleIds") String roleIds) {

        // 检查RequestParam值userId和roleIds是否为空，
        if (ToolUtil.isOneEmpty(userId, roleIds)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);

        }

        // 不能修改超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new ApplicationException(BizExceptionEnum.CANT_CHANGE_ADMIN);

        }
        User user = userService.get(userId);

        // 不为空则获取用户实体，并设置roleid为roleIds，
        user.setRoleid(roleIds);

        // 最后更新用户实体并返回操作成功信息。
        userService.update(user);
        return Rets.success();

    }

}
