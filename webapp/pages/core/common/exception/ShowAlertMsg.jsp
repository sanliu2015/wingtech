<%@ page contentType="text/html; charset=UTF-8"%>
 <script language="javascript"> 
    function showAlertMsgEvent(){   
		try{
		$.messager.alert('提示', "${alertMessage}"); 
		} catch(e){alert("${alertMessage}");}
	} 
 </script>