package com.ts.main.sys.bulletin;
    
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ts.core.common.bean.OperatePromptBean;
import com.ts.core.common.constant.Globals;
import com.ts.core.common.service.IAppService;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.system.user.UserBean;
import com.ts.core.util.DateTimeUtil;
import com.ts.core.util.StringUtils;

@Service("showBulletinService")
public class ShowBulletinServiceImpl implements IAppService{
	private     Log log = LogFactory.getLog(this.getClass()); 
	 
	 
	public void showBulletins(RequestContext requestContext , IBaseServiceManger service,BulletinForm form){
		 
	} 
	public void addBulletinReply(RequestContext requestContext , IBaseServiceManger service,BulletinForm form){
		 
	} 
	public OperatePromptBean updateViewerStatus(RequestContext requestContext , IBaseServiceManger service,BulletinForm form){
		Bulletin bean=service.getDb().getObject(Bulletin.class, form.getId(), requestContext);
		form.setBean(bean);
		StringBuffer sql=new StringBuffer();
		sql.append("select a from BulletinViewer as a where a.bulletin.id="+bean.getId());
		sql.append(" and a.viewerId="+requestContext.getUserId());
		List<BulletinViewer> viewerList=service.getDb().queryHqlForList(sql.toString(), requestContext);
		if(viewerList!=null && viewerList.size()>0){
			BulletinViewer viewer=viewerList.get(viewerList.size()-1);
			service.getDb().mergeObject(viewer, requestContext);
		} else {
			BulletinViewer viewer=new BulletinViewer();
			viewer.setViewerId(requestContext.getUser().getId());
			viewer.setBulletin(bean);
			service.getDb().saveObjectAndId(viewer,requestContext); 
		}
		OperatePromptBean prompt=new OperatePromptBean();
		prompt.setId(form.getBean().getId().toString());
		prompt.setStatememt(prompt.hint_success); 
		return prompt;
	}
	public String updateBulletinView(RequestContext requestContext , IBaseServiceManger service,BulletinForm form){
		Bulletin bean=service.getDb().getObject(Bulletin.class, form.getId(),requestContext);
		form.setBean(bean);
		BulletinServiceImpl.getDetailList(requestContext,service,form);
		StringBuffer sql=new StringBuffer();
		sql.append("select a from BulletinViewer as a where a.bulletin.id="+bean.getId());
		sql.append(" and a.viewerId="+requestContext.getUserId());
		List<BulletinViewer> viewerList=service.getDb().queryHqlForList(sql.toString(), requestContext);
		if(viewerList!=null && viewerList.size()>0){
			BulletinViewer viewer=viewerList.get(viewerList.size()-1);
			service.getDb().mergeObject(viewer, requestContext);
		} else {
			BulletinViewer viewer=new BulletinViewer();
			viewer.setViewerId(requestContext.getUser().getId());
			viewer.setBulletin(bean);
			service.getDb().saveObjectAndId(viewer,requestContext); 
		}
		form.setViewerList(viewerList); 
		return "ViewBulletin";
	}
	public OperatePromptBean updateRecordsViewStatus(RequestContext requestContext , IBaseServiceManger service,BulletinForm form){
		String[] rows=form.getSelectedRecordIds();
		for(String id:rows){
			id=id.substring(1, id.length()-1);
			form.setId(new Integer(id));
			updateViewerStatus(requestContext,service,form);
		}
		 OperatePromptBean prompt=new OperatePromptBean(); 
		 prompt.setStatememt(prompt.hint_success); 
		return prompt;
	}
	public static String getFilterBulletinSql(RequestContext requestContext,Integer id,String topicKind){
		UserBean user=requestContext.getUser(); 
		StringBuffer sql=new StringBuffer();
		sql.append(" select a.id,a.name,a.topicKind,a.createDate,a.createdBy, a.needReply,");
		sql.append("a.emergentLevel,b.replyTimes,a.receiverType ");
		sql.append(" from SYS_Bulletin as a  ");
		sql.append(" left join (select count(v.id) as replyTimes,v.bulletinId ");
		sql.append("           from SYS_BulletinViewer as v  where v.viewerId="+user.getId());
		sql.append("           group by v.bulletinId) as b on b.bulletinId=a.id   ");
		sql.append(" where a.status!='0'");
		if(!StringUtils.isNoValue(topicKind))
			sql.append(" and a.topicKind='"+topicKind+"' ");
		if(!StringUtils.isNoValue(id))
			sql.append(" and a.id="+id );
		if(user.isAdmin()==false){
			sql.append(" and (a.id in (  SELECT  b.bulletinId  FROM   SYS_BulletinReceiver AS b");
			sql.append("  WHERE b.bulletinId = a.id "); 
			if(StringUtils.isNoValue(user.getCustId())==false){
				sql.append("  and (b.receiverType='"+Globals.receiverType.cust.name()+"' ");
				sql.append("       and b.receiverId="+user.getCustId()+") ");
				
			} else {
				sql.append("  and (b.receiverType='"+Globals.receiverType.employee.name()+"' ");
				sql.append("       and b.receiverId="+user.getEmployeeId()+") ");
			} 
			sql.append(" )");
			if(StringUtils.isNoValue(user.getCustId())==false){
				sql.append(" or (a.receiverType='"+Globals.receiverType.cust.name()+"' ");
				sql.append("  and not a.id in (  SELECT  bb.bulletinId  FROM   SYS_BulletinReceiver AS bb ");
				sql.append("  WHERE bb.bulletinId = a.id ))"); 
			}
			sql.append(" or ','+a.deptIds+',' like '%,"+user.getDepartmentId()+",%' ");
			sql.append(" or ','+a.companyIds+',' like '%,"+user.getCompanyId()+",%'");
			sql.append(" or a.receiverType='"+Globals.receiverType.all.name()+"' ");
			sql.append(" or a.receiverType='"+Globals.receiverType.group.name()+"' ");
			sql.append(" ) ");
		} 
		sql.append(" order by a.createDate desc,a.id desc ");
		return sql.toString();
	}
	public Map<String, Object> getBulletins(RequestContext requestContext , IBaseServiceManger service,BulletinForm form){
		UserBean user=requestContext.getUser(); 
		Integer  records = form.getRecords(); 
		List topicKinds=(List)requestContext.getMessageResource().getSqlResultMap("topicKindList");
		if(topicKinds==null || topicKinds.size()<=0){
			String sb="select code,name from  TS_SysCode  where codeKind=25";
			topicKinds=service.getDb().queryForList(sb, requestContext);
		}
		StringBuffer sql=new StringBuffer();  
		sql.append(" select a.id,a.name,a.topicKind,a.createDate,a.createdBy, a.needReply,");
		sql.append("a.emergentLevel,b.replyTimes,a.receiverType,a.mobileHome ");
		sql.append(" from SYS_Bulletin as a  ");
		sql.append(" left join (select count(v.id) as replyTimes,v.bulletinId ");
		sql.append("           from SYS_BulletinViewer as v  where v.viewerId="+user.getId());
		sql.append("           group by v.bulletinId) as b on b.bulletinId=a.id   ");
		sql.append(" where a.status!='0'");
		//sql.append(" and a.topicKind='"+form.getTopicKind()+"' ");
		if(user.isAdmin()==false){
			sql.append(" and (a.id in (  SELECT  b.bulletinId  FROM   SYS_BulletinReceiver AS b");
			sql.append("  WHERE b.bulletinId = a.id "); 
			if(StringUtils.isNoValue(user.getCustId())==false){
				sql.append("  and (b.receiverType='"+Globals.receiverType.cust.name()+"' ");
				sql.append("       and b.receiverId="+user.getCustId()+") ");
			} else {
				sql.append("  and (b.receiverType='"+Globals.receiverType.employee.name()+"' ");
				sql.append("       and b.receiverId="+user.getEmployeeId()+") ");
			} 
			sql.append(" )");
			if(StringUtils.isNoValue(user.getCustId())==false){
				sql.append(" or (a.receiverType='"+Globals.receiverType.cust.name()+"' ");
				sql.append("  and not a.id in (  SELECT  bb.bulletinId  FROM   SYS_BulletinReceiver AS bb ");
				sql.append("  WHERE bb.bulletinId = a.id ))"); 
			}
			sql.append(" or ','+a.deptIds+',' like '%,"+user.getDepartmentId()+",%' ");
			sql.append(" or ','+a.companyIds+',' like '%,"+user.getCompanyId()+",%'");
			sql.append(" or a.receiverType='"+Globals.receiverType.all.name()+"' ");
			sql.append(" or a.receiverType='"+Globals.receiverType.group.name()+"' ");
			sql.append(" ) ");
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(form.getPageSize()!=null && form.getPageSize().intValue()>0){
			StringBuffer sb=new StringBuffer();
			sb.append("select count(*) from ("+sql.toString());
			sb.append(") as a ");
			Integer totalnumber = service.getDbService().queryForInt(sb.toString());
			resultMap.put("total", totalnumber);
		}
		
		sql.append(" order by a.createDate desc,a.id desc ");
		List<Map> lt=service.getDb().queryForList(sql.toString(), requestContext);
		if(form.getPageSize()!=null && form.getPageSize().intValue()>0){
			lt=service.getDb().queryForList(sql.toString(),form.getPageNo(),form.getPageSize());
		} else {
			lt=service.getDb().queryForList(sql.toString(), requestContext);
		}
		int noReadCount=0;
		List recordList=new ArrayList();
		if (lt!=null && lt.size()>0){ 
			//List topicKindList=(List)requestContext.getMessageResource().getSqlResultMap("receiverTypeList");
			 
			for(int i=0;i<lt.size();i++){
				Map row = lt.get(i);  
				Integer id=(Integer)row.get("id");
				/*String type=service.getRuleService().getDicCodeToName(topicKindList,(String)row.get("receiverType"));
				row.put("objType", type); */
				String create=(String)row.get("createDate"); 
				String topicKind=service.getRuleService().getDicCodeToName(topicKinds,(String)row.get("topicKind"));
				row.put("topicKindName", topicKind);
				row.put("image","0");
				Integer replyTimes=null;
				if(row.get("replyTimes") instanceof Integer){
					replyTimes=(Integer)row.get("replyTimes");
				}
				if(create.equals(DateTimeUtil.formatDate())){
					if(replyTimes==null){ 
						row.put("image", "1"); 
					} 
				}  
				row.put("fontColor","0");   
				if(replyTimes!=null){ 
					row.put("fontColor","1");
				} else {
					noReadCount++;
				} 
				String needReply=(String)row.get("needReply"); 
				if(!"1".equals(needReply)){
					row.put("replyTimes",0);
				}
				if(!StringUtils.isNoValue(records)){
					if(recordList.size()<records.intValue()) 
						recordList.add(row); 
				} else {
					recordList.add(row);
				}
				
			}
		}
		
		resultMap.put("rows", recordList); 
		resultMap.put("noReadCount", noReadCount);
		return resultMap;
	}
	 
}
