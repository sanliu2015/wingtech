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
 <form action="${contextPath}/main/departmentService/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
<div style="padding:10px;padding-left:20%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
			<tr  >
				<td nowrap><label id="number-label" for="bean.number">部门编号</label></td>
				<td style="width:260px"><input name="bean.number"  id="bean.number"  class="easyui-textbox"   style="width:180px;height:30px"></input></td> 
			</tr> 
            
            <tr  >
                <td  nowrap><label   for="bean.name">部门名称*</label></td>
				<td><input name="bean.name" id="bean.name" class="easyui-textbox"   value=""style="width:180px;height:30px"  /></input></td>
           </tr> 
            <tr  >
                <td  nowrap><label   for="bean.company.id">所属公司</label></td>
				<td> <select name="bean.company.id"   id="bean.company.id"    class="easyui-combobox"  style="width:180px;height:30px" >   
                                <ts:forEach name='companyList'/>
							</select></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.principal">负责人</label></td>
				<td><input name="bean.principal"  class="easyui-textbox"   style="width:250px;height:30px"   /></input><a href="javascript:void(0)" class="easyui-linkbutton"  plain='true' iconCls="icon-search" onClick="return departmentScript.chooseEmployee(this);">选择</a>
                <input name="bean.principalId"  id="bean.principalId" type="hidden"  /></td>
           </tr>  
           <tr  >
                <td  nowrap><label   for="bean.principal">联系电话</label></td>
				<td><input name="bean.contactPhone"  class="easyui-textbox"   style="width:250px;height:30px"   /></input></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.fax">传真</label></td>
				<td><input name="bean.fax"   class="easyui-textbox"  style="width:180px;height:30px"  /></input></td>
           </tr> 
			<tr>
				<td nowrap><label  for="bean.isMenu">是否为生产部</label></td>
				<td><input type="radio" name="bean.productDept" value="1"   />是
                <input type="radio" name="bean.productDept"  value="0"  checked/>否</td> 
			</tr>
            
            <tr  >
                <td  nowrap><label   for="bean.parentName">上级部门名称</label></td>
				<td><input name="bean.parentId" id="bean.parentId"  value="${form.bean.parentId}" class="easyui-textbox"  style="width:180px;height:30px"  /></input>  </td>
           </tr> 
            <tr  >
            <td  nowrap><label   for="bean.divisionKind">部门类型</label></td>
				<td> <select name="bean.divisionKind"  id="bean.divisionKind" class="easyui-combobox"  data-options="editable:false"  style="width:180px;height:30px" > 
                              <ts:forEach name='divisionKindList' />
							</select></td>
                    </tr> 
           <tr  >
                <td  nowrap><label   for="bean.fax">顺序号</label></td>
				<td><input name="bean.itemIndex"   class="easyui-numberspinner"  style="width:80px;height:30px"  /></input></td>
           </tr>  
			<tr  >
				<td nowrap><label   for="bean.description">备注</label></td>
				<td  ><input class="easyui-textbox" name="bean.description"   data-options="multiline:true" style="height:50px;width:300px"></input></td>
			</tr>
		</table>
</div> 
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return departmentScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/Department.js"></script> 
<script type="text/javascript">
    var departmentScript=new DepartmentScript();  
	function modalDialogLoadEvent() {
		$('#bean\\.company\\.id').combobox('disableTextbox',{stoptype:'readOnly',activeTextArrow:true,stopArrowFocus:true});
		departmentScript.loadDepartmentCombotree(); 
		$.ts.EasyUI.setComboboxText($("#bean\\.company\\.id")); 
	}
	$(function() { 
	   departmentScript.initPage({ appKey:"${appReqeustContext.appKey}"});    
	   
	}); 
	 
</script>      
</form> 


</body>
</html>