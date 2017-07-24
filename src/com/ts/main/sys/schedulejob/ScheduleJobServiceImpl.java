package com.ts.main.sys.schedulejob;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl implements IAppService {
	
	public final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	public void add(ScheduleJobForm form,RequestContext requestContext,IBaseServiceManger service) {
		
	}
	
	public OperatePromptBean save(ScheduleJobForm form,RequestContext requestContext,IBaseServiceManger service)  {
		ScheduleJob bean = form.getBean();
		OperatePromptBean prompt = new OperatePromptBean();
		try {
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(bean.getCronExpression());
			bean.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
			service.getDb().saveObjectAndId(bean, requestContext);
			prompt.setId(bean.getId().toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return prompt;
	}
	
	public void edit(ScheduleJobForm form,RequestContext requestContext , IBaseServiceManger service){
		ScheduleJob bean=service.getDb().getObject(ScheduleJob.class, form.getId(),requestContext);
		form.setBean(bean); 
	}
	
	public OperatePromptBean update(ScheduleJobForm form,RequestContext requestContext,IBaseServiceManger service)  {
		ScheduleJob bean = service.getDb().getObject(ScheduleJob.class, form.getBean().getId(), requestContext);
		BeanUtils.copyNoNullProperties(form.getBean(), bean);
		OperatePromptBean prompt = new OperatePromptBean();
		try {
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(bean.getCronExpression());
			bean.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
			service.getDb().mergeObject(bean, requestContext);
			prompt.setId(bean.getId().toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		 
		prompt.setId(bean.getId().toString()); 
		return prompt;
	}
	
	public OperatePromptBean delete(ScheduleJobForm form,RequestContext requestContext , IBaseServiceManger service ){ 
		ScheduleJob bean=service.getDb().getObject(ScheduleJob.class, form.getId(),requestContext); 
		service.getDbService().deleteObject(bean,requestContext);
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setId(bean.getId().toString());  
		return prompt;
	}
	
	// 开启
	@Transactional
	public OperatePromptBean open(ScheduleJobForm form,RequestContext requestContext , IBaseServiceManger service ) throws SchedulerException{ 
		ScheduleJob bean = service.getDb().getObject(ScheduleJob.class, form.getId(),requestContext); 
		bean.setJobStatus(ScheduleJob.STATUS_RUNNING);
		service.getDb().mergeObject(bean, requestContext);
		try {
			addJob(bean);
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw e;
		}
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setId(bean.getId().toString());  
		return prompt;
	}
	
	@Transactional
	public OperatePromptBean stop(ScheduleJobForm form,RequestContext requestContext , IBaseServiceManger service ) throws SchedulerException{ 
		ScheduleJob bean = service.getDb().getObject(ScheduleJob.class, form.getId(),requestContext); 
		bean.setJobStatus(ScheduleJob.STATUS_NOT_RUNNING);
		service.getDb().mergeObject(bean, requestContext);
		try {
			deleteJob(bean); 
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw e;
		}
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setId(bean.getId().toString());  
		return prompt;
	}
	
	public OperatePromptBean doRightNow(ScheduleJobForm form,RequestContext requestContext , IBaseServiceManger service ) throws SchedulerException{ 
		ScheduleJob bean = service.getDb().getObject(ScheduleJob.class, form.getId(),requestContext); 
		try {
			runAJobNow(bean);
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw e;
		}
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setId(bean.getId().toString());  
		return prompt;
	}
	
	public void lookLastLog(ScheduleJobForm form,RequestContext requestContext , IBaseServiceManger service ){ 
		StringBuilder sql = new StringBuilder();
		Integer maxId = service.getDb().queryForInt("select max(id) from Sys_ScheduleJobLog where jobId=" + form.getId());
		ScheduleJobLog logBean =  service.getDb().getObject(ScheduleJobLog.class, maxId, requestContext);
		requestContext.getRequest().setAttribute("logBean", logBean);
	} 
	
	
	/**
	 * 添加任务到定时器中
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void addJob(ScheduleJob job) throws SchedulerException {
		if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			return;
		}

		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		log.debug(scheduler + ".......................................................................................add");
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
	}
	
	
	/**
	 * 从定时器中移除任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);
	}
	
	/**
	 * 立即执行job
	 * 
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	public void runAJobNow(ScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}
	
	
	public static void main(String[] args) {
		try {
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("xxx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
