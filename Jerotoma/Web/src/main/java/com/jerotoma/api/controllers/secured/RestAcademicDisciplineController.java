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
import com.jerotoma.common.constants.AcademicDisciplineConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.exceptions.UnAuthorizedAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academicDisciplines.AcademicDiscipline;
import com.jerotoma.common.utils.validators.AcademicDisciplineValidator;
import com.jerotoma.config.auth.common.UserContext;
import com.jerotoma.services.academicdisciplines.AcademicDisciplineService;


@RestController
@RequestMapping(EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE)
public class RestAcademicDisciplineController extends BaseController {
	
	@Autowired AcademicDisciplineService academicDisciplineService;;
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	public HttpResponseEntity<Object> getFieldOfStudies(
			Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			map = academicDisciplineService.loadMapList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(map);
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
	}
	
	
	@GetMapping(value = {"/list", "/list/"})
	@ResponseBody
	public HttpResponseEntity<Object> loadFieldOfStudyList(
			Authentication auth) {
		
		List<AcademicDiscipline> academicDisciplines = new ArrayList<>();
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE + "/list");
		this.securityCheckAccessByRoles(auth);
		
		try {
			academicDisciplines = academicDisciplineService.loadList();
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		super.instance.setSuccess(true);
		super.instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		super.instance.setData(academicDisciplines);
		super.instance.setHttpStatus(HttpStatus.OK);
		return super.instance;
	}


	protected HttpResponseEntity<Object> getShowPosition() {
		
		return null;
	}


	protected HttpResponseEntity<Object> updatePosition() {
		
		return null;
	}

	@PostMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> createPosition(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		List<String> requiredFields;
		this.logRequestDetail("POST : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_NAME,
						AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_DESCRIPTION,
						AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_CODE));
		
		AcademicDiscipline fieldOfStudy = AcademicDisciplineValidator.validate(params, requiredFields);
		
		try {
			fieldOfStudy = academicDisciplineService.createObject(fieldOfStudy);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(fieldOfStudy);
		return instance;
	}

	@PutMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> editPosition(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
	
		List<String> requiredFields;
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
		UserContext userContext = authenticationFacade.getUserContext(auth);
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.USER_ROLES.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new Teacher to the system");
		}
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_NAME,
						AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_DESCRIPTION,
						AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_CODE));
		
		AcademicDiscipline fieldOfStudy = AcademicDisciplineValidator.validate(params, requiredFields);
		
		try {
			fieldOfStudy = academicDisciplineService.updateObject(fieldOfStudy);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(fieldOfStudy);
		return instance;
	}

	@DeleteMapping(value = {"/{fieldOfStudyId}", "/{fieldOfStudyId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteFieldOfStudy(Authentication auth, @PathVariable("fieldOfStudyId") Integer fieldOfStudyId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
		UserContext userContext = authenticationFacade.getUserContext(auth);
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.USER_ROLES.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new Teacher to the system");
		}
		
		AcademicDiscipline fieldOfStudy;
		
		try {
			fieldOfStudy = academicDisciplineService.findObject(fieldOfStudyId);	
			if (fieldOfStudy == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = academicDisciplineService.deleteObject(fieldOfStudy);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}

}
