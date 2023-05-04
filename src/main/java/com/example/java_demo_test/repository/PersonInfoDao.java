package com.example.java_demo_test.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
	//===EntityManager=====================
	public List<PersonInfo> doQueryByAge(int age);
	
	public List<PersonInfo> doQueryByAge(int age, int limitSize);
	
	public List<PersonInfo> doQueryByAge(int age, int limitSize, int startPosition);
	
	//更新
	@Transactional  //EntityManager: SQL update要加上
	public int doUpdateAgeByName(int age, String name);
	
	//====JPQL=============================
	
	//@Query寫法: JPQL update要加上
//	@Transactional
//	@Modifying
	
//	@Transactional
//	@Modifying  //DML
//	@Query("update PersonInfo p set p.id = :newId, p.name = :newName, p.age = :newAge, p.city = :newCity"
//			+ " where p.id = :newId")  //要enter下一行，要記得空格再下一行，或者下一行後要空格!!!!!!
//	//update Entity名稱 (as)別名 set 別名.屬性名 = :自訂參數名
//	public int updateNameById(
//			@Param("newId") String inputId, 
//			@Param("newName") String inputName, 
//			@Param("newAge") int inputAge, 
//			@Param("newCity") String inputCity);
//	//update成功是1，失敗是0
	
	//只修改name，可以只寫name就好
	@Transactional
	@Modifying  //DML
	@Query("update PersonInfo p set p.id = :newId, p.name = :newName "
			+ "where p.id = :newId")  //要enter下一行，要記得先空格再下一行，或者下一行後要空格!!!!!!
	//update Entity名稱 (as)別名 set 別名.資料庫欄位名 = :自訂參數名
	public int updateNameById(
			@Param("newId") String inputId, 
			@Param("newName") String inputName);
	
	//可以省去兩個Dao的操作
	//insert語法，要加上nativeQuery = true (預設是false)
	//照片
	
}

