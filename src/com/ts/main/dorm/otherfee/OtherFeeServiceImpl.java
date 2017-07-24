package com.ts.main.dorm.otherfee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.db.support.CallableParameter;
import com.ts.main.dorm.checkin.CheckInForm;
import com.ts.main.util.StringUtil;

@Service("otherFeeService")
public class OtherFeeServiceImpl implements IAppService {

	public void generateOtherFee(RequestContext requestContext, IBaseServiceManger service) {
		
	}
	
	public OperatePromptBean doGenerateOtherFee(RequestContext requestContext, IBaseServiceManger service) {
		String yearMonth = requestContext.getRequest().getParameter("yearMonth");
		String generateOtherFeeSp = "" + requestContext.getMessageResource().get("generateOtherFeeSp");
		List<CallableParameter> paraList = new ArrayList<CallableParameter>();
		CallableParameter param1 = new CallableParameter(); 
		param1.setParameterValue(yearMonth);
		param1.setSqlParameter(new SqlParameter("yearMonth", java.sql.Types.VARCHAR));
		paraList.add(param1);
		service.getDb().sp().call(generateOtherFeeSp, paraList);
		OperatePromptBean prompt = new OperatePromptBean();
		return prompt;
	}
	
	public OperatePromptBean delete(RequestContext requestContext , IBaseServiceManger service) { 
		String yearMonth = requestContext.getRequest().getParameter("yearMonth");
		String deleteOtherFeeSp = "" + requestContext.getMessageResource().get("deleteOtherFeeSp");
		List<CallableParameter> paraList = new ArrayList<CallableParameter>();
		CallableParameter param1 = new CallableParameter(); 
		param1.setParameterValue(yearMonth);
		param1.setSqlParameter(new SqlParameter("yearMonth", java.sql.Types.VARCHAR));
		paraList.add(param1);
		service.getDb().sp().call(deleteOtherFeeSp, paraList);
		OperatePromptBean prompt = new OperatePromptBean();
		return prompt;
	}
	
}
