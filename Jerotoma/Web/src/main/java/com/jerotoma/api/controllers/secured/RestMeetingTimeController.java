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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.MeetingTimeConstant;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.exceptions.UnAuthorizedAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.schedules.MeetingTime;
import com.jerotoma.common.utils.validators.MeetingTimeValidator;
import com.jerotoma.config.auth.common.UserContext;
import com.jerotoma.services.assemblers.academic.AssemblerMeetingTimeService;
import com.jerotoma.services.schedules.MeetingTimeService;

@RestController
@RequestMapping(EndPointConstants.REST_MEETING_TIME_CONTROLLER.BASE)
public class RestMeetingTimeController extends BaseController {
	
	@Autowired MeetingTimeService meetingTimeService;
	@Autowired AssemblerMeetingTimeService assemblerMeetingTimeService;
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getMeetingTimes(Authentication auth) {
		
		this.securityCheckAccessByRoles(auth);
		this.logRequestDetail("GET : " + EndPointConstants.REST_MEETING_TIME_CONTROLLER.BASE);
		
		try {
			instance.setData(assemblerMeetingTimeService.findAllMeetingTimes());
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));		
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
	}

	
	@GetMapping(value = {"/paginated", "/paginated/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getMeetingTimesPaginated(
			Authentication auth,			
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(page, pageSize, fieldName, orderby);
		this.logRequestDetail("GET : " + EndPointConstants.REST_ACADEMIC_YEAR_CONTROLLER.BASE);
		
		try {
			instance.setData(assemblerMeetingTimeService.loadMapList(queryParam));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));		
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
	}
	@PostMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> createMeetingTime(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		List<String> requiredFields;
		this.securityCheckAccessByRoles(auth);
		this.logRequestDetail("POST : " + EndPointConstants.REST_ACADEMIC_YEAR_CONTROLLER.BASE);
			
		requiredFields = new ArrayList<>(
				Arrays.asList(MeetingTimeConstant.TIME));
		
		MeetingTime meetingTime = MeetingTimeValidator.validate(params, requiredFields);
		
		try {
			instance.setData(meetingTimeService.createObject(meetingTime));		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		return instance;
	}

	@PutMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> editMeetingTime(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
	
		List<String> requiredFields;
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		this.securityCheckAdminAccess(auth, "edit");
		
		requiredFields = new ArrayList<>(
				Arrays.asList(MeetingTimeConstant.TIME));
		
		MeetingTime meetingTime = MeetingTimeValidator.validate(params, requiredFields);
		
		try {
			instance.setData(meetingTimeService.updateObject(meetingTime));		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		return instance;
	}

	@DeleteMapping(value = {"/{meetingTimeId}", "/{meetingTimeId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteMeetingTime(Authentication auth, @PathVariable("meetingTimeId") Integer meetingTimeId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
		UserContext userContext = authenticationFacade.getUserContext(auth);
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.USER_ROLES.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new meetingTime to the system");
		}
		
		MeetingTime meetingTime;
		
		try {
			meetingTime = meetingTimeService.findObject(meetingTimeId);	
			if (meetingTime == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = meetingTimeService.deleteObject(meetingTime);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}

}
