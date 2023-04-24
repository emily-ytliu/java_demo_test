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
	
	//�s�W�ӤH��T
	@Override
	public PersonInfoResponse addPersonInfo(List<PersonInfo> personInfoList) {
		//�Ѽƶi�ӲĤ@��ơA�n�ˬd�����b
		//���b:�ˬd�Ѽ�
		//�ˬd1: List����Onull�Ϊ� //CollectionUtils.isEmpty()  //�p�G�Onull�A�|����NullPointerException
		if (CollectionUtils.isEmpty(personInfoList)) {  //�p�GList�Onull�ΪŰ}�C
			return new PersonInfoResponse("�s�W��T���~");
		}
		//�ˬd2: List�����C����T(PersonInfo): id�Bname�Bcity�Bage  //��foreach�M��List���C�Ӷ���
		List<String> idList = new ArrayList<>();
		for (PersonInfo item : personInfoList) {
			//id�Bname�Bcity: ����Onull�B�Ŧr��B���ť�  //StringUtils.hasText()
			//age: ����O�t��
			//��||(�Ӥ��O&&)��]: �|�ӧP�_�u�n���@�ӲŦX�N�|�^�ǿ��~
			if (!StringUtils.hasText(item.getId())
					|| !StringUtils.hasText(item.getName())
					|| item.getAge() < 0
					|| !StringUtils.hasText(item.getCity())) {
				return new PersonInfoResponse("�s�W��T���~");
			}
			//�`��id
			idList.add(item.getId());  //�]�t�w�g�s�b�M���Ӥ��s�b��id
		}
		//�T�{: �n�s�W��idList�O�_�w�g�s�b��Ʈw(���s�b���~��s�W)
		List<PersonInfo> result = personInfoDao.findAllById(idList);  //�z��X��Ʈw�w�g�s�b��id
		//�p�G��Ʈw�w�g�s�b�Ҧ��n�s�W��T��id
		if (result.size() == personInfoList.size()) {
			return new PersonInfoResponse("�Ҧ��s�W��T���w�s�b");
		}
		//�p�G��Ʈw�w�g�s�b��id
		if (result.size() > 0) {  //�p�G������result.size() > 0 ��ܸ�Ʈw�w�g����ơA�ҥH��id�w�g�s�b���Ʈw 
								  //�p�G����result.size() == 0�A��ܸ�Ʈw�٨S����ơA�ҥH��id���s�b���Ʈw
			
			//�簣��idList����Ʈw�w�g�s�b��id�A�u�O�d��Ʈw�٤��s�b��id
			List<String> resIds = new ArrayList<>();
			for (PersonInfo item : result) {
				resIds.add(item.getId());  //��w�g�s�b���Ʈw��id�A�[�J��resIds�o��List��
			}
			//�@��g�k
			List<PersonInfo> saveList = new ArrayList<>();
			for (PersonInfo item : personInfoList) {
				if (!resIds.contains(item.getId())) {
					saveList.add(item);  //�n�s�W���Ʈw��id�A�p�G��Ʈw�٤��s�b�A�N�[�J��saveList�o��List��
				}
			}
			//�ɥR: Lambda�g�k
//			List<PersonInfo> saveList2 = personInfoList.stream()
//					.filter(item -> !resIds.contains(item.getId()))
//					.collect(Collectors.toList());
			personInfoDao.saveAll(saveList);
			return new PersonInfoResponse(saveList, "�w�s�b��id���s�W�A��L�s�W��T���\");
		}
		//�p�G�ˬd���S�����D�A(�]���S����Ʈw�w�g�s�b��id)�A�s�J��Ʈw
		personInfoDao.saveAll(personInfoList);
		return new PersonInfoResponse(personInfoList, "�s�W��T���\");
	}

	//��id���o�������ӤH��T
	@Override
	public GetPersonInfoResponse getPersonInfoById(String id) {
		//�ˬd: ����Onull�B����O�Ŧr��B����O���ť�
		if (!StringUtils.hasText(id)) {
			return new GetPersonInfoResponse("id��T���~");
		}
		//�q��Ʈw�z��X����id����T
		Optional<PersonInfo> op = personInfoDao.findById(id);  //Jpa����findById()�n��Optional��
		//�ˬd ��id�O�_�s�b���Ʈw
		if (!op.isPresent()) {
			return new GetPersonInfoResponse("id���s�b");
		}
		return new GetPersonInfoResponse(op.get(), "���o��id������T���\");
	}
//	@Override
//	public GetPersonInfoResponse getPersonInfoById(GetPersonInfoRequest request) {
//		String id = request.getId();
//		//�ˬd: ����Onull�B����O�Ŧr��B����O���ť�
//		if (!StringUtils.hasText(id)) {
//			return new GetPersonInfoResponse("id��T���~");
//		}
//		//�q��Ʈw�z��X����id����T
//		Optional<PersonInfo> op = personInfoDao.findById(id);  //Jpa����findById()�n��Optional��
//		//�ˬd ��id�O�_�s�b���Ʈw
//		if (!op.isPresent()) {
//			return new GetPersonInfoResponse("id���s�b");
//		}
//		return new GetPersonInfoResponse(op.get(), "���o��id������T���\");
//	}
	
	//���o�Ҧ��ӤH��T
	@Override
	public GetPersonInfoResponse getAllPersonInfo() {
		return new GetPersonInfoResponse(personInfoDao.findAll(), "���o�Ҧ���T���\");
	}

	//���o�~���j���J���󪺩Ҧ��ӤH��T
	@Override
	public GetPersonInfoResponse getPersonInfoByAgeGreaterThan(int age) {
		//�g�k�@ �@��g�k
//		if (age < 0) {
//			return new GetPersonInfoResponse("�~�֤���O�t��");
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
//			return new GetPersonInfoResponse("�L�ŦX���󪺸�T");
//		}
//		return new GetPersonInfoResponse(all, "���o��T���\");
		
		//�g�k�G Dao�s�WJpa�y�k: findByAgeGreaterThan
		if (age < 0) {
			return new GetPersonInfoResponse("�~�֤���O�t��");
		}
		List<PersonInfo> ageGreaterList = personInfoDao.findByAgeGreaterThan(age);
		if (ageGreaterList.isEmpty()) {
			return new GetPersonInfoResponse("�L�ŦX���󪺸�T");
		}
		return new GetPersonInfoResponse(ageGreaterList, "���o��T���\");
		
	}

	//���o�~���p�󵥩��J���󪺩Ҧ��ӤH��T�A�Ѥp��j�Ƨ�
	//Dao�s�WJpa�y�k:
	@Override
	public GetPersonInfoResponse getPersonInfoByAgeLessThanEqual(int age) {
		if (age < 0) {
			return new GetPersonInfoResponse("�~�֤���O�t��");
		}
		List<PersonInfo> ageLessList = personInfoDao.findByAgeLessThanEqualOrderByAgeAsc(age);
		if (ageLessList.isEmpty()) {
			return new GetPersonInfoResponse("�L�ŦX���󪺸�T");
		}
		return new GetPersonInfoResponse(ageLessList, "���o��T���\");
	}
	
	//���s��϶�: ���o�p�����1 �M �j�����2���ӤH��T
	@Override
	public GetPersonInfoResponse getPersonInfoByAgeLessThanOrAgeGreaterThan(int age1, int age2) {
		if (age1 < 0 || age2 < 0) {
			return new GetPersonInfoResponse("�~�֤���O�t��");
		}
		List<PersonInfo> ageLessOrGreaterList = personInfoDao.findByAgeLessThanOrAgeGreaterThan(age1, age2);
		if (ageLessOrGreaterList.isEmpty()) {
			return new GetPersonInfoResponse("�L�ŦX���󪺸�T");
		}
		return new GetPersonInfoResponse(ageLessOrGreaterList, "���o��T���\");
	}

	//���~������2�ӼƦr(���]�t)��������T�A�Ѥj��p�ƧǡA�u���e�T�������
	//Dao�s�WJpa�y�k:
	@Override
	public GetPersonInfoResponse getPersonInfoByAgeBetween(int age1, int age2) {
		if (age1 < 0 || age2 < 0) {
			return new GetPersonInfoResponse("�~�֤���O�t��");
		}
		List<PersonInfo> ageBetweenList = personInfoDao.findTop3ByAgeBetweenOrderByAgeDesc(age1, age2);
		if (ageBetweenList.isEmpty()) {
			return new GetPersonInfoResponse("�L�ŦX���󪺸�T");
		}
		return new GetPersonInfoResponse(ageBetweenList, "���o��T���\");
	}

	//���ocity�]�t�Y�ӯS�w�r���Ҧ��ӤH��T
	//Dao�s�WJpa�y�k:
	@Override
	public GetPersonInfoResponse getPersonInfoContaining(String city) {
		if (!StringUtils.hasText(city)) {
			return new GetPersonInfoResponse("��T���~");
		}
		List<PersonInfo> hasWordList = personInfoDao.findByCityContaining(city);
		if (hasWordList.isEmpty()) {
			return new GetPersonInfoResponse("�L���S�w�r");
		}
		return new GetPersonInfoResponse(hasWordList, "���o��T���\");
	}

	//���o�~���j���J���� �H�� city�]�t�Y�ӯS�w�r���Ҧ��ӤH��T
	//Dao�s�WJpa�y�k:
	@Override
	public GetPersonInfoResponse getPersonInfoByAgeAndCityContaining(int age, String city) {
		if (age < 0) {
			return new GetPersonInfoResponse("�~�֤���O�t��");
		}
		if (!StringUtils.hasText(city)) {
			return new GetPersonInfoResponse("��T���~");
		}
		List<PersonInfo> ageAndWordList = personInfoDao.findByAgeGreaterThanAndCityContainingOrderByAgeDesc(age, city);
		if (ageAndWordList.isEmpty()) {
			return new GetPersonInfoResponse("�L�ŦX���󪺸�T");
		}
		return new GetPersonInfoResponse(ageAndWordList, "���o��T���\");
	}

	

}
