package com.ts.main.dorm.empenter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.CommonBean;

@BeanProperty(description="员工入厂登记")
@Entity
@Table(name = "Dorm_EmpEnter")
public class EmpEnter extends CommonBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(length = 50)
	private String icNo;
	
	@Column(length = 50)
	private String name;
	
	@Column(length = 50)
	private String number;
	
	@Column(length = 10)
	private String gender;
	
	@Column(length = 50)
	private String idCard;
	
	@Column(length = 100)
	private String depName;
	
	@Column(length = 100)
	private String parentDepName;
	
	@Column(length = 200)
	private String posName;
	
	@Column(length = 5)
	private String doorIn;
	@Column(length = 5)
	private String doorOut;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIcNo() {
		return icNo;
	}

	public void setIcNo(String icNo) {
		this.icNo = icNo;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
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

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
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
