package com.example.java_demo_test.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "login")
public class Login {
	
	@Id
	@Column(name = "account")
	private String account;
	
	@Column(name = "pwd")
	private String pwd;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "age")
	private int age;  //因為資料庫有寫default(預設值)是0，所以用小寫int
	//如果資料庫沒有寫default(預設值)，資料庫default預設會是NULL，所以要寫大寫Integer
	
	@Column(name = "city")
	private String city;
	
	/*
	 * Date只有日期(沒有時間)
	 * private Date regTime
	 * 產生 Date ==> new Date()
	 */
	
	@Column(name = "register_time")
	private LocalDateTime regTime;  //LocalDateTime是 日期加時間
	//產生 LocalDateTime ==> LocalDateTime.now()
	
	@Column(name = "active")
	private boolean isActive;  //因為資料庫有寫default(預設值)是false==>0，所以用小寫boolean  //命名開頭加上is，辨識可以知道是布林值
	//如果資料庫沒有寫default(預設值)，資料庫default預設會是NULL，所以要寫大寫Boolean

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public LocalDateTime getRegTime() {
		return regTime;
	}

	public void setRegTime(LocalDateTime regTime) {
		this.regTime = regTime;
	}

	public boolean isActive() {  //因為是布林值，所以是is開頭，而不是get  //就算屬性命名本來就寫isActive，生成getter也只會寫isActive()
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
	
}
