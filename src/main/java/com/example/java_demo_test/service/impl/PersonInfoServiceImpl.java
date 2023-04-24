package com.example.java_demo_test.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.java_demo_test.entity.PersonInfo;
import com.example.java_demo_test.repository.PersonInfoDao;
import com.example.java_demo_test.service.ifs.PersonInfoService;
import com.example.java_demo_test.vo.GetPersonInfoRequest;
import com.example.java_demo_test.vo.GetPersonInfoResponse;
import com.example.java_demo_test.vo.PersonInfoResponse;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {

	@Autowired
	public PersonInfoDao personInfoDao;
	
	//新增個人資訊
	@Override
	public PersonInfoResponse addPersonInfo(List<PersonInfo> personInfoList) {
		//參數進來第一件事，要檢查做防呆
		//防呆:檢查參數
		//檢查1: List不能是null或空 //CollectionUtils.isEmpty()  //如果是null，會報錯NullPointerException
		if (CollectionUtils.isEmpty(personInfoList)) {  //如果List是null或空陣列
			return new PersonInfoResponse("新增資訊錯誤");
		}
		//檢查2: List中的每項資訊(PersonInfo): id、name、city、age  //用foreach遍歷List的每個項目
		List<String> idList = new ArrayList<>();
		for (PersonInfo item : personInfoList) {
			//id、name、city: 不能是null、空字串、全空白  //StringUtils.hasText()
			//age: 不能是負數
			//用||(而不是&&)原因: 四個判斷只要有一個符合就會回傳錯誤
			if (!StringUtils.hasText(item.getId())
					|| !StringUtils.hasText(item.getName())
					|| item.getAge() < 0
					|| !StringUtils.hasText(item.getCity())) {
				return new PersonInfoResponse("新增資訊錯誤");
			}
			//蒐集id
			idList.add(item.getId());  //包含已經存在和本來不存在的id
		}
		//確認: 要新增的idList是否已經存在資料庫(不存在的才能新增)
		List<PersonInfo> result = personInfoDao.findAllById(idList);  //篩選出資料庫已經存在的id
		//如果資料庫已經存在所有要新增資訊的id
		if (result.size() == personInfoList.size()) {
			return new PersonInfoResponse("所有新增資訊都已存在");
		}
		//如果資料庫已經存在此id
		if (result.size() > 0) {  //如果有長度result.size() > 0 表示資料庫已經有資料，所以此id已經存在於資料庫 
								  //如果長度result.size() == 0，表示資料庫還沒有資料，所以此id不存在於資料庫
			
			//剔除掉idList中資料庫已經存在的id，只保留資料庫還不存在的id
			List<String> resIds = new ArrayList<>();
			for (PersonInfo item : result) {
				resIds.add(item.getId());  //把已經存在於資料庫的id，加入到resIds這個List中
			}
			//一般寫法
			List<PersonInfo> saveList = new ArrayList<>();
			for (PersonInfo item : personInfoList) {
				if (!resIds.contains(item.getId())) {
					saveList.add(item);  //要新增到資料庫的id，如果資料庫還不存在，就加入到saveList這個List中
				}
			}
			//補充: Lambda寫法
//			List<PersonInfo> saveList2 = personInfoList.stream()
//					.filter(item -> !resIds.contains(item.getId()))
//					.collect(Collectors.toList());
			personInfoDao.saveAll(saveList);
			return new PersonInfoResponse(saveList, "已存在的id未新增，其他新增資訊成功");
		}
		//如果檢查都沒有問題，(也都沒有資料庫已經存在的id)，存入資料庫
		personInfoDao.saveAll(personInfoList);
		return new PersonInfoResponse(personInfoList, "新增資訊成功");
	}

	//用id取得對應的個人資訊
	@Override
	public GetPersonInfoResponse getPersonInfoById(String id) {
		//檢查: 不能是null、不能是空字串、不能是全空白
		if (!StringUtils.hasText(id)) {
			return new GetPersonInfoResponse("id資訊錯誤");
		}
		//從資料庫篩選出有此id的資訊
		Optional<PersonInfo> op = personInfoDao.findById(id);  //Jpa內建findById()要用Optional接
		//檢查 此id是否存在於資料庫
		if (!op.isPresent()) {
			return new GetPersonInfoResponse("id不存在");
		}
		return new GetPersonInfoResponse(op.get(), "取得此id對應資訊成功");
	}
//	@Override
//	public GetPersonInfoResponse getPersonInfoById(GetPersonInfoRequest request) {
//		String id = request.getId();
//		//檢查: 不能是null、不能是空字串、不能是全空白
//		if (!StringUtils.hasText(id)) {
//			return new GetPersonInfoResponse("id資訊錯誤");
//		}
//		//從資料庫篩選出有此id的資訊
//		Optional<PersonInfo> op = personInfoDao.findById(id);  //Jpa內建findById()要用Optional接
//		//檢查 此id是否存在於資料庫
//		if (!op.isPresent()) {
//			return new GetPersonInfoResponse("id不存在");
//		}
//		return new GetPersonInfoResponse(op.get(), "取得此id對應資訊成功");
//	}
	
	//取得所有個人資訊
	@Override
	public GetPersonInfoResponse getAllPersonInfo() {
		return new GetPersonInfoResponse(personInfoDao.findAll(), "取得所有資訊成功");
	}

	//取得年紀大於輸入條件的所有個人資訊
	@Override
	public GetPersonInfoResponse getPersonInfoByAgeGreaterThan(int age) {
		//寫法一 一般寫法
//		if (age < 0) {
//			return new GetPersonInfoResponse("年齡不能是負數");
//		}
//		List<PersonInfo> ageGreaterList = new ArrayList<>();
//		List<PersonInfo> all = personInfoDao.findAll();
//		for (PersonInfo item : all) {
//			if (item.getAge() <= age) {
//				continue;
//			}
//			ageGreaterList.add(item);
//		}
//		if (ageGreaterList.isEmpty()) {
//			return new GetPersonInfoResponse("無符合條件的資訊");
//		}
//		return new GetPersonInfoResponse(all, "取得資訊成功");
		
		//寫法二 Dao新增Jpa語法: findByAgeGreaterThan
		if (age < 0) {
			return new GetPersonInfoResponse("年齡不能是負數");
		}
		List<PersonInfo> ageGreaterList = personInfoDao.findByAgeGreaterThan(age);
		if (ageGreaterList.isEmpty()) {
			return new GetPersonInfoResponse("無符合條件的資訊");
		}
		return new GetPersonInfoResponse(ageGreaterList, "取得資訊成功");
		
	}

	//取得年紀小於等於輸入條件的所有個人資訊，由小到大排序
	//Dao新增Jpa語法:
	@Override
	public GetPersonInfoResponse getPersonInfoByAgeLessThanEqual(int age) {
		if (age < 0) {
			return new GetPersonInfoResponse("年齡不能是負數");
		}
		List<PersonInfo> ageLessList = personInfoDao.findByAgeLessThanEqualOrderByAgeAsc(age);
		if (ageLessList.isEmpty()) {
			return new GetPersonInfoResponse("無符合條件的資訊");
		}
		return new GetPersonInfoResponse(ageLessList, "取得資訊成功");
	}
	
	//不連續區間: 取得小於條件1 和 大於條件2的個人資訊
	@Override
	public GetPersonInfoResponse getPersonInfoByAgeLessThanOrAgeGreaterThan(int age1, int age2) {
		if (age1 < 0 || age2 < 0) {
			return new GetPersonInfoResponse("年齡不能是負數");
		}
		List<PersonInfo> ageLessOrGreaterList = personInfoDao.findByAgeLessThanOrAgeGreaterThan(age1, age2);
		if (ageLessOrGreaterList.isEmpty()) {
			return new GetPersonInfoResponse("無符合條件的資訊");
		}
		return new GetPersonInfoResponse(ageLessOrGreaterList, "取得資訊成功");
	}

	//找到年紀介於2個數字(有包含)之間的資訊，由大到小排序，只取前三筆的資料
	//Dao新增Jpa語法:
	@Override
	public GetPersonInfoResponse getPersonInfoByAgeBetween(int age1, int age2) {
		if (age1 < 0 || age2 < 0) {
			return new GetPersonInfoResponse("年齡不能是負數");
		}
		List<PersonInfo> ageBetweenList = personInfoDao.findTop3ByAgeBetweenOrderByAgeDesc(age1, age2);
		if (ageBetweenList.isEmpty()) {
			return new GetPersonInfoResponse("無符合條件的資訊");
		}
		return new GetPersonInfoResponse(ageBetweenList, "取得資訊成功");
	}

	//取得city包含某個特定字的所有個人資訊
	//Dao新增Jpa語法:
	@Override
	public GetPersonInfoResponse getPersonInfoContaining(String city) {
		if (!StringUtils.hasText(city)) {
			return new GetPersonInfoResponse("資訊錯誤");
		}
		List<PersonInfo> hasWordList = personInfoDao.findByCityContaining(city);
		if (hasWordList.isEmpty()) {
			return new GetPersonInfoResponse("無此特定字");
		}
		return new GetPersonInfoResponse(hasWordList, "取得資訊成功");
	}

	//取得年紀大於輸入條件 以及 city包含某個特定字的所有個人資訊
	//Dao新增Jpa語法:
	@Override
	public GetPersonInfoResponse getPersonInfoByAgeAndCityContaining(int age, String city) {
		if (age < 0) {
			return new GetPersonInfoResponse("年齡不能是負數");
		}
		if (!StringUtils.hasText(city)) {
			return new GetPersonInfoResponse("資訊錯誤");
		}
		List<PersonInfo> ageAndWordList = personInfoDao.findByAgeGreaterThanAndCityContainingOrderByAgeDesc(age, city);
		if (ageAndWordList.isEmpty()) {
			return new GetPersonInfoResponse("無符合條件的資訊");
		}
		return new GetPersonInfoResponse(ageAndWordList, "取得資訊成功");
	}

	

}
