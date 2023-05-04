package com.example.java_demo_test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.java_demo_test.entity.Bank;
import com.example.java_demo_test.repository.BankDao;
import com.example.java_demo_test.service.ifs.BankService;
import com.example.java_demo_test.vo.BankResponse;

@SpringBootTest(classes = JavaDemoTestApplication.class)  //跑Spring Boot
public class BankTest {
	
	@Autowired  //連結到 @Repository(數據訪問層)-BankDao
	private BankDao bankDao;
	
	@Autowired
	private BankService bankService;
	
	
	//新增假資料 (每一個方法前後)
	@BeforeEach
	public void beforeEach() {
		
	}
	
	//刪除假資料 (每一個方法前後)
	@AfterEach
	public void aftereEach() {
		
	}
	
	
	@Test
	public void addBankInfo() {  //*寫死資料
		Bank bank = new Bank("aaa", "bbb", 100);  //Bank的建構方法 Bank(String account, String pwd, int amount)
		bankDao.save(bank);  //新增資料到bank這張表  //上面要寫@Autowired做連結 才能找到bankDao
	}
	
	@Test
	public void addInfoTest() {  //*帶入動態資料
		Bank bank = new Bank("AA999", "AA123456@", 2000);
		bankService.addInfo(bank);  //新增資料到bank這張表  //上面要寫@Autowired做連結 才能找到bankService
	}
	
	@Test
	public void getAmountByIdTest() {
		Bank bank = bankService.getAmountById("A111");
		System.out.println("帳號: " + bank.getAccount() + " 餘額: " + bank.getAmount());
	}
	
	@Test
	public void depositTest() {
		//創建假資料
		//寫法一
//		Bank bank = new Bank("AA999", "AA123456@", 2000);
//		Bank oldBank = bankDao.save(bank);
		//寫法二
		Bank oldBank = bankDao.save(new Bank("AA999", "AA123456@", 2000));
		
		//存款
		Bank newBank = new Bank("AA999", "AA123456@", 3000);
		BankResponse response = bankService.deposit(newBank);  //*response
		//確認金額有存進去  //Assert.isTrue確認程式碼有沒有錯
		Bank resBank = response.getBank();   //*response
		Assert.isTrue(resBank.getAmount() == oldBank.getAmount() + newBank.getAmount(), "存款錯誤");
		Assert.isTrue(response.getMessage().equals("存款成功"), "存款失敗");
		//刪除測試資料
		bankDao.delete(resBank);
	}
	
	@Test
	public void withdrawTest() {
		//創建假資料
		Bank oldBank = bankDao.save(new Bank("AA999", "AA123456@", 2000));
		//提款
		Bank newBank = new Bank("AA999", "AA123456@", 500);
		BankResponse response = bankService.withdraw(newBank);  //*response: 因為BankResponse有帶入Bank 所以可以用Bank
		//確認提款後餘額有變  //Assert.isTrue確認程式碼有沒有錯
		Bank resBank = response.getBank();  //*response
		Assert.isTrue(resBank.getAmount() == oldBank.getAmount() - newBank.getAmount(), "提款錯誤");
		Assert.isTrue(response.getMessage().equals("提款成功"), "提款失敗");
//		System.out.println("帳號: " + resBank.getAccount() + "密碼: " + resBank.getPwd());
		//刪除測試資料
		bankDao.delete(resBank);
	}
	
	@Test
	public void test() {
		int[] ary1 = {10, 20 ,30 ,40};
		int[] ary2 = new int[2];
		ary2 = ary1;
		for(int val : ary2) {
			System.out.println(val);
		}
		
	}

}

