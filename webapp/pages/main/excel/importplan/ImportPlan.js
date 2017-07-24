var dataOpt = {table: "dtlListGrid"};
function ImportPlanScript() {
	this.appKey = "";
	this.opts = null;
}
ImportPlanScript.prototype = {
	initPage : function(option) {
		this.opts = $.extend({}, option);
	},

	chooseThings : function(obj) {
		var recDataTemplate = [ {
			field : [ "id", "dtlList.thingsId" ],
			sameRecordFlag : "1"
		}, {
			field : [ "thingsName", "thingsName" ]
		}, {
			field : [ "thingsType", "thingsType" ]
		} ];
		var ids = document.getElementsByName("dtlList.thingsId");
		var selectedRows = [];
		for (var i = 0; i < ids.length; i++) {
			selectedRows[i] = {
				"id" : ids[i].value
			};
		}
		var chooseOptions = {
			selectedRows : selectedRows,
			rowPrefix : "dtlList",
			operatePattern : "insertRow",
			operateTarget : obj,
			copyRows : 1,
			container : "dtlListGrid",
			recDataTemplate : recDataTemplate
		};
		var urlJson = {
			urlType : "chooseRows",
			moduleFileName : "ChoseThingsReport",
			openQueryResult : "1",
			timeStamp : "" + (new Date()).getTime()
		};
		var url = $.ts.Utils.toUrlParam(urlJson);

		var handler = $.ts.EasyUI.frameDialog({
			title : "选择物品",
			href : url
		}, chooseOptions);
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
	comboboxFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");    
		var dataList = fieldName + "List";
		return  "<input class='easyui-combobox' type='text' name='dtlList."+fieldName+"' id='dtlList."+fieldName+"' value='" + val + "'  style='width:100%'    data-options='editable:false,valueField:\"value\",textField:\"text\",data:"+importPlanScript[dataList]+"'/>";
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

	submitForm : function(obj) {// 点击按钮提交
		var bodyId = "#" + this.opts.appKey + "Body";
		var formId = this.opts.appKey + "Form";
		var url = $("#" + formId).attr("action") + '?timeStamp='
				+ (new Date()).getTime();

		if ($.trim($("#bean\\.number").val()) == "") {
			$.messager.alert("警告", "唯一编号不能为空!");
			return false;
		}
		$.ts.EasyUI.clearElementsSubscript("dtlList.id", $("#dtlListGrid").parent(), null);
		
		var visibleTr = $("#dtlListGrid").datagrid("getPanel").find('div[class="datagrid-body"]').children("table").find("tr:visible").filter(".datagrid-row");
	 
		var failFlag = false;
		$.each(visibleTr,function(i,row) {
			if ($(row).find('td[field="title"]').find(".easyui-textbox").textbox("getValue") == "") {
				$.messager.alert('提示','行号为' + (i+1) + "标题不能为空!");
				failFlag = true;
				return false;
			}
			if ($(row).find('td[field="colName"]').find(".easyui-textbox").textbox("getValue") == "") {
				$.messager.alert('提示','行号为' + (i+1) + "字段名不能为空!");
				failFlag = true;
				return false;
			}
			if ($(row).find('td[field="colType"]').find(".easyui-combobox").combobox("getValue") == "") {
				$.messager.alert('提示','行号为' + (i+1) + "字段类型不能为空!");
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
	}
}
