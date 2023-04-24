package com.example.java_demo_test.vo;

import java.util.List;
import java.util.Map;

import com.example.java_demo_test.entity.Menu;

public class OrderResponse {

	private List<Menu> menus;  //返回新增的餐點名稱和價格(單筆或多筆都可以)
							   //如果是寫 private Menu menu 只會返回單筆而已
	private String message;
	
	private Map<String, Integer> orderMap;  //Map<key: 餐點名稱, value: 數量>
	
	private int discountTotal;
	
	public OrderResponse() {
		
	}
	
	public OrderResponse(String message) {
		this.message = message;
	}

	public OrderResponse(List<Menu> menus, String message) {
		this.menus = menus;
		this.message = message;
	}
	
	public OrderResponse(String message, Map<String, Integer> orderMap, int discountTotal) {
		super();
		this.message = message;
		this.orderMap = orderMap;
		this.discountTotal = discountTotal;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Integer> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<String, Integer> orderMap) {
		this.orderMap = orderMap;
	}

	public int getDiscountTotal() {
		return discountTotal;
	}

	public void setDiscountTotal(int discountTotal) {
		this.discountTotal = discountTotal;
	}

	

	
	
	

	
	
	
	
}
