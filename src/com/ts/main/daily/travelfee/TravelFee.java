package com.ts.main.daily.travelfee;
 
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
import com.ts.core.common.bean.BaseBean;  
import com.ts.main.hr.employee.Employee;


@BeanProperty(description="差旅费用主表")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "DAILY_TravelFee")
public class TravelFee  extends BaseBean{
	@BeanProperty(description="申请日期")
    @Column(length = 20) 
	private String applyDate;
	@ManyToOne(cascade={CascadeType.PERSIST}) 
	@JoinColumn(name="employeeId") 
	private Employee employee=new Employee(); 
	
	@BeanProperty(description="申请对象类别")
    @Column(length = 10) 
	private String requestObjectKind; 
	
	@BeanProperty(description="支付方式")
    @Column(length = 10) 
	private String paymentMode; 
	
	@BeanProperty(description="要求付款日期")
    @Column(length = 20) 
	private String requiredPayDate; 
	
	@BeanProperty(description="凭证号")
    @Column(length = 30) 
	private String vourchNo; 
	
	@BeanProperty(description="部门id")
    @Column
	private Integer deptId;			 
	@BeanProperty(description="公司id")
    @Column
	private Integer orgId;	
	@BeanProperty(description="关联行程日志id")
    @Column
	private Integer calendarLogId;
	@BeanProperty(description="预支金额")
	@Column(precision=18,scale=2 ) 
	private BigDecimal advanceAmount;
	
	@BeanProperty(description="申请金额")
	@Column(precision=18,scale=2 ) 
	private BigDecimal applyAmount;	 
	
	
	
	
	@Transient
	private String deptName;		// 部门名称
	@Transient
	private String employeeName;
	@Transient
	private String calendarLogEvent;
	
	
	public String getVourchNo() {
		return vourchNo;
	}
	public void setVourchNo(String vourchNo) {
		this.vourchNo = vourchNo;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getRequestObjectKind() {
		return requestObjectKind;
	}
	public void setRequestObjectKind(String requestObjectKind) {
		this.requestObjectKind = requestObjectKind;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getRequiredPayDate() {
		return requiredPayDate;
	}
	public void setRequiredPayDate(String requiredPayDate) {
		this.requiredPayDate = requiredPayDate;
	}
	public BigDecimal getAdvanceAmount() {
		return advanceAmount;
	}
	public void setAdvanceAmount(BigDecimal advanceAmount) {
		this.advanceAmount = advanceAmount;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getCalendarLogEvent() {
		return calendarLogEvent;
	}
	public void setCalendarLogEvent(String calendarLogEvent) {
		this.calendarLogEvent = calendarLogEvent;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public Integer getCalendarLogId() {
		return calendarLogId;
	}
	public void setCalendarLogId(Integer calendarLogId) {
		this.calendarLogId = calendarLogId;
	}
	public BigDecimal getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	
}
