package com.example.java_demo_test.vo;

public class RegisterRequest {

	private String account;
	
	private String pwd;
	
	public RegisterRequest() {
		
	}

	public RegisterRequest(String account, String pwd) {
		super();
		this.account = account;
		this.pwd = pwd;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
