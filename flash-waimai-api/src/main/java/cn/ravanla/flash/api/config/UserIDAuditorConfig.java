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
 *@Author ravanla
 * @version 2021/1/8 0008
 */
@Configuration
public class UserIDAuditorConfig implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            String token = HttpKit.getRequest().getHeader(Constants.TOKEN_NAME);
            if (StringUtils.isNotEmpty(token)) {
                return Optional.of(JwtUtil.getUserId(token));
            }
        }catch (Exception e){
            //返回系统用户id
            return Optional.of(Constants.SYSTEM_USER_ID);
        }
        //返回系统用户id
        return Optional.of(Constants.SYSTEM_USER_ID);
    }
}
