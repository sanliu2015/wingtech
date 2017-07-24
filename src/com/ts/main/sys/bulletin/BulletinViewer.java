package com.ts.main.sys.bulletin;

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

@BeanProperty(description="新闻公告查阅者")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "SYS_BulletinViewer")
public class BulletinViewer  extends BaseBean  {
	
	
	@ManyToOne(cascade={CascadeType.PERSIST}  ) 
	@JoinColumn(name="bulletinId") 
	private Bulletin bulletin; 
	@BeanProperty(description="接收者ID")
    @Column 
	private Integer viewerId;
	public Bulletin getBulletin() {
		return bulletin;
	}
	public void setBulletin(Bulletin bulletin) {
		this.bulletin = bulletin;
	}
	public Integer getViewerId() {
		return viewerId;
	}
	public void setViewerId(Integer viewerId) {
		this.viewerId = viewerId;
	} 
	  
	
	
	
}
