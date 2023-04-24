package com.example.java_demo_test.vo;

import java.util.List;

import com.example.java_demo_test.entity.Login;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {
	
	private Login login;
	
	private String account;
	
	private String pwd;
	
	private String city;

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
	
}
