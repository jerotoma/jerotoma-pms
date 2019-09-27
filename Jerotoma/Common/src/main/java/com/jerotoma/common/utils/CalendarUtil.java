package com.jerotoma.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarUtil {
	
	public static final String ISO_8601_DATE_TIME_WITH_TZ_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final String ISO_8601_DATE_FORMAT = "yyyy-MM-dd";
	public static final String ISO_8601_DATE_WITH_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	
	public static Date convertStringToDate(String strDate){	
		
		Date date = null;
		
		if (StringUtility.isEmpty(strDate)) {
			return date;
		}
		
		if (strDate.contains("T") && strDate.contains("Z")) {
			date = convertToDate(ISO_8601_DATE_TIME_WITH_TZ_FORMAT, strDate);
		} else if (strDate.contains("T")) {
			date = convertToDate(ISO_8601_DATE_WITH_TIME_FORMAT, strDate);
		} else {
			date = convertToDate(ISO_8601_DATE_FORMAT, strDate);
		}
		return date;		
	}
	/**
	 * Converts a string date to the format specified.
	 *
	 * @param pFormat
	 *				A String containing the date format specification.
	 * @param pDateTxt
	 *				A String date value.
	 *
	 * @return A formatted Date value.
	 */
	public static Date convertToDate(String pFormat, String pDateTxt) {
		if (pFormat != null && pDateTxt != null) {
			SimpleDateFormat aFormattter = new SimpleDateFormat(pFormat);
			return convertToDate(aFormattter, pDateTxt);
		}
		return null;
	}
	
	public static Date convertToDate(SimpleDateFormat pSimpleDateFormat, String pDateTxt) {
		Date date = null;
		if (pSimpleDateFormat == null) {
			return null;
		}
		if (StringUtility.isEmpty(pDateTxt)) {
			 return null;
		}
		try {
			date = pSimpleDateFormat.parse(pDateTxt);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		 return date;
	}
	public static Date getTodaysDate() {
		
		return new Date();
	}

}
