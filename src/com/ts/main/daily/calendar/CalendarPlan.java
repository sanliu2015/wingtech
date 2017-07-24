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
import com.ts.main.hr.company.Company;
import com.ts.main.hr.employee.Employee;


@BeanProperty(description="周每日计划")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "DAILY_CalendarPlan")
public class CalendarPlan  extends BaseBean{
	@ManyToOne(cascade={CascadeType.PERSIST}  ) 
	@JoinColumn(name="employeeId") 
	private Employee employee=new Employee();
	
	@BeanProperty(description="计划人所在部门id")
    @Column
	private Integer deptId;
	@BeanProperty(description="计划日期")
    @Column(length = 20) 
	private String planDate; 
	@BeanProperty(description="计划星期几")
    @Column(length = 20) 
	private String planWeek; 
    
	@BeanProperty(description="计划事件/标题")
    @Column(length = 100) 
	private String eventExplain;	 
	
	@BeanProperty(description="计划地点")
    @Column(length = 100) 
	private String place;	
	
	@BeanProperty(description="计划拜访客户")
    @Column(length = 150) 
	private String custName;		
	
	@BeanProperty(description="am/pm")
    @Column(length = 10) 
	private String dayType;		 
	
	@BeanProperty(description="第几周")
    @Column
	private Integer weekIndex;
	@BeanProperty(description="关联工作日志id")
    @Column
	private Integer logId;
	
	
	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Employee getEmployee() {
		return employee;
	}
	
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
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

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	public String getPlanWeek() {
		return planWeek;
	}

	public void setPlanWeek(String planWeek) {
		this.planWeek = planWeek;
	}

	public String getEventExplain() {
		return eventExplain;
	}

	public void setEventExplain(String eventExplain) {
		this.eventExplain = eventExplain;
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
	
	
}
