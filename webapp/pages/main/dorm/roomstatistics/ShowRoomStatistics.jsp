<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/fn" prefix="fn"%>
<%@ taglib uri="/tags/c-rt" prefix="c"%>
<%@ taglib uri="/tags/tstag" prefix="ts"%>
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">   
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<style type="text/css">
.rmstatus {
	padding: 2px;
}

.peopleSum {
	text-align:center;
}

.text-overflow {
	display: block; /*内联对象需加*/
	word-break: keep-all; /* 不换行 */
	white-space: nowrap; /* 不换行 */
	overflow: hidden; /* 内容超出宽度时隐藏超出部分的内容 */
	text-overflow: ellipsis;
	/* 当对象内文本溢出时显示省略标记(...) ；需与overflow:hidden;一起使用。*/
}
.totalInfo {
	margin: 0px auto;
	border: 1px solid #CCEFF5;
	background-color: #FAFCFD;
	font-family: 宋体;
	font-size: 12px;
}
</style>
</head>
<body id="${appReqeustContext.appKey}Body" class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<!-- 左侧 -->
	<div data-options="region:'west',split:true,title:'所在楼宇', fit:false" style="width: 150px;">
		<ul id="leftTree" class="ztree"></ul>
	</div>
	<div data-options="region:'center',title:'房间状态'">
		<div id="room"></div>
	</div>
	
	<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>  
	<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="<ts:base ref='path'/>/RoomStatistics.js"></script>
	<script type="text/javascript">
    var setting = {
    		data: {
    			simpleData: {
    				enable: true
    			}
    		},
    		view: {
    			selectedMulti: false
    		},
    		callback: {
    			onClick:function(e, id, node){
    				//var zTree = $.fn.zTree.getZTreeObj("leftTree");
    				//var buildKind = node.buildingKind;
    				//window.frames["rightFrame"].location.href='${contextPath}/main/roomStatisticsService/showRoom.do?id=' + node.id + "&buildingKind=" + node.buildingKind;
    				$.ajax({
    					type:'POST',
    					dataType:'html',
    					cache:false,
    					url:'${contextPath}/main/roomStatisticsService/json/showRoom.do',
    					data:'id=' + node.id + "&buildingKind=" + node.buildingKind,
    					beforeSend:function(XMLHttpRequest){
    						$("#room").html("<center><p><img src='${contextPath}/resource/image/icon/ajax_loading.gif' alt='' border='0' /></p></center>");
    					},
    					success:function(data, textStatus){
    						$("#room").html(data);
    						$('.tooltipTD').tooltip({
    							position:'top',
    							trackMouse:true,
    							content:this.title,
    							onShow:function(){
    								$(this).tooltip('tip').css({
    									backgroundColor: '#f7f5d1',
    									border:'1px solid #333',
    								});
    							}
    						});
    					}
    				});
    			}
    		}
    	};
    var zNodes = ${treeData};
	$(document).ready(function(){
		$.fn.zTree.init($("#leftTree"), setting, zNodes);
	});
	</script>
</body>
</html>