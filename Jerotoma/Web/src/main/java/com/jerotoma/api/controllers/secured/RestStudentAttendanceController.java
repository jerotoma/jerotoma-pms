package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.constants.AttendanceConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.attendances.ClassAttendance;
import com.jerotoma.common.models.attendances.StudentAttendance;
import com.jerotoma.common.models.attendances.StudentAttendance.StudentAttendanceParam;
import com.jerotoma.common.models.users.Student;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.validators.AttendanceValidator;
import com.jerotoma.services.attendances.StudentAttendanceService;
import com.jerotoma.services.users.StudentService;

@RestController
@RequestMapping(EndPointConstants.REST_STUDENT_ATTENDANCE_CONTROLLER.BASE)
public class RestStudentAttendanceController extends RestAttendanceController {
	
	@Autowired StudentAttendanceService studentAttendanceService;
	@Autowired StudentService studentService;

	@Override
	public HttpResponseEntity<Object> index(Authentication auth, String search, Integer page, Integer pageSize,
			String fieldName, String orderby) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpResponseEntity<Object> show(Authentication auth, Integer entityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@PutMapping(value = {"", "/"})
	@ResponseBody
	@Override
	public HttpResponseEntity<Object> update(Authentication auth, Integer entityId, Map<String, Object> params) {
		this.logRequestDetail("PUT : " + EndPointConstants.REST_STUDENT_ATTENDANCE_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		
		List<String> requiredFields = new ArrayList<>(Arrays.asList(
				AttendanceConstant.ATTENDANCE_STATUS,
				AttendanceConstant.STUDENT_ATTENDANCE_ID,
				AttendanceConstant.STUDENT_ID,
				AttendanceConstant.CLASS_ATTENDANCE_ID
				));
		StudentAttendanceParam studentAttendanceParam = AttendanceValidator.validateStudentAttendance(params, requiredFields);		
		Date today = CalendarUtil.getTodaysDate();	
				
		try {
						
			ClassAttendance classAttendance = classAttendanceService.findObject(studentAttendanceParam.getId());
			Student student = studentService.findObject(studentAttendanceParam.getStudentId());
			StudentAttendance studentAttendance = studentAttendanceService.findObject(studentAttendanceParam.getId());
			studentAttendance.setStudent(student);
			studentAttendance.setClassAttendance(classAttendance);
			studentAttendance.setAttendanceStatus(studentAttendanceParam.getAttendanceStatus());
			studentAttendance.setAddedBy(authUser.getId());
			studentAttendance.setUpdatedOn(today);
			
			
			instance.setData(studentAttendanceService.createObject(studentAttendance));
			instance.setSuccess(true);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);	
		}
		instance.setHttpStatus(HttpStatus.OK);	
		return instance;
	}

	@PostMapping(value = {"", "/"})
	@ResponseBody
	@Override
	public HttpResponseEntity<Object> create(Authentication auth, Map<String, Object> params) {
		this.logRequestDetail("POST : " + EndPointConstants.REST_STUDENT_ATTENDANCE_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		
		List<String> requiredFields = new ArrayList<>(Arrays.asList(
				AttendanceConstant.ATTENDANCE_STATUS,
				AttendanceConstant.STUDENT_ATTENDANCE_ID,
				AttendanceConstant.STUDENT_ID,
				AttendanceConstant.CLASS_ATTENDANCE_ID
				));
		StudentAttendanceParam studentAttendanceParam = AttendanceValidator.validateStudentAttendance(params, requiredFields);		
		Date today = CalendarUtil.getTodaysDate();	
				
		try {
						
			ClassAttendance classAttendance = classAttendanceService.findObject(studentAttendanceParam.getId());
			Student student = studentService.findObject(studentAttendanceParam.getStudentId());
			StudentAttendance studentAttendance = new StudentAttendance(student, classAttendance, studentAttendanceParam.getAttendanceStatus(), authUser.getId(), today, today);
			instance.setData(studentAttendanceService.createObject(studentAttendance));
			instance.setSuccess(true);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);	
		}
		instance.setHttpStatus(HttpStatus.OK);	
		return instance;
	}

	@Override
	public HttpResponseEntity<Object> edit(Authentication auth, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpResponseEntity<Object> delete(Authentication auth, Integer entityId) {
		// TODO Auto-generated method stub
		return null;
	}

}
