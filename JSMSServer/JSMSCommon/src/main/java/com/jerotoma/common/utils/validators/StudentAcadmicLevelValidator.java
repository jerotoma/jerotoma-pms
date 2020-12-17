package com.jerotoma.common.utils.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.users.students.StudentAcademicLevel;

public class StudentAcadmicLevelValidator {
	
	@SuppressWarnings("unchecked")
	public static StudentAcademicLevel.Fields validate(Map<String, Object> params, List<String> requiredFields) {
		
		Boolean isCurrentStudentAcademicLevel = Boolean.FALSE;
		Integer studentId = null;
		Integer academicLevelId = null;
		Integer commpletionStatusId = null;	
		Integer academicYearId = null;
		List<Integer> jClassIds = null;
		Integer id = null;	
		
		if(params.containsKey(StudentConstant.Class.ID)) {
			id  = (Integer)params.get(StudentConstant.Class.ID);
		}
		
		if(params.containsKey(StudentConstant.Class.STUDENT_ID)) {
			studentId  = (Integer)params.get(StudentConstant.Class.STUDENT_ID);
		}
		
		if(params.containsKey(StudentConstant.Class.COMPLETION_STATUS_ID)) {
			commpletionStatusId  = (Integer)params.get(StudentConstant.Class.COMPLETION_STATUS_ID);
		}
		
		if(params.containsKey(StudentConstant.Class.IS_CURRENT_STUDENT_ACADEMIC_LEVEL)) {
			isCurrentStudentAcademicLevel  = (Boolean)params.get(StudentConstant.Class.IS_CURRENT_STUDENT_ACADEMIC_LEVEL);
		}
		
		if(params.containsKey(StudentConstant.Class.ACADEMIC_YEAR_ID)) {
			academicYearId  = (Integer)params.get(StudentConstant.Class.ACADEMIC_YEAR_ID);
		}
		
		if(params.containsKey(StudentConstant.Class.ACADEMIC_LEVEL_ID)) {
			academicLevelId = (Integer)params.get(StudentConstant.Class.ACADEMIC_LEVEL_ID);
		}
		
		if(params.containsKey(StudentConstant.Class.JCLASS_IDS)) {
			jClassIds  = (ArrayList<Integer>)params.get(StudentConstant.Class.JCLASS_IDS);
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
		
		if (academicYearId == null && requiredFields.contains(StudentConstant.Class.ACADEMIC_YEAR_ID)) {
			throw new FieldRequiredException("Academic Year ID is required to continue");
		}
		
		if (isCurrentStudentAcademicLevel == null && requiredFields.contains(StudentConstant.Class.IS_CURRENT_STUDENT_ACADEMIC_LEVEL)) {
			throw new FieldRequiredException("Current Student Academic Level is required to continue");
		}
		
		if (jClassIds == null && requiredFields.contains(StudentConstant.Class.JCLASS_IDS)) {
			throw new FieldRequiredException("Class ID is required to continue");
		}
		
		return new StudentAcademicLevel.Fields(id, studentId, commpletionStatusId, academicLevelId, academicYearId, jClassIds, isCurrentStudentAcademicLevel);
	}
}
