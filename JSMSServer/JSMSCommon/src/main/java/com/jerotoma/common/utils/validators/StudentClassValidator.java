package com.jerotoma.common.utils.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.users.students.StudentClass;

public class StudentClassValidator {
	
	@SuppressWarnings("unchecked")
	public static StudentClass.Fields validate(Map<String, Object> params, List<String> requiredFields) {
		
		Integer studentAcademicLevelId = null;
		List<Integer> jClassIds = null;
		List<Integer> studentIds = null;
		Integer id = null;	
		
		if(params.containsKey(StudentConstant.Class.JCLASS_IDS)) {
			jClassIds  = (ArrayList<Integer>)params.get(StudentConstant.Class.JCLASS_IDS);
		}
		
		if(params.containsKey(StudentConstant.Class.STUDENT_IDS)) {
			studentIds  = (ArrayList<Integer>)params.get(StudentConstant.Class.STUDENT_IDS);
		}	
		
		if(params.containsKey(StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID)) {
			studentAcademicLevelId  = (Integer)params.get(StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID);
		}
		
		if(params.containsKey(StudentConstant.Class.ID)) {
			id  = (Integer)params.get(StudentConstant.Class.ID);
		}
		
		if (id == null && requiredFields.contains(StudentConstant.Class.ID)) {
			throw new FieldRequiredException("ID is required to continue");
		}
		
		
		if (studentIds == null && requiredFields.contains(StudentConstant.Class.STUDENT_ID)) {
			throw new FieldRequiredException("Student ID is required to continue");
		}
		
		if (jClassIds == null && requiredFields.contains(StudentConstant.Class.JCLASS_IDS)) {
			throw new FieldRequiredException("Class ID is required to continue");
		}
		
		if (studentAcademicLevelId == null && requiredFields.contains(StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID)) {
			throw new FieldRequiredException("Academic Level ID is required to continue");
		}
		
		
		return new StudentClass.Fields(id, studentIds, jClassIds, studentAcademicLevelId);
	}
}
