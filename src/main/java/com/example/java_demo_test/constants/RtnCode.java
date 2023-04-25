package com.example.java_demo_test.constants;

public enum RtnCode {

	SUCCESSFUL("200", "Successful"),  //�r�I
	DATA_ERROR("400", "Data error!"),
	NOT_FOUND("404", "Not found!"),
	ALREADY_EXIST("409", "Already exists!");
	
	private String httpStatusCode;
	
	private String message;
	
	private RtnCode(String httpStatusCode, String message) {
		this.httpStatusCode = httpStatusCode;
		this.message = message;
	}

	public String getHttpStatusCode() {
		return httpStatusCode;
	}

	public String getMessage() {
		return message;
	}
	
	
}
