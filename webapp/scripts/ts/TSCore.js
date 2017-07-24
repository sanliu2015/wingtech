
jQuery.ts = {   
    getContextPath:function(){  
		var pathName=window.document.location.pathname; 
		var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
		if(projectName=="/") projectName="";
		return projectName;
	}, 
	closeDialog:function(obj,callbackEvent){ 
		 var dialog=$(obj).parents(".panel,.window"); 
		
		 if(dialog.length==0) return;  
		 var closeButton=dialog.find(".panel-tool-close"); 
		 closeButton.click() ; 
		 if($.ts.Utils.isEmpty(callbackEvent)==false && $.isFunction(callbackEvent)){ 
			   callbackEvent(this,dialog);        
		 }
  }, 
  Array:function(arg){
		 Array.prototype.addItem =function(obj){ 
			this[this.length] = obj;  
	    }  
		 Array.prototype.__arrayCopy = function(srcArray, srcIndex, dstArray, dstIndex, copyLength){
			if(!srcArray) return;
			if(!dstArray) return; 
			if ((srcIndex + copyLength) > srcArray.length){
				 
				 throw "(srcIndex + copyLength) > srcArray.length";
			}
			if (dstIndex > dstArray.length) { 
				throw "dstIndex > dstArray.length";
			}	
			if ((dstArray.length - dstIndex) < copyLength) {
				// alert("(dstArray.length - dstIndex) < copyLength");
				dstArray.length = dstIndex + copyLength;
			} 
			var i;
			var tempArray = new $.ts.Array(); 
			for (i = 0; i < copyLength; i++ ){ 
				tempArray.addItem(srcArray[srcIndex + i]);
			 
			}
			for (i = 0; i < copyLength; i++ ){ 
				dstArray.setItemAt(tempArray[i], dstIndex + i);
			}
		}
		Array.prototype.arrayCopy = Array.prototype.__arrayCopy;
		Array.prototype.getFirstItem = function(){
			if (this.length == 0) throw "NoSuchItemException";
			return this[0];
		} 
		Array.prototype.getRangeValue=function (startPos,endPos){
		  return __getArrayRangeValue(this,startPos,endPos);
		}  
		function __getArrayRangeValue(arr,startPos,endPos){
		   var value="";   
		   if (arr==null) return null; 
		   for(var i=startPos;i<=endPos;i++){
			   value=value+arr[i]; 
		   } 
		   return value;
		} 
		Array.prototype.getFirstItem = function(){
			if (this.length == 0) throw "NoSuchItemException";
			return this[0];
		} 
		Array.prototype.getLastItem = function(){
			if (this.length == 0) throw "NoSuchItemException";
			return this[this.length - 1];
		} 
		Array.prototype.addItem = function(obj){ 
			this[this.length] = obj;   
		} 
		Array.prototype.insertItem = function(obj, index){
			if(index<0) index = 0;
			if (index >= this.length){ //throw "ArrayIndexOutOfBoundsException: " + index + " > " + this.length;
				this[this.length] = obj; 
			}
			else{ 
				if(this.length>0)
					this.__arrayCopy(this, index, this, index + 1, this.length - index);
				this[index] = obj; 
			}
		} 
		Array.prototype.addRange = function(rangeArray){
			if(!rangeArray) return;
			this.__arrayCopy(rangeArray, 0, this, this.length, rangeArray.length);
		}
		Array.prototype.Clone = function(){
		  var retArray = new Array(10);
		  this.__arrayCopy(this, 0, retArray, 0, this.length);
		  retArray.length = this.length;
		  return retArray;
		}
		Array.prototype.clone = function () {  
			return this.slice(0);  
		}   
		Array.prototype.Parse = function(str, token){
			if(!str) return;
			if(!str.split) return;
			var temp = str.split(token);
			this.__arrayCopy(temp, 0, this, 0, temp.length);
			this.length = temp.length;
		}
		Array.prototype.getItem = function(index){
			if(!this.length) return null;
			if (index >= this.length) throw "ArrayIndexOutOfBoundsException: " + index + " >= " + this.length;
			else if (index < 0) throw "ArrayIndexOutOfBoundsException: " + index;
			return this[index];
		} 
		Array.prototype.getLength = function(){
			return this.length;
		} 
		Array.prototype.setItemAt = function(obj, index) {
			if (index == null)  
				return;  
			if (this.length < index) {
				throw ("this.length < " + index); 
			}
			this[index] = obj;
		}  
		Array.prototype.removeItem = function(val) {
			var retCount = 0;
			for(var i = 0; i < this.length; i++) {
				if(this[i] == val) {
					for(var j = i; j < this.length - 1; j++) {
						this[j] = this[j + 1];
					}
					this.length = this.length - 1;
					retCount++;
				}
			}
			return retCount;
		} 
		Array.prototype.removeItemAt = function RemoveItemAt (index) {
			if(!this.length) return;
			if(index < this.length){
				for(var i = index; i < this.length-1; i++) {
					this[i] = this[i + 1];
				}
				this.length = this.length - 1;
			}
		} 
		Array.prototype.removeAll = function() {
		  this.length = 0;
		} 
		Array.prototype.clear = function() {
		  this.removeAll();
		} 
		Array.prototype.indexOf = function(obj, index){
			var i;
			if (!index) index = 0;
			if (obj == null) {
			  for (i = index; i < this.length; i++)
					if (this[i] == null) return i;
			} else {
				for (i = index; i < this.length; i++)
					if (obj == this[i]) return i;
			}
			return -1;
		} 
		Array.prototype.lastIndexOf = function(obj, index){
			var i;
			if  (!index) index = this.length - 1;
			if (index >= this.length) { 
				throw index + " >= "+ this.length;
		  }		
			if (obj == null) {
				for (i = index; i >= 0; i--){
					if (this[i] == null) return i;
				}
			} else {
				for (i = index; i >= 0; i--) {
					if (obj == this[i]) return i;
				}
			}
			return -1;
		} 
		Array.prototype.contains = function(obj){
			return this.indexOf(obj, 0) >= 0;
		}
		Array.prototype.Sort = function(sortMethod){ //必须为Sort,若为sort有隐患
			if (!sortMethod) { this.sort(); }
			else { this.sort(sortMethod); } 
		} 
		if(typeof(arg)!="undefined")
			return new Array(arg);
		else
			return new Array(0);
	 }, 
	  String:function(arg){ 
		 String.prototype.trim = function(str)  { 
			return this.replace(/(^\str*)|(\str*$)/g, ""); 
		} 
		String.prototype.toCharArray=function( ){
			var arr=new Array(this.length); 
			for(var i=0;i<this.length;i++){
				arr[i]=this.charAt(i);
			}
			return arr;
		} 
		String.prototype.leftTrim = function(str)  { 
			return this.replace(/(^\str*)/g, ""); 
		}  
		String.prototype.toDate = function(token, mask) {
			if(token == null) token = "-";
			if(mask == null) mask = "ymd";
			var dateArray = this.split(token);
			var y = dateArray[mask.indexOf("y")];
			var m = dateArray[mask.indexOf("m")] - 1;
			var d = dateArray[mask.indexOf("d")];
			return new Date(y, m, d);
		}
		String.prototype.rightTrim = function(str) { 
			return this.replace(/(\str*$)/g, ""); 
		}  
		String.prototype.equals = function(str){
			if(this == str) return true;
			return false;
		}
		String.prototype.hashCode = function(){
			var h = 0;
			for (var i = 0; i < this.length; i++) {
			  h = 31 * h + this.charCodeAt(i);
			}
			return h;
		} 
		String.prototype.capitalize = function(str)  {  
			var value=str;
			if (!str)
				value=this;
			var firstChr=value.substring(0,1);
			firstChr=firstChr.toUpperCase();
			value=firstChr+value.substring(1,value.length);
			return value;  
		} 
		String.prototype.replaceAll=function(s1,s2){     
			return this.replace(new RegExp(s1,"gm"),s2);     
		} 
		return new String(arg);
  }, 
  Number:function(arg){ 
	function divide(arg1,arg2){
		 var t1=0,t2=0,r1,r2;
		 try{t1=arg1.toString().split(".")[1].length}catch(e){}
		 try{t2=arg2.toString().split(".")[1].length}catch(e){}
		 with(Math){
		  r1=Number(arg1.toString().replace(".",""))
		  r2=Number(arg2.toString().replace(".",""))
		  return (r1/r2)*pow(10,t2-t1);
		 }
	} 
	Number.prototype.divide = function (arg){
	   return divide(this, arg);
	} 
	function multiply(arg1,arg2) {
		 var m=0,s1=arg1.toString(),s2=arg2.toString();
		 if(s1=="") s1="0";
		 if(s2=="") s1="0";
		 try{m+=s1.split(".")[1].length}catch(e){}
		 try{m+=s2.split(".")[1].length}catch(e){}
		 return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
	}  
	Number.prototype.multiply = function (arg){
	  return multiply(arg, this);
	}
	 
	function add(arg1,arg2){
		 var r1,r2,m;
		 try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
		 try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
		 
		 m=Math.pow(10,Math.max(r1,r2)); 
		 return (parseInt(arg1.multiply(m))+parseInt(arg2.multiply(m)))/m
	}  
	Number.prototype.add = function (arg){
		return add(this,arg);
	} 
	function subtract(arg1,arg2){
		 var r1,r2,m;
		 try{
			 r1=arg1.toString().split(".")[1].length;
		 }catch(e){
			 r1=0;
		 }
		 try{
			 r2=arg2.toString().split(".")[1].length;
		 }catch(e){
			 r2=0
		 }
		 m=Math.pow(10,Math.max(r1,r2)); 
		 return (parseInt(arg1*m)-parseInt(arg2*m)).divide(m)
	}  
	
	Number.prototype.subtract = function (arg){
		return subtract(this,arg);
	}
	function compare(arg1,arg2){
		if(parseFloat(arg1)>parseFloat(arg2)){
			return 1;
		} else if(parseFloat(arg1)==parseFloat(arg2)){
			return 0;
		} else return -1;
	}
	Number.prototype.compare=function(arg){
		return compare(this,arg);
	}
	 if(typeof(arg)!="undefined"){
		 if(arg==""){
			return new Number("0");
		}
		var value= new Number(arg.toString().replace(/,/g,""));
		return value;
	 } else {
	   if(arg=="") arg="0";
	   return new Number(arg);
	 }
  },
  StringBuffer:function(arg){  
	 function StringBuffer(str){
		 if(str==undefined||str==null){
			str="";
		 }
		 this.orig=str; 
		 this.nStr=str;
		 this.className="ts.StringBuffer";
	 };
	 StringBuffer.prototype.append=function (pvalue){
		this.nStr+=pvalue; 
	}; 
	StringBuffer.prototype.getLength=function (){
		return this.nStr.length; 
	}; 
	StringBuffer.prototype.charAt=function (index){
		return this.nStr.charAt(index); 
	}; 
	StringBuffer.prototype.deleteBetween=function (index1,index2){
		if(index1<index2){
		 var str=this.nStr.substring(0,index1)+this.nStr.substring(index2);
		 this.nStr=str;
		}else if(index1==index2){
		 var str=this.nStr.substring(0,index1)+this.nStr.substring(index1+1);
		 this.nStr=str;
		} 
	}; 
	StringBuffer.prototype.getValue=function (){
		return this.nStr; 
	};
	StringBuffer.prototype.toString=function (){
		return this.nStr; 
	};
	StringBuffer.prototype.deleteCharAt=function (index){
		this.deleteBetween(index,index); 
	};
	StringBuffer.prototype.getChars=function (){
		var chars=new Array(this.getLength()); 
		for(var i=0;i<chars.length;i++){
			chars[i]=this.nStr.charAt(i); 
		}
	   return chars;
	};
	StringBuffer.prototype.indexOf=function (str){
		return this.nStr.indexOf(str); 
	};
	StringBuffer.prototype.insert=function (index,str){
		var s= this.nStr.substring(0,index)+str+this.nStr.substring(index);
		this.nStr=s;
	};
	StringBuffer.prototype.lastIndexOf=function (str){
		return this.nStr.lastIndexOf(str); 
	};
	StringBuffer.prototype.substring=function (index){
		return this.nStr.substring(index); 
	};
	StringBuffer.prototype.substringBetween=function (index1,index2){
		return this.nStr.substring(index1,index2); 
	};
	StringBuffer.prototype.reverse=function (){
		var str="";
		for(var i=this.getLength()-1;i>=0;i--){
			str+=this.charAt(i); 
		} 
		this.nStr=str;
	};
	return new StringBuffer(arg);
  },
 
  Date:function(arg){
	  Date.prototype.isLeapYear = function () {
		return (0 == this.getYear() % 4 && ((this.getYear() % 100 != 0) || (this.getYear() % 400 == 0)));
	  }; 
	  Date.prototype.format = function (formatStr) {
		var str = formatStr;
		var Week = ["\u65e5", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d"];
		str = str.replace(/yyyy|YYYY/, this.getFullYear());
		str = str.replace(/yy|YY/, (this.getYear() % 100) > 9 ? (this.getYear() % 100).toString() : "0" + (this.getYear() % 100));
		str = str.replace(/MM/, (this.getMonth()+1) > 9 ? (this.getMonth()+1).toString() : "0" + (this.getMonth()+1));
		str = str.replace(/M/g, (this.getMonth()+1));
		str = str.replace(/w|W/g, Week[this.getDay()]);
		str = str.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString() : "0" + this.getDate());
		str = str.replace(/d|D/g, this.getDate());
		str = str.replace(/hh|HH/, this.getHours() > 9 ? this.getHours().toString() : "0" + this.getHours());
		str = str.replace(/h|H/g, this.getHours());
		str = str.replace(/mm/, this.getMinutes() > 9 ? this.getMinutes().toString() : "0" + this.getMinutes());
		str = str.replace(/m/g, this.getMinutes());
		str = str.replace(/ss|SS/, this.getSeconds() > 9 ? this.getSeconds().toString() : "0" + this.getSeconds());
		str = str.replace(/s|S/g, this.getSeconds());
		return str;
	};  
	Date.prototype.dateAdd = function (strInterval, Number) {
		var dtTmp = this; 
		switch (strInterval) {
		  case "s":
			return new Date(Date.parse(dtTmp) + (1000 * Number));
		  case "n":
			return new Date(Date.parse(dtTmp) + (60000 * Number));
		  case "h":
			return new Date(Date.parse(dtTmp) + (3600000 * Number));
		  case "d":
			return new Date(Date.parse(dtTmp) + (86400000 * Number));
		  case "w":
			return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));
		  case "q":
			return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number * 3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
		  case "m":
			return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
		  case "y":
			return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
		}
	};  
	Date.prototype.dateDiff = function (strInterval, dtEnd) {
		var dtStart = this;
		if (typeof dtEnd == "string") {//如果是字符串转换为日期型
			dtEnd = StringToDate(dtEnd);  
		}	
		switch (strInterval) {
		  case "s":
			return parseInt((dtEnd - dtStart) / 1000);
		  case "n":
			return parseInt((dtEnd - dtStart) / 60000);
		  case "h":
			return parseInt((dtEnd - dtStart) / 3600000);
		  case "d":
			return parseInt((dtEnd - dtStart) / 86400000);
		  case "w":
			return parseInt((dtEnd - dtStart) / (86400000 * 7));
		  case "m":
			return (dtEnd.getMonth() + 1) + ((dtEnd.getFullYear() - dtStart.getFullYear()) * 12) - (dtStart.getMonth() + 1);
		  case "y":
			return dtEnd.getFullYear() - dtStart.getFullYear();
		}
	};  
	Date.prototype.toArray = function () {
		var myDate = this;
		var myArray = Array();
		myArray[0] = myDate.getFullYear();
		myArray[1] = myDate.getMonth();
		myArray[2] = myDate.getDate();
		myArray[3] = myDate.getHours();
		myArray[4] = myDate.getMinutes();
		myArray[5] = myDate.getSeconds();
		return myArray;
	};  
	Date.prototype.datePart = function (interval) {
		var myDate = this;
		var partStr = "";
		var Week = ["\u65e5", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d"];
		switch (interval) {
		  case "y":
			partStr = myDate.getFullYear();
			break;
		  case "m":
			partStr = myDate.getMonth() + 1;
			break;
		  case "d":
			partStr = myDate.getDate();
			break;
		  case "w":
			partStr = Week[myDate.getDay()];
			break;
		 
		  case "h":
			partStr = myDate.getHours();
			break;
		  case "n":
			partStr = myDate.getMinutes();
			break;
		  case "s":
			partStr = myDate.getSeconds();
			break;
		}
		return partStr;
	};
	if(typeof(arg)=="undefined"){
		return new Date();
	} else {
		return new Date(arg);
	}
  },
  Map:function(){
	  var  keys  =   new  Array();
	  var  entrys  =   new  Array(); 
	  this.type  =  'SimpleMap'; 
	  this .keySet  =  function () {
        return  keys;
      };  
     this.entrySet  =   function () {
         return  entrys;
     };
	 this.put=function(key,entry) { 
       if (key  !=   null  
           &&  key  !=  'undefined'
         &&  entry  !=   null
           &&  entry  !=  'undefined') {  
           this .keySet().push(key);     
           this .entrySet().push(entry);    
       }
  	};
	this.get=function (key) {  
        var  index=this.indexOf(key);
        if (index!=-1) {
            return  this .entrySet()[index];
       }
        return   null ;         
    } ;
	this.size=function () {    
       return   this .keySet().length;
    } ; 
	this.clear=function () {     
		   while ( this .keySet().length  >   0 ) {  
			   this .keySet().pop();
			  this .entrySet().pop();
		  } 
	};  
    this.indexOf  =   function (key) { 
       if (key  !=   null   &&  key  !=  'undefined') { 
           for ( var  i = 0 ;i < this .keySet().length;i ++ ) {    
               if ( this .keySet()[i]  ==  key) {
                   return  i;
              }
          }     
       }                    
       return   - 1 ;
     };
	 this.containsKey =function(key){
		 return this.indexOf(key) !== -1; 
	 };
	 this.remove=function (key) {
       var  index  =   this .indexOf(key);
	   if (index  ==- 1 ) return ; 
	   var  tempKey  =   new  Array(); 
	   var  tempEntry  =   new  Array(); 
	   for ( var  i = this .keySet().length;i > 0 ;i -- ) {    
		   if (i  !=  (index + 1 )) {     
			  tempKey.push( this .keySet().pop());
			  tempEntry.push( this .entrySet().pop());
		  } else {    
			   this .keySet().pop();
			   this .entrySet().pop();
		  }
	   }            
	   for ( var  i = tempKey.length;i > 0 ;i -- ) {    
		   this.keySet().push(tempKey.pop());
		   this.entrySet().push(tempEntry.pop()); 
	   }   
     };  
	 this.isEmpty = function() { 
	    return this.keySet().length <= 0;
	 }          
  }, 
  List:function(){
	this.length=0;
	this.array=new $.ts.Array();
	this.position=0;
	this.add=function(obj){
		this.array[this.length]=obj;
		this.length++;  
	};
	this.insert=function(obj,index){
		this.array.insertItem(obj,index);
		this.length++;
	}
	this.remove=function(position){
		if (position<this.length && position>=0&& this.length>0){
			for(var i=position;i<this.length-1;i++){
				this.array[i]=this.array[i+1];
			}
			this.length--;
		}
	};
	this.removeValue=function(val){
		for(var i=0;i<this.size();i++){
			if (this.get(i)==val){
				this.remove(i);
			}
		}
	};
	this.get=function(position){
		if (position<this.length && position>=0 && this.length>0){
			return this.array[position ];
		} else return "undefined";
	};
	this.removeAll=function(){
		this.array = [];
		this.length=0;
	};
	this.toArray=function(){
		var arr=new Array();
		for(var i=0;i<this.length;i++){
			arr[i]=this.array[i];
		}
		return arr;
	};
	this.size=function(){
		return this.length;
	};
	this.indexOf = function(obj, index){
		return this.array.indexOf(obj, index);
	};
	this.constains=function(obj){
		return this.array.contains(obj);
	}
  },tsExtends:function(superclass,object){
	var obj = object;
	if(!obj) obj = this;  
	var superobject = new superclass();
	for (var key in superobject) { 
        if (!obj[key] && !(key.indexOf("__") == 0)){
            obj[key] = superobject[key];   
		}
    } 
	superobject = null;
	return obj;
  }, ScriptParser:function(){ 
	  var INode=function (){    
		this.getTagName=function(){ };//抽象方法
		this.setText=function(text){}; 
		this.getText=function(){ return 'a';}; 
		this.getNodeText=function(){}; 
		this.getTagType=function(){}; 
		this.getValue=function(){}; 
		this.setValue=function(value){}; 
		this.getNodeIndex=function(){}; 
		this.setNodeIndex=function(nodeIndex){};   
	};  
	 INode.TAGTYPE_TEXT=0;//text
	 INode.TAGTYPE_EXPR=1;//#{...}
	 INode.TAGTYPE_SCRIPT=2;//<?s...?>
	 INode.TAGTYPE_MACRO=3;//${...}
	 INode.TAGTYPE_CELL=5;//解析单元格变量 如A1,AB20
	 this.INode=INode; 
	 function CellNode  (){
	   this.text=null;
	   this.tagName="#cell";   
	   this.nodeIndex=null;
	   this.value=null;
	};
	 $.ts.tsExtends(INode, CellNode); 
	 
	CellNode.prototype.getValue=function(){
	  return this.value;
	};
	CellNode.prototype.setValue=function(value){
	  this.value = value;
	};
	CellNode.prototype.getNodeIndex=function(){
	  return this.nodeIndex;
	};
	CellNode.prototype.setNodeIndex=function(nodeIndex){
	  this.nodeIndex = nodeIndex;
	};
	CellNode.prototype.getTagName=function(){ 
	  return this.tagName;
	}
	CellNode.prototype.getText=function(){
	  return this.text;
	}
	CellNode.prototype.getNodeText=function(){
	  return this.text;
	}
	CellNode.prototype.setText=function(text){
	  this.text = text;
	}
	CellNode.prototype.getTagType=function(){ 
	  return  INode.TAGTYPE_CELL;
	}  
	this.CellNode=CellNode;
	var ExprNode=function(){
	   this.text=null;
	   this.tagName="#expr";   
	   this.nodeIndex=null;
	   this.value=null;
	} 
	$.ts.tsExtends(INode,ExprNode); 
	 
	ExprNode.prototype.getValue=function(){
	  return this.value;
	}
	ExprNode.prototype.setValue=function(value){
	  this.value = value;
	}
	ExprNode.prototype.getNodeIndex=function(){
	  return this.nodeIndex;
	}
	ExprNode.prototype.setNodeIndex=function(nodeIndex){
	  this.nodeIndex = nodeIndex;
	}
	ExprNode.prototype.getTagName=function(){ 
	  return this.tagName;
	}
	ExprNode.prototype.getText=function(){
	  return this.text;
	}
	ExprNode.prototype.getNodeText=function(){
	  return "#{"+this.text+"}";
	}
	ExprNode.prototype.setText=function(text){
	  this.text = text;
	}
	ExprNode.prototype.getTagType=function(){ 
	  return INode.TAGTYPE_EXPR;
	}  
	this.ExprNode=ExprNode;
	var MacroNode=function(){
	   this.text=null;
	   this.tagName="#macro";   //$
	   this.nodeIndex=null;
	   this.value=null;
	}
	$.ts.tsExtends(INode,MacroNode); 
	MacroNode.prototype.getValue=function(){
	  return this.value;
	}
	MacroNode.prototype.setValue=function(value){
	  this.value = value;
	}
	MacroNode.prototype.getNodeIndex=function(){
	  return this.nodeIndex;
	}
	MacroNode.prototype.setNodeIndex=function(nodeIndex){
	  this.nodeIndex = nodeIndex;
	}
	MacroNode.prototype.getTagName=function(){ 
	  return this.tagName;
	}
	MacroNode.prototype.getText=function(){
	  return this.text;
	}
	MacroNode.prototype.getNodeText=function(){
	  return "${"+this.text+"}";
	}
	MacroNode.prototype.setText=function(text){
	  this.text = text;
	}
	MacroNode.prototype.getTagType=function(){
	  return  INode.TAGTYPE_MACRO;
	}
	this.MacroNode=MacroNode;
	var ScriptNode=function(){
	   this.text=null;
	   this.tagName="#script";   
	   this.nodeIndex=null;
	   this.value=null;
	}
	$.ts.tsExtends(INode,ScriptNode); 
	ScriptNode.prototype.getValue=function(){
	  return this.value;
	}
	ScriptNode.prototype.setValue=function(value){
	  this.value = value;
	}
	ScriptNode.prototype.getNodeIndex=function(){
	  return this.nodeIndex;
	}
	ScriptNode.prototype.setNodeIndex=function(nodeIndex){
	  this.nodeIndex = nodeIndex;
	
	}
	ScriptNode.prototype.getTagName=function(){ 
	  return this.tagName;
	}
	ScriptNode.prototype.getText=function(){
	  return this.text;
	}
	ScriptNode.prototype.getNodeText=function(){
	  return "<?s"+this.text+"?>";
	}
	ScriptNode.prototype.setText=function(text){
	  this.text = text;
	}
	ScriptNode.prototype.getTagType=function(){
	  return  INode.TAGTYPE_SCRIPT;
	}
	this.ScriptNode=ScriptNode;
	 function TextNode(){
	   this.text=null;
	   this.tagName="#text";   
	   this.nodeIndex=null;
	   this.value=null;
	}
	$.ts.tsExtends(INode,TextNode); 
	 
	TextNode.prototype.getValue=function(){
	  return this.value;
	}
	TextNode.prototype.setValue=function(value){
	  this.value = value;
	}
	TextNode.prototype.getNodeIndex=function(){
	  return this.nodeIndex;
	}
	TextNode.prototype.setNodeIndex=function(nodeIndex){
	  this.nodeIndex = nodeIndex;
	}
	TextNode.prototype.getTagName=function(){ 
	  return this.tagName;
	}
	TextNode.prototype.getText=function(){	
	  return this.text;
	}
	TextNode.prototype.getNodeText=function(){
	  return this.text;
	}
	TextNode.prototype.setText=function(text){
	  this.text = text;
	  
	}
	TextNode.prototype.getTagType=function(){  
	  return  INode.TAGTYPE_TEXT;
	} 
	this.TextNode=TextNode;
	/**
	*@memo  把字符串通过特殊分隔符分成一系列数组,分隔符包括#{},&{},<s? >
	*@param separateChar 代表特殊分隔符数字，通过jcfl.parser.INode中静态常量访问
	*@param text 要解析的字符串
	*/
	 function BuilderNodes(text,separateChar){
	   var EOI=String.fromCharCode(26);
	   var bp=-1;
	   var ch=' ';
	   var  buf=null;
	   var startPos=bp;
	   var endPos=bp;
	   var prevEndPos=bp;
	   var nodeList=null;
	   var charType=-1;
	   var text=text;
	   if (typeof(separateChar)!="undefined"){
		   charType=separateChar; 
	   }
	   this.getNodeList=function(){
		   return nodeList;
	   }
	   function builderText(){  
		  var buflen=text.length;   
		  buf = new $.ts.Array(buflen+1); //  new Array(buflen+1);
		  buf.arrayCopy((new $.ts.String(text)).toCharArray(), 0, buf, 0, buflen);	 
		  buf[buflen]=EOI;
		  scanChar();
		  nodeList=new $.ts.Array();
		  parseText(); 
	   }
	   function builderSpecText(){ 
		  var buflen=text.length; 
		  buf = new $.ts.Array(buflen+1); 	  
		  buf.arrayCopy((new $.ts.String(text)).toCharArray(), 0, buf, 0, buflen);	 
		  buf[buflen]=EOI;
		  scanChar();
		  nodeList=new $.ts.Array();
		  this.parseSpecifyCharFromText();
	   }
	   function scanChar() { 
		  if (ch==EOI) return;
		  bp++;       
		  ch = buf[bp]; 
	  } 
	  function parseText(){   
		  startPos=0;
		  while (true){
			 switch (ch) {
			 case '#':  
				scanChar();
				if (ch=='{'){ 
				   prevEndPos=bp-2; 
				   addTextNode();
				   scanExpr(); 
				} else if (ch==EOI){
				   endPos=bp;
				   break;
				} else {
				   scanChar();
				   break;
				}
				break;
			 case '$':  
				scanChar(); 
				if (ch=='{'){ 
				   prevEndPos=bp-2;  
				   addTextNode();
				   scanMacro(); 
				} else if (ch==EOI){
				   endPos=bp;
				   break;
				} else {
				   scanChar();
				   break;
				}
				break;
			 case '<':
				scanChar();  
				if (ch=='?'){
				   scanChar();
				   if (ch=='s'){ 
					  prevEndPos=bp-3;  
					  addTextNode();
					  scanScript();
					  break;
				   } 
				}
				break; 
			  default: 
				 if (ch==EOI){
					prevEndPos=bp; 
					addTextNode();
					return ;
				 }
				 scanChar(); 
			 }  
		  } 
	   } 
		function parseSpecifyCharFromText(){   
		  startPos=0;
		  if ( charType== INode.TAGTYPE_EXPR){ 
			 while (true){  
				switch (ch) {
				case '#':   
				   scanChar();
				   if (ch=='{'){ 			  
					  prevEndPos=bp-2; 
					  addTextNode();
					   
					  scanExpr(); 
					 
				   } else if (ch==EOI){
					  endPos=bp;
					  break;
				   } else {
					  scanChar();
					  break;
				   }
				   break;
				default: 
				   if (ch==EOI){
					  prevEndPos=bp; 
					  addTextNode();
					  return ;
				   }
				   scanChar(); 
					
			   }  
			 }
		  } else if ( charType== INode.TAGTYPE_MACRO){
			 while (true){ 
				switch (ch) {
				case '$': 
				   scanChar();
				   if (ch=='{'){ 
					  prevEndPos=bp-2; 
					  addTextNode();
					  scanMacro(); 
				   } else if (ch==EOI){
					  endPos=bp;
					  break;
				   } else {
					  scanChar();
					  break;
				   }
				   break;
				default: 
				   if (ch==EOI){
					  prevEndPos=bp; 
					  addTextNode();				  
					  return ;
				   }
				   scanChar(); 
			   }  
			 }
		  } else if ( charType== INode.TAGTYPE_SCRIPT){
			 while (true){ 
				switch (ch) {
				case '<':
				   scanChar();
				   if (ch=='?'){
					  scanChar();
					  if (ch=='s'){ 
						 prevEndPos=bp-3;  
						 addTextNode();
						 scanScript();
						 break;
					  } 
				   }
				   break; 
				default: 
				   if (ch==EOI){
					  prevEndPos=bp; 
					  addTextNode();
					  return ;
				   }
				   scanChar(); 
			   }  
			 }
		  } 
	   }
	   function addTextNode(){ 
		 //if (prevEndPos<=startPos) return ;
		 if (prevEndPos<startPos) return ;
		// var parser=new    $.ts.ScriptParser();
		 var  textNode=new  TextNode(); 
		 if (ch==EOI) prevEndPos--; 
		 textNode.setText(buf.getRangeValue(startPos,prevEndPos)); 
		 textNode.setNodeIndex(nodeList.length);  
		 nodeList.addItem(textNode);   
	  }
	  function scanExpr() {  
		  var lbracket=1;
		  scanChar();  
		  startPos=bp;
		  while (true){
			 if (ch==EOI){
				throw "表达式缺少}关闭符号!" ; 
				return ; 
			 } 
			 if (ch=='{'){
				lbracket++; 
			 } else if (ch=='}'){
				lbracket--;
				if (lbracket==0){
				   endPos=bp-1; 
				   var node=new  ExprNode();
				   node.setText(buf.getRangeValue(startPos,endPos));
				   node.setNodeIndex(nodeList.length);
				   nodeList.addItem(node);
				   startPos=bp+1;
				   return ;
				}
			 }
			 scanChar();  
		  } 
	   }
		function scanMacro() {  
		  var lbracket=1;
		  scanChar();  
		  startPos=bp;
		  while (true){
			 if (ch==EOI){
				throw "表达式缺少}关闭符号!" ; 
				return ; 
			 } 
			 if (ch=='{'){
				lbracket++; 
			 } else if (ch=='}'){
				lbracket--;
				if (lbracket==0){
				   endPos=bp-1;  
				   var node=new  MacroNode();
				   node.setText(buf.getRangeValue(startPos,endPos));
				   node.setNodeIndex(nodeList.length);
				   nodeList.addItem(node);
				   startPos=bp+1;
				   return ;
				}
			 }
			 scanChar();  
		  } 
	   }
	   function scanScript(){       
		  scanChar(); 
		  startPos=bp;  
		  while (true){
			 if (ch==EOI){
				throw "脚本缺少?>关闭符号!";
				return ;            
			 } 
			 if (ch=='?'){
				scanChar();
				if (ch=='>'){
				   endPos=bp-2; 
				   var node=new  ScriptNode();
				   node.setText(buf.getRangeValue(startPos,endPos));
				   node.setNodeIndex(nodeList.length);
				   nodeList.addItem(node);
				   startPos=bp+1;
				   return ;
				}
			 } 
			 scanChar();
		  }
	   } 
	   if(text!=""){
		    if (charType>-1) 
			   builderSpecText();
		    else  
				builderText();  
	   } 
	}
	this.BuilderNodes= BuilderNodes;
	this.parseScript=function(content,contextMap){
		var parser=new BuilderNodes(content);
		var builderNodes=parser.getNodeList();
		var values=new $.ts.StringBuffer("");
		for(var i=0;i<builderNodes.length;i++){
			var key=builderNodes[i].getText(); 
			var value=key;
			var tag=builderNodes[i].getTagType();
			if ( tag== INode.TAGTYPE_MACRO || tag== INode.TAGTYPE_EXPR){ 
				value=contextMap.get(key);  
				if(contextMap.containsKey(key)==false){
					var element=document.getElementById(key);
					if(element!=null){
						value=element.value;
					} else {
						value="";
					}
				} else if(value.constructor == window.Array){
					value=value[0];
					if(value==null){
						var element=document.getElementById(key);
						if(element!=null){
							value=element.value;
						}  else {
							value="";
						}
					} 
				}  
				
			}   else if ( tag== INode.TAGTYPE_SCRIPT){ 
				var tmpScript=this.parseScript(value,contextMap); 
				try {   
					value=eval(tmpScript);
					if(isNaN(value) || value=="Infinity" || value=="undefined" ){
					 value="";  
					}
				} catch (e) {
					value=""; 
					throw (tmp+e);
				}
			}
			values.append(value); 			 
		}   
		return values.toString();
	};
	 
  } 
}; 
$.ts.DB=function(){
	this.className="ts.DB";
};
$.ts.DB.queryMultiDataset=function(jsonPara,opts,callbackEvent){
	  if($.isArray(jsonPara)==false){
		  jsonPara=[{keyName:"queryList",sql:jsonPara,isHql:"0"}]; 
	  } 
	  if($.ts.Utils.isEmpty(opts)==true)
	  	opts={}; 
	try{
	  $.ajax($.extend({
		url: tsContextPath +"/core/queryDbServlet?timeStamp="+new Date().getTime(),   
		data:  JSON.stringify(jsonPara) ,
		datatype: "json",
		type: "POST",  
		async:false,
		success:function(result,textStatus){
			var resultJson = jQuery.parseJSON( result );   
			if($.ts.Utils.isEmpty(callbackEvent)==false && $.isFunction(callbackEvent)){ 
			    if($.isArray(resultJson.queryList)==false){
					resultJson.queryList=new Array();
				}  
			   callbackEvent(this,resultJson);        
			}
		 }, 
		error:function(XMLHttpRequest, textStatus, errorThrown){
			var str=XmlHttpRequest.responseText; 
			 if($.ts.Utils.isEmpty(str)){
				  str=XmlHttpRequest.responseXML;
			 }
			 str=str+"<hr/>"+textStatus;
			 $.ts.EasyUI.showContentDialog(str); 
		}
	},opts)); 
	} catch(e){}
}
$.ts.DB.executeSql=function(jsonPara,opts,callbackEvent){ 
	  if($.isArray(jsonPara)==false){
		   //var saveDataAry=[];  
		   //var data1={"keyName":"queryList","sql":jsonPara,"isHql":"0","isQuerySql":"0"};
		 // saveDataAry.push(data1); 
		  //jsonPara={"id":"ss","configures":saveDataAry}; 
		  //jsonPara={"id":"ss","serviceName":"sssse33","configures":[]}; 
		 // jsonPara= [{"keyName":"queryList","sql":jsonPara,"isHql":"0","isQuerySql":"0"}] ; 
		  jsonPara= {"configures":[{"keyName":"queryList","sql":jsonPara,"isHql":"0","isQuerySql":"0"}] }; 
	  } 
	  if($.ts.Utils.isEmpty(opts)==true)
	  	opts={};   
	try{
	  $.ajax($.extend({
		url: tsContextPath +"/core/executeSqlTsService/recToJson/executeSql.do?timeStamp="+new Date().getTime(),   
		data:   JSON.stringify(jsonPara)  ,
		datatype: "json",
		contentType:"application/json",
		type: "POST",  
		async:false,
		success:function(result,textStatus){ 
			var resultJson = jQuery.parseJSON( result );    
			if($.ts.Utils.isEmpty(callbackEvent)==false && $.isFunction(callbackEvent)){  
			   callbackEvent(this,resultJson);        
			}
		 }, 
		error:function(XMLHttpRequest, textStatus, errorThrown){
			 
			var str=XmlHttpRequest.responseText; 
			
			 if($.ts.Utils.isEmpty(str)){
				  str=XmlHttpRequest.responseXML;
			 }
			 str=str+"<hr/>"+textStatus;
			 $.ts.EasyUI.showContentDialog(str); 
		}
	},opts)); 
	} catch(e){}
}

$.ts.EasyUI=function(){
	this.className="ts.EasyUI";
};
$.ts.EasyUI.frameDialog=function(options,tsOptions){
	 if ($.ts.EasyUI.frameDialog.handler != undefined ) return null;
	 var divId="div"+(new Date()).getTime() ;
	 var frameId="frame"+(new Date()).getTime() ; 
	 var opts = $.extend({
		title : '弹出对话框',
		width : 840, 
		modal : true,
		height : $(document).height()-5,
		collapsible: true,  
		maximizable: true, 
		resizable:true,
		method:"POST",
		onOpen:function(){  
			var childFrame=$("#"+frameId);//$.find("iframe[id='"+frameId+"']");  
			if($.ts.Utils.isEmpty(tsOptions)==false){   
				function frameLoad (){  
					if(childFrame.length>0 && typeof(childFrame[0].contentWindow.reportScript)!="undefined" ){  
					
					  if($.ts.Utils.isEmpty(tsOptions.container)==false){
						  $.ts.Utils.getContainerObj(tsOptions);
					  }
					  childFrame[0].contentWindow.reportScript.reportOptions=tsOptions; 
				    }
			     }; 
				 $.ts.Utils.addEvent("load",childFrame[0],frameLoad);
			}
		},
		onClose:function() {  
			var handler=$.ts.EasyUI.frameDialog.handler; 
			if($.ts.Utils.isEmpty(tsOptions)==false && tsOptions.selectedRows==null && tsOptions.operatePattern=="insertRow" ){
				var childFrame=$("#"+frameId); 
				tsOptions.selectedRows=childFrame[0].contentWindow.reportScript.reportOptions.selectedRows;
			}
			if(handler!=null){
				try{  
					var closeCallbackEvent=null;
					if($.ts.Utils.isEmpty(tsOptions)==false ){
						if( jQuery.isFunction(tsOptions)){
							closeCallbackEvent=tsOptions;
						} else if($.ts.Utils.isEmpty(tsOptions.closeDialog)==false && jQuery.isFunction(tsOptions.closeDialog) ){
							closeCallbackEvent=tsOptions.closeDialog;
						}
					}
					if(typeof(closeCallbackEvent)!="undefined" && closeCallbackEvent!=null){  
						var childFrame=$("#"+frameId);
						if(childFrame.length>0 && typeof(childFrame[0].contentWindow.reportScript)!="undefined" ){
							var json=childFrame[0].contentWindow.reportScript.getDialogSelectedRows();
							closeCallbackEvent.call(handler,json );
						} 
					} 
					if(typeof(closeFramDialogCallbackEvent)!="undefined"   && $.isFunction(closeFramDialogCallbackEvent)){ 
						   closeFramDialogCallbackEvent(this,handler);        
					}
				} catch(e){ 
					$.messager.alert('Hint', e); 
				} 
			}
			$.ts.EasyUI.frameDialog.handler = undefined;
			$(this).remove();  
		}  
	}, options);  
	 opts.modal = true; 
	 opts.cache=false; 
	 var sb=$.ts.StringBuffer(""); 
	 sb.append('<iframe id="'+frameId+'" name="'+frameId+'"' ); 
	 sb.append(' frameborder="0"  src="'+options.href+'"' ); 
	 sb.append(' style="width:100%;height:98%;" ></iframe>'); 
	 opts.content=sb.toString();
	 opts.href="";
	 var handler=null;
	 if(typeof(opts.targetName)!="undefined" && "parent"==opts.targetName){
		 handler= parent.$('<div id="'+divId+'" />').dialog(opts); 
		 if(typeof(parent.mainScript)!="undefined" && typeof(reportScript)!="undefined" ){
			 parent.mainScript.frameDialogReportScript=reportScript; 
		 }
	 } else {
		 handler= $('<div id="'+divId+'"/>').dialog(opts);
	 } 
	 handler.data("dialogDivId",divId);
	 handler.data("dialogFrmaeId",frameId); 
	 $.ts.EasyUI.frameDialog.handler=handler;
	 return handler ; 
};
$.ts.EasyUI.modalDialog = function(options,closeCallbackEvent,initLoadPageCallback) { 
    if(typeof($.ts.EasyUI.modalDialog.handler)!="undefined"){
		if($.ts.EasyUI.modalDialog.handler.data("dialogTitle")==options.title) return null;
	}   
	var opts = $.extend({
		title : '对话框',
		width : 840,
		cache:true,
		height : $(document).height()-5,
		modal : true,  
		onLoad: function () {  
		    if(typeof(showAlertMsgEvent)!="undefined" && $.isFunction(showAlertMsgEvent)){ 
				$(this).dialog("destroy").remove(); 
				showAlertMsgEvent(); 
				showAlertMsgEvent=null;
				return ;
			}
			if(typeof(initLoadPageCallback)!="undefined" && $.isFunction(initLoadPageCallback)){
				initLoadPageCallback();
			}
			if(typeof(modalDialogLoadEvent)!="undefined" && $.isFunction(modalDialogLoadEvent)){
				modalDialogLoadEvent();
			}
			
			
		}, 
		onClose:function() { 
		    var handler=$.ts.EasyUI.modalDialog.handler; 
			if(typeof(modalDialogCloseEvent)!="undefined" && $.isFunction(modalDialogCloseEvent)){
				modalDialogCloseEvent();
			}
			if(handler!=null){
				try{   
					if( typeof(closeCallbackEvent)!="undefined"  && jQuery.isFunction(closeCallbackEvent)){ 
						var childFrame=$(this).find("iframe"); 
						if(childFrame.length>0 && typeof(childFrame[0].contentWindow.reportScript)!="undefined" ){
							var json=childFrame[0].contentWindow.reportScript.getDialogSelectedRows();
							closeCallbackEvent.call(handler,json );
						}  else {
							closeCallbackEvent.call(handler );
						}
					} 
					if(typeof(closeDialogCallbackEvent)!="undefined"   && $.isFunction(closeDialogCallbackEvent)){ 
						   closeDialogCallbackEvent(this,handler);        
					}
				} catch(e){ 
					 $.messager.alert('Hint', e); 
				} 
			} 
			 $(this).remove(); 
			$.ts.EasyUI.modalDialog.handler = undefined;
			
		} 
	 }, options);   
	 var divId="modalDialog"+(new Date()).getTime();
	 var handler=null;
	 if(typeof(opts.targetName)!="undefined" &&   "parent"==opts.targetName){
		 handler= parent.$('<div id="'+divId+'"/>').dialog(opts); 
		 if(typeof(parent.mainScript)!="undefined" && typeof(reportScript)!="undefined" ){
			 parent.mainScript.frameDialogReportScript=reportScript; 
		 }
	 } else {
		 handler= $('<div id="'+divId+'"/>').dialog(opts);
	 }   
	 handler.data("dialogFormDivId",divId);
	 handler.data("dialogTitle",opts.title); 
	 $.ts.EasyUI.modalDialog.handler=handler;
	 return handler; 
};
$.ts.EasyUI.closeDialog=function(obj,needRefreshReport,resultData){
	var dialog=$(obj).parents(".panel,.window");   
	var needRefreshReportFlag="1";
	if(typeof(needRefreshReport)!="undefined"){
		needRefreshReportFlag=needRefreshReport;
	} 
	var   workFlowFlag=$("#workFlowFlag").val();
	if("2"==workFlowFlag) needRefreshReport="0";
	function loadSubmitForm(){ 
	     if($.ts.Utils.isEmpty(resultData)==true) return ;
		 if($.ts.Utils.isEmpty(resultData,resultData.processInstanceId,resultData.billId,resultData.taskNodeType)==true) return;
		 if(resultData.taskNodeType!="submit") return;
		 var urls=tsContextPath+"/core/workFlowAuditService/addWorkFlowAudit.do?operateAuthCheckFlag=0";
		 urls=urls+"&processInstanceId="+resultData.processInstanceId+"&businessKey="+resultData.billId;
		 urls=urls+"&id="+resultData.billId;
			 $.ts.EasyUI.modalDialog(  {  
				width : 800, 
				height : $(document).height()-10,
				modal:false,
				title:"提交单据",
				method:"POST", 
				cache:true,
				collapsible: true,  
				maximizable: true, 
				maximized:true,
				resizable:true, 
				href : urls   
			} ); 
		   
	}
	 if(dialog.length>0) { 
	     for(var i=0;i<dialog.length;i++){ 
			 var closeButton=$(dialog[i]).find(".panel-tool-close");    
			 if(closeButton.length>0){
				 try{
					  $(closeButton[0]).click() ; 
					 if(typeof(reportScript)!="undefined" &&  needRefreshReportFlag=="1"){
						 reportScript.queryReportResult();
					 }
					 
				 } catch (e){}
				 loadSubmitForm();
				 return;
			 }
		 }
	 }  
	 var dialog=$(window.parent.document).find("body").find(".panel,.window");  
	 
	 if(typeof(parent.mainScript)!="undefined"){ 
		 for(var i=0;i<dialog.length;i++){
			  var closeButton=$(dialog[i]).find(".panel-tool-close");   
			  if(closeButton.length<1) continue;  
			  try{
				 var mainObj=parent.mainScript;
				 closeButton[0].click() ; 
				 if(typeof(mainObj)!="undefined" && typeof(mainObj.frameDialogReportScript)!="undefined"  
				 &&  needRefreshReportFlag=="1"){
					 mainObj.frameDialogReportScript.queryReportResult();
					 mainObj.frameDialogReportScript=null;
				 }  
			  }catch(e){}
			 break; 
			   
		 } 
   } else {  
	   for(var i=dialog.length-1;i>=0;i--){
			  var closeButton=$(dialog[i]).find(".panel-tool-close");   
			  if(closeButton.length<1) continue;   
			 try{
				 var mainObj=parent.reportScript; 
				 closeButton[0].click() ; 
				 if(typeof(mainObj)!="undefined" && typeof(mainObj.queryReportResult)!="undefined"  
				 &&  needRefreshReportFlag=="1"){
					 mainObj.queryReportResult(); 
				 }  
			 } catch(e){}
			 break; 
			   
		 } 
   }
   loadSubmitForm();
},
$.ts.EasyUI.submitRowsToList=function(nodeName,container,dataOpts){ 
     var opts={beanRows:1,callbackEvent:null,effectRowFields:[],cloneRowFlag:"cloneRowFlag"}
	 if($.ts.Utils.isEmpty(dataOpts)==false){
		if($.ts.Utils.isString(dataOpts)){
			dataOpts=$.ts.Utils.parseOpts(dataOpts);
		}
		$.extend( opts ,dataOpts);
    }   
	if($.ts.Utils.isEmpty(opts.beanRows)) opts.beanRows=1;
	 var rootNode=null; 
	 var e=jQuery.event.fix(event || window.event);
	 var operateObj= e.target;  
	 if(typeof(container)!="undefined" && container!=null){
		 if($.ts.Utils.isString(container)){
			 rootNode=$("#"+container);
		 } else {
			 rootNode=$(container);
		 }
	 } else {  
		 rootNode=$(operateObj).parents("form");   
	 } 
	 var rowPrefix=nodeName.substring(0,nodeName.indexOf(".")); 
	 var nodes=$(rootNode).find("[name='"+nodeName+"']"); 
	 var subfixName=nodeName.substring(nodeName.indexOf(".")+1);
	 var submitedFullName=rowPrefix+"[0]."+subfixName;  
	 var submitedNodes=$(rootNode).find("[name='"+submitedFullName+"']");  
	 if(submitedNodes.length>0){
		 $.ts.EasyUI.clearElementsSubscript(nodeName,container,opts.beanRows);
	 }
	
	  nodes=$(rootNode).find("[name='"+nodeName+"']");  
	 var index = 0;   
	 
	 $.each(nodes, function(i,node){
		var rowNode= $(node).parent();  
		while(rowNode[0]!=rootNode[0] && rowNode!=null && rowNode.length>0){
			var tmpNode= rowNode.parent(); 
			if(tmpNode[0]==rootNode[0]){
				break;
			} else {
				var parentNode=$(tmpNode).find("[name='"+nodeName+"']"); 
				if(parentNode.length>1){ 
					break;
				} else if(index>0) {
					var jNode=$(node);
		            var name=jNode.attr("name").substring(jNode.attr("name").indexOf(".")+1);
					var fullName=rowPrefix+"["+(index-1)+"]."+name;  
					parentNode=$(tmpNode).find("[name='"+fullName+"']"); 
					if(parentNode.length>0) break; 
				}
			}
			if(tmpNode.length>0)
			  rowNode=rowNode.parent();   
			else break; 
			if($.ts.Utils.isEmpty(rowNode.attr(opts.cloneRowFlag))==false)  break ;  
		}  
		if(rowNode.attr(opts.cloneRowFlag)=="1")  return ;  
		var rowSubmitNode=$(rowNode).find("[name*='.rowSubmitFlag']");  
		if(rowSubmitNode!=null && rowSubmitNode.length>0){
			var rowSubmitFlagValue="1";
			if(rowSubmitNode.attr("type")=="checkbox"){
				if(rowSubmitNode[0].checked==false){
					rowSubmitFlagValue="0";
				}
			} else {
				rowSubmitFlagValue=rowSubmitNode.val();
			}
			if(rowSubmitFlagValue=="0"){ 
				var nodeIdName=rowPrefix+".id";
				var rowIdNode=$(rowNode).find("[name='"+nodeIdName+"']");  
				if(rowIdNode.val()=="" ){
					return;
				}  
			}  
		}
		if(opts.effectRowFields!=null && opts.effectRowFields.length>0){
			var haveRecord=true;
			for(var s=0;s<opts.effectRowFields.length;s++){
				var fieldName=opts.effectRowFields[s];
				if(fieldName.indexOf(rowPrefix)<0){
					fieldName=rowPrefix+"."+fieldName;
				}
				var fieldNameNode=rowNode.find("[name='"+fieldName+"']");   
				if(fieldNameNode.length==1){ 
					if($.trim(fieldNameNode.val())==""  ){
						 
						haveRecord=false;
						break;
					}
				}
			}
			if(haveRecord==false) return; 
		}
		if( opts.callbackEvent!=null && $.isFunction(opts.callbackEvent)){  
		   var submitFlag=opts.callbackEvent.call(operateObj ,rowNode); 
		   if(false==submitFlag) {
			   return ;
		   }
	    }
		 
		$.ts.EasyUI.submitRowToList(rowNode, index, rowPrefix); 
		if(opts.beanRows>1){
			var nextNode=rowNode.next();
			for(var n=1;n<opts.beanRows;n++){
				$.ts.EasyUI.submitRowToList(nextNode, index, rowPrefix); 
				nextNode=nextNode.next();
				if(nextNode.length<1) break;
			}
		}
		index++; 
	 });  
	 return index;
},
$.ts.EasyUI.clearElementsSubscript=function(nodeName,container,beanRows){ 
      var rootNode=null;
	 if(typeof(container)!="undefined" && container!=null){
		 if($.ts.Utils.isString(container)){
			 rootNode=$("#"+container);
		 } else {
			 rootNode=$(container);
		 }
	 } else { 
	     var e=jQuery.event.fix(event || window.event);
	     var operateObj= e.target;   
		 rootNode=$(operateObj).parents("form");   
	 } 
	 var rowPrefix=nodeName.substring(0,nodeName.indexOf(".")); 
	  var nodes=rootNode.find("[name^='"+rowPrefix+"[']"); 
	   $.each(nodes, function(i,node){
		   var jNode=$(node);
		   var nodeN=jNode.attr("name").substring(jNode.attr("name").indexOf(".")+1); 
		   jNode.attr("name",rowPrefix+"."+nodeN); 
	   });
},
$.ts.EasyUI.submitRowToList=function(rowNode,index,rowPrefix){ 
	  var nodes=rowNode.find("[name^='"+rowPrefix+".']"); 
	   $.each(nodes, function(i,node){
		   var jNode=$(node);
		   var nodeName=jNode.attr("name").substring(jNode.attr("name").indexOf(".")+1); 
		   jNode.attr("name",rowPrefix+"["+index+"]."+nodeName); 
	   });
},

$.ts.EasyUI.copyTableRow=function(obj,dataOpts,rowData){
  var opts={table:"",rowPrefix:"",id:"id",recordOperateStatus:"recordOperateStatus",
  tableRowNum:"tableRowNum",copyRows:1,cloneRowFlag:"cloneRowFlag",callbackEvent:null};
  if($.ts.Utils.isEmpty(dataOpts)==false){
		if($.ts.Utils.isString(dataOpts)){
			dataOpts=$.ts.Utils.parseOpts(dataOpts);
		}
		$.extend( opts ,dataOpts);
		if($.ts.Utils.isEmpty(dataOpts.tsOptions)==false){
		  var cls=dataOpts.tsOptions.containerObj.attr("class"); 
		  if(cls.indexOf("easyui-datagrid")>=0){ 
			    if($.ts.Utils.isEmpty(dataOpts.insertRowEvent)==false && $.isFunction(dataOpts.insertRowEvent)){ 
				   var insertRowEvent=dataOpts.insertRowEvent;
				   insertRowEvent.call(obj,dataOpts,rowData);
				   return ;
			    }
			    
				var template=opts.tsOptions.recDataTemplate;
				var newRow={}; 
				var len=(opts.tsOptions.rowPrefix).length+1;
				for(var i=0;i<template.length;i++){
					var field=template[i].field;  
					for(var n=1;n<field.length;n++){  
						var key=field[n];
						if(key.indexOf(".")>0){
							key=key.substring(len);
						} 
						$.ts.Utils.setChainPropertyValue(newRow,key,rowData[field[0]]); 
					}
				} 
				newRow.recordOperateStatus="add"; 
				debugger;
				var table= dataOpts.tsOptions.containerObj ;  
	 			table.datagrid('appendRow',newRow); 
			    var ids=table.parent().find("input[name='"+opts.rowPrefix+"."+opts.id+"']");   
			    var row=$(ids[ids.length-1]).parent().parent().parent() ; 
				try{
			   	  $.parser.parse(row);
				} catch(e){$.messager.alert('提示', e);}  
				var fix=opts.tsOptions.rowPrefix;
				var tmp={};  
				tmp[fix]=newRow; 
		  		row.tsLoadData(tmp); 
			   return;
		  }
	   }
  }   
   
  if($.ts.Utils.isEmpty(obj)){
	  var e=jQuery.event.fix(event || window.event);
	  obj= e.target;
  }
  var p=$(obj).parent();
  var cloneRow=null;
  while(p!=null && p.length>0){
	  cloneRow=p.find("["+opts.cloneRowFlag+"='1']");
	  if(cloneRow.length>0){
		  break;
	  } else {
		  p=p.parent();
	  }
  }
  if(cloneRow==null || cloneRow.length==0) return ; 
  var copyRow=cloneRow.clone().attr(opts.cloneRowFlag,"0");
  copyRow.css('display','');  
  $(cloneRow[0]).before(copyRow); 
  $.ts.EasyUI.updateContainerRowNum($(cloneRow[0]).parent(),opts.tableRowNum);
  if(copyRow!=null && opts.callbackEvent!=null && $.isFunction(opts.callbackEvent)){  
	   opts.callbackEvent.call(obj ,copyRow,rowData); 
	   //alert($(obj).data("rowData").name );
  } 
  return copyRow;
}
$.ts.EasyUI.deleteTableRows=function(obj,dataOpts){
	var tableJq=$(obj);
	var nodes=tableJq.find("input[name$='recordOperateStatus']");
	for(var i=0;i<nodes.length;i++){
		$.ts.EasyUI.deleteTableRow(nodes[i],dataOpts);
	}
}
$.ts.EasyUI.deleteTableRow=function(obj,dataOpts){
	var opts={table:"",rowPrefix:"",id:"id",recordOperateStatus:"recordOperateStatus",tableRowNum:"tableRowNum",
	deleteRows:1,callbackEvent:null}; 
	if($.ts.Utils.isEmpty(dataOpts)==false){
		if($.ts.Utils.isString(dataOpts)){
			dataOpts=$.ts.Utils.parseOpts(dataOpts); 
		} 
		opts=$.extend( opts,dataOpts); 
	}    
	var gridTr=$(obj); 
	 
	while(gridTr!=null && gridTr.length>0 && gridTr[0].tagName!="TR"){
		gridTr=gridTr.parent();  
	}
	
	var index = $(gridTr).index();  
	if($.ts.Utils.isEmpty(opts.rowPrefix)){
		var node=gridTr.find("[name$='."+opts.id+"']"); 
		if(node.length>0){
			var nodeName=$(node[0]).attr("name");
			opts.rowPrefix=nodeName.substring(0,nodeName.indexOf(".")); 
		}
	} 
	if($.ts.Utils.isEmpty(opts.table)){
		opts.table=$("#"+opts.rowPrefix+"Grid");
	} else if($.ts.Utils.isString(opts.table)){
		opts.table=$("#"+opts.table );
	}
	if(opts.table.length==0){
		 var grid=$(gridTr); 
		 while(grid!=null && grid.length>0 && grid[0].tagName!="TABLE"){
			grid=grid.parent();  
		}
		opts.table=grid;
	}
	var idNode= gridTr.find("[name='"+opts.rowPrefix+"." + opts.id + "']"); 
	if($.ts.Utils.isEmpty(opts.tsOptions)==false){
		opts.table=opts.tsOptions.containerObj;
	}
	var id="";
	if(idNode.length>0) 
		id=idNode.val(); 
	var deleteRow=null;
	var isEasyTable=true; 
	if(typeof(opts.table.attr("class"))=="undefined"  || opts.table.attr("class").indexOf("easyui-")<0){
		isEasyTable=false;
		if(opts.deleteRows==1){
			var cloneRows=opts.table.find("[cloneRowFlag='1']");
			if(cloneRows.length>0){
				opts.deleteRows=cloneRows.length;
			}
		}
	}  
	var getCopyNodes=function (){
		var deleteNodes=new Array(); 
		deleteNodes.push(gridTr);
		if(opts.deleteRows>1){
			var nextNode=gridTr ; 
			for(var n=1;n<opts.deleteRows;n++){ 
				nextNode=nextNode.next(); 
				if(nextNode.length<1) break;
				deleteNodes.push(nextNode);
			}
		}  
		return deleteNodes;
	}
	if(id=="" || id=="0"){  
		if(isEasyTable==false){  
		    if(gridTr.attr("cloneRowFlag")=="1"){
				//$.messager.alert('提示', "不能删除原始数据！"); 
				return ;
			}
		    var deleteNodes=getCopyNodes(); 
			for(var n=0;n<deleteNodes.length;n++){
				$(deleteNodes[n]).remove(); 
			}
		} else {
			 
			opts.table.datagrid('deleteRow', index);
			if(opts.deleteRows>1){ 
				for(var n=1;n<opts.deleteRows;n++){
					opts.table.datagrid('deleteRow', index);
				}
			}
		}
	} else {
		var recordOperateStatus= gridTr.find("input[name='"+opts.rowPrefix+".recordOperateStatus']"); 
		 
		if(recordOperateStatus.length>0){
			if($(obj).attr("type")=="checkbox") 
			  recordOperateStatus.val((obj.checked==true?"delete":"update"));  
			else  
			  recordOperateStatus.val("delete");  
			if(isEasyTable==false){  
				if(gridTr.attr("cloneRowFlag")=="1"){
					$.messager.alert('提示', "不能删除原始数据！"); 
					return ;
				}
				var deleteNodes=getCopyNodes();
				for(var n=0;n<deleteNodes.length;n++){
					$(deleteNodes[n]).css({"display":"none"}); 
				}
			}  else {
				var deleteNodes=getCopyNodes();
				for(var n=0;n<deleteNodes.length;n++){
					$(deleteNodes[n]).hide();
				}
			}
		}
	}  
	if(isEasyTable){
		var totalRows=opts.table.datagrid("getRows").length;
		if(totalRows<=index)
			index=index-1; 
		if(index>=0) 
			opts.table.datagrid("selectRow",index );  
	}  
	$.ts.EasyUI.updateContainerRowNum(opts.table,opts.tableRowNum); 
	if(deleteRow!=null && opts.callbackEvent!=null && $.isFunction(opts.callbackEvent)){  
	   opts.callbackEvent.call(obj ,gridTr); 
	}
},
$.ts.EasyUI.updateContainerRowNum=function(container,tableNum){
	var tableNums=$(container).find("[name='"+tableNum+"']");
	if(tableNums.length>0){ 
	    var i=1;
		for(var n=0;n<tableNums.length;n++){  
		    var p=$(tableNums[n]).parent();
			var isAdd=true;
			while(p!=null && p.length>0){ 
				if(p[0].tagName=="TR"){
					
					if(p[0].style.display=="none"){
						isAdd=false;
					}
					break;
				}
				p=p.parent();
			}
			if(isAdd){
				$(tableNums[n]).html(i);
				i++;
			}
		}
	}
}

$.ts.EasyUI.titleAppendToolbar=function(sourceId,targetId,align,calHeight){
	var target=$("#"+targetId);
	var sourceToolbar=$("#"+sourceId);
	sourceToolbar.css("white-space","nowrap");
	sourceToolbar.css("display","inline-block");
	sourceToolbar.css("padding","2px");
	var header=null;
	var className=target.attr("class"); 
	 
	try{
		if(className.indexOf("easyui-panel")>=0){  
			header=target.panel("header");
		} else if(className.indexOf("easyui-datagrid")>=0){
			header=target.datagrid("getPanel").panel("header");
		} else if(className.indexOf("datagrid-f")>=0){
			header=target.datagrid("getPanel").panel("header");
		}
		var titleToolbar=header.find(".panel-title");
		titleToolbar.css("white-space","nowrap");
		titleToolbar.css("display","inline-block");   
		if(true==calHeight)
			titleToolbar.css("height",sourceToolbar.height());   
		//alert(titleToolbar.length);  
		titleToolbar.append(sourceToolbar); 
		if($.ts.Utils.isEmpty(align)==false && "center"==align){
			sourceToolbar.css("position","absolute");  
			sourceToolbar.css("left", ( target.width() - sourceToolbar.width() ) / 2+$(window).scrollLeft() + "px");
			sourceToolbar.css("top",  "-2px");
		}  else if($.ts.Utils.isEmpty(align)==false && "left"==align){
			sourceToolbar.css("position","absolute");  
			sourceToolbar.css("left",titleToolbar.css("width")+ "px");
			sourceToolbar.css("top",  "-2px");
		} else {
			sourceToolbar.css("position","absolute");  
			sourceToolbar.css("left",titleToolbar.css("width")+ "px");
			sourceToolbar.css("top",  "-2px");  
		}
	}catch (e){alert(e)}
}
 
$.ts.EasyUI.titleAppendDialog=function(sourceId,targetId,align){
	var target=$("#"+targetId);
	var sourceToolbar=$("#"+sourceId);
	sourceToolbar.css("white-space","nowrap");
	sourceToolbar.css("display","inline-block");
	sourceToolbar.css("padding","5px"); 
	var header=null;  
	try{ 
		header=target.prev();  
		var titleToolbar=header.find(".panel-title"); 
		titleToolbar.css("white-space","nowrap");
		titleToolbar.css("display","inline-block");   
		titleToolbar.append(sourceToolbar); 
		if($.ts.Utils.isEmpty(align)==false && "center"==align){
			sourceToolbar.css("position","absolute");  
			sourceToolbar.css("left", ( target.width() - sourceToolbar.width() ) / 2+$(window).scrollLeft() + "px");
			sourceToolbar.css("top",  "-12px");
		}  else if($.ts.Utils.isEmpty(align)==false && "left"==align){
			sourceToolbar.css("position","absolute");  
			sourceToolbar.css("left",titleToolbar.css("width")+ "px");
			sourceToolbar.css("top",  "-12px");
		} else {
			sourceToolbar.css("position","absolute");  
			sourceToolbar.css("left",titleToolbar.css("width")+ "px");
			sourceToolbar.css("top",  "-12px");  
		}
	}catch (e){}
}
$.ts.EasyUI.ajaxSubmitAppForm=function(url,formId,callbackEvent,errorCallbackEvent){
	 if(formId.indexOf("#")==0){
		 formId=formId.substring(1);
	 }
	 $("#"+formId).mask(); 
	 $("#"+formId).ajaxSubmit( {
		url:url,   
		type: 'post',  
		datatype: "json",
		success : function(result) {    
			 try{   
			   
				   if($.ts.Utils.isString(result)){  
				   	  var errorHint=false;
				      for(var i=0;i<$.ts.constant.errorPageBodyIds.length;i++){
						  if(result!=null && result.indexOf($.ts.constant.errorPageBodyIds[i])>0){
							  errorHint=true;
							  break;
						  }
					  }  
				      if(errorHint ){
						  $("#"+formId).mask("hide");  
						  $.ts.EasyUI.showContentDialog(result);  
						  if($.ts.Utils.isEmpty(errorCallbackEvent)==false && $.isFunction(errorCallbackEvent)){
							 errorCallbackEvent($("#"+formId));
						 }
						  return ;
					  } else {
					      result=jQuery.parseJSON(result);
					  }
				   }    
				  
				   if($.ts.Utils.isEmpty(callbackEvent)==false && $.isFunction(callbackEvent)){
					   if($.ts.Utils.isEmpty(result.error)==true && $.ts.Utils.isEmpty(result.alertMsg)==true){		// plq修改
						   callbackEvent.call($("#"+formId),result); 
					   }
				   }
				   var state=result.statememt;
				  
				   if($.ts.Utils.isEmpty(result.error)==false || $.ts.Utils.isEmpty(result.alertMsg)==false){
						state=result.error;
						if($.ts.Utils.isEmpty(result.alertMsg)==false)
						  state=result.alertMsg;
						$.messager.alert('提示',state); 
						$("#"+formId).mask("hide");  
						return ;
				   }
				   $("#"+formId).mask("hide");   
				   if($.ts.Utils.isEmpty(result.statememt) )
				      result.statememt="操作成功！";
				    
				  
			 } catch(e){ 
				 $("#"+formId).mask("hide");  
			}  
		},
		error: function(XmlHttpRequest, textStatus, errorThrown){    
		     if($.ts.Utils.isEmpty(errorCallbackEvent)==false && $.isFunction(errorCallbackEvent)){
				 errorCallbackEvent($("#"+formId),XmlHttpRequest);
			 }
			 $("#"+formId).mask("hide"); 
			 var str=XmlHttpRequest.responseText; 
			 if($.ts.Utils.isEmpty(str)){
				  str=XmlHttpRequest.responseXML;
			 }
			 str=str+"<hr/>"+textStatus;
			 $.ts.EasyUI.showContentDialog(str); 
		 }  
	}); 
}
 $.ts.EasyUI.validateSubmitResult=function(result){
	 if($.ts.Utils.isString(result)){   
		  var errorHint=false; 
		  for(var i=0;i<$.ts.constant.errorPageBodyIds.length;i++){
			  if(result!=null && result.indexOf($.ts.constant.errorPageBodyIds[i])>0){
				  errorHint=true; 
				  break;
			  }
		  }   
		  if(errorHint==false ){
			  if(result.indexOf("\"alertMsg\"")>0 || result.indexOf("\"error\"")>0 ){
				  errorHint=true;   
				  var errorJq=$.parseJSON($(result).html());   
				  result=errorJq.alertMsg;
				  //result=result.replace(/<br\/>/g, "<br>")
				  result=new $.ts.String(result).replaceAll("br","<br>");
			  } 
		  } 
		  if(errorHint ){
			   
			  $.ts.EasyUI.showContentDialog(result);  
			   
			  return false;
		  } else { 
			  return true;
		  }
	   }  
 }
$.ts.EasyUI.ajaxSubmitForm=function(url,formId,callbackEvent,errorCallbackEvent){
	 if(formId.indexOf("#")==0){
		 formId=formId.substring(1);
	 }
	 var isMask=true;
	 var displayMaskFlag=$("#"+formId).find("#displayMaskFlag");
	 if(displayMaskFlag!=null && displayMaskFlag.length>0){
		 if(displayMaskFlag.val()=="0"){
			 isMask=false;
		 }
	 }
	 if(isMask){
		 $("#"+formId).mask(); 
	 }
	 $("#"+formId).ajaxSubmit( {
		url:url,   
		type: 'post',  
		datatype: "json",
		success : function(result) {     
			 try{    
				   if($.ts.Utils.isString(result)){   
				   	  var errorHint=false; 
				      for(var i=0;i<$.ts.constant.errorPageBodyIds.length;i++){
						  if(result!=null && result.indexOf($.ts.constant.errorPageBodyIds[i])>0){
							  errorHint=true;
							  break;
						  }
					  }  
					  
					  if(errorHint==false ){
						  if(result.indexOf("\"alertMsg\"")>0 || result.indexOf("\"error\"")>0 ){
							  errorHint=true;   
							  var errorJq=$.parseJSON($(result).html());   
							  result=errorJq.alertMsg;
							  //result=result.replace(/<br\/>/g, "<br>")
							  result=new $.ts.String(result).replaceAll("br","<br>");
						  } 
					  } 
				      if(errorHint ){
						  if(isMask){
						 	 $("#"+formId).mask("hide");  
						  }
						  $.ts.EasyUI.showContentDialog(result);  
						  if($.ts.Utils.isEmpty(errorCallbackEvent)==false && $.isFunction(errorCallbackEvent)){
							 errorCallbackEvent($("#"+formId));
						 }
						 //$.ts.EasyUI.closeDialog($("#"+formId),"0"); 
						  return ;
					  } else {  
					      if($.ts.Utils.isEmpty(errorCallbackEvent)==false && $.isFunction(errorCallbackEvent)==false  && $.ts.Utils.isString(errorCallbackEvent)){
							callbackEvent.call($("#"+formId),result); 
							return;
						  }
					      result=jQuery.parseJSON(result); 
					  }
				   }    
				  
				   if($.ts.Utils.isEmpty(callbackEvent)==false && $.isFunction(callbackEvent)){
					   if($.ts.Utils.isEmpty(result.error)==true && $.ts.Utils.isEmpty(result.alertMsg)==true){		// plq修改
						   callbackEvent.call($("#"+formId),result); 
					   }
				   }
				   var state=result.statememt; 
				   if($.ts.Utils.isEmpty(result.error)==false || $.ts.Utils.isEmpty(result.alertMsg)==false){
						state=result.error;
						if($.ts.Utils.isEmpty(result.alertMsg)==false)
						  state=result.alertMsg;
						$.messager.alert('提示',state); 
						if(isMask){
							$("#"+formId).mask("hide");  
						}
						return ;
				   }
				   if(isMask){
				   	 $("#"+formId).mask("hide");   
				   }
				   if($.ts.Utils.isEmpty(result.statememt) )
				      result.statememt="操作成功！";
				   if(isMask){
					   $.messager.show({
						title:'操作提示',
						msg:result.statememt,
						timeout:$.ts.constant.timeout,
						style:{
							right:'',
							bottom:''
						}
					  });  
					} 
				  
			 } catch(e){ 
				 if(isMask){
					 $("#"+formId).mask("hide");  
				 }
			}  
		},
		error: function(XmlHttpRequest, textStatus, errorThrown){     
		     if($.ts.Utils.isEmpty(errorCallbackEvent)==false && $.isFunction(errorCallbackEvent)){
				 errorCallbackEvent($("#"+formId),XmlHttpRequest);
			 }
			 if(isMask){
			 	$("#"+formId).mask("hide"); 
			 }
			 var str=XmlHttpRequest.responseText; 
			 if($.ts.Utils.isEmpty(str)){
				  str=XmlHttpRequest.responseXML;
			 }
			 str=str+"<hr/>"+textStatus;
			 $.ts.EasyUI.showContentDialog(str); 
		 }  
	}); 
}
$.ts.EasyUI.showErrorDialog=function(result){
	if($.ts.Utils.isString(result)  ){
		 var errorHint=false;
		  for(var i=0;i<$.ts.constant.errorPageBodyIds.length;i++){
			  if(result!=null && result.indexOf($.ts.constant.errorPageBodyIds[i])>0){
				  errorHint=true;
				  break;
			  }
		  }
		if(errorHint==true){ 
			$.ts.EasyUI.showContentDialog(result);
		} else {
			$.messager.alert('提示', result);
		}
		return true;
	}
	return false;
}
$.ts.EasyUI.showContentDialog=function(content){
	var opt={title:"Error Hint",width : 800, 
		height : 400,
		modal:false,content:content,method:"get",collapsible:true,  
		maximizable: true, resizable:true   
	}
	$.ts.EasyUI.modalDialog(opt);
}
$.ts.EasyUI.setComboboxText=function(obj){
	try{
	var value=obj.combobox('getValue');
	var d=obj.combobox('getData');
	
	for(var i=0;i<d.length;i++){ 
		if(d[i].value==value){ 
			obj.combobox('setText',d[i].text);
			break;
		}
	}
	}catch(e){}
} 
$.ts.EasyUI.appendComboboxData=function(postList,selectControl){
	    var selectedValue=selectControl.getAttribute("value");
		if($.ts.Utils.isEmpty(selectControl.getAttribute("selectedValue"))==false){
			selectedValue=selectControl.getAttribute("selectedValue");
		} 
		 while(selectControl.childNodes.length > 0) {
			selectControl.removeChild(selectControl.childNodes[0]);
		} 
		var option = document.createElement("option"); 
		option.value="";
		option.appendChild(document.createTextNode(""));
		selectControl.appendChild(option);    
		
		if(postList!=null && postList.length!=null)
		for(var i=0;i<postList.length;i++){
			var record=postList[i];
			var option = document.createElement("option");    
			var id=record.code;
			if($.ts.Utils.isEmpty(record.id)==false){
				id=record.id;  
			}
			option.value=id;    
			if(selectedValue==id){
				option.selected=true; 
			} 
			var name=record.name;
			if($.ts.Utils.isEmpty(record.text)==false){
				name=record.text;  
			}
			option.appendChild(document.createTextNode(name ));
			selectControl.appendChild(option); 
		}
}
$.ts.EasyUI.loadComboboxData=function(sql,callbackEvent,ops){
	  var jsonPara=[{keyName:"queryList",sql:sql,isHql:"0",insertEmpty:"1"}];  
	  var dataOpts={async:true};
	  if($.ts.Utils.isEmpty(ops)==false){
		 dataOpts= $.extend(dataOpts,ops);
	  } 
	  $.ts.DB.queryMultiDataset(jsonPara,dataOpts, function(handler,resultJson){  
		    if($.isArray(resultJson.queryList)==false){
				resultJson.queryList=new Array();
			} 
			resultJson.queryList.push({id:0,text:$.ts.constant.blankStr});
			if($.ts.Utils.isEmpty(callbackEvent)==false && $.isFunction(callbackEvent))
			   callbackEvent(handler,resultJson.queryList);  
	  }); 
}
$.ts.EasyUI.formatDatagridParam=function (resultJson){
	if(resultJson!=null && resultJson.customProperty!=null){
		var cp=resultJson.customProperty; 
		$.extend(resultJson,cp); 
		resultJson.customProperty="undefined";  
	}  
	if($.ts.Utils.isEmpty(resultJson.dataOptions)==false) {  
		resultJson.dataOptions=$.trim(resultJson.dataOptions);
		if(resultJson.dataOptions.indexOf("{")!=0){
			resultJson.dataOptions="{"+resultJson.dataOptions+"}";
		} 
		var dataOptions=(new Function("return "+resultJson.dataOptions))();  
		
		$.extend( resultJson ,dataOptions);
		
		resultJson.dataOptions="undefined";  
	}  
};
$.ts.EasyUI.formatGridColumns=function (resultJson){
		var columns=resultJson.columns[0]; 
		for(var n=0; n<columns.length; n++){
			var col =columns[n];  
			$.ts.EasyUI.formatDataOptions(col); 
			if(typeof(col.formatter)!="undefined") { 
			    var fun=(new Function("return "+col.formatter))();   
				col.formatter= fun;  
			}
		}
	}; 
$.ts.EasyUI.formatDataOptions=function (obj){
	if(obj!=null && typeof(obj.dataOptions)!="undefined" && obj.dataOptions!=null){
		obj.dataOptions=$.trim(obj.dataOptions);
		if(obj.dataOptions.indexOf("{")!=0){
			obj.dataOptions="{"+obj.dataOptions+"}";
		}
		var dataOptions=(new Function("return "+obj.dataOptions))();  
		$.extend(obj,dataOptions);
		//obj["data-options"]=obj.dataOptions; 
		obj.dataOptions="undefined";  
	}   
}; 
$.ts.EasyUI.loadReportGrid=function(urlJson,grid,callbackEvent,ops){
	 var that =this;
	 var urlBean={urlType:"initReportJson" }; 
	  $.extend( urlBean ,urlJson);
	  var url=$.ts.Utils.toUrlParam(urlBean); 
	 $.ajax( {
		url: url, 
		datatype: "json",
		type: "POST",  
		async:true,
		success:function(result,textStatus){ 
			 var reportDatagrid= result[0];
			 var resultJson = reportDatagrid.datagrid;   
			 $.ts.EasyUI.formatDatagridParam(resultJson);  
			resultJson.rows=null;  
			 $.ts.EasyUI.formatGridColumns(resultJson); 
			 //grid.attr("reportType",resultJson.reportType);
			 if($.ts.Utils.isEmpty(callbackEvent)==false ){
				 $.extend( resultJson,callbackEvent);
			 } 
			 grid.combogrid(resultJson); 
			  
		 }, 
		error:function(XMLHttpRequest, textStatus, errorThrown){
			var str=XmlHttpRequest.responseText; 
			 if($.ts.Utils.isEmpty(str)){
				  str=XmlHttpRequest.responseXML;
			 }
			 str=str+"<hr/>"+textStatus;
			 $.ts.EasyUI.showContentDialog(str); 
		}
	} ); 
}
$.ts.EasyUI.queryReportResult=function(urlJson, grid,callbackEvent,ops){ 
	 var urlBean={urlType:"queryReportResult" }; 
	 $.extend( urlBean ,urlJson);
	 var url=$.ts.Utils.toUrlParam(urlBean);  
	 var that =this;
	 $.ajax( {
		url: url , 
		datatype: "json",
		type: "POST",  
		async:true,
		success:function(result,textStatus){ 
			var reportDatagrids= result[0];
			
			 var resultJson = reportDatagrids.datagrid;    
			 var loadRows = { 'rows':resultJson.rows};   
			 if(grid!=null){
				 var g = grid.combogrid('grid');	 
				 g.datagrid("loadData",loadRows);  
			 } 
			 if($.ts.Utils.isEmpty(callbackEvent)==false && $.isFunction(callbackEvent)){ 
				 callbackEvent.call(that,grid,resultJson);
			 }
		 }, 
		error:function(XMLHttpRequest, textStatus, errorThrown){
			var str=XmlHttpRequest.responseText; 
			 if($.ts.Utils.isEmpty(str)){
				  str=XmlHttpRequest.responseXML;
			 }
			 str=str+"<hr/>"+textStatus;
			 $.ts.EasyUI.showContentDialog(str); 
		}
	} ); 
}
$.ts.Utils=function(){ 
	this.className="ts.Utils";
}
$.ts.Utils.setChainPropertyValue=function(obj,field,value){
	if(field.indexOf(".")>0){
		var fields=field.split(".");
		var chainValue=null;
		if(!obj[fields[0]]){
			chainValue={};
			obj[fields[0]]=chainValue; 
		} else {
			chainValue=obj[fields[0]]; 
		}
		for(var n=1;n<fields.length-1 ;n++){
			if(!chainValue[fields[n]]){
				chainValue[fields[n]]={}; 
			} else{
				chainValue=chainValue[fields[n]];
			} 
		}
		chainValue[fields[fields.length-1]]=value; 
	} else {
		obj[field]=value;
	}
}
$.ts.Utils.onLineBrowseFile=function(fileName){
  var url=tsContextPath+"/core/downloadFileService/onLineBrowse.do?name="+fileName; 
  $.ts.EasyUI.frameDialog(  {  
			maximized:true, title:"在线查阅", modal:false, method:"POST",  cache:false,
			collapsible: true, maximizable: true, resizable:true,href:url   
  } );
}

$.ts.Utils.getContainerRowNode=function(nodeName,node,rootNode){
	var rowNode= $(node).parent();  
	while(rowNode!=rootNode && rowNode!=null && rowNode.length>0){
		var tmpNode= rowNode.parent(); 
		if(tmpNode==rootNode){
			break;
		} else {
			var parentNode=$(tmpNode).find("[name='"+nodeName+"']"); 
			if(parentNode.length>1){ 
				break;
			}  
		}
		if(tmpNode.length>0)
		  rowNode=rowNode.parent();   
		else break;
		if($.ts.Utils.isEmpty(rowNode.attr("cloneRowFlag"))==false)  break ;
	}
	return rowNode;
}; 
$.ts.Utils.getContainerRowToBean=function(recDataTemplate,rowNode){ 
	var bean={}; 
	for(var i=0;i<recDataTemplate.length;i++){
		var beanTemplate=recDataTemplate[i];
		if("1"==beanTemplate.sameRecordFlag ){
			nodeName=beanTemplate.field[1];
			var node=$(rowNode).find("[name='"+beanTemplate.field[1]+"']");
			var value=$.ts.Utils.getContainerRowNodeValue(node); 
			bean[beanTemplate.field[0]]=value; 
		}
	} 
	return bean;
};
$.ts.Utils.getContainerRowNodeValue=function(node){
	if(node.is("input")==true || node.is("select")==true){
		return node.val();
	} else if(node.is("span")==true || node.is("div")==true ){
		return node.text();
	} else if(node.is("textarea")==true){
		return node.val();
	} else if(node.is("select")==true){
		return node.val();
	}
};
$.ts.Utils.setContainerRowNodeValue=function(node,value){
	if(node.is("input")==true || node.is("select")==true){
		return node.val(value);
	} else if(node.is("span")==true || node.is("div")==true ){
		return node.text(value);
	} else if(node.is("textarea")==true){
		return node.val(value);
	} else if(node.is("select")==true){
		return node.val();
	}
};
$.ts.Utils.getContainerObj=function(tsOptions){ 
	if($.ts.Utils.isEmpty(tsOptions.containerObj)==false)
		return tsOptions.containerObj;
	var rootNode=null;   
	if($.ts.Utils.isEmpty(tsOptions.container)==false){
		 if($.ts.Utils.isString(tsOptions.container)){
			 rootNode=$("#"+tsOptions.container);
			 if($.ts.Utils.isEmpty(tsOptions.containerObj))
			 	tsOptions.containerObj=rootNode;
		 } else {
			 rootNode=$(container);
		 }
	} else {  
		 rootNode=$("form");   
	} 
	return rootNode;
};
$.ts.Utils.deleteDialogChooseRow=function(obj,tsOptions,dataOpts,deleteRowObj){
	var recDataTemplate=tsOptions.recDataTemplate;  
	var rowJq=null;
	if($.ts.Utils.isEmpty(deleteRowObj)==false){
		rowJq=deleteRowObj;
	} else{ 
		rowJq=$(obj); 
		while(rowJq!=null && rowJq.length>0 && rowJq[0].tagName!="TR"){
			rowJq=rowJq.parent();  
		}
	}
	var bean=$.ts.Utils.getContainerRowToBean(recDataTemplate,rowJq);  
	if($.ts.Utils.isEmpty(tsOptions.selectedRows)){
		$.ts.Utils.getContainerRowsValue(tsOptions); 
	}   
	var index=$.ts.Utils.findListRowIndex(bean,tsOptions.selectedRows); 
	if(index>=0){
		$.ts.EasyUI.deleteTableRow(obj,dataOpts);
		tsOptions.selectedRows.removeItemAt(index);
	} 
};
$.ts.Utils.getContainerNodeName=function(recDataTemplate){
	var nodeName="";
	for(var i=0;i<recDataTemplate.length;i++){
		var beanTemplate=recDataTemplate[i];
		if(beanTemplate.sameRecordFlag=="1"){
			nodeName=beanTemplate.field[1];
			break;
		}
	}
	if(nodeName==""){
		var beanTemplate=recDataTemplate[0];
		nodeName=beanTemplate.field[1];
	}
	return nodeName;
};
$.ts.Utils.getContainerRowsValue=function(tsOptions){
	var recDataTemplate=tsOptions.recDataTemplate;
	if($.ts.Utils.isEmpty(recDataTemplate)) return null; 
	var container=tsOptions.container;
	var rootNode=$.ts.Utils.getContainerObj(tsOptions);
	var nodeName=$.ts.Utils.getContainerNodeName(recDataTemplate); 
	var nodes=$(rootNode).find("[name='"+nodeName+"']"); 
	var rows=new $.ts.Array();  
	for(var i=0;i<nodes.length;i++){
		var node=nodes[i];
		var rowNode=$.ts.Utils.getContainerRowNode(nodeName,node,rootNode);
		if($(rowNode).find("[cloneRowFlag='1']").length>0) continue;
		var bean=$.ts.Utils.getContainerRowToBean(recDataTemplate,rowNode); 
		rows.push(bean);
	}
	tsOptions.selectedRows=rows;
	return rows;
};
$.ts.Utils.isString=function(obj){
	return (typeof obj=='string')&& obj.constructor==String; 
}
$.ts.Utils.capitalize=function(str){
	if(str==null||str=="") 
		return str; 
	if(str.length==1) 
		return str.toUpperCase(); 
	var nstr=str.charAt(0).toUpperCase()+str.substring(1);
	return nstr;
};
$.ts.Utils.getDateRange=function(rangeType,dateStr){ 
	var startDate="",endDate="";
	var date = new $.ts.Date();
	if($.ts.Utils.isEmpty(dateStr)==false){
		date =$.ts.Utils.stringToDate(dateStr);
	}
	var formatStr="YYYY-MM-dd",mFormat="YYYY-MM-01";
	switch (rangeType) {
		case "date":startDate=date.format(formatStr);
		                   endDate=startDate;break; 
		case "yesterday":startDate=date.dateAdd("d",-1).format(formatStr);
		                   endDate=startDate;break; 
		case "week":var weekIndex=date.getDay();if(weekIndex==0) weekIndex=7;
		                   var startDate= date.dateAdd("d",  -weekIndex+1).format(formatStr);          
		                   endDate= date.dateAdd("d",  7-weekIndex).format(formatStr);break;    
		case "prevWeek":var weekIndex=date.getDay();if(weekIndex==0) weekIndex=7;
		                   var endDate=date.dateAdd("d",-weekIndex).format(formatStr);          
		                   startDate= date.dateAdd("d",  -7-weekIndex+1).format(formatStr);break; 
		case "month":startDate=date.format(mFormat);   
		                   var montStartDate=$.ts.Utils.stringToDate(startDate);                                      
		                   endDate= montStartDate.dateAdd("m",1).dateAdd("d",-1).format(formatStr);break;
		case "prevMonth":startDate=date.format(mFormat);   
		                   var montStartDate=$.ts.Utils.stringToDate(startDate);                                      
		                   endDate= montStartDate.dateAdd("d",-1).format(formatStr);
						   startDate=montStartDate.dateAdd("m",-1).format(formatStr);  break;    
		case "halfYear": if(date.datePart("m")<=6)                        
						   	startDate=date.format(mFormat);   
						   else 
						   	startDate=date.format(mFormat);  
		                   var montStartDate=$.ts.Utils.stringToDate(startDate);                                      
		                   endDate= montStartDate.dateAdd("m",6).dateAdd("d",-1).format(formatStr);  break;   				       case "year":startDate=date.format("yyyy-01-01");   
		                   var montStartDate=$.ts.Utils.stringToDate(startDate);                                      
		                   endDate= montStartDate.dateAdd("m",12).dateAdd("d",-1).format(formatStr);  break;
		case "prevYear":startDate=date.format("yyyy-01-01");   
		                var montStartDate=$.ts.Utils.stringToDate(startDate);                                      
		                startDate= montStartDate.dateAdd("m",-12).format(formatStr); 
						endDate= montStartDate.dateAdd("d",-1).format(formatStr);  break;  			                
	}
	var dates=new Array();
	dates[0]=startDate;dates[1]=endDate;
	return dates;
};
$.ts.Utils.daysBetween=function(DateOne, DateTwo) {
	var OneMonth = DateOne.substring(5, DateOne.lastIndexOf("-"));
	var OneDay = DateOne.substring(DateOne.length, DateOne.lastIndexOf("-") + 1);
	var OneYear = DateOne.substring(0, DateOne.indexOf("-"));
	var TwoMonth = DateTwo.substring(5, DateTwo.lastIndexOf("-"));
	var TwoDay = DateTwo.substring(DateTwo.length, DateTwo.lastIndexOf("-") + 1);
	var TwoYear = DateTwo.substring(0, DateTwo.indexOf("-"));
	var cha = ((Date.parse(OneMonth + "/" + OneDay + "/" + OneYear) - Date.parse(TwoMonth + "/" + TwoDay + "/" + TwoYear)) / 86400000);
	return Math.abs(cha);
 };
$.ts.Utils.isValidDate=function (DateStr) {
	var sDate = DateStr.replace(/(^\s+|\s+$)/g, ""); //去两边空格;   
	if (sDate == "") {
		return true;
	}     
	var s = sDate.replace("/[d]{ 4,4 }[-/]{ 1 }[d]{ 1,2 }[-/]{ 1 }[d]{ 1,2 }/g", ""); 
	 //说明格式满足YYYY-MM-DD或YYYY-M-DD或YYYY-M-D或YYYY-MM-D 
	if (s == "") {
		var t = new Date(sDate.replace(/\-/g, "/"));
		var ar = sDate.split("/[-/:]/");
		if (ar[0] != t.getYear() || ar[1] != t.getMonth() + 1 || ar[2] != t.getDate()) {    
			return false;
		}
	} else {     
		return false;
	}
	return true;
};
$.ts.Utils.stringToDate=function (DateStr) {
	var converted = Date.parse(DateStr);
	var myDate = new $.ts.Date(converted);
	if (isNaN(myDate)) {   
		var arys = DateStr.split("-");
		myDate = new Date(arys[0], --arys[1], arys[2]);
	}
	return myDate;
};
$.ts.Utils.isEmpty=function( ){ 
	for(var i=0;i<arguments.length;i++){
	 	var str= arguments[i]; 
	 	if(str==undefined || $.trim(str)=="" || str==null || str=="null"){
			return true;
		}
	}
	return false; 
};
$.ts.Utils.isNumberic=function(str){  
	var num=$.ts.Number(str);
	if(isNaN(num)) { 
		return false;
	}
	return true;
};
$.ts.Utils.toUrlParam=function(urlJson){  
    var urlType=urlJson.urlType;
	var url="";
	if($.ts.Utils.isEmpty(urlType)==false){
		url=$.ts.constant.urlType[urlType]; 
		delete urlJson.urlType;
	} else {
		 url=urlJson.url;
		 delete urlJson.url;
	}  
	var param=$.param(urlJson);
	if(param!=null && param!="")
	  url=url+"?"+$.param(urlJson);   
	return url;
};
$.ts.Utils.currentTime=function(){
	 return new Date().getTime();
 };
$.ts.Utils.getPosition=function(e, $menu) {          
	var mouseX = e.clientX
		, mouseY = e.clientY
		, boundsX = $(window).width()
		, boundsY = $(window).height()
		, menuWidth = $menu.outerWidth()//$menu.find('.dropdown-menu').outerWidth()
		, menuHeight =$menu.outerHeight()// $menu.find('.dropdown-menu').outerHeight()
		, tp = {"position":"absolute","z-index":9999}
		, Y, X, parentOffset;

	if (mouseY + menuHeight > boundsY) {
		Y = {"top": mouseY - menuHeight + $(window).scrollTop()};
	} else {
		Y = {"top": mouseY + $(window).scrollTop()};
	}

	if ((mouseX + menuWidth > boundsX) && ((mouseX - menuWidth) > 0)) {
		X = {"left": mouseX - menuWidth + $(window).scrollLeft()};
	} else {
		X = {"left": mouseX + $(window).scrollLeft()};
	} 
	parentOffset = $menu.offsetParent().offset();
	X.left = X.left - parentOffset.left;
	Y.top = Y.top - parentOffset.top; 
	return $.extend(tp, Y, X);      
};
$.ts.Utils.parseOpts=function(s) { 
    if($.isFunction(s)) return s;
	var options = {}; 
	if (s){ 
	    var s = $.trim(s);
		if (s.substring(0, 1) != '{'){
			s = '{' + s + '}';
		}
		options = (new Function('return ' + s))();
	} 
	return options;
}
$.ts.Utils.parseOptions=function(target, properties) {          
	var t = $(target);
	var options = {}; 
	var s = $.trim(t.attr('tsdata-options'));
	if (s){ 
		if (s.substring(0, 1) != '{'){
			s = '{' + s + '}';
		}
		options = (new Function('return ' + s))();
	} 
	if (properties){
		var opts = {};
		for(var i=0; i<properties.length; i++){
			var pp = properties[i];
			if (typeof pp == 'string'){
				if (pp == 'width' || pp == 'height' || pp == 'left' || pp == 'top'){
					opts[pp] = parseInt(target.style[pp]) || undefined;
				} else {
					opts[pp] = t.attr(pp);
				}
			} else {
				for(var name in pp){
					var type = pp[name];
					if (type == 'boolean'){
						opts[name] = t.attr(name) ? (t.attr(name) == 'true') : undefined;
					} else if (type == 'number'){
						opts[name] = t.attr(name)=='0' ? 0 : parseFloat(t.attr(name)) || undefined;
					}
				}
			}
		}
		$.extend(options, opts);
	}
	return options; 
}; 
$.ts.Utils.isIE=function( ){
	if(!!window.ActiveXObject || "ActiveXObject" in window){ 
		return true;
	} else {
		return false;
	}
}
$.ts.Utils.appendLookHistoricProcessButton=function(jq,param ){
	if(typeof(param.bean)!="undefined" && typeof(param.bean.processInstanceId)!="undefined"){
		var workFlowFlag=jq.find("workFlowFlag");
		if(param.bean.processInstanceId!=null && param.bean.processInstanceId!=""){
			if(jq.find("#bean\\.processInstanceId").length==0){ 
				jq.prepend('<input name="bean.processInstanceId" id="bean.processInstanceId" type="hidden"  />'); 
			} 
			if(workFlowFlag.length>0 && workFlowFlag.val()=="1"){ 
			
			} else { 
				var processJq=jq.find("#lookupHistoricProcessId"); 
				if(processJq.length==0){
					var str="<a href=\"javascript:void(0)\" class=\"easyui-linkbutton\" ";
					str=str+" id='lookupHistoricProcessId' data-options=\"iconCls:'icon-man' \" ";
					str=str+" onclick=\"$.ts.Utils.lookupHistoricProcess(document.getElementById('bean.processInstanceId').value)\">查看审核工作流</a>&nbsp;&nbsp;";
					var toolbar=jq.find("#formToolbar");
					if(toolbar.length>0){
						toolbar.prepend(str); 
					}
				}
			}
			
		}
	}
}
$.ts.Utils.lookupHistoricProcess=function(processInstanceId ){
	 var urls=tsContextPath+"/core/workFlowAuditService/lookupHistoricProcess.do?processInstanceId="+processInstanceId; 
		urls=urls+"&operateAuthCheckFlag=0";
		 $.ts.EasyUI.frameDialog(  {  
				width :800, height : $(document).height()-50,title:"查看历史审核记录", modal:false, method:"POST",  cache:false,
				collapsible: true, maximizable: true, resizable:true,href:urls   
	  } );  
}
$.ts.Utils.appendPrintContainer=function(containerId){ 
	var  content='<style media=print>'+
		'.noprint { display: none;color:green }'+
		'.ieNoprint{display:none;}'+
		'.PageNext{page-break-after: always;} '+
		'.autoPageNext{page-break-after: auto;} '+
  	  '</style> ';
	  content+='<center class="noprint" > '+
	  '<p>'+ 
	  '<OBJECT id="WebBrowser" classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0 width=0> '+
	  '</OBJECT> ';
	  if($.ts.Utils.isIE()){
		  content+='<input type=button value="直接打印" onClick="window.print();ts_executeBillPrintTimes();"    >' +
		  '<input type=button value="页面设置" onClick="document.getElementById(\'WebBrowser\').ExecWB(8,1)"  > '+
		  '<input type=button value="打印预览"  onClick="document.getElementById(\'WebBrowser\').ExecWB(7,1);">';
		  
	} else {
		 content+='<input type="button" value="打印预览"  onClick="window.print();ts_executeBillPrintTimes(); ">';
		 
	}
	 content+='<script type="text/javascript"> function ts_executeBillPrintTimes() { ';
	 content+='if(document.getElementsByName("updateBillPrintTimesSql").length>0)';
	 content+='{$.ts.DB.executeSql(document.getElementById("updateBillPrintTimesSql").value); }}</script>';
	content+='</p> '+ '<hr align="center" width="90%" size="1" noshade> '+ '</center>  ';
   $("#"+containerId).before(content);
}
$.ts.Utils.commaJoinStr=function(list){
	if(!list) return null;
	if(list.length<=0) return null;
	var ids="",names="";
	var template={};
	var bean=list[0];
	for (var key in bean){  
	    template[key]="";  
	}
	for(var i=0;i<list.length;i++){
		bean=list[i]; 
		for (var key in bean){  
			if(i==0){
				template[key]=bean[key]; 
			} else {
				template[key]=template[key]+","+bean[key];  
			}
		} 
	} 
	return template;
}
$.ts.Utils.findListRowIndex=function(row,targetRows,sp){
	if($.ts.Utils.isEmpty(targetRows)) return -1;
	for(var i=0;i<targetRows.length;i++){
		var bean=targetRows[i];
		var sameRow=true;
		if(typeof(sp)!="undefined" && sp==true){
			for (var key in row){   
				if(bean[key]!=row[key]) {
					sameRow=false;
					break;
				}
			}
		} else {
			for (var key in bean){   
				if(row[key]!=bean[key]) {
					sameRow=false;
					break;
				}
			}
		}
		if(sameRow) return i;
	}
	return -1;
};
$.ts.Utils.beanPropertyToList=function(template){
	var list={}; 
	var len=0;
	var rows=new $.ts.Array(); 
	for (var key in template){   
		 var value=template[key]; 
		 if($.ts.Utils.isEmpty(value)==false){
			 values=value.split(",");
			 if(len<values.length) len=values.length;
			 list[key]=values;
		 } else list[key]=null;
	}
	for(var i=0;i<len;i++){
		var  row={};
		for (var key in list){
			if(list[key]!=null && list[key].length>i){
				row[key]=list[key][i];
			} else {
				row[key]="";
			}
		}
		rows.push(row);
	}
	return rows;
};
$.ts.Utils.random=function(count,letters,numbers){
	if(isNaN(count)) 
		return;  
	if(count==0) 
		return ""; 
	var minAlpha="a".charCodeAt(0);
	var maxAlpha="z".charCodeAt(0);
	var alphas=["a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"];
	var digits=["0","1","2","3","4","5","6","7","8","9"];
	if(!letters&&numbers){
		var str="";
		for(var i=0;i<count;i++){
			str+=digits[Math.floor(Math.random()*10)];
		}
		return str;
	}
	if(letters&&!numbers){
		var str="";
		for(var i=0;i<count;i++){
			str+=alphas[Math.floor(Math.random()*26)];
		}
		return str;
	}
	if(letters && numbers||!letters && !numbers){
		var str="";
		for(var i=0;i<count;i++){
			var r=Math.floor(Math.random()*2);
			if(r==0){
				str+=alphas[Math.floor(Math.random()*26)];
			}else if(r==1){
				str+=digits[Math.floor(Math.random()*10)];
			}
		}
		return str;
	}
	return "";
};  
$.ts.Utils.lettersToColumnIndex=function(str){ 
	var returnValue = 0; 
      str=str.toUpperCase();
     for(var i=0;i<str.length;i++){ 
    	returnValue+=(str.charAt(i).charCodeAt()-64)*Math.pow(26, str.length-i-1);
     } 
     return returnValue;
} 
$.ts.Utils.asciiToOrdinate=function(i)   {   
	  var   s   =   "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";   
	  var   sArray=s.split(",");   
	  if   (i<1)   return   "";   
		
	  if   (parseInt((i/26)+"")==0)   return   sArray[i%26-1];   
	  else   {   
		  if   (i%26==0)   return   ($.ts.Utils.asciiToOrdinate(parseInt((i/26)+"")-1))+sArray[26-1];   
		  else   return   sArray[parseInt((i/26)+"")-1]+sArray[i%26-1];   
	  }   
}  
$.ts.Utils.cellNameToCoordinate=function(str){ 
    var  a=new Array(); 
    for(var i=0;i<str.length;i++){ 
        var c=str.charAt(i); 
        if (c>='0' && c<='9'){ 
           a[0]=str.substring(i); 
           a[1]=str.substring(0,i);
           break; 
       } 
   }  
   return a; 
 } 	
$.ts.Utils.addEvent=function(eventName,element,fn){
	  if (element.attachEvent) element.attachEvent("on"+eventName,fn);
	  else element.addEventListener(eventName,fn,false);
}
$.ts.Utils.execJavaScript=function(jsonPara,opts,callbackEvent){
	   
	  if($.ts.Utils.isEmpty(opts)==true) opts={};
	  $.ajax($.extend({
		url: tsContextPath +"/core/executeJavaScriptService/json/executeJavaScript.do?timeStamp="+new Date().getTime(),   
		data:  jsonPara,//JSON.stringify(jsonPara) ,
		datatype: "json",
		type: "POST",  
		async:true,
		success:function(result,textStatus){
			  var errorHint=false;
			  for(var i=0;i<$.ts.constant.errorPageBodyIds.length;i++){
				  if(result.indexOf($.ts.constant.errorPageBodyIds[i])>0){
					  errorHint=true;
					  break;
				  }
			  }
			if(errorHint==true){
				$.ts.EasyUI.showContentDialog(result);
				return ;
			} 
			if($.ts.Utils.isEmpty(callbackEvent)==false && $.isFunction(callbackEvent)){ 
			   callbackEvent.call(this,result);        
			}
		 }, 
		error:function(XMLHttpRequest, textStatus, errorThrown){
			var str=XmlHttpRequest.responseText; 
			 if($.ts.Utils.isEmpty(str)){
				  str=XmlHttpRequest.responseXML;
			 }
			 str=str+"<hr/>"+textStatus;
			 $.ts.EasyUI.showContentDialog(str); 
		}
	},opts)); 
}

$.ts.Format=function( ){ 
  this.className="ts.Format";
};
$.ts.Format.paramsToJson=function (serializedParams) {
      var obj={};
      function evalThem (str) {
        var attributeName = str.split("=")[0];
        var attributeValue = str.split("=")[1];
        if(!attributeValue){
            return ;
        } 
        var array = attributeName.split(".");
        for (var i = 1; i < array.length; i++) {
            var tmpArray = Array();
            tmpArray.push("obj");
            for (var j = 0; j < i; j++) {
                tmpArray.push(array[j]);
            };
            var evalString = tmpArray.join(".");
            if(!eval(evalString)){
                eval(evalString+"={};");                
            }
        }; 
        eval("obj."+attributeName+"='"+attributeValue+"';");
	  };
       var properties = serializedParams.split("&");
	   if(properties.length==1 && serializedParams.indexOf("=")<=0) return serializedParams;
	   for (var i = 0; i < properties.length; i++) {
			evalThem(properties[i]);
	    }; 
		return obj;
};
$.ts.Format.formatNumber=function(number,pattern){
	 if(isNaN(number)){
		throw "The argument must be a number" ;
	 } 
	 if(pattern==""){
		return number;
	 }
	 var strNum=new String(number);
	 var numNum=parseFloat(number);
	 var isNegative=false;
	 if(numNum<0){
		isNegative=true;
	 }
	 if(isNegative){
		strNum=strNum.substring(1,strNum.length);
		numNum=-numNum;
	 }
	 var ePos=pattern.indexOf("E");
	 var pPos=pattern.indexOf("%");
	 if(ePos!=-1 && pPos!=-1){
		throw  "Malformed exponential pattern : E and % can not be existed at the same time" ;
	 }
	 if(ePos!=-1){
		if(ePos==pattern.length-1){
			throw  "Malformed exponential pattern "+this.pattern ;
		}
		beStr=pattern.substring(0,ePos);
		aeStr=pattern.substring(ePos+1);
		var dPos=beStr.indexOf(".");
		var dPosOfNum=strNum.indexOf(".");
		if(dPos!=-1){     		
			if(dPosOfNum==-1){
				dPosOfNum=strNum.length-1;
			} 
			var strNumBuffer=new $.ts.StringBuffer(strNum);
			strNumBuffer.deleteCharAt(dPosOfNum);
			strNumBuffer.insert(dPos,".");
			var snbStr=strNumBuffer.getValue();
			var adStrLength=beStr.length-dPos;
			var snbFixed=new Number(parseFloat(snbStr)).toFixed(adStrLength-1);     		
			var aeLabel=dPosOfNum-dPos;
			if(isNegative){
				return "-"+snbFixed+"e"+(aeLabel);
			}else{
				return snbFixed+"e"+(aeLabel);
			}
		}else{
			if(dPosOfNum==-1){
				dPosOfNum=strNum.length-1;
			}
			var strNumBuffer=new $.ts.StringBuffer(strNum);
			strNumBuffer.deleteCharAt(dPosOfNum);
			strNumBuffer.insert(beStr.length,".");
			var snbStr=strNumBuffer.getValue();
			var adStrLength=beStr.length-beStr.length;
			var snbFixed=-1;
			if(adStrLength==0){
				snbFixed=new Number(parseFloat(snbStr)).toFixed();     		
			}else{
				snbFixed=new Number(parseFloat(snbStr)).toFixed(adStrLength-1);
			}
			var aeLabel=dPosOfNum-beStr.length;
			if(isNegative){
				return "-"+snbFixed+"e"+(aeLabel);
			}else{
				return snbFixed+"e"+(aeLabel);
			}
		}    	
	 }
	 if(pPos!=-1){
		if(pPos!=pattern.length-1){
			throw  "Malformed exponential pattern "+this.pattern ;
		}
		pattern=pattern.substring(0,pattern.length-1);
		numNum=parseFloat(number)*100;
		strNum=new String(numNum);
		if(isNegative){
			strNum=strNum.substring(1,strNum.length);
			numNum=-numNum;
		}
	 }    
	 var dPos=pattern.indexOf(".");
	 var dPosOfNum=strNum.indexOf(".");   	
	 var result=""; 
	 if(dPos!=-1){     		
		if(dPosOfNum==-1){
			dPosOfNum=strNum.length-1;
		}
		var adStrLength=pattern.length-dPos;
		var snbFixed=new Number(parseFloat(strNum)).toFixed(adStrLength-1);   
		if(isNegative){
			result="-"+snbFixed;
		}else{
			result=snbFixed;
		}
	 }else{
		if(dPosOfNum==-1){
			dPosOfNum=strNum.length-1;
		}
		var snbFixed=new Number(parseFloat(strNum)).toFixed();   
		if(isNegative){
			result="-"+snbFixed;
		}else{
			result=snbFixed;
		}
	 }
	 if(pPos!=-1){
		result+="%";
	 }
	 return result;
 };
$.ts.Format.getAges=function(strBirthday){ 
   
	var returnAge;
		var strBirthdayArr=strBirthday.split("-");
		var birthYear = parseInt(strBirthdayArr[0]);
		var birthMonth = parseInt(strBirthdayArr[1]);
		var birthDay = parseInt(strBirthdayArr[2]);
		
		var d = new Date();
		var nowYear = d.getFullYear();
		var nowMonth = d.getMonth() + 1;
		var nowDay = d.getDate();
		
		if(nowYear == birthYear) {
			returnAge = 0;//同年 则为0岁
		} else {
			var ageDiff = nowYear - birthYear ; //年之差 
			if(ageDiff > 0) {
				if(nowMonth == birthMonth){
					var dayDiff = nowDay - birthDay;//日之差
					if(dayDiff < 0) {returnAge = ageDiff - 1;}
					else {returnAge = ageDiff ;}
				}else{
					var monthDiff = nowMonth - birthMonth;//月之差
					if(monthDiff < 0)
					{returnAge = ageDiff - 1;}
					else
					{returnAge = ageDiff ;}
				}
			} else {
				returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天}
			}
		}
		return returnAge;//返回周岁年龄
 };
 $.ts.Format.numMoneyToChinese=function(numberValue){
	  var numberValue=new String(Math.round(numberValue*100)); // 数字金额
	  var chineseValue="";          // 转换后的汉字金额
	  var String1 = "零壹贰叁肆伍陆柒捌玖";       // 汉字数字
	  var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分";     // 对应单位
	  var len=numberValue.length;         // numberValue 的字符串长度
	  var Ch1;             // 数字的汉语读法
	  var Ch2;             // 数字位的汉字读法
	  var nZero=0;            // 用来计算连续的零值的个数
	  var String3;            // 指定位置的数值
	  if(len>15){
	    throw "超出计算范围";
	    return "";
	  }
	  if (numberValue==0){ 
	   chineseValue = "零元整";
	   return chineseValue; 
	  }
	  
	  String2 = String2.substr(String2.length-len, len);   
	  for(var i=0; i<len; i++){ 
	   String3 = parseInt(numberValue.substr(i, 1),10);  
	   //alert(String3);
	   if ( i != (len - 3) && i != (len - 7) && i != (len - 11) && i !=(len - 15) ){ 
		if ( String3 == 0 ){
		 
		 Ch1 = "";
		 Ch2 = "";
		 nZero = nZero + 1;
		 
		}else if ( String3 != 0 && nZero != 0 ){
		 
		 Ch1 = "零" + String1.substr(String3, 1);
		 Ch2 = String2.substr(i, 1);
		 nZero = 0;
		 
		}else{ 
		 Ch1 = String1.substr(String3, 1);
		 Ch2 = String2.substr(i, 1);
		 nZero = 0;
		}
	   }else{              // 该位是万亿，亿，万，元位等关键位
		if( String3 != 0 && nZero != 0 ){
		 
		 Ch1 = "零" + String1.substr(String3, 1);
		 Ch2 = String2.substr(i, 1);
		 nZero = 0;
		 
		}else if ( String3 != 0 && nZero == 0 ){
		 
		 Ch1 = String1.substr(String3, 1);
		 Ch2 = String2.substr(i, 1);
		 nZero = 0;
		 
		}else if( String3 == 0 && nZero >= 3 ){
		 
		 Ch1 = "";
		 Ch2 = "";
		 nZero = nZero + 1;
		 
		}else{
		 
		 Ch1 = "";
		 Ch2 = String2.substr(i, 1);
		 nZero = nZero + 1; 
		} 
		if( i == (len - 11) || i == (len - 3)) {  
		 Ch2 = String2.substr(i, 1);
		}
		
	   }
	   chineseValue = chineseValue + Ch1 + Ch2; 
	  } 
	  if ( String3 == 0 ){         
	   chineseValue = chineseValue + "整";
	  }
	  return chineseValue;
 };
$.ts.constant=function(){
	this.className="ts.constant";
};
var tsContextPath=$.ts.getContextPath(); 
if(tsContextPath=="/main"){
	tsContextPath="";
}
$.ts.constant.timeout=2000;
$.ts.constant.blankStr="空数据";
$.ts.constant.errorPageBodyIds=["ShowErrorPageBody","ShowErrorPageBodyH2","ShowSysErrorPageBody","ShowSysErrorPageBodyH2","ShowAlertMsgPageBody","ShowAlertMsgPageBodyH2"]; 
$.ts.constant.urlType={"combotree":tsContextPath + "/core/reportResolver/json/queryCombotree.do",
                       "chooseRow":tsContextPath + "/core/reportResolver/chooseRow.do",
					   "chooseRows":tsContextPath + "/core/reportResolver/chooseRows.do",
					   "queryList":tsContextPath + "/core/reportResolver/initPage.do",
					   "initReportJson":tsContextPath + "/core/reportResolver/json/initReportJson.do",
					   "queryReportResult":tsContextPath + "/core/reportResolver/json/queryReportResult.do"};				   

 