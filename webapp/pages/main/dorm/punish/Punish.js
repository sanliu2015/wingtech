
function PunishScript( ){  
	 this.appKey=""; 
	 this.opts=null;
 }
PunishScript.prototype={      
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
		 
		 debugger;
		 if ($.trim($("#bean\\.occurDate").val()) == "") {
			 $.messager.alert("警告", "日期不能为空!");
			 return false;
		 }
		 
		 if ($.trim($("#bean\\.empName").textbox("getText")) == "") {
			 $.messager.alert("警告", "姓名不能为空!");
			 return false;
		 }
		 
		 if ($.trim($("#bean\\.amount").numberbox("getValue")) == "" || $("#bean\\.amount").numberbox("getValue") == 0) {
			 $.messager.alert("警告", "经济处罚不能为空!");
			 return false;
		 }
		 
		 if ($.trim($("#bean\\.reason").combobox("getText")) == "") {
			 $.messager.alert("警告", "惩罚原因不能为空!");
			 return false;
		 }
		 
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 }); 
		return false;
	 } 
 }
 
 