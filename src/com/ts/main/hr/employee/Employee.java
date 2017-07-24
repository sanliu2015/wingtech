package com.ts.main.hr.employee;

import java.math.BigDecimal;

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
import com.ts.core.common.bean.BaseAutoIdBean;
import com.ts.core.common.bean.BaseFileBean;
import com.ts.main.hr.company.Company;
import com.ts.main.hr.department.Department;

@BeanProperty(description="员工")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "HR_Employee")
public class Employee  extends BaseAutoIdBean { 
	@BeanProperty(description="英文名字")
	@Column( length = 50) 
	private String nameEn; 
	@BeanProperty(description="性别")
	@Column( length = 10) 
    private String gender; 
	@BeanProperty(description="联系地址")
	@Column( length = 150) 
    private String addr; 
	@BeanProperty(description="联系电话")
	@Column( length = 50) 
    private String phone; 
	@BeanProperty(description="邮箱")
	@Column( length = 100) 
	private String email;
	@BeanProperty(description="即时通")
	@Column( length = 50) 
	private String messageNo;
	
	@BeanProperty(description="学历")
	@Column( length = 20) 
	private String degree;
	
	@BeanProperty(description="住房补贴")
	@Column
	private BigDecimal subsidies;
	
	@BeanProperty(description="身份证号码")
	@Column( length =30) 
    private String idCard; 
	@BeanProperty(description="考勤卡号")
	@Column( length =30) 
	private String icNo;
	@BeanProperty(description="考勤卡ID")
	@Column( length =30) 
	private String idNo;
	@BeanProperty(description="发卡日期")
	@Column( length =15) 
	private String cardDate;
	@BeanProperty(description="出生日期")
	@Column( length =15) 
	private String birthday;
	@BeanProperty(description="加入公司日期")
	@Column( length = 20) 
    private String inDate; 
	@BeanProperty(description="合同到期日期")
	@Column( length = 20) 
    private String outDate;
	@BeanProperty(description="职位")
	@Column  
	private Integer positionId;
	@BeanProperty(description="类别")
	@Column( length =20) 
    private String employeeKind; 
	
	@BeanProperty(description="是否业务人员")
	@Column( length =5) 
	private String isSaler; 
	@BeanProperty(description="可休年假小时数") 
	@Column(precision=18,scale=2 ) 
	private   BigDecimal yearHolidays;
	@BeanProperty(description="工作年数")
	@Column 
	private Integer workYears;
	@Transient
	private String positionName;
	
	@ManyToOne(cascade={CascadeType.PERSIST}  ) 
	@JoinColumn(name="deptId") 
	private Department dept=new Department();
	@ManyToOne(cascade={CascadeType.PERSIST}  ) 
	@JoinColumn(name="orgId") 
	private Company company=new Company();
	
	@BeanProperty(description="分类")
	@Column( length =5) 
    private String employeeCategory; 
	@BeanProperty(description="剩余可调休小时数")
	@Column(precision=18,scale=2 ) 
	private   BigDecimal remainRestHours;
	@Column
	private Integer interimId;//劳务派遣公司id
	@Column
	private Integer payPersonId;//关联付款人员id
	
	@Column(length = 500)
	private String leaveReason;
	
	
	public Integer getPayPersonId() {
		return payPersonId;
	}
	public void setPayPersonId(Integer payPersonId) {
		this.payPersonId = payPersonId;
	}
	public Integer getInterimId() {
		return interimId;
	}
	public void setInterimId(Integer interimId) {
		this.interimId = interimId;
	}
	public BigDecimal getRemainRestHours() {
		return remainRestHours;
	}
	public void setRemainRestHours(BigDecimal remainRestHours) {
		this.remainRestHours = remainRestHours;
	}
	public String getEmployeeCategory() {
		return employeeCategory;
	}
	public void setEmployeeCategory(String employeeCategory) {
		this.employeeCategory = employeeCategory;
	}
	public String getIcNo() {
		return icNo;
	}
	public void setIcNo(String icNo) {
		this.icNo = icNo;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getCardDate() {
		return cardDate;
	}
	public void setCardDate(String cardDate) {
		this.cardDate = cardDate;
	}
	public Integer getWorkYears() {
		return workYears;
	}
	public void setWorkYears(Integer workYears) {
		this.workYears = workYears;
	}
	 
	public BigDecimal getYearHolidays() {
		return yearHolidays;
	}
	public void setYearHolidays(BigDecimal yearHolidays) {
		this.yearHolidays = yearHolidays;
	}
	public String getIsSaler() {
		return isSaler;
	}
	public void setIsSaler(String isSaler) {
		this.isSaler = isSaler;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMessageNo() {
		return messageNo;
	}
	public void setMessageNo(String messageNo) {
		this.messageNo = messageNo;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getEmployeeKind() {
		return employeeKind;
	}
	public void setEmployeeKind(String employeeKind) {
		this.employeeKind = employeeKind;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
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
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getInDate() {
		return inDate;
	}
	public void setInDate(String inDate) {
		this.inDate = inDate;
	}
	public String getOutDate() {
		return outDate;
	}
	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}
	public BigDecimal getSubsidies() {
		return subsidies;
	}
	public void setSubsidies(BigDecimal subsidies) {
		this.subsidies = subsidies;
	}
	public String getLeaveReason() {
		return leaveReason;
	}
	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	} 
	
	
}
