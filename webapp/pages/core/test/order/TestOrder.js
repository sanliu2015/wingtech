var editIndex = undefined;
function numformatter(value){
	if($.ts.Utils.isEmpty(value)) return "";
	var  str=$.ts.Format.formatNumber(value,"#.##");
	//alert(str);
	return str;
}
function TestOrderScript( ){ 
	 this.contextPath=""; 
	 this.appKey=""; 
	 this.opts=null;
 }
 
TestOrderScript.prototype={  
    initPage:function(option){
		this.opts= $.extend({},option);      
		 //$('#testOrderDtlListGrid').datagrid("loadData",[ {"id":"","ageStartQty":"","ageEndQty":"","salaryAmount":"","comments":""}]);
		  
		//$('#bean\\.orderKind').combobox('disableTextbox',{stoptype:'readOnly',activeTextArrow:true,stopArrowFocus:true});
		 this.insertDatagridRow();
		this.insertDatagridRow();
		this.insertDatagridRow();
		$('#testOrderDtlListGrid').datagrid("resize");    
		//scriptInstance.setDataGridPanel();
		 $(window).resize(function(){   
			$("#testOrderDtlListGrid").datagrid({  
				width: $("body").width()
			});                
		  });   
	},
	integerInputFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");   
		return  "<input type='text' name='testOrderDtlList."+fieldName+"' id='testOrderDtlList."+fieldName+"' value='" + val + "'  style='width:100%'  class='easyui-numberbox'/>";
	 },
	  numericInputFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");   
		return  "<input type='text' name='testOrderDtlList."+fieldName+"' id='testOrderDtlList."+fieldName+"' value='" + val + "'  style='width:100%'   class='easyui-numberbox' data-options=\"precision:2,formatter:numformatter\"/>";
	 },
	 textInputFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");   
		return  "<input type='text' name='testOrderDtlList."+fieldName+"' id='testOrderDtlList."+fieldName+"' value='" + val + "'  style='width:100%'  class='easyui-textbox'/>";
	 },
	 textAndSearchInputFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");   
		return  "<input type='text' name='testOrderDtlList."+fieldName+"' id='testOrderDtlList."+fieldName+"' value='" + val + "'  style='width:65%'  class='easyui-textbox'/><a href='javascript:void(0)' class='easyui-linkbutton'  plain=‘true’ iconCls='icon-search' onClick='return testOrderScript.chooseProduct(this);'>选择</a>";
	 },
	 dateInputFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");   
		return  "<input   name='testOrderDtlList."+fieldName+"' id='testOrderDtlList."+fieldName+"' value='" + val + "'  style='width:100%'  class='easyui-my97' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\"  ></input>";
	 },
	  textAreaInputFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");   
		/**return  "<input type='text' name='testOrderDtlList."+fieldName+"' id='testOrderDtlList."+fieldName+"' value='" + val + "'  style='width:100%'  class='easyui-textbox'/>";**/
		return '<select name="bean.orderKind"  id="bean.orderKind" class="easyui-combobox" data-options="editable:false"  style="width:150px;height:30px"><option value="1">abee</option><option value="3">444</option></select>';
	 },
	 fileFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");  
		return  "<input type='file' name='testOrderDtlList."+fieldName+"' id='testOrderDtlList."+fieldName+"' style='width:100%'   />";
	 },
	 hiddenColumnFormat:function(val, row, index) {
		if(val == undefined)	val = "";
		var fieldName=$(this).attr("field");   
		return  "<input type='hidden' name='testOrderDtlList."+fieldName+"' id='testOrderDtlList."+fieldName+"' value='" + val + "'   />";
	 },
	removeRowFormat:function(val, row, index) {
		if(val == undefined)	val = ""; 
		var  text='<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-cancel"  onClick="$.ts.EasyUI.deleteTableRow(this)"></a> '; 
		return  text;
	 },
	 
	chooseProduct:function(obj){  
	      var divId=(new Date()).getTime()+"0";
		  var url=tsContextPath+"/core/reportResolver/chooseRow.do?moduleFileName=MaterialReport&tsFilterSql={ and name like '%25v%25'}"; 
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择产品",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){
							  var row=$(obj).parent().parent().parent(); 
							  row.find("#testOrderDtlList\\.material\\.name").textbox('setValue',json.name); 
							  row.find("#testOrderDtlList\\.material\\.number").textbox('setValue',json.number);  
							  row.find("#testOrderDtlList\\.material\\.id").val(json.id); 
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	chooseEmployee:function(obj){   
		  var url=tsContextPath+"/core/reportResolver/chooseRow.do?moduleFileName=EmployeeSerivceReport"; 
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择员工",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							  $("#bean\\.employee\\.name").textbox('setValue',json.name);  
							  $("#bean\\.employee\\.id").val(json.id);  
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	chooseCust:function(obj){   
		  var url=tsContextPath+"/core/reportResolver/chooseRow.do?moduleFileName=CustomerSerivceReport"; 
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择员工",   
				href : url   
			  } , function(json){  
					  try{  
						 if(json!=null){ 
							  $("#bean\\.customer\\.name").textbox('setValue',json.custName);  
							  $("#bean\\.customer\\.id").val(json.id);  
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	onClickRow:function(index){
			if (editIndex != index){
				if (1==1){
					$('#testOrderDtlListGrid').datagrid('selectRow', index)
							.datagrid('beginEdit', index);
					editIndex = index;
				} else {
					$('#testOrderDtlListGrid').datagrid('selectRow', editIndex);
				}
			}
		},
	insertDatagridRow:function(){
	   $('#testOrderDtlListGrid').datagrid('appendRow',{ });
	   var table=$('#testOrderDtlListGrid'); 
	   var ids=document.getElementsByName("testOrderDtlList.id"); 
	   
	   var  row=$(ids[ids.length-1]).parent().parent().parent() ;
	   $.parser.parse(row)
	   //$('#testOrderDtlListGrid').datagrid('beginEdit', 0);
  },
   
    submitForm:function (obj){//点击按钮提交   
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId="#"+this.opts.appKey+"Form";
		 var url=$(formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 //$(bodyId).mask(); 
		 $.ts.EasyUI.submitRowsToList("testOrderDtlList.id",$("#testOrderDtlListGrid").parent()); 
		 $.ts.EasyUI.submitRowsToList("testOrderFileList.id",$("#testOrderFileListGrid") );  
		 $.ts.EasyUI.ajaxSubmitForm(url,this.opts.appKey+"Form" );
	  	  
		return false;
	 } 
 }
 
 