package fengge.controller.task;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * 使用Quartz框架实现定时任务
 * Feng, Ge 2021/6/18 0018 15:29
 */
public class QuartzTask {

    public static void main(String[] args) throws SchedulerException {
        //1.创建Scheduler的工厂
        SchedulerFactory sf = new StdSchedulerFactory();
        //2.从工厂中获取调度器实例
        Scheduler scheduler = sf.getScheduler();
        //3.创建JobDetail，
        JobDetail jb = JobBuilder.newJob(SelfJob.class)
                //job的描述
                .withDescription("this is a ram job")
                //job 的name和group
                .withIdentity("ramJob", "ramGroup")
                .build();
        //任务运行的时间，SimpleSchedle类型触发器有效，3秒后启动任务
        long time = System.currentTimeMillis() + 3 * 1000L;
        Date statTime = new Date(time);
        //4.创建Trigger
        //使用SimpleScheduleBuilder或者CronScheduleBuilder
        Trigger t = TriggerBuilder.newTrigger()
                .withDescription("")
                .withIdentity("ramTrigger", "ramTriggerGroup")
                //.withSchedule(SimpleScheduleBuilder.simpleSchedule())
                //默认当前时间启动
                .startAt(statTime)
                //两秒执行一次，Quartz表达式，支持各种牛逼表达式
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build();
        //5.注册任务和定时器
        scheduler.scheduleJob(jb, t);
        //6.启动 调度器
        scheduler.start();
    }
}
