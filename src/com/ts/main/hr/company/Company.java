package com.ts.main.hr.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseBean;


@BeanProperty(description="公司")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "HR_Company")
public class Company  extends BaseBean {
	@BeanProperty(description="简称")
	@Column( length = 50) 
	private String forShort;  
    @BeanProperty(description="英文名字")
	@Column( length = 200) 
	private String nameEn; 
    @BeanProperty(description="负责人(法人姓名)")
	@Column( length = 50) 
    private String principal;  
    @BeanProperty(description="负责人id")
    @Column   
    private Integer principalId;//负责人id
    
    @BeanProperty(description="组织类型(1-总公司,2-分公司，3-集团)")
	@Column( length = 50) 
    private String companyType; 
    @BeanProperty(description="单位地址")
	@Column( length = 200) 
    private String addr; 
    @BeanProperty(description="邮政编码")
	@Column( length = 50) 
    private String postCode; 
    @BeanProperty(description="联系人")
	@Column( length = 50) 
    private String linkman; 
    @BeanProperty(description="联系电话")
	@Column( length = 50) 
    private String contactPhone; 
    @BeanProperty(description="传真")
	@Column( length = 50) 
    private String fax;
    
    @BeanProperty(description="账户名")
	@Column( length = 100) 
    private String accountName;
    @BeanProperty(description="开户行")
	@Column( length = 100) 
    private String bank;
    @BeanProperty(description="账号")
	@Column( length = 50) 
    private String accountNo;
    
    
    
	public Integer getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(Integer principalId) {
		this.principalId = principalId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getForShort() {
		return forShort;
	}
	public void setForShort(String forShort) {
		this.forShort = forShort;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	} 
    
    
}
