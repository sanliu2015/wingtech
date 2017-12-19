var dataOpt = {table: "dtlListGrid"};
$.ajaxSetup({
	  async: false
});
function queryRoom() {
	var build = $("#build").combobox("getValue");
	var unit = $("#unit").combobox("getValue");
	var roomType = $("#roomType").combobox("getValue");
	var roomName = $("#roomName").textbox("getValue");
	var canInBg = $("#canInBg").numberspinner("getValue");
	var canInEd = $("#canInEd").numberspinner("getValue");
	var params = {"build":build,"unit":unit,"roomType":roomType,"roomName":roomName,"canInBg":canInBg,"canInEd":canInEd};
	$.ajax({
		dataType:'json',
		cache:false,
		url:tsContextPath + '/main/checkInService/json/queryRoom.do',
		data:params,
		beforeSend:function(XMLHttpRequest){
			$("#roomPanel").html("<center><p><img src='" + tsContextPath + "/resource/image/icon/ajax_loading.gif' alt='' border='0' /></p></center>");
		},
		success:function(data, textStatus){
			var dtlList = data;
			var htmlStr = '';
			var emptyCount = 0;
			for (var i=0,len=dtlList.length; i<len; i++) {
				var title = "";
				title += "所在楼栋：<strong>" + dtlList[i].buildingName + "</strong><br/>" + "所在单元：<strong>" + dtlList[i].unitName + "</strong><br/>" + "房间类型：<strong>" + dtlList[i].typeName + "</strong><br/>" + "入住人数：<strong>" + dtlList[i].inSum + "/" + dtlList[i].bigNumber + "</strong>";
				htmlStr += '<div id="room' + dtlList[i].id + '" class="room" title="' + title + '" ';
				if (dtlList[i].inSum == 0) {
					htmlStr += 'style="background-color:#FFFFFF" '
					emptyCount++;
				} else {
					htmlStr += 'style="background-color:#90EE90" '
				}
				htmlStr += '>' + dtlList[i].roomName + '</div>';
			}
			htmlStr = '<div class="total" style="padding:2px;">总数：' + dtlList.length + '&nbsp;&nbsp;&nbsp;空房：' + emptyCount + '&nbsp;&nbsp;&nbsp;<input type="button" value="保存分配" onClick="doCheckIn();"></div>' + htmlStr;
			$("#roomPanel").html(htmlStr);
			$('.room').droppable({
				accept:'.emp',
				onDragEnter:function(e,source){
					$(source).draggable('options').cursor='auto';
					$(source).draggable('proxy').css('border','1px solid red');
					$(this).addClass('over');
				},
				onDragLeave:function(e,source){
					$(source).draggable('options').cursor='not-allowed';
					$(source).draggable('proxy').css('border','1px solid #ccc');
					$(this).removeClass('over');
				},
				onDrop:function(e,source){
					$(this).append(source)
					$(this).removeClass('over');
				}
			}).tooltip({
				position:'top',
				trackMouse:true,
				content:this.title,
				onShow:function(){
					$(this).tooltip('tip').css({
						backgroundColor: '#f7f5d1',
						border:'1px solid #333',
					});
				}
			});
		},
		error:function(XmlHttpRequest, textStatus, errorThrown){   
			$("#roomPanel").html('');
			var str = XmlHttpRequest.responseText; 
			if($.ts.Utils.isEmpty(str)){
				str=XmlHttpRequest.responseXML;
			}
			str=str+"<hr/>"+textStatus;
			$.ts.EasyUI.showContentDialog(str); 
		} 
	});
}


function queryEmp() {
	var interimId = $("#interimId").combobox("getValue");
	var empName = $("#empName").textbox("getValue");
	var idCard = $("#idCard").textbox("getValue");
	var number = $("#number").textbox("getValue");
	var gender = $("#gender").combobox("getValue");
	var params = {"empName":empName,"number":number,"interimId":interimId,"idCard":idCard,"gender":gender};
	$.ajax({
		dataType:'json',
		cache:false,
		url:tsContextPath + '/main/checkInService/json/queryEmp.do',
		data:params,
		beforeSend:function(XMLHttpRequest){
			$("#empPanel").html("<center><p><img src='" + tsContextPath + "/resource/image/icon/ajax_loading.gif' alt='' border='0' /></p></center>");
		},
		success:function(data, textStatus){
			var dtlList = data;
			var htmlStr = '';
			for (var i=0,len=dtlList.length; i<len; i++) {
				var title = "";
				title += "姓名：<strong>" + dtlList[i].name + "</strong><br/>" + "工号：<strong>" + dtlList[i].number + "</strong><br/>" + "科室：<strong>" + dtlList[i].deptName + "</strong><br/>";
				htmlStr += '<div id="emp' + dtlList[i].id + '" class="emp" title="' + title + '" >';
				if (dtlList[i].gender == '1') {		// 男
					htmlStr += '<img src="' + tsContextPath + '/resource/image/icon/male.png" alt="男" border="0" />';
				} else if (dtlList[i].gender == '0') {	// 女
					htmlStr += '<img src="' + tsContextPath + '/resource/image/icon/female.png" alt="女" border="0" />';
				}
				htmlStr += dtlList[i].name + '</div>';
			}
			$("#empPanel").html(htmlStr);
			$('#empPanel').droppable({
				accept:'.emp',
				onDragEnter:function(e,source){
					$(source).draggable('options').cursor='auto';
					$(source).draggable('proxy').css('border','1px solid red');
					$(this).addClass('over');
				},
				onDragLeave:function(e,source){
					$(source).draggable('options').cursor='not-allowed';
					$(source).draggable('proxy').css('border','1px solid #ccc');
					$(this).removeClass('over');
				},
				onDrop:function(e,source){
					$(this).append(source)
					$(this).removeClass('over');
				}
			});
			$('.emp').draggable({
				proxy:'clone',
				revert:true,
				cursor:'auto',
				onStartDrag:function(){
					$(this).draggable('options').cursor='not-allowed';
					$(this).draggable('proxy').addClass('dp');
				},
				onStopDrag:function(){
					$(this).draggable('options').cursor='auto';
				}
			}).tooltip({
				position:'top',
				trackMouse:true,
				content:this.title,
				onShow:function(){
					$(this).tooltip('tip').css({
						backgroundColor: '#f7f5d1',
						border:'1px solid #333',
					});
				}
			});
		},
		error:function(XmlHttpRequest, textStatus, errorThrown){   
			$("#roomPanel").html('');
			var str = XmlHttpRequest.responseText; 
			if($.ts.Utils.isEmpty(str)){
				str=XmlHttpRequest.responseXML;
			}
			str=str+"<hr/>"+textStatus;
			$.ts.EasyUI.showContentDialog(str); 
		} 
	});
}

function clearRoomCondition() {
	$("#build,#unit,#roomType").combobox("clear");
	$("#roomName").textbox("clear");
}

function clearEmpCondition() {
	$("#empName,#number").textbox("clear");
	$("#deptId").combobox("clear");
}

function doCheckIn() {
	if ($("#roomPanel").find(".emp").length == 0) {
		$.messager.alert("警告", "没有分配人员入住!");
		return false;
	}
	
	var dataList = []
	$.each($("#roomPanel").find(".emp"), function(){  
		var obj = {};
		obj.empId = $(this).attr("id").substring(3);
		obj.roomId = $(this).parent().attr("id").substring(4);
		dataList.push(obj);
	});
	
	$.ajax({
		type : "post",
		dataType: "json",
		url : tsContextPath + '/main/checkInService/json/doCheckIn.do?timeStamp=' + new Date().getTime(),
		data : {"dataList" : JSON.stringify(dataList)},
		success : function(result) {
			$.messager.show({
				title:'提示',
				timeout:'1000',
				showType:'fade',
				msg:'' + result.msg,
				style:{
					right:'',
					bottom:''
				}
			});
		},
		error : function(XmlHttpRequest, textStatus, errorThrown){   
			var str = XmlHttpRequest.responseText; 
			if($.ts.Utils.isEmpty(str)){
				str=XmlHttpRequest.responseXML;
			}
			str=str+"<hr/>"+textStatus;
			$.ts.EasyUI.showContentDialog(str); 
		} 
	});
	
}


function CheckInScript( ){  
	 this.appKey=""; 
	 this.opts=null;
}
CheckInScript.prototype={      
   initPage:function(option){
		this.opts= $.extend({},option);   
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
							  $("#bean\\.buildingName").textbox('setText',json.buildingName);  
							  $("#bean\\.roomId").val(json.id);   
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	chooseEmployee:function(obj){    
	      var urlJson={urlType:"chooseRow", number:$("#bean\\.empNumber").val(),  moduleFileName:"ChoseEmployeeReport",tsFilterSql:"{and not exists(select 1 from dorm_checkIn x where x.employeeId=a.id and isnull(x.checkOutFlag,0)=0 ) }",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  //debugger;
		  var that=this;
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择员工",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							 $("#bean\\.empName").textbox('setText',json.name);  
							 $("#bean\\.empNumber").textbox('setText',json.number);  
							 $("#bean\\.employeeId").val(json.id);   
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
	comboboxMultipleFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");    
		var dataList = fieldName + "List";
		return  "<input class='easyui-combobox' type='text' name='dtlList."+fieldName+"' id='dtlList."+fieldName+"' value='" + val + "'  style='width:100%'    data-options='multiple:true,valueField:\"name\",textField:\"name\",data:"+checkInScript[dataList]+"'/>";
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
		return "<input type='text' name='dtlList."
				+ fieldName
				+ "' id='dtlList."
				+ fieldName
				+ "' value='"
				+ val
				+ "'  style='width:100%'   class='easyui-numberbox' data-options=\"min:0\"/>";
	},
	insertDatagridRow : function(rowBean) {
		var rowOpt = {};
		if ($.ts.Utils.isEmpty(rowBean) == false) {
			rowOpt = rowBean;
		}
		var table = $('#dtlListGrid');
		table.datagrid('appendRow', rowOpt['dtlList']);
		var ids = document.getElementsByName("dtlList.employeeId");
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
		 
		 if ($.trim($("#bean\\.roomName").textbox("getText")) == "") {
			 $.messager.alert("警告", "房间不能为空!");
			 return false;
		 }
		 
		 if ($.trim($("#bean\\.empName").textbox("getText")) == "") {
			 $.messager.alert("警告", "姓名不能为空!");
			 return false;
		 }
		 
		 if ($.trim($("#bean\\.inDate").val()) == "") {
			 $.messager.alert("警告", "入住日期不能为空!");
			 return false;
		 }
		 
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 }); 
		return false;
	 },
	 
	 submitFormOnCheckOut:function (obj){//点击按钮提交   
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		 var url=$("#"+formId).attr("action")+'?timeStamp='+new Date().getTime();  
		 
		 if ($.trim($("#bean\\.outDate").val()) == "") {
			 $.messager.alert("警告", "退宿日期不能为空!");
			 return false;
		 }
		 
		 if ($("#damage\\.amount").numberbox("getValue") > 0) {
			 if ($.trim($("#damage\\.reason").textbox("getValue")) == "") {
				 $.messager.alert("警告", "扣款原因不能为空!");
				 return false;
			 }
		 }
		 
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 }); 
		return false;
	 },
	 
	 submitFormOnCheckOutBatch:function (obj){//点击按钮提交   
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		 var url=$("#"+formId).attr("action")+'?timeStamp='+new Date().getTime();  
		 
		 if ($.trim($("#bean\\.outDate").val()) == "") {
			 $.messager.alert("警告", "退宿日期不能为空!");
			 return false;
		 }
		 $.each($("input[name='dtlList.amount']"),function(i,val){
			 if (val.value > 0) {
				 if ($.trim($(val).parent().parent().parent().parent().find("#dtlList\\.reason").textbox("getValue")) == "") {
					 $.messager.alert("警告", "第" + (i+1) + "行扣款原因不能为空!");
					 return false;
				 }
			 }
		 });
		 
		 $.ts.EasyUI.clearElementsSubscript("dtlList.employeeId", $("#dtlListGrid").parent(), null);
		 var index=$.ts.EasyUI.submitRowsToList("dtlList.employeeId",$("#dtlListGrid").parent(),{effectRowFields:["dtlList.recordOperateStatus"]}); 
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 }); 
		return false;
	 },
	 
	 chooseBeforeRoom:function(obj){    
	      var urlJson={urlType:"chooseRow",  moduleFileName:"ChoseRoomReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var that=this;
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择房间",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							  $("#beforeRoom").textbox('setText',json.roomName);  
							  $("#beforeRoomId").val(json.id);
							  $.ajax({
									dataType:'json',
									cache:false,
									url:tsContextPath + '/main/checkInService/json/queryRoomEmp.do?timeStamp=' + new Date().getTime() + "&roomId=" + (json.id == undefined ? 0 : json.id),
									beforeSend:function(XMLHttpRequest){
										$("#beforePanel").html("<center><p><img src='" + tsContextPath + "/resource/image/icon/ajax_loading.gif' alt='' border='0' /></p></center>");
									},
									success:function(data, textStatus){
										var dtlList = data;
										var htmlStr = '';
										for (var i=0,len=dtlList.length; i<len; i++) {
											var title = "";
											title += "姓名：<strong>" + dtlList[i].name + "</strong><br/>" + "工号：<strong>" + dtlList[i].number + "</strong><br/>" + "科室：<strong>" + dtlList[i].deptName + "</strong><br/>";
											htmlStr += '<div id="emp' + dtlList[i].id + '" data-orgRoom=' + dtlList[i].orgRoom + ' class="emp" title="' + title + '" >';
											if (dtlList[i].gender == '1') {		// 男
												htmlStr += '<img src="' + tsContextPath + '/resource/image/icon/male.png" alt="男" border="0" />';
											} else if (dtlList[i].gender == '0') {	// 女
												htmlStr += '<img src="' + tsContextPath + '/resource/image/icon/female.png" alt="女" border="0" />';
											}
											htmlStr += dtlList[i].name + "|入住日期" + dtlList[i].inDate + '</div>';
										}
										$("#beforePanel").html(htmlStr);
										$('.emp').draggable({
											proxy:'clone',
											accept:'.emp',
											revert:true,
											cursor:'auto',
											onStartDrag:function(){
												$(this).draggable('options').cursor='not-allowed';
												$(this).draggable('proxy').addClass('dp');
											},
											onStopDrag:function(){
												$(this).draggable('options').cursor='auto';
											}
										}).tooltip({
											position:'top',
											trackMouse:true,
											content:this.title,
											onShow:function(){
												$(this).tooltip('tip').css({
													backgroundColor: '#f7f5d1',
													border:'1px solid #333',
												});
											}
										});
									},
									error:function(XmlHttpRequest, textStatus, errorThrown){   
										$("#beforePanel").html('');
										var str = XmlHttpRequest.responseText; 
										if($.ts.Utils.isEmpty(str)){
											str=XmlHttpRequest.responseXML;
										}
										str=str+"<hr/>"+textStatus;
										$.ts.EasyUI.showContentDialog(str); 
									} 
								});
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	
	chooseAfterRoom:function(obj){    
	      var urlJson={urlType:"chooseRow",  moduleFileName:"ChoseRoomReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var that=this;
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择房间",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							  $("#afterRoom").textbox('setText',json.roomName);  
							  $("#afterRoomId").val(json.id);
							  $.ajax({
									dataType:'json',
									cache:false,
									url:tsContextPath + '/main/checkInService/json/queryRoomEmp.do?timeStamp=' + new Date().getTime() + "&roomId=" + (json.id == undefined ? 0 : json.id),
									beforeSend:function(XMLHttpRequest){
										$("#afterPanel").html("<center><p><img src='" + tsContextPath + "/resource/image/icon/ajax_loading.gif' alt='' border='0' /></p></center>");
									},
									success:function(data, textStatus){
										var dtlList = data;
										var htmlStr = '';
										for (var i=0,len=dtlList.length; i<len; i++) {
											var title = "";
											title += "姓名：<strong>" + dtlList[i].name + "</strong><br/>" + "工号：<strong>" + dtlList[i].number + "</strong><br/>" + "科室：<strong>" + dtlList[i].deptName + "</strong><br/>";
											htmlStr += '<div id="emp' + dtlList[i].id + '" data-orgRoom=' + dtlList[i].orgRoom + ' class="emp" title="' + title + '" >';
											if (dtlList[i].gender == '1') {		// 男
												htmlStr += '<img src="' + tsContextPath + '/resource/image/icon/male.png" alt="男" border="0" />';
											} else if (dtlList[i].gender == '0') {	// 女
												htmlStr += '<img src="' + tsContextPath + '/resource/image/icon/female.png" alt="女" border="0" />';
											}
											htmlStr += dtlList[i].name + "|入住日期" + dtlList[i].inDate + '</div>';
										}
										$("#afterPanel").html(htmlStr);
										$('.emp').draggable({
											proxy:'clone',
											revert:true,
											cursor:'auto',
											onStartDrag:function(){
												$(this).draggable('options').cursor='not-allowed';
												$(this).draggable('proxy').addClass('dp');
											},
											onStopDrag:function(){
												$(this).draggable('options').cursor='auto';
											}
										}).tooltip({
											position:'top',
											trackMouse:true,
											content:this.title,
											onShow:function(){
												$(this).tooltip('tip').css({
													backgroundColor: '#f7f5d1',
													border:'1px solid #333',
												});
											}
										});
									},
									error:function(XmlHttpRequest, textStatus, errorThrown){   
										$("#afterPanel").html('');
										var str = XmlHttpRequest.responseText; 
										if($.ts.Utils.isEmpty(str)){
											str=XmlHttpRequest.responseXML;
										}
										str=str+"<hr/>"+textStatus;
										$.ts.EasyUI.showContentDialog(str); 
									} 
								});
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	
	submitFormOnAdjust:function(obj) {
		var dataList = []
		var beforeRoomId = $("#beforeRoomId").val();
		var afterRoomId = $("#afterRoomId").val();
		if ($("#beforePanel").find(".emp").length > 0) {
			if(isNaN(beforeRoomId) || beforeRoomId =="") {
				$.messager.alert("警告", "房间1不能为空!");
				return false;
			} 
		}
		if ($("#afterPanel").find(".emp").length > 0) {
			if(isNaN(afterRoomId) || afterRoomId == "") {
				$.messager.alert("警告", "房间2不能为空!");
				return false;
			} 
		}
		
		$.each($("#beforePanel").find(".emp"), function(){  
			if ($(this).data("orgroom") != beforeRoomId) {
				var obj = {};
				obj.empId = $(this).attr("id").substring(3);
				obj.roomId = beforeRoomId;
				dataList.push(obj);
			}
		});
		
		$.each($("#afterPanel").find(".emp"), function(){  
			if ($(this).data("orgroom") != afterRoomId) {
				var obj = {};
				obj.empId = $(this).attr("id").substring(3);
				obj.roomId = afterRoomId;
				dataList.push(obj);
			}
		});
		
//		alert(JSON.stringify(dataList));
		if (dataList.length == 0) {
			$.messager.alert("警告", "没有要调整的记录!");
		} else {
			$("#submitBtn").attr("disabled", true);
			$.ajax({
				type : "post",
				dataType: "json",
				url : tsContextPath + '/main/checkInService/json/doAdjust.do?timeStamp=' + new Date().getTime(),
				data : {"dataList" : JSON.stringify(dataList)},
				success : function(result) {
					$.messager.show({
						title:'提示',
						timeout:'1000',
						showType:'fade',
						msg:'' + result.msg,
						style:{
							right:'',
							bottom:''
						}
					});
					$("#submitBtn").removeAttr("disabled");
					
					$.ajax({
						dataType:'json',
						cache:false,
						url:tsContextPath + '/main/checkInService/json/queryRoomEmp.do?timeStamp=' + new Date().getTime() + "&roomId=" + $("#beforeRoomId").val(),
						beforeSend:function(XMLHttpRequest){
							$("#beforePanel").html("<center><p><img src='" + tsContextPath + "/resource/image/icon/ajax_loading.gif' alt='' border='0' /></p></center>");
						},
						success:function(data, textStatus){
							var dtlList = data;
							var htmlStr = '';
							for (var i=0,len=dtlList.length; i<len; i++) {
								var title = "";
								title += "姓名：<strong>" + dtlList[i].name + "</strong><br/>" + "工号：<strong>" + dtlList[i].number + "</strong><br/>" + "科室：<strong>" + dtlList[i].deptName + "</strong><br/>";
								htmlStr += '<div id="emp' + dtlList[i].id + '" data-orgRoom=' + dtlList[i].orgRoom + ' class="emp" title="' + title + '" >';
								if (dtlList[i].gender == '1') {		// 男
									htmlStr += '<img src="' + tsContextPath + '/resource/image/icon/male.png" alt="男" border="0" />';
								} else if (dtlList[i].gender == '0') {	// 女
									htmlStr += '<img src="' + tsContextPath + '/resource/image/icon/female.png" alt="女" border="0" />';
								}
								htmlStr += dtlList[i].name + "|入住日期" + dtlList[i].inDate + '</div>';
							}
							$("#beforePanel").html(htmlStr);
							$('.emp').draggable({
								proxy:'clone',
								accept:'.emp',
								revert:true,
								cursor:'auto',
								onStartDrag:function(){
									$(this).draggable('options').cursor='not-allowed';
									$(this).draggable('proxy').addClass('dp');
								},
								onStopDrag:function(){
									$(this).draggable('options').cursor='auto';
								}
							}).tooltip({
								position:'top',
								trackMouse:true,
								content:this.title,
								onShow:function(){
									$(this).tooltip('tip').css({
										backgroundColor: '#f7f5d1',
										border:'1px solid #333',
									});
								}
							});
						},
						error:function(XmlHttpRequest, textStatus, errorThrown){   
							$("#beforePanel").html('');
							var str = XmlHttpRequest.responseText; 
							if($.ts.Utils.isEmpty(str)){
								str=XmlHttpRequest.responseXML;
							}
							str=str+"<hr/>"+textStatus;
							$.ts.EasyUI.showContentDialog(str); 
						} 
					});
					
					// 重载
					$.ajax({
						dataType:'json',
						cache:false,
						url:tsContextPath + '/main/checkInService/json/queryRoomEmp.do?timeStamp=' + new Date().getTime() + "&roomId=" + $("#afterRoomId").val(),
						beforeSend:function(XMLHttpRequest){
							$("#afterPanel").html("<center><p><img src='" + tsContextPath + "/resource/image/icon/ajax_loading.gif' alt='' border='0' /></p></center>");
						},
						success:function(data, textStatus){
							var dtlList = data;
							var htmlStr = '';
							for (var i=0,len=dtlList.length; i<len; i++) {
								var title = "";
								title += "姓名：<strong>" + dtlList[i].name + "</strong><br/>" + "工号：<strong>" + dtlList[i].number + "</strong><br/>" + "科室：<strong>" + dtlList[i].deptName + "</strong><br/>";
								htmlStr += '<div id="emp' + dtlList[i].id + '" data-orgRoom=' + dtlList[i].orgRoom + ' class="emp" title="' + title + '" >';
								if (dtlList[i].gender == '1') {		// 男
									htmlStr += '<img src="' + tsContextPath + '/resource/image/icon/male.png" alt="男" border="0" />';
								} else if (dtlList[i].gender == '0') {	// 女
									htmlStr += '<img src="' + tsContextPath + '/resource/image/icon/female.png" alt="女" border="0" />';
								}
								htmlStr += dtlList[i].name + "|入住日期" + dtlList[i].inDate + '</div>';
							}
							$("#afterPanel").html(htmlStr);
							$('.emp').draggable({
								proxy:'clone',
								revert:true,
								cursor:'auto',
								onStartDrag:function(){
									$(this).draggable('options').cursor='not-allowed';
									$(this).draggable('proxy').addClass('dp');
								},
								onStopDrag:function(){
									$(this).draggable('options').cursor='auto';
								}
							}).tooltip({
								position:'top',
								trackMouse:true,
								content:this.title,
								onShow:function(){
									$(this).tooltip('tip').css({
										backgroundColor: '#f7f5d1',
										border:'1px solid #333',
									});
								}
							});
						},
						error:function(XmlHttpRequest, textStatus, errorThrown){   
							$("#afterPanel").html('');
							var str = XmlHttpRequest.responseText; 
							if($.ts.Utils.isEmpty(str)){
								str=XmlHttpRequest.responseXML;
							}
							str=str+"<hr/>"+textStatus;
							$.ts.EasyUI.showContentDialog(str); 
						} 
					});
					
					
					
				},
				error : function(XmlHttpRequest, textStatus, errorThrown){   
					var str = XmlHttpRequest.responseText; 
					if($.ts.Utils.isEmpty(str)){
						str=XmlHttpRequest.responseXML;
					}
					str=str+"<hr/>"+textStatus;
					$.ts.EasyUI.showContentDialog(str); 
				} 
			});
		}
		
	},
	
	
	submitFormOnImport:function(obj) {  
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId="#"+this.opts.appKey+"Form";
		 var url=$(formId).attr("action")+'?timeStamp='+(new Date()).getTime();
		 if ($("#sheetNo").numberbox("getValue") == "") {
			 alert("sheet编号不能为空!");
			 return false;
		 }
		 if ($("#headNo").numberbox("getValue") == "") {
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
		 $(formId).mask(); 
		 $(formId).ajaxSubmit({
				url:url,   
				async:false,
				type: 'post',  
				dataType: "json",
				success : function(resultObj) {
					$(formId).mask("hide"); 
					debugger;
					if (resultObj.needClose == "1") {
						$.ts.EasyUI.closeDialog(obj);
						$.messager.show({
							title:'操作提示',
							msg:resultObj.statememt,
							showType:'fade',
							timeout:500,
							style:{
								right:'',
								bottom:''
							}
						});
					} else {
						if (resultObj.errList.length > 0) {
								$.messager.alert('提示', resultObj.statememt,
										'error', function() {
											$("#errorListGrid").datagrid('loadData', {'total' : '0',rows : []});
											$("#errorListGrid").datagrid('loadData',resultObj.errList);
										}); 
							
						} 
					}
					
				},
				error: function(XmlHttpRequest, textStatus, errorThrown){   
					 $(formId).mask("hide"); 
					 var str=XmlHttpRequest.responseText; 
					 if($.ts.Utils.isEmpty(str)){
						  str=XmlHttpRequest.responseXML;
					 }
					 str=str+"<hr/>"+textStatus;
					 $.ts.EasyUI.showContentDialog(str); 
				 } 
		 });
//	  	 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
//			 $.ts.EasyUI.closeDialog(obj);
//		 });
		return false;
	 } 
} 


function changeToTable(targetDatagrid) {
    var tableString = '<table cellspacing="0" class="pb">';
    var frozenColumns = targetDatagrid.datagrid("options").frozenColumns;    // 得到frozenColumns对象
    var columns = targetDatagrid.datagrid("options").columns;    			// 得到columns对象
    var nameList = new Array();

    // 载入title
    if (typeof columns != 'undefined' && columns != '') {
        $(columns).each(function (index) {
            tableString += '\n<tr style="height:40px;">';
            if (typeof frozenColumns != 'undefined' && typeof frozenColumns[index] != 'undefined') {
                for (var i = 0; i < frozenColumns[index].length; ++i) {
                    if (!frozenColumns[index][i].hidden && frozenColumns[index][i].checkbox != true) {		// 过滤复选框
                        tableString += '\n<th nowrap="nowrap" width="' + frozenColumns[index][i].width + '"';
                        if (typeof frozenColumns[index][i].rowspan != 'undefined' && frozenColumns[index][i].rowspan > 1) {
                            tableString += ' rowspan="' + frozenColumns[index][i].rowspan + '"';
                        }
                        if (typeof frozenColumns[index][i].colspan != 'undefined' && frozenColumns[index][i].colspan > 1) {
                            tableString += ' colspan="' + frozenColumns[index][i].colspan + '"';
                        }
                        if (typeof frozenColumns[index][i].field != 'undefined' && frozenColumns[index][i].field != '') {
                            nameList.push(frozenColumns[index][i]);
                        }
                        tableString += '>' + frozenColumns[0][i].title + '</th>';
                    }
                }
            }
            for (var i = 0; i < columns[index].length; ++i) {
                if (!columns[index][i].hidden) {
                    tableString += '\n<th nowrap="nowrap" width="' + columns[index][i].width + '"';
                    if (typeof columns[index][i].rowspan != 'undefined' && columns[index][i].rowspan > 1) {
                        tableString += ' rowspan="' + columns[index][i].rowspan + '"';
                    }
                    if (typeof columns[index][i].colspan != 'undefined' && columns[index][i].colspan > 1) {
                        tableString += ' colspan="' + columns[index][i].colspan + '"';
                    }
                    if (typeof columns[index][i].field != 'undefined' && columns[index][i].field != '') {
                        nameList.push(columns[index][i]);
                    }
                    tableString += '>' + columns[index][i].title + '</th>';
                }
            }
            tableString += '\n</tr>';
        });
    }
    // 载入内容
    var rows = targetDatagrid.datagrid("getRows"); // 这段代码是获取当前页的所有行
    for (var i = 0; i < rows.length; ++i) {
    	debugger;
        tableString += '\n<tr style="height:30px;">';
        for (var j = 0; j < nameList.length; ++j) {
            tableString += '\n<td nowrap="nowrap"';
            if (typeof nameList[j].align != 'undefined' && nameList[j].align != '') {
                tableString += ' style="text-align:' + nameList[j].align + ';"';
            }
            tableString += '>';
            tableString += rows[i][nameList[j].field] == undefined ? '': rows[i][nameList[j].field];
            tableString += '</td>';
        }
        tableString += '\n</tr>';
    }
    tableString += '\n</table>';
    return tableString;
}

function exportExcel(strXlsName, exportGrid) {
    var f = $('<form action="' + tsContextPath + '/main/publicUtilService/stream/exportExcel.do" method="post" id="fm1"></form>');
    var i = $('<input type="hidden" id="excelContent" name="excelContent" />');
    var l = $('<input type="hidden" id="excelName" name="excelName" />');
    i.val(changeToTable(exportGrid));
    i.appendTo(f);
    l.val(strXlsName);
    l.appendTo(f);
    f.appendTo(document.body).submit();
    document.body.removeChild(f);
}