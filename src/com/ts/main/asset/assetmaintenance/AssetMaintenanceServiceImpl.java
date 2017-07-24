package com.ts.main.asset.assetmaintenance;
    
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ts.core.common.bean.OperatePromptBean; 
import com.ts.core.common.constant.Globals;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;

@Service("assetMaintenanceService")
public class AssetMaintenanceServiceImpl implements IAppService{	
	
	public void add(RequestContext requestContext, IBaseServiceManger service, AssetMaintenanceForm form) {
	}

	public OperatePromptBean save(RequestContext requestContext, IBaseServiceManger service, AssetMaintenanceForm form) {
		AssetMaintenance bean = form.getBean();
		service.getDb().saveObjectAndId(bean, requestContext);
		OperatePromptBean prompt = new OperatePromptBean();
		prompt.setId(bean.getId().toString());
		return prompt;
	}
	
	
	public void edit(RequestContext requestContext, IBaseServiceManger service, AssetMaintenanceForm form) {
		AssetMaintenance bean = service.getDb().getObject(AssetMaintenance.class, form.getId(), requestContext);
		form.setBean(bean);
	}
	
	public OperatePromptBean update(RequestContext requestContext, IBaseServiceManger service, AssetMaintenanceForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		AssetMaintenance bean = form.getBean(); 
		AssetMaintenance orignBean = service.getDb().getObject(AssetMaintenance.class, bean.getId(), requestContext);
		BeanUtils.copyNoNullProperties(bean, orignBean);
		service.getDb().updateObject(orignBean, requestContext);
		opb.setBillId(bean.getId());
		return opb;
	}

	public OperatePromptBean delete(RequestContext requestContext, IBaseServiceManger service, AssetMaintenanceForm form) {
		String[] selectParm = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectParm, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		for (Map tempMap : selectParamList) {
			service.getDb().executeSqlForJdbc("delete from Asset_maintenance where id=?", new Object[]{tempMap.get("id")});
		}
		OperatePromptBean opb = new OperatePromptBean();
		return opb;
	}

}
