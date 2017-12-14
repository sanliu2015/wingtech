<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="${contextPath}/scripts/bootstrap-3.3.5/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/bootstrap-3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/layer/layer.js"></script>
<title>会议室一览</title> 
</head>
<body id="body">
<audio id="errorAudio" src="${contextPath}/pages/main/dorm/empenter/error.mp3">  
         你的浏览器不支持video标签。
</audio> 
  <div class="panel-body">
    <form class="form-inline">
	  <div class="form-group">
	    <label for="name">工卡</label>
	    <input type="text" class="form-control" id="icNo" placeholder="请刷工卡"   >
	    <span id="errorMsg" style="color:red;"></span>
	    <label for="name">厂门</label>
	    	<select name="door" id="door" class="form-control">
	    	<option value="">请选择</option>
	    		<c:forEach items="${doorList}" var="dtl">   
	    		<option value="${dtl.code}">${dtl.name}</option>
	    		</c:forEach>
			</select>
	  </div>
	</form>
	<br/>
 	<table class="table table-bordered">
    <thead>
      <tr style="height:100px;">
	   	<th style="width:7%;line-height:100px;">姓名</th>
	   	<td style="width:30%;line-height:100px;" id="name"></td>
	   	<th style="width:7%;line-height:100px;">工号</th>
	   	<td style="width:30%;line-height:100px;" id="number"></td>
	   	<td style="width:300px;text-align:center;vertical-align:middle;" rowspan="3">
	   		<img height="280px" width="200px;" id="photo" src="${contextPath}/upload/employee/001.jpg" alt="暂无相片"/>
	   		<span id="idCard" style="color:red"></span>
	   	</td>
      </tr>
	  <tr style="height:100px;">
	   	<th style="width:7%;line-height:100px;">性别</th>
	   	<td style="width:30%;line-height:100px;" id="gender"></td>
	   	<th style="width:7%;line-height:100px;">职位</th>
	   	<td style="width:30%;line-height:100px;" id="posName"></td>
      </tr>
      <tr style="height:100px;">
	   	<th style="width:7%;line-height:100px;">部门</th>
	   	<td style="width:30%;line-height:100px;" id="parentDepName"></td>
	   	<th style="width:7%;line-height:100px;">科室</th>
	   	<td style="width:30%;line-height:100px;" id="depName"></td>
      </tr>
     </thead>
     </table> 
  </div>

  
<script type="text/javascript">
	var tsContextPath = "${contextPath}";
	$(function(){
		$("#icNo")[0].focus();
		
		document.onkeydown = function(event){
			var e = event || window.event || arguments.callee.caller.arguments[0];
			if(e && e.keyCode==13){ // enter 键
				if ($("#icNo").val() == "") {
					alert("工卡不能为空!");
				} else {
					debugger;
					if ($("#door").val() == "") {
						alert("门号不能为空!");
						return false;
					}
					
					$.ajax({
						type:"post",
						url: tsContextPath + "/main/empEnterService/json/doCheck.do?timestamp=" + new Date().getTime(),
						data:{"door":$("#door").val(),"icNo":$("#icNo").val().replace(/\b(0+)/gi,"")},
						dataType:"json",
						success:function(result,textStatus) {
							if (result.success == "1") {
								$("#errorMsg").text("");
								$("#name").text(result.name);
								$("#number").text(result.number);
								$("#gender").text(result.gender);
								$("#posName").text(result.posName);
								$("#depName").text(result.depName);
								$("#parentDepName").text(result.parentDepName);
								$("#photo").attr("src", tsContextPath + "/upload/employee/" + result.idCard + ".jpg");
								$("#idCard").text(result.idCard);
							} else {
								$("#errorMsg").text(result.errorMsg);
								$('#errorAudio')[0].play();
								$("#name").text("");
								$("#number").text("");
								$("#gender").text("");
								$("#posName").text("");
								$("#depName").text("");
								$("#parentDepName").text("");
								$("#photo").attr("src", "");
							}
							
						},
						error: function(XmlHttpRequest, textStatus, errorThrown) {  
							var str=XmlHttpRequest.responseText; 
							if(str == "" || str==undefined || str==null || str=="null"){
								str=XmlHttpRequest.responseXML;
							}
							str=str+"<hr/>"+textStatus;
							var index = layer.open({
								  type: 1,
								  content: str,
								  maxmin: true
								});
							layer.full(index);
							return false;
						}
					});
				}
				$("#icNo").select();
				return false;
			}
		}
	});
</script>
</body>
</html>