function CompanyScript( ){ 
	 this.contextPath=""; 
	 this.appKey=""; 
	 this.opts=null;
 }
CompanyScript.prototype={  
    initPage:function(option){
		this.opts= $.extend({},option); 
		this.contextPath=this.opts.contextPath;    
	}, 
	testCloseForm:function(obj){
		 
		$.ts.EasyUI.closeDialog(obj,"0");
	},
	chooseEmployee:function(obj){   
		  var url=tsContextPath+"/core/reportResolver/chooseRow.do?moduleFileName=EmployeeSerivceReport&openQueryResult=1"; 
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择员工",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){   
							  $("#bean\\.principal").textbox('setValue',json.name);    
							  $("#bean\\.principalId").val(json.id);  
							   
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
 
 