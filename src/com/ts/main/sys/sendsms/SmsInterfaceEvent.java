package com.ts.main.sys.sendsms;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException; 

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ts.core.common.service.IBaseServiceManger;
import com.ts.core.util.DateTimeUtil;
import com.ts.core.util.StringUtils;
 
public class SmsInterfaceEvent {
	
	private static String encode(String str){
		try {
			return URLEncoder.encode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}
	/**
	 * 单条发送短信
	 * @param sendSmsReceiver 要发送的人员，注意要注入sendSmsConten对象，主要用来获取发送内容
	 * @return
	 */
	public static boolean sendSmsContenEvent(SendSmsContent content){
		 URL   url   =   null;   
         HttpURLConnection   httpurlconnection   =   null;   
         try   
           {   
        	 url = new URL(SmsConfigure.SENDMSG_URL);
        	 HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        	 StringBuffer parameters = SendSmsUtil.SendMsg(content.getSendContent(), content.getAdditionalMobileNo());
        	 Integer result = SendSmsUtil.TestPost(url, connection, parameters.toString());
        	 if(result != 0){
        		 content.setStatus("0");
            	 return false;
        	 }
        	 content.setStatus("1");
        	 content.setResponseCode(result.toString());
//         	 parameters.append(SmsConfigure.sendInterfaceUrl+"?");
//         	 parameters.append(SmsConfigure.userNameKey+"="+encode(SmsConfigure.userNameValue));
//         	 String timesamp=new Long(Math.round(new Date().getTime()/1000)).toString();
//         	 String pwd=SmsConfigure.userNameValue+SmsConfigure.pwdValue+timesamp;
//         	
//         	String encodePassword=MD5.MD5Encode(pwd);
//         	 parameters.append("&"+SmsConfigure.pwdKey+"="+encode(encodePassword));
//         	parameters.append("&dt="+encode(timesamp));
//         	// parameters.append("&"+SmsConfigure.actionKey+"="+SmsConfigure.sendAction);
//         	 parameters.append("&"+SmsConfigure.mobileKey+"="+encode(content.getAdditionalMobileNo()));    
//         	parameters.append("&"+SmsConfigure.msgKey+"="+encode(content.getSendContent())); 
//        	 url   =   new   URL(parameters.toString());
//             //以post方式请求   
//             httpurlconnection   =   (HttpURLConnection)   url.openConnection();   
//           
//             httpurlconnection.connect();
//             //获取响应代码     
//              int   code   =   httpurlconnection.getResponseCode();   
//             
//             if(code==200)
//            	 content.setStatus("1");
//             else {
//            	 content.setStatus("0");
//            	 return false;
//             } 
//             //获取页面内容   
//             java.io.InputStream   in=   httpurlconnection.getInputStream();   
//             java.io.BufferedReader   breader=new   BufferedReader(new   InputStreamReader(in,"gb2312"));
//             StringBuffer sb=new StringBuffer(); 
//			 String   str=breader.readLine();   
//			 while(str  !=   null){   
//				 sb.append(str);  
//				 str=breader.readLine();   
//			 }   
//			 if(!"0".equals(sb.toString()))
//				 content.setStatus("0");
//			 content.setResponseCode(result.toString());
         }   
         catch(Exception   e)  {   
             e.printStackTrace();   
             return false;
         }   finally  {   
             if(httpurlconnection!=null)   
                 httpurlconnection.disconnect();   
         }
		return true;
	}
	/**
	 * 单条发送短信
	 * @param sendSmsReceiver 要发送的人员，注意要注入sendSmsConten对象，主要用来获取发送内容
	 * @return
	 */
	public static boolean sendSmsEvent(SendSmsReceiver sendSmsReceiver){
		 URL   url   =   null;   
         HttpURLConnection   httpurlconnection   =   null;   
         try   
           {  
        	 url = new URL(SmsConfigure.SENDMSG_URL);
        	 HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        	 StringBuffer parameters = SendSmsUtil.SendMsg(sendSmsReceiver.getSendSmsContent().getSendContent(), sendSmsReceiver.getMobileNo());
        	 Integer result = SendSmsUtil.TestPost(url, connection, parameters.toString());
        	 if(result != 0){
        		 sendSmsReceiver.setReceiveStatus("0");
            	 return false;
        	 }
        	 sendSmsReceiver.setReceiveStatus("1");
//        	 StringBuffer parameters=new StringBuffer();
//         	 parameters.append(SmsConfigure.sendInterfaceUrl+"?");
//         	 parameters.append(SmsConfigure.userNameKey+"="+encode(SmsConfigure.userNameValue));
//         	 String timesamp=new Long(Math.round(new Date().getTime()/1000)).toString();
//         	 String pwd=SmsConfigure.userNameValue+SmsConfigure.pwdValue+timesamp;
//         	
//         	String encodePassword=MD5.MD5Encode(pwd);
//         	 parameters.append("&"+SmsConfigure.pwdKey+"="+encode(encodePassword));
//         	parameters.append("&dt="+encode(timesamp));
//         	// parameters.append("&"+SmsConfigure.actionKey+"="+SmsConfigure.sendAction);
//         	 parameters.append("&"+SmsConfigure.mobileKey+"="+encode(sendSmsReceiver.getMobileNo()));    
//         	parameters.append("&"+SmsConfigure.msgKey+"="+encode(sendSmsReceiver.getSendSmsContent().getSendContent()));
//         	//String sendContext=   new   String(parameters.toString().getBytes(), "UTF-8"); 
//         	//String sendContext=URLEncoder.encode(parameters.toString(),"UTF-8");
//        	 url   =   new   URL(parameters.toString());
//             //以post方式请求   
//             httpurlconnection   =   (HttpURLConnection)   url.openConnection();   
//            /* httpurlconnection.setDoOutput(true);   
//             httpurlconnection.setRequestMethod("GET");    
//             //String sendContext=SmsConfigure.msgKey+"="+sendSmsReceiver.getSendSmsContent().getSendContent();
//            // sendContext=   new   String(sendContext.getBytes(), "UTF-8"); 
//             //httpurlconnection.getOutputStream().write(sendContext.getBytes());
//             httpurlconnection.getOutputStream().flush();   
//             httpurlconnection.getOutputStream().close();   */
//             httpurlconnection.connect();
//             //获取响应代码     
//              int   code   =   httpurlconnection.getResponseCode();   
//             
//             if(code==200)
//            	 sendSmsReceiver.setReceiveStatus("1");
//             else {
//            	 sendSmsReceiver.setReceiveStatus("0");
//            	 return false;
//             } 
//             //获取页面内容   
//             java.io.InputStream   in=   httpurlconnection.getInputStream();   
//             java.io.BufferedReader   breader=new   BufferedReader(new   InputStreamReader(in,"gb2312"));
//             StringBuffer sb=new StringBuffer(); 
//			 String   str=breader.readLine();   
//			 while(str  !=   null){   
//				 sb.append(str);  
//				 str=breader.readLine();   
//			 }   
//			 if(!"0".equals(sb.toString()))
//				 sendSmsReceiver.setReceiveStatus("0");
//			 sendSmsReceiver.setResponseCode(sb.toString());
         }   
         catch(Exception   e)  {   
             e.printStackTrace();   
             return false;
         }   finally  {   
             if(httpurlconnection!=null)   
                 httpurlconnection.disconnect();   
         }
		return true;
	}
	public static boolean tenRecordSendSmsEvent(List sendSmsReceivers,IBaseServiceManger baseStaticIntf){
		 URL   url   =   null;   
       HttpURLConnection   httpurlconnection   =   null;   
       try   
         {   
    	   url = new URL(SmsConfigure.SENDMSG_URL);
      	 HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      	 SendSmsReceiver sendSmsReceiver=(SendSmsReceiver)sendSmsReceivers.get(sendSmsReceivers.size()-1);
      	List<String> list = new ArrayList<String>();
      	 for(int i=0;i<sendSmsReceivers.size()-1;i++){
	  		 SendSmsReceiver s=(SendSmsReceiver)sendSmsReceivers.get(i);
	  		 s.setReceiveStatus("0");
	  		list.add(sendSmsReceiver.getMobileNo());
      	}
      	 StringBuffer parameters = SendSmsUtil.SendMsg(sendSmsReceiver.getSendSmsContent().getSendContent(), list);
      	 Integer result = SendSmsUtil.TestPost(url, connection, parameters.toString());
      	 if(result != 0){
      		 sendSmsReceiver.setReceiveStatus("0");
          	 return false;
      	 }
//      	StringBuffer parameters=new StringBuffer();
//       	 parameters.append(SmsConfigure.sendInterfaceUrl+"?");
//       	 parameters.append(SmsConfigure.userNameKey+"="+SmsConfigure.userNameValue);
//       	 parameters.append("&"+SmsConfigure.pwdKey+"="+SmsConfigure.pwdValue);
//       	 parameters.append("&"+SmsConfigure.actionKey+"="+SmsConfigure.sendAction);
//       	 parameters.append("&"+SmsConfigure.mobileKey+"=");
//       	 for(int i=0;i<sendSmsReceivers.size()-1;i++){
//       		 SendSmsReceiver sendSmsReceiver=(SendSmsReceiver)sendSmsReceivers.get(i);
//       		 sendSmsReceiver.setReceiveStatus("0");
//       		 parameters.append(sendSmsReceiver.getMobileNo()+","); 
//       	 }
//       	 SendSmsReceiver sendSmsReceiver=(SendSmsReceiver)sendSmsReceivers.get(sendSmsReceivers.size()-1);
//       	 parameters.append(sendSmsReceiver.getMobileNo());
//       	 sendSmsReceiver.setReceiveStatus("0");       	 
//       	 url   =   new   URL(parameters.toString());
//           //以post方式请求   
//           httpurlconnection   =   (HttpURLConnection)   url.openConnection();   
//           httpurlconnection.setDoOutput(true);   
//           httpurlconnection.setRequestMethod("POST");    
//           String sendContext=SmsConfigure.msgKey+"="+sendSmsReceiver.getSendSmsContent().getSendContent();
//           httpurlconnection.getOutputStream().write(sendContext.getBytes());
//           httpurlconnection.getOutputStream().flush();   
//           httpurlconnection.getOutputStream().close();   
//             
//           //获取响应代码     
//           int   code   =   httpurlconnection.getResponseCode();   
//           
//           if(code==200)
//           	sendSmsReceiver.setReceiveStatus("1");
//           else {
//          	 	sendSmsReceiver.setReceiveStatus("0");
//          	 return false;
//           }
//           //获取页面内容   
//           java.io.InputStream   in=   httpurlconnection.getInputStream();   
//           java.io.BufferedReader   breader=new   BufferedReader(new   InputStreamReader(in,"gb2312"));
//           StringBuffer sb=new StringBuffer(); 
//			 String   str=breader.readLine();   
//			 while(str  !=   null){   
//				 sb.append(str);  
//				 str=breader.readLine();   
//			 }    
			 for(int i=0;i<sendSmsReceivers.size();i++){
	       		 SendSmsReceiver temp=(SendSmsReceiver)sendSmsReceivers.get(i);
	       		 if(result == 0)
	       			 temp.setReceiveStatus("1");
	       		 temp.setResponseCode(result.toString());
	       		 baseStaticIntf.getDb().updateObject(temp,null);
			 }  
       }   
       catch(Exception   e)  {   
           e.printStackTrace();   
           return false;
       }   finally  {   
           if(httpurlconnection!=null)   
               httpurlconnection.disconnect();   
       }
		return true;
	}
	
	public static boolean groupSendSmsEvent(List sendSmsReceivers,IBaseServiceManger baseStaticIntf){
		 URL   url   =   null;   
        HttpURLConnection   httpurlconnection   =   null;   
        try   
          {   
        	  url = new URL(SmsConfigure.SENDMSG_URL);
           	 HttpURLConnection connection = (HttpURLConnection)url.openConnection();
           	 SendSmsReceiver sendSmsReceiver=(SendSmsReceiver)sendSmsReceivers.get(sendSmsReceivers.size()-1);
           	List<String> list = new ArrayList<String>();
           	 for(int i=0;i<sendSmsReceivers.size()-1;i++){
     	  		 SendSmsReceiver s=(SendSmsReceiver)sendSmsReceivers.get(i);
     	  		 s.setReceiveStatus("0");
     	  		list.add(sendSmsReceiver.getMobileNo());
           	}
           	 StringBuffer parameters = SendSmsUtil.SendMsg(sendSmsReceiver.getSendSmsContent().getSendContent(), list);
           	 Integer result = SendSmsUtil.TestPost(url, connection, parameters.toString());
           	 if(result != 0){
           		 sendSmsReceiver.setReceiveStatus("0");
               	 return false;
           	 }
        	
//       		StringBuffer parameters=new StringBuffer();
//        	 parameters.append(SmsConfigure.sendInterfaceUrl+"?");
//        	 parameters.append(SmsConfigure.userNameKey+"="+SmsConfigure.userNameValue);
//        	 parameters.append("&"+SmsConfigure.pwdKey+"="+SmsConfigure.pwdValue);
//        	 parameters.append("&"+SmsConfigure.actionKey+"="+SmsConfigure.groupSendAction);
//        	 parameters.append("&"+SmsConfigure.mobileKey+"=");
//        	 for(int i=0;i<sendSmsReceivers.size()-1;i++){
//        		 SendSmsReceiver sendSmsReceiver=(SendSmsReceiver)sendSmsReceivers.get(i);
//        		 sendSmsReceiver.setReceiveStatus("0");
//        		 parameters.append(sendSmsReceiver.getMobileNo()+","); 
//        	 }
//        	 SendSmsReceiver sendSmsReceiver=(SendSmsReceiver)sendSmsReceivers.get(sendSmsReceivers.size()-1);
//        	 parameters.append(sendSmsReceiver.getMobileNo());
//        	 sendSmsReceiver.setReceiveStatus("0");       	 
//        	 url   =   new   URL(parameters.toString());
//            //以post方式请求   
//            httpurlconnection   =   (HttpURLConnection)   url.openConnection();   
//            httpurlconnection.setDoOutput(true);   
//            httpurlconnection.setRequestMethod("POST");    
//            String sendContext=SmsConfigure.msgKey+"="+sendSmsReceiver.getSendSmsContent().getSendContent();
//            httpurlconnection.getOutputStream().write(sendContext.getBytes());
//            httpurlconnection.getOutputStream().flush();   
//            httpurlconnection.getOutputStream().close();   
//              
//            //获取响应代码     
//            int   code   =   httpurlconnection.getResponseCode();   
//            
//            if(code==200)
//            	sendSmsReceiver.setReceiveStatus("1");
//            else {
//           	 	sendSmsReceiver.setReceiveStatus("0");
//           	 return false;
//            }
//            //获取页面内容   
//            java.io.InputStream   in=   httpurlconnection.getInputStream();   
//            java.io.BufferedReader   breader=new   BufferedReader(new   InputStreamReader(in,"gb2312"));
//            StringBuffer sb=new StringBuffer(); 
//			 String   str=breader.readLine();   
//			 while(str  !=   null){   
//				 sb.append(str);  
//				 str=breader.readLine();   
//			 }    
			 for(int i=0;i<sendSmsReceivers.size();i++){
        		 SendSmsReceiver temp=(SendSmsReceiver)sendSmsReceivers.get(i);
        		 if(result == 0)
        			 temp.setReceiveStatus("1");
        		 temp.setResponseCode(result.toString());
        		 baseStaticIntf.getDb().updateObject(temp,null);
			 }  
        }   
        catch(Exception   e)  {   
            e.printStackTrace();   
            return false;
        }   finally  {   
            if(httpurlconnection!=null)   
                httpurlconnection.disconnect();   
        }
		return true;
	}
	/**
	 * 调用余额（积分)接口
	 * @return
	 */
	public static long getSmsPoints(){
		URL   url   =   null;   
        HttpURLConnection   httpurlconnection   =   null;   
        try   
          { 
        	//登陆
        	url = new URL(SmsConfigure.LOGIN_URL);
		    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		    StringBuffer parameters=new StringBuffer();
		    parameters.append(SmsConfigure.USERNAME_KEY+"="+SmsConfigure.USERNAME_VALUE+"&");
	    	parameters.append(SmsConfigure.ID_KEY+"="+SmsConfigure.ID_VALUE+"&");
	    	parameters.append(SmsConfigure.PASSWORD_KEY+"="+SmsConfigure.PASSWORD_VALUE);
		    String sTotalString = SendSmsUtil.TestPost1(url,connection,parameters.toString());
		    String session_value = connection.getHeaderField("Set-Cookie" );
	        String[] sessionId = session_value.split(";");
	        String Activeid = sTotalString.substring(82,98);
	        //查询余额
	        URL url3 = new URL(SmsConfigure.FINDBALANCE_URL);      // 查询余额
		    HttpURLConnection connection3 = (HttpURLConnection)url3.openConnection();
		    connection3.setRequestProperty( "Cookie", sessionId[0]);                       //再次连接的时候设置 session相同
		    Integer msgNumber =  SendSmsUtil.GetBalance(url3,connection3,"ActiveID="+Activeid );
		     //退出
		    URL url4 = new URL("http://www.lanz.net.cn/LANZGateway/Logoff.asp");           // 注销
		    HttpURLConnection connection4 = (HttpURLConnection)url4.openConnection();
		    connection4.setRequestProperty( "Cookie", sessionId[0]);                       //再次连接的时候设置 session相同
		    SendSmsUtil.TestPost1(url4,connection4,"ActiveID="+Activeid );
//        	StringBuffer parameters=new StringBuffer();
//        	parameters.append(SmsConfigure.pointsInterfaceUrl+"?");
//        	parameters.append(SmsConfigure.userNameKey+"="+encode(SmsConfigure.userNameValue));
//        	String timesamp=new Long(Math.round(new Date().getTime()/1000)).toString();
//        	 String pwd=SmsConfigure.userNameValue+SmsConfigure.pwdValue+timesamp;
//        	
//        	String encodePassword=MD5.MD5Encode(pwd);
//        	
//        	parameters.append("&"+SmsConfigure.pwdKey+"="+encode(encodePassword));
//        	parameters.append("&dt="+encode(timesamp));             
//        	url   =   new   URL(parameters.toString());
//            //以post方式请求   
//            httpurlconnection   =   (HttpURLConnection)   url.openConnection();   
//            httpurlconnection.connect();
//             
//            //获取响应代码     
//            int   code   =   httpurlconnection.getResponseCode(); 
//            if(code!=200) 
//            	return -1;   
//            //获取页面内容   
//            java.io.InputStream   in=   httpurlconnection.getInputStream();   
//            java.io.BufferedReader   breader=new   BufferedReader(new   InputStreamReader(in,"gb2312"));
//            StringBuffer sb=new StringBuffer(); 
//			 String   str=breader.readLine();   
//			 while(str  !=   null){   
//				 sb.append(str);  
//				 str=breader.readLine();   
//			 }   
			 if(msgNumber > 0)
				 return new Long(msgNumber)*10;
			 else
				 return -1;
        }   
        catch(Exception   e)  {   
            e.printStackTrace();   
            return -1;
        }   finally  {   
            if(httpurlconnection!=null)   
                httpurlconnection.disconnect();   
        } 
	}
	/**
	 * 调用接收短信接口
	 * @return
	 */
	public static List  receiveSms(){
		URL   url   =   null;   
        HttpURLConnection   httpurlconnection   =   null;   
        try   
          {   
        	StringBuffer parameters=new StringBuffer();
        	parameters.append(SmsConfigure.receiverUrl+"?");
        	parameters.append(SmsConfigure.userNameKey+"="+SmsConfigure.userNameValue);
        	parameters.append("&"+SmsConfigure.corpCodeKey+"="+SmsConfigure.corpCodeValue);             
        	parameters.append("&"+SmsConfigure.pwdKey+"="+SmsConfigure.pwdValue); 
        	url   =   new   URL(parameters.toString());
            //以post方式请求   
            httpurlconnection   =   (HttpURLConnection)   url.openConnection();   
            httpurlconnection.setDoOutput(true);   
            httpurlconnection.setRequestMethod("POST");     
            httpurlconnection.getOutputStream().flush();   
            httpurlconnection.getOutputStream().close();    
            //获取响应代码     
            int   code   =   httpurlconnection.getResponseCode(); 
            if(code!=200) 
            	return null;   
            //获取页面内容   
            java.io.InputStream   in=   httpurlconnection.getInputStream();   
            java.io.BufferedReader   breader=new   BufferedReader(new   InputStreamReader(in,"gb2312"));
            StringBuffer sb=new StringBuffer(); 
			 String   str=breader.readLine();   
			 while(str  !=   null){   
				 sb.append(str);  
				 str=breader.readLine();   
			 }   
			 if(!StringUtils.isNoValue(sb.toString())){ 
				 return parseSmsContent(sb.toString());
			 } else
				 return null;
        }   
        catch(Exception   e)  {   
            e.printStackTrace();   
            return null;
        }   finally  {   
            if(httpurlconnection!=null)   
                httpurlconnection.disconnect();   
        }  
	}
	public static List parseSmsContent(String xml){
		Document xmlDoc = null;
        try {
            xmlDoc =DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xml.getBytes()));
        }
        catch(ParserConfigurationException e) {
            System.out.println("ParserConfigurationException: " + e);
        }
        catch(SAXException e) {
            System.out.println("SAXException: " + e);
            return null;
        } catch (IOException e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
			 return null;
		}
        List receiveLogList=new ArrayList<ReceiveSmsLog>();
        NodeList smsList = xmlDoc.getElementsByTagName("Sms"); 
        for(int i=0;i<smsList.getLength();i++){
        	Node sms=smsList.item(i);
        	NodeList moList=sms.getChildNodes();
        	for(int k=0;k<moList.getLength();k++){
        		Node mo=moList.item(k);
        		ReceiveSmsLog receiveSmsLog=new ReceiveSmsLog(); 
        		NodeList fieldList=mo.getChildNodes();
        		for(int n=0;n<fieldList.getLength();n++){
        			Node field=fieldList.item(n); 
        			if("mobile".equals(field.getNodeName())){
        				receiveSmsLog.setMobileNo(field.getTextContent());        				
        			} else if("msg".equals(field.getNodeName())){
        				receiveSmsLog.setMsg(field.getTextContent());        				
        			} else if("msource".equals(field.getNodeName())){
        				receiveSmsLog.setMsource(field.getTextContent());        				
        			} else  if("time".equals(field.getNodeName())){
        				String dateTime=field.getTextContent();
        				String[] dateTimes=dateTime.split(" ");        				
        				receiveSmsLog.setSendDate(dateTimes[0]);
        				Date date=DateTimeUtil.parseStringToDate(dateTimes[0]);
        				receiveSmsLog.setSendDate(DateTimeUtil.formatDate(date));
        				receiveSmsLog.setSendTime(dateTimes[1]);
        			} 
        		}
        		receiveLogList.add(receiveSmsLog);
        	} 
        }
		return receiveLogList;
	}
}
