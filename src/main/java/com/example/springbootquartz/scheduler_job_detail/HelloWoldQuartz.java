package com.example.springbootquartz.scheduler_job_detail;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时任务的工作内容
 */
public class HelloWoldQuartz implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap userEntryId = jobExecutionContext.getJobDetail().getJobDataMap();
        System.out.println(userEntryId);
        System.out.println(jobExecutionContext);
        System.out.println("HelloWoldQuartz");

    }
}
