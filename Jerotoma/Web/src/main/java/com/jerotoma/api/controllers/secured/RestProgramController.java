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
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.api.controllers.Controller;
import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.ProgramConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.Program;
import com.jerotoma.common.utils.validators.ProgramValidator;
import com.jerotoma.services.courses.ProgramService;

@RestController
@RequestMapping(EndPointConstants.REST_PROGRAM_CONTROLLER.BASE)
public class RestProgramController extends BaseController implements Controller {
	
	@Autowired ProgramService programService;

	@Override
	public HttpResponseEntity<Object> index(Authentication auth, String search, Integer page, Integer pageSize,
			String fieldName, String orderby) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_PROGRAM_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			map = programService.loadMapList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
								
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(map);
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}

	@GetMapping(value = {"/{entityId}"})
	@Override
	public HttpResponseEntity<Object> show(Authentication auth, Integer entityId) {
		this.logRequestDetail("GET : "+ EndPointConstants.REST_PROGRAM_CONTROLLER.BASE + "/" + entityId);
		this.securityCheckAccessByRoles(auth);
		
		try {
			Program program = programService.findObject(entityId);	
			response.setData(program);
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}

	@PutMapping
	@Override
	public HttpResponseEntity<Object> update(Authentication auth, Integer entityId, Map<String, Object> params) {
		List<String> requiredFields;
		this.logRequestDetail("PUT : "+ EndPointConstants.REST_PROGRAM_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						ProgramConstant.ID,
						ProgramConstant.NAME,
						ProgramConstant.CODE,
						ProgramConstant.DESCRIPTION,
						ProgramConstant.TOTAL_ACADEMIC_LEVELS_REQUIRED
						));
		
		Program program = ProgramValidator.validate(params, requiredFields);
		userSecurityClearance.checkProgramCreationPermission();
		
		try {
			program = programService.createObject(program);	
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(program);
		return response;
	}

	@PostMapping
	@Override
	public HttpResponseEntity<Object> create(Authentication auth, Map<String, Object> params) {
		List<String> requiredFields;
		this.logRequestDetail("POST : "+ EndPointConstants.REST_PROGRAM_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						ProgramConstant.ID,
						ProgramConstant.NAME,
						ProgramConstant.CODE,
						ProgramConstant.DESCRIPTION,
						ProgramConstant.TOTAL_ACADEMIC_LEVELS_REQUIRED
						));
		
		Program program = ProgramValidator.validate(params, requiredFields);
		userSecurityClearance.checkProgramCreationPermission();
		
		try {
			program = programService.createObject(program);	
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(program);
		return response;
	}

	@DeleteMapping(value = {"/{entityId}"})
	@Override
	public HttpResponseEntity<Object> delete(Authentication auth, Integer entityId) {
		this.logRequestDetail("DELETE : "+ EndPointConstants.REST_PROGRAM_CONTROLLER.BASE + "/" + entityId);
		this.securityCheckAccessByRoles(auth);
		userSecurityClearance.checkGeneralEntityDeletionPermission();
		try {
			Program program = programService.findObject(entityId);	
			response.setData(programService.deleteObject(program));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@Override
	public HttpResponseEntity<Object> edit(Authentication auth, Map<String, Object> params) {
		
		return null;
	}


}
