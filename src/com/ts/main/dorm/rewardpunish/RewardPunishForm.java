package com.ts.main.dorm.rewardpunish;

import java.util.ArrayList;
import java.util.List;

import com.ts.core.common.form.BaseInfoForm;

public class RewardPunishForm extends BaseInfoForm {

	private RewardPunish bean = new RewardPunish();
	private List<RewardPunishDtl> dtlList = new ArrayList<RewardPunishDtl>();
	
	public RewardPunish getBean() {
		return bean;
	}
	public void setBean(RewardPunish bean) {
		this.bean = bean;
	}
	public List<RewardPunishDtl> getDtlList() {
		return dtlList;
	}
	public void setDtlList(List<RewardPunishDtl> dtlList) {
		this.dtlList = dtlList;
	}
	
}
