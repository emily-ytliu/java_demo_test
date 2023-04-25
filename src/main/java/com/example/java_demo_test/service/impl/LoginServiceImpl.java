package com.example.java_demo_test.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.java_demo_test.constants.RtnCode;
import com.example.java_demo_test.entity.Login;
import com.example.java_demo_test.entity.PersonInfo;
import com.example.java_demo_test.repository.LoginDao;
import com.example.java_demo_test.service.ifs.LoginService;
import com.example.java_demo_test.vo.LoginResponse;
import com.example.java_demo_test.vo.PersonInfoResponse;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDao loginDao;

	//註冊帳號
	@Override
	public LoginResponse addAccount(Login login) {
		//檢查1: 輸入的login 不能是null  
		if (login == null) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		//檢查2: login的每項資訊: account、pwd、name、age
		//      String: account、pwd、name 不能是null、空字串、全空白
		//      int: age 不能是負數
		if (!StringUtils.hasText(login.getAccount())
				|| !StringUtils.hasText(login.getPwd())
				|| !StringUtils.hasText(login.getName())
				|| login.getAge() < 0) { 
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		//檢查3: account 長度3~8碼、不能有空白
		//      pwd 長度8~12碼、至少要有一個特殊符號、不能有空白/Tab/定位/換行/換頁
		String pattern1 = "\\w{3,8}";  //  \w是可以 數字、字母、底線 [A-Za-z0-9_]
		String pattern2 = "^(?=.*[!@#$%^&*(),.?\":{}|<>])\\S{8,16}";  //  \S是不能空白、定位、Tab鍵、換行、換頁，其他字元都可
		/*^(?=.*[!@#$%^&*(),.?\":{}|<>])\\S{8,16}
		 * 整個表示「開頭後方是()裡的內容，?=必須是.*任意字符出現0次或多次[]裡的內容，」
		 * ^ 表示 從開頭開始
		 * ?= 表示 positive lookahead，前面有^表示要找「開頭後方必須是」，匹配一個位置
		 * .* 表示 前方任意字符出現0次或多次
		 *    . 表示 任意字符
		 *    * 表示 出現0次或多次
		 * [!@#$%^&*(),.?\":{}|<>] 表示 []所有特殊符號
		 * () 表示 自成一個群組
		 */
//		String pattern2 = "^(?=.*[\\p{Punct}])[\\S]{8,16}$";
//		String pattern2 = "^(?=.*[\\S^\\w])[\\S]{8,16}$";
		/* 
		 * ^ 表示 匹配字串的開頭
		 * $ 表示 匹配字串的結尾
		 * . 表示 萬用字元
		 * \p 表示 比對的字元具備某種特性
		 * Punct 表示 任何標點符號 !@#$%^&*(),.?\":{}|<>
		 */
		
		if (!login.getAccount().matches(pattern1)
				|| !login.getPwd().matches(pattern2)) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		//確認: 要新增的account是否已經存在資料庫(不存在的才能新增)
		Optional<Login> op = loginDao.findById(login.getAccount());  //篩選出資料庫已經存在的account
		if (op.isPresent()) {
			return new LoginResponse(RtnCode.ALREADY_EXIST.getMessage());
		}
		//確認以上都沒問題後，就設定註冊時間
		login.setRegTime(LocalDateTime.now());
		return new LoginResponse(loginDao.save(login), RtnCode.SUCCESSFUL.getMessage());
	}

	//生效帳號
	@Override
	public LoginResponse activeAccount(String account, String pwd) {
		//檢查: 輸入的帳號密碼 不能是null、空字串、全空白
		if (!StringUtils.hasText(account)
				|| !StringUtils.hasText(pwd)) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		//確認: 帳號是否存在於資料庫(資料庫已經存在此帳號才能生效)
		Optional<Login> op = loginDao.findById(account);  //篩選出資料庫已經存在的account
		if (!op.isPresent()) {
			return new LoginResponse(RtnCode.NOT_FOUND.getMessage());
		}
		Login login = op.get();
		//確認: 如果帳號已經生效，不能重複生效
		if (login.isActive() == true) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		//如果帳號存在，就將boolean isActive設定成true，並存入資料庫
		login.setActive(true);
		
		return new LoginResponse(loginDao.save(login), RtnCode.SUCCESSFUL.getMessage());
	}

	//登入帳號
	@Override
	public LoginResponse login(String account, String pwd) {
		//寫法一 我的寫法
//		//檢查: 輸入的帳號密碼 不能是null、空字串、全空白
//		if (!StringUtils.hasText(account)
//				|| !StringUtils.hasText(pwd)) {
//			return new LoginResponse("帳號密碼錯誤");
//		}
//		//確認: 帳號是否存在於資料庫(帳號已經存在於資料庫才能登入)
//		Optional<Login> op = loginDao.findById(account);  //篩選出資料庫已經存在的account
//		if (!op.isPresent()) {
//			return new LoginResponse("此帳號不存在");
//		}
//		//確認: 密碼錯誤(與資料庫不相符)就無法成功登入
//		if (!op.get().getPwd().equals(pwd)) {  //String比較要用equals() !!!!!!
//			return new LoginResponse("密碼錯誤");
//		}
//		//確認: 帳號是否已經生效(還沒生效無法登入)
//		if (op.get().isActive() == false) {
//			return new LoginResponse("此帳號尚未生效");
//		}
//		return new LoginResponse("登入成功");
		
		//寫法二 Better 直接比對 輸入的帳號密碼 和 資料庫的帳號密碼
		//確認: 帳號密碼是否存在於資料庫(一次比對帳號和密碼=>自己寫Dao)(帳號密碼都沒輸入錯誤才能登入)
		Login login = loginDao.findByAccountAndPwd(account, pwd);  //篩選出資料庫已經存在的account
		if (login == null) {
			return new LoginResponse(RtnCode.NOT_FOUND.getMessage());
		}
		//確認: 帳號是否已經生效(還沒生效無法登入)
		if (login.isActive() == false) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		return new LoginResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public LoginResponse findAccountByCity(String city) {
		//檢查: 輸入的city 不能是null、空字串、全空白
		if (!StringUtils.hasText(city)) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		//確認: 輸入的city是否存在於資料庫(city存在於資料庫才能找出含有此特定city的使用者)
		List<Login> result = loginDao.findByCityContainingOrderByAgeAsc(city);
		if (result.isEmpty()) {
			return new LoginResponse(RtnCode.NOT_FOUND.getMessage());
		}
		return new LoginResponse(result, RtnCode.SUCCESSFUL.getMessage());
	}
	
	
}
