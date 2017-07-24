<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>Exception!</title>
</head>
<body id="ShowAlertMsgPageBody">
<H2 id="ShowAlertMsgPageBodyH2">模块标识: ${appReqeustContext.appKey}</H2> 
<hr />
<H3>错误描述：</H3>
 ${alertMessage}
 
</body>
</html>