var dataOpt = {table: "dtlListGrid"};
function RewardPunishScript( ){  
	 this.appKey=""; 
	 this.opts=null;
 }
RewardPunishScript.prototype={      
    initPage:function(option){
		this.opts= $.extend({},option);   
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
							  $("#bean\\.empName").textbox('setText',json.name);  
							  $("#bean\\.empNumber").textbox('setText',json.number);   
							  $("#bean\\.depName").textbox('setText',json.deptName);   
							  $("#bean\\.posName").textbox('setText',json.positionName);   
							  $("#bean\\.employeeId").val(json.id);  
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	chooseRewardEmp : function(obj) {
		var recDataTemplate = [ {
			field : [ "id", "dtlList.employeeId" ],
			sameRecordFlag : "1"
		}, {
			field : [ "name", "dtlList.empName" ]
		}, {
			field : [ "number", "dtlList.empNumber" ]
		},{
			field : [ "deptName", "dtlList.depName" ]
		} ];
		var ids = document.getElementsByName("dtlList.employeeId");
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
			moduleFileName : "ChoseEmployeeReport",
			openQueryResult : "1",
			timeStamp : "" + (new Date()).getTime()
		};
		var url = $.ts.Utils.toUrlParam(urlJson);

		var handler = $.ts.EasyUI.frameDialog({
			title : "选择人员",
			href : url
		}, chooseOptions);
	},
	hiddenColumnFormat : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");
		return "<input type='hidden' name='dtlList." + fieldName
				+ "' id='dtlList." + fieldName + "' value='" + val + "'   />";
	},
	numberboxInputFormat : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");
		return "<input name='dtlList."
				+ fieldName
				+ "' id='dtlList."
				+ fieldName
				+ "' value='"
				+ val
				+ "' class='easyui-numberbox'  style='width:100%' data-options=\"min:0,precision:0\"/>";
	},
	textInputFormat : function(val, row, index) {
		if (val == undefined)
			val = "";
		var fieldName = $(this).attr("field");

		return "<input   name='dtlList." + fieldName + "' id='dtlList."
				+ fieldName + "' value='" + val
				+ "'  style='width:100%'  class='easyui-textbox'/>";
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
    submitForm:function (obj){//点击按钮提交   
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 
		 if ($.trim($("#bean\\.occurDate").val()) == "") {
			 $.messager.alert("警告", "日期不能为空!");
			 return false;
		 }
		 
		 if ($.trim($("#bean\\.empName").textbox("getText")) == "") {
			 $.messager.alert("警告", "姓名不能为空!");
			 return false;
		 }

		 if ($.trim($("#bean\\.reason").combobox("getText")) == "") {
			 $.messager.alert("警告", "违规违纪内容不能为空!");
			 return false;
		 }
		 $.ts.EasyUI.clearElementsSubscript("dtlList.id", $("#dtlListGrid").parent(), null);
		 var index=$.ts.EasyUI.submitRowsToList("dtlList.id",$("#dtlListGrid").parent(),{effectRowFields:["dtlList.recordOperateStatus"]}); 
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 }); 
		return false;
	 },
	 
	 submitFormOnExportExcel : function(obj) {// 点击按钮提交
			var bodyId = "#" + this.opts.appKey + "Body";
			var formId = this.opts.appKey + "Form";
			var url = $("#" + formId).attr("action") + '?timeStamp='
					+ new Date().getTime() + "&yearMonth="
					+ $("#" + formId).find("#yearMonth").val();

			if ($.trim($("#" + formId).find("#yearMonth").val()) == "") { // 防止与查询列表配置的字段冲突
				$.messager.alert("警告", "选择月份不能为空!");
				return false;
			}

//			$.ts.EasyUI.closeDialog(obj);
			window.open(url);
			return false;
		}
 }
 
 