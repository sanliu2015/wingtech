package com.ts.main.dorm.room;
    
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
import com.ts.core.common.constant.Globals;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.db.support.CallableParameter;
import com.ts.main.util.StringUtil;

@Service("roomService")
public class RoomServiceImpl implements IAppService{	
	
	public void add(RequestContext requestContext, IBaseServiceManger service, RoomForm form) {
	}

	public OperatePromptBean save(RequestContext requestContext, IBaseServiceManger service, RoomForm form) {
		Room bean = form.getBean();
		service.getDb().saveObject(bean, requestContext);
		OperatePromptBean prompt = new OperatePromptBean();
		prompt.setId(bean.getId().toString());
		return prompt;
	}

	private void sumbitEntryList(RequestContext requestContext, IBaseServiceManger service, RoomForm form) {
		if (form.getDtlList() == null) return;
		Room bean = form.getBean();
		for (int i = 0; i < form.getDtlList().size(); i++) {
			RoomThings dtl = form.getDtlList().get(i); 
			switch (Globals.recordOperateStatus.getStatus(dtl.getRecordOperateStatus())) {
			case update: 
				RoomThings orignBean = service.getDb().getObject(RoomThings.class, dtl.getId(), requestContext);
				BeanUtils.copyNoNullProperties(dtl, orignBean);
				service.getDb().updateObject(orignBean, requestContext);
				break;
			case delete:
				service.getDb().executeSqlForJdbc("delete from DORM_RoomThings where id=?", new Object[]{dtl.getId()});
				break;
			default:
				dtl.setRoomId(bean.getId());
				service.getDb().saveObject(dtl, requestContext);
			} 
		}
	}
	
	
	public void edit(RequestContext requestContext, IBaseServiceManger service, RoomForm form) {
		Room bean = service.getDb().getObject(Room.class, form.getId(), requestContext);
		form.setBean(bean);
		String querySql = "" + requestContext.getMessageResource().get("querySql");
		List<Map<String,Object>> dtlList = service.getDb().findForJdbc(querySql, new Object[]{bean.getId()});
		requestContext.getRequest().setAttribute("dtlList", JSON.toJSONString(dtlList));
	}
	
	
	public OperatePromptBean update(RequestContext requestContext, IBaseServiceManger service, RoomForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		Room bean = form.getBean(); 
		Room orignBean = service.getDb().getObject(Room.class, bean.getId(), requestContext);
		BeanUtils.copyNoNullProperties(bean, orignBean);
		if ("".equals(orignBean.getType())) {		// 防止房价属性影响
			orignBean.setType(null);
		}
		service.getDb().updateObject(orignBean, requestContext);
		sumbitEntryList(requestContext, service, form);
		opb.setBillId(bean.getId());
		return opb;
	}

	public OperatePromptBean delete(RequestContext requestContext, IBaseServiceManger service, RoomForm form) {
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
					List<CallableParameter> paraList = new ArrayList<CallableParameter>();
					CallableParameter param = new CallableParameter(); 
					param.setParameterValue(idList.get(i));
					param.setSqlParameter(new SqlParameter("id", java.sql.Types.INTEGER));
					paraList.add(param);
					service.getDb().sp().call(deleteSp, paraList);
				}
			}
			
		}
		return opb;	
	}
	
	
	public void openClose(RequestContext requestContext, IBaseServiceManger service, RoomForm form) {
		Room bean = service.getDb().getObject(Room.class, form.getId(), requestContext);
		form.setBean(bean);
	}
	
	public OperatePromptBean doOpenClose(RequestContext requestContext, IBaseServiceManger service, RoomForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		Room bean = form.getBean();
		String openCloseSp = "" + requestContext.getMessageResource().get("openCloseSp");;
		List<CallableParameter> paraList = new ArrayList<CallableParameter>();
		CallableParameter param1 = new CallableParameter(); 
		param1.setParameterValue(bean.getId());
		param1.setSqlParameter(new SqlParameter("id", java.sql.Types.INTEGER));
		paraList.add(param1);
		CallableParameter param2 = new CallableParameter(); 
		param2.setParameterValue(bean.getDisabled());
		param2.setSqlParameter(new SqlParameter("disabled", java.sql.Types.VARCHAR));
		paraList.add(param2);
		service.getDb().sp().call(openCloseSp, paraList);
		opb.setId(""+bean.getId());
		return opb;	
	}

}
