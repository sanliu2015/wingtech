package com.ts.main.excel.importplan;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ts.core.common.bean.CommonBean;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Excel_ImportPlan")
public class ImportPlan extends CommonBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column
	private String name;
	
	@Column(length=50,unique=true)
	private String number;
	
	@Column(length=500)
	private String beforeSp;
	
	@Column(length=500)
	private String middleSp;
	
	@Column(length=500)
	private String afterSp;
	
	@Column
	private String templateName;

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

	public String getBeforeSp() {
		return beforeSp;
	}

	public void setBeforeSp(String beforeSp) {
		this.beforeSp = beforeSp;
	}

	public String getMiddleSp() {
		return middleSp;
	}

	public void setMiddleSp(String middleSp) {
		this.middleSp = middleSp;
	}

	public String getAfterSp() {
		return afterSp;
	}

	public void setAfterSp(String afterSp) {
		this.afterSp = afterSp;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	
}
