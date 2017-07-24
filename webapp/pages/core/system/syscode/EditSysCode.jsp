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
<body id="${appReqeustContext.appKey}Body">  
 <form action="${contextPath}/core/${appReqeustContext.appService}/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
  <input name="bean.id" type="hidden"  />  
      <input name="bean.codeKind"  id="bean.codeKind"  type="hidden"  value="109" /> 
  <div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
<div style="padding:10px;padding-left:2%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
			<tr  >
				<td nowrap><label id="number-label" for="bean.code">代码</label></td>
				<td style="width:260px"><input name="bean.code"  id="bean.code"  class="easyui-textbox"   style="width:180px;height:30px"></input></td> 
			</tr>  
            <tr  >
                <td  nowrap><label   for="bean.name">名称*</label></td>
				<td><input name="bean.name" class="easyui-textbox"   value=""style="width:180px;height:30px"  /></input></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.urlName">顺序</label></td>
				<td><input name="bean.itemIndex"  class="easyui-numberspinner"  style="width:180px;height:30px"  /></input></td>
           </tr>  
           <tr>
				<td nowrap><label  for="bean.status">状态</label></td>
				<td><input type="radio" name="bean.status"   value="1"/>启用
                <input type="radio" name="bean.status"  value="0"/>停用</td> 
			</tr>  
			<tr  >
				<td nowrap><label   for="bean.description">备注</label></td>
				<td  ><input class="easyui-textbox" name="bean.description"   data-options="multiline:true" style="height:50px;width:300px"></input></td>
			</tr>
		</table>
</div> 
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return roleScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/${appReqeustContext.messageResource.viewFileSuffix}.js"></script> 
<script type="text/javascript">
    var roleScript=new RoleScript();  
	$(function() { 
	   var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
	   var formJson=jQuery.parseJSON(jsonContentObj.text());  
	   jsonContentObj.remove(); 
	   $('#${appReqeustContext.appKey}Form').form('tsLoad',formJson);   
	   roleScript.initPage({appKey:"${appReqeustContext.appKey}"});   
	});  
	
</script>      
</form> 


</body>
</html>