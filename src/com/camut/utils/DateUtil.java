package com.camut.utils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	
	public static final String HqlDateFormat = "yyyy-MM-dd"; 
	public static String FormatDate(Date date, String formate)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(formate);
		return dateFormat.format(date);
	}
	
	public static Date AddDays(Date date, int offset)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, offset);
		return cal.getTime();
	}

}
