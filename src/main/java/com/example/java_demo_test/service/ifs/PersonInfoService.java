package com.example.java_demo_test.service.ifs;

import java.util.List;

import com.example.java_demo_test.entity.PersonInfo;
import com.example.java_demo_test.vo.GetPersonInfoRequest;
import com.example.java_demo_test.vo.GetPersonInfoResponse;
import com.example.java_demo_test.vo.PersonInfoResponse;

public interface PersonInfoService {

//	public void addPersonInfo(PersonInfo personInfo);  //�u�঳1��PersonInfo��1�����(��4���ݩ�/4�����)
	//void�S���^�ǭ� �p�G�n�^�ǭn�ﱼ
	
	//�s�W�ӤH��T
	public PersonInfoResponse addPersonInfo(List<PersonInfo> personInfoList);  //�i�H���@���Φh�����
	
	//��id���o�������ӤH��T
	public GetPersonInfoResponse getPersonInfoById(String id);
	
//	public GetPersonInfoResponse getPersonInfoById(GetPersonInfoRequest request);
	
	//���o�Ҧ��ӤH��T
	public GetPersonInfoResponse getAllPersonInfo();
	
	//���o�~���j���J���󪺩Ҧ��ӤH��T
	public GetPersonInfoResponse getPersonInfoByAgeGreaterThan(int age);
	
	//���o�~���p�󵥩��J���󪺩Ҧ��ӤH��T�A�Ѥp��j�Ƨ�
	public GetPersonInfoResponse getPersonInfoByAgeLessThanEqual(int age);
	
	//���s��϶�: ���o�p�����1 �M �j�����2���ӤH��T
	public GetPersonInfoResponse getPersonInfoByAgeLessThanOrAgeGreaterThan(int age1, int age2);
	
	//�s��϶�: ���o�~������2�ӼƦr(���]�t)��������T�A�Ѥj��p�ƧǡA�u���e�T�������
	public GetPersonInfoResponse getPersonInfoByAgeBetween(int age1, int age2);
	
	//���ocity�]�t�Y�ӯS�w�r���Ҧ��ӤH��T
	public GetPersonInfoResponse getPersonInfoContaining(String city);
	
	//���o�~���j���J���� �H�� city �]�t�Y�ӯS�w�r���Ҧ��ӤH��T�A�Ѥj��p�Ƨ�
	public GetPersonInfoResponse getPersonInfoByAgeAndCityContaining(int age, String city);
}
