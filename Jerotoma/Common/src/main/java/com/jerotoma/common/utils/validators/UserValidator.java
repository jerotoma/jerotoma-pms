package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.models.addresses.Address;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.common.models.users.Student;
import com.jerotoma.common.models.users.Teacher;
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
	static Integer userId = null;
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
		
		if(params.containsKey(UserConstant.GENDER)) {
			gender  = params.get(UserConstant.GENDER).toString();
		}
		
		if(params.containsKey(UserConstant.OCCUPATION)) {
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
			throw new FieldIsRequiredException("Age is required to continue");
		}
		teacher.setAge(age);
		
		if (userId == null && requiredFields.contains(UserConstant.USER_ID)) {
			throw new FieldIsRequiredException("User ID is required to continue");
		}
		teacher.setUserId(userId);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldIsRequiredException("Birth date is required to continue");
		}
		Date cal = CalendarUtil.convertStringToDate(birthDate);
		teacher.setBirthDate(cal);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldIsRequiredException("First Name is required to continue");
		}
		teacher.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldIsRequiredException("Last Name is required to continue");
		}
		teacher.setLastName(lastName);
		
		if (middleNames == null && requiredFields.contains(UserConstant.MIDDLE_NAMES)) {
			throw new FieldIsRequiredException("Middle Names are required to continue");
		}
		teacher.setMiddleNames(middleNames);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldIsRequiredException("Full Name is required to continue");
		}
		teacher.setFullName(fullName);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldIsRequiredException("Gender is required to continue");
		}
		teacher.setGender(gender);
		
		if (occupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldIsRequiredException("Occupation is required to continue");
		}
		teacher.setOccupation(occupation);
		
		if (userCode == null && requiredFields.contains(UserConstant.USER_CODE)) {
			throw new FieldIsRequiredException("User Code is required to continue");
		}
		teacher.setUserCode(userCode);
		
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldIsRequiredException("Picture is required to continue");
		}
		teacher.setPicture(picture);
		
		if (id == null && requiredFields.contains(UserConstant.ID)) {
			throw new FieldIsRequiredException("Teacher's ID is required to continue");
		}
		teacher.setId(id);
		
		if (emailAddress == null && requiredFields.contains(UserConstant.EMAIL_ADDRESS)) {
			throw new FieldIsRequiredException("Email Address is required to continue");
		}
		teacher.setEmailAddress(emailAddress);
		
		if (phoneNumber == null && requiredFields.contains(UserConstant.PHONE_NUMBER)) {
			throw new FieldIsRequiredException("Phone number is required to continue");
		}
		teacher.setPhoneNumber(phoneNumber);
		
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
			throw new FieldIsRequiredException("Age is required to continue");
		}
		student.setAge(age);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldIsRequiredException("Birth date is required to continue");
		}
		Date cal = CalendarUtil.convertStringToDate(birthDate);
		student.setBirthDate(cal);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldIsRequiredException("First Name is required to continue");
		}
		student.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldIsRequiredException("Last Name is required to continue");
		}
		student.setLastName(lastName);
		
		if (middleNames == null && requiredFields.contains(UserConstant.MIDDLE_NAMES)) {
			throw new FieldIsRequiredException("Middle Names are required to continue");
		}
		student.setMiddleNames(middleNames);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldIsRequiredException("Full Name is required to continue");
		}
		student.setFullName(fullName);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldIsRequiredException("Gender is required to continue");
		}
		student.setGender(gender);
		
		if (occupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldIsRequiredException("Occupation is required to continue");
		}
		student.setOccupation(occupation);
		
		if (studentNumber == null && requiredFields.contains(UserConstant.STUDENT_NUMBER)) {
			throw new FieldIsRequiredException("Student Number is required to continue");
		}
		student.setStudentNumber(studentNumber);		
				
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldIsRequiredException("Picture is required to continue");
		}
		student.setPicture(picture);
		
		if (emailAddress == null && requiredFields.contains(UserConstant.EMAIL_ADDRESS)) {
			throw new FieldIsRequiredException("Email Address is required to continue");
		}
		student.setEmailAddress(emailAddress);
		
		if (phoneNumber == null && requiredFields.contains(UserConstant.PHONE_NUMBER)) {
			throw new FieldIsRequiredException("Phone number is required to continue");
		}
		student.setPhoneNumber(phoneNumber);
		
		if (parentIDs == null && requiredFields.contains(UserConstant.PARENT_IDS)) {
			throw new FieldIsRequiredException("Parent ID is required to continue");
		}
		if (id == null && requiredFields.contains(UserConstant.ID)) {
			throw new FieldIsRequiredException("Student's ID is required to continue");
		}
		student.setId(id);
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
		
		if(params.containsKey(UserConstant.STUDENT_IDS)) {
			studentIDs = (List<Integer>)params.get(UserConstant.STUDENT_IDS);
		}
				
		if (age == null && requiredFields.contains(UserConstant.AGE)) {
			throw new FieldIsRequiredException("Age can not be empty");
		}
		parent.setAge(age);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldIsRequiredException("Birth date is required to continue");
		}
		Date cal = CalendarUtil.convertStringToDate(birthDate);
		parent.setBirthDate(cal);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldIsRequiredException("First Name is required to continue");
		}
		parent.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldIsRequiredException("Last Name is required to continue");
		}
		parent.setLastName(lastName);
		
		if (middleNames == null && requiredFields.contains(UserConstant.MIDDLE_NAMES)) {
			throw new FieldIsRequiredException("Middle Names are required to continue");
		}
		parent.setMiddleNames(middleNames);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldIsRequiredException("Full Name is required to continue");
		}
		parent.setFullName(fullName);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldIsRequiredException("Gender is required to continue");
		}
		parent.setGender(gender);
		
		if (occupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldIsRequiredException("Occupation is required to continue");
		}
		parent.setOccupation(occupation);
				
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldIsRequiredException("Picture is required to continue");
		}
		parent.setPicture(picture);
		
		if (emailAddress == null && requiredFields.contains(UserConstant.EMAIL_ADDRESS)) {
			throw new FieldIsRequiredException("Email Address is required to continue");
		}
		parent.setEmailAddress(emailAddress);
		
		if (phoneNumber == null && requiredFields.contains(UserConstant.PHONE_NUMBER)) {
			throw new FieldIsRequiredException("Phone number is required to continue");
		}
		parent.setPhoneNumber(phoneNumber);
		
		if (studentIDs == null && requiredFields.contains(UserConstant.STUDENT_IDS)) {
			throw new FieldIsRequiredException("Student ID is required to continue");
		}
		parent.setStudentIds(studentIDs);
		
		if (id == null && requiredFields.contains(UserConstant.ID)) {
			throw new FieldIsRequiredException("Parent's ID is required to continue");
		}
		parent.setId(id);
		
		today = CalendarUtil.getTodaysDate();
		address = AddressValidator.validateAddress(params, requiredFields);
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
			throw new FieldIsRequiredException("Age is required to continue");
		}
		staff.setAge(age);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldIsRequiredException("Birth Date is required to continue");
		}
		Date cal = CalendarUtil.convertStringToDate(birthDate);
		staff.setBirthDate(cal);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldIsRequiredException("First Name is required to continue");
		}
		staff.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldIsRequiredException("Last Name is required to continue");
		}
		staff.setLastName(lastName);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldIsRequiredException("Full Name is required to continue");
		}
		staff.setFullName(fullName);
		
		if (middleNames == null && requiredFields.contains(UserConstant.MIDDLE_NAMES)) {
			throw new FieldIsRequiredException("Middle Names are required to continue");
		}
		staff.setMiddleNames(middleNames);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldIsRequiredException("Gender is required to continue");
		}
		staff.setGender(gender);
		
		if (occupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldIsRequiredException("Ocupation is required to continue");
		}
		staff.setOccupation(occupation);
				
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldIsRequiredException("Picture is required to continue");
		}
		staff.setPicture(picture);
		
		if (emailAddress == null && requiredFields.contains(UserConstant.EMAIL_ADDRESS)) {
			throw new FieldIsRequiredException("Email Address is required to continue");
		}
		staff.setEmailAddress(emailAddress);
		
		if (phoneNumber == null && requiredFields.contains(UserConstant.PHONE_NUMBER)) {
			throw new FieldIsRequiredException("Phone number is required to continue");
		}
		staff.setPhoneNumber(phoneNumber);
		
		if (id == null && requiredFields.contains(UserConstant.ID)) {
			throw new FieldIsRequiredException("Staff's ID is required to continue");
		}
		staff.setId(id);
			
		address = AddressValidator.validateAddress(params, requiredFields);
		staff.setAddress(address);
		Date today = CalendarUtil.getTodaysDate();
		
		staff.setCreatedOn(today);
		staff.setUpdatedOn(today);
		
		return staff;
	}
}


