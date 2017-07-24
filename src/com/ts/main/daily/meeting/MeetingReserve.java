package com.ts.main.daily.meeting;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.CommonBean;

@BeanProperty(description = "会议室预定")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Daily_MeetingReserve")
public class MeetingReserve extends CommonBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	private Integer meetingRoomId;
	
	@Column
	private String theme;
	
	@Column(length = 10)
	private String conveneDate;		// 召开日期
	
	@Column(length = 10)
	private String beginTime;		// 开始时间
	
	@Column(length = 10)
	private String endedTime;		// 结束时间
	
	@Column
	private Integer needProjector;	// 是否需要投影仪
	
	@Column
	private Integer joinSum;
	
	@Column
	private Integer reserveEmpId;
	
	@Transient
	private String reserveEmpName;
	
	public Integer getMeetingRoomId() {
		return meetingRoomId;
	}

	public void setMeetingRoomId(Integer meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getConveneDate() {
		return conveneDate;
	}

	public void setConveneDate(String conveneDate) {
		this.conveneDate = conveneDate;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndedTime() {
		return endedTime;
	}

	public void setEndedTime(String endedTime) {
		this.endedTime = endedTime;
	}

	public Integer getNeedProjector() {
		return needProjector;
	}

	public void setNeedProjector(Integer needProjector) {
		this.needProjector = needProjector;
	}

	public Integer getJoinSum() {
		return joinSum;
	}

	public void setJoinSum(Integer joinSum) {
		this.joinSum = joinSum;
	}

	public Integer getReserveEmpId() {
		return reserveEmpId;
	}

	public void setReserveEmpId(Integer reserveEmpId) {
		this.reserveEmpId = reserveEmpId;
	}

	public String getReserveEmpName() {
		return reserveEmpName;
	}

	public void setReserveEmpName(String reserveEmpName) {
		this.reserveEmpName = reserveEmpName;
	}
	
	
}
