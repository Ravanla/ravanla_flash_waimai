package cn.ravanla.flash.service.system;

import cn.ravanla.flash.cache.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AccountService
 *
 *@Author ravanla
 * @version 2020/9/12 0012
 */
@Service
public class AccountService {
    @Autowired
    private TokenCache tokenCache;
    @Autowired
    private UserService userService;



    public void logout(String token) {
        tokenCache.remove(token);
    }

}
