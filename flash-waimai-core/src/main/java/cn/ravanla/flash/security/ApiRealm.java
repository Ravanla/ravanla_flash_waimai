package cn.ravanla.flash.security;

import cn.ravanla.flash.bean.core.ShiroUser;
import cn.ravanla.flash.bean.entity.front.Shop;
import cn.ravanla.flash.dao.MongoRepository;
import cn.ravanla.flash.service.system.UserService;
import cn.ravanla.flash.utils.Constants;
import cn.ravanla.flash.utils.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Author ravanla
 * @date ：Created in 2021/7/30 22:58
 */
@Service
public class ApiRealm extends AuthorizingRealm {

    private Logger logger = LogManager.getLogger(getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private ShiroFactroy shiroFactroy;
    @Autowired
    private MongoRepository mongoRepository;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 首先从参数principals中获取当前用户的身份信息，并解析出账号信息accountInfo。
        AccountInfo accountInfo = JwtUtil.getAccountInfo();
        ShiroUser user = null;

        // 根据账号信息判断用户是否为商家（非管理员用户），
        if (accountInfo != null && !accountInfo.isMgr()) {
            // 如果是，则通过mongoRepository从数据库中查找对应的商家用户信息并封装到ShiroUser对象中。
            Shop shop = mongoRepository.findOne(Shop.class,
                Maps.newHashMap("name", accountInfo.getUsername(), "password", accountInfo.getPassword()));
            user = shiroFactroy.shiroUser(shop);
        } else {
            // 如果不是商家用户，则通过userService根据账号信息从数据库中查找对应的用户信息，
            // 并将其封装到ShiroUser对象中
            String username = JwtUtil.getUsername(principals.toString());
            user = shiroFactroy.shiroUser(userService.findByAccount(username));
        }

        // 创建SimpleAuthorizationInfo对象，
        // 并将ShiroUser对象中包含的角色和权限信息添加到SimpleAuthorizationInfo对象中。
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(user.getRoleCodes());
        Set<String> permission = user.getPermissions();
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     * 实现了基于JWT令牌的认证流程，首先解析JWT令牌中的账号信息，
     * 然后根据账号类型从数据库或MongoDB中获取对应的用户信息，
     *
     * 最后对比JWT令牌中的账号密码和获取到的用户信息中的账号密码是否一致，
     * 如果一致则返回认证成功的信息，否则返回认证失败的信息。
     */
    @Override
    // AuthenticationToken auth表示用户提交的身份信息，例如用户名和密码，这里使用了JWT令牌的方式。
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        // 获取从前端提交的JWT令牌。
        // 解密获得username，用于和数据库进行对比
        String token = (String)auth.getCredentials();
        AccountInfo accountInfo = JwtUtil.getAccountInfo(token);

        // 解析JWT令牌中的账号信息，如果解析不出来则抛出“token invalid”异常。
        if (accountInfo == null) {
            throw new AuthenticationException("token invalid");
        }

        ShiroUser userBean = null;

        // 判断账号类型，如果是“mgr”（即后台管理用户），则从数据库中获取对应的用户信息。
        // 如果是“shop”（即商家用户），则从MongoDB中获取对应的用户信息。
        if (Constants.USER_TYPE_MGR.equals(accountInfo.getUserType())) {
            // 将获取到的用户信息封装成ShiroUser对象。
            userBean = ShiroFactroy.me().shiroUser(userService.findByAccount(accountInfo.getUsername()));
        } else if (Constants.USER_TYPE_SHOP.equals(accountInfo.getUserType())) {
            userBean = ShiroFactroy.me().shiroUser(mongoRepository.findOne(Shop.class,
                Maps.newHashMap("name", accountInfo.getUsername(), "password", accountInfo.getPassword())));
        }

        // 将获取到的JWT令牌进行校验，如果验证不通过则抛出“Username or password error”异常。
        if (userBean == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        if (!JwtUtil.verify(token, accountInfo.getUsername(), userBean.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }

        // 将认证结果封装成SimpleAuthenticationInfo对象返回给Shiro框架。
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
