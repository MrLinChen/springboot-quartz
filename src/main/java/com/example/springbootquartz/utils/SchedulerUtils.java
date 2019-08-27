package com.example.springbootquartz.utils;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component // springBoot 容器启动后,自动将该类实例化
public class SchedulerUtils {

    private SchedulerFactory schedulerFactory;

    private static Scheduler scheduler;

    public SchedulerUtils() throws SchedulerException {
        schedulerFactory = new StdSchedulerFactory(); // 实例化定时器工厂
        scheduler = schedulerFactory.getScheduler();
    }


    /**
     *  创建一个每几秒内执行的定时器
     * @param schedulerId 定时器id 保持唯一性即可
     * @param schedulerGroup 定时器组名称
     *  主要保证 一个组里面只要一个唯一的id即可。但一个组里面可以有多个不同的id
     * @param loopTime 几秒内执行一次 。 如 2 即2秒内执行一次
     * @param jobTask 定时器工作任务
     */
    public static void createScheduler(
            String schedulerId, String schedulerGroup,
            Integer loopTime, Class jobTask) throws SchedulerException {

        JobDetail jobDetail = JobBuilder.newJob(jobTask)
                .withIdentity(schedulerId,schedulerGroup) // 给工作内容加入唯一的标识符。用于定时器删除
                .build();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(schedulerId,schedulerGroup) // 给触发器加入唯一的标识符。用于触发器修改触发时间
                .startNow() // 重现在开始触发
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(3,loopTime)) // 在几秒内执行三次触发器
                .build();

        scheduler.start(); // 开启定时任务
        scheduler.scheduleJob(jobDetail,trigger); // 将工作内容和触发时间加入到定时器中
    }

    /**
     * 在创建一个在指定时间内执行的定时器
     * @param schedulerId 定时器id 保持唯一性即可
     * @param schedulerGroup 定时器组名称
     * @param appointTime 需要在某个指定的时间执行改定时器
     * @param jobTask
     */
    public static void createSchedulerAtAppointTime(String schedulerId, String schedulerGroup,
                                                    Long appointTime, Class jobTask) throws SchedulerException {

        JobDetail jobDetail = JobBuilder.newJob(jobTask)
                .withIdentity(schedulerId,schedulerGroup)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(schedulerId,schedulerGroup)
                .startAt(new Date(appointTime))
                .build();

        scheduler.start();
        scheduler.scheduleJob(jobDetail,trigger);
    }

    /**
     * 在创建一个在指定时间内执行的定时器
     * @param params 给定时器传入参数，用于jobTask 里面获取
     * @param schedulerId 定时器id 保持唯一性即可
     * @param schedulerGroup 定时器组名称
     * @param appointTime 需要在某个指定的时间执行改定时器
     * @param jobTask
     */
    public static void createSchedulerAtAppointTime(
            Map params,
            String schedulerId, String schedulerGroup,
            Long appointTime, Class jobTask) throws SchedulerException {

        JobDataMap jobDataMap = new JobDataMap(params);

        JobDetail jobDetail = JobBuilder.newJob(jobTask)
                .withIdentity(schedulerId,schedulerGroup)
                .setJobData(jobDataMap) // 给定时器传入参数
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(schedulerId,schedulerGroup)
                .startAt(new Date(appointTime))
                .build();

        scheduler.start();
        scheduler.scheduleJob(jobDetail,trigger);
    }
}
