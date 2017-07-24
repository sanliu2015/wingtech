<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 <title>修改密码</title>   
</head>
<body id="${appReqeustContext.appKey}Body"   >  
 <form action="${contextPath}/core/${appReqeustContext.appService}/json/modifyUserPassword.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
   <input name="bean.id" type="hidden"  value="${form.bean.id}" />  
    <input name="bean.loginKind" type="hidden" id="bean.loginKind"    value="${form.bean.loginKind}" />  
    <input name="bean.custName" type="hidden"  id="bean.custName" value="${form.bean.custName}" />  
<div style="padding:10px;padding-left:20%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
            <tr  >
				<td nowrap><label  for="bean.name">用户名*</label></td>
				<td  ><input name="bean.name"  id="bean.name" type="text" class="easyui-textbox" value="${form.bean.name}"   style="width:180px;height:30px"></input> 
                </td> 
			</tr> 
			<tr  >
				<td nowrap><label  for="bean.name">原密码*</label></td>
				<td  ><input name="bean.oldPassword"  id="bean.oldPassword" type="password" class="easyui-textbox"   style="width:180px;height:30px"></input> 
                </td> 
			</tr>  
            <tr  >
                <td  nowrap><label   for="bean.name">新密码*</label></td>
				<td><input name="bean.password" id="bean.password" class="easyui-textbox"   type="password"  style="width:180px;height:30px"  /></input></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.confirmPassword">确认新密码*</label></td>
				<td><input name="bean.confirmPassword" id="bean.confirmPassword" class="easyui-textbox"   type="password"  style="width:180px;height:30px"  /></input></td>
           </tr> 
            
		</table>
</div> 
 <div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return userScript.submitModifyPasswordForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/User.js?version=1.2"></script> 
<script type="text/javascript">
    var userScript=new UserScript();  
	$(function() {      
	   userScript.initPage({appKey:"${appReqeustContext.appKey}"});   
	});  
	 
</script>      
</form> 


</body>
</html>