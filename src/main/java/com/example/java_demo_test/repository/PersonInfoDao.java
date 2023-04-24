package com.example.java_demo_test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java_demo_test.entity.PersonInfo;

@Repository
public interface PersonInfoDao extends JpaRepository<PersonInfo, String>{
	//因為條件不是PK，所以會回傳多筆(一整個欄位的資料)，所以要用List接回去:
	//只有有下@Id的屬性是PK，寫findByid或findByItem(java屬性名稱)，會是回傳單筆
	//findBy後面要接java寫的屬性名稱

	public List<PersonInfo> findByAgeGreaterThan(int age);  
	
	public List<PersonInfo> findByAgeLessThanEqualOrderByAgeAsc(int age);  //Asc是小到大 ascending升冪/遞增
	
	public List<PersonInfo> findByAgeLessThanOrAgeGreaterThan(int age1, int age2);
	//不連續區間: 用Or連接，1個欄位Age，但有2個輸入條件
	
	public List<PersonInfo> findTop3ByAgeBetweenOrderByAgeDesc(int age1, int age2);  //Desc是大到小 descending降冪/遞減
	//連續區間: 用Between，1個欄位Age，但有2個輸入條件  //Between有包含輸入條件本身
	//First或Top都可 //要寫在find後面
	
	public List<PersonInfo> findByCityContaining(String city);
	//Containing 等於是SQL語法的 e.g. '%台%' 文字中只要有台都有
	
	public List<PersonInfo> findByAgeGreaterThanAndCityContainingOrderByAgeDesc(int age, String city); //2個欄位用And連接
	

}

