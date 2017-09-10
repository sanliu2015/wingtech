
function EmployeeScript( ){  
	 this.appKey=""; 
	 this.opts=null;
	 this.Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子   
     this.ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值.10代表X   
 }
EmployeeScript.prototype={ 
      
    initPage:function(option){
		this.opts= $.extend({},option);   
		this.changeCompany(); 
	},
	idCardChangeEvent:function( newValue,oldValue){
		employeeScript.IdCardValidate.call(employeeScript,newValue,oldValue);
	},
	loadDeptIdsCombotree:function ( ){
			$('#bean\\.dept\\.id').combotree({
			 url: tsContextPath +'/core/reportResolver/json/queryCombotree.do?moduleFileName=DepartmentCombotree',
			 multiple: false,
			 async:true 
			}); 
			 
		},
	IdCardValidate:function ( newValue,oldValue) {    
		var idCard = $.trim(newValue); 
		if (idCard.length == 15) {   
			return this.isValidityBrithBy15IdCard(idCard);   
		} else if (idCard.length == 18) {   
			var a_idCard = idCard.split("");// 得到身份证数组   
			if(this.isValidityBrithBy18IdCard(idCard) && this.isTrueValidateCodeBy18IdCard(a_idCard)){   
			    this.maleOrFemalByIdCard(idCard);
				return true;   
			}else {   
			    $.messager.alert('Hint', "身份证号不合法！");
				document.getElementsByName("bean.birthday")[0].value="";
				return false;   
			}   
		} else if(idCard!='') {  
		    $.messager.alert('Hint', "身份证号不合法！"); 
			document.getElementsByName("bean.birthday")[0].value="";
			return false;   
		}   
  }, 
  isTrueValidateCodeBy18IdCard:function (a_idCard) {   
    var sum = 0; // 声明加权求和变量   
    if (a_idCard[17].toLowerCase() == 'x') {   
        a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作   
    }   
    for ( var i = 0; i < 17; i++) {   
        sum += this.Wi[i] * a_idCard[i];// 加权求和   
    }   
    valCodePosition = sum % 11;// 得到验证码所位置   
    if (a_idCard[17] == this.ValideCode[valCodePosition]) {   
        return true;   
    } else {   
        return false;   
    }   
   },
   maleOrFemalByIdCard:function (idCard){   
    idCard = $.trim(idCard); 
    if(idCard.length==15){   
        if(idCard.substring(14,15)%2==0){   
		    document.getElementsByName("bean.gender")[1].checked=true;
            return '0';  //女性 
        }else{   
		    document.getElementsByName("bean.gender")[0].checked=true;
            return '1';   
        }   
    }else if(idCard.length ==18){   
        if(idCard.substring(14,17)%2==0){   
		    document.getElementsByName("bean.gender")[1].checked=true;
            return '0';   
        }else{   
		    document.getElementsByName("bean.gender")[0].checked=true;
            return '1';   
        }   
    }else{   
        return null;   
    }
   },
   isValidityBrithBy18IdCard:function (idCard18){   
    var year =  idCard18.substring(6,10);   
    var month = idCard18.substring(10,12);   
    var day = idCard18.substring(12,14);   
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
	var d=new $.ts.Date();
	var cy=d.datePart("y");
	 
	if(parseInt(year)+65<=parseInt(cy)){ 
		$.messager.alert('Hint', "身份证号不合法！"); 
		document.getElementsByName("bean.birthday")[0].value="";
		return false;
	}
    // 这里用getFullYear()获取年份，避免千年虫问题   
    if(temp_date.getFullYear()!=parseFloat(year)   
          ||temp_date.getMonth()!=parseFloat(month)-1   
          ||temp_date.getDate()!=parseFloat(day)){   
		    document.getElementsByName("bean.birthday")[0].value="";
            return false;   
    }else{   
	    var str=year+"-"+month+"-"+day;
		document.getElementsByName("bean.birthday")[0].value=str;
        return true;   
    }   
},
 isValidityBrithBy15IdCard:function (idCard15){   
      var year =  idCard15.substring(6,8);   
      var month = idCard15.substring(8,10);   
      var day = idCard15.substring(10,12);   
      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
      if(temp_date.getYear()!=parseFloat(year)   
              ||temp_date.getMonth()!=parseFloat(month)-1   
              ||temp_date.getDate()!=parseFloat(day)){   
			  document.getElementsByName("bean.birthday")[0].value="";
              return false;   
        }else{   
		    var str=year+"-"+month+"-"+day;
			document.getElementsByName("bean.birthday")[0].value=str;
            return true;   
        }   
  },
	loadDepartmentTree:function(){  
	      var companyId=$("#bean\\.company\\.id").combobox('getValue'); 
		  var urlJson={urlType:"combotree",
		            moduleFileName:"DepartmentCombotree",
					tsFilterSql:"{a.orgId="+companyId+"}",
		            timeStamp:""+(new Date()).getTime()}; 
			var url=$.ts.Utils.toUrlParam(urlJson);  
		   $("#bean\\.parentName").combotree({
			 url: url,
			 multiple: false,
			 required: false,
			 async:true,
			 onBeforeLoad: function(node, param) { 
				 $("#bean\\.parentName").combotree('enable'); 
			 }, 
			 onClick: function(node) {  
				$("#bean\\.dept\\.id").val(node.id);  
			 } 
		 }) 
	}, 
	loadPositionTree:function(){   
		  var urlJson={urlType:"combotree",
		            moduleFileName:"PositionCombotree", 
		            timeStamp:""+(new Date()).getTime()}; 
			var url=$.ts.Utils.toUrlParam(urlJson);  
		   $("#bean\\.positionName").combotree({
			 url: url,
			 multiple: false,
			 required: false,
			 async:true,
			 onBeforeLoad: function(node, param) { 
				 $("#bean\\.positionName").combotree('enable'); 
			 }, 
			 onClick: function(node) {  
				$("#bean\\.positionId").val(node.id);  
			 } 
		 }) 
	}, 
	changeCompany:function(){
		var that=this;
		 $("#bean\\.company\\.id").combobox({    
			 onSelect:function(rec){    
				 that.loadDepartmentTree();
			 }  
		 });  
	},
	previewImage:function(file){
		var MAXWIDTH  = 260; 
          var MAXHEIGHT = 180;
          var div = document.getElementById('preview');
		  var that=this;
          if (file.files && file.files[0])
          { 
              div.innerHTML ='<img id=imghead>';
              var img = document.getElementById('imghead');
              img.onload = function(){
                var rect = that.clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
                img.width  =  rect.width;
                img.height =  rect.height-20;
//                 img.style.marginLeft = rect.left+'px';
                img.style.marginTop = rect.top+'px';
              }
              var reader = new FileReader();
              reader.onload = function(evt){img.src = evt.target.result;}
              reader.readAsDataURL(file.files[0]);
          }
          else //兼容IE
          {
            var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
            file.select();
            var src = document.selection.createRange().text;
            div.innerHTML = '<img id=imghead>';
            var img = document.getElementById('imghead');
            img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
            var rect = that.clacImgZoomParam(MAXWIDTH, MAXHEIGHT, img.offsetWidth, img.offsetHeight);
            status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);
            div.innerHTML = "<div id=divhead style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;"+sFilter+src+"\"'></div>";
          }
        },
       clacImgZoomParam: function ( maxWidth, maxHeight, width, height ){
            var param = {top:0, left:0, width:width, height:height};
            if( width>maxWidth || height>maxHeight )
            {
                rateWidth = width / maxWidth;
                rateHeight = height / maxHeight;
                 
                if( rateWidth > rateHeight )
                {
                    param.width =  maxWidth;
                    param.height = Math.round(height / rateWidth);
                }else
                {
                    param.width = Math.round(width / rateHeight);
                    param.height = maxHeight;
                }
            }
             
            param.left = Math.round((maxWidth - param.width) / 2);
            param.top = Math.round((maxHeight - param.height) / 2);
            return param; 
	},
	
    submitForm:function (obj){//点击按钮提交   
	     var bodyId="#"+this.opts.appKey+"Body";
		 var formId= this.opts.appKey+"Form";
		 var url=$("#"+formId).attr("action")+'?timeStamp='+(new Date()).getTime();  
		 if ($.trim($("#bean\\.name").textbox("getText")) == "") {
			 $.messager.alert("警告", "姓名不能为空!");
			 return false;
		 }
		 if ($.trim($("#bean\\.number").textbox("getText")) == "") {
			 $.messager.alert("警告", "工号不能为空!");
			 return false;
		 }
		 $.ts.EasyUI.ajaxSubmitForm(url,formId,function(){
			 $.ts.EasyUI.closeDialog(obj);
		 }); 
		return false;
	 } 
 }
 
 