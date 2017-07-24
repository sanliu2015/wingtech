<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html  xmlns="http://www.w3.org/1999/xhtml" lang="zh-cn">
<ts:base />
<head> 
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=2, user-scalable=yes" />  
  <TITLE>查看图片</TITLE>   
  <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css">  
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">  

<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>     
  <script type="text/javascript" src="${contextPath}/scripts/ts/TSCore.js?version=1.2"></script>
    <script language="javascript" src="CJL.0.1.min.js"></script>
      <script language="javascript" src="ImageTrans.js"></script>
 

         
    </HEAD>    
<body  >
  <style>
#idContainer{border:1px solid #000;width:600px; height:500px; background:#FFF center no-repeat;}
</style>  
      <table width="100%" border="0" cellspacing="0"   height="90%" id="attachmentTableId"  >  
	 <tr class="bgwhiteleft"   id="downloadLogTr"> 
	   <td   align="center" id="idContainer"> </td> </tr>	 		 
	  </table> 
       <table width="100%" border="0" cellspacing="0"   height="40" id="attachmentTableId"  >  
	 <tr class="bgwhiteleft"   > 
	   <td  align="center" >   
		<input id="idLeft" type="button" value="向左旋转" />
<input id="idRight" type="button" value="向右旋转" />
<input id="idVertical" type="button" value="垂直翻转" />
<input id="idHorizontal" type="button" value="水平翻转" />
<input id="idReset" type="button" value="重置" />
 </td> </tr>		  
 </table>  
<script language="javascript">
   (function(){

var container = $$("idContainer"), src = "${contextPath}${form.name}",
	options = {
		onPreLoad: function(){ container.style.backgroundImage = "url('loading.gif')"; },
		onLoad: function(){ container.style.backgroundImage = ""; }
	},
	it = new ImageTrans( container, options );
it.load(src);
//垂直翻转
$$("idVertical").onclick = function(){ it.vertical(); }
//水平翻转
$$("idHorizontal").onclick = function(){ it.horizontal(); }
//左旋转
$$("idLeft").onclick = function(){ it.left(); }
//右旋转
$$("idRight").onclick = function(){ it.right(); }
//重置
$$("idReset").onclick = function(){ it.reset(); }
 

})()
</script> 
</BODY>   
</html>