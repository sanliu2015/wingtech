<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %> 
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<%@ taglib uri="/tags/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
  <head>  
 <title>历史流程</title>   
  <%@ include file="/pages/core/common/Include.jsp" %>  
  </head>
<body id="${appReqeustContext.appKey}Body"  >  
<form id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" action="${contextPath}/core/${appReqeustContext.appService}/json/doSubmit.do" method="post">
   <input name="workFlowAuditLog.id" type="hidden"  /> 
   <input name="workFlowAuditLog.auditStatus" id="workFlowAuditLog.auditStatus" type="hidden"  value="0"/> 
   <input name="workFlowAuditLog.taskId" id="workFlowAuditLog.taskId" type="hidden"  value="${form.workFlowAuditLog.taskId}"/> 
   <input name="workFlowAuditLog.processInstanceId" id="workFlowAuditLog.processInstanceId"  type="hidden"  value="${form.workFlowAuditLog.processInstanceId}"/> 
   <input name="workFlowAuditLog.workFlowConfigureFile" id="workFlowAuditLog.workFlowConfigureFile"  type="hidden"  value="${form.workFlowAuditLog.workFlowConfigureFile}"/> 
	 <input name="workFlowAuditLog.businessKey" id="workFlowAuditLog.businessKey"  type="hidden"  value="${form.workFlowAuditLog.businessKey}"/> 
     <input name="workFlowAuditLog.processDefinitionKey" id="workFlowAuditLog.processDefinitionKey"  type="hidden"  value="${form.workFlowAuditLog.processDefinitionKey}"/> 
     <input name="workFlowAuditLog.taskNodeType" id="workFlowAuditLog.taskNodeType"  type="hidden"  value="${form.workFlowAuditLog.taskNodeType}"/>
     
    <div id="processPagePanel" class="easyui-panel"  title="流程综合信息-【${processDefinition.name}】-${historicProcessInstance.processInstanceId}" data-options="collapsible:true" width="100%" >  
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
           <tr  >
              <td  nowrap><label >流程ID</label></td>
				<td>${historicProcessInstance.id} </td>
                <td  nowrap><label   >流程定义ID</label></td>
				<td>${historicProcessInstance.processDefinitionId}</td>
                 <td  nowrap><label  >业务KEY</label></td>
				<td>${historicProcessInstance.businessKey}</td>
             </tr>
             <tr>
              <td  nowrap><label  >流程启动时间</label></td>
				<td><fmt:formatDate value="${historicProcessInstance.startTime}" pattern="yyyy-MM-dd hh:mm:ss"/> </td>
                <td  nowrap><label   >流程结束时间</label></td>
				<td><fmt:formatDate value="${historicProcessInstance.endTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
                 <td  nowrap><label  >流程状态</label></td>
				<td>${empty historicProcessInstance.endTime ? '未结束': '已结束'}</td>
             </tr>
        </table>
	</div>   
      
     <table align="center"    id="historyAuditorRecordsGrid" title="历史审核记录"  style="width:100%;height:auto;border:1px solid #ccc;"  data-options="rownumbers:true,collapsible:true, singleSelect:true">
       <thead> 
           <thead> 
            <tr   >    
					<th data-options="field:'id'">活动ID</th>
					<th data-options="field:'activityName'">活动名称</th> 
                    <th data-options="field:'taskId'" >任务ID</th> 
                    <th data-options="field:'assignee'" >处理人</th> 
                    <th data-options="field:'comment'" >意见</th> 
                    <th data-options="field:'startTime'" >活动开始时间</th> 
                    <th data-options="field:'endTime'" >活动结束时间</th> 
                    <th data-options="field:'seconds'" >耗时(秒)</th> 
				  </tr> 
            </thead> 
            <c:if test="${!empty historicWorkFlow}">
                  <c:forEach items="${historicWorkFlow}" var="obj"  varStatus="stat"> 	
				  <tr class="bgwhiteleft"  >  
					<td nowrap="nowrap">${obj.id}</td> 
					<td nowrap="nowrap">${obj.activityName}</td>  
                    <td nowrap="nowrap">${obj.taskId}</td>  
                    <td nowrap="nowrap">${obj.assignee}</td>  
                    <td nowrap="nowrap">${obj.comment}</td> 
                     <td nowrap="nowrap">${obj.startTime}</td>  
                     <td nowrap="nowrap">${obj.endTime}</td>   
                     <td nowrap="nowrap">${obj.seconds}</td> 
				  </tr>
				 </c:forEach>
                 </c:if>
        </table>  
  <script type="text/javascript" src="<ts:base ref='path'/>/WorkFlowAudit.js"></script> 
  <script language="javascript"> 
	 $('#historyAuditorRecordsGrid').datagrid(  );  
  </script>
 </form>   
   
 
  </body>
  
</html>

