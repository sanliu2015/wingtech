$.fn.panel.defaults.loadingMessage = '加载中....';
$.fn.datagrid.defaults.loadMsg = '加载中....';

 
/**
 * @author tgp
 * 
 * @requires jQuery,EasyUI
 * 
 * panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题
 */
$.fn.panel.defaults.onBeforeDestroy = function() {
	var frame = $('iframe', this);
	try {
		if (frame.length > 0) {
			for ( var i = 0; i < frame.length; i++) {
				frame[i].src = '';
				frame[i].contentWindow.document.write('');
				frame[i].contentWindow.close();
			}
			frame.remove();
			if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
				try {
					CollectGarbage();
				} catch (e) {
				}
			}
		}
	} catch (e) {
	}
};

/**
 * @author tgp 
 * @requires jQuery,EasyUI 
 * 防止panel/window/dialog组件超出浏览器边界
 * @param left
 * @param top
 */
var easyuiPanelOnMove = function(left, top) {
	var l = left;
	var t = top;
	if (l < 1) {
		l = 1;
	}
	if (t < 1) {
		t = 1;
	}
	var width = parseInt($(this).parent().css('width')) + 14;
	var height = parseInt($(this).parent().css('height')) + 14;
	var right = l + width;
	var buttom = t + height;
	var browserWidth = $(window).width();
	var browserHeight = $(window).height();
	if (right > browserWidth) {
		l = browserWidth - width;
	}
	if (buttom > browserHeight) {
		t = browserHeight - height;
	}
	$(this).parent().css({/* 修正面板位置 */
		left : l,
		top : t
	});
};
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.panel.defaults.onMove = easyuiPanelOnMove;

/**
 * @author tgp
 * 
 * @requires jQuery,EasyUI
 * 
 * 通用错误提示
 * 
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 */
var easyuiErrorFunction = function(XMLHttpRequest) {
	$.messager.progress('close');
	$.messager.alert('错误', XMLHttpRequest.responseText);
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

/**
 * @author tgp
 * 
 * @requires jQuery,EasyUI
 * 
 * 为datagrid、treegrid增加表头菜单，用于显示或隐藏列，注意：冻结列不在此菜单中
 */
var createGridHeaderContextMenu = function(e, field) {
	e.preventDefault();
	var grid = $(this);/* grid本身 */
	var headerContextMenu = this.headerContextMenu;/* grid上的列头菜单对象 */
	if (!headerContextMenu) {
		var tmenu = $('<div style="width:100px;"></div>').appendTo('body');
		var fields = grid.datagrid('getColumnFields');
		for ( var i = 0; i < fields.length; i++) {
			var fildOption = grid.datagrid('getColumnOption', fields[i]);
			if (!fildOption.hidden) {
				$('<div iconCls="tick" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
			} else {
				$('<div iconCls="bullet_blue" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
			}
		}
		headerContextMenu = this.headerContextMenu = tmenu.menu({
			onClick : function(item) {
				var field = $(item.target).attr('field');
				if (item.iconCls == 'tick') {
					grid.datagrid('hideColumn', field);
					$(this).menu('setIcon', {
						target : item.target,
						iconCls : 'bullet_blue'
					});
				} else {
					grid.datagrid('showColumn', field);
					$(this).menu('setIcon', {
						target : item.target,
						iconCls : 'tick'
					});
				}
			}
		});
	}
	headerContextMenu.menu('show', {
		left : e.pageX,
		top : e.pageY
	});
};
$.fn.datagrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
$.fn.treegrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;

/**
 * grid tooltip参数
 * 
 * @author tgp
 */
var gridTooltipOptions = {
	tooltip : function(jq, fields) {
		return jq.each(function() {
			var panel = $(this).datagrid('getPanel');
			if (fields && typeof fields == 'object' && fields.sort) {
				$.each(fields, function() {
					var field = this;
					bindEvent($('.datagrid-body td[field=' + field + '] .datagrid-cell', panel));
				});
			} else {
				bindEvent($(".datagrid-body .datagrid-cell", panel));
			}
		});

		function bindEvent(jqs) {
			jqs.mouseover(function() {
				var content = $(this).text();
				if (content.replace(/(^\s*)|(\s*$)/g, '').length > 5) {
					$(this).tooltip({
						content : content,
						trackMouse : true,
						position : 'bottom',
						onHide : function() {
							$(this).tooltip('destroy');
						},
						onUpdate : function(p) {
							var tip = $(this).tooltip('tip');
							if (parseInt(tip.css('width')) > 500) {
								tip.css('width', 500);
							}
						}
					}).tooltip('show');
				}
			});
		}
	}
};
/**
 * Datagrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 * 
 * 简单实现，如需高级功能，可以自由修改
 * 
 * 使用说明:
 * 
 * 在easyui.min.js之后导入本js
 * 
 * 代码案例:
 * 
 * $("#dg").datagrid('tooltip'); 所有列
 * 
 * $("#dg").datagrid('tooltip',['productid','listprice']); 指定列
 * 
 * @author 夏悸
 */
$.extend($.fn.datagrid.methods, gridTooltipOptions);

/**
 * Treegrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 * 
 * 简单实现，如需高级功能，可以自由修改
 * 
 * 使用说明:
 * 
 * 在easyui.min.js之后导入本js
 * 
 * 代码案例:
 * 
 * $("#dg").treegrid('tooltip'); 所有列
 * 
 * $("#dg").treegrid('tooltip',['productid','listprice']); 指定列
 * 
 * @author 夏悸
 */
$.extend($.fn.treegrid.methods, gridTooltipOptions);

/**
 * @author tgp
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展validatebox，添加验证两次密码功能
 */
$.extend($.fn.validatebox.defaults.rules, {
	eqPwd : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	}
});
/**
 * @author 夏悸
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展tree，使其可以获取实心节点
 */
$.extend($.fn.tree.methods, {
	getCheckedExt : function(jq) {// 获取checked节点(包括实心)
		var checked = $(jq).tree("getChecked");
		var checkbox2 = $(jq).find("span.tree-checkbox2").parent();
		$.each(checkbox2, function() {
			var node = $.extend({}, $.data(this, "tree-node"), {
				target : this
			});
			checked.push(node);
		});
		return checked;
	},
	getSolidExt : function(jq) {// 获取实心节点
		var checked = [];
		var checkbox2 = $(jq).find("span.tree-checkbox2").parent();
		$.each(checkbox2, function() {
			var node = $.extend({}, $.data(this, "tree-node"), {
				target : this
			});
			checked.push(node);
		});
		return checked;
	}
});

/**
 * @author 夏悸
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展tree，使其支持平滑数据格式
 */
$.fn.tree.defaults.loadFilter = function(data, parent) {
	var opt = $(this).data().tree.options;
	var idFiled, textFiled, parentField;
	if (opt.parentField) {
		idFiled = opt.idFiled || 'id';
		textFiled = opt.textFiled || 'text';
		parentField = opt.parentField;
		var i, l, treeData = [], tmpMap = [];
		for (i = 0, l = data.length; i < l; i++) {
			tmpMap[data[i][idFiled]] = data[i];
		}
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
				if (!tmpMap[data[i][parentField]]['children'])
					tmpMap[data[i][parentField]]['children'] = [];
				data[i]['text'] = data[i][textFiled];
				tmpMap[data[i][parentField]]['children'].push(data[i]);
			} else {
				data[i]['text'] = data[i][textFiled];
				treeData.push(data[i]);
			}
		}
		return treeData;
	}
	return data;
};

/**
 *   
 * @requires jQuery,EasyUI 
 * 扩展treegrid，使其支持平滑数据格式
 */
$.fn.treegrid.defaults.loadFilter = function(data, parentId) {
	var opt = $(this).data().treegrid.options;
	var idFiled, textFiled, parentField;
	if (opt.parentField) {
		idFiled = opt.idFiled || 'id';
		textFiled = opt.textFiled || 'text';
		parentField = opt.parentField;
		var i, l, treeData = [], tmpMap = [];
		for (i = 0, l = data.length; i < l; i++) {
			tmpMap[data[i][idFiled]] = data[i];
		}
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
				if (!tmpMap[data[i][parentField]]['children'])
					tmpMap[data[i][parentField]]['children'] = [];
				data[i]['text'] = data[i][textFiled];
				tmpMap[data[i][parentField]]['children'].push(data[i]);
			} else {
				data[i]['text'] = data[i][textFiled];
				treeData.push(data[i]);
			}
		}
		return treeData;
	}
	return data;
};

/**
 * @author tgp
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展combotree，使其支持平滑数据格式
 */
$.fn.combotree.defaults.loadFilter = $.fn.tree.defaults.loadFilter;

/**
 * @author tgp
 * 
 * @requires jQuery,EasyUI
 * 
 * 创建一个模式化的dialog
 * 
 * @returns $.modalDialog.handler 这个handler代表弹出的dialog句柄
 * 
 * @returns $.modalDialog.xxx 这个xxx是可以自己定义名称，主要用在弹窗关闭时，刷新某些对象的操作，可以将xxx这个对象预定义好
 */
$.modalDialog = function(options) {
	
	if ($.modalDialog.handler == undefined ) {// 避免重复弹出
		var opts = $.extend({
			title : '',
			width : 840,
			height : 680,
			modal : true,
			onClose:function() { 
				$.modalDialog.handler = undefined;
				$(this).dialog('destroy');
			},
			onLoad:function(){ 
				parent.$.messager.progress('close');

			},
			onOpen : function() { 
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
			}
		}, options); 
		//opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
		 var divId=(new Date()).getTime()+"0";
		 $.modalDialog.handler = $('<div id="'+divId+'"/>').dialog(opts);
		 $.modalDialog.handler.data("dialogFormId",divId);
		return $.modalDialog.handler ;
	} else {
		 if($.modalDialog.handler.dialog("options").title!=options.title){
			 var opts = $.extend({
				title : '',
				width : 840,
				height : 680,
				modal : true,
				onClose:function() { 
					$.modalDialog.handler = undefined;
					$(this).dialog('destroy');
				},
				onLoad:function(){ 
					parent.$.messager.progress('close');
	
				},
				onOpen : function() { 
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
				}
			}, options); 
			//opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
			var divId=(new Date()).getTime()+"0";
		    $.modalDialog.handler = $('<div id="'+divId+'"/>').dialog(opts);
		    $.modalDialog.handler.data("dialogFormId",divId);
		    return $.modalDialog.handler ;
		 }
	}
};
$.extend($.fn.combo.methods, {
	/**
	 * 禁用combo文本域
	 * @param {Object} jq
	 * @param {Object} param stopArrowFocus:是否阻止点击下拉按钮时foucs文本域
	 * activeTextArrow:是否激活点击文本域也显示下拉列表
	 * stoptype:禁用类型，包含disable和readOnly两种方式
	 */
	disableTextbox: function(jq, param){
		return jq.each(function(){
			param = param || {};
			var textbox = $(this).combo("textbox");
			var that = this;
			var panel = $(this).combo("panel");
			var data = $(this).data('combo');
			if (param.stopArrowFocus) {
				data.stopArrowFocus = param.stopArrowFocus;
				var arrowbox = $.data(this, 'combo').combo.find('span.combo-arrow');
				arrowbox.unbind('click.combo').bind('click.combo', function(){
					if (panel.is(":visible")) {
						$(that).combo('hidePanel');
					}
					else {
						$("div.combo-panel").panel("close");
						$(that).combo('showPanel');
					}
				});
				textbox.unbind('mousedown.mycombo').bind('mousedown.mycombo', function(e){
					e.preventDefault();
				});
			}
			if (param.activeTextArrow) {
				data.activeTextArrow = param.activeTextArrow;
				textbox.bind('click.mycombo', function(){
					if (panel.is(":visible")) {
						$(that).combo('hidePanel');
					}
					else {
						try{ 
						  $("div.combo-panel").panel("close");  
						} catch (e){ }
						$(that).combo('showPanel');
					}
				});
			}
			textbox.prop(param.stoptype ? param.stoptype : 'disabled', true);
			data.stoptype = param.stoptype ? param.stoptype : 'disabled';
		});
	},
	/**
	 * 还原文本域
	 * @param {Object} jq
	 */
	enableTextbox: function(jq){
		return jq.each(function(){
			var textbox = $(this).combo("textbox");
			var data = $(this).data('combo');
			if (data.stopArrowFocus) {
				var that = this;
				var panel = $(this).combo("panel");
				var arrowbox = $.data(this, 'combo').combo.find('span.combo-arrow');
				arrowbox.unbind('click.combo').bind('click.combo', function(){
					if (panel.is(":visible")) {
						$(that).combo('hidePanel');
					} else {
						try{
						   $("div.combo-panel").panel("close"); 
						} catch (e){}
						$(that).combo('showPanel');
					}
					textbox.focus();
				});
				textbox.unbind('mousedown.mycombo');
				data.stopArrowFocus = null;
			}
			if (data.activeTextArrow) {
				textbox.unbind('click.mycombo');
				data.activeTextArrow = null;
			}
			textbox.prop(data.stoptype, false);
			data.stoptype = null;
		});
	}
});
$.extend($.fn.combo.methods, {   
    /**
     * 激活点击文本框也显示下拉面板的功能  
     * @param {Object} jq  
     */  
    activeTextArrow : function(jq) {   
        return jq.each(function() {   
            var textbox = $(this).combo("textbox");   
            var that = this;   
            var panel = $(this).combo("panel");   
            textbox.bind('click.mycombo', function() {   
                if (panel.is(":visible")) {   
                    $(that).combo('hidePanel');   
                } else {   
                    $("div.combo-panel").panel("close");   
                    $(that).combo('showPanel');   
                }   
            });   
        });   
    },   
    /**
     * 取消点击文本框也显示下拉面板的功能  
     * @param {Object} jq  
     */  
    inactiveTextArrow : function(jq) {   
        return jq.each(function() {   
            var textbox = $(this).combo("textbox");   
            textbox.unbind('click.mycombo');   
        });   
    }   
});
(function($){       
	   $.fn.tsLoadData = function(options) {   
	     
	       var recordOperateStatus=options.recordOperateStatus;   
		   if(recordOperateStatus=="lookup"){ 
		     $.ts.Utils.appendLookHistoricProcessButton($(this),options); 
		   }
		 load(this ,options); 
		 function setJqValueAndReadonly(name,value,form){ 
				var f = form.find("input[numberboxName=\"" + name + "\"]");
				
				if (f.length) {
					f.numberbox("setValue", value);
					if("lookup"==recordOperateStatus){
						f.attr("readonly","readonly");
						f.numberbox('readonly',true); 
					}
				} else {
					var inputJq=$("input[id=\"" + name + "\"]", form); 
					if(inputJq.length==0){
						inputJq=$("input[name=\"" + name + "\"]", form); 
					}
					if(inputJq.length>0){
						inputJq.val(value);
						if("lookup"==recordOperateStatus)
							inputJq.attr("readonly","readonly"); 
						if(inputJq.length>0 && typeof(inputJq.attr("class"))!="undefined"){  
							if(inputJq.attr("class").indexOf("easyui-textbox")>=0){ 
								if("lookup"==recordOperateStatus)
									inputJq.textbox('readonly',true);
								inputJq.textbox('setValue',value);  
							} else if(inputJq.attr("class").indexOf("easyui-my97")>=0){ 
								if("lookup"==recordOperateStatus){  
									inputJq.attr("onfocus","");  
									inputJq.attr("click.my97","");  
									inputJq.unbind("onfocus");
									inputJq.removeClass("easyui-my97"); 
								}
								//inputJq.my97('setValue',value);  
							} else if(inputJq.attr("class").indexOf("easyui-combobox ")>=0){ 
								if("lookup"==recordOperateStatus){  
									 inputJq.combobox({"readonly":true} );
								} 
							}
						}  
						
					} else {
						var textareaJq=$("textarea[name=\"" + name + "\"]", form);
						if(textareaJq.length>0){
							textareaJq.val(value);
							if("lookup"==recordOperateStatus)
							textareaJq.attr("readonly","readonly");
						} else {
							var selectJq=$("select[name=\"" + name + "\"]", form);
							if(selectJq.length>0){
								selectJq.val(value);
							}  else {
								spanJq=$("[name=\"" + name + "\"]", form); 
								if(spanJq.length>0){
									spanJq.text(value);
								}
							}
							if("lookup"==recordOperateStatus){
							  //selectJq.attr("disabled",true );
							  selectJq.attr("readonly","readonly");
							  //selectJq.combobox({"readonly":true} );
							}
						}
					} 
				}
				if("lookup"==recordOperateStatus){ 
				    var fieldPath=new $.ts.String(name).replaceAll(".","");
				    var linkbutton=$("#" + fieldPath+"LinkButton", form);
					linkbutton.remove();
				}
							
			}   
		function handleNeedHideDom(form){
			if("lookup"==recordOperateStatus){
				$("[tsOptions*='needHide']",form).hide();
			}
		}
		 function load(target, param) {
              
                loadData(param);
            
            function loadData(dd) {
                var form = $(target);
                var formFields = form.find("span[dataOptions],a[dataOptions],input[name],select[name],textarea[name]");
				handleNeedHideDom(form);
                formFields.each(function(){
                    var name = this.name; 
					
					var haveValue=true;
					
                    var value = jQuery.proxy(function(){try{return eval('this.'+name);}catch(e){ haveValue=false;return  undefined ;}},dd)();  
					  
					if(haveValue==false || typeof(value)=="undefined"){
						if("lookup"==recordOperateStatus){
							setJqValueAndReadonly(name,"",form,status); 
						}
					   return;
					} 
                    var rr = setNormalVal(name,value); 
					/**if(name.indexOf("description")>0){
						alert(name+" length="+rr.length);
					}**/
					setJqValueAndReadonly(name,value,form); 
                    /**if (!rr.length) {
                        var f = form.find("input[numberboxName=\"" + name + "\"]"); 
						setJqValueAndReadonly(name,value,form,status);  
                    }**/
                    setPlugsVal(name,value);
					 
                });
                 
                 
            };
            function setNormalVal(key, val) {
				
                var rr = $(target).find("input[name=\"" + key + "\"][type=radio], input[name=\"" + key + "\"][type=checkbox]");
				 
                rr._propAttr("checked", false);
                rr.each(function () {
                    var f = $(this); 
                    if (f.val() == String(val) || $.inArray(f.val(), val) >= 0) {
                        f._propAttr("checked", true);
                    }
                });
                return rr;
            };
            function setPlugsVal(key, val) {
                var form = $(target);
                var cc = ["combobox", "combotree", "combogrid", "datetimebox", "datebox", "combo"];
                var c = form.find("[comboName=\"" + key + "\"]");
                if (c.length) { 
                    for (var i = 0; i < cc.length; i++) {
                        var combo = cc[i];
                        if (c.hasClass(combo + "-f")) {
                            if (c[combo]("options").multiple) {
                                c[combo]("setValues", val);
                            } else {
                                c[combo]("setValue", val);
                            }
                            return;
                        }
                    }
                }
            };
        };
		     
	  };  
	     
})(jQuery); 
$.extend($.fn.form.methods, {
    tsLoad : function (jq, param) { 
	      
	    var recordOperateStatus=param.recordOperateStatus;
		if(recordOperateStatus=="lookup"){
			 $.ts.Utils.appendLookHistoricProcessButton(jq,param);
		}
        return jq.each(function () {
            load(this, param);
			
        });
        function load(target, param) {
            if (!$.data(target, "form")) {
                $.data(target, "form", {
                    options : $.extend({}, $.fn.form.defaults)
                });
            }
            var options = $.data(target, "form").options;
            if (typeof param == "string") {
                var params = {};
                if (options.onBeforeLoad.call(target, params) == false) {
                    return;
                }
                $.ajax({
                    url : param,
                    data : params,
                    dataType : "json",
                    success : function (rsp) {
                        loadData(rsp);
                    },
                    error : function () {
                        options.onLoadError.apply(target, arguments);
                    }
                });
            } else {
                loadData(param);
            }
			function setJqValueAndReadonly(name,value,form,status){ 
				var f = form.find("input[numberboxName=\"" + name + "\"]");
				if (f.length) {
					f.numberbox("setValue", value);
					if("lookup"==recordOperateStatus)
						f.attr("readonly","readonly");
				} else {
					var inputJq=$("input[name=\"" + name + "\"]", form);
					if(inputJq.length>0){
						inputJq.val(value);
						if("lookup"==recordOperateStatus)
						inputJq.attr("readonly","readonly");
						inputJq.attr("onfocus","");  
						 
					} else {
						var textareaJq=$("textarea[name=\"" + name + "\"]", form);
						 
						if(textareaJq.length>0){
							if("lookup"==recordOperateStatus){
								textareaJq.attr("readonly","readonly");
								if(value!=""){ 
									textareaJq.val(value);
								}
							} else  { 
								textareaJq.val(value); 
							}
						} else {
							var selectJq=$("select[name=\"" + name + "\"]", form);
							if(selectJq.length>0){
								selectJq.val(value);
							} 
							if("lookup"==recordOperateStatus){
							  //selectJq.attr("disabled",true );
							  selectJq.attr("readonly","readonly"); 
							}
						}
					} 
				}
			}
			function handleNeedHideDom(form){
				if("lookup"==recordOperateStatus){
					$("[tsOptions*='needHide']",form).hide();
				}
			}
            function loadData(dd) {
                var form = $(target);
				handleNeedHideDom(form);
                var formFields = form.find("input[name],select[name],textarea[name]");
                formFields.each(function(){
                    var name = this.name; 
					var haveValue=true;
                    var value = jQuery.proxy(function(){try{return eval('this.'+name);}catch(e){
						 haveValue=false;return  undefined ;}},dd)(); 
					 
					if(haveValue==false || typeof(value)=="undefined") {
						if("lookup"==recordOperateStatus){
							setJqValueAndReadonly(name,"",form,status); 
							setNormalVal(name,value);
						}
						
						return;
					} 
                    var rr = setNormalVal(name,value);
                    if (!rr.length) {
						setJqValueAndReadonly(name,value,form,status); 
                    }
                    setPlugsVal(name,value);
                });
                options.onLoadSuccess.call(target, dd);
                $(target).form("validate");
            };
            function setNormalVal(key, val) {
                var rr = $(target).find("input[name=\"" + key + "\"][type=radio], input[name=\"" + key + "\"][type=checkbox]");
                rr._propAttr("checked", false);
                rr.each(function () {
                    var f = $(this); 
                    if (f.val() == String(val) || $.inArray(f.val(), val) >= 0) {
                        f._propAttr("checked", true);
                    }
					if("lookup"==recordOperateStatus){
						f.attr("disabled","disabled");
					}
                });
                return rr;
            };
            function setPlugsVal(key, val) {
                var form = $(target);
                var cc = ["combobox", "combotree", "combogrid", "datetimebox", "datebox", "combo"];
                var c = form.find("[comboName=\"" + key + "\"]");
                if (c.length) {
                    for (var i = 0; i < cc.length; i++) {
                        var combo = cc[i];
                        if (c.hasClass(combo + "-f")) {
                            if (c[combo]("options").multiple) {
                                c[combo]("setValues", val);
                            } else {
                                c[combo]("setValue", val);
                            }
                            return;
                        }
                    }
                }
            };
        };
    }
});