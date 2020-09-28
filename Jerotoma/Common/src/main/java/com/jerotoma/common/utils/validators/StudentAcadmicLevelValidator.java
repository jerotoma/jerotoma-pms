package com.jerotoma.common.utils.validators;

import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.academic.StudentAcademicLevel;

public class StudentAcadmicLevelValidator {
	
	public static StudentAcademicLevel.Fields validate(Map<String, Object> params, List<String> requiredFields) {
		
		
		Integer studentId = null;
		Integer academicLevelId = null;
		Integer commpletionStatusId = null;		
		Integer id = null;	
		
		if(params.containsKey(StudentConstant.Class.STUDENT_ID)) {
			studentId  = (Integer)params.get(StudentConstant.Class.STUDENT_ID);
		}
		
		if(params.containsKey(StudentConstant.Class.COMPLETION_STATUS_ID)) {
			commpletionStatusId  = (Integer)params.get(StudentConstant.Class.COMPLETION_STATUS_ID);
		}
		
		if(params.containsKey(StudentConstant.Class.ACADEMIC_LEVEL_ID)) {
			academicLevelId = (Integer)params.get(StudentConstant.Class.ACADEMIC_LEVEL_ID);
		}
		
		if(params.containsKey(StudentConstant.Class.ID)) {
			id  = (Integer)params.get(StudentConstant.Class.ID);
		}
		
		if (id == null && requiredFields.contains(StudentConstant.Class.ID)) {
			throw new FieldRequiredException("ID is required to continue");
		}
		
		
		if (studentId == null && requiredFields.contains(StudentConstant.Class.STUDENT_ID)) {
			throw new FieldRequiredException("Student ID is required to continue");
		}
		
		if (academicLevelId == null && requiredFields.contains(StudentConstant.Class.JCLASS_IDS)) {
			throw new FieldRequiredException("Class ID is required to continue");
		}
		
		if (commpletionStatusId == null && requiredFields.contains(StudentConstant.Class.COMPLETION_STATUS_ID)) {
			throw new FieldRequiredException("Completion Status ID is required to continue");
		}
		
		if (academicLevelId == null && requiredFields.contains(StudentConstant.Class.ACADEMIC_LEVEL_ID)) {
			throw new FieldRequiredException("Academic Level ID is required to continue");
		}
		
		
		return new StudentAcademicLevel.Fields(id, studentId, commpletionStatusId, academicLevelId);
	}
}
