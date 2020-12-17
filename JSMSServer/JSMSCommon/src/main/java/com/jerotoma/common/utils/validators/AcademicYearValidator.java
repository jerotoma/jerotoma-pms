package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.AcademicYearConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.common.utils.CalendarUtil;

public class AcademicYearValidator {
	
	public static AcademicYear validate(Map<String, Object> params, List<String> requiredFields) {
		
		AcademicYear academicYear = new AcademicYear();
		String name  = null;		
		String description = null;
		String code = null;	
		Integer id = null;	
		String yearOfStudy = null;
		
		
		if(params.containsKey(AcademicYearConstant.ACADEMIC_YEAR_NAME)) {
			name  = params.get(AcademicYearConstant.ACADEMIC_YEAR_NAME).toString();
		}
		if(params.containsKey(AcademicYearConstant.ACADEMIC_YEAR_DESCRIPTION)) {
			description  = params.get(AcademicYearConstant.ACADEMIC_YEAR_DESCRIPTION).toString();
		}
		
		if(params.containsKey(AcademicYearConstant.ACADEMIC_YEAR_CODE)) {
			code  = params.get(AcademicYearConstant.ACADEMIC_YEAR_CODE).toString();
		}
		
		if(params.containsKey(AcademicYearConstant.ACADEMIC_YEAR_OF_STUDY)) {
			yearOfStudy = params.get(AcademicYearConstant.ACADEMIC_YEAR_OF_STUDY).toString();
		}
		if(params.containsKey(AcademicYearConstant.ACADEMIC_YEAR_ID)) {
			id  = (Integer)params.get(AcademicYearConstant.ACADEMIC_YEAR_ID);
		}
		
		if (id == null && requiredFields.contains(AcademicYearConstant.ACADEMIC_YEAR_ID)) {
			throw new FieldRequiredException("ID is required to continue");
		}
		academicYear.setId(id);
		
		if (name == null && requiredFields.contains(AcademicYearConstant.ACADEMIC_YEAR_NAME)) {
			throw new FieldRequiredException("Name is required to continue");
		}
		academicYear.setName(name);
		
		if (description == null && requiredFields.contains(AcademicYearConstant.ACADEMIC_YEAR_DESCRIPTION)) {
			throw new FieldRequiredException("Description is required to continue");
		}
		academicYear.setDescription(description);
		
		if (code == null && requiredFields.contains(AcademicYearConstant.ACADEMIC_YEAR_CODE)) {
			throw new FieldRequiredException("Code is required to continue");
		}
		academicYear.setCode(code);
		
		if (yearOfStudy == null && requiredFields.contains(AcademicYearConstant.ACADEMIC_YEAR_OF_STUDY)) {
			throw new FieldRequiredException("Year of Study is required to continue");
		}
		academicYear.setYearOfStudy(yearOfStudy);
		
		
		
		Date today = CalendarUtil.getTodaysDate();		
		academicYear.setCreatedOn(today);
		academicYear.setUpdatedOn(today);
		
		return academicYear;
	}
}
