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
 <form action="${contextPath}/main/knowledgeTypeService/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
   <input name="knowType.id" type="hidden" value="${form.knowType.id }" />  
 <div class="error" style="color: white; position: absolute; float: left; background-color: rgb(209, 72, 54); z-index: 100; margin-left: 91px; margin-top: -21px; font-size: 16px;"> </div>
<div style="padding:10px;padding-left:20%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
            <tr  >
                <td  nowrap><label   for="knowType.typeName">类别名称*</label></td>
				<td><input name="knowType.typeName" id="knowType.typeName" class="easyui-textbox"   value="${form.knowType.typeName}" style="width:180px;height:30px"  /></input></td>
           </tr>
           <tr> 
           		<td  nowrap><label   for="knowType.knowledgeTypeId">上级节点*</label></td>
				<td  > 
					<select name="knowType.knowledgeTypeId"    class="easyui-combobox" data-options="editable:false"  style="width:180px;height:30px">   
                    	<option value="0">请选择上级节点</option>
                    	<c:forEach items="${form.typeList }" var="obj">
                    		<c:if test="${obj.id==form.knowType.knowledgeTypeId }">
                    			<option value="${obj.id }" selected="selected">${obj.typeName}</option>
                    		</c:if>
                    		<c:if test="${obj.knowledgeTypeId!=form.knowType.knowledgeTypeId }">
                    			<option value="${obj.id }">${obj.typeName}</option>
                    		</c:if>
                    	</c:forEach>
					</select>
				</td>                 
            </tr>   
           <tr>
				<td nowrap><label  for="knowType.status">状态</label></td>
				<td><input type="radio" name="knowType.status" value="1"  checked />启用
                <input type="radio" name="knowType.status"  value="0"  />停用</td> 
			</tr> 
			<tr  >
				<td nowrap><label   for="knowType.description">备注</label></td>
				<td  ><input class="easyui-textbox" name="knowType.description" value="${form.knowType.description }"  data-options="multiline:true" style="height:50px;width:300px"></input></td>
			</tr>
		</table>
</div> 
<div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return knowledgeTypeScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/KnowledgeType.js"></script> 
<script type="text/javascript">
    var knowledgeTypeScript=new KnowledgeTypeScript();   
	$(function() { 
		knowledgeTypeScript.initPage({ appKey:"${appReqeustContext.appKey}"});    
	     
	});  
	  
</script>      
</form>  
</body>
</html>