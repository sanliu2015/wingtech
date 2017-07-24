function myFormamter(value){
	return value<10?('0'+value):value;
}


function RoomSetScript( ){  
	 this.appKey=""; 
	 this.opts=null;
 }
RoomSetScript.prototype={      
    initPage:function(option){
		this.opts= $.extend({},option);   
	},
	
    submitForm:function (obj){//点击按钮提交   
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 
		 if ($.trim($("#bean\\.endDay").numberbox("getValue")) == "") {
			 $.messager.alert("警告", "每月截止日不能为空!");
			 return false;
		 }
		 
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 }); 
		return false;
	 } 
 }
 
 