package com.ts.main.dorm.truck;

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

@BeanProperty(description="货运车辆出入登记")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Dorm_Truck")
public class Truck extends CommonBean { 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(length = 50)
	private String number;
	
	@Column(length = 50)
	private String tempCard;
	
	@Column
	private Integer employeeId;
	
	@Column(length = 50)
	private String visitor;
	
	@Column(length = 20)
	private String visitorPhone;
	
	@Column(length = 50)
	private String visitorIdCard;
	
	@Column(length = 50)
	private String visitorCarNum;
	
	@Column(length = 500)
	private String visitReason;
	
	@Column(length = 500)
	private String visitCompany;
	
	@Column
	private Integer visitorNum;
	
	@Column(length = 10)
	private String visitDate;
	
	@Column(length = 10)
	private String visitTime;
	
	@Column(length = 10)
	private String status;
	
	@Column
	private Integer leaveNum;
	
	@Column(length = 30)
	private String leaveBy;

	@Column(length = 10)
	private String leaveDate;

	@Column(length = 10)
	private String leaveTime;
	
	@Column
	private Integer leaveById;

	@Column
	private Integer leaveUserId;
	
	@Column(length = 50)
	private String sendNo;
	
	@Column
	private Integer hasGood;
	
	@Column(length = 5)
	private String doorIn;
	
	@Column(length = 5)
	private String doorOut;
	
	@Transient
	private String empName;
	@Transient
	private String empNumber;
	@Transient
	private String depName;
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
	public String getTempCard() {
		return tempCard;
	}
	public void setTempCard(String tempCard) {
		this.tempCard = tempCard;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getVisitorPhone() {
		return visitorPhone;
	}
	public void setVisitorPhone(String visitorPhone) {
		this.visitorPhone = visitorPhone;
	}
	public String getVisitorIdCard() {
		return visitorIdCard;
	}
	public void setVisitorIdCard(String visitorIdCard) {
		this.visitorIdCard = visitorIdCard;
	}
	public String getVisitorCarNum() {
		return visitorCarNum;
	}
	public void setVisitorCarNum(String visitorCarNum) {
		this.visitorCarNum = visitorCarNum;
	}
	public String getVisitReason() {
		return visitReason;
	}
	public void setVisitReason(String visitReason) {
		this.visitReason = visitReason;
	}
	public String getVisitCompany() {
		return visitCompany;
	}
	public void setVisitCompany(String visitCompany) {
		this.visitCompany = visitCompany;
	}
	public Integer getVisitorNum() {
		return visitorNum;
	}
	public void setVisitorNum(Integer visitorNum) {
		this.visitorNum = visitorNum;
	}
	public String getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	public String getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getLeaveNum() {
		return leaveNum;
	}
	public void setLeaveNum(Integer leaveNum) {
		this.leaveNum = leaveNum;
	}
	public String getLeaveBy() {
		return leaveBy;
	}
	public void setLeaveBy(String leaveBy) {
		this.leaveBy = leaveBy;
	}
	public String getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
	public String getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}
	public Integer getLeaveById() {
		return leaveById;
	}
	public void setLeaveById(Integer leaveById) {
		this.leaveById = leaveById;
	}
	public Integer getLeaveUserId() {
		return leaveUserId;
	}
	public void setLeaveUserId(Integer leaveUserId) {
		this.leaveUserId = leaveUserId;
	}
	public String getSendNo() {
		return sendNo;
	}
	public void setSendNo(String sendNo) {
		this.sendNo = sendNo;
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
	public Integer getHasGood() {
		return hasGood;
	}
	public void setHasGood(Integer hasGood) {
		this.hasGood = hasGood;
	}
	public String getVisitor() {
		return visitor;
	}
	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}
	public String getDoorIn() {
		return doorIn;
	}
	public void setDoorIn(String doorIn) {
		this.doorIn = doorIn;
	}
	public String getDoorOut() {
		return doorOut;
	}
	public void setDoorOut(String doorOut) {
		this.doorOut = doorOut;
	}
	
	
}
