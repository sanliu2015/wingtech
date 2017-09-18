var dataOpt = {table: "dtlListGrid"};
function PassBillScript() {
	this.appKey = "";
	this.opts = null;
}
PassBillScript.prototype = {
	initPage : function(option) {
		this.opts = $.extend({}, option);
	},
	
	chooseEmployee:function(obj){    
	      var urlJson={urlType:"chooseRow",  moduleFileName:"CheckInServiceReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var that=this;
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择入住员工",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							  $("#bean\\.empName").textbox('setText',json.empName);  
							  $("#bean\\.buildName").textbox('setText',json.buildingName);  
							  $("#bean\\.roomName").textbox('setText',json.roomName);  
							  $("#bean\\.empNumber").textbox('setText',json.number);  
							  $("#bean\\.empId").val(json.employeeId);   
							  $("#bean\\.roomId").val(json.roomId);   
							  $("#bean\\.buildId").val(json.buildingId); 
							  $("#damage\\.checkInId").val(json.id);  
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	chooseEmployeeOut:function(obj){    
	      var urlJson={urlType:"chooseRow",  moduleFileName:"checkOutServiceReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var that=this;
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择退宿员工",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							  $("#bean\\.empName").textbox('setText',json.empName);  
							  $("#bean\\.buildName").textbox('setText',json.buildingName);  
							  $("#bean\\.roomName").textbox('setText',json.roomName);  
							  $("#bean\\.empNumber").textbox('setText',json.number);  
							  $("#bean\\.empId").val(json.employeeId);   
							  $("#bean\\.roomId").val(json.roomId);   
							  $("#bean\\.buildId").val(json.buildingId);   
							  $("#damage\\.checkInId").val(json.id);   
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	
	chooseRoom:function(obj){    
	      var urlJson={urlType:"chooseRow",  moduleFileName:"ChoseRoomReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var that=this;
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择房间",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							  $("#bean\\.roomName").textbox('setText',json.roomName);  
							  $("#bean\\.roomId").val(json.id);   
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
		return  "<input class='easyui-combobox' type='text' name='dtlList."+fieldName+"' id='dtlList."+fieldName+"' value='" + val + "'  style='width:100%'    data-options='editable:false,valueField:\"value\",textField:\"text\",data:"+passBillScript[dataList]+"'/>";
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
				+ "'  style='width:100%'   class='easyui-numberspinner' data-options=\"min:1,value:1\"/>";
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

		if ($.trim($("#bean\\.empId").val()) == "") {
			$.messager.alert("警告", "姓名不能为空!");
			return false;
		}
		if ($.trim($("#bean\\.passDate").val()) == "") {
			$.messager.alert("警告", "日期不能为空!");
			return false;
		}
		if ($("#bean\\.reason").combobox("getValue") == "") {
			$.messager.alert("警告", "原因不能为空!");
			return false;
		}
		$.ts.EasyUI.clearElementsSubscript("dtlList.id", $("#dtlListGrid").parent(), null);
		
		var visibleTr = $("#dtlListGrid").datagrid("getPanel").find('div[class="datagrid-body"]').children("table").find("tr:visible").filter(".datagrid-row");
		 
		var failFlag = false;
		$.each(visibleTr,function(i,row) {
			if ($(row).find('td[field="name"]').find(".easyui-combobox").combobox("getValue") == "") {
				$.messager.alert('提示','行号为' + (i+1) + "名称不能为空!");
				failFlag = true;
				return false;
			}
			if ($(row).find('td[field="quantity"]').find(".easyui-numberspinner").textbox("getValue") == "") {
				$.messager.alert('提示','行号为' + (i+1) + "数量不能为空!");
				failFlag = true;
				return false;
			}
		});
		 
		if (failFlag) {
			return false;
		} 
		
//		return false;
		var index=$.ts.EasyUI.submitRowsToList("dtlList.id",$("#dtlListGrid").parent(),{effectRowFields:["dtlList.recordOperateStatus"]}); 
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
