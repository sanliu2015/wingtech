<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %> 
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
  <head> 
  <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">  
<link rel="stylesheet" type="text/css" href="${contextPath}/style/TSStyle.css"> 
 <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/my97.css"> 
  <title>${repManager.reportConfigureBean.name}</title> 
  </head>
<body id="${appReqeustContext.appKey}Body"  >  
 <form action="${contextPath}/main/${appReqeustContext.appService}/json/save.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"  > 
 <input name="importType" id="importType" type="hidden"  /> 
 <div style="padding:10px;padding-left:5%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table"> 
         <tr> 
                <td  ><label  for="file">上传附件</label>
            	<td  ><input type="file" name="file" id="file"   onChange="importExcelScript.uploadFile()" />(注：Excel2007以上版本)  </td>
          </tr>  
          <tr> 
                <td  ><label  for="bulletinFileList">数据开始行</label>
            	<td><input name="startLineNumber" id="startLineNumber" class="easyui-numberspinner"   value="1"  style="width:100px;height:30px"  /></input></td>
          </tr> 
           <tr> 
                <td  ><label  for="bulletinFileList">工作薄</label>
            	<td><select name="sheetIndex"  id="sheetIndex" class="easyui-combobox"  data-options="editable:false"  style="width:150px;height:30px" >    
						</select></td>
          </tr> 
          <tr> 
                <td  ><label  for="bulletinFileList">默认省份</label>
            	<td><select name="repealerId"  id="repealerId" class="easyui-combobox"  data-options="editable:false"  style="width:150px;height:30px" >   
							<option value="">请选择</option>   
                        	<ts:forEach name='areaList'/>
						</select></td>
          </tr> 
          <tr> 
                <td  ><label  for="bulletinFileList">默认归属部门</label>
            	<td>
                <input name="sysListLinkSetId" id="sysListLinkSetId"   class="easyui-textbox"  style="width:180px;height:30px"  /></input>
                </td>
          </tr> 
      </table>  
       <div id="uploadFileContainer" class="easyui-panel" title="设置导入规则" style="width:100%;height:300px" >
	
      <table class="tablestyle" style="width: 100%; height:300px;text-align: center;" id="importRuleTable">
               
					<tr class="haveborder" align="center">
                       <td class="alt">序号</td>
						<td class="alt">属性名称</td>
						<td class="alt">对应EXCEL文件中列序号</td> 
					</tr>
                    <c:forEach items="${form.mapRuleList}" var="row" varStatus="vs"> 
                     <tr class="haveborder" cloneRowFlag="0"  align="center"  > 
						<td><span name="tableRowNum">${vs.index+1}</span></td>
						<td><input type="text" class="text" name="mapRuleList.name" readonly value="${row.name}"/>  </td>
						<td>  <select name="mapRuleList.number"    class="easyui-combobox"  data-options="editable:true"  style="width:180px;height:30px" > 
                                <ts:forEach name='charStrList'   value='${row.number}'/>
							</select>
							 <input type="hidden" class="text"  name="mapRuleList.enName" value="${row.enName}"/>
                           <input type="hidden" class="text"  name="mapRuleList.id"  />
                           <input type="hidden" class="text"  name="mapRuleList.recordOperateStatus"  />
						</td> 
					</tr>  
                    </c:forEach> 
				</table>  
            </div>   
   </div> 
   
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return importExcelScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="parent.mainScript.closeCurrentTab();">关闭</a>     
     </div>    
   <script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js?version=1.0"></script>   
<script language="javascript" src='${contextPath}/scripts/jquery/mask.js'></script>
    
 <script type="text/javascript" src="${contextPath}/scripts/jquery/jquery.form.js"></script>   
 <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/datepicker/WdatePicker.js"></script>
 <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/datepicker/jquery.my97.js"></script>
 <script type="text/javascript" src="${contextPath}/scripts/easyui/extEasyUI.js"></script>  
  <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/validate/jquery.validate.js"></script>
  <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/validate/additional-methods.js"></script>
   <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/validate/messages_zh.js"></script>
 <script type="text/javascript" src="${contextPath}/scripts/ts/TSCore.js?version=1.5"></script>
 
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

