package com.example.java_demo_test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.java_demo_test.entity.Menu;
import com.example.java_demo_test.repository.OrderDao;
import com.example.java_demo_test.service.ifs.OrderService;
import com.example.java_demo_test.vo.OrderResponse;

@SpringBootTest(classes = JavaDemoTestApplication.class)
public class OrderTest {

	@Autowired  //連結到 @Repository(數據訪問層)-BankDao
	private OrderDao orderDao;
	
	@Autowired
	private OrderService orderService;
	
	@Test
	public void addMenusTest() {
		List<Menu> menus = new ArrayList<>();
		menus.add(new Menu("牛", 200));
		menus.add(new Menu("豬", 100));
		menus.add(new Menu("雞", 150));
		OrderResponse orderResponse = orderService.addMenus(menus);
		System.out.println(orderResponse.getMessage());
		
		List<Menu> res = orderResponse.getMenus();
		Assert.isTrue(res != null, "新增餐點錯誤!!");
	}
	
	@Test
	public void orderTest() {
		Map<String, Integer> order = new HashMap<>();
		order.put("牛", 5);
		order.put("豬", 3);
		order.put("雞", 6);
		OrderResponse orderResponse = orderService.order(order);
		System.out.println(orderResponse.getMessage());
	}
	
	@Test
	//補充forEach
	public void forEachTest() {
		List<String> ids = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));
		//一般寫法
		for (String str : ids) {
			System.out.println(str);
		}
		//Lambda寫法1
		System.out.println("==========================");
		ids.forEach(str -> System.out.println(str));
		//Lambda寫法2
		System.out.println("==========================");
		ids.forEach(System.out::println);
		
		
	}
}
