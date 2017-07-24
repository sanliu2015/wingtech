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
			 cascadeCheck:false, 
			 multiple: true,
			 required: false,
			 async:true 
		 })
	},  
	 setIndeterminateIds:function (){ 
	    var tree=  $("#bean\\.moduleIds").combotree('tree')
		var nodes = tree.tree('getChecked', ['indeterminate']);
		var s="";
		for(var i=0;i<nodes.length;i++){
			if(s==""){
				s=nodes[i].id ;
			} else {
				s=s+","+ nodes[i].id ;
			}
		} 
		$("#bean\\.indeterminateIds").val(s);    
	 },
    submitForm:function (obj){//点击按钮提交   
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 this.setIndeterminateIds();
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 }); 
		return false;
	 } 
 }
 function modalDialogLoadEvent() { 
	$('#bean\\.roleKind').combobox('disableTextbox',{stoptype:'readOnly',activeTextArrow:true,stopArrowFocus:true});
	roleScript.loadModuleCombotree();
}

 