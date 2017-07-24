<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 <title>添加模块</title>
</head>
<body id="${appReqeustContext.appKey}Body"   >  
 <form action="${contextPath}/main/companyService/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
 <input name="bean.id" type="hidden" value="${form.bean.id}"/> 
 <div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
<div style="padding:10px;padding-left:5%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
			<tr  >
				<td nowrap><label id="number-label" for="bean.number">编号</label></td>
				<td style="width:260px"><input name="bean.number"    class="easyui-textbox"  style="width:250px;height:30px"  ></input></td>  
                <td  nowrap><label   for="bean.name">公司名称*</label></td>
				<td><input name="bean.name" id="bean.name"    class="easyui-textbox"    style="width:250px;height:30px"   /></input></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.name">简称</label></td>
				<td><input name="bean.forShort"    class="easyui-textbox"   style="width:250px;height:30px"  /></input></td>
           
                <td  nowrap><label   for="bean.name">英文名字</label></td>
				<td><input name="bean.nameEn"     class="easyui-textbox"   style="width:250px;height:30px"  /></input></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.principal">负责人</label></td>
				<td><input name="bean.principal"  id="bean.principal"    class="easyui-textbox"   style="width:250px;height:30px"   /></input><a href="javascript:void(0)" class="easyui-linkbutton"  plain='true' iconCls="icon-search" onClick="return companyScript.chooseEmployee(this);">选择</a>
                <input name="bean.principalId"  id="bean.principalId" type="hidden"  /></td>
                </td>
            
                <td  nowrap><label   for="bean.addr">单位地址</label></td>
				<td><input name="bean.addr"    class="easyui-textbox"   style="width:250px;height:30px"  /></input></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.urlName">邮政编码</label></td>
				<td><input name="bean.postCode"      class="easyui-textbox"  style="width:180px;height:30px"  /></input> </td>
            
                <td  nowrap><label   for="bean.principal">联系电话</label></td>
				<td><input name="bean.contactPhone"      class="easyui-textbox"   style="width:250px;height:30px"   /></input></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.fax">传真</label></td>
				<td><input name="bean.fax"     class="easyui-textbox"  style="width:180px;height:30px"  /></input></td>
            
                <td  nowrap><label   for="bean.securityLevel">组织类型</label></td>
				<td> <select name="bean.companyType"   class="easyui-combobox" data-options="editable:false"   style="width:180px;height:30px" >   
                                <ts:forEach name='companyTypeList' value='${form.bean.companyType}'/>
							</select></td>
           </tr> 
           <tr  >
           	    <td  nowrap><label   for="bean.accountName">账户名</label></td>
				<td><input name="bean.accountName"   class="easyui-textbox"  style="width:180px;height:30px"  /></input></td>
                 <td  nowrap><label   for="bean.bank">开户行</label></td>
				<td><input name="bean.bank"   class="easyui-textbox"  style="width:180px;height:30px"  /></input></td>
                
           </tr> 
           <tr  >  
           	 <td  nowrap><label   for="bean.bank">银行账号</label></td>
				<td><input name="bean.accountNo"   class="easyui-textbox"  style="width:180px;height:30px"  /></input></td>
           </tr>   
			<tr  >
				<td nowrap><label   for="bean.description">备注</label></td>
				<td   colspan="3"><input class="easyui-textbox" name="bean.description" value="${form.bean.description}"  data-options="multiline:true" style="height:50px;width:400px"></input></td>
			</tr>
		</table>
</div> 
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return companyScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/Company.js?version=3.3"></script> 
<script type="text/javascript">
    var companyScript=new CompanyScript();  
	$(function() { 
	   companyScript.initPage({appKey:"${appReqeustContext.appKey}"});  
	   var jsonContentObj=$("#${appReqeustContext.appKey}FormJson");
	   var formJson=jQuery.parseJSON(jsonContentObj.text()); 
	   jsonContentObj.remove();
	   $('#${appReqeustContext.appKey}Form').form('tsLoad',formJson);  
	    
	});
	 
	 
</script>      
</form> 


</body>
</html>