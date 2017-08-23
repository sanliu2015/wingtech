package com.ts.main.dorm.roomset;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.CommonBean;

@BeanProperty(description="相关设置")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Dorm_RoomSet")
public class RoomSet extends CommonBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(length = 5)
	private String endDay;
	
	@Column(precision=9,scale=2)
	private BigDecimal sharedFee = BigDecimal.ZERO;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public BigDecimal getSharedFee() {
		return sharedFee;
	}

	public void setSharedFee(BigDecimal sharedFee) {
		this.sharedFee = sharedFee;
	}

	
	
}
