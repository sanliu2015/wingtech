
(function ($) {
	function init(options){
		alert('a');
		options= $.extend($.fn.BootstrapTabs.defaults, options); 
		var selector_no_hash = $(this).selector.substr(1); 
		return this.each(function() {  
			 $this = $(this);  
			 var tbCnt = $this.next("div.tab-content"); 
			 if (tbCnt.length === 0) {
				var div_tab_content = [];
				div_tab_content.push("<div class='tab-content'>")
				div_tab_content.push("<div class='tab-pane active' id='" + selector_no_hash + "-content'>");
				div_tab_content.push("</div>");
				div_tab_content.push("</div>"); 
				$this.parent().append(div_tab_content.join(""));
			}
		});
	}
	function show(target, tabId){
		alert( );
	}
	$.fn.BootstrapTabs.methods = {
		show: function(jq, param){
			return jq.each(function(){
				show(this, param);
			});
		} 
	};
	$.fn.BootstrapTabs= function (options, param) {//options 经常用这个表示有许多个参数。
	    alert(options);
	    if (typeof options == 'string'){
			return $.fn.BootstrapTabs.methods[options](this, param);
		}  
		options = options || {}; 
		return  init(options);
	}; 
	$.fn.BootstrapTabs.defaults = {
        maskMsg:'加载中……',
        tabContent:"",
        timeout:30000,
        opacity:0.6
    };
})(jQuery ); 
 