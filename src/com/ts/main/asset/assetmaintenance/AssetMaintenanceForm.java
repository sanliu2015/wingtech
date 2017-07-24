package com.ts.main.asset.assetmaintenance;

import java.util.ArrayList;
import java.util.List;

import com.ts.core.common.form.BaseInfoForm;

public class AssetMaintenanceForm extends BaseInfoForm {
	
	private AssetMaintenance bean = new AssetMaintenance(); 
	
	public AssetMaintenance getBean() {
		return bean;
	}
	public void setBean(AssetMaintenance bean) {
		this.bean = bean;
	}
	

}
