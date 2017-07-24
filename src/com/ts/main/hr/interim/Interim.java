package com.ts.main.hr.interim;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseBean;
@BeanProperty(description="劳务派遣")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "HR_Interim")
public class Interim extends BaseBean{
	
	@BeanProperty(description="派遣公司名称")
	@Column(length=20)
	private String sendCompanyName;
	
	@BeanProperty(description="派遣公司地址")
	@Column(length=40)
	private String sendCompanyAddress;
	
	@BeanProperty(description="派遣公司电话")
	@Column(length=20)
	private String sendCompanyPhone;
	
	@BeanProperty(description="派遣公司联系人")
	@Column(length=10)
	private String sendCompanyContact;
	
	@BeanProperty(description="派遣公司邮件")
	@Column(length=20)
	private String sendCompanyEmail;

	public String getSendCompanyName() {
		return sendCompanyName;
	}

	public void setSendCompanyName(String sendCompanyName) {
		this.sendCompanyName = sendCompanyName;
	}

	public String getSendCompanyAddress() {
		return sendCompanyAddress;
	}

	public void setSendCompanyAddress(String sendCompanyAddress) {
		this.sendCompanyAddress = sendCompanyAddress;
	}

	public String getSendCompanyPhone() {
		return sendCompanyPhone;
	}

	public void setSendCompanyPhone(String sendCompanyPhone) {
		this.sendCompanyPhone = sendCompanyPhone;
	}

	public String getSendCompanyContact() {
		return sendCompanyContact;
	}

	public void setSendCompanyContact(String sendCompanyContact) {
		this.sendCompanyContact = sendCompanyContact;
	}

	public String getSendCompanyEmail() {
		return sendCompanyEmail;
	}

	public void setSendCompanyEmail(String sendCompanyEmail) {
		this.sendCompanyEmail = sendCompanyEmail;
	}

	

}
