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
 <form action="${contextPath}/core/${appReqeustContext.appService}/json/update.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post">  
 <div id="${appReqeustContext.appKey}FormJson" style="display:none">${formJson}</div> 
 <div style="display:none" id="copyDialogContent"></div> 
   <input name="bean.id" type="hidden"  /><input name="actionType" type="hidden" value="update"/>  
<div style="padding:10px;padding-left:5%">
		<table cellpadding="0" cellspacing="0" class="baseForm-table">
			<tr  >
				<td nowrap><label for="bean.name">标题*</label></td>
				<td    ><input name="bean.name"  id="bean.name"  class="easyui-textbox"   style="width:300px;height:30px"></input>
               
                </td> 
                <td nowrap><label  for="bean.status">状态</label></td>
				<td><input type="radio" name="bean.status" value="1"    />启用
                <input type="radio" name="bean.status"  value="0"  />停用</td> 
			</tr>   
            <tr  >
                <td  nowrap><label   for="bean.topicKind">信息类别*</label></td>
				<td> <select name="bean.topicKind"  id="bean.topicKind" class="easyui-combobox"  data-options="editable:false"  style="width:180px;height:30px" > 
                                <ts:forEach name='topicKindList'  insertEmpty='0'/>
							</select></td>
                   <td  nowrap><label   for="bean.bulletinTopicId">栏目</label></td>
				<td> <select name="bean.bulletinTopicId"  id="bean.bulletinTopicId" class="easyui-combobox"  data-options="editable:false,valueField:'id',textField:'text'"  style="width:180px;height:30px"  value="${form.bean.bulletinTopicId}"> 
							</select></td>
            </tr> 
           <tr  >
                <td  nowrap><label   for="bean.receiverType">接收者类型</label></td>
				<td> <select name="bean.receiverType"  id="bean.receiverType" class="easyui-combobox"  data-options="editable:false"  style="width:180px;height:30px" > 
                              <ts:forEach name='receiverTypeList' value='0' insertEmpty='0'/>
							</select></td>
                 <td nowrap><label  for="bean.needReply">需要回复 </label></td>
				<td><input type="radio" name="bean.needReply"    value="1"/>需要
                <input type="radio" name="bean.needReply"  value="0"  />不需要</td> 
           </tr>   
           <tr  id="companyIdsTr" receiverAttr="company">
                <td  nowrap><label   for="bean.companyIds">选择公司*</label></td>
				<td colspan="3"><input name="bean.companyIds"  id="bean.companyIds" class="easyui-textbox"   data-options='multiple:true,data:<ts:forEach name="companyList" toJson="1"/>'  style="width:400px;height:30px">
                  </td>
           </tr> 
           <tr  id="deptIdsTr" receiverAttr="dept">
                <td  nowrap><label   for="bean.deptIds">选择部门*</label></td>
				<td  colspan="3"><input name="bean.deptIds" id="bean.deptIds" class="easyui-textbox"   style="width:400px;height:30px"></input>   </td>
           </tr>
            <tr  id="employeeIdsTr" receiverAttr="employee">
                <td  nowrap><label   for="bean.deptIds">选择员工*</label></td>
				<td  colspan="3"> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="bulletinScript.showEmployeeDlg();">添加员工</a>
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
		    onclick="$.ts.Utils.deleteDialogChooseRow(this,bulletinScript.chooseEmployeesOptions)">删除</a></td>
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
		    onclick="$.ts.Utils.deleteDialogChooseRow(this,bulletinScript.chooseEmployeesOptions)">删除</a></td>
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
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="bulletinScript.chooseEmployees(this);">批量添加</a>  
		</div></td>
           </tr>
           <tr  id="custIdsTr" receiverAttr="cust">
                <td  nowrap><label    >选择客户*</label></td>
				<td  colspan="3"> <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="bulletinScript.showCustDlg();">添加客户</a>
                <div id="custDlg" class="easyui-dialog" title="选择客户"  data-options="modal:true,closed:true" style="width:99%;height:400px;padding:10px"> 
				<table class="tablestyle" style="width: 100%; text-align: center;" id="custReceiverListTable">
					<tr class="haveborder" align="center">
						<td class="alt">操作</td>
						<td class="alt">序号</td>
						<td class="alt">客户编号</td>
						<td class="alt">客户名称</td>
						<td class="alt">客户类别</td> 
					</tr>
                    <c:forEach items="${form.employeeReceiverList }" var="cust" varStatus="vs">
                      <c:if test="${employ.receiverType=='cust'}">
                          <tr class="haveborder" cloneRowFlag="0"  align="center"  >
                            <td><a href="javascript:void(0)" class="easyui-linkbutton" deleteRecordOperate="1" data-options="iconCls:'icon-cancel',plain:true"
                onclick="$.ts.Utils.deleteDialogChooseRow(this,bulletinScript.custsOptions)">删除</a></td>
                            <td><span name="tableRowNum">${vs.index+1}</span></td>
                            <td><input type="text" class="text" name="custReceiverList.number" readonly  value="${cust.customer.number}"/>  </td>
                            <td> <input type="text" class="text"  name="custReceiverList.name" readonly value="${cust.customer.name}"/>  
                                 <input type="hidden" class="text"  name="custReceiverList.receiverId"  value="${cust.receiverId}"/>
                                <input type="hidden" class="text"  name="custReceiverList.custId" value="${cust.receiverId}"/></td>
                            <td><input type="text" class="text" name="custReceiverList.custTypeName" readonly  value="<ts:forEach name='custTypeNameList' value='${cust.customer.custType}' outValue='1'/>"/> 
                            <input type="hidden" class="text"  name="custReceiverList.recordOperateStatus" value="update"/>
                                <input type="hidden" class="text"  name="custReceiverList.id"  value="${cust.id}"/> 
                            </td> 
                        </tr>
                      </c:if>
                    </c:forEach>
					<tr class="haveborder" cloneRowFlag="1"  align="center" style="display:none">
						<td><a href="javascript:void(0)" class="easyui-linkbutton" deleteRecordOperate="1" data-options="iconCls:'icon-cancel',plain:true"
		    onclick="$.ts.Utils.deleteDialogChooseRow(this,bulletinScript.custsOptions)">删除</a></td>
						<td><span name="tableRowNum">1</span></td>
						<td><input type="text" class="text" name="custReceiverList.number" readonly/>  </td>
						<td> <input type="text" class="text"  name="custReceiverList.name" readonly/>  
							 <input type="hidden" class="text"  name="custReceiverList.receiverId"/>
                            <input type="hidden" class="text"  name="custReceiverList.custId"/></td>
						<td><input type="text" class="text" name="custReceiverList.custTypeName" readonly/> 
                        <input type="hidden" class="text"  name="custReceiverList.recordOperateStatus" value="add"/>
                            <input type="hidden" class="text"  name="custReceiverList.id"/> 
						</td> 
					</tr> 
				</table> 
			<div style="text-align: center;" class="dialog-button" id="formToolbar">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="$.ts.closeDialog(this);">确定</a>
			</div>
		</div> 
        <div id="custToolbar"  style="display:none">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="bulletinScript.chooseCusts(this);">批量添加</a>  
		</div></td>
           </tr>
            <tr>
                 <td nowrap><label  for="bean.emergentLevel">信息级别 </label></td>
				<td><input type="radio" name="bean.emergentLevel"    value="0"  />普通
                <input type="radio" name="bean.emergentLevel"  value="1" />紧急</td>
                 <td  nowrap><label   for="bean.mobileHome">为APP端首页</label></td> 
				<td><input type="radio" name="bean.mobileHome"    value="1"/>是
                <input type="radio" name="bean.mobileHome"  value="0"  />否</td>  
				
			</tr>  
           <tr> 
             <td colspan="4" ><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="bulletinScript.uploadFile(this);"><label  for="bulletinFileList">上传附件</label></a> 
             <table id="bulletinFileListTable">
             <c:forEach items="${form.bulletinFileList}" var="file" varStatus="vs">
             	<tr cloneRowFlag="0"  ><td> <a href="javascript:void(0)" class="easyui-linkbutton" deleteRecordOperate="1" data-options=" plain:true"
                onclick="$.ts.EasyUI.deleteTableRow(this)">删除</a><span id="fieldName" style="border:1px solid #03C; background:#FFC">${file.fileName}</span><input type="file" name="bulletinFileList.file"    style="display:none" />
                 <input name="bulletinFileList.id"   type="hidden"  value="${file.id}"/>
                 <input name="bulletinFileList.recordOperateStatus"   type="hidden"  value="update"/></td>
                 </tr>
             </c:forEach>
                 <tr cloneRowFlag="1" style="display:none" ><td> <a href="javascript:void(0)" class="easyui-linkbutton" deleteRecordOperate="1" data-options=" plain:true"
                onclick="$.ts.EasyUI.deleteTableRow(this)">删除</a><span id="fieldName" style="border:1px solid #03C; background:#FFC"></span><input type="file" name="bulletinFileList.file"    style="display:none" />
                 <input name="bulletinFileList.id"   type="hidden" />
                 <input name="bulletinFileList.recordOperateStatus"   type="hidden" value="add" /></td>
                 </tr>
             </table>
             </td>
           </tr>  
			<tr  > 
				<td    colspan="4"  ><textarea   name="bean.description" id="bean.description"  style="height:400px;width:800px"  >${form.bean.description}</textarea></td>
			</tr>
		</table> 
</div> 
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return bulletinScript.submitForm(this);">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div> 
<script type="text/javascript" src="<ts:base ref='path'/>/Bulletin.js?version=1.1"></script> 
<script type="text/javascript" src="${contextPath}/scripts/jquery/plugs/ckeditor/ckeditor.js?version=1.1"></script>
 
<script type="text/javascript">
    var bulletinScript=new BulletinScript();  
	var formJson=null;
	$(function() { 
	   var jsonContentObj=$("#${appReqeustContext.appKey}FormJson"); 
	   formJson=jQuery.parseJSON(jsonContentObj.text());   
	   formJson.bean.description=undefined; 
	   delete  formJson.employeeReceiverList; 
	   jsonContentObj.remove();  
	   $('#${appReqeustContext.appKey}Form').form('tsLoad',formJson); 
	   bulletinScript.initPage({appKey:"${appReqeustContext.appKey}"}); 
	   bulletinScript.editInitReceiverType();
	   
	});  
	 
	 var editor=null;
	 function createEditor(){
		// var editor = CKEDITOR.inline( 'bean.description' );
		 editor = CKEDITOR.replace( 'bean.description',
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