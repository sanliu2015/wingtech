function RoleScript( ){  
	 this.appKey=""; 
	 this.opts=null;
 }
RoleScript.prototype={  
    initPage:function(option){
		this.opts= $.extend({},option);  
		//this.loadModuleCombotree(); 
	},
	loadModuleCombotree:function(){   
	        var urlJson={urlType:"combotree",
		            moduleFileName:"ModuleCombotree",
		            timeStamp:""+(new Date()).getTime()};
		   var url=$.ts.Utils.toUrlParam(urlJson);   
		   $("#bean\\.moduleIds").combotree({
			 url:url,
			 cascadeCheck:true, 
			 multiple: true,
			 required: false,
			 async:true 
		 })
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
 function modalDialogLoadEvent() { 
	 
}

 