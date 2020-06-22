package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import com.jerotoma.common.constants.UserPreferenceConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.config.UserPreference;
import com.jerotoma.common.utils.validators.UserPreferenceValidator;
import com.jerotoma.services.configs.UserPreferenceService;

@RestController
@RequestMapping(EndPointConstants.REST_USER_PREFERENCE_CONTROLLER.BASE)
public class RestUserPreferenceController extends BaseController {
	
	@Autowired UserPreferenceService userPreferenceService;;
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	public HttpResponseEntity<Object> getUserPreferences(
			Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_USER_PREFERENCE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			map = userPreferenceService.loadMapList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(map);
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	
	@GetMapping(value = {"/list", "/list/"})
	@ResponseBody
	public HttpResponseEntity<Object> loadUserPreferenceList(
			Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		List<UserPreference> userPreferences = new ArrayList<>();
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_PREFERENCE_CONTROLLER.BASE + "/list");
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			userPreferences = userPreferenceService.loadList(queryParam);			
			super.response.setSuccess(true);
		} catch (SQLException | EmptyResultDataAccessException e) {
			if (e instanceof EmptyResultDataAccessException) {
				response.setSuccess(false); 
			} else {
				throw new JDataAccessException(e.getMessage(), e);
			}			
		}	
		super.response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		super.response.setData(userPreferences);
		super.response.setHttpStatus(HttpStatus.OK);
		return super.response;
	}

	
	@GetMapping(value = {"/{id}", "/{id}/"})
	@ResponseBody
	public HttpResponseEntity<Object> getUserPreference(Authentication auth, @PathVariable("id") Integer userPreferenceId) throws JDataAccessException {
	
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_PREFERENCE_CONTROLLER.BASE + "/" + userPreferenceId);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		UserPreference userPreference = null;
		
		try {
			userPreference = userPreferenceService.findObject(userPreferenceId);
			response.setSuccess(true);
		} catch (SQLException | EmptyResultDataAccessException e) {
			if (e instanceof EmptyResultDataAccessException) {
				userPreference = null; 
				response.setSuccess(false);
			} else {
				throw new JDataAccessException(e.getMessage(), e);
			}			
		}
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(userPreference);
		return response;
	}
	
	@GetMapping(value = {"/keys", "/keys/"})
	@ResponseBody
	public HttpResponseEntity<Object> getUserPreferenceByKey(Authentication auth, @RequestParam(required = true, value="key") String userPreferenceKey) throws JDataAccessException {
	
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_PREFERENCE_CONTROLLER.BASE + "/keys");
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		UserPreference userPreference = null;
		
		try {
			userPreference = userPreferenceService.findObjectUniqueKey(userPreferenceKey);
			response.setSuccess(true);
		} catch (SQLException | EmptyResultDataAccessException e) {
			if (e instanceof EmptyResultDataAccessException) {
				userPreference = null; 
				response.setSuccess(false);
			} else {
				throw new JDataAccessException(e.getMessage(), e);
			}		
		}
			
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(userPreference);
		return response;
	}


	

	@PostMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> createUserPreference(Authentication auth, @RequestBody Map<String, Object> params) throws JDataAccessException {
		
		List<String> requiredFields;
		this.logRequestDetail("POST : " + EndPointConstants.REST_USER_PREFERENCE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						UserPreferenceConstant.NAME,
						UserPreferenceConstant.VALUE));
		
		UserPreference userPreference = UserPreferenceValidator.validate(params, requiredFields);
		userPreference.setUserId(authUser.getId());
		
		try {
			userPreference = userPreferenceService.createObject(userPreference);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(userPreference);
		return response;
	}

	@PutMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> updateUserPreference(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
	
		List<String> requiredFields;
		this.logRequestDetail("PUT : "+ EndPointConstants.REST_USER_PREFERENCE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						UserPreferenceConstant.NAME,
						UserPreferenceConstant.VALUE));
		
		UserPreference userPreference = UserPreferenceValidator.validate(params, requiredFields);		
		userPreference.setUserId(authUser.getId());
		
		try {
			userPreference = userPreferenceService.updateObject(userPreference);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(userPreference);
		return response;
	}

	@DeleteMapping(value = {"/{id}", "/{id}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteUserPreference(Authentication auth, @PathVariable("id") Integer userPreferenceId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
		
		this.logRequestDetail("DELETE : " + EndPointConstants.REST_USER_PREFERENCE_CONTROLLER.BASE + "/" + userPreferenceId);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		UserPreference userPreference = null; 
		
		try {
			userPreference = userPreferenceService.findObject(userPreferenceId);	
			if (userPreference == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = userPreferenceService.deleteObject(userPreference);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}

}
