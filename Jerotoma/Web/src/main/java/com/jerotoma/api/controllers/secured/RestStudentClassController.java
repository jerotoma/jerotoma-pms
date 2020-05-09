package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.common.models.academic.Class;
import com.jerotoma.common.models.academic.StudentClass;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.common.models.users.Student;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.validators.StudentClassValidator;
import com.jerotoma.common.viewobjects.StudentClassVO;
import com.jerotoma.services.assemblers.academic.AssemblerStudentClassService;
import com.jerotoma.services.courses.AcademicYearService;
import com.jerotoma.services.courses.CourseService;
import com.jerotoma.services.courses.ClassService;
import com.jerotoma.services.courses.StudentClassService;
import com.jerotoma.services.users.StudentService;

@RestController
@RequestMapping(EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE)
public class RestStudentClassController extends BaseController {
	
	@Autowired StudentClassService studentClassService;
	@Autowired AssemblerStudentClassService assemblerStudentClassService;
	@Autowired AcademicYearService academicYearService;
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
			map = assemblerStudentClassService.loadMapList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(map);
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
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
		
		List<StudentClassVO> studentClasses = new ArrayList<>();
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE + "/list");
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			studentClasses = assemblerStudentClassService.loadList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		super.instance.setSuccess(true);
		super.instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		super.instance.setData(studentClasses);
		super.instance.setHttpStatus(HttpStatus.OK);
		return super.instance;
	}

	@PostMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> createStudentClass(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		List<String> requiredFields;
		this.logRequestDetail("POST : "+ EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						StudentConstant.Class.ACADEMIC_YEAR_ID,
						StudentConstant.Class.STUDENT_ID,
						StudentConstant.Class.JCLASS_IDS
						));
		Set<Class> jClasses = new HashSet<>();
		StudentClass.Fields jClassFields = StudentClassValidator.validate(params, requiredFields);
		StudentClass studentClass = new StudentClass();		
		try {
			
			AuthUser authUser = authUserService.loadUserByUsername(userContext.getUsername());
			Student student = studentService.findObject(jClassFields.getStudentId());
			AcademicYear academicYear = academicYearService.findObject(jClassFields.getAcademicYearId());	
			
			studentClass.setStudent(student);
			studentClass.setAcademicYear(academicYear);
			studentClass.setUpdatedBy(authUser.getId());
			studentClass.setCreatedOn(CalendarUtil.getTodaysDate());
			studentClass.setUpdatedOn(CalendarUtil.getTodaysDate());
			
			for (Integer classId : jClassFields.getClassIds()) {		
				Class jClass = jClassService.findObject(classId);						
				jClasses.add(jClass);
			}
			studentClass.setClasses(jClasses);			
			studentClass = studentClassService.createObject(studentClass);			
			instance.setData(studentClass);
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		
		return instance;
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
						StudentConstant.Class.STUDENT_ID,
						StudentConstant.Class.JCLASS_ID
						));
		
		StudentClass.Fields jClassFields = StudentClassValidator.validate(params, requiredFields);
		StudentClass studentClass;
		Set<Class> jClasses = new HashSet<>();		
		try {			
			studentClass = studentClassService.findObject(jClassFields.getId());	
			AuthUser authUser = authUserService.loadUserByUsername(userContext.getUsername());
			Student student = studentService.findObject(jClassFields.getStudentId());
			AcademicYear academicYear = academicYearService.findObject(jClassFields.getAcademicYearId());
			studentClass.setStudent(student);
			studentClass.setAcademicYear(academicYear);
			studentClass.setUpdatedBy(authUser.getId());
			studentClass.setUpdatedOn(CalendarUtil.getTodaysDate());		
			for (Integer classId : jClassFields.getClassIds()) {					
				Class jClass = jClassService.findObject(classId);		
				jClasses.add(jClass);
			}
			studentClass.setClasses(jClasses);			
			studentClass = studentClassService.updateObject(studentClass);
			instance.setData(studentClass);
				
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		return instance;
	}
	
	@GetMapping(value = {"students/{studentId}", "/students/{studentId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> loadStudentClassByStudentId(Authentication auth, @PathVariable("studentId") Integer studentId) {
		this.logRequestDetail("GET : " + EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		try {
			instance.setData(assemblerStudentClassService.findStudentClassesByStudentId(studentId));
			instance.setSuccess(true);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}
	
	@GetMapping(value = {"/{studentClassId}", "/{studentClassId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> loadStudentClassByStudentClassId(Authentication auth, @PathVariable("studentClassId") Integer studentClassId) {
		this.logRequestDetail("GET : " + EndPointConstants.REST_STUDENT_CLASS_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		StudentClassVO studentClassVO;
		try {
			studentClassVO = assemblerStudentClassService.findObject(studentClassId);
			instance.setData(studentClassVO);
			instance.setSuccess(true);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
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
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = studentClassService.deleteObject(studentClass);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}

}
