package com.jerotoma.common.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtility {
	
	public static boolean isEmpty(String s) {
		return StringUtils.isEmpty(s);
	}
	
	public static Integer parseInteger(String s) {
		if(!isEmpty(s) && isNumeric(s)) {
			return Integer.parseInt(s);
		}
		return Integer.MIN_VALUE;
	}

	public static boolean isNumeric(String object) {
		return StringUtils.isNumeric(object);
	}
	
	public static String capitalize(String s) {
		return StringUtils.capitalize(s.toLowerCase());
	}

	public static String getString(Object object) {
		if (object != null) {
			return String.valueOf(object);
		}
		return null;
	}

	public static boolean isNumeric(Object object) {	
		return isNumeric(getString(object)) ;
	}
}