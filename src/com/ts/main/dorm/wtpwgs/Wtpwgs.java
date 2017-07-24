package com.ts.main.dorm.wtpwgs;

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

@BeanProperty(description = "水电气费")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Dorm_Wtpwgs")
public class Wtpwgs extends CommonBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(nullable=false)
	private Integer roomId;
	
	@Column
	private Integer parentRoomId;
	
	@BeanProperty(description = "锁定标识，为1不能修改")
	@Column
	private Integer lockFlag;
	
	@BeanProperty(description = "月份")
	@Column(length=10, nullable=false)
	private String yearMonth;
	
	@BeanProperty(description = "上月用水抄数")
	@Column(precision = 9, scale = 2, nullable=false)
	private BigDecimal lastWaterNum = BigDecimal.ZERO;
	
	@BeanProperty(description = "本月用水抄数")
	@Column(precision = 9, scale = 2, nullable=false)
	private BigDecimal thisWaterNum = BigDecimal.ZERO;
	
	@BeanProperty(description = "水费价格")
	@Column(precision = 9, scale = 2, nullable=false)
	private BigDecimal waterPrice = BigDecimal.ZERO;
	
	@Column(precision = 9, scale = 2)
	private BigDecimal waterFee = BigDecimal.ZERO;
	
	@BeanProperty(description = "上月用电抄数")
	@Column(precision = 9, scale = 2, nullable=false)
	private BigDecimal lastPowerNum = BigDecimal.ZERO;
	
	@BeanProperty(description = "本月用电抄数")
	@Column(precision = 9, scale = 2, nullable=false)
	private BigDecimal thisPowerNum = BigDecimal.ZERO;
	
	@BeanProperty(description = "电费价格")
	@Column(precision = 9, scale = 2, nullable=false)
	private BigDecimal powerPrice = BigDecimal.ZERO;
	
	@Column(precision = 9, scale = 2)
	private BigDecimal powerFee = BigDecimal.ZERO;
	
	@BeanProperty(description = "上月燃气抄数")
	@Column(precision = 9, scale = 2)
	private BigDecimal lastGasNum = BigDecimal.ZERO;
	
	@BeanProperty(description = "本月燃气抄数")
	@Column(precision = 9, scale = 2)
	private BigDecimal thisGasNum = BigDecimal.ZERO;
	
	@BeanProperty(description = "燃气价格")
	@Column(precision = 9, scale = 2)
	private BigDecimal gasPrice = BigDecimal.ZERO;
	
	@Column(precision = 9, scale = 2)
	private BigDecimal gasFee = BigDecimal.ZERO;
	
	@Column(precision = 9, scale = 2)
	private BigDecimal wtpwgsFee = BigDecimal.ZERO;
	
	@Transient
	private String roomName;
	@Transient
	private String buildName;
	
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
	public Integer getLockFlag() {
		return lockFlag;
	}
	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public BigDecimal getLastWaterNum() {
		return lastWaterNum;
	}
	public void setLastWaterNum(BigDecimal lastWaterNum) {
		this.lastWaterNum = lastWaterNum;
	}
	public BigDecimal getThisWaterNum() {
		return thisWaterNum;
	}
	public void setThisWaterNum(BigDecimal thisWaterNum) {
		this.thisWaterNum = thisWaterNum;
	}
	public BigDecimal getWaterPrice() {
		return waterPrice;
	}
	public void setWaterPrice(BigDecimal waterPrice) {
		this.waterPrice = waterPrice;
	}
	public BigDecimal getLastPowerNum() {
		return lastPowerNum;
	}
	public void setLastPowerNum(BigDecimal lastPowerNum) {
		this.lastPowerNum = lastPowerNum;
	}
	public BigDecimal getThisPowerNum() {
		return thisPowerNum;
	}
	public void setThisPowerNum(BigDecimal thisPowerNum) {
		this.thisPowerNum = thisPowerNum;
	}
	public BigDecimal getPowerPrice() {
		return powerPrice;
	}
	public void setPowerPrice(BigDecimal powerPrice) {
		this.powerPrice = powerPrice;
	}
	public BigDecimal getLastGasNum() {
		return lastGasNum;
	}
	public void setLastGasNum(BigDecimal lastGasNum) {
		this.lastGasNum = lastGasNum;
	}
	public BigDecimal getThisGasNum() {
		return thisGasNum;
	}
	public void setThisGasNum(BigDecimal thisGasNum) {
		this.thisGasNum = thisGasNum;
	}
	public BigDecimal getGasPrice() {
		return gasPrice;
	}
	public void setGasPrice(BigDecimal gasPrice) {
		this.gasPrice = gasPrice;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public BigDecimal getWaterFee() {
		return waterFee;
	}
	public void setWaterFee(BigDecimal waterFee) {
		this.waterFee = waterFee;
	}
	public BigDecimal getPowerFee() {
		return powerFee;
	}
	public void setPowerFee(BigDecimal powerFee) {
		this.powerFee = powerFee;
	}
	public BigDecimal getGasFee() {
		return gasFee;
	}
	public void setGasFee(BigDecimal gasFee) {
		this.gasFee = gasFee;
	}
	public BigDecimal getWtpwgsFee() {
		return wtpwgsFee;
	}
	public void setWtpwgsFee(BigDecimal wtpwgsFee) {
		this.wtpwgsFee = wtpwgsFee;
	}
	public Integer getParentRoomId() {
		return parentRoomId;
	}
	public void setParentRoomId(Integer parentRoomId) {
		this.parentRoomId = parentRoomId;
	}

	
}
