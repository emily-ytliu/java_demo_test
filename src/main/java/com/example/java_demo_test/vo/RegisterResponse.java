package com.example.java_demo_test.vo;

import java.time.LocalDateTime;

public class RegisterResponse {
	
	private LocalDateTime regTime;
	
	private String message;
	
	public RegisterResponse() {
		
	}
	
	public RegisterResponse(LocalDateTime regTime, String message) {
		super();
		this.regTime = regTime;
		this.message = message;
	}

	public RegisterResponse(String message) {
		super();
		this.message = message;
	}
	
	public LocalDateTime getRegTime() {
		return regTime;
	}

	public void setRegTime(LocalDateTime regTime) {
		this.regTime = regTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
