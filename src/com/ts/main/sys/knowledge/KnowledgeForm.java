package com.ts.main.sys.knowledge;

import java.util.ArrayList;
import java.util.List;

import com.ts.core.common.form.BaseInfoForm;

public class KnowledgeForm extends BaseInfoForm {
	private Integer pageNo;
	private Integer pageSize; 
	private Knowledge know = new Knowledge();
	private List<Knowledge> list = new ArrayList<Knowledge>();
	private KnowledgeType knowType = new KnowledgeType();
	private List<KnowledgeType> typeList = new ArrayList<KnowledgeType>();
	private List<KnowledgeFile> knowledgeFileList = new ArrayList<KnowledgeFile>();
	private List<KnowledgeReceiver> employeeReceiverList = new ArrayList<KnowledgeReceiver>();
	
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Knowledge getKnow() {
		return know;
	}
	public void setKnow(Knowledge know) {
		this.know = know;
	}
	public List<Knowledge> getList() {
		return list;
	}
	public void setList(List<Knowledge> list) {
		this.list = list;
	}
	public KnowledgeType getKnowType() {
		return knowType;
	}
	public void setKnowType(KnowledgeType knowType) {
		this.knowType = knowType;
	}
	public List<KnowledgeType> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<KnowledgeType> typeList) {
		this.typeList = typeList;
	}
	public List<KnowledgeFile> getKnowledgeFileList() {
		return knowledgeFileList;
	}
	public void setKnowledgeFileList(List<KnowledgeFile> knowledgeFileList) {
		this.knowledgeFileList = knowledgeFileList;
	}
	public List<KnowledgeReceiver> getEmployeeReceiverList() {
		return employeeReceiverList;
	}
	public void setEmployeeReceiverList(List<KnowledgeReceiver> employeeReceiverList) {
		this.employeeReceiverList = employeeReceiverList;
	}
	
}
