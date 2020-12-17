package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.AcademicDisciplineConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.academicDisciplines.AcademicDiscipline;
import com.jerotoma.common.utils.CalendarUtil;

public class AcademicDisciplineValidator {
	
	public static AcademicDiscipline validate(Map<String, Object> params, List<String> requiredFields) {
		AcademicDiscipline academicDiscipline = new AcademicDiscipline();
		String name  = null;		
		String description = null;
		String code = null;	
		Integer id = null;	
		
		
		if(params.containsKey(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_NAME)) {
			name  = params.get(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_NAME).toString();
		}
		if(params.containsKey(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_DESCRIPTION)) {
			description  = params.get(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_DESCRIPTION).toString();
		}
		
		if(params.containsKey(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_CODE)) {
			code  = params.get(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_CODE).toString();
		}
		if(params.containsKey(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_ID)) {
			id  = (Integer)params.get(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_ID);
		}
		
		if (id == null && requiredFields.contains(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_ID)) {
			throw new FieldRequiredException("Academic Discipline ID can not be empty");
		}
		academicDiscipline.setId(id);
		
		if (name == null && requiredFields.contains(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_NAME)) {
			throw new FieldRequiredException("Academic Discipline Name can not be empty");
		}
		academicDiscipline.setName(name);
		
		if (description == null && requiredFields.contains(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_DESCRIPTION)) {
			throw new FieldRequiredException("Academic Discipline Description can not be empty");
		}
		academicDiscipline.setDescription(description);
		
		if (code == null && requiredFields.contains(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_CODE)) {
			throw new FieldRequiredException("Academic Discipline Code can not be empty");
		}
		academicDiscipline.setCode(code);
		
		
		
		Date today = CalendarUtil.getTodaysDate();		
		academicDiscipline.setCreatedOn(today);
		academicDiscipline.setUpdatedOn(today);
		
		return academicDiscipline;
	}
}
