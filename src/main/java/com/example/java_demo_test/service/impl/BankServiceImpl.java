package com.example.java_demo_test.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.java_demo_test.entity.Bank;
import com.example.java_demo_test.repository.BankDao;
import com.example.java_demo_test.service.ifs.BankService;
import com.example.java_demo_test.vo.BankResponse;

@Service  //*�~���޿�h ���ѪA��
public class BankServiceImpl implements BankService {  //��@BankService�o�Ӥ���
	
	@Autowired  //�s���� @Repository(�ƾڳX�ݼh)-BankDao
	private BankDao bankDao;

	@Override
	public void addInfo(Bank bank) {  //*��@�����n�a�J��k
									  //*addInfo(Bank bank)�o�Ӥ�k�n�a�Jclass Bank �~��ʺA���
		
		//�ˬd1 bank
		//�ˬd2 �b��
		checkAccount(bank);
		//�ˬd3 �K�X
		checkPwd(bank);
		//�ˬd4 �l�B ����O�t��
		checkAmount(bank);
		
		//*�s�W��ƨ�bank�o�i��
		bankDao.save(bank);  
		
		
	}
	
	//��X��k
	//���k�O�gvoid���^�� �̭��greturn�|���X��Ӥ�k
	private void checkAccount(Bank bank) {  
		//�ˬd1 bank ����Onull
		if (bank == null) {
			System.out.println("Bank����Onull!!");
			return;
		}
		//�ˬd2 �b��
		//����: (1)����Onull (2)����3~8 (3)���঳����ť� (4)���঳�S��Ÿ�(~!@#$%&?*...) 
		//�����׭��� �� �Υ��W��F
		String account = bank.getAccount();
		String pattern1 = "\\w{3,8}";  //  \w�O�i�H �Ʀr�B�r���B���u [A-Za-z0-9]
		if (account == null || !account.matches(pattern1)) {
			System.out.println("�b�����ŦX�W�w!!");
			return;
		}
	}
	
	private void checkPwd(Bank bank) {
		//�ˬd3 �K�X
		//����: (1)����Onull (2)����8~16 (3)���঳����ť� 
		String pwd = bank.getPwd();
		String pattern2 = "\\S{8,16}";  //  \S�O���� �ťաB�w��BTab��B����B�����r��
//		String pattern2 = "[\\w~!@#$%^&*]{8,16}"
		if (pwd == null || !pwd.matches(pattern2)) {
			System.out.println("�K�X���ŦX�W�w!!");
			return;
		}
	}
	
	private void checkAmount(Bank bank) {
		//�ˬd4 �l�B ����O�t��
		int amount = bank.getAmount();
		if (amount < 0) {
			return;
		}
	}

	//�αb���Ө��o�l�B
	@Override
	public Bank getAmountById(String id) {  //id�O��Bank���UatId��account(�b��)
		//�n�ˬd �ư��������n���ʧ@ �ӭ��C�귽������
		//�T�{id���S�����e
		if (!StringUtils.hasText(id)) {  //StringUtils.hasText() ��ܦ����e
			return new Bank();
		}
		//��Optional���^ �i�H�ֳt���DBank�O�_�Onull �p�G�Onull�|���W����
		
		//�g�k�@
//		Optional<Bank> op = bankDao.findById(id); //���ƥ�find��k
//		//�T�{id�O�_�s�b
//		if (!op.isPresent()) {  //isPresent() ��ܧt����(���Onull)
//			return new Bank();
//		}
//		//�Y�H�W���S���D �N���o��id�����
//		return op.get();
		
		//�g�k�G
		Optional<Bank> op = bankDao.findById(id); 
		return op.orElse(new Bank());  //orElse()�P�_ �p�G�t����(���Onull)�Nreturn op�A
													//�Y�S����(�Onull)�Nnew Bank()
		//�W��orElse()�g�k ���P��U��if-else�g�k
//		if (op.isPresent()) {
//			return op.get();
//		} else {
//			return new Bank();
//		}
	}
	
	//�s��
	@Override
	public BankResponse deposit(Bank bank) {
		//�g�k�@
//		if (bank == null) {
//			return new Bank();
//		}
//		if (!StringUtils.hasText(bank.getAccount())) {
//			return new Bank();
//		}
//		if (!StringUtils.hasText(bank.getPwd())) {
//			return new Bank();
//		}
//		if (bank.getAmount() <= 0) {
//			return new Bank();
//		}
		
		//�g�k�G
		if (bank == null 
				|| !StringUtils.hasText(bank.getAccount()) 
				|| !StringUtils.hasText(bank.getPwd()) 
				|| bank.getAmount() <= 0) {
			return new BankResponse(new Bank(), "�b���αK�X���~!!");
		}
		
		//�g�k�@
		//�qbankDao��Ʈw��O�_�����b��
//		Optional<Bank> op = bankDao.findById(bank.getAccount());  
//		if (!op.isPresent()) {
//			return new Bank();
//		}
//		Bank resBank = op.get();  //result ���o�쥻���
//		//�ݭ쥻�K�X �O�_����K�X
//		if (!resBank.getPwd().equals(bank.getPwd())) {
//			return new Bank();
//		}
		//�g�k�G
		//�qbankDao��Ʈw��O�_�����b���M�K�X
		Bank resBank = bankDao.findByAccountAndPwd(bank.getAccount(), bank.getPwd());
		if (resBank == null) {
			return new BankResponse(new Bank(), "�b���K�X���s�b!!");
		}
		int oldAmount = resBank.getAmount();  //�쥻���l�B
		int depositAmount = bank.getAmount();  //�s�ڪ����B
		int newAmount = oldAmount + depositAmount;  
		
		resBank.setAmount(newAmount);  //�]�w�s�ګ᪺���B
		
		return new BankResponse(bankDao.save(resBank), "�s�ڦ��\");  //bankDao.save(resBank) �s���Ʈw
		
		
		
	}
	
	//����
	@Override
	public BankResponse withdraw(Bank bank) {
		if (bank == null 
				|| !StringUtils.hasText(bank.getAccount()) 
				|| !StringUtils.hasText(bank.getPwd()) 
				|| bank.getAmount() <= 0) {
			return new BankResponse(new Bank(), "�b���αK�X���~!!");
		}
		
		Bank resBank = bankDao.findByAccountAndPwd(bank.getAccount(), bank.getPwd());
		if (resBank == null) {
			return new BankResponse(new Bank(), "�b���K�X���s�b!!");
		}
		int oldAmount = resBank.getAmount();  //�쥻���l�B
		int withdrawAmount = bank.getAmount();  //���ڪ����B
//		�μg oldAmount - withdrawAmount < 0
		if (withdrawAmount > oldAmount) {  //*���ڵ��s�ڦh�o�@�ӧP�_
			return new BankResponse(new Bank(), "�l�B����!!");	
		}
		int newAmount = oldAmount - withdrawAmount;  
		
		resBank.setAmount(newAmount);  //�]�w���ګ᪺���B
		
		return new BankResponse(bankDao.save(resBank), "���ڦ��\");  //bankDao.save(resBank) �s���Ʈw
	}
	

}


