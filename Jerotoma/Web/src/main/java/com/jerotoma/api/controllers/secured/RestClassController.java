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
import com.jerotoma.common.constants.ClassConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.common.models.academic.Class;
import com.jerotoma.common.models.academic.Course;
import com.jerotoma.common.models.academic.Program;
import com.jerotoma.common.models.academic.Room;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.models.users.User;
import com.jerotoma.common.schedules.MeetingTime;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.validators.ClassValidator;
import com.jerotoma.common.viewobjects.AcademicLevelVO;
import com.jerotoma.common.viewobjects.AcademicYearVO;
import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.services.assemblers.academic.AssemblerAcademicLevelService;
import com.jerotoma.services.assemblers.academic.AssemblerClassService;
import com.jerotoma.services.configs.SystemConfigService;
import com.jerotoma.services.courses.AcademicLevelService;
import com.jerotoma.services.courses.AcademicYearService;
import com.jerotoma.services.courses.ClassService;
import com.jerotoma.services.courses.CourseService;
import com.jerotoma.services.courses.ProgramService;
import com.jerotoma.services.courses.RoomService;
import com.jerotoma.services.schedules.MeetingTimeService;
import com.jerotoma.services.schedules.ScheduleDataService;
import com.jerotoma.services.users.TeacherService;

@RestController
@RequestMapping(EndPointConstants.REST_CLASS_CONTROLLER.BASE)
public class RestClassController extends BaseController {
	
	@Autowired ClassService classService;
	@Autowired AcademicLevelService academicLevelService;
	@Autowired AssemblerAcademicLevelService assemblerAcademicLevelService;
	@Autowired ProgramService programService;
	@Autowired AssemblerClassService assemblerClassService;
	@Autowired SystemConfigService systemConfigService;
	@Autowired AcademicYearService academicYearService;
	@Autowired CourseService courseService;
	@Autowired MeetingTimeService meetingTimeService;
	@Autowired RoomService roomService;
	@Autowired TeacherService teacherService;
	@Autowired ScheduleDataService scheduleDataService;
	
	int generationNumber = 0;
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	public HttpResponseEntity<Object> getJClasses(
			Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			map = assemblerClassService.loadMapList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
								
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(map);
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}

	@PostMapping(value = {"/auto-generate", "/auto-generate/"})
	@ResponseBody
	public HttpResponseEntity<Object> autoGenerateClasses(
			Authentication auth,
			@RequestBody Map<String, Object> params) throws SQLException {
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE + "/auto-generate");
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		Integer academicYearId = (Integer)params.get("academicYearId");
		Integer academicLevelId = (Integer)params.get("academicLevelId");
		AcademicYearVO academicYear = academicYearId != null ? scheduleDataService.getAcademicYear(academicYearId) : scheduleDataService.getCurrentAcademicYear();
		AcademicLevelVO academicLevel = assemblerAcademicLevelService.findObject(academicLevelId);
		
		
		if (academicLevel != null) {
			response.setSuccess(true);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			response.setData(scheduleDataService.generateClasses(academicYear, academicLevel, authUser));
			response.setHttpStatus(HttpStatus.OK);
		}		
		return response;
	}
		
	@GetMapping(value = {"/{classId}", "/{classId}/"})
	@ResponseBody
	public HttpResponseEntity<Object> getJClass(Authentication auth, @PathVariable("classId") Integer classId) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE + "/" + classId);
		this.securityCheckAccessByRoles(auth);
		
		try {
			ClassVO jClassVO = assemblerClassService.findObject(classId);	
			response.setData(jClassVO);
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = {"academic-years/{academicYearId}", "/academic-years/{academicYearId}/"})
	@ResponseBody
	public HttpResponseEntity<Object> loadJClassesByAcademicYear(Authentication auth, @PathVariable("academicYearId") Integer academicYearId) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE + "/academic-years/" + academicYearId);
		this.securityCheckAccessByRoles(auth);
		
		try {
			response.setData(assemblerClassService.loadClassesByAcademicYear(academicYearId));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = {"academic-years/{academicYearId}/students/{studentId}/unregistered", "/academic-years/{academicYearId}/students/{studentId}/unregistered"})
	@ResponseBody
	public HttpResponseEntity<Object> loadUnregisteredJClassesByStudent(
			Authentication auth, 
			@PathVariable("academicYearId") Integer academicYearId,
			@PathVariable("academicLevelId") Integer academicLevelrId,
			@PathVariable("studentId") Integer studentId) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		try {
			response.setData(assemblerClassService.loadStudentUnregisteredClassesByAcademicYear(academicYearId, studentId, academicLevelrId));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = {
			"academic-years/{academicYearId}/students/{studentId}", 
			"/academic-years/{academicYearId}/students/{studentId}"
	})
	@ResponseBody
	public HttpResponseEntity<Object> loadStudentJClassesByAcademicYear(
			Authentication auth, 
			@PathVariable("academicYearId") Integer academicYearId,
			@PathVariable("studentId") Integer studentId) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE + "/academic-years/" + academicYearId + "/students/" + studentId);
		this.securityCheckAccessByRoles(auth);
		
		try {
			response.setData(assemblerClassService.loadStudentClassesByAcademicYear(studentId, academicYearId));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = {"/list", "/list/"})
	@ResponseBody
	public HttpResponseEntity<Object> loadJClassList(
			Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		List<ClassVO> mClasses = new ArrayList<>();
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_CLASS_CONTROLLER.BASE + "/list");
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			mClasses = assemblerClassService.loadList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		super.response.setSuccess(true);
		super.response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		super.response.setData(mClasses);
		super.response.setHttpStatus(HttpStatus.OK);
		return super.response;
	}

	@PostMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> createClasses(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		List<String> requiredFields;
		this.logRequestDetail("POST : "+ EndPointConstants.REST_CLASS_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						ClassConstant.CLASS_ACADEMIC_YEAR_ID,
						ClassConstant.CLASS_ROOM_ID,
						ClassConstant.CLASS_COURSE_ID,
						ClassConstant.CLASS_TEACHER_ID,
						ClassConstant.CLASS_CAPACITY,
						ClassConstant.CLASS_PROGRAM_ID,
						ClassConstant.CLASS_ACADEMIC_LEVEL_ID
						));
		Class mClass = new Class();
		Class.ClassFields classFields = ClassValidator.validate(params, requiredFields);
		
		
		try {
			MeetingTime meetingTime = null;
			Room room = null;
			Program program = null;
			AcademicLevel academicLevel = null;
			User authUser = authUserService.loadUserByUsername(userContext.getUsername());
			Teacher teacher = teacherService.findObject(classFields.getTeacherId());
			Course course = courseService.findObject(classFields.getCourseId());
			AcademicYear academicYear = academicYearService.findObject(classFields.getAcademicYearId());
			
			if (classFields.getRoomId() != null) {
				room = roomService.findObject(classFields.getRoomId());
			}
			
			if (classFields.getMeetingTimeId() != null) {
				meetingTime = meetingTimeService.findObject(classFields.getMeetingTimeId());
			}
			
			if (classFields.getProgramId()!= null) {
				program = programService.findObject(classFields.getProgramId());
			}
			
			if (classFields.getAcademicLevelId() != null) {
				academicLevel = academicLevelService.findObject(classFields.getAcademicLevelId());
			}
			
			if (program == null || program.getId() != course.getProgram().getId()) {
				throw new RuntimeException("Invalid program");
			}
			
			if (academicLevel == null || academicLevel.getId() != course.getAcademicLevel().getId()) {
				throw new RuntimeException("Invalide Academic Level");
			}
									
			mClass.setCapacity(classFields.getCapacity());
			mClass.setRoom(room);
			mClass.setTeacher(teacher);
			mClass.setCourse(course);
			mClass.setMeetingTime(meetingTime);
			mClass.setAcademicYear(academicYear);
			mClass.setUpdatedBy(authUser.getId());
			mClass.setUpdatedOn(CalendarUtil.getTodaysDate());
			mClass.setCreatedOn(CalendarUtil.getTodaysDate());
			
			mClass = classService.createObject(mClass);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(mClass);
		return response;
	}

	@PutMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> editJClasses(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
	
		List<String> requiredFields;
		this.logRequestDetail("PUT : "+ EndPointConstants.REST_CLASS_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						ClassConstant.CLASS_ID,
						ClassConstant.CLASS_ACADEMIC_YEAR_ID,
						ClassConstant.CLASS_ROOM_ID,
						ClassConstant.CLASS_COURSE_ID,
						ClassConstant.CLASS_TEACHER_ID,
						ClassConstant.CLASS_CAPACITY,
						ClassConstant.CLASS_PROGRAM_ID,
						ClassConstant.CLASS_ACADEMIC_LEVEL_ID
						));
		
		Class.ClassFields classFields = ClassValidator.validate(params, requiredFields);
		Class mClass;
		
		Program program = null;
		AcademicLevel academicLevel = null;
		
		try {
			mClass = classService.findObject(classFields.getId());
			Teacher teacher = teacherService.findObject(classFields.getTeacherId());
			Course course = courseService.findObject(classFields.getCourseId());
			AcademicYear academicYear = academicYearService.findObject(classFields.getAcademicYearId());
			Room room = null;
			MeetingTime meetingTime = null;
			
			if (classFields.getRoomId() != null) {
				room = roomService.findObject(classFields.getRoomId());
			}
			
			if (classFields.getMeetingTimeId() != null) {
				meetingTime = meetingTimeService.findObject(classFields.getMeetingTimeId());
			}
			
			if (classFields.getProgramId()!= null) {
				program = programService.findObject(classFields.getProgramId());
			}
			
			if (classFields.getAcademicLevelId() != null) {
				academicLevel = academicLevelService.findObject(classFields.getAcademicLevelId());
			}
			
			if (program == null || program.getId() != course.getProgram().getId()) {
				throw new RuntimeException("Invalid program");
			}
			
			if (academicLevel == null || academicLevel.getId() != course.getAcademicLevel().getId()) {
				throw new RuntimeException("Invalide Academic Level");
			}
			
			mClass.setCapacity(classFields.getCapacity());
			mClass.setRoom(room);
			mClass.setTeacher(teacher);
			mClass.setCourse(course);
			mClass.setMeetingTime(meetingTime);
			mClass.setAcademicYear(academicYear);
			mClass.setUpdatedBy(authUser.getId());
			mClass.setUpdatedOn(CalendarUtil.getTodaysDate());
			
			mClass = classService.updateObject(mClass);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(mClass);
		return response;
	}

	@DeleteMapping(value = {"/{classId}", "/{classId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteFieldOfStudy(Authentication auth, @PathVariable("classId") Integer classId) {
		this.logRequestDetail("DELETE : "+ EndPointConstants.REST_CLASS_CONTROLLER.BASE + "/" + classId);
		this.securityCheckAccessByRoles(auth);
		
		Class mClass;
		
		try {
			mClass = classService.findObject(classId);	
			if (mClass == null) {
				response.setSuccess(false);
				response.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return response;
			} 
			boolean isDeleted = classService.deleteObject(mClass);
			response.setSuccess(isDeleted);
			response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return response;
	}
	

}
