package com.example.java_demo_test.service.ifs;

import java.util.List;
import java.util.Map;

import com.example.java_demo_test.entity.Menu;
import com.example.java_demo_test.vo.GetMenuResponse;
import com.example.java_demo_test.vo.OrderResponse;

public interface OrderService {

	//���o��@���
	public GetMenuResponse getMenuByName(String name);  //name�O�\�I�W�� //Response���ݩ�: menu�Mmessage //���b ���~�T�� //�n�gcontroller //postman�ݵ��G
	
	//���o�Ҧ����
	public OrderResponse getAllMenus();
	
	//�s�W�\�I
	public OrderResponse addMenus(List<Menu> menus);
	
	//�I�\
	public OrderResponse order(Map<String, Integer> order);
	
	//�ק������
	public OrderResponse updateMenuPrice(List<Menu> menuList);  //(1)�u��ק�w�s�b��������(���椣��O�t��) (2)���s�b����椣��s�W (3)��^�ק�᪺���W�٩M�s����
	
}
