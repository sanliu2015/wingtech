
function onData(event){ 
    try{
		var msg=event.get("msg");  
		if($.ts.Utils.isEmpty(msg)) return;    
		var userIds=event.get("userIds");    
		var eventType=event.get("eventType");   
		if(userIds!="all" && userIds!=""  && "broadcast"!=eventType){   
			if( $.ts.Utils.isEmpty(userIds) || userIds.indexOf(PL.userId)<0) return; 
		}
		var callEvent=event.get("callEvent"); 
		if($.ts.Utils.isEmpty(callEvent)) return;   
		msgPushletScript[callEvent].call(msgPushletScript ,event,msg);   
	} catch(e){}
} 	
 function MainScript( ){  
	 this.checkCodeUrl=""; 
	 this.userId="0";
	 this.opts=null;
 }
  
MainScript.prototype={ 
    setOptions:function(opts){
		var opts= $.extend({},opts);  
		this.userId=opts.userId; 
		this.opts=opts;
		this.opts.bulletins=0;
		this.opts.tasks=0;
		this.initTree();
	},
    initPage:function(option){  
		 this.setOptions(option);
		 this.setMenusEvent();   
		 this.counterTimer();
		 this.ajaxCounterOnLinePers();
		 this.initDesktopTab();
		 this.tabRightEven();
		 this.treeRightMenuBindEvent(); 
		 this.bindLinkClickEvent(); 
	},
	ajaxCounterOnLinePers:function (){
		 $.ajax({
			type: "POST", 
			datatype:"json",
			url : tsContextPath+"/core/userService/json/countOnLinePers.do?operateAuthCheckFlag=0",
			success : function(result) { 
				 $("#mainOnLineCounters").text(result.id); 
			},
			error: function(XmlHttpRequest, textStatus, errorThrown){   
				 
			} 
		});
	 },
	displayRemindMsgRecords:function(){
		var records=this.opts.tasks;//this.opts.bulletins+
		$("#main_taskRecords").text(records);
	},
	bindLinkClickEvent:function(){
		var that=this;
		$("#main_homepageBtn").click(function(){
			that.initDesktopTab();
		});
		$("#main_returnSysBtn").click(function(){
			$.messager.confirm('确认操作', "您确认要退出本系统吗？", function(r) {
			   if(r){  
				   document.location.href=tsContextPath+"/core/userService/logout.do?operateAuthCheckFlag=0&appService=userService&loginstatus=pc&userId="+that.userId;
			   }
			});
		});
		$("#main_modifyPasswordBtn").click(function(){
			  var url=tsContextPath+"/core/userService/editUser.do?operateAuthCheckFlag=0&id="+that.userId;
			  url+="&appUrl=ModifyUserPassword" ; 
			  $.ts.EasyUI.modalDialog(  {  
						width : 480,  height: 300, title:"修改密码", modal:false, method:"POST",  cache:false,
						collapsible: true, maximizable: true, resizable:true,href:url   
			  } );
		});
		$("#main_taskRecordsContainer").click(function(){
			that.refreshTab("待办事件","/core/reportResolver/initPage.do?moduleFileName=WorkFlowAuditServiceReport&openQueryResult=1&operateAuthCheckFlag=0"); 
		});
	},
	setMenusEvent:function(){ 
	     var $this=this; 
		 $("#sysMenu").find("a").each(function(index, element) {  
            var menu=$(element);
			var tabId=menu.attr("id")+"Tab";
			var url=menu.attr("href");
			if(url=="" || url=="#") return ;
			var title=menu.text();
			if(url.indexOf("/")!=0){
				url=tsContextPath+"/"+url;
			}   
			var tabOpts={url:url,tabId:tabId,title:title,closable:true};
			menu.bind("click",function(e){
				 e.preventDefault(); 
				 $("#mytab").bsTabs("add",tabOpts );
				});
        }); 
		 
	},
	initTree:function(){
	   var self=this; 
		var setting = {  
		     isMask:false, 
			callback: { 
					onClick: function(event, treeId, treeNode) { 
					        var tempId=treeNode.name ;   
							if($.trim(treeNode.urlName)=='' || $.trim(treeNode.urlName)=="''"){
								
							} else { 
								 var tempId=treeNode.name ;
								
								 self.refreshTab(tempId,treeNode.urlName); 
							}
					 },
					onRightClick: function(event, treeId, treeNode){
							if (!treeNode && event.target.tagName.toLowerCase() != "button"
											&& $(event.target).parents("a").length == 0) { 
							} else if (treeNode && !treeNode.noR) {
								zTree.selectNode(treeNode);  
								if($.trim(treeNode.urlName)=='' || $.trim(treeNode.urlName)=="''"){
								
								} else {
									$('#treeRightMenu').data("currentTreeNode",treeNode);
									$('#treeRightMenu').menu('show',{
										left: event.pageX,
										top: event.pageY
									}); 
								}
							}
					}
			}
		}; 
		self.setting=setting;
		setting.expandSpeed = ($.support.boxModel && $.support.leadingWhitespace)?"":"fast"; 
		this.refreshMenuTree("automatic");  
			 
   },
    refreshMenuTree:function(operateType){ //manual
      
      /**var treeObj = $.fn.zTree.getZTreeObj("menuTree"); 
      treeObj.reAsyncChildNodes(null, "refresh",null,hiddenTreeCallBack); **/ 
	   $("#menuTree").mask( );  
	   $.ajax({
		   type: "POST", 
		   datatype: "json",
		   async:true,
		   url:tsContextPath+"/core/moduleService/json/getModules.do?operateAuthCheckFlag=0&actionType="+operateType+"&timeStamp="+(new Date()).getTime(), 
		   success: function(data){//complete
			   var setting = mainScript.setting ;   
			   zTree=$.fn.zTree.init($("#menuTree"), setting, data);
			   $("#menuTree").mask("hide"); 
		   } ,
		   error: function (XMLHttpRequest, textStatus, errorThrown) { 
				 $.messager.alert('提示', errorThrown); 
			}  
		});   
   },
   counterTimer:function (){
	try{ 
		  
		  var text="导航面板&nbsp;&nbsp;&nbsp;&nbsp;在线<a href='javascript:void(0)' id='mainOnLineCounters'>1</a>人";
		  text=text+"<img src='"+tsContextPath+"/resource/image/home/return.gif'  height='12px' title='刷新菜单' style='cursor:pointer' onClick='mainScript.refreshMenuTree(\"manual\")'> ";
		 // var header= $("div[region='west']").prevAll(".panel-header").eq(0); 
		  //var header=$("div[region='west']").panel("panel").find(".panel-title").eq(0); 
		  var header=$("div[region='west']").parent().find(".panel-title").eq(0); 
		  //alert($("div[region='west']").parent().find(".panel-title").html()); 
		  header.html(text);  
	 }catch(e){ }
   },
   refreshTab:function(tempId,action){
	   var tab =null;
	   if(!$('#tabs').tabs('exists',tempId)){  
	      var ableClose=true;
		  var iframeId="";
		  if(tempId=="首页") {
			  ableClose=false;
			  iframeId="homePageIframeId";
		  } else {
			  iframeId=(new Date()).getTime()+"IframeId";
		  }
		  if(action.indexOf("?")>0){
			  action=action+"&operateAuthCheckFlag=0";
		  } else {
			  action=action+"?operateAuthCheckFlag=0";
		  }
		  var tab=$('#tabs').tabs('addIframeTab',{
			tab:{
			title:tempId, 
			closable:ableClose,  
			width:'100%', 
			fit:true 
			},
			iframe:{src:tsContextPath+action,id:iframeId,image:tsContextPath+"/resource/image/home/panel_loading.gif"}
		});
		this.initTab($(tab).find(".tabs-inner")); 
		tab=$('#tabs').tabs('select', tempId);
	  } else{	
		tab=$('#tabs').tabs('select', tempId);//选中tt中title为tempId的选项卡
		$('#tabs').tabs('updateIframeTab',{'which':tempId}); 
	  }      
   },
    initTab:function(tabs){
	   $.each(tabs,function(i,n){ 
			$(n).dblclick(function(){ 
				var subtitle = $(this).children("span").text();
				$('#tabs').tabs('close',subtitle);
			})  ;
			$(n).bind('contextmenu',function(e){
				$('#mm').menu('show',{
					left: e.pageX,
					top: e.pageY
				});
				var subtitle =$(this).children("span").text();
				$('#mm').data("currtab",subtitle);
				return false;
			});				
	   });
	    
   }, 
   treeRightMenuBindEvent:function(){
	    var self=this; 
		$('#rm-openNewTab').click(function(){
			var treeNode = $('#treeRightMenu').data("currentTreeNode");
			var action=treeNode.urlName;
			var tempId=treeNode.name;
			var tabs=$('#tabs');
			if(!tabs.tabs('exists',tempId)){  
				self.refreshTab(tempId,action);
			} else {
				var n=1;
				var name=tempId+n;
				while(1==1){
					if(!tabs.tabs('exists',name)){  
						self.refreshTab(name,action);
						break;
					}  else {
						n++;
						name=tempId+n;
					}
				}
			} 
		});
		$('#rm-openTab').click(function(){
			var treeNode = $('#treeRightMenu').data("currentTreeNode");
			var action=treeNode.urlName;
			var tempId=treeNode.name; 
			self.refreshTab(tempId,action);
			 
		});
		$('#rm-insertBookMark').click(function(){
			var treeNode = $('#treeRightMenu').data("currentTreeNode");
			var action=treeNode.urlName;
			var moduleId=treeNode.id;  
			$.ajax({
			   type: "POST", 
			   url: tsContextPath+"/managerBookMark.do",
			   data: "menuId="+moduleId,
			   success: function(msg){
				   alert("添加成功！");
			   }
			}); 
			 
		});
		$('#rm-refreshMenuTree').click(function(){ 
			self.refreshMenuTree( );
			 
		});
   },
   //绑定右键菜单事件
	tabRightEven:function(){
		//刷新
		$('#mm-tabrefresh').click(function(){
			var currtab_title = $('#mm').data("currtab");
			 
			 $('#tabs').tabs('updateIframeTab',{'which':currtab_title});
		});
		//关闭当前
		$('#mm-tabclose').click(function(){
			var currtab_title = $('#mm').data("currtab");
			if(currtab_title!="首页")
				$('#tabs').tabs('close',currtab_title);
		});
		//全部关闭
		$('#mm-tabcloseall').click(function(){
			$('.tabs-inner span').each(function(i,n){
				var t = $(n).text();
				if(t!="首页")
					$('#tabs').tabs('close',t);
			});	
		});
		//关闭除当前之外的TAB
		$('#mm-tabcloseother').click(function(){
			var currtab_title = $('#mm').data("currtab");
			$('.tabs-inner span').each(function(i,n){
				var t = $(n).text();
				if(t!=currtab_title && t!="首页")
					$('#tabs').tabs('close',t);
			});	
		});
		//关闭当前右侧的TAB
		$('#mm-tabcloseright').click(function(){
			var nextall = $('.tabs-selected').nextAll();
			if(nextall.length==0){ 
				return false;
			}
			nextall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t!="首页")
					$('#tabs').tabs('close',t);
			});
			return false;
		});
		//关闭当前左侧的TAB
		$('#mm-tabcloseleft').click(function(){
			var prevall = $('.tabs-selected').prevAll();
			if(prevall.length==0){ 
				return false;
			}
			prevall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				if(t!="首页")
					$('#tabs').tabs('close',t);
			});
			return false;
		}); 
		//退出
		$("#mm-exit").click(function(){ 
			$('#mm').menu('hide');
		});
	}, 
   initDesktopTab:function(){ 
	   this.refreshTab("首页","/main/showBulletinService/showBulletins.do"); 
   },
	closeCurrentTab:function(){
		if($('#tabs')!=null && $('#tabs').length>0 && $('#tabs').tabs("getSelected").length>0){
		  $('#tabs').tabs('close',$('#tabs').tabs("getSelected").panel('options').title);    
		}
	},
	 
	openBulletinDialog:function(msg){
		var bulletinId=msg ;  
    	var url=tsContextPath+"/main/showBulletinService/viewBulletin.do?id="+bulletinId;  
	   var closeCallbackEvent=function(){
		  $.ajax({
					type: "POST",  
					 async:false, 
					 datatype: "json",
					url: tsContextPath+"/main/showBulletinService/json/updateViewerStatus.do?id=" + bulletinId,
					success: function(data, textStatus) {
					}});
	   }
		  $.ts.EasyUI.modalDialog(   {  
			width : 800, 
			height : $(document).height()-5,
			modal:true, 
			method:"POST", 
			title:"查阅内容对话框",
			cache:false,
			collapsible: true,  
			maximizable: true, 
			resizable:true, 
			href : url   
		},closeCallbackEvent ); 
	}
 }
 
 