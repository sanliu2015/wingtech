package com.ts.main.dorm.building;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.CommonBean;
import com.ts.main.hr.employee.Employee;

@BeanProperty(description = "楼栋信息")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Dorm_Building")
public class Building extends CommonBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@BeanProperty(description = "名称")
	@Column(length = 100)
	private String name;

	@BeanProperty(description = "唯一编号")
	@Column(length = 30, unique=true, nullable=false)
	private String number;
	
	@Column
	private Integer principalId;	
	
	
	@BeanProperty(description = "节点属性")
	@Column(length = 5)
	private String kind;
	
	@BeanProperty(description = "类型")
	@Column(length = 5)
	private String type;

	@BeanProperty(description = "公摊水电费")
	@Column(precision = 9, scale = 2)
	private BigDecimal sharedFee = new BigDecimal(0.00);

	@BeanProperty(description = "卫生清洗费")
	@Column(precision = 9, scale = 2)
	private BigDecimal cleanFee = new BigDecimal(0.00);

	@BeanProperty(description = "水费")
	@Column(precision = 9, scale = 2)
	private BigDecimal waterFee = new BigDecimal(0.00);

	@BeanProperty(description = "电费")
	@Column(precision = 9, scale = 2)
	private BigDecimal powerFee = new BigDecimal(0.00);

	@BeanProperty(description = "气费")
	@Column(precision = 9, scale = 2)
	private BigDecimal gasFee = new BigDecimal(0.00);

	@BeanProperty(description = "是否有气")
	@Column
	private Integer hasGas;

	@BeanProperty(description = "父节点")
	@Column
	private Integer parentId;

	@Transient
	private String principalName;
	
	@Transient
	private String parentName;

	@Transient
	private Integer updateChildren;
	

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

	public Integer getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(Integer principalId) {
		this.principalId = principalId;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Integer getHasGas() {
		return hasGas;
	}

	public void setHasGas(Integer hasGas) {
		this.hasGas = hasGas;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getUpdateChildren() {
		return updateChildren;
	}

	public void setUpdateChildren(Integer updateChildren) {
		this.updateChildren = updateChildren;
	}


}
