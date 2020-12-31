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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.jerotoma.services.academic.AcademicLevelService;
import com.jerotoma.services.academic.ProgramService;
import com.jerotoma.services.assemblers.academic.AssemblerAcademicLevelService;
import com.jerotoma.services.assemblers.academic.AssemblerProgramService;

@RestController
@RequestMapping(EndPointConstants.REST_PROGRAM_CONTROLLER.BASE)
public class RestProgramController extends BaseController implements Controller {
	
	@Autowired ProgramService programService;
	@Autowired AssemblerProgramService assemblerProgramService;
	@Autowired AssemblerAcademicLevelService assemblerAcademicLevelService;
	@Autowired AcademicLevelService academicLevelService;

	@GetMapping
	@Override
	public HttpResponseEntity<Object> index(
			Authentication auth, 
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_PROGRAM_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			map = assemblerProgramService.loadMapList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
								
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(map);
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = {"/list"})
	@ResponseBody
	public HttpResponseEntity<Object> getList(Authentication auth) {
		this.logRequestDetail("GET : "+ EndPointConstants.REST_PROGRAM_CONTROLLER.BASE + "/list");
		this.securityCheckAccessByRoles(auth);		
		try {
			response.setData(assemblerProgramService.getAllProgram());
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			response.setHttpStatus(HttpStatus.OK);
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return response;
	}

	@GetMapping(value = {"/{entityId}"})
	@Override
	public HttpResponseEntity<Object> show(Authentication auth, Integer entityId) {
		this.logRequestDetail("GET : "+ EndPointConstants.REST_PROGRAM_CONTROLLER.BASE + "/" + entityId);
		this.securityCheckAccessByRoles(auth);		
		try {
			response.setData(assemblerProgramService.findObject(entityId));
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
		this.logRequestDetail("PUT : " + EndPointConstants.REST_PROGRAM_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						ProgramConstant.ID,
						ProgramConstant.NAME,
						ProgramConstant.CODE,
						ProgramConstant.DESCRIPTION,						
						ProgramConstant.ACADEMIC_LEVEL_IDS,
						ProgramConstant.ACADEMIC_LEVEL_COMPLETION_ORDERS
						));
		Program program = ProgramValidator.validate(params, requiredFields);
		List<Integer> academicLevelIDs = program.getAcademicLevelIDs();
		userSecurityClearance.checkProgramCreationPermission();
		
		try {
			program =  programService.updateProgramAndAssociateAcademicLevels(program, academicLevelIDs);
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
						ProgramConstant.NAME,
						ProgramConstant.CODE,
						ProgramConstant.DESCRIPTION
						));
		
		Program program = ProgramValidator.validate(params, requiredFields);
		//List<Integer> academicLevelIDs = program.getAcademicLevelIDs();
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
	
	@DeleteMapping(value = {"/{programId}/academic-levels/{academicLevelId}"})
	public HttpResponseEntity<Object> removeAcademicLevelFromProgram(Authentication auth, 
			@PathVariable(required = true, value = "programId") Integer programId,
			@PathVariable(required = true, value = "academicLevelId") Integer academicLevelId) {
		
		this.logRequestDetail("DELETE : "+ EndPointConstants.REST_PROGRAM_CONTROLLER.BASE + "/" + programId + "/academic-levels/" + academicLevelId);
		this.securityCheckAccessByRoles(auth);
		userSecurityClearance.checkGeneralEntityDeletionPermission();
		
				
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
