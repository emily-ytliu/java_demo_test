package com.example.java_demo_test.vo;  //vo (value of object)

import com.example.java_demo_test.entity.Bank;

public class BankResponse {
	
	private Bank bank;  //帶入Bank
	
	private String message;  //顯示的訊息
	
	public BankResponse() {
		
	}

	public BankResponse(Bank bank, String message) {
		this.bank = bank;
		this.message = message;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
	
}
