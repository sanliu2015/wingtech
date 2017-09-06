package com.ts.main.dorm.damage;

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

@BeanProperty(description = "物品损坏扣款")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Dorm_Damage")
public class Damage extends CommonBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(nullable=false)
	private Integer employeeId;
	
	@Column(length=10,nullable=false)
	private String occurDate;
	
//	@BeanProperty(description = "科室")
//	@Column
//	private Integer depId;
//	
//	@BeanProperty(description = "部门")
//	private Integer parentDepId;
//	
	@Column(precision = 9, scale = 2, nullable=false)
	private BigDecimal amount = BigDecimal.ZERO;
	
	@Column(length=500)
	private String reason;
	
	@BeanProperty(description = "锁定标识，为1不能修改")
	@Column
	private Integer lockFlag;
	
	@Transient
	private String empName;
	@Transient
	private String empNumber;
	@Transient
	private String depName;
	@Transient
	private String parentDepName;
	
	@BeanProperty(description = "维修申请绑定")
	@Column
	private Integer repairApplyId;
	
	@BeanProperty(description = "退款绑定")
	private Integer checkInId;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getOccurDate() {
		return occurDate;
	}
	public void setOccurDate(String occurDate) {
		this.occurDate = occurDate;
	}
//	public Integer getDepId() {
//		return depId;
//	}
//	public void setDepId(Integer depId) {
//		this.depId = depId;
//	}
//	public Integer getParentDepId() {
//		return parentDepId;
//	}
//	public void setParentDepId(Integer parentDepId) {
//		this.parentDepId = parentDepId;
//	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getLockFlag() {
		return lockFlag;
	}
	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpNumber() {
		return empNumber;
	}
	public void setEmpNumber(String empNumber) {
		this.empNumber = empNumber;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getParentDepName() {
		return parentDepName;
	}
	public void setParentDepName(String parentDepName) {
		this.parentDepName = parentDepName;
	}
	public Integer getRepairApplyId() {
		return repairApplyId;
	}
	public void setRepairApplyId(Integer repairApplyId) {
		this.repairApplyId = repairApplyId;
	}
	public Integer getCheckInId() {
		return checkInId;
	}
	public void setCheckInId(Integer checkInId) {
		this.checkInId = checkInId;
	}
	
	
}
