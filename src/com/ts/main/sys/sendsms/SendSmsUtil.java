package com.ts.main.sys.sendsms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;  

/**
 * 短信接口发送命令工具
 * @author xuzhenyao
 *
 */
public class SendSmsUtil {
	private static String encode(String str){
		try {
			return URLEncoder.encode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
			return str;
		}
	}
	public static Integer TestPost(URL url,HttpURLConnection connection,String SMSContent) throws IOException 
	{
	    connection.setRequestMethod("POST");
			connection.setDoOutput(true);
	    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");              
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "GB2312");
			out.write(SMSContent);
			out.flush();
			out.close();
			String sCurrentLine;
			String sTotalString;
			sCurrentLine = "";
			sTotalString = "";
			InputStream l_urlStream;
			l_urlStream = connection.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
			while ((sCurrentLine = l_reader.readLine()) != null) 
			{
				sTotalString += sCurrentLine + "\r\n";
			}
			String[] returns =  sTotalString.split("ErrorNum");
			StringBuffer sb = new StringBuffer(returns[1]);
			sb.deleteCharAt(0);
			sb.deleteCharAt(sb.length() - 2);
			sb.deleteCharAt(sb.length() - 1);
			Integer result = Integer.parseInt(sb.toString());
			return result;	          
	}
	public static String TestPost1(URL url,HttpURLConnection connection,String SMSContent) throws IOException 
	{
	    connection.setRequestMethod("POST");
			connection.setDoOutput(true);
	    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");              
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "GB2312");
			out.write(SMSContent);
			out.flush();
			out.close();
			String sCurrentLine;
			String sTotalString;
			sCurrentLine = "";
			sTotalString = "";
			InputStream l_urlStream;
			l_urlStream = connection.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
			while ((sCurrentLine = l_reader.readLine()) != null) 
			{
				sTotalString += sCurrentLine + "\r\n";
			}
			return sTotalString;	          
	}
	
	public static Integer GetBalance(URL url,HttpURLConnection connection,String SMSContent) throws IOException 
	{
	    connection.setRequestMethod("POST");
			connection.setDoOutput(true);
	    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");              
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "GB2312");
			out.write(SMSContent);
			out.flush();
			out.close();
			String sCurrentLine;
			String sTotalString;
			sCurrentLine = "";
			sTotalString = "";
			InputStream l_urlStream;
			l_urlStream = connection.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
			while ((sCurrentLine = l_reader.readLine()) != null) 
			{
				sTotalString += sCurrentLine + "\r\n";
			}
			String[] returns =  sTotalString.split("ErrorNum");
			StringBuffer sb = new StringBuffer(returns[1]);
			sb.deleteCharAt(0);
			sb.deleteCharAt(sb.length() - 2);
			sb.deleteCharAt(sb.length() - 1);
			Integer result = Integer.parseInt(sb.toString());
			if(result == 0){
				String[] strs =  sTotalString.split("Stock");
				StringBuffer sb1 = new StringBuffer(strs[1]);
				sb1.deleteCharAt(0);
				sb1.deleteCharAt(sb1.length() - 2);
				sb1.deleteCharAt(sb1.length() - 1);
				result = Integer.parseInt(sb1.toString());
			}else{
				result = 0;
			}
			return result;	          
	}
	public static StringBuffer SendMsg(String conten,String phone){
		conten=conten+"【富朗特】";
		 StringBuffer parameters=new StringBuffer();
    	 parameters.append(SmsConfigure.USERNAME_KEY+"="+SmsConfigure.USERNAME_VALUE+"&");
    	 parameters.append(SmsConfigure.ID_KEY+"="+SmsConfigure.ID_VALUE+"&");
    	 parameters.append(SmsConfigure.PASSWORD_KEY+"="+SmsConfigure.PASSWORD_VALUE+"&");
    	 parameters.append("SMSType=1&");
    	 parameters.append(SmsConfigure.COMTEN_KEY+"="+conten+"&");
    	 parameters.append(SmsConfigure.PHONE_KEY+"="+encode(phone)+"&SendDate=&Sendtime=");
		return parameters;
	}
	public static StringBuffer SendMsg(String conten,List<String> phones){
		 StringBuffer parameters=new StringBuffer();
	   	 parameters.append(SmsConfigure.USERNAME_KEY+"="+SmsConfigure.USERNAME_VALUE+"&");
	   	 parameters.append(SmsConfigure.ID_KEY+"="+SmsConfigure.ID_VALUE+"&");
	   	 parameters.append(SmsConfigure.PASSWORD_KEY+"="+SmsConfigure.PASSWORD_VALUE+"&");
	   	 parameters.append("SMSType=1&");
	   	 parameters.append(SmsConfigure.COMTEN_KEY+"="+conten+"&");
	   	 parameters.append(SmsConfigure.PHONE_KEY+"=");
	   	 if(phones != null && phones.size() > 0){
	   		 for(String str:phones){
	 	   	 	parameters.append(str+";");
	 	   	 }
	 	   	 parameters.deleteCharAt(parameters.length()-1);
	   	 }
	   	 parameters.append("&SendDate=&Sendtime=");
			return parameters;
		}
}
