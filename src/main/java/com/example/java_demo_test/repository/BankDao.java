package com.example.java_demo_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java_demo_test.entity.Bank;

@Repository  //數據訪問層: Data Access Object(資料存取物件) → 資料存取庫
			 //連結@Entity
public interface BankDao extends JpaRepository<Bank, String>{  //BanDao 是指Bank的Data Access Object(資料存取物件)
											// <T, ID> T是@Entity的class名稱 ID是MySQL的PK主要的資料型態
	public Bank findByAccountAndPwd(String account, String pwd);
	
}
