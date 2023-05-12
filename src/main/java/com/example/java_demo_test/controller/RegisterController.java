package com.example.java_demo_test.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_demo_test.service.ifs.RegisterService;
import com.example.java_demo_test.vo.RegisterRequest;
import com.example.java_demo_test.vo.RegisterResponse;

@RestController
public class RegisterController {

	@Autowired
	private RegisterService registerService;
	
	@PostMapping(value = "api/register")
	public RegisterResponse register(@RequestBody RegisterRequest request) {
		return registerService.register(request.getAccount(), request.getPwd());
	}
	
	@PostMapping(value = "active")
	public RegisterResponse active(@RequestBody RegisterRequest request) {
		return registerService.active(request.getAccount(), request.getPwd());
	}
	
	@PostMapping(value = "login")
	public RegisterResponse login(@RequestBody RegisterRequest request, HttpSession httpSession) {
		RegisterResponse res = registerService.login(request.getAccount(), request.getPwd());
		
		if (res.getMessage().equalsIgnoreCase("Successful!")) {  //一般會用equals比對RtnCode
			//創造驗證碼
			double random = Math.random()*10000;  //*10000是讓random為四位數
			int verifyCode = (int)Math.round(random);  //驗證碼，round()四捨五入
			
			//透過setAttribute，把驗證碼、帳號、密碼存到session裡面
			httpSession.setAttribute("verifyCode", verifyCode);  
			httpSession.setAttribute("account", request.getAccount());
			httpSession.setAttribute("pwd", request.getPwd());
			httpSession.setMaxInactiveInterval(60);  //設定session資料保存時間(單位是秒)(預設是30分鐘)，此表示60秒
			
			res.setSessionId(httpSession.getId());
			res.setVerifyCode(verifyCode);
		}
		return res;
	}
	
	@PostMapping(value = "get_reg_time")
	public RegisterResponse getRegTime(@RequestBody RegisterRequest request) {
		return registerService.getRegTime(request.getAccount(), request.getPwd());
	}
	
	//判斷寫在Controller
	@PostMapping(value = "get_reg_time1")
	public RegisterResponse getRegTime1(@RequestBody RegisterRequest request, HttpSession httpSession) {
		//因為setAttribute(key: String, value: Object)，所以getAttribute("account")也是Object，因此要強制轉型回(String)
		String account = (String) httpSession.getAttribute("account");  
		String pwd = (String) httpSession.getAttribute("pwd");
		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
			return new RegisterResponse("Please login!");
		}
		//前面資料型態int和轉型寫(int)會報錯原因: 
		//如果httpSession.getAttribute("verifyCode")是null，就無法強轉成(int)，就會報錯 → 所以要用Integer
		//可能出現null的情況: (1)沒有login (2)session時效過期
		Integer verifyCode = (Integer) httpSession.getAttribute("verifyCode"); 
		if (verifyCode == null || verifyCode != request.getVerifyCode()) {
			return new RegisterResponse("Verify code incorrect!");
		}
		
		return registerService.getRegTime(account, pwd);
	}
	
	//判斷寫在Impl
	@PostMapping(value = "get_reg_time2")
	public RegisterResponse getRegTime2(@RequestBody RegisterRequest request, HttpSession httpSession) {
		String sessionAccount = (String) httpSession.getAttribute("account");  
		String sessionPwd = (String) httpSession.getAttribute("pwd");
		Integer sessionVerifyCode = (Integer) httpSession.getAttribute("verifyCode"); 
		
		//request是要帶入輸入的verifyCode
		return registerService.getRegTime2(request, sessionAccount, sessionPwd, sessionVerifyCode);
		}
}
