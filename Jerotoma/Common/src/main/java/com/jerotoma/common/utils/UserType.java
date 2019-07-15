package com.jerotoma.common.utils;

import java.util.Arrays;

public enum UserType {
	STUDENT("student"),
	PARENT("parent"),
	TEACHER("teacher"),
	OTHER_STAFF("other-staff");
	
	private String value;
	
	UserType(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public static UserType fromValue(String value) {
		for (UserType type : values()) {
			if (type.value.equalsIgnoreCase(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException(
				"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
	}

}
