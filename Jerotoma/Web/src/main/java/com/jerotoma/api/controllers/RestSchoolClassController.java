package com.jerotoma.api.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.SchoolClassConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.exceptions.UnAuthorizedAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.SchoolClass;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.common.utils.validators.SchoolClassValidator;
import com.jerotoma.config.auth.common.UserContext;
import com.jerotoma.config.auth.interfaces.IAuthenticationFacade;
import com.jerotoma.services.courses.SchoolClassService;
import com.jerotoma.services.users.AuthUserService;


@RestController
@RequestMapping(EndPointConstants.REST_SCHOOL_CLASS_CONTROLLER.BASE)
public class RestSchoolClassController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired SchoolClassService schoolClassService;
	@Autowired IAuthenticationFacade authenticationFacade;
	@Autowired AuthUserService authUserService;
	
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getSchoolClasss(Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
		Map<String, Object> map = new HashMap<>();
		
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
		logger.debug("getSchoolClasss : [model] : {}");
		
		page = page == null ? 1 : page;
		pageSize = pageSize == null ? 12 : pageSize;
		orderby = StringUtility.isEmpty(orderby) || orderby.equals("none") || orderby.equals("undefined") ? "DESC" : orderby;


		QueryParam queryParam = QueryParam.getInstance();
		queryParam.setPage(page);
		queryParam.setPageSize(pageSize);
		queryParam.setFieldName(fieldName);
		queryParam.setOrderby(orderby);
				
		UserContext userContext = authenticationFacade.getUserContext(auth);
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.EROLE.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new SchoolClass to the system");
		}
		
		try {
			map = schoolClassService.loadMapList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(map);
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
	}

	@PostMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> createSchoolClass(
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
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.EROLE.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new SchoolClass to the system");
		}
		AuthUser authUser = authUserService.loadUserByUsername(userContext.getUsername());
		requiredFields = new ArrayList<>(
				Arrays.asList(
						SchoolClassConstant.SCHOOL_CLASS_NAME,
						SchoolClassConstant.SCHOOL_CLASS_DESCRIPTION,
						SchoolClassConstant.SCHOOL_CLASS_CODE));
		
		SchoolClass schoolClass = SchoolClassValidator.validate(params, requiredFields);
		schoolClass.setUpdatedBy(authUser.getId());
		try {
			schoolClass = schoolClassService.createObject(schoolClass);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(schoolClass);
		return instance;
	}

	protected HttpResponseEntity<Object> showSchoolClass() {
		
		return null;
	}


	@PutMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> editschoolClass(
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
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.EROLE.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new SchoolClass to the system");
		}
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						SchoolClassConstant.SCHOOL_CLASS_NAME,
						SchoolClassConstant.SCHOOL_CLASS_DESCRIPTION,
						SchoolClassConstant.SCHOOL_CLASS_CODE));
		AuthUser authUser = authUserService.loadUserByUsername(userContext.getUsername());
		
		SchoolClass schoolClass = SchoolClassValidator.validate(params, requiredFields);
		schoolClass.setUpdatedBy(authUser.getId());
		
		try {
			schoolClass = schoolClassService.updateObject(schoolClass);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(schoolClass);
		return instance;
	}

	@DeleteMapping(value = {"/{schoolClassId}", "/{schoolClassId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteSchoolClass(Authentication auth, @PathVariable("schoolClassId") Integer schoolClassId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
		UserContext userContext = authenticationFacade.getUserContext(auth);
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.EROLE.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new SchoolClass to the system");
		}
		
		SchoolClass schoolClass;
		
		try {
			schoolClass = schoolClassService.findObject(schoolClassId);	
			if (schoolClass == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = schoolClassService.deleteObject(schoolClass);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}

}