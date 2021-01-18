package com.jerotoma.common.utils.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.users.students.StudentClass;
import com.jerotoma.common.models.users.students.StudentClass.StudentClassParam;
import com.jerotoma.common.models.users.students.StudentClass.StudentClassProgressParam;

public class StudentClassValidator {
	
	@SuppressWarnings("unchecked")
	public static StudentClass.Fields validate(Map<String, Object> params, List<String> requiredFields) {
		
		Integer studentAcademicLevelId = null;
		List<Integer> jClassIds = null;
		List<Integer> studentIds = null;
		Integer id = null;	
		
		if(params.containsKey(StudentConstant.Class.CLASS_IDS)) {
			jClassIds  = (ArrayList<Integer>)params.get(StudentConstant.Class.CLASS_IDS);
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
		
		if (jClassIds == null && requiredFields.contains(StudentConstant.Class.CLASS_IDS)) {
			throw new FieldRequiredException("Class ID is required to continue");
		}
		
		if (studentAcademicLevelId == null && requiredFields.contains(StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID)) {
			throw new FieldRequiredException("Academic Level ID is required to continue");
		}
		
		
		return new StudentClass.Fields(id, studentIds, jClassIds, studentAcademicLevelId);
	}

	@SuppressWarnings("unchecked")
	public static StudentClassParam validateStudentClassProgress(Map<String, Object> params, List<String> requiredFields) {
		Integer classId = null;
		List<StudentClassProgressParam> studentClassProgressParams =  null;		
		List<Map<String, Object>> mapStudentClassProgressParams = null;
		
		if(params.containsKey(StudentConstant.Class.CLASS_ID)) {
			classId  = (Integer)params.get(StudentConstant.Class.CLASS_ID);
		}
		
		if(params.containsKey(StudentConstant.Class.STUDENT_PROGRESS_ARRAY)) {
			mapStudentClassProgressParams  = (ArrayList<Map<String, Object>>)params.get(StudentConstant.Class.STUDENT_PROGRESS_ARRAY);
		}	
		
		if (mapStudentClassProgressParams == null && requiredFields.contains(StudentConstant.Class.STUDENT_PROGRESS_ARRAY)) {
			throw new FieldRequiredException("Student Class Progress is required to continue");
		}
		
		if (mapStudentClassProgressParams != null && !mapStudentClassProgressParams.isEmpty()) {
			studentClassProgressParams =  new ArrayList<>();
			for (Map<String, Object> mapStudentClassProgressParam: mapStudentClassProgressParams) {
				Integer studentId = (Integer) mapStudentClassProgressParam.get(StudentConstant.Class.STUDENT_ID);
				Double score = null;
				if (mapStudentClassProgressParam.get(StudentConstant.Class.SCORE) instanceof Double) {
					score = (Double) mapStudentClassProgressParam.get(StudentConstant.Class.SCORE);
				} else {
					Integer sc = (Integer) mapStudentClassProgressParam.get(StudentConstant.Class.SCORE);
					score = sc.doubleValue();
				}				
				Integer statusId = (Integer) mapStudentClassProgressParam.get(StudentConstant.Class.STATUS_ID);
				Integer studentAcademicLevelId = (Integer) mapStudentClassProgressParam.get(StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID);
				studentClassProgressParams.add(new StudentClassProgressParam(studentAcademicLevelId, studentId, statusId, score));
			}
		}
		
		
		if (classId == null && requiredFields.contains(StudentConstant.Class.CLASS_ID)) {
			throw new FieldRequiredException("Class ID is required to continue");
		}
		
		
		return new StudentClassParam(classId, studentClassProgressParams);
	}
}
