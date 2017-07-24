
function PositionRoleScript( ){  
	 this.appKey=""; 
	 this.opts=null;
 }
PositionRoleScript.prototype={ 
      
    initPage:function(option){
		this.opts= $.extend({},option);    
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
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 }); 
		return false;
	 } 
 }
 
 