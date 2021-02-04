package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
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
import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.users.students.StudentAcademicLevel;
import com.jerotoma.common.utils.validators.StudentAcadmicLevelValidator;
import com.jerotoma.services.students.StudentProgressService;

@RestController
@RequestMapping(EndPointConstants.REST_STUDENT_PROGRESS_CONTROLLER.BASE)
public class RestStudentProgressController extends BaseController {
	
	@Autowired StudentProgressService studentProgressService;
	
	@GetMapping("/classes/students/{studentId}")
	@ResponseBody
	protected HttpResponseEntity<Object> loadStudentAcademicLevelsByStudentId(Authentication auth, @PathVariable("studentId") Integer studentId) {
		this.logRequestDetail("GET : " + EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		response.setData(studentProgressService.findStudentClassesProgressByStudentId(studentId));
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		return response;
	}
	
	
	@GetMapping(value = "/academic-levels/students/{studentId}")
	@ResponseBody
	protected HttpResponseEntity<Object> loadStudentAcademicLevelsProgressByStudentId(Authentication auth, @PathVariable("studentId") Integer studentId) {
		this.logRequestDetail("GET : " + EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		try {
			response.setData(studentProgressService.findStudentAcademicLevelsProgressByStudentId(studentId));
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return response;
	}

	@PostMapping(value = "/academic-levels/students")
	@ResponseBody
	protected HttpResponseEntity<Object> updateStudentAcademicLevelsProgress(Authentication auth, @RequestBody Map<String, Object> params) {
		this.logRequestDetail("GET : " + EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		try {
			List<String> requiredFields;
			this.logRequestDetail("POST : "+ EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE);
			this.securityCheckAccessByRoles(auth);
			userSecurityClearance.checkStudentCreationPermission();
			
			requiredFields = Arrays.asList(
							StudentConstant.Class.PROGRESS_STATUS_ID,
							StudentConstant.Class.ACADEMIC_YEAR_ID,
							StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID,							
							StudentConstant.Class.STUDENT_ID);
			
			StudentAcademicLevel.Fields studentAcademicLevelField = StudentAcadmicLevelValidator.validate(params, requiredFields);
			
			response.setData(studentProgressService.updateStudentAcademicLevelsProgress(studentAcademicLevelField));
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return response;
	}

}
