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
import com.jerotoma.common.constants.ClassConstant;
import com.jerotoma.common.constants.ScheduleConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.common.models.academic.Room;
import com.jerotoma.common.models.academic.Course;
import com.jerotoma.common.models.academic.Class;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.schedules.GeneticAlgorithm;
import com.jerotoma.common.schedules.MeetingTime;
import com.jerotoma.common.schedules.Population;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.validators.JClassValidator;
import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.services.assemblers.academic.AssemblerClassService;
import com.jerotoma.services.courses.AcademicYearService;
import com.jerotoma.services.courses.RoomService;
import com.jerotoma.services.courses.CourseService;
import com.jerotoma.services.courses.ClassService;
import com.jerotoma.services.schedules.MeetingTimeService;
import com.jerotoma.services.users.TeacherService;

@RestController
@RequestMapping(EndPointConstants.REST_CLASS_CONTROLLER.BASE)
public class RestClassController extends BaseController {
	
	@Autowired ClassService classService;
	@Autowired AssemblerClassService assemblerClassService;
	@Autowired AcademicYearService academicYearService;
	@Autowired CourseService courseService;
	@Autowired MeetingTimeService meetingTimeService;
	@Autowired RoomService roomService;
	@Autowired TeacherService teacherService;
	
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
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		try {
			map = assemblerClassService.loadMapList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		
		
		
		List<ClassVO> classList = (List<ClassVO>)map.get(ClassConstant.JCLASSES);
		
		System.out.println("> Generation # " + generationNumber);
		System.out.println(" Schedule # |                                        ");
		System.out.print(" Classes [ dept, class, room, meeting-time ]     ");
		System.out.println("                               | fitness | Conflicts");
		System.out.print("---------------------------------------------------------------------------");
		System.out.println("---------------------------------------------------------------------------");
		
		GeneticAlgorithm geneticAlgorithm =  new GeneticAlgorithm(classList);
		Population population = new Population(ScheduleConstant.POPULATION_SIZE, classList).sortByFitness();		
		while (population.getSchedules().get(0).getFitness() != 1.0) {
			population = geneticAlgorithm.evolve(population).sortByFitness();
			population.getSchedules().forEach(schedule -> System.out.println("     " + generationNumber++ 
					+ "      | " + schedule + "  | " + String.format("%.5f", schedule.getFitness()) + " | " + schedule.getNumberOfConflicts()));
			
		}
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(map);
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
	}
	
	@GetMapping(value = {"/{classId}", "/{classId}/"})
	@ResponseBody
	public HttpResponseEntity<Object> getJClass(Authentication auth, @PathVariable("classId") Integer classId) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		try {
			ClassVO jClassVO = assemblerClassService.findObject(classId);	
			instance.setData(jClassVO);
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
	}
	
	@GetMapping(value = {"academic-years/{academicYearId}", "/academic-years/{academicYearId}/"})
	@ResponseBody
	public HttpResponseEntity<Object> loadJClassesByAcademicYear(Authentication auth, @PathVariable("academicYearId") Integer academicYearId) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		try {
			instance.setData(assemblerClassService.loadClassesByAcademicYear(academicYearId));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
	}
	
	@GetMapping(value = {"academic-years/{academicYearId}/students/{studentId}/unregistered", "/academic-years/{academicYearId}/students/{studentId}/unregistered"})
	@ResponseBody
	public HttpResponseEntity<Object> loadUnregisteredJClassesByStudent(
			Authentication auth, 
			@PathVariable("academicYearId") Integer academicYearId,
			@PathVariable("studentId") Integer studentId) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		try {
			instance.setData(assemblerClassService.loadStudentUnregisteredClassesByAcademicYear(academicYearId, studentId));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
	}
	
	@GetMapping(value = {"academic-years/{academicYearId}/students/{studentId}", "/academic-years/{academicYearId}/students/{studentId}"})
	@ResponseBody
	public HttpResponseEntity<Object> loadStudentJClassesByAcademicYear(
			Authentication auth, 
			@PathVariable("academicYearId") Integer academicYearId,
			@PathVariable("studentId") Integer studentId) {
		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_ACADEMIC_DISCIPLINE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		try {
			instance.setData(assemblerClassService.loadStudentClassesByAcademicYear(studentId, academicYearId));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
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
				
		super.instance.setSuccess(true);
		super.instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		super.instance.setData(mClasses);
		super.instance.setHttpStatus(HttpStatus.OK);
		return super.instance;
	}

	@PostMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> createJClasses(
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
						ClassConstant.JCLASS_CAPACITY,
						ClassConstant.CLASS_MEETING_TIME_ID
						));
		Class mClass = new Class();
		Class.ClassFields jClassFields = JClassValidator.validate(params, requiredFields);
		
		
		try {
			
			AuthUser authUser = authUserService.loadUserByUsername(userContext.getUsername());
			Teacher teacher = teacherService.findObject(jClassFields.getTeacherId());
			Course course = courseService.findObject(jClassFields.getCourseId());
			AcademicYear academicYear = academicYearService.findObject(jClassFields.getAcademicYearId());
			Room room = roomService.findObject(jClassFields.getRoomId());
			MeetingTime meetingTime = meetingTimeService.findObject(jClassFields.getMeetingTimeId());
						
			mClass.setCapacity(jClassFields.getCapacity());
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
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(mClass);
		return instance;
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
						ClassConstant.JCLASS_ID,
						ClassConstant.CLASS_ACADEMIC_YEAR_ID,
						ClassConstant.CLASS_ROOM_ID,
						ClassConstant.CLASS_COURSE_ID,
						ClassConstant.CLASS_TEACHER_ID,
						ClassConstant.JCLASS_CAPACITY
						));
		
		Class.ClassFields mClassFields = JClassValidator.validate(params, requiredFields);
		Class mClass;
		
		try {
			mClass = classService.findObject(mClassFields.getId());
			Teacher teacher = teacherService.findObject(mClassFields.getTeacherId());
			Course course = courseService.findObject(mClassFields.getCourseId());
			AcademicYear academicYear = academicYearService.findObject(mClassFields.getAcademicYearId());
			Room room = roomService.findObject(mClassFields.getRoomId());
			MeetingTime meetingTime = meetingTimeService.findObject(mClassFields.getMeetingTimeId());
			
			mClass.setCapacity(mClassFields.getCapacity());
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
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(mClass);
		return instance;
	}

	@DeleteMapping(value = {"/{classId}", "/{classId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteFieldOfStudy(Authentication auth, @PathVariable("classId") Integer classId) {
		this.logRequestDetail("DELETE : "+ EndPointConstants.REST_CLASS_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		
		Class mClass;
		
		try {
			mClass = classService.findObject(classId);	
			if (mClass == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = classService.deleteObject(mClass);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}

}
