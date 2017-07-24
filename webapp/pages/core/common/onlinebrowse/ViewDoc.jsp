<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %>
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
  <head> 
 <%  
response.setHeader("Pragma","No-cache");//HTTP 1.1 
response.setHeader("Cache-Control","no-cache");//HTTP 1.0 
response.setHeader("Expires","0");//防止被proxy 
%>  
<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js?version=1.0"></script>   
  <script language="javascript" src="<ts:base ref='path'/>/main.js"></script>
        <TITLE>阅读DOC/XLS文档</TITLE>   
         
    </HEAD>   
	
	<!-- --------------------=== 调用Weboffice初始化方法 ===--------------------- -->
<SCRIPT language=javascript event=NotifyCtrlReady for=WebOffice1>
/****************************************************
*
*	在装载完Weboffice(执行<object>...</object>)
*	控件后执行 "WebOffice1_NotifyCtrlReady"方法
*
****************************************************/
    WebOffice_Event_Flash("NotifyCtrlReady");
	WebOffice1_NotifyCtrlReady()
</SCRIPT>

<SCRIPT language=javascript event=NotifyWordEvent(eventname) for=WebOffice1>
<!--
WebOffice_Event_Flash("NotifyWordEvent");
 WebOffice1_NotifyWordEvent(eventname);
 
//-->
</SCRIPT>

<SCRIPT language=javascript event=NotifyToolBarClick(iIndex) for=WebOffice1>
<!--
  WebOffice_Event_Flash("NotifyToolBarClick");
 WebOffice1_NotifyToolBarClick(iIndex);
//-->
</SCRIPT>
	<script language="javascript"> 
  	contextPath='${contextPath}';
     
	 urlContextPath="${contextPath}";  
	 /****************************************************
*
*		控件初始化WebOffice方法
*
****************************************************/
function WebOffice1_NotifyCtrlReady() {
	document.all.WebOffice1.SetWindowText("", 0);
	document.all.WebOffice1.OptionFlag |= 128; 
	// 新建文档 
	document.all.WebOffice1.LoadOriginalFile("${form.name}", "${form.number}");
	document.all.WebOffice1.ProtectDoc(1, 2, "");
	notPrint();
	document.all.WebOffice1.lContinue = 0; 
	bToolBar_New_onclick();
	bToolBar_Open_onclick();
	bToolBar_Save_onclick();
	bToolBar_onclick();
	//bToolBar_onclick();
	notMenu();
	//beginMenu_onclick();
	 allHideMenu_onclick();
	//spnWebOfficeInfo.innerText="----   您电脑上安装的WebOffice版本为:V" + document.getElementById("WebOffice1").GetOcxVersion() +"\t\t\t本实例是根据版本V6046编写";
}
	function checkForm() { 
	    scriptInstance.setValidateRule(); 
		var validateResult=validator.checkForm();
	 	if (validateResult==false) return false;    
		scriptInstance.changeControlNameToList();  
		document.getElementById("submitBtn").disabled=true; 
		return true;
   } 
   /****************************************************
	*
	*		接收office事件处理方法
	*
	****************************************************/
	var vNoCopy = 1;
	var vNoPrint = 1;
	var vNoSave = 1;
	var vClose=0;
	function no_copy(){
		vNoCopy = 1;
	}
	function yes_copy(){
		vNoCopy = 0;
	}
	
	
	function no_print(){
		vNoPrint = 1;
	}
	function yes_print(){
		vNoPrint = 0;
	}
	
	
	function no_save(){
		vNoSave = 1;
	}
	function yes_save(){
		vNoSave = 0;
	}
	function EnableClose(flag){
	 vClose=flag;
	}
	function CloseWord(){
		
	  document.getElementById("WebOffice1").CloseDoc(0); 
	}
	
	function WebOffice1_NotifyWordEvent(eventname) {
		//alert(eventname);
		if(eventname=="DocumentBeforeSave"){
			if(vNoSave){
				document.getElementById("WebOffice1").lContinue = 0;
				alert("此文档已经禁止保存");
			}else{
				document.getElementById("WebOffice1").lContinue = 1;
			}
		}else if(eventname=="DocumentBeforePrint"){
			if(vNoPrint){
				document.getElementById("WebOffice1").lContinue = 0;
				alert("此文档已经禁止打印");
			}else{
				document.getElementById("WebOffice1").lContinue = 1;
			}
		}else if(eventname=="WindowSelectionChange"){
			if(vNoCopy){
				document.getElementById("WebOffice1").lContinue = 0;
				//alert("此文档已经禁止复制");
			}else{
				document.getElementById("WebOffice1").lContinue = 1;
			}
		}else   if(eventname =="DocumentBeforeClose"){
			if(vClose==0){
				document.getElementById("WebOffice1").lContinue=0;
			} else{
				//alert("word");
				document.getElementById("WebOffice1").lContinue = 1;
			  }
	 }
		//alert(eventname); 
	}	 	   
  </script>
   <body leftmargin="0" topmargin="0" marginwidth="0"  marginheight="0"  onUnload="return window_onunload()">
  <form id="form1"     > 
           
      <table width="100%" border="0" cellspacing="0"   height="100%" id="attachmentTableId"  class="tableStyle2"> 
					   
					 <tr class="bgwhiteleft"   id="downloadLogTr"> 
					   <td colspan="2" align="center"  height="650">  
					   <object id="WebOffice1" height='100%' width='100%' style='LEFT: 0px; TOP: 0px'  classid='clsid:E77E049B-23FC-4DB8-B756-60529A35FAD5' codebase='weboffice_v6.0.5.0.cab#version=6,0,5,0'>
					   <param name='_ExtentX' value='6350'><param name='_ExtentY' value='6350'> 
					   </OBJECT>
					   
    
 </td>
					   </tr>		  
					 
				  </table> 
  </form>
 
</BODY>   
</html>