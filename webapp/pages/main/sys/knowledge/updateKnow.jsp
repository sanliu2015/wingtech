<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 <title>修改${appReqeustContext.appName}</title>   
</head>
<body id="${appReqeustContext.appKey}Body"   >
<form action="${contextPath}/main/mainknowledgeService/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
  <div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
 <div style="display:none" id="copyDialogContent"></div> 
 <input name="know.id" type="hidden" value="${form.know.id }" />  
<div style="padding:10px;padding-left:5%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
			<tr  >
				<td nowrap><label for="know.name">文件名称*</label></td>
				<td ><input name="know.name"  id="know.name" value="${form.know.name }" class="easyui-textbox"   style="width:180px;height:30px"></input>
               
                </td> 
                <td  nowrap><label   for="know.size">大小*</label></td>
				<td>
					<input name="know.size"  id="know.size" value="${form.know.size }" class="easyui-textbox"   style="width:180px;height:30px"></input>
                </td>
			</tr>   
            <tr  >
            	<td  nowrap><label   for="know.knowledgeType">文件类别*</label></td>
				<td> 
					<select name="know.knowledgeType"  id="know.knowledgeType" class="easyui-combobox"  data-options="editable:false"  style="width:180px;height:30px" > 
						<option value="">请选择文件类别</option>
						<c:forEach items="${knowType }" var="obj" >
							<c:if test="${form.know.knowledgeType==obj.id }">
								<option value="${obj.id }" selected="selected">${obj.typeName }</option>
							</c:if>
							<c:if test="${form.know.knowledgeType!=obj.id }">
								<option value="${obj.id }">${obj.typeName }</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
				<td  nowrap><label   for="know.knowledgeId">上级节点*</label></td>
				<td> 
					<select name="know.knowledgeId"  id="know.knowledgeId" class="easyui-combobox"  data-options="editable:false"  style="width:180px;height:30px" > 
						<option value="0">请选择文件上级节点</option>
						<c:forEach items="${parentId }" var="obj" >
							<c:if test="${form.know.knowledgeId==obj.id }">
								<option value="${obj.id }" selected="selected">${obj.name }</option>
							</c:if>
							<c:if test="${form.know.knowledgeId!=obj.id }">
								<option value="${obj.id }">${obj.name }</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
            </tr>
             <tr  >
				<td  nowrap><label   for="know.size">可阅读者*</label></td>
				<td> 
					<select name="know.receiverType"  id="know.receiverType" class="easyui-combobox"  data-options="editable:false"  style="width:180px;height:30px" > 
						<option value="">请选择文件可阅读者</option>
						<c:forEach items="${peopleType }" var="obj" >
							<c:if test="${obj.code==form.know.receiverType}">
								<option value="${obj.code}" selected="selected">${obj.name }</option>
							</c:if>
							<c:if test="${obj.code!=form.know.receiverType}">
								<option value="${obj.code}">${obj.name }</option>		
							</c:if>
						</c:forEach>
					</select>
				</td>
				<td  nowrap><label   for="know.readingTime">最近阅读*</label></td>
				<td>
					<input name="know.readingTime"  id="know.readingTime" value="${appReqeustContext.currentDate}" readonly="readonly" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" class="easyui-textbox"   style="width:180px;height:30px"></input>
                </td>
            </tr>
            
                <tr  id="companyIdsTr" receiverAttr="company">
                <td  nowrap><label   for="know.companyIds">选择公司*</label></td>
				<td colspan="3"><input name="know.companyIds"  id="know.companyIds" class="easyui-textbox"   data-options='multiple:true,data:<ts:forEach name="companyList" toJson="1"/>'  style="width:400px;height:30px">
                  </td>
           </tr> 
           <tr  id="deptIdsTr" receiverAttr="dept">
                <td  nowrap><label   for="know.deptIds">选择部门*</label></td>
				<td  colspan="3"><input name="know.deptIds" id="know.deptIds" class="easyui-textbox"   style="width:400px;height:30px"></input>   </td>
           </tr>
            <tr  id="employeeIdsTr" receiverAttr="employee">
                <td  nowrap><label   for="know.deptIds">选择员工*</label></td>
				<td  colspan="3"> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="knowledgeScript.showEmployeeDlg();">添加员工</a>
                <div id="employeeDlg" class="easyui-dialog" title="选择员工"  data-options="modal:true,closed:true" style="width:99%;height:400px;padding:10px"> 
				<table class="tablestyle" style="width: 100%; text-align: center;" id="employeeReceiverListTable">
					<tr class="haveborder" align="center">
						<td class="alt">操作</td>
						<td class="alt">序号</td>
						<td class="alt">工号</td>
						<td class="alt">姓名</td>
						<td class="alt">部门</td>
                        <td class="alt">公司</td>
					</tr>
                    <c:forEach items="${form.employeeReceiverList }" var="employ" varStatus="vs">
                      <c:if test="${employ.receiverType=='employee'}">
                    	<tr class="haveborder" cloneRowFlag="0"  align="center"  >
						<td><a href="javascript:void(0)" class="easyui-linkbutton" deleteRecordOperate="1" data-options="iconCls:'icon-cancel',plain:true"
		    onclick="$.ts.Utils.deleteDialogChooseRow(this,knowledgeScript.chooseEmployeesOptions)">删除</a></td>
						<td><span name="tableRowNum">${vs.index+1}</span></td>
						<td><input type="text" class="text" name="employeeReceiverList.number" readonly value="${employ.employee.number}"/>  </td>
						<td> <input type="text" class="text"   name="employeeReceiverList.name" readonly value="${employ.employee.name}"/>  
							 <input type="hidden" class="text"  name="employeeReceiverList.receiverId" value="${employ.receiverId}"/>
                            <input type="hidden" class="text"  name="employeeReceiverList.employeeId" value="${employ.employeeId}"/></td>
						<td><input type="text" class="text" name="employeeReceiverList.deptName" readonly value="${employ.employee.dept.name}"/> 
							<input type="hidden" class="text"  name="employeeReceiverList.deptId"  value="${employ.employee.dept.id}"/>
						</td>
                        <td><input type="text" class="text"   name="employeeReceiverList.companyName" readonly value="${employ.employee.company.name}"/> 
							<input type="hidden" class="text"  name="employeeReceiverList.orgId"  value="${employ.employee.company.id}"/>
                            <input type="hidden" class="text"  name="employeeReceiverList.recordOperateStatus" value="update"/>
                            <input type="hidden" class="text"  name="employeeReceiverList.id"  value="${employ.id}"/>
						</td>
					</tr> 
                    </c:if>
                    </c:forEach>
					<tr class="haveborder" cloneRowFlag="1"  align="center" style="display:none">
						<td><a href="javascript:void(0)" class="easyui-linkbutton" deleteRecordOperate="1" data-options="iconCls:'icon-cancel',plain:true"
		    onclick="$.ts.Utils.deleteDialogChooseRow(this,knowledgeScript.chooseEmployeesOptions)">删除</a></td>
						<td><span name="tableRowNum">1</span></td>
						<td><input type="text" class="text" name="employeeReceiverList.number" readonly/>  </td>
						<td> <input type="text" class="text"   name="employeeReceiverList.name" readonly/>  
							 <input type="hidden" class="text"  name="employeeReceiverList.receiverId"/>
                            <input type="hidden" class="text"  name="employeeReceiverList.employeeId"/></td>
						<td><input type="text" class="text" name="employeeReceiverList.deptName" readonly/> 
							<input type="hidden" class="text"  name="employeeReceiverList.deptId"/>
						</td>
                        <td><input type="text" class="text"   name="employeeReceiverList.companyName" readonly/> 
							<input type="hidden" class="text"  name="employeeReceiverList.orgId"/>
                            <input type="hidden" class="text"  name="employeeReceiverList.recordOperateStatus" value="add"/>
                            <input type="hidden" class="text"  name="employeeReceiverList.id"/>
						</td>
					</tr> 
				</table> 
			<div style="text-align: center;" class="dialog-button" id="formToolbar">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="$.ts.closeDialog(this);">确定</a>
			</div>
		</div> 
        <div id="insertEmployeeToolbar"  style="display:none">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="knowledgeScript.chooseEmployees(this);">批量添加</a>  
		</div></td>
           </tr>
            
              <tr>
                 <td nowrap><label  for="know.emergentLevel">信息级别 </label></td>
				<td>
					<c:if test="${form.know.emergentLevel=='0' }">
						<input type="radio" name="know.emergentLevel" checked="checked" value="0"  />普通
					</c:if>
					<c:if test="${form.know.emergentLevel!='0' }">
						<input type="radio" name="know.emergentLevel"  value="0"  />普通
					</c:if>
					<c:if test="${form.know.emergentLevel=='1' }">
						<input type="radio" name="know.emergentLevel" checked="checked" value="1"  />重要
					</c:if>
					<c:if test="${form.know.emergentLevel!='1' }">
						<input type="radio" name="know.emergentLevel"  value="1"  />重要
					</c:if>
                </td>
                <td  nowrap><label   for="know.mobileHome">为APP端首页</label></td>
                <td>
					<c:if test="${form.know.mobileHome=='1' }">
						<input type="radio" name="know.mobileHome" checked="checked" value="1"  />是
					</c:if>
					<c:if test="${form.know.mobileHome!='1' }">
						<input type="radio" name="know.mobileHome"  value="1"  />是
					</c:if>
					<c:if test="${form.know.mobileHome=='0' }">
						<input type="radio" name="know.mobileHome" checked="checked" value="0"  />否
					</c:if>
					<c:if test="${form.know.mobileHome!='0' }">
						<input type="radio" name="know.mobileHome"  value="0"  />否
					</c:if>
                </td>
				
			</tr>  
			
			<tr> 
             <td colspan="4" ><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="knowledgeScript.uploadFile(this);"><label  for="knowledgeFileList">上传附件</label></a> 
             <table id="knowledgeFileListTable">
             <c:forEach items="${form.knowledgeFileList}" var="file" varStatus="vs">
             	<tr cloneRowFlag="0"  ><td> <a href="javascript:void(0)" class="easyui-linkbutton" deleteRecordOperate="1" data-options=" plain:true"
                onclick="$.ts.EasyUI.deleteTableRow(this)">删除</a><span id="fieldName" style="border:1px solid #03C; background:#FFC">${file.fileName}</span><input type="file" name="knowledgeFileList.file"    style="display:none" />
                 <input name="knowledgeFileList.id"   type="hidden"  value="${file.id}"/>
                 <input name="knowledgeFileList.recordOperateStatus"   type="hidden"  value="update"/></td>
                 </tr>
             </c:forEach>
                 <tr cloneRowFlag="1" style="display:none" ><td> <a href="javascript:void(0)" class="easyui-linkbutton" deleteRecordOperate="1" data-options=" plain:true"
                onclick="$.ts.EasyUI.deleteTableRow(this)">删除</a><span id="fieldName" style="border:1px solid #03C; background:#FFC"></span><input type="file" name="knowledgeFileList.file"    style="display:none" />
                 <input name="knowledgeFileList.id"   type="hidden" />
                 <input name="knowledgeFileList.recordOperateStatus"   type="hidden" value="add" /></td>
                 </tr>
             </table>
             </td>
           </tr>
			
			<tr  > 
				<td    colspan="4"  ><textarea   name="know.content" id="know.content"  style="height:400px;width:800px">${form.know.content }</textarea></td>
			</tr>
		</table>
</div> 
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return knowledgeScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
 </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/Knowledge.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/jquery/plugs/ckeditor/ckeditor.js?version=1.1"></script>
<script type="text/javascript">
    var knowledgeScript=new KnowledgeScript();  
    $(function() { 
    	knowledgeScript.initPage({appKey:"${appReqeustContext.appKey}"});  
 	   
 	});
	 var editor=null;
	 function createEditor(){
		// var editor = CKEDITOR.inline( 'bean.description' );
		 editor = CKEDITOR.replace( 'know.content',
		{   
			filebrowserUploadUrl:tsContextPath+'/ckeditor/uploader',
			filebrowserImageUploadUrl : tsContextPath+'/ckeditor/uploader?type=Image',
		    filebrowserFlashUploadUrl : tsContextPath+'/ckeditor/uploader?type=Flash' ,
			filebrowserImageBrowseUrl : tsContextPath+'/ckeditor/uploader?type=image' 
		});
	}
	createEditor(); 
	 
</script>      
</form> 
</body>
</html>