package com.jerotoma.common.utils.validators;

import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.AcademicYearConstant;
import com.jerotoma.common.constants.JClassConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.models.academic.JClass;

public class JClassValidator {
public static JClass.JClassFields validate(Map<String, Object> params, List<String> requiredFields) {
		
		
		Integer courseId = null;
		Integer academicYearId = null;
		Integer classRoomId = null;
		Integer teacherId = null;
		Integer capacity = null;	
		Integer id = null;	
		
		if(params.containsKey(JClassConstant.JCLASS_CAPACITY)) {
			capacity  = (Integer)params.get(JClassConstant.JCLASS_CAPACITY);
		}
		
		if(params.containsKey(JClassConstant.JCLASS_COURSE_ID)) {
			courseId  = (Integer)params.get(JClassConstant.JCLASS_COURSE_ID);
		}
		if(params.containsKey(JClassConstant.JCLASS_CLASS_ROOM_ID)) {
			classRoomId  = (Integer)params.get(JClassConstant.JCLASS_CLASS_ROOM_ID);
		}
		if(params.containsKey(JClassConstant.JCLASS_TEACHER_ID)) {
			teacherId  = (Integer)params.get(JClassConstant.JCLASS_TEACHER_ID);
		}
		if(params.containsKey(JClassConstant.JCLASS_ACADEMIC_YEAR_ID)) {
			academicYearId  = (Integer)params.get(JClassConstant.JCLASS_ACADEMIC_YEAR_ID);
		}
		
		if(params.containsKey(JClassConstant.JCLASS_ID)) {
			id  = (Integer)params.get(JClassConstant.JCLASS_ID);
		}
		
		if (id == null && requiredFields.contains(AcademicYearConstant.ACADEMIC_YEAR_ID)) {
			throw new FieldIsRequiredException("ID is required to continue");
		}
		
		if (capacity == null && requiredFields.contains(JClassConstant.JCLASS_CAPACITY)) {
			throw new FieldIsRequiredException("Capacity is required to continue");
		}
		
		if (courseId == null && requiredFields.contains(JClassConstant.JCLASS_COURSE_ID)) {
			throw new FieldIsRequiredException("Course ID is required to continue");
		}
		
		if (teacherId == null && requiredFields.contains(JClassConstant.JCLASS_TEACHER_ID)) {
			throw new FieldIsRequiredException("Teacher ID is required to continue");
		}
		
		if (classRoomId == null && requiredFields.contains(JClassConstant.JCLASS_CLASS_ROOM_ID)) {
			throw new FieldIsRequiredException("Class Room ID is required to continue");
		}
		
		if (academicYearId == null && requiredFields.contains(JClassConstant.JCLASS_ACADEMIC_YEAR_ID)) {
			throw new FieldIsRequiredException("Academic Year ID is required to continue");
		}
		
		
		return new JClass.JClassFields(courseId, academicYearId, classRoomId, teacherId, capacity, id);
	}
}
