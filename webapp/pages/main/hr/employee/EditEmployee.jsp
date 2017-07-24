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
 <form action="${contextPath}/main/${appReqeustContext.appService}/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post" enctype="multipart/form-data">  
 <input name="bean.id" type="hidden"  /> 
  <input name="bean.thirdSysId" type="hidden"  /> 
 <div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
<div style="padding:10px;padding-left:10px">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
            <tr  >
                <td  nowrap><label   for="bean.company.id">公司</label></td>
				<td> <select name="bean.company.id"   id="bean.company.id"    class="easyui-combobox"  style="width:180px;height:30px" >   
                                <ts:forEach name='companyList' value=''/>
							</select></td>
                
                <td  nowrap><label   for="bean.parentName">部门</label></td>
				<td><input name="bean.dept.id" id="bean.dept.id"    class="easyui-combotree"  style="width:180px;height:30px"  /></input>  </td>
            <td  nowrap><label   for="bean.positionName">职位</label></td>
				<td><input name="bean.positionName" id="bean.positionName"    class="easyui-textbox"  style="width:180px;height:30px"  /></input>
                <input name="bean.positionId" id="bean.positionId" type="hidden"   /> </td>
                <td nowrap><label id="number-label" for="bean.number">工号</label></td>
				<td style="width:260px"><input name="bean.number"  id="bean.number"  class="easyui-textbox"   style="width:180px;height:30px"></input></td>  
           </tr> 
			<tr  >
				
                <td  nowrap><label   for="bean.name">姓名*</label></td>
				<td><input name="bean.name" id="bean.name" class="easyui-textbox"   value=""style="width:180px;height:30px"  /></input></td>
                <td nowrap><label  for="bean.gender">性别</label></td>
				<td><input type="radio" name="bean.gender" value="1"   />男
                <input type="radio" name="bean.gender"  value="0"   />女</td> 
                 <td  nowrap><label   for="bean.nameEn">英文名</label></td>
				<td><input name="bean.nameEn" id="bean.nameEn" class="easyui-textbox"   value=""style="width:180px;height:30px"  /></input></td>
                <td  nowrap colspan="2" rowspan="5"> 
                 <fieldset style="height:170px; width:130px"><legend>上传照片</legend>
                 <div id="preview">
                    <img id="imghead" width="100%" height="100%" border=0 src="${contextPath}/upload/employee/${form.bean.idCard}.jpg"  >
                </div>  
                 </fieldset> 
                 <br/>
                 <input type="file" name="photoFile" onchange="employeeScript.previewImage(this)" /> </td>
           </tr>    
			 <tr  >
                <td  nowrap><label   for="bean.idCard">身份证号码</label></td>
				<td><input name="bean.idCard"  class="easyui-textbox"   style="width:180px;height:30px" data-options="onChange:employeeScript.idCardChangeEvent"  /></input></td>
            
                <td  nowrap><label   for="bean.birthday">出生日期</label></td>
				<td><input name="bean.birthday"   class="easyui-my97" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:150px;height:30px"   /></input></td>
           
            <td  nowrap><label   for="bean.securityLevel">学历</label></td>
				<td  > <select name="bean.degree"  id="bean.degree" class="easyui-combobox"  style="width:180px;height:30px">   
                                <ts:forEach name='degreeList' value='${form.bean.degree}'/>
							</select></td>
             </tr> 
           <tr  >
                <td  nowrap><label   for="bean.phone">联系电话</label></td>
				<td><input name="bean.phone"   class="easyui-textbox"  style="width:180px;height:30px"  /></input></td>
                 
            
                <td  nowrap><label   for="bean.email">邮箱</label></td>
				<td><input name="bean.email"   class="easyui-textbox"  style="width:180px;height:30px"  /></input></td>
                 <td  nowrap><label   for="bean.outDate">离职日期</label></td>
				<td><input name="bean.outDate"    class="easyui-my97" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:150px;height:30px"  /></input></td>
                 
           </tr>  
           <tr  >
           <td  nowrap><label   for="bean.birthday">入司日期</label></td>
				<td><input name="bean.inDate"   class="easyui-my97" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:150px;height:30px"   /></input></td>
             <td  nowrap><label   for="bean.addr">联系地点</label></td>
			 <td  ><input name="bean.addr"   class="easyui-textbox"  style="width:180px;height:30px"  /></input></td>          
               <td  nowrap><label   for="bean.workYears">工作年限</label></td>
				<td><input name="bean.workYears"   class="easyui-numberbox"  data-options="precision:0" style="width:120px;height:30px"  /></input>(年)</td>
             </tr>
              <tr>
                 <td  nowrap><label   for="bean.workAaddr">工作地点</label></td>
				<td><input name="bean.workAaddr"   class="easyui-textbox"  style="width:180px;height:30px"  /></input></td>
                <td  nowrap><label   for="bean.icNo">考勤卡号</label></td>
				<td><input name="bean.icNo"   class="easyui-textbox"  style="width:180px;height:30px"  /></input></td> 
                 <td  nowrap><label   for="bean.cardDate">发卡日期</label></td>
				<td><input name="bean.cardDate"    class="easyui-my97" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width:150px;height:30px"  /></input></td>
               </tr> 
              <tr>
                <td  nowrap><label   for="bean.employeeKind">人员类别</label></td>
				<td  > <select name="bean.employeeKind"  id="bean.employeeKind" class="easyui-combobox"  style="width:180px;height:30px">   
                                <ts:forEach name='employeeKindList' value=''/>
							</select></td>
                 <td  nowrap><label   for="bean.employeeCategory">人员分类</label></td>
				<td  > <select name="bean.employeeCategory"  id="bean.employeeCategory" class="easyui-combobox"  data-options="editable:false" style="width:180px;height:30px">   
                                <ts:forEach name='categoryList' value=''/>
							</select></td>
				
                 <td  nowrap><label   for="bean.interimId">劳务派遣公司</label></td>
				<td  > <select name="bean.interimId"  id="bean.interimId" class="easyui-combobox"  style="width:180px;height:30px"  data-options="editable:false">   
                                <ts:forEach name='interimList' value=''/>
							</select></td>
                  <td nowrap><label  for="bean.status">状态</label></td>
				<td><input type="radio" name="bean.status" value="1"    />启用
                <input type="radio" name="bean.status"  value="0"  />停用</td> 
			</tr>  
			<tr  > 
				<td nowrap ><label   for="bean.description">备注</label></td>
				<td  colspan="3"> <input name="bean.description"   class="easyui-textbox"  style="width:380px;height:30px"  /></input></td>
			</tr>
		</table>
</div> 
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return employeeScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/Employee.js"></script> 
<style type="text/css">
#preview{width:130px;height:150px;border:1px solid #000;overflow:hidden;}
#imghead {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
</style>
<script type="text/javascript">
    var employeeScript=new EmployeeScript();  
	function modalDialogLoadEvent() { 
		 
		//employeeScript.loadDepartmentTree(); 
		employeeScript.loadPositionTree();
		$.ts.EasyUI.setComboboxText($("#bean\\.company\\.id")); 
	}
	$(function() {  
	   var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
	   var formJson=jQuery.parseJSON(jsonContentObj.text());  
	   jsonContentObj.remove();  
	  
	   $('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
	    employeeScript.initPage({ appKey:"${appReqeustContext.appKey}"});   
		 employeeScript.loadDeptIdsCombotree(); 
	}); 
	 
</script>      
</form> 


</body>
</html>