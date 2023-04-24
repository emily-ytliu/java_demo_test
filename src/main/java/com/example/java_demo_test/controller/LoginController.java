package com.example.java_demo_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_demo_test.service.ifs.LoginService;
import com.example.java_demo_test.vo.LoginRequest;
import com.example.java_demo_test.vo.LoginResponse;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@PostMapping(value = "add_account")
	public LoginResponse addAccount(@RequestBody LoginRequest request) {
		return loginService.addAccount(request.getLogin()) ;
	}
	
	@PostMapping(value = "active_account")
	public LoginResponse activeAccount(@RequestBody LoginRequest request) {
		return loginService.activeAccount(request.getAccount(), request.getPwd()) ;
	}
	
	@PostMapping(value = "login")
	public LoginResponse login(@RequestBody LoginRequest request) {
		return loginService.login(request.getAccount(), request.getPwd()) ;
	}
	
	@PostMapping(value = "find_by_city")
	public LoginResponse findAccountByCity(@RequestBody LoginRequest request) {
		return loginService.findAccountByCity(request.getCity()) ;
	}
}
