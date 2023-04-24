package com.example.java_demo_test.vo;

import java.util.List;
import java.util.Map;

import com.example.java_demo_test.entity.Menu;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderRequest {
	
	@JsonProperty("menu_list")  //若加上atJsonProperty，外部(Postman)會改成從括號內的名字進入  //括號內是Postman輸入的key值
	private List<Menu> menus;			//若沒加atJsonProperty，預設是用原本屬性menus當作Postman的key值進入
	
	@JsonProperty("each_menu")
	private String name;
	
	@JsonProperty("order")
	private Map<String, Integer> orderMap;
	
	public Map<String, Integer> getOrderMap() {
		return orderMap;
	}

	public void setOrderMap(Map<String, Integer> orderMap) {
		this.orderMap = orderMap;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
	
}
