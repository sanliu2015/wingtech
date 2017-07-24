<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/tags/fn" prefix="fn" %> 
<%@ taglib uri="/tags/c-rt" prefix="c" %> 
<%@ taglib uri="/tags/tstag" prefix="ts" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<ts:base />
  <head> 
  <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/bootstrap/easyui.css"> 
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/scripts/easyui/themes/color.css">  

<script type="text/javascript" src="${contextPath}/scripts/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/scripts/easyui/jquery.easyui.min.js"></script>   
<script language="javascript" src='${contextPath}/scripts/jquery/mask.js'></script>
    
 <script type="text/javascript" src="${contextPath}/scripts/jquery/jquery.form.js"></script>   
 <script language="JavaScript" type="text/javascript" src="${contextPath}/scripts/jquery/plugs/datepicker/WdatePicker.js"></script>
 <script type="text/javascript" src="${contextPath}/scripts/easyui/extEasyUI.js"></script>  
 <script type="text/javascript" src="${contextPath}/scripts/ts/TSCore.js?version=1.1"></script> 
 <script type="text/javascript" src="http://myxdoc.sohuapps.com/xdoc.js"></script> 
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>测试JS</title>
</head>
<body>
 <script id="myxdoc" type="text/xdoc" _format="pdf" style="width:100%;height:100%;"> 
     <xdoc version="9.8.1">
  <meta author="xdoc"/>
  <paper margin="10"/>
  <body sizeType="autosize">
    <para lineSpacing="4" align="center">
      <img line="1111" height="230" name="x55" src="http://img1.imglefeng.com/images/product/000/000/001/742/463/1742463_bg_0_q.jpg" width="230"/>
    </para>
    <para align="center">
      <text fontName="黑体" fontColor="#ffffff" backColor="#ff9900"> 包邮 </text>
      <text fontName="标宋"> 静佳Jplus全身瘦最强攻略3件套装</text>
    </para>
    <para lineSpacing="6" align="center" backColor="#f0f0f0">
      <text fontName="标宋" valign="center" fontSize="21">￥168</text>
      <rect padding="0" height="37" name="x63" width="107" color="">
        <para>
          <path fillColor="#800000" height="18" width="40" shape="M 389 190 L 389 1 L 101 1 L 101 131 L 40 189 Z" color="">
            <para align="right">
              <text fontColor="#ffffff" fontSize="10">4.4折</text>
            </para>
          </path>
        </para>
        <para>
          <text fontColor="#808080" fontStyle="throughline" valign="center">￥390.0</text>
        </para>
      </rect>
      <rect fillColor="#800000" gradual="6" arc="8" fillColor2="#ff0000" href="http://product.lefeng.com/pklist/1742463.html?productId=1742463" align="center" color="" valign="center">
        <para align="center">
          <text fontColor="#ffffff" fontStyle="bold" valign="center">立即疯抢</text>
        </para>
      </rect>
    </para>
  </body>
</xdoc>
    </script> 
 <form action="<c:url value='/app/core/test/testOrder/execute.htm?appMethod=submit'/>" method="post">
    用户名：
    <input type="text" name="user.name"  id="user.name" value="${name}"/>
    <br>
    密 码：
    <input type="user.password" name="user.password" id="password"  />

    <br>
	金额：
    <input type="text" name="user.totalAmouont"  id="user.totalAmouont"  />
	<br>
	<table width="100%" id="userListTable">
		<tr>
			<td>姓名</td>
			<td><input type="text" name="userList.name"  id="userList.name" value=""/></td>
			<td>编号</td>
			<td><input type="text" name="userList.number"  id="userList.number" value=""/></td>
            <td>性别</td>
			<td> <select name="userList.sex" id="userList.sex">
                          <option value="1" selected>第1名</option>
                         <option value="2">第2名</option>
            </select> <input name="pageNumber"  id="pageNumber" type="hidden" value="333"/>  </td>
		</tr>
		<tr>
			<td>姓名</td>
			<td><input type="text" name="userList.name"  id="userList.name" value=""/></td>
			<td>编号</td>
			<td><input type="text" name="userList.number"  id="userList.number" value=""/></td>
             <td>性别</td>
			<td> <select name="userList.sex" id="userList.sex">
                          <option value="1">第1名</option>
                         <option value="2"  selected>第3名</option>
            </select></td>
		</tr>
        <tr>
			<td>姓名3</td>
			<td><input type="text" name="userList.name"  id="userList.name" value=""/></td>
			<td>编号</td>
			<td><input type="text" name="userList.number"  id="userList.number" value=""/></td>
             <td>性别</td>
			<td> <select name="userList.sex" id="userList.sex">
                          <option value="1">第1名</option>
                         <option value="2"   >第2名</option>
                         <option value="3"  selected>第2名</option>
            </select></td>
		</tr>
        
	</table>
    输入日期：   <input type="text" name="dateRangeName"  id="dateRangeName"  /><select name="dateRangeType" id="dateRangeType">
                          <option value="date">今天</option>
                         <option value="week"   >本周</option>
                         <option value="month"  selected>本月</option>
                         <option value="prevMonth"  selected>上个月</option>
                         <option value="halfYear"  selected>半年度</option>
                         <option value="year"  selected>本年度</option>
                         <option value="prevYear"  selected>上年度</option>
                         <option value="prevWeek"  selected>上周</option>
            </select>
    <input type="button" value="测试解析字符" onClick="parseStr()"/>
    <input type="button" value="测试Array"  onClick="testArray()"/>
    <input type="button" value="测试Map"  onClick="testMap()"/>
    <input type="button" value="测试List"  onClick="testList()"/>
    <input type="button" value="测试StringBuffer"  onClick="testStringBuffe()"/>
    <input type="button" value="格式化数字"  onClick="testNumberFormat()"/>
    <input type="button" value="测试工具类Utils"  onClick="testUtils()"/>
    <input type="button" value="测试Number类" id="textNumberId" onClick="testNumber()" recparamtemplate="{&quot;id&quot;:&quot;d&quot;}"/>
    <br/>
    <input type="button" value="测试JsonToSerialize"  onClick="textJsonToSerialize()"/>
    <input type="button" value="测试Dialog对话框"  onClick="showDialog()"/>
     <input type="button" value="测试验证脚本"  onClick="testValidateScript()"/>
      <input type="button" value="提交多行记录"  onClick="testSubmitRows(this)"/>
    <input type="button" value="测试日期范围"  onClick="testGetDateRange()"/>
</form>
<script type="text/javascript"> 
      String.prototype.replaceAll  = function(s1,s2){     
		return this.replace(new RegExp(s1,"gm"),s2);     
	} 
	function testGetDateRange(){
		var dates=$.ts.Utils.getDateRange($("#dateRangeType").val(),$("#dateRangeName").val());
		alert(dates[0]+"  | "+dates[1]);
	}
	function testSubmitRows(obj){
		var nodes=$.find("[name='userList[0].name']");
	    //alert(nodes.length);
		$.ts.EasyUI.submitRowsToList( "userList.name");//,"userListTable");
		 alert(document.getElementById("userListTable").outerHTML);
	}
	function testValidateScript(){
		var rows={name:"eee",number:"1",id:3};
		var str="rows.number==\"2\"";
		function validateMethod(){
			var v=eval(str);
			return v;
		}
		var value=validateMethod();
		alert(value);
		str=" {name:'a',fitemid:3} ";
		var dataOptions=(new Function("return "+str))();  
		 
		alert(dataOptions.name);
		
	}
	function showDialog(){
		var url="${contextPath}/core/moduleService/editModule.do?id=3";
		var dialogOps={title:"eee"};
		$.modalDialog($.extend({ 
				width : 800, 
				height : $(document).height(),
				modal:false,
				method:"POST",    
				collapsible: true,  
				maximizable: true, 
				resizable:true,
				href : url   
			},dialogOps)); 
	}
	 
      function testNumber(){ 
	      alert(jQuery.isFunction(showDialog));
	      var targes="name=# {name}&id=$ {id}&number.id=33";
		  var json=$.ts.Format.paramsToJson(targes);
		  alert(json.number.id);
		  var template= "{\\\"id\\\":\\\"# {id}\\\"}" ;
		  template=new $.ts.String(template).replaceAll("\\\\","");
		  alert(template);
		  var jsonBean=jQuery.parseJSON(template);
		  alert(jsonBean.id);
	       var conf= $.messager.confirm("操作提示", "您确定要执行操作吗？", function (data) {
	            if (data) {
	                new fun();
	            }
	            else {
	                alert("取消22");
	            }
	        });
	     var fun=function (){
			 var str="/core/moduelservice/recJson/add.do";
			 if(str.indexOf("/recJson/")>0){
				 alert(str.indexOf("/recJson/"));
			 }
			 var n=new $.ts.Number("333");
			 alert(n.multiply( Number(3.8) ));
			 alert(n.multiply( 5.3 ));
			 var template='{"id":"333"}';
			 var jsonBean=jQuery.parseJSON(template);
			 alert(jsonBean.id);
		}
	  }
     function testUtils(){  
		 alert($.ts.Utils.isEmpty("  ")+$.ts.Utils.capitalize('abcid'));
		 alert($.ts.Utils.capitalize("ooe"));
	 }
     function testNumberFormat(){ 
		 var str=$.ts.Format.formatNumber(0.8257,"#.#%");
		 alert(str);
		 str=$.ts.Format.numMoneyToChinese(3900.03);
		 alert(str);
		 alert( $.ts.Utils.isNumberic("ae33.20"));
	 }
     function testStringBuffe(){
		 var sb=new $.ts.StringBuffer("sb");
		 sb.append("text");
		 sb.append("testStringBuffe");
		 alert(sb.toString());
		 alert(sb.indexOf("text"));
		 alert(sb.className);
	 }
	 function testMap(){ 
	   var map=new $.ts.Map();
	   map.put("name","223");
	   map.put("number","001");
	   map.put("id",333); 
	   alert(map.get("number"));
	   alert(map.containsKey("id3"));
	}
	function testList(){ 
	   var list=new $.ts.List();
	   list.add("223");
	   list.add("001");
	   list.add( 333); 
	   alert(list.get(0)+":"+list.size()); 
	   alert(list.indexOf(333)); 
	    alert(list.constains("0031")); 
	}
	function testArray(){ 
	   var array=new $.ts.Array();
	   array[0]="a";
	   array[1]="b"; 
	   array[2]="c"; 
	   array[3]="d"; 
	   array[4]="c"; 
	   alert(  array.getRangeValue(1,2)+":"+array.getItem(0)); 
	   var str=new $.ts.String("113eefeee");
	   alert(str.toCharArray());
	    alert(array.indexOf("bd")+":"+array.lastIndexOf('c'));
		array[5]={name:"123",number:"002"};
		array[6]={name:"333",number:"02202"};
		var arg={name:"123",number:"002"};
		var newArr=array.select(arg);
		alert(newArr[6].name);
	}
	function parseStr(){
		var text="d#"+"{name}abc"+"$"+"{number}999<?s 1+1?>#"+"{ts}ok"; 
		var buf=new $.ts.Array(text.length+1);
		alert(buf);   
		var par=new $.ts.ScriptParser();  
		// alert(par.INode.TAGTYPE_EXPR);
		//alert(new par.CellNode().getText()+"aaa");
		//alert(new par.CellNode ); 
		 var parser= new par.BuilderNodes(text);
		
		 var builderNodes=parser.getNodeList();
		 for(var i=0;i<builderNodes.length;i++){
			var key=builderNodes.getItem(i).getText(); 
			var tagName=builderNodes.getItem(i).tagName;
			// alert(key+",tagName="+tagName);
		 }
		 var map=new $.ts.Map();
		 map.put("name","tomkysoft");
		 map.put("number","0002");
		 map.put("ts","0004"); 
		 var content=par.parseScript(text,map);
		alert(content);
	}
	function textJsonToSerialize(){
		var obj=new Array();
		obj[0]={name:"wewwe",number:"0002",id:233,age:33};
		obj[1]={name:"test",number:"0003",id:2000,age:200};
		//var obj= {name:"wewwe",number:"0002",id:233,age:33} ;
		if($.isArray(obj)){
			var sb=new $.ts.StringBuffer("");
			for(var i=0;i<obj.length;i++){
				var serial=$.param(obj[i]); 
				sb.append("&isSelect='"+serial+"'");
			}
			alert(sb.toString());
		} else {
		   var serial=$.param(obj);
		   alert(serial);
		}
	}
</script>
</body>
</html>