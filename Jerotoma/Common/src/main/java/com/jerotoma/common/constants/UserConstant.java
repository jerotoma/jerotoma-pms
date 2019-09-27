package com.jerotoma.common.constants;

public class UserConstant {
	
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String USER_NAME = "username";
	public static final String PASSWORD = "password";
	public static final String CONFIRM_PASS = "confirmPassword";
	public static final String TERMS = "terms";
	public static final String AGE  = "age";
	public static final String GENDER = "gender";
	public static final String OCCUPATION = "occupation";
	public static final String BIRTH_DATE = "birthDate";
	public static final String PICTURE = "picture";
	public static final String AVATAR = "avatar";
	public static final String FULL_NAME = "fullName";
	public static final String EMPLOYMENT_CODE = "employmentCode";
	public static final String TEACHER_CODE = "teacherCode";
	public static final String POSITION = "position";
	public static final String POSITION_ID = "positionId";
	public static final String ACADEMIC_DISCIPLINE_ID = "academicDisciplineId";
	public static final String STUDENT_NUMBER = "studentNumber";
	public static final String USER_ID = "userId";
	public static final String ID = "id";
	public static final String UPDATED_ON = "updatedOn";
	public static final String CREATED_ON = "createdOn";
	public static final String EMAIL_ADDRESS = "emailAddress";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String ADDRESS = "address";
	public static final String MIDDLE_NAMES = "middleNames";
	public static final String PARENT_IDS = "parentIds";
	public static final String STUDENT_IDS = "studentIds";
	
	public static enum USER_TYPES{
		TEACHER("teacher"),
		PARENT("parent"),
		STAFF("staff"),
		STUDENT("student"),
		STUDENT_AND_PARENT("studentAndParent");
		
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
