package com.camut.utils;

import java.util.Comparator;

import com.camut.pageModel.PageEvaluate;

public class PageEvaluteComparator implements Comparator{
	
	public int compare(Object o1,Object o2){
		PageEvaluate p1=(PageEvaluate)o1;
		PageEvaluate p2=(PageEvaluate)o2;
		 long i = 0;
         i = p2.getCreatetime().getTime() - p1.getCreatetime().getTime(); // 使用字符串的比较
         if(i==0){
        	 int j = p1.getConsumer().compareTo(p2.getConsumer());
        	 return j;
         }else if(i>0){
        	 return 1;
         }else {
        	 return -1;
         }
		
	}
	
}
