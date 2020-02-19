package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.CourseConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.models.academic.Course;
import com.jerotoma.common.utils.CalendarUtil;

public class CourseValidator {

	public static Course validate(Map<String, Object> params, List<String> requiredFields) {
		
		Course course = new Course();
		String name  = null;		
		String description = null;
		String code = null;	
		Integer id = null;	
		Integer academicYearId = null;	
		
		
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
		
		if(params.containsKey(CourseConstant.ACADEMIC_YEAR_ID)) {
			academicYearId  = (Integer)params.get(CourseConstant.ACADEMIC_YEAR_ID);
		}
		
		if (id == null && requiredFields.contains(CourseConstant.COURSE_ID)) {
			throw new FieldIsRequiredException("ID is required to continue");
		}
		course.setId(id);
		
		if (academicYearId == null && requiredFields.contains(CourseConstant.ACADEMIC_YEAR_ID)) {
			throw new FieldIsRequiredException("Academic Year ID is required to continue");
		}
		course.setAcademicYearId(academicYearId);
		
		
		if (name == null && requiredFields.contains(CourseConstant.COURSE_NAME)) {
			throw new FieldIsRequiredException("Name is required to continue");
		}
		course.setName(name);
		
		if (description == null && requiredFields.contains(CourseConstant.COURSE_DESCRIPTION)) {
			throw new FieldIsRequiredException("Description is required to continue");
		}
		course.setDescription(description);
		
		if (code == null && requiredFields.contains(CourseConstant.COURSE_CODE)) {
			throw new FieldIsRequiredException("Code is required to continue");
		}
		course.setCode(code);
		
		
		
		Date today = CalendarUtil.getTodaysDate();		
		course.setCreatedOn(today);
		course.setUpdatedOn(today);
		
		return course;
	}

}
