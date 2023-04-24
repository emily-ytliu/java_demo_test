package com.example.java_demo_test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
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
	

}

