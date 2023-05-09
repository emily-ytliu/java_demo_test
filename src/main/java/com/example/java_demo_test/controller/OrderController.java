package com.example.java_demo_test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_demo_test.entity.Menu;
import com.example.java_demo_test.service.ifs.OrderService;
import com.example.java_demo_test.vo.GetMenuResponse;
import com.example.java_demo_test.vo.OrderRequest;
import com.example.java_demo_test.vo.OrderResponse;

import io.swagger.v3.oas.annotations.Hidden;

//import springfox.documentation.annotations.ApiIgnore;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	

	@PostMapping(value = "get_menu_by_name")  //�s���~��
	public GetMenuResponse getMenuByName(@RequestBody OrderRequest request) {  //@RequestBody�NPostMan��J��value�a�J
		return orderService.getMenuByName(request.getName());
	}
	
	@GetMapping(value = "all_menus")  //�s���~��
	public OrderResponse getAllMenus() {  //@RequestBody�NPostMan��J��value�a�J
		return orderService.getAllMenus();
	}
	
//	@ApiIgnore  //springfox-boot-starter: �[�Wat ApiIgnore �|�� swagger ����ܦ�API 
	@Hidden  //springdoc-openapi: �[�Wat Hidden �|�� swagger ����ܦ�API 
	@PostMapping(value = "add_menus")  //�s���~��
	public OrderResponse addMenus(@RequestBody OrderRequest request) {  //@RequestBody�NPostMan��J��value�a�J
		return orderService.addMenus(request.getMenus());
	}
	
	@PostMapping(value = "order")  //�s���~��
	public OrderResponse order(@RequestBody OrderRequest request) {  //@RequestBody�NPostMan��J��value�a�J
		return orderService.order(request.getOrderMap());
	}
	
	@PostMapping(value = "update_menu_price")  //�s���~��
	public OrderResponse updateMenuPrice(@RequestBody OrderRequest request) {  //@RequestBody�NPostMan��J��value�a�J
		return orderService.updateMenuPrice(request.getMenus());
	}
	
}
