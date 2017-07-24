var dataOpt = {table: "dtlListGrid"};
var dataOpt1 = {table: "damageListGrid"};
function RepairApplyScript() {
	this.appKey = "";
	this.opts = null;
}
RepairApplyScript.prototype = {
	initPage : function(option) {
		this.opts = $.extend({}, option);
	},
	
	chooseEmployee:function(obj){    
	      var urlJson={urlType:"chooseRow",  moduleFileName:"ChoseEmployeeReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var that=this;
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择员工",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							  $("#bean\\.employeeName").textbox('setText',json.name);  
							  $("#bean\\.employeeId").val(json.id);   
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	
	chooseRepairer:function(obj){    
		  var buildingId = $("#bean\\.buildingId").combobox("getValue");
		  var repairTypeName = $("#bean\\.repairType").combobox("getText");
	      var urlJson={urlType:"chooseRow", moduleFileName:"repairerServiceReport",openQueryResult:"1", repairTypeName:repairTypeName, buildingId:buildingId}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var that=this;
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择维修人员",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							  $("#bean\\.repairerName").textbox('setText',json.name);  
							  $("#bean\\.repairerId").val(json.id);   
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	
	chooseCheckInEmp : function(obj) {
		var repairFee = $("#bean\\.repairFee").numberbox("getValue");
		var recDataTemplate = [ {
			field : [ "id", "damageList.employeeId" ],
			sameRecordFlag : "1"
		}, {
			field : [ "empName", "empName" ]
		}, {
			field : [ "number", "empNumber" ]
		} ];
		var ids = document.getElementsByName("damageList.employeeId");
		var selectedRows = [];
		for (var i = 0; i < ids.length; i++) {
			selectedRows[i] = {
				"id" : ids[i].value
			};
		}
		var chooseOptions = {
			selectedRows : selectedRows,
			rowPrefix : "damageList",
			operatePattern : "insertRow",
			operateTarget : obj,
			copyRows : 1,
			container : "damageListGrid",
			recDataTemplate : recDataTemplate
		};
		var urlJson = {
			urlType : "chooseRows",
			moduleFileName : "CheckInServiceReport",
			openQueryResult : "1",
			timeStamp : "" + (new Date()).getTime()
		};
		var url = $.ts.Utils.toUrlParam(urlJson);

		var handler = $.ts.EasyUI.frameDialog({
			title : "选择入住人员",
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

	principalNameFormat : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");

		return "<input name='dtlList.principalName' id='dtlList.principalName'  value='" + val
				+ "' style='width:80%' readonly  class='easyui-textbox'/>"
				+ "<input type='hidden' id='dtlList.principalId' name='dtlList.principalId' value='" + val + "' />"
				+ "<a href='javascript:void(0)' class='easyui-linkbutton'  iconCls='icon-search' onClick='return repairApplyScript.choosePrincipal(this);'  plain='true'></a>";
	},
	
	textInputReadonlyFormat : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");

		return "<input   name='dtlList." + fieldName + "' id='dtlList."
				+ fieldName + "' value='" + val
				+ "'  style='width:100%' readonly class='easyui-textbox'/>";
	},
	numberspinnerInputFormat : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");
		return "<input type='text' name='dtlList."
				+ fieldName
				+ "' id='dtlList."
				+ fieldName
				+ "' value='"
				+ val
				+ "'  style='width:100%'   class='easyui-numberspinner' data-options=\"min:1\"/>";
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

		if ($.trim($("#bean\\.employeeId").val()) == "") {
			$.messager.alert("警告", "报修人员不能为空!");
			return false;
		}
		if ($("#bean\\.buildingId").combobox("getValue") == "") {
			$.messager.alert("警告", "楼栋位置不能为空!");
			return false;
		}
		if ($("#bean\\.repairType").combobox("getValue") == "") {
			$.messager.alert("警告", "报修类别不能为空!");
			return false;
		}
		if ($.trim($("#bean\\.repairerId").val()) == "") {
			$.messager.alert("警告", "维修人员不能为空!");
			return false;
		}
		if ($.trim($("#bean\\.repairDate").val()) == "") {
			$.messager.alert("警告", "报修日期不能为空!");
			return false;
		}
		if ($.trim($("#bean\\.repairContent").val()) == "") {
			$.messager.alert("警告", "报修内容不能为空!");
			return false;
		}
		$.ts.EasyUI.clearElementsSubscript("dtlList.id", $("#dtlListGrid").parent(), null);
		var index=$.ts.EasyUI.submitRowsToList("dtlList.id",$("#dtlListGrid").parent(),{effectRowFields:["dtlList.recordOperateStatus"]}); 
		$.ts.EasyUI.ajaxSubmitForm(url, formId, function() {
			$.ts.EasyUI.closeDialog(obj);
		});
		return false;
	},
	
	submitFormEndWork : function(obj) {// 点击按钮提交
		var bodyId = "#" + this.opts.appKey + "Body";
		var formId = this.opts.appKey + "Form";
		var url = $("#" + formId).attr("action") + '?timeStamp='
				+ (new Date()).getTime();
		if ($.trim($("#bean\\.endWorkDate").val()) == "") {
			$.messager.alert("警告", "完工不能为空!");
			return false;
		}
		if ($.trim($("#bean\\.autoScoreDate").val()) == "") {
			$.messager.alert("警告", "评分截止日期不能为空!");
			return false;
		}
		if ($("input[type='radio']:checked").val() == "1") {
			if ($.trim($("#bean\\.empPhone").val()) == "") {
				$.messager.alert("警告", "申请人手机号不能为空!");
				return false;
			}
		}
		$.ts.EasyUI.clearElementsSubscript("damageList.id", $("#damageListGrid").parent(), null);
		var index=$.ts.EasyUI.submitRowsToList("damageList.id",$("#damageListGrid").parent(),{effectRowFields:["damageList.recordOperateStatus"]}); 
		$.ts.EasyUI.ajaxSubmitForm(url, formId, function() {
			$.ts.EasyUI.closeDialog(obj);
		});
		return false;
	},
	submitFormScore : function(obj) {// 点击按钮提交
		if ($('#scoreResult').raty('score') == undefined) {
			$.messager.alert("警告", "满意度还没评分!");
			return false;
		} else {
			$("#bean\\.scoreResult").val($('#scoreResult').raty('score'));
		}
		var bodyId = "#" + this.opts.appKey + "Body";
		var formId = this.opts.appKey + "Form";
		var url = $("#" + formId).attr("action") + '?timeStamp='
				+ (new Date()).getTime();

		$.ts.EasyUI.ajaxSubmitForm(url, formId, function() {
			$.ts.EasyUI.closeDialog(obj);
		});
		return false;
	},
	
	
	textInputFormatDamage : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");

		return "<input   name='damageList." + fieldName + "' id='damageList."
				+ fieldName + "' value='" + val
				+ "'  style='width:100%'  class='easyui-textbox'/>";
	},
	numberboxInputFormatDamage : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");
		return "<input name='damageList."
				+ fieldName
				+ "' id='damageList."
				+ fieldName
				+ "' value='"
				+ val
				+ "' style='width:100%' data-options=\"min:0,precision:2\"/>";
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
