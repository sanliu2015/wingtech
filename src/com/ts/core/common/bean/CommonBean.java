package com.ts.core.common.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.ts.core.annotation.BeanProperty;

@MappedSuperclass
public class CommonBean implements Serializable {

	private static final long serialVersionUID = 336704369145627435L;

	@BeanProperty(description = "创建者姓名")
	@Column(length = 30)
	protected String createdBy;
	
	@BeanProperty(description = "创建日期")
	@Column(length = 10)
	protected String createDate;

	@BeanProperty(description = "创建时间")
	@Column(length = 10)
	protected String createTime;

	@BeanProperty(description = "创建人员id")
	@Column
	protected Integer createrId;

	@BeanProperty(description = "操作员id")
	@Column
	protected Integer userId;

	@BeanProperty(description = "修改人员姓名")
	@Column(length = 30)
	protected String modifiedBy;

	@BeanProperty(description = "修改日期")
	@Column(length = 10)
	protected String modifyDate;

	@BeanProperty(description = "修改时间")
	@Column(length = 10)
	protected String modifyTime;
	
	@BeanProperty(description = "修改人员id")
	@Column
	protected Integer modifiedById;

	@BeanProperty(description = "修改时的操作员id")
	@Column
	protected Integer modifyUserId;

	@BeanProperty(description = "描述")
	@Column(length = 500)
	protected String description;
	
	@Transient
	protected String recordOperateStatus;


	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getRecordOperateStatus() {
		return recordOperateStatus;
	}

	public void setRecordOperateStatus(String recordOperateStatus) {
		this.recordOperateStatus = recordOperateStatus;
	}

	
}
