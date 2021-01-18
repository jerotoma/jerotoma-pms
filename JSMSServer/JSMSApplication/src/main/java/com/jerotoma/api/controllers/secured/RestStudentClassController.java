package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.ProgramConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.users.students.StudentClass.StudentClassParam;
import com.jerotoma.common.utils.validators.StudentClassValidator;
import com.jerotoma.services.academic.StudentClassService;
import com.jerotoma.services.assemblers.academic.AssemblerStudentClassService;

@RestController
@RequestMapping(EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE)
public class RestStudentClassController extends BaseController {
	
	@Autowired AssemblerStudentClassService assemblerStudentClassService;
	@Autowired StudentClassService studentClassService;
	
	@GetMapping("/classes/{classId}")
	@ResponseBody
	protected HttpResponseEntity<Object> loadStudentAcademicLevelsByStudentId(Authentication auth, @PathVariable("classId") Integer classId) {
		this.logRequestDetail("GET : " + EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		try {
			response.setData(assemblerStudentClassService.findStudentClassesByClassId(classId));
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return response;
	}
	
	@PostMapping
	public HttpResponseEntity<Object> create(Authentication auth, @RequestBody Map<String, Object> params) {
		List<String> requiredFields;
		this.logRequestDetail("POST : "+ EndPointConstants.REST_PROGRAM_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						ProgramConstant.NAME,
						ProgramConstant.CODE,
						ProgramConstant.DESCRIPTION
						));
		
		StudentClassParam studentClassParam = StudentClassValidator.validateStudentClassProgress(params, requiredFields);
		userSecurityClearance.checkStudentCreationPermission();		
		try {
			response.setData(studentClassService.createStudentClassProgress(studentClassParam));		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		
		return response;
	}

}
