package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.Controller;
import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AttendanceConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.attendances.AttendanceStatus;
import com.jerotoma.common.utils.validators.AttendanceValidator;
import com.jerotoma.services.attendances.AttendanceStatusService;

@RestController
@RequestMapping(EndPointConstants.REST_ATTENDANCE_STATUS_CONTROLLER.BASE)
public class RestAttendanceStatusController extends RestAttendanceController implements Controller {

	@Autowired AttendanceStatusService attendanceStatusService;
	
	@GetMapping({"/paginated", "/paginated/"})
	@Override
	public HttpResponseEntity<Object> index(Authentication auth, String search, Integer page, Integer pageSize,
			String fieldName, String orderby) {
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_ATTENDANCE_STATUS_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);		
		
		try {
			response.setData(attendanceStatusService.loadMapList(queryParam));
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			response.setHttpStatus(HttpStatus.OK);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return response;
	}
	
	@GetMapping
	public HttpResponseEntity<Object> getAll(Authentication auth) {
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_ATTENDANCE_STATUS_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);			
		
		try {
			response.setData(attendanceStatusService.getAll());
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			response.setHttpStatus(HttpStatus.OK);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return response;
	}


	@Override
	public HttpResponseEntity<Object> show(Authentication auth, Integer entityId) {
		
		return null;
	}

	@PutMapping
	@ResponseBody
	@Override
	public HttpResponseEntity<Object> update(Authentication auth, Integer entityId, Map<String, Object> params) {
		this.logRequestDetail("PUT : " + EndPointConstants.REST_ATTENDANCE_STATUS_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		List<String> requiredFields = new ArrayList<>(Arrays.asList(
				AttendanceConstant.ATTENDANCE_STATUS_ID,
				AttendanceConstant.ATTENDANCE_STATUS_NAME));
		AttendanceStatus attendanceStatus  = AttendanceValidator.validateAttendanceStatus(params, requiredFields);				
		try {
			AttendanceStatus mAttendanceStatus = attendanceStatusService.findObject( attendanceStatus.getId());
			mAttendanceStatus.setName(attendanceStatus.getName());
			mAttendanceStatus.setDescription(attendanceStatus.getDescription());
			mAttendanceStatus.setUpdatedOn(attendanceStatus.getUpdatedOn());
			
			response.setData(attendanceStatusService.updateObject(mAttendanceStatus));
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);	
		}
		response.setHttpStatus(HttpStatus.OK);	
		return response;
	}

	@PostMapping
	@ResponseBody
	@Override
	public HttpResponseEntity<Object> create(Authentication auth, Map<String, Object> params) {
		this.logRequestDetail("POST : " + EndPointConstants.REST_STUDENT_ATTENDANCE_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		
		List<String> requiredFields = new ArrayList<>(Arrays.asList(
				AttendanceConstant.ATTENDANCE_STATUS_NAME));
		AttendanceStatus attendanceStatus  = AttendanceValidator.validateAttendanceStatus(params, requiredFields);	
				
		try {
			attendanceStatus.setAddedBy(authUser.getId());		
			response.setData(attendanceStatusService.createObject(attendanceStatus));
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);	
		}
		response.setHttpStatus(HttpStatus.OK);	
		return response;
	}

	@Override
	public HttpResponseEntity<Object> edit(Authentication auth, Map<String, Object> params) {		
		return null;
	}

	@DeleteMapping("/{entityId}")
	@ResponseBody
	@Override
	public HttpResponseEntity<Object> delete(Authentication auth, Integer entityId) {
		this.logRequestDetail("DELTE : " + EndPointConstants.REST_STUDENT_ATTENDANCE_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);				
		try {
			
			this.userSecurityClearance.checkGeneralEntityDeletionPermission();
			
			AttendanceStatus attendanceStatus = attendanceStatusService.findObject(entityId);	
			response.setData(attendanceStatusService.deleteObject(attendanceStatus));
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);	
		}
		response.setHttpStatus(HttpStatus.OK);	
		return response;
	}

}
