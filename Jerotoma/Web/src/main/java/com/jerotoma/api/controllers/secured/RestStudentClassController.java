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
import com.jerotoma.common.constants.CompletionStatus;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.StudentConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.common.models.academic.Class;
import com.jerotoma.common.models.academic.StudentAcademicLevel;
import com.jerotoma.common.models.academic.StudentClass;
import com.jerotoma.common.models.students.Student;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.validators.StudentAcadmicLevelValidator;
import com.jerotoma.common.utils.validators.StudentClassValidator;
import com.jerotoma.common.viewobjects.StudentAcademicLevelVO;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.services.assemblers.academic.AssemblerStudentAcademicLevelService;
import com.jerotoma.services.courses.AcademicLevelService;
import com.jerotoma.services.courses.AcademicYearService;
import com.jerotoma.services.courses.ClassService;
import com.jerotoma.services.courses.CourseService;
import com.jerotoma.services.courses.StudentClassService;
import com.jerotoma.services.students.StudentAcademicLevelService;
import com.jerotoma.services.users.StudentService;

@RestController
@RequestMapping(EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE)
public class RestStudentClassController extends BaseController {
	
	@Autowired StudentClassService studentClassService;
	@Autowired AssemblerStudentAcademicLevelService assemblerStudentAcademicLevelService;
	@Autowired AcademicYearService academicYearService;
	@Autowired StudentAcademicLevelService studentAcademicLevelService;
	@Autowired AcademicLevelService academicLevelService;
	@Autowired CourseService courseService;
	@Autowired ClassService jClassService;
	@Autowired StudentService studentService;
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	public HttpResponseEntity<Object> getStudentClasses(
			Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE);
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
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE + "/list");
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

	@PostMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> createStudentClass(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		List<String> requiredFields;
		this.logRequestDetail("POST : "+ EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		userSecurityClearance.checkStudentCreationPermission();
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						StudentConstant.Class.ACADEMIC_YEAR_ID,
						StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID,
						StudentConstant.Class.STUDENT_IDS,
						StudentConstant.Class.JCLASS_IDS
						));
		
		StudentClass.Fields jClassFields = StudentClassValidator.validate(params, requiredFields);		
		List<StudentClass> studentClasses = new ArrayList<>();		
		try {
			UserVO authUser = getAuthenticatedUserVO();
			AcademicLevel academicLevel = academicLevelService.findObject(jClassFields.getAcademicLevelId());
			AcademicYear academicYear = academicYearService.findObject(jClassFields.getAcademicYearId());
			
			for (Integer classId : jClassFields.getClassIds()) {		
				Class jClass = jClassService.findObject(classId);				
				for (Integer studentId: jClassFields.getStudentIds()) {
					StudentAcademicLevel studentAcademicLevel = studentAcademicLevelService.findStudentAcademicLevel(studentId, academicLevel.getId());
					
					StudentClass studentClass = new StudentClass();	
					studentClass.setmClass(jClass);	
					studentClass.setAcademicYear(academicYear);
					studentClass.setStudentAcademicLevel(studentAcademicLevel);
					studentClass.setUpdatedBy(authUser.getUserId());
					studentClass.setCreatedOn(CalendarUtil.getTodaysDate());
					studentClass.setUpdatedOn(CalendarUtil.getTodaysDate());
					studentClasses.add(studentClass);							
				}
			}						
			response.setData(studentClassService.createBatchObject(studentClasses));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		
		return response;
	}

	@PutMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> editStudentClass(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
	
		List<String> requiredFields;
		this.logRequestDetail("PUT : "+ EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);		
				
		requiredFields = new ArrayList<>(
				Arrays.asList(
						StudentConstant.Class.ID,
						StudentConstant.Class.ACADEMIC_YEAR_ID,
						StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID,
						StudentConstant.Class.STUDENT_IDS,
						StudentConstant.Class.JCLASS_ID
						));
		
		StudentAcademicLevel.Fields studentAcademicLevelFields = StudentAcadmicLevelValidator.validate(params, requiredFields);
		requiredFields.remove(StudentConstant.Class.STUDENT_ACADEMIC_LEVEL_ID);
		StudentClass.Fields studentClassFields = StudentClassValidator.validate(params, requiredFields);
		StudentClass studentClass;
		List<StudentClass> studentClasses = new ArrayList<>();		
		try {
			UserVO authUser = getAuthenticatedUserVO();
			Student student = studentService.findObject(studentAcademicLevelFields.getStudentId());
			AcademicLevel academicLevel  = academicLevelService.findObject(studentAcademicLevelFields.getAcademicLevelId());
			CompletionStatus completionStatus = CompletionStatus.getCompletionStatusfromID(studentAcademicLevelFields.getAcademicLevelId());
			
			StudentAcademicLevel studentAcademicLevel = studentAcademicLevelService.findObject(studentAcademicLevelFields.getId());
			studentAcademicLevel.setAcademicLevel(academicLevel);
			studentAcademicLevel.setCompletionStatus(completionStatus);
			studentAcademicLevel.setStudent(student);
			
			studentAcademicLevel = studentAcademicLevelService.updateObject(studentAcademicLevel);			
			AcademicYear academicYear = academicYearService.findObject(studentClassFields.getAcademicYearId());
			
			for (Integer classId : studentClassFields.getClassIds()) {		
				Class jClass = jClassService.findObject(classId);				
				studentClass  = studentClassService.findStudentClass(classId, studentAcademicLevel.getId(), academicYear.getId());
				if (studentClass == null) {
					studentClass = new StudentClass();
				}				
				studentClass.setmClass(jClass);	
				studentClass.setAcademicYear(academicYear);
				studentClass.setStudentAcademicLevel(studentAcademicLevel);
				studentClass.setUpdatedBy(authUser.getUserId());
				studentClass.setCreatedOn(CalendarUtil.getTodaysDate());
				studentClass.setUpdatedOn(CalendarUtil.getTodaysDate());
				studentClasses.add(studentClass);				
			}
			response.setData(studentClassService.updateBatchObject(studentClasses));				
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		return response;
	}
	
	@GetMapping(value = {"students/{studentId}", "/students/{studentId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> loadStudentClassByStudentId(Authentication auth, @PathVariable("studentId") Integer studentId) {
		this.logRequestDetail("GET : " + EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		try {
			response.setData(assemblerStudentAcademicLevelService.findStudentClassesByStudentId(studentId));
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
		this.logRequestDetail("GET : " + EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		try {
			
			UserVO user = userService.getUserByUserId(userId);
			
			switch(user.getUserType()) {
			case STUDENT:
				response.setData(assemblerStudentAcademicLevelService.findStudentClassesByStudentId(user.getId()));
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
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE);
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
	
	@GetMapping(value = {"/{studentClassId}", "/{studentClassId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> loadStudentClassByStudentClassId(Authentication auth, @PathVariable("studentClassId") Integer studentClassId) {
		this.logRequestDetail("GET : " + EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		StudentAcademicLevelVO studentClassVO;
		try {
			studentClassVO = assemblerStudentAcademicLevelService.findObject(studentClassId);
			response.setData(studentClassVO);
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return response;
	}

	@DeleteMapping(value = {"/{studentClassId}", "/{studentClassId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteStudentClass(Authentication auth, @PathVariable("studentClassId") Integer studentClassId) {
		this.logRequestDetail("DELETE : " + EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		StudentClass studentClass;
		
		try {
			studentClass = studentClassService.findObject(studentClassId);	
			if (studentClass == null) {
				response.setSuccess(false);
				response.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return response;
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
