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
<style type="text/css">
	body{font－size:40px;}
</style> 
 
　　
</head>
<body id="${appReqeustContext.appKey}Body"   >  
<form action="${contextPath}/main/${appReqeustContext.appService}/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
<input name="bean.id" type="hidden"  /><input name="bean.auditStatus" type="hidden" value="0" /><input name="bean.status" type="hidden" value="0" /> 
	<div style="padding:10px; font-size:14px;font-weight:bold" id="printContainer" >
    <%-- <input name="updateBillPrintTimesSql" id="updateBillPrintTimesSql" type="hidden" value="update Dorm_RepairApply set printTimes=isnull(printTimes,0)+1 where id=${form.bean.id}"/>   --%>
	 	<table   border="0" cellspacing="0" cellpadding="0" style="width:800px"  align="center">
	       <tr style="height:36px;"> 
	          <td nowrap width="15%" align="left" >编号: </td> 
	          <td nowrap width="35%" align="left" >${form.bean.number}</td> 
	          <td nowrap width="15%" align="left" >房间员： </td> 
	          <td nowrap width="35%" align="left" >${form.bean.roomName}</td>
	       </tr>
	       <tr style="height:36px;"> 
	          <td nowrap align="left" >姓名： </td> 
	          <td nowrap align="left" >${form.bean.empName}</td> 
	          <td nowrap align="left" >工号： </td> 
	          <td nowrap align="left" >${form.bean.empNumber}</td>
	       </tr>
	       <tr style="height:36px;"> 
	       	  <td nowrap align="left" >离厂原因： </td> 
	          <td nowrap colspan="3" align="left" >
	          	请假${form.bean.reason == '请假' ? '<image src="/wt/resource/image/icon/cced.png" align="absmiddle"/>' : '<image src="/wt/resource/image/icon/cc.png" align="absmiddle"/>'}
	          	离职${form.bean.reason == '离职' ? '<image src="/wt/resource/image/icon/cced.png" align="absmiddle"/>' : '<image src="/wt/resource/image/icon/cc.png" align="absmiddle"/>'}   
	          	自离${form.bean.reason == '自离' ? '<image src="/wt/resource/image/icon/cced.png" align="absmiddle"/>' : '<image src="/wt/resource/image/icon/cc.png" align="absmiddle"/>'}   
	          	外住${form.bean.reason == '外住' ? '<image src="/wt/resource/image/icon/cced.png" align="absmiddle"/>' : '<image src="/wt/resource/image/icon/cc.png" align="absmiddle"/>'}   
	          	邮寄${form.bean.reason == '邮寄' ? '<image src="/wt/resource/image/icon/cced.png" align="absmiddle"/>' : '<image src="/wt/resource/image/icon/cc.png" align="absmiddle"/>'} 
	          	其他（物出）${form.bean.reason == '其他（物出）' ? '<image src="/wt/resource/image/icon/cced.png" align="absmiddle"/>' : '<image src="/wt/resource/image/icon/cc.png" align="absmiddle"/>'}    
	          </td> 
	       </tr>
	       <tr style="height:36px;"> 
	          <td nowrap align="left" >钥匙： </td> 
	          <td nowrap align="left" >
	          	已退${form.bean.keyStatus == '已退' ? '<image src="/wt/resource/image/icon/cced.png" align="absmiddle"/>' : '<image src="/wt/resource/image/icon/cc.png" align="absmiddle"/>'}
	          	未退${form.bean.keyStatus == '未退' ? '<image src="/wt/resource/image/icon/cced.png" align="absmiddle"/>' : '<image src="/wt/resource/image/icon/cc.png" align="absmiddle"/>'}   
	          	无${form.bean.keyStatus == '无' ? '<image src="/wt/resource/image/icon/cced.png" align="absmiddle"/>' : '<image src="/wt/resource/image/icon/cc.png" align="absmiddle"/>'}   
	          </td> 
	          <td nowrap align="left" >空调遥控器： </td> 
	          <td nowrap align="left" >
	          	已退${form.bean.telStatus == '已退' ? '<image src="/wt/resource/image/icon/cced.png" align="absmiddle"/>' : '<image src="/wt/resource/image/icon/cc.png" align="absmiddle"/>'}
	          	未退${form.bean.telStatus == '未退' ? '<image src="/wt/resource/image/icon/cced.png" align="absmiddle"/>' : '<image src="/wt/resource/image/icon/cc.png" align="absmiddle"/>'}   
	          	无${form.bean.telStatus == '无' ? '<image src="/wt/resource/image/icon/cced.png" align="absmiddle"/>' : '<image src="/wt/resource/image/icon/cc.png" align="absmiddle"/>'}   
	          </td>
	       </tr>
	       <tr style="height:36px;"> 
	          <td nowrap align="left" >行李数量： </td> 
	          <td nowrap align="left" >${form.bean.packageNum}</td> 
	          <td nowrap align="left" >贵重物品数量： </td> 
	          <td nowrap align="left" >${form.bean.preciousNum}</td>
	       </tr>
	       <tr style="height:36px;"> 
	       	  <td nowrap align="left" >备注： </td> 
	          <td nowrap colspan="3" align="left" >${form.bean.description}</td> 
	       </tr>
	       <tr style="height:36px;"> 
	          <td nowrap align="left" >宿舍人员签字： </td> 
	          <td nowrap align="left" ></td> 
	          <td nowrap align="left" >宿舍管理员签字： </td> 
	          <td nowrap align="left" ></td>
	       </tr>
	       <tr style="height:36px;"> 
	          <td nowrap  align="left" >行李检查人签字： </td> 
	          <td nowrap  align="left" ></td> 
	          <td nowrap  align="left" >行李检查时间： </td> 
	          <td nowrap  align="left" ></td>
	       </tr>
	    </table>    
		
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