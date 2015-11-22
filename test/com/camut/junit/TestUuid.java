package com.camut.junit;

import com.camut.utils.StringUtil;

public class TestUuid {

	/**
	 * @Title: main
	 * @Description: 
	 * @param: @param args
	 * @return void  
	 **/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			for(int i = 1;i<=100;i++){
				String uuid = StringUtil.getUUID();
				System.out.println("uuid"+i+":"+uuid);
			}
	}

}
