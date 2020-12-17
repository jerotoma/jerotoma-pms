package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.attendances.AttendanceStatus;
import com.jerotoma.services.assemblers.attendances.AssemblerAttendanceReportService;
import com.jerotoma.services.assemblers.attendances.AssemblerAttendanceStatusService;
import com.jerotoma.services.assemblers.attendances.AssemblerStudentAttendanceService;

@RestController
@RequestMapping(EndPointConstants.REST_ATTENDANCE_REPORT_CONTROLLER.BASE)
public class RestAttendanceReportController extends BaseController {
	
	@Autowired AssemblerAttendanceReportService assemblerAttendanceReportService;
	@Autowired AssemblerAttendanceStatusService assemblerAttendanceStatusService;
	@Autowired AssemblerStudentAttendanceService assemblerStudentAttendanceService;
	
	@GetMapping(value = "/students/{studentId}/academic-levels/{academicLevelId}")
	public HttpResponseEntity<Object> loadAttendanceReportsByStudentID(
			Authentication auth,			
			@PathVariable("academicLevelId") Integer academicLevelId,
			@PathVariable("studentId") Integer studentId) {
		
		this.logRequestDetail("POST : " + EndPointConstants.REST_ATTENDANCE_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		try {
			AttendanceStatus attendanceStatus = assemblerAttendanceStatusService.loadAttendanceStatusMarkedAsPresent();
			if (attendanceStatus == null) {
				throw new RuntimeException("Attendance Status Marked as Present is required to continue");
			}
			response.setData(assemblerAttendanceReportService.loadAttendanceReportsByStudentID(studentId, academicLevelId, attendanceStatus.getId()));		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = "/classes/{classId}/students/{studentId}")
	public HttpResponseEntity<Object> loadAttendanceReportDetailsByStudentID(
			Authentication auth,			
			@PathVariable("classId") Integer classId,		
			@PathVariable("studentId") Integer studentId) {
		
		this.logRequestDetail("POST : " + EndPointConstants.REST_ATTENDANCE_REPORT_CONTROLLER.BASE + "/attendance-reports");
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		try {
			response.setData(assemblerStudentAttendanceService.loadStudentClassAttendanceReportsByStudentID(studentId, classId));		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}

}
