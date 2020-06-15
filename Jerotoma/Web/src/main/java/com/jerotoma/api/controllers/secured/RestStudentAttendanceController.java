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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AttendanceConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.attendances.AttendanceStatus;
import com.jerotoma.common.models.attendances.ClassAttendance;
import com.jerotoma.common.models.attendances.StudentAttendance;
import com.jerotoma.common.models.attendances.StudentAttendance.StudentAttendanceParam;
import com.jerotoma.common.models.attendances.StudentAttendance.StudentAttendanceStatusParam;
import com.jerotoma.common.models.users.Student;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.validators.AttendanceValidator;
import com.jerotoma.services.assemblers.attendances.AssemblerStudentAttendanceService;
import com.jerotoma.services.attendances.AttendanceStatusService;
import com.jerotoma.services.attendances.StudentAttendanceService;
import com.jerotoma.services.users.StudentService;

@RestController
@RequestMapping(EndPointConstants.REST_STUDENT_ATTENDANCE_CONTROLLER.BASE)
public class RestStudentAttendanceController extends RestAttendanceController {
	
	@Autowired StudentAttendanceService studentAttendanceService;
	@Autowired AssemblerStudentAttendanceService assemblerStudentAttendanceService;
	@Autowired AttendanceStatusService attendanceStatusService;
	@Autowired StudentService studentService;

	
	@GetMapping
	@ResponseBody
	@Override
	public HttpResponseEntity<Object> index(Authentication auth, String search, Integer page, Integer pageSize,
			String fieldName, String orderby) {
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(page, pageSize, fieldName, orderby);
		try {
			map = assemblerStudentAttendanceService.loadMapList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(map);
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
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
				AttendanceConstant.ATTENDANCE_STATUS_NAME,
				AttendanceConstant.STUDENT_ATTENDANCE_ID,
				AttendanceConstant.STUDENT_ID,
				AttendanceConstant.CLASS_ATTENDANCE_ID
				));
		StudentAttendanceParam studentAttendanceParam = AttendanceValidator.validateStudentAttendance(params, requiredFields);		
		Date today = CalendarUtil.getTodaysDate();	
				
		try {			
			ClassAttendance classAttendance = classAttendanceService.findObject(studentAttendanceParam.getClassAttendanceId());
			List<StudentAttendanceStatusParam> studentAttendanceStatuses = studentAttendanceParam.getStudentAttendanceStatuses();
			List<StudentAttendance> studentAttendances = new ArrayList<>();
			for (StudentAttendanceStatusParam studentAttendanceStatus : studentAttendanceStatuses) {
				Student student = studentService.findObject(studentAttendanceStatus.getStudentId());
				AttendanceStatus attendanceStatus = attendanceStatusService.findObject(studentAttendanceStatus.getAttendanceStatusId());
				StudentAttendance studentAttendance = studentAttendanceService.findObject(studentAttendanceParam.getId());
				studentAttendance.setStudent(student);
				studentAttendance.setClassAttendance(classAttendance);
				studentAttendance.setAttendanceStatus(attendanceStatus);
				studentAttendance.setAddedBy(authUser.getId());
				studentAttendance.setUpdatedOn(today);					
				studentAttendances.add(studentAttendance);
			}
			studentAttendanceService.updateBatch(studentAttendances);
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
	public HttpResponseEntity<Object> create(Authentication auth, @RequestBody Map<String, Object> params) {
		this.logRequestDetail("POST : " + EndPointConstants.REST_STUDENT_ATTENDANCE_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		
		List<String> requiredFields = new ArrayList<>(Arrays.asList(			
				AttendanceConstant.STUDENT_ATTENDANCE_STATUSES,
				AttendanceConstant.CLASS_ATTENDANCE_ID
				));
		StudentAttendanceParam studentAttendanceParam = AttendanceValidator.validateStudentAttendance(params, requiredFields);		
				
		try {						
			ClassAttendance classAttendance = classAttendanceService.findObject(studentAttendanceParam.getClassAttendanceId());
			List<StudentAttendanceStatusParam> studentAttendanceStatuses = studentAttendanceParam.getStudentAttendanceStatuses();
			instance.setData(studentAttendanceService.createBatch(processRequest(classAttendance, studentAttendanceStatuses)));
			instance.setSuccess(true);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);	
		}
		instance.setHttpStatus(HttpStatus.OK);	
		return instance;
	}

	protected List<StudentAttendance> processRequest(ClassAttendance classAttendance, List<StudentAttendanceStatusParam> studentAttendanceStatuses) throws SQLException {
		List<StudentAttendance> studentAttendances = new ArrayList<>();
		for (StudentAttendanceStatusParam studentAttendanceStatus : studentAttendanceStatuses) {
			Student student = studentService.findObject(studentAttendanceStatus.getStudentId());
			AttendanceStatus attendanceStatus = attendanceStatusService.findObject(studentAttendanceStatus.getAttendanceStatusId());
			
			StudentAttendance sAttendance = studentAttendanceService.getStudentAttendanceByStudentIdAndClassAttendanceId(student.getId(), classAttendance.getId());				
			if (sAttendance == null) {
				StudentAttendance studentAttendance = new StudentAttendance(student, classAttendance, attendanceStatus, authUser.getId(), today, today);
				studentAttendances.add(studentAttendance);
			} else {
				sAttendance.setAttendanceStatus(attendanceStatus);
				sAttendance.setStudent(student);
				studentAttendances.add(sAttendance);
			}				
		}
		return studentAttendances;
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
