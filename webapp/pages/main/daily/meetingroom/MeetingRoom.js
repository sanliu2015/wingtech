function MeetingRoomScript() {
	this.appKey = "";
	this.opts = null;
}
MeetingRoomScript.prototype = {
	initPage : function(option) {
		this.opts = $.extend({}, option);
	},
	submitForm : function(obj) {// 点击按钮提交
		var bodyId = "#" + this.opts.appKey + "Body";
		var formId = this.opts.appKey + "Form";
		var url = $("#" + formId).attr("action") + '?timeStamp=' + new Date().getTime();
		if ($.trim($("#bean\\.name").textbox("getText")) == "") {
			$.messager.alert("警告", "姓名不能为空!");
			return false;
		}
		if ($.trim($("#bean\\.number").textbox("getText")) == "") {
			$.messager.alert("警告", "姓名不能为空!");
			return false;
		}

		$.ts.EasyUI.ajaxSubmitForm(url, formId, function() {
			$.ts.EasyUI.closeDialog(obj);
		});
		return false;
	}
}


function queryData(flag) {
	var firstDate = $("#titleTr").find("span").eq(0).text();
	$.ajax({
		type:"post",
		url: tsContextPath + "/main/meetingRoomService/json/QueryMeetingRoom.do?timestamp=" + new Date().getTime(),
		data:{"firstDate":firstDate,"flag":flag,"name":$("#name").val(),"capacity":$("#capacity").val()},
		dataType:"json",
		success:function(result,textStatus) {
			$("tr[id ^= 'rm']").remove();
			var dateList = result.dateList;
			for (var i=0;i<7;i++) {
				$("#titleTr").find("span").eq(i).text(dateList[i]);
			}
			$("#timeTitle").after(result.trStr);
		},
		error: function(XmlHttpRequest, textStatus, errorThrown) {  
			var str=XmlHttpRequest.responseText; 
			if(str == "" || str==undefined || str==null || str=="null"){
				str=XmlHttpRequest.responseXML;
			}
			str=str+"<hr/>"+textStatus;
			var index = layer.open({
				  type: 1,
				  content: str,
				  maxmin: true
				});
			layer.full(index);
		}
	});
}


function viewDtl(obj) {
	var $this = $(obj);
	var dateParam = $this.attr("name");
	var roomId = $this.parent().parent().attr("id").substr(2);
	layer.open({
		type: 2,
		area: ['700px', '300px'],
		fixed: false, //不固定
		maxmin: true,
		content: tsContextPath + '/main/meetingRoomService/viewDtl.do?roomId=' + roomId + '&dateParam=' + dateParam 
	});
}
