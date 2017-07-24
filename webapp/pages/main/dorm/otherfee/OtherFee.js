
function OtherFeeScript() {
	this.appKey = "";
	this.opts = null;
}
OtherFeeScript.prototype = {
	initPage : function(option) {
		this.opts = $.extend({}, option);
	},

	submitFormOnCheckIn : function(obj) {// 点击按钮提交
		var bodyId = "#" + this.opts.appKey + "Body";
		var formId = this.opts.appKey + "Form";
		var url = $("#" + formId).attr("action") + '?timeStamp='
				+ (new Date()).getTime();

		if ($.trim($("#" + formId).find("#yearMonth").val()) == "") { // 防止与查询列表配置的字段冲突
			$.messager.alert("警告", "选择月份不能为空!");
			return false;
		}

		$.ts.EasyUI.ajaxSubmitForm(url, formId, function() {
			$.ts.EasyUI.closeDialog(obj);
		});
		return false;
	},

	submitFormOnOtherFee : function(obj) {// 点击按钮提交
		var bodyId = "#" + this.opts.appKey + "Body";
		var formId = this.opts.appKey + "Form";
		var url = $("#" + formId).attr("action") + '?timeStamp='
				+ (new Date()).getTime();

		if ($.trim($("#" + formId).find("#yearMonth").val()) == "") { // 防止与查询列表配置的字段冲突
			$.messager.alert("警告", "选择月份不能为空!");
			return false;
		}

		$.ts.EasyUI.ajaxSubmitForm(url, formId, function() {
			$.ts.EasyUI.closeDialog(obj);
		});
		return false;
	},

	submitFormOnExportExcel : function(obj) {// 点击按钮提交
		var bodyId = "#" + this.opts.appKey + "Body";
		var formId = this.opts.appKey + "Form";
		var url = $("#" + formId).attr("action") + '?timeStamp='
				+ new Date().getTime() + "&yearMonth="
				+ $("#" + formId).find("#yearMonth").val() + "&buildingId="
				+ $("#" + formId).find("#buildingId").combobox("getValue");

		if ($.trim($("#" + formId).find("#yearMonth").val()) == "") { // 防止与查询列表配置的字段冲突
			$.messager.alert("警告", "选择月份不能为空!");
			return false;
		}

//		$.ts.EasyUI.closeDialog(obj);
		window.open(url);
		return false;
	},
	submitFormOnDelete : function(obj) {// 点击按钮提交
		var bodyId = "#" + this.opts.appKey + "Body";
		var formId = this.opts.appKey + "Form";
		var url = $("#" + formId).attr("action") + '?timeStamp='
				+ (new Date()).getTime();

		if ($.trim($("#" + formId).find("#yearMonth").val()) == "") { // 防止与查询列表配置的字段冲突
			$.messager.alert("警告", "选择月份不能为空!");
			return false;
		}

		$.ts.EasyUI.ajaxSubmitForm(url, formId, function() {
			$.ts.EasyUI.closeDialog(obj);
		});
		return false;
	}
}
