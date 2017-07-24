function ScriptUtil() {
	this.appKey = "";
	this.opts = null;
}
ScriptUtil.prototype = {

	initPage : function(option) {
		this.opts = $.extend({}, option);
	},
	submitForm : function(obj) {
		var bodyId = "#" + this.opts.appKey + "Body";
		var formId = this.opts.appKey + "Form";
		var url = $("#" + formId).attr("action") + '?timeStamp='+ (new Date()).getTime();
		if ($.trim($("#bean\\.jobName").val()) == "") {
			alert("任务名称不能为空！")
			return false;
		}
		if ($.trim($("#bean\\.jobGroup").val()) == "") {
			alert("任务小组不能为空！")
			return false;
		}
		if ($.trim($("#bean\\.cronExpression").val()) == "") {
			alert("cron表达式不能为空！")
			return false;
		}
		if ($.trim($("#bean\\.beanClass").val()) == "") {
			alert("java类名不能为空！")
			return false;
		}
		if ($.trim($("#bean\\.methodName").val()) == "") {
			alert("java方法名不能为空！")
			return false;
		}
		$.ts.EasyUI.ajaxSubmitForm(url, formId, function() {
			$.ts.EasyUI.closeDialog(obj);
		});
		return false;
	}
	
}
 
 