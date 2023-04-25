package com.example.java_demo_test.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.java_demo_test.constants.RtnCode;
import com.example.java_demo_test.entity.Login;
import com.example.java_demo_test.entity.PersonInfo;
import com.example.java_demo_test.repository.LoginDao;
import com.example.java_demo_test.service.ifs.LoginService;
import com.example.java_demo_test.vo.LoginResponse;
import com.example.java_demo_test.vo.PersonInfoResponse;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDao loginDao;

	//���U�b��
	@Override
	public LoginResponse addAccount(Login login) {
		//�ˬd1: ��J��login ����Onull  
		if (login == null) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		//�ˬd2: login���C����T: account�Bpwd�Bname�Bage
		//      String: account�Bpwd�Bname ����Onull�B�Ŧr��B���ť�
		//      int: age ����O�t��
		if (!StringUtils.hasText(login.getAccount())
				|| !StringUtils.hasText(login.getPwd())
				|| !StringUtils.hasText(login.getName())
				|| login.getAge() < 0) { 
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		//�ˬd3: account ����3~8�X�B���঳�ť�
		//      pwd ����8~12�X�B�ܤ֭n���@�ӯS��Ÿ��B���঳�ť�/Tab/�w��/����/����
		String pattern1 = "\\w{3,8}";  //  \w�O�i�H �Ʀr�B�r���B���u [A-Za-z0-9_]
		String pattern2 = "^(?=.*[!@#$%^&*(),.?\":{}|<>])\\S{8,16}";  //  \S�O����ťաB�w��BTab��B����B�����A��L�r�����i
		/*^(?=.*[!@#$%^&*(),.?\":{}|<>])\\S{8,16}
		 * ��Ӫ�ܡu�}�Y���O()�̪����e�A?=�����O.*���N�r�ťX�{0���Φh��[]�̪����e�A�v
		 * ^ ��� �q�}�Y�}�l
		 * ?= ��� positive lookahead�A�e����^��ܭn��u�}�Y��襲���O�v�A�ǰt�@�Ӧ�m
		 * .* ��� �e����N�r�ťX�{0���Φh��
		 *    . ��� ���N�r��
		 *    * ��� �X�{0���Φh��
		 * [!@#$%^&*(),.?\":{}|<>] ��� []�Ҧ��S��Ÿ�
		 * () ��� �ۦ��@�Ӹs��
		 */
//		String pattern2 = "^(?=.*[\\p{Punct}])[\\S]{8,16}$";
//		String pattern2 = "^(?=.*[\\S^\\w])[\\S]{8,16}$";
		/* 
		 * ^ ��� �ǰt�r�ꪺ�}�Y
		 * $ ��� �ǰt�r�ꪺ����
		 * . ��� �U�Φr��
		 * \p ��� ��諸�r����ƬY�دS��
		 * Punct ��� ������I�Ÿ� !@#$%^&*(),.?\":{}|<>
		 */
		
		if (!login.getAccount().matches(pattern1)
				|| !login.getPwd().matches(pattern2)) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		//�T�{: �n�s�W��account�O�_�w�g�s�b��Ʈw(���s�b���~��s�W)
		Optional<Login> op = loginDao.findById(login.getAccount());  //�z��X��Ʈw�w�g�s�b��account
		if (op.isPresent()) {
			return new LoginResponse(RtnCode.ALREADY_EXIST.getMessage());
		}
		//�T�{�H�W���S���D��A�N�]�w���U�ɶ�
		login.setRegTime(LocalDateTime.now());
		return new LoginResponse(loginDao.save(login), RtnCode.SUCCESSFUL.getMessage());
	}

	//�ͮıb��
	@Override
	public LoginResponse activeAccount(String account, String pwd) {
		//�ˬd: ��J���b���K�X ����Onull�B�Ŧr��B���ť�
		if (!StringUtils.hasText(account)
				|| !StringUtils.hasText(pwd)) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		//�T�{: �b���O�_�s�b���Ʈw(��Ʈw�w�g�s�b���b���~��ͮ�)
		Optional<Login> op = loginDao.findById(account);  //�z��X��Ʈw�w�g�s�b��account
		if (!op.isPresent()) {
			return new LoginResponse(RtnCode.NOT_FOUND.getMessage());
		}
		Login login = op.get();
		//�T�{: �p�G�b���w�g�ͮġA���୫�ƥͮ�
		if (login.isActive() == true) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		//�p�G�b���s�b�A�N�Nboolean isActive�]�w��true�A�æs�J��Ʈw
		login.setActive(true);
		
		return new LoginResponse(loginDao.save(login), RtnCode.SUCCESSFUL.getMessage());
	}

	//�n�J�b��
	@Override
	public LoginResponse login(String account, String pwd) {
		//�g�k�@ �ڪ��g�k
//		//�ˬd: ��J���b���K�X ����Onull�B�Ŧr��B���ť�
//		if (!StringUtils.hasText(account)
//				|| !StringUtils.hasText(pwd)) {
//			return new LoginResponse("�b���K�X���~");
//		}
//		//�T�{: �b���O�_�s�b���Ʈw(�b���w�g�s�b���Ʈw�~��n�J)
//		Optional<Login> op = loginDao.findById(account);  //�z��X��Ʈw�w�g�s�b��account
//		if (!op.isPresent()) {
//			return new LoginResponse("���b�����s�b");
//		}
//		//�T�{: �K�X���~(�P��Ʈw���۲�)�N�L�k���\�n�J
//		if (!op.get().getPwd().equals(pwd)) {  //String����n��equals() !!!!!!
//			return new LoginResponse("�K�X���~");
//		}
//		//�T�{: �b���O�_�w�g�ͮ�(�٨S�ͮĵL�k�n�J)
//		if (op.get().isActive() == false) {
//			return new LoginResponse("���b���|���ͮ�");
//		}
//		return new LoginResponse("�n�J���\");
		
		//�g�k�G Better ������� ��J���b���K�X �M ��Ʈw���b���K�X
		//�T�{: �b���K�X�O�_�s�b���Ʈw(�@�����b���M�K�X=>�ۤv�gDao)(�b���K�X���S��J���~�~��n�J)
		Login login = loginDao.findByAccountAndPwd(account, pwd);  //�z��X��Ʈw�w�g�s�b��account
		if (login == null) {
			return new LoginResponse(RtnCode.NOT_FOUND.getMessage());
		}
		//�T�{: �b���O�_�w�g�ͮ�(�٨S�ͮĵL�k�n�J)
		if (login.isActive() == false) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		return new LoginResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public LoginResponse findAccountByCity(String city) {
		//�ˬd: ��J��city ����Onull�B�Ŧr��B���ť�
		if (!StringUtils.hasText(city)) {
			return new LoginResponse(RtnCode.DATA_ERROR.getMessage());
		}
		//�T�{: ��J��city�O�_�s�b���Ʈw(city�s�b���Ʈw�~���X�t�����S�wcity���ϥΪ�)
		List<Login> result = loginDao.findByCityContainingOrderByAgeAsc(city);
		if (result.isEmpty()) {
			return new LoginResponse(RtnCode.NOT_FOUND.getMessage());
		}
		return new LoginResponse(result, RtnCode.SUCCESSFUL.getMessage());
	}
	
	
}
