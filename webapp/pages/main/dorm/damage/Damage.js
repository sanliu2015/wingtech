
function DamageScript( ){  
	 this.appKey=""; 
	 this.opts=null;
 }
DamageScript.prototype={      
    initPage:function(option){
		this.opts= $.extend({},option);   
	},
	chooseEmployee:function(obj){    
	      var urlJson={urlType:"chooseRow",  moduleFileName:"ChoseEmployeeReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var that=this;
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择员工",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							  $("#bean\\.empName").textbox('setText',json.name);  
							  $("#bean\\.empNumber").textbox('setText',json.number);   
							  $("#bean\\.employeeId").val(json.id);  
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	
    submitForm:function (obj){//点击按钮提交   
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 
		 if ($.trim($("#bean\\.occurDate").val()) == "") {
			 $.messager.alert("警告", "日期不能为空!");
			 return false;
		 }
		 
		 if ($.trim($("#bean\\.empName").textbox("getText")) == "") {
			 $.messager.alert("警告", "姓名不能为空!");
			 return false;
		 }
		 
		 if ($.trim($("#bean\\.amount").numberbox("getValue")) == "") {
			 $.messager.alert("警告", "金额不能为空!");
			 return false;
		 }
		 
		 if ($.trim($("#bean\\.reason").combobox("getText")) == "") {
			 $.messager.alert("警告", "原因不能为空!");
			 return false;
		 }
		 
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 }); 
		return false;
	 } 
 }
 
 