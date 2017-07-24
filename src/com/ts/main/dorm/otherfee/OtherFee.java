package com.ts.main.dorm.otherfee;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import com.ts.core.annotation.BeanProperty;

@BeanProperty(description="其他费用统计")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Dorm_OtherFee")
public class OtherFee implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(nullable=false)
	private Integer employeeId;
	
	@Column(length=10,nullable=false)
	private String yearMonth;
	
	@BeanProperty(description = "奖励")
	@Column(precision = 9, scale = 2)
	private BigDecimal rewardFee = BigDecimal.ZERO;
	
	@BeanProperty(description = "惩罚")
	@Column(precision = 9, scale = 2)
	private BigDecimal punishFee = BigDecimal.ZERO;
	
	@BeanProperty(description = "遗失损坏")
	@Column(precision = 9, scale = 2)
	private BigDecimal damageFee = BigDecimal.ZERO;
	
	@Column(columnDefinition = "datetime")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date generateDate;

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

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
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

	public BigDecimal getDamageFee() {
		return damageFee;
	}

	public void setDamageFee(BigDecimal damageFee) {
		this.damageFee = damageFee;
	}

	public Date getGenerateDate() {
		return generateDate;
	}

	public void setGenerateDate(Date generateDate) {
		this.generateDate = generateDate;
	}
	
	
}
