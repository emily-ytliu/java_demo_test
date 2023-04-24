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
	private int age;  //�]����Ʈw���gdefault(�w�]��)�O0�A�ҥH�Τp�gint
	//�p�G��Ʈw�S���gdefault(�w�]��)�A��Ʈwdefault�w�]�|�ONULL�A�ҥH�n�g�j�gInteger
	
	@Column(name = "city")
	private String city;
	
	/*
	 * Date�u�����(�S���ɶ�)
	 * private Date regTime
	 * ���� Date ==> new Date()
	 */
	
	@Column(name = "register_time")
	private LocalDateTime regTime;  //LocalDateTime�O ����[�ɶ�
	//���� LocalDateTime ==> LocalDateTime.now()
	
	@Column(name = "active")
	private boolean isActive;  //�]����Ʈw���gdefault(�w�]��)�Ofalse==>0�A�ҥH�Τp�gboolean  //�R�W�}�Y�[�Wis�A���ѥi�H���D�O���L��
	//�p�G��Ʈw�S���gdefault(�w�]��)�A��Ʈwdefault�w�]�|�ONULL�A�ҥH�n�g�j�gBoolean

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

	public boolean isActive() {  //�]���O���L�ȡA�ҥH�Ois�}�Y�A�Ӥ��Oget  //�N���ݩʩR�W���ӴN�gisActive�A�ͦ�getter�]�u�|�gisActive()
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	
	
}
