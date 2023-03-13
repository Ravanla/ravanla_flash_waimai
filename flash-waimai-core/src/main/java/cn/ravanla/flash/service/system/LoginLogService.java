package cn.ravanla.flash.service.system;


import cn.ravanla.flash.bean.entity.system.LoginLog;
import cn.ravanla.flash.dao.system.LoginLogRepository;
import cn.ravanla.flash.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * Created  on 2020/3/26 0026.
 *
 *@Author ravanla
 */
@Service
public class LoginLogService extends BaseService<LoginLog,Long, LoginLogRepository> {

}
