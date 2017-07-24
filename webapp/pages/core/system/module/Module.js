function ModuleScript( ){  
	 this.appKey=""; 
	 this.opts=null;
 }
ModuleScript.prototype={ 
      
    initPage:function(option){
		this.opts= $.extend({},option);  
		this.loadModuleCombotree(); 
	},
	loadModuleCombotree:function(){   
	        var urlJson={urlType:"combotree",
		            moduleFileName:"ModuleCombotree",
		            timeStamp:""+(new Date()).getTime()};
		   var url=$.ts.Utils.toUrlParam(urlJson);   
		   $("#bean\\.parentId").combotree({
			 url:url,
			 multiple: false,
			 required: false,
			 async:true 
		 })
	}, 
	 removeRowFormat:function(val, row, index) {
		if(val == undefined)	val = ""; 
		var  text='<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-cancel"  onClick="$.ts.EasyUI.deleteTableRow(this)"></a> '; 
		return  text;
	 },
	  textInputFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");    
		 return  "<input  name='childNodes."+fieldName+"' id='childNodes."+fieldName+"' value='" + val + "'  style='width:100%'  class='easyui-textbox'/>";  
	 },
	 numberspinnerFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");    
		return  "<input type='text' name='childNodes."+fieldName+"' id='childNodes."+fieldName+"' value='" + val + "'  style='width:100%'   class='easyui-numberbox' data-options=\"precision:0\"/>";
	 },
	 comboboxYesNoFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");    
		return  "<input type='text' name='childNodes."+fieldName+"' id='childNodes."+fieldName+"' value='" + val + "'  style='width:100%'   class='easyui-combobox' data-options='editable:false,valueField:\"code\",textField:\"name\",data:"+moduleScript.yesOrNoList+"'/>";
	 },
	 comboboxStatusFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");    
		return  "<input type='text' name='childNodes."+fieldName+"' id='childNodes."+fieldName+"' value='" + val + "'  style='width:100%'   class='easyui-combobox' data-options='editable:false,valueField:\"code\",textField:\"name\",data:"+moduleScript.statusList+"'/>";
	 },
	 comboboxSecurityLevelFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");    
		return  "<input type='text' name='childNodes."+fieldName+"' id='childNodes."+fieldName+"' value='" + val + "'  style='width:100%'   class='easyui-combobox' data-options='editable:false,valueField:\"code\",textField:\"name\",data:"+moduleScript.securityLevelList+"'/>";
	 },
	  hiddenColumnFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");   
		return  "<input type='hidden' name='childNodes."+fieldName+"' id='childNodes."+fieldName+"' value='" + val + "'   />";
	 },
	 insertDatagridRow:function(rowBean){
	    var rowOpt={};
		if($.ts.Utils.isEmpty(rowBean)==false){
			rowOpt=rowBean;
		} 
		var table=$('#omOrderDtlListGrid'); 
	   table.datagrid('appendRow',{}); 
	   var ids=document.getElementsByName("childNodes.id");  
	   var  row=$(ids[ids.length-1]).parent().parent().parent() ;
	   $.parser.parse(row); 
	   return row;
	   
  },
    submitChildNodesForm:function (obj){//点击按钮提交    
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId="#"+this.opts.appKey+"Form";
		 var url=$(formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 //$(bodyId).mask();  
		 var index=$.ts.EasyUI.submitRowsToList("childNodes.id",$("#omOrderDtlListGrid").parent(),{effectRowFields:["name"]}); 
		 if(index==0){
			 $.messager.alert('提示',"子节点记录不能为空！"); 
			 return false;
		 }
		 
	  	 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 } );
		return false;
	 } ,
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
	$('#bean\\.securityLevel').combobox('disableTextbox',{stoptype:'readOnly',activeTextArrow:true,stopArrowFocus:true});
}
 