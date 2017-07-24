<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 <title>添加${appReqeustContext.appName}</title>   
</head>
<body id="${appReqeustContext.appKey}Body"   >  
 <form action="${contextPath}/core/${appReqeustContext.appService}/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
 <div style="display:none" id="copyDialogContent"></div> 
   <input name="bean.id" type="hidden"  /><input name="bean.status" type="hidden"  value="1" />
   <input name="actionType" id="actionType" type="hidden" value="add"/>  
<div style="padding:10px;padding-left:5%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table"> 
            <tr  >
                <td  nowrap><label   for="bean.topicKind">信息类别*</label></td>
				<td> <select name="bean.topicKind"  id="bean.topicKind" class="easyui-combobox"  data-options="editable:false"  style="width:180px;height:30px" > 
                                <ts:forEach name='subscribeCodeList'  insertEmpty='0'/>
							</select></td>
                   <td  nowrap><label   for="bean.bulletinTopicId">栏目</label></td>
				<td> <select name="bean.bulletinTopicId"  id="bean.bulletinTopicId" class="easyui-combobox"  data-options="editable:false,valueField:'id',textField:'text'"  style="width:180px;height:30px" > 
							</select></td>
            </tr> 
          <tr  >
            <td  nowrap colspan="4"> 
           <div id="insertEmployeeToolbar"   >
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="subscribeScript.chooseEmployees(this);">批量添加</a>  
		</div></td>
        </tr> 
            <tr  id="employeeIdsTr" receiverAttr="employee"  >
                 <td  nowrap colspan="4">  
				<table class="tablestyle" style="width: 100%; text-align: center;" id="employeeReceiverListTable">
					<tr class="haveborder" align="center">
						<td class="alt">操作</td>
						<td class="alt">序号</td>
						<td class="alt">工号</td>
						<td class="alt">姓名</td>
						<td class="alt">部门</td>
                        <td class="alt">公司</td>
					</tr>
					<tr class="haveborder" cloneRowFlag="1"  align="center" style="display:none">
						<td><a href="javascript:void(0)" class="easyui-linkbutton" deleteRecordOperate="1" data-options="iconCls:'icon-cancel',plain:true"
		    onclick="$.ts.Utils.deleteDialogChooseRow(this,subscribeScript.chooseEmployeesOptions)">删除</a></td>
						<td><span name="tableRowNum">1</span></td>
						<td><input type="text" class="text" name="employeeReceiverList.number" readonly/>  </td>
						<td> <input type="text" class="text"   name="employeeReceiverList.name" readonly/>  
							 <input type="hidden" class="text"  name="employeeReceiverList.receiverId"/>
                            <input type="hidden" class="text"  name="employeeReceiverList.employeeId"/></td>
						<td><input type="text" class="text" name="employeeReceiverList.deptName" readonly/> 
							<input type="hidden" class="text"  name="employeeReceiverList.deptId"/>
						</td>
                        <td><input type="text" class="text"   name="employeeReceiverList.companyName" readonly/> 
							<input type="hidden" class="text"  name="employeeReceiverList.orgId"/>
                            <input type="hidden" class="text"  name="employeeReceiverList.recordOperateStatus" value="add"/>
                            <input type="hidden" class="text"  name="employeeReceiverList.id"/>
						</td>
					</tr> 
				</table>  
		</td>
       
           </tr> 
		</table> 
</div> 
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return subscribeScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/Subscribe.js?version=1.1"></script> 
 
<script type="text/javascript">
    var subscribeScript=new SubscribeScript();  
	$(function() { 
	   subscribeScript.initPage({appKey:"${appReqeustContext.appKey}"});  
	   
	});  
	 
</script>      
</form> 


</body>
</html>