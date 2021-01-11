package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.ParentConstant;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.addresses.Address;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.models.users.User;
import com.jerotoma.common.models.users.students.Student;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.StringUtility;

public class UserValidator {
	
	static Integer id = null;
	static Student student = null;
	static Staff staff = null;
	static Teacher teacher = null;
	static Address address = null;
	static Parent parent = null;
	static String firstName  = null;		
	static String lastName = null;
	static String relationshipType = null;
	static String fullName = null;	
	static String middleNames = null;
	static Integer age = null;		
	static String gender = null;
	static String occupation = null;		
	static String birthDate = null;
	static String picture = null;
	static Integer studentNumber = null;
	static String emailAddress = null;
	static String phoneNumber = null;
	static Date today = null;	
	static List<Integer> parentIDs = null;
	static List<Integer> studentIDs = null;
	static Integer studentID = null;
	static Integer userId = null;
	static Integer programId = null;
	static Integer academicLevelId = null;
	static String userCode = null;
	
	public static Teacher validateTeacherInputInfo(Map<String, Object> params, List<String> requiredFields) {
		
		teacher = new Teacher();		
		
		if(params.containsKey(UserConstant.ID)) {
			id  = params.get(UserConstant.ID) != null ? (Integer) params.get(UserConstant.ID) : null;
		}
		
		if(params.containsKey(UserConstant.FIRST_NAME)) {
			firstName  = params.get(UserConstant.FIRST_NAME).toString();
		}
		
		if(params.containsKey(UserConstant.LAST_NAME)) {
			lastName  = params.get(UserConstant.LAST_NAME).toString();
		}
		
		if(params.containsKey(UserConstant.MIDDLE_NAMES)) {
			middleNames  = params.get(UserConstant.MIDDLE_NAMES).toString();
		}
		
		
		if(params.containsKey(UserConstant.FULL_NAME)) {
			fullName  = params.get(UserConstant.FULL_NAME).toString();
		}
		
		if(params.containsKey(UserConstant.AGE)) {
			age = (Integer) params.get(UserConstant.AGE);	
		}
		
		if(params.containsKey(UserConstant.USER_ID)) {
			userId = (Integer) params.get(UserConstant.USER_ID);	
		}
		
		if(params.containsKey(UserConstant.GENDER) && params.get(UserConstant.GENDER) != null) {
			gender  = params.get(UserConstant.GENDER).toString();
		}
		
		if(params.containsKey(UserConstant.OCCUPATION) && params.get(UserConstant.OCCUPATION) != null) {
			occupation = params.get(UserConstant.OCCUPATION).toString();
		}
		
		if(params.containsKey(UserConstant.BIRTH_DATE)) {
			birthDate = params.get(UserConstant.BIRTH_DATE).toString();
		}
		
		if(params.containsKey(UserConstant.OCCUPATION)) {
			occupation = (String) params.get(UserConstant.OCCUPATION);
		}
		
		if(params.containsKey(UserConstant.PICTURE)) {
			picture = (String) params.get(UserConstant.PICTURE);
		}
		
		if(params.containsKey(UserConstant.USER_CODE)) {
			userCode = (String) params.get(UserConstant.USER_CODE);
		}
		
		if(params.containsKey(UserConstant.EMAIL_ADDRESS)) {
			emailAddress = (String) params.get(UserConstant.EMAIL_ADDRESS);
		}
		
		if(params.containsKey(UserConstant.PHONE_NUMBER)) {
			phoneNumber = (String) params.get(UserConstant.PHONE_NUMBER);
		}
				
		if (age == null && requiredFields.contains(UserConstant.AGE)) {
			throw new FieldRequiredException("Age is required to continue");
		}
		teacher.setAge(age);
		
		if (userId == null && requiredFields.contains(UserConstant.USER_ID)) {
			throw new FieldRequiredException("User ID is required to continue");
		}
		teacher.setUserId(userId);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldRequiredException("Birth date is required to continue");
		}
		Date cal = CalendarUtil.convertStringToDate(birthDate);
		teacher.setBirthDate(cal);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldRequiredException("First Name is required to continue");
		}
		teacher.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldRequiredException("Last Name is required to continue");
		}
		teacher.setLastName(lastName);
		
		if (middleNames == null && requiredFields.contains(UserConstant.MIDDLE_NAMES)) {
			throw new FieldRequiredException("Middle Names are required to continue");
		}
		teacher.setMiddleNames(middleNames);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldRequiredException("Full Name is required to continue");
		}
		teacher.setFullName(fullName);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldRequiredException("Gender is required to continue");
		}
		teacher.setGender(gender);
		
		if (occupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldRequiredException("Occupation is required to continue");
		}
		teacher.setOccupation(occupation);
		
		if (userCode == null && requiredFields.contains(UserConstant.USER_CODE)) {
			throw new FieldRequiredException("User Code is required to continue");
		}
		teacher.setUserCode(userCode);
		
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldRequiredException("Picture is required to continue");
		}
		teacher.setPicture(picture);
		
		if (id == null && requiredFields.contains(UserConstant.ID)) {
			throw new FieldRequiredException("Teacher's ID is required to continue");
		}
		teacher.setId(id);
		
		if (emailAddress == null && requiredFields.contains(UserConstant.EMAIL_ADDRESS)) {
			throw new FieldRequiredException("Email Address is required to continue");
		}
		teacher.setEmailAddress(emailAddress);
		
		if (phoneNumber == null && requiredFields.contains(UserConstant.PHONE_NUMBER)) {
			throw new FieldRequiredException("Phone number is required to continue");
		}
		teacher.setPhoneNumber(phoneNumber);
		
		User newUser = User.validateAndMapAuthUser(getParams(params, UserConstant.USER_LOGIN_INPUT), RoleConstant.USER_ROLES.ROLE_TEACHER);
		teacher.setUser(newUser);
		
		address = AddressValidator.validateAddress(params, requiredFields);
		teacher.setAddress(address);
		today = CalendarUtil.getTodaysDate();
		
		teacher.setCreatedOn(today);
		teacher.setUpdatedOn(today);
		
		return teacher;
	}
	
	@SuppressWarnings("unchecked")
	public static Student validateStudentInputInfo(Map<String, Object> params, List<String> requiredFields) {
		
		student = new Student();
		
		if(params.containsKey(UserConstant.ID)) {
			id  = params.get(UserConstant.ID) != null ? (Integer) params.get(UserConstant.ID) : null;
		}
		
		if(params.containsKey(UserConstant.FIRST_NAME)) {
			firstName  = (String) params.get(UserConstant.FIRST_NAME);
		}
		
		if(params.containsKey(UserConstant.LAST_NAME)) {
			lastName  = (String) params.get(UserConstant.LAST_NAME);
		}
		
		if(params.containsKey(UserConstant.MIDDLE_NAMES)) {
			
			middleNames  = StringUtility.isEmpty((String)params.get(UserConstant.MIDDLE_NAMES)) ? null : params.get(UserConstant.MIDDLE_NAMES).toString();
		}
		
		if(params.containsKey(UserConstant.FULL_NAME)) {
			fullName  = (String) params.get(UserConstant.FULL_NAME);
		}
		
		if(params.containsKey(UserConstant.USER_ID)) {
			userId = (Integer) params.get(UserConstant.USER_ID);	
		}
		
		if(params.containsKey(UserConstant.PROGRAM_ID)) {
			programId = (Integer) params.get(UserConstant.PROGRAM_ID);	
		}
		
		if(params.containsKey(UserConstant.ACADEMIC_LEVEL_ID)) {
			academicLevelId = (Integer) params.get(UserConstant.ACADEMIC_LEVEL_ID);	
		}
		
		if(params.containsKey(UserConstant.AGE)) {
			age = (Integer) params.get(UserConstant.AGE);	
		}
		
		if(params.containsKey(UserConstant.GENDER)) {
			gender  = (String) params.get(UserConstant.GENDER);
		}
		
		if(params.containsKey(UserConstant.OCCUPATION)) {
			occupation = (String) params.get(UserConstant.OCCUPATION);
		}
		
		if(params.containsKey(UserConstant.BIRTH_DATE)) {
			birthDate = params.get(UserConstant.BIRTH_DATE).toString();
		}
		
		if(params.containsKey(UserConstant.OCCUPATION)) {
			occupation = (String) params.get(UserConstant.OCCUPATION);
		}
		
		if(params.containsKey(UserConstant.PICTURE)) {
			picture = (String) params.get(UserConstant.PICTURE);
		}
		
		if(params.containsKey(UserConstant.STUDENT_NUMBER)) {
			studentNumber = (Integer) params.get(UserConstant.STUDENT_NUMBER);
		}
		
		if(params.containsKey(UserConstant.EMAIL_ADDRESS)) {
			emailAddress = (String) params.get(UserConstant.EMAIL_ADDRESS);
		}
		
		if(params.containsKey(UserConstant.PHONE_NUMBER)) {
			phoneNumber = (String) params.get(UserConstant.PHONE_NUMBER);
		}
		
		if(params.containsKey(UserConstant.PARENT_IDS)) {
			parentIDs = (List<Integer>)params.get(UserConstant.PARENT_IDS);
		}
		
		if (age == null && requiredFields.contains(UserConstant.AGE)) {
			throw new FieldRequiredException("Age is required to continue");
		}
		student.setAge(age);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldRequiredException("Birth date is required to continue");
		}
		Date cal = CalendarUtil.convertStringToDate(birthDate);
		student.setBirthDate(cal);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldRequiredException("First Name is required to continue");
		}
		student.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldRequiredException("Last Name is required to continue");
		}
		student.setLastName(lastName);
		
		if (middleNames == null && requiredFields.contains(UserConstant.MIDDLE_NAMES)) {
			throw new FieldRequiredException("Middle Names are required to continue");
		}
		student.setMiddleNames(middleNames);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldRequiredException("Full Name is required to continue");
		}
		student.setFullName(fullName);
		
		if (userId == null && requiredFields.contains(UserConstant.USER_ID)) {
			throw new FieldRequiredException("User ID is required to continue");
		}
		student.setUserId(userId);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldRequiredException("Gender is required to continue");
		}
		student.setGender(gender);
		
		if (occupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldRequiredException("Occupation is required to continue");
		}
		student.setOccupation(occupation);
		
		if (studentNumber == null && requiredFields.contains(UserConstant.STUDENT_NUMBER)) {
			throw new FieldRequiredException("Student Number is required to continue");
		}
		student.setStudentNumber(studentNumber);		
				
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldRequiredException("Picture is required to continue");
		}
		student.setPicture(picture);
		
		if (emailAddress == null && requiredFields.contains(UserConstant.EMAIL_ADDRESS)) {
			throw new FieldRequiredException("Email Address is required to continue");
		}
		student.setEmailAddress(emailAddress);
		
		if (phoneNumber == null && requiredFields.contains(UserConstant.PHONE_NUMBER)) {
			throw new FieldRequiredException("Phone number is required to continue");
		}
		student.setPhoneNumber(phoneNumber);
		
		if (parentIDs == null && requiredFields.contains(UserConstant.PARENT_IDS)) {
			throw new FieldRequiredException("Parent ID is required to continue");
		}
		
		if (id == null && requiredFields.contains(UserConstant.ID)) {
			throw new FieldRequiredException("Student's ID is required to continue");
		}
		
		if (programId == null && requiredFields.contains(UserConstant.PROGRAM_ID)) {
			throw new FieldRequiredException("Program's ID is required to continue");
		}
		student.setProgramId(programId);
		
		if (academicLevelId == null && requiredFields.contains(UserConstant.ACADEMIC_LEVEL_ID)) {
			throw new FieldRequiredException("Academic Level's ID is required to continue");
		}
		student.setAcademicLevelId(academicLevelId);
		
		requiredFields.remove(UserConstant.BIRTH_DATE);
		requiredFields.remove(UserConstant.PROGRAM_ID);	
		requiredFields.remove(UserConstant.ACADEMIC_LEVEL_ID);	
		requiredFields.add(UserConstant.RELATIONSHIP_TYPE);
		
		student.setId(id);
		student.setPrimaryParent(validateParentInputInfo(UserValidator.getParams(params, ParentConstant.PARENT), requiredFields));
		student.setUser(User.validateAndMapAuthUser(getParams(params, UserConstant.USER_LOGIN_INPUT), RoleConstant.USER_ROLES.ROLE_STUDENT));
		student.setParentIds(parentIDs);
		address = AddressValidator.validateAddress(params, requiredFields);
		student.setAddress(address);
		today = CalendarUtil.getTodaysDate();
		student.setCreatedOn(today);
		student.setUpdatedOn(today);
		
		return student;
	}
	
	@SuppressWarnings("unchecked")
	public static Parent validateParentInputInfo(Map<String, Object> params, List<String> requiredFields) {
		
		parent = new Parent();
		
		params = getParams(params, ParentConstant.PARENT);
		
		if(params.containsKey(UserConstant.ID)) {
			id  = params.get(UserConstant.ID) != null ? (Integer) params.get(UserConstant.ID) : null;
		}
		
		if(params.containsKey(UserConstant.FIRST_NAME)) {
			firstName  = (String) params.get(UserConstant.FIRST_NAME);
		}
		
		if(params.containsKey(UserConstant.USER_ID)) {
			userId = (Integer) params.get(UserConstant.USER_ID);	
		}
		
		if(params.containsKey(UserConstant.LAST_NAME)) {
			lastName  = (String) params.get(UserConstant.LAST_NAME);
		}
		
		if(params.containsKey(UserConstant.MIDDLE_NAMES)) {
			middleNames  = params.get(UserConstant.MIDDLE_NAMES).toString();
		}
				
		if(params.containsKey(UserConstant.FULL_NAME)) {
			fullName  = (String) params.get(UserConstant.FULL_NAME);
		}
		
		if(params.containsKey(UserConstant.AGE)) {
			age = (Integer) params.get(UserConstant.AGE);	
		}
		
		if(params.containsKey(UserConstant.GENDER)) {
			gender  = (String) params.get(UserConstant.GENDER);
		}
		
		if(params.containsKey(UserConstant.OCCUPATION)) {
			occupation = (String) params.get(UserConstant.OCCUPATION);
		}
		
		
		if(params.containsKey(UserConstant.RELATIONSHIP_TYPE)) {
			relationshipType = (String) params.get(UserConstant.RELATIONSHIP_TYPE);
		}
		
		if(params.containsKey(UserConstant.BIRTH_DATE)) {
			birthDate = params.get(UserConstant.BIRTH_DATE).toString();
		}
		
		if(params.containsKey(UserConstant.OCCUPATION)) {
			occupation = (String) params.get(UserConstant.OCCUPATION);
		}
		
		if(params.containsKey(UserConstant.PICTURE)) {
			picture = (String) params.get(UserConstant.PICTURE);
		}
		
		if(params.containsKey(UserConstant.EMAIL_ADDRESS)) {
			emailAddress = (String) params.get(UserConstant.EMAIL_ADDRESS);
		}
		
		if(params.containsKey(UserConstant.PHONE_NUMBER)) {
			phoneNumber = (String) params.get(UserConstant.PHONE_NUMBER);
		}
		
		if(params.containsKey(UserConstant.STUDENT_IDS)) {
			studentIDs = (List<Integer>)params.get(UserConstant.STUDENT_IDS);
		}
		
		if(params.containsKey(UserConstant.STUDENT_ID)) {
			studentID = (Integer)params.get(UserConstant.STUDENT_ID);
		}
				
		if (age == null && requiredFields.contains(UserConstant.AGE)) {
			throw new FieldRequiredException("Age can not be empty");
		}
		parent.setAge(age);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldRequiredException("Birth date is required to continue");
		}
		Date cal = CalendarUtil.convertStringToDate(birthDate);
		parent.setBirthDate(cal);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldRequiredException("First Name is required to continue");
		}
		parent.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldRequiredException("Last Name is required to continue");
		}
		parent.setLastName(lastName);
		
		if (middleNames == null && requiredFields.contains(UserConstant.MIDDLE_NAMES)) {
			throw new FieldRequiredException("Middle Names are required to continue");
		}
		parent.setMiddleNames(middleNames);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldRequiredException("Full Name is required to continue");
		}
		parent.setFullName(fullName);
		
		if (userId == null && requiredFields.contains(UserConstant.USER_ID)) {
			throw new FieldRequiredException("User ID is required to continue");
		}
		parent.setUserId(userId);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldRequiredException("Gender is required to continue");
		}
		parent.setGender(gender);
		
		if (occupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldRequiredException("Occupation is required to continue");
		}
		parent.setOccupation(occupation);
		
		if (relationshipType == null && requiredFields.contains(UserConstant.RELATIONSHIP_TYPE)) {
			throw new FieldRequiredException("Relationship Type is required to continue");
		}
		parent.setRelationshipType(relationshipType);
				
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldRequiredException("Picture is required to continue");
		}
		parent.setPicture(picture);
		
		if (emailAddress == null && requiredFields.contains(UserConstant.EMAIL_ADDRESS)) {
			throw new FieldRequiredException("Email Address is required to continue");
		}
		parent.setEmailAddress(emailAddress);
		
		if (phoneNumber == null && requiredFields.contains(UserConstant.PHONE_NUMBER)) {
			throw new FieldRequiredException("Phone number is required to continue");
		}
		parent.setPhoneNumber(phoneNumber);
		
		if (studentIDs == null && requiredFields.contains(UserConstant.STUDENT_IDS)) {
			throw new FieldRequiredException("Student IDs are required to continue");
		}
		parent.setStudentIds(studentIDs);
		
		if (studentID == null && requiredFields.contains(UserConstant.STUDENT_ID)) {
			throw new FieldRequiredException("Student ID is required to continue");
		}
		parent.setStudentId(studentID);
		
		if (id == null && requiredFields.contains(UserConstant.ID)) {
			throw new FieldRequiredException("Parent's ID is required to continue");
		}
		parent.setId(id);
		
		User newUser = User.validateAndMapAuthUser(getParams(params, UserConstant.USER_LOGIN_INPUT), RoleConstant.USER_ROLES.ROLE_PARENT);
		today = CalendarUtil.getTodaysDate();
		address = AddressValidator.validateAddress(params, requiredFields);
		parent.setUser(newUser);
		parent.setAddress(address);
		parent.setCreatedOn(today);
		parent.setUpdatedOn(today);
		
		return parent;
	}

	public static Staff validateOtherStaffInputInfo(Map<String, Object> params, List<String> requiredFields) {
		
		staff = new Staff();
		
		if(params.containsKey(UserConstant.ID)) {
			id  = params.get(UserConstant.ID) != null ? (Integer) params.get(UserConstant.ID) : null;
		}
		
		if(params.containsKey(UserConstant.FIRST_NAME)) {
			firstName  = (String) params.get(UserConstant.FIRST_NAME);
		}
		
		if(params.containsKey(UserConstant.LAST_NAME)) {
			lastName  = (String) params.get(UserConstant.LAST_NAME);
		}
		
		if(params.containsKey(UserConstant.MIDDLE_NAMES)) {
			middleNames  = params.get(UserConstant.MIDDLE_NAMES).toString();
		}
		
		if(params.containsKey(UserConstant.FULL_NAME)) {
			fullName  = (String) params.get(UserConstant.FULL_NAME);
		}
		
		if(params.containsKey(UserConstant.USER_ID)) {
			userId = (Integer) params.get(UserConstant.USER_ID);	
		}
		
		if(params.containsKey(UserConstant.AGE)) {
			age = (Integer) params.get(UserConstant.AGE);	
		}
		
		if(params.containsKey(UserConstant.GENDER)) {
			gender  = (String) params.get(UserConstant.GENDER);
		}
		
		if(params.containsKey(UserConstant.OCCUPATION)) {
			occupation = (String) params.get(UserConstant.OCCUPATION);
		}
		
		if(params.containsKey(UserConstant.BIRTH_DATE)) {
			birthDate = params.get(UserConstant.BIRTH_DATE).toString();
		}
		
		if(params.containsKey(UserConstant.OCCUPATION)) {
			occupation = (String) params.get(UserConstant.OCCUPATION);
		}
		
		if(params.containsKey(UserConstant.PICTURE)) {
			picture = (String) params.get(UserConstant.PICTURE);
		}
		
		if(params.containsKey(UserConstant.EMAIL_ADDRESS)) {
			emailAddress = (String) params.get(UserConstant.EMAIL_ADDRESS);
		}
		
		if(params.containsKey(UserConstant.PHONE_NUMBER)) {
			phoneNumber = (String) params.get(UserConstant.PHONE_NUMBER);
		}
		
		if (age == null && requiredFields.contains(UserConstant.AGE)) {
			throw new FieldRequiredException("Age is required to continue");
		}
		staff.setAge(age);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldRequiredException("Birth Date is required to continue");
		}
		Date cal = CalendarUtil.convertStringToDate(birthDate);
		staff.setBirthDate(cal);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldRequiredException("First Name is required to continue");
		}
		staff.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldRequiredException("Last Name is required to continue");
		}
		staff.setLastName(lastName);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldRequiredException("Full Name is required to continue");
		}
		staff.setFullName(fullName);
		
		if (userId == null && requiredFields.contains(UserConstant.USER_ID)) {
			throw new FieldRequiredException("User ID is required to continue");
		}
		staff.setUserId(userId);
		
		if (middleNames == null && requiredFields.contains(UserConstant.MIDDLE_NAMES)) {
			throw new FieldRequiredException("Middle Names are required to continue");
		}
		staff.setMiddleNames(middleNames);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldRequiredException("Gender is required to continue");
		}
		staff.setGender(gender);
		
		if (occupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldRequiredException("Ocupation is required to continue");
		}
		staff.setOccupation(occupation);
				
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldRequiredException("Picture is required to continue");
		}
		staff.setPicture(picture);
		
		if (emailAddress == null && requiredFields.contains(UserConstant.EMAIL_ADDRESS)) {
			throw new FieldRequiredException("Email Address is required to continue");
		}
		staff.setEmailAddress(emailAddress);
		
		if (phoneNumber == null && requiredFields.contains(UserConstant.PHONE_NUMBER)) {
			throw new FieldRequiredException("Phone number is required to continue");
		}
		staff.setPhoneNumber(phoneNumber);
		
		if (id == null && requiredFields.contains(UserConstant.ID)) {
			throw new FieldRequiredException("Staff's ID is required to continue");
		}
		staff.setId(id);
		requiredFields.remove(UserConstant.ID);
		address = AddressValidator.validateAddress(params, requiredFields);
		staff.setAddress(address);
		Date today = CalendarUtil.getTodaysDate();
		
		User newUser = User.validateAndMapAuthUser(getParams(params, UserConstant.USER_LOGIN_INPUT), RoleConstant.USER_ROLES.ROLE_STAFF);
		staff.setUser(newUser);
		staff.setCreatedOn(today);
		staff.setUpdatedOn(today);
		
		return staff;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParams(Map<String, Object> params, String key){
		if(params.containsKey(key)) {
			return (Map<String, Object>)params.get(key);
		}
		return params;		
	}
}


