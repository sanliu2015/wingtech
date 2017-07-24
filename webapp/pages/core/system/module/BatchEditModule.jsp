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
 <form action="${contextPath}/core/moduleService/json/updateChildNodes.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
  <input name="bean.id" type="hidden"  /> 
 <div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
<div id="pagePanel" class="easyui-panel"    data-options="collapsible:true" width="100%" >  
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
			<tr  >
				<td nowrap><label id="number-label" for="bean.number"><ts:lang name='bean.number'/></label></td>
				<td style="width:260px"><input name="bean.number"   class="easyui-textbox"   readonly  style="width:180px;height:30px"></input></td> 
			 
             
                <td  nowrap><label   for="bean.name"><ts:lang name='bean.name'/>*</label></td>
				<td><input name="bean.name"  class="easyui-textbox"    readonly style="width:180px;height:30px"  /></input></td>
           
                <td  nowrap><label   for="bean.itemIndex">菜单顺序</label></td>
				<td><input name="bean.itemIndex"  class="easyui-numberspinner" readonly style="width:180px;height:30px"  /></input></td>
           </tr>
           <tr  >
                <td  nowrap><label   for="bean.appServiceName">服务名称</label></td>
				<td><input name="bean.appServiceName"  class="easyui-textbox"  readonly style="width:180px;height:30px"  /></input></td> 
			 
				<td nowrap><label   for="bean.urlName">url</label></td>
				<td  colspan="3" ><input class="easyui-textbox" name="bean.urlName" id="bean.urlName" readonly  style="height:30px;width:400px"></input></td>
			</tr>
		</table>
	</div>  
 <div  class="blankLine"></div>
 <table id="omOrderDtlListGrid"   class="easyui-datagrid" style="width:100%;height:auto;"
		title="子节点"  data-options="collapsible:true,singleSelect:true">
			<thead>
				<tr>
					<th data-options="field:'operateField',width:50, formatter:moduleScript.removeRowFormat">操作</th>
                    <th data-options="field:'name',width:100, formatter:moduleScript.textInputFormat">名称</th>
					<th data-options="field:'urlName',width:350, formatter:moduleScript.textInputFormat">url</th> 
					<th data-options="field:'itemIndex', width:80, formatter:moduleScript.numberspinnerFormat">菜单顺序</th> 
					<th data-options="field:'reportFileName',width:150, formatter:moduleScript.textInputFormat">查询列表文件</th> 
                    <th data-options="field:'isMenu',width:80, formatter:moduleScript.comboboxYesNoFormat">是否为菜单</th>  
					<th data-options="field:'authCheck',width:80, formatter:moduleScript.comboboxYesNoFormat">需要权限验证</th>  
                    <th data-options="field:'securityLevel',width:80, formatter:moduleScript.comboboxSecurityLevelFormat">菜单级别</th> 
                    <th data-options="field:'status',width:80, formatter:moduleScript.comboboxStatusFormat">状态</th>
					<th data-options="field:'recordOperateStatus',width:10, formatter:moduleScript.hiddenColumnFormat,hidden:true">id</th>  
                     <th data-options="field:'id',width:10, formatter:moduleScript.hiddenColumnFormat,hidden:true">id</th> 
				</tr>
			</thead>
		</table> 
   <div id="omOrderDtlListToolbar"  >
		<a href="javascript:void(0)" class="easyui-linkbutton" id="insertOmOrderDtlRowId" data-options="iconCls:'icon-add',plain:true"
		 onclick="moduleScript.insertDatagridRow()">添加</a>&nbsp;&nbsp; 
	</div>
   <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return moduleScript.submitChildNodesForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a> 
     </div> 
      

<script type="text/javascript" src="<ts:base ref='path'/>/Module.js?version=1.4"></script>  
<script type="text/javascript">
    var moduleScript=new ModuleScript();  
	moduleScript.yesOrNoList='<ts:forEach name="yesOrNoList" toJson="1"/>';
	moduleScript.securityLevelList='<ts:forEach name="securityLevelList" toJson="1"/>';
	moduleScript.statusList='<ts:forEach name="statusList" toJson="1"/>'; 
	var jsonContentObj=$("#${appReqeustContext.appKey}FormJson");
	   var formJson=jQuery.parseJSON(jsonContentObj.text()); 
	var dtlList=formJson.childNodes;  
	
	$(function() { 
	    
	   $('#${appReqeustContext.appKey}Form').form('tsLoad',formJson);  
	   moduleScript.initPage({ appKey:"${appReqeustContext.appKey}"});  
	});
	function modalDialogLoadEvent(){  
		$.ts.EasyUI.titleAppendToolbar("omOrderDtlListToolbar","omOrderDtlListGrid"); 
		
		 for(var i=0;i<dtlList.length;i++){ 
		   var rowBean=dtlList[i]; 
		   var  row=moduleScript.insertDatagridRow();  
		   var tmp={"childNodes":rowBean};
		   row.tsLoadData(tmp);   
		 }  
	}

</script>
</form> 
</body>
</html>