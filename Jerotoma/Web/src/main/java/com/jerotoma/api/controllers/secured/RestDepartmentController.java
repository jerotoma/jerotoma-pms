package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import com.jerotoma.common.constants.DepartmentConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.Department;
import com.jerotoma.common.utils.validators.DepartmentValidator;
import com.jerotoma.services.assemblers.academic.AssemblerDepartmentService;
import com.jerotoma.services.assemblers.academic.DepartmentService;

@RestController
@RequestMapping(EndPointConstants.REST_DEPARTMENT_CONTROLLER.BASE)
public class RestDepartmentController extends BaseController {
	
	@Autowired DepartmentService departmentService;
	@Autowired AssemblerDepartmentService assemblerDepartmentService;
		
	@GetMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getDepartments(Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		Map<String, Object> map = new HashMap<>();
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_DEPARTMENT_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			map = assemblerDepartmentService.loadMapList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(map);
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
	}
	
	@GetMapping(value = {"/{departmentId}", "/{departmentId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getDepartment(Authentication auth, @PathVariable("departmentId") Integer departmentId) {
	
		this.logRequestDetail("GET : " + EndPointConstants.REST_COURSE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		try {
			instance.setData(assemblerDepartmentService.findObject(departmentId));		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
	}
	
	@PostMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> createDepartment(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		this.logRequestDetail("POST : " + EndPointConstants.REST_DEPARTMENT_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		this.securityCheckAdminAccess(auth, "create department");
		
		List<String> requiredFields = new ArrayList<>(
				Arrays.asList(
						DepartmentConstant.DEPARTMENT_NAME));
		
		Department department = DepartmentValidator.validate(params, requiredFields);		
		try {			
			department = departmentService.createObject(department);			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(department);
		return instance;
	}

	protected HttpResponseEntity<Object> showDepartment() {
		
		return null;
	}


	@PutMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> editDepartment(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		this.logRequestDetail("PUT : " + EndPointConstants.REST_DEPARTMENT_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
	
		List<String> requiredFields = new ArrayList<>(
				Arrays.asList(
						DepartmentConstant.DEPARTMENT_NAME,
						DepartmentConstant.DEPARTMENT_ID
						));
		
		Department department = DepartmentValidator.validate(params, requiredFields);
		try {			 
			department = departmentService.updateObject(department);	
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(department);
		return instance;
	}

	@DeleteMapping(value = {"/{departmentId}", "/{departmentId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteDepartment(Authentication auth, @PathVariable("departmentId") Integer departmentId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		this.logRequestDetail("DELETE : " + EndPointConstants.REST_DEPARTMENT_CONTROLLER.BASE + "/" + departmentId);
		this.securityCheckAccessByRoles(auth);
		this.securityCheckAdminAccess(auth, "delete");
		this.proccessLoggedInUser(auth);
		
		Department department;
		
		try {
			department = departmentService.findObject(departmentId);	
			if (department == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = departmentService.deleteObject(department);
			instance.setData(isDeleted);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}
}
