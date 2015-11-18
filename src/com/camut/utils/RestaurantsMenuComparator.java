package com.camut.utils;

import java.util.Comparator;

import com.camut.pageModel.PageRestaurantsMenu;

public class RestaurantsMenuComparator implements Comparator<Object>{
	 public int compare(Object o1,Object o2) {
		 PageRestaurantsMenu p1=(PageRestaurantsMenu)o1;
		 PageRestaurantsMenu p2=(PageRestaurantsMenu)o2;
		 int i = 0;
         i = p1.getMenuName().compareTo(p2.getMenuName()); // 使用字符串的比较
         return i;
	 }

	
}
