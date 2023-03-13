package cn.ravanla.flash.dao.system;


import cn.ravanla.flash.bean.entity.system.OperationLog;
import cn.ravanla.flash.dao.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * Created  on 2020/3/21 0021.
 *
 *@Author ravanla
 */
public interface OperationLogRepository extends BaseRepository<OperationLog,Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "delete from t_sys_operation_log")
    int clear();
}
