package com.jerotoma.common.schedules;

import java.time.LocalTime;

import org.json.JSONObject;

public class Time {
	private LocalTime local;
	private Integer hour;
	private Integer minute;
	private Integer second;
	
	public Time() {
		hour = 00;
		minute = 00;
		second = 00;
	}
		
	public Time(Integer hour, Integer minute, Integer second) {
		this.local = LocalTime.of(hour, minute, second);
		this.hour = local.getHour();
		this.minute = local.getMinute();
		this.second = local.getSecond();		
	}
	
	public Time(String time) {
		LocalTime local = LocalTime.parse(time);		
		validateHourValue(local.getHour());
		validateMinuteValue(local.getMinute());
		validateSecondValue(local.getSecond());		
	}
	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		validateHourValue(hour);
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		validateMinuteValue(minute);
	}

	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		validateSecondValue(second);
	}

	@Override
	public String toString() {
		String h = this.hour < 10 ? "0" + this.hour : "" + this.hour;
		String m = this.minute < 10 ? "0" + this.minute : "" + this.minute;
		String s = this.second < 10 ? "0" + this.second : "" + this.second;
		
		return h + ":" + m + ":" + s;
	}
	
	private void validateHourValue(Integer hour) {
		if (hour != null && hour > 0 && hour < 23) {
			this.hour = hour ;
		} else {
			throw new RuntimeException(String.format("Invalid value of hour:  %. Hour value must be from 0 - 23 range", hour));
		}
	}
	
	private void validateMinuteValue(Integer minute) {
		
		if (minute != null && minute > 0 && minute < 59) {
			this.minute = minute ;
		} else {
			throw new RuntimeException(String.format("Invalid value of minute:  %. Minute value must be from 0 - 59 range", minute));
		}
	}
	
	private void validateSecondValue(Integer second) {
		if (second != null && second > 0 && second < 59) {
			this.second = second;
		} else {
			throw new RuntimeException(String.format("Invalid value of second:  %. Second value must be from 0 - 59 range", second));
		}
	}
	
	public static Time parse(String objectString) {
		JSONObject jsonEndTime = new JSONObject(objectString.replace("=", ":"));
		int hour = jsonEndTime.getInt("hour");
		int minute = jsonEndTime.getInt("minute");
		int second = jsonEndTime.getInt("second");
		
		return new Time(hour, minute, second);
	}
	
}
