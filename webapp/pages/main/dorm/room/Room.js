var dataOpt = {table: "dtlListGrid"};
function RoomScript() {
	this.appKey = "";
	this.opts = null;
}
RoomScript.prototype = {
	initPage : function(option) {
		this.opts = $.extend({}, option);
	},

	chooseThings : function(obj) {
		var recDataTemplate = [ {
			field : [ "id", "dtlList.thingsId" ],
			sameRecordFlag : "1"
		}, {
			field : [ "name", "thingsName" ]
		}, {
			field : [ "unit", "thingsUnit" ]
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
			moduleFileName : "ThingsServiceReport",
			openQueryResult : "1",
			timeStamp : "" + (new Date()).getTime()
		};
		var url = $.ts.Utils.toUrlParam(urlJson);

		var handler = $.ts.EasyUI.frameDialog({
			title : "选择物品",
			href : url
		}, chooseOptions);
	},

	choosePrincipal:function(obj){    
	      var urlJson={urlType:"chooseRow",  moduleFileName:"ChoseEmployeeReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var that=this;
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择员工",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							 debugger;
							 $(obj).parent().find("#dtlList\\.principalName").textbox('setText',json.name);  
							 $(obj).parent().find("#dtlList\\.principalId").val(json.id);
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
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
				+ "<a href='javascript:void(0)' class='easyui-linkbutton'  iconCls='icon-search' onClick='return roomScript.choosePrincipal(this);'  plain='true'></a>";
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

	submitForm : function(obj) {// 点击按钮提交
		var bodyId = "#" + this.opts.appKey + "Body";
		var formId = this.opts.appKey + "Form";
		var url = $("#" + formId).attr("action") + '?timeStamp='
				+ (new Date()).getTime();

		if ($.trim($("#bean\\.name").val()) == "") {
			$.messager.alert("警告", "房间名称不能为空!");
			return false;
		}
		if ($.trim($("#bean\\.number").val()) == "") {
			$.messager.alert("警告", "房间编号不能为空!");
			return false;
		}
		if ($("#bean\\.floorId").combotree("getValue") == "") {
			$.messager.alert("警告", "楼层位置不能为空!");
			return false;
		}
		$.ts.EasyUI.clearElementsSubscript("dtlList.id", $("#dtlListGrid").parent(), null);
		var index=$.ts.EasyUI.submitRowsToList("dtlList.id",$("#dtlListGrid").parent(),{effectRowFields:["dtlList.thingsQty"]}); 
		$.ts.EasyUI.ajaxSubmitForm(url, formId, function() {
			$.ts.EasyUI.closeDialog(obj);
		});
		return false;
	},
	
	submitFormOnOpCs : function(obj) {// 点击按钮提交
		var bodyId = "#" + this.opts.appKey + "Body";
		var formId = this.opts.appKey + "Form";
		var url = $("#" + formId).attr("action") + '?timeStamp=' + new Date().getTime();
		$.ts.EasyUI.ajaxSubmitForm(url, formId, function() {
			$.ts.EasyUI.closeDialog(obj);
		});
		return false;
	}
}
