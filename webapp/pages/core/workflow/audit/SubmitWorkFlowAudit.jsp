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
<form id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" action="${contextPath}/core/${appReqeustContext.appService}/json/doSubmit.do" method="post">
   <input name="workFlowAuditLog.id" type="hidden"  /> 
   <input name="workFlowAuditLog.auditStatus" id="workFlowAuditLog.auditStatus" type="hidden"  value="0"/> 
   <input name="workFlowAuditLog.taskId" id="workFlowAuditLog.taskId" type="hidden"  value="${form.workFlowAuditLog.taskId}"/> 
   <input name="workFlowAuditLog.processInstanceId" id="workFlowAuditLog.processInstanceId"  type="hidden"  value="${form.workFlowAuditLog.processInstanceId}"/> 
   <input name="workFlowAuditLog.workFlowConfigureFile" id="workFlowAuditLog.workFlowConfigureFile"  type="hidden"  value="${form.workFlowAuditLog.workFlowConfigureFile}"/> 
	 <input name="workFlowAuditLog.businessKey" id="workFlowAuditLog.businessKey"  type="hidden"  value="${form.workFlowAuditLog.businessKey}"/> 
     <input name="workFlowAuditLog.processDefinitionKey" id="workFlowAuditLog.processDefinitionKey"  type="hidden"  value="${form.workFlowAuditLog.processDefinitionKey}"/> 
     <input name="workFlowAuditLog.taskNodeType" id="workFlowAuditLog.taskNodeType"  type="hidden"  value="${form.workFlowAuditLog.taskNodeType}"/>
     <input name="workFlowAuditLog.taskRowType" id="workFlowAuditLog.taskRowType"  type="hidden"  value="${form.workFlowAuditLog.taskRowType}"/> 
     <div id="currentAuditContentPanel"   style="display:none" title="当前提交信息"  data-options="collapsible:true" width="98%" > 
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
				<td  ><input name="workFlowAuditLog.taskName"  id="workFlowAuditLog.taskName"  class="easyui-textbox"  style="width:250px;height:25px"  value="${form.task.name}" readonly></input></td> 
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
				<tr   style="display:none" >  						 		 
				   <td nowrap="nowrap"> <label  for="workFlowAuditStatus">审核意见</label>  <span class="bitian">*</span>    </td> 
				  <td  class="txtBoxnoborder" colspan="3" id="signSealPostionArea"> 
                  <input class="easyui-textbox" name="workFlowAuditLog.description" id="workFlowAuditLog.description" data-options="multiline:true" style="height:50px;width:500px"  value="确认提交"></input>  
				    
				  </td>          
				  </tr>  
        </table>
     </div>
      
     <table align="center"  class="easyui-datagrid"  id="choseNextTaskAuditorGrid" title="选择审核人"  style="width:98%;height:auto;border:1px solid #ccc;"  data-options="rownumbers:true,collapsible:true,selectOnCheck:false,checkOnSelect:false,singleSelect:false">
       <thead> 
            <tr   >    
                    <th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'delegaterKindName',width:150, formatter:workFlowAuditScript.textInputFormat">审核对象类型</th>
					<th data-options="field:'assignmentName',width:150,formatter:workFlowAuditScript.textInputFormat">审批人</th>
                    <th data-options="field:'appointAuditor',width:150, formatter:workFlowAuditScript.textAndSearchInputFormats">变更审核人</th>
					<th data-options="field:'taskName',width:200, formatter:workFlowAuditScript.textInputFormat">节点名称</th> 
                    
                    <th data-options="field:'taskDefineKey', formatter:workFlowAuditScript.hiddenColumnFormat,hidden:'true'" >隐藏</th> 
                    <th data-options="field:'delegaterIds', formatter:workFlowAuditScript.hiddenColumnFormat,hidden:'true'" >隐藏</th> 
                     <th data-options="field:'appointAuditorId', formatter:workFlowAuditScript.hiddenColumnFormat,hidden:'true'" >隐藏</th> 
                     <th data-options="field:'taskNodeType', formatter:workFlowAuditScript.hiddenColumnFormat,hidden:'true'" >隐藏</th>
				  </tr>
            </thead> 
                </table> 
       
      <div style="text-align:center;"    class="dialog-button" id="workFlowFormToolbar">
      <a href="javascript:void(0)"  style="list-style:none;float:left;text-align:left" class="easyui-linkbutton"    data-options="iconCls:'icon-tip',plain:true"
		 onclick="workFlowAuditScript.readProcessDefinitionResource(this)">查看流程图</a>
   	<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return workFlowAuditScript.submitForm(this);" >确认提交</a>
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
		  $('#choseNextTaskAuditorGrid').datagrid( ); 
		  workFlowAuditScript.nextAuditLogList= <ts:toJSONStr name='form.nextAuditLogList'/> ;
		   
	 });  
	 function modalDialogLoadEvent(){ 
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
	
  </script>
 </form>   
   
 
  </body>
  
</html>

