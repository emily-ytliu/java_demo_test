package com.example.java_demo_test.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.java_demo_test.entity.Bank;
import com.example.java_demo_test.repository.BankDao;
import com.example.java_demo_test.service.ifs.BankService;
import com.example.java_demo_test.vo.BankResponse;

@Service  //*業務邏輯層 提供服務
public class BankServiceImpl implements BankService {  //實作BankService這個介面
	
	@Autowired  //連結到 @Repository(數據訪問層)-BankDao
	private BankDao bankDao;

	@Override
	public void addInfo(Bank bank) {  //*實作介面要帶入方法
									  //*addInfo(Bank bank)這個方法要帶入class Bank 才能動態資料
		
		//檢查1 bank
		//檢查2 帳號
		checkAccount(bank);
		//檢查3 密碼
		checkPwd(bank);
		//檢查4 餘額 不能是負數
		checkAmount(bank);
		
		//*新增資料到bank這張表
		bankDao.save(bank);  
		
		
	}
	
	//抽出方法
	//當方法是寫void不回傳 裡面寫return會跳出整個方法
	private void checkAccount(Bank bank) {  
		//檢查1 bank 不能是null
		if (bank == null) {
			System.out.println("Bank不能是null!!");
			return;
		}
		//檢查2 帳號
		//條件: (1)不能是null (2)長度3~8 (3)不能有任何空白 (4)不能有特殊符號(~!@#$%&?*...) 
		//有長度限制 → 用正規表達
		String account = bank.getAccount();
		String pattern1 = "\\w{3,8}";  //  \w是可以 數字、字母、底線 [A-Za-z0-9]
		if (account == null || !account.matches(pattern1)) {
			System.out.println("帳號不符合規定!!");
			return;
		}
	}
	
	private void checkPwd(Bank bank) {
		//檢查3 密碼
		//條件: (1)不能是null (2)長度8~16 (3)不能有任何空白 
		String pwd = bank.getPwd();
		String pattern2 = "\\S{8,16}";  //  \S是不行 空白、定位、Tab鍵、換行、換頁字元
//		String pattern2 = "[\\w~!@#$%^&*]{8,16}"
		if (pwd == null || !pwd.matches(pattern2)) {
			System.out.println("密碼不符合規定!!");
			return;
		}
	}
	
	private void checkAmount(Bank bank) {
		//檢查4 餘額 不能是負數
		int amount = bank.getAmount();
		if (amount < 0) {
			return;
		}
	}

	//用帳號來取得餘額
	@Override
	public Bank getAmountById(String id) {  //id是指Bank有下atId的account(帳號)
		//要檢查 排除掉不必要的動作 來降低資源的消耗
		//確認id有沒有內容
		if (!StringUtils.hasText(id)) {  //StringUtils.hasText() 表示有內容
			return new Bank();
		}
		//用Optional接回 可以快速知道Bank是否是null 如果是null會馬上報錯
		
		//寫法一
//		Optional<Bank> op = bankDao.findById(id); //找資料用find方法
//		//確認id是否存在
//		if (!op.isPresent()) {  //isPresent() 表示含有值(不是null)
//			return new Bank();
//		}
//		//若以上都沒問題 就取得此id的資料
//		return op.get();
		
		//寫法二
		Optional<Bank> op = bankDao.findById(id); 
		return op.orElse(new Bank());  //orElse()判斷 如果含有值(不是null)就return op，
													//若沒有值(是null)就new Bank()
		//上方orElse()寫法 等同於下方if-else寫法
//		if (op.isPresent()) {
//			return op.get();
//		} else {
//			return new Bank();
//		}
	}
	
	//存款
	@Override
	public BankResponse deposit(Bank bank) {
		//寫法一
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
		
		//寫法二
		if (bank == null 
				|| !StringUtils.hasText(bank.getAccount()) 
				|| !StringUtils.hasText(bank.getPwd()) 
				|| bank.getAmount() <= 0) {
			return new BankResponse(new Bank(), "帳號或密碼錯誤!!");
		}
		
		//寫法一
		//從bankDao資料庫找是否有此帳號
//		Optional<Bank> op = bankDao.findById(bank.getAccount());  
//		if (!op.isPresent()) {
//			return new Bank();
//		}
//		Bank resBank = op.get();  //result 取得原本資料
//		//看原本密碼 是否等於密碼
//		if (!resBank.getPwd().equals(bank.getPwd())) {
//			return new Bank();
//		}
		//寫法二
		//從bankDao資料庫找是否有此帳號和密碼
		Bank resBank = bankDao.findByAccountAndPwd(bank.getAccount(), bank.getPwd());
		if (resBank == null) {
			return new BankResponse(new Bank(), "帳號密碼不存在!!");
		}
		int oldAmount = resBank.getAmount();  //原本的餘額
		int depositAmount = bank.getAmount();  //存款的金額
		int newAmount = oldAmount + depositAmount;  
		
		resBank.setAmount(newAmount);  //設定存款後的金額
		
		return new BankResponse(bankDao.save(resBank), "存款成功");  //bankDao.save(resBank) 存到資料庫
		
		
		
	}
	
	//提款
	@Override
	public BankResponse withdraw(Bank bank) {
		if (bank == null 
				|| !StringUtils.hasText(bank.getAccount()) 
				|| !StringUtils.hasText(bank.getPwd()) 
				|| bank.getAmount() <= 0) {
			return new BankResponse(new Bank(), "帳號或密碼錯誤!!");
		}
		
		Bank resBank = bankDao.findByAccountAndPwd(bank.getAccount(), bank.getPwd());
		if (resBank == null) {
			return new BankResponse(new Bank(), "帳號密碼不存在!!");
		}
		int oldAmount = resBank.getAmount();  //原本的餘額
		int withdrawAmount = bank.getAmount();  //提款的金額
//		或寫 oldAmount - withdrawAmount < 0
		if (withdrawAmount > oldAmount) {  //*提款筆存款多這一個判斷
			return new BankResponse(new Bank(), "餘額不足!!");	
		}
		int newAmount = oldAmount - withdrawAmount;  
		
		resBank.setAmount(newAmount);  //設定提款後的金額
		
		return new BankResponse(bankDao.save(resBank), "提款成功");  //bankDao.save(resBank) 存到資料庫
	}
	

}


