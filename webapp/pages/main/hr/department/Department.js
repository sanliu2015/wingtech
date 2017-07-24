
function DepartmentScript( ){  
	 this.appKey=""; 
	 this.opts=null;
 }
DepartmentScript.prototype={ 
      
    initPage:function(option){
		this.opts= $.extend({},option);   
	    this.changeCompany(); 
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
	loadDepartmentCombotree:function(){  
	      var companyId=$("#bean\\.company\\.id").combobox('getValue'); 
		  var urlJson={urlType:"combotree",
		            moduleFileName:"DepartmentCombotree",
					tsFilterSql:"{a.orgId="+companyId+"}",
		            timeStamp:""+(new Date()).getTime()}; 
			var url=$.ts.Utils.toUrlParam(urlJson);   
		   $("#bean\\.parentId").combotree({ 
			 url:url,
			 multiple: false,
			 required: false,
			 async:true 
		 }) 
	},  
	changeCompany:function(){
		 var that=this;
		 $("#bean\\.company\\.id").combobox({    
			 onSelect:function(rec){    
				 that.loadDepartmentCombotree();
				 $("#bean\\.parentId").combotree("setValue","");
			 }  
		 });  
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
 
 