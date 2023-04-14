package com.example.java_demo_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java_demo_test.entity.Bank;

@Repository  //�ƾڳX�ݼh: Data Access Object(��Ʀs������) �� ��Ʀs���w
			 //�s��@Entity
public interface BankDao extends JpaRepository<Bank, String>{  //BanDao �O��Bank��Data Access Object(��Ʀs������)
											// <T, ID> T�O@Entity��class�W�� ID�OMySQL��PK�D�n����ƫ��A
	public Bank findByAccountAndPwd(String account, String pwd);
	
}
