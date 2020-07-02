package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.CourseConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.academic.Course;
import com.jerotoma.common.utils.CalendarUtil;

public class CourseValidator {

	public static Course validate(Map<String, Object> params, List<String> requiredFields) {
		
		Course course = new Course();
		String name  = null;		
		String description = null;
		String code = null;	
		Integer id = null;	
		Integer academicLevelId = null;
		Integer programId = null;
		Integer departmentId = null;
				
		if(params.containsKey(CourseConstant.COURSE_NAME)) {
			name  = params.get(CourseConstant.COURSE_NAME).toString();
		}
		if(params.containsKey(CourseConstant.COURSE_DESCRIPTION)) {
			description  = params.get(CourseConstant.COURSE_DESCRIPTION).toString();
		}
		
		if(params.containsKey(CourseConstant.COURSE_CODE)) {
			code  = params.get(CourseConstant.COURSE_CODE).toString();
		}
		if(params.containsKey(CourseConstant.COURSE_ID)) {
			id  = (Integer)params.get(CourseConstant.COURSE_ID);
		}
		
		if(params.containsKey(CourseConstant.ACADEMIC_LEVEL_ID)) {
			academicLevelId  = (Integer)params.get(CourseConstant.ACADEMIC_LEVEL_ID);
		}
		
		if(params.containsKey(CourseConstant.PROGRAM_ID)) {
			programId  = (Integer)params.get(CourseConstant.PROGRAM_ID);
		}
		
		if(params.containsKey(CourseConstant.DEPARTMENT_ID)) {
			departmentId = (Integer)params.get(CourseConstant.DEPARTMENT_ID);
		}
		
		if (id == null && requiredFields.contains(CourseConstant.COURSE_ID)) {
			throw new FieldRequiredException("ID is required to continue");
		}
		course.setId(id);
		
		if (academicLevelId == null && requiredFields.contains(CourseConstant.ACADEMIC_LEVEL_ID)) {
			throw new FieldRequiredException("Academic Level ID is required to continue");
		}
		course.setAcademicLevelId(academicLevelId);
		
		if (programId == null && requiredFields.contains(CourseConstant.PROGRAM_ID)) {
			throw new FieldRequiredException("Program ID is required to continue");
		}
		course.setAcademicLevelId(academicLevelId);
		
		
		if (name == null && requiredFields.contains(CourseConstant.COURSE_NAME)) {
			throw new FieldRequiredException("Name is required to continue");
		}
		course.setName(name);
		
		if (description == null && requiredFields.contains(CourseConstant.COURSE_DESCRIPTION)) {
			throw new FieldRequiredException("Description is required to continue");
		}
		course.setDescription(description);
		
		if (code == null && requiredFields.contains(CourseConstant.COURSE_CODE)) {
			throw new FieldRequiredException("Code is required to continue");
		}
		course.setCode(code);
		
		if (departmentId == null && requiredFields.contains(CourseConstant.DEPARTMENT_ID)) {
			throw new FieldRequiredException("Department ID is required to continue");
		}
		course.setDepartmentID(departmentId);		
		
		Date today = CalendarUtil.getTodaysDate();		
		course.setCreatedOn(today);
		course.setUpdatedOn(today);
		
		return course;
	}

}
