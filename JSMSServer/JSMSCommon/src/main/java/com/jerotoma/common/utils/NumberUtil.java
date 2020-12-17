package com.jerotoma.common.utils;

public class NumberUtil {
	public static Double get2FixedRoundNumber(Double amount) {
		return new NumberFormatter().round2Places(amount);		
	}
	
	public static String getTwoDigit(Integer number) {		
		return NumberFormatter.getTwoDigit(number);		
	}
}
