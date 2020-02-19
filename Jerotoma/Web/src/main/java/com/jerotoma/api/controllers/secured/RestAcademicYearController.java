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
import com.jerotoma.common.constants.AcademicYearConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.exceptions.UnAuthorizedAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.common.utils.validators.AcademicYearValidator;
import com.jerotoma.config.auth.common.UserContext;
import com.jerotoma.services.courses.AcademicYearService;

@RestController
@RequestMapping(EndPointConstants.REST_ACADEMIC_YEAR_CONTROLLER.BASE)
public class RestAcademicYearController extends BaseController {
		
	@Autowired AcademicYearService academicYearService;
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getAcademicYears(Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		this.securityCheckAccessByRoles(auth);
		this.logRequestDetail("GET : " + EndPointConstants.REST_ACADEMIC_YEAR_CONTROLLER.BASE);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			map = academicYearService.loadMapList(queryParam);		
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
	protected HttpResponseEntity<Object> createAcademicYear(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		List<String> requiredFields;
		this.securityCheckAccessByRoles(auth);
		this.logRequestDetail("POST : " + EndPointConstants.REST_ACADEMIC_YEAR_CONTROLLER.BASE);
			
		requiredFields = new ArrayList<>(
				Arrays.asList(
						AcademicYearConstant.ACADEMIC_YEAR_NAME,
						AcademicYearConstant.ACADEMIC_YEAR_DESCRIPTION,
						AcademicYearConstant.ACADEMIC_YEAR_OF_STUDY));
		
		AcademicYear academicYear = AcademicYearValidator.validate(params, requiredFields);
		
		try {
			academicYear = academicYearService.createObject(academicYear);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(academicYear);
		return instance;
	}

	protected HttpResponseEntity<Object> showAcademicYear() {
		
		return null;
	}


	@PutMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> editAcademicYear(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
	
		List<String> requiredFields;
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		this.securityCheckAccessByRoles(auth);	
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						AcademicYearConstant.ACADEMIC_YEAR_NAME,
						AcademicYearConstant.ACADEMIC_YEAR_DESCRIPTION,
						AcademicYearConstant.ACADEMIC_YEAR_OF_STUDY));
		
		AcademicYear academicYear = AcademicYearValidator.validate(params, requiredFields);
		
		try {
			academicYear = academicYearService.updateObject(academicYear);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(academicYear);
		return instance;
	}

	@DeleteMapping(value = {"/{academicYearId}", "/{academicYearId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteAcademicYear(Authentication auth, @PathVariable("academicYearId") Integer academicYearId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
		UserContext userContext = authenticationFacade.getUserContext(auth);
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.USER_ROLES.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new AcademicYear to the system");
		}
		
		AcademicYear academicYear;
		
		try {
			academicYear = academicYearService.findObject(academicYearId);	
			if (academicYear == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = academicYearService.deleteObject(academicYear);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}

}
