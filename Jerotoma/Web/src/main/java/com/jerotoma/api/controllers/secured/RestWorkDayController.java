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
import com.jerotoma.common.constants.WorkDayConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.schedules.WorkDay;
import com.jerotoma.common.utils.validators.WorkDayValidator;
import com.jerotoma.services.assemblers.academic.AssemblerWorkDayService;
import com.jerotoma.services.schedules.WorkDayService;


@RestController
@RequestMapping(EndPointConstants.REST_WORK_DAY_CONTROLLER.BASE)
public class RestWorkDayController extends BaseController {
	
	@Autowired WorkDayService workDayService;
	@Autowired AssemblerWorkDayService assemblerWorkDayService;
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getWorkDays(Authentication auth) {
		
		this.securityCheckAccessByRoles(auth);
		this.logRequestDetail("GET : " + EndPointConstants.REST_WORK_DAY_CONTROLLER.BASE);
		
		try {
			response.setData(assemblerWorkDayService.findAllWorkDays());
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}

	
	@GetMapping(value = {"/paginated", "/paginated/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getWorkDaysPaginated(
			Authentication auth,			
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(page, pageSize, fieldName, orderby);
		this.logRequestDetail("GET : " + EndPointConstants.REST_ACADEMIC_YEAR_CONTROLLER.BASE);
		
		try {
			response.setData(assemblerWorkDayService.loadMapList(queryParam));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	@PostMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> createWorkDay(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		List<String> requiredFields;
		this.securityCheckAccessByRoles(auth);
		this.logRequestDetail("POST : " + EndPointConstants.REST_WORK_DAY_CONTROLLER.BASE);
			
		requiredFields = new ArrayList<>(
				Arrays.asList(WorkDayConstant.DAY_ID));
		
		WorkDay workDay = WorkDayValidator.validate(params, requiredFields);
		
		try {
			response.setData(workDayService.createObject(workDay));		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		return response;
	}

	@PutMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> editWorkDay(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
	
		List<String> requiredFields;
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		this.securityCheckAdminAccess(auth, "edit");
		
		requiredFields = new ArrayList<>(
				Arrays.asList(WorkDayConstant.ID, WorkDayConstant.DAY_ID));
		
		WorkDay workDay = WorkDayValidator.validate(params, requiredFields);
		
		try {
			instance.setData(workDayService.updateObject(workDay));		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		return instance;
	}

	@DeleteMapping(value = {"/{workDayId}", "/{workDayId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteWorkDay(Authentication auth, @PathVariable("workDayId") Integer workDayId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
		this.securityCheckAdminAccess(auth, "delete");
		
		WorkDay workDay;
		
		try {
			workDay = workDayService.findObject(workDayId);	
			if (workDay == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = workDayService.deleteObject(workDay);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}

}
