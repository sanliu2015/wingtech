package com.ts.main.daily.meetingroom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.AppContext;
import com.ts.core.context.RequestContext;
import com.ts.core.db.support.CallableParameter;
import com.ts.main.util.StringUtil;

@Service("meetingRoomService")
public class MeetingRoomServiceImpl implements IAppService {

	private static String AM_BEGINTIME = "08:00";
	private static String AM_ENDEDTIME = "11:59";
	private static String PM_BEGINTIME = "12:00";
	private static String PM_ENDEDTIME = "17:59";
	private static String NT_BEGINTIME = "18:00";
	private static String NT_ENDEDTIME = "23:59";
	
	public void add(MeetingRoomForm form,RequestContext requestContext,IBaseServiceManger service) {
		
	}
	
	public OperatePromptBean save(MeetingRoomForm form,RequestContext requestContext,IBaseServiceManger service)  {
		OperatePromptBean prompt = new OperatePromptBean();
		MeetingRoom bean = form.getBean();
		service.getDb().saveObject(bean);
		return prompt;
	}
	
	public void edit(MeetingRoomForm form,RequestContext requestContext,IBaseServiceManger service) {
		MeetingRoom bean = service.getDb().getObject(MeetingRoom.class, form.getId(), requestContext);
		form.setBean(bean);
	}
	
	public OperatePromptBean update(MeetingRoomForm form,RequestContext requestContext,IBaseServiceManger service) {
		MeetingRoom bean = service.getDb().getObject(MeetingRoom.class, form.getBean().getId(), requestContext);
		OperatePromptBean prompt = new OperatePromptBean();
		BeanUtils.copyNoNullProperties(form.getBean(), bean);
		service.getDb().mergeObject(bean, requestContext);
		prompt.setId(bean.getId().toString()); 
		return prompt;
	}
	
	public OperatePromptBean delete(MeetingRoomForm form,RequestContext requestContext,IBaseServiceManger service) {
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
	
	public void show(MeetingRoomForm form,RequestContext requestContext,IBaseServiceManger service) {
		requestContext.getRequest().setAttribute("appUrl", "ShowMeetingRoom");
	}
	
	public String QueryMeetingRoom(RequestContext requestContext,IBaseServiceManger service) throws Exception {
		String firstDate = requestContext.getRequest().getParameter("firstDate");
		String flag = requestContext.getRequest().getParameter("flag");
		String capacity = requestContext.getRequest().getParameter("capacity");
		String name = requestContext.getRequest().getParameter("name");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtil.isNoValue(firstDate) || "thisWeek".equals(flag)) {
			Calendar cal =Calendar.getInstance();
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	        firstDate = df.format(cal.getTime());
		}
//		String amBeginTime = "08:00";
//		String amEndedTime = "11:59";
//		String pmBeginTime = "12:00";
//		String pmEndedTime = "17:59";
//		String ntBeginTime = "18:00";
//		String ntEndedTime = "23:59";
		
		List<String> dateList = new ArrayList<String>(7);
		Calendar c = Calendar.getInstance(); 
        Date date = df.parse(firstDate);
		if ("lastWeek".equals(flag)) {
			for (int i = 7; i > 0; i--) {
				c.setTime(df.parse(firstDate));
	            int day = c.get(Calendar.DATE);
	            c.set(Calendar.DATE, day - i);
	            dateList.add(df.format(c.getTime()));
	        }
		} else if ("nextWeek".equals(flag)){
			for (int i = 7; i < 14; i++) {
				c.setTime(df.parse(firstDate));
	            int day = c.get(Calendar.DATE);
	            c.set(Calendar.DATE, day + i);
	            dateList.add(df.format(c.getTime()));
	        }
		} else {
			for (int i = 0; i < 7; i++) {
				c.setTime(df.parse(firstDate));
	            int day = c.get(Calendar.DATE);
	            c.set(Calendar.DATE, day + i);
	            dateList.add(df.format(c.getTime()));
	        }
		}
		StringBuilder trStr = new StringBuilder(1024);
		String queryMeetingRoom = "select id,name from Daily_MeetingRoom where 1=1 ";
		if (!StringUtil.isNoValue(name)) {
			queryMeetingRoom += "and name like '%" + name +"%' ";
		}
		if (!StringUtil.isNoValue(capacity)) {
			queryMeetingRoom += "and capacity >=" + capacity;
		}
		List<Map<String, Object>> meetingRoomList = service.getDb().findForJdbc(queryMeetingRoom);
		if (meetingRoomList != null && meetingRoomList.size() > 0) {
			String sql = "select count(id) from Daily_MeetingReserve where meetingRoomId=? and conveneDate=? and beginTime between ? and ?";
			for (Map<String, Object> meetingRoom : meetingRoomList) {
				trStr.append("<tr id='rm").append(meetingRoom.get("id")).append("'>");
				trStr.append("<td>").append(meetingRoom.get("name")).append("</td>");
				for (String currentDate : dateList) {
					// 上午
					int amCount = service.getDb().getCountForJdbcParam(sql, new Object[]{meetingRoom.get("id"), currentDate, AM_BEGINTIME, AM_ENDEDTIME});
					if (amCount > 0) {
						trStr.append("<td align='center'>").append("<button onclick='viewDtl(this);' class='btn btn-warning btn-xs' type='button' ");
						trStr.append("name='am:").append(currentDate).append("'>").append(amCount).append("</button></td>");
					} else {
						trStr.append("<td>").append("</td>");
					}
					// 中午
					int pmCount = service.getDb().getCountForJdbcParam(sql, new Object[]{meetingRoom.get("id"), currentDate, PM_BEGINTIME, PM_ENDEDTIME});
					if (pmCount > 0) {
						trStr.append("<td align='center'>").append("<button onclick='viewDtl(this);' class='btn btn-warning btn-xs' type='button' ");
						trStr.append("name='pm:").append(currentDate).append("'>").append(pmCount).append("</button></td>");
					} else {
						trStr.append("<td>").append("</td>");
					}
					// 晚上
					int ntCount = service.getDb().getCountForJdbcParam(sql, new Object[]{meetingRoom.get("id"), currentDate, NT_BEGINTIME, NT_ENDEDTIME});
					if (ntCount > 0) {
						trStr.append("<td align='center'>").append("<button onclick='viewDtl(this);' class='btn btn-warning btn-xs' type='button' ");
						trStr.append("name='nt:").append(currentDate).append("'>").append(ntCount).append("</button></td>");
					} else {
						trStr.append("<td>").append("</td>");
					}
				}
				trStr.append("</tr>");
			}
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dateList", dateList);
		resultMap.put("trStr", trStr);
		requestContext.getResponse().getWriter().write(JSON.toJSONString(resultMap));
		requestContext.getResponse().getWriter().flush();
		requestContext.getResponse().getWriter().close();
		return null;
	}
	
	public void viewDtl(RequestContext requestContext,IBaseServiceManger service) {
		String roomId = requestContext.getRequest().getParameter("roomId");
		String dateParam = requestContext.getRequest().getParameter("dateParam");
		String conveneDate = dateParam.substring(3);
		String dayTime = dateParam.substring(0, 2);
		String beginTime = "";
		String endedTime = "";
		if ("am".equals(dayTime)) {
			beginTime = AM_BEGINTIME;
			endedTime = AM_ENDEDTIME;
		} else if ("pm".equals(dayTime)) {
			beginTime = PM_BEGINTIME;
			endedTime = PM_ENDEDTIME;
		} else {
			beginTime = NT_BEGINTIME;
			endedTime = NT_ENDEDTIME;
		}
		List<Map<String, Object>> dataList = service.getDb().findForJdbc("" + requestContext.getMessageResource().get("querySql"), new Object[] {conveneDate,roomId,beginTime,endedTime});
		
		requestContext.getRequest().setAttribute("dataList", dataList);
		
	}
	
}
