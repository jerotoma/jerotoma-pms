package com.jerotoma.common.utils;

import java.util.Map;

public class NumberUtil {
	public static Double get2FixedRoundNumber(Double amount) {
		return new NumberFormatter().round2Places(amount);		
	}
	
	public static String getTwoDigit(Integer number) {		
		return NumberFormatter.getTwoDigit(number);		
	}
	
	public static Double getDoube(Map<String, Object> mapParam, String fieldLable) {
		Double score = null;
		if (mapParam.get(fieldLable) instanceof Double) {
			score = (Double) mapParam.get(fieldLable);
		} else {
			Integer sc = (Integer) mapParam.get(fieldLable);
			score = sc.doubleValue();
		}
		return score;
	}
}
