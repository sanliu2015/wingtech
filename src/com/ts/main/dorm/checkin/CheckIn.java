package com.ts.main.dorm.checkin;

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
import com.ts.core.common.bean.BaseBean;
import com.ts.core.common.bean.CommonBean;

@BeanProperty(description="房间入住信息")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Dorm_CheckIn")
public class CheckIn extends CommonBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@BeanProperty(description="房间Id")
	@Column(nullable=false) 
    private Integer roomId;
	
	@BeanProperty(description="员工Id")
	@Column(nullable=false) 
    private Integer employeeId;
	
	@BeanProperty(description="入住日期")
	@Column(length = 10, nullable=false) 
    private String inDate;
	
	@BeanProperty(description="入住时间")
	@Column( length = 10) 
    private String inTime;
	
	@BeanProperty(description="入住铺位")
	@Column( length = 20) 
    private String bunk; 
	
	@BeanProperty(description="是否已退房")
	@Column
	private Integer checkOutFlag;
	
	@BeanProperty(description="退宿日期")
	@Column( length = 10) 
	private String outDate;
	
	@BeanProperty(description="退宿时间")
	@Column( length = 10) 
	private String outTime;
	
	@BeanProperty(description="退宿原因")
	@Column( length = 500) 
	private String outReason;
	
	@BeanProperty(description="经办人")
	@Column
	private Integer handleEmpId;
	
	@Column(length = 50)
	private String keyStatus;
	
	@Column(length = 50)
	private String remoterKeep;
	
	@Transient
	private String empName;
	@Transient
	private String empNumber;
	@Transient
	private String roomName;
	@Transient
	private String buildingName;
	@Transient
	private String handleEmpName;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getBunk() {
		return bunk;
	}

	public void setBunk(String bunk) {
		this.bunk = bunk;
	}

	public Integer getCheckOutFlag() {
		return checkOutFlag;
	}

	public void setCheckOutFlag(Integer checkOutFlag) {
		this.checkOutFlag = checkOutFlag;
	}

	public String getOutDate() {
		return outDate;
	}

	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getOutReason() {
		return outReason;
	}

	public void setOutReason(String outReason) {
		this.outReason = outReason;
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

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public Integer getHandleEmpId() {
		return handleEmpId;
	}

	public void setHandleEmpId(Integer handleEmpId) {
		this.handleEmpId = handleEmpId;
	}

	public String getHandleEmpName() {
		return handleEmpName;
	}

	public void setHandleEmpName(String handleEmpName) {
		this.handleEmpName = handleEmpName;
	}

	public String getKeyStatus() {
		return keyStatus;
	}

	public void setKeyStatus(String keyStatus) {
		this.keyStatus = keyStatus;
	}

	public String getRemoterKeep() {
		return remoterKeep;
	}

	public void setRemoterKeep(String remoterKeep) {
		this.remoterKeep = remoterKeep;
	}
	

}
