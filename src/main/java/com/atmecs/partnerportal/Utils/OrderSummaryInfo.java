package com.atmecs.partnerportal.Utils;


public class OrderSummaryInfo {
	
	@Override
	public String toString() {
		return "OrderSummaryInfo [customerName=" + customerName + ", customerOrgId=" + customerOrgId + ", projectName="
				+ projectName + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state="
				+ state + ", country=" + country + ", postalCode=" + postalCode + ", customerPhoneNo=" + customerPhoneNo
				+ ", customeEmail=" + customeEmail + ", poNumber=" + poNumber + "]";
	}


	private String customerName;
	private String customerOrgId;
	private String projectName;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	private String customerPhoneNo;
	private String customeEmail;
	private String poNumber;
	
	
	public String getCustomerName() {
		return customerName;
	}


	public String getCustomerOrgId() {
		return customerOrgId;
	}


	public String getProjectName() {
		return projectName;
	}


	public String getAddress1() {
		return address1;
	}


	public String getAddress2() {
		return address2;
	}


	public String getCity() {
		return city;
	}


	public String getState() {
		return state;
	}


	public String getCountry() {
		return country;
	}


	public String getPostalCode() {
		return postalCode;
	}


	public String getCustomerPhoneNo() {
		return customerPhoneNo;
	}


	public String getCustomeEmail() {
		return customeEmail;
	}


	public String getPoNumber() {
		return poNumber;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public void setCustomerOrgId(String customerOrgId) {
		this.customerOrgId = customerOrgId;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public void setAddress1(String address1) {
		this.address1 = address1;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public void setState(String state) {
		this.state = state;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	public void setCustomerPhoneNo(String customerPhoneNo) {
		this.customerPhoneNo = customerPhoneNo;
	}


	public void setCustomeEmail(String customeEmail) {
		this.customeEmail = customeEmail;
	}


	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}


	public OrderSummaryInfo(String customerName, String customerOrgId, String projectName, String address1,
			String address2, String city, String state, String country, String postalCode, String customerPhoneNo,
			String customeEmail, String poNumber) {
		super();
		this.customerName = customerName;
		this.customerOrgId = customerOrgId;
		this.projectName = projectName;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.postalCode = postalCode;
		this.customerPhoneNo = customerPhoneNo;
		this.customeEmail = customeEmail;
		this.poNumber = poNumber;
	}
	
	
	
	

}
