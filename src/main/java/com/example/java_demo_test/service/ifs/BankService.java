package com.example.java_demo_test.service.ifs;

import com.example.java_demo_test.entity.Bank;
import com.example.java_demo_test.vo.BankResponse;

public interface BankService {  //�إߤ@��interface �������@��class BankServiceImpl�h��@�o�Ӥ���
	
	public void addInfo(Bank bank);  //�w�q��k�n��public 
									 //addInfo(Bank bank)�o�Ӥ�k�n�a�Jclass Bank���ݩ�
	//���XBank�̪����
	public Bank getAmountById(String id);  //���o�l�B
	
	//�s��
	public BankResponse deposit(Bank bank);
	
	//����
	public BankResponse withdraw(Bank bank);
	
}
