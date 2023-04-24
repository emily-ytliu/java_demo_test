package com.example.java_demo_test.service.ifs;

import java.util.List;

import com.example.java_demo_test.entity.PersonInfo;
import com.example.java_demo_test.vo.GetPersonInfoRequest;
import com.example.java_demo_test.vo.GetPersonInfoResponse;
import com.example.java_demo_test.vo.PersonInfoResponse;

public interface PersonInfoService {

//	public void addPersonInfo(PersonInfo personInfo);  //只能有1個PersonInfo有1筆資料(有4個屬性/4個欄位)
	//void沒有回傳值 如果要回傳要改掉
	
	//新增個人資訊
	public PersonInfoResponse addPersonInfo(List<PersonInfo> personInfoList);  //可以有一筆或多筆資料
	
	//用id取得對應的個人資訊
	public GetPersonInfoResponse getPersonInfoById(String id);
	
//	public GetPersonInfoResponse getPersonInfoById(GetPersonInfoRequest request);
	
	//取得所有個人資訊
	public GetPersonInfoResponse getAllPersonInfo();
	
	//取得年紀大於輸入條件的所有個人資訊
	public GetPersonInfoResponse getPersonInfoByAgeGreaterThan(int age);
	
	//取得年紀小於等於輸入條件的所有個人資訊，由小到大排序
	public GetPersonInfoResponse getPersonInfoByAgeLessThanEqual(int age);
	
	//不連續區間: 取得小於條件1 和 大於條件2的個人資訊
	public GetPersonInfoResponse getPersonInfoByAgeLessThanOrAgeGreaterThan(int age1, int age2);
	
	//連續區間: 取得年紀介於2個數字(有包含)之間的資訊，由大到小排序，只取前三筆的資料
	public GetPersonInfoResponse getPersonInfoByAgeBetween(int age1, int age2);
	
	//取得city包含某個特定字的所有個人資訊
	public GetPersonInfoResponse getPersonInfoContaining(String city);
	
	//取得年紀大於輸入條件 以及 city 包含某個特定字的所有個人資訊，由大到小排序
	public GetPersonInfoResponse getPersonInfoByAgeAndCityContaining(int age, String city);
}
