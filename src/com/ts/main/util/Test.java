package com.ts.main.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Test {

	private static String SENDURL = "http://120.193.39.85/webservice/services/sendmsg?wsdl";			// url
	private static String CORPORATION = "wz999999";
	private static String MESSAGE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><infos><info>"
								  + "<msg_id><![CDATA[-1]]></msg_id>"
								  + "<password><![CDATA[{0}]]></password>"
								  + "<src_tele_num><![CDATA[106573999999]]></src_tele_num>"
								  + "<dest_tele_num><![CDATA[{1}]]></dest_tele_num>"
								  + "<msg><![CDATA[{2}]]></msg></info></infos>";
	public static String sendSms(String phone, String content) throws Exception {
		String message = "";
		String lastFourNum = phone.substring(phone.length()-4,phone.length());
		Integer password = Integer.valueOf(lastFourNum)*3 + 579;
		message = MessageFormat.format(MESSAGE,new Object[]{""+password,phone,content});
		JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
		Client client = clientFactory.createClient(SENDURL);
		Object[] result = client.invoke("sendmsg",new Object[]{CORPORATION, message});
		return result[0].toString();
	}
	
	public static void main(String[] args) {
//		try {
//			String retVal = sendSms("13761336189","短信测试");
//			System.out.println(retVal);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		try {
//			int a = 3/0;
//			System.out.println("111");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("222");
		Map<String, Object> a = new HashMap<String, Object>();
		Map<String, Object> b = new HashMap<String, Object>();
		a.put("colType", "string");
		b.put("model", a);
		System.out.println(b.get("model"));
		System.out.println((1 == 1 ? "是" : "否") + "123");
	}
}
