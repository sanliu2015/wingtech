 
function SubscribeScript( ){ 
	 this.appKey=""; 
	 this.opts=null;
	 this.chooseEmployeesOptions=null;
 } 
SubscribeScript.prototype={  
   initPage:function(option){
		this.opts= $.extend({},option);    
		this.changeTopicKind();  
		this.setChooseEmployeeOptions(); 
		 
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
	      var urlJson={urlType:"chooseRows",  moduleFileName:"EmployeeSerivceReport",openQueryResult:"1"}; 
		   urlJson.tsFilterSql="{ and not a.id  in (select receiverId from SYS_SubscribeReceiver where bulletinTopicId="+$('#bean\\.bulletinTopicId').combobox('getValue' )+")}"; 
		  var url=$.ts.Utils.toUrlParam(urlJson);   
		  this.chooseEmployeesOptions.operateTarget=obj;
		  var handler=$.ts.EasyUI.frameDialog({title:"选择员工", href:url},this.chooseEmployeesOptions);    
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
	validateForm:function(){
		 if($.trim($("#bean\\.topicKind").combobox('getValue'))=="" ){
			$.messager.alert('提示',$($.find("label[for='bean.topicKind']")[0]).text()+"不能为空！"); 
			return false;
		}
		 if($.trim($("#bean\\.bulletinTopicId").combobox('getValue'))=="" ){
			$.messager.alert('提示',$($.find("label[for='bean.bulletinTopicId']")[0]).text()+"不能为空！"); 
			return false;
		}
		return true;
	},
    submitForm:function (obj){//点击按钮提交  
		 if(this.validateForm()==false) return false; 
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form"; 
		 var index=$.ts.EasyUI.submitRowsToList("employeeReceiverList.id",$("#employeeReceiverListTable")); 
		 if(index==0){
			 $.messager.alert('提示',"人员记录能为空！"); 
			 return false;
		 }
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 var that=this; 
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){ 
			 $.ts.EasyUI.closeDialog(obj); 
		 } );
		return false;
	 } 
 }
 function modalDialogLoadEvent() {  
    if($("#actionType").val()=="lookup") return;
	subscribeScript.loadDepartmentCombotree();
	$.ts.EasyUI.setComboboxText($("#bean\\.topicKind")); 
	subscribeScript.loadBulletinTopic(); 
	if(typeof(formJson)!="undefined")
	  $('#bean\\.bulletinTopicId').combobox("setValue",formJson.bean.bulletinTopicId);
	
}
 