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
import com.jerotoma.common.constants.CourseConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.exceptions.UnAuthorizedAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.Course;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.common.utils.validators.CourseValidator;
import com.jerotoma.config.auth.common.UserContext;
import com.jerotoma.config.auth.interfaces.IAuthenticationFacade;
import com.jerotoma.services.courses.CourseService;


@RestController
@RequestMapping(EndPointConstants.REST_COURSE_CONTROLLER.BASE)
public class RestCourseController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired CourseService courseService;
	@Autowired IAuthenticationFacade authenticationFacade;
	
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getCourses(Authentication auth,
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
		logger.debug("getCourses : [model] : {}");
		
		page = page == null ? 1 : page;
		pageSize = pageSize == null ? 12 : pageSize;
		orderby = StringUtility.isEmpty(orderby) || orderby.equals("none") || orderby.equals("undefined") ? "DESC" : orderby;


		QueryParam queryParam = QueryParam.getInstance();
		queryParam.setPage(page);
		queryParam.setPageSize(pageSize);
		queryParam.setFieldName(fieldName);
		queryParam.setOrderby(orderby);
				
		UserContext userContext = authenticationFacade.getUserContext(auth);
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.USER_ROLES.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new Course to the system");
		}
		
		try {
			map = courseService.loadMapList(queryParam);		
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
	protected HttpResponseEntity<Object> createCourse(
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
			throw new UnAuthorizedAccessException("You have no authorization to add new Course to the system");
		}
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						CourseConstant.COURSE_NAME,
						CourseConstant.COURSE_DESCRIPTION,
						CourseConstant.COURSE_CODE));
		
		Course course = CourseValidator.validate(params, requiredFields);
		
		try {
			course = courseService.createObject(course);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(course);
		return instance;
	}

	protected HttpResponseEntity<Object> showCourse() {
		
		return null;
	}


	@PutMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> editcourse(
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
			throw new UnAuthorizedAccessException("You have no authorization to add new Course to the system");
		}
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						CourseConstant.COURSE_NAME,
						CourseConstant.COURSE_DESCRIPTION,
						CourseConstant.COURSE_CODE));
		
		Course course = CourseValidator.validate(params, requiredFields);
		
		try {
			course = courseService.updateObject(course);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(course);
		return instance;
	}

	@DeleteMapping(value = {"/{courseId}", "/{courseId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteCourse(Authentication auth, @PathVariable("courseId") Integer courseId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
		UserContext userContext = authenticationFacade.getUserContext(auth);
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.USER_ROLES.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new Course to the system");
		}
		
		Course course;
		
		try {
			course = courseService.findObject(courseId);	
			if (course == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = courseService.deleteObject(course);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}

}
