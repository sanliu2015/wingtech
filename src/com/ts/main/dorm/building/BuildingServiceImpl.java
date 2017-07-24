package com.ts.main.dorm.building;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.Bean;
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
import com.ts.core.util.ConvertUtil;
import com.ts.core.util.StringUtils;
import com.ts.main.dorm.room.Room;
import com.ts.main.hr.employee.Employee;
import com.ts.main.util.StringUtil;

@Service("buildingService")
public class BuildingServiceImpl implements IAppService {

	public void add(RequestContext requestContext, IBaseServiceManger service, BuildingForm form) {
		Integer buildingId = form.getId();
		StringBuilder sql = new StringBuilder(100);
		sql.append("select id, name, kind+1 childKind, parentId from DORM_Building where kind<>'2' ");
		List<Map<String, Object>> parentList = service.getDb().findForJdbc(sql.toString());
		requestContext.getRequest().setAttribute("parentList", JSON.toJSONString(parentList));
		if (buildingId != null && buildingId.intValue() > 0) {
			Building bean = service.getDb().getObject(Building.class, buildingId, requestContext);
			form.getBean().setParentId(buildingId);
			form.getBean().setType(bean.getType());
			form.getBean().setHasGas(bean.getHasGas());
			form.getBean().setWaterFee(bean.getWaterFee());
			form.getBean().setPowerFee(bean.getPowerFee());
			form.getBean().setGasFee(bean.getGasFee());
			form.getBean().setCleanFee(bean.getCleanFee());
			form.getBean().setSharedFee(bean.getSharedFee());
		} else {
			form.getBean().setKind("0");
		}
	}

	public OperatePromptBean save(RequestContext requestContext, IBaseServiceManger service, BuildingForm form) {
		Building bean = form.getBean();
		service.getDb().saveObjectAndId(bean, requestContext);
		OperatePromptBean prompt = new OperatePromptBean();
		prompt.setId(bean.getId().toString());
		return prompt;
	}

	public void edit(RequestContext requestContext, IBaseServiceManger service, BuildingForm form) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("select id, name, kind+1 childKind, parentId from DORM_Building where kind<>'2' ");
		List<Map<String, Object>> parentList = service.getDb().findForJdbc(sql.toString());
		requestContext.getRequest().setAttribute("parentList", JSON.toJSONString(parentList));
		sql.setLength(0);
		sql.append("select a.*,b.name principalName from DORM_Building a left join HR_Employee b on a.principalId=b.id where a.id=?");
		List<Map<String, Object>> beanList = service.getDb().findForJdbc(sql.toString(), new Object[]{form.getId()});
		Building bean = JSON.parseObject(JSON.toJSONString(beanList.get(0)), Building.class);
		form.setBean(bean);
	}

	public OperatePromptBean update(RequestContext requestContext, IBaseServiceManger service, BuildingForm form) {
		Building bean = service.getDb().getObject(Building.class, form.getBean().getId(), requestContext);
		BeanUtils.copyNoNullProperties(form.getBean(), bean);
		service.getDb().updateObject(bean, requestContext);
		if (bean.getUpdateChildren().intValue() == 1) {
			String updateSp = "" + requestContext.getMessageResource().get("updateSp");
			List<CallableParameter> paraList = new ArrayList<CallableParameter>();
			CallableParameter param1 = new CallableParameter(); 
			param1.setParameterValue(bean.getId());
			param1.setSqlParameter(new SqlParameter("parentId", java.sql.Types.INTEGER));
			paraList.add(param1);
			CallableParameter param2 = new CallableParameter();
			param2.setParameterValue("");
			param2.setSqlParameter(new SqlOutParameter("retMsg", Types.VARCHAR)); 
			paraList.add(param2);
			service.getDb().sp().call(updateSp, paraList);
		}
		OperatePromptBean prompt = new OperatePromptBean();
		prompt.setId(form.getBean().getId().toString());
		return prompt;
	}

	public OperatePromptBean delete(RequestContext requestContext, IBaseServiceManger service, BuildingForm form) {
		String deleteSp = "" + requestContext.getMessageResource().get("deleteSp");
		List<CallableParameter> paraList1 = new ArrayList<CallableParameter>();
		CallableParameter param1 = new CallableParameter(); 
		param1.setParameterValue(form.getId());
		param1.setSqlParameter(new SqlParameter("id", java.sql.Types.INTEGER));
		paraList1.add(param1);
		service.getDb().sp().call(deleteSp, paraList1);
		OperatePromptBean prompt = new OperatePromptBean();
		prompt.setStatememt("删除成功!");
		return prompt;
	}

}
