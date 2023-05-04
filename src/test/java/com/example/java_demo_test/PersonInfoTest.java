package com.example.java_demo_test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.java_demo_test.entity.PersonInfo;
import com.example.java_demo_test.repository.PersonInfoDao;
import com.example.java_demo_test.service.ifs.PersonInfoService;

@SpringBootTest(classes = JavaDemoTestApplication.class)
public class PersonInfoTest {

	@Autowired
	private PersonInfoDao personInfoDao;
	
	@Autowired
	private PersonInfoService personInfoService;
	
	@Test
	public void updateNameByIdTest() {
//		int result = personInfoDao.updateNameById("A02", "更新", 20, "台北市");
		int result = personInfoDao.updateNameById("A02", "更新");
		System.out.println(result);
	}
	
	@Test
	public void doQueryByAgeTest() {
		List<PersonInfo> res = personInfoDao.doQueryByAge(30);
		System.out.println(res.size());
	}
	
	@Test
	public void doUpdateAgeByNameTest() {
		int res = personInfoDao.doUpdateAgeByName(12, "第一人");
		System.out.println(res);
	}
}
