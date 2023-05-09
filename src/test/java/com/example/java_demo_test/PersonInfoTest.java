package com.example.java_demo_test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

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
//		int result = personInfoDao.updateNameById("A02", "穝", 20, "カ");
		int result = personInfoDao.updateNameById("A02", "穝");
		System.out.println(result);
	}
	
	@Test
	public void doQueryByAgeTest() {
		List<PersonInfo> res = personInfoDao.doQueryByAge(30);
		System.out.println(res.size());
	}
	
	@Test
	public void doUpdateAgeByNameTest() {
		int res = personInfoDao.doUpdateAgeByName(12, "材");
		System.out.println(res);
	}
	
	//===笆篈把计琩高(like + 家絢琩高 + or)==========================
	/*
	 * ~All
	 * ㄢ把计常⊿""(SQLボ'%%')穓碝挡狦琌 场 戈
	 * ㄤいΤ穓碝挡狦琌 场 戈 
	 * △ 璝璶ㄤいΤ才兵ン挡狦
	 * ㄢ常Τ穓碝挡狦
	 */
	
	/*
	 * ~None
	 * ㄢ把计常⊿"null"穓碝挡狦琌 ⊿ 戈
	 * ㄤいΤ才兵ン挡狦
	 * ㄢ常Τ穓碝挡狦
	 */
	
	//===JPA==================================================
	@Test
	public void searchNameOrCityJpaAllTest() {
		String inputName = "";
		String inputCity = "蔼";
		String name = StringUtils.hasText(inputName) ? inputName : "";
		String city = StringUtils.hasText(inputCity) ? inputCity : "";
		List<PersonInfo> result = personInfoDao.findByNameContainingOrCityContaining(name, city);
		System.out.println(result.size());
	}
	
	@Test
	public void searchNameOrCityJpaNoneTest() {
		String inputName = "";
		String inputCity = "";
		String name = StringUtils.hasText(inputName) ? inputName : null;
		String city = StringUtils.hasText(inputCity) ? inputCity : null;
		List<PersonInfo> result = personInfoDao.findByNameContainingOrCityContaining(name, city);
		System.out.println(result.size());
	}
	
	//===at Query==================================================
	@Test
	public void searchNameOrCityQueryAllTest() {
		String inputName = "";
		String inputCity = "";
		String name = StringUtils.hasText(inputName) ? inputName : "";
		String city = StringUtils.hasText(inputCity) ? inputCity : "";
		List<PersonInfo> result = personInfoDao.searchNameOrCityQuery(name, city);
		System.out.println(result.size());
	}
	
	@Test
	public void searchNameOrCityQueryNoneTest() {
		String inputName = "";
		String inputCity = "";
		String name = StringUtils.hasText(inputName) ? inputName : null;
		String city = StringUtils.hasText(inputCity) ? inputCity : null;
		List<PersonInfo> result = personInfoDao.searchNameOrCityQuery(name, city);
		System.out.println(result.size());
	}
	
	//===REGEXP==================================================
	@Test
	public void searchNameOrCityRegexpAllTest() {
		String inputName = "";
		String inputCity = "";
		List<String> resNamelist = Arrays.asList(inputName.split(" "));
		List<String> resCitylist = Arrays.asList(inputCity.split(" "));
		
		inputName = String.join("|", resNamelist);
		inputCity = String.join("|", resCitylist);
		
		//△ 璝璶ㄤいΤ才兵ン挡狦: 
		boolean hasText = !StringUtils.hasText(inputName) && !StringUtils.hasText(inputCity);
		String name = hasText ? "." : StringUtils.hasText(inputName) ? inputName : null;
		String city = hasText ? "." : StringUtils.hasText(inputCity) ? inputCity : null;
		
		//ㄤいΤ穓碝挡狦琌 场 戈
//		String name = StringUtils.hasText(inputName) ? inputName : ".";  //糶"."┪"|"常穓碝场戈
//		String city = StringUtils.hasText(inputCity) ? inputCity : ".";
		
		List<PersonInfo> result = personInfoDao.searchNameOrCityRegexp(name, city);
		System.out.println(result.size());
	}
	
	@Test
	public void searchNameOrCityRegexpNoneTest() {
		String inputName = "";
		String inputCity = "";
		List<String> resNamelist = Arrays.asList(inputName.split(" "));
		List<String> resCitylist = Arrays.asList(inputCity.split(" "));
		
		inputName = String.join("|", resNamelist);
		inputCity = String.join("|", resCitylist);
		
		String name = StringUtils.hasText(inputName) ? inputName : null;
		String city = StringUtils.hasText(inputCity) ? inputCity : null;
		List<PersonInfo> result = personInfoDao.searchNameOrCityRegexp(name, city);
		System.out.println(result.size());
	}
}
