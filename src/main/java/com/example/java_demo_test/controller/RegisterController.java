package com.example.java_demo_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@PostMapping(value = "register")
	public RegisterResponse register(@RequestBody RegisterRequest request) {
		return registerService.register(request.getAccount(), request.getPwd());
	}
	
	@PostMapping(value = "active")
	public RegisterResponse active(@RequestBody RegisterRequest request) {
		return registerService.active(request.getAccount(), request.getPwd());
	}
	
	@PostMapping(value = "login")
	public RegisterResponse login(@RequestBody RegisterRequest request) {
		return registerService.login(request.getAccount(), request.getPwd());
	}
	
	@PostMapping(value = "get_reg_time")
	public RegisterResponse getRegTime(@RequestBody RegisterRequest request) {
		return registerService.getRegTime(request.getAccount(), request.getPwd());
	}
}
