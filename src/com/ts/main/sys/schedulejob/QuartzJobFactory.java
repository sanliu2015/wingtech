package com.ts.main.sys.schedulejob;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Description 计划任务执行处 无状态
 * @author plq
 *
 */
public class QuartzJobFactory implements Job {
    public final Logger log = Logger.getLogger(this.getClass());
 
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        TaskUtils.invokMethod(scheduleJob);
    }
}
