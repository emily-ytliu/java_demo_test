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

	@Autowired  //�s���� @Repository(�ƾڳX�ݼh)-BankDao
	private OrderDao orderDao;
	
	@Autowired
	private OrderService orderService;
	
	@Test
	public void addMenusTest() {
		List<Menu> menus = new ArrayList<>();
		menus.add(new Menu("��", 200));
		menus.add(new Menu("��", 100));
		menus.add(new Menu("��", 150));
		OrderResponse orderResponse = orderService.addMenus(menus);
		System.out.println(orderResponse.getMessage());
		
		List<Menu> res = orderResponse.getMenus();
		Assert.isTrue(res != null, "�s�W�\�I���~!!");
	}
	
	@Test
	public void orderTest() {
		Map<String, Integer> order = new HashMap<>();
		order.put("��", 5);
		order.put("��", 3);
		order.put("��", 6);
		OrderResponse orderResponse = orderService.order(order);
		System.out.println(orderResponse.getMessage());
	}
	
	@Test
	//�ɥRforEach
	public void forEachTest() {
		List<String> ids = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));
		//�@��g�k
		for (String str : ids) {
			System.out.println(str);
		}
		//Lambda�g�k1
		System.out.println("==========================");
		ids.forEach(str -> System.out.println(str));
		//Lambda�g�k2
		System.out.println("==========================");
		ids.forEach(System.out::println);
		
		
	}
}
