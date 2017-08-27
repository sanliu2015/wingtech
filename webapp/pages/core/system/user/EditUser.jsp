<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 <title>修改${appReqeustContext.appName}</title>   
</head>
<body id="${appReqeustContext.appKey}Body"   >  
 <form action="${contextPath}/core/${appReqeustContext.appService}/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
   <input name="bean.id" type="hidden"  /> 
   <input name="bean.loginKind" type="hidden"  /> 
   <div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
<div style="padding:10px;padding-left:20%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
			<tr  >
				<td nowrap><label id="number-label" for="bean.name">用户名*</label></td>
				<td  ><input name="bean.name"  id="bean.name"  class="easyui-textbox"   style="width:180px;height:30px"></input>
               
                </td> 
			</tr>  
            <tr  >
                <td  nowrap><label   for="password">密码*</label></td>
				<td><input name="password" id="password" class="easyui-textbox"   readonly type="password"    value="********" style="width:180px;height:30px"  /></input>重置密码<input name="status" id="status" type="checkbox" value="1"  onClick="userScript.resetPasword(this)"  ></td>
           </tr> 
           <tr   id="newPasswordContainer" style="display:none">
                <td  nowrap><label   for="bean.password">新密码*</label></td>
				<td><input name="bean.password" id="bean.password" class="easyui-textbox"   type="password"  style="width:180px;height:30px"  /></input> 
                <label   for="bean.confirmPassword">确认密码*</label><input name="bean.confirmPassword" id="bean.confirmPassword" class="easyui-textbox"   type="password"  style="width:180px;height:30px"  /></input></td>
           </tr> 
           <!-- <tr>
				<td nowrap><label  for="bean.loginKind">用户对象</label></td>
				<td><input type="radio" name="bean.loginKind"     value="0"/>员工
                <input type="radio" name="bean.loginKind"  value="1"/>客户</td> 
			</tr> -->
            <tr  id="loginKindEmployeeId" >
                <td  nowrap><label   for="bean.employeeName">员工姓名</label></td>
				<td><input name="bean.employeeName"   id="bean.employeeName"  class="easyui-textbox" readonly  style="width:125px;height:30px"></input><a href="javascript:void(0)" class="easyui-linkbutton" plain=true iconCls="icon-search" onClick="return userScript.chooseEmployee(this);">选择</a> <label id="number-label" for="bean.departmentName">部门&nbsp;&nbsp;</label><input name="bean.departmentName"  id="bean.departmentName"  class="easyui-textbox"   readonly style="width:180px;height:30px"></input> <input name="bean.employeeId" id="bean.employeeId" type="hidden"  /></td>
           </tr> 
           <tr  id="loginKindCustId"  style="display:none">
                <td  nowrap><label   for="bean.custName">客户名称*</label></td>
				<td><input name="bean.custName"   id="bean.custName"  class="easyui-textbox" readonly  style="width:180px;height:30px"></input><a href="javascript:void(0)" class="easyui-linkbutton"  plain=true iconCls="icon-search" onClick="return userScript.chooseCustomer(this);">选择</a>  <input name="bean.custId" id="bean.custId" type="hidden"  /></td>
           </tr>
            <tr  >
           <td  nowrap><label   for="bean.securityLevel">用户类别</label></td>
				<td> <select name="bean.userLevel"  id="bean.userLevel" class="easyui-combobox" data-options="editable:false"   style="width:180px;height:30px" > 
                                <ts:forEach name='userLevelList' value='${form.bean.userLevel}'/>
							</select></td>
            </tr> 
           <tr  >
                <td  nowrap><label   for="bean.name">选择功能角色</label></td>
				<td><input name="bean.roleIds"  id="bean.roleIds" class="easyui-combotree"   data-options='multiple:true,data:<ts:forEach name="roleList" toJson="1"/>'  style="width:400px;height:30px">  
							</input></td>
           </tr>  
           <tr  >
                <td  nowrap><label   for="bean.positionRoleIds">用户岗位角色</label></td>
				<td><input name="bean.positionRoleIds"  id="bean.positionRoleIds" class="easyui-combotree"   data-options='multiple:true,data:<ts:forEach name="positionRoleList" toJson="1"/>'  style="width:400px;height:30px"> </td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.companyIds">公司权限 </label></td>
				<td><input name="bean.companyIds"  id="bean.companyIds" class="easyui-textbox"   data-options='multiple:true,data:<ts:forEach name="companyList" insertEmpty="1" toJson="1"/>'  style="width:400px;height:30px">
                  </td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.deptIds">部门权限</label></td>
				<td><input name="bean.deptIds" id="bean.deptIds" class="easyui-textbox"   style="width:400px;height:30px"></input>   </td>
           </tr>
           <tr  >
                <td  nowrap><label for="bean.employeeIds">人员权限</label></td>
				<td><input name="bean.employeeNames" id="bean.employeeNames" class="easyui-textbox"   readonly style="width:400px;height:30px"></input><a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return userScript.chooseEmployees(this);">选择</a><input name="bean.employeeIds" id="bean.employeeIds" type="hidden"    /></td>
           </tr>
           <tr>
				<td nowrap><label  for="bean.status">状态</label></td>
				<td><input type="radio" name="bean.status"   checked value="1"/>启用
                <input type="radio" name="bean.status"  value="0"/>停用</td> 
			</tr>  
			<tr  >
				<td nowrap><label   for="bean.description">备注</label></td>
				<td  ><input class="easyui-textbox" name="bean.description"    style="height:30px;width:400px"></input></td>
			</tr>
		</table>
</div> 
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return userScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/User.js?version=1.1"></script> 
<script type="text/javascript">
    var userScript=new UserScript();  
	$(function() { 
	   var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
	   var formJson=jQuery.parseJSON(jsonContentObj.text());  
	   if(formJson.bean!=null)
	     formJson.bean.password="";
	   jsonContentObj.remove(); 
	   $('#${appReqeustContext.appKey}Form').form('tsLoad',formJson);   
	   userScript.initPage({appKey:"${appReqeustContext.appKey}"});   
	});  
	 
</script>      
</form> 


</body>
</html>