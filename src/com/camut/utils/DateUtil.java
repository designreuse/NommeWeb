package com.camut.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;


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
	
	public static Date AddHours(Date date, int offset)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, offset);
		return cal.getTime();
	}
	
	public static Date SetToMidnightTime(Date date)
	{
		if(date != null){
			try {
				return new SimpleDateFormat("yyyy-MM-dd").parse((new SimpleDateFormat("yyyy-MM-dd").format(date)));
			} catch (ParseException e) {
				Log4jUtil.error(e);
				Log4jUtil.info("Something went wrong when trying to convert DateTime to Date in function addOrder()");
			}
		}
		Log4jUtil.info("SetToMidnightTime(Date date): input value is NULL");
		
		return null;
	}
	
	public static Date ToDate(DateTime dateTime)
	{
		if(dateTime != null){
			try {
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
			} catch (ParseException e) {
				Log4jUtil.error(e);
				Log4jUtil.info("Something went wrong when trying to convert DateTime to Date");
			}
		}
		Log4jUtil.info("ToDate(DateTime dateTime) failed: input value is NULL");
		return null;
	}

}
