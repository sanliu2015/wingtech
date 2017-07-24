package com.ts.main.sys.sendsms;
 
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ts.core.common.bean.BaseBean;
 

/**
 * 接收回复的短信日志
 * @author tgp
 *
 */
public class ReceiveSmsLog extends BaseBean {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column 
    private Integer id; //表主键id
	private Integer custId;//客户id
	private Integer employeeId;//员工id
	private Integer contactId;//联系人id  
	private String mobileNo;//手机号码
	private String msg;//短信内容
	private String sendDate;//发送日期
	private String sendTime;//发送时间
	private String receiverType;//接收者类型,0-客户，1-本公司员工,2-其它(直接录入手机号码的）
	private String sender;//发送者姓名 
	private String receiveDate;//接收日期
	private String receiveTime;//接收时间
	private String msource;//企业代码

	
	public String getMsource() {
		return msource;
	}


	public void setMsource(String msource) {
		this.msource = msource;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	 


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getCustId() {
		return custId;
	}


	public void setCustId(Integer custId) {
		this.custId = custId;
	}


	public Integer getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}


	public Integer getContactId() {
		return contactId;
	}


	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}


	public String getMobileNo() {
		return mobileNo;
	}


	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	public String getSendDate() {
		return sendDate;
	}


	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}


	public String getSendTime() {
		return sendTime;
	}


	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}


	public String getReceiverType() {
		return receiverType;
	}


	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}
 
	public String getReceiveDate() {
		return receiveDate;
	}


	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}


	public String getReceiveTime() {
		return receiveTime;
	}


	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}


	public boolean equals(Object obj){
		if (obj instanceof ReceiveSmsLog){
			ReceiveSmsLog object = (ReceiveSmsLog) obj;
			return object.getId()==this.id;
		}
		return false;
	}
}
