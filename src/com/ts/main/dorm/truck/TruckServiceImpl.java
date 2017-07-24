package com.ts.main.dorm.truck;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.db.support.CallableParameter;
import com.ts.main.dorm.truck.Truck;
import com.ts.main.dorm.truck.TruckForm;
import com.ts.main.util.StringUtil;

@Service("truckService")
public class TruckServiceImpl implements IAppService {

	public void add(RequestContext requestContext, IBaseServiceManger service, TruckForm form) {
	}

	public OperatePromptBean save(RequestContext requestContext, IBaseServiceManger service, TruckForm form) {
		Truck bean = form.getBean();
		service.getDb().saveObject(bean, requestContext);
		OperatePromptBean prompt = new OperatePromptBean();
		prompt.setId(bean.getId().toString());
		return prompt;
	}

	
	public void edit(RequestContext requestContext, IBaseServiceManger service, TruckForm form) {
		List<Map<String, Object>> doorList = service.getDb().findForJdbc("select code,name from ts_syscode where codekind=320");
		requestContext.getRequest().setAttribute("doorList", doorList);
		String queryObj = "" + requestContext.getMessageResource().get("queryObj");
		List<Map<String, Object>> beanList = service.getDb().findForJdbc(queryObj, new Object[]{form.getId()});
		Truck bean = JSON.parseObject(JSON.toJSONString(beanList.get(0)), Truck.class);
		form.setBean(bean);
	}
	
	
	public OperatePromptBean update(RequestContext requestContext, IBaseServiceManger service, TruckForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		Truck bean = form.getBean(); 
		Truck orignBean = service.getDb().getObject(Truck.class, bean.getId(), requestContext);
		if ("1".equals(orignBean.getStatus())) {
			opb.setError("已经完成，不能修改!"); 
		} else {
			BeanUtils.copyNoNullProperties(bean, orignBean);
			service.getDb().updateObject(orignBean, requestContext);
			opb.setBillId(bean.getId());
		}
		return opb;
	}

	public OperatePromptBean delete(RequestContext requestContext, IBaseServiceManger service, TruckForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		String[] selectedRecordIds = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectedRecordIds, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		List idList = StringUtil.getListFromListMapByKey(selectParamList, "id");
		if (idList != null && idList.size() > 0) {
			String deleteSp = "" + requestContext.getMessageResource().get("deleteSp");
			// 删除
			if (!StringUtil.isNoValue(deleteSp)) {
				for (int i=0,len=idList.size(); i<len; i++) {
					List<CallableParameter> paraList1 = new ArrayList<CallableParameter>();
					CallableParameter param1 = new CallableParameter(); 
					param1.setParameterValue(idList.get(i));
					param1.setSqlParameter(new SqlParameter("id", java.sql.Types.INTEGER));
					paraList1.add(param1);
					CallableParameter param2 = new CallableParameter();
					param2.setParameterValue("");
					param2.setSqlParameter(new SqlOutParameter("retMsg", Types.VARCHAR)); 
					paraList1.add(param2);
					Map<String,Object> spValue1 = service.getDb().sp().call(deleteSp, paraList1);
				}
			}
			
		}
		return opb;
	}
	
	
	public void leave(RequestContext requestContext, IBaseServiceManger service, TruckForm form) {
		String queryObj = "" + requestContext.getMessageResource().get("queryObj");
		List<Map<String, Object>> beanList = service.getDb().findForJdbc(queryObj, new Object[]{form.getId()});
		Truck bean = JSON.parseObject(JSON.toJSONString(beanList.get(0)), Truck.class);
		form.setBean(bean);
	}
	
	public OperatePromptBean doLeave(RequestContext requestContext, IBaseServiceManger service, TruckForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		Truck bean = form.getBean(); 
		Truck orignBean = service.getDb().getObject(Truck.class, bean.getId(), requestContext);
		if ("1".equals(orignBean.getStatus())) {
			opb.setError("已经离开登记过，不能重复操作!"); 
		} else {
			orignBean.setStatus("1");
			orignBean.setLeaveDate(bean.getLeaveDate());
			orignBean.setLeaveTime(bean.getLeaveTime());
			orignBean.setLeaveBy(requestContext.getUser().getEmployeeName());
			orignBean.setLeaveById(requestContext.getUser().getEmployeeId());
			orignBean.setLeaveUserId(requestContext.getUser().getId());
			service.getDb().updateObject(orignBean, requestContext);
			opb.setBillId(bean.getId());
		}
		return opb;
	}
}
