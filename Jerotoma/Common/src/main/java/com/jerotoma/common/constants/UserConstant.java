package com.jerotoma.common.constants;

import java.util.Collection;

import com.jerotoma.common.models.security.Role;

public class UserConstant {
	
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String USERNAME = "username";
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
	public static final String USER_CODE = "userCode";
	public static final String DEPARTMENT_ID = "departmentId";
	public static final String userType = "userType";
	public static final String DEFAULT_PHONE_NUMBER = "800-900-8888";
	public static final String DEFAULT_BIRTH_DATE = "1920-01-01";
	public static final String PROFILE_IMAGE_ID = "profileImageId";
	
	public static enum USER_TYPE {		
		TEACHER("teacher"),
		TEACHERS("teachers"),
		PARENT("parent"),
		PARENTS("parents"),
		STAFF("staff"),
		STAFFS("staffs"),
		STUDENT("student"),
		STUDENTS("students"),
		STUDENT_AND_PARENT("studentAndParent"),
		USER("user");
		
		private String type;
		
		USER_TYPE(String type){
			this.type = type;
		}
		
		public String getType(){
			return type;
		}
	}
	
	public static USER_TYPE processUserType(String type) {
		for (USER_TYPE ut : USER_TYPE.values()) {
			if(ut.type.equals(type) || ut.name().equals(type)) {
				return ut;
			}
		}
		return null;
	}
	
	
	public static enum userTypeByPath{
		ADMIN("admin"),
		TEACHERS("teachers"),
		PARENTS("parents"),
		STAFFS("staffs"),
		STUDENTS("students"),
		USERS("users");
		
		private String type;
		
		userTypeByPath(String type){
			this.type = type;
		}
		
		public String getType(){
			return type;
		}
	}

	public static userTypeByPath processUserTypeByPath(String type) {
		for (userTypeByPath ut : userTypeByPath.values()) {
			if(ut.type.equals(type) || ut.name().equals(type)) {
				return ut;
			}
		}
		return null;
	}



	public static USER_TYPE processUserType(Role role) {
		if (RoleConstant.USER_ROLES.ROLE_PARENT.getRoleName().equals(role.getName())) {
			return USER_TYPE.PARENT;
		} else if (RoleConstant.USER_ROLES.ROLE_TEACHER.getRoleName().equals(role.getName())) {
			return USER_TYPE.TEACHER;
		} else if (RoleConstant.USER_ROLES.ROLE_STUDENT.getRoleName().equals(role.getName())) {
			return USER_TYPE.STUDENT;
		} else if (RoleConstant.USER_ROLES.ROLE_STAFF.getRoleName().equals(role.getName())) {
			return USER_TYPE.STAFF;
		} else if (RoleConstant.USER_ROLES.ROLE_ADMIN.getRoleName().equals(role.getName())) {
			return USER_TYPE.STAFF;
		} else if (RoleConstant.USER_ROLES.ROLE_USER.getRoleName().equals(role.getName())) {
			return USER_TYPE.USER;
		} else if (RoleConstant.USER_ROLES.ROLE_TEACHER.getRoleName().equals(role.getName())) {
			return USER_TYPE.TEACHER;
		} else if (RoleConstant.USER_ROLES.ROLE_TEACHER.getRoleName().equals(role.getName())) {
			return USER_TYPE.TEACHER;
		} else if (RoleConstant.USER_ROLES.ROLE_TEACHER.getRoleName().equals(role.getName())) {
			return USER_TYPE.TEACHER;
		}		
		return null;
	}

	public static USER_TYPE processUserTypeByRole(Collection<Role> roles) {
		Role role = roles.stream().findFirst().get();
		return processUserType(role);
	}
}
