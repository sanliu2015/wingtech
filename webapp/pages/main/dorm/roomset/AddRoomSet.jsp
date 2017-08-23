<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
<title>${appReqeustContext.appName}</title> 
</head>
<body id="${appReqeustContext.appKey}Body">  
<form action="${contextPath}/main/${appReqeustContext.appService}/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
	<input name="actionType" id="actionType" type="hidden" value="insert"/>
	<div id="pagePanel" title="" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
          		<td  nowrap><label   for="bean.endDay">每月截止日<span style="color:red">*</span></label></td>
				<td><input name="bean.endDay" id="bean.endDay" class="easyui-numberspinner" style="height:30px;width:130px;" value="25" data-options="max:28,formatter:myFormamter"/></td>
				<td nowrap><label   for="bean.sharedFee">公摊费</label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.sharedFee"  id="bean.sharedFee" data-options="min:0,precision:2"  style="width:150px;height:30px"></td>
		   	</tr >
		   	<tr>
		   		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="3"><input class="easyui-textbox" name="bean.description" style="height:30px;width:300px;"></td>
		   	</tr>
		</table>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return roomSetScript.submitForm(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/RoomSet.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/locale/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript">
    var roomSetScript = new RoomSetScript();  
	function modalDialogLoadEvent() {
		roomSetScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	} 
</script>      
</form> 
</body>
</html>