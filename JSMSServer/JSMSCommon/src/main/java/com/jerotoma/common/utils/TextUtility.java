package com.jerotoma.common.utils;

public class TextUtility {

	public static String longToString(Long tokenExpirationTime) {		
		return String.valueOf(tokenExpirationTime);
	}

	public static boolean isNumeric(String s) {		
		return StringUtility.isNumeric(s);
	}

}
