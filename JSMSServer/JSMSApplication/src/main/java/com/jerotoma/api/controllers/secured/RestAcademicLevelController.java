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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.api.controllers.Controller;
import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AcademicLevelConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.utils.validators.AcademicLevelValidator;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.services.academic.AcademicLevelService;
import com.jerotoma.services.academic.StreamService;
import com.jerotoma.services.assemblers.AssemblerStudentService;
import com.jerotoma.services.assemblers.academic.AssemblerAcademicLevelService;

@RestController
@RequestMapping(EndPointConstants.REST_ACADEMIC_LEVEL_CONTROLLER.BASE)
public class RestAcademicLevelController extends BaseController implements Controller {
	
	@Autowired AcademicLevelService academicLevelService;
	@Autowired AssemblerAcademicLevelService assemblerAcademicLevelService;
	@Autowired AssemblerStudentService assemblerStudentService;
	@Autowired StreamService streamService;

	@GetMapping
	@Override
	public HttpResponseEntity<Object> index(Authentication auth, String search, Integer page, Integer pageSize,
			String fieldName, String orderby) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			map = assemblerAcademicLevelService.loadMapList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
								
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(map);
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/list")
	@ResponseBody
	public HttpResponseEntity<Object> getList(Authentication auth) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		
		
		try {
			response.setData(assemblerAcademicLevelService.getAllAcademicLevels());		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
								
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}

	@GetMapping("/programs/{programId}/unadded")
	@ResponseBody
	public HttpResponseEntity<Object> loadUnAddedAcademicLevels(Authentication auth, @PathVariable(required = true, value = "programId") Integer programId) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		
		
		try {
			response.setData(assemblerAcademicLevelService.loadUnAddedAcademicLevelByProgram(programId));
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			response.setHttpStatus(HttpStatus.OK);
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return response;
	}
	
	@GetMapping("/programs/{programId}")
	@ResponseBody
	public HttpResponseEntity<Object> loadAcademicLevelsByProgram(Authentication auth, @PathVariable(required = true, value = "programId") Integer programId) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		
		
		try {
			response.setData(assemblerAcademicLevelService.loadAcademicLevelByProgram(programId));
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			response.setHttpStatus(HttpStatus.OK);
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return response;
	}
	
	@GetMapping("/students/{studentId}")
	@ResponseBody
	public HttpResponseEntity<Object> loadAvailableAcademicLevelsByStudentId(Authentication auth, @PathVariable(required = true, value = "studentId") Integer studentId) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		
		try {
			StudentVO student = assemblerStudentService.findObject(studentId);
			response.setData(assemblerAcademicLevelService.loadAvailableAcademicLevelsByStudentId(student.getProgramId(), studentId));
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			response.setHttpStatus(HttpStatus.OK);
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return response;
	}
	
	@PutMapping
	@Override
	public HttpResponseEntity<Object> update(Authentication auth, Integer entityId, Map<String, Object> params) {
		List<String> requiredFields;
		this.logRequestDetail("PUT : "+ EndPointConstants.REST_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						AcademicLevelConstant.ID,
						AcademicLevelConstant.NAME,
						AcademicLevelConstant.CODE,
						AcademicLevelConstant.DESCRIPTION						
						));
		
		AcademicLevel academicLevel = AcademicLevelValidator.validate(params, requiredFields);
		userSecurityClearance.checkGeneralEntityModificationPermission();
		
		try {
			academicLevel = academicLevelService.updateObject(academicLevel);	
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(academicLevel);
		return response;
	}

	@PostMapping
	@Override
	public HttpResponseEntity<Object> create(Authentication auth, Map<String, Object> params) {
		List<String> requiredFields;
		this.logRequestDetail("POST : " + EndPointConstants.REST_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						AcademicLevelConstant.NAME,
						AcademicLevelConstant.CODE,
						AcademicLevelConstant.DESCRIPTION						
						));
		
		AcademicLevel academicLevel = AcademicLevelValidator.validate(params, requiredFields);
		userSecurityClearance.checkGeneralEntityCreationPermission();
		
		try {
			academicLevel = academicLevelService.createObject(academicLevel);	
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(academicLevel);
		return response;
	}
	
	@DeleteMapping(value = {"/{entityId}"})
	@Override
	public HttpResponseEntity<Object> delete(Authentication auth, Integer entityId) {
		this.logRequestDetail("DELETE : "+ EndPointConstants.REST_ACADEMIC_LEVEL_CONTROLLER.BASE + "/" + entityId);
		this.securityCheckAccessByRoles(auth);
		userSecurityClearance.checkGeneralEntityDeletionPermission();
		try {
			AcademicLevel academicLevel = academicLevelService.findObject(entityId);	
			response.setData(academicLevelService.deleteObject(academicLevel));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}

	@PostMapping("/{entityId}/streams")
	@SuppressWarnings("unchecked")
	public HttpResponseEntity<Object> addStreamsToAcademicLevel(
			Authentication auth, 
			@PathVariable(required = true, value = "entityId") Integer entityId, 
			@RequestBody Map<String, Object> params) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_LEVEL_CONTROLLER.BASE + "/" + entityId);
		this.securityCheckAccessByRoles(auth);	
		
		List<Integer> streamIds = (ArrayList<Integer>)params.get("streamIds");
		if (streamIds == null) {
			throw new FieldRequiredException("At least One Stream is required to continue");
		}
		
		try {
			AcademicLevel academicLevel = academicLevelService.addStreamsToAcademicLevel(entityId, streamIds);	
			response.setData(academicLevel);
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	
	@GetMapping("/{entityId}/streams")
	public HttpResponseEntity<Object> getStreamsByAcademicLevelId(
			Authentication auth, 			
			@PathVariable(required = true, value = "entityId") Integer entityId) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_LEVEL_CONTROLLER.BASE + "/" + entityId);
		this.securityCheckAccessByRoles(auth);	
		
		try {
		response.setData(streamService.getStreamsByAcademicLevelId(entityId));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@DeleteMapping("/{entityId}/streams/{streamId}")
	public HttpResponseEntity<Object> addStreamsToAcademicLevel(
			Authentication auth, 
			@PathVariable(required = true, value = "streamId") Integer streamId, 
			@PathVariable(required = true, value = "entityId") Integer entityId) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_LEVEL_CONTROLLER.BASE + "/" + entityId);
		this.securityCheckAccessByRoles(auth);	
		
		try {
		response.setData(academicLevelService.deleteStreamsFromAcademicLevel(entityId, streamId));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}

	@GetMapping(value = {"/{entityId}"})
	@Override
	public HttpResponseEntity<Object> show(Authentication auth, Integer entityId) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_LEVEL_CONTROLLER.BASE + "/" + entityId);
		this.securityCheckAccessByRoles(auth);
		
		try {
			AcademicLevel academicLevel = academicLevelService.findObject(entityId);	
			response.setData(academicLevel);
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
