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
			alert("�������Ʋ���Ϊ�գ�")
			return false;
		}
		if ($.trim($("#bean\\.jobGroup").val()) == "") {
			alert("����С�鲻��Ϊ�գ�")
			return false;
		}
		if ($.trim($("#bean\\.cronExpression").val()) == "") {
			alert("cron���ʽ����Ϊ�գ�")
			return false;
		}
		if ($.trim($("#bean\\.beanClass").val()) == "") {
			alert("java��������Ϊ�գ�")
			return false;
		}
		if ($.trim($("#bean\\.methodName").val()) == "") {
			alert("java����������Ϊ�գ�")
			return false;
		}
		$.ts.EasyUI.ajaxSubmitForm(url, formId, function() {
			$.ts.EasyUI.closeDialog(obj);
		});
		return false;
	}
	
}
 
 