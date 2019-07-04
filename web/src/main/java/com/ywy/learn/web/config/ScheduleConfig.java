package com.ywy.learn.web.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ve
 * @date 2019/4/30 9:49
 */
@Configuration
public class ScheduleConfig {

    public static Scheduler scheduler;

    @Autowired
    public void setScheduler(Scheduler scheduler) throws SchedulerException {
        ScheduleConfig.scheduler = scheduler;
        ScheduleConfig.scheduler.start();
    }

    @Bean
    public Scheduler initScheduler() throws SchedulerException {
        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        stdSchedulerFactory.initialize();
        return stdSchedulerFactory.getScheduler();
    }
}
