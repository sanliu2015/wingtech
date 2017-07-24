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
 <form action="${contextPath}/main/${appReqeustContext.appService}/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
  <input name="bean.id" type="hidden"  /> 
<div id="pagePanel" title="资产维护" class="easyui-panel" width="100%"  style="padding:10px;">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
         <tr  >
				<td  nowrap><label   for="bean.name">名称</label></td>
				<td><input name="bean.name" id="bean.name" class="easyui-textbox"  style="width:150px;height:30px"   /></input></td>
				
				<td nowrap><label id="number-label" for="bean.price">单价</label></td>
				<td><input name="bean.price" id="bean.price"  class="easyui-textbox"  style="width:150px;height:30px"/></td>												
	     </tr >
         <tr  >
			    <td nowrap><label for="bean.assetType">类别</label></td>
			    
			    <td>
	      			<select name="bean.assetType"  id="bean.assetType" class="easyui-combobox" data-options="editable:false" style="width:150px;height:30px">   
                		<ts:forEach name='assetTypeList' insertEmpty='0'/>
					</select>
	      		</td>    
			    
			    
			    
			  
			    <td nowrap><label for="bean.specifications">规格</label></td>
			    <td ><input name="bean.specifications" id="bean.size" class="easyui-textbox"  style="width:150px;height:30px"/></input>
			    
         </tr >
         <tr  >
         	 	
			   <td nowrap><label   for="bean.description">备注</label></td>
			    <td colspan="3"><input name="bean.description" id="bean.description" class="easyui-textbox"  style="width:150px;height:30px"/></td>								
			   
		 </tr >
		 
		</table> 
</div> 
  <div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return boardroomScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div> 

<script type="text/javascript" src="<ts:base ref='path'/>/AssetMaintenance.js"></script> 
<script type="text/javascript">
var boardroomScript = new BoardroomScript();  
function modalDialogLoadEvent() {
	
	boardroomScript.initPage({contextPath:"${contextPath}",appKey:"${appReqeustContext.appKey}"}); 
} 
</script>      
</form> 
</body>
</html>