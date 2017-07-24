
 function LoginScript( ){ 
	 this.contextPath=""; 
	 this.checkCodeUrl=""; 
 }
LoginScript.prototype={ 
    setOptions:function(option){
		var opts= $.extend({},option); 
		this.contextPath=opts.contextPath; 
	},
    initPage:function(option){ 
		this.setOptions(option);
		this.bindControlEvent();
		this.setKeydown(); 
		//$("#createCheckCode").trigger("click");
		$("#name").focus();
	},
	setKeydown:function(){
		 $("#loginForm").keydown(function(e){
			 var e = e || event;
			 keycode = e.which || e.keyCode;
			 if (keycode==13) {//回车键
			    $("#submitButton").trigger("click");
			 }
		});
		$("#name").keydown(function(e){
			 var e = e || event;
			 keycode = e.which || e.keyCode;
			 if (keycode==40) {//向下方向键
			    $("#password").focus();
			 } 
		});
		$("#password").keydown(function(e){
			 var e = e || event;
			 keycode = e.which || e.keyCode;
			 if (keycode==40) {
			    $("#checkCode").focus();
			 } else if (keycode==38) {//向上方向键
			    $("#name").focus();
			 }
		});
		$("#checkCode").keydown(function(e){
			 var e = e || event;
			 keycode = e.which || e.keyCode;
			 if (keycode==38) {//向上方向键
			    $("#password").focus();
			 }
		});
	},
	checkForm:function(){ 
		if($("#name").val()==""){
			alert("用户名不能为空！");
			$("#name").focus();
			return false;
		}
		if($("#password").val()==""){
			alert("密码不能为空！");
			$("#password").focus();
			return false;
		}
		if($("#checkCode").val()==""){
			alert("验证码不能为空！");
			$("#checkCode").focus();
			return false;
		}
		$("form:first").submit(); 
	},
	bindControlEvent:function(){
		this.checkCodeUrl=document.getElementById("createCheckCode").src;   
		$("#createCheckCode").click(function(){    
			document.getElementById("createCheckCode").src=loginScript.checkCodeUrl+"?nocache="+new Date().getTime();
		});
		$("#submitButton").bind("click",this.checkForm);
	}
 }
 var loginScript=new LoginScript(); 
 