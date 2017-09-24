package com.ts.main.dorm.repairapply;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.CommonBean;

@BeanProperty(description = "维修申请")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Dorm_RepairApply")
public class RepairApply extends CommonBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(length = 50)
	private String number;
	
	@BeanProperty(description = "楼栋id")
	@Column
	private Integer buildingId;
	
	@BeanProperty(description = "报修人id")
	@Column
	private Integer employeeId;
	
	@BeanProperty(description = "保修类别")
	@Column(length = 5)
	private String repairType;
	
	@BeanProperty(description = "保修日期")
	@Column(length = 10)
	private String repairDate;
	
	@Column(length = 500)
	private String repairContent;
	
	@BeanProperty(description = "审核状态")
	@Column(length = 5)
	private String auditStatus;
	
	@Column(length = 5)
	private String status;
	
	@BeanProperty(description = "维修人id")
	@Column
	private Integer repairerId;
	
	@BeanProperty(description = "是否发送短信给维修人员")
	@Column(length = 5)
	private String sendToRepFlag;
	
	@BeanProperty(description = "是否发送短信给报修人员")
	@Column(length = 5)
	private String sendToEmpFlag;
	
	@BeanProperty(description = "完工日期")
	@Column(length = 10)
	private String endWorkDate;
	
	@Column
	private BigDecimal repairFee;
	
	@BeanProperty(description = "奖励费用")
	@Column
	private BigDecimal rewardFee;
	
	@BeanProperty(description = "评分结果")
	@Column(length = 5)
	private String scoreResult;
	
	@BeanProperty(description = "评分日期")
	@Column(length = 10)
	private String scoreDate;
	
	@Column
	private Integer notifyFlag;
	
	@Column(length=15)
	private String empPhone;
	
	@Column(length=10)
	private String autoScoreDate;
	
	@Column
	private Integer printTimes;
	
	@Transient
	private String employeeName;
	@Transient
	private String repairerName;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getRepairType() {
		return repairType;
	}

	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}

	public String getRepairDate() {
		return repairDate;
	}

	public void setRepairDate(String repairDate) {
		this.repairDate = repairDate;
	}

	public String getRepairContent() {
		return repairContent;
	}

	public void setRepairContent(String repairContent) {
		this.repairContent = repairContent;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRepairerId() {
		return repairerId;
	}

	public void setRepairerId(Integer repairerId) {
		this.repairerId = repairerId;
	}

	public String getSendToRepFlag() {
		return sendToRepFlag;
	}

	public void setSendToRepFlag(String sendToRepFlag) {
		this.sendToRepFlag = sendToRepFlag;
	}

	public String getSendToEmpFlag() {
		return sendToEmpFlag;
	}

	public void setSendToEmpFlag(String sendToEmpFlag) {
		this.sendToEmpFlag = sendToEmpFlag;
	}

	public String getEndWorkDate() {
		return endWorkDate;
	}

	public void setEndWorkDate(String endWorkDate) {
		this.endWorkDate = endWorkDate;
	}

	public BigDecimal getRepairFee() {
		return repairFee;
	}

	public void setRepairFee(BigDecimal repairFee) {
		this.repairFee = repairFee;
	}

	public String getScoreResult() {
		return scoreResult;
	}

	public void setScoreResult(String scoreResult) {
		this.scoreResult = scoreResult;
	}

	public String getScoreDate() {
		return scoreDate;
	}

	public void setScoreDate(String scoreDate) {
		this.scoreDate = scoreDate;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getRepairerName() {
		return repairerName;
	}

	public void setRepairerName(String repairerName) {
		this.repairerName = repairerName;
	}

	public Integer getPrintTimes() {
		return printTimes;
	}

	public void setPrintTimes(Integer printTimes) {
		this.printTimes = printTimes;
	}

	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	public String getAutoScoreDate() {
		return autoScoreDate;
	}

	public void setAutoScoreDate(String autoScoreDate) {
		this.autoScoreDate = autoScoreDate;
	}

	public Integer getNotifyFlag() {
		return notifyFlag;
	}

	public void setNotifyFlag(Integer notifyFlag) {
		this.notifyFlag = notifyFlag;
	}

	public BigDecimal getRewardFee() {
		return rewardFee;
	}

	public void setRewardFee(BigDecimal rewardFee) {
		this.rewardFee = rewardFee;
	}


}
