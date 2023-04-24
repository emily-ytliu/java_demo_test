package com.example.java_demo_test.vo;

import java.util.List;
import java.util.Map;

import com.example.java_demo_test.entity.Menu;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderRequest {
	
	@JsonProperty("menu_list")  //�Y�[�WatJsonProperty�A�~��(Postman)�|�令�q�A�������W�r�i�J  //�A�����OPostman��J��key��
	private List<Menu> menus;			//�Y�S�[atJsonProperty�A�w�]�O�έ쥻�ݩ�menus��@Postman��key�ȶi�J
	
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
