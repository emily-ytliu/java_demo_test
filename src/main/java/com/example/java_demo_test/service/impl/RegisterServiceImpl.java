package com.example.java_demo_test.service.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.java_demo_test.entity.Register;
import com.example.java_demo_test.repository.RegisterDao;
import com.example.java_demo_test.service.ifs.RegisterService;
import com.example.java_demo_test.vo.RegisterRequest;
import com.example.java_demo_test.vo.RegisterResponse;

@EnableScheduling
@Service
public class RegisterServiceImpl implements RegisterService{

	@Autowired
	private RegisterDao registerDao;
	
	@Override
	public RegisterResponse register(String account, String pwd) {
		//防呆
		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
			return new RegisterResponse("Data error!");
		}
		
		Optional<Register> op = registerDao.findById(account);
		if (op.isPresent()) {
			return new RegisterResponse("Already exists!");
		}
		
		Register newReg = new Register();
		newReg.setAccount(account);
		newReg.setPwd(pwd);
		registerDao.save(newReg);
		
		//錯誤寫法: 因為資料庫不存在才能設定帳號密碼，所以因為資料庫不存在就找不到資料set~
//		op.get().setAccount(account);
//		op.get().setPwd(pwd);
//		registerDao.save(op.get());
		
		return new RegisterResponse("Successful!");
	}

	@Override
	public RegisterResponse active(String account, String pwd) {
		//防呆
		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
			return new RegisterResponse("Data error!");
		}
		
		Register res = registerDao.findByAccountAndPwd(account, pwd);
		if (res == null) {
			return new RegisterResponse("Account or password is wrong or account does not exist!");
		}
		if (res.isActive() == true) {
			return new RegisterResponse("Already active!");
		}
		
		res.setActive(true);
	
		registerDao.save(res);
		
		return new RegisterResponse("Successful!");
	}

	@Override
	public RegisterResponse login(String account, String pwd) {
		
		Register res = registerDao.findByAccountAndPwdAndIsActive(account, pwd, true);
		if (res == null) {
			return new RegisterResponse("Account or password is wrong or account does not exist or account does not active!");
		}
		
		return new RegisterResponse("Successful!");
	}

	@Override
	public RegisterResponse getRegTime(String account, String pwd) {
		//防呆
		if (!StringUtils.hasText(account) || !StringUtils.hasText(pwd)) {
			return new RegisterResponse("Data error!");
		}
		
		Register res = registerDao.findByAccountAndPwdAndIsActive(account, pwd, true);
		if (res == null) {
			return new RegisterResponse("Account or password is wrong or account does not exist or account does not active!");
		}
		
		return new RegisterResponse(res.getRegTime(), "Successful!");
	}

	@Override
	public RegisterResponse getRegTime2(RegisterRequest request, String sessionAccount, String sessionPwd, Integer sessionVerifyCode) {
		if (!StringUtils.hasText(sessionAccount) || !StringUtils.hasText(sessionPwd)) {
			return new RegisterResponse("Please login!");
		}
		if (sessionVerifyCode == null || sessionVerifyCode != request.getVerifyCode()) {
			return new RegisterResponse("Verify code incorrect!");
		}
		Register res = registerDao.findByAccountAndPwdAndIsActive(sessionAccount, sessionPwd, true);
		if (res == null) {
			return new RegisterResponse("Account or password is wrong or account does not exist or account does not active!");
		}
		return new RegisterResponse(res.getRegTime(), "Successful!");
	}

	//排程練習
	@Scheduled(cron = "0 0 10 * * *")  //每天早上10點執行一次
	//        (cron = "0 * 14-16 * * *")  //每天下午14-16點 每分鐘執行一次
	public void scheduleTest() {
		System.out.println("now: " + LocalTime.now());
	}
}
