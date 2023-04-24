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
		//檢查 (1)不能是null (2)不能是空 (3)不能完全空白 → !StringUtils.hasText()
		if (!StringUtils.hasText(name)) {
			return new GetMenuResponse("餐點名稱錯誤");
		}
		//確認資料庫 是否存在此餐點
		Optional<Menu> op = orderDao.findById(name);
		if (!op.isPresent()) {
			return new GetMenuResponse("無此餐點");
		}
		return new GetMenuResponse(op.get(), "成功");
		
		//我的寫法 錯
//		List<Menu> menus = orderDao.findAll();
//		if (!StringUtils.hasText(name)) {  
//			return new GetMenuResponse("餐點名稱錯誤");
//		}
//		for(Menu item : menus) {  //此寫法問題: 如果第一個item就不同於name，就會return"無此餐點"結束，但是可能這個name的item在第三個就無法到後面才找到
//			if (!name.equals(item.getItem())) { 
//				return new GetMenuResponse("無此餐點");
//			}
//		}
//		return new GetMenuResponse(orderDao.findById(name).get(), "成功");
	}

	@Override
	public OrderResponse getAllMenus() {
		return new OrderResponse(orderDao.findAll(), "取得餐點成功!!");
	}
	
	@Override
	public OrderResponse addMenus(List<Menu> menus) {  //可一次新增一筆或多筆 用List
		
		//檢查 menus這個List不能是null，也不能是空陣列 → !CollectionUtils.isEmpty()
		//寫法一
//		if (menus == null || menus.isEmpty()) {
//			return new OrderResponse("新增餐點錯誤");
//		}
		//寫法二
		if (CollectionUtils.isEmpty(menus)) {
			return new OrderResponse("新增餐點錯誤");
		}
		//用foreach: 
		for(Menu item : menus) {
			if (!StringUtils.hasText(item.getItem())) {
				return new OrderResponse("餐點名稱不能是空");
			}
			if (item.getPrice() <= 0) {
				return new OrderResponse("價格錯誤!!");
			}
		}
		
		//回傳OrderResponse
		//寫法一
		//List<Menu> res = orderDao.saveAll(menus);
		//return new OrderResponse(res, "增加餐點成功!!");
		//寫法二
		return new OrderResponse(orderDao.saveAll(menus), "新增餐點成功!!");  //saveAll()存多筆: e.g. List, Set (因為是集合collection 是多筆)
																		   //save()存單一筆: e.g. Map  
	}

	@Override
	public OrderResponse order(Map<String, Integer> orderMap) {  //Map<key:餐點名稱, value:數量>
		//寫法一	不在迴圈進資料庫	
		List<String> itemList = new ArrayList<>();  //餐點名稱List
		int total = 0;
		Map<String, Integer> finalOrderMap = new HashMap();
		//檢查 數量不能是負值
		for (Entry<String, Integer> map : orderMap.entrySet()) {  //點餐的Map
			String key = map.getKey();  //點餐Map餐點名稱
			int value = map.getValue();  //點餐Map數量
			
			if (value < 0) {
				return new OrderResponse("餐點數量錯誤!!");
			}
			itemList.add(key);
		}
		List<Menu> result = orderDao.findAllById(itemList);  //從資料庫篩選出 資料庫有此Id的餐點名稱
		//確認 餐點存在
		for (Menu menu : result) {
			String item = menu.getItem();  //資料庫的餐點名稱
			int price = menu.getPrice();  
			
			for (Entry<String, Integer> map : orderMap.entrySet()) {  //點餐的Map
				String key = map.getKey();  //點餐Map餐點名稱
				int value = map.getValue();  //點餐Map數量
				
				if (item.equals(key)) {  //確認資料庫的餐點名稱和點餐的餐點名稱相同
					int eachTotal = price * value;
					total += eachTotal;
					finalOrderMap.put(key, value);
				}
			}
		}
		total = total > 500 ? (int) (total * 0.9) : total;
		return new OrderResponse("點餐成功!!", finalOrderMap, total);
		
		//寫法二
//		//改用orderDao.existsById()寫 但不建議進迴圈一筆一筆找資料庫 缺點:耗效能
//		int total = 0;
//		Map<String, Integer> finalOrderMap = new HashMap();
//		//確認 餐點存在
//		for (Entry<String, Integer> map : orderMap.entrySet()) {  //點餐的Map
//			//檢查 數量不能是負值
//			if (map.getValue() < 0) {
//				return new OrderResponse("餐點數量錯誤!!");
//			}
//			//從資料庫取出 資料庫有此Id的餐點名稱
//			/* 這裡如果用existsById()不適合 因為這樣會需要進資料庫第二次 後面才能取到getPrice()
//			 * Optional<Menu> op = orderDao.findById(map.getKey());
//			 * if (orderDao.existsById(map.getKey())) {}  //existsById()是回傳boolean(true或false)
//			 * Menu menu = orderDao.findById(map.getKey()).get()  
//			 * menu.getPrice()
//			 */
//			if (!op.isPresent()) {                     
//				continue;
//			}
//			finalOrderMap.put(map.getKey(), map.getValue());
//			int eachTotal = op.get().getPrice() * map.getValue();  //***取得價格
//			total += eachTotal;
//		}
//		total = total > 500 ? (int) (total * 0.9) : total;
//		return new OrderResponse("點餐成功!!", finalOrderMap, total);
	}

	//(1)只能修改已存在的菜單價格(價格不能是負數) (2)不存在的菜單不能新增 (3)返回修改後的菜單名稱和新價格
	@Override
	public OrderResponse updateMenuPrice(List<Menu> menuList) {
		//寫法一(我的寫法) 不建議進迴圈一筆一筆找資料庫
//		for (Menu menu: menuList) {
//			//檢查 資料庫是否有此餐點名稱
//			Optional<Menu> op = orderDao.findById(menu.getItem());
//			if (!op.isPresent()) {
//				return new OrderResponse("只能修改已存在的菜單");
//			}
//			//檢查 修改後的價格不能是負數和0
//			if (menu.getPrice() <= 0) {
//				return new OrderResponse("價格不能是負數和0");
//			}
//		}
//		return new OrderResponse(orderDao.saveAll(menuList) ,"菜單修改成功");
		
		//寫法二(老師寫法)
		//檢查 是否為null (CollectionUtils.isEmpty(): 檢查是否為null或空陣列)
		if (CollectionUtils.isEmpty(menuList)) {
			return new OrderResponse("菜單錯誤");
		}
		//檢查 修改後的價格不能是負數和0
		List<String> ids = new ArrayList<>();  //收集外部menuList的id  //
		for (Menu menu : menuList) {
			//不需要檢查是否為空字串或空白字串，因為就算帶入了空字串或空白字串，DB也查無資料
			if (menu.getPrice() <= 0) {
				return new OrderResponse("價格不能是負數和0");
			}
			ids.add(menu.getItem());  //ids有原本資料庫就有的id 和不在資料庫的id
		}		
		//檢查 資料庫是否有此餐點名稱 //找到資料庫和這些id相同的List
		List<Menu> res = orderDao.findAllById(ids);  //只會取出資料庫有的  //
		//檢查 若要修改的餐點名稱(id)不存在，回傳查無菜單
		if (res.size() == 0) {  //因為有可能找出來才是空的 所以放在Dao後面判斷
			return new OrderResponse("查無菜單");
		}
		//若都沒問題的話，加入到newMenuList
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
		return new OrderResponse(orderDao.saveAll(newMenuList) ,"菜單修改成功");
		
		//寫法三(老師寫法) existsById() 但不建議進迴圈一筆一筆找資料庫
//		List<Menu> updateMenus = new ArrayList<>();
//		for (Menu menu : menuList) {
//			//boolean bool = orderDao.existsById(menu.getItem());
//			if (orderDao.existsById(menu.getItem())) {
//				updateMenus.add(menu);
//			}
//		}
//		if (updateMenus.size() == 0) {
//			return new OrderResponse("查無菜單");
//		}
//		return new OrderResponse(orderDao.saveAll(updateMenus) ,"菜單修改成功");
	}
	
		

	//saveAll() 資料庫已經存在會修改 不存在會新增
	
	/*
	 * 不懂問題
	 * (1)取資料庫就是用findById()或findAllById()嗎?
	 * (2)findById()和findAllById()兩者的差別?
	 *    *findById(id名稱) 從資料庫取出此<id名稱>的id
	 *    *findAllById(id名稱List) 從資料庫取出有此<id名稱List>的所有id
	 * (3)什麼時候會用到Optional?
	 */
	

}
