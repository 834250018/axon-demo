package com.ywy.learn.web.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author ve
 * @date 2019/7/12 15:02
 */
public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // doSomething
        System.out.println("执行任务");
    }
}
