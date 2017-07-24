package com.ts.main.hr.department;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy; 

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseBean; 
import com.ts.main.hr.company.Company;
@BeanProperty(description="部门")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "HR_Department")
public class Department  extends BaseBean{
	 
    @BeanProperty(description="部门负责人姓名")
	@Column( length = 50) 
    private String principal; 
    @BeanProperty(description="负责人id")
    @Column   
    private Integer principalId;//负责人id
    @BeanProperty(description="联系电话")
	@Column( length = 50) 
    private String contactPhone; 
    @BeanProperty(description="传真")
	@Column( length = 50) 
    private String fax;
    @BeanProperty(description="传真")
   	@Column( length = 20)
    private String productDept;//是否生产部门
    
     
    
    @ManyToOne(cascade={CascadeType.PERSIST}  ) 
	@JoinColumn(name="orgId") 
	private Company company=new Company();
    @Transient
    private String companyName;
    @Transient
    private String companyNumber;
    
    
	 
	public String getCompanyNumber() {
		return companyNumber;
	}
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public Integer getPrincipalId() {
		return principalId;
	}
	public void setPrincipalId(Integer principalId) {
		this.principalId = principalId;
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
	public String getProductDept() {
		return productDept;
	}
	public void setProductDept(String productDept) {
		this.productDept = productDept;
	}
    
    
}
