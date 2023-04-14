package com.example.java_demo_test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//連結MySQL資料庫 要到src/main/resources裡的application.properties
//(接續上)寫code 要寫java_demo_test 才能連結到java_demo_test這個資料庫

@Entity  //import前要先去build.gradle dependencies新增依賴spring-boot-starter-data-jpa
		 //@Entity import persistence的  
		 //@Entity 是連結@Repository(數據訪問層)-BankDao和資料庫的中介橋梁
@Table(name = "bank")  //連結MySQL資料庫的bank這張表  //@Table import persistence的
public class Bank {
	
	@Id  //因為在資料庫 account是設定PK(primary key) 所以要連結的話要加上@Id
	@Column(name = "account")  //連結MySQL資料庫的bank這張表 的欄位的account
	private String account;
	
	@Column(name = "pwd")  //連結MySQL資料庫的bank這張表 的欄位的pwd
	private String pwd; // password的簡稱
	
	@Column(name = "amount")  //連結MySQL資料庫的bank這張表 的欄位的amount
	private int amount;
	
	public Bank() {  //***記得建立空的建構方法(default constructor)
		
	}

	//快速生成帶參數的建構方法: 空白處按右鍵 source → Generate Constructor using Fields
	public Bank(String account, String pwd, int amount) {
		this.account = account;
		this.pwd = pwd;
		this.amount = amount;
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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
