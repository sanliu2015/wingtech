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
	 
     <div id="workFlowRefBillForm"     style="width:98%;padding:2px;"   >
     <IFRAME ID="workFlowRefBillIframe" Name="workFlowRefBillIframe" onload="workFlowAuditScript.dyniframesize('workFlowRefBillIframe')" FRAMEBORDER=0 SCROLLING=AUTO width=100%  height=100% SRC="${contextPath}${form.workFlowAuditLog.url}"></IFRAME>
	</div>
    <div id="pagePanel" class="easyui-panel"   title="当前审核信息"  data-options="collapsible:true" width="98%" > 
     <table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
			<tr  >
				<td nowrap><label  for="workFlowAuditLog.name">任务标题</label></td>
				<td  ><input name="workFlowAuditLog.taskTitle"  id="workFlowAuditLog.taskTitle"  class="easyui-textbox"  style="width:250px;height:25px"  value="${form.workFlowAuditLog.taskTitle}"   readonly></input></td>  
               <td nowrap><label  for="workFlowAuditLog.processName">工作流名称</label></td>
				<td  ><input name="workFlowAuditLog.processName"  id="workFlowAuditLog.processName"  class="easyui-textbox"  style="width:250px;height:25px"  value="${form.workFlowAuditLog.processName}" readonly></input></td> 
           </tr> 
           <tr  >
				<td nowrap><label  for="workFlowAuditLog.assignmentName">审核人</label></td>
				<td  ><input name="workFlowAuditLog.assignmentName"  id="workFlowAuditLog.assignmentName"  class="easyui-textbox"  style="width:250px;height:25px"  value="${form.workFlowAuditLog.assignmentName}"   readonly></input></td>  
               <td nowrap><label  for="workFlowAuditLog.taskName">审核节点</label></td>
				<td  ><input name="workFlowAuditLog.taskName"  id="workFlowAuditLog.taskName"  class="easyui-textbox"  style="width:250px;height:25px"  value="${form.task.name}" readonly></input></td> 
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
				   <input type="radio" name="workFlowAuditStatus" id="workFlowAuditStatus" value="6" onClick="workFlowAuditScript.setAuditStatusValue()" alt="暂缓审核"  title="提交人员不能修改该单据，审核人员后续可继续审核"/>暂缓审核
				    </td> 
				</tr> 
				<tr   >  						 		 
				   <td nowrap="nowrap"> <label  for="workFlowAuditStatus">审核意见</label>  <span class="bitian">*</span>    </td> 
				  <td  class="txtBoxnoborder" colspan="3" id="signSealPostionArea"> 
                  <input class="easyui-textbox" name="workFlowAuditLog.auditComments" id="workFlowAuditLog.auditComments" data-options="multiline:true" style="height:50px;width:500px"></input>  
				    
				  </td>          
				  </tr>  
        </table>
     </div>
     <table align="center"    id="choseNextTaskAuditorGrid" title="选择下一层审核人员"  style="width:98%;height:auto;border:1px solid #ccc;"  data-options="rownumbers:true,collapsible:true,selectOnCheck:true,checkOnSelect:true,singleSelect:false">
       <thead> 
            <tr   >    
                    <th data-options="field:'ck',checkbox:true"></th>
					<th data-options="field:'delegaterKindName',width:150, formatter:workFlowAuditScript.textInputFormat">审核对象类型</td>
					<th data-options="field:'assignmentName',width:250,formatter:workFlowAuditScript.textAndSearchInputFormat">审批人</td>
					<th data-options="field:'taskName',width:200, formatter:workFlowAuditScript.textInputFormat">节点名称</td> 
                    <th data-options="field:'taskDefineKey',hidden:'true'" >隐藏</td> 
				  </tr>
            </thead>
				 <c:if test="${!empty form.nextAuditLogList}">
                  <c:forEach items="${form.nextAuditLogList}" var="obj"  varStatus="stat"> 	
				  <tr class="bgwhiteleft"  >  
                    <td nowrap="nowrap" width="150px"> </td> 
					<td nowrap="nowrap" width="150px"><input name="nextAuditLogList.delegaterKindName" id="bean.number" class="easyui-textbox"   value="${obj.delegaterKindName}" style="width:100%;height:25px"  readonly /></input></td> 
					<td nowrap="nowrap" width="150px"><input name="nextAuditLogList.delegaterKindName" id="bean.number" class="easyui-textbox"   value="${obj.assignmentName}" style="width:100%;height:25px"  readonly /></input></td> 
					<td nowrap="nowrap" width="150px"><input name="nextAuditLogList.delegaterKindName" id="bean.number" class="easyui-textbox"   value="${obj.taskName}" style="width:100%;height:25px"  readonly /></input></td>  
					<td  ><input name="nextAuditLogList.taskDefineKey" type="hidden" value="${obj.taskDefineKey}" /></td> 
				  </tr>
				 </c:forEach>
                 </c:if>
                </table> 
       <div id="choseNextTaskAuditorToolbar"  >
		<a href="javascript:void(0)" class="easyui-linkbutton" id="insertOmOrderDtlRowId" data-options="iconCls:'icon-add',plain:true"
		 onclick="workFlowAuditScript.insertNextTaskAuditorGridRow(this)">添加</a>&nbsp;&nbsp; 
	</div>
      <div style="text-align:center;"    class="dialog-button" id="workFlowFormToolbar">
   	<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return workFlowAuditScript.submitForm(this);" >确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a> 
     </div>  
    <script type="text/javascript" src="<ts:base ref='path'/>/WorkFlowAudit.js"></script>  
  <script language="javascript">
     var workFlowAuditScript=new WorkFlowAuditScript();  
	 $(function() {  
    	  workFlowAuditScript.setAuditStatusValue();
		  $('#choseNextTaskAuditorGrid').datagrid( );
		  var formJson=jQuery.parseJSON("<ts:toJSONStr name='form.nextAuditLogList'");
	 });  
	 function modalDialogLoadEvent(){ 
	    
		 $.ts.EasyUI.titleAppendToolbar("choseNextTaskAuditorToolbar","choseNextTaskAuditorGrid");
	}
	
  </script>
 </form>   
   
 
  </body>
  
</html>

