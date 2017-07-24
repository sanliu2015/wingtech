function OutsidersScript( ){ 
 } 
OutsidersScript.prototype={
		
   initPage:function(option){
		this.opts= $.extend({},option);
	},
	validateForm:function(){
		 if($.trim($("#bean\\.outsidersName").val())=="" ){
			$.messager.alert('提示',$($.find("label[for='bean.outsidersName']")[0]).text()+"不能为空！"); 
			return false;
		} 
		var formId= this.opts.appKey+"Form";
		return true;
	},
	submitForm:function (obj){//点击按钮提交 
	    if(this.validateForm()==false) return false;   
	    var bodyId="#"+this.opts.appKey+"Body";
		var formId= this.opts.appKey+"Form";
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 var that=this; 
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 });
		return false;
	 } 
 }