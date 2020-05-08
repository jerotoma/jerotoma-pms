package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.ClassRoomConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.Room;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.common.utils.validators.SchoolClassValidator;
import com.jerotoma.config.auth.interfaces.IAuthenticationFacade;
import com.jerotoma.services.courses.RoomService;
import com.jerotoma.services.users.AuthUserService;


@RestController
@RequestMapping(EndPointConstants.REST_CLASS_ROOM_CONTROLLER.BASE)
public class RestClassRoomController  extends BaseController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired RoomService schoolClassService;
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
		
		logger.debug("getSchoolClasss : [model] : {}");
		
		QueryParam queryParam = this.setParams(page, pageSize, fieldName, orderby);
		this.securityCheckAccessByRoles(auth);			
				
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
		this.securityCheckAccessByRoles(auth);	
		List<String> requiredFields;
		AuthUser authUser = authUserService.loadUserByUsername(userContext.getUsername());
		requiredFields = new ArrayList<>(
				Arrays.asList(
						ClassRoomConstant.CLASS_ROOM_NAME,
						ClassRoomConstant.CLASS_ROOM_DESCRIPTION,
						ClassRoomConstant.CLASS_ROOM_CODE));
		
		Room schoolClass = SchoolClassValidator.validate(params, requiredFields);
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
	
		this.securityCheckAccessByRoles(auth);	
		List<String> requiredFields = new ArrayList<>(
				Arrays.asList(
						ClassRoomConstant.CLASS_ROOM_NAME,
						ClassRoomConstant.CLASS_ROOM_DESCRIPTION,
						ClassRoomConstant.CLASS_ROOM_CODE));
		AuthUser authUser = authUserService.loadUserByUsername(userContext.getUsername());
		
		Room schoolClass = SchoolClassValidator.validate(params, requiredFields);
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
			
		this.securityCheckAccessByRoles(auth);		
		Room schoolClass;
		
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