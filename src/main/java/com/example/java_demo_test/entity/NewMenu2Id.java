package com.example.java_demo_test.entity;

import java.io.Serializable;

/*
 * 放複合id屬性用的class NewMenu2Id:
 * Step1: @Entity class NewMenu2 有下@Id的屬性(private String category; & private String item;)複製貼上
 * Step2: 生成Getter/Setter、帶參數的建構方法、空的建構方法
 * Step3: implements Serializable(實作序列化)
 * (Step4: 可做可不做，NewMenu2Id有黃蚯蚓，修正方式用哪個都可)
 */

public class NewMenu2Id implements Serializable{

	private static final long serialVersionUID = 1L;

	private String category;
	
	private String item;

	public NewMenu2Id() {
		
	}

	public NewMenu2Id(String category, String item) {
		this.category = category;
		this.item = item;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
	
}
