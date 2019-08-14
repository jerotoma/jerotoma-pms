package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.exceptions.FieldCanNotBeEmptyException;
import com.jerotoma.common.models.users.OtherStaff;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.common.models.users.Student;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.utils.CalendarUtil;

public class UserValidator {
	
	public static Teacher validateTeacherInputInfo(Map<String, Object> params, List<String> requiredFields) {
		
		Teacher teacher = new Teacher();
		String firstName  = null;		
		String lastName = null;
		String fullName = null;	
		Integer age = null;	
		Integer userId = null;	
		String gender = null;
		String occupation = null;		
		String birthDate = null;
		String picture = null;
		String teacherCode = null;		
		Date today = null;
		
		
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
				
		if (age == null && requiredFields.contains(UserConstant.AGE)) {
			throw new FieldCanNotBeEmptyException("Age can not be empty");
		}
		teacher.setAge(age);
		
		if (userId == null && requiredFields.contains(UserConstant.USER_ID)) {
			throw new FieldCanNotBeEmptyException("User ID can not be empty");
		}
		teacher.setUserId(userId);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldCanNotBeEmptyException("Birth date can not be empty");
		}
		Date cal = CalendarUtil.convertStringToDate(birthDate);
		teacher.setBirthDate(cal);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldCanNotBeEmptyException("First Name can not be empty");
		}
		teacher.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldCanNotBeEmptyException("Last Name can not be empty");
		}
		teacher.setLastName(lastName);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldCanNotBeEmptyException("Full Name can not be empty");
		}
		teacher.setFullName(fullName);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldCanNotBeEmptyException("Gender can not be empty");
		}
		teacher.setGender(gender);
		
		if (occupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldCanNotBeEmptyException("Occupation date can not be empty");
		}
		teacher.setOccupation(occupation);
		
		if (teacherCode == null && requiredFields.contains(UserConstant.EMPLOYMENT_CODE)) {
			throw new FieldCanNotBeEmptyException("Teacher Code date can not be empty");
		}
		teacher.setTeacherCode(teacherCode);
		
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldCanNotBeEmptyException("Picture date can not be empty");
		}
		teacher.setPicture(picture);
		
		
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
		String studentCode = null;
		String position = null;
		
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
		
		if(params.containsKey(UserConstant.STUDENT_CODE)) {
			studentCode = (String) params.get(UserConstant.STUDENT_CODE);
		}
		
		if(params.containsKey(UserConstant.POSITION)) {
			position = (String) params.get(UserConstant.POSITION);
		}
		
		if (age == null && requiredFields.contains(UserConstant.AGE)) {
			throw new FieldCanNotBeEmptyException("Age can not be empty");
		}
		student.setAge(age);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldCanNotBeEmptyException("Birth date can not be empty");
		}
		Date cal = CalendarUtil.convertStringToDate(birthDate);
		student.setBirthDate(cal);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldCanNotBeEmptyException("First Name can not be empty");
		}
		student.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldCanNotBeEmptyException("Last Name can not be empty");
		}
		student.setLastName(lastName);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldCanNotBeEmptyException("Full Name can not be empty");
		}
		student.setFullName(fullName);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldCanNotBeEmptyException("Gender can not be empty");
		}
		student.setGender(gender);
		
		if (occupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldCanNotBeEmptyException("Occupation date can not be empty");
		}
		student.setOccupation(occupation);
		
		if (studentCode == null && requiredFields.contains(UserConstant.STUDENT_CODE)) {
			throw new FieldCanNotBeEmptyException("Student code can not be empty");
		}
		student.setStudentCode(studentCode);		
				
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldCanNotBeEmptyException("Picture date can not be empty");
		}
		student.setPicture(picture);
		
		if (position == null && requiredFields.contains(UserConstant.POSITION)) {
			throw new FieldCanNotBeEmptyException("Position date can not be empty");
		}
		student.setPosition(position);
		
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
		String position = null;
		
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
		
		if(params.containsKey(UserConstant.POSITION)) {
			position = (String) params.get(UserConstant.POSITION);
		}
		
		if (age == null && requiredFields.contains(UserConstant.AGE)) {
			throw new FieldCanNotBeEmptyException("Age can not be empty");
		}
		parent.setAge(age);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldCanNotBeEmptyException("Birth date can not be empty");
		}
		parent.setBirthDate(birthDate);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldCanNotBeEmptyException("First Name can not be empty");
		}
		parent.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldCanNotBeEmptyException("Last Name can not be empty");
		}
		parent.setLastName(lastName);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldCanNotBeEmptyException("Full Name can not be empty");
		}
		parent.setFullName(fullName);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldCanNotBeEmptyException("Gender can not be empty");
		}
		parent.setGender(gender);
		
		if (occupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldCanNotBeEmptyException("Occupation date can not be empty");
		}
		parent.setOccupation(occupation);
				
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldCanNotBeEmptyException("Picture date can not be empty");
		}
		parent.setPicture(picture);
		
		if (position == null && requiredFields.contains(UserConstant.POSITION)) {
			throw new FieldCanNotBeEmptyException("Position date can not be empty");
		}
		parent.setPosition(position);
		
		Date today = CalendarUtil.getTodaysDate();
		
		parent.setCreatedOn(today);
		parent.setUpdatedOn(today);
		
		return parent;
	}

	public static OtherStaff validateOtherStaffInputInfo(Map<String, Object> params, List<String> requiredFields) {
		
		OtherStaff otherStaff = new OtherStaff();
		String firstName  = null;		
		String lastName = null;
		String fullName = null;	
		Integer age = null;		
		String gender = null;
		String ocupation = null;		
		Date birthDate = null;
		String picture = null;
		String position = null;
		
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
			throw new FieldCanNotBeEmptyException("Age can not be empty");
		}
		otherStaff.setAge(age);
		
		if (birthDate == null && requiredFields.contains(UserConstant.BIRTH_DATE)) {
			throw new FieldCanNotBeEmptyException("Birth date can not be empty");
		}
		otherStaff.setBirthDate(birthDate);
		
		if (firstName == null && requiredFields.contains(UserConstant.FIRST_NAME)) {
			throw new FieldCanNotBeEmptyException("First Name can not be empty");
		}
		otherStaff.setFirstName(firstName);
		
		if (lastName == null && requiredFields.contains(UserConstant.LAST_NAME)) {
			throw new FieldCanNotBeEmptyException("Last Name can not be empty");
		}
		otherStaff.setLastName(lastName);
		
		if (fullName == null && requiredFields.contains(UserConstant.FULL_NAME)) {
			throw new FieldCanNotBeEmptyException("Full Name can not be empty");
		}
		otherStaff.setFullName(fullName);
		
		if (gender == null && requiredFields.contains(UserConstant.GENDER)) {
			throw new FieldCanNotBeEmptyException("Gender can not be empty");
		}
		otherStaff.setGender(gender);
		
		if (ocupation == null && requiredFields.contains(UserConstant.OCCUPATION)) {
			throw new FieldCanNotBeEmptyException("Ocupation date can not be empty");
		}
		otherStaff.setOccupation(ocupation);
				
		if (picture == null && requiredFields.contains(UserConstant.PICTURE)) {
			throw new FieldCanNotBeEmptyException("Picture date can not be empty");
		}
		otherStaff.setPicture(picture);
		
		if (position == null && requiredFields.contains(UserConstant.POSITION)) {
			throw new FieldCanNotBeEmptyException("Position date can not be empty");
		}
		otherStaff.setPosition(position);
		
		Date today = CalendarUtil.getTodaysDate();
		
		otherStaff.setCreatedOn(today);
		otherStaff.setUpdatedOn(today);
		
		return otherStaff;
	}
}
