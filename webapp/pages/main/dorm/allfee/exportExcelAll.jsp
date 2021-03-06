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
<form action="${contextPath}/main/${appReqeustContext.appService}/stream/exportExcelAll.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
	<input name="actionType" id="actionType" type="hidden" value="insert"/>
	<div id="pagePanel" title="" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
          		<td  nowrap><label   for="yearMonth">选择月份<span style="color:red">*</span></label></td>
				<td><input name="yearMonth" id="yearMonth" class="easyui-my97" style="width:126px;height:28px" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"/></td>
		   		<td nowrap><label   for="buildingId">所属楼栋</label></td>
	      		<td>
	      			<select name="buildingId"  id="buildingId" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='buildingList' insertEmpty='1' value="" />
					</select>
	      		</td>
		   	</tr >
		</table>
	</div> 
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return allFeeScript.submitFormOnExportExcel(this);">确定</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
    </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/AllFee.js?v1712072100"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/locale/easyui-lang-zh_CN.js"></script> 
<script type="text/javascript">
    var allFeeScript = new AllFeeScript();  
	function modalDialogLoadEvent() {
		allFeeScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
	} 
</script>      
</form> 
</body>
</html>