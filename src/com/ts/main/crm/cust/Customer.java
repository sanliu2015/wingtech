package com.ts.main.crm.cust;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseBean;
import com.ts.core.common.bean.BaseFileBean;

//客户信息
@Entity
@Table(name = "CRM_Customer")
public class Customer extends BaseFileBean {

	// 客户信息 负责人 业务员(前三个需要录入) 合作记录

	// @BeanProperty(description = "客户名称")
	// @Column
	// private String custName;

	@BeanProperty(description = "客户类别")
	@Column(length = 10)
	private String custType;

	@BeanProperty(description = "客户类别名称")
	@Transient
	private String custTypeName;

	@BeanProperty(description = "手机号码")
	@Column(length = 50)
	private String mobile;

	@BeanProperty(description = "固定电话")
	@Column(length = 50)
	private String telephone;

	@BeanProperty(description = "电子邮箱")
	@Column(length = 80)
	private String email;

	@BeanProperty(description = "传真")
	@Column(length = 50)
	private String fax;

	@BeanProperty(description = "公司地址")
	@Column(length = 200)
	private String address;

	@BeanProperty(description = "地区Id")
	@Column
	private Integer custAreaId;

	@BeanProperty(description = "省份Id")
	@Column
	private Integer provinceId;

	@BeanProperty(description = "地区Name")
	@Transient
	private String custAreaName;

	@BeanProperty(description = "经营许可证")
	@Column(length = 50)
	private String license;

	@BeanProperty(description = "信誉额度")
	@Column(length =50)
	private String credit;

	@BeanProperty(description = "币种")
	@Column
	private Integer currency;

	@BeanProperty(description = "币种")
	@Transient
	private String currencyName;

	@BeanProperty(description = "结算方式")
	@Column
	private Integer settlementWay;

	@BeanProperty(description = "结算方式")
	@Column(length = 50)
	private String settlementWayName;

	@BeanProperty(description = "信用级别")
	@Column
	private Integer creditLevel;

	@BeanProperty(description = "信用级别")
	@Column(length = 20)
	private String creditLevelName;
	
	@BeanProperty(description="邮政编码")
	@Column( length = 50) 
    private String postCode; 
	 
	@BeanProperty(description = "税号")
	@Column(length = 50)
	private String tariff;
	@BeanProperty(description = "是否为本公司客户")
	@Column(length = 5)
	private String companyCust="0";
	
	@BeanProperty(description="归属部门")
	@Column 
	private Integer deptId;
	@BeanProperty(description = "付款联系人")
	@Column(length = 50)
	private String payContact;
	@BeanProperty(description = "付款联系人电话")
	@Column(length = 50)
	private String payContactPhone;
	@BeanProperty(description = "发展日期")
	@Column(length = 15)
	private String developDate;
	
	@BeanProperty(description = "简称")
	@Column(length = 80)
	private String abbName;
	@BeanProperty(description = "法人")
	@Column(length = 100)
	private String corporation;
	
	public String getPayContact() {
		return payContact;
	}

	public void setPayContact(String payContact) {
		this.payContact = payContact;
	}

	public String getPayContactPhone() {
		return payContactPhone;
	}

	public void setPayContactPhone(String payContactPhone) {
		this.payContactPhone = payContactPhone;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getCompanyCust() {
		return companyCust;
	}

	public void setCompanyCust(String companyCust) {
		this.companyCust = companyCust;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCustTypeName() {
		return custTypeName;
	}

	public void setCustTypeName(String custTypeName) {
		this.custTypeName = custTypeName;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getTariff() {
		return tariff;
	}

	public void setTariff(String tariff) {
		this.tariff = tariff;
	}

	public Integer getCurrency() {
		return currency;
	}

	public void setCurrency(Integer currency) {
		this.currency = currency;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public Integer getSettlementWay() {
		return settlementWay;
	}

	public void setSettlementWay(Integer settlementWay) {
		this.settlementWay = settlementWay;
	}

	public String getSettlementWayName() {
		return settlementWayName;
	}

	public void setSettlementWayName(String settlementWayName) {
		this.settlementWayName = settlementWayName;
	}

	public Integer getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(Integer creditLevel) {
		this.creditLevel = creditLevel;
	}

	public String getCreditLevelName() {
		return creditLevelName;
	}

	public void setCreditLevelName(String creditLevelName) {
		this.creditLevelName = creditLevelName;
	}

	public Integer getCustAreaId() {
		return custAreaId;
	}

	public void setCustAreaId(Integer custAreaId) {
		this.custAreaId = custAreaId;
	}

	public String getCustAreaName() {
		return custAreaName;
	}

	public void setCustAreaName(String custAreaName) {
		this.custAreaName = custAreaName;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
}
