package com.ts.main.dorm.room;

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
import org.hibernate.annotations.GenericGenerator;

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseBean;
import com.ts.core.common.bean.CommonBean;

@BeanProperty(description="房间信息")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Dorm_Room")
public class Room extends CommonBean { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@BeanProperty(description = "名称")
	@Column(length = 150)
	private String name;

	@BeanProperty(description = "编号")
	@Column(length = 30, unique=true, nullable=false)
	private String number;
	
	@BeanProperty(description="楼层Id")
	@Column(nullable=false) 
    private Integer floorId;
	
	@BeanProperty(description="最多可容纳人数")
	@Column 
    private Integer bigNumber;
	
	@BeanProperty(description="租金标准")
	@Column(precision=9,scale=2)
	private BigDecimal rentStandard;
	
	@BeanProperty(description = "公摊费")
	@Column(precision = 9, scale = 2)
	private BigDecimal sharedFee = BigDecimal.ZERO;

	@BeanProperty(description = "卫生清洗费")
	@Column(precision = 9, scale = 2)
	private BigDecimal cleanFee = BigDecimal.ZERO;

	@BeanProperty(description = "水费")
	@Column(precision = 9, scale = 2)
	private BigDecimal waterFee = BigDecimal.ZERO;

	@BeanProperty(description = "电费")
	@Column(precision = 9, scale = 2)
	private BigDecimal powerFee = BigDecimal.ZERO;

	@BeanProperty(description = "气费")
	@Column(precision = 9, scale = 2)
	private BigDecimal gasFee = BigDecimal.ZERO;
	
	@BeanProperty(description = "房间类型")
	@Column(length=5)
	private String type;
	
	@BeanProperty(description = "禁用标识")
	@Column(length=5)
	private String disabled;
	
	@BeanProperty(description = "公司承担费用")
	private Integer corpBear;
	
	@Column
	private Integer parentId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getFloorId() {
		return floorId;
	}

	public void setFloorId(Integer floorId) {
		this.floorId = floorId;
	}

	public BigDecimal getRentStandard() {
		return rentStandard;
	}

	public void setRentStandard(BigDecimal rentStandard) {
		this.rentStandard = rentStandard;
	}
	
	public Integer getBigNumber() {
		return bigNumber;
	}

	public void setBigNumber(Integer bigNumber) {
		this.bigNumber = bigNumber;
	}

	public BigDecimal getSharedFee() {
		return sharedFee;
	}

	public void setSharedFee(BigDecimal sharedFee) {
		this.sharedFee = sharedFee;
	}

	public BigDecimal getCleanFee() {
		return cleanFee;
	}

	public void setCleanFee(BigDecimal cleanFee) {
		this.cleanFee = cleanFee;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public Integer getCorpBear() {
		return corpBear;
	}

	public void setCorpBear(Integer corpBear) {
		this.corpBear = corpBear;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	
	
}
