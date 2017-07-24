<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %> 
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
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
 <%@ include file="/pages/core/common/Include.jsp" %> 
  <title>${repManager.reportConfigureBean.name}</title> 
  </head>
<body id="${appReqeustContext.appKey}Body"  >  
 <form action="${contextPath}/core/${appReqeustContext.appService}/json/doImportExcelData.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"  > 
 <input name="importType" id="importType" type="hidden"  /> 
 <input name="validateOperae" id="validateOperae" type="hidden"  value="0" /> 
 <input name="moduleFileName" id="moduleFileName" type="hidden" value="${repManager.reportConfigureBean.filePath}"/> 
 <div style="padding:5px;padding-left:5%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table"> 
         <tr> 
                <td  ><label  for="file">上传附件</label>
            	<td  colspan="5"><input type="file" name="file" id="file"   onChange="importExcelScript.uploadFile()" />(注：Excel2007以上版本)&nbsp;&nbsp;<a href="${contextPath}/core/downloadFileService/stream/downloadFile.do
?number=${repManager.reportConfigureBean.attributes['saveAsFile']}&name=${repManager.reportConfigureBean.templateFile}" onClick="">下载导入模板</a>  </td>   
          </tr>  
          <tr> 
		        <td  ><label  for="bulletinFileList">工作薄</label>
            	<td   ><select name="sheetIndex"  id="sheetIndex" class="easyui-combobox"  data-options="editable:false"  style="width:150px;height:30px" >    
						</select></td>
                <td  ><label  for="bulletinFileList">数据开始行</label>
            	<td><input name="startLineNumber" id="startLineNumber" class="easyui-numberspinner"   value="${repManager.reportConfigureBean.attributes['startLineNumber']}"  style="width:100px;height:30px"  ></input></td>
				<td  ><label  for="bulletinFileList">最大列</label>
            	<td><input name="maxColNumber" id="maxColNumber" class="easyui-numberspinner"   value="${repManager.reportConfigureBean.attributes['maxColNumber']}"  style="width:100px;height:30px"  ></input></td>

          </tr>  
      </table>  
       <div id="uploadFileContainer" class="easyui-panel" title="设置导入规则" style="width:100%;height:400px" >
	
      <table class="tablestyle" style="width: 100%; height:300px;text-align: center;" id="importRuleTable">
               
					<tr class="haveborder" align="center">
                       <td class="alt">序号</td>
						<td class="alt">属性名称</td>
						<td class="alt">对应EXCEL文件中列序号</td> 
					</tr>
                    <c:forEach items="${sourceTableRuleBean.sourceFields}" var="row" varStatus="vs"> 
                      <c:if test="${row.neeedExport!='0'}"> 
                     <tr class="haveborder" cloneRowFlag="0"  align="center"  > 
						<td><span name="tableRowNum">${vs.index+1}</span></td>
						<td><input type="text" class="text" name="mapRuleList.name" readonly value="${row.displayLable}"/>  </td>
						<td>  <select name="mapRuleList.number"    class="easyui-combobox"  data-options="editable:true"  style="width:180px;height:30px" > 
                                <ts:forEach name='charStrList'   value='${row.fieldName}'/>
							</select>
							 <input type="hidden" class="text"  name="mapRuleList.enName" value="${row.fieldAliasName}"/>
                           <input type="hidden" class="text"  name="mapRuleList.id"  />
                           <input type="hidden" class="text"  name="mapRuleList.recordOperateStatus"  />
						</td> 
					</tr>  
                    </c:if>
                    </c:forEach> 
				</table>  
            </div>   
   </div> 
   
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-tip"  onClick=" return importExcelScript.validateForm(this);"   style="list-style:none;float:left;text-align:left">验证</a>  
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return importExcelScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="parent.mainScript.closeCurrentTab();">关闭</a>     
     </div>     
 <script type="text/javascript" src="<ts:base ref='path'/>/ImportExcel.js"></script>  
  <script language="javascript">
     var importExcelScript=new ImportExcelScript();  
	 $(function() {
    	 importExcelScript.initPage({appKey:"${appReqeustContext.appKey}"});   
		  
	 }); 
	
  </script>
  </form>
  </body> 
</html>

