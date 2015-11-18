package com.camut.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.camut.framework.constant.GlobalConstant;

public class CreateOrderNumber {
	public static String createUnique(){
		//生成唯一码
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat fomat2 = new SimpleDateFormat("yyyyMMddHHmmss");
		String mydate = fomat2.format(date);
		//int num=(int) ((Math.random()*9000000)+1000000);
		
		String base = "0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i <GlobalConstant.ORDRE_RANDOM_LENGTH ; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
		String unique = mydate+sb.toString(); 
		return unique;
	}
	
	public static String getRadomString(){
		String base = "0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i <16; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
		String unique = sb.toString(); 
		return unique;
	}
	
}
