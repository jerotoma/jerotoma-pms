package com.jerotoma.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarUtil {
	
	public static Calendar convertStringDateToDate(String strDate){
		Date date  = null;
		Calendar cal = null;
		 
		strDate = strDate.substring(0, 10);
		DateFormat sdf = strDate.contains("T") || strDate.contains("Z") 
				? new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH)
				: new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	
		try {
			date = sdf.parse(strDate);
			cal = Calendar.getInstance();
			cal.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal;		
	}

}
