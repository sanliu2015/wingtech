<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<meta name="viewport" content="width=device-width, initial-scale=1">
<ts:base />
<link href="${contextPath}/scripts/bootstrap-3.3.5/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/bootstrap-3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/layer/layer.js"></script>
<title>会议室一览</title> 
</head>
<body id="body"> 
<div class="panel panel-danger">
  <!-- <div class="panel-heading">
    <h3 class="panel-title">会议室预定情况一览</h3>
  </div> -->
  
  <table id="contentTable" class="table table-striped table-bordered table-condensed">
    	<thead>
			<tr>
				<th>会议主题</th>
				<th>会议室名称</th>
				<th>会议日期</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>是否需要<br />投影仪</th>
				<th>与会人数</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
	<c:forEach items="${dataList}" var="dtl">
		<tr>
			<td>
				${dtl.theme}
			</td>
			<td>
				${dtl.roomName}
			</td>
			<td>
				${dtl.conveneDate}
			</td>
			<td>
				${dtl.beginTime}
			</td>
			<td>
				${dtl.endedTime}
			</td>
			<td>
				<c:choose> 
  					<c:when test="${dtl.needProjector eq 1}"> 
  						是
					</c:when>
					<c:otherwise>  
						否 
					</c:otherwise>
				</c:choose>
			</td>
			<td>
				${dtl.joinSum}
			</td>
			<td>
				${dtl.description}
			</td>
		</tr>	
	</c:forEach>		
  </table>
</div>
<script type="text/javascript" src="<ts:base ref='path'/>/MeetingRoom.js"></script>
<script type="text/javascript">
	var tsContextPath = "${contextPath}";
</script>
</body>
</html>