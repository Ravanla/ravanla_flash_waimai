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
/*
* 在应用程序启动时启动任务，通过从JobService获取任务列表，然后循环添加任务。
* 例如，可以使用它来实现定时发送邮件的功能，在应用程序启动时，将任务添加到任务列表中，每隔一段时间发送一封邮件。
* */
@Component
public class StartJob implements ApplicationRunner {

    @Autowired
    private JobService jobService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("start ravanla`s Job >>>>>>>>>>>>>>>>>>>>>>>");
        List<QuartzJob> list = jobService.getTaskList();
        for (QuartzJob quartzJob : list) {
            jobService.addJob(quartzJob);
        }
    }
}
