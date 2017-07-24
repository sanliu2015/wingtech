<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts"%>
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 <title>添加${appReqeustContext.appName}</title>  
</head>
<body id="${appReqeustContext.appKey}Body"   >  
<form action="${contextPath}/main/${appReqeustContext.appService}/json/doOpenClose.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
<input name="bean.id" type="hidden"  /> 
<div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
	<div id="pagePanel" class="easyui-panel" width="100%"  style="padding:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
        	<tr>
          		<td  nowrap><label   for="bean.name">房间名称<span style="color:red">*</span></label></td>
				<td><input name="bean.name" id="bean.name" class="easyui-textbox" style="width:150px;height:30px"  /></td>
				<td  nowrap><label   for="bean.number">房间编号<span style="color:red">*</span></label></td>
				<td><input name="bean.number" id="bean.number" class="easyui-textbox"   value=""style="width:150px;height:30px"  /></td>
		   		<td nowrap><label   for="bean.disabled">状态</label></td>
	      		<td>
	      			<select name="bean.disabled"  id="bean.disabled" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='disabledList' insertEmpty='1' />
					</select>
	      		</td>
		   	</tr >
		</table>
	</div> 
 	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<c:if test="${form.bean.disabled==1}">
 			<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-ok"  onClick=" return roomScript.submitFormOnOpCs(this);">启用</a>  
        </c:if>
        <c:if test="${form.bean.disabled!=1}">
 			<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-no"  onClick=" return roomScript.submitFormOnOpCs(this);">停用</a>  
        </c:if>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div>
<script type="text/javascript" src="<ts:base ref='path'/>/Room.js"></script> 
<script type="text/javascript">
	var roomScript=new RoomScript();  
	var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
	var formJson=jQuery.parseJSON(jsonContentObj.text()); 
	$(function(){ 
		jsonContentObj.remove();  
		$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
		roomScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
		$(":input").attr("readonly","readonly");
	});
	 
</script>      
</form> 
</body>
</html>