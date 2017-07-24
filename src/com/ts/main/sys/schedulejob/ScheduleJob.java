package com.ts.main.sys.schedulejob;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseBean;

@BeanProperty(description="计划任务")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Sys_ScheduleJob")
public class ScheduleJob implements Serializable {

	public static final String STATUS_RUNNING = "1";
	public static final String STATUS_NOT_RUNNING = "0";
	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";
	
	@Id 
    @GeneratedValue(generator = "paymentableGenerator")   
    @GenericGenerator(name = "paymentableGenerator", strategy = "assigned")  
    @Column 
    private Integer id; //表主键id
	
	@BeanProperty(description = "创建者姓名")
	@Column(length = 50)
	private String createdBy;

	@BeanProperty(description = "创建日期")
	@Column(length = 10)
	private String createDate;

	@BeanProperty(description = "创建时间")
	@Column(length = 10)
	private String createTime;

	@BeanProperty(description = "创建人员id")
	@Column
	private Integer createrId;

	@BeanProperty(description = "操作员id")
	@Column
	private Integer userId;

	@BeanProperty(description = "修改人员姓名")
	@Column(length = 50)
	private String modifiedBy;

	@BeanProperty(description = "修改人员id")
	@Column
	private Integer modifiedById;

	@BeanProperty(description = "修改时的操作员id")
	@Column
	private Integer modifyUserId;

	@BeanProperty(description = "修改日期")
	@Column(length = 10)
	private String modifyDate;

	@BeanProperty(description = "修改时间")
	@Column(length = 10)
	private String modifyTime;

	@BeanProperty(description="任务名称")
	@Column(length = 150) 
	private String jobName;
	
	@BeanProperty(description="任务分组")
	@Column(length = 100) 
	private String jobGroup;
	
	@BeanProperty(description="任务状态")
	@Column(length = 10) 
	private String jobStatus;

	@BeanProperty(description="cron表达式")
	@Column(length = 100) 
	private String cronExpression;

	@BeanProperty(description="任务是否有状态")
	@Column(length = 10)
	private String isConcurrent;

	@BeanProperty(description="任务执行时调用哪个类的方法 包名+类名")
	@Column(length = 100)
	private String beanClass;
	
	@BeanProperty(description="spring bean")
	@Column(length = 500)
	private String springId;
	
	@BeanProperty(description="任务调用的方法名")
	@Column(length = 100)
	private String methodName;
	
	@BeanProperty(description="sql对象")
	@Column(length = 100)
	private String sqlObject;
	
	@BeanProperty(description="描述")
    @Column(length = 1000)
	private String description;
	
	@BeanProperty(description="最近一次执行结果")
	@Column(length = 50)
	private String lastSuccessStatus;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Integer getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(Integer modifiedById) {
		this.modifiedById = modifiedById;
	}

	public Integer getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(Integer modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getIsConcurrent() {
		return isConcurrent;
	}

	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	public String getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}

	public String getSpringId() {
		return springId;
	}

	public void setSpringId(String springId) {
		this.springId = springId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getSqlObject() {
		return sqlObject;
	}

	public void setSqlObject(String sqlObject) {
		this.sqlObject = sqlObject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLastSuccessStatus() {
		return lastSuccessStatus;
	}

	public void setLastSuccessStatus(String lastSuccessStatus) {
		this.lastSuccessStatus = lastSuccessStatus;
	} 
	
	
}
