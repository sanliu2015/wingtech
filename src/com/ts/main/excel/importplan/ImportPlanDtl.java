package com.ts.main.excel.importplan;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Excel_ImportPlanDtl")
public class ImportPlanDtl implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(length = 50)
	private String title;
	
	@Column(length = 50)
	private String colName;
	
	@Column(length = 50)
	private String colType;
	
	@Column
	private Integer hdrId;
	
	@Column
	private String description;
	
	@Transient
	protected String recordOperateStatus;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}

	public Integer getHdrId() {
		return hdrId;
	}

	public void setHdrId(Integer hdrId) {
		this.hdrId = hdrId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRecordOperateStatus() {
		return recordOperateStatus;
	}

	public void setRecordOperateStatus(String recordOperateStatus) {
		this.recordOperateStatus = recordOperateStatus;
	}

	
}
