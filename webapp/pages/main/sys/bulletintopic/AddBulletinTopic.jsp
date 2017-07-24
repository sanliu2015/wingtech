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
 <form action="${contextPath}/main/${appReqeustContext.appService}/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
   <input name="bean.id" type="hidden"  />  
   <div class="error" style="color: white; position: absolute; float: left; background-color: rgb(209, 72, 54); z-index: 100; margin-left: 91px; margin-top: -21px; font-size: 16px;"> </div>
<div style="padding:10px;padding-left:20%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
			<tr  >
				<td nowrap><label id="number-label" for="bean.number">编号</label></td>
				<td style="width:260px"><input name="bean.number"  id="bean.number"  class="easyui-textbox"   style="width:180px;height:30px"></input></td> 
			</tr> 
            
            <tr  >
                <td  nowrap><label   for="bean.name">栏目名称*</label></td>
				<td><input name="bean.name" id="bean.name" class="easyui-textbox"   value="" style="width:180px;height:30px"  /></input></td>
           </tr> 
           <td  nowrap><label   for="bean.topicKind">栏目类别*</label></td>
				<td  > <select name="bean.topicKind"    class="easyui-combobox" data-options="editable:false"  style="width:180px;height:30px">   
                                <ts:forEach name='topicKindList'  value=''/>
							</select></td>                 
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
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return bulletinTopicScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/BulletinTopic.js"></script> 
<script type="text/javascript">
    var bulletinTopicScript=new BulletinTopicScript();   
	$(function() { 
	    bulletinTopicScript.initPage({ appKey:"${appReqeustContext.appKey}"});    
	     
	});  
	  
</script>      
</form>  
</body>
</html>