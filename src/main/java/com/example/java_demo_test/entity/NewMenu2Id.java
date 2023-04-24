package com.example.java_demo_test.entity;

import java.io.Serializable;

/*
 * ��ƦXid�ݩʥΪ�class NewMenu2Id:
 * Step1: @Entity class NewMenu2 ���U@Id���ݩ�(private String category; & private String item;)�ƻs�K�W
 * Step2: �ͦ�Getter/Setter�B�a�Ѽƪ��غc��k�B�Ū��غc��k
 * Step3: implements Serializable(��@�ǦC��)
 * (Step4: �i���i�����ANewMenu2Id�����L�C�A�ץ��覡�έ��ӳ��i)
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
