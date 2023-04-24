package com.example.java_demo_test.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "new_menu")
public class NewMenu {

	//SB02_ppt 26.27
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //因為MySQL有勾選AI(Auto Incremental)自動增長，所以要加上這行
	@Column(name = "seq")
	private int seq;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "item")
	private String item;
	
	@Column(name = "price")
	private int price;
	
	//SB02_ppt 28
	@Type(type = "uuid-char")  //Type要import hibernate.annotations
	@Column(name = "uuid")
	private UUID uuid = UUID.randomUUID();  //UUID.randomUUID()是預設值
	
	public NewMenu() {
		
	}
	
	public NewMenu(int seq, String category, String item, int price) {
		this.seq = seq;
		this.category = category;
		this.item = item;
		this.price = price;
	}

	public NewMenu(String category, String item, int price) {
		this.category = category;
		this.item = item;
		this.price = price;
	}

	public NewMenu(String category, String item, int price, UUID uuid) {
		this.category = category;
		this.item = item;
		this.price = price;
		this.uuid = uuid;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	
	
}
