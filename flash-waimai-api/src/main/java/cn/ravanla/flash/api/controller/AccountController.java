package cn.ravanla.flash.api.controller;

import cn.ravanla.flash.api.utils.ApiConstants;
import cn.ravanla.flash.bean.core.ShiroUser;
import cn.ravanla.flash.bean.entity.front.Shop;
import cn.ravanla.flash.bean.entity.system.User;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.dao.MongoRepository;
import cn.ravanla.flash.security.AccountInfo;
import cn.ravanla.flash.security.JwtUtil;
import cn.ravanla.flash.security.ShiroFactroy;
import cn.ravanla.flash.service.system.AccountService;
import cn.ravanla.flash.service.system.MenuService;
import cn.ravanla.flash.service.system.UserService;
import cn.ravanla.flash.utils.*;
import cn.ravanla.flash.utils.*;
import org.nutz.mapl.Mapl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AccountController
 *
 *@Author ravanla
 * @version 2020/9/12 0012
 */
@RestController
@RequestMapping("/account")
public class AccountController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MongoRepository mongoRepository;


    /**
     * 用户登录<br>
     * 1，验证没有注册<br>
     * 2，验证密码错误<br>
     * 3，登录成功
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(
            @RequestParam("userType") String userType,
            @RequestParam("username") String userName,
            @RequestParam("password") String password) {
        try {
            logger.info("用户登录:" + userName + ",密码:" + password);
            String token = null;
            if (Constants.USER_TYPE_MGR.equals(userType)) {
                //1,
                User user = userService.findByAccount(userName);
                if (user == null) {
                    return Rets.failure("该用户不存在");
                }
                String passwdMd5 = MD5.md5(password, user.getSalt());
                //2,
                if (!user.getPassword().equals(passwdMd5)) {
                    return Rets.failure("输入的密码错误");
                }
                token = JwtUtil.sign(user);
            } else if (Constants.USER_TYPE_SHOP.equals(userType)) {

                // 通过MongoRepository查询相应的Shop实例，查询条件为name为userName，password为password
                Shop shop = mongoRepository.findOne(Shop.class, Maps.newHashMap("name", userName, "password", password));
                // Shop shop = mongoRepository.findOne(Shop.class, Maps.newHashMap("name", userName, "password", password));
                if(shop==null){
                    return Rets.failure("没有改账号");
                }
                if(shop.getDisabled() == 1){
                    return Rets.failure("该商户已停用");
                }
                if (shop == null) {
                    return Rets.failure("账号或密码错误");
                }
                token = JwtUtil.sign(shop);
            }

            Map<String, String> result = new HashMap<>(1);
            logger.info("token:{}", token);
            result.put("token", token);

            return Rets.success(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Rets.failure("登录时失败");
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Object logout(HttpServletRequest request) {
        String token = this.getToken(request);
        accountService.logout(token);
        return Rets.success();
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Object info(HttpServletRequest request) {
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        Long idUser = accountInfo.getUserId();
        if (idUser != null) { // 如果idUser有值
            ShiroUser shiroUser = null; // 定义一个ShiroUser变量
            Map profile = null; // 定义Map变量

            // 如果accountInfo的userType属性等于USERTYPEMGR
            if (Constants.USER_TYPE_MGR.equals(accountInfo.getUserType())) {
                User user = userService.get(idUser); // 从userService中获取idUser的值

                if (StringUtils.isEmpty(user.getRoleid())) { // 如果user的roleId属性为空
                    return Rets.failure("该用户未配置权限"); // 返回错误信息
                }

                shiroUser = ShiroFactroy.me().shiroUser(user); // 将user的信息转换为shiroUser
                profile = (Map) Mapl.toMaplist(user); // 将user转换为Map
            } else {

                // 从mongoRepository中获取name和password属性对应的Shop对象
                Shop shop = mongoRepository.findOne(Shop.class, Maps.newHashMap("name", accountInfo.getUsername(), "password", accountInfo.getPassword()));
                shiroUser = ShiroFactroy.me().shiroUser(shop); // 将shop的信息转换为shiroUser
                profile = (Map) Mapl.toMaplist(shop); // 将shop转换为Map
            }

            // 将name,role,roles属性转换为Map
            Map map = Maps.newHashMap("name", accountInfo.getUsername(), "role", "admin", "roles", shiroUser.getRoleCodes());
            List menus = menuService.getMenusByRoleIds(shiroUser.getRoleList()); // 从menuService中获取shiroUser的roleList

            map.put("menus", menus); // 将menus放入map中
            map.put("permissions", shiroUser.getUrls()); // 将permissions放入map中
            profile.put("dept", shiroUser.getDeptName()); // 将dept放入profile中
            profile.put("roles", shiroUser.getRoleNames()); // 将roles放入profile中
            map.put("profile", profile); // 将profile放入map中
            return Rets.success(map); // 返回map
        }
        return Rets.failure("获取用户信息失败");
    }

    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public Object updatePwd(String oldPassword, String password, String rePassword) {
        try {
            if (!password.equals(rePassword)) {
                return Rets.failure("新密码前后不一致");
            }
            AccountInfo accountInfo = JwtUtil.getAccountInfo();
//            if(Constants.USER_TYPE_MGR.equals(accountInfo.getUserType())) {
//                User user = userService.get(getIdUser(HttpKit.getRequest()));
//                if (ApiConstants.ADMIN_ACCOUNT.equals(user.getAccount())) {
//                    return Rets.failure("不能修改超级管理员密码");
//                }
//                logger.info("oldPassword:{},password:{},rePassword:{}", MD5.md5(oldPassword, user.getSalt()), password, rePassword);
//
//                if (!MD5.md5(oldPassword, user.getSalt()).equals(user.getPassword())) {
//                    return Rets.failure("旧密码输入错误");
//                }
//
//                user.setPassword(MD5.md5(password, user.getSalt()));
//                userService.update(user);
            // 检查用户类型是否为管理员
            if(Constants.USER_TYPE_MGR.equals(accountInfo.getUserType())) { // 检查用户是否为普通用户
                // 获取当前用户
                User user = userService.get(getIdUser(HttpKit.getRequest()));
                // 检查是否为超级管理员
                if (ApiConstants.ADMIN_ACCOUNT.equals(user.getAccount())) {
                    return Rets.failure("不能修改超级管理员密码");
                }
                // 输出日志
                logger.info("oldPassword:{},password:{},rePassword:{}", MD5.md5(oldPassword, user.getSalt()), password, rePassword);
                // 检查旧密码是否正确
                if (!MD5.md5(oldPassword, user.getSalt()).equals(user.getPassword())) {
                    return Rets.failure("旧密码输入错误");
                }
                // 更新密码
                user.setPassword(MD5.md5(password, user.getSalt()));
                userService.update(user);
            }else if(Constants.USER_TYPE_MGR.equals(accountInfo.getUserType())){ // 检查用户是否为商家
            // 此代码段检测用户是否为商家，
            // 如果是商家，就查询数据库是否有指定用户名和旧密码的商家信息，
            // 如果有，则进行更新密码，否则返回报错信息。


                // 查询指定用户名和旧密码的商家信息
                Shop shop = mongoRepository.findOne(Shop.class, Maps.newHashMap("name", accountInfo.getUsername(), "password", oldPassword));

                // 如果查询不到，则返回报错
                if(shop==null){
                    return Rets.failure("旧密码输入错误");
                }
                // 检查用户id是否匹配
                if(shop.getId()!=accountInfo.getUserId()){
                    // 基本不会出现这种情况
                    return Rets.failure("不允许该操作");
                }
                // 执行更新密码操作
                mongoRepository.update(accountInfo.getUserId(),"shops",Maps.newHashMap("password",password));
            }
            // 操作成功
            return Rets.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Rets.failure("更改密码失败");
    }


}
