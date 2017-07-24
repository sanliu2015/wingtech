 
function WorkFlowAuditScript( ){ 
	 this.appKey=""; 
	 this.opts=null;
	 this.nextAuditLogList=null;
	 this.chooseEmployeesOptions=null;
	 this.chooseOrderProductOptions=null;
 } 
 WorkFlowAuditScript.prototype={  
   initPage:function(option){  
		this.opts= $.extend({},option);  
	},
	insertSubscribeDatagridRow:function(rowBean){
	    var rowOpt={};
		if($.ts.Utils.isEmpty(rowBean)==false){
			rowOpt=rowBean;
		} 
		var table=$('#subscribeLogListGrid'); 
	   table.datagrid('appendRow',{}); 
	   var ids=document.getElementsByName("subscribeLogList.id");  
	   var  row=$(ids[ids.length-1]).parent().parent().parent() ;
	   $.parser.parse(row); 
	   if($.ts.Utils.isEmpty(rowBean)==false){
	  	  row.tsLoadData(rowBean);
	   } 
	   return row; 
   },
    setChooseEmployeeOptions:function(){
		if($.ts.Utils.isEmpty(this.chooseOrderProductOptions)==false ) return;
		var recDataTemplate=[{field:["id","subscribeLogList.appointAuditorId"],
		                     sameRecordFlag:"1"},
			  {field:["deptId","subscribeLogList.departmentId"]},{field:["number","subscribeLogList.orderNo"]},
			  {field:["companyId","subscribeLogList.orgId"]},{field:["name","subscribeLogList.assignmentName"]},
			  {field:["name","subscribeLogList.appointAuditor"]},{field:["departmentname","subscribeLogList.deptName"]}];
		var selectedRows=null;  
		 var ids=document.getElementsByName("subscribeLogList.appointAuditorId");  
		 selectedRows=[]; 
		 for(var i=0;i<ids.length;i++){
			 selectedRows[i]={"appointAuditorId":ids[i].value}; 
			 
			 } 
		 
		var chooseOrderProductsOptions={selectedRows:selectedRows, rowPrefix:"subscribeLogList", operatePattern:"insertRow",operateTarget:null,
			    copyRows:1, containerObj:$("#subscribeLogListGrid"),  recDataTemplate:recDataTemplate 
		};
		this.chooseOrderProductOptions=chooseOrderProductsOptions; 
		return chooseOrderProductsOptions;
	},
	chooseSubscribers:function(obj){   
	      var urlJson={urlType:"chooseRows",  moduleFileName:"EmployeeSerivceReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);   
		  this.chooseOrderProductOptions.operateTarget=obj;
		  var handler=$.ts.EasyUI.frameDialog({title:"选择查阅人员", href:url,width : 900},this.chooseOrderProductOptions);    
	},
	subscriberTextInputFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");    
		return  "<input   name='subscribeLogList."+fieldName+"' id='subscribeLogList."+fieldName+"' value='" + val + "'  style='width:100%'  class='easyui-textbox' readonly/>";
	 },
	 subscriberHiddenColumnFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");   
		return  "<input type='hidden' name='subscribeLogList."+fieldName+"' id='subscribeLogList."+fieldName+"' value='" + val + "'   />";
	 },
	 subscriberRemoveRowFormat:function(val, row, index) {
		if(val == undefined)	val = ""; 
		var  text='<a href="javascript:void(0)" deleteRecordOperate="1" class="easyui-linkbutton" plain="true" iconCls="icon-cancel"  onClick="$.ts.EasyUI.deleteTableRow(this)"></a> '; 
		return  text;
	 },
	dyniframesize:function (down) { 
		var pTar = null; 
		if (document.getElementById){ 
			pTar = document.getElementById(down); 
		}else{ 
			eval('pTar = ' + down + ';'); 
		} 
		if (pTar && !window.opera){ 
			//begin resizing iframe 
			pTar.style.display="block";
			if (pTar.contentDocument && pTar.contentDocument.body.offsetHeight){ 
				//ns6 syntax 
				pTar.height = pTar.contentDocument.body.offsetHeight +20; 
				pTar.width = pTar.contentDocument.body.scrollWidth+20; 
			}else if (pTar.Document && pTar.Document.body.scrollHeight){ 
				//ie5+ syntax 
				pTar.height = pTar.Document.body.scrollHeight; 
				pTar.width = pTar.Document.body.scrollWidth; 
			}  
			 if( $(pTar.contentDocument.body).find("form").find("#workFlowFlag").length==0){ 
			   //	var idNode=document.getElementsByName("bean.id");  
			   
				$(pTar.contentDocument.body).find("form").prepend('<input name="workFlowFlag" id="workFlowFlag" type="hidden" value="1"/>'); 
		     } else {
		  	 	$(pTar.contentDocument.body).find("form").find("#workFlowFlag").val("1");
		    }
		   
			var cancelButton=$(pTar.contentDocument.body).find("[iconCls='icon-cancel']");
			if(cancelButton.length>0){
				cancelButton.hide();
			}
			var formToolbarJq=$(pTar.contentDocument.body).find("#formToolbar");
			if(formToolbarJq.length>0){
				formToolbarJq.hide();
			}
		} 
	} ,
	setAppAuditStatusValue:function (){
		var auditStatus=document.getElementsByName("workFlowAuditStatus");
		if(auditStatus==null) return;
		for(var i=0;i<auditStatus.length;i++){
			if(auditStatus[i].checked==true){
				document.getElementById('workFlowAuditLog.auditStatus').value=auditStatus[i].value;  
				document.getElementById('workFlowAuditLog.description').value=auditStatus[i].alt;    
				
				break;
			}
		} 
		if(document.getElementById('workFlowAuditLog.auditStatus').value!="1"){
			//this.setRowsCheckStatus();
		}
  },
	setAuditStatusValue:function (){
		var auditStatus=document.getElementsByName("workFlowAuditStatus");
		if(auditStatus==null) return;
		for(var i=0;i<auditStatus.length;i++){
			if(auditStatus[i].checked==true){
				document.getElementById('workFlowAuditLog.auditStatus').value=auditStatus[i].value;  
				//document.getElementById('workFlowAuditLog.description').value=auditStatus[i].alt; 
				$("#workFlowAuditLog\\.description").textbox("setValue",auditStatus[i].alt);  
				if(document.getElementById('workFlowAuditLog.auditStatus').value=="12"){
					   
					 $('#choseNextTaskAuditorGrid').datagrid('unselectAll');
				}
				break;
			}
		} 
		if(document.getElementById('workFlowAuditLog.auditStatus').value!="1"){
			//this.setRowsCheckStatus();
		}
  },
   textInputFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");    
		return  "<input   name='nextAuditLogList."+fieldName+"' id='nextAuditLogList."+fieldName+"' value='" + val + "'  style='width:100%'  class='easyui-textbox' readonly/>";
	 },
  textAndSearchInputFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");    
		
		if(val==""){ 
			return  "<input  name='nextAuditLogList."+fieldName+"' id='nextAuditLogList."+fieldName+"' value='" + val + "'  style='width:75%'  class='easyui-textbox' readonly/><a href='javascript:void(0)' class='easyui-linkbutton' id="+fieldName+"LinkButton  plain='true' iconCls='icon-search' tsOptions='needHide:\'1\'' onClick='return workFlowAuditScript.chooseEmployee(this);'></a>";
		} else { 
			return  "<input   name='nextAuditLogList."+fieldName+"' id='nextAuditLogList."+fieldName+"' value='" + val + "'  style='width:100%'  class='easyui-textbox'/>";
		}
		
  },
   textAndSearchInputFormats:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");     
		 return  "<input  name='nextAuditLogList."+fieldName+"' id='nextAuditLogList."+fieldName+"' value='" + val + "'  style='width:75%'  class='easyui-textbox' readonly/><a href='javascript:void(0)' class='easyui-linkbutton' id="+fieldName+"LinkButton  plain='true' iconCls='icon-search'   onClick='return workFlowAuditScript.chooseEmployees(this);'></a>";
		 
		
	 },
  readProcessDefinitionResource:function(){
	  
	  var url=tsContextPath+"/core/workFlowAuditService/lookupProcessTrack.do?";
	  url=url+"processInstanceId="+$("#workFlowAuditLog\\.processInstanceId").val(); 
	   
	  $.ts.EasyUI.frameDialog(  {  
				width : 800, height : $(document).height()-50,title:"流程跟踪", modal:false, method:"POST",  cache:false,
				collapsible: true, maximizable: true, resizable:true,href:url   
	  } );
  },
   hiddenColumnFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");   
		return  "<input type='hidden' name='nextAuditLogList."+fieldName+"' id='nextAuditLogList."+fieldName+"' value='" + val + "'   />";
	 },
  insertNextTaskAuditorGridRow:function(rowBean){
	    
		var table=$('#choseNextTaskAuditorGrid'); 
	    table.datagrid('appendRow',{}); 
	    var ids=document.getElementsByName("nextAuditLogList.taskDefineKey");  
	    var  row=$(ids[ids.length-1]).parent().parent().parent() ;
	    $.parser.parse(row);
	    return row;  
  },
  chooseEmployee:function(obj){     
	      var urlJson={urlType:"chooseRow",  moduleFileName:"EmployeeSerivceReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择员工",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							  var row=$(obj).parent().parent().parent(); 
							  row.find("#nextAuditLogList\\.assignmentName").textbox('setValue',json.name); 
							  row.find("#nextAuditLogList\\.delegaterIds").val(json.id);   
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	chooseEmployees:function(obj){     
	      var urlJson={urlType:"chooseRow",  moduleFileName:"EmployeeSerivceReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择员工",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							  var row=$(obj).parent().parent().parent(); 
							  row.find("#nextAuditLogList\\.appointAuditor").textbox('setValue',json.name); 
							  row.find("#nextAuditLogList\\.appointAuditorId").val(json.id);   
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
  appendNextTaskAuditorGridRow:function(rowBean){
	    
		var table=$('#choseNextTaskAuditorGrid'); 
	    table.datagrid('appendRow',{"delegaterKindName":"个人"}); 
	    var ids=document.getElementsByName("nextAuditLogList.taskDefineKey");  
		
	    var  row=$(ids[ids.length-1]).parent().parent().parent() ;
		if(ids.length>1){
		  var  prevRow=$(ids[ids.length-2]).parent().parent().parent() ;
		  row.find("input[name='nextAuditLogList.taskDefineKey']").val(prevRow.find("input[name='nextAuditLogList.taskDefineKey']").val()); 
		  row.find("input[name='nextAuditLogList.taskName']").val(prevRow.find("input[name='nextAuditLogList.taskName']").val());
		}
	    $.parser.parse(row);
	    return row; 
  },
  submitAppForm:function (obj){//点击按钮提交 
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId="#"+this.opts.appKey+"Form";
		 var url=$(formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 //$(bodyId).mask();  
		 var auditStatus=document.getElementById("workFlowAuditLog.auditStatus").value;  
		 var index=$.ts.EasyUI.submitRowsToList("nextAuditLogList.taskDefineKey",$("#submitAuditNodeTable"),{effectRowFields:["assignmentName" ]}); 
		 if(auditStatus=="1" && index==0){
			 if(workFlowAuditScript.nextAuditLogList!=null && workFlowAuditScript.nextAuditLogList.length>0){
				 alert('提示',"下级审核人不能为空！"); 
				 return false;
			 }
		 }     
	  	 $.ts.EasyUI.ajaxSubmitAppForm(url,formId,function(){ 
		     
		     var urls=contextPath+$("#appReturnUrl").val(); 
			  mui.openWindow({
					 url:urls
				});
		 } );
		return false;
	 } ,
  submitForm:function (obj){//点击按钮提交 
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId="#"+this.opts.appKey+"Form";
		 var url=$(formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 //$(bodyId).mask();
		 var auditStatus=document.getElementById("workFlowAuditLog.auditStatus").value;  
		 var index=$.ts.EasyUI.submitRowsToList("nextAuditLogList.taskDefineKey",$("#choseNextTaskAuditorGrid").parent(),{effectRowFields:["assignmentName" ]}); 
		 if(auditStatus=="1" && index==0){
			 if(workFlowAuditScript.nextAuditLogList!=null && workFlowAuditScript.nextAuditLogList.length>0){
				 $.messager.alert('提示',"下级审核人不能为空！"); 
				 return false;
			 }
		 }    
		 if(this.canAddSubscriber=="1"){
			var index=$.ts.EasyUI.submitRowsToList("subscribeLogList.appointAuditor",$("#subscribeLogListGrid").parent(),{effectRowFields:["appointAuditor" ]}); 
		 
		 }
	  	 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 } );
		return false;
	 },
	 submitSubscribeForm:function (obj){//点击按钮提交 
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId="#"+this.opts.appKey+"Form";
		 var url=$(formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 //$(bodyId).mask();
		 var auditStatus=document.getElementById("workFlowAuditLog.auditStatus").value;  
		 var index=$.ts.EasyUI.submitRowsToList("nextAuditLogList.taskDefineKey",$("#choseNextTaskAuditorGrid").parent(),{effectRowFields:["assignmentName" ]}); 
		 if(auditStatus=="1" && index==0){
			 if(workFlowAuditScript.nextAuditLogList!=null && workFlowAuditScript.nextAuditLogList.length>0){
				 $.messager.alert('提示',"下级审核人不能为空！"); 
				 return false;
			 }
		 }    
		 if(this.canAddSubscriber=="1"){
			var index=$.ts.EasyUI.submitRowsToList("subscribeLogList.appointAuditor",$("#subscribeLogListGrid").parent(),{effectRowFields:["appointAuditor" ]}); 
		 
		 }
	  	 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 
		 } );
		return false;
	 },
	  submitAndModifyBillForm:function (obj){//点击按钮提交 
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId="#"+this.opts.appKey+"Form";
		 var url=$(formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 //$(bodyId).mask();  
		  
		  
	  	 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 var urls=tsContextPath+document.getElementById("workFlowAuditLog.editUrl").value; 
			 $.ts.EasyUI.closeDialog(obj); 
				 $.ts.EasyUI.modalDialog(  {  
					width : 800, 
					height : $(document).height()-5,
					modal:false,
					title:"修改单据",
					method:"POST", 
					cache:false,
					collapsible: true,  
					maximizable: true, 
					maximized:true,
					resizable:true, 
					href : urls   
				} );  
		 } );
		return false;
	 } , 
	 lookupHistoricProcess:function(){
		 $.ts.Utils.lookupHistoricProcess(document.getElementById("workFlowAuditLog.processInstanceId").value); 
		 
	 },
	modifyBillForm:function(){
		var urls=tsContextPath+document.getElementById("workFlowAuditLog.editUrl").value; 
		urls=urls+"&operateAuthCheckFlag=0";
		var closeCallbackEvent=function(){
		 	 //document.getElementById('workFlowRefBillIframe').contentWindow.location.reload(true);
			 $('#workFlowRefBillIframe').attr('src', $('#workFlowRefBillIframe').attr('src'));
	   };	 
	   var initLoadPageCallback=function(){  
		   if($(document.forms[document.forms.length-1]).find("#workFlowFlag").length==0){ 
			   //	var idNode=document.getElementsByName("bean.id");  
				$(document.forms[document.forms.length-1]).prepend('<input name="workFlowFlag" id="workFlowFlag" type="hidden" value="2"/>'); 
		   } else {
		  	 	$(document.forms[document.forms.length-1]).find("#workFlowFlag").val("2");
		   }
	   }
		 $.ts.EasyUI.modalDialog(  {  
			width : 800, 
			height : $(document).height()-15,
			modal:false,
			title:"修改单据",
			method:"POST", 
			cache:false,
			collapsible: true,  
			maximizable: true, 
			maximized:true,
			resizable:true, 
			modal:true,
			href : urls   
		},closeCallbackEvent ,initLoadPageCallback);  
	}
}
