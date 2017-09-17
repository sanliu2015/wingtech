<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts"%>
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
 
　　
</head>
<body id="${appReqeustContext.appKey}Body"   >  
<form action="${contextPath}/main/${appReqeustContext.appService}/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
<input name="bean.id" type="hidden"  /><input name="bean.auditStatus" type="hidden" value="0" /><input name="bean.status" type="hidden" value="0" /> 
	<div style="padding:0px; font-size:12px;font-weight:bold;font-family:'黑体','Times New Roman',Arial" id="printContainer" >
    <span style="width:30px;display:inline-block;"></span><span style="font-size:16px;">宿舍物品放行条(当天有效)</span><br/><br/><br/>
    <span style="width:130px;display:inline-block;">编号:${form.bean.number}</span>日期:${form.bean.passDate}<br/><br/>
    <span style="width:130px;display:inline-block;">姓名:${form.bean.empName}</span>工号：${form.bean.empNumber}<br/><br/>
    <span style="width:130px;display:inline-block;">房间：${form.bean.buildNumber}.${form.bean.roomName}</span>离厂原因:${form.bean.reason}<br/><br/>
    <span style="width:130px;display:inline-block;">钥匙:${form.bean.keyStatus}</span>遥控器：${form.bean.telStatus}<br/><br/>
    <span style="width:130px;display:inline-block;">行李数量:${form.bean.packageNum}</span><br/><br/>
    <c:if test="${dtlListPrint != null && fn:length(dtlListPrint) > 0}">
    	= = = = = = = = = = = = = = = = = = = = = <br>
    	<span style="width:65px;display:inline-block;">物品名称</span><span style="width:35px;display:inline-block;">数量</span>备注<br/>
    	<c:forEach items="${dtlListPrint}" var="obj" varStatus="idx">
    		<span style="width:70px;display:inline-block;font-weight:normal;">${obj['name']}</span><span style="width:30px;display:inline-block;font-weight:normal;">${obj['quantity']}</span><span style="font-weight:normal;">${obj['description']}</span><br/>
    	</c:forEach>
    	= = = = = = = = = = = = = = = = = = = = = <br><br>
    </c:if>
    <span style="width:130px;display:inline-block;">宿舍人员签字:</span>宿舍管理员签字：<br/><br/>
    <span style="width:130px;display:inline-block;">检查人签字:</span>检查时间：<br/>
	</div> 
<script type="text/javascript" src="<ts:base ref='path'/>/PassBill.js"></script> 
<script type="text/javascript">
function modalDialogLoadEvent(){ 
}
$.ts.Utils.appendPrintContainer("printContainer");
</script>      
</form> 
</body>
</html>