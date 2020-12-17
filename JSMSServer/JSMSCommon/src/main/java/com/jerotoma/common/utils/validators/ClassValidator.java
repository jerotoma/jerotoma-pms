package com.jerotoma.common.utils.validators;

import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.AcademicYearConstant;
import com.jerotoma.common.constants.ClassConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.academic.Class;

public class ClassValidator {
public static Class.ClassFields validate(Map<String, Object> params, List<String> requiredFields) {
		
		
		Integer courseId = null;
		Integer academicYearId = null;
		Integer classRoomId = null;
		Integer academicLevelId = null;
		Integer programId = null;
		Integer teacherId = null;
		Integer meetingTimeId = null;
		Integer capacity = null;	
		Integer id = null;	
		
		if(params.containsKey(ClassConstant.CLASS_CAPACITY)) {
			capacity  = (Integer)params.get(ClassConstant.CLASS_CAPACITY);
		}
		
		if(params.containsKey(ClassConstant.CLASS_COURSE_ID)) {
			courseId  = (Integer)params.get(ClassConstant.CLASS_COURSE_ID);
		}
		if(params.containsKey(ClassConstant.CLASS_ROOM_ID)) {
			classRoomId  = (Integer)params.get(ClassConstant.CLASS_ROOM_ID);
		}
		if(params.containsKey(ClassConstant.CLASS_TEACHER_ID)) {
			teacherId  = (Integer)params.get(ClassConstant.CLASS_TEACHER_ID);
		}
		if(params.containsKey(ClassConstant.CLASS_ACADEMIC_YEAR_ID)) {
			academicYearId  = (Integer)params.get(ClassConstant.CLASS_ACADEMIC_YEAR_ID);
		}
		
		if(params.containsKey(ClassConstant.CLASS_MEETING_TIME_ID)) {
			meetingTimeId  = (Integer)params.get(ClassConstant.CLASS_MEETING_TIME_ID);
		}
		
		if(params.containsKey(ClassConstant.CLASS_ACADEMIC_LEVEL_ID)) {
			academicLevelId  = (Integer)params.get(ClassConstant.CLASS_ACADEMIC_LEVEL_ID);
		}
		
		if(params.containsKey(ClassConstant.CLASS_PROGRAM_ID)) {
			programId  = (Integer)params.get(ClassConstant.CLASS_PROGRAM_ID);
		}
		
		if(params.containsKey(ClassConstant.CLASS_ID)) {
			id  = (Integer)params.get(ClassConstant.CLASS_ID);
		}
		
		if (id == null && requiredFields.contains(AcademicYearConstant.ACADEMIC_YEAR_ID)) {
			throw new FieldRequiredException("ID is required to continue");
		}
		
		if (capacity == null && requiredFields.contains(ClassConstant.CLASS_CAPACITY)) {
			throw new FieldRequiredException("Capacity is required to continue");
		}
		
		if (courseId == null && requiredFields.contains(ClassConstant.CLASS_COURSE_ID)) {
			throw new FieldRequiredException("Course ID is required to continue");
		}
		
		if (teacherId == null && requiredFields.contains(ClassConstant.CLASS_TEACHER_ID)) {
			throw new FieldRequiredException("Teacher ID is required to continue");
		}
		
		if (classRoomId == null && requiredFields.contains(ClassConstant.CLASS_ROOM_ID)) {
			throw new FieldRequiredException("Class Room ID is required to continue");
		}
		
		if (academicYearId == null && requiredFields.contains(ClassConstant.CLASS_ACADEMIC_YEAR_ID)) {
			throw new FieldRequiredException("Academic Year ID is required to continue");
		}
		
		if (meetingTimeId == null && requiredFields.contains(ClassConstant.CLASS_MEETING_TIME_ID)) {
			throw new FieldRequiredException("Meeting Time ID is required to continue");
		}
		

		if (academicLevelId == null && requiredFields.contains(ClassConstant.CLASS_ACADEMIC_LEVEL_ID)) {
			throw new FieldRequiredException("Academic Level ID is required to continue");
		}
		
		if (programId == null && requiredFields.contains(ClassConstant.CLASS_PROGRAM_ID)) {
			throw new FieldRequiredException("Program ID is required to continue");
		}
		
		return new Class.ClassFields(courseId, academicYearId, classRoomId, academicLevelId, programId, teacherId, meetingTimeId, capacity, id);
	}
}
