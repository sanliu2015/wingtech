package com.ts.main.sys.subscribe;

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
import com.ts.core.common.bean.BaseFileBean;
import com.ts.main.crm.cust.Customer;
import com.ts.main.hr.company.Company;
import com.ts.main.hr.department.Department;
import com.ts.main.hr.employee.Employee;
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "SYS_SubscribeReceiver")
public class SubscribeReceiver extends BaseBean  {
	
	
	@BeanProperty(description="订阅主题代码")
    @Column( length = 30) 
	private String topicKind;
	@BeanProperty(description="栏目id")
    @Column 
	private Integer bulletinTopicId;
	@BeanProperty(description="接收者ID")
    @Column 
	private Integer receiverId; 
	
	@BeanProperty(description="业务员ID")
    @Column 
	private Integer employeeId; 
	@BeanProperty(description="公司id")
    @Column 
	private Integer orgId; 
	@BeanProperty(description="部门id")
    @Column 
	private Integer deptId; 
	@BeanProperty(description="部门id")
    @Column 
    private Integer custId; 
	
	@BeanProperty(description="接收人类型")
    @Column(length = 20)  
	private  String receiverType; 
	
	@Transient
	private Employee employee;//个人对象
	@Transient
	private Department dept;//部门对象
	@Transient
	private Company company;//公司对象
	@Transient
	private Customer customer;
	 
	
	 
	public Integer getBulletinTopicId() {
		return bulletinTopicId;
	}
	public void setBulletinTopicId(Integer bulletinTopicId) {
		this.bulletinTopicId = bulletinTopicId;
	}
	public String getTopicKind() {
		return topicKind;
	}
	public void setTopicKind(String topicKind) {
		this.topicKind = topicKind;
	}
	public Integer getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Department getDept() {
		return dept;
	}
	public void setDept(Department dept) {
		this.dept = dept;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	 
	
	
}
