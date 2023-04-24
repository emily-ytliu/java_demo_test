package com.example.java_demo_test.service.ifs;

import java.util.List;

import com.example.java_demo_test.entity.Login;
import com.example.java_demo_test.vo.LoginResponse;

public interface LoginService {

	public LoginResponse addAccount(Login login);
	
	public LoginResponse activeAccount(String account, String pwd);
	
	public LoginResponse login(String account, String pwd);
	
	public LoginResponse findAccountByCity(String city);
}
