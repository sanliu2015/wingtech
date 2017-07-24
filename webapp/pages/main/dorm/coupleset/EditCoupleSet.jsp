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
<form action="${contextPath}/main/${appReqeustContext.appService}/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
	<div id="pagePanel" class="easyui-panel" width="100%"  style="padding:0px">
	<table id="dtlListGrid" class="easyui-datagrid" data-options="rownumbers:true,toolbar:'#dtlListGridToolbar'">
		<thead>
			<tr>
				<th data-options="field:'operateField',width:40,formatter:coupleSetScript.removeRowFormat">操作</th>
                <th data-options="field:'orgEmpName',width:150, formatter:coupleSetScript.orgEmpNameFormat">姓名<span style="color:red">*</span></th>
				<th data-options="field:'orgEmpNumber',width:150,formatter:coupleSetScript.textInputReadonlyFormat">工号</th>
				<th data-options="field:'orgEmpSex',width:50,formatter:coupleSetScript.textInputReadonlyFormat">性别</th>
				<th data-options="field:'desEmpName',width:150, formatter:coupleSetScript.desEmpNameFormat">实际承担费用<br>人员姓名<span style="color:red">*</span></th>
				<th data-options="field:'desEmpNumber',width:150,formatter:coupleSetScript.textInputReadonlyFormat">实际承担费用<br>人员工号</th>
				<th data-options="field:'desEmpSex',width:50,formatter:coupleSetScript.textInputReadonlyFormat">性别</th>
				<th data-options="field:'description',width:250, formatter:coupleSetScript.textInputFormat">备注</th>                    
				<th data-options="field:'recordOperateStatus',width:10, formatter:coupleSetScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'id',width:10, formatter:coupleSetScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'orgEmpId',width:10, formatter:coupleSetScript.hiddenColumnFormat,hidden:true"></th> 
				<th data-options="field:'desEmpId',width:10, formatter:coupleSetScript.hiddenColumnFormat,hidden:true"></th> 
			</tr>
		</thead>
	</table> 
	</div> 
	<div id="dtlListGridToolbar"  >
        <a href="javascript:void(0)" class="easyui-linkbutton" id="insertDtlRowId" data-options="iconCls:'icon-add',plain:true" onclick="coupleSetScript.appendRow(this);">增加</a>
	</div> 
	</div> 
    
 	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return coupleSetScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div>
<style>
.datagrid-row {
        height: 27px;
    }
</style>      
<script type="text/javascript" src="<ts:base ref='path'/>/CoupleSet.js"></script> 
<script type="text/javascript">
    var coupleSetScript=new CoupleSetScript();   
    var dtlList = ${dtlList};
	$(function() { 
	   coupleSetScript.initPage({ appKey:"${appReqeustContext.appKey}"});    
	}); 
	function modalDialogLoadEvent() {   
		for(var i=0;i<dtlList.length;i++) { 
			var tmp = {"dtlList" : dtlList[i]}; 
			coupleSetScript.insertDatagridRow(tmp);   
		}  
		//coupleSetScript.initPage({ appKey:"${appReqeustContext.appKey}"});    
	} 
</script>      
</form> 
</body>
</html>