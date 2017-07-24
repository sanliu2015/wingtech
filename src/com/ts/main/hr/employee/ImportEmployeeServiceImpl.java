package com.ts.main.hr.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ts.core.common.bean.BaseBean;
import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.report.excel.importdata.ImportExcelForm;
import com.ts.core.report.excel.support.HandleExcelPOIServiceImpl;
@Service("importEmployeeService")
public class ImportEmployeeServiceImpl implements IAppService{
	
	public String openUploadExcelFile(RequestContext requestContext, IBaseServiceManger service, ImportExcelForm form){
		form.setImportTemplateFileName("/resource/template/main/hr/employee/ImportEmployee.xlsx");
		form.setEndBy("导入人员信息.xlsx");
		//requestContext.getRequest().setAttribute("form", form);
		return "core/report/excel/UploadExcelFile";
	}
	public OperatePromptBean uploadExcelFile(RequestContext requestContext, IBaseServiceManger service, ImportExcelForm form){
		OperatePromptBean prompt=new OperatePromptBean();
		return prompt;
	}
	
	 
	private void setImportRule(List<BaseBean> ruleList,String excelCol,String name,String field){
		BaseBean bean=new BaseBean();
		bean.setNumber(excelCol);
		bean.setName(name);
		bean.setEnName(field);
		ruleList.add(bean);
	}
	private List<BaseBean> setImportRuleList(){
		List<BaseBean> ruleList=new ArrayList();
		setImportRule(ruleList,"A","顺序号","itemIndex");
		setImportRule(ruleList,"B","部门","deptName");
		setImportRule(ruleList,"C","联系人电话","phone"); 
		setImportRule(ruleList,"D","联系地点","address"); 
		setImportRule(ruleList,"E","业务员姓名","employName"); 
		setImportRule(ruleList,"F","受检者","examineName"); 
		setImportRule(ruleList,"G","性别","sex"); 
		setImportRule(ruleList,"H","年龄","age"); 
		setImportRule(ruleList,"I","产品名称","productName"); 
		setImportRule(ruleList,"J","项目","project");  
		setImportRule(ruleList,"K","结算价","settlePrice");  
		setImportRule(ruleList,"L","总金额","totalAmount");  
		setImportRule(ruleList,"M","备注","desc"); 
		setImportRule(ruleList,"N","发票抬头","invoiceHead");
		setImportRule(ruleList,"O","预约时间","appointDate"); 
		setImportRule(ruleList,"P","代理商","agentName"); 
		return ruleList;
	}
	public String openImportExcel(RequestContext requestContext, IBaseServiceManger service, ImportExcelForm form){
		  
		List colList=new ArrayList();
		for(int i=1;i<=52;i++){
			String charStr=HandleExcelPOIServiceImpl.IToCoordinateY(i);
			Map row=new HashMap();
			row.put("value", charStr);
			row.put("text", charStr); 
			colList.add(row);
		}
		requestContext.getRequest().setAttribute("charStrList", colList);
		List<BaseBean> ruleList=setImportRuleList();
		form.setMapRuleList(ruleList);
		form.setImportTemplateFileName("/resource/template/main/om/order/ImportOmOrder.xlsx");
		form.setEndBy("导入销售订单文件.xlsx");
		return "core/report/excel/ImportExcel";
	}
	 
}
