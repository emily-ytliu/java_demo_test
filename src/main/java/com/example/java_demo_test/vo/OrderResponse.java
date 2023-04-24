package com.example.java_demo_test.vo;

import java.util.List;
import java.util.Map;

import com.example.java_demo_test.entity.Menu;

public class OrderResponse {

	private List<Menu> menus;  //��^�s�W���\�I�W�٩M����(�浧�Φh�����i�H)
							   //�p�G�O�g private Menu menu �u�|��^�浧�Ӥw
	private String message;
	
	private Map<String, Integer> orderMap;  //Map<key: �\�I�W��, value: �ƶq>
	
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
