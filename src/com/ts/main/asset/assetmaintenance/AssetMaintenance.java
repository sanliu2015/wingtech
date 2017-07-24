package com.ts.main.asset.assetmaintenance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseBean;

/**
 * @author yb
 *
 */
@BeanProperty(description="资产维护")
@Entity
@Table(name = "Asset_maintenance")
public class AssetMaintenance extends BaseBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@BeanProperty(description = "资产类型")
	@Column(length = 200)
	private String assetType ;
	
	
	@BeanProperty(description = "规格")
	@Column(length = 20)
	private String specifications;
	
	@BeanProperty(description = "单价")
	@Column(length = 20)
	private String price;

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

	
	
}
