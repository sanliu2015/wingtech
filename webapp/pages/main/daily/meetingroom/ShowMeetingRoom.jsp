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
  <div class="panel-body">
    <form class="form-inline">
	  <div class="form-group">
	    <label for="name">会议室名称</label>
	    <input type="text" class="form-control" id="name" placeholder="某某会议室">
	  </div>
	  <div class="form-group">
	    <label for="capacity">可容纳人数</label>
	    <input type="number" class="form-control" id="capacity" style="width:90px;" placeholder="10">
	  </div>
	  <button type="button" class="btn btn-danger" onClick="queryData('thisWeek');">本周</button>
	  <button type="button" class="btn btn-primary" onClick="queryData('lastWeek');">上周</button>
	  <button type="button" class="btn btn-success" onClick="queryData('nextWeek');">下周</button>
	 <span style="color:red">【上午：08:00-11:59，下午：12:00-17:59，晚上：18:00-23:59】</span>
	</form>
  </div>
  <table class="table table-bordered">
    <thead>
      <tr id="titleTr">
	    <th style="width:150px;" rowspan="2">会议室名称</th>
	    <th colspan="3" style="text-align:center">星期一<br/><span></span></th>
	    <th colspan="3" style="text-align:center">星期二<br/><span></span></th>
	    <th colspan="3" style="text-align:center">星期三<br/><span></span></th>
	    <th colspan="3" style="text-align:center">星期四<br/><span></span></th>
	    <th colspan="3" style="text-align:center">星期五<br/><span></span></th>
	    <th colspan="3" style="text-align:center">星期六<br/><span></span></th>
	    <th colspan="3" style="text-align:center">星期日<br/><span></span></th>
      </tr>
      <tr id="timeTitle">
      	<th style="width:55px;">上午</th>
      	<th style="width:55px;">下午</th>
      	<th style="width:55px;">晚上</th>
      	<th style="width:55px;">上午</th>
      	<th style="width:55px;">下午</th>
      	<th style="width:55px;">晚上</th>
      	<th style="width:55px;">上午</th>
      	<th style="width:55px;">下午</th>
      	<th style="width:55px;">晚上</th>
      	<th style="width:55px;">上午</th>
      	<th style="width:55px;">下午</th>
      	<th style="width:55px;">晚上</th>
      	<th style="width:55px;">上午</th>
      	<th style="width:55px;">下午</th>
      	<th style="width:55px;">晚上</th>
      	<th style="width:55px;">上午</th>
      	<th style="width:55px;">下午</th>
      	<th style="width:55px;">晚上</th>
      	<th style="width:55px;">上午</th>
      	<th style="width:55px;">下午</th>
      	<th style="width:55px;">晚上</th>
      </tr>
    </thead>
  </table>
</div>
<script type="text/javascript" src="<ts:base ref='path'/>/MeetingRoom.js?v201707201900"></script>
<script type="text/javascript">
	var tsContextPath = "${contextPath}";
</script>
</body>
</html>