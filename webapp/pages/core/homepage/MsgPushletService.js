 
function MsgPushletScript( ){  
	this.msgTypeName= {"login":""}	 
}
   
MsgPushletScript.prototype={ 

   playRemindSound:function(){  
		var url=tsContextPath+"/resource/image/home/overweight.wav"; 
		document.getElementById("msgHintSound").src =url; 
	},
    login:function(pEvent,msg){
		try{
		$("#mainOnLineCounters").text(msg);    
		} catch(e){}
	},
    bulletin:function(pEvent,msg){
		var msgJson=$.parseJSON(msg);
		mainScript.opts.bulletins=mainScript.opts.bulletins+msgJson.records; 
		mainScript.displayRemindMsgRecords();
		 
		var t=window.frames[0].document.getElementById("ltBulletinTable"); 
		window.frames[0].transactionHintUtil.insertBulletinTableRow(msgJson,t); 
		if(msgJson.records!=-1){ 
			if( msgJson.remindMode=="message"){ 
				$.messager.show({
					title:'消息提示',
					msg:msgJson.name+"【新闻公告】",
					timeout:$.ts.constant.timeout,
					style:{
						left:'',
						right:0,
						top:document.body.scrollTop+document.documentElement.scrollTop,
						bottom:''
					}
				  }); 
				 // this.playHintMusic();
			}
		}  
	},
	task:function(pEvent,msg){ 
		var msgJson=$.parseJSON(msg);
		mainScript.opts.tasks=mainScript.opts.tasks+msgJson.records; 
		mainScript.displayRemindMsgRecords();
		var t=document.frames[0].document.getElementById("ltNotifyTable");
		
		if(msgJson.records!=-1){ 
		    document.frames[0].transactionHintUtil.insertMyTaskTableRow(msgJson,t); 
			
			if( msgJson.remindMode=="message"){ 
				$.messager.show({
					title:'消息提示',
					msg:msgJson.title+"【待办事件】",
					timeout:$.ts.constant.timeout,
					style:{
						left:'',
						right:0,
						top:document.body.scrollTop+document.documentElement.scrollTop,
						bottom:''
					}
				  }); 
				  this.playRemindSound();
			}
		}  
	}
}
 
 