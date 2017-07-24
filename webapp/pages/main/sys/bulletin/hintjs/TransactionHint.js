 

function TransactionHintUtil(defaults){  
 	 var settings={ userId:'0',empId:'0',userStatus:"",deptIds:"0"};
	 if(defaults) {
		$.extend(settings, defaults);
	 }
	 this.defaults=settings; 
	 this.ltBriefing={intervalId:null,currentRow:0,data:null,speed:1000*2};
	 this.calendarClickEvent();
}
TransactionHintUtil.prototype={ 	
    calendarClickEvent:function(){
		$('#fullCalendarButton').click(function(){
			   
			   window.parent.mainScript.refreshTab("日历管理","/main/calendarService/showMyCalendar.do"); 
			}
		);
	},
    appendCandarButton:function(){
		var header=$("div[region='east']").parent().find(".panel-title").eq(0); 
		//header.html(text); 
		var sourceToolbar=$("#fullCalendarButton");
		sourceToolbar.css("position","absolute");  
		sourceToolbar.css("top",  "-3px");
		sourceToolbar.appendTo(header); 
		sourceToolbar.linkbutton( );
		//$("#fullCalendarButton").parent().parse();
	},
    openMoreBulletinTab: function () { 	   
	       var url='/core/reportResolver/initPage.do?moduleFileName=BulletinRemindServiceReport&openQueryResult=1';  
	      var toolbarObj = window.parent.mainScript;
		  if(toolbarObj==null)
		  	toolbarObj=window.parent.parent.mainScript; 
		  toolbarObj.refreshTab("查阅信息",url);
		  return true;
		   
    },
	openMoreAuditReportTab:function ( ) { 
	      var url='/core/reportResolver/initPage.do?moduleFileName=WorkFlowAuditServiceReport&openQueryResult=1'; 
	      var toolbarObj = window.parent.mainScript;
		  if(toolbarObj==null)
		  	toolbarObj=window.parent.parent.mainScript; 
		  toolbarObj.refreshTab("待办事件",url);
		  return true;
		   
    },
    replyWindow: function (path,id){
     	document.location.href=tsContextPath+'/main/showBulletinService/addBulletinReply.do?id='+id;
	    return false;
    },
	openWindow:function (obj,dialogTarget){ 
	    var id=$(obj).attr("businessKey");
	    var closeCallbackEvent=function(){
		 	 var t=$("#ltBulletinTable"); 
			 var tr=t.find("#bulletin_"+id);
			 tr.find("img[src*='90.gif']").remove();
	   };
	  var url=tsContextPath+"/main/showBulletinService/updateBulletinView.do?id="+id; 
	  var paraJson={   width : 800, height : $(document).height()-5, modal:true,
		    method:"POST", title:"查阅新闻公告", cache:false, collapsible: true, maximizable: true, 
		    maximized:true, resizable:true,  href : url   
	   } ;
	  if(dialogTarget==1)
	    paraJson.targetName="parent";
	  $.ts.EasyUI.modalDialog( paraJson ,closeCallbackEvent );    
 	  return false; 
  },
     initPage:function(){  
	     this.refreshBulletinTable("ltBulletin","1","新闻公告"); 
		 this.refreshMyTaskTable("ltNotify","3","待办事件");
		 this.appendCandarButton();
	 },
	 clearTableRows:function(tableName){
		 var trs = $("#"+tableName+" tr"); 
		 for(var i=trs.length-1;i>=0;i--){ 
			trs.eq(i).remove().fadeOut("slow");;
		 }
	 },
	 insertEmptyRows:function(tableName,rowCount){
		 var t=$("#"+tableName);
		 for(var i=0;i<rowCount;i++){
			 var tr = $('<tr></tr>').appendTo(t);
			 var content='<td  colspan="3"> &nbsp;&nbsp;</td>';
			 $(content).appendTo(tr);
			 var tr2 = $('<tr></tr>').appendTo(t);
			$('<td></td>').appendTo(tr2);
			$('<td colspan="3"    height="1"></td>').appendTo(tr2);
			 
		 }
	 },
	 refreshBulletinTable:function(tableName,bulletinKinds,kindName){ 
		 $.ajax({
		   type: "POST",  
		   async:true, 
		   datatype: "json",
		   url: tsContextPath+"/main/showBulletinService/json/getBulletins.do?records=8&timeStamp="+new Date().getTime(), 
		   success:function(result,textStatus){
			    var zNodes=result;
				transactionHintUtil.clearTableRows(tableName+"Table");
				//transactionHintUtil.insertEmptyRows(tableName+"Table",8);
				var haveData=false;
				window.parent.mainScript.opts.bulletins=0;
				if(zNodes!=null ){
					var t=$("#"+tableName+"Table"); 
					var rows=zNodes.rows;
					if(zNodes.noReadCount=="0"){
						$("#"+tableName+"NoRecordCount").text("");  
					} 
					if(zNodes.noReadCount!="0"){
						$("#"+tableName+"NoRecordCount").text(zNodes.noReadCount+"条未读");  
					}
					window.parent.mainScript.opts.bulletins=zNodes.noReadCount;  
					if(rows!=null && typeof(rows.length)!="undefined" && rows.length>0){
						transactionHintUtil.insertBulletinTableRows(rows,t,bulletinKinds);
						haveData=true;
					}
				} 
				window.parent.mainScript.displayRemindMsgRecords();
				if(haveData==false){
					var tr = $('<tr></tr>').appendTo(t);
					$('<td align="center">暂时没'+kindName+'，谢谢！</td>').appendTo(tr);
				}
		   } 
		});   
	 },
	 insertBulletinTableRows:function(rows,t,bulletinKinds){
		 if(rows!=null && typeof(rows.length)!="undefined"){
			for(var i=rows.length-1;i>=0;i--){
				transactionHintUtil.insertBulletinTableRow(rows[i],t,bulletinKinds);
			}
		 }
	 },
	 insertBulletinTableRow:function(obj,t,bulletinKinds){  
	        if($.ts.Utils.isEmpty(obj.records)==false && "-1"==obj.records){
				var deleteRow=$(t).find("#bulletin_"+obj.id);
				var nextRow=deleteRow.next();
				deleteRow.remove();
				nextRow.remove();
				return;
			} 
			var tr=null; 
			if($("tr:eq(0)",t).length==0){
				tr = $('<tr  id="bulletin_'+obj.id+'"  ></tr>').appendTo(t); 
			} else {
				tr=$('<tr  id="bulletin_'+obj.id+'"  ></tr>').insertBefore($("tr:eq(0)",t)); 
			}  
			var fontColor="";
			if("1"==obj.emergentLevel && obj.fontColor!="1"){   
				fontColor="color:#333";
				obj.image="0";
			}
			if(obj.fontColor=="1") {
				fontColor="color:#333";
			}
			var imageTd= $('<td width="16"><div align="right" style="height:2px" ><img src="'+tsContextPath+'/resource/image/icon/154.gif" >&nbsp;&nbsp;</div></td>').appendTo(tr);
			var content='<td width="70%"    >';
			content+='<a href="javascript:void(0)" businessKey="'+obj.id+'" onClick="return transactionHintUtil.openWindow(this,0);"'; 
			content+=' style="'+fontColor+'">'+obj.name+'</a>'; 
			if(obj.image=="1"){
				content+='<img src="'+tsContextPath+'/resource/image/icon/90.gif" >';
			}
			content+='</td>';
			$(content).appendTo(tr);
			var bulletinType='<td width="10%" style="'+fontColor+'">'+obj.topicKindName+'</td>';
			$(bulletinType).appendTo(tr);
			
			var replay="";
			if(obj.needReply=="1"){
				replay='&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onClick="return transactionHintUtil.replyWindow(\''+obj.id+');" ';
				replay+='style="'+fontColor+'">'+obj.replyTimes+'次</a>';
			}
			var createDate='<td style="'+fontColor+'">'+obj.createDate+replay+'</td>'; 
			$(createDate).appendTo(tr); 
			var tr2 = $('<tr></tr>').insertAfter(tr);
			$('<td></td>').appendTo(tr2);
			$('<td colspan="3" background="'+tsContextPath+'/resource/image/icon/xian.gif" style="background-repeat:repeat-x"  height="1"></td>').appendTo(tr2);
			if("1"==obj.emergentLevel && obj.fontColor!="1"){   
			       transactionHintUtil.openWindow(tr.find("a")[0],1);  
			} 
	 },
	 refreshMyTaskTable:function(tableName,bulletinKinds,kindName){ 
	     var urlJson={ moduleFileName:"WorkFlowAuditServiceReport",urlType:"queryReportResult",pageNumber:1,pageSize:8};   
		 var url=$.ts.Utils.toUrlParam(urlJson);  
		 var tableName="ltNotify";
		 var that =this;
		 $.ajax( {
			url: url , 
			datatype: "json",
			type: "POST",  
			async:true,
			success:function(result,textStatus){ 
			    that.clearTableRows(tableName+"Table");
				var reportDatagrids= result[0];
				var resultJson = reportDatagrids.datagrid;   
				var rows=resultJson.rows; 
				var t=$("#"+tableName+"Table"); 
				var haveData=false;
				if(rows!=null && typeof(rows.length)!="undefined" && rows.length>0){
					that.insertMyTaskTableRows(rows,t);
					haveData=true;
				} 
				var records=0;
				if(haveData){
					$("#"+tableName+"NoRecordCount").text(resultJson.total+"条"+kindName);   
					records=resultJson.total;
				} else {
					var tr = $('<tr></tr>').appendTo(t);
					$('<td align="center">暂时没'+kindName+'，谢谢！</td>').appendTo(tr); 
				}
				window.parent.mainScript.opts.tasks=resultJson.total;
				window.parent.mainScript.displayRemindMsgRecords(); 
			 }, 
			error:function(XMLHttpRequest, textStatus, errorThrown){
				var str=XmlHttpRequest.responseText; 
				 if($.ts.Utils.isEmpty(str)){
					  str=XmlHttpRequest.responseXML;
				 }
				 str=str+"<hr/>"+textStatus;
				 $.ts.EasyUI.showContentDialog(str); 
			}
		} );  
		 
	 },
	  insertMyTaskTableRows:function(rows,t,bulletinKinds){
		   if(rows!=null && typeof(rows.length)!="undefined"){
			for(var i=0;i<rows.length;i++){
				var obj=rows[i];
				transactionHintUtil.insertMyTaskTableRow(obj,t,bulletinKinds);
			}
		   }
	  },
	 insertMyTaskTableRow:function(obj,t,bulletinKinds){ 
			var tr = $('<tr   id="task_'+obj.businessKey+'"></tr>').appendTo(t); 
			var fontColor="color:#333"; 
			var imageTd= $('<td width="16"><div align="right" style="height:2px" ><img src="'+tsContextPath+'/resource/image/icon/154.gif" >&nbsp;&nbsp;</div></td>').appendTo(tr);
			var content='<td width="60%"    >';
			content+='<a href="javascript:void(0)" onClick="return transactionHintUtil.openTaskWindow(this,0);"'; 
			content+=' businessKey="'+obj.businessKey+'" url="'+obj.url+'" processInstanceId="'+obj.processInstanceId+'"' ;
			content+=' taskRowType="'+obj.taskRowType+'" taskId="'+obj.taskId+'" style="'+fontColor+'" >'+obj.title+'</a>'; 
			content+='<img src="'+tsContextPath+'/resource/image/icon/90.gif" >'; 
			content+='</td>';
			$(content).appendTo(tr);
			var bulletinType='<td width="20%" style="'+fontColor+'">'+obj.processName+'</td>';
			$(bulletinType).appendTo(tr); 
			var createDate='<td style="'+fontColor+'">'+obj.createDate.substring(0,10)+ '</td>';  
			$(createDate).appendTo(tr);
			var tr2 = $('<tr></tr>').appendTo(t);
			$('<td></td>').appendTo(tr2);
			$('<td colspan="3" background="'+tsContextPath+'/resource/image/icon/xian.gif" style="background-repeat:repeat-x"  height="1"></td>').appendTo(tr2);
			if("dialog"==obj.remindMode  ){   
			   transactionHintUtil.openTaskWindow($.find("a",tr)[0],1); 
			}  
	 },
	 openTaskWindow:function (obj,dialogTarget){ 
	    var url=$(obj).attr("url"); 
		var processInstanceId=$(obj).attr("processInstanceId");
		var businessKey=$(obj).attr("businessKey");
		var taskId=$(obj).attr("taskId");
		var taskRowType=$(obj).attr("taskRowType");
		var title=$(obj).text();
		if($.ts.Utils.isEmpty(url)){
			url=tsContextPath+"/core/workFlowAuditService/addWorkFlowAudit.do?operateAuthCheckFlag=0&processInstanceId="+processInstanceId+"&taskId="+taskId+"&taskRowType="+taskRowType;
		} else {
			url=tsContextPath+url;
			if(url.indexOf("?")<0) {
			  url=url+"?id="+businessKey;
			} else {
			  url=url+"&id="+businessKey;
			}
			url=url+"&operateAuthCheckFlag=0&processInstanceId="+processInstanceId+"&taskId="+taskId+"&taskRowType="+taskRowType;
		}   
		var closeCallbackEvent=function(){
		 	 var t=$("#ltNotifyTable"); 
			 var tr=t.find("#task_"+businessKey);
			 var nextRow=tr.next();
			 nextRow.remove();
			 tr.remove();
	   };
		var paraJson= {   width : 800,  height : $(document).height()-5, modal:true, method:"POST", 
			title:title, cache:false, collapsible: true, maximizable: true, maximized:true, resizable:true, 
			href : url   
		};
	    if(dialogTarget==1)
	      paraJson.targetName="parent";  
	  $.ts.EasyUI.modalDialog(  paraJson,closeCallbackEvent );    
 	  return false;
   
  },
	 updateBulletinStatus:function(id){ 
		if(this.ltBriefing.data==null) return;
		var notReadCount=0;
		for(var i=0;i<this.ltBriefing.data.length;i++){
			if(this.ltBriefing.data[i].id==id){
				this.ltBriefing.data[i].fontColor="1";
				this.ltBriefing.data[i].image="0"; 
			} else {
				if(this.ltBriefing.data[i].fontColor!="1"){
					notReadCount++;
				}
			}
		}
		if(notReadCount>0)
			$("#ltBriefingNoRecordCount").text(notReadCount+"条未读");
		else
			$("#ltBriefingNoRecordCount").text("");
	 },
	    
     getDateRanges:function(){
		  var tds=$("#fullCalendarDiv").find('td[abbr]');   
		  var startDate=transactionHintUtil.formatCalendarDate($(tds[0]).attr('abbr'));
		  var  endDate=transactionHintUtil.formatCalendarDate($(tds[tds.length-1]).attr('abbr'));
		   
		  var days=new Array();
		  days[0]=startDate;
		  days[1]=endDate;
		  return days;
	 },
	 setEmployeeCalendarEvents:function(){
		 var days=this.getDateRanges();
		 var sql=" select a.id,a.eventExplain,a.date  from  " ;
		 sql=sql+" (select id,eventExplain,date,hdrId from OM_WEEKLY_PLAN ";		
		 sql=sql+"  union all select  id,eventExplain,date,hdrId  from OM_WEEKLY_DID where isnull(planid,0)<=0 ) as a ";			
		 sql=sql+"  , OM_WEEKLY_SUMMARY as b ";
		 sql=sql+" where b.id=a.hdrId and b.employeeId="+this.defaults.empId;
		 sql=sql+" and a.date between '"+days[0]+"' and '"+days[1]+"'" ; 
		 sql+=" order by a.date  ";   
		 var dateEvents=new Array();
	     var bean={methodName:"executeSqlToRecordMap",  sql:sql,  transfersDataFormat:"json",  pageLength:0,queryPosition:0}; 
		  $.ajax({
		   type: "POST", 
		   datatype: "json",
		   async:true,
		   data: JSON.stringify(bean),
		   url: tsContextPath+"/jcf/dbEngineServlet?timeStamp="+new Date().getTime(), 
		   complete: function(msg){
			    var zNodes=jQuery.parseJSON(msg.responseText) ;
				if(zNodes!=null && zNodes.length>0){
					for(var i=0;i<zNodes.length;i++){
						var bean=zNodes[i];
						var existsRecord=false;
						for(var n=0;n<dateEvents.length;n++){
							if(dateEvents[n][0]==bean.date){
								dateEvents[n][dateEvents[n].length]=bean.eventExplain;
								existsRecord=true;
								break;
							}
						}
						if(existsRecord==false){
							dateEvents[dateEvents.length]=new Array();
							dateEvents[dateEvents.length-1][0]=bean.date;
							dateEvents[dateEvents.length-1][1]=bean.eventExplain;
						}
					}
					transactionHintUtil.setCalendarDayEvents(dateEvents);
				}  
		   } 
		});  
	 },
	 formatCalendarDate:function(str){
		 var parts =str.split(',');
		 var year=parts[0];
		 var month=parts[1];
		 if(typeof(month)=="undefined"){
			 return;
		 }
		 if(month.length==1)
			 month="0"+month;
		 var date=parts[2];
		 if(date.length==1)
			 date="0"+date;
		var dagteStr=year+"-"+month+"-"+date;
		return dagteStr;
	 },
     setCalendarDayEvents:function(dateEvents){ 
	 	  var tds=$("#fullCalendarDiv").find('td[abbr]');
		 // alert(tds.length );
		  $(tds).each(function(i){
				var parts = $(this).attr('abbr').split(','); 
				var currentDate=transactionHintUtil.formatCalendarDate($(this).attr('abbr'));
				var inf = $(this).data('info');
				//if(inf!=null && inf.lunarFestival) 
				//$(this).find("#holidayContainer").text( inf.lunarFestival );
				//if(inf!=null && inf.solarFestival)
				//$(this).find("#holidayContainer").text( inf.solarFestival);
				for(var k=0;k<dateEvents.length;k++){
					if(dateEvents[k][0]==currentDate){
						var events=dateEvents[k].length-1;
						$(this).find("#eventContainer").text("("+events+")");
						var hintStr="";
						for(var j=1;j<=events;j++){
							if(hintStr=="")
								hintStr=j+":"+dateEvents[k][j];
							else
								hintStr=hintStr+"<br/>"+j+":"+dateEvents[k][j];
						}
						 
						$(this).attr("hintStr",hintStr);
					}
				} 
		 });
	 },
	 initCalendarEvent:function(){
		$('#fullCalendarDiv').fullCalendar({ 
			onChange:function(obj){
				transactionHintUtil.setEmployeeCalendarEvents();
				 
			},
			onSelect: function (date, target) {
				window.location.href = tsContextPath + "/om/showWeeklyCalendar.do?mdate=" + date.format("yyyy-MM-dd");
			}
		}); 
	 }
};

  function getDateCell(){
	  var tds=$("#fullCalendarDiv").find('td[abbr]');
	  alert(tds.length );
	  $(tds).each(function(i){
	        //var parts = $(this).attr('abbr').split(',');
			if(i==20){
				$(this).find("#eventContainer").text("(1唐)");
				alert($(this).attr('abbr'));
			}
			
	 });
	/**
	  for(var i=0;i<tds.length;i++){
            var parts = $(this).attr('abbr').split(',');
			alert($(tds[i]).attr('abbr'));
	  }**/
  }
  
 