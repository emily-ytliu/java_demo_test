package com.example.java_demo_test.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.java_demo_test.entity.Register;
import com.example.java_demo_test.repository.RegisterDao;
import com.example.java_demo_test.service.ifs.RegisterService;
import com.example.java_demo_test.vo.RegisterResponse;

@Service
public class RegisterServiceImpl implements RegisterService{

	@Autowired
	private RegisterDao registerDao;
	
	@Override
	public RegisterResponse register(String account, String pwd) {
		//¨¾§b
		if (!StringUtils.hasText(account) || StringUtils.hasText(pwd)) {
			return new RegisterResponse("Data error!");
		}
		
		Optional<Register> op = registerDao.findById(account);
		
		if (op.isPresent()) {
			return new RegisterResponse("Already exists!");
		}
		
		op.get().setAccount(account);
		op.get().setPwd(pwd);
		
		registerDao.save(op.get());
		
		return new RegisterResponse("Successful!");
	}

	@Override
	public RegisterResponse active(String account, String pwd) {
		//¨¾§b
		if (!StringUtils.hasText(account) || StringUtils.hasText(pwd)) {
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
		//¨¾§b
		if (!StringUtils.hasText(account) || StringUtils.hasText(pwd)) {
			return new RegisterResponse("Data error!");
		}
		
		Register res = registerDao.findByAccountAndPwdAndActive(account, pwd, true);
		
		if (res == null) {
			return new RegisterResponse("Account or password is wrong or account does not exist or account does not active!");
		}
		
		return new RegisterResponse("Successful!");
	}

	@Override
	public RegisterResponse getRegTime(String account, String pwd) {
		//¨¾§b
		if (!StringUtils.hasText(account) || StringUtils.hasText(pwd)) {
			return new RegisterResponse("Data error!");
		}
		
		Register res = registerDao.findByAccountAndPwdAndActive(account, pwd, true);
		
		if (res == null) {
			return new RegisterResponse("Account or password is wrong or account does not exist or account does not active!");
		}
		
		return new RegisterResponse(res.getRegTime(), "Successful!");
	}

}
