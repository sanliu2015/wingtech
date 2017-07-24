package com.ts.main.dorm.allfee;

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

@BeanProperty(description="所有费用统计")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Dorm_AllFee")
public class AllFee implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(length=10)
	private String yearMonth;
	
	@Column
	private Integer employeeId;
	
	@Column
	private Integer payPersonId;
	
	@BeanProperty(description = "房租")
	@Column(precision = 9, scale = 2)
	private BigDecimal rentFee = BigDecimal.ZERO;
	
	@BeanProperty(description = "职位补贴")
	@Column(precision = 9, scale = 2)
	private BigDecimal subsidies;
	
	@BeanProperty(description = "公司补贴")
	@Column(precision = 9, scale = 2)
	private BigDecimal corpFee;
	
	@BeanProperty(description = "水电气费")
	@Column(precision = 9, scale = 2)
	private BigDecimal wtpwgsFee = BigDecimal.ZERO;
	
	@BeanProperty(description = "公摊费")
	@Column(precision = 9, scale = 2)
	private BigDecimal sharedFee = BigDecimal.ZERO;
		
	@BeanProperty(description = "应付房费")
	@Column(precision = 9, scale = 2)
	private BigDecimal paidRoomFee;
	
	@BeanProperty(description = "遗失损坏")
	@Column(precision = 9, scale = 2)
	private BigDecimal damageFee = BigDecimal.ZERO;
	
	@BeanProperty(description = "奖励")
	@Column(precision = 9, scale = 2)
	private BigDecimal rewardFee = BigDecimal.ZERO;
	
	@BeanProperty(description = "惩罚")
	@Column(precision = 9, scale = 2)
	private BigDecimal punishFee = BigDecimal.ZERO;
	
	private BigDecimal trueRentFee = BigDecimal.ZERO;
	
	@BeanProperty(description = "修改人员姓名")
	@Column(length = 30)
	private String modifiedBy;

	@BeanProperty(description = "修改日期")
	@Column(length = 10)
	private String modifyDate;

	@BeanProperty(description = "修改时间")
	@Column(length = 10)
	private String modifyTime;
	
	@BeanProperty(description = "修改人员id")
	@Column
	private Integer modifiedById;

	@BeanProperty(description = "修改时的操作员id")
	@Column
	private Integer modifyUserId;

	@BeanProperty(description = "描述")
	@Column(length = 500)
	private String description;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getPayPersonId() {
		return payPersonId;
	}

	public void setPayPersonId(Integer payPersonId) {
		this.payPersonId = payPersonId;
	}

	public BigDecimal getRentFee() {
		return rentFee;
	}

	public void setRentFee(BigDecimal rentFee) {
		this.rentFee = rentFee;
	}

	public BigDecimal getSubsidies() {
		return subsidies;
	}

	public void setSubsidies(BigDecimal subsidies) {
		this.subsidies = subsidies;
	}

	public BigDecimal getCorpFee() {
		return corpFee;
	}

	public void setCorpFee(BigDecimal corpFee) {
		this.corpFee = corpFee;
	}

	public BigDecimal getWtpwgsFee() {
		return wtpwgsFee;
	}

	public void setWtpwgsFee(BigDecimal wtpwgsFee) {
		this.wtpwgsFee = wtpwgsFee;
	}

	public BigDecimal getSharedFee() {
		return sharedFee;
	}

	public void setSharedFee(BigDecimal sharedFee) {
		this.sharedFee = sharedFee;
	}

	public BigDecimal getPaidRoomFee() {
		return paidRoomFee;
	}

	public void setPaidRoomFee(BigDecimal paidRoomFee) {
		this.paidRoomFee = paidRoomFee;
	}

	public BigDecimal getDamageFee() {
		return damageFee;
	}

	public void setDamageFee(BigDecimal damageFee) {
		this.damageFee = damageFee;
	}

	public BigDecimal getRewardFee() {
		return rewardFee;
	}

	public void setRewardFee(BigDecimal rewardFee) {
		this.rewardFee = rewardFee;
	}

	public BigDecimal getPunishFee() {
		return punishFee;
	}

	public void setPunishFee(BigDecimal punishFee) {
		this.punishFee = punishFee;
	}

	public BigDecimal getTrueRentFee() {
		return trueRentFee;
	}

	public void setTrueRentFee(BigDecimal trueRentFee) {
		this.trueRentFee = trueRentFee;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	} 

	
}
