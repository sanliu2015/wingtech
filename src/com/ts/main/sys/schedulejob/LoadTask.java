package com.ts.main.sys.schedulejob;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.ts.core.common.service.IBaseServiceManger;

@Service("loadTaskService")
public class LoadTask {

	private static final Log log = LogFactory.getLog(LoadTask.class);
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	@Autowired
	@Qualifier("baseService")
	private IBaseServiceManger baseService;
	
	@PostConstruct
	public void initTask() throws Exception {
//		System.out.println("计划任务开始初始化...");
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		List<ScheduleJob> jobList = baseService.getDb().queryHqlForList("From ScheduleJob where jobStatus='" + ScheduleJob.STATUS_RUNNING + "'");
		for (ScheduleJob job : jobList) {
			try {
				 if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			            return;
			        }
			        
			        log.debug(scheduler + ".............................................add");
			        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
			        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			 
			        // 不存在，创建一个
			        if (null == trigger) {
			            Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;
			            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
			            jobDetail.getJobDataMap().put("scheduleJob", job);
			            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
			            scheduler.scheduleJob(jobDetail, trigger);
			        } else {
			            // Trigger已存在，那么更新相应的定时设置
			            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			            // 按新的cronExpression表达式重新构建trigger
			            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			            // 按新的trigger重新设置job执行
			            scheduler.rescheduleJob(triggerKey, trigger);
			        }
			} catch (SchedulerException e) {
				e.printStackTrace();
				log.error("执行计划任务出错！", e);
			}
		}

	}

}