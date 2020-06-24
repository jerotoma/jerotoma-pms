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
import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.config.SystemConfig;
import com.jerotoma.common.utils.validators.SystemConfigValidator;
import com.jerotoma.common.viewobjects.SystemConfigVO;
import com.jerotoma.services.assemblers.systemconfigs.AssemblerSystemConfigService;
import com.jerotoma.services.assemblers.systemconfigs.AssemblerUserPreferenceService;
import com.jerotoma.services.configs.SystemConfigService;
import com.jerotoma.services.configs.UserPreferenceService;

@RestController
@RequestMapping(EndPointConstants.REST_SYSTEM_CONFIG_CONTROLLER.BASE)
public class RestSystemConfigController extends BaseController {
	
	@Autowired AssemblerSystemConfigService assemblerSystemConfigService;
	@Autowired AssemblerUserPreferenceService assemblerUserPreferenceService;
	@Autowired SystemConfigService systemConfigService;
	@Autowired UserPreferenceService userPreferenceService;;
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	public HttpResponseEntity<Object> getSystemConfigs(
			Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_SYSTEM_CONFIG_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			map = assemblerSystemConfigService.loadMapList(queryParam);		
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
	public HttpResponseEntity<Object> loadSystemConfigList(
			Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		List<SystemConfigVO> systemConfigs = new ArrayList<>();
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_SYSTEM_CONFIG_CONTROLLER.BASE + "/list");
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			systemConfigs = assemblerSystemConfigService.loadList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		super.response.setSuccess(true);
		super.response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		super.response.setData(systemConfigs);
		super.response.setHttpStatus(HttpStatus.OK);
		return super.response;
	}

	
	@GetMapping(value = {"/{id}", "/{id}/"})
	@ResponseBody
	public HttpResponseEntity<Object> getSystemConfig(Authentication auth, @PathVariable("id") Integer systemConfigId) throws JDataAccessException {
	
		this.logRequestDetail("GET : " + EndPointConstants.REST_SYSTEM_CONFIG_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		SystemConfigVO systemConfig = null;
		
		try {
			systemConfig = assemblerSystemConfigService.findObject(systemConfigId);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(systemConfig);
		return response;
	}
	
	@GetMapping(value = {"/keys", "/keys/"})
	@ResponseBody
	public HttpResponseEntity<Object> getSystemConfigByKey(Authentication auth, @RequestParam(required = true, value="key") String systemConfigKey) throws JDataAccessException {
		this.logRequestDetail("GET : " + EndPointConstants.REST_SYSTEM_CONFIG_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);		
		SystemConfigVO systemConfig = null;		
		try {
			systemConfig = assemblerSystemConfigService.findObjectUniqueKey(systemConfigKey);		
		} catch (SQLException | EmptyResultDataAccessException e) {
			
			if (e instanceof EmptyResultDataAccessException) {
				systemConfig = null;
			} else {
				throw new JDataAccessException(e.getMessage(), e);
			}		
		}		
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(systemConfig);
		return response;
	}


	

	@PostMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> createSystemConfig(Authentication auth, @RequestBody Map<String, Object> params) throws JDataAccessException {
		
		List<String> requiredFields;
		this.logRequestDetail("POST : " + EndPointConstants.REST_SYSTEM_CONFIG_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						SystemConfigConstant.NAME,
						SystemConfigConstant.VALUE));
		
		SystemConfig systemConfig = SystemConfigValidator.validate(params, requiredFields);
		
		try {
			systemConfig = systemConfigService.createObject(systemConfig);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(systemConfig);
		return response;
	}

	@PutMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> updateSystemConfig(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
	
		List<String> requiredFields;
		this.logRequestDetail("PUT : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						SystemConfigConstant.NAME,
						SystemConfigConstant.VALUE));
		
		SystemConfig systemConfig = SystemConfigValidator.validate(params, requiredFields);
		
		try {
			
			
			SystemConfig dbSystemConfig  = systemConfigService.findObjectUniqueKey(systemConfig.getName());
			systemConfig.setId(dbSystemConfig.getId());			
			systemConfig = systemConfigService.updateObject(systemConfig);		
		} catch (SQLException | EmptyResultDataAccessException e) {
			if (e instanceof EmptyResultDataAccessException) {
				try {
					systemConfig = systemConfigService.createObject(systemConfig);	
				} catch (SQLException ee) {
					throw new JDataAccessException(ee.getMessage(), ee);
				}
			} else {
				throw new JDataAccessException(e.getMessage(), e);
			}		
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(systemConfig);
		return response;
	}

	@DeleteMapping(value = {"/{id}", "/{id}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteFieldOfStudy(Authentication auth, @PathVariable("id") Integer systemConfigId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
		
		this.logRequestDetail("DELETE : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE );
		this.securityCheckAccessByRoles(auth);
		
		SystemConfig systemConfig = null; 
		
		try {
			systemConfig = systemConfigService.findObject(systemConfigId);	
			if (systemConfig == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = systemConfigService.deleteObject(systemConfig);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}

}
