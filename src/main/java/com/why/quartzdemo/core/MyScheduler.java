package com.why.quartzdemo.core;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

public class MyScheduler {
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 创建调度器 Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        // 创建 JobDetail 实例，并与 PrintWordsJob 类绑定（ Job 执行内容）
        JobDetail jobDetail = JobBuilder.newJob(PrintWordsJob.class).withIdentity("job1", "group1").build();
        // 构建 Trigger 实例，每隔 1s 执行一次
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1","triggerGroup1")
                .startNow()     //立即生效
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(1)   // 每隔1s执行一次
                .repeatForever()).build();  // 一直执行

        // 4、执行
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("--------scheduler start!--------");
        scheduler.start();

        // 睡眠
        TimeUnit.MINUTES.sleep(1);
        scheduler.shutdown();
        System.out.println("--------scheduler shutdown!--------");
    }
}
