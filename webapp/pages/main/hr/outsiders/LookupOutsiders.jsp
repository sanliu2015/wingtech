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
<div style="padding:10px;padding-left:2%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
		        <tr  >					
					<td nowrap><label id="number-label" for="bean.outsidersName">姓名</label></td>
					<td ><input name="bean.outsidersName"  id="bean.outsidersName"  class="easyui-textbox"  style="width:200px;height:30px"></input></td> 					
					<td nowrap><label id="number-label" for="bean.number">编号</label></td>
				    <td style="width:260px"><input name="bean.number"  id="bean.number"  class="easyui-textbox"   style="width:200px;height:30px"></input></td>  
				</tr>
		        <tr  >
					<td nowrap><label id="number-label" for="bean.mobile">手机号码</label></td>
					<td ><input name="bean.mobile"  id="bean.mobile"  class="easyui-textbox"  style="width:200px;height:30px"></input></td>
					<td nowrap><label id="number-label" for="bean.email">电子邮箱</label></td>
					<td ><input name="bean.email"  id="bean.email"  class="easyui-textbox"  style="width:200px;height:30px"></input></td> 
		        </tr>
		        <tr  >					
					<td nowrap><label id="number-label" for="bean.companyName">公司名称</label></td>
					<td ><input name="bean.companyName"  id="bean.companyName"  class="easyui-textbox"  style="width:200px;height:30px"></input></td> 
					<td nowrap><label id="number-label" for="bean.positionName">职务</label></td>
					<td ><input name="bean.positionName"  id="bean.positionName"  class="easyui-textbox"  style="width:200px;height:30px"></input></td>  
		        </tr>
		        <tr  >					
					<td nowrap><label id="number-label" for="bean.address">公司地址</label></td>
					<td ><input name="bean.address"  id="bean.address"  class="easyui-textbox"  style="width:200px;height:30px"></input></td> 
		            <td nowrap><label  for="bean.companyCust">是否为本公司客户</label></td>
				    <td><input type="radio" name="bean.companyCust" value="1"  checked />是
                        <input type="radio" name="bean.companyCust"  value="0"   />否</td>				
		        </tr>
		</table> 
</div> 
<div style="text-align:center;padding:5px; width:98%" class="dialog-button">  
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
 </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/Outsiders.js?version=1.1"></script> 
 
<script type="text/javascript">
var outsidersScript=new OutsidersScript();
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