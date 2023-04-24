package com.example.java_demo_test.vo;

import java.util.List;

import com.example.java_demo_test.entity.Login;

public class LoginResponse {
	
	private List<Login> cityList;
	
	private Login login;
	
	private String message;

	public LoginResponse() {
		
	}
	
	public LoginResponse(List<Login> cityList, String message) {
		this.cityList = cityList;
		this.message = message;
	}

	public LoginResponse(Login login, String message) {
		this.login = login;
		this.message = message;
	}

	public LoginResponse(String message) {
		this.message = message;
	}
	
	public List<Login> getCityList() {
		return cityList;
	}

	public void setCityList(List<Login> cityList) {
		this.cityList = cityList;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
