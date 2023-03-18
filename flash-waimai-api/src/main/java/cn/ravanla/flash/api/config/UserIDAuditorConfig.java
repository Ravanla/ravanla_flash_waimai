package cn.ravanla.flash.api.config;

import cn.ravanla.flash.security.JwtUtil;
import cn.ravanla.flash.utils.Constants;
import cn.ravanla.flash.utils.HttpKit;
import cn.ravanla.flash.utils.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 审计功能配置
 *
 * @version 2021/1/8 0008
 * @Author ravanla
 */
// 声明这是一个配置类，并实现 AuditorAware 接口，泛型参数为 Long 类型，表示用户 ID 的类型
@Configuration
public class UserIDAuditorConfig implements AuditorAware<Long> {
    //重写 getCurrentAuditor 方法，返回一个 Optional 对象，包含当前操作用户的 ID
    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            // 从 HttpKit 工具类中获取请求对象，并从请求头中获取 TOKEN_NAME 常量对应的值，即 token 字符串
            String token = HttpKit.getRequest().getHeader(Constants.TOKEN_NAME);
            // 如果 token 不为空，则调用 JwtUtil 工具类中的 getUserId 方法，从 token 中解析出用户 ID，并返回一个 Optional 对象包含该 ID
            if (StringUtils.isNotEmpty(token)) {
                return Optional.of(JwtUtil.getUserId(token));
            }
        } catch (Exception e) {
            // 如果发生异常，则返回系统用户 ID 常量对应的值，并返回一个 Optional 对象包含该值
            return Optional.of(Constants.SYSTEM_USER_ID);
        }
        // 如果 token 为空，则返回系统用户 ID 常量对应的值，并返回一个 Optional 对象包含该值
        return Optional.of(Constants.SYSTEM_USER_ID);
    }
}