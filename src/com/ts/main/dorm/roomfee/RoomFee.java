package com.ts.main.dorm.roomfee;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ts.core.annotation.BeanProperty;

@BeanProperty(description="房间费用明细")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Dorm_RoomFee")
public class RoomFee implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(nullable=false)
	private Integer roomId;
	
	@Column(nullable=false)
	private Integer employeeId;
	
	@Column(nullable=false)
	private Integer checkInId;
	
	@Column(length=10)
	private String yearMonth;
	
	@Column(length=10)
	private String inDate;
	
	@Column(length=10)
	private String outDate;
	
	@Column(length=10,nullable=false)
	private String beginDate;
	
	@Column(length=10,nullable=false)
	private String endedDate;
	
	@Column
	private Integer inDays;
	
	@BeanProperty(description="房间租金")
	@Column(precision=9,scale=2)
	private BigDecimal rentStandard = BigDecimal.ZERO;
	
	@BeanProperty(description = "公摊费")
	@Column(precision = 9, scale = 2)
	private BigDecimal sharedFee = BigDecimal.ZERO;
	
	@BeanProperty(description = "水电气费合计")
	@Column(precision = 9, scale = 2)
	private BigDecimal wtpwgsFee = BigDecimal.ZERO;
	
	@BeanProperty(description = "房租")
	@Column(precision = 9, scale = 2)
	private BigDecimal rentFee = BigDecimal.ZERO;
	
	@BeanProperty(description = "公司补贴")
	@Column(precision = 9, scale = 2)
	private BigDecimal corpFee = BigDecimal.ZERO;
	
	@BeanProperty(description = "职位补贴")
	@Column(precision = 9, scale = 2)
	private BigDecimal subsidies;
	
	@BeanProperty(description = "职位补贴标准/天")
	@Column(precision = 9, scale = 2)
	private BigDecimal subsidiesStandard;
	
	@Column
	private Integer generateFeeFlag;
	
	@BeanProperty(description = "导出excel专用每个房间第一行标识")
	@Column
	private Integer firstRowFlag;
	
	@BeanProperty(description = "导出excel合并单元行")
	@Column
	private Integer totalSum;
	
	@Column
	private Integer payPersonId;
	
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

	public Integer getCheckInId() {
		return checkInId;
	}

	public void setCheckInId(Integer checkInId) {
		this.checkInId = checkInId;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	public String getOutDate() {
		return outDate;
	}

	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}

	public Integer getInDays() {
		return inDays;
	}

	public void setInDays(Integer inDays) {
		this.inDays = inDays;
	}

	public BigDecimal getRentStandard() {
		return rentStandard;
	}

	public void setRentStandard(BigDecimal rentStandard) {
		this.rentStandard = rentStandard;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndedDate() {
		return endedDate;
	}

	public void setEndedDate(String endedDate) {
		this.endedDate = endedDate;
	}

	public BigDecimal getSharedFee() {
		return sharedFee;
	}

	public void setSharedFee(BigDecimal sharedFee) {
		this.sharedFee = sharedFee;
	}


	public BigDecimal getWtpwgsFee() {
		return wtpwgsFee;
	}

	public void setWtpwgsFee(BigDecimal wtpwgsFee) {
		this.wtpwgsFee = wtpwgsFee;
	}

	public BigDecimal getRentFee() {
		return rentFee;
	}

	public void setRentFee(BigDecimal rentFee) {
		this.rentFee = rentFee;
	}

	public Integer getGenerateFeeFlag() {
		return generateFeeFlag;
	}

	public void setGenerateFeeFlag(Integer generateFeeFlag) {
		this.generateFeeFlag = generateFeeFlag;
	}

	public Integer getFirstRowFlag() {
		return firstRowFlag;
	}

	public void setFirstRowFlag(Integer firstRowFlag) {
		this.firstRowFlag = firstRowFlag;
	}

	public Integer getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(Integer totalSum) {
		this.totalSum = totalSum;
	}

	public BigDecimal getCorpFee() {
		return corpFee;
	}

	public void setCorpFee(BigDecimal corpFee) {
		this.corpFee = corpFee;
	}

	public BigDecimal getSubsidies() {
		return subsidies;
	}

	public void setSubsidies(BigDecimal subsidies) {
		this.subsidies = subsidies;
	}

	public BigDecimal getSubsidiesStandard() {
		return subsidiesStandard;
	}

	public void setSubsidiesStandard(BigDecimal subsidiesStandard) {
		this.subsidiesStandard = subsidiesStandard;
	}

	public Integer getPayPersonId() {
		return payPersonId;
	}

	public void setPayPersonId(Integer payPersonId) {
		this.payPersonId = payPersonId;
	}

	
}
