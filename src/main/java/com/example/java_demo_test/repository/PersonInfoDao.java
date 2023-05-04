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
	//�]�����󤣬OPK�A�ҥH�|�^�Ǧh��(�@�����쪺���)�A�ҥH�n��List���^�h:
	//�u�����U@Id���ݩʬOPK�A�gfindByid��findByItem(java�ݩʦW��)�A�|�O�^�ǳ浧
	//findBy�᭱�n��java�g���ݩʦW��

	public List<PersonInfo> findByAgeGreaterThan(int age);  
	
	public List<PersonInfo> findByAgeLessThanEqualOrderByAgeAsc(int age);  //Asc�O�p��j ascending�ɾ�/���W
	
	public List<PersonInfo> findByAgeLessThanOrAgeGreaterThan(int age1, int age2);
	//���s��϶�: ��Or�s���A1�����Age�A����2�ӿ�J����
	
	public List<PersonInfo> findTop3ByAgeBetweenOrderByAgeDesc(int age1, int age2);  //Desc�O�j��p descending����/����
	//�s��϶�: ��Between�A1�����Age�A����2�ӿ�J����  //Between���]�t��J���󥻨�
	//First��Top���i //�n�g�bfind�᭱
	
	public List<PersonInfo> findByCityContaining(String city);
	//Containing ����OSQL�y�k�� e.g. '%�x%' ��r���u�n���x����
	
	public List<PersonInfo> findByAgeGreaterThanAndCityContainingOrderByAgeDesc(int age, String city); //2������And�s��
	
	//===EntityManager=====================
	public List<PersonInfo> doQueryByAge(int age);
	
	public List<PersonInfo> doQueryByAge(int age, int limitSize);
	
	public List<PersonInfo> doQueryByAge(int age, int limitSize, int startPosition);
	
	//��s
	@Transactional  //EntityManager: SQL update�n�[�W
	public int doUpdateAgeByName(int age, String name);
	
	//====JPQL=============================
	
	//@Query�g�k: JPQL update�n�[�W
//	@Transactional
//	@Modifying
	
//	@Transactional
//	@Modifying  //DML
//	@Query("update PersonInfo p set p.id = :newId, p.name = :newName, p.age = :newAge, p.city = :newCity"
//			+ " where p.id = :newId")  //�nenter�U�@��A�n�O�o�Ů�A�U�@��A�Ϊ̤U�@���n�Ů�!!!!!!
//	//update Entity�W�� (as)�O�W set �O�W.�ݩʦW = :�ۭq�ѼƦW
//	public int updateNameById(
//			@Param("newId") String inputId, 
//			@Param("newName") String inputName, 
//			@Param("newAge") int inputAge, 
//			@Param("newCity") String inputCity);
//	//update���\�O1�A���ѬO0
	
	//�u�ק�name�A�i�H�u�gname�N�n
	@Transactional
	@Modifying  //DML
	@Query("update PersonInfo p set p.id = :newId, p.name = :newName "
			+ "where p.id = :newId")  //�nenter�U�@��A�n�O�o���Ů�A�U�@��A�Ϊ̤U�@���n�Ů�!!!!!!
	//update Entity�W�� (as)�O�W set �O�W.��Ʈw���W = :�ۭq�ѼƦW
	public int updateNameById(
			@Param("newId") String inputId, 
			@Param("newName") String inputName);
	
	//�i�H�٥h���Dao���ާ@
	//insert�y�k�A�n�[�WnativeQuery = true (�w�]�Ofalse)
	//�Ӥ�
	
}

