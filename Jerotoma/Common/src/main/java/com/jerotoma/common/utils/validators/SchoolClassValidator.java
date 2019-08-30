package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.SchoolClassConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.models.academic.SchoolClass;
import com.jerotoma.common.utils.CalendarUtil;

public class SchoolClassValidator {
	
	public static SchoolClass validate(Map<String, Object> params, List<String> requiredFields) {
		
		SchoolClass schoolClass = new SchoolClass();
		String name  = null;		
		String description = null;
		String code = null;	
		Integer id = null;		
		Integer capacity = null;	
		
		
		if(params.containsKey(SchoolClassConstant.SCHOOL_CLASS_NAME)) {
			name  = params.get(SchoolClassConstant.SCHOOL_CLASS_NAME).toString();
		}
		if(params.containsKey(SchoolClassConstant.SCHOOL_CLASS_DESCRIPTION)) {
			description  = params.get(SchoolClassConstant.SCHOOL_CLASS_DESCRIPTION).toString();
		}
		
		if(params.containsKey(SchoolClassConstant.SCHOOL_CLASS_CODE)) {
			code  = params.get(SchoolClassConstant.SCHOOL_CLASS_CODE).toString();
		}
		
		if(params.containsKey(SchoolClassConstant.SCHOOL_CLASS_CAPACITY)) {
			capacity  = (Integer)params.get(SchoolClassConstant.SCHOOL_CLASS_CAPACITY);
		}
		
		if(params.containsKey(SchoolClassConstant.SCHOOL_CLASS_ID)) {
			id  = (Integer)params.get(SchoolClassConstant.SCHOOL_CLASS_ID);
		}
		
		if (id == null && requiredFields.contains(SchoolClassConstant.SCHOOL_CLASS_ID)) {
			throw new FieldIsRequiredException("ID is required continue");
		}
		schoolClass.setId(id);
		
		if (capacity == null && requiredFields.contains(SchoolClassConstant.SCHOOL_CLASS_CAPACITY)) {
			throw new FieldIsRequiredException("Capacity is required continue");
		}
		schoolClass.setCapacity(capacity);
		
		if (name == null && requiredFields.contains(SchoolClassConstant.SCHOOL_CLASS_NAME)) {
			throw new FieldIsRequiredException("Name is required continue");
		}
		schoolClass.setName(name);
		
		if (description == null && requiredFields.contains(SchoolClassConstant.SCHOOL_CLASS_DESCRIPTION)) {
			throw new FieldIsRequiredException("Description is required continue");
		}
		schoolClass.setDescription(description);
		
		if (code == null && requiredFields.contains(SchoolClassConstant.SCHOOL_CLASS_CODE)) {
			throw new FieldIsRequiredException("Code is required continue");
		}
		schoolClass.setCode(code);
		
		
		
		Date today = CalendarUtil.getTodaysDate();		
		schoolClass.setCreatedOn(today);
		schoolClass.setUpdatedOn(today);
		
		return schoolClass;
	}
}
