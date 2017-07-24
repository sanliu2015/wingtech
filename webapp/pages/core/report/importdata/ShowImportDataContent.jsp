<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %> 
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
  <head>  
  <title>${repManager.reportConfigureBean.name}</title>  
   
  </head>
<body  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  id="importDataBody"> 
<form method="post"  name="importDataForm" id="importDataForm" action="${contextPath}/core/importDataCoreService/json/doImportQueryData.do"   > 
    <input name="moduleFileName" id="moduleFileName" type="hidden" value="${repManager.reportConfigureBean.filePath}"/> 
  <input name="progressBarEventId" id="progressBarEventId" type="hidden"  > 
  <input name="tsFilterSql" id="tsFilterSql" type="hidden" value="${param.tsFilterSql}"/>   
	<ts:report name="hidden" />  
    <div style="text-align:center;padding:5px" class="dialog-toolbar">
    <input type="button" name="deleteImportDataRecordsButtonN" value="删除导入的数据" class="buttonstyle" style="display:none"  onClick="return deleteImportData();">	
    导相同的数据<input type="checkbox" name="importSameRecordFlag"  id="importSameRecordFlag" value="1"   />
     <a href="javascript:void(0)" class="easyui-linkbutton" name="confirmImportDataButton"  iconCls="icon-filter"  onClick=" return reportScript.importData(this);">导入数据</a> &nbsp;&nbsp;
        <a href="javascript:void(0)" class="easyui-linkbutton" name="deleteImportDataRecordsButton" iconCls="icon-lock"  onClick=" return reportScript.deleteImportData(this);">删除导入数据</a> 
		记录颜色标志 ：<span style="background:#99CCFF;border-color:#3300FF; border:solid 1px">&nbsp;&nbsp;&nbsp;</span>-新增 
		<span style="background:#66FFCC;border-color:#3300FF; border:solid 1px">&nbsp;&nbsp;&nbsp;</span>-修改过 	
		<span style="background:#CCCCCC;border-color:#3300FF; border:solid 1px">&nbsp;&nbsp;&nbsp;</span>-删除过  
		<span style="background:#FFFFFF; border-color:#3300FF; border:solid 1px">&nbsp;&nbsp;&nbsp;</span>-相同的 
		<span style="background:#669933;border-color:#3300FF; border:solid 1px">&nbsp;&nbsp;&nbsp;</span>-变更字段
        
     </div>
    <table align="center" width="100%" cellpadding="0" cellspacing="1" class="bgbian"  >
        <tbody> 
            <tr  class="bgwhiteleft"><td  colspan="4">
			 <div class="tableContainer" id="tableDiv"  >
                <table width="100%" border="0" cellspacing="1" cellpadding="1" id="dtlTable"   class="bgbian" >
				<thead>
                  <tr class="bgcenter">
                    <td nowrap="nowrap"> 选择<input name="choseAll" type="checkbox" value="1" class="buttonstyle"    onClick="reportScript.choseAllComp(this);"    /></td>
					<td nowrap="nowrap">序号</td> 
					<td nowrap="nowrap">记录状态</td> 
                   <c:forEach items="${datagrid.columns}" var="obj" >  
                      <c:forEach items="${obj}" var="column"  > 
                      <c:if test="${ column.hidden!=true}">
                     	 <td nowrap="nowrap">${column.title}</td> 
                      </c:if>
                      </c:forEach>
                      </c:forEach> 
				  <td style="display:none"> </td> 
				  </tr>
				   </thead> 
                  <c:if test="${!empty resultList}">
				  <c:forEach items="${resultList}" var="obj"  varStatus="vs" >  
				  <tr class="bgwhiteleft"  style="background-color:${obj.recordBackgroupColor}"   >
				  	<td nowrap="nowrap"> <input type="checkbox" name="selectImportRowParam"  id="selectImportRowParam" value="${obj.selectRowParam}"   importRecordType="${obj.importRecordType}">  </td>
					<td nowrap="nowrap"><span id="tableRowNum">${vs.index+1}</span></td> 
					<td nowrap="nowrap">  <c:if test="${obj.importRecordType=='0'}">新增</c:if>
                   <c:if test="${obj.importRecordType=='2'}">更新</c:if>
                   <c:if test="${obj.importRecordType=='4'}">删除</c:if>
                   <c:if test="${obj.importRecordType=='1'}">相同</c:if> 
					 </td>  
                      ${obj.wrapRowBean}  
					<td style="display:none"><input name="isSelectRow" id="isSelectRow"  type="hidden" value="0"  ></td> 
				  </tr>
				   </c:forEach>
				  </c:if>
                </table> 
				</div>
				 </td></tr>
           
                </table> </td></tr>
        </tbody>
    </table>
   </form>   
   
    
  </body>
  
</html>

