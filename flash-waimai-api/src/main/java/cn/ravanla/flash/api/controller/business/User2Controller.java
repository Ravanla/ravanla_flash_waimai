package cn.ravanla.flash.api.controller.business;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.entity.front.FrontUser;
import cn.ravanla.flash.bean.entity.front.FrontUserInfo;
import cn.ravanla.flash.bean.entity.front.Ids;
import cn.ravanla.flash.bean.vo.SpringContextHolder;
import cn.ravanla.flash.bean.vo.business.CityInfo;
import cn.ravanla.flash.bean.vo.business.LoginVo;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.cache.TokenCache;
import cn.ravanla.flash.dao.MongoRepository;
import cn.ravanla.flash.service.front.IdsService;
import cn.ravanla.flash.service.front.PositionService;
import cn.ravanla.flash.utils.*;
import cn.ravanla.flash.utils.*;
import org.nutz.lang.Strings;
import org.nutz.mapl.Mapl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zt on 2021/12/12 0012.
 */
@RestController
@RequestMapping("/v1/users")
public class User2Controller extends BaseController {
    private Logger logger = LoggerFactory.getLogger(User2Controller.class);
    @Autowired
    private MongoRepository mongoRepository;
    @Autowired
    private IdsService idsService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private TokenCache tokenCache;

    @RequestMapping(value = "/v1/user", method = RequestMethod.GET)
    // 定义一个方法，返回一个用户对象
    public Object getUser() {
        // 从当前会话中获取用户对象，并赋值给ret变量
        Object ret = getSession("currentUser");
        // 如果ret变量为空，说明没有获取到用户对象
        if (ret == null) {
            // 从请求参数中获取token字符串，并赋值给token变量
            String token = getRequest().getParameter("token");
            // 打印日志信息，显示token的值
            logger.info("token:{}", token);
            // 如果token变量不为空，说明有传入有效的token参数
            if (StringUtils.isNotEmpty(token)) {
                // 从TokenCache类中获取userId值，并赋值给userId变量
                Long userId = SpringContextHolder.getBean(TokenCache.class).get(token, Long.class);
                // 打印日志信息，显示userId的值
                logger.info("userId:{}", userId);
                // 从mongoRepository中查询FrontUser类的对象，并根据"user_id"字段匹配userId值，将查询结果赋值给frontUser变量
                FrontUser frontUser = mongoRepository.findOne(FrontUser.class, "user_id", userId);
                // 从mongoRepository中查询FrontUserInfo类的对象，并根据"user_id"字段匹配userId值，将查询结果赋值给userInfo变量
                FrontUserInfo userInfo = mongoRepository.findOne(FrontUserInfo.class, Maps.newHashMap("user_id", userId));
                // 将frontUser和userInfo两个对象合并成一个新的对象，并赋值给result变量
                Object result = Mapl.merge(frontUser, userInfo);
                // 返回result变量作为方法的返回值
                return result;
            }
        }
        // 如果没有返回任何有效的用户对象，打印错误日志信息，并返回null作为方法的返回值
        logger.error("获取用户信息失败");
        return null;
    }

    @RequestMapping(method = RequestMethod.GET)
    // 定义一个方法，接收一个user_id参数，返回一个用户对象
    public Object getUser(@RequestParam("user_id") Long userId) {
        // 从mongoRepository中查询FrontUser类的对象，并根据"user_id"字段匹配userId值，将查询结果包装成一个成功的响应对象，并返回
        return Rets.success(mongoRepository.findOne(FrontUser.class, "user_id", userId));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    // 定义一个方法，接收两个参数，分别是offset和limit，用来表示查询的起始位置和数量
    public Object list(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        // 从mongoRepository中查询"userinfos"集合中的所有数据，并赋值给list变量
        List list = mongoRepository.findAll("userinfos");
        // 返回list变量作为方法的返回值
        return list;
    }


    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Object count() {
        return Rets.success("count", 2);
    }

    @RequestMapping(value = "/v2/login", method = RequestMethod.POST)
    // 定义一个方法，接收一个LoginVo对象作为参数，用来封装用户的登录信息
    public Object login(@RequestBody LoginVo loginVo) {
        // 从tokenCache中获取验证码，并赋值给captchCode变量
        String captchCode = tokenCache.get(loginVo.getCaptchCodeId(), String.class);

        // 如果用户输入的验证码和缓存中的验证码不一致，返回一个失败的响应对象，并提示验证码不正确
        if (!Strings.equals(loginVo.getCaptchaCode(), captchCode)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_CAPTCHA", "message", "验证码不正确"));
        }

        // 从mongoRepository中查询"users"集合中的数据，并根据"username"字段匹配loginVo.getUsername()值，将查询结果赋值给user变量
        Map user = mongoRepository.findOne("users", "username", loginVo.getUsername());

        // 对用户输入的密码进行加密处理，并赋值给newPassword变量
        String newPassword = MD5.getMD5String(MD5.getMD5String(loginVo.getPassword()).substring(2, 7) + MD5.getMD5String(loginVo.getPassword()));

        // 如果user变量为空，说明没有查询到对应的用户数据
        if (user == null) {
            FrontUser frontUser = new FrontUser();

            // 从idsService中获取一个唯一的用户ID，并设置给frontUser对象的user_id属性
            // 将loginVo对象的username属性值设置给frontUser对象的username属性
            // 将newPassword变量值设置给frontUser对象的password属性
            frontUser.setUser_id(idsService.getId(Ids.USER_ID));
            frontUser.setUsername(loginVo.getUsername());
            frontUser.setPassword(newPassword);

            // 将frontUser对象保存到mongoRepository中
            mongoRepository.save(frontUser);

            FrontUserInfo userInfo = new FrontUserInfo();

            // 将frontUser对象的user_id属性值设置给userInfo对象的id和user_id属性
            // 获取当前时间，并格式化成字符串，设置给userInfo对象的registe_time属性
            userInfo.setId(frontUser.getUser_id());
            userInfo.setUser_id(frontUser.getUser_id());
            userInfo.setRegiste_time(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));

            // 获取当前请求的IP地址，并赋值给ip变量
            // 从positionService中获取IP地址对应的城市信息，并赋值给cityInfo变量
            String ip = getIp();
            CityInfo cityInfo = positionService.getPostion(ip);

            // 将cityInfo对象的city属性值设置给userInfo对象的city属性
            // 将frontUser对象的username属性值设置给userInfo对象的username属性
            // 将userInfo对象保存到mongoRepository中
            userInfo.setCity(cityInfo.getCity());
            userInfo.setUsername(frontUser.getUsername());
            mongoRepository.save(userInfo);

            // 将frontUser和userInfo两个对象合并成一个新的对象，并赋值给result变量
            // 将result变量作为当前会话中存储用户信息 的键为"currentUser" 的 值
            Object result = Mapl.merge(frontUser, userInfo);
            setSession("currentUser", result);
            // 返回frontUser变量作为方法 的 返回 值
            return frontUser;
        }
        else {
            // 如果user 变量 不为空 ，说明查询到了对应的 用户 数据
            // 比对密码是否正确
            if (newPassword.equals(user.get("password"))) {
                // 从mongoRepository 中 查询"userinfos" 集合 中 的 数据 ，并 根据"user_id" 字段 匹配 user 变量 中 的 "user_id" 字段 值 ，将 查询 结果 赋值 给 userInfo 变量
                Map userInfo = mongoRepository.findOne("userinfos", "user_id", Long.valueOf(user.get("user_id").toString()));

                // 将user和userInfo两个对象合并成一个新的对象，并赋值给result变量
                // 将result变量作为当前会话中存储用户信息的键为"currentUser"的值
                Object result = Mapl.merge(user, userInfo);
                setSession("currentUser", result);
                return result;
            } else {
                // 如果newPassword变量值和user变量中的"password"字段值不相等，说明密码错误，返回一个失败的响应对象，并提示密码错误
                return Rets.failure(Maps.newHashMap("type", "ERROR_PASSWORD", "message", "密码错误"));
            }

        }
    }

    @RequestMapping(value = "/v2/signout", method = RequestMethod.GET)
    public Object signOut() {
        getRequest().getSession().removeAttribute("currentUser");
        return Rets.success();
    }

    @RequestMapping(value = "/v2/changepassword", method = RequestMethod.POST)
    public Object changePassword(@RequestParam("username") String userName,
                                 @RequestParam("oldpassWord") String oldPassword,
                                 @RequestParam("newpassword") String newPassword,
                                 @RequestParam("confirmpassword") String confirmPassword,
                                 @RequestParam("captcha_code") String captchaCode) {

        String captch = (String) getSession(CaptchaCode.CAPTCH_KEY);
        if (!Strings.equals(captchaCode, captch)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_CAPTCHA", "message", "验证码不正确"));
        }
        Map user = mongoRepository.findOne("users", "username", userName);
        if (user == null) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "用户不存在"));
        }
        if (!Strings.equals(oldPassword, Strings.sNull(user.get("password")))) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "原密码错误"));
        }
        if (Strings.equals(newPassword, confirmPassword)) {
            return Rets.failure(Maps.newHashMap("type", "ERROR_QUERY", "message", "新密码不一致"));
        }

        user.put("password", newPassword);
        mongoRepository.update(Long.valueOf(user.get("id").toString()), "users", user);

        return Rets.success();
    }


}
