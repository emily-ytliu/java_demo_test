package com.example.java_demo_test.service.ifs;

import java.util.List;
import java.util.Map;

import com.example.java_demo_test.entity.Menu;
import com.example.java_demo_test.vo.GetMenuResponse;
import com.example.java_demo_test.vo.OrderResponse;

public interface OrderService {

	//取得單一菜單
	public GetMenuResponse getMenuByName(String name);  //name是餐點名稱 //Response的屬性: menu和message //防呆 錯誤訊息 //要寫controller //postman看結果
	
	//取得所有菜單
	public OrderResponse getAllMenus();
	
	//新增餐點
	public OrderResponse addMenus(List<Menu> menus);
	
	//點餐
	public OrderResponse order(Map<String, Integer> order);
	
	//修改菜單價格
	public OrderResponse updateMenuPrice(List<Menu> menuList);  //(1)只能修改已存在的菜單價格(價格不能是負數) (2)不存在的菜單不能新增 (3)返回修改後的菜單名稱和新價格
	
}
