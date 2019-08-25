package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.exceptions.FieldCanNotBeEmptyException;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.common.models.addresses.Address;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.common.models.users.Student;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.utils.CalendarUtil;

public class UserValidator {
	
	public static Teacher validateTeacherInputInfo(Map<String, Object> params, List<String> requiredFields) {
		
		Teacher teacher = new Teacher();
		Integer id = null;
		String firstName  = null;		
		String lastName = null;
		String fullName = null;	
		Integer age = null;	
		Integer userId = null;	
		Address address = null;
		String gender = null;
		String occupation = null;		
		String birthDate = null;
		String picture = null;
		String teacherCode = null;		
		Date today = null;		
		String emailAddress = null;
		String phoneNumber = null;
		
		if(params.containsKey(UserConstant.ID)) {
			id  = params.get(UserConstant.ID) != null ? (Integer) params.get(UserConstant.ID) : null;
		}
		
		if(params.containsKey(UserConstant.FIRST_NAME)) {
			firstName  = params.get(UserConstant.FIRST_NAME).toString();
		}
		
		if(params.containsKey(UserConstant.LAST_NAME)) {
			lastName  = params.get(UserConstant.LAST_NAME).toString();
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
		
		if(params.containsKey(UserConstant.EMPLOYMENT_CODE)) {
			teacherCode = (String) params.get(UserConstant.EMPLOYMENT_CODE);
		}
		
		if(params.containsKey(UserConstant.EMAIL_ADDRESS)) {
			emailAddress = (String) params.get(UserConstant.EMAIL_ADDRESS);
		}
		
		if(params.containsKey(UserConstant.PHONE_NUMBER)) {
			phoneNumber = (String) params.get(UserConstant.PHONE_NUMBER);
		}
				
		if (age == null && requiredFields.contains(UserConstant.AGE)) {
			throw new FieldCanNotBeEmptyException("Age is required to continue");
		}
		teacher.setAge(age);
		
		if (userId == null && requiredFields.contains(UserConstant.USER_ID)) {
			throw new FieldCanNotBeEmptyException("User ID is required to continue");
		}
		teacher.setUserId(userId);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldCanNotBeEmptyException("Birth date is required to continue");
		}
		Date cal = CalendarUtil.convertStringToDate(birthDate);
		teacher.setBirthDate(cal);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldCanNotBeEmptyException("First Name is required to continue");
		}
		teacher.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldCanNotBeEmptyException("Last Name is required to continue");
		}
		teacher.setLastName(lastName);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldCanNotBeEmptyException("Full Name is required to continue");
		}
		teacher.setFullName(fullName);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldCanNotBeEmptyException("Gender is required to continue");
		}
		teacher.setGender(gender);
		
		if (occupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldCanNotBeEmptyException("Occupation is required to continue");
		}
		teacher.setOccupation(occupation);
		
		if (teacherCode == null && requiredFields.contains(UserConstant.EMPLOYMENT_CODE)) {
			throw new FieldCanNotBeEmptyException("Teacher Code is required to continue");
		}
		teacher.setTeacherCode(teacherCode);
		
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldCanNotBeEmptyException("Picture is required to continue");
		}
		teacher.setPicture(picture);
		
		if (id == null && requiredFields.contains(UserConstant.ID)) {
			throw new FieldCanNotBeEmptyException("Teacher's ID is required to continue");
		}
		teacher.setId(id);
		
		if (emailAddress == null && requiredFields.contains(UserConstant.EMAIL_ADDRESS)) {
			throw new FieldCanNotBeEmptyException("Email Address is required to continue");
		}
		teacher.setEmailAddress(emailAddress);
		
		if (phoneNumber == null && requiredFields.contains(UserConstant.PHONE_NUMBER)) {
			throw new FieldCanNotBeEmptyException("Phone number is required to continue");
		}
		teacher.setPhoneNumber(phoneNumber);
		
		address = AddressValidator.validateAddress(params, requiredFields);
		teacher.setAddress(address);
		today = CalendarUtil.getTodaysDate();
		
		teacher.setCreatedOn(today);
		teacher.setUpdatedOn(today);
		
		return teacher;
	}
	
	public static Student validateStudentInputInfo(Map<String, Object> params, List<String> requiredFields) {
		
		Student student = new Student();
		String firstName  = null;		
		String lastName = null;
		String fullName = null;	
		Integer age = null;		
		String gender = null;
		String occupation = null;		
		String birthDate = null;
		String picture = null;
		Integer studentNumber = null;
		String emailAddress = null;
		String phoneNumber = null;
		
		Address address = null;
		
		if(params.containsKey(UserConstant.FIRST_NAME)) {
			firstName  = (String) params.get(UserConstant.FIRST_NAME);
		}
		
		if(params.containsKey(UserConstant.LAST_NAME)) {
			lastName  = (String) params.get(UserConstant.LAST_NAME);
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
		
		if (age == null && requiredFields.contains(UserConstant.AGE)) {
			throw new FieldCanNotBeEmptyException("Age is required to continue");
		}
		student.setAge(age);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldCanNotBeEmptyException("Birth date is required to continue");
		}
		Date cal = CalendarUtil.convertStringToDate(birthDate);
		student.setBirthDate(cal);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldCanNotBeEmptyException("First Name is required to continue");
		}
		student.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldCanNotBeEmptyException("Last Name is required to continue");
		}
		student.setLastName(lastName);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldCanNotBeEmptyException("Full Name is required to continue");
		}
		student.setFullName(fullName);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldCanNotBeEmptyException("Gender is required to continue");
		}
		student.setGender(gender);
		
		if (occupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldCanNotBeEmptyException("Occupation is required to continue");
		}
		student.setOccupation(occupation);
		
		if (studentNumber == null && requiredFields.contains(UserConstant.STUDENT_NUMBER)) {
			throw new FieldCanNotBeEmptyException("Student Number is required to continue");
		}
		student.setStudentNumber(studentNumber);		
				
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldCanNotBeEmptyException("Picture is required to continue");
		}
		student.setPicture(picture);
		
		if (emailAddress == null && requiredFields.contains(UserConstant.EMAIL_ADDRESS)) {
			throw new FieldCanNotBeEmptyException("Email Address is required to continue");
		}
		student.setEmailAddress(emailAddress);
		
		if (phoneNumber == null && requiredFields.contains(UserConstant.PHONE_NUMBER)) {
			throw new FieldCanNotBeEmptyException("Phone number is required to continue");
		}
		student.setPhoneNumber(phoneNumber);
		address = AddressValidator.validateAddress(params, requiredFields);
		student.setAddress(address);
		Date today = CalendarUtil.getTodaysDate();
		student.setCreatedOn(today);
		student.setUpdatedOn(today);
		
		return student;
	}
	
	public static Parent validateParentInputInfo(Map<String, Object> params, List<String> requiredFields) {
		
		Parent parent = new Parent();
		String firstName  = null;		
		String lastName = null;
		String fullName = null;	
		Integer age = null;		
		String gender = null;
		String occupation = null;		
		Date birthDate = null;
		String picture = null;
		
		Address address = null;
		
		if(params.containsKey(UserConstant.FIRST_NAME)) {
			firstName  = (String) params.get(UserConstant.FIRST_NAME);
		}
		
		if(params.containsKey(UserConstant.LAST_NAME)) {
			lastName  = (String) params.get(UserConstant.LAST_NAME);
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
			birthDate = (Date) params.get(UserConstant.BIRTH_DATE);
		}
		
		if(params.containsKey(UserConstant.OCCUPATION)) {
			occupation = (String) params.get(UserConstant.OCCUPATION);
		}
		
		if(params.containsKey(UserConstant.PICTURE)) {
			picture = (String) params.get(UserConstant.PICTURE);
		}
				
		if (age == null && requiredFields.contains(UserConstant.AGE)) {
			throw new FieldCanNotBeEmptyException("Age can not be empty");
		}
		parent.setAge(age);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldCanNotBeEmptyException("Birth date is required to continue");
		}
		parent.setBirthDate(birthDate);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldCanNotBeEmptyException("First Name is required to continue");
		}
		parent.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldCanNotBeEmptyException("Last Name is required to continue");
		}
		parent.setLastName(lastName);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldCanNotBeEmptyException("Full Name is required to continue");
		}
		parent.setFullName(fullName);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldCanNotBeEmptyException("Gender is required to continue");
		}
		parent.setGender(gender);
		
		if (occupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldCanNotBeEmptyException("Occupation is required to continue");
		}
		parent.setOccupation(occupation);
				
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldCanNotBeEmptyException("Picture is required to continue");
		}
		parent.setPicture(picture);
		
		Date today = CalendarUtil.getTodaysDate();
		address = AddressValidator.validateAddress(params, requiredFields);
		parent.setAddress(address);
		parent.setCreatedOn(today);
		parent.setUpdatedOn(today);
		
		return parent;
	}

	public static Staff validateOtherStaffInputInfo(Map<String, Object> params, List<String> requiredFields) {
		
		Staff staff = new Staff();
		String firstName  = null;		
		String lastName = null;
		String fullName = null;	
		Integer age = null;		
		String gender = null;
		String ocupation = null;		
		Date birthDate = null;
		String picture = null;
		String position = null;
		Address address = null;
		
		if(params.containsKey(UserConstant.FIRST_NAME)) {
			firstName  = (String) params.get(UserConstant.FIRST_NAME);
		}
		
		if(params.containsKey(UserConstant.LAST_NAME)) {
			lastName  = (String) params.get(UserConstant.LAST_NAME);
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
			ocupation = (String) params.get(UserConstant.OCCUPATION);
		}
		
		if(params.containsKey(UserConstant.BIRTH_DATE)) {
			birthDate = (Date) params.get(UserConstant.BIRTH_DATE);
		}
		
		if(params.containsKey(UserConstant.OCCUPATION)) {
			ocupation = (String) params.get(UserConstant.OCCUPATION);
		}
		
		if(params.containsKey(UserConstant.PICTURE)) {
			picture = (String) params.get(UserConstant.PICTURE);
		}
		
		if(params.containsKey(UserConstant.POSITION)) {
			position = (String) params.get(UserConstant.POSITION);
		}
		
		if (age == null && requiredFields.contains(UserConstant.AGE)) {
			throw new FieldCanNotBeEmptyException("Age is required to continue");
		}
		staff.setAge(age);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldCanNotBeEmptyException("Birth Date is required to continue");
		}
		staff.setBirthDate(birthDate);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldCanNotBeEmptyException("First Name is required to continue");
		}
		staff.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldCanNotBeEmptyException("Last Name is required to continue");
		}
		staff.setLastName(lastName);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldCanNotBeEmptyException("Full Name is required to continue");
		}
		staff.setFullName(fullName);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldCanNotBeEmptyException("Gender is required to continue");
		}
		staff.setGender(gender);
		
		if (ocupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldCanNotBeEmptyException("Ocupation is required to continue");
		}
		staff.setOccupation(ocupation);
				
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldCanNotBeEmptyException("Picture is required to continue");
		}
		staff.setPicture(picture);
		
		if (position == null && requiredFields.contains(UserConstant.POSITION)) {
			throw new FieldCanNotBeEmptyException("Position is required to continue");
		}
		staff.setPosition(position);
		address = AddressValidator.validateAddress(params, requiredFields);
		staff.setAddress(address);
		Date today = CalendarUtil.getTodaysDate();
		
		staff.setCreatedOn(today);
		staff.setUpdatedOn(today);
		
		return staff;
	}
}


