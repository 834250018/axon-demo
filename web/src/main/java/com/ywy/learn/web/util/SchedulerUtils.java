/*
package com.ywy.learn.web.util;

import com.ywy.learn.web.config.ScheduleConfig;
import org.quartz.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * 定时任务工具类
 *
 * @author ve
 * @date 2019/7/12 15:03
 * <p>
 * 设置定时器
 * @param jobDetailName  任务名
 * @param jobDetailGroup 任务组
 * @param triggerTime    触发器触发时间
 * <p>
 * 设置定时器
 * @param dataMap        数据map
 * @param jobDetailName  任务配置name
 * @param jobDetailGroup 任务配置group
 * @param triggerName    触发器name
 * @param triggerGroup   触发器group
 * @param triggerTime    触发器触发时间
 * <p>
 * 调整任务执行时间
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @param newTime  新任务执行时间
 * @throws SchedulerException
 * <p>
 * 删除任务
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @throws SchedulerException
 * <p>
 * 设置定时器
 * @param jobDetailName  任务名
 * @param jobDetailGroup 任务组
 * @param triggerTime    触发器触发时间
 * <p>
 * 设置定时器
 * @param dataMap        数据map
 * @param jobDetailName  任务配置name
 * @param jobDetailGroup 任务配置group
 * @param triggerName    触发器name
 * @param triggerGroup   触发器group
 * @param triggerTime    触发器触发时间
 * <p>
 * 调整任务执行时间
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @param newTime  新任务执行时间
 * @throws SchedulerException
 * <p>
 * 删除任务
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @throws SchedulerException
 * <p>
 * 设置定时器
 * @param jobDetailName  任务名
 * @param jobDetailGroup 任务组
 * @param triggerTime    触发器触发时间
 * <p>
 * 设置定时器
 * @param dataMap        数据map
 * @param jobDetailName  任务配置name
 * @param jobDetailGroup 任务配置group
 * @param triggerName    触发器name
 * @param triggerGroup   触发器group
 * @param triggerTime    触发器触发时间
 * <p>
 * 调整任务执行时间
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @param newTime  新任务执行时间
 * @throws SchedulerException
 * <p>
 * 删除任务
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @throws SchedulerException
 * <p>
 * 设置定时器
 * @param jobDetailName  任务名
 * @param jobDetailGroup 任务组
 * @param triggerTime    触发器触发时间
 * <p>
 * 设置定时器
 * @param dataMap        数据map
 * @param jobDetailName  任务配置name
 * @param jobDetailGroup 任务配置group
 * @param triggerName    触发器name
 * @param triggerGroup   触发器group
 * @param triggerTime    触发器触发时间
 * <p>
 * 调整任务执行时间
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @param newTime  新任务执行时间
 * @throws SchedulerException
 * <p>
 * 删除任务
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @throws SchedulerException
 * <p>
 * 设置定时器
 * @param jobDetailName  任务名
 * @param jobDetailGroup 任务组
 * @param triggerTime    触发器触发时间
 * <p>
 * 设置定时器
 * @param dataMap        数据map
 * @param jobDetailName  任务配置name
 * @param jobDetailGroup 任务配置group
 * @param triggerName    触发器name
 * @param triggerGroup   触发器group
 * @param triggerTime    触发器触发时间
 * <p>
 * 调整任务执行时间
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @param newTime  新任务执行时间
 * @throws SchedulerException
 * <p>
 * 删除任务
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @throws SchedulerException
 * <p>
 * 设置定时器
 * @param jobDetailName  任务名
 * @param jobDetailGroup 任务组
 * @param triggerTime    触发器触发时间
 * <p>
 * 设置定时器
 * @param dataMap        数据map
 * @param jobDetailName  任务配置name
 * @param jobDetailGroup 任务配置group
 * @param triggerName    触发器name
 * @param triggerGroup   触发器group
 * @param triggerTime    触发器触发时间
 * <p>
 * 调整任务执行时间
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @param newTime  新任务执行时间
 * @throws SchedulerException
 * <p>
 * 删除任务
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @throws SchedulerException
 * <p>
 * 设置定时器
 * @param jobDetailName  任务名
 * @param jobDetailGroup 任务组
 * @param triggerTime    触发器触发时间
 * <p>
 * 设置定时器
 * @param dataMap        数据map
 * @param jobDetailName  任务配置name
 * @param jobDetailGroup 任务配置group
 * @param triggerName    触发器name
 * @param triggerGroup   触发器group
 * @param triggerTime    触发器触发时间
 * <p>
 * 调整任务执行时间
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @param newTime  新任务执行时间
 * @throws SchedulerException
 * <p>
 * 删除任务
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @throws SchedulerException
 *//*

public class SchedulerUtils {

    */
/**
 * 设置定时器
 *
 * @param jobDetailName  任务名
 * @param jobDetailGroup 任务组
 * @param triggerTime    触发器触发时间
 *//*

    public static void setScheduler(String jobDetailName, String jobDetailGroup, Date triggerTime, Class clazz) throws SchedulerException {
        setScheduler(new HashMap(), jobDetailName, jobDetailGroup, jobDetailName, jobDetailGroup, triggerTime, clazz);
    }

    */
/**
 * 设置定时器
 *
 * @param dataMap        数据map
 * @param jobDetailName  任务配置name
 * @param jobDetailGroup 任务配置group
 * @param triggerName    触发器name
 * @param triggerGroup   触发器group
 * @param triggerTime    触发器触发时间
 *//*

    public static void setScheduler(Map dataMap, String jobDetailName, String jobDetailGroup, String triggerName, String triggerGroup, Date triggerTime, Class clazz) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap(dataMap);
        JobDetail jobDetail = JobBuilder.newJob(clazz)
                .withIdentity(jobDetailName, jobDetailGroup)
                .setJobData(jobDataMap)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroup)
                .startAt(triggerTime)
                .build();
        ScheduleConfig.scheduler.scheduleJob(jobDetail, trigger);
    }

    */
/**
 * 调整任务执行时间
 *
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @param newTime  新任务执行时间
 * @throws SchedulerException
 *//*

    public static void update(String jobName, String jobGroup, Date newTime) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
        ScheduleConfig.scheduler.rescheduleJob(triggerKey, TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .startAt(newTime)
                .build());
    }

    */
/**
 * 删除任务
 *
 * @param jobName  任务名
 * @param jobGroup 任务组
 * @throws SchedulerException
 *//*

    public static void delete(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        ScheduleConfig.scheduler.deleteJob(jobKey);
    }

}
*/
