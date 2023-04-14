package com.example.java_demo_test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//�s��MySQL��Ʈw �n��src/main/resources�̪�application.properties
//(����W)�gcode �n�gjava_demo_test �~��s����java_demo_test�o�Ӹ�Ʈw

@Entity  //import�e�n���hbuild.gradle dependencies�s�W�̿�spring-boot-starter-data-jpa
		 //@Entity import persistence��  
		 //@Entity �O�s��@Repository(�ƾڳX�ݼh)-BankDao�M��Ʈw����������
@Table(name = "bank")  //�s��MySQL��Ʈw��bank�o�i��  //@Table import persistence��
public class Bank {
	
	@Id  //�]���b��Ʈw account�O�]�wPK(primary key) �ҥH�n�s�����ܭn�[�W@Id
	@Column(name = "account")  //�s��MySQL��Ʈw��bank�o�i�� ����쪺account
	private String account;
	
	@Column(name = "pwd")  //�s��MySQL��Ʈw��bank�o�i�� ����쪺pwd
	private String pwd; // password��²��
	
	@Column(name = "amount")  //�s��MySQL��Ʈw��bank�o�i�� ����쪺amount
	private int amount;
	
	public Bank() {  //***�O�o�إߪŪ��غc��k(default constructor)
		
	}

	//�ֳt�ͦ��a�Ѽƪ��غc��k: �ťճB���k�� source �� Generate Constructor using Fields
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
