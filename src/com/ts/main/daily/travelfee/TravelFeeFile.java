package com.ts.main.daily.travelfee;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseFileBean;

@BeanProperty(description="差旅费附件")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "DAILY_TravelFeeFile")
public class TravelFeeFile  extends BaseFileBean  {
	@ManyToOne(cascade={CascadeType.PERSIST}  ) 
	@JoinColumn(name="hdrId") 
	private TravelFee hdr;

	public TravelFee getHdr() {
		return hdr;
	}

	public void setHdr(TravelFee hdr) {
		this.hdr = hdr;
	}

	 
	
	
}
