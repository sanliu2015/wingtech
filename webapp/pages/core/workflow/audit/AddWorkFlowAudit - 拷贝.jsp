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
 <title>工作流审核</title>    
  </head>
<body id="${appReqeustContext.appKey}Body"  >  
<form id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" action="${contextPath}/core/${appReqeustContext.appService}/json/save.do" method="post"  >
   <input name="workFlowAuditLog.id" type="hidden"  /> 
   <input name="workFlowAuditLog.auditStatus" id="workFlowAuditLog.auditStatus" type="hidden"  value="1"/> 
   <input name="workFlowAuditLog.taskId" id="workFlowAuditLog.taskId" type="hidden"  value="${form.workFlowAuditLog.taskId}"/> 
   <input name="workFlowAuditLog.processInstanceId" id="workFlowAuditLog.processInstanceId"  type="hidden"  value="${form.workFlowAuditLog.processInstanceId}"/> 
   <input name="workFlowAuditLog.workFlowConfigureFile" id="workFlowAuditLog.workFlowConfigureFile"  type="hidden"  value="${form.workFlowAuditLog.workFlowConfigureFile}"/> 
	 <input name="workFlowAuditLog.businessKey" id="workFlowAuditLog.businessKey"  type="hidden"  value="${form.workFlowAuditLog.businessKey}"/> 
     <input name="workFlowAuditLog.processDefinitionKey" id="workFlowAuditLog.processDefinitionKey"  type="hidden"  value="${form.workFlowAuditLog.processDefinitionKey}"/> 
     <input name="workFlowAuditLog.taskNodeType" id="workFlowAuditLog.taskNodeType"  type="hidden"  value="${form.workFlowAuditLog.taskNodeType}"/>
     <input name="workFlowAuditLog.taskRowType" id="workFlowAuditLog.taskRowType"  type="hidden"  value="${form.workFlowAuditLog.taskRowType}"/>  
     
      <input name="workFlowAuditLog.canModifyBill" id="workFlowAuditLog.canModifyBill"  type="hidden"  value="${form.workFlowAuditLog.canModifyBill}"/>  
      <input name="workFlowAuditLog.editUrl" id="workFlowAuditLog.editUrl" type="hidden"  value="${form.workFlowAuditLog.editUrl}" />  
     <div id="workFlowRefBillForm"     style="width:98%;padding:2px;"   >
     <IFRAME ID="workFlowRefBillIframe" Name="workFlowRefBillIframe" onload="workFlowAuditScript.dyniframesize('workFlowRefBillIframe')" FRAMEBORDER=0 SCROLLING=AUTO width=100%  height=100% SRC="${contextPath}${form.workFlowAuditLog.url}"></IFRAME>
	</div>
    <div id="currentAuditContentPanel" class="easyui-panel"   title="当前审核信息"  data-options="collapsible:true" width="98%" > 
     <table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
			<tr    style="display:none">
				<td nowrap><label  for="workFlowAuditLog.name">任务标题</label></td>
				<td  ><input name="workFlowAuditLog.taskTitle"  id="workFlowAuditLog.taskTitle"  class="easyui-textbox"  style="width:250px;height:25px"  value="${form.workFlowAuditLog.taskTitle}"   readonly></input></td>  
               <td nowrap><label  for="workFlowAuditLog.processName">工作流名称</label></td>
				<td  ><input name="workFlowAuditLog.processName"  id="workFlowAuditLog.processName"  class="easyui-textbox"  style="width:250px;height:25px"  value="${form.workFlowAuditLog.processName}" readonly></input></td> 
           </tr> 
           <tr  style="display:none">
				<td nowrap><label  for="workFlowAuditLog.assignmentName">审核人</label></td>
				<td  ><input name="workFlowAuditLog.assignmentName"  id="workFlowAuditLog.assignmentName"  class="easyui-textbox"  style="width:250px;height:25px"  value="${form.workFlowAuditLog.assignmentName}"   readonly></input></td>  
               <td nowrap><label  for="workFlowAuditLog.taskName">审核节点</label></td>
				<td  ><input name="workFlowAuditLog.taskName"  id="workFlowAuditLog.taskName"  class="easyui-textbox"  style="width:250px;height:25px"  value="${form.workFlowAuditLog.taskName}" readonly></input></td> 
           </tr> 
           <tr>   	  
	                  <td nowrap> <label  for="workFlowAuditStatus">审核结果</label><span class="bitian">*</span> </td>
					  <td  class="txtBoxnoborder" nowrap="nowrap" style="font-size:16px" colspan="3" > 
					    <input type="radio" name="workFlowAuditStatus" id="workFlowAuditStatus" value="1"  checked="checked" onClick="workFlowAuditScript.setAuditStatusValue()" alt="同意"/>同意
					     &nbsp;&nbsp;&nbsp;&nbsp;
				   <input type="radio" name="workFlowAuditStatus" id="workFlowAuditStatus" value="3" onClick="workFlowAuditScript.setAuditStatusValue()"  alt="不同意"  title="提交人员不能该修改"/> 不同意
				    &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="workFlowAuditStatus" id="workFlowAuditStatus" value="12" onClick="workFlowAuditScript.setAuditStatusValue()" alt="撤回重改" title="提交人员可重新修改后，继续提交审核"/>撤回重改
				    &nbsp;&nbsp;&nbsp;&nbsp; 
				    </td> 
				</tr> 
				<tr   >  						 		 
				   <td nowrap="nowrap"> <label  for="workFlowAuditStatus">审核意见</label>  <span class="bitian">*</span>    </td> 
				  <td  class="txtBoxnoborder" colspan="3" id="signSealPostionArea"> 
                  <input class="easyui-textbox" name="workFlowAuditLog.description" id="workFlowAuditLog.description" data-options="multiline:true" style="height:50px;width:500px"></input>  
				    
				  </td>          
				  </tr>  
        </table>
     </div>
      <div id="currentTaskAuditorToolbar"  >
		<a href="javascript:void(0)" class="easyui-linkbutton"   data-options="iconCls:'icon-tip',plain:true"
		 onclick="workFlowAuditScript.readProcessDefinitionResource(this)">流程跟踪</a>&nbsp;&nbsp; 
	</div>
    <div  >
     <table align="center"  class="easyui-datagrid"  id="choseNextTaskAuditorGrid" title="选择下一层审核人员"  style="width:98%;height:auto;border:1px solid #ccc;"  data-options="rownumbers:true,collapsible:true,selectOnCheck:false,checkOnSelect:false,singleSelect:false">
       <thead> 
            <tr   >    
                    <th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'delegaterKindName',width:150, formatter:workFlowAuditScript.textInputFormat,hidden:'true'">审核对象类型</th>
					<th data-options="field:'assignmentName',width:250,formatter:workFlowAuditScript.textInputFormat">审批人</th>
                    <th data-options="field:'appointAuditor',width:150, formatter:workFlowAuditScript.textAndSearchInputFormats">变更审核人</th>
					<th data-options="field:'taskName',width:200, formatter:workFlowAuditScript.textInputFormat">节点名称</td> 
                    <th data-options="field:'taskDefineKey', formatter:workFlowAuditScript.hiddenColumnFormat,hidden:'true'" >隐藏</th> 
                    <th data-options="field:'delegaterIds', formatter:workFlowAuditScript.hiddenColumnFormat,hidden:'true'" >隐藏</th> 
                     <th data-options="field:'appointAuditorId', formatter:workFlowAuditScript.hiddenColumnFormat,hidden:'true'" >隐藏</th>
                     <th data-options="field:'taskNodeType', formatter:workFlowAuditScript.hiddenColumnFormat,hidden:'true'" >隐藏</th>
                     <th data-options="field:'id', formatter:workFlowAuditScript.hiddenColumnFormat,hidden:'true'" >隐藏</th>
				  </tr>
            </thead> 
         </table> 
        </div>
       <div id="choseNextTaskAuditorToolbar" style="display:none"  >
		<a href="javascript:void(0)" class="easyui-linkbutton" id="insertOmOrderDtlRowId" data-options="iconCls:'icon-add',plain:true"
		 onclick="workFlowAuditScript.appendNextTaskAuditorGridRow(this)">添加</a>&nbsp;&nbsp; 
	</div>
     <c:if test="${form.canAddSubscriber=='1'}">
     <div  >
     <table align="center"  class="easyui-datagrid"  id="subscribeLogListGrid" title="选择查阅人员"  style="width:98%;height:auto;border:1px solid #ccc;"  data-options="rownumbers:true,collapsible:true,selectOnCheck:false,checkOnSelect:false,singleSelect:false">
       <thead> 
            <tr   >    
                   <th data-options="field:'operateField',width:40,formatter:workFlowAuditScript.subscriberRemoveRowFormat">操作</th> 
					<th data-options="field:'appointAuditor',width:250,formatter:workFlowAuditScript.subscriberTextInputFormat">查阅人姓名</th>
                    <th data-options="field:'deptName',width:150, formatter:workFlowAuditScript.subscriberTextInputFormat">部门</th> 
                    <th data-options="field:'departmentId', formatter:workFlowAuditScript.subscriberHiddenColumnFormat,hidden:'true'" >隐藏</th> 
                    <th data-options="field:'delegaterIds', formatter:workFlowAuditScript.subscriberHiddenColumnFormat,hidden:'true'" >隐藏</th> 
                    <th data-options="field:'companyId', formatter:workFlowAuditScript.subscriberHiddenColumnFormat,hidden:'true'" >隐藏</th> <th data-options="field:'assignmentName', formatter:workFlowAuditScript.subscriberHiddenColumnFormat,hidden:'true'" >隐藏</th> 
                     <th data-options="field:'appointAuditorId', formatter:workFlowAuditScript.subscriberHiddenColumnFormat,hidden:'true'" >隐藏</th>
                     <th data-options="field:'taskNodeType', formatter:workFlowAuditScript.subscriberHiddenColumnFormat,hidden:'true'" >隐藏</th>
                      <th data-options="field:'id', formatter:workFlowAuditScript.subscriberHiddenColumnFormat,hidden:'true'" >隐藏</th>
                      <th data-options="field:'recordOperateStatus',width:10, formatter:workFlowAuditScript.subscriberHiddenColumnFormat,hidden:true">id</th> 
				  </tr>
            </thead> 
         </table> 
         </div>
         <div id="subscriberToolbar"  >
		<a href="javascript:void(0)" class="easyui-linkbutton" id="insertSubscriberDtlRowId" data-options="iconCls:'icon-add',plain:true"
		 onclick="workFlowAuditScript.chooseSubscribers(this)">添加查阅人</a>&nbsp;&nbsp; 
	</div> 
     </c:if>
      <div style="text-align:center;"    class="dialog-button" id="workFlowFormToolbar">
      
   	<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return workFlowAuditScript.submitForm(this);" >确定审核</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a> 
    <c:if test="${form.workFlowAuditLog.canModifyBill=='1'}">
    &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-edit"  onClick=" return workFlowAuditScript.modifyBillForm(this);"  >修改单据</a>
    </c:if>
     </div>  
    <script type="text/javascript" src="<ts:base ref='path'/>/WorkFlowAudit.js"></script>  
  <script language="javascript">
     var workFlowAuditScript=new WorkFlowAuditScript();   
	 workFlowAuditScript.subscribeLogList=null;
	 var canAddSubscriber="${form.canAddSubscriber}";
	 workFlowAuditScript.canAddSubscriber=canAddSubscriber;
	 $(function() {  
	      workFlowAuditScript.initPage({appKey:"${appReqeustContext.appKey}"});  
    	  
		  $('#choseNextTaskAuditorGrid').datagrid( ); 
		  workFlowAuditScript.nextAuditLogList= <ts:toJSONStr name='form.nextAuditLogList'/> ;
		    $("#choseNextTaskAuditorGrid").hide();
	      if(canAddSubscriber=="1"){
			   workFlowAuditScript.subscribeLogList= <ts:toJSONStr name='form.subscribeLogList'/> ;
		  }
	 });  
	 function modalDialogLoadEvent(){ 
	     var nextList=workFlowAuditScript.nextAuditLogList;
	     if(nextList!=null && nextList.length>0){
			 for(var i=0;i<nextList.length;i++){ 
			   var rowBean=nextList[i];
			   rowBean.recordOperateStatus="insert";
			   var  row=workFlowAuditScript.insertNextTaskAuditorGridRow();   
			   var tmp={"nextAuditLogList":rowBean,"recordOperateStatus":"insert"};
			   row.tsLoadData(tmp);   
			 } 
			 $('#choseNextTaskAuditorGrid').datagrid("checkRow",0);
		 } else {
			  $("#choseNextTaskAuditorGrid").parent().parent().parent().hide();
			  
		 }
		  var subscribeLogList=workFlowAuditScript.subscribeLogList;
		  
		  if(subscribeLogList!=null && subscribeLogList.length>0){
			 for(var i=0;i<subscribeLogList.length;i++){ 
			   var rowBean=subscribeLogList[i];
			   rowBean.recordOperateStatus="update";
			   var  row=workFlowAuditScript.insertSubscribeDatagridRow();   
			   var tmp={"subscribeLogList":rowBean,"recordOperateStatus":"lookup"};
			   row.tsLoadData(tmp);   
			 } 
		 }
		// $.ts.EasyUI.titleAppendToolbar("choseNextTaskAuditorToolbar","choseNextTaskAuditorGrid");
		 $.ts.EasyUI.titleAppendToolbar("currentTaskAuditorToolbar","currentAuditContentPanel");
		 if(canAddSubscriber=="1"){
		 	$.ts.EasyUI.titleAppendToolbar("subscriberToolbar","subscribeLogListGrid");  
			workFlowAuditScript.setChooseEmployeeOptions(); 
		 }
		 workFlowAuditScript.setAuditStatusValue();
		 
	}
	
  </script>
 </form>   
   
 
  </body>
  
</html>

