
function PositionScript( ){  
	 this.appKey=""; 
	 this.opts=null;
 }
PositionScript.prototype={ 
      
    initPage:function(option){
		this.opts= $.extend({},option);    
	},
	loadPositionCombotree:function(){   
		  var urlJson={urlType:"combotree",
		            moduleFileName:"PositionCombotree", 
		            timeStamp:""+(new Date()).getTime()}; 
			var url=$.ts.Utils.toUrlParam(urlJson);   
		   $("#bean\\.parentName").combotree({ 
			 url:url,
			 multiple: false,
			 required: false,
			 async:true,
			 onBeforeLoad: function(node, param) { 
				 $("#bean\\.parentName").combotree('enable'); 
			 }, 
			 onClick: function(node) {  
				$("#bean\\.parentId").val(node.id);  
			 } 
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
 
 