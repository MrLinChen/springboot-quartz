package com.example.springbootquartz;

import com.example.springbootquartz.scheduler_job_detail.HelloWoldQuartz;
import com.example.springbootquartz.utils.SchedulerUtils;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringBootQuartzApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootQuartzApplication.class, args);
    }

    // 实现CommandLineRunner 里面的run 方法。即SpringBoot启动完成之后，执行以下方法
    @Override
    public void run(String... args) throws Exception {

        // 创建一个每5秒执行的触发器
        // SchedulerUtils.createScheduler("123456","hello",5,HelloWoldQuartz.class);

        // 创建一个在当前时间 + 5 秒的指定时间内执行
/*        Long appointTime = new Date().getTime() + 1000 * 5;
        System.out.println(new Date());
        SchedulerUtils.createSchedulerAtAppointTime("123456","hello",appointTime,HelloWoldQuartz.class);*/

        Long appointTime = new Date().getTime() + 1000 * 5;
        System.out.println(new Date());
        Map<String,String> params = new HashMap<>();
        params.put("id","hhhh");
        SchedulerUtils.createSchedulerAtAppointTime(
                params,
                "123456",
                "hello",
                appointTime,HelloWoldQuartz.class);




    }
}
