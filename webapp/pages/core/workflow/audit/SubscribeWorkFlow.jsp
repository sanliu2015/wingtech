<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %> 
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
  <head> 
  <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">  
<link rel="stylesheet" type="text/css" href="${contextPath}/style/TSStyle.css"> 
 <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/my97.css"> 
 <title>提交审核单据</title>    
  </head>
<body id="${appReqeustContext.appKey}Body"  >  
<form id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" action="${contextPath}/core/${appReqeustContext.appService}/json/doSubscribe.do" method="post">
   <input name="workFlowAuditLog.id" type="hidden"  value="${form.workFlowAuditLog.id}" /> 
   <input name="taskRowType" id="taskRowType" type="hidden"   value="${form.taskRowType}"/> 
   <input name="workFlowAuditLog.auditStatus" id="workFlowAuditLog.auditStatus" type="hidden"  value="${form.workFlowAuditLog.auditStatus}" /> 
   <input name="workFlowAuditLog.taskId" id="workFlowAuditLog.taskId" type="hidden"  value="${form.workFlowAuditLog.taskId}"/> 
   <input name="workFlowAuditLog.processInstanceId" id="workFlowAuditLog.processInstanceId"  type="hidden"  value="${form.workFlowAuditLog.processInstanceId}"/> 
   <input name="workFlowAuditLog.workFlowConfigureFile" id="workFlowAuditLog.workFlowConfigureFile"  type="hidden"  value="${form.workFlowAuditLog.workFlowConfigureFile}"/> 
	 <input name="workFlowAuditLog.businessKey" id="workFlowAuditLog.businessKey"  type="hidden"  value="${form.workFlowAuditLog.businessKey}"/> 
     <input name="workFlowAuditLog.processDefinitionKey" id="workFlowAuditLog.processDefinitionKey"  type="hidden"  value="${form.workFlowAuditLog.processDefinitionKey}"/> 
     <input name="workFlowAuditLog.taskNodeType" id="workFlowAuditLog.taskNodeType"  type="hidden"  value="${form.workFlowAuditLog.taskNodeType}"/>
     <input name="workFlowAuditLog.taskRowType" id="workFlowAuditLog.taskRowType"  type="hidden"  value="${form.workFlowAuditLog.taskRowType}"/> 
      <input name="workFlowAuditLog.editUrl" id="workFlowAuditLog.editUrl" type="hidden"  value="${form.workFlowAuditLog.editUrl}" /> 
   <input name="displayMaskFlag" id="displayMaskFlag" type="hidden"  value="1" /> 
     <div id="currentAuditContentPanel"    title="当前审核信息"  data-options="collapsible:true" width="98%" > 
     <table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
			<tr  style="display:none">
				<td nowrap><label  for="workFlowAuditLog.name">任务标题</label></td>
				<td  ><input name="workFlowAuditLog.taskTitle"  id="workFlowAuditLog.taskTitle"  class="easyui-textbox"  style="width:250px;height:25px"  value="${form.workFlowAuditLog.taskTitle}"   readonly></input></td>  
               <td nowrap><label  for="workFlowAuditLog.processName">工作流名称</label></td>
				<td  ><input name="workFlowAuditLog.processName"  id="workFlowAuditLog.processName"  class="easyui-textbox"  style="width:250px;height:25px"  value="${form.workFlowAuditLog.processName}" readonly></input></td> 
           </tr> 
           <tr style="display:none" >
				<td nowrap><label  for="workFlowAuditLog.assignmentName">审核人</label></td>
				<td  ><input name="workFlowAuditLog.assignmentName"  id="workFlowAuditLog.assignmentName"  class="easyui-textbox"  style="width:250px;height:25px"  value="${form.workFlowAuditLog.assignmentName}"   readonly></input></td>  
               <td nowrap><label  for="workFlowAuditLog.taskName">审核节点</label></td>
				<td  ><input name="workFlowAuditLog.taskName"  id="workFlowAuditLog.taskName"  class="easyui-textbox"  style="width:250px;height:25px"  value="${form.workFlowAuditLog.taskName}" readonly></input></td> 
           </tr> 
           <tr style="display:none">   	  
	                  <td nowrap> <label  for="workFlowAuditStatus">审核结果</label><span class="bitian">*</span> </td>
					  <td  class="txtBoxnoborder" nowrap="nowrap" style="font-size:16px" colspan="3" > 
					    <input type="radio" name="workFlowAuditStatus" id="workFlowAuditStatus" value="1"  checked="checked" onClick="workFlowAuditScript.setAuditStatusValue()" alt="同意"/>同意
					     &nbsp;&nbsp;&nbsp;&nbsp;
				   <input type="radio" name="workFlowAuditStatus" id="workFlowAuditStatus" value="3" onClick="workFlowAuditScript.setAuditStatusValue()"  alt="不同意"  title="提交人员不能该修改"/> 不同意
				    &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="workFlowAuditStatus" id="workFlowAuditStatus" value="12" onClick="workFlowAuditScript.setAuditStatusValue()" alt="撤回重改" title="提交人员可重新修改后，继续提交审核"/>撤回重改
				    &nbsp;&nbsp;&nbsp;&nbsp;
				   <input type="radio" name="workFlowAuditStatus" id="workFlowAuditStatus" value="6" onClick="workFlowAuditScript.setAuditStatusValue()" alt="暂缓审核"  title="提交人员不能修改该单据，审核人员后续可继续审核"/>暂缓审核
				    </td> 
				</tr> 
                <tr    >  						 		 
				   <td nowrap="nowrap" colspan="4"> <label  for="workFlowAuditStatus">任务标题：</label><input name="taskTitle"  id="taskTitle"  class="easyui-textbox"  style="width:250px;height:25px"  value="${form.workFlowAuditLog.taskTitle}"   readonly></input>
                   <br/>
                  <label  for="workFlowAuditStatus">审批信息：</label>
                  <br/> <input class="easyui-textbox" name="workFlowAuditLogdescription" id="workFlowAuditLogdescription" data-options="multiline:true" style="height:50px;width:500px"  value="${form.workFlowAuditLog.description}" readonly></input> </td>            
				  </tr> 
				<tr style="display:none">   					 		 
				   <td nowrap="nowrap" colspan="4">  
                   
                  <input class="easyui-textbox" name="workFlowAuditLog.description" id="workFlowAuditLog.description" data-options="multiline:true" style="height:50px;width:500px"  value="${form.workFlowAuditLog.description}" readonly></input>  
				    
				  </td>          
				  </tr>  
        </table>
     </div>
       
       
      <div style="text-align:center;"    class="dialog-button" id="workFlowFormToolbar">
      
   	<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  style="display:none" onClick=" return workFlowAuditScript.submitSubscribeForm(this);" >确认查阅</a>
     <c:if test="${form.workFlowAuditLog.auditStatus=='12'}">
     &nbsp;&nbsp;&nbsp;&nbsp;
    <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return workFlowAuditScript.submitAndModifyBillForm(this);" >查阅并修改单据</a>
     </c:if>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a> 
     </div>  
     <div id="workFlowRefBillForm"   style="width:98%;padding:2px;"   >
     <IFRAME ID="workFlowRefBillIframe" Name="workFlowRefBillIframe" onload="workFlowAuditScript.dyniframesize('workFlowRefBillIframe')" FRAMEBORDER=0 SCROLLING=AUTO width=100%  height=100% SRC="${contextPath}${form.workFlowAuditLog.url}"></IFRAME>
	</div>
    
    <script type="text/javascript" src="<ts:base ref='path'/>/WorkFlowAudit.js"></script>  
  <script language="javascript">
     var workFlowAuditScript=new WorkFlowAuditScript();   
	 $(function() {  
	      workFlowAuditScript.initPage({appKey:"${appReqeustContext.appKey}"});   
		  //$('#choseNextTaskAuditorGrid').datagrid( ); 
		  //workFlowAuditScript.nextAuditLogList= <ts:toJSONStr name='form.nextAuditLogList'/> ;
		  var auditStatus=document.getElementById("workFlowAuditLog.auditStatus").value;
		   
	 });  
	 function modalDialogLoadEvents(){ 
	     var nextList=workFlowAuditScript.nextAuditLogList;
	     if(nextList!=null){
			 for(var i=0;i<nextList.length;i++){ 
			   var rowBean=nextList[i];
			   rowBean.recordOperateStatus="update";
			   var  row=workFlowAuditScript.insertNextTaskAuditorGridRow();   
			   var tmp={"nextAuditLogList":rowBean,"recordOperateStatus":"lookup"};
			   row.tsLoadData(tmp);   
			 } 
			 $('#choseNextTaskAuditorGrid').datagrid("checkRow",0);
		 }
		 $.ts.EasyUI.titleAppendToolbar("choseNextTaskAuditorToolbar","choseNextTaskAuditorGrid");
		 //$.ts.EasyUI.titleAppendToolbar("currentTaskAuditorToolbar","currentAuditContentPanel");
		// workFlowAuditScript.setAuditStatusValue();
	}
	if(document.getElementById("workFlowAuditLog.auditStatus").value!="12"){
		document.getElementById("displayMaskFlag").value="0";
		document.getElementById("submitBtn").click();
	}
  </script>
 </form>   
   
 
  </body>
  
</html>

