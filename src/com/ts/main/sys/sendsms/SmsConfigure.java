package com.ts.main.sys.sendsms;

import java.io.Serializable;
/**
 * 短信接口配置
 * @author tgp
 *
 */
public class SmsConfigure implements Serializable{
	
	public static String actionKey="action";//事件参数名 
	public static String userNameKey="username";//用户名参数名称
	public static String userNameValue="shtangqi";//用户名参数值
	public static String pwdKey="pwd";//密码参数名称
	public static String pwdValue="shtqmm";//密码参数值
	public static String corpCodeKey="msource";//企业代码参数名
	public static String corpCodeValue="10690091398";//企业代码参数值
	public static String pointsInterfaceUrl="http://211.147.222.37:8080/getsmsnum/";//积分接口url
	public static String pointsAction="jifen";//积分事件参数值
	
	public static String sendInterfaceUrl="http://211.147.222.37:8080/sendsms/";
	public static String sendAction="sendmsg";//单条发送事件参数值
	public static String groupSendAction="psendmsg";//群发事件参数值
	public static String mobileKey="mobiles";//手机号码参数名
	public static String msgKey="msg";//发送短信内容参数名
	
	public static String receiverUrl="http://211.147.222.37/sendsms/mo.asp";//回复网关url
	public static String SENDWAY_WORKBRENCH="0";//短信平台发送
	public static String SENDWAY_EXCHANGE="1";//通过信息交流菜单发送
	public static String SENDWAY_CUSTOMERCARE="2";//通过客户关怀发送
	
	/**
	 * 登陆URL
	 */
	public static String LOGIN_URL = "http://www.lanz.net.cn/LANZGateway/Login.asp"; //登陆URL
	/**
	 * 发送短信URL
	 */
	public static String SENDMSG_URL = "http://www.lanz.net.cn/LANZGateway/DirectSendSMSs.asp"; //发送短信URL
	/**
	 * 短信余额URL
	 */
	public static String FINDBALANCE_URL = "http://www.lanz.net.cn/LANZGateway/GetSMSStock.asp"; //短信余额URL
	/**
	 * 注销用户URL
	 */
	public static String LOGOUT_URL = "http://www.lanz.net.cn/LANZGateway/Logoff.asp"; //注销用户URL
	
	public static String USERNAME_KEY = "UserID";  //连接名称
	public static String USERNAME_VALUE = "869589";  //连接名称值
	public static String ID_KEY = "Account";  //账号
	public static String ID_VALUE = "test123";  //账号值
	public static String PASSWORD_KEY = "Password";  //密码
	public static String PASSWORD_VALUE = "7C4A8D09CA3762AF61E59520943DC26494F8941B";  //密码值 需加密
	public static String ACTIVEID_KEY = "ActiveID";  //账号
	
	public static String PHONE_KEY = "Phones";  //号码设置名
	public static String COMTEN_KEY = "Content";  //内容设置名
}
