 

function MyCalendarUtil(defaults){  
 	 var settings={ userId:'0',empId:'0' };
	 if(defaults) {
		$.extend(settings, defaults);
	 }
	 this.defaults=settings;  
	 this.dateRangeType="week";
	 this.weekNameArr=["星期一","星期二", "星期三","星期四","星期五","星期六","星期天"];
}
MyCalendarUtil.prototype={ 
   changeDateTitile:function(){
	   var calendarDate=$("#plan\\.planDate").my97("getValue"); 
	   var dayType=$("input[name='plan.dayType']:checked").val();
	   var jsonPara={script:"DateTimeUtil.formatDateTitle('"+calendarDate+"','"+dayType+"')"};
	   $.ts.Utils.execJavaScript(jsonPara,null,function(result){
		   var values=result.split(" "); 
		   $("input[name='plan.planWeek']").val(values[1]);
		   $("#plan\\.name").textbox("setValue",result);
	   });
   },
   changeLogDateTitile:function(){
	   var calendarDate=$("#log\\.summaryDate").my97("getValue"); 
	   var dayType=$("input[name='log.dayType']:checked").val();
	   var jsonPara={script:"DateTimeUtil.formatDateTitle('"+calendarDate+"','"+dayType+"')"};
	   $.ts.Utils.execJavaScript(jsonPara,null,function(result){
		   var values=result.split(" "); 
		   $("input[name='log.summaryWeek']").val(values[1]);
		   $("#log\\.name").textbox("setValue",result);
	   });
   },
   bindChangeDateRangeEvent:function(){
	    var that=this;
	    $("#weekPlanButtonGroup").click(function(){
			that.dateRangeType=$(this).attr("dateRangeType"); 
			$("#prevWeekBtn").linkbutton({text:"上一周"});
			$("#nextWeekBtn").linkbutton({text:"下一周"} );
			$("#testOrderFileListPanel").panel("open");
			that.getMyCalendarDatas(null,""); 
		});
		$("#monthPlanButtonGroup").click(function(){
			that.dateRangeType=$(this).attr("dateRangeType"); 
			$("#prevWeekBtn").linkbutton({text:"上个月"});
			$("#nextWeekBtn").linkbutton({text:"下个月"});
			$("#testOrderFileListPanel").panel("close");
			that.getMyCalendarDatas(null,""); 
		});
		
   },
   
   bindCalendarLogRecordEvent:function(objJq){
	   objJq.mouseover(function(){
			$(this).addClass("datagrid-row-over");
			$(this).find(".easyui-linkbutton").show();
		}).mouseout(function(){
			 $(this).removeClass("datagrid-row-over");
			$(this).find(".easyui-linkbutton").hide();
		}).click(function(){
			var id=$(this).attr("logId");
			var dayType=$(this).attr("dayType");   
			var operateType=$(this).attr("status");  
			var otherPer=false;
			var editTitle="修改";
			if($("#employeeId").val()!=$("#myCalendarEmployeeId").val()){ 
				otherPer=true; 
				editTitle="查看";
			}
			  if("0"==operateType){
				  var url=tsContextPath+"/main/calendarService/editMyCalendarPlan.do?id="+id+"&plan.dayType="+dayType;
				  if(otherPer){url+="&appUrl=LookupMyCalendarPlan";}
				  url+="&plan.employee.id="+$("#employeeId").val();
				  $.ts.EasyUI.modalDialog(  {  
							width : 600,  height : 350, title:editTitle+"日程安排", modal:false, method:"POST",  cache:false,
							collapsible: true,  maximizable: true,resizable:true,href:url   
				  } );
			  } else if("1"==operateType){
				  var url=tsContextPath+"/main/calendarService/editMyCalendarLog.do?id="+id+"&log.dayType="+dayType;
				  if(otherPer){url+="&appUrl=LookupMyCalendarLog";}
				  url+="&log.employee.id="+$("#employeeId").val();
				  $.ts.EasyUI.modalDialog(  {  
							width : 700,  height : 580, title:editTitle+"出差拜访", modal:false, method:"POST",  cache:false,
							collapsible: true,  maximizable: true,resizable:true,href:url   
				  } );
			  }  else if("2"==operateType){
				  var url=tsContextPath+"/main/calendarService/editMyCalendarSummary.do?id="+id;
				  if(otherPer){url+="&appUrl=LookupMyCalendarSummary";}
				  url+="&summary.employee.id="+$("#employeeId").val(); 
				  $.ts.EasyUI.modalDialog(  {  
							width : 800,  height: 530, title:editTitle+"本周工作总结", modal:false, method:"POST",  cache:false,
							collapsible: true, maximizable: true, resizable:true,href:url   
				  } );
			  }  else if("3"==operateType){
				  var url=tsContextPath+"/main/calendarService/editMyWeekPlan.do?id="+id;
				  
				  url+="&summary.employee.id="+$("#employeeId").val(); 
				  $.ts.EasyUI.modalDialog(  {  
							width : 800,  height: 530, title:editTitle+"本周行动计划", modal:false, method:"POST",  cache:false,
							collapsible: true, maximizable: true, resizable:true,href:url   
				  } );
			  } else if("4"==operateType){
				 
				  var url=tsContextPath+"/main/calendarService/editMyMonthSummary.do?id="+id;
				  if(otherPer){url+="&appUrl=LookupMyMonthSummary";}
				  url+="&summary.employee.id="+$("#employeeId").val(); 
				  $.ts.EasyUI.modalDialog(  {  
							width : 800,  height: 530, title:editTitle+"月工作总结", modal:false, method:"POST",  cache:false,
							collapsible: true, maximizable: true, resizable:true,href:url   
				  } );
			  }
			//modifyRecord
			event.stopPropagation();
		}); 
		objJq.find("a").click(function(){  
		    var that=this;
			var operate=$(that).attr("operate");
			function deleteCalendarRecordLog(){ 
				var p=$(that).parent();
				var id=p.attr("logId");
				var status=p.attr("status");
				var deleteStr="delete";
				if(status=="1")
					deleteStr="deleteLog";
			    else if(status=="2")
					deleteStr="deleteSummary";
				else if(status=="3")
					deleteStr="deleteSummary";
				else if(status=="4")
					deleteStr="deleteMonthSummary";
				var url=tsContextPath+"/main/calendarService/json/"+deleteStr+".do?id="+id+"&status="+status;
				$.ajax({
					type: "POST", 
					datatype:"json",
					url : url,
					success : function(result) { 
					    if(status=="2" || status=="3" || status=="4"){
							 p.parent().empty();
						} else {
							p.remove();
						}
						$.messager.show({
							title : '提示',
							msg :result.statememt,
							timeout:$.ts.constant.timeout,
							style:{
								right:'',
								bottom:''
							}
						});
					}
				}); 
			}
			if("execute"==operate){
				myCalendarUtil.executeCalendarLog(that,'1')
			} else {
				if($("#employeeId").val()!=$("#myCalendarEmployeeId").val()){
					$.messager.alert('warning',"不能删除别人的日程记录！");
					return false;
				}
				$.messager.confirm('确认操作', "您确认要删除该记录？", function(r) {
				   if(r){ 
					  new deleteCalendarRecordLog(); 
				   }
				});
			}
			event.stopPropagation(); 
		});
		
   },parseEasyUI:function(objJq){ 
	   for(var i=0;i<objJq.length;i++){
		   var jq=$(objJq[i]);
		   var operates=jq.attr("operate");
		   if("execute"==operates){
			  jq.linkbutton({iconCls:'icon-tip',plain:true });  
		   } else {
			 jq.linkbutton({iconCls:'icon-cancel',plain:true }); 
		   }
	   } 
	   objJq.hide();
   },
   executeCalendarLog:function(obj,operateType){
	   var id=$("input[name='plan.id']").val(); 
	   if(operateType=="1"){
		   id=$(obj).parent().attr("logId");
	   }  else {
		   $.ts.EasyUI.closeDialog(obj,'0');
	   }
	   if($("#employeeId").val()!=$("#myCalendarEmployeeId").val()){
			$.messager.alert('warning',"不能操作别人的日程记录！");
			return false;
		}
	  var url=tsContextPath+"/main/calendarService/editMyCalendarLog.do?planId="+id;  
	  $.ts.EasyUI.modalDialog(  {  
				width : 700,  height : 500, title:"执行日程完成情况", modal:false, method:"POST",  cache:false,
				collapsible: true,  maximizable: true,resizable:true,href:url   
	  } );
	  
   },
   submitPlanForm:function (obj){//点击按钮提交   
	     var appKey="calendarService";
	     var bodyId="#"+appKey+"Body";
		 var formId= appKey+"Form";  
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();   
		 var title=$("#plan\\.eventExplain").combobox("getText");
		 if($("input[name='plan.eventExplain']").length==0){
			 $("#"+formId).append('<input name="plan.eventExplain" type="hidden"    value="'+title+'"/> '); 
		 } 
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(formObj,data){ 
			 var mdate= $("#"+formId).find("[name='plan.planDate']").val( ); 
			 var dayType= $("#"+formId).find("[name='plan.dayType']").val( );  
			 var $td = $("td[mdate='" + mdate +"'][dayType='" + dayType + "'][status='0']");  
			  
			 if($td.length==0){
				 $.ts.EasyUI.closeDialog(obj);
				 return;
			 } 
			 var spanJq=$td.find("span[logId='"+formObj.id+"']"); 
			 if(spanJq.length>0){
				 spanJq.remove();
			 }  
			 $td.append("<span logId='" + formObj.id + "'  status='0'>" + title + 
			 "<a  class='easyui-linkbutton' operate='delete'/><a class='easyui-linkbutton' operate='execute'/></span><br/>");  
			 myCalendarUtil.bindCalendarLogRecordEvent($td.find("span[logId='"+formObj.id+"']"));  
			 myCalendarUtil.parseEasyUI($td.find(".easyui-linkbutton"));  
			 $.ts.EasyUI.closeDialog(obj,"0");
		 }); 
		return false;
	 } ,	
  openAddTravelFee:function(obj ){
	  var id=$("input[name='log.id']").val(); 
	  $.ts.EasyUI.closeDialog(obj);
	  var url=tsContextPath+"/main/travelFeeService/addTravelFee.do?bean.calendarLogId="+id; 
	  $.ts.EasyUI.modalDialog(  {  
				width : 900,  height : 530, title:"添加费用报销单", modal:false, method:"POST",  cache:false,
				collapsible: true,  maximizable: true,resizable:true,href:url   
	  } );
   },
   lookupAddTravelFee:function(obj ){
	  var id=$("input[name='log.id']").val(); 
	  $.ts.EasyUI.closeDialog(obj);
	  var url=tsContextPath+"/main/travelFeeService/editTravelFee.do?appUrl=LookupTravelFee&bean.calendarLogId="+id; 
	  $.ts.EasyUI.modalDialog(  {  
				width : 900,  height : 530, title:"查看费用报销单", modal:false, method:"POST",  cache:false,
				collapsible: true,  maximizable: true,resizable:true,href:url   
	  } );
   },
  addTravelFee:function(id){
	  if($("#employeeId").val()!=$("#myCalendarEmployeeId").val()){
			$.messager.alert('warning',"不能操作别人的费用报销单！");
			return false;
		}
	  var url=tsContextPath+"/main/travelFeeService/addTravelFee.do?bean.calendarLogId="+id; 
	  $.ts.EasyUI.modalDialog(  {  
				width : 900,  height : 530, title:"添加费用报销单", modal:false, method:"POST",  cache:false,
				collapsible: true,  maximizable: true,resizable:true,href:url   
	  } );
   },
  submitLogForm:function (obj,nextFlag){     
         if($.trim($("#log\\.summaryEvent").textbox('getValue'))=="" ){ 
			$.messager.alert('提示',$($.find("label[for='log.summaryEvent']")[0]).text()+"不能为空！"); 
			return false;
		}
	     var appKey="calendarService";
	     var bodyId="#"+appKey+"Body";
		 var formId= appKey+"Form";  
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();   
		 var title=$("#log\\.summaryEvent").textbox("getValue");
		 var that=this;
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(data,formObj){ 
			 var mdate= $("#"+formId).find("[name='log.summaryDate']").val( ); 
			 var dayType= $("#"+formId).find("[name='log.dayType']").val( ); 
			 var $td = $("td[mdate='" + mdate +"'][dayType='" + dayType + "'][status='1']"); 
			 if($td.length==0){
				 $.ts.EasyUI.closeDialog(obj);
				  if("1"==nextFlag)
				  that.addTravelFee(data.id);
				 return;
			 }
			 var spanJq=$td.find("span[logId='"+data.id+"']");
			 if(spanJq.length>0){
				 spanJq.remove();
			 }
			 $td.append("<span logId='" + data.id + "'  status='1'>" + title + 
			 "<a class='easyui-linkbutton' ></a></span><br/>");
			 myCalendarUtil.bindCalendarLogRecordEvent($td.find("span[logId='"+data.id+"']")); 
			 myCalendarUtil.parseEasyUI($td.find(".easyui-linkbutton"));  
			 $.ts.EasyUI.closeDialog(obj,"0");
			 if("1"==nextFlag)
			 	that.addTravelFee(data.id);
		 }); 
		return false;
	 } ,
   submitWeekPlanForm:function (obj){     
         if($.trim($("#summary\\.eventExplain").textbox('getValue'))=="" ){ 
			$.messager.alert('提示',$($.find("label[for='summary.eventExplain']")[0]).text()+"不能为空！"); 
			return false;
		}
	     var appKey="calendarService";
	     var bodyId="#"+appKey+"Body";
		 var formId= appKey+"Form";  
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();   
		 var title=$("#summary\\.eventExplain").combobox("getText");
		 var content= $("#summary\\.description").textbox("getText");
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(data,formObj){ 
			 var mdate= $("#"+formId).find("[name='summary.startDate']").val( );  
			 var $td = $("td[mdate='" + mdate +"'][status='3']");  
			 if($td.length==0){
				 $.ts.EasyUI.closeDialog(obj);
				 return;
			 }
			 $td.empty();
			 $td.append("<span logId='" + data.id + "'  status='3'>" + title + 
			 "<a   class='easyui-linkbutton' ></a></span><br/>");
			 $td.append(content);
			 myCalendarUtil.bindCalendarLogRecordEvent($td.find("span[logId='"+data.id+"']")); 
			 myCalendarUtil.parseEasyUI($td.find(".easyui-linkbutton"));  
			 $.ts.EasyUI.closeDialog(obj,"0");
		 }); 
		return false;
	 } ,
   submitSummaryForm:function (obj){     
	     var appKey="calendarService";
	     var bodyId="#"+appKey+"Body";
		 var formId= appKey+"Form";  
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();   
		 var title=$("#summary\\.eventExplain").combobox("getText");
		 var content=editor.document.getBody().getHtml(); //取得html文本
		 
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(data,formObj){ 
			 var mdate= $("#"+formId).find("[name='summary.startDate']").val( ); 
			 var dayType= $("#"+formId).find("[name='log.dayType']").val( ); 
			 var $td = $("td[mdate='" + mdate +"'][status='2']"); 
			 if($td.length==0){
				 $.ts.EasyUI.closeDialog(obj);
				 return;
			 }
			 $td.empty();
			 $td.append("<span logId='" + data.id + "'  status='2'>" + title + 
			 "<a   class='easyui-linkbutton' ></a></span>");
			 $td.append(content);
			 myCalendarUtil.bindCalendarLogRecordEvent($td.find("span[logId='"+data.id+"']")); 
			 myCalendarUtil.parseEasyUI($td.find(".easyui-linkbutton"));  
			 $.ts.EasyUI.closeDialog(obj,"0");
		 }); 
		return false;
	 } ,
  submitMonthSummaryForm:function (obj){     
	 var appKey="calendarService";
	 var bodyId="#"+appKey+"Body";
	 var formId= appKey+"Form";  
	 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();   
	 var title=$("#summary\\.eventExplain").combobox("getText");
	 var content=editor.document.getBody().getHtml(); //取得html文本
	 
	 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(data,formObj){ 
		 var mdate= $("#"+formId).find("[name='summary.startDate']").val( );  
		 var $td = $("td[mdate='" + mdate +"'][status='4']"); 
		 if($td.length==0){
			 $.ts.EasyUI.closeDialog(obj);
			 return;
		 }
		 $td.empty();
		 $td.append("<span logId='" + data.id + "'  status='4'>" + title + 
		 "<a   class='easyui-linkbutton' ></a></span>");
		 $td.append(content);
		 myCalendarUtil.bindCalendarLogRecordEvent($td.find("span[logId='"+data.id+"']")); 
		 myCalendarUtil.parseEasyUI($td.find(".easyui-linkbutton"));  
		 $.ts.EasyUI.closeDialog(obj,"0");
	 }); 
	return false;
 } ,
  choseEmployee:function(obj){   
	  var urlJson={urlType:"chooseRow",  moduleFileName:"EmployeeSerivceReport", openQueryResult:"1" }; 
	  var url=$.ts.Utils.toUrlParam(urlJson);  
	  var that=this;
	  var handler=$.ts.EasyUI.frameDialog( { 
			title:"选择员工",   
			href : url   
		  } , function(json){ 
				  try{  
					 if(json!=null){ 
						  $("#name").textbox('setValue',json.name);  
						  $("#employeeId").val(json.id);  
						  that.getMyCalendarDatas(null,"");
					 }
				  } catch(e){
					 $.messager.alert('Hint', e); 
				  }
			}
		);    
  },
  choseCust:function(obj){   
	  var urlJson={urlType:"chooseRow",  moduleFileName:"CustomerSerivceReport", openQueryResult:"1" }; 
	  var url=$.ts.Utils.toUrlParam(urlJson);  
	  var that=this;
	  var handler=$.ts.EasyUI.frameDialog( { 
			title:"选择客户",   
			href : url   
		  } , function(json){ 
				  try{  
					 if(json!=null){ 
						  $("#log\\.custName").textbox('setValue',json.custName); 
						  $("#log\\.contactPerson").textbox('setValue',json.headName);  
						  $("#log\\.contactPhone").textbox('setValue',json.headMobile);  
						  that.getMyCalendarDatas(null,"");
					 }
				  } catch(e){
					 $.messager.alert('Hint', e); 
				  }
			}
		);    
  },
  addRecord:function(obj,operateType){
	  var date=$(obj).attr("mdate"); 
	  var dayType=$(obj).attr("dayType");  
	  if($("#employeeId").val()!=$("#myCalendarEmployeeId").val()){
			$.messager.alert('warning',"不能添加别人的日程记录！");
			return false;
		}
	  if("0"==operateType){
		  var url=tsContextPath+"/main/calendarService/addMyCalendarPlan.do?plan.planDate="+date+"&plan.dayType="+dayType;
		  url+="&plan.employee.id="+$("#employeeId").val();
		  $.ts.EasyUI.modalDialog(  {  
					width : 600,  height : 350, title:"新增日程安排", modal:false, method:"POST",  cache:false,
					collapsible: true,  maximizable: true,resizable:true,href:url   
		  } );
	  } else if("1"==operateType){
		  var url=tsContextPath+"/main/calendarService/addMyCalendarLog.do?log.summaryDate="+date+"&log.dayType="+dayType;
		  url+="&log.employee.id="+$("#employeeId").val();
		  $.ts.EasyUI.modalDialog(  {  
					width : 700,  height : 580, title:"新增出差拜访", modal:false, method:"POST",  cache:false,
					collapsible: true,  maximizable: true,resizable:true,href:url   
		  } );
	  }  else if("2"==operateType){
		  var url=tsContextPath+"/main/calendarService/addMyCalendarSummary.do?summary.startDate="+date;
		  url+="&summary.employee.id="+$("#employeeId").val(); 
		  $.ts.EasyUI.modalDialog(  {  
					width : 800,  height: 530, title:"本周工作总结", modal:false, method:"POST",  cache:false,
					collapsible: true, maximizable: true, resizable:true,href:url   
		  } );
	  }  else if("3"==operateType){
		  var url=tsContextPath+"/main/calendarService/addMyWeekPlan.do?summary.startDate="+date;
		  url+="&summary.employee.id="+$("#employeeId").val(); 
		  $.ts.EasyUI.modalDialog(  {  
					width : 600,  height: 450, title:"本周工作计划", modal:false, method:"POST",  cache:false,
					collapsible: true, maximizable: true, resizable:true,href:url   
		  } );
	  } else if("4"==operateType){
		  var url=tsContextPath+"/main/calendarService/addMyMonthSummary.do?summary.startDate="+date;
		  url+="&summary.employee.id="+$("#employeeId").val(); 
		  $.ts.EasyUI.modalDialog(  {  
					width : 800,  height: 530, title:"月工作总结", modal:false, method:"POST",  cache:false,
					collapsible: true, maximizable: true, resizable:true,href:url   
		  } );
	  }
  }, 
  bindCalendarTdClickEvent:function(jq){
	  var that=this;
	  jq.dblclick(function(){
		 var status=$(this).attr("status");
		 that.addRecord(this,status);
	 });
  },
  setCalendarContainerEvents:function(){
	    this.bindCalendarLogRecordEvent($("#myCalendarPlanTable").find("span[logId]"));
	    this.bindCalendarLogRecordEvent($("#myCalendarSummaryTable").find("span[logId]"));  
		this.bindCalendarTdClickEvent($("#myCalendarPlanTable").find("td[mdate]"));
		this.bindCalendarTdClickEvent($("#myCalendarSummaryTable").find("td[mdate]")); 
  },
  setMonthTableContainerEvents:function(){
	    this.bindCalendarLogRecordEvent($("#myCalendarPlanTable").find("span[logId]")); 
		this.bindCalendarTdClickEvent($("#myCalendarPlanTable").find("td[mdate]"));   
  },
  getMyMonthCalendarDatas:function (obj,weekType){
	 var employeeId=$("#employeeId").val();
	 var calendarDate=$("#calendarDate").my97("getValue"); 
	 var that=this;
	 $.ajax({
		type: "POST",  
		async:true, 
		data:"employeeId="+employeeId+"&calendarDate="+calendarDate+"&weekOperateType="+weekType,
		datatype: "json",
		url: tsContextPath+"/main/calendarService/json/getMonthList.do" ,
		success: function(data, textStatus) { 
			var head = "<tr class='datagrid-header-row'><th>  </th>"; 
			$("#calendarDate").my97("setValue",data.currentDate);
			var titleList=that.weekNameArr;
			for(var i=0;i<titleList.length;i++){
				var bean=titleList[i];
				head += "<th >"+bean + "</th>";
			}
			head += "</tr>";
			var weekContent= "";
			var weekList=data.logList;
			for(var i=0;i<weekList.length;i++){
				var bean=weekList[i]; 
				weekContent+= "<tr><th height='70'>"+bean.name+"</th>"; 
				var dayList=bean.logList;
				for(var n=0;n<dayList.length;n++){
					var logObj=dayList[n]; 
					weekContent += "<td   mdate='" + logObj.currentDate  + "' status='5'   valign='top'>"; 
					weekContent += "<div  class='fc-day-number'>"+logObj.day+"</div>"; 
					var logList=logObj.logList;
					if($.ts.Utils.isEmpty(logList)==false)
					for(var j=0;j<logList.length;j++){
						var logBean=logList[j]; 
						weekContent += "<span logId='" + logObj.id + "'  status='5'> " + logBean.summaryEvent +
					                    "</span><br/>"; 
					}
					weekContent += "</td>";
				}
				weekContent += "</tr>";
			} 
			var summary="<tr><th height='100'>月总结</th>";
			var summaryObj=data.summary;
			summary += "<td  mdate='" + summaryObj.startDate  + "' status='4' valign='top'  colspan='7'>";
			summary += "<span logId='" + summaryObj.id + "'  status='4'> " + summaryObj.eventExplain +
					" <a  class='easyui-linkbutton'></a> </span>";
			if($.ts.Utils.isEmpty(summaryObj.fileName)==false){
				summary+='&nbsp;&nbsp;&nbsp;&nbsp;上传附件：'+summaryObj.fileName;
				summary+='<a href="'+tsContextPath+'/core/downloadFileService/stream/downloadFile.do';
                summary+='?number='+summaryObj.fileName+'&name='+summaryObj.filePath+'/'+summaryObj.saveToFileName;
				summary+='onClick="">下载</a>';
			}
			summary += summaryObj.description;
			summary += "</td></tr>";
			$("#myCalendarPlanTable").html(head+weekContent+summary);
			that.parseEasyUI($("#myCalendarPlanTable").find(".easyui-linkbutton"));  
			that.setMonthTableContainerEvents();
		}
	 });	
  },
  getMyCalendarDatas:function(obj,weekType){ 
	  if(this.dateRangeType=="week")
	  	this.getMyWeekCalendarDatas(obj,weekType);
	  else 
	  	this.getMyMonthCalendarDatas(obj,weekType);
  },
  getMyWeekCalendarDatas:function (obj,weekType){
	 var employeeId=$("#employeeId").val();
	 var calendarDate=$("#calendarDate").my97("getValue"); 
	 $.ajax({
		type: "POST",  
		async:true, 
		data:"employeeId="+employeeId+"&calendarDate="+calendarDate+"&weekOperateType="+weekType,
		datatype: "json",
		url: tsContextPath+"/main/calendarService/json/getWeekList.do" ,
		success: function(data, textStatus) { 
			var head = "<tr class='datagrid-header-row'><th>  </th>"; 
			$("#calendarDate").my97("setValue",data.currentDate);
			var titleList=data.weekTitleList;
			for(var i=0;i<titleList.length;i++){
				var bean=titleList[i];
				if (  bean.auditedBy!="")
					head += "<th class='"+bean.auditedBy+"'>";
				else
					head += "<th >"; 
				 head +=bean.name  + "</th>";
			}
			head += "</tr>";
			var am= "<tr><th height='50'>上午</th>";
			var planAmList=data.planAmList;
			for(var i=0;i<planAmList.length;i++){
				var bean=planAmList[i];
				var titleObj=titleList[i]; 
				am += "<td   mdate='" + titleObj.createDate  + "' status='0' dayType='am' valign='top'>"; 
				for(var n=0;n<bean.length;n++){
					var logObj=bean[n];
					var fontStyle="";
					if($.ts.Utils.isEmpty(logObj.logId)==false && logObj.logId!="0"){
						fontStyle="style='font-weight:bold'";
					}
					am += "<span logId='" + logObj.id + "' status='0' "+fontStyle+"  > " + logObj.eventExplain + 
					"<a  class='easyui-linkbutton'  operate='delete'></a>"+
					"<a  class='easyui-linkbutton' operate='execute'></a></span><br/>";
				}
				am += "</td>";
			}
			am += "</tr>";
			var pm= "<tr><th height='50'>下午</th>";
			var planPmList=data.planPmList;   
			for(var i=0;i<planPmList.length;i++){ 
				var bean=planPmList[i];   
				var titleObj=titleList[i];
				pm += "<td    mdate='" + titleObj.createDate  + "' status='0' dayType='pm' valign='top'>";  
				 
				for(var n=0;n<bean.length;n++){
					var logObj=bean[n];
					var fontStyle="";
					if($.ts.Utils.isEmpty(logObj.logId)==false && logObj.logId!="0"){
						fontStyle="style='font-weight:bold'";
					}
					pm += "<span logId='" + logObj.id + "'  "+fontStyle+" status='0'  > " + logObj.eventExplain +
					"<a  class='easyui-linkbutton'  operate='delete'></a>"+
					"<a  class='easyui-linkbutton' operate='execute'  ></a></span><br/>";
				}
				pm += "</td>";
			} 
			pm += "</tr>";
			
			var weekPlan="<tr><th height='100'>计划</th>";
			var weekPlanObj=data.weekPlan;
			weekPlan += "<td  mdate='" + weekPlanObj.startDate  + "' status='3' valign='top'  colspan='7'>";
			weekPlan += "<span logId='" + weekPlanObj.id + "'  status='3'> " + weekPlanObj.eventExplain +
					" <a  class='easyui-linkbutton'></a> </span><br/>"; 
			weekPlan += weekPlanObj.description;
			weekPlan += "</td></tr>"; 
			$("#myCalendarPlanTable").html(head+am+pm+weekPlan);
			myCalendarUtil.parseEasyUI($("#myCalendarPlanTable").find(".easyui-linkbutton")); 
			am= "<tr><th height='50'>上午</th>";
			var logAmList=data.logAmList;
			for(var i=0;i<logAmList.length;i++){
				var bean=logAmList[i];
				var titleObj=titleList[i];
				am += "<td    mdate='" + titleObj.createDate  + "' status='1' dayType='am' valign='top'>";
				for(var n=0;n<bean.length;n++){
					var logObj=bean[n];
					am += "<span logId='" + logObj.id + "'  status='1'> " + logObj.summaryEvent + 
					" <a  class='easyui-linkbutton' ></a></span><br/>";
				}
				am += "</td>";
			}
			am += "</tr>";
			pm= "<tr><th height='50'>下午</th>";
			var logPmList=data.logPmList;
			for(var i=0;i<logPmList.length;i++){
				var bean=logPmList[i];
				var titleObj=titleList[i];
				pm += "<td  mdate='" + titleObj.createDate  + "' status='1' dayType='pm' valign='top'>";
				for(var n=0;n<bean.length;n++){
					var logObj=bean[n];
					pm += "<span logId='" + logObj.id + "'  status='1'> " + logObj.summaryEvent +
					" <a  class='easyui-linkbutton'></a></span><br/>";
				}
				pm += "</td>";
			}
			pm += "</tr>";
			var summary="<tr><th height='100'>总结</th>";
			var summaryObj=data.summary;
			summary += "<td  mdate='" + summaryObj.startDate  + "' status='2' valign='top'  colspan='7'>";
			summary += "<span logId='" + summaryObj.id + "'  status='2'> " + summaryObj.eventExplain +
					" <a  class='easyui-linkbutton'></a> </span>";
			if($.ts.Utils.isEmpty(summaryObj.fileName)==false){
				summary+='&nbsp;&nbsp;&nbsp;&nbsp;上传附件：'+summaryObj.fileName;
				summary+='<a href="'+tsContextPath+'/core/downloadFileService/stream/downloadFile.do';
                summary+='?number='+summaryObj.fileName+'&name='+summaryObj.filePath+'/'+summaryObj.saveToFileName;
				summary+='onClick="">下载</a>';
			}
			summary += summaryObj.description;
			summary += "</td></tr>";
			$("#myCalendarSummaryTable").html(head+am+pm+summary);
			myCalendarUtil.parseEasyUI($("#myCalendarSummaryTable").find(".easyui-linkbutton"));  
			myCalendarUtil.setCalendarContainerEvents();
		}
	 });	
	
 	 return false; 
  },
  changePlanId:function(){
	  var that=this;
	 $("#log\\.planId").combobox({    
		 onSelect:function(rec){  
		      var id=rec.value;     
			  if($.ts.Utils.isEmpty(planList)==false){
				  for(var i=0;i<planList.length;i++){
					  var bean=planList[i];
					  if(id==bean.id){
						  $("#log\\.custName").textbox("setValue",bean.custName);
						  $("#log\\.summaryEvent").textbox("setValue",bean.eventExplain);
						  $("#log\\.place").textbox("setValue",bean.place);
					  }
				  }
			  } 
			  return false;
		 }  
	 });
  },
 initPage:function(){ 
	 this.getMyCalendarDatas(null,""); 
	 this.bindChangeDateRangeEvent();
 } 
	 
};
 
  
  
 