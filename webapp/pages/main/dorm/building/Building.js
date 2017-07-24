
function BuildingScript( ){  
	 this.appKey=""; 
	 this.opts=null;
 }
BuildingScript.prototype={      
    initPage:function(option){
		this.opts= $.extend({},option);   
	},
	chooseEmployee:function(obj){    
	      var urlJson={urlType:"chooseRow",  moduleFileName:"ChoseEmployeeReport",openQueryResult:"1"}; 
		  var url=$.ts.Utils.toUrlParam(urlJson);  
		  var that=this;
		  var handler=$.ts.EasyUI.frameDialog( { 
		        title:"选择员工",   
				href : url   
			  } , function(json){ 
					  try{  
						 if(json!=null){ 
							  $("#bean\\.principal\\.name").textbox('setText',json.name);  
							  $("#bean\\.principal\\.id").val(json.id);   
						 }
					  } catch(e){
						 $.messager.alert('Hint', e); 
					  }
				}
			);    
	},
	
//	loadBuildingCombotree:function(){  
//		  var urlJson={urlType:"combotree",
//		            moduleFileName:"BuildingCombotree",
//		            timeStamp:""+(new Date()).getTime()}; 
//			var url=$.ts.Utils.toUrlParam(urlJson);   
//		   $("#bean\\.parentId").combotree({ 
//			 url:url,
//			 multiple: false,
//			 required: false,
//			 async:true 
//		 }) 
//	},  
    submitForm:function (obj){//点击按钮提交   
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 if ($.trim($("#bean\\.name").val()) == "") {
			 $.messager.alert("警告", "节点名称不能为空!");
			 return false;
		 }
		 
		 if ($.trim($("#bean\\.number").val()) == "") {
			 $.messager.alert("警告", "唯一编码不能为空!");
			 return false;
		 }
		 
		 var kind = $("#bean\\.kind").combobox("getValue");
		 var node = $('#bean\\.parentId').combotree('tree').tree('getSelected');
		 debugger;
		 if (node == null) {
			 if (kind != "0") {
				 $.messager.alert("警告", "上级节点为空时节点属性必须选择栋!");
				 return false;
			 }
		 } else if (kind < node.childKind) {
			 $.messager.alert("警告", "节点属性与上级节点不匹配!");
			 return false;
		 }
		 
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 }); 
		return false;
	 } 
 }
 
 