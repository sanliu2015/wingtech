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
 <form action="${contextPath}/main/${appReqeustContext.appService}/json/saveSummary.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
   <input name="summary.id" type="hidden"  value="${form.summary.id}" /> 
    <input name="summary.status" type="hidden"    value="1"/>    
    <input name="summary.eventType" type="hidden"    value="summary"/>  
    <input name="summary.dateRangeType" type="hidden"    value="week"/>  
   <input name="summary.startDate" type="hidden"   value="${form.summary.startDate}"/> 
   <input name="summary.endDate" type="hidden"   value="${form.summary.endDate}"/> 
   <input name="summary.employee.id" type="hidden"   value="${form.summary.employee.id}"/> 
<div style="padding:5px;padding-left:1%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
			<tr  >
				<td nowrap><label id="number-label" for="summary.name">时间</label> 
				 <input name="summary.name"  id="summary.name"  class="easyui-textbox" readonly   style="width:180px;height:30px" value="${form.summary.name}"></input>
                 </td> 
                 <td nowrap align="left"><label id="number-label" for="summary.name">上传附件</label> 
                  ${form.summary.fileName}  &nbsp;&nbsp;
                <c:if test="${!empty  form.summary.fileName }"><a href="${contextPath}/core/downloadFileService/stream/downloadFile.do
?number=${form.summary.fileName}&name=${form.summary.filePath}/${form.summary.saveToFileName}" onClick="">下载</a> </c:if>
                 </td> 
			</tr> 
            <tr  > 
           <td  nowrap colspan="2"><label   for="summary.eventExplain">标题*</label> 
				 <input name="summary.eventExplain"  id="summary.eventExplain"  class="easyui-textbox"     style="width:280px;height:30px" value="${form.summary.eventExplain}" readonly></input></td>                 
            </tr> 
              <tr  >
				<td nowrap   colspan="2" ><label  for="summary.description" >本周总结</label></td> 
			</tr> 
            <tr  > 
				<td   colspan="2" > ${form.summary.description} </td>
			</tr>
		</table>
</div> 
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div>        

  
</form>   
</body>
</html>