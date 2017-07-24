package com.ts.main.excel.importplan;

import java.util.ArrayList;
import java.util.List;

import com.ts.core.common.form.BaseInfoForm;

public class ImportPlanForm extends BaseInfoForm {

	private ImportPlan bean = new ImportPlan();
	private List<ImportPlanDtl> dtlList = new ArrayList<ImportPlanDtl>();
	
	public ImportPlan getBean() {
		return bean;
	}
	public void setBean(ImportPlan bean) {
		this.bean = bean;
	}
	public List<ImportPlanDtl> getDtlList() {
		return dtlList;
	}
	public void setDtlList(List<ImportPlanDtl> dtlList) {
		this.dtlList = dtlList;
	}
	
}
