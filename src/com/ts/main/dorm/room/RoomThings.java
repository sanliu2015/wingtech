package com.ts.main.dorm.room;

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
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Dorm_RoomThings")
public class RoomThings extends CommonBean {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column 
	private Integer roomId;	
	
	@Column
	private Integer thingsId;	

	@BeanProperty(description="物品个数")
	@Column 
    private Integer thingsQty;
	
	@BeanProperty(description="负责人")
	@Column 
	private Integer principalId;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getThingsId() {
		return thingsId;
	}

	public void setThingsId(Integer thingsId) {
		this.thingsId = thingsId;
	}

	public Integer getThingsQty() {
		return thingsQty;
	}

	public void setThingsQty(Integer thingsQty) {
		this.thingsQty = thingsQty;
	}

	public Integer getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(Integer principalId) {
		this.principalId = principalId;
	}

	
}
