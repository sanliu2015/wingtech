package com.ts.main.dorm.subsidies;

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
import com.ts.main.dorm.subsidies.Subsidies;
import com.ts.main.dorm.subsidies.SubsidiesForm;
import com.ts.main.util.StringUtil;

@Service("subsidiesService")
public class SubsidiesServiceImpl implements IAppService {

	public void add(SubsidiesForm form,RequestContext requestContext,IBaseServiceManger service) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("select distinct a.id,a.name,a.parentId pid from HR_Position a  ");
		sql.append("left join DORM_Subsidies b on a.id=b.positionId where b.id is null");
		List<Map<String, Object>> posList = service.getDb().findForJdbc(sql.toString());
		requestContext.getRequest().setAttribute("posList", JSON.toJSONString(posList));
	}
	
	public OperatePromptBean save(SubsidiesForm form,RequestContext requestContext,IBaseServiceManger service)  {
		synchronized (this) {
			StringBuilder sql = new StringBuilder(100);
			Subsidies bean = form.getBean();
			if (bean.getPositionId() != null && bean.getPositionId().intValue() > 0) {
				sql.append("select 1 from DORM_Subsidies where positionId=").append(bean.getPositionId());
			} else {
				sql.append("select 1 from DORM_Subsidies where employeeId=").append(bean.getEmployeeId());
			}
			
			OperatePromptBean prompt = new OperatePromptBean();
			
			List<Map<String, Object>> rsList = service.getDb().findForJdbc(sql.toString());
			if (rsList != null && rsList.size() > 0) {
				prompt.setError("系统已经存在此职务或此人的记录，不能重复新建!");
			} else {
				service.getDb().saveObject(bean, requestContext);
				prompt.setId(bean.getId().toString()); 
			}
			
			return prompt;
		}
		
	}
	
	public void edit(SubsidiesForm form,RequestContext requestContext , IBaseServiceManger service) {
		Map<String, Object> beanMap = service.getDb().findForJdbc(requestContext.getMessageResource().get("findObj").toString(), new Object[]{form.getId()}).get(0);
		Subsidies bean = JSON.parseObject(JSON.toJSONString(beanMap), Subsidies.class);
		form.setBean(bean); 
	}
	
	public OperatePromptBean update(SubsidiesForm form,RequestContext requestContext,IBaseServiceManger service) {
		 Subsidies bean = service.getDb().getObject(Subsidies.class, form.getBean().getId(), requestContext);
		 OperatePromptBean prompt = new OperatePromptBean();
		 BeanUtils.copyNoNullProperties(form.getBean(), bean);
		 service.getDb().mergeObject(bean, requestContext);
		 prompt.setId(bean.getId().toString()); 
		 return prompt;
	}
	
	public OperatePromptBean delete(SubsidiesForm form,RequestContext requestContext , IBaseServiceManger service) { 
		OperatePromptBean opb = new OperatePromptBean();
		String[] selectedRecordIds = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectedRecordIds, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		List idList = StringUtil.getListFromListMapByKey(selectParamList, "id");
		if (idList != null && idList.size() > 0) {
			service.getDb().executeSqlForJdbc("delete from Dorm_Subsidies where id in(" + org.apache.commons.lang3.StringUtils.join(idList,",") + ")");
		}
		return opb;	
	}
	
	public OperatePromptBean doEffective(SubsidiesForm form,RequestContext requestContext , IBaseServiceManger service) { 
		OperatePromptBean opb = new OperatePromptBean();
		String[] selectedRecordIds = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectedRecordIds, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		List idList = StringUtil.getListFromListMapByKey(selectParamList, "id");
		if (idList != null && idList.size() > 0) {
			String doEffectiveSp = "" + requestContext.getMessageResource().get("doEffectiveSp");
			// 删除
			if (!StringUtil.isNoValue(doEffectiveSp)) {
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
					service.getDb().sp().call(doEffectiveSp, paraList1);
				}
			}
			
		}
		return opb;	
	}
	
	
}
