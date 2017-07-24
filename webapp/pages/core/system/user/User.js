
function UserScript( ){ 
	 this.appKey=""; 
	 this.opts=null;
 } 
UserScript.prototype={  
   initPage:function(option){
		this.opts= $.extend({},option);  
		this.selectedRows=$.ts.Utils.beanPropertyToList({id:$("#bean\\.employeeIds").val(),name:$("#bean\\.employeeNames").val()}); 		this.bindLoginKindEvent();
		if($("input[name='bean.loginKind']")[0].checked){
			$($("input[name='bean.loginKind']")[0]).click();
		} else {
			$($("input[name='bean.loginKind']")[1]).click();
		}
		
	},  
	bindLoginKindEvent:function(){
		$("input[name='bean.loginKind']").click(function(){
			if(this.value=="1"){
				$("#loginKindCustId").show();
				$("#loginKindEmployeeId").hide();
				$("#bean.employeeId").val("0");
			} else {
				$("#loginKindCustId").hide();
				$("#loginKindEmployeeId").show();
				$("#bean.custId").val("0");
			}
		});
	},
	resetPasword:function(obj){
		if(obj.checked){
			$("#newPasswordContainer").show();
		} else {
			$("#newPasswordContainer").hide();
		}
	},
	chooseEmployee:function(obj){   
	      var urlJson={urlType:"chooseRow",  moduleFileName:"EmployeeSerivceReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择员工", href : url   
			  } , function(json){  
					  try{  
						 if(json!=null){  
							  $("#bean\\.employeeName").textbox('setValue',json.name);  
							  $("#bean\\.employeeId").val(json.id);  
							  $("#bean\\.departmentName").textbox('setValue',json.departmentname);  
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	}, 
	chooseCustomer:function(obj){   
	      var urlJson={urlType:"chooseRow",  moduleFileName:"CustomerSerivceReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择客户", href : url   
			  } , function(json){  
					  try{  
						 if(json!=null){  
							  $("#bean\\.custName").textbox('setValue',json.custName);  
							  $("#bean\\.custId").val(json.id);   
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	}, 
	chooseEmployees:function(obj){   
	      var urlJson={urlType:"chooseRows",  moduleFileName:"EmployeeSerivceReport",openQueryResult:"1" }; 
		  var url=$.ts.Utils.toUrlParam(urlJson);    
		  var tsOptions={
			  selectedRows:this.selectedRows, 
			  recDataTemplate:{id:"",name:""}, 
			  operatePattern:"mergeValues",
			  setValues:function(template){ 
				  $("#bean\\.employeeIds").val(template.id); 
				  $("#bean\\.employeeNames").textbox('setValue',template.name);  
			  } 
		  };
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择员工", href : url   
			  } , tsOptions );    
	},
	changeCompany:function(){
		 var that=this;
		 if($("#bean\\.companyIds").length==0) return;
		 $("#bean\\.companyIds").combotree({    
			 onCheck:function(rec){     
				// that.loadDepartmentCombotree();
				 //$("#bean\\.deptIds").val("");
				 //$("#bean\\.deptIds").combotree("setValue","");
			 }  
		 });  
	},
	loadDepartmentCombotree:function(){    
	      if($('#bean\\.companyIds').length==0) return;
		  var companyTree= $('#bean\\.companyIds').combotree('tree');
		  var companyId=$("#bean\\.companyIds").combo('getValues'); 
		  if(typeof(companyId)=="undefined" || companyId==""){
			  companyId="0";
		  } 
		  var urlJson={urlType:"combotree",
		            moduleFileName:"DepartmentCombotree",
					//tsFilterSql:"{a.orgId in ("+companyId+")}",
		            timeStamp:""+(new Date()).getTime()}; 
			var url=$.ts.Utils.toUrlParam(urlJson);   
		   $("#bean\\.deptIds").combotree({ 
			 url:url,
			 multiple: true,
			 //cascadeCheck:false,
			 required: false,
			 async:true 
		 }) 
	},  
	validateForm:function(){
		if($("#bean\\.password").textbox('getValue')!=$("#bean\\.confirmPassword").textbox('getValue')){
			$.messager.alert('提示',"2次输入的密码不一致！"); 
			return false;
		}
		if($.trim($("#bean\\.name").textbox('getValue'))=="" ){
			$.messager.alert('提示',"用户名不能为空！"); 
			return false;
		}
		 if($("input[name='bean.loginKind']")[0].checked){
			if($.trim($("#bean\\.employeeName").textbox('getValue'))=="" ){
				$.messager.alert('提示',"用户名不能为空！"); 
				return false;
			}
		 } else {
			 if($.trim($("#bean\\.custName").textbox('getValue'))=="" ){
				$.messager.alert('提示',"客户名称不能为空！"); 
				return false;
			}
		 }
		if($.trim($("#bean\\.userLevel").textbox('getValue'))=="-1" ){
			if($.trim($("#bean\\.positionRoleIds").combotree('getValues'))=="" ){
				$.messager.alert('提示',"用户岗位角色不能为空！"); 
				return false;
			} 
		}
		return true;
	},
    submitForm:function (obj){//点击按钮提交  
		 if(this.validateForm()==false) return false; 
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 }); 
		return false;
	 },
	  submitModifyPasswordForm:function (obj){//点击按钮提交  
	     var validatePassword=true;
	     if($("#status").length>0){
			 if($("#status").checked==false){
				 validatePassword=false;
			 }
		 }  
		 if(validatePassword==true){
		   if($("#bean\\.password").textbox('getValue')!=$("#bean\\.confirmPassword").textbox('getValue')){
			$.messager.alert('提示',"2次输入的密码不一致！"); 
			return false;
		  } 
		 }
		  
		 if($("input[name='bean.loginKind']")[0].checked){
			if($.trim($("#bean\\.name").textbox('getValue'))=="" ){
				$.messager.alert('提示',"用户名不能为空！"); 
				return false;
			}
		 } else {
			 try{
				 if($.trim($("#bean\\.custName").textbox('getValue'))=="" ){
					$.messager.alert('提示',"客户名称不能为空！"); 
					return false;
				}
			 } catch(e){}
		 }
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(result){ 
			  if($.ts.Utils.isEmpty(result.error)==false || $.ts.Utils.isEmpty(result.alertMsg)==false){ 
			      
			  } else {
				  $.ts.EasyUI.closeDialog(obj);
			  }
			 //$.ts.EasyUI.closeDialog(obj);
		 }); 
		return false;
	 } 
 }
 function modalDialogLoadEvent() { 
	  
	//$('#bean\\.positionRoleIds').combobox('disableTextbox',{stoptype:'readOnly',activeTextArrow:true,stopArrowFocus:true});
	//$('#bean\\.roleIds').combobox('disableTextbox',{stoptype:'readOnly',activeTextArrow:true,stopArrowFocus:true});
	//userScript.loadDepartmentCombotree(); 
	userScript.changeCompany();
	userScript.loadDepartmentCombotree();
}
 