package com.jerotoma.common.utils;

import java.text.DecimalFormat;

public class NumberFormatter {
	DecimalFormat decimalFormat;

	public NumberFormatter() {
		decimalFormat = new DecimalFormat(".##");
	}

	public static NumberFormatter getInsatnce() {
		return new NumberFormatter();
	}

	public Double formatForStripeRound2Places(Double amount) {
		// System.err.println(decimalFormat.format(amount));
		return (Double.valueOf(decimalFormat.format(amount)) * 100);
	}

	public Double LongToDoubleFromStripe(Long num) {
		return (Double.valueOf(num) / 100);
	}

	public Double round2Places(Double amount) {
		return (Double.valueOf(decimalFormat.format(amount)));
	}
	
	public void setFormatPattern(String pattern) {
		this.decimalFormat = new DecimalFormat(pattern);
	}
	
	public static String getTwoDigit(Integer number) {		
		String stringNumber = number < 10 ? "0" + number : "" + number;		
		return stringNumber ;		
	}
	
	public static String formatTimeRange(String time) {		
		String timePart1 = time.substring(0, time.indexOf("-"));
		String timePart2 = time.substring(time.indexOf("-") + 1);
		
		String hour1 = timePart1.substring(0, timePart1.indexOf(":"));
		String hour2 = timePart2.substring(0, timePart2.indexOf(":"));
		
		String min1 = timePart1.substring(timePart1.indexOf(":") + 1);
		String min2 = timePart2.substring(timePart2.indexOf(":") + 1);
		
		String twoDigitHour1 = getTwoDigit(Integer.valueOf(hour1.trim()));
		String twoDigitHour2 = getTwoDigit(Integer.valueOf(hour2.trim()));
		String twoDigitMin1 = getTwoDigit(Integer.valueOf(min1.trim()));
		String twoDigitMin2 = getTwoDigit(Integer.valueOf(min2.trim()));	
		
		return twoDigitHour1.concat(":").concat(twoDigitMin1).concat(" - ").concat(twoDigitHour2).concat(":").concat(twoDigitMin2) ;		
	}
}
