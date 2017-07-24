
(function ($) {
	function init(target,options){ 
		options= $.extend($.fn.bsTabs.defaults, options);  
		$(target).data("options",options);
		hiddenContextMenu(target);
		return target.each(function() {  
			 $this = $(target);  
			 var tbCnt = addTabContent(target); 
			 if (tbCnt.children().length === 0) { 
				$(target).find('li>a').each(function(index, element) {  
				    var opts=$.ts.parseOptions(element);
					setTabOptions(target,element,opts); 
					addTabPanel(target,element); 
					bindTabContextMenu(target,element);
                });   
			}  
			bindTabEvents( target );
		});
	} 
	function hiddenContextMenu(target){
		var id=$(target).attr("id")+"-contextMenu";
		var contextMenuId=$(target).data("options").contextMenu;
		if(contextMenuId!=""){
			id=contextMenuId;
		}
		var menu=$("#"+id); 
		if(menu.length>0){
			$(document).mousedown(function(e) {
				menu.hide();
			}); 
		}
	}
	 
	function bindTabContextMenu(target,tab){
		var id=$(target).attr("id")+"-contextMenu";
		var contextMenuId=$(target).data("options").contextMenu;
		if(contextMenuId!=""){
			id=contextMenuId;
		}  
		var menu=$("#"+id);
		if(menu.length<=0) return; 
		$(tab).bind("contextmenu", function(e) { 
			e.stopPropagation();
			e.preventDefault();
			if(3 == e.which){   
				var tp=$.ts.getPosition(e,menu);  
				tp.top= $(tab).offset().top+$(tab).outerHeight();
				tp.left=parseInt($(tab).offset().left);//+parseInt($(tab).outerWidth()); 
				menu.data("",$(tab).attr("id")); 
				menu.css(tp);
				menu.show();
				return false;
			}
		});
	}
	function getTabContent(target){
		return  $(target).next("div.tab-content");   
	}
	function addTabContent(target){
		$this = $(target);  
		var tbCnt =getTabContent(target);
		if (tbCnt.length === 0) {
			var div_tab_content = [];
			var div_content_container = [];
			div_content_container.push("<div class='tab-content'>");
			div_content_container.push("</div>"); 
			$this.parent().append(div_content_container.join(""));
			tbCnt = getTabContent(target); 
		}
		return tbCnt;
	}
	function addTabPanel(target,tab){
		if($(tab).hasClass("dropdown-toggle")) return;
		$this = $(target);  
		var tbCnt = addTabContent(target);
		var href=$(tab).attr("href");
		
		var contentId="";
		if(typeof(href)!="undefined"){
			var hash = href.indexOf("#");
			if(href.indexOf("#")>=0) 
				contentId=href.substr(hash+1);
			if(hash>0){
				var url=href.substr(0, hash); 
				$(tab).attr("url",url);
				$(tab).attr("href",href.substr(hash));
			}
		}
		if(contentId==""){
			var id=$(tab).attr("id"); 
			if(id=="" || typeof id=="undefined"){
				var selector_no_hash = $(target).selector.substr(1); 
				var index=tbCnt.children().length;
				id=selector_no_hash+index ;
				contentId=id+"-content";
				$(tab).attr("id",id);
			} else {
				contentId=id+"-content";
			}
		}  
		$(tab).attr("href","#"+contentId);			
		var div_tab_content = []; 
		div_tab_content.push("<div class='tab-pane fade' id='"+contentId+"'>");
		div_tab_content.push("</div>");  
		tbCnt.append(div_tab_content.join(""));
	}
	function parseOptions(target, properties){
		var t = $(target);
		var options = {}; 
		var s = $.trim(t.attr('tsdata-options'));
		if (s){ 
			if (s.substring(0, 1) != '{'){
				s = '{' + s + '}';
			}
			options = (new Function('return ' + s))();
		} 
		if (properties){
			var opts = {};
			for(var i=0; i<properties.length; i++){
				var pp = properties[i];
				if (typeof pp == 'string'){
					if (pp == 'width' || pp == 'height' || pp == 'left' || pp == 'top'){
						opts[pp] = parseInt(target.style[pp]) || undefined;
					} else {
						opts[pp] = t.attr(pp);
					}
				} else {
					for(var name in pp){
						var type = pp[name];
						if (type == 'boolean'){
							opts[name] = t.attr(name) ? (t.attr(name) == 'true') : undefined;
						} else if (type == 'number'){
							opts[name] = t.attr(name)=='0' ? 0 : parseFloat(t.attr(name)) || undefined;
						}
					}
				}
			}
			$.extend(options, opts);
		}
		return options; 
	}
	function loadPage( e){
		var $anchor = $(e.target);   
		var href = $anchor.attr("href"); 
		if(typeof(href)=="undefined"){  
			return true; 
		}
		if( $anchor .hasClass("dropdown-toggle")) return; 
		var hash = href.indexOf("#");
		var target = href.substr(hash);
		href = href.substr(0, hash);  
		if($anchor.attr("url")!="undefined") 
			href=$anchor.attr("url"); 
		var question=(href.indexOf("?")>0?"&":"?");
		href=href+question+"nocache="+new Date().getTime();
		var contentId=$anchor.attr("id")+"-content";   
		$("#"+contentId).addClass("active");
		if(typeof $anchor.attr("clicked")!="undefined")  
			return; 
		$anchor.attr("clicked","1");
		$("#"+contentId).addClass("active"); 
		$("#"+contentId).load(href ,function( response, status, xhr ) { 
		    var target=null;
			var node=getNavTabsTarget($anchor); 
		    var height=parseInt(node.data("options").height);
			if( $("#"+contentId).height()<=height)
				 $("#"+contentId).height(height);
		});
	}
	function getNavTabsTarget(obj){
		var node=obj;
		while(node!=null){
			if(node.hasClass("nav-tabs")){
				break;
			} else {
				node=node.parent();
			}
		}
		return node;
	}
	function loadSinglePage(target,tabOpts){
		var href=tabOpts.url;
		var question=(href.indexOf("?")>0?"&":"?");
		href=href+question+"nocache="+new Date().getTime();
		$(this).load(href ,function( response, status, xhr ) { 
		    var height=parseInt(target.data("options").height);
			if( $(target).height()<=height)
				 $(target).height(height);
		});
	}
	function bindTabEvents(target){  
	    $(target). bind("show", function (e) {  
		      alert(e);   
        }); 
		$(target).find('a').each(function(index, element) {
            bindTabEvent(target,element); 
        });  
	}
	function bindTabEvent(tabs,tab){ 
		$(tab).bind("click", function (e) {   
		     e.preventDefault();//阻止a链接的跳转行为 
			 $(e.target).tab('show');//显示当前选中的链接及关联的conte 
			 loadPage(e);      
        });
		$(tab).on('shown', function (e) { 
			 //e.target // activated tab
			   alert($(e.target).attr("href"));
			//e.relatedTarget // previous tab
		 
		});
	}
	function show(target, tabId){
		var tab=null; 
		if($(target).hasClass("nav-tabs")==false){
			tab=$(target);
		} else {
		  tab=$(target).find( "#"+tabId);
		} 
		if(tab==null || tab.length<=0) return null;  
	    tab.trigger("click");
	}
	function setTabOptions(target,tab,opts){
		var tab_content = [];
		if(opts.closable){
			tab_content.push("<button type=\"button\" class=\"close\" style=\"");
			tab_content.push("position:relative;margin-top:-10px;margin-right:-10px;width:10px\" data-dismiss=\"alert\">×</button>");
		} 
		$(tab).data("options",opts);
		if(tab_content.length>0){
			$(tab).append(tab_content.join(""));
			$(tab).find("button").bind("click",function(e){  
					var e = e || event; 
					closeTab(target,$(tab)); 
				});
			 
		}
	}
	function addTab(target,opts){
		var tab_content = [];
		tab_content.push("<li><a url=\""+opts.url+"\" id=\""+opts.tabId+"\" href=\"#"+opts.tabId+"-content\">"); 
		tab_content.push(opts.title+"</a></li>");
		$(target).append(tab_content.join(""));
	}
	function add(target, opts){
		var tabId=opts.tabId;
		var tab=$(target).find( "#"+tabId); 
		if(tab.length>0){
			if( opts.active!=false) 
				tab.trigger("click");
			return tab;
		}
		addTab(target,opts); 
		tab=$(target).find( "#"+tabId);   
		setTabOptions(target,tab,opts); 
		bindTabEvent(target,tab[0]);
		addTabPanel(target,tab[0]);
		if( opts.active!=false) 
			tab.trigger("click");
		return tab;
	}
	function closeTab(target,tab){  
		if(tab==null || tab.length<=0) return; 
		var tabContent=getTabContent(target);
		var tabPanelId=tab.attr("href");  
		tabPanelId=tabPanelId.substr(1);
		$("#"+tabPanelId).remove();   
		if(tabContent.children().length==0){
			tab.parent().remove();
			tabContent.remove();
		} else {
			var activeTab=getPrevTab(tab);
			if(activeTab==null) 
			  activeTab=getNextTab(tab); 
			tab.parent().remove();
			if(activeTab!=null)  
			  activeTab.trigger("click");
			 
		}
	}
	
	function getPrevTab(tab){
		var prev=tab.parent().prev("li"); 
		if(prev.length>0){
			return prev.find("a:first");
		} else return null;  
	}
	function getNextTab(tab){ 
		var next=tab.parent().next("li");
		if(next.length>0){
			return next.find("a:first");
		} else return null; 
	}
	function getTabCount(target){ 
		return $(target).find("li>a").length;
	}
	$.fn.bsTabs= function (options, param) {
	    if (typeof options == 'string'){
			return $.fn.bsTabs.methods[options](this, param);
		}  
		options = options || {}; 
		return  init(this,options);
	}; 
	$.fn.bsTabs.methods = {
		show: function(jq, param){
			return jq.each(function(){
				show(this, param);
			});
		},
		add:function(jq,param){
			return jq.each(function(){
				add(this, param);
			});
		},
		loadPage:function(jq,param){
			return loadSinglePage(jq[0], param);
		},
		getTabCount:function(jq,param){
			 return getTabCount(jq, param); 
		},
		close:function(jq,param){
			return jq.each(function(){
				if(jq.hasClass("nav-tabs")){
					var tab=this.find("#"+param);
					closeTab(this, tab);
				} else {
					closeTab(this.parentsUntil(".nav-tabs"),this);
				}
			});
		},
		exists: function(jq, which){
			return exists(jq[0], which);
		},
		getTab: function(jq, which){
			return getTab(jq[0], which);
		}
	};
	$.fn.bsTabs.defaults = {
        maskMsg:'加载中……',
        tabContent:"", 
		height:400,
		singlePage:false,
		contextMenu:"",
		onAdd: function(title, index){}
    };
})(jQuery ); 
 