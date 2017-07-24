var dataOpt = {table: "dtlListGrid"};
function MeetingReserveScript() {
	this.appKey = "";
	this.opts = null;
}
MeetingReserveScript.prototype = {
	initPage : function(option) {
		this.opts = $.extend({}, option);
	},
	submitForm : function(obj) {// 点击按钮提交
		var bodyId = "#" + this.opts.appKey + "Body";
		var formId = this.opts.appKey + "Form";
		var url = $("#" + formId).attr("action") + '?timeStamp=' + new Date().getTime();
		if ($.trim($("#bean\\.theme").textbox("getValue")) == "") {
			$.messager.alert("警告", "会议主题不能为空!");
			return false;
		}
		if ($.trim($("#bean\\.meetingRoomId").combobox("getValue")) == "") {
			$.messager.alert("警告", "请选择会议室!");
			return false;
		}
		
		if ($.trim($("#bean\\.conveneDate").val()) == "") {
			$.messager.alert("警告", "会议日期不能为空!");
			return false;
		}
		if ($.trim($("#bean\\.beginTime").val()) == "") {
			$.messager.alert("警告", "开始时间不能为空!");
			return false;
		}
		if ($.trim($("#bean\\.endedTime").val()) == "") {
			$.messager.alert("警告", "结束时间不能为空!");
			return false;
		}
		if ($("#bean\\.endedTime").val()<=$("#bean\\.beginTime").val()) {
			$.messager.alert("警告", "结束时间必须大于开始时间!");
			return false;
		}
		var index=$.ts.EasyUI.submitRowsToList("dtlList.id",$("#dtlListGrid").parent(),{effectRowFields:["dtlList.recordOperateStatus","dtlList.name"]}); 
		$.ts.EasyUI.ajaxSubmitForm(url, formId, function() {
			$.ts.EasyUI.closeDialog(obj);
		});
		return false;
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
							  $("#bean\\.reserveEmpName").textbox('setText',json.name);  
							  $("#bean\\.reserveEmpId").val(json.id);   
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
		return  "<input class='easyui-combobox' type='text' name='dtlList."+fieldName+"' id='dtlList."+fieldName+"' value='" + val + "'  style='width:100%'    data-options='editable:false,valueField:\"value\",textField:\"text\",data:"+meetingReserveScript[dataList]+"'/>";
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
	}
}
