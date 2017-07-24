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
import com.ts.main.hr.employee.Employee;


@BeanProperty(description="周总结或周计划")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "DAILY_CalendarSummary")
public class CalendarSummary  extends BaseFileBean{
	@ManyToOne(cascade={CascadeType.PERSIST}) 
	@JoinColumn(name="employeeId") 
	private Employee employee=new Employee(); 
	@BeanProperty(description="计划人所在部门id")
    @Column
	private Integer deptId; 
	@BeanProperty(description="开始日期")
    @Column(length = 20)  
	private String startDate;
	@BeanProperty(description="结束日期")
    @Column(length = 20)  
	private String endDate;
	@BeanProperty(description="计划事件/标题")
    @Column(length = 100) 
	private String eventExplain;
	
	@BeanProperty(description="第几周")
    @Column
	private Integer weekIndex;
	
	@BeanProperty(description="月份")
    @Column(length = 2) 
	private String monthNo;
	@BeanProperty(description="周总结或周计划")
    @Column(length = 10) 
	private String eventType;//plan,summary
	@BeanProperty(description="周总结或月总结")
    @Column(length = 10) 
	private String dateRangeType;//week、month
	@BeanProperty(description="哪一年")
    @Column(length = 5) 
	private String yearNo;
	
	@BeanProperty(description="重点拜访客户")
    @Column(length = 200) 
	private String visitCustNames; 
	
	@BeanProperty(description="新开发客户")
    @Column(length = 200) 
	private String developCustNames;
	 
	
	public String getVisitCustNames() {
		return visitCustNames;
	}

	public void setVisitCustNames(String visitCustNames) {
		this.visitCustNames = visitCustNames;
	}

	public String getDevelopCustNames() {
		return developCustNames;
	}

	public void setDevelopCustNames(String developCustNames) {
		this.developCustNames = developCustNames;
	}

	public String getYearNo() {
		return yearNo;
	}

	public void setYearNo(String yearNo) {
		this.yearNo = yearNo;
	}

	public String getDateRangeType() {
		return dateRangeType;
	}

	public void setDateRangeType(String dateRangeType) {
		this.dateRangeType = dateRangeType;
	}

	public String getMonthNo() {
		return monthNo;
	}

	public void setMonthNo(String monthNo) {
		this.monthNo = monthNo;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEventExplain() {
		return eventExplain;
	}

	public void setEventExplain(String eventExplain) {
		this.eventExplain = eventExplain;
	}

	public Integer getWeekIndex() {
		return weekIndex;
	}

	public void setWeekIndex(Integer weekIndex) {
		this.weekIndex = weekIndex;
	}
	
	  
	
	
}
