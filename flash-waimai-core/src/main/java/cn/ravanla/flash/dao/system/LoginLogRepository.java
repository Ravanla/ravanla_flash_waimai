package cn.ravanla.flash.dao.system;

import cn.ravanla.flash.bean.entity.system.LoginLog;
import cn.ravanla.flash.dao.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created  on 2020/3/21 0021.
 *
 *@Author ravanla
 */
public interface LoginLogRepository extends BaseRepository<LoginLog,Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "delete from t_sys_login_log")
    int clear();
}
