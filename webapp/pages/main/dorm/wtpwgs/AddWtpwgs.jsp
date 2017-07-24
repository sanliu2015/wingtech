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
	<input name="bean.roomId" id="bean.roomId" type="hidden" />
	<div id="pagePanel" title="基本信息" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
          		<td  nowrap><label   for="bean.yearMonth">月份<span style="color:red">*</span></label></td>
				<td><input name="bean.yearMonth" id="bean.yearMonth" class="textbox" style="width:148px;height:30px" onclick="WdatePicker({dateFmt:'yyyy-MM'})" /></td>
				<td  nowrap><label   for="bean.buildName">楼栋</label></td>
				<td><input name="bean.buildName" id="bean.buildName" class="easyui-textbox"  style="width:150px;height:30px" readonly /></td>
				<td  nowrap><label   for="bean.roomName">房间<span style="color:red">*</span></label></td>
				<td>
					<input name="bean.roomName" id="bean.roomName" class="easyui-textbox"  style="width:150px;height:30px" readonly />
					<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return wtpwgsScript.chooseRoom(this);"  plain='true'></a>
					<a href="javascript:void(0)" onClick="return wtpwgsScript.getLastResult(this);"  plain='true'>上次抄数</a>
				</td>
		   	</tr >
          	<tr>
			   	<td nowrap><label   for="bean.lastWaterNum">上月用水抄数<span style="color:red">*</span></label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.lastWaterNum" id="bean.lastWaterNum" data-options="min:0,precision:2" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="bean.thisWaterNum">本月用水抄数<span style="color:red">*</span></label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.thisWaterNum" id="bean.thisWaterNum" data-options="min:0,precision:2" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="bean.waterPrice">水费单价<span style="color:red">*</span></label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.waterPrice" id="bean.waterPrice" data-options="min:0,precision:2" style="width:150px;height:30px" /></td>
	      	</tr>
	      	<tr>
			   	<td nowrap><label   for="bean.lastPowerNum">上月用电抄数<span style="color:red">*</span></label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.lastPowerNum" id="bean.lastPowerNum" data-options="min:0,precision:2" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="bean.thisPowerNum">本月用电抄数<span style="color:red">*</span></label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.thisPowerNum" id="bean.thisPowerNum" data-options="min:0,precision:2" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="bean.powerPrice">电费单价<span style="color:red">*</span></label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.powerPrice" id="bean.powerPrice" data-options="min:0,precision:2" style="width:150px;height:30px" /></td>
	      	</tr>
	      	<tr>
			   	<td nowrap><label   for="bean.lastGasNum">上月燃气抄数</label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.lastGasNum" id="bean.lastGasNum" data-options="min:0,precision:2" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="bean.thisGasNum">本月燃气抄数</label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.thisGasNum" id="bean.thisGasNum" data-options="min:0,precision:2" style="width:150px;height:30px" /></td>
			   	<td nowrap><label   for="bean.gasPrice">燃气单价</label></td>
			   	<td  ><input class="easyui-numberbox" name="bean.gasPrice" id="bean.gasPrice" data-options="min:0,precision:2" style="width:150px;height:30px" /></td>
	      	</tr>
	      	
	      	<tr>
	      		<td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="5" ><input class="easyui-textbox" name="bean.description" style="height:30px;width:500px"></td>
	      	</tr>  
		</table>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return wtpwgsScript.submitForm(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/Wtpwgs.js"></script> 
<script type="text/javascript">
    var wtpwgsScript = new WtpwgsScript();  
	function modalDialogLoadEvent() {
		wtpwgsScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	} 
</script>      
</form> 
</body>
</html>