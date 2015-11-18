package com.camut.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

public class Log4jUtil {
	private static Logger fcpLog = Logger.getLogger("op");	
	
	public static Logger sysLog = Logger.getLogger(Log4jUtil.class);	
	
	
	/**
	 * 记录后台操作日志
	 * @param operatorName   操作人员名称
	 * @param memo  操作项目描述
	 */
	public static void info(String operatorName,String memo){
		fcpLog.info("操作人员："+operatorName+"-----操作内容:"+memo);
	}
	
	
	/**
	 * 记录后台操作日志
	 * @param operatorName   操作人员名称
	 * @param memo  操作项目描述
	 */
	public static void info(String memo){
		fcpLog.error(memo);
	}
	
	 public static void error(Exception e) {
	        String print = getExceptionTrace(e);
	        sysLog.error(print);
	    }
	 
	 private static String getExceptionTrace(Exception e) {
	        String print = null;
	        ByteArrayOutputStream bout = new ByteArrayOutputStream();
	        PrintWriter wrt = new PrintWriter(bout);
	        e.printStackTrace(wrt);
	        wrt.close();
	        print = bout.toString();
	        return print;
	    }
	
}
