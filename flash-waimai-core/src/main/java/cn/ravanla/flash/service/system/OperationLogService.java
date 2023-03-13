package cn.ravanla.flash.service.system;

import cn.ravanla.flash.bean.entity.system.OperationLog;
import cn.ravanla.flash.dao.system.OperationLogRepository;
import cn.ravanla.flash.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * Created  on 2020/3/26 0026.
 *
 *@Author ravanla
 */
@Service
public class OperationLogService extends BaseService<OperationLog,Long, OperationLogRepository> {

}
