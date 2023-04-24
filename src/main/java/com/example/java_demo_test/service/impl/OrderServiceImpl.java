package com.example.java_demo_test.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.java_demo_test.entity.Menu;
import com.example.java_demo_test.repository.OrderDao;
import com.example.java_demo_test.service.ifs.OrderService;
import com.example.java_demo_test.vo.GetMenuResponse;
import com.example.java_demo_test.vo.OrderResponse;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderDao orderDao;
	
	@Override
	public GetMenuResponse getMenuByName(String name) { 
		//�ˬd (1)����Onull (2)����O�� (3)���৹���ť� �� !StringUtils.hasText()
		if (!StringUtils.hasText(name)) {
			return new GetMenuResponse("�\�I�W�ٿ��~");
		}
		//�T�{��Ʈw �O�_�s�b���\�I
		Optional<Menu> op = orderDao.findById(name);
		if (!op.isPresent()) {
			return new GetMenuResponse("�L���\�I");
		}
		return new GetMenuResponse(op.get(), "���\");
		
		//�ڪ��g�k ��
//		List<Menu> menus = orderDao.findAll();
//		if (!StringUtils.hasText(name)) {  
//			return new GetMenuResponse("�\�I�W�ٿ��~");
//		}
//		for(Menu item : menus) {  //���g�k���D: �p�G�Ĥ@��item�N���P��name�A�N�|return"�L���\�I"�����A���O�i��o��name��item�b�ĤT�ӴN�L�k��᭱�~���
//			if (!name.equals(item.getItem())) { 
//				return new GetMenuResponse("�L���\�I");
//			}
//		}
//		return new GetMenuResponse(orderDao.findById(name).get(), "���\");
	}

	@Override
	public OrderResponse getAllMenus() {
		return new OrderResponse(orderDao.findAll(), "���o�\�I���\!!");
	}
	
	@Override
	public OrderResponse addMenus(List<Menu> menus) {  //�i�@���s�W�@���Φh�� ��List
		
		//�ˬd menus�o��List����Onull�A�]����O�Ű}�C �� !CollectionUtils.isEmpty()
		//�g�k�@
//		if (menus == null || menus.isEmpty()) {
//			return new OrderResponse("�s�W�\�I���~");
//		}
		//�g�k�G
		if (CollectionUtils.isEmpty(menus)) {
			return new OrderResponse("�s�W�\�I���~");
		}
		//��foreach: 
		for(Menu item : menus) {
			if (!StringUtils.hasText(item.getItem())) {
				return new OrderResponse("�\�I�W�٤���O��");
			}
			if (item.getPrice() <= 0) {
				return new OrderResponse("������~!!");
			}
		}
		
		//�^��OrderResponse
		//�g�k�@
		//List<Menu> res = orderDao.saveAll(menus);
		//return new OrderResponse(res, "�W�[�\�I���\!!");
		//�g�k�G
		return new OrderResponse(orderDao.saveAll(menus), "�s�W�\�I���\!!");  //saveAll()�s�h��: e.g. List, Set (�]���O���Xcollection �O�h��)
																		   //save()�s��@��: e.g. Map  
	}

	@Override
	public OrderResponse order(Map<String, Integer> orderMap) {  //Map<key:�\�I�W��, value:�ƶq>
		//�g�k�@	���b�j��i��Ʈw	
		List<String> itemList = new ArrayList<>();  //�\�I�W��List
		int total = 0;
		Map<String, Integer> finalOrderMap = new HashMap();
		//�ˬd �ƶq����O�t��
		for (Entry<String, Integer> map : orderMap.entrySet()) {  //�I�\��Map
			String key = map.getKey();  //�I�\Map�\�I�W��
			int value = map.getValue();  //�I�\Map�ƶq
			
			if (value < 0) {
				return new OrderResponse("�\�I�ƶq���~!!");
			}
			itemList.add(key);
		}
		List<Menu> result = orderDao.findAllById(itemList);  //�q��Ʈw�z��X ��Ʈw����Id���\�I�W��
		//�T�{ �\�I�s�b
		for (Menu menu : result) {
			String item = menu.getItem();  //��Ʈw���\�I�W��
			int price = menu.getPrice();  
			
			for (Entry<String, Integer> map : orderMap.entrySet()) {  //�I�\��Map
				String key = map.getKey();  //�I�\Map�\�I�W��
				int value = map.getValue();  //�I�\Map�ƶq
				
				if (item.equals(key)) {  //�T�{��Ʈw���\�I�W�٩M�I�\���\�I�W�٬ۦP
					int eachTotal = price * value;
					total += eachTotal;
					finalOrderMap.put(key, value);
				}
			}
		}
		total = total > 500 ? (int) (total * 0.9) : total;
		return new OrderResponse("�I�\���\!!", finalOrderMap, total);
		
		//�g�k�G
//		//���orderDao.existsById()�g ������ĳ�i�j��@���@�����Ʈw ���I:�Ӯį�
//		int total = 0;
//		Map<String, Integer> finalOrderMap = new HashMap();
//		//�T�{ �\�I�s�b
//		for (Entry<String, Integer> map : orderMap.entrySet()) {  //�I�\��Map
//			//�ˬd �ƶq����O�t��
//			if (map.getValue() < 0) {
//				return new OrderResponse("�\�I�ƶq���~!!");
//			}
//			//�q��Ʈw���X ��Ʈw����Id���\�I�W��
//			/* �o�̦p�G��existsById()���A�X �]���o�˷|�ݭn�i��Ʈw�ĤG�� �᭱�~�����getPrice()
//			 * Optional<Menu> op = orderDao.findById(map.getKey());
//			 * if (orderDao.existsById(map.getKey())) {}  //existsById()�O�^��boolean(true��false)
//			 * Menu menu = orderDao.findById(map.getKey()).get()  
//			 * menu.getPrice()
//			 */
//			if (!op.isPresent()) {                     
//				continue;
//			}
//			finalOrderMap.put(map.getKey(), map.getValue());
//			int eachTotal = op.get().getPrice() * map.getValue();  //***���o����
//			total += eachTotal;
//		}
//		total = total > 500 ? (int) (total * 0.9) : total;
//		return new OrderResponse("�I�\���\!!", finalOrderMap, total);
	}

	//(1)�u��ק�w�s�b��������(���椣��O�t��) (2)���s�b����椣��s�W (3)��^�ק�᪺���W�٩M�s����
	@Override
	public OrderResponse updateMenuPrice(List<Menu> menuList) {
		//�g�k�@(�ڪ��g�k) ����ĳ�i�j��@���@�����Ʈw
//		for (Menu menu: menuList) {
//			//�ˬd ��Ʈw�O�_�����\�I�W��
//			Optional<Menu> op = orderDao.findById(menu.getItem());
//			if (!op.isPresent()) {
//				return new OrderResponse("�u��ק�w�s�b�����");
//			}
//			//�ˬd �ק�᪺���椣��O�t�ƩM0
//			if (menu.getPrice() <= 0) {
//				return new OrderResponse("���椣��O�t�ƩM0");
//			}
//		}
//		return new OrderResponse(orderDao.saveAll(menuList) ,"���ק令�\");
		
		//�g�k�G(�Ѯv�g�k)
		//�ˬd �O�_��null (CollectionUtils.isEmpty(): �ˬd�O�_��null�ΪŰ}�C)
		if (CollectionUtils.isEmpty(menuList)) {
			return new OrderResponse("�����~");
		}
		//�ˬd �ק�᪺���椣��O�t�ƩM0
		List<String> ids = new ArrayList<>();  //�����~��menuList��id  //
		for (Menu menu : menuList) {
			//���ݭn�ˬd�O�_���Ŧr��Ϊťզr��A�]���N��a�J�F�Ŧr��Ϊťզr��ADB�]�d�L���
			if (menu.getPrice() <= 0) {
				return new OrderResponse("���椣��O�t�ƩM0");
			}
			ids.add(menu.getItem());  //ids���쥻��Ʈw�N����id �M���b��Ʈw��id
		}		
		//�ˬd ��Ʈw�O�_�����\�I�W�� //����Ʈw�M�o��id�ۦP��List
		List<Menu> res = orderDao.findAllById(ids);  //�u�|���X��Ʈw����  //
		//�ˬd �Y�n�ק諸�\�I�W��(id)���s�b�A�^�Ǭd�L���
		if (res.size() == 0) {  //�]�����i���X�Ӥ~�O�Ū� �ҥH��bDao�᭱�P�_
			return new OrderResponse("�d�L���");
		}
		//�Y���S���D���ܡA�[�J��newMenuList
		List<Menu> newMenuList = new ArrayList<>();
		for (Menu resMenu : res) {
			String itemInDB = resMenu.getItem();
			for (Menu menu : menuList) {
				String updateItem = menu.getItem();
				if (itemInDB.equals(updateItem)) {
					newMenuList.add(menu);
				}
			}
		}
		return new OrderResponse(orderDao.saveAll(newMenuList) ,"���ק令�\");
		
		//�g�k�T(�Ѯv�g�k) existsById() ������ĳ�i�j��@���@�����Ʈw
//		List<Menu> updateMenus = new ArrayList<>();
//		for (Menu menu : menuList) {
//			//boolean bool = orderDao.existsById(menu.getItem());
//			if (orderDao.existsById(menu.getItem())) {
//				updateMenus.add(menu);
//			}
//		}
//		if (updateMenus.size() == 0) {
//			return new OrderResponse("�d�L���");
//		}
//		return new OrderResponse(orderDao.saveAll(updateMenus) ,"���ק令�\");
	}
	
		

	//saveAll() ��Ʈw�w�g�s�b�|�ק� ���s�b�|�s�W
	
	/*
	 * �������D
	 * (1)����Ʈw�N�O��findById()��findAllById()��?
	 * (2)findById()�MfindAllById()��̪��t�O?
	 *    *findById(id�W��) �q��Ʈw���X��<id�W��>��id
	 *    *findAllById(id�W��List) �q��Ʈw���X����<id�W��List>���Ҧ�id
	 * (3)����ɭԷ|�Ψ�Optional?
	 */
	

}
