package com.ts.main.sys.bulletin;
 
import java.util.Map;

import com.ts.core.common.constant.Globals;
import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.context.RequestContext;
import com.ts.core.report.bean.InvokeEventConfigure;
import com.ts.core.report.bean.RepManager;
import com.ts.core.report.bean.ReportFieldPoint;
import com.ts.core.report.bean.SourceTableRuleBean;
import com.ts.core.report.bean.TargetTableRuleBean;
import com.ts.core.report.invokeevent.IInvokeEvent;
import com.ts.core.system.user.UserBean;
import com.ts.core.util.StringUtils;

public class FilterRemindBulletinSql  implements IInvokeEvent {
 
	 
	@Override
	public Object invokeEvent(RepManager repManager,
			TargetTableRuleBean targetTableRuleBean, Map recordMap,RequestContext requestContext,IBaseServiceManger baseService)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object execute(InvokeEventConfigure configure,
			RepManager repManager, SourceTableRuleBean sourceTableRuleBean,
			TargetTableRuleBean targetTableRuleBean, Map recordMap,
			ReportFieldPoint fieldPoint, RequestContext requestContext,IBaseServiceManger baseService)  {
		UserBean user=requestContext.getUser(); 
		StringBuffer sql=new StringBuffer();  
		 
		//sql.append(" and a.topicKind='"+form.getTopicKind()+"' ");
		if(user.isAdmin()==false){
			sql.append("  a.id in (  SELECT  b.bulletinId  FROM   SYS_BulletinReceiver AS b");
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
			 
		}
		return sql.toString();
	}
	 
}
