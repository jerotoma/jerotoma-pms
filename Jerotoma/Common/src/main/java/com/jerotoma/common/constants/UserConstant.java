package com.jerotoma.common.constants;

public class UserConstant {
	
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String USER_NAME = "username";
	public static final String PASSWORD = "password";
	public static final String CONFIRM_PASS = "confirmPassword";
	public static final String TERM = "term";
	public static final String AGE  = "age";
	public static final String GENDER = "gender";
	public static final String OCCUPATION = "occupation";
	public static final String BIRTH_DATE = "birthDate";
	public static final String PICTURE = "picture";
	public static final String FULL_NAME = "fullName";
	public static final String TEACHER_CODE = "fullName";
	public static final String POSITION = "position";
	public static final String STUDENT_CODE = "studentCode";
	public static final String USER_ID = "userId";
	
	public static enum USER_TYPES{
		TEACHER("teacher"),
		PARENT("parent"),
		STAFF("staff"),
		STUDENT("student");
		
		private String type;
		
		USER_TYPES(String type){
			this.type = type;
		}
		
		public String getType(){
			return type;
		}
	}

	public static String USER_TYPE = "userType";
	
	
	public static USER_TYPES processUserType(String type) {
		for (USER_TYPES ut : USER_TYPES.values()) {
			if(ut.type.equals(type)) {
				return ut;
			}
		}
		return null;
	}
}
