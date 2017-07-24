<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/fmt" prefix="fmt" %>
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
  <head> 
 <%  
response.setHeader("Pragma","No-cache");//HTTP 1.1 
response.setHeader("Cache-Control","no-cache");//HTTP 1.0 
response.setHeader("Expires","0");//防止被proxy 
%>
 <%@ include file="/pages/core/common/Include.jsp" %> 
<style>
.billTable {border-right:1px solid black;border-bottom:1px solid black} 
.billTable td{border-left:1px solid black;border-top:1px solid black} 
</style>
</head>
<body id="${appReqeustContext.appKey}Body"  >  
 <form action="${contextPath}/main/${appReqeustContext.appService}/print.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"   > 
 <input name="bean.id" type="hidden"  /> 
  <div style="padding:10px; font-size:14px" id="printContainer">
  <input name="updateBillPrintTimesSql" id="updateBillPrintTimesSql" type="hidden" value="update Dorm_RepairApply set printTimes=isnull(printTimes,0)+1 where id=${form.bean.id}"/>  
 	<table   border="0" cellspacing="0" cellpadding="0" style="width:1000px"  align="center">
       <tr style="height:36px;"> 
          <td nowrap width="15%" align="left" >报修单号： </td> 
          <td nowrap width="18%" align="left" >${form.bean.number}</td> 
          <td nowrap width="15%" align="left" >报修人员： </td> 
          <td nowrap width="18%" align="left" >${form.bean.employeeName}</td>
          <td nowrap width="15%" align="left" >楼栋位置： </td> 
          <td nowrap width="18%" align="left" ><ts:forEach name='buildingIdList' value='${form.bean.buildingId}' outValue="1"/></td>  
       </tr>
       <tr style="height:36px;"> 
          <td nowrap width="15%" align="left" >报修类别： </td> 
          <td nowrap width="18%" align="left" ><ts:forEach name='repairTypeList' value='${form.bean.repairType}' outValue="1"/></td> 
          <td nowrap width="15%" align="left" >维修人员： </td> 
          <td nowrap width="18%" align="left" >${form.bean.repairerName}</td>
          <td nowrap width="15%" align="left" >报修日期： </td> 
          <td nowrap width="18%" align="left" >${form.bean.repairDate}</td>  
       </tr>
       <tr style="height:36px;"> 
       	  <td nowrap width="15%" align="left" >报修内容： </td> 
       	  <td nowrap align="left" >${form.bean.repairContent}</td>
       </tr>
       <tr style="height:36px;"> 
       	  <td nowrap width="15%" align="left" >报修内容： </td> 
       	  <td nowrap align="left" >${form.bean.description}</td>
       </tr>
   </table>    
   <br/>
    <c:if test="${dtlList != null && fn:length(dtlList) > 0}">
    	<table   border="0"  cellspacing="0" cellpadding="0" style="width:1000px"  id="dtlListGrid"   align="center" class="billTable" >
           <tr style="height:40px;">
              <td    align="center" style="width:50px" ><label>序号</label></td>
              <td    align="center" style="width:100px" ><label>设备名称</label></td> 
              <td    align="center" ><label>备注</label></td>
            </tr>
            <c:forEach items="${dtlList}" var="dtl" varStatus="vs">
				<tr style="height: 32px;">
					<td align="center">${vs.index+1}</td>
					<td align="center">${dtl['name']}</td>
					<td align="center">${dtl['description']}</td>
				</tr>
			</c:forEach> 
       	</table>
    </c:if>
     </div>   
<script type="text/javascript" src="<ts:base ref='path'/>/RepairApply.js"></script> 
<script type="text/javascript"> 
	function modalDialogLoadEvent(){ 
		
	}
	 $.ts.Utils.appendPrintContainer("printContainer");
</script>
</form>
</body>
</html>