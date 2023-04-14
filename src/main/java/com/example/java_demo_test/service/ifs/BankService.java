package com.example.java_demo_test.service.ifs;

import com.example.java_demo_test.entity.Bank;
import com.example.java_demo_test.vo.BankResponse;

public interface BankService {  //建立一個interface 之後讓一個class BankServiceImpl去實作這個介面
	
	public void addInfo(Bank bank);  //定義方法要用public 
									 //addInfo(Bank bank)這個方法要帶入class Bank的屬性
	//取出Bank裡的資料
	public Bank getAmountById(String id);  //取得餘額
	
	//存款
	public BankResponse deposit(Bank bank);
	
	//提款
	public BankResponse withdraw(Bank bank);
	
}
