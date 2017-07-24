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
<body id="${appReqeustContext.appKey}Body">  
 <form action="${contextPath}/main/${appReqeustContext.appService}/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
   <input name="bean.id" type="hidden"  />
  <div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div>
   <input name="actionType" id="actionType" type="hidden" value="lookup"/> 
    <input name="bean.employeeCategory" id="bean.employeeCategory" type="hidden" /> 
<div style="padding:10px;padding-left:2%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
		<tr>	
		          <td nowrap><label for="bean.number">单据编号*</label></td>
				  <td><input name="bean.number"  id="bean.number" value="${form.bean.number }"  readonly class="easyui-textbox"   style="width:200px;height:30px"></input></td>  								   
			      <td nowrap><label for="bean.sendCompanyName">派遣公司名称</label></td>
			      <td style="width:260px"><input name="bean.sendCompanyName"  id="bean.sendCompanyName"  class="easyui-textbox"  style="width:200px;height:30px"></input></td> 
			    </tr>	
			    <tr  >	
			        <td nowrap><label for="bean.sendCompanyAddress">派遣公司地址</label></td>
					<td style="width:260px"><input name="bean.sendCompanyAddress"  id="bean.sendCompanyAddress"  class="easyui-textbox"  style="width:200px;height:30px"></input></td> 
					<td nowrap><label for="bean.sendCompanyPhone">派遣公司电话</label></td>
					<td style="width:260px"><input name="bean.sendCompanyPhone"  id="bean.sendCompanyPhone"  class="easyui-textbox"  style="width:200px;height:30px"></input></td> 
			    </tr>
			    <tr  >				
					<td nowrap><label for="bean.sendCompanyContact">派遣公司联系人</label></td>
					<td style="width:260px"><input name="bean.sendCompanyContact"  id="bean.sendCompanyContact"  class="easyui-textbox"  style="width:200px;height:30px"></input></td> 
					<td nowrap><label for="bean.sendCompanyEmail">派遣公司邮件</label></td>
					<td style="width:260px"><input name="bean.sendCompanyEmail"  id="bean.sendCompanyEmail"  class="easyui-textbox"  style="width:200px;height:30px"></input></td> 
               </tr>		
		</table> 
</div> 
<div style="text-align:center;padding:5px; width:98%" class="dialog-button">  
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
 </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/Interim.js?version=1.1"></script> 
 
<script type="text/javascript">
var interimScript=new InterimScript();
var formJson=null;
var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
formJson=jQuery.parseJSON(jsonContentObj.text());
formJson.recordOperateStatus="lookup";
$('#${appReqeustContext.appKey}Form').form('tsLoad',formJson);  
function modalDialogLoadEvent() {   
	 $('#${appReqeustContext.appKey}Form').form('tsLoad',formJson);
	 
}
</script> 
</form>
</body>
</html>