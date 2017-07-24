package com.ts.main.hr.outsiders;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseBean;

@BeanProperty(description="外来人员信息")
@Entity
@Table(name = "HR_Outsiders")
public class Outsiders extends BaseBean {
	@BeanProperty(description = "姓名")
	@Column(length = 10)
	private String outsidersName;

	@BeanProperty(description = "手机号码")
	@Column(length = 20)
	private String mobile;

	@BeanProperty(description = "电子邮箱")
	@Column(length = 20)
	private String email;

	@BeanProperty(description="公司名称")
	@Column(length = 20)
	private String companyName;
	
	@BeanProperty(description = "公司地址")
	@Column(length = 150)
	private String address;
	
	@BeanProperty(description = "职务")
	@Column(length = 50)
	private String positionName;
	 
	@BeanProperty(description = "是否为本公司客户")
	@Column(length = 5)
	private String companyCust="0";

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getOutsidersName() {
		return outsidersName;
	}

	public void setOutsidersName(String outsidersName) {
		this.outsidersName = outsidersName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyCust() {
		return companyCust;
	}

	public void setCompanyCust(String companyCust) {
		this.companyCust = companyCust;
	}


}
