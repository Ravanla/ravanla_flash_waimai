package cn.ravanla.flash.api.runner;

import cn.ravanla.flash.bean.vo.QuartzJob;
import cn.ravanla.flash.service.task.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化定时任务
 *
 *@Author ravanla
 * @Date 2021-08-13
 */
@Component
public class StartJob implements ApplicationRunner {

    @Autowired
    private JobService jobService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("start Job >>>>>>>>>>>>>>>>>>>>>>>");
        List<QuartzJob> list = jobService.getTaskList();
        for (QuartzJob quartzJob : list) {
            jobService.addJob(quartzJob);
        }
    }
}
