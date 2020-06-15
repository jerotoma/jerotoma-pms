package com.jerotoma.common.utils.validators;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.AttendanceConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.models.attendances.AttendanceStatus;
import com.jerotoma.common.models.attendances.ClassAttendance.ClassAttendanceParam;
import com.jerotoma.common.models.attendances.StudentAttendance.StudentAttendanceParam;
import com.jerotoma.common.models.attendances.StudentAttendance.StudentAttendanceStatusParam;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.StringUtility;

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
	
	@SuppressWarnings("unchecked")
	public static StudentAttendanceParam validateStudentAttendance(Map<String, Object> params, List<String> requiredFields) {
		
		StudentAttendanceParam studentAttendanceParam = new StudentAttendanceParam();
		List<StudentAttendanceStatusParam> studentAttendanceStatusParams = new ArrayList<>();
		Integer id = null;
		Integer studentId = null;
		Integer classAttendanceId = null;
		Integer attendanceStatusId = null;	
		
		ArrayList<Map<String, Object>> studentAttendanceStatuses;
		
		if(params.containsKey(AttendanceConstant.CLASS_ATTENDANCE_ID)) {
			classAttendanceId  = (Integer) params.get(AttendanceConstant.CLASS_ATTENDANCE_ID);
		}
		
		if(params.containsKey(AttendanceConstant.ID)) {
			id  = (Integer)params.get(AttendanceConstant.ID);
		}
		
		if(params.containsKey(AttendanceConstant.STUDENT_ATTENDANCE_STATUSES)) {
			studentAttendanceStatuses  = (ArrayList<Map<String, Object>>)params.get(AttendanceConstant.STUDENT_ATTENDANCE_STATUSES);
			for (Map<String, Object> studentAttendanceStatus: studentAttendanceStatuses) {
				
				StudentAttendanceStatusParam studentAttendanceStatusParam = new StudentAttendanceStatusParam();
				
				studentId = (Integer)studentAttendanceStatus.get(AttendanceConstant.STUDENT_ID);
				if (studentId == null && requiredFields.contains(AttendanceConstant.STUDENT_ID)) {
					throw new FieldIsRequiredException("Student ID is required to continue");
				}
				
				attendanceStatusId = (Integer)studentAttendanceStatus.get(AttendanceConstant.STUDENT_ATTENDANCE_STATUS_ID);
				if (attendanceStatusId == null && requiredFields.contains(AttendanceConstant.STUDENT_ATTENDANCE_STATUS_ID)) {
					throw new FieldIsRequiredException("Attendance Status is required to continue");
				}
				
				studentAttendanceStatusParam.setStudentId(studentId);
				studentAttendanceStatusParam.setAttendanceStatusId(attendanceStatusId);			
				studentAttendanceStatusParams.add(studentAttendanceStatusParam);
			}
		} else {
			throw new FieldIsRequiredException("Student Attendance Statuses are required to continue");
		}
		studentAttendanceParam.setStudentAttendanceStatuses(studentAttendanceStatusParams);
		
		if (id == null && requiredFields.contains(AttendanceConstant.ID)) {
			throw new FieldIsRequiredException("ID is required to continue");
		}
		studentAttendanceParam.setId(id);
		
		if (classAttendanceId == null && requiredFields.contains(AttendanceConstant.CLASS_ATTENDANCE_ID)) {
			throw new FieldIsRequiredException("Class Attendance ID is required to continue");
		}
		studentAttendanceParam.setClassAttendanceId(classAttendanceId);
		

				
		//studentAttendanceParam.setAttendanceStatus(attendanceStatus);
		
		return studentAttendanceParam;
	}
	
	
	public static AttendanceStatus validateAttendanceStatus(Map<String, Object> params, List<String> requiredFields) {
		
		AttendanceStatus attendanceStatus = new AttendanceStatus();
		Integer id = null;		
		String status = null;
		String description = null;
		
		if(params.containsKey(AttendanceConstant.ID)) {
			id  = (Integer)params.get(AttendanceConstant.ID);
		}
		
		if(params.containsKey(AttendanceConstant.ATTENDANCE_STATUS_NAME)) {
			status  = StringUtility.getString(params.get(AttendanceConstant.ATTENDANCE_STATUS_NAME));
		}
		
		if(params.containsKey(AttendanceConstant.ATTENDANCE_STATUS_DESCRIPTION)) {
			description  = StringUtility.getString(params.get(AttendanceConstant.ATTENDANCE_STATUS_DESCRIPTION));
		}
		
				
		if (id == null && requiredFields.contains(AttendanceConstant.ID)) {
			throw new FieldIsRequiredException("ID is required to continue");
		}
		attendanceStatus.setId(id);
		
		if (status == null && requiredFields.contains(AttendanceConstant.ATTENDANCE_STATUS_NAME)) {
			throw new FieldIsRequiredException("Status is required to continue");
		}
		attendanceStatus.setName(status);
		

		if (description == null && requiredFields.contains(AttendanceConstant.ATTENDANCE_STATUS_DESCRIPTION)) {
			throw new FieldIsRequiredException("Description ID is required to continue");
		}
		attendanceStatus.setDescription(description);
		
		Date cal = CalendarUtil.getTodaysDate();
		attendanceStatus.setCreatedOn(cal);
		attendanceStatus.setUpdatedOn(cal);
		
		return attendanceStatus;
	}

}
