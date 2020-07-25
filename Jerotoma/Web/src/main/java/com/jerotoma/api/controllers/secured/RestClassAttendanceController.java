package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AttendanceConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.common.models.academic.Class;
import com.jerotoma.common.models.attendances.ClassAttendance;
import com.jerotoma.common.models.attendances.ClassAttendance.ClassAttendanceParam;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.validators.AttendanceValidator;

@RestController
@RequestMapping(EndPointConstants.REST_CLASS_ATTENDANCE_CONTROLLER .BASE)
public class RestClassAttendanceController extends RestAttendanceController {
	
	
	@GetMapping(value = {"", "/"})
	public HttpResponseEntity<Object> index(Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		try {
			response.setData(assemblerClassAttendanceService.getAll());		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@PostMapping
	@ResponseBody
	public HttpResponseEntity<Object> create(Authentication auth, @RequestBody Map<String, Object> params) {
		
		this.logRequestDetail("POST : " + EndPointConstants.REST_ATTENDANCE_CONTROLLER.BASE + "/classes");
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		
		List<String> requiredFields = new ArrayList<>(Arrays.asList(
						AttendanceConstant.CLASS_ID,
						AttendanceConstant.ACADEMIC_YEAR_ID,
						AttendanceConstant.ATTENDANCE_DATE));
		ClassAttendanceParam classAttendanceParam = AttendanceValidator.validateClassAttendance(params, requiredFields);		
		Date today = CalendarUtil.getTodaysDate();	
		ClassAttendance classAttendance = null;	
		
		try {
			AcademicYear academicYear = academicYearService.findObject(classAttendanceParam.getAcademicYearId());
			Class mClass = classService.findObject(classAttendanceParam.getClassId());			
			classAttendance = new ClassAttendance(mClass, academicYear, classAttendanceParam.getAttendanceDate(), authUser.getId(), today, today);
			
			response.setData(classAttendanceService.createObject(classAttendance));
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);	
		}
		response.setHttpStatus(HttpStatus.OK);	
		return response;
	}

	@GetMapping("/paginated")	
	@ResponseBody
	public HttpResponseEntity<Object> getClassAttendances(Authentication auth, 
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {		
		
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(page, pageSize, fieldName, orderby);
		try {
			map = assemblerClassAttendanceService.loadMapList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(map);
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/{classAttendanceId}")	
	@ResponseBody
	public HttpResponseEntity<Object> getClassAttendance(Authentication auth, @PathVariable(name="classAttendanceId") Integer classAttendanceId) {		
		
		this.securityCheckAccessByRoles(auth);
		
		try {
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			response.setData(assemblerClassAttendanceService.findObject(classAttendanceId));
			response.setHttpStatus(HttpStatus.OK);
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		
		return response;
	}
}
