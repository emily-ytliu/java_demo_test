package com.example.java_demo_test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_demo_test.entity.PersonInfo;
import com.example.java_demo_test.service.ifs.PersonInfoService;
import com.example.java_demo_test.vo.OrderRequest;
import com.example.java_demo_test.vo.OrderResponse;
import com.example.java_demo_test.vo.AddPersonInfoRequest;
import com.example.java_demo_test.vo.GetPersonInfoRequest;
import com.example.java_demo_test.vo.GetPersonInfoResponse;
import com.example.java_demo_test.vo.PersonInfoResponse;

@RestController
public class PersonInfoController {

	@Autowired
	public PersonInfoService personInfoService;
	
	@PostMapping(value = "add_person_info")  //�s���~��
	public PersonInfoResponse addPersonInfo(@RequestBody AddPersonInfoRequest request) {  //@RequestBody�NPostMan��J��value�a�J
		return personInfoService.addPersonInfo(request.getPersonInfoList());  //�����I�sPersonInfoService�A�ҥH�W���n@Autowired PersonInfoService
	}
	
	@PostMapping(value = "person_info_by_id")  //�s���~��
	public GetPersonInfoResponse getPersonInfoById(@RequestBody GetPersonInfoRequest request) {  
		return personInfoService.getPersonInfoById(request.getId());  
	}
	
//	@PostMapping(value = "get_person_info_by_id1")  //�s���~��
//	public GetPersonInfoResponse getPersonInfoById1(@RequestBody GetPersonInfoRequest request) {  
//		return personInfoService.getPersonInfoById(request);  
//	}
	
	@PostMapping(value = "all_person_info")  //�s���~��
	public GetPersonInfoResponse getAllPersonInfo() {  
		return personInfoService.getAllPersonInfo();  
	}
	
	@PostMapping(value = "age_greater")  //�s���~��
	public GetPersonInfoResponse getPersonInfoByAgeGreaterThan(@RequestBody GetPersonInfoRequest request) {  
		return personInfoService.getPersonInfoByAgeGreaterThan(request.getAge());  
	}
	
	@PostMapping(value = "age_less")  //�s���~��
	public GetPersonInfoResponse getPersonInfoByAgeLessThanEqual(@RequestBody GetPersonInfoRequest request) {  
		return personInfoService.getPersonInfoByAgeLessThanEqual(request.getAge());  
	}
	
	@PostMapping(value = "age_less_or_greater")  //�s���~��
	public GetPersonInfoResponse getPersonInfoByAgeLessThanOrAgeGreaterThan(@RequestBody GetPersonInfoRequest request) {  
		return personInfoService.getPersonInfoByAgeLessThanOrAgeGreaterThan(request.getAge(), request.getAge2());  
	}
	
	@PostMapping(value = "age_between")  //�s���~��
	public GetPersonInfoResponse getPersonInfoByAgeBetween(@RequestBody GetPersonInfoRequest request) {  
		return personInfoService.getPersonInfoByAgeBetween(request.getAge(), request.getAge2());  
	}
	
	@PostMapping(value = "city_has_word")  //�s���~��
	public GetPersonInfoResponse getPersonInfoContaining(@RequestBody GetPersonInfoRequest request) {  
		return personInfoService.getPersonInfoContaining(request.getCity());  
	}
	
	@PostMapping(value = "age_greater_and_city_has_word")  //�s���~��
	public GetPersonInfoResponse getPersonInfoByAgeAndCityContaining(@RequestBody GetPersonInfoRequest request) {  
		return personInfoService.getPersonInfoByAgeAndCityContaining(request.getAge(), request.getCity());  
	}
}
