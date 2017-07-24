<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %> 
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
  <head> 
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title></title>
   <script src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
    <script src="${contextPath}/pages/app/js/mui.min.js"></script>
	<link href="${contextPath}/pages/app/css/mui.min.css" rel="stylesheet" />
 	<link href="${contextPath}/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
 	<script src="${contextPath}/scripts/bootstrap/js/bootstrap.min.js"></script>
	<script src="${contextPath}/scripts/app/laydate/laydate.js"></script>
 	<link href="${contextPath}/scripts/app/layer/layer.css" rel="stylesheet" />
 	<script src="${contextPath}/scripts/app/layer/layer.m.js"></script>
 	<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery.form.js"></script> 
 	<script type="text/javascript" src='${contextPath}/scripts/jquery/mask.js'></script>
 	<script src="${contextPath}/scripts/ts/TSCore.js"></script> 	
    <style>
			html,body {
				background-color:#efeff4;
			}
	</style>
  </head>
<body id="${appReqeustContext.appKey}Body"  >   
<header class="mui-bar mui-bar-nav">
    <button class="mui-action-back mui-btn mui-btn-link mui-btn-nav mui-pull-left">
			    <span class="mui-icon mui-icon-left-nav"></span>
			  </button>
    <h1 class="mui-title">提交${form.workFlowAuditLog.taskTitle}</h1>
 </header>
<div class="mui-content">
     <form id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" action="${contextPath}/core/${appReqeustContext.appService}/json/doSubmit.do" method="post" class="mui-input-group" >
   <input name="workFlowAuditLog.id" type="hidden"  /> 
   <input name="workFlowAuditLog.auditStatus" id="workFlowAuditLog.auditStatus" type="hidden"  value="0"/> 
   <input name="workFlowAuditLog.taskId" id="workFlowAuditLog.taskId" type="hidden"  value="${form.workFlowAuditLog.taskId}"/> 
   <input name="workFlowAuditLog.processInstanceId" id="workFlowAuditLog.processInstanceId"  type="hidden"  value="${form.workFlowAuditLog.processInstanceId}"/> 
   <input name="workFlowAuditLog.workFlowConfigureFile" id="workFlowAuditLog.workFlowConfigureFile"  type="hidden"  value="${form.workFlowAuditLog.workFlowConfigureFile}"/> 
	 <input name="workFlowAuditLog.businessKey" id="workFlowAuditLog.businessKey"  type="hidden"  value="${form.workFlowAuditLog.businessKey}"/> 
     <input name="workFlowAuditLog.processDefinitionKey" id="workFlowAuditLog.processDefinitionKey"  type="hidden"  value="${form.workFlowAuditLog.processDefinitionKey}"/> 
     <input name="workFlowAuditLog.taskNodeType" id="workFlowAuditLog.taskNodeType"  type="hidden"  value="${form.workFlowAuditLog.taskNodeType}"/>
      <input name="workFlowAuditLog.taskTitle" id="workFlowAuditLog.taskTitle"  type="hidden"  value="${form.workFlowAuditLog.taskTitle}"/>
       <input name="workFlowAuditLog.processName" id="workFlowAuditLog.processName"  type="hidden"  value="${form.workFlowAuditLog.processName}"/>
       <input name="workFlowAuditLog.appReturnUrl" id="appReturnUrl"  type="hidden"  value="${form.workFlowAuditLog.appReturnUrl}"/>
       <input name="workFlowAuditLog.taskRowType" id="workFlowAuditLog.taskRowType"  type="hidden"  value="${form.workFlowAuditLog.taskRowType}"/> 
     <table class="table table-bordered table-condensed table-responsive" id="submitAuditNodeTable">
    				 <tr>
			          	<td ><label>审核对象类型</label></td>
			            <td><label>审核节点名称</label></td>
			        </tr>
                  <c:forEach items="${form.nextAuditLogList}" var="obj">
                     <tr>
                        <td nowrap="nowrap">${obj.assignmentName }</td> 
                       
                        <td nowrap="nowrap">${obj.taskName}</td>
                    </tr>
                   </c:forEach>  
              <tr> 
			          	<td colspan="2"><label>变更审核人:
                        选择部门</label><select  class="form-control"  id="deptId"  onchange="getemployee(this);">
                                  
									 <ts:forEach name='deptList' keyField='id' valueField='name' value=''/>
								</select>
                                <label>选择人员</label><select  class="form-control"  id="appointAuditorId" name="nextAuditLogList.appointAuditorId" >
								</select> 
                                <input name="nextAuditLogList.appointAuditor" id="nextAuditLogList.appointAuditor"  type="hidden"   />
                                <input name="nextAuditLogList.taskDefineKey" id="nextAuditLogList.taskDefineKey"  type="hidden"  value="${obj.taskDefineKey}" /> 
                               <input name="nextAuditLogList.delegaterIds" id="nextAuditLogList.delegaterIds"  type="hidden"  value="${obj.delegaterIds}" /> 
                               <input name="nextAuditLogList.assignmentName" id="nextAuditLogList.assignmentName"  type="hidden"  value="${obj.assignmentName}" /> 
                               <input name="nextAuditLogList.taskName" id="nextAuditLogList.taskName"  type="hidden"  value="${obj.taskName}" /> 
                                <input name="nextAuditLogList.taskNodeType" id="nextAuditLogList.taskNodeType"  type="hidden"  value="${obj.taskNodeType}" /> </td> 
			        </tr>
             <tr>    
             <td nowrap="nowrap" colspan="2">
             <div class="mui-button-row">
						<button type="button" class="mui-btn mui-btn-primary" onclick="workFlowAuditScript.submitAppForm(this);">确认提交</button>&nbsp;&nbsp;
		 </div> 
             </td> 
         </tr>   
      </table> 
      
       <script type="text/javascript" src="<ts:base ref='path'/>/WorkFlowAudit.js"></script>  
  <script language="javascript">
        var contextPath="<ts:base ref='root'/>";
        mui.init();
		mui.plusReady(function() {
		})
		function getemployee(dom){
			var id=$(dom).val();
			//$("#custName").val($(dom).find("option:selected").text());
			$.post("<ts:base ref='root'/>/app/travelfeeAppService/json/getemployee.do",{id:id},function(data){
				if(data){
					var selectobj=$("#appointAuditorId").empty();
					$.each(data,function(index,n){
						selectobj.append("<option  value='"+n.id+"'>"+ n.name+ "</option>");
					});
				}
			})
		}
     var workFlowAuditScript=new WorkFlowAuditScript();   
	 $(function() {  
	      workFlowAuditScript.initPage({appKey:"${appReqeustContext.appKey}"});   
		 
		   
	 });  
	  
	
  </script>
       </form>   
     <IFRAME ID="workFlowRefBillIframe" Name="workFlowRefBillIframe" onload="workFlowAuditScript.dyniframesize('workFlowRefBillIframe')" FRAMEBORDER=0 SCROLLING=AUTO width=100%  height=100% SRC="${contextPath}${form.workFlowAuditLog.url}"></IFRAME>
 </div>
    
   

   
 
  </body>
  
</html>

