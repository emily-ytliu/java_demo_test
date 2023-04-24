package com.example.java_demo_test;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.java_demo_test.entity.NewMenu;
import com.example.java_demo_test.repository.NewMenuDao;

@SpringBootTest(classes = JavaDemoTestApplication.class)
public class NewMenuTest {

	@Autowired
	private NewMenuDao newMenuDao;
	
	@Test
	public void addDataTest() {
		NewMenu nm1 = new NewMenu(1, "beef", "¬õ¿N", 120);
		newMenuDao.saveAll(Arrays.asList(nm1));
	}
}
