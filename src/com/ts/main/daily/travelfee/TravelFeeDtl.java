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
@BeanProperty(description="差旅费明细")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "DAILY_TravelFeeDtl")
public class TravelFeeDtl extends BaseBean {
	@ManyToOne(cascade={CascadeType.PERSIST}  ) 
	@JoinColumn(name="hdrId") 
	private TravelFee hdr;
	@BeanProperty(description="费用大别")
    @Column 
	private Integer feeKindParentId;
	@Transient
	private String feeKindParentName;	 
	@BeanProperty(description="费用类别")
    @Column 
	private Integer feeKindId;
	@Transient
	private String feeKindName;	 
	
	@BeanProperty(description="金额")
	@Column(precision=18,scale=2 ) 
	private BigDecimal amount;
	@BeanProperty(description="时间")
	@Column( length = 50) 
	private String eventTime; 
	@BeanProperty(description="地点")
	@Column( length = 150) 
	private String place;
	
	
	
	public String getFeeKindParentName() {
		return feeKindParentName;
	}
	public void setFeeKindParentName(String feeKindParentName) {
		this.feeKindParentName = feeKindParentName;
	}
	public String getFeeKindName() {
		return feeKindName;
	}
	public void setFeeKindName(String feeKindName) {
		this.feeKindName = feeKindName;
	}
	public Integer getFeeKindParentId() {
		return feeKindParentId;
	}
	public void setFeeKindParentId(Integer feeKindParentId) {
		this.feeKindParentId = feeKindParentId;
	}
	public TravelFee getHdr() {
		return hdr;
	}
	public void setHdr(TravelFee hdr) {
		this.hdr = hdr;
	}
	public Integer getFeeKindId() {
		return feeKindId;
	}
	public void setFeeKindId(Integer feeKindId) {
		this.feeKindId = feeKindId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}		 
	  
	
}
