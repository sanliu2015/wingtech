
function ImportExcelScript( ){  
	 this.appKey=""; 
	 this.opts=null;
 }
ImportExcelScript.prototype={ 
      
    initPage:function(option){
		this.opts= $.extend({},option);  
		this.loadDepartmentTree();     
	},  
	uploadFile:function(){
		$("#importType").val("getSheet");
		 var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(data){
			 var value=data.customProperty["0"];
			 var i=0;
			 var queryList=new Array();
			  var bean=data.customProperty;
			 while($.ts.Utils.isEmpty(value)==false){
				 var key= i.toString(); 
			 	 value=bean[key]; 
				 queryList[i]={"value":key,"text":value};
				 i++;
				 key= i.toString();
				  value=bean[key];
				
				
			 }
			 $('#sheetIndex').combobox("loadData", queryList); 
		 });  
		 
	},
	changeSheet:function(){
		var that=this;
		 $("#sheetIndex").combobox({    
			 onSelect:function(rec){    
				 that.loadBulletinTopic(); 
			 }  
		 });  
	},
	loadDepartmentTree:function(){  
	      
		  var urlJson={urlType:"combotree",
		            moduleFileName:"DepartmentCombotree", 
		            timeStamp:""+(new Date()).getTime()}; 
			var url=$.ts.Utils.toUrlParam(urlJson);  
		   $("#sysListLinkSetId").combotree({
			 url: url,
			 multiple: false,
			 required: false,
			 async:true 
		 }) 
	}, 
	validateForm:function (obj){//点击按钮提交  
	    if( document.getElementById("file").value==""){
			  $.messager.alert('提示',"导入的文件不能为空！"); 
			 return false;
		 } 
		 if( document.getElementsByName("sheetIndex")[0].value==""){
			  $.messager.alert('提示',"工作薄不能为空！"); 
			 return false;
		 }
		 $("#validateOperae").val("1");
		 $("#importType").val("save");
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		  var index=$.ts.EasyUI.submitRowsToList("mapRuleList.id",$("#importRuleTable")); 
		  
		  
		 var url=tsContextPath+'/core/importExcelDataCoreService/json/validateImportExcelData.do?timeStamp='+(new Date()).getTime();  
		 $.ts.EasyUI.ajaxSubmitForm(url,formId ); 
		return false;
	 } ,
    submitForm:function (obj){//点击按钮提交  
	    if( document.getElementById("file").value==""){
			  $.messager.alert('提示',"导入的文件不能为空！"); 
			 return false;
		 } 
		 if( document.getElementsByName("sheetIndex")[0].value==""){
			  $.messager.alert('提示',"工作薄不能为空！"); 
			 return false;
		 }
		 $("#importType").val("save");
		 $("#validateOperae").val("0");
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		  var index=$.ts.EasyUI.submitRowsToList("mapRuleList.id",$("#importRuleTable")); 
		  
		  
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(data){
			 if($.ts.Utils.isEmpty(data)==false){
				  $.messager.show({
					title:'导入提示',
					msg:'共导入'+data.billId+'条记录！',
					timeout:$.ts.constant.timeout,
					style:{
						right:'',
						bottom:''
					}
				 }); 
			 }
			parent.mainScript.closeCurrentTab();
		 }); 
		return false;
	 } 
 }
 
 