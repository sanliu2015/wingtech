package com.ts.main.sys.bulletin;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ts.core.common.bean.BaseFileBean;
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "SYS_BulletinFile")
public class BulletinFile  extends BaseFileBean  {
	@ManyToOne(cascade={CascadeType.PERSIST}  ) 
	@JoinColumn(name="bulletinId") 
	private Bulletin bulletin;

	public Bulletin getBulletin() {
		return bulletin;
	}

	public void setBulletin(Bulletin bulletin) {
		this.bulletin = bulletin;
	}

	 
	
	
}
