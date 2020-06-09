package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.AttendanceConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.models.attendances.AttendanceStatus;
import com.jerotoma.common.models.attendances.ClassAttendance.ClassAttendanceParam;
import com.jerotoma.common.models.attendances.StudentAttendance.StudentAttendanceParam;
import com.jerotoma.common.utils.CalendarUtil;

public class AttendanceValidator {

	public static ClassAttendanceParam validateClassAttendance(Map<String, Object> params, List<String> requiredFields) {
		
		ClassAttendanceParam classAttendanceParam = new ClassAttendanceParam();
		Integer id = null;
		Integer classId = null;
		Integer academicYearId = null;
		String attendanceDate = null;
		
		if(params.containsKey(AttendanceConstant.CLASS_ID)) {
			classId  = (Integer) params.get(AttendanceConstant.CLASS_ID);
		}
		if(params.containsKey(AttendanceConstant.ACADEMIC_YEAR_ID)) {
			academicYearId  = (Integer) params.get(AttendanceConstant.ACADEMIC_YEAR_ID);
		}
		
		if(params.containsKey(AttendanceConstant.ATTENDANCE_DATE)) {
			attendanceDate  = params.get(AttendanceConstant.ATTENDANCE_DATE).toString();
		}
		if(params.containsKey(AttendanceConstant.CLASS_ATTENDANCE_ID)) {
			id  = (Integer)params.get(AttendanceConstant.CLASS_ATTENDANCE_ID);
		}		
		
		if (id == null && requiredFields.contains(AttendanceConstant.CLASS_ATTENDANCE_ID)) {
			throw new FieldIsRequiredException("ID is required to continue");
		}
		classAttendanceParam.setId(id);
		
		if (academicYearId == null && requiredFields.contains(AttendanceConstant.ACADEMIC_YEAR_ID)) {
			throw new FieldIsRequiredException("Academic Year ID is required to continue");
		}
		classAttendanceParam.setAcademicYearId(academicYearId);
		

		if (classId == null && requiredFields.contains(AttendanceConstant.CLASS_ID)) {
			throw new FieldIsRequiredException("Class ID is required to continue");
		}
		classAttendanceParam.setClassId(classId);
		

		if (attendanceDate == null && requiredFields.contains(AttendanceConstant.ATTENDANCE_DATE)) {
			throw new FieldIsRequiredException("Attendance Date is required to continue");
		}
		Date cal = CalendarUtil.convertStringToDate(attendanceDate);
		classAttendanceParam.setAttendanceDate(cal);	
		
		return classAttendanceParam;
	}
	
	public static StudentAttendanceParam validateStudentAttendance(Map<String, Object> params, List<String> requiredFields) {
		
		StudentAttendanceParam studentAttendanceParam = new StudentAttendanceParam();
		Integer id = null;
		Integer studentId = null;
		Integer classAttendanceId = null;
		String attendanceStatus = null;
		
		if(params.containsKey(AttendanceConstant.CLASS_ID)) {
			studentId  = (Integer) params.get(AttendanceConstant.CLASS_ID);
		}
		if(params.containsKey(AttendanceConstant.CLASS_ATTENDANCE_ID)) {
			classAttendanceId  = (Integer) params.get(AttendanceConstant.CLASS_ATTENDANCE_ID);
		}
		
		if(params.containsKey(AttendanceConstant.ATTENDANCE_STATUS)) {
			attendanceStatus  = params.get(AttendanceConstant.ATTENDANCE_STATUS).toString();
		}
		if(params.containsKey(AttendanceConstant.STUDENT_ATTENDANCE_ID)) {
			id  = (Integer)params.get(AttendanceConstant.STUDENT_ATTENDANCE_ID);
		}		
		
		if (id == null && requiredFields.contains(AttendanceConstant.STUDENT_ATTENDANCE_ID)) {
			throw new FieldIsRequiredException("ID is required to continue");
		}
		studentAttendanceParam.setId(id);
		
		if (classAttendanceId == null && requiredFields.contains(AttendanceConstant.CLASS_ATTENDANCE_ID)) {
			throw new FieldIsRequiredException("Class Attendance ID is required to continue");
		}
		studentAttendanceParam.setClassAttendanceId(classAttendanceId);
		

		if (studentId == null && requiredFields.contains(AttendanceConstant.CLASS_ID)) {
			throw new FieldIsRequiredException("Student ID is required to continue");
		}
		studentAttendanceParam.setStudentId(studentId);
		

		if (attendanceStatus == null && requiredFields.contains(AttendanceConstant.ATTENDANCE_DATE)) {
			throw new FieldIsRequiredException("Attendance Status is required to continue");
		}		
		studentAttendanceParam.setAttendanceStatus(attendanceStatus);
		
		return studentAttendanceParam;
	}
	
	
	public static AttendanceStatus validateAttendanceStatus(Map<String, Object> params, List<String> requiredFields) {
		
		AttendanceStatus attendanceStatus = new AttendanceStatus();
		Integer id = null;		
		String status = null;
		String description = null;
		
		if(params.containsKey(AttendanceConstant.ATTENDANCE_STATUS_ID)) {
			id  = (Integer)params.get(AttendanceConstant.ATTENDANCE_STATUS_ID);
		}
		
		if(params.containsKey(AttendanceConstant.ATTENDANCE_STATUS)) {
			status  = params.get(AttendanceConstant.ATTENDANCE_STATUS).toString();
		}
		if(params.containsKey(AttendanceConstant.ATTENDANCE_STATUS_DESCRIPTION)) {
			description  = params.get(AttendanceConstant.ATTENDANCE_STATUS_DESCRIPTION).toString();
		}
		
				
		if (id == null && requiredFields.contains(AttendanceConstant.ATTENDANCE_STATUS_ID)) {
			throw new FieldIsRequiredException("ID is required to continue");
		}
		attendanceStatus.setId(id);
		
		if (status == null && requiredFields.contains(AttendanceConstant.ATTENDANCE_STATUS)) {
			throw new FieldIsRequiredException("Status is required to continue");
		}
		attendanceStatus.setStatus(status);
		

		if (description == null && requiredFields.contains(AttendanceConstant.ATTENDANCE_STATUS_DESCRIPTION)) {
			throw new FieldIsRequiredException("Class ID is required to continue");
		}
		attendanceStatus.setDescription(description);
		
		Date cal = CalendarUtil.getTodaysDate();
		attendanceStatus.setCreatedOn(cal);
		attendanceStatus.setUpdatedOn(cal);
		
		return attendanceStatus;
	}

}
