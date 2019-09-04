package com.jerotoma.api.controllers;

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

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AcademicDisciplineConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.JClassConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.common.models.academic.ClassRoom;
import com.jerotoma.common.models.academic.Course;
import com.jerotoma.common.models.academic.JClass;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.validators.JClassValidator;
import com.jerotoma.common.viewobjects.JClassVO;
import com.jerotoma.services.assemblers.academic.AssemblerJClassService;
import com.jerotoma.services.courses.AcademicYearService;
import com.jerotoma.services.courses.ClassRoomService;
import com.jerotoma.services.courses.CourseService;
import com.jerotoma.services.courses.JClassService;
import com.jerotoma.services.users.TeacherService;

@RestController
@RequestMapping(EndPointConstants.REST_JCLASS_CONTROLLER.BASE)
public class RestJClassController extends BaseController {
	
	@Autowired JClassService jClassService;
	@Autowired AssemblerJClassService assemblerJClassService;
	@Autowired AcademicYearService academicYearService;
	@Autowired CourseService courseService;
	@Autowired ClassRoomService classRoomService;
	@Autowired TeacherService teacherService;
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	public HttpResponseEntity<Object> getFieldOfStudies(
			Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE);
		this.securityCheckAdminAccess(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			map = assemblerJClassService.loadMapList(queryParam);		
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
	public HttpResponseEntity<Object> loadFieldOfStudyList(
			Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		List<JClassVO> jClasses = new ArrayList<>();
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_JCLASS_CONTROLLER.BASE + "/list");
		this.securityCheckAdminAccess(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			jClasses = assemblerJClassService.loadList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		super.instance.setSuccess(true);
		super.instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		super.instance.setData(jClasses);
		super.instance.setHttpStatus(HttpStatus.OK);
		return super.instance;
	}

	@PostMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> createPosition(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		List<String> requiredFields;
		this.logRequestDetail("POST : "+ EndPointConstants.REST_JCLASS_CONTROLLER.BASE);
		this.securityCheckAdminAccess(auth);
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						JClassConstant.JCLASS_ACADEMIC_YEAR_ID,
						JClassConstant.JCLASS_CLASS_ROOM_ID,
						JClassConstant.JCLASS_COURSE_ID,
						JClassConstant.JCLASS_TEACHER_ID,
						JClassConstant.JCLASS_CAPACITY
						));
		JClass jClass = new JClass();
		JClass.JClassFields jClassFields = JClassValidator.validate(params, requiredFields);
		
		
		try {
			
			AuthUser authUser = authUserService.loadUserByUsername(userContext.getUsername());
			Teacher teacher = teacherService.findObject(jClassFields.getTeacherId());
			Course course = courseService.findObject(jClassFields.getCourseId());
			AcademicYear academicYear = academicYearService.findObject(jClassFields.getAcademicYearId());
			ClassRoom classRoom = classRoomService.findObject(jClassFields.getClassRoomId());
			
			jClass.setCapacity(jClassFields.getCapacity());
			jClass.setClassRoom(classRoom);
			jClass.setTeacher(teacher);
			jClass.setCourse(course);
			jClass.setAcademicYear(academicYear);
			jClass.setUpdatedBy(authUser.getId());
			jClass.setUpdatedOn(CalendarUtil.getTodaysDate());
			jClass.setCreatedOn(CalendarUtil.getTodaysDate());
			
			jClass = jClassService.createObject(jClass);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(jClass);
		return instance;
	}

	@PutMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> editPosition(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
	
		List<String> requiredFields;
		this.logRequestDetail("PUT : "+ EndPointConstants.REST_JCLASS_CONTROLLER.BASE);
		this.securityCheckAdminAccess(auth);		
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_NAME,
						AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_DESCRIPTION,
						AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_CODE));
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						JClassConstant.JCLASS_ID,
						JClassConstant.JCLASS_ACADEMIC_YEAR_ID,
						JClassConstant.JCLASS_CLASS_ROOM_ID,
						JClassConstant.JCLASS_COURSE_ID,
						JClassConstant.JCLASS_TEACHER_ID,
						JClassConstant.JCLASS_CAPACITY
						));
		
		JClass.JClassFields jClassFields = JClassValidator.validate(params, requiredFields);
		JClass jClass;
		
		try {
			jClass = jClassService.findObject(jClassFields.getId());
			AuthUser authUser = authUserService.loadUserByUsername(userContext.getUsername());
			Teacher teacher = teacherService.findObject(jClassFields.getTeacherId());
			Course course = courseService.findObject(jClassFields.getCourseId());
			AcademicYear academicYear = academicYearService.findObject(jClassFields.getAcademicYearId());
			ClassRoom classRoom = classRoomService.findObject(jClassFields.getClassRoomId());
			
			jClass.setCapacity(jClassFields.getCapacity());
			jClass.setClassRoom(classRoom);
			jClass.setTeacher(teacher);
			jClass.setCourse(course);
			jClass.setAcademicYear(academicYear);
			jClass.setUpdatedBy(authUser.getId());
			jClass.setUpdatedOn(CalendarUtil.getTodaysDate());
			
			jClass = jClassService.createObject(jClass);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(jClass);
		return instance;
	}

	@DeleteMapping(value = {"/{classId}", "/{classId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteFieldOfStudy(Authentication auth, @PathVariable("classId") Integer classId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		this.logRequestDetail("DELETE : "+ EndPointConstants.REST_JCLASS_CONTROLLER.BASE);
		this.securityCheckAdminAccess(auth);
		
		JClass jClass;
		
		try {
			jClass = jClassService.findObject(classId);	
			if (jClass == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = jClassService.deleteObject(jClass);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}

}
