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
    <h1 class="mui-title">审核${form.workFlowAuditLog.taskTitle}</h1>
 </header>
<div class="mui-content">
<IFRAME ID="workFlowRefBillIframe" Name="workFlowRefBillIframe" onload="workFlowAuditScript.dyniframesize('workFlowRefBillIframe')" FRAMEBORDER=0 SCROLLING=AUTO width=100%  height=100% SRC="${contextPath}${form.workFlowAuditLog.url}"></IFRAME>
<form id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" action="${contextPath}/core/${appReqeustContext.appService}/json/save.do" method="post"  class="mui-input-group" >
   <input name="workFlowAuditLog.id" type="hidden"  /> 
   <input name="workFlowAuditLog.auditStatus" id="workFlowAuditLog.auditStatus" type="hidden"  value="1"/> 
   <input name="workFlowAuditLog.taskId" id="workFlowAuditLog.taskId" type="hidden"  value="${form.workFlowAuditLog.taskId}"/> 
   <input name="workFlowAuditLog.processInstanceId" id="workFlowAuditLog.processInstanceId"  type="hidden"  value="${form.workFlowAuditLog.processInstanceId}"/> 
   <input name="workFlowAuditLog.workFlowConfigureFile" id="workFlowAuditLog.workFlowConfigureFile"  type="hidden"  value="${form.workFlowAuditLog.workFlowConfigureFile}"/> 
	 <input name="workFlowAuditLog.businessKey" id="workFlowAuditLog.businessKey"  type="hidden"  value="${form.workFlowAuditLog.businessKey}"/> 
     <input name="workFlowAuditLog.processDefinitionKey" id="workFlowAuditLog.processDefinitionKey"  type="hidden"  value="${form.workFlowAuditLog.processDefinitionKey}"/>   
     <input name="workFlowAuditLog.appReturnUrl" id="appReturnUrl"  type="hidden"  value="${form.workFlowAuditLog.appReturnUrl}"/>
     <input name="workFlowAuditLog.taskTitle" id="workFlowAuditLog.taskTitle"  type="hidden"  value="${form.workFlowAuditLog.taskTitle}"/>
      <input name="workFlowAuditLog.processName" id="workFlowAuditLog.processName"  type="hidden"  value="${form.workFlowAuditLog.processName}"/>
      <input name="workFlowAuditLog.assignmentName" id="workFlowAuditLog.assignmentName"  type="hidden"  value="${form.workFlowAuditLog.assignmentName}"/>
      <input name="workFlowAuditLog.taskRowType" id="workFlowAuditLog.taskRowType"  type="hidden"  value="${form.workFlowAuditLog.taskRowType}"/> 
    <table class="table table-bordered table-condensed table-responsive" id="submitAuditNodeTable"> 
            
           <tr>   	  
	                  <td nowrap> <label  for="workFlowAuditStatus">审核结果</label><span class="bitian">*</span>
                       </td>
					  <td    nowrap="nowrap"   >
                      <ul class="mui-table-view"> 
                     <li class="mui-table-view-cell mui-radio mui-left">
		  <input type="radio" name="workFlowAuditStatus" id="workFlowAuditStatus" value="1"  checked="checked" onClick="workFlowAuditScript.setAppAuditStatusValue()" alt="同意"/>同意
	      </li>
          <li class="mui-table-view-cell mui-radio mui-left">
				   <input type="radio" name="workFlowAuditStatus" id="workFlowAuditStatus" value="3" onClick="workFlowAuditScript.setAppAuditStatusValue()"  alt="不同意"  title="提交人员不能该修改"/> 不同意 
                </li>
                  <li class="mui-table-view-cell mui-radio mui-left">
					<input type="radio" name="workFlowAuditStatus" id="workFlowAuditStatus" value="12" onClick="workFlowAuditScript.setAppAuditStatusValue()" alt="撤回重改" title="提交人员可重新修改后，继续提交审核"/>撤回重改 
                   </li>
                   </ul>
				    </td> 
				</tr> 
				<tr   >  						 		 
				   <td nowrap="nowrap"> <label  for="workFlowAuditStatus">审核意见</label>  <span class="bitian">*</span>    </td> 
				  <td    id="signSealPostionArea"> 
                  <div class="mui-input-row">
                  <textarea rows="5" name="workFlowAuditLog.description" id="workFlowAuditLog.description" placeholder="输入审核意见" style="width: 200px" ></textarea>  
                  </div>
				  </td>          
			  </tr>  
               <tr   >  
                <td nowrap="nowrap" colspan="2">
                <div class="mui-button-row">
                <button type="button" class="mui-btn mui-btn-primary" onclick="workFlowAuditScript.submitAppForm(this);">确认审核</button>&nbsp;&nbsp; 
   				 </div> 
    		  </tr>  
        </table> 
     
     <table align="center"   id="choseNextTaskAuditorGrid"   style="display:none"> 
            <tr   >    
                 <td ><label>审核对象类型</label></td>
			            <td><label>审核节点名称</label></td>
				  </tr>
             <c:forEach items="${form.nextAuditLogList}" var="obj">
                     <tr>
                        <td nowrap="nowrap">${obj.assignmentName }</td> 
                       
                        <td nowrap="nowrap">${obj.taskName}
                         <input name="nextAuditLogList.taskDefineKey" id="nextAuditLogList.taskDefineKey"  type="hidden"  value="${obj.taskDefineKey}" /> 
                               <input name="nextAuditLogList.delegaterIds" id="nextAuditLogList.delegaterIds"  type="hidden"  value="${obj.delegaterIds}" /> 
                               <input name="nextAuditLogList.assignmentName" id="nextAuditLogList.assignmentName"  type="hidden"  value="${obj.assignmentName}" /> 
                               <input name="nextAuditLogList.taskName" id="nextAuditLogList.taskName"  type="hidden"  value="${obj.taskName}" />  
                               <input name="nextAuditLogList.delegaterKindName" id="nextAuditLogList.delegaterKindName"  type="hidden"  value="${obj.delegaterKindName}" />  
                                <input name="nextAuditLogList.taskNodeType" id="nextAuditLogList.taskNodeType"  type="hidden"  value="${obj.taskNodeType}" />  
                               </td> 
                    </tr>
                   </c:forEach>  
         </table>   
      
    <script type="text/javascript" src="<ts:base ref='path'/>/WorkFlowAudit.js"></script>  
  <script language="javascript">
        var contextPath="<ts:base ref='root'/>";
        mui.init();
		mui.plusReady(function() {
		})
     var workFlowAuditScript=new WorkFlowAuditScript();   
	 $(function() {  
	      workFlowAuditScript.initPage({appKey:"${appReqeustContext.appKey}"});   
		  workFlowAuditScript.setAppAuditStatusValue();
	 });  
	 
	
  </script> 
 </form>   
  </div>  
 
  </body>
  
</html>

