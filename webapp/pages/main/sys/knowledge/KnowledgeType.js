
function KnowledgeTypeScript( ){  
	 this.appKey=""; 
	 this.opts=null;
 }
KnowledgeTypeScript.prototype={ 
    initPage:function(option){
		this.opts= $.extend({},option);   
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
 
 