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
 <form action="${contextPath}/core/moduleService/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
   <input name="bean.id" type="hidden"  /> 
<div style="padding:5px;padding-left:20%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
			<tr  >
				<td nowrap><label id="number-label" for="bean.number"><ts:lang name='bean.number'/></label></td>
				<td style="width:260px"><input name="bean.number"  id="bean.number"  class="easyui-textbox"   style="width:180px;height:30px"></input></td> 
			</tr>  
            <tr  >
                <td  nowrap><label   for="bean.name"><ts:lang name='bean.name'/>*</label></td>
				<td><input name="bean.name" class="easyui-textbox"   value=""style="width:180px;height:30px"  /></input></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.urlName">菜单顺序</label></td>
				<td><input name="bean.itemIndex"  class="easyui-numberspinner"  style="width:180px;height:30px"  /></input></td>
           </tr>
           <tr  >
                <td  nowrap><label   for="bean.name">服务名称</label></td>
				<td><input name="bean.appServiceName" id="bean.appServiceName" class="easyui-textbox"   value=""style="width:180px;height:30px"  /></input></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.name">查询列表文件</label></td>
				<td><input name="bean.reportFileName" id="bean.reportFileName" class="easyui-textbox"   value=""style="width:180px;height:30px"  /></input></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.urlName"><ts:lang name='bean.urlName'/>*</label></td>
				<td><input name="bean.urlName" id="bean.urlName" class="easyui-textbox"  style="width:500px;height:30px"  /></input> </td>
           </tr>
           <tr  >
                <td  nowrap><label   for="bean.urlName">图标路径</label></td>
				<td><input name="bean.iconName" id="bean.iconName" class="easyui-textbox"  style="width:250px;height:30px"  /></input></td>
           </tr>
			<tr>
				<td nowrap><label  for="bean.isMenu">是否为菜单</label></td>
				<td><input type="radio" name="bean.isMenu"   checked  value="1"/>是
                <input type="radio" name="bean.isMenu"   value="0"/>否</td> 
			</tr>
            <tr>
				<td nowrap><label  for="bean.isMenu">需要权限验证</label></td>
				<td><input type="radio" name="bean.authCheck"   checked value="1"/>是
                <input type="radio" name="bean.authCheck"  value="0"/>否</td> 
			</tr> 
            <tr  >
                <td  nowrap><label   for="bean.parentName">上层节点名称</label></td>
				<td><input name="bean.parentId" id="bean.parentId" class="easyui-textbox"  value="${form.id}" style="width:180px;height:30px"  /></input></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.securityLevel">菜单级别</label></td>
				<td> <select name="bean.securityLevel"  id="bean.securityLevel" class="easyui-combobox"  style="width:180px;height:30px" >   
                                <ts:forEach name='securityLevelList'/>
							</select></td>
           </tr> 
           <tr>
				<td nowrap><label  for="bean.status">状态</label></td>
				<td><input type="radio" name="bean.status"    value="1" checked/>启用
                <input type="radio" name="bean.status"  value="0"/>停用</td> 
			</tr> 
			<tr  >
				<td nowrap><label   for="bean.description">备注</label></td>
				<td  ><input class="easyui-textbox" name="bean.description"   data-options="multiline:true" style="height:50px;width:300px"></input></td>
			</tr>
		</table>
</div> 
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return moduleScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/Module.js?version=3.1"></script> 
<script type="text/javascript">
    var moduleScript=new ModuleScript();  
	 
	$(function() { 
	   moduleScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"});  
	    
	});
	 
	 
	 
</script>      
</form> 


</body>
</html>