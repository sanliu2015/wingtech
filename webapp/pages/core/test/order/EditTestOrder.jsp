<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=2, user-scalable=yes" />  
 <title>添加模块</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
  <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/my97.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">  

<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>   
<script language="javascript" src='${contextPath}/scripts/jquery/mask.js'></script> 
 <script type="text/javascript" src="${contextPath}/scripts/jquery/jquery.form.js"></script>   
 <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/datepicker/WdatePicker.js"></script>
 <script type="text/javascript" src="${contextPath}/scripts/easyui/extEasyUI.js"></script>  
<script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/datepicker/jquery.my97.js"></script>
 
  <script type="text/javascript" src="${contextPath}/scripts/ts/TSCore.js?version=1.2"></script>
</head>
<body id="${appReqeustContext.appKey}Body"  style="overflow:scroll">  
 <form action="${contextPath}/core/testOrderService/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"   > 
<div id="pagePanel" class="easyui-panel" title="基本信息"     data-options="collapsible:true" width="100%"> 
		<table cellpadding="0" cellspacing="0" class="baseForm-table" width="100%">
			<tr  >
				<td nowrap><label  for="bean.number">下单日期</label></td>
				<td  ><input name="bean.orderDate"  id="bean.orderDate"  class="easyui-my97" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:130px;height:30px"></input></td> 
			 
                <td  nowrap><label   for="bean.name">订单编号</label></td>
				<td><input name="bean.number" id="bean.number" class="easyui-textbox"   value="" style="width:150px;height:30px"  /></input></td>
                <td  nowrap><label   for="bean.securityLevel">订单类别</label></td>
				<td> <select name="bean.orderKind"  id="bean.orderKind" class="easyui-combobox"  style="width:150px;height:30px">   
                                <ts:forEach name='orderKindList'/>
							</select></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="bean.custName">客户*</label></td>
				<td><input name="bean.customer.name" id="bean.customer.name" class="easyui-textbox"  style="width:150px;height:30px"  /></input><a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return testOrderScript.chooseCust(this);">选择</a><input name="bean.customer.id" id="bean.customer.id" type="hidden"  /></td>
           
                <td  nowrap><label   for="bean.urlName">客户联系人</label></td>
				<td><input name="bean.custContact" id="bean.custContact" class="easyui-textbox"  style="width:150px;height:30px"  /></input></td>
          
				 <td  nowrap><label   for="bean.urlName"> 联系人电话</label></td>
				<td><input name="bean.custContactPhone" id="bean.custContactPhone" class="easyui-textbox"  style="width:150px;height:30px"  /></input></td>
             </tr> 
			 <tr  >
                <td  nowrap><label   for="bean.parentName">收货地址</label></td>
				<td><input name="bean.orderAddress" id="bean.orderAddress" class="easyui-textbox"  style="width:200px;height:30px"  /></input> </td>
               <td  nowrap><label   for="bean.custName">业务员*</label></td>
				<td><input name="bean.employee.name" id="bean.employee.name" class="easyui-textbox"  style="width:150px;height:30px"  /></input><a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return testOrderScript.chooseEmployee(this);">选择</a> <input name="bean.employee.id" id="bean.employee.id" type="hidden"  /> </td> 
            </tr>
            <tr  >
				<td nowrap><label   for="bean.description">备注</label></td>
				<td  colspan="3" ><input class="easyui-textbox" name="bean.description" id="bean.description" data-options="multiline:true" style="height:50px;width:500px"></input></td>
			</tr>
            
		</table>
	</div>  
 <div  class="blankLine"></div>
<table id="testOrderDtlListGrid"   class="easyui-datagrid" style="width:auto;height:auto;"
		title="产品信息"  data-options="collapsible:true,singleSelect:true,   rowStyler: function(index,row){ return 'height:40'}">
			<thead>
				<tr>
					<th data-options="field:'',width:50,formatter:testOrderScript.removeRowFormat,frozen:true">操作</th>
                    <th data-options="field:'material.number',width:180, formatter:testOrderScript.textAndSearchInputFormat">产品编号</th>
					<th data-options="field:'material.name',width:150, formatter:testOrderScript.textInputFormat">产品名称</th> 
					<th data-options="field:'price', width:100, formatter:testOrderScript.integerInputFormat">单价</th> 
					<th data-options="field:'qty',width:100, formatter:testOrderScript.numericInputFormat">数量</th> 
                    <th data-options="field:'amount',width:100, formatter:testOrderScript.numericInputFormat">金额</th> 
                    <th data-options="field:'deliveryDate',width:100, formatter:testOrderScript.dateInputFormat">要求到货日期</th> 
					<th data-options="field:'description',width:150, formatter:testOrderScript.textAreaInputFormat">备注</th> 
                    <th data-options="field:'file',width:150, formatter:testOrderScript.fileFormat">附件</th> 
					<th data-options="field:'recordOperateStatus',width:10, formatter:testOrderScript.hiddenColumnFormat,hidden:true">id</th> 
                    <th data-options="field:'material.id',width:10, formatter:testOrderScript.hiddenColumnFormat,hidden:true">materialId</th> 
                     <th data-options="field:'id',width:10, formatter:testOrderScript.hiddenColumnFormat,hidden:true">id</th> 
				</tr>
			</thead>
		</table> 
       <div  class="blankLine"></div>
       <div id="testOrderFileListPanel" class="easyui-panel"   title="附件"  data-options="collapsible:true" width="100%">  
       <table id="testOrderFileListGrid"  class="tablestyle"    width="100%">
        <tr class="haveborder"><td>序号</td><td>操作</td><td>文件</td><td style="display:none"></td>
        </tr>
       <tr cloneRowFlag="1"  class="haveborder"   style="display:none">
        	<td><span name="tableRowNum">1</span></td>
       	    <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
		    onclick="$.ts.EasyUI.deleteTableRow(this )">删除</a></td>
           <td> <input type="file" name="testOrderFileList.file" /> </td>
           <td style="display:none"> <input name="testOrderFileList.id"   type="hidden" />
           		<input name="testOrderFileList.recordOperateStatus"   type="hidden" />
            </td>
          </tr> 
          <tr cloneRowFlag="1" class="haveborder"  style="display:none" > 
       	    <td colspan="2"> </td>
           <td> 备注：<input name="testOrderFileList.description"   class="easyui-textbox"  style="width:150px;height:30px"  /></input> </td>
           <td style="display:none">  </td>
          </tr> 
      </table>
       </div>
	<div id="testOrderDtlListToolbar"  >
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
		 onclick="testOrderScript.insertDatagridRow()">添加</a>&nbsp;&nbsp; 
	</div> 
   <div id="testOrderFileListToolbar" >
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
		 onclick="$.ts.EasyUI.copyTableRow(this)">添加</a>&nbsp;&nbsp; 
	</div> 
   <div style="text-align:center;"   class="dialog-button" id="formToolbar">
   	<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return testOrderScript.submitForm(this);">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back"  onClick="$.ts.Utils.closeDialog(this)">关闭</a>
    <a href="${contextPath}/core/downloadFileService/stream/downloadFile.do?number=1测试ff.xls&name=/resource/upload/core/test/order/TestOrderDtl/1测试.xls" onClick="">1测试ff.xls</a> 
     </div>  
</form>
<link rel="stylesheet" type="text/css" href="${contextPath}/style/TSStyle.css"> 
<script type="text/javascript" src="<ts:base ref='path'/>/TestOrder.js?version=1.3"></script> 
<script type="text/javascript">
    var testOrderScript=new TestOrderScript();   
	$(function() { 
	    testOrderScript.initPage({appKey:"${appReqeustContext.appKey}"});  
		$.ts.EasyUI.titleAppendToolbar("testOrderFileListToolbar","testOrderFileListPanel");
		$.ts.EasyUI.titleAppendToolbar("testOrderDtlListToolbar","testOrderDtlListGrid"); 
	});
</script>
</body>
</html>