function closeDialogCallbackEvent(){
	knowledgeScript.removeDialogs(); 
}
function KnowledgeScript( ){  
	this.appKey=""; 
	 this.opts=null;
	 this.chooseEmployeesOptions=null;
 }
KnowledgeScript.prototype={ 
      
    initPage:function(option){
		this.opts= $.extend({},option); 
		$("#know\\.companyIds").combotree();
		this.hiddenReceivers();
		this.changeReceiverType();
		this.setChooseEmployeeOptions();
		this.setCustOptions();
	},
	removeDialogs:function(){  
	     
		$("#employeeDlg").dialog({onClose:function() { $(this).remove();}});
		$("#employeeDlg").dialog('close');
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
	changeReceiverType:function(){
		var that=this;
		 $("#know\\.receiverType").combobox({    
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
		var value=$("#know\\.receiverType").combobox("getValue");
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
	loadDepartmentCombotree:function(){     
		var urlJson={urlType:"combotree", moduleFileName:"DepartmentCombotree" }; 
		var url=$.ts.Utils.toUrlParam(urlJson);   
	    $("#know\\.deptIds").combotree({url:url,  multiple: true,  cascadeCheck:false, required: false, async:true }) 
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
		 
		if($.trim($("#know\\.name").textbox('getValue'))=="" ){
			$.messager.alert('提示',$($.find("label[for='know.name']")[0]).text()+"不能为空！"); 
			return false;
		}
		 
		return true;
	},
    submitForm:function (obj){//点击按钮提交   
    	if(this.validateForm()==false) return false; 
    	var bodyId="#"+this.opts.appKey+"Body";
    	var formId= this.opts.appKey+"Form";
    	$("#copyDialogContent").empty();
    	$.ts.EasyUI.submitRowsToList("employeeReceiverList.id",$("#employeeReceiverListTable"));
    	$.ts.EasyUI.submitRowsToList("knowledgeFileList.id",$("#knowledgeFileListTable") ); 
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
    knowledgeScript.loadDepartmentCombotree();
	$.ts.EasyUI.setComboboxText($("#know\\.receiverType")); 
	
}