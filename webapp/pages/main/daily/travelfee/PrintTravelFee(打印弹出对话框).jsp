<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 <title>打印${appReqeustContext.appName}</title>   
  
</head>
<body id="${appReqeustContext.appKey}Body"   >  
 <form action="${contextPath}/core/downloadFileService/printContent.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"    > 
<input name="bean.id" type="hidden"  /><input name="actionType" id="actionType" type="hidden" value="print"/>
<input name="auditComments" id="auditComments" type="hidden"  />
<input name="baseBusiOperateType" id="baseBusiOperateType" type="hidden"  />
  <div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
 
  <div style="padding:10px;padding-left:10px; font-size:14px" id="printContainer">
<style>    
.billTable{ border: 2px #000 solid ;border-collapse: collapse; }
.billTable td{ border:1px   #000 solid; height:30px}
.paymentSpan{width:180px;display:inline-block;  }
 .billThinBorderTable{ border: 1px #000 solid ;border-collapse: collapse; }
 @media print { 
.noprint { display: none;color:green } 
} 
  </style> 

  <table   border="0" cellspacing="0" cellpadding="0"  width="100%"  align="center">
          <td nowrap width="30%" align="left" background="${contextPath}/resource/image/logo/logo.png" style="background-repeat:no-repeat" height="50px">   </td> 
           <td nowrap="nowrap" width="35%" colspan="2"><span style="font-size:24px; font-weight:bold;letter-spacing:28px">付款申请单</span><br/>
           <span style="font-size:24px; font-weight:bold;">PAYMENT PREQUEST</span>
           </td>
           <td width="35%">
            <table    border="0"  cellspacing="0" cellpadding="0"  width="100%"  align="center"  class="billThinBorderTable">
            	<tr><td colspan="3" height="30px">For Finance Office Only 仅限财务部门填写</td></tr>
                <tr><td   height="30px">Period/ 日期：</td><td  >Year/年</td><td  >Month/月</td></tr>
                <tr><td  colspan="3"  height="30px"> Voucher NO/凭证号：<input  style="width:100px;" class="inputUnderLine" name="bean.number" value="${form.bean.number}" readonly></input></td></tr> 
            </table>
           </td>
          </tr>
   </table>

    <table   border="0" cellspacing="0" cellpadding="0"  width="100%"  align="center">
    	<tr><td width="25%"  height="30px">动物营养事业部<input name="bean.requestObjectKind" type="checkbox" value="0" disabled></td>
          <td width="25%">动物保健事业部<input name="bean.requestObjectKind" type="checkbox" value="1"  disabled></td>
          <td>管理<input name="bean.requestObjectKind" type="checkbox" value="2" disabled></td></tr>
     </table>
      <table   border="0"  cellspacing="0" cellpadding="0" width="100%"  id="dtlListGrid"   align="center" class="billTable" >
            	<tr><td colspan="4" height="30px"   >Full name of Payee/申请人全名：<input  style="width:100px;"  name="bean.employee.name"   id="bean.employee.name" class="inputUnderLine"  value="${form.bean.employee.name}"></input><input name="bean.employee.id" id="bean.employee.id" type="hidden"    /></td>
                <td colspan="2" height="30px"  >Department/所在部门：<input  style="width:150px;"  name="bean.deptName"   id="bean.deptName" readonly class="inputUnderLine"  value="${form.bean.deptName}"></input><input name="bean.deptId" id="bean.deptId" type="hidden"   /></td>
                </tr>
                <tr><td colspan="5" height="30px"  align="center">Explanation/摘要</td>
                   <td   height="30px"  align="center">Amount/金额</td>
                </tr> 
                <tr><td   > item No/项目编号 </td>
                    <td > Date/日 期</td> 
                    <td   colspan="3"> Description/内容描述</td> 
                    <td  >RMB/人民币</td> 
                 </tr>
                  <c:forEach items="${form.dtlList}" var="dtl" varStatus="vs">
                 <tr   >
                     <td> ${dtl.feeKindParentName}  </td>
                    <td > ${dtl.eventTime}  </td> 
                     <td   colspan="3">  ${dtl.feeKindName}  </td> 
                    <td  > ${dtl.amount} 
                           <input name="dtlList.id"   type="hidden" />
           		              <input name="dtlList.recordOperateStatus"   type="hidden" /> </td> 
                 </tr> 
                  </c:forEach>
                  <tr  cloneRowFlag="1"  id="dtlListTr" style="display:none">
                     <td>   </td>
                    <td >   </td> 
                     <td   colspan="3">  </td> 
                    <td  >  
                           <input name="dtlList.id"   type="hidden" />
           		              <input name="dtlList.recordOperateStatus"   type="hidden" /> </td> 
                 </tr> 
                  <tr><td>Total/合计</td>
                    <td   colspan="4">总金额(大写)&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;<span id="chineseMoneySpan" style="letter-spacing:5px">佰拾万仟佰拾元角分</span>  </td>  
                    <td  >  <input  style="width:120px;"  name="bean.applyAmount"   id="bean.applyAmount" readonly class="inputUnderLine" />  </td> 
                 </tr>
                 <tr><td colspan="6">
                 Mode of Payment/付款方式： <span  class="paymentSpan"><input name="bean.paymentMode" type="checkbox" value="1" onClick="tavelFeeScript.checkSinglebox(this)" disabled>Cash/现金</span>
                  <span  style="width:210px;display:inline-block;"><input name="bean.paymentMode" type="checkbox" value="2" onClick="tavelFeeScript.checkSinglebox(this)" disabled>Bank draft/银行承兑汇票&nbsp;&nbsp;&nbsp;&nbsp;</span>
                  <span class="paymentSpan"><input name="bean.paymentMode" type="checkbox" value="3" onClick="tavelFeeScript.checkSinglebox(this)" disabled>Cheque/支票</span>
                   <span class="paymentSpan"><input name="bean.paymentMode" type="checkbox" value="4" onClick="tavelFeeScript.checkSinglebox(this)"   disabled>Remittance/转账</span></td>
                    
                 </tr>
                 <tr><td colspan="6">
                 Date of payment required/要求付款日期：<input  style="width:100px;"  name="bean.requiredPayDate" class="inputUnderLine"  readonly style="width:130px;" value="${form.bean.requiredPayDate}"></input>
                   </td> 
                 </tr>
                  <tr><td colspan="6"  style="height:55px">
                      Remarks/备注：<input  style="width:500px;"  name="bean.description" readonly  id="bean.description"   class="inputUnderLine" value="${form.bean.description}"/>  
                      <br/>Cash Advanced/现金预支(金额)：<input  style="width:100px;"  name="bean.advanceAmount" class="inputUnderLine" readonly value="${form.bean.advanceAmount}"></input>元</td> 
                 </tr>
            </table> 
   </div>  
<script type="text/javascript" src="<ts:base ref='path'/>/TravelFee.js?version=1.0"></script> 
<script type="text/javascript">

    var tavelFeeScript=new TravelFeeScript();   
   
	//tavelFeeScript.initRequestPage({appKey:"${appReqeustContext.appKey}"});
		
	var formJson=null;
	
	 var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
    formJson=jQuery.parseJSON(jsonContentObj.text());  
	 var dtlList=formJson.dtlList;  
	 $(function() {    
	   delete  formJson.dtlList; 
	   delete  formJson.fileList; 
	   jsonContentObj.remove();   
	    $("#bean\\.applyAmount").val(formJson.bean.applyAmount);
		var str=$.ts.Format.numMoneyToChinese(formJson.bean.applyAmount);
		$("#chineseMoneySpan").text(str);
		try{
	   $('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
		} catch(e){}
		
	 });
	function cloneDtlRow(){
		var dtlIds=document.getElementsByName("dtlList.id");
		var n=9-dtlIds.length-1;
		var cloneRow=$("#dtlListTr"); 
		for(var i=0;i<n;i++){
			 var copyRow=cloneRow.clone();
			  copyRow.css({'display':''});    
			  $(cloneRow[0]).before(copyRow);  
		}
	}
	
	 
	function modalDialogLoadEvent(){ 
	    cloneDtlRow();
		document.getElementById("auditComments").value=$("#printContainer").html();
		var formObj=document.getElementById("${appReqeustContext.appKey}Form"); 
		 if($.ts.Utils.isIE()){ 
			document.getElementById("baseBusiOperateType").value="isIE"; 
		} 
		formObj.target='_blank';
		formObj.submit();  
		//$.ts.EasyUI.closeDialog(formObj,'0'); 
		
	} 
	$.ts.Utils.appendPrintContainer("printContainer");
	
</script>
</form> 
</body>
</html>