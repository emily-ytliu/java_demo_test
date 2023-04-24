package com.example.java_demo_test;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.java_demo_test.entity.NewMenu;
import com.example.java_demo_test.entity.NewMenu2;
import com.example.java_demo_test.repository.NewMenu2Dao;

@SpringBootTest(classes = JavaDemoTestApplication.class)
public class NewMenu2Test {

	@Autowired
	NewMenu2Dao newMenu2Dao;
	
	@Test
	public void addDataTest() {
		NewMenu2 nm1 = new NewMenu2("beef", "¬õ¿N", 120);
		newMenu2Dao.saveAll(Arrays.asList(nm1));
	}
}
