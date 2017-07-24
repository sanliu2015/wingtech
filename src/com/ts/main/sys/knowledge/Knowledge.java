package com.ts.main.sys.knowledge;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseBean;

@Entity
@Table(name = "SYS_Knowledge")
public class Knowledge extends BaseBean {
	
	@BeanProperty(description="知识库父ID")
    protected Integer knowledgeId;
	
	@BeanProperty(description="知识内容")
    @Column(length = 150) 
    protected String content;
	
	@BeanProperty(description="知识类别")
    @Column(length = 50) 
    protected Integer knowledgeType;
	
	@BeanProperty(description="接受者类型")
    @Column(length = 50) 
    protected String receiverType;
	
	@BeanProperty(description="文件大小")
    @Column(length = 50) 
    protected String size;
	
	@BeanProperty(description="最近阅读时间")
    @Column( length = 55)
    protected String readingTime; 
	
	 @BeanProperty(description="紧急级别")
	 @Column(length = 10)  
	 private String emergentLevel;
	 
	 @BeanProperty(description="发给手机首页的知识点")
	 @Column(length = 10)  
	 private String mobileHome;
	 
	 @BeanProperty(description="公司id")
	 @Column(length = 200)  
	 private String companyIds;
	 @BeanProperty(description="部门id")
	 @Column(length = 500)  
	 private String deptIds;
	 @Transient
	 private String employeeIds;

	public Integer getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(Integer knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getKnowledgeType() {
		return knowledgeType;
	}

	public void setKnowledgeType(Integer knowledgeType) {
		this.knowledgeType = knowledgeType;
	}

	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getReadingTime() {
		return readingTime;
	}

	public void setReadingTime(String readingTime) {
		this.readingTime = readingTime;
	}

	public String getEmergentLevel() {
		return emergentLevel;
	}

	public void setEmergentLevel(String emergentLevel) {
		this.emergentLevel = emergentLevel;
	}

	public String getMobileHome() {
		return mobileHome;
	}

	public void setMobileHome(String mobileHome) {
		this.mobileHome = mobileHome;
	}

	public String getCompanyIds() {
		return companyIds;
	}

	public void setCompanyIds(String companyIds) {
		this.companyIds = companyIds;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(String employeeIds) {
		this.employeeIds = employeeIds;
	}
	
}
