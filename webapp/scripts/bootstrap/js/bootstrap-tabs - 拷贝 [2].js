
(function ($) {
	function init(target,options){ 
		options= $.extend($.fn.BootstrapTabs.defaults, options); 
		var selector_no_hash = $(target).selector.substr(1);   
		bindTabEvents( target ); 
		return target.each(function() {  
			 $this = $(target);  
			 var tbCnt = addTabContentContainer(target); 
			 if (tbCnt.children().length === 0) {
				var div_tab_content = []; 
				$(target).find('a').each(function(index, element) { 
					var active=""; 
					var href=$(element).attr("href");
					var contentId="";
					if(typeof(href)!="undefined"){
						var hash = href.indexOf("#");
						if(href.indexOf("#")>=0){
							contentId=href.substr(hash+1)
						}
						if(hash>0){
							var url=href.substr(0, hash); 
							$(element).attr("url",url);
							$(element).attr("href",href.substr(hash));
						}
					}
					if(contentId==""){
						var id=$(element).attr("id"); 
						if(id=="" || typeof id=="undefined"){
							id=selector_no_hash+index ;
							contentId=id+"-content";
							$(element).attr("id",id);
						} else {
							id=id+"-content";
						}
					}  
					$(element).attr("href","#"+contentId);
                    div_tab_content.push("<div class='tab-pane' id='" + contentId+"'>");
					div_tab_content.push("</div>");
                });  
				tbCnt.append(div_tab_content.join(""));
			}
			
			/**
			 $(target).find('a').click(function (e) { 
				  e.preventDefault();//阻止a链接的跳转行为  
				  $(this).tab('show');//显示当前选中的链接及关联的content
				   
			}) **/ 
			
		});
	} 
	function addTabContentContainer(target){
		$this = $(target);  
		var tbCnt = $this.next("div.tab-content");   
		if (tbCnt.length === 0) {
			var div_tab_content = [];
			var div_content_container = [];
			div_content_container.push("<div class='tab-content'>");
			div_content_container.push("</div>"); 
			$this.parent().append(div_content_container.join(""));
			tbCnt = $this.next("div.tab-content");
		}
		return tbCnt;
	}
	function addTabConent(target,tab){
		$this = $(target);  
		var tbCnt = addTabContentContainer(target);
		var div_tab_content = []; 
		div_tab_content.push("<div class='tab-pane' id='" + $(tab).attr("id")+"-content'>");
		div_tab_content.push("</div>");  
		tbCnt.append(div_tab_content.join(""));
	}
	function loadPage( e){
		var $anchor = $(e.target);   
		var href = $anchor.attr("href");
		var hash = href.indexOf("#");
		var target = href.substr(hash);
		href = href.substr(0, hash);  
		if($anchor.attr("url")!="undefined"){
			href=$anchor.attr("url");
		} 
		href=href+"?nocache="+new Date().getTime();
		var contentId=$anchor.attr("id")+"-content";   
		$("#"+contentId).addClass("active");
		if(typeof $anchor.attr("clicked")!="undefined") { 
			return;
		}
		$anchor.attr("clicked","1");
		 $("#"+contentId).addClass("active");
		 $("#"+contentId).load(href ,function( response, status, xhr ) { 
			if( $("#"+contentId).height()<=400)
				 $("#"+contentId).height(400);
		});
	}
	function bindTabEvents(target){  
	    $(target). bind("show", function (e) {  
		      alert(e);   
        });
		
		$(target).find('a').each(function(index, element) {
            bindTabEvent(target,element); 
        }); 
		/** $(target).find('a').bind("click", function (e) {  
		      e.preventDefault();//阻止a链接的跳转行为
			//$(e.target).attr("href","javascript:void(0);");
			 $(e.target).tab('show');//显示当前选中的链接及关联的conte 
			 loadPage(e);      
        });
		 $(target).find('a').on('shown', function (e) { 
			 //e.target // activated tab
			   alert($(e.target).attr("href"));
			//e.relatedTarget // previous tab
		 
		});**/
	}
	function bindTabEvent(tabs,tab){ 
		$(tab).bind("click", function (e) {  
		      e.preventDefault();//阻止a链接的跳转行为
			//$(e.target).attr("href","javascript:void(0);");
			 
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
		$(target).find( "#"+tabId).tab("show");
	}
	function add(target, opts){
		var tabId=opts.tabId;
		var tab=$(target).find( "#"+tabId);
		
		if(tab.length>0){
			tab.click();
			return tab;
		}
		var tab_content = [];
		tab_content.push("<li><a url=\""+opts.url+"\" id=\""+tabId+"\" href=\"#"+tabId+"-content\">");
		tab_content.push(opts.title+"</a></li>");
		$(target).append(tab_content.join(""));
		tab=$(target).find( "#"+tabId);  
		
		bindTabEvent(target,tab[0]);
		addTabConent(target,tab);
		tab.trigger("click");
		return tab;
	}
	$.fn.bsTabs= function (options, param) {
	    if (typeof options == 'string'){
			return $.fn.BootstrapTabs.methods[options](this, param);
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
		}
	};
	$.fn.bsTabs.defaults = {
        maskMsg:'加载中……',
        tabContent:"",
        timeout:30000,
        opacity:0.6
    };
})(jQuery ); 
 