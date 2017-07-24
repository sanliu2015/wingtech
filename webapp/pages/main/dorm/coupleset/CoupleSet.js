var dataOpt = {table: "dtlListGrid"};
function CoupleSetScript() {
	this.appKey = "";
	this.opts = null;
}
CoupleSetScript.prototype = {
	initPage : function(option) {
		this.opts = $.extend({}, option);
	},
	
	chooseOrgEmployee:function(index){ 
		var $currentTr = $(index).parent().parent().parent();
	      var urlJson={urlType:"chooseRow",  moduleFileName:"ChoseEmployeeReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var that=this;
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择入住员工",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							 $currentTr.find("#dtlList\\.orgEmpName").textbox('setValue',json.name);  
							 $currentTr.find("#dtlList\\.orgEmpNumber").textbox('setValue',json.number);  
							 $currentTr.find("#dtlList\\.orgEmpSex").textbox('setValue',json.gender);  
							 $currentTr.find("#dtlList\\.orgEmpId").val(json.id);   
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	
	chooseDesEmployee:function(index){ 
		var $currentTr = $(index).parent().parent().parent();
	      var urlJson={urlType:"chooseRow",  moduleFileName:"ChoseEmployeeReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var that=this;
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择入住员工",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							 $currentTr.find("#dtlList\\.desEmpName").textbox('setValue',json.name);  
							 $currentTr.find("#dtlList\\.desEmpNumber").textbox('setValue',json.number);  
							 $currentTr.find("#dtlList\\.desEmpSex").textbox('setValue',json.gender);  
							 $currentTr.find("#dtlList\\.desEmpId").val(json.id);   
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	
	orgEmpNameFormat : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");

		return "<input   name='dtlList." + fieldName + "' id='dtlList."
				+ fieldName + "' value='" + val
				+ "'  style='width:80%' readonly  class='easyui-textbox'/>"
				+ "<a href='javascript:void(0)' class='easyui-linkbutton'  iconCls='icon-search' onClick='return coupleSetScript.chooseOrgEmployee(this);'  plain='true'></a>";
	},
	
	desEmpNameFormat : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");

		return "<input   name='dtlList." + fieldName + "' id='dtlList."
				+ fieldName + "' value='" + val
				+ "'  style='width:80%' readonly  class='easyui-textbox'/>"
				+ "<a href='javascript:void(0)' class='easyui-linkbutton'  iconCls='icon-search' onClick='return coupleSetScript.chooseDesEmployee(this);'  plain='true'></a>";
	},
	
	textInputFormat : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");

		return "<input   name='dtlList." + fieldName + "' id='dtlList."
				+ fieldName + "' value='" + val
				+ "'  style='width:100%'  class='easyui-textbox'/>";
	},

	textInputReadonlyFormat : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");

		return "<input   name='dtlList." + fieldName + "' id='dtlList."
				+ fieldName + "' value='" + val
				+ "'  style='width:100%' readonly class='easyui-textbox'/>";
	},
	hiddenColumnFormat : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");
		return "<input type='hidden' name='dtlList." + fieldName
				+ "' id='dtlList." + fieldName + "' value='" + val + "'   />";
	},
	appendRow : function() {
		var rowOpt = {};
		rowOpt.recordOperateStatus = "add";
		$("#dtlListGrid").datagrid("appendRow", rowOpt);
		var ids = document.getElementsByName("dtlList.id");
		var row = $(ids[ids.length - 1]).parent().parent().parent();
		$.parser.parse(row);
	},
	removeRowFormat : function(val, row, index) {
		if (val == undefined)
			val = "";
		var text = '<a href="javascript:void(0)" deleteRecordOperate="1" class="easyui-linkbutton" plain="true" iconCls="icon-cancel"  onClick="$.ts.EasyUI.deleteTableRow(this, dataOpt)"></a> ';
		return text;
	},
	insertDatagridRow : function(rowBean) {
		var rowOpt = {};
		if ($.ts.Utils.isEmpty(rowBean) == false) {
			rowOpt = rowBean;
		}
		var table = $('#dtlListGrid');
		table.datagrid('appendRow', rowOpt['dtlList']);
		var ids = document.getElementsByName("dtlList.id");
		var row = $(ids[ids.length - 1]).parent().parent().parent();
		$.parser.parse(row);
		if ($.ts.Utils.isEmpty(rowBean) == false) {
			row.tsLoadData(rowBean);
		}
		// return row;
	},
	
	insertDamageListRow : function(rowBean) {
		var rowOpt = {};
		if ($.ts.Utils.isEmpty(rowBean) == false) {
			rowOpt = rowBean;
		}
		var table = $('#damageListGrid');
		table.datagrid('appendRow', rowOpt['damageList']);
		var ids = document.getElementsByName("damageList.id");
		var row = $(ids[ids.length - 1]).parent().parent().parent();
		$.parser.parse(row);
		if ($.ts.Utils.isEmpty(rowBean) == false) {
			row.tsLoadData(rowBean);
		}
		// return row;
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
			if ($(row).find("#dtlList\\.orgEmpName").textbox("getValue") == "") {
				$.messager.alert('提示','行号为' + (i+1) + "姓名不能为空!");
				failFlag = true;
				return false;
			}
			if ($(row).find("#dtlList\\.desEmpName").textbox("getValue") == "") {
				$.messager.alert('提示','行号为' + (i+1) + "实际承担费用人员姓名不能为空!");
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
	},
	
	submitFormEndWork : function(obj) {// 点击按钮提交
		var bodyId = "#" + this.opts.appKey + "Body";
		var formId = this.opts.appKey + "Form";
		var url = $("#" + formId).attr("action") + '?timeStamp='
				+ (new Date()).getTime();

		$.ts.EasyUI.clearElementsSubscript("damageList.id", $("#damageListGrid").parent(), null);
		var index=$.ts.EasyUI.submitRowsToList("damageList.id",$("#damageListGrid").parent(),{effectRowFields:["damageList.recordOperateStatus"]}); 
		$.ts.EasyUI.ajaxSubmitForm(url, formId, function() {
			$.ts.EasyUI.closeDialog(obj);
		});
		return false;
	},
	comboboxFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");    
		var dataList = fieldName + "List";
		return  "<input class='easyui-combobox' type='text' name='dtlList."+fieldName+"' id='dtlList."+fieldName+"' value='" + val + "'  style='width:100%'    data-options='editable:false,valueField:\"value\",textField:\"text\",data:"+passBillScript[dataList]+"'/>";
	 },
	hiddenColumnFormatDamage : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");
		return "<input type='hidden' name='damageList." + fieldName
				+ "' id='damageList." + fieldName + "' value='" + val + "'   />";
	},
	removeRowFormatDamage : function(val, row, index) {
		if (val == undefined)
			val = "";
		var text = '<a href="javascript:void(0)" deleteRecordOperate="1" class="easyui-linkbutton" plain="true" iconCls="icon-cancel"  onClick="$.ts.EasyUI.deleteTableRow(this, dataOpt1)"></a> ';
		return text;
	}
	
}
