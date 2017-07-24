<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 <title>查看${appReqeustContext.appName}</title>   

</head>
<body id="${appReqeustContext.appKey}Body"   >  
 <form   id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
 
 
   <input name="bean.id" type="hidden"  /><input name="actionType" type="hidden" value="lookup"/>  
<div style="padding:10px;padding-left:5%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
            <tr  align="left" >
				<td nowrap class="newTitle"  colspan="3" align="left" background="${contextPath}/resource/image/logo/logo.png" style="background-repeat:no-repeat" height="50px"><span  style="width:120px;display:inline-block;">&nbsp;</span><ts:forEach name='topicKindList' outValue='1' value='${form.bean.topicKind}'/></td> 
			</tr> 
			<tr  align="center" >
				<td nowrap class="newTitle"  colspan="3" align="center"><strong>${form.bean.name}</strong></td> 
			</tr>   
            <tr  >
                <td  nowrap align="center"> <label  >接收者:</label><ts:forEach name='receiverTypeList' outValue='1' value='${form.bean.receiverType}'/></td>
				<td><label  >发布人:</label>${form.bean.createdBy}  </td>
                 <td><label  >日期:</label>${form.bean.createDate} &nbsp;&nbsp; ${form.bean.createTime}</td>
            </tr> 
            <tr  >
            	<td colspan="3" background="${contextPath}/resource/image/icon/xian.gif" style="background-repeat:repeat-x"  height="3"></td>
             </tr>     
           <tr  >
                <td  nowrap  colspan="3" align="center"> <div  style="width:700px; overflow:hidden" ><br/>
                        ${form.bean.description} </div></td> 
           </tr>   
           <c:if test="${!empty form.bulletinFileList}">
           <tr  >
                 
             <td nowrap   align="left"><label  >下载附件：</label></td>
              <td nowrap  colspan="2" align="left">  
             <table  >
             <c:forEach items="${form.bulletinFileList}" var="file" varStatus="vs">
             	<tr cloneRowFlag="0"  ><td><span id="fieldName" style="border:1px solid #03C; background:#FFC">${file.fileName}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="${contextPath}/core/downloadFileService/stream/downloadFile.do
?number=${file.fileName}&name=${file.filePath}/${file.saveToFileName}" onClick="">下载</a>
&nbsp;&nbsp;<a  href="javascript:void(0)" onClick="$.ts.Utils.onLineBrowseFile('${file.filePath}/${file.saveToFileName}')"  > 在线查阅</a> </td>
                 </tr>
             </c:forEach> 
             </table>
             </td> 
           </tr>  
           </c:if>
		</table> 
</div> 
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button">  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div>  
  <style type="text/css">
 
body {
	background-color: #FFFFFF;
}
.newTitle {	font-size: 18px;  
	font-weight: bold;
	font-family: 微软雅黑, tahoma;
}
 
</style>
   
</form> 


</body>
</html>