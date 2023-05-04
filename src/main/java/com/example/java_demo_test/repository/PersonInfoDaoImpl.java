package com.example.java_demo_test.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.java_demo_test.entity.PersonInfo;

public class PersonInfoDaoImpl extends BaseDao {

	//實作PersonInfoDao的方法
	public List<PersonInfo> doQueryByAge(int age) {
		StringBuffer sb = new StringBuffer();  //StringBuffer有暫存空間
		sb.append("SELECT p FROM PersonInfo p "
				+ "WHERE p.age >= :inputAge");  //因為BaseDao是用createQuery，(不是原生SQL，createNativeQuery才是原生)，所以不能用SELECT * 
		Map<String, Object> params = new HashMap<>();
		params.put("inputAge", age);
		return doQuery(sb.toString(), params, PersonInfo.class);
	}	
	
	public List<PersonInfo> doQueryByAge(int age, int limitSize) {
		StringBuffer sb = new StringBuffer();  //StringBuffer有暫存空間
		sb.append("SELECT p FROM PersonInfo p "
				+ "WHERE p.age >= :inputAge");  //因為BaseDao是用createQuery，(不是原生SQL，createNativeQuery才是原生)，所以不能用SELECT * 
		Map<String, Object> params = new HashMap<>();
		params.put("inputAge", age);
		return doQuery(sb.toString(), params, PersonInfo.class, limitSize);
	}
	
	public List<PersonInfo> doQueryByAge(int age, int limitSize, int startPosition) {
		StringBuffer sb = new StringBuffer();  //StringBuffer有暫存空間
		sb.append("SELECT p FROM PersonInfo p "
				+ "WHERE p.age >= :inputAge");  //因為BaseDao是用createQuery，(不是原生SQL，createNativeQuery才是原生)，所以不能用SELECT * 
		Map<String, Object> params = new HashMap<>();
		params.put("inputAge", age);
		return doQuery(sb.toString(), params, PersonInfo.class, limitSize, startPosition);
	}
	
	
	//更新
	public int doUpdateAgeByName(int age, String name) {
		StringBuffer sb = new StringBuffer();  //StringBuffer有暫存空間
		sb.append("UPDATE PersonInfo SET age = :inputAge "
				+ "WHERE name = :inputName");
		Map<String, Object> params = new HashMap<>();
		params.put("inputAge", age);
		params.put("inputName", name);
		return doUpdate(sb.toString(), params);
	}
}
