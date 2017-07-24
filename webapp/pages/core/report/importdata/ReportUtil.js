function ReportScript( ){ 
	 this.contextPath=""; 
	 this.appKey=""; 
	 this.opts=null;
	 this.clearSelectRowsDataFlag=0;
	 this.reportOptions=null;
	  
 }
ReportScript.prototype={ 
	setOption:function(option){
		this.opts= $.extend({},option); 
		this.contextPath=this.opts.contextPath;  
	},
	initPage:function (option){ 
	     this.setOption(option);
		 
		if($("#operatePattern").val()=="chooseRow" || $("#operatePattern").val()=="chooseRows"){   
			$("#reportBackbtn").click(function(e) { 
				$.ts.EasyUI.closeDialog(document.getElementById("queryDatasBtn"),"0"); 
			}) 
		 } else {
			 $("#reportBackbtn").click(function(e) { 
			   if(parent!=null && typeof(parent.mainScript)!="undefined"){
				   parent.mainScript.closeCurrentTab();
			   }
				 
			}) 
		 }
	},  
	formatCustomProperty:function (obj){
		if(obj!=null  && typeof(obj.customProperty)!="undefined" && obj.customProperty!=null){
			var cp=obj.customProperty; 
			$.extend(obj,cp); 
			obj.customProperty="undefined";  
		}   
	}, 
	 
	 refreshDatagridHeadStyle:function (table){
		var panel = table.datagrid('getPanel');   
		var tr = panel.find('div.datagrid-header tr');  //div.datagrid-body tr  
		tr.each(function(){   
			var tds = $(this).children('td'); 
			for(var i=0;i<tds.length;i++){
				var td=$(tds[i]);  
				td.children("div").css({   
					"text-align": "center",
					"font-weight":"bold"
				});   
			}   
		});   
	},
	setTableSelectPage:function (table,pagination){ 
	    var self=this;
	    $.ts.EasyUI.formatDatagridParam(pagination);    
		pagination.onSelectPage=function(pPageIndex, pPageSize) {   
			var gridOpts =null;
			if("treegrid"==table.attr("reportType")){
				gridOpts =table.treegrid('options');   
			} else {
				gridOpts =table.datagrid('options');   
			}
			gridOpts.pageNumber = pPageIndex;   
			gridOpts.pageSize = pPageSize;     
			self.queryReportResult(table);   
		} ;
		if("treegrid"==table.attr("reportType")){
			table.treegrid('getPager').pagination(pagination);  
		} else {
			table.datagrid('getPager').pagination(pagination);  
		}
		if(pagination.extendHtml!=null && pagination.extendHtml!=""){
			var td=$(pagination.extendHtml);
			var tr=null;
			if("treegrid"==table.attr("reportType")){
				tr=table.treegrid('getPager').find("tr");
			} else {
				tr=table.datagrid('getPager').find("tr");
			}
			td.appendTo(tr); 
		}  
	},
	validateSameRow:function(row,targetRows){
		for(var i=0;i<targetRows.length;i++){
			var bean=targetRows[i];
			var sameRow=true;
			for (var key in bean){  
				if(row[key]!=bean[key]) {
					sameRow=false;
					break;
				}
			}
			if(sameRow) return i;
		}
		return -1;
	},
	
	queryReportResult:function (table){  
	    var paras=$("#reportEngineForm").serialize();  
	   $('#importDataContentPanel').panel({    
		href:tsContextPath+'/core/importDataCoreService/queryImportResult.do?operateAuthCheckFlag=0&timeStamp='+(new Date()).getTime()+"&"+paras,    
		title:"",
		//queryParams:paras,
		method:"post",
		cache:false,
		onLoad:function(){    
			//alert('loaded successfully');    
		}    
	}); 
	   //document.forms[0].target="importDataContentPanel";  
	   //document.forms[0].submit();
	    
		return true;
	},
	 
	promptMessageDailog:function(title,msg){
		 $.messager.show({
			title:title,
			msg:msg,
			timeout:$.ts.constant.timeout,
			style:{
				right:'',
				bottom:''
			}
		 }); 
	}, 
	choseAllComp:function ( checkObj){ 
		   var obj=null;
		   if (document.getElementsByName("selectImportRowParam").length == null){
			  document.getElementById("selectImportRowParam").checked=checkObj.checked;
		   } else {
			  for (var i=0;i<document.getElementsByName("selectImportRowParam").length;i++){
					obj=document.getElementsByName("selectImportRowParam")[i];				 
					obj.checked=checkObj.checked; 				 				 
			  }
		   }
	} ,
	validateSelectDeleteImportRows:function (){
		var selectRowParams=document.getElementsByName("selectImportRowParam");
		if(  selectRowParams==null){
			alert("请选择要删除的导入数据");
			return false;
		}
		var selectRow=false;
		for(var i=0;i<selectRowParams.length;i++){
			if(selectRowParams[i].checked==true){
				 
					selectRow=true;
					break;
				 
			}
		}
		if(selectRow==false){
			alert("请选择要删除的导入数据");
			return false;
		}
		return true;
	 },
	 validateSelectImportRows: function (){
		var selectRowParams=document.getElementsByName("selectImportRowParam");
		if(  selectRowParams==null){
			alert("请选择要导入的数据");
			return false;
		}
		var selectRow=false;
		for(var i=0;i<selectRowParams.length;i++){
			if(selectRowParams[i].checked==true){
				selectRow=true;
				break;
			}
		}
		if(selectRow==false){
			alert("请选择要导入的数据");
			return false;
		}
		return true;
	 },
	 deleteImportData:function(obj){
		 if(this.validateSelectDeleteImportRows()==false) return;   
	     var bodyId="#importDataBody";
		 var formId="#importDataForm";
		 var url=tsContextPath+'/core/importDataCoreService/json/deleteImportQueryData.do?timeStamp='+(new Date()).getTime();  
		 //$(bodyId).mask();  
		  
		 var that=this;
	  	 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(resultData){
			 //$.ts.EasyUI.closeDialog(obj);
			  //that.promptMessageDailog('操作提示','导入成功！');
			  var context="<table  width='100%'><tr><td align='center'  style='font-size:18px; font-weight:bold'>共删除";
			  context=context+resultData.billId+"条记录！</td></tr></table>";
			  $('#importDataContentPanel').panel({   
			    href:"", 
				content:context,    
				title:"导入结果提示", 
				cache:false,
				onLoad:function(){    
					//alert('loaded successfully');    
				}    
			}); 
		 } );
		return false;
	 },
	importData:function (obj){//点击按钮提交  
	     if(this.validateSelectImportRows()==false) return;   
	     var bodyId="#importDataBody";
		 var formId="#importDataForm";
		 var url=tsContextPath+'/core/importDataCoreService/json/doImportQueryData.do?timeStamp='+(new Date()).getTime();  
		 //$(bodyId).mask();  
		  
		 var that=this;
	  	 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(resultData){
			 //$.ts.EasyUI.closeDialog(obj);
			  //that.promptMessageDailog('操作提示','导入成功！');
			  var context="<table  width='100%'><tr><td align='center'  style='font-size:18px; font-weight:bold'>共导入";
			  context=context+resultData.billId+"条记录！</td></tr></table>";
			  $('#importDataContentPanel').panel({   
			    href:"", 
				content:context,    
				title:"导入结果提示", 
				cache:false,
				onLoad:function(){    
					//alert('loaded successfully');    
				}    
			}); 
		 } );
		return false;
	 } 
}
 