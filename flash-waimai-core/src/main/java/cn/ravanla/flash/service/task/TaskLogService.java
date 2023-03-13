package cn.ravanla.flash.service.task;


import cn.ravanla.flash.bean.entity.system.TaskLog;
import cn.ravanla.flash.dao.system.TaskLogRepository;
import cn.ravanla.flash.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * 定时任务日志服务类
 * @author  ravanla
 * @date 2021-08-13
 */
@Service
public class TaskLogService extends BaseService<TaskLog,Long, TaskLogRepository> {
}
