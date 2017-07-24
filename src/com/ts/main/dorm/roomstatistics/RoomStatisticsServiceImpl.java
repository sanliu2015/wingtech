package com.ts.main.dorm.roomstatistics;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;

@Service("roomStatisticsService")
public class RoomStatisticsServiceImpl implements IAppService {

	public void show(RequestContext requestContext, IBaseServiceManger service) {
		StringBuilder sql = new StringBuilder(100);
		sql.append("select a.id,a.parentId as pId,a.name,a.number,a.kind buildingKind ");
		sql.append("from DORM_Building a ");
		List<Map> treeDataList = service.getDb().queryForList(sql.toString(), requestContext);
		requestContext.getRequest().setAttribute("treeData", JSON.toJSONString(treeDataList));
	}
	
	// 房间信息预览
	public String showRoom(RequestContext requestContext, IBaseServiceManger service) throws Exception {
		StringBuilder sql = new StringBuilder(100);
		sql.append("select a.* from VM_RoomBasicInfo a ");
		String id = requestContext.getRequest().getParameter("id");
		String buildingKind = requestContext.getRequest().getParameter("buildingKind");
		if ("2".equals(buildingKind)) {	 		// 选中的是楼层节点
			sql.append("where a.floorId=");
		} else if ("1".equals(buildingKind)) {	// 选中的是单元节点
			sql.append("where a.unitId=");
		} else if ("0".equals(buildingKind)) {	// 选中的是楼栋节点
			sql.append("where a.buildingId=");
		}
		sql.append(id);
		sql.append(" order by a.buildingId,a.unitId,a.roomNumber");
		List<Map> roomList = service.getDb().queryForList(sql.toString(), requestContext);
		int totalRoom = 0;		// 房间总数
		int emptyRoom = 0;		// 空房总数
		int totalOutSiders = 0; // 外来入住人数
		int totalEmployees = 0; // 内部员工人数
		StringBuilder outHtmlStr = new StringBuilder(1000);
		StringBuilder contentDiv = new StringBuilder(500);
		if (roomList != null && roomList.size() > 0) {
			totalRoom = roomList.size();
			StringBuilder querySql = new StringBuilder(100);
			for (int i=0,len=roomList.size(); i<len; i++) {
				StringBuilder tipStr = new StringBuilder(100);
				StringBuilder peopleName = new StringBuilder(100);
				tipStr.append("房间编号：<strong>");
				tipStr.append(roomList.get(i).get("roomNumber"));
				tipStr.append("</strong><br>");
				if ("2".equals(buildingKind)) {	 		// 选中的是楼层节点
				} else if ("1".equals(buildingKind)) {	// 选中的是单元节点
					tipStr.append("所在楼层：<strong>");
					tipStr.append(roomList.get(i).get("floorName"));
					tipStr.append("</strong><br>");
				} else if ("0".equals(buildingKind)) {	// 选中的是楼栋节点
					tipStr.append("所在楼层：<strong>");
					tipStr.append(roomList.get(i).get("floorName"));
					tipStr.append("</strong><br>");
					tipStr.append("所在单元：<strong>");
					tipStr.append(roomList.get(i).get("unitName"));
					tipStr.append("</strong><br>");
				}
				querySql.append("select a.inDate,b.name,b.number,c.name as deptName from DORM_CheckIn a ");
				querySql.append("inner join HR_Employee b on a.employeeId=b.id ");
				querySql.append("inner join HR_Department c on b.deptId=c.id where isnull(a.checkOutFlag,0)=0 and a.roomId=");
				querySql.append(roomList.get(i).get("id"));
				List<Map> employeeList = service.getDb().queryForList(querySql.toString(), requestContext);
				totalEmployees += employeeList.size();
				querySql.setLength(0);
//				// 外部人员
//				querySql.append("select a.inTime,b.outsidersName+'(外)' as name,b.number,b.companyName as deptName from DORM_CheckIn a ");
//				querySql.append("inner join HR_Outsiders b on a.outsidersId=b.id ");
//				querySql.append("where isnull(a.checkOutFlag,0)=0 and a.roomId=");
//				querySql.append(roomList.get(i).get("id"));
//				List<Map> outSiderList = service.getDb().queryForList(querySql.toString(), requestContext);
//				totalOutSiders += outSiderList.size();
//				querySql.setLength(0);
//				employeeList.addAll(outSiderList);
				tipStr.append("入住人数：<strong>");
				tipStr.append(employeeList.size());
				tipStr.append("</strong><br>");
				if (employeeList != null && employeeList.size() > 0) {
					tipStr.append("---------------入住信息----------------<br>");
					for (Map peopleMap : employeeList) {
						tipStr.append("<strong>");
						tipStr.append(peopleMap.get("inDate"));
						tipStr.append("&nbsp;&nbsp;");
						tipStr.append(peopleMap.get("name"));
						tipStr.append("&nbsp;&nbsp;");
						tipStr.append(peopleMap.get("number"));
						tipStr.append("&nbsp;&nbsp;");
						tipStr.append(peopleMap.get("deptName"));
						tipStr.append("&nbsp;&nbsp;");
						tipStr.append("</strong><br>");
						peopleName.append("|");
						peopleName.append(peopleMap.get("name"));
					}
				} else {
					emptyRoom += 1;
				}
				
			    if (i % 7 == 0) {
					contentDiv.append("<div style=\"padding:1px;\"><table><tbody><tr><td style=\"width:40px;\">第");
					contentDiv.append(i/7 + 1);
					contentDiv.append("行</td>");
				}
			    
			    
			    
			    // 房间信息标签拼接
			    contentDiv.append("<td class=\"tooltipTD\" align=\"center\" title=\"");
			    contentDiv.append(tipStr);
			    contentDiv.append("\" >");
			    if (employeeList == null || employeeList.size() == 0) {    // 空房间
			    	contentDiv.append("<div class=\"rmstatus\" style=\"width:120px; height:68px; background-color:#EEE9E9; padding:1px;\">");
				} else {
					contentDiv.append("<div class=\"rmstatus\" style=\"width:120px; height:68px; background-color:#BFEFFF; padding:1px;\">");
				}
			    contentDiv.append("<table width=\"118px\"><tbody>");
			    
			    // 房间号
			    contentDiv.append("<tr><td style=\"height:17px;\" colspan=\"2\" align=\"center\"><div class=\"text-overflow\" style=\"width:118px;\">");
//				contentDiv.append(roomList.get(i).get("roomNumber"));
				contentDiv.append(roomList.get(i).get("roomName"));
				contentDiv.append("</div></td></tr>");
				
				// 入住人员姓名
				contentDiv.append("<tr><td style=\"height:17px;\" colspan=\"2\" align=\"center\"><div class=\"text-overflow\" style=\"width:118px;\">");
				if (employeeList == null || employeeList.size() == 0) {    // 没人入住
					contentDiv.append("&nbsp;");
				} else {
					contentDiv.append(peopleName.substring(1));
				}
				contentDiv.append("</div></td></tr>");
				
				// 入住人数
				contentDiv.append("<tr>");
//				contentDiv.append("<td style=\"height:17px;\"><div class=\"text-overflow\" style=\"width:68px;\"><a>入住</a></div></td>");
				contentDiv.append("<td width=\"50px\" align=\"right\"><div class=\"peopleSum\" ><image src=\"");
				contentDiv.append(requestContext.getRequest().getContextPath());
				contentDiv.append("/scripts/easyui/themes/icons/man.png\">");
				contentDiv.append(employeeList.size());
				contentDiv.append("</div></td></tr>");
				contentDiv.append("</tbody></table></div></td>");
				
				if (i+1 == len) {
					contentDiv.append("</tr></tbody></table></div>");
				} else if ((i+1) % 7 == 0){
					contentDiv.append("</tr></tbody></table></div>");
				}
				
			}
			
			outHtmlStr.append("<div id=\"d_rminfo\" style=\"padding:5px;\">");
			outHtmlStr.append("<div class=\"totalInfo\" style=\"padding:2px;\">");
			outHtmlStr.append("<table><tbody><tr>");
			outHtmlStr.append("<td>房间总数：");
			outHtmlStr.append(totalRoom);
			outHtmlStr.append("&nbsp;&nbsp;</td>");
			outHtmlStr.append("<td>入住房间：");
			outHtmlStr.append(totalRoom-emptyRoom);
			outHtmlStr.append("&nbsp;&nbsp;</td>");
			outHtmlStr.append("<td>空置房间：");
			outHtmlStr.append(emptyRoom);
			outHtmlStr.append("&nbsp;&nbsp;</td>");
			outHtmlStr.append("<td>入住人数：");
			outHtmlStr.append(totalEmployees + totalOutSiders);
			outHtmlStr.append("&nbsp;&nbsp;</td>");
//			outHtmlStr.append("<td>内部员工：");
//			outHtmlStr.append(totalEmployees);
//			outHtmlStr.append("&nbsp;&nbsp;</td>");
//			outHtmlStr.append("<td>外来人员：");
//			outHtmlStr.append(totalOutSiders);
//			outHtmlStr.append("&nbsp;&nbsp;</td>");
			outHtmlStr.append("</tr></tbody></table></div>");
			outHtmlStr.append(contentDiv);
			outHtmlStr.append("</div>");
		} else {
			outHtmlStr.append("系统找不到对应的房间信息!");
		}
		
		
		
		try {
			requestContext.getResponse().getWriter().write(outHtmlStr.toString());
			requestContext.getResponse().getWriter().flush();
			requestContext.getResponse().getWriter().close();
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	
		return null;
	}
	
}
