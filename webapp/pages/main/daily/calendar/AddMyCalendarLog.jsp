<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
 <title>添加${appReqeustContext.appName}</title> 
</head>
<body id="${appReqeustContext.appKey}Body"   >  
 <form action="${contextPath}/main/${appReqeustContext.appService}/json/saveLog.do" id="${appReqeustContext.appKey}Form" name="${appReqeustContext.appKey}Form" method="post"> 
   <input name="log.id" type="hidden"  />  <input name="log.status" type="hidden"    value="1"/>   
   <input name="log.summaryWeek" type="hidden"   value="${form.log.summaryWeek}"/> 
   <input name="log.employee.id" type="hidden"   value="${form.log.employee.id}"/> 
<div style="padding:10px;padding-left:20%"> 
        <table cellpadding="0" cellspacing="0" class="baseForm-table">
			<tr  >
				<td nowrap><label id="number-label" for="log.name">时间</label></td>
				<td style="width:260px"><input name="log.name"  id="log.name"  class="easyui-textbox" readonly   style="width:180px;height:30px" value="${form.log.name}"></input></td> 
			</tr>
            <tr  >
				<td nowrap><label  for="log.summaryDate">日期</label></td>
				<td  ><input name="log.summaryDate"  id="log.summaryDate"  class="easyui-my97" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:function(dp){myCalendarUtil.changeLogDateTitile(this);return true;}})" style="width:130px;height:30px"   value="${form.log.summaryDate}"></input> 
				 <input type="radio" name="log.dayType"    value="am" onClick="myCalendarUtil.changeLogDateTitile(this)" ${form.log.dayType=='am'?'checked':""} />上午
                <input type="radio" name="log.dayType"  value="pm"  onClick="myCalendarUtil.changeLogDateTitile(this)" ${form.log.dayType=='pm'?'checked':""} />下午</td> 
			</tr>  
             <tr  >
               <td  nowrap><label   for="log.planId">关联计划</label></td>
				<td> <select name="log.planId"  id="log.planId" class="easyui-combobox"  data-options="editable:false"  style="width:180px;height:30px" > 
                  <ts:forEach name='form.planList' keyField='id' valueField='eventExplain' insertEmpty='1' value=''/>
				 </select></td>
            </tr>
            <tr  > 
           <td  nowrap><label   for="log.summaryEvent">完成事项*</label></td>
				<td  > <input name="log.summaryEvent" id="log.summaryEvent" class="easyui-textbox"     style="width:180px;height:30px"  ></input></td>                 
            </tr> 
            <tr  >
                <td  nowrap><label   for="plan.custName">拜访客户</label></td>
				<td><input name="log.custName" id="log.custName" class="easyui-textbox"     style="width:180px;height:30px"  ></input><a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return myCalendarUtil.choseCust(this);">选择</a></td>
           </tr>    
            <tr  >
                <td  nowrap><label   for="log.place">地点 </label></td>
				<td><input name="log.place" id="log.place" class="easyui-textbox"  value="${form.log.place}"   style="width:180px;height:30px"  ></input></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="log.contactPerson">接洽人 </label></td>
				<td><input name="log.contactPerson" id="log.contactPerson" class="easyui-textbox"  value="${form.log.contactPerson}"   style="width:180px;height:30px"  ></input></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="log.contactDept">部门 </label></td>
				<td><input name="log.contactDept" id="log.contactDept" class="easyui-textbox"  value="${form.log.contactDept}"   style="width:180px;height:30px"  ></input></td>
           </tr> 
           <tr  >
                <td  nowrap><label   for="log.contactPhone">电话 </label></td>
				<td><input name="log.contactPhone" id="log.contactPhone" class="easyui-textbox"  value="${form.log.contactPhone}"   style="width:180px;height:30px"  ></input></td>
           </tr>  
            <tr  >
                <td  nowrap><label   for="plan.purpose">交谈目的 </label></td>
				<td><input name="log.purpose" id="log.purpose" class="easyui-textbox"   data-options="multiline:true"  style="height:50px;width:400px"  value="${form.log.purpose}" ></input></td>
           </tr>  
			<tr  >
				<td nowrap><label   for="plan.description">结果</label></td>
				<td  ><input class="easyui-textbox" name="log.description"   data-options="multiline:true" style="height:70px;width:400px" value="${form.log.description}" ></input></td>
			</tr>
            <tr  >
             <td nowrap align="left"><label id="number-label" for="log.file">上传附件</label>   </td> 
                <td  > <input type="file" name="log.file"    />   </td> 
               </tr>
		</table>
</div> 
 <div style="text-align:center;padding:5px; width:98%" class="dialog-button"> 
    <a href="javascript:void(0)" class="easyui-linkbutton" id="feeSubmitBtn" iconCls="icon-tip"  onClick=" return myCalendarUtil.submitLogForm(this,'1');"   style="list-style:none;float:left;text-align:left">保存并添加费用报销单</a> 
 			 <a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return myCalendarUtil.submitLogForm(this,'0');">确定</a>  
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>     
     </div> 
 <c:if test="${form.actionType=='1'}">
	<script type="text/javascript" src="<ts:base ref='path'/>/MyCalendar.js?version=1.1"></script> 
    <script language="javascript">
    var  myCalendarUtil=null; 
    $(function(){ 
         var defaults={ userId:'${appReqeustContext.user.id}',empId:'${appReqeustContext.user.employeeId}' };
         myCalendarUtil=new MyCalendarUtil(defaults);  
         myCalendarUtil.initPage();    
          
    });
    </script>
</c:if>
 <script type="text/javascript">
 	 var planList= <ts:toJSONStr name='form.planList'   />;
	 myCalendarUtil.changePlanId();
	// alert( planList[0].custName);
 </script>       
</form> 

</body>
</html>