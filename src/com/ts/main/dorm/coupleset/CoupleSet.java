package com.ts.main.dorm.coupleset;

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

@BeanProperty(description = "夫妻关系设置：实际扣款")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Dorm_CoupleSet")
public class CoupleSet extends CommonBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column
	private Integer orgEmpId;
	
	@Column
	private Integer desEmpId;
	
	@Column(length = 5)
	private String status;
	
	@Transient
	private String orgEmpNumber;
	@Transient
	private String orgEmpSex;
	@Transient
	private String orgEmpName;
	@Transient
	private String desEmpName;
	@Transient
	private String desEmpNumber;
	@Transient
	private String destEmpSex;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrgEmpId() {
		return orgEmpId;
	}
	public void setOrgEmpId(Integer orgEmpId) {
		this.orgEmpId = orgEmpId;
	}
	public Integer getDesEmpId() {
		return desEmpId;
	}
	public void setDesEmpId(Integer desEmpId) {
		this.desEmpId = desEmpId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrgEmpNumber() {
		return orgEmpNumber;
	}
	public void setOrgEmpNumber(String orgEmpNumber) {
		this.orgEmpNumber = orgEmpNumber;
	}
	public String getOrgEmpSex() {
		return orgEmpSex;
	}
	public void setOrgEmpSex(String orgEmpSex) {
		this.orgEmpSex = orgEmpSex;
	}
	public String getOrgEmpName() {
		return orgEmpName;
	}
	public void setOrgEmpName(String orgEmpName) {
		this.orgEmpName = orgEmpName;
	}
	public String getDesEmpName() {
		return desEmpName;
	}
	public void setDesEmpName(String desEmpName) {
		this.desEmpName = desEmpName;
	}
	public String getDesEmpNumber() {
		return desEmpNumber;
	}
	public void setDesEmpNumber(String desEmpNumber) {
		this.desEmpNumber = desEmpNumber;
	}
	public String getDestEmpSex() {
		return destEmpSex;
	}
	public void setDestEmpSex(String destEmpSex) {
		this.destEmpSex = destEmpSex;
	}

	
}
