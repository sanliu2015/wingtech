function closeDialogCallbackEvent(){
		bulletinScript.removeDialogs(); 
}
function BulletinScript( ){ 
	 this.appKey=""; 
	 this.opts=null;
	 this.chooseEmployeesOptions=null;
 } 
BulletinScript.prototype={  
   initPage:function(option){
		this.opts= $.extend({},option);    
		this.changeTopicKind(); 
		$("#bean\\.companyIds").combotree();
		this.hiddenReceivers();
		this.changeReceiverType();
		this.setChooseEmployeeOptions();
		this.setCustOptions();
		 
	},    
	removeDialogs:function(){  
	     
		$("#employeeDlg").dialog({onClose:function() { $(this).remove();}});
		$("#employeeDlg").dialog('close');
		$("#custDlg").dialog({onClose:function() { $(this).remove();}});
		$("#custDlg").dialog('close'); 
		 
	},
	setChooseEmployeeOptions:function(){
		if($.ts.Utils.isEmpty(this.chooseEmployeesOptions)==false ) return;
		var recDataTemplate=[{field:["id","employeeReceiverList.receiverId","employeeReceiverList.employeeId"],
		                     sameRecordFlag:"1"},{field:["name","employeeReceiverList.name"]},
			  {field:["departmentname","employeeReceiverList.deptName"]},{field:["number","employeeReceiverList.number"]},
			  {field:["companyName","employeeReceiverList.companyName"]}];
		var chooseEmployeesOptions={selectedRows:null,  operatePattern:"insertRow",operateTarget:null,
			    copyRows:1, container:"employeeReceiverListTable",  recDataTemplate:recDataTemplate 
		};
		this.chooseEmployeesOptions=chooseEmployeesOptions;
		return chooseEmployeesOptions;
	},
	chooseEmployees:function(obj){   
	      var urlJson={urlType:"chooseRows",  moduleFileName:"EmployeeSerivceReport",timeStamp:""+(new Date()).getTime()}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);   
		  this.chooseEmployeesOptions.operateTarget=obj;
		  var handler=$.ts.EasyUI.frameDialog({title:"选择员工", href:url},this.chooseEmployeesOptions);    
	},
	setCustOptions:function(){
		if($.ts.Utils.isEmpty(this.custsOptions)==false ) return;
		var recDataTemplate=[{field:["id","custReceiverList.receiverId","custReceiverList.custId"],
		                     sameRecordFlag:"1"},{field:["custName","custReceiverList.name"]},
			  {field:["custTypeName","custReceiverList.custTypeName"]},{field:["number","custReceiverList.number"]}];
		var custsOptions={selectedRows:null,  operatePattern:"insertRow",operateTarget:null,
			    copyRows:1, container:"custReceiverListTable",  recDataTemplate:recDataTemplate 
		};
		this.custsOptions=custsOptions;
		return custsOptions;
	},
	chooseCusts:function(obj){   
	      var urlJson={urlType:"chooseRows",  moduleFileName:"CustomerSerivceReport",timeStamp:""+(new Date()).getTime()}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);   
		  this.custsOptions.operateTarget=obj;
		  var handler=$.ts.EasyUI.frameDialog({title:"选择客户",href:url},this.custsOptions );    
	},
	changeReceiverType:function(){
		var that=this;
		 $("#bean\\.receiverType").combobox({    
			 onSelect:function(rec){   
			     if(rec.value=="all" || rec.value=="group" || rec.value==""){
					 $("tr[receiverAttr]").hide(); 
				 }  else { 
				    $("tr[receiverAttr]").hide();
					$("tr[receiverAttr='"+rec.value+"']").show();
					if(rec.value=="employee"){
						that.showEmployeeDlg();
					}
				 } 
			 }  
		 });  
	},
	editInitReceiverType:function(){
		var value=$("#bean\\.receiverType").combobox("getValue");
		if(value=="all" || value=="group" || value==""){
			 $("tr[receiverAttr]").hide(); 
		 }  else { 
			$("tr[receiverAttr]").hide();
			$("tr[receiverAttr='"+value+"']").show();
			 
		 } 
	},
	hiddenReceivers:function(){
		$("tr[receiverAttr]").hide(); 
	},
	showEmployeeDlg:function (){
		$('#employeeDlg').dialog('open');
		if($("#actionType").val()!="lookup")
		$.ts.EasyUI.titleAppendDialog("insertEmployeeToolbar", "employeeDlg");
	},
	showCustDlg:function (){
		$('#custDlg').dialog('open');
		if($("#actionType").val()!="lookup")
		$.ts.EasyUI.titleAppendDialog("custToolbar", "custDlg");
	},
	changeTopicKind:function(){
		var that=this;
		 $("#bean\\.topicKind").combobox({    
			 onSelect:function(rec){    
				 that.loadBulletinTopic(); 
			 }  
		 });  
	},
	loadBulletinTopic:function(){
		var topicKind=$("#bean\\.topicKind").combobox('getValue'); 
		var sql="select  id , name as text from  SYS_BulletinTopic   where  topicKind='"+topicKind+"'";
		 
		$.ts.EasyUI.loadComboboxData(sql, function(handler,queryList){   
		     if($("#actionType").val()=="add")
		       $('#bean\\.bulletinTopicId').combobox('setValue', '0');
			 $('#bean\\.bulletinTopicId').combobox("loadData", queryList); 
		});
		
		 
	}, 
	loadDepartmentCombotree:function(){     
		var urlJson={urlType:"combotree", moduleFileName:"DepartmentCombotree" }; 
		var url=$.ts.Utils.toUrlParam(urlJson);   
	    $("#bean\\.deptIds").combotree({url:url,  multiple: true,  cascadeCheck:false, required: false, async:true }) 
	},  
	uploadFile:function(obj){
		var backcallEvent=function(row,rowData){
			var file=$(row).find("[type='file']"); 
			file.click();
			$(row).find("#fieldName").append(file.val());
		};
		$.ts.EasyUI.copyTableRow(obj,{callbackEvent:backcallEvent})
	},
	validateForm:function(){
		 
		if($.trim($("#bean\\.name").textbox('getValue'))=="" ){
			$.messager.alert('提示',$($.find("label[for='bean.name']")[0]).text()+"不能为空！"); 
			return false;
		}
		if($.trim($("#bean\\.topicKind").combobox('getValue'))=="" ){
			$.messager.alert('提示',$($.find("label[for='bean.topicKind']")[0]).text()+"不能为空！"); 
			return false;
		}
		 
		return true;
	},
    submitForm:function (obj){//点击按钮提交  
		 if(this.validateForm()==false) return false; 
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		 $("#copyDialogContent").empty();
		 $.ts.EasyUI.submitRowsToList("custReceiverList.id",$("#custReceiverListTable")); 
		 $.ts.EasyUI.submitRowsToList("employeeReceiverList.id",$("#employeeReceiverListTable")); 
		 $.ts.EasyUI.submitRowsToList("bulletinFileList.id",$("#bulletinFileListTable") ); 
		 $("#custReceiverListTable").clone().appendTo($("#copyDialogContent"));
		 $("#employeeReceiverListTable").clone().appendTo($("#copyDialogContent")); 
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 var that=this; 
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 that.removeDialogs();
			 $.ts.EasyUI.closeDialog(obj);
			 
		 },function(){
			 that.removeDialogs();
			 $("#copyDialogContent").empty(); 
		 });
		return false;
	 } 
 }
 function modalDialogLoadEvent() {  
    if($("#actionType").val()=="lookup") return;
	bulletinScript.loadDepartmentCombotree();
	$.ts.EasyUI.setComboboxText($("#bean\\.topicKind"));
	$.ts.EasyUI.setComboboxText($("#bean\\.receiverType")); 
	bulletinScript.loadBulletinTopic(); 
	if(typeof(formJson)!="undefined")
	  $('#bean\\.bulletinTopicId').combobox("setValue",formJson.bean.bulletinTopicId);
	
}
 