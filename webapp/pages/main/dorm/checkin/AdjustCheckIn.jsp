<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
<head> 
<title>员工入住</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">   
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<style type="text/css">
.emp {
	width: 200px;
	height: 20px;
	margin: 5px;
	border: 1px solid #ccc;
	background: #AACCFF;
	float:left;
	font-weight: normal;
	color: black;
}

.dp {
	opacity: 0.5;
	filter: alpha(opacity = 50);
}

.over {
	background: #FBEC88;
}
body {
	margin:0px;
}
.room{
	border:1px solid #ccc;
	width:100px;
	min-height:50px;
	height:auto;
	float:left;
	margin:3px;
	font-weight: bold;
	color: red;
}
.total {
	margin: 0px auto;
	background-color: #FFFACD;
	font-family: 宋体;
	font-size: 12px;
}
.box { 
/*非IE的主流浏览器识别的垂直居中的方法*/ 
display: table-cell; 
vertical-align:middle; 
/*设置水平居中*/ 
text-align:center; 
/* 针对IE的Hack */ 
*display: block; 
*font-size: 175px;/*约为高度的0.873，200*0.873 约为175*/ 
*font-family:Arial;/*防止非utf-8引起的hack失效问题，如gbk编码*/ 
width:200px; 
height:360px; 
border: 1px solid #eee; 
} 
.box img { 
/*设置图片垂直居中*/ 
vertical-align:middle; 
}
</style>
</head>
<body id="${appReqeustContext.appKey}Body">
<div id="cc" class="easyui-layout" style="height:400px;"> 
	<div data-options="region:'west',title:'调整房间1', fit:false" style="width:45%;background:#eee;">
		<input type="hidden" id="beforeRoomId" />
		&nbsp;房间1&nbsp;<input name="beforeRoom" id="beforeRoom" class="easyui-textbox" style="width:150px;height:30px" readonly />
		<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return checkInScript.chooseBeforeRoom(this);"  plain='true'></a>
		<div id="beforePanel" style="background:#fafafa;height:90%;" ></div>
	</div>
	<div data-options="title:'',region:'center'" style="background:#eee;width:10%;text-align:center;">
		<div class="box">
			<image src="${contextPath}/resource/image/icon/exchange.png" onClick="exchange();" />
		</div>
	</div>	
	<div data-options="title:'调整房间2',region:'east'" style="background:#eee;width:45%">
		<input type="hidden" id="afterRoomId" />
		&nbsp;房间2&nbsp;<input name="afterRoom" id="afterRoom" class="easyui-textbox" style="width:150px;height:30px" readonly />
		<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search" onClick="return checkInScript.chooseAfterRoom(this);"  plain='true'></a>
		<div id="afterPanel" style="background:#fafafa;height:90%;" ></div>
	</div>
</div>
	<div id="pagePanel" class="easyui-panel dialog-button" width="100%" style="text-align:center;padding:5px;">
 		<a href="javascript:void(0)" class="easyui-linkbutton" id="submitBtn" iconCls="icon-save"  onClick=" return checkInScript.submitFormOnAdjust(this);">保存</a>  
        <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"  onClick="$.ts.EasyUI.closeDialog(this,'0');">关闭</a>      -->
    </div> 	
	<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>  
	<script type="text/javascript" src="${contextPath}/scripts/easyui/extEasyUI.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/ts/TSCore.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/ztree/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${contextPath}/scripts/jquery/json2.js"></script>
	<script type="text/javascript" src="<ts:base ref='path'/>/CheckIn.js"></script>
	<script>
	    var tsContextPath = "${contextPath}";
	    var checkInScript = new CheckInScript(); 
	    $(function(){
	    	$('#beforePanel,#afterPanel').droppable({
				accept:'.emp',
				onDragEnter:function(e,source){
					$(source).draggable('options').cursor='auto';
					$(source).draggable('proxy').css('border','1px solid red');
					$(this).addClass('over');
				},
				onDragLeave:function(e,source){
					$(source).draggable('options').cursor='not-allowed';
					$(source).draggable('proxy').css('border','1px solid #ccc');
					$(this).removeClass('over');
				},
				onDrop:function(e,source){
					$(this).append(source)
					$(this).removeClass('over');
				}
			});
	    });
	    
	    function exchange() {
	    	var beforeStr = $("#beforePanel").html();
	    	var afterStr = $("#afterPanel").html();
	    	 $("#beforePanel").html(afterStr);
	    	 $("#afterPanel").html(beforeStr);
	    }
	</script>
</body>
</html>