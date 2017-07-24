package com.ts.main.sys.sendsms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseAutoIdBean;
import com.ts.core.common.bean.BaseBean;

/**
 * 发送短信内容
 * 
 * @author tgp
 *
 */
@BeanProperty(description = "发送短信内容")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "SYS_SendSmsContent")
public class SendSmsContent extends BaseAutoIdBean {
	@BeanProperty(description = "发送短信内容")
	@Column(length = 1000)
	private String sendContent;// 发送内容
	@BeanProperty(description = "过滤发短信接收者的sql")
	@Column(length = 1000)
	private String receiverSql;// 过滤发短信接收者的sql
	@BeanProperty(description = "额外的接收手机号")
	@Column(length = 1000)
	private String additionalMobileNo;// 额外的接收手机号
	@BeanProperty(description = "发送短信用途类型")
	@Column(length = 20)
	private String sendUseType;
	@BeanProperty(description = "发送短信定时日期")
	@Column(length = 20)
	private String timingDate;
	@BeanProperty(description = "发送短信定时时间")
	@Column(length = 20)
	private String timingTime;
	@BeanProperty(description = "0-立即，1-定时")
	@Column(length = 10)
	private String invokeTimeType = "0";
	@BeanProperty(description = "定时发送完成状态")
	@Column(length = 10)
	private String timeIngSendStatus;// 定时发送完成状态
	@BeanProperty(description = "发送短信途径")
	@Column(length = 10)
	private String sendWay;// 发送短信途径
							// ，空或0-通过短信平台菜单正常发送，1-通过信息交流发送，2-取样计划,3-通过特批发送的
	@BeanProperty(description = "客户id")
	@Column
	private Integer custId;
	@BeanProperty(description = "客户id")
	@Column(length = 100)
	private String custName;
	@BeanProperty(description = "客户id")
	@Column(length = 30)
	private String custNumber;
	@BeanProperty(description = "发送返回状态")
	@Column(length = 10)
	private String responseCode;
	private String rewardKind;// 奖励类型

	public String getCustNumber() {
		return custNumber;
	}

	public void setCustNumber(String custNumber) {
		this.custNumber = custNumber;
	}

	public String getRewardKind() {
		return rewardKind;
	}

	public void setRewardKind(String rewardKind) {
		this.rewardKind = rewardKind;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getSendWay() {
		return sendWay;
	}

	public void setSendWay(String sendWay) {
		this.sendWay = sendWay;
	}

	public String getTimeIngSendStatus() {
		return timeIngSendStatus;
	}

	public void setTimeIngSendStatus(String timeIngSendStatus) {
		this.timeIngSendStatus = timeIngSendStatus;
	}

	public String getSendContent() {
		return sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public String getReceiverSql() {
		return receiverSql;
	}

	public void setReceiverSql(String receiverSql) {
		this.receiverSql = receiverSql;
	}

	public String getAdditionalMobileNo() {
		return additionalMobileNo;
	}

	public void setAdditionalMobileNo(String additionalMobileNo) {
		this.additionalMobileNo = additionalMobileNo;
	}

	public String getSendUseType() {
		return sendUseType;
	}

	public void setSendUseType(String sendUseType) {
		this.sendUseType = sendUseType;
	}

	public String getTimingDate() {
		return timingDate;
	}

	public void setTimingDate(String timingDate) {
		this.timingDate = timingDate;
	}

	public String getTimingTime() {
		return timingTime;
	}

	public void setTimingTime(String timingTime) {
		this.timingTime = timingTime;
	}

	public String getInvokeTimeType() {
		return invokeTimeType;
	}

	public void setInvokeTimeType(String invokeTimeType) {
		this.invokeTimeType = invokeTimeType;
	}

}
