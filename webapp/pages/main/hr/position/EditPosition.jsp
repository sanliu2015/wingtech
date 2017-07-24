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
 <form action="${contextPath}/main/positionService/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
  <input name="bean.id" type="hidden" value="${form.bean.id}"/> 
 <div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
<div style="padding:10px;padding-left:20%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
			<tr  >
				<td nowrap><label id="number-label" for="bean.number">职位编号</label></td>
				<td style="width:260px"><input name="bean.number"  id="bean.number"  class="easyui-textbox"   style="width:180px;height:30px"></input></td> 
			</tr> 
            
            <tr  >
                <td  nowrap><label   for="bean.name">职位名称*</label></td>
				<td><input name="bean.name" id="bean.name" class="easyui-textbox"   value=""style="width:180px;height:30px"  /></input></td>
           </tr> 
            <tr  >
                <td  nowrap><label   for="bean.parentName">上级职位</label></td>
				<td><input name="bean.parentName" id="bean.parentName"    class="easyui-textbox"  style="width:180px;height:30px"  /></input>
                <input name="bean.parentId" id="bean.parentId" type="hidden"  /> </td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.itemIndex">顺序号</label></td>
				<td><input name="bean.itemIndex"   class="easyui-numberspinner"  style="width:80px;height:30px"  /></input></td>
           </tr>  
            <tr>
				<td nowrap><label  for="bean.status">状态</label></td>
				<td><input type="radio" name="bean.status" value="1"  checked />启用
                <input type="radio" name="bean.status"  value="0"  />停用</td> 
			</tr> 
			<tr  >
				<td nowrap><label   for="bean.description">备注</label></td>
				<td  ><input class="easyui-textbox" name="bean.description"   data-options="multiline:true" style="height:50px;width:300px"></input></td>
			</tr>
		</table> 
  </div>
   <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return positionScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.closeDialog(this)">关闭</a> 
     </div> 
      
</form>
<script type="text/javascript" src="<ts:base ref='path'/>/Position.js"></script> 
<script type="text/javascript">
    var positionScript=new PositionScript();  
	$(function() { 
	   var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
	   var formJson=jQuery.parseJSON(jsonContentObj.text()); 
	   jsonContentObj.remove(); 
	   formJson.bean.parentName=formJson.bean.parentId; 
	   $('#${appReqeustContext.appKey}Form').form('tsLoad',formJson);  
	   positionScript.initPage({appKey:"${appReqeustContext.appKey}"}); 
	  
	});
	function modalDialogLoadEvent() { 
		positionScript.loadPositionCombotree(); 
	}
	 
	
</script>
</body>
</html>