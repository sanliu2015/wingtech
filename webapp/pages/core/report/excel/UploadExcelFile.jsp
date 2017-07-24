<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %> 
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
  <head> 
 
  <title> 上传附件 </title> 
  </head>
<body id="${appReqeustContext.appKey}FileBody"  >  
 <form action="${contextPath}/main/${appReqeustContext.appService}/json/uploadExcelFile.do" id="${appReqeustContext.appKey}FileForm" name="${appReqeustContext.appKey}FileForm" method="post"  > 
   <input name="uploadFileSavePath" id="uploadFileSavePath" type="hidden"  /> 
 <div style="padding:10px;padding-left:10%;display:none" >
		<table cellpadding="0" cellspacing="0" class="baseForm-table" style="display:none"> 
         <tr> 
                <td  ><label  for="file">上传附件</label>
            	<td  > <input type="file" name="file" id="file" onChange="$('#fileName').textbox('setValue',this.value)"    />  </td>
          </tr>  
      </table>      
   </div>   
  <script language="javascript">
      
      function submitFileForm(obj){ 
	     if( document.getElementById("file").value==""){
			  $.messager.alert('提示',"导入的文件不能为空！"); 
			 return false;
		 }
		 var appKey="${appReqeustContext.appKey}";
	     var bodyId="#"+appKey+"FileBody";
		 var formId="#"+appKey+"FileForm";
		 var url=$(formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		     
	  	 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj,'0');
		 } );
		return false;
	 } 
	
  </script>
  </form>
  </body> 
</html>

