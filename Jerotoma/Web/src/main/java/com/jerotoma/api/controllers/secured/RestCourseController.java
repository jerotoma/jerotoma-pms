package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import com.jerotoma.common.constants.CourseConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.common.models.academic.Course;
import com.jerotoma.common.models.academicDisciplines.AcademicDiscipline;
import com.jerotoma.common.utils.validators.CourseValidator;
import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.services.academicdisciplines.AcademicDisciplineService;
import com.jerotoma.services.assemblers.academic.AssemblerCourseService;
import com.jerotoma.services.courses.AcademicYearService;
import com.jerotoma.services.courses.CourseService;


@RestController
@RequestMapping(EndPointConstants.REST_COURSE_CONTROLLER.BASE)
public class RestCourseController extends BaseController {
	
	@Autowired CourseService courseService;
	@Autowired AssemblerCourseService assemblerCourseService;
	@Autowired AcademicDisciplineService academicDisciplineService;
	@Autowired AcademicYearService academicYearService;
		
	@GetMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getCourses(Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		Map<String, Object> map = new HashMap<>();
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_COURSE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			map = assemblerCourseService.loadMapList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(map);
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
	}
	
	
	@GetMapping(value = {"/{courseId}", "/{courseId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getCourse(Authentication auth, @PathVariable("courseId") Integer courseId) {
	
		this.logRequestDetail("GET : " + EndPointConstants.REST_COURSE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		try {
			instance.setData(assemblerCourseService.findObject(courseId));		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
	}
	
	@GetMapping(value = {"/academicYears/{academicYearId}"})
	@ResponseBody
	protected HttpResponseEntity<Object> getCoursesByAcademicYear(Authentication auth,
			@PathVariable(value="academicYearId", required=true)Integer academicYearId) {
		
		List<CourseVO> courses = new ArrayList<>();
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_COURSE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		try {
			courses = assemblerCourseService.findCoursesByAcademicYearId(academicYearId);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(courses);
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
	}

	@PostMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> createCourse(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		this.logRequestDetail("POST : " + EndPointConstants.REST_COURSE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		List<String> requiredFields = new ArrayList<>(
				Arrays.asList(
						CourseConstant.COURSE_NAME,
						CourseConstant.COURSE_DESCRIPTION,
						CourseConstant.COURSE_CODE,
						CourseConstant.ACADEMIC_DISCIPLINE_IDS,
						CourseConstant.ACADEMIC_YEAR_ID));
		
		Course course = CourseValidator.validate(params, requiredFields);
		Set<AcademicDiscipline> academicDisciplines = new HashSet<>();
		try {
			 for (Integer academicDisciplineId: course.getAcademicDisciplineIds()) {
				 AcademicDiscipline academicDiscipline = academicDisciplineService.findObject(academicDisciplineId);
				 academicDisciplines.add(academicDiscipline);
			 }
			 course.setAcademicDisciplines(academicDisciplines);
			
			AcademicYear academicYear = academicYearService.findObject(course.getAcademicYearId());
			course.setAcademicYear(academicYear);
			course.setUpdatedBy(authUser.getId());
			course = courseService.createObject(course);
			course.setAcademicYearId(academicYear.getId());
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
	protected HttpResponseEntity<Object> editCourse(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		this.logRequestDetail("PUT : " + EndPointConstants.REST_COURSE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
	
		List<String> requiredFields = new ArrayList<>(
				Arrays.asList(
						CourseConstant.COURSE_NAME,
						CourseConstant.COURSE_DESCRIPTION,
						CourseConstant.COURSE_ID,
						CourseConstant.ACADEMIC_YEAR_ID,
						CourseConstant.ACADEMIC_DISCIPLINE_IDS,
						CourseConstant.COURSE_CODE));
		
		Course course = CourseValidator.validate(params, requiredFields);
		Set<AcademicDiscipline> academicDisciplines = new HashSet<>();
		try {
			 for (Integer academicDisciplineId: course.getAcademicDisciplineIds()) {
				 AcademicDiscipline academicDiscipline = academicDisciplineService.findObject(academicDisciplineId);
				 academicDisciplines.add(academicDiscipline);
			 }
			 course.setAcademicDisciplines(academicDisciplines);
			
			AcademicYear academicYear = academicYearService.findObject(course.getAcademicYearId());
			course.setAcademicYear(academicYear);
			course.setUpdatedBy(authUser.getId());
			course.setUpdatedOn(today);
			course = courseService.updateObject(course);	
			course.setAcademicYearId(academicYear.getId());
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
			
		this.logRequestDetail("DELETE : " + EndPointConstants.REST_COURSE_CONTROLLER.BASE + "/" + courseId);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
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
