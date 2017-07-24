var dataOpt = {table: "dtlListGrid"};
function RepairerScript( ){  
	 this.appKey=""; 
	 this.opts=null;
 }
RepairerScript.prototype={      
    initPage:function(option){
		this.opts= $.extend({},option);   
	},
	textInputFormat : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");

		return "<input   name='dtlList." + fieldName + "' id='dtlList."
				+ fieldName + "' value='" + val
				+ "'  style='width:100%'  class='easyui-textbox'/>";
	},
	comboboxFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");    
		var dataList = fieldName + "List";
		return  "<input class='easyui-combobox' type='text' name='dtlList."+fieldName+"' id='dtlList."+fieldName+"' value='" + val + "'  style='width:100%'    data-options='editable:false,multiple:true,valueField:\"value\",textField:\"text\",data:"+repairerScript[dataList]+"'/>";
	},
	hiddenColumnFormat : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");
		return "<input type='hidden' name='dtlList." + fieldName
				+ "' id='dtlList." + fieldName + "' value='" + val + "'   />";
	},
	removeRowFormat : function(val, row, index) {
		if (val == undefined)
			val = "";
		var text = '<a href="javascript:void(0)" deleteRecordOperate="1" class="easyui-linkbutton" plain="true" iconCls="icon-cancel"  onClick="$.ts.EasyUI.deleteTableRow(this, dataOpt)"></a> ';
		return text;
	},
	
	appendRow : function() {
		var rowOpt = {};
		rowOpt.recordOperateStatus = "add";
		$("#dtlListGrid").datagrid("appendRow", rowOpt);
		var ids = document.getElementsByName("dtlList.id");
		var row = $(ids[ids.length - 1]).parent().parent().parent();
		$.parser.parse(row);
	},
	
	submitForm : function(obj) {// 点击按钮提交
		var bodyId = "#" + this.opts.appKey + "Body";
		var formId = this.opts.appKey + "Form";
		var url = $("#" + formId).attr("action") + '?timeStamp='
				+ (new Date()).getTime();

		$.ts.EasyUI.clearElementsSubscript("dtlList.id", $("#dtlListGrid").parent(), null);
		
		var visibleTr = $("#dtlListGrid").datagrid("getPanel").find('div[class="datagrid-body"]').children("table").find("tr:visible").filter(".datagrid-row");
	 
		var failFlag = false;
		$.each(visibleTr,function(i,row) {
			if ($(row).find('td[field="name"]').find(".easyui-textbox").textbox("getValue") == "") {
				$.messager.alert('提示','行号为' + (i+1) + "名称不能为空!");
				failFlag = true;
				return false;
			}
		});
		 
		if (failFlag) {
			return false;
		} 
		
		var index=$.ts.EasyUI.submitRowsToList("dtlList.id",$("#dtlListGrid").parent(),{effectRowFields:["dtlList.recordOperateStatus"]}); 
		$.ts.EasyUI.ajaxSubmitForm(url, formId, function() {
			$.ts.EasyUI.closeDialog(obj);
		});
		return false;
	},
	
	submitFormOnEdit:function (obj){//点击按钮提交   
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 if ($.trim($("#bean\\.name").textbox("getText")) == "") {
			 $.messager.alert("警告", "姓名不能为空!");
			 return false;
		 }
		 
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 }); 
		return false;
	 } 
 }
 
 