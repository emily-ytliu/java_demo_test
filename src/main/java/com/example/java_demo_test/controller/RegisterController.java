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
		
		if (res.getMessage().equalsIgnoreCase("Successful!")) {  //�@��|��equals���RtnCode
			//�гy���ҽX
			double random = Math.random()*10000;  //*10000�O��random���|���
			int verifyCode = (int)Math.round(random);  //���ҽX�Around()�|�ˤ��J
			
			//�z�LsetAttribute�A�����ҽX�B�b���B�K�X�s��session�̭�
			httpSession.setAttribute("verifyCode", verifyCode);  
			httpSession.setAttribute("account", request.getAccount());
			httpSession.setAttribute("pwd", request.getPwd());
			httpSession.setMaxInactiveInterval(60);  //�]�wsession��ƫO�s�ɶ�(���O��)(�w�]�O30����)�A�����60��
			
			res.setSessionId(httpSession.getId());
			res.setVerifyCode(verifyCode);
		}
		return res;
	}
	
	@PostMapping(value = "get_reg_time")
	public RegisterResponse getRegTime(@RequestBody RegisterRequest request) {
		return registerService.getRegTime(request.getAccount(), request.getPwd());
	}
	
	//�P�_�g�bController
	@PostMapping(value = "get_reg_time1")
	public RegisterResponse getRegTime1(@RequestBody RegisterRequest request, HttpSession httpSession) {
		//�]��setAttribute(key: String, value: Object)�A�ҥHgetAttribute("account")�]�OObject�A�]���n�j���૬�^(String)
		String account = (String) httpSession.getAttribute("account");  
		String pwd = (String) httpSession.getAttribute("pwd");
		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
			return new RegisterResponse("Please login!");
		}
		//�e����ƫ��Aint�M�૬�g(int)�|������]: 
		//�p�GhttpSession.getAttribute("verifyCode")�Onull�A�N�L�k�j�ন(int)�A�N�|���� �� �ҥH�n��Integer
		//�i��X�{null�����p: (1)�S��login (2)session�ɮĹL��
		Integer verifyCode = (Integer) httpSession.getAttribute("verifyCode"); 
		if (verifyCode == null || verifyCode != request.getVerifyCode()) {
			return new RegisterResponse("Verify code incorrect!");
		}
		
		return registerService.getRegTime(account, pwd);
	}
	
	//�P�_�g�bImpl
	@PostMapping(value = "get_reg_time2")
	public RegisterResponse getRegTime2(@RequestBody RegisterRequest request, HttpSession httpSession) {
		String sessionAccount = (String) httpSession.getAttribute("account");  
		String sessionPwd = (String) httpSession.getAttribute("pwd");
		Integer sessionVerifyCode = (Integer) httpSession.getAttribute("verifyCode"); 
		
		//request�O�n�a�J��J��verifyCode
		return registerService.getRegTime2(request, sessionAccount, sessionPwd, sessionVerifyCode);
		}
}
