package com.ts.main.daily.meeting;

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

@Service("meetingReserveService")
public class MeetingReserveServiceImpl implements IAppService {

	public void add(RequestContext requestContext, IBaseServiceManger service, MeetingForm form) {
		
	}

	public OperatePromptBean save(RequestContext requestContext, IBaseServiceManger service, MeetingForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		MeetingReserve bean = form.getBean();
		String[] conveneDate = bean.getConveneDate().split(",");
		StringBuilder sql = new StringBuilder(300);
		sql.append("select 1 from Daily_MeetingReserve where conveneDate=? and meetingRoomId=? ");
		sql.append("and (beginTime between ? and ? or endedTime between ? and ?)");
		StringBuilder error = new StringBuilder();
		for (int i=0; i<conveneDate.length; i++) {
			List<Map<String, Object>> rsList = service.getDb().findForJdbc(sql.toString(), new Object[]{conveneDate[i],bean.getMeetingRoomId(),bean.getBeginTime(),bean.getEndedTime(),bean.getBeginTime(),bean.getEndedTime()});
			if (rsList != null && rsList.size() > 0) {
				error.append(",").append(conveneDate[i]);
			} else {
				MeetingReserve newBean = new MeetingReserve();
				BeanUtils.copyProperties(bean, newBean);
				newBean.setConveneDate(conveneDate[i]);
				service.getDb().saveObject(newBean, requestContext);
				sumbitEntryList(requestContext, service, form, newBean);
			}
		}
		
		if (error.length() > 0) {
			opb.setError(error.substring(1) + "时间段有预定信息，请重新选择其他时间！");
		}
		
		return opb;
	}

	private void sumbitEntryList(RequestContext requestContext, IBaseServiceManger service, MeetingForm form,
			MeetingReserve newBean) {
		if (form.getDtlList() == null) return;
		for (int i = 0; i < form.getDtlList().size(); i++) {
			MeetingParticipant dtl = form.getDtlList().get(i); 
			switch (Globals.recordOperateStatus.getStatus(dtl.getRecordOperateStatus())) {
			case update: 
				MeetingParticipant orignBean = service.getDb().getObject(MeetingParticipant.class, dtl.getId(), requestContext);
				BeanUtils.copyNoNullProperties(dtl, orignBean);
				service.getDb().updateObject(orignBean, requestContext);
				break;
			case delete:
				service.getDb().executeSqlForJdbc("delete from Daily_MeetingParticipant where id=?", new Object[]{dtl.getId()});
				break;
			default:
				dtl.setHdrId(newBean.getId());
				service.getDb().saveObject(dtl, requestContext);
			} 
		}
		
	}

	private void sumbitEntryList(RequestContext requestContext, IBaseServiceManger service, MeetingForm form) {
		if (form.getDtlList() == null) return;
		MeetingReserve bean = form.getBean();
		for (int i = 0; i < form.getDtlList().size(); i++) {
			MeetingParticipant dtl = form.getDtlList().get(i); 
			switch (Globals.recordOperateStatus.getStatus(dtl.getRecordOperateStatus())) {
			case update: 
				MeetingParticipant orignBean = service.getDb().getObject(MeetingParticipant.class, dtl.getId(), requestContext);
				BeanUtils.copyNoNullProperties(dtl, orignBean);
				service.getDb().updateObject(orignBean, requestContext);
				break;
			case delete:
				service.getDb().executeSqlForJdbc("delete from Daily_MeetingParticipant where id=?", new Object[]{dtl.getId()});
				break;
			default:
				dtl.setHdrId(bean.getId());
				service.getDb().saveObject(dtl, requestContext);
			} 
		}
	}
	
	
	public void edit(RequestContext requestContext, IBaseServiceManger service, MeetingForm form) {
		Map<String, Object> beanMap = service.getDb().findForJdbc(requestContext.getMessageResource().get("findObj").toString(), new Object[]{form.getId()}).get(0);
		MeetingReserve bean = JSON.parseObject(JSON.toJSONString(beanMap), MeetingReserve.class);
		form.setBean(bean); 
		List<MeetingParticipant> dtlList = service.getDb().queryHqlForList("select a from MeetingParticipant a where a.hdrId=" + form.getId());
		form.setDtlList(dtlList);
	}
	
	
	public OperatePromptBean update(RequestContext requestContext, IBaseServiceManger service, MeetingForm form) {
		OperatePromptBean opb = new OperatePromptBean();
		MeetingReserve bean = form.getBean(); 
		StringBuilder sql = new StringBuilder(300);
		sql.append("select 1 from Daily_MeetingReserve where conveneDate=? and meetingRoomId=? and id<>? ");
		sql.append("and (beginTime between ? and ? or endedTime between ? and ?)");
		List<Map<String, Object>> rsList = service.getDb().findForJdbc(sql.toString(), new Object[]{bean.getConveneDate(),bean.getMeetingRoomId(),bean.getId(),bean.getBeginTime(),bean.getEndedTime(),bean.getBeginTime(),bean.getEndedTime()});
		if (rsList != null && rsList.size() > 0) {
			requestContext.setAlertMsg(new StringBuffer("该会议室此时间段已经有预定信息，请重新选择时间点！"));
		} else {
			MeetingReserve orignBean = service.getDb().getObject(MeetingReserve.class, bean.getId(), requestContext);
			BeanUtils.copyNoNullProperties(bean, orignBean);
			service.getDb().updateObject(orignBean, requestContext);
			sumbitEntryList(requestContext, service, form);
			opb.setBillId(bean.getId());
		}
		
		return opb;
	}

	public OperatePromptBean delete(RequestContext requestContext, IBaseServiceManger service, MeetingForm form) {
		String[] selectParm = form.getSelectedRecordIds();
		String jsonStr = "[" + org.apache.commons.lang3.StringUtils.join(selectParm, ",") + "]";
		List<Map> selectParamList = JSON.parseArray(jsonStr, Map.class);
		for (Map tempMap : selectParamList) {
			service.getDb().executeSqlForJdbc("delete from Daily_MeetingReserve where id=?", new Object[]{tempMap.get("id")});
			service.getDb().executeSqlForJdbc("delete from Daily_MeetingParticipant where hdrId=?", new Object[]{tempMap.get("id")});
		}
		OperatePromptBean opb = new OperatePromptBean();
		return opb;
	}
}
