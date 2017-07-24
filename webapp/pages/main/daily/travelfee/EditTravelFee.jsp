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
 <form action="${contextPath}/main/travelFeeService/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"   > 
<input name="bean.id" type="hidden"  /><input name="actionType" id="actionType" type="hidden" value="update"/>
  <div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
<style>    
.billTable{ border: 2px solid ;border-collapse: collapse; }
.billTable td{ border:1px   #000 solid; height:30px}
.paymentSpan{width:180px;display:inline-block;  }
 .billThinBorderTable{ border: 1px solid ;border-collapse: collapse; }
  </style> 
<div style="padding:10px;padding-left:10px; font-size:14px">
  <table   border="0" cellspacing="0" cellpadding="0"  width="100%"  align="center">
          <td nowrap width="30%" align="left" background="${contextPath}/resource/image/logo/logo.png" style="background-repeat:no-repeat" height="50px">   </td> 
           <td nowrap="nowrap" width="35%" colspan="2"><span style="font-size:24px; font-weight:bold;letter-spacing:28px">付款申请单</span><br/>
           <span style="font-size:24px; font-weight:bold;">PAYMENT PREQUEST</span>
           </td>
           <td width="35%">
            <table    border="0"  cellspacing="0" cellpadding="0"  width="100%"  align="center"  class="billThinBorderTable">
            	<tr><td colspan="3" height="30px">For Finance Office Only 仅限财务部门填写</td></tr>
                <tr><td   height="30px">Period/ 日期：</td><td  >Year/年</td><td  >Month/月</td></tr>
                <tr><td  colspan="3"  height="30px"> Voucher NO/凭证号：<input  style="width:100px;" class="inputUnderLine" name="bean.number"></input></td></tr> 
            </table>
           </td>
          </tr>
   </table>

    <table   border="0" cellspacing="0" cellpadding="0"  width="100%"  align="center">
    	<tr><td width="25%"  height="30px">动物营养事业部<input name="bean.requestObjectKind" type="checkbox" value="0"  ></td>
          <td width="25%">动物保健事业部<input name="bean.requestObjectKind" type="checkbox" value="1"  ></td>
          <td>管理<input name="bean.requestObjectKind" type="checkbox" value="2"  ></td></tr>
     </table>
      <table   border="0"  cellspacing="0" cellpadding="0" width="100%"  id="dtlListGrid"   align="center" class="billTable" >
            	<tr><td colspan="4" height="30px"   >Full name of Payee/申请人全名：<input  style="width:100px;"  name="bean.employeeName"   id="bean.employeeName" class="inputUnderLine"  ></input><input name="bean.employee.id" id="bean.employee.id" type="hidden"   /></td>
                <td colspan="2" height="30px"  >Department/所在部门：<input  style="width:150px;"  name="bean.deptName"   id="bean.deptName" readonly class="inputUnderLine"   ></input><input name="bean.deptId" id="bean.deptId" type="hidden"    /></td>
                </tr>
                <tr><td colspan="5" height="30px"  align="center">Explanation/摘要</td>
                   <td   height="30px"  align="center">Amount/金额</td>
                </tr> 
                <tr><td   > item No/项目编号 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="insertExplanationRowId"
		 onclick="tavelFeeScript.insertExplanatioRow(this)">添加</a></td>
                    <td > Date/日 期</td> 
                    <td   colspan="3"> Description/内容描述</td> 
                    <td  >RMB/人民币</td> 
                 </tr>
                 <tr cloneRowFlag="1"  style="display:none" >
                     <td> <select name='dtlList.feeKindParentId' id='dtlList.feeKindParentId'   style='width:95%'  data-options="editable:false,valueField:'id',textField:'text'"/></select>  </td>
                    <td > <input   name='dtlList.eventTime' id='dtlList.eventTime'    style='width:85%'  class='easyui-my97' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  ></input> </td> 
                     <td   colspan="3"> <input   name='dtlList.description' id='dtlList.description'    style='width:100%'  class='easyui-textbox'   ></input></td> 
                    <td  > <input type='text' name='dtlList.amount' id='dtlList.amount'   style='width:70%'   data-options="precision:2,onChange:tavelFeeScript.amountcChange"/> 
            <a href="javascript:void(0)" class="easyui-linkbutton" id="feeSubmitBtn" iconCls="icon-cancel"  onClick=" $.ts.EasyUI.deleteTableRow(this );"  plain=true  style="list-style:none;float:right;text-align:left"></a>
            <input name="dtlList.id"   type="hidden" />
           		<input name="dtlList.recordOperateStatus"   type="hidden" /> </td> 
                 </tr> 
                  <tr><td>Total/合计</td>
                    <td   colspan="4">总金额(大写)&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;<span id="chineseMoneySpan" style="letter-spacing:5px">佰拾万仟佰拾元角分</span>  </td>  
                    <td  ><input  style="width:120px;"  name="bean.applyAmount"   id="bean.applyAmount" readonly class="inputUnderLine" />  </td> 
                 </tr>
                 <tr><td colspan="6">
                 Mode of Payment/付款方式： <span  class="paymentSpan"><input name="bean.paymentMode" type="checkbox" value="1" onClick="tavelFeeScript.checkSinglebox(this)">Cash/现金</span>
                  <span  style="width:210px;display:inline-block;"><input name="bean.paymentMode" type="checkbox" value="2" onClick="tavelFeeScript.checkSinglebox(this)">Bank draft/银行承兑汇票&nbsp;&nbsp;&nbsp;&nbsp;</span>
                  <span class="paymentSpan"><input name="bean.paymentMode" type="checkbox" value="3" onClick="tavelFeeScript.checkSinglebox(this)">Cheque/支票</span>
                   <span class="paymentSpan"><input name="bean.paymentMode" type="checkbox" value="4" onClick="tavelFeeScript.checkSinglebox(this)">Remittance/转账</span></td>
                    
                 </tr>
                 <tr><td colspan="6">
                 Date of payment required/要求付款日期：<input  style="width:100px;"  name="bean.requiredPayDate" class="inputUnderLine"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:130px;" ></input>
                   </td> 
                 </tr>
                  <tr><td colspan="6"  style="height:55px">
                      Remarks/备注：<input  style="width:500px;"  name="bean.description"   id="bean.description"   class="inputUnderLine" />  
                      <br/>Cash Advanced/现金预支(金额)：<input  style="width:100px;"  name="bean.advanceAmount" class="inputUnderLine"></input>元</td> 
                 </tr>
            </table>
      <table id="fileListGridContainer">
      <tr> 
             <td  ><label  for="bulletinFileList"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="tavelFeeScript.uploadFile(this);">上传附件</a> </label>
             <table id="fileListGrid">
              <c:forEach items="${form.fileList}" var="file" varStatus="vs">
             	<tr cloneRowFlag="0"  ><td> <a href="javascript:void(0)" class="easyui-linkbutton" deleteRecordOperate="1" data-options="plain:true"
                onclick="$.ts.EasyUI.deleteTableRow(this)">删除</a><span id="fieldName" style="border:1px solid #03C; background:#FFC">${file.fileName}</span><input type="file" name="fileList.file"   onChange="tavelFeeScript.fileOnChange(this)"   style="display:none" />
                 <input name="fileList.id"   type="hidden"  value="${file.id}"/>
                 <input name="fileList.recordOperateStatus"   type="hidden"  value="update"/></td>
                 </tr>
             </c:forEach>
             <tr cloneRowFlag="1" style="display:none" ><td> <a href="javascript:void(0)" class="easyui-linkbutton" deleteRecordOperate="1" data-options=" plain:true"
		    onclick="$.ts.EasyUI.deleteTableRow(this)">删除</a><span id="fieldName" style="border:1px solid #03C; background:#FFC"></span><input type="file" name="fileList.file"   onChange="tavelFeeScript.fileOnChange(this)"   style="display:none" />
             <input name="fileList.id"   type="hidden" />
           	 <input name="fileList.recordOperateStatus"   type="hidden" /></td></tr></table>
             </td>
           </tr> 
      </table>      
   </div> 
   <div style="text-align:center;"   class="dialog-button" id="formToolbar">
   	<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return tavelFeeScript.submitForm(this);">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a> 
     </div>  

<script type="text/javascript" src="<ts:base ref='path'/>/TravelFee.js?version=1.0"></script> 
<script type="text/javascript">
    var tavelFeeScript=new TravelFeeScript();   
	tavelFeeScript.initRequestPage({appKey:"${appReqeustContext.appKey}"});
	var formJson=null;
	var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
    formJson=jQuery.parseJSON(jsonContentObj.text());  
	 var dtlList=formJson.dtlList;  
	   var fileList=formJson.fileList;  
	  $(function() {   
	   delete  formJson.dtlList; 
	   delete  formJson.fileList; 
	   jsonContentObj.remove();   
	   $('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
	});
	function modalDialogLoadEvent() {  
		// $('#dtlListGrid').datagrid('appendRow',{ });
		 var table=$('#dtlListGrid'); 
		 var operateBut=document.getElementById("insertExplanationRowId");
		 for(var i=0;i<dtlList.length;i++){ 
		   var  row=tavelFeeScript.insertExplanatioRow(operateBut); 
		   dtlList[i].recordOperateStatus="update";
		   var tmp={"dtlList":dtlList[i]};
		   row.tsLoadData(tmp);  
		   tavelFeeScript.dynamicLoadFeeKinds(row,{id:dtlList[i].feeKindParentId},dtlList[i].feeKindId); 
		 } 
		 
	}
</script>
</form> 
</body>
</html>