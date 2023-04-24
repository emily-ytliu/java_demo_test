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

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	

	@PostMapping(value = "get_menu_by_name")  //連結外部
	public GetMenuResponse getMenuByName(@RequestBody OrderRequest request) {  //@RequestBody將PostMan輸入的value帶入
		return orderService.getMenuByName(request.getName());
	}
	
	@GetMapping(value = "all_menus")  //連結外部
	public OrderResponse getAllMenus() {  //@RequestBody將PostMan輸入的value帶入
		return orderService.getAllMenus();
	}
	
	@PostMapping(value = "add_menus")  //連結外部
	public OrderResponse addMenus(@RequestBody OrderRequest request) {  //@RequestBody將PostMan輸入的value帶入
		return orderService.addMenus(request.getMenus());
	}
	
	@PostMapping(value = "order")  //連結外部
	public OrderResponse order(@RequestBody OrderRequest request) {  //@RequestBody將PostMan輸入的value帶入
		return orderService.order(request.getOrderMap());
	}
	
	@PostMapping(value = "update_menu_price")  //連結外部
	public OrderResponse updateMenuPrice(@RequestBody OrderRequest request) {  //@RequestBody將PostMan輸入的value帶入
		return orderService.updateMenuPrice(request.getMenus());
	}
	
}
