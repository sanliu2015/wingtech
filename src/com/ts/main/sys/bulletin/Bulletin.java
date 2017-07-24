package com.ts.main.sys.bulletin;
 
import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.Table; 
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;  

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseBean;  


@BeanProperty(description="公告内容管理")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "SYS_Bulletin")
public class Bulletin  extends BaseBean{
	
	 @BeanProperty(description="栏目id")
     @Column 
	 private Integer bulletinTopicId;
	 @BeanProperty(description="公告大类别")
	 @Column(length = 20)  
	 private  String receiverType;//接收人类型
	 
	 @BeanProperty(description="公告大类别")
	 @Column(length = 20)  
	 private String topicKind; 
	 @BeanProperty(description="是否需要回复")
	 @Column(length = 10)  
	 private String needReply;
	 @BeanProperty(description="紧急级别")
	 @Column(length = 10)  
	 private String emergentLevel;
	 @BeanProperty(description="发给手机首页的新闻")
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
	 @Transient
	 private String custIds;
	 
	 
	public String getMobileHome() {
		return mobileHome;
	}
	public void setMobileHome(String mobileHome) {
		this.mobileHome = mobileHome;
	}
	public String getEmergentLevel() {
		return emergentLevel;
	}
	public void setEmergentLevel(String emergentLevel) {
		this.emergentLevel = emergentLevel;
	}
	public String getNeedReply() {
		return needReply;
	}
	public void setNeedReply(String needReply) {
		this.needReply = needReply;
	}
	public Integer getBulletinTopicId() {
		return bulletinTopicId;
	}
	public void setBulletinTopicId(Integer bulletinTopicId) {
		this.bulletinTopicId = bulletinTopicId;
	}
	 
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	public String getTopicKind() {
		return topicKind;
	}
	public void setTopicKind(String topicKind) {
		this.topicKind = topicKind;
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
	public String getCustIds() {
		return custIds;
	}
	public void setCustIds(String custIds) {
		this.custIds = custIds;
	}
	 
	 
}
