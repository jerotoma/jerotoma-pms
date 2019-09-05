package com.jerotoma.common.utils.validators;

import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.models.academic.StudentClass;

public class StudentClassValidator {
	
	public static StudentClass.Fields validate(Map<String, Object> params, List<String> requiredFields) {
		
		
		Integer academicYearId = null;
		Integer jClassId = null;
		Integer studentId = null;
		Integer id = null;	
		
		if(params.containsKey(StudentConstant.Class.JCLASS_ID)) {
			jClassId  = (Integer)params.get(StudentConstant.Class.JCLASS_ID);
		}
		if(params.containsKey(StudentConstant.Class.STUDENT_ID)) {
			studentId  = (Integer)params.get(StudentConstant.Class.STUDENT_ID);
		}
		if(params.containsKey(StudentConstant.Class.ACADEMIC_YEAR_ID)) {
			academicYearId  = (Integer)params.get(StudentConstant.Class.ACADEMIC_YEAR_ID);
		}
		
		if(params.containsKey(StudentConstant.Class.ID)) {
			id  = (Integer)params.get(StudentConstant.Class.ID);
		}
		
		if (id == null && requiredFields.contains(StudentConstant.Class.ACADEMIC_YEAR_ID)) {
			throw new FieldIsRequiredException("ID is required to continue");
		}
		
		
		if (studentId == null && requiredFields.contains(StudentConstant.Class.STUDENT_ID)) {
			throw new FieldIsRequiredException("Student ID is required to continue");
		}
		
		if (jClassId == null && requiredFields.contains(StudentConstant.Class.JCLASS_ID)) {
			throw new FieldIsRequiredException("Class ID is required to continue");
		}
		
		if (academicYearId == null && requiredFields.contains(StudentConstant.Class.ACADEMIC_YEAR_ID)) {
			throw new FieldIsRequiredException("Academic Year ID is required to continue");
		}
		
		
		return new StudentClass.Fields(id, studentId, jClassId, academicYearId);
	}
}
