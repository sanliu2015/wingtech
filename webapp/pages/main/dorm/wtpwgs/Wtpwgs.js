function isEmptyObject(obj){
    for(var n in obj){return false} 
    return true; 
}

function WtpwgsScript( ){  
	 this.appKey=""; 
	 this.opts=null;
 }
WtpwgsScript.prototype={      
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
							  $("#bean\\.buildName").textbox('setText',json.buildingName);  
							  $("#bean\\.roomName").textbox('setText',json.roomName); 
							  $("#bean\\.waterPrice").numberbox('setValue',json.waterFee);   
							  $("#bean\\.powerPrice").numberbox('setValue',json.powerFee);   
							  $("#bean\\.gasPrice").numberbox('setValue',json.gasFee);   
							  $("#bean\\.roomId").val(json.id);  
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	
	getLastResult:function() {
		if ($.trim($("#bean\\.yearMonth").val()) == "") {
			 $.messager.alert("警告", "月份不能为空!");
			 return false;
		}
		
		if ($.trim($("#bean\\.roomId").val()) == "") {
			 $.messager.alert("警告", "房间不能为空!");
			 return false;
		}
		$.ajax({
			url: tsContextPath + "/main/wtpwgsService/json/getLastResult.do?timestamp=" + new Date().getTime(),
			dataType:"json",
			data: {"yearMonth":$("#bean\\.yearMonth").val(),"roomId":$("#bean\\.roomId").val()},
			success:function(result, textStatus){
				if (isEmptyObject(result)) {
					$.messager.alert("提示", "找不到上次本房间水电气抄数!");
				} else {
					$("#bean\\.lastWaterNum").numberbox('setText',result.thisWaterNum);   
					$("#bean\\.lastPowerNum").numberbox('setText',result.thisPowerNum);   
					$("#bean\\.lastGasNum").numberbox('setText',result.thisGasNum);   
				}
				
			},
			error:function(XmlHttpRequest, textStatus, errorThrown){
				var str = XmlHttpRequest.responseText; 
				if($.ts.Utils.isEmpty(str)){
					str=XmlHttpRequest.responseXML;
				}
				str=str+"<hr/>"+textStatus;
				$.ts.EasyUI.showContentDialog(str); 
			}
		});
	},
	
    submitForm:function (obj){//点击按钮提交   
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 
		 if ($.trim($("#bean\\.yearMonth").val()) == "") {
			 $.messager.alert("警告", "月份不能为空!");
			 return false;
		}
		
		if ($.trim($("#bean\\.roomId").val()) == "") {
			 $.messager.alert("警告", "房间不能为空!");
			 return false;
		}
		 
		 if ($.trim($("#bean\\.lastWaterNum").numberbox("getValue")) == "") {
			 $.messager.alert("警告", "上月用水抄数不能为空!");
			 return false;
		 }
		 
		 if ($.trim($("#bean\\.thisWaterNum").numberbox("getValue")) == "") {
			 $.messager.alert("警告", "本月用水抄数不能为空!");
			 return false;
		 }
		 
		 if ($.trim($("#bean\\.waterPrice").numberbox("getValue")) == "") {
			 $.messager.alert("警告", "水费单价不能为空!");
			 return false;
		 }
		 
		 if ($.trim($("#bean\\.lastPowerNum").numberbox("getValue")) == "") {
			 $.messager.alert("警告", "上月用电抄数不能为空!");
			 return false;
		 }
		 
		 if ($.trim($("#bean\\.thisPowerNum").numberbox("getValue")) == "") {
			 $.messager.alert("警告", "本月用电抄数不能为空!");
			 return false;
		 }
		 
		 if ($.trim($("#bean\\.powerPrice").numberbox("getValue")) == "") {
			 $.messager.alert("警告", "电费单价不能为空!");
			 return false;
		 }
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 }); 
		return false;
	 } 
 }
 
 