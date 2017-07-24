function ReportScript( ){ 
	 this.contextPath=""; 
	 this.appKey=""; 
	 this.opts=null;
	 this.clearSelectRowsDataFlag=0;
	 this.reportOptions=null;
	 this.baseReportOptions={  
	      clearAllData:function(){
			  var rows=reportScript.reportOptions.selectedRows; 
			  rows.splice(0,rows.length);  
			  var template=reportScript.reportOptions.recDataTemplate;
			  var row={};
			  for (var key in template){  
					row[key]="";
			  } 
			  reportScript.reportOptions.setValues(template); 
			  $("#reportBackbtn").click();
		  },
		  datagridOpts:{ 
			  onCheck:function(rowIndex,rowData){ 
				  var index=$.ts.Utils.findListRowIndex(rowData,reportScript.reportOptions.selectedRows); 
				  if(index==-1){
					  var template=reportScript.reportOptions.recDataTemplate;
					  var row={};
					  for (var key in template){  
							row[key]=rowData[key];  
					  }
					  reportScript.reportOptions.selectedRows.push(row); 
					  var template=$.ts.Utils.commaJoinStr(reportScript.reportOptions.selectedRows);
					  reportScript.reportOptions.setValues(template); 
				  } 
			 },
			 onCheckAll:function(rows){
				 for(var i=0;i<rows.length;i++){
					 reportScript.reportOptions.datagridOpts.onCheck(i,rows[i]);
				 }
			 },
			 onUncheck:function(rowIndex,rowData){
				  var index=$.ts.Utils.findListRowIndex(rowData,reportScript.reportOptions.selectedRows);
				  if(index>-1){
					  reportScript.reportOptions.selectedRows.removeItemAt(index);
					  var template=$.ts.Utils.commaJoinStr(reportScript.reportOptions.selectedRows);
					  reportScript.reportOptions.setValues(template); 
				  }
			 },
			 onUncheckAll:function(rows){
				 for(var i=0;i<rows.length;i++){
					 reportScript.reportOptions.datagridOpts.onUncheck(i,rows[i]);
				 }
			 }
		  }
   };
   this.choseRowsOptions={  //reportOptions have operateTarget ,insertRowEvent,deleteRowEvent 
          defaultInsertRowEvent:{callbackEvent:function(copyRow,rowData){  
				var row=$(copyRow); 
				var template=reportScript.reportOptions.recDataTemplate;
				for(var i=0;i<template.length;i++){
					var field=template[i].field; 
					for(var n=1;n<field.length;n++){ 
						var node=row.find("[name='"+field[n]+"']");
						$.ts.Utils.setContainerRowNodeValue(node,rowData[field[0]]);
					}
				} 
		  } },
		  defaultDeleteRowEvent: function(index,rowData){  
		  	 var opts=reportScript.reportOptions;
			 var row=opts.selectedRows[index]; 
			 var recDataTemplate=reportScript.reportOptions.recDataTemplate;
			 var rootNode=opts.containerObj;
			 var cls=opts.containerObj.attr("class"); 
			  if(cls.indexOf("easyui-datagrid")>=0){ 
			  	rootNode=rootNode.parent();
			  }
			 var nodeName=$.ts.Utils.getContainerNodeName(recDataTemplate);  
			 var nodes=$(rootNode).find("[name='"+nodeName+"']"); 
			 
			 if(nodes.length==0) return ; 
			 for(var i=0;i<nodes.length;i++){
				var node=nodes[i];
				var rowJq=$.ts.Utils.getContainerRowNode(nodeName,node,rootNode); 
				 
				if($(rowJq).find("[cloneRowFlag='1']").length>0) continue;
				var bean=$.ts.Utils.getContainerRowToBean(recDataTemplate,rowJq);
				var sameRow=true;
			 	for (var key in bean){ 
					if(bean[key]!=rowData[key]){
						sameRow=false;
						break;
					}
				} 
				if(sameRow==true){
					var obj= $(rowJq).find("a[deleteRecordOperate='1']");
					if(obj.length==0){
						obj= $(rowJq).find("[name='"+nodeName+"']");
					}
					if(obj.length==0){
						obj= $(rowJq).find("input[name]");
					}
					var deleteEvent={};
					if($.ts.Utils.isEmpty(opts.deleteRowAfterEvent)==false && $.isFunction(opts.deleteRowAfterEvent)){ 
						 deleteEvent.callbackEvent=opts.deleteRowAfterEvent;
					} 
					deleteEvent.tsOptions=opts;
					$.ts.EasyUI.deleteTableRow($(obj[0]),deleteEvent);
					break;
				} 
			 } 
		  },
		  datagridOpts:{ 
			  onCheck:function(rowIndex,rowData){
				  var opts=reportScript.reportOptions;   
				  var index=$.ts.Utils.findListRowIndex(rowData,opts.selectedRows);  
				  if(index==-1){
					  var template=opts.recDataTemplate;
					  var row={};
					  if($.ts.Utils.isEmpty(template)) return;
					  for(var i=0;i<template.length;i++){
						  if("1"!=template[i].sameRecordFlag) continue;
						  var key=template[i].field[0];
						  row[key]=rowData[key];
					  }  
					  opts.selectedRows.push(row);  
					  var backcallEvent=opts.defaultInsertRowEvent;
					  if($.ts.Utils.isEmpty(opts.insertRowEvent)==false && $.isFunction(opts.insertRowEvent)){
						  backcallEvent=opts.insertRowEvent;
					  }  
					  if($.ts.Utils.isEmpty(opts.copyRows)==false)
					  	backcallEvent.copyRows=opts.copyRows;  
					  if($.ts.Utils.isEmpty(opts.rowPrefix)==false)
					 	 backcallEvent.rowPrefix=opts.rowPrefix;
					  if($.ts.Utils.isEmpty(opts)==false)
						backcallEvent.tsOptions=opts;
					  var copyRow=$.ts.EasyUI.copyTableRow(opts.operateTarget,backcallEvent,rowData); 
					  if($.ts.Utils.isEmpty(opts.insertRowAfterEvent)==false && $.isFunction(opts.insertRowAfterEvent)){ 
						  opts.insertRowAfterEvent.call(opts.operateTarget ,copyRow,rowData); 
					  } 
				  } 
			 },
			 onCheckAll:function(rows){
				 for(var i=0;i<rows.length;i++){
					 reportScript.reportOptions.datagridOpts.onCheck(i,rows[i]);
				 }
			 },
			 onUncheck:function(rowIndex,rowData){
				 if($.isFunction(reportScript.reportOptions.selectedRows)) return;
				  var index=$.ts.Utils.findListRowIndex(rowData,reportScript.reportOptions.selectedRows);
				  if(index>-1){ 
					  var opts=reportScript.reportOptions; 
					  
					  var backcallEvent=opts.defaultDeleteRowEvent;
					  if($.ts.Utils.isEmpty(opts.deleteRowEvent)==false && $.isFunction(opts.deleteRowEvent)){
						  backcallEvent=opts.deleteRowEvent;
					  }
					  backcallEvent.call(this,index,rowData);  
					  opts.selectedRows.removeItemAt(index);  
				  }
			 },
			 onUncheckAll:function(rows){
				 for(var i=0;i<rows.length;i++){
					 reportScript.reportOptions.datagridOpts.onUncheck(i,rows[i]);
				 }
			 }
		  }
   };
 }
ReportScript.prototype={ 
	setOption:function(option){
		this.opts= $.extend({},option); 
		this.contextPath=this.opts.contextPath;  
	},
	initPage:function (option){ 
	     this.setOption(option);
		 reportDatagrid= reportDatagrid[0];
		 var resultJson = reportDatagrid.datagrid;   
		 $.ts.EasyUI.formatDatagridParam(resultJson); 
		 var grid=$('#'+resultJson.tableId);  
		
		resultJson.rows=null;  
		$.ts.EasyUI.formatGridColumns(resultJson); 
		 grid.attr("reportType",resultJson.reportType);
		 
		 if("treegrid"==resultJson.reportType){
			 grid.treegrid(resultJson); 
		 } else {
		 	grid.datagrid(resultJson); 
		 } 
		 this.setTableSelectPage(grid,reportDatagrid.pagination);
		 /** var queryConditionHeight=0;
		   if($("#reportQueryConditionTable").length>0){
			   queryConditionHeight=$("#reportQueryConditionTable").height();
		   }
		   grid.datagrid('resize', {
				height: ($(document).height()-queryConditionHeight-50) 
			});**/
		//this.refreshDatagridHeadStyle(grid);
		//grid.datagrid("resize");   
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
	exportQueryResult:function(table){
		var gridOpts=null;
	   var firstGrid=null;
	   if(table!=null){
		   if("treegrid"==table.attr("reportType")){
			   gridOpts=table.treegrid('options');
		   } else {
		   	   gridOpts=table.datagrid('options');
		   }
		   firstGrid=table;
	   } else { 
		   var tableId=reportDatagrid.datagrid.tableId;  
		   firstGrid=$('#'+tableId); 
		    if("treegrid"==firstGrid.attr("reportType")){
			   gridOpts=firstGrid.treegrid('options');
		   } else {
		   	   gridOpts=firstGrid.datagrid('options');
		   } 
	   }  
	   //firstGrid.datagrid("loading");
	   var options = null;
	   if("treegrid"==firstGrid.attr("reportType")){
		   options=firstGrid.treegrid('getPager').data("pagination").options;
	   } else {
	   	   options=firstGrid.datagrid('getPager').data("pagination").options;
	   } 
	   var pageSize = options.pageSize; 
	   //alert(gridOpts.pageNumber);
	   $("#pageSize").val(pageSize);
	   if(gridOpts==null){
			$("#pageNumber").val(0);
	   } else {
		   $("#pageNumber").val(gridOpts.pageNumber);
	   } 
	   var url=this.contextPath+'/core/reportResolver/exportReportResult.do?operateAuthCheckFlag=0&timeStamp='+(new Date()).getTime();
	   var that=this;
	   var paras=$("#reportEngineForm").serialize();
		 $.ajax({
			type: "POST", 
			datatype: "json",
			url : url,
			data:paras,
			async: true, 
			success : function(result) {  
			    if($.ts.Utils.isString(result)){
					var errorHint=false;
				    for(var i=0;i<$.ts.constant.errorPageBodyIds.length;i++){
					  if(result.indexOf($.ts.constant.errorPageBodyIds[i])>0){
						  errorHint=true;
						  break;
					  }
					}
					if(errorHint==true){
						$.ts.EasyUI.showContentDialog(result); 
						return ;
					}
				}
			    $.ts.EasyUI.modalDialog(  {  
				    title:"导出文件",
					width : 800, 
					height : 300,
					modal:false,
					method:"POST", 
					cache:true,
					collapsible: true,  
					maximizable: true, 
					resizable:true, 
					content : result   
				} ); 
				 
			},
			error: function(XmlHttpRequest, textStatus, errorThrown){   
					$.messager.alert('提示', "导出数据出错！"); 
			 } 
		});   
		return true;
	},
	bindQueryReportResult:function(table){
		this.setFirstPage(table);
		this.queryReportResult(table);
	},
	setFirstPage:function(table){
	   var gridOpts=null;
	   var firstGrid=null;
	   if(table!=null){
		   if("treegrid"==table.attr("reportType")){
			   gridOpts=table.treegrid('options');
		   } else {
		   	   gridOpts=table.datagrid('options');
		   }
		   firstGrid=table;
	   } else { 
		   var tableId=reportDatagrid.datagrid.tableId;  
		   firstGrid=$('#'+tableId); 
		    if("treegrid"==firstGrid.attr("reportType")){
			   gridOpts=firstGrid.treegrid('options');
		   } else {
		   	   gridOpts=firstGrid.datagrid('options');
		   } 
	   }    
	   if(gridOpts==null){
			$("#pageNumber").val(0);
	   } else {
		    gridOpts.pageNumber=1;
	   } 
	},
	queryReportResult:function (table){  
	   var gridOpts=null;
	   var firstGrid=null;
	   if(table!=null){
		   if("treegrid"==table.attr("reportType")){
			   gridOpts=table.treegrid('options');
		   } else {
		   	   gridOpts=table.datagrid('options');
		   }
		   firstGrid=table;
	   } else { 
		   var tableId=reportDatagrid.datagrid.tableId;  
		   firstGrid=$('#'+tableId); 
		    if("treegrid"==firstGrid.attr("reportType")){
			   gridOpts=firstGrid.treegrid('options');
		   } else {
		   	   gridOpts=firstGrid.datagrid('options');
		   } 
	   }  
	   firstGrid.datagrid("loading");
	   var options = null;
	   if("treegrid"==firstGrid.attr("reportType")){
		   options=firstGrid.treegrid('getPager').data("pagination").options;
	   } else {
	   	   options=firstGrid.datagrid('getPager').data("pagination").options;
	   } 
	   var pageSize = options.pageSize; 
	   //alert(gridOpts.pageNumber);
	   $("#pageSize").val(pageSize);
	   if(gridOpts==null){
			$("#pageNumber").val(0);
	   } else {
		   $("#pageNumber").val(gridOpts.pageNumber);
	   } 
	   var url=this.contextPath+'/core/reportResolver/json/queryReportResult.do?operateAuthCheckFlag=0&timeStamp='+(new Date()).getTime();
	   var that=this;
	   var paras=$("#reportEngineForm").serialize();
		 $.ajax({
			type: "POST", 
			datatype: "json",
			url : url,
			data:paras,
			async: true, 
			success : function(result) {  
			    if($.ts.Utils.isString(result)){
					var errorHint=false;
				    for(var i=0;i<$.ts.constant.errorPageBodyIds.length;i++){
					  if(result.indexOf($.ts.constant.errorPageBodyIds[i])>0){
						  errorHint=true;
						  break;
					  }
					}
					if(errorHint==true){
						$.ts.EasyUI.showContentDialog(result); 
						return ;
					}
				}
				var resultJson = result[0];    
				var datagrid=resultJson.datagrid; 
				$.ts.EasyUI.formatDatagridParam(datagrid); 
				var grid=$('#'+datagrid.tableId);   
				grid.attr("reportType",datagrid.reportType);
				var rows=datagrid.rows;
				datagrid.rows=null; 
				datagrid.pageSize=pageSize;
				$.ts.EasyUI.formatGridColumns(datagrid);
				if($("#operatePattern").val()=="chooseRow"){ 
				    $("#reportClearBlankBtn").show(); 
					 $("#reportClearBlankBtn").click(function(){that.clearSelectRowsDataFlag=1;
					 $.ts.EasyUI.closeDialog(document.getElementById("queryDatasBtn"),"0"); }); 
					datagrid.onDblClickRow=function(rowIndex,rowData){ 
						 $.ts.EasyUI.closeDialog(document.getElementById("queryDatasBtn"),"0"); 
					} 
				} else {
					 var linkOperateBut=$("#reportHiddenContainer").find("a");  
					 var dblClickRowBindButton=$("a[position='dblClickRowShow']"); 
					  if(dblClickRowBindButton.length>0){ 
						  datagrid.onDblClickRow=function(rowIndex,rowData){  
						     $(dblClickRowBindButton[0]).attr("selectedRowIndex",rowIndex);
						     $(dblClickRowBindButton[0]).click();  
						     // that.methInvokeEvent(linkOperateBut[0]);  
						}  
					 } else {  
						if(linkOperateBut.length>0){     
						   datagrid.onDblClickRow=function(rowIndex,rowData){ 
							  linkOperateBut.attr("selectedRowIndex",rowIndex);
							  linkOperateBut.click();  
						  }  
						}
					}  
				}
				if($("#operatePattern").val()=="chooseRows"){     
				    if($.ts.Utils.isEmpty(that.reportOptions)==false ){
						if( that.reportOptions.operatePattern=="mergeValues"){     
							that.reportOptions=$.extend({},that.baseReportOptions,that.reportOptions);
							$.extend(datagrid,that.reportOptions.datagridOpts);
							$("#reportClearBlankBtn").show();
							 $("#reportClearBlankBtn").click(function(){that.baseReportOptions.clearAllData();}); 
						} else {
							that.reportOptions=$.extend({},that.choseRowsOptions,that.reportOptions);
							
							if($.ts.Utils.isEmpty(that.reportOptions.selectedRows)){ 
							    
								$.ts.Utils.getContainerRowsValue(that.reportOptions); 
							} else if(that.reportOptions.selectedRows.length==0){ 
								$.ts.Utils.getContainerRowsValue(that.reportOptions);
							}
							$.extend(datagrid,that.reportOptions.datagridOpts);
						}
					}
					
				}
				if("treegrid"==datagrid.reportType){
					datagrid.onLoadSuccess=function(row,data){  
					   if(data && $.ts.Utils.isEmpty(that.reportOptions)==false 
						 && $.ts.Utils.isEmpty(that.reportOptions.selectedRows)==false 
						 && that.reportOptions.selectedRows.length>0){ 
							var selectedRows=that.reportOptions.selectedRows; 
							$.each(data.rows, function(index, bean){  
								if(bean.checked){ 
									if("treegrid"==datagrid.reportType){
										firstGrid.treegrid('checkRow', index); 
									} else
										firstGrid.datagrid('checkRow', index); 
								}  else {
									if(that.validateSameRow(bean,selectedRows)>-1){
										if("treegrid"==datagrid.reportType){
											firstGrid.treegrid('checkRow', index); 
										} else
											firstGrid.datagrid('checkRow', index); 
									}
								}
							}); 
						} else {
							/**var rowObj=$($(grid).parent().find(".easyui-linkbutton")).parent() ;
							 if(rowObj!=null && rowObj.length>0){
								  $.parser.parse(rowObj);   
							 }**/ 
							 if(data!=null && data.rows!=null)
							 $.each(data.rows, function(index, bean){  
							   var rowObj= $($(grid).parent().find(".easyui-linkbutton")[index]).parent() ;
							   if(rowObj!=null && rowObj.length>0){
								 $.parser.parse(rowObj);
							   }
							}); 
						
						}
					};
				} else {
					datagrid.onLoadSuccess=function(data){  
					   if(data && $.ts.Utils.isEmpty(that.reportOptions)==false 
						 && $.ts.Utils.isEmpty(that.reportOptions.selectedRows)==false 
						 && that.reportOptions.selectedRows.length>0){ 
							var selectedRows=that.reportOptions.selectedRows; 
							$.each(data.rows, function(index, bean){  
								if(bean.checked){ 
									if("treegrid"==datagrid.reportType){
										firstGrid.treegrid('checkRow', index); 
									} else
										firstGrid.datagrid('checkRow', index); 
								}  else {
									if(that.validateSameRow(bean,selectedRows)>-1){
										if("treegrid"==datagrid.reportType){
											firstGrid.treegrid('checkRow', index); 
										} else
											firstGrid.datagrid('checkRow', index); 
									}
								}
							}); 
						} else {
							 
							 if(data!=null && data.rows!=null){
								 $(grid).parent().find("tr[datagrid-row-index]").find(".easyui-linkbutton").linkbutton();
								 						
							 }
						}
					};
				}
				if("treegrid"==datagrid.reportType){
					grid.treegrid(datagrid); 
				} else {
					grid.datagrid(datagrid); 
				}
				that.setTableSelectPage(grid,resultJson.pagination);
				var loadRows = {'total':datagrid.total,'rows':rows};  
				if("treegrid"==datagrid.reportType){
					grid.treegrid("loadData",loadRows);   
				} else {
					grid.datagrid("loadData",loadRows);   
				}
				if(grid!=null){
				    //grid.datagrid("resize"); 
				   var queryConditionHeight=0;
				   if($("#reportQueryConditionTable").length>0){
					   queryConditionHeight=$("#reportQueryConditionTable").height();
				   }
				   grid.datagrid('resize', {
						height: ($(window).height()-queryConditionHeight-50) 
					}); 
				  
				}  
			},
			error: function(XmlHttpRequest, textStatus, errorThrown){   
					$.messager.alert('提示', "查询数据出错！"); 
			 } 
		});   
		return true;
	},
	evalValidateExpress:function(qObj,table,fields,chooseRowsJson){
		var validateExp=qObj.attr("validateExp");  
		var sameFieldExp=qObj.attr("sameFieldExp"); 
		if($.ts.Utils.isEmpty(validateExp) &&  $.ts.Utils.isEmpty(sameFieldExp)) return true;
		var limitChooseRows=qObj.attr("limitChooseRows");  
		var that=this;   
		function rowValidate( row){  
			var map=that.putFieldsToMap(fields,table,row);  
			var par=new $.ts.ScriptParser(); 
		    var parsers=new par.BuilderNodes("");
		    validateExp=par.parseScript(validateExp,map);  
			var value=eval(validateExp);
			return value;
		}
		function rowsValidate(rows ){ 
		    var par=new $.ts.ScriptParser(); 
		    var parsers=new par.BuilderNodes("");
			if(sameFieldExp.indexOf("rows.length")>0){
				validateExp=par.parseScript(validateExp,map);
			    var value=eval(validateExp);
				return value;
			}
			var firstSameFieldValue="";
		    for(var i=0;i<rows.length;i++){
				var row=rows[i];
				var map=that.putFieldsToMap(fields,table,row);  
				if($.ts.Utils.isEmpty(validateExp)==false){ 
		            var express=par.parseScript(validateExp,map); 
					var value=eval(express);
					if(false==value){
						return false;
					}
				}
				if($.ts.Utils.isEmpty(sameFieldExp)==false){
					var express=par.parseScript(sameFieldExp,map);
					var value=eval(express);
					if(i==0){
						firstSameFieldValue=value;
					} else {
						if(value!=firstSameFieldValue){
							return false;
						}
					}
				}
			}
			return value;
		}
		var v=true;
		
		if(limitChooseRows=="2"){
			v=rowsValidate( chooseRowsJson);
		} else {
			v=rowValidate( chooseRowsJson);
		} 
		if(v==false){
			var validatePrompt=qObj.attr("validatePrompt"); 
			if(typeof(validatePrompt)=="undefined")
				validatePrompt="不符合操作条件！";
			$.messager.alert('warning',validatePrompt);
		}
		return v;
	},
	parseRowToTemplateJson:function(qObj,fields,table,row,template){
		 var map=this.putFieldsToMap(fields,table,row);   
		 var jsonBean=null;  
		  
		 try{
			 jsonBean=jQuery.parseJSON(template);//优先采用json转化
		 } catch(e){  
			 jsonBean=$.ts.Format.paramsToJson(template);
		 }  
		 if($.ts.Utils.isString(jsonBean)){ 
			var par=new $.ts.ScriptParser(); 
			var parsers=new par.BuilderNodes("");
			var content=par.parseScript(jsonBean,map);
			return content;
		 } 
		 for (var key in jsonBean){
			var text=jsonBean[key]; 
			if(text==""){
				jsonBean[key]=map.get(key);
			} else { 
			    var par=new $.ts.ScriptParser(); 
				var parsers=new par.BuilderNodes("");
				var content=par.parseScript(text,map);
				jsonBean[key]=content;
				map.put(key,content);   
			} 
			 
		} 
		return jsonBean;
	},
	 
	parseRowToColumnsJson:function(qObj,fields,table,row){
		var jsonBean={}; 
		if(row){
			for(var n=0;n<fields.length;n++){ 
				 var col =null;
				 if("treegrid"==table.attr("reportType")){
					 col = table.treegrid('getColumnOption', fields[n]);
				 }  else {
					 col = table.datagrid('getColumnOption', fields[n]);
				 }
			
				 if(typeof(col.field)!="undefined" && col.field!=""){
					 jsonBean[col.field]=row[col.field];   
				 }
			}
		} 
		return jsonBean;
	},
	parseRowJson:function(qObj,fields,table,row,recAllColumn){
		var template=qObj.attr("recParamTemplate");
		var limitChooseRows=qObj.attr("limitChooseRows"); 
		if($.ts.Utils.isEmpty(recAllColumn)==false && "1"==recAllColumn)   
		    template=""; 
		if($.ts.Utils.isEmpty( template)==false){ 
			return this.parseRowToTemplateJson(qObj,fields,table,row,template); 
		} else if($.ts.Utils.isEmpty(limitChooseRows)==false && limitChooseRows!="0"){
			return this.parseRowToColumnsJson(qObj,fields,table,row);
		} else return {};
	}, 
	putFieldsToMap:function(fields,table,row){
		var map=new $.ts.Map(); 
		if(row){ 
		    if($.isArray(row))
				row=row[0];
			
			for(var n=0;n<fields.length;n++){ 
				 var col = null;
				 if("treegrid"==table.attr("reportType")){
					  col = table.treegrid('getColumnOption', fields[n]); 
				 } else {
					  col = table.datagrid('getColumnOption', fields[n]); 
				 }
				 if(typeof(col.field)!="undefined" && col.field!=""){
					 map.put(col.field,row[col.field]);   
				 }
				 
			}
		}
		return map;
	},
	formatUrlRowArrayParam:function(sb,json){  
		//var serial=$.param(json); 
		var serial="";
		if($.ts.Utils.isString(json)){
			serial=json;
		} else {
			serial= JSON.stringify(json);  
		} 
		if(serial=="") return ;
		if(sb.toString().indexOf("?")>0){
			sb.append("&selectedRecordIds="+serial); 
		} else {
			sb.append("?selectedRecordIds="+serial);
		}    
	},
	formatUrlRowParam:function(sb,json){  
		var serial="";
		if($.ts.Utils.isString(json)){
			serial=json;
		} else {
			serial=$.param(json);  
		}
		if(serial=="") return;
		if(sb.toString().indexOf("?")>0){
			  sb.append("&"+serial ); 
		} else {
			sb.append("?"+serial);
		}   
	},
	formatUrlRowsParam:function(url,json){
		var sb=new $.ts.StringBuffer("");
		sb.append(url); 
		if($.isArray(json)){ 
			for(var i=0;i<json.length;i++){ 
				this.formatUrlRowArrayParam(sb,json[i]); 
			}
		} else {
			this.formatUrlRowParam(sb,json); 
		}
		return sb.toString();
	},
	getDialogSelectedRows:function(){
		if(this.clearSelectRowsDataFlag==1){
			return {};
		}
		var tableId = reportDatagrid.datagrid.tableId;   
		var table=$('#'+tableId);
		var limitChooseRows="1";
		return this.getSelectedRows(table,limitChooseRows,null );
	},
	getSelectedRows:function(table,limitChooseRows,qObj){
		var selectedRowIndex=-1;
		if(qObj!=null){
			var position=qObj.attr("position");  
			selectedRowIndex=qObj.attr("selectedRowIndex");   
			if($.ts.Utils.isEmpty(position)==false && "toolbar"!=position && "dblClickRow"!=position
				&& "dblClickRowShow"!=position){
				var gridTr =qObj.parent().parent().parent();
				var index = $(gridTr).index();  
				var rows = null;
				if("treegrid"==table.attr("reportType")){
					 rows= table.treegrid("getRows"); 
				} else {
					 rows = table.datagrid("getRows");
				}
				var selectRows=null;
				if("treegrid"==table.attr("reportType")){
					selectRows=table.treegrid('getSelected'); 
				} else {
					selectRows=table.datagrid('getSelected');  
				}
				if(selectRows==null || selectRows.length<=0){
					if("treegrid"==table.attr("reportType")){
						 table.treegrid('selectRow',index); 
					} else {
						 table.datagrid('selectRow',index); 
					}
				}
				 
				return rows[index]; 
			}
		}
		var cks = $.find("input[type=checkbox][name=ck]"); 
		var selectRows=null;
		
		if(cks.length>0 && limitChooseRows!="0"){// 
			var rows = null;
			if("treegrid"==table.attr("reportType")){
				 rows = table.treegrid("getRows"); 
			} else {
				 rows = table.datagrid("getRows");
			}
			if($.ts.Utils.isEmpty(selectedRowIndex)==false){
				if(selectedRowIndex>-1){
					return rows[selectedRowIndex];
				}
			}
			for(var i=0;i<cks.length;i++){ 
			   var ck=cks[i];
			   var checked=$(ck).is(":checked");
			   if(checked){
				   if(selectRows==null){
				     selectRows=new Array();
				   }
			       var  row=$(ck).parent().parent().parent() ;
				   var index = row.index(); 
				   selectRows.push(rows[index]);
			   }
			} 
		} else if(limitChooseRows=="1" || limitChooseRows=="0"){ 
			 if("treegrid"==table.attr("reportType")){
				selectRows=table.treegrid('getSelected'); 
			} else {
				selectRows=table.datagrid('getSelected'); 
			}
		} else if(limitChooseRows=="2"){ 
			if("treegrid"==table.attr("reportType")){
				selectRows=table.treegrid('getSelections'); 
			} else {
				selectRows = table.datagrid('getSelections');
			}
		}
		return selectRows;
	},
	promptMessageDailog:function(title,msg){
		 $.messager.show({
			title:'操作提示',
			msg:msg,
			timeout:$.ts.constant.timeout,
			style:{
				right:'',
				bottom:''
			}
		 }); 
	}, 
	ajaxValidateOperate:function (json, qObj ){    
		var bsValidate=qObj.attr("bsValidate"); 
		if("1"!=bsValidate) return true;
		var url=tsContextPath+"/core/reportResolver/json/validateOperate.do?operateAuthCheckFlag=0";
		json.moduleFileName=$("#moduleFileName").val();
		var opUrl=qObj.attr("url"); 
		json.url=opUrl; 
		var results=true;
		 $.ajax({
				type: "POST", 
				datatype:"json", 
				url : url,
				data:json,
				async:false,
				success : function(result) {   
				     if(typeof(result.alertMsg)=="undefined"){
						 $.ts.EasyUI.showContentDialog(result);
						 results=false;
					 } else    if(result!=null && result.alertMsg!=""){
						 $.messager.alert('提示', result.alertMsg);  
						 results=false;
					 }
				},
				error: function(XmlHttpRequest, textStatus, errorThrown){   
					$.messager.alert('提示', "操作失败！"); 
					results=false;
				} 
			});
			return results;
	 },
	methInvokeEvent:function (obj){  
		var tableId = reportDatagrid.datagrid.tableId;   
		var table=$('#'+tableId); 
		var fields =null;
		if("treegrid"==table.attr("reportType")){ 
			fields =table.treegrid('getColumnFields',true).concat(table.treegrid('getColumnFields')); 
		} else {
			fields =table.datagrid('getColumnFields',true).concat(table.datagrid('getColumnFields')); 
		}
	    var qObj= $(obj);   
		var limitChooseRows=qObj.attr("limitChooseRows");   
		var promptStatement=qObj.attr("promptStatement"); 
		var recParamTemplate=qObj.attr("recParamTemplate"); 
		var position=qObj.attr("position"); 
		var targetName=qObj.attr("targetName"); 
		var bindUrlField=qObj.attr("bindUrlField");
		
		var json={};
		var rowToJson={};
		var jsonContent=""; 
		if($.ts.Utils.isEmpty(limitChooseRows)==true){
			limitChooseRows="1";
			qObj.attr("limitChooseRows",limitChooseRows);
		} 
		var selectedRows=this.getSelectedRows(table, limitChooseRows,qObj);   
		if( limitChooseRows=="0"){
			var row = null; 
			if(selectedRows!=null){
				if($.isArray(selectedRows)){
					row=selectedRows[0];
				} else {
					row=selectedRows;
				}
			} 
			json=this.parseRowJson(qObj,fields,table,row);
			if($.ts.Utils.isEmpty(recParamTemplate)==false){
				rowToJson=this.parseRowJson(qObj,fields,table,row,"1"); 
			} else {
				rowToJson=json;
			}
		} 
		if(limitChooseRows=="1"){  
		     var validateState="";
			 if(selectedRows==""){
				this.promptMessageDailog('操作提示',"请选中记录");  
				 return ;
			}
			 if(!selectedRows  ){
				 validateState="请选中记录！";
			 } else if(selectedRows.length>1){
				 validateState="只能选中一条记录！";
			 }
			 if(validateState!="" ){
				 this.promptMessageDailog('操作提示',validateState); 
				 return ;
			 }  
			 if(selectedRows!=null && $.isArray(selectedRows)){
				selectedRows=selectedRows[0];
			 }
			 json=this.parseRowJson(qObj,fields,table,selectedRows);    
			 rowToJson=this.parseRowJson(qObj,fields,table,selectedRows ,"1"); 
			  
			 if(this.ajaxValidateOperate({"paramList":[selectedRows]}, qObj )==false){ 
				 return  ;
			 }
			
			 //rowToJson=json;
		} else if(limitChooseRows=="2"){ 
		    if(selectedRows==""){
				this.promptMessageDailog('操作提示',"请选中记录");  
				 return ;
			}
			if(!selectedRows){
				 this.promptMessageDailog('操作提示',"请选中记录");  
				 return ;
			 }
			json=new Array(); 
			rowToJson=new Array();  
			for(var i=0; i<selectedRows.length; i++){
				var row = selectedRows[i];
				var bean=this.parseRowJson(qObj,fields,table,row);
				if($.ts.Utils.isEmpty(recParamTemplate)==false){
					var allColumnBean=this.parseRowJson(qObj,fields,table,row,"1");
					rowToJson.push(allColumnBean);
				} 
				json.push(bean);
			} 
			if($.ts.Utils.isEmpty(recParamTemplate)){
				rowToJson=json;
			}
		}  
		var validateValue=this.evalValidateExpress(qObj,table, fields,rowToJson);
		if(false==validateValue) return;
		if(typeof(qObj.attr("propertyPrefix"))!="undefined" && qObj.attr("propertyPrefix")!=""){  
			var formJson={};
			formJson[qObj.attr("propertyPrefix")]=json;
			json=formJson;  
		} 
		var url=qObj.attr("url"); 
		if($.ts.Utils.isEmpty(bindUrlField)==false){
			
			 bindUrlField=rowToJson[bindUrlField];
			 
			 if($.ts.Utils.isEmpty(bindUrlField)==false){
				 url=bindUrlField;
			 }
			
		 }
		if(url.indexOf("?")>0){
			url+="&operateAuthCheckFlag=0&timeStamp="+(new Date()).getTime();
		} else {
			url+="?operateAuthCheckFlag=0&timeStamp="+(new Date()).getTime();
		}
		var dialogOpts={};
		var dlgOptions=qObj.attr("dialogOpts");
		if($.ts.Utils.isEmpty(dlgOptions)==false){
			if(dlgOptions.indexOf(0)!="{"){
				dlgOptions="{"+dlgOptions+"}";
			}
			var dlgObj=(new Function("return  "+dlgOptions))();   
			$.extend(dialogOpts,dlgObj);
		}
		var dialogWidth=qObj.attr("dialogWidth");
		if($.ts.Utils.isEmpty(dialogWidth)==false){
			dialogOpts.width=dialogWidth;
		}
		var dialogTitle=qObj.attr("title");
		if($.ts.Utils.isEmpty(dialogTitle)==false){
			dialogOpts.title=dialogTitle;
		} else {
			dialogOpts.title=qObj.text()+$("#reportConfigureTitle").val();
		} 
		if(url.indexOf("/recJson/")>0){ 
		    dialogOpts["datatype"]="json";
			dialogOpts["contentType"]="application/json";  
			jsonContent= JSON.stringify(json);  
			dialogOpts["data"]=jsonContent; 
		} else { 
			url= this.formatUrlRowsParam(url,json);   
			json=null; 
			rowToJson=null;
		}
		url=this.contextPath+url;   
		if($.ts.Utils.isEmpty(promptStatement)==false){
			$.messager.confirm('确认操作', promptStatement, function(r) {
			   if(r){ 
				  new callbackFun(); 
			   }
			});
		} else { 
			new callbackFun();
		}
		function callbackAjaxFun(){
			$.ajax({
				type: "POST", 
				datatype:"json",
				url : url,
				success : function(result) { 
				    if($.ts.Utils.isEmpty(result)  ){
						$.messager.alert('提示', "操作失败！"); 
						return ;
					} 
					 if($.ts.Utils.isString(result)  ){
						 var errorHint=false;
						  for(var i=0;i<$.ts.constant.errorPageBodyIds.length;i++){
							  if(result!=null && result.indexOf($.ts.constant.errorPageBodyIds[i])>0){
								  errorHint=true;
								  break;
							  }
						  }
						if(errorHint==true){ 
							$.ts.EasyUI.showContentDialog(result);
						} else {
							$.messager.alert('提示', result);
						}
						return ;
					}
					var state=result.statememt;
					if($.ts.Utils.isEmpty(result.error)==false || $.ts.Utils.isEmpty(result.alertMsg)==false){
						state=result.error;
						if($.ts.Utils.isEmpty(result.alertMsg)==false)
						  state=result.alertMsg;
						$.messager.alert('提示',state); 
						return ;
					}
					if($.ts.Utils.isEmpty(result.confirm)==false){
						state=result.confirm;
						url=url+"&needValidate=1";
						$.messager.confirm('确认操作', state, function(r) {
						   if(r){ 
							  new callbackAjaxFun(); 
							  return ;
						   }
						});
						return ;
					}
					$.messager.show({
						title : '提示',
						msg :state,
						timeout:$.ts.constant.timeout,
						style:{
							right:'',
							bottom:''
						}
					});
					//table.datagrid("reload"); 
					reportScript.queryReportResult(); 
				},
				error: function(XmlHttpRequest, textStatus, errorThrown){   
					$.messager.alert('提示', "操作失败！"); 
				} 
			});
		}
		function callbackDialog(){  
		   var initLoadPageCallback=function(){ 
	        
			   //	var idNode=document.getElementsByName("bean.id"); 
				$(document.forms[1]).prepend('<input name="bean.processInstanceId" id="bean.processInstanceId" type="hidden" value="${form.bean.processInstanceId}"/>');
				//$(idNode).after('<input name="workFlowFlag" id="workFlowFlag" type="hidden" value="2"/>');
		    
	   } 
		   $.ts.EasyUI.modalDialog($.extend(  {  
				width : 800, 
				height : $(document).height()-5,
				modal:false,
				method:"POST", 
				cache:true,
				collapsible: true,  
				maximizable: true, 
				resizable:true, 
				href : url   
			},dialogOpts)); 
		} 
		function callbackFrameDialog(){   
		   $.ts.EasyUI.frameDialog($.extend(  {  
				width : 800, 
				height : $(document).height()-5,
				modal:false,
				method:"POST",     
				cache:false,
				collapsible: true,  
				maximizable: true, 
				resizable:true,
				href : url   
			},dialogOpts)); 
		} 
		function callbackJsScript(){   
		    var api=dialogOpts.jsScript; 
			api.call(this,json); 
		} 
		function callbackNewPage(){
			window.open(url);
			//window.open(url,dialogOpts.title);
		}
		function callbackFun (){
			dialogOpts.targetName=targetName;
			if(targetName=="ajax"){ 
				 callbackAjaxFun();
			}   else {
				if("frameParent"==targetName){
					dialogOpts.targetName="parent"; 
					callbackFrameDialog();
				} else if("frame"==targetName){ 
					callbackFrameDialog();
				}  else if("newPage"==targetName){ 
					callbackNewPage();
				} else {
				   callbackDialog();
				}
			} 
		}
	} 
}
 