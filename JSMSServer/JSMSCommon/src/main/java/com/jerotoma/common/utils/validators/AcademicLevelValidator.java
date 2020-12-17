package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.AcademicLevelConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.utils.CalendarUtil;

public class AcademicLevelValidator {
	
	public static AcademicLevel validate(Map<String, Object> params, List<String> requiredFields) {
		AcademicLevel program = new AcademicLevel();
		Integer id = null;
		String code = null;
		String name = null;
		String description = null;
		
		if (params.containsKey(AcademicLevelConstant.ID)) {
			id = (Integer) params.get(AcademicLevelConstant.ID);
		}
		
		if (params.containsKey(AcademicLevelConstant.CODE)) {
			code = (String) params.get(AcademicLevelConstant.CODE);
		}
		
		if (params.containsKey(AcademicLevelConstant.NAME)) {
			name = (String) params.get(AcademicLevelConstant.NAME);
		}
		
		if (params.containsKey(AcademicLevelConstant.DESCRIPTION)) {
			description = (String) params.get(AcademicLevelConstant.DESCRIPTION);
		}
		
		if (id == null && requiredFields.contains(AcademicLevelConstant.ID)) {
			throw new FieldRequiredException("ID is required to continue");
		}
		program.setId(id);
		
		if (name == null && requiredFields.contains(AcademicLevelConstant.NAME)) {
			throw new FieldRequiredException("Name is required to continue");
		}
		program.setName(name);
		
		if (code == null && requiredFields.contains(AcademicLevelConstant.CODE)) {
			throw new FieldRequiredException("ID is required to continue");
		}
		program.setCode(code);
		
		if (description == null && requiredFields.contains(AcademicLevelConstant.DESCRIPTION)) {
			throw new FieldRequiredException("Description is required to continue");
		}
		program.setDescription(description);		
		
		Date today = CalendarUtil.getTodaysDate();
		
		program.setCreatedOn(today);
		program.setUpdatedOn(today);
		
		return program;
	}

}
