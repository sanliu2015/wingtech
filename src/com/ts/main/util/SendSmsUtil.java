package com.ts.main.util;

import java.text.MessageFormat;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 调用发送短信接口
 * @author plq
 *
 */
public class SendSmsUtil {

	private static String SENDURL = "http://111.1.31.202/webservice/services/sendmsg?wsdl";			// url
	private static String CORPORATION = "rww_zjrhbjx255381";
	private static String MESSAGE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><infos><info>"
								  + "<msg_id><![CDATA[-1]]></msg_id>"
								  + "<password><![CDATA[{0}]]></password>"
								  + "<src_tele_num><![CDATA[106575255381]]></src_tele_num>"
								  + "<dest_tele_num><![CDATA[{1}]]></dest_tele_num>"
								  + "<msg><![CDATA[{2}]]></msg></info></infos>";
	public static String sendSms(String phone, String content) throws Exception {
		String message = "";
		String lastFourNum = phone.substring(phone.length()-4,phone.length());
		Integer password = Integer.valueOf(lastFourNum)*3 + 1314;
		message = MessageFormat.format(MESSAGE,new Object[]{""+password,phone,content});
		JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
		Client client = clientFactory.createClient(SENDURL);
		Object[] result = client.invoke("sendmsg",new Object[]{CORPORATION, message});
		return result[0].toString();
	}
	
	public static void main(String[] args) {
		try {
			String retVal = sendSms("13761336189","短信测试");
			System.out.println(retVal);
			
//			String sb = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><infos><info><msg_id><![CDATA[13761336189]]></msg_id><state><![CDATA[0]]></state></info></infos>";
//			Document document;
//    		try {
//    			document = DocumentHelper.parseText(sb.toString());
//    			Element root = document.getRootElement();
//    		    Element node = root.element("info").element("state");  
//    		    String account = node.getText();
//    		    System.out.println(account);
//    		} catch (Exception e) {
//    			e.printStackTrace();
//    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
