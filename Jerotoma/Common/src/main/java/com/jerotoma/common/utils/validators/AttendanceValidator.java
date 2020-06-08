package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.AttendanceConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.models.attendances.ClassAttendance.ClassAttendanceParam;
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

}
