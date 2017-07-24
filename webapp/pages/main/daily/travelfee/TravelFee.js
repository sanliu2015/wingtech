var editIndex = undefined;

function TravelFeeScript( ){ 
	 this.contextPath=""; 
	 this.appKey=""; 
	 this.opts=null;
	 this.feeKindParentList=null;
}
 
TravelFeeScript.prototype={  
    
    initPage:function(option){
		this.opts= $.extend({},option);      
		var actionType=$("#actionType").val(); 
		 if(actionType!="update"){
			 this.asyncLoadFeeKindParentList(); 
		 } else  
			this.loadFeeKindParentList();
	},
	uploadFile:function(obj){
		var backcallEvent=function(row,rowData){
			var file=$(row).find("[type='file']"); 
			file.click(); 
			$(row).find("#fieldName").append(file.val());
		};
		$.ts.EasyUI.copyTableRow(obj,{callbackEvent:backcallEvent})
	},
	fileOnChange:function(obj){
		var file=$(obj);
		file.parent().find("#fieldName").append(file.val());
	},
	initRequestPage:function(option){
		
		this.opts= $.extend({},option);    
	
		 $("input[name='bean.employeeName").bind("dblclick", this.chooseEmployee );
		  	
		this.loadFeeKindParentList(); 
	},
	
	cloneExplanationRows:function(){
		var operateBut=document.getElementById("insertExplanationRowId");
		for(var i=0;i<5;i++){
			this.insertExplanatioRow(operateBut);
		}
	},
	getfeeKindParentData:function(){
		var that=this;
		var sql="select  name as id , name as text from  DAILY_FeeKind   where  isnull(parentId,0)=0  and isnull(status,'')!='0'";
			    var jsonPara=[{keyName:"queryList",sql:sql,isHql:"0"}];  
			  $.ajax( {
				url: tsContextPath +"/core/queryDbServlet?timeStamp="+new Date().getTime(),   
				data:  JSON.stringify(jsonPara) ,
				datatype: "json",
				type: "POST",  
				async:false,
				success:function(result,textStatus){
					var resultJson = jQuery.parseJSON( result );   
					 that.feeKindParentList=resultJson.queryList;
				 } 
			} ); 
	},
	insertExplanatioRow:function(obj){
		var copyRow=$.ts.EasyUI.copyTableRow(obj);  
	    var feeKindParentJq=copyRow.find("#dtlList\\.feeKindParentId"); 
		var feeKindIdJq=copyRow.find("#dtlList\\.description"); 
		var amountJq=copyRow.find("#dtlList\\.amount"); 
		feeKindParentJq.combobox();
		//feeKindIdJq.combobox();
		feeKindIdJq.textbox();
		amountJq.numberbox();
		//$.parser.parse(copyRow);
		var that=tavelFeeScript;   
		if(this.feeKindParentList==null){
			 this.getfeeKindParentData();
		}
	    feeKindParentJq.combobox("loadData", this.feeKindParentList);  
	    /**feeKindParentJq.combobox({    
			 onSelect:function(rec){    
				 that.dynamicLoadFeeKinds(copyRow,rec,0); 
			 }  
		 });**/   
		return  copyRow;
  },
	checkSinglebox:function(obj){
		if(obj.checked==false) return;
		var checkboxName=$(obj).attr("name");
		var nodes=$.find("input[name='"+checkboxName+"']");
		for(var i=0;i<nodes.length;i++){
			var node=nodes[i];
			if(node!=obj){
				node.checked=false;
			}
		}
	},
	integerInputFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");   
		return  "<input type='text' name='dtlList."+fieldName+"' id='dtlList."+fieldName+"' value='" + val + "'  style='width:100%'  class='easyui-numberbox'/>";
	 },
	 amountcChange:function(n,o){
	    var amountNodes=$("input[name='dtlList.amount']");
		var amount=$.ts.Number("0"); 
		$.each(amountNodes, function(i,node){ 
			amount=amount.add($.ts.Number($(node).val())); 
		});
		if($("#bean\\.applyAmount").attr("class")=="inputUnderLine"){
			$("#bean\\.applyAmount").val(amount);
		} else {
			$("#bean\\.applyAmount").val("setValue",amount);
		}
		var str=$.ts.Format.numMoneyToChinese(amount);
		$("#chineseMoneySpan").text(str);
     },
	  numericInputFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");   
		return  "<input type='text' name='dtlList."+fieldName+"' id='dtlList."+fieldName+"' value='" + val + "'  style='width:100%'   class='easyui-numberbox' data-options=\"precision:2,onChange:tavelFeeScript.amountcChange\"/>";
	 },
	 textInputFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");    
		 
		return  "<input type='text' name='dtlList."+fieldName+"' id='dtlList."+fieldName+"' value='" + val + "'  style='width:100%'  class='easyui-textbox'></input>";
	 },
	  comboboxFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");   
		return  "<select name='dtlList."+fieldName+"' id='dtlList."+fieldName+"'   style='width:100%'  class='easyui-combobox' data-options=\"editable:false,valueField:'id',textField:'text'\"/></select>";
	 },
	 
	 dateInputFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");   
		return  "<input   name='dtlList."+fieldName+"' id='dtlList."+fieldName+"' value='" + val + "'  style='width:100%'  class='easyui-my97' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\"  ></input>";
	 }, 
	 hiddenColumnFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");   
		return  "<input type='hidden' name='dtlList."+fieldName+"' id='dtlList."+fieldName+"' value='" + val + "'   />";
	 },
	removeRowFormat:function(val, row, index) {
		if(val == undefined)	val = ""; 
		var  text='<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-cancel"  onClick="$.ts.EasyUI.deleteTableRow(this)" tsOptions="needHide:\'1\'"></a> '; 
		return  text;
	 }, 
	chooseEmployee:function(obj){   
		  var url=tsContextPath+"/core/reportResolver/chooseRow.do?moduleFileName=EmployeeSerivceReport&openQueryResult=1"; 
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择员工",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
						      if($("#bean\\.employeeName").attr("class")=="inputUnderLine"){
								  $("#bean\\.employeeName").val(json.name); 
							  } else {
							  	  $("#bean\\.employeeName").textbox('setValue',json.name);   
							  }
							  $("#bean\\.employee\\.id").val(json.id);  
							  if($("#bean\\.employeeName").attr("class")=="inputUnderLine"){
								  $("#bean\\.deptName").val(json.departmentname); 
							  } else {
							  	  $("#bean\\.deptName").textbox('setValue',json.departmentname);  
							  }
							  $("#bean\\.deptId").val(json.deptId);  
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	chooseCanlendarLog:function(obj){   
		  var url=tsContextPath+"/core/reportResolver/chooseRow.do?moduleFileName=CalendarLogServiceReport&openQueryResult=1"; 
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择出差记录",   
				href : url   
			  } , function(json){  
					  try{  
						 if(json!=null){ 
							  $("#bean\\.calendarLogEvent").textbox('setValue',json.summaryEvent);  
							  $("#bean\\.calendarLogId").val(json.id);  
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	}, 
	asyncLoadFeeKindParentList:function(){ 
		var sql="select  id , name as text from  DAILY_FeeKind   where  isnull(parentId,0)=0  and isnull(status,'')!='0' ";
		var that=this;
		$.ts.EasyUI.loadComboboxData(sql, function(handler,queryList){   
		     that.feeKindParentList=queryList; 
			 var actionType=$("#actionType").val(); 
			 if(actionType!="update"){
				 for(var i=0;i<5;i++)
			 		that.insertDatagridRow();
			 }
		},{async:true}); 
	}, 
	loadFeeKindParentList:function(){ 
		var sql="select  id , name as text from  DAILY_FeeKind   where  isnull(parentId,0)=0  and isnull(status,'')!='0' ";
		var that=this;
		$.ts.EasyUI.loadComboboxData(sql, function(handler,queryList){   
		     that.feeKindParentList=queryList; 
			 var actionType=$("#actionType").val(); 
			 if(actionType!="update"){
				 for(var i=0;i<5;i++)
			 		that.insertDatagridRow();
			 }
		},{async:false}); 
	}, 
	insertDatagridRow:function(rowBean){
		var rowOpt={};
		if($.ts.Utils.isEmpty(rowBean)==false){
			rowOpt=rowBean;
		}
	   $('#dtlListGrid').datagrid('appendRow',rowOpt);
	   var table=$('#dtlListGrid'); 
	   var ids=document.getElementsByName("dtlList.id");  
	   var  row=$(ids[ids.length-1]).parent().parent().parent() ;
	   var feeKindParentJq=row.find("#dtlList\\.feeKindParentId");
	    $.parser.parse(row);
		var that=this; 
		
	   /** feeKindParentJq.combobox("loadData", this.feeKindParentList);  
	    feeKindParentJq.combobox({    
			 onSelect:function(rec){    
				 that.dynamicLoadFeeKinds(row,rec,0); 
			 }  
		 });   **/
		 
  },
   dynamicLoadFeeKinds:function(row,rec,value){
	    var feeKindParentJq=$(row.find("#dtlList\\.feeKindParentId")[0]);
		/**var feeKindJq=$(row.find("#dtlList\\.description")[0]);
	    var parentId=rec.id;
		var sql="select  name as id , name as text from  DAILY_FeeKind   where  isnull(parentId,0)="+ parentId;
		sql=sql+" and isnull(status,'')!='0' ";  
		 
		$.ts.EasyUI.loadComboboxData(sql, function(handler,queryList){   
		     feeKindJq.combobox('setValue', value);
			 feeKindJq.combobox("loadData", queryList);  
		});**/
   },
   validateForm:function(){
		 
		if($.trim($("#bean\\.applyAmount").val())=="" ){
			$.messager.alert('提示',$($.find("label[for='bean.applyAmount']")[0]).text()+"不能为空！"); 
			return false;
		}
		/**if($.trim($("#bean\\.applyDate").val())=="" ){
			$.messager.alert('提示',$($.find("label[for='bean.applyDate']")[0]).text()+"不能为空！"); 
			return false;
		}**/
		 
		return true;
	},
    submitForm:function (obj){ 
	      if(this.validateForm()==false) return false;    
	     var bodyId="#"+this.opts.appKey+"Body"; 
		 var formId= "#"+this.opts.appKey+"Form";
		 var url=$(formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 //$(bodyId).mask(); 
		 var index=$.ts.EasyUI.submitRowsToList("dtlList.id",$("#dtlListGrid").parent(),{effectRowFields:["amount"]}); 
		 if(index==0){
			 $.messager.alert('提示',"摘要记录不能为空！"); 
			 return false;
		 }
		 $.ts.EasyUI.submitRowsToList("fileList.id",$("#fileListGrid") );   
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(resultData){
			 $.ts.EasyUI.closeDialog(obj);
			  if($.ts.Utils.isEmpty(resultData.processInstanceId)==false){
				 var urls=tsContextPath+"/core/workFlowAuditService/addWorkFlowAudit.do?processInstanceId="+resultData.processInstanceId+"&businessKey="+resultData.billId+"&id="+resultData.billId;
				 $.ts.EasyUI.modalDialog(  {  
					width : 800, 
					height : $(document).height()-5,
					modal:false,
					title:"提交审核单据",
					method:"POST", 
					cache:false,
					collapsible: true,  
					maximizable: true, 
					maximized:true,
					resizable:true, 
					href : urls   
				} ); 
			  }
		 } );
	  	  
		return false;
	 } 
 }
 
 