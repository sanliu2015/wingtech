package com.ts.main.daily.calendar;
 
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table; 

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;  

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseBean;   
import com.ts.core.common.bean.BaseFileBean;
import com.ts.core.report.bean.BaseFieldBean;
import com.ts.main.hr.employee.Employee;


@BeanProperty(description="周工作日志")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "DAILY_CalendarLog")
public class CalendarLog  extends BaseFileBean{
	@ManyToOne(cascade={CascadeType.PERSIST}  ) 
	@JoinColumn(name="employeeId") 
	private Employee employee=new Employee();
	@BeanProperty(description="计划记录id")
    @Column
	private Integer planId;
	@BeanProperty(description="计划人所在部门id")
    @Column
	private Integer deptId;
	@BeanProperty(description="完成日期")
    @Column(length = 20) 
	private String summaryDate; 
	@BeanProperty(description="星期几")
    @Column(length = 20) 
	private String summaryWeek; 
    
	@BeanProperty(description="事件/标题")
    @Column(length = 100) 
	private String summaryEvent;	 
	@BeanProperty(description="地点")
    @Column(length = 100) 
	private String place;	
	@BeanProperty(description="am/pm")
    @Column(length = 10) 
	private String dayType;		 
	@BeanProperty(description="计划拜访客户")
    @Column(length = 150) 
	private String custName;	
	@BeanProperty(description="接洽人")
    @Column(length = 50) 
	private String contactPerson;
	@BeanProperty(description="部门")
    @Column(length = 50) 
	private String contactDept;
	@BeanProperty(description="联系电话")
    @Column(length = 50) 
	private String contactPhone;
	@BeanProperty(description="目标")
    @Column(length = 200) 
	private String purpose;
	@BeanProperty(description="出差报销单编号")
    @Column(length = 20) 
	private String travelFeeNo;
	@BeanProperty(description="出差报销单id")
    @Column
	private Integer travelFeeId;
	
	@BeanProperty(description="第几周")
    @Column
	private Integer weekIndex;
	
	@BeanProperty(description="客户反馈")
    @Column(length = 500) 
	private String feedback;	 
	@BeanProperty(description="客户满意度代码")
    @Column(length = 10) 
	private String satisfactionId;	// 客户满意度ID  
	@BeanProperty(description=" 感兴趣产品")
    @Column(length = 150) 
	private String likeProduct;	 
	 
	 
	
	public String getTravelFeeNo() {
		return travelFeeNo;
	}

	public void setTravelFeeNo(String travelFeeNo) {
		this.travelFeeNo = travelFeeNo;
	}

	public Integer getTravelFeeId() {
		return travelFeeId;
	}

	public void setTravelFeeId(Integer travelFeeId) {
		this.travelFeeId = travelFeeId;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactDept() {
		return contactDept;
	}

	public void setContactDept(String contactDept) {
		this.contactDept = contactDept;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
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

	 
   
	public String getSummaryDate() {
		return summaryDate;
	}

	public void setSummaryDate(String summaryDate) {
		this.summaryDate = summaryDate;
	}

	public String getSummaryWeek() {
		return summaryWeek;
	}

	public void setSummaryWeek(String summaryWeek) {
		this.summaryWeek = summaryWeek;
	}

	public String getSummaryEvent() {
		return summaryEvent;
	}

	public void setSummaryEvent(String summaryEvent) {
		this.summaryEvent = summaryEvent;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDayType() {
		return dayType;
	}

	public void setDayType(String dayType) {
		this.dayType = dayType;
	}

	public Integer getWeekIndex() {
		return weekIndex;
	}

	public void setWeekIndex(Integer weekIndex) {
		this.weekIndex = weekIndex;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getSatisfactionId() {
		return satisfactionId;
	}

	public void setSatisfactionId(String satisfactionId) {
		this.satisfactionId = satisfactionId;
	}

	public String getLikeProduct() {
		return likeProduct;
	}

	public void setLikeProduct(String likeProduct) {
		this.likeProduct = likeProduct;
	}

	 
	
}
