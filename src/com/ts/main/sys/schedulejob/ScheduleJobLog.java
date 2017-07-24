package com.ts.main.sys.schedulejob;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.ts.core.annotation.BeanProperty;

@BeanProperty(description="计划任务执行日志")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Sys_ScheduleJobLog")
public class ScheduleJobLog implements Serializable {

	private static final long serialVersionUID = -7229139711802879770L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)		
    private Integer id; //表主键id
	
	@BeanProperty(description = "发生时间")
	@Column(length = 20)
	private String occurDate;
	
	@BeanProperty(description = "信息标示：成功还是失败")
	@Column(length = 20)
	private String infoFlag;
	
	@BeanProperty(description = "结果信息")
	@Column(columnDefinition = "text")
	private String description;
	
	@BeanProperty(description = "对应计划任务id")
	@Column
	private Integer jobId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOccurDate() {
		return occurDate;
	}

	public void setOccurDate(String occurDate) {
		this.occurDate = occurDate;
	}

	public String getInfoFlag() {
		return infoFlag;
	}

	public void setInfoFlag(String infoFlag) {
		this.infoFlag = infoFlag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	
	
	
	
}
