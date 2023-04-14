package com.example.java_demo_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.java_demo_test.entity.Bank;
import com.example.java_demo_test.repository.BankDao;
import com.example.java_demo_test.service.ifs.BankService;
import com.example.java_demo_test.vo.BankResponse;

@SpringBootTest(classes = JavaDemoTestApplication.class)  //�]Spring Boot
public class BankTest {
	
	@Autowired  //�s���� @Repository(�ƾڳX�ݼh)-BankDao
	private BankDao bankDao;
	
	@Autowired
	private BankService bankService;
	
	@Test
	public void addBankInfo() {  //*�g�����
		Bank bank = new Bank("aaa", "bbb", 100);  //Bank���غc��k Bank(String account, String pwd, int amount)
		bankDao.save(bank);  //�s�W��ƨ�bank�o�i��  //�W���n�g@Autowired���s�� �~����bankDao
	}
	
	@Test
	public void addInfoTest() {  //*�a�J�ʺA���
		Bank bank = new Bank("AA999", "AA123456@", 2000);
		bankService.addInfo(bank);  //�s�W��ƨ�bank�o�i��  //�W���n�g@Autowired���s�� �~����bankService
	}
	
	@Test
	public void getAmountByIdTest() {
		Bank bank = bankService.getAmountById("A111");
		System.out.println("�b��: " + bank.getAccount() + " �l�B: " + bank.getAmount());
	}
	
	@Test
	public void depositTest() {
		//�Ыذ����
		//�g�k�@
//		Bank bank = new Bank("AA999", "AA123456@", 2000);
//		Bank oldBank = bankDao.save(bank);
		//�g�k�G
		Bank oldBank = bankDao.save(new Bank("AA999", "AA123456@", 2000));
		
		//�s��
		Bank newBank = new Bank("AA999", "AA123456@", 3000);
		BankResponse response = bankService.deposit(newBank);
		//�T�{���B���s�i�h  //Assert.isTrue�T�{�{���X���S����
		Bank resBank = response.getBank(); 
		Assert.isTrue(resBank.getAmount() == oldBank.getAmount() + newBank.getAmount(), "�s�ڿ��~");
		Assert.isTrue(response.getMessage().equals("�s�ڦ��\"), "�s�ڥ���");
		//�R�����ո��
		bankDao.delete(resBank);
	}
	
	@Test
	public void withdrawTest() {
		//�Ыذ����
		Bank oldBank = bankDao.save(new Bank("AA999", "AA123456@", 2000));
		//����
		Bank newBank = new Bank("AA999", "AA123456@", 500);
		BankResponse response = bankService.withdraw(newBank);  //*��
		//�T�{���ګ�l�B����  //Assert.isTrue�T�{�{���X���S����
		Bank resBank = response.getBank();  //*
		Assert.isTrue(resBank.getAmount() == oldBank.getAmount() - newBank.getAmount(), "���ڿ��~");
		Assert.isTrue(response.getMessage().equals("���ڦ��\"), "���ڥ���");
		//�R�����ո��
		bankDao.delete(resBank);
	}

}
