function ImportExcelScript() {
	this.appKey = "";
	this.opts = null;
}
ImportExcelScript.prototype = {
	initPage : function(option) {
		this.opts = $.extend({}, option);
	},
	
	submitForm :function(obj) {  
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId="#"+this.opts.appKey+"Form";
		 var url=$(formId).attr("action")+'?timeStamp='+(new Date()).getTime();
		 if ($("#sheetNo").numberspinner("getValue") == "") {
			 alert("sheet编号不能为空!");
			 return false;
		 }
		 if ($("#headNo").numberspinner("getValue") == "") {
			 alert("标题行号不能为空!");
			 return false;
		 }
		 if ($("#excelFile").filebox("getText") == "") {
			 alert("没有要导入的文件!");
			 return false;
		 } else {
			 var fileName = $("#excelFile").filebox("getText");
			 var fileNameSuffix =fileName.substr(fileName.lastIndexOf(".")).toLowerCase();
			 if (fileNameSuffix != ".xlsx" && fileNameSuffix != ".xls") {
				 alert("不是有效的excel格式文件!");
				 return false;
			 }
		 }
		 
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 });
	}

	
}
