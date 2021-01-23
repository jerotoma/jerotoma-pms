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
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.users.students.StudentAcademicLevel;
import com.jerotoma.common.models.users.students.StudentClass;
import com.jerotoma.common.utils.validators.StudentAcadmicLevelValidator;
import com.jerotoma.common.viewobjects.StudentAcademicLevelVO;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.services.academic.AcademicLevelService;
import com.jerotoma.services.academic.AcademicYearService;
import com.jerotoma.services.academic.ClassService;
import com.jerotoma.services.academic.CourseService;
import com.jerotoma.services.academic.StudentClassService;
import com.jerotoma.services.assemblers.academic.AssemblerStudentAcademicLevelService;
import com.jerotoma.services.assemblers.academic.AssemblerStudentClassService;
import com.jerotoma.services.students.StudentAcademicLevelService;
import com.jerotoma.services.students.StudentProgressService;
import com.jerotoma.services.users.StudentService;

@RestController
@RequestMapping(EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE)
public class RestStudentAcademicLevelController  extends BaseController {
	
	@Autowired StudentClassService studentClassService;
	@Autowired AssemblerStudentAcademicLevelService assemblerStudentAcademicLevelService;
	@Autowired AssemblerStudentClassService assemblerStudentClassService;
	@Autowired AcademicYearService academicYearService;
	@Autowired StudentAcademicLevelService studentAcademicLevelService;
	@Autowired AcademicLevelService academicLevelService;
	@Autowired CourseService courseService;
	@Autowired ClassService jClassService;
	@Autowired StudentService studentService;
	@Autowired StudentProgressService studentProgressService;
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	public HttpResponseEntity<Object> getStudentAcademicLevels(
			Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			map = assemblerStudentAcademicLevelService.loadMapList(queryParam);		
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
	public HttpResponseEntity<Object> loadStudentClassList(
			Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		List<StudentAcademicLevelVO> studentClasses = new ArrayList<>();
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE + "/list");
		this.securityCheckAccessByRoles(auth);
		try {
			studentClasses = assemblerStudentAcademicLevelService.loadList();		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		super.response.setSuccess(true);
		super.response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		super.response.setData(studentClasses);
		super.response.setHttpStatus(HttpStatus.OK);
		return super.response;
	}

	@PostMapping(value = "{academicLevelId}/classes")
	@ResponseBody
	protected HttpResponseEntity<Object> createStudentAcademicLevelClasses(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		List<String> requiredFields;
		this.logRequestDetail("POST : " + EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		userSecurityClearance.checkStudentCreationPermission();
		
		requiredFields = Arrays.asList(
						StudentConstant.Class.ACADEMIC_YEAR_ID,
						StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID,
						StudentConstant.Class.STUDENT_IDS,
						StudentConstant.Class.CLASS_ID);
		
		StudentAcademicLevel.Fields studentAcademicLevelField = StudentAcadmicLevelValidator.validate(params, requiredFields);
		try {
			UserVO authUser = getAuthenticatedUserVO();			
			response.setData(studentAcademicLevelService.createStudentAcademicLevelClasses(studentAcademicLevelField, authUser));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		
		return response;
	}
	
	@PostMapping
	@ResponseBody
	protected HttpResponseEntity<Object> createStudentAcademicLevel(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		List<String> requiredFields;
		this.logRequestDetail("POST : "+ EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		userSecurityClearance.checkStudentCreationPermission();
		
		requiredFields = Arrays.asList(
						StudentConstant.Class.ACADEMIC_YEAR_ID,
						StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID,
						StudentConstant.Class.IS_CURRENT_STUDENT_ACADEMIC_LEVEL,
						StudentConstant.Class.STUDENT_ID);
		
		StudentAcademicLevel.Fields studentAcademicLevelField = StudentAcadmicLevelValidator.validate(params, requiredFields);
		try {
			UserVO authUser = getAuthenticatedUserVO();			
			response.setData(studentAcademicLevelService.createStudentAcademicLevel(studentAcademicLevelField, authUser));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		
		return response;
	}
	
	@PutMapping
	@ResponseBody
	protected HttpResponseEntity<Object> editStudentAcademicLevels(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
	
		List<String> requiredFields;
		this.logRequestDetail("PUT : "+ EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);		
				
		requiredFields = Arrays.asList(
						StudentConstant.Class.ID,
						StudentConstant.Class.ACADEMIC_YEAR_ID,
						StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID,
						StudentConstant.Class.STUDENT_ID);		
		StudentAcademicLevel.Fields studentAcademicLevelField = StudentAcadmicLevelValidator.validate(params, requiredFields);		
		try {
			UserVO authUser = getAuthenticatedUserVO();			
			response.setData(studentAcademicLevelService.updateStudentAcademicLevel(studentAcademicLevelField, authUser));				
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		return response;
	}

	@PutMapping(value = "/classes")
	@ResponseBody
	protected HttpResponseEntity<Object> editStudentAcademicLevelClasses(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
	
		List<String> requiredFields;
		this.logRequestDetail("PUT : "+ EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);		
				
		requiredFields = Arrays.asList(
						StudentConstant.Class.ID,
						StudentConstant.Class.ACADEMIC_YEAR_ID,
						StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID,
						StudentConstant.Class.STUDENT_IDS,
						StudentConstant.Class.CLASS_ID
						);		
		StudentAcademicLevel.Fields studentAcademicLevelField = StudentAcadmicLevelValidator.validate(params, requiredFields);		
		try {
			UserVO authUser = getAuthenticatedUserVO();			
			response.setData(studentAcademicLevelService.updateStudentAcademicLevelClasses(studentAcademicLevelField, authUser));				
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		return response;
	}
	
	@PostMapping(value = "/delete-student-class")
	@ResponseBody
	protected HttpResponseEntity<Object> deleteStudentClass(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
	
		List<String> requiredFields;
		this.logRequestDetail("POST : " + EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE + "/delete-student-class");
		this.securityCheckAccessByRoles(auth);	
		userSecurityClearance.checkStudentClassDeletionPermission();
				
		requiredFields = Arrays.asList(
				StudentConstant.Class.ACADEMIC_YEAR_ID,
				StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID,
				StudentConstant.Class.STUDENT_ID,
				StudentConstant.Class.CLASS_ID
				);
		
		StudentAcademicLevel.Fields studentAcademicLevelField = StudentAcadmicLevelValidator.validate(params, requiredFields);		
		Integer classId = (Integer) params.get(StudentConstant.Class.CLASS_ID);		
		studentAcademicLevelField.setClassIds(Arrays.asList(classId));		
		try {				
			response.setData(studentAcademicLevelService.deleteStudentClass(studentAcademicLevelField));				
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		return response;
	}
	
	@GetMapping(value = {"students/{studentId}", "/students/{studentId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> loadStudentAcademicLevelsByStudentId(Authentication auth, @PathVariable("studentId") Integer studentId) {
		this.logRequestDetail("GET : " + EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		try {
			response.setData(assemblerStudentAcademicLevelService.findStudentAcademicLevelsByStudentId(studentId));
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return response;
	}
	
	@GetMapping(value = {"users/{userId}", "/users/{userId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> loadStudentClassByUserId(Authentication auth, @PathVariable("userId") Integer userId) {
		this.logRequestDetail("GET : " + EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		try {
			
			UserVO user = userService.getUserVOByUserId(userId);
			
			switch(user.getUserType()) {
			case STUDENT:
				response.setData(assemblerStudentAcademicLevelService.findStudentAcademicLevelsByStudentId(user.getId()));
				break;
			case TEACHER:
				response.setData(assemblerStudentAcademicLevelService.findTeacherClassesByTeacherId(user.getId()));
				break;			
			default:
				throw new RuntimeException("Invalid user type: " + user.getUserType());
			}			
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return response;
	}
	
	@GetMapping(value = {
			"students/{studentId}/academic-levels/{academicLevelId}", 
			"/students/{studentId}/academic-levels/{academicLevelId}/"
			})
	@ResponseBody
	protected HttpResponseEntity<Object> loadClassesByStudentIDAndAcademicLevelID(
			Authentication auth, 
			@PathVariable("studentId") Integer studentId, 
			@PathVariable("academicLevelId") Integer academicLevelId) {
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		try {
			response.setData(assemblerStudentAcademicLevelService.findStudentClassesByStudentIdAndAndAcademicLevelID(studentId, academicLevelId));
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return response;
	}
	
	@GetMapping(value = {"/{studentAcademicLevelId}", "/{studentAcademicLevelId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> loadStudentClassByStudentClassId(Authentication auth, @PathVariable("studentAcademicLevelId") Integer studentAcademicLevelId) {
		this.logRequestDetail("GET : " + EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		StudentAcademicLevelVO studentAcademicLevelVO;
		try {
			studentAcademicLevelVO = assemblerStudentAcademicLevelService.findObject(studentAcademicLevelId);
			response.setData(studentAcademicLevelVO);
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return response;
	}

	@DeleteMapping(value = {"/{studentAcademicLevelId}", "/{studentAcademicLevelId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteStudentClass(Authentication auth, @PathVariable("studentAcademicLevelId") Integer studentAcademicLevelId) {
		this.logRequestDetail("DELETE : " + EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		StudentAcademicLevel studentAcademicLevel;
		StudentAcademicLevelVO studentAcademicLevelVO;
		
		try {
			studentAcademicLevelVO = assemblerStudentAcademicLevelService.findObject(studentAcademicLevelId);	
			if (studentAcademicLevelVO == null) {
				response.setSuccess(false);
				response.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return response;
			} 
			studentAcademicLevel = studentAcademicLevelService.findObject(studentAcademicLevelId);	
			boolean isDeleted = studentAcademicLevelService.deleteObject(studentAcademicLevel);
			response.setData(isDeleted);
			response.setSuccess(isDeleted);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return response;
	}
	
	
	@DeleteMapping(value = "/{studentAcademicLevelId}/classes/{classId}")
	@ResponseBody
	protected HttpResponseEntity<Object> deleteStudentAcademicLevelClasses(Authentication auth, 
			@PathVariable("classId") Integer classId,
			@PathVariable("studentAcademicLevelId") Integer studentAcademicLevelId) {
		this.logRequestDetail("DELETE : " + EndPointConstants.REST_STUDENT_ACADEMIC_LEVEL_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
			
		try {
			StudentClass studentClass = null;
			if (assemblerStudentClassService.doesStudentClassRecordExist(classId, studentAcademicLevelId)) {
				studentClass  = studentClassService.findStudentClass(classId, studentAcademicLevelId);
			}
			boolean isDeleted = studentClassService.deleteObject(studentClass);
			response.setSuccess(isDeleted);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return response;
	}
}
