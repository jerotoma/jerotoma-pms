package com.jerotoma.api.controllers.secured;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import org.springframework.web.multipart.MultipartFile;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AcademicDisciplineConstant;
import com.jerotoma.common.constants.DepartmentConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.ParentConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.constants.TeacherConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.constants.UserConstant.USER_TYPE;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.Department;
import com.jerotoma.common.models.academicDisciplines.AcademicDiscipline;
import com.jerotoma.common.models.positions.Position;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.common.models.users.Person;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.models.users.students.Student;
import com.jerotoma.common.utils.ExcelUtility;
import com.jerotoma.common.utils.validators.UserValidator;
import com.jerotoma.common.viewobjects.ParentVO;
import com.jerotoma.common.viewobjects.ResultBuilder;
import com.jerotoma.common.viewobjects.StaffVO;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.services.AddressService;
import com.jerotoma.services.academic.AcademicLevelService;
import com.jerotoma.services.academic.DepartmentService;
import com.jerotoma.services.academic.ProgramService;
import com.jerotoma.services.academicdisciplines.AcademicDisciplineService;
import com.jerotoma.services.assemblers.AssemblerParentService;
import com.jerotoma.services.assemblers.AssemblerSequenceGeneratorService;
import com.jerotoma.services.assemblers.AssemblerStaffService;
import com.jerotoma.services.assemblers.AssemblerStudentService;
import com.jerotoma.services.assemblers.AssemblerTeacherService;
import com.jerotoma.services.assemblers.academic.AssemblerProgramService;
import com.jerotoma.services.positions.PositionService;
import com.jerotoma.services.students.StudentAcademicLevelService;
import com.jerotoma.services.users.ParentAddressService;
import com.jerotoma.services.users.ParentService;
import com.jerotoma.services.users.StaffAddressService;
import com.jerotoma.services.users.StaffService;
import com.jerotoma.services.users.StudentAddressService;
import com.jerotoma.services.users.StudentService;
import com.jerotoma.services.users.TeacherAddressService;
import com.jerotoma.services.users.TeacherService;

@RestController
@RequestMapping(EndPointConstants.REST_USER_CONTROLLER.BASE)
public class RestUserController extends BaseController {
	
	@Autowired TeacherService teacherService;
	@Autowired AssemblerTeacherService assemblerTeacherService;
	@Autowired AssemblerStudentService assemblerStudentService;
	@Autowired AssemblerStaffService assemblerStaffService;
	@Autowired AssemblerParentService assemblerParentService;
	@Autowired AssemblerSequenceGeneratorService sequenceGeneratorService;
	@Autowired StudentService studentService;
	@Autowired StaffService staffService;
	@Autowired ParentService parentService;
	@Autowired TeacherAddressService teacherAddressService;
	@Autowired AddressService addressService;
	@Autowired StudentAddressService studentAddressService;
	@Autowired StaffAddressService staffAddressService;
	@Autowired ParentAddressService parentAddressService;
	@Autowired PositionService positionService;
	@Autowired AcademicDisciplineService academicDisciplineService;
	@Autowired DepartmentService departmentService;
	@Autowired ProgramService programService;
	@Autowired AcademicLevelService academicLevelService;
	@Autowired StudentAcademicLevelService studentAcademicLevelService;
	@Autowired AssemblerProgramService assemblerProgramService;
		
	@GetMapping(value= {"", EndPointConstants.REST_USER_CONTROLLER.INDEX})
	@ResponseBody
	public HttpResponseEntity<Object> getUsers(Authentication auth,
			@RequestParam(value="userType", required=false) String userType,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) throws UsernameNotFoundException, JDataAccessException{
		
		Map<String, Object> mapVOs = new HashMap<>();
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(page, pageSize, fieldName, orderby);
		UserVO user = getAuthenticatedUserVO();
		
		UserConstant.USER_TYPE type = UserConstant.processUserType(userType);
		try {
			switch(type) {
			case TEACHER:
				if (user.getUserType().equals(USER_TYPE.STUDENT)) {
					ResultBuilder<TeacherVO> resultBuilder = assemblerTeacherService.loadTeacherMapListByStudentID(queryParam, user.getId());	
					mapVOs.put(TeacherConstant.TEACHERS, resultBuilder.getDataList());
					mapVOs.put(TeacherConstant.TEACHER_COUNT, resultBuilder.getCount());
					mapVOs.put(SystemConstant.PAGE_COUNT, resultBuilder.getPageCount());
				
				} else {
					mapVOs = assemblerTeacherService.loadMapList(queryParam);	
				}
				break;
			case STUDENT:
				mapVOs = assemblerStudentService.loadMapList(queryParam);
				break;
			case STAFF:
				mapVOs = assemblerStaffService.loadMapList(queryParam);
				break;
			case PARENT:
				if (user.getUserType().equals(USER_TYPE.STUDENT)) {
					ResultBuilder<ParentVO> resultBuilder =  assemblerParentService.loadParentMapListByStudentID(queryParam, user.getId());	
					mapVOs.put(ParentConstant.PARENTS, resultBuilder.getDataList());
					mapVOs.put(ParentConstant.PARENT_COUNT, resultBuilder.getCount());
					mapVOs.put(SystemConstant.PAGE_COUNT, resultBuilder.getPageCount());
				
				} else {
					mapVOs = assemblerParentService.loadMapList(queryParam);
				}				
				break;
			default:
				throw new JDataAccessException("User type not found");
			}
		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(mapVOs);
		response.setHttpStatus(HttpStatus.OK);
		return response;
		
	}
	
	@GetMapping(value="/{userId}")
	@ResponseBody
	public HttpResponseEntity<Object> getUser(Authentication auth,
			@PathVariable(value="userId", required = true) Integer userId			
			) throws UsernameNotFoundException, JDataAccessException{
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE + "/" + userId);
		this.securityCheckAccessByRoles(auth);		
		response.setData(userService.getUserVOByUserId(userId));
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);		
		return response;		
	}
	
	@PostMapping(value= {"/profile","/profile/", })
	@ResponseBody
	public HttpResponseEntity<Object> loadUserByUsername(Authentication auth, @RequestBody Map<String, String> map) throws UsernameNotFoundException, JDataAccessException{
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE + "/profile");
		this.securityCheckAccessByRoles(auth);
		
		String username = map.get("username");
		
		if (!userContext.getUsername().equals(username.trim())) {
			throw new UsernameNotFoundException("User type not found");		
		}
		UserVO user = userService.getUserByUsername(username);		
		response.setData(user);	
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;
		
	}
	
	@PostMapping("/excel/import")
	@ResponseBody
	public HttpResponseEntity<Object> importUserFromExcel(
			@RequestParam Map<String, String> params, 
			@RequestParam("upload_files[]") MultipartFile[] files) {
		List<StudentVO> students = null;
		try {
			students = ExcelUtility.readStudent(files[0].getInputStream(), 0);
			for (StudentVO student : students) {
				System.out.println(student.getFirstName());
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}
		response.setData(students);	
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = {"/programs/{programId}/academic-levels/{academicLevelId}"})
	@ResponseBody
	protected HttpResponseEntity<Object> getStudentsByAcademicYear(Authentication auth,
			@PathVariable(value="programId", required=true)Integer programId,
			@PathVariable(value="academicLevelId", required=true)Integer academicLevelId) {		
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_COURSE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		try {
			response.setData(assemblerStudentService.loadStudentsByProgramAndAcademicLevelIDs(programId, academicLevelId));	
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = {"/students/{studentId}/parents"})
	@ResponseBody
	protected HttpResponseEntity<Object> getParentsByStudentID(Authentication auth,
			@PathVariable(value="studentId", required=true)Integer studentId) {		
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_COURSE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		try {
			response.setData(assemblerStudentService.loadParentsByStudentId(studentId));	
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = {"/parents/{parentId}/students"})
	@ResponseBody
	protected HttpResponseEntity<Object> gettudentsByParentID(Authentication auth,
			@PathVariable(value="parentId", required=true)Integer parentId) {		
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_COURSE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		try {
			response.setData(assemblerStudentService.loadStudentsByParentId(parentId));	
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@PostMapping
	@ResponseBody
	public HttpResponseEntity<Object> createUser(Authentication auth, @RequestBody Map<String, Object> params) throws JDataAccessException{
		
		List<String> requiredFields;
		Staff staff;
		Parent parent;	
		Teacher teacher;
		Student student;		
		Position position;		
		Department department;		
		
		requiredFields = new ArrayList<>(Arrays.asList(
						UserConstant.FIRST_NAME, 
						UserConstant.LAST_NAME,
						UserConstant.USERNAME,
						UserConstant.ADDRESS,
						UserConstant.BIRTH_DATE,
						UserConstant.GENDER,
						UserConstant.PHONE_NUMBER
						));
				
		this.logRequestDetail("POST : " + EndPointConstants.REST_USER_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		userSecurityClearance.checkGeneralEntityCreationPermission();
					
		if(!params.containsKey(UserConstant.USER_TYPE_LABEL)) {
			throw new FieldRequiredException("User type can not be empty");
		}
		String userType = (String) params.get(UserConstant.USER_TYPE_LABEL);
		
		if (userType == null) {
			throw new FieldRequiredException("User type can not be empty");
		}
		UserConstant.USER_TYPE type = UserConstant.processUserType(userType);
		Person person;		
		try {
			switch(type) {
			case TEACHER:
				requiredFields.add(UserConstant.POSITION);
				
				position = processPosition(params, requiredFields);			
				department = processDepartment(params, requiredFields);
				teacher  = UserValidator.validateTeacherInputInfo(params, requiredFields);
				teacher.setPosition(position);
				teacher.setDepartment(department);
				teacher.setUpdatedBy(authUser.getId());											
				person = userService.createUser(teacher);				
				response.setData(userService.getUserVOByUserId(person.getUserId()));	
				break;
			case STUDENT:				
				requiredFields.add(UserConstant.ACADEMIC_LEVEL_ID);
				requiredFields.add(UserConstant.PROGRAM_ID);				
				student = UserValidator.validateStudentInputInfo(params, requiredFields);
				person = userService.createUser(student);				
				response.setData(userService.getUserVOByUserId(person.getUserId()));				
				break;
			case STAFF:
				requiredFields.remove(UserConstant.BIRTH_DATE);
				requiredFields.add(UserConstant.POSITION);				
				position = processPosition(params, requiredFields);	
				staff = UserValidator.validateOtherStaffInputInfo(params, requiredFields);
				staff.setPosition(position);
				staff.setUpdatedBy(authUser.getId());							
				person = userService.createUser(staff);				
				response.setData(userService.getUserVOByUserId(person.getUserId()));	
				break;
			case PARENT:
				requiredFields.remove(UserConstant.BIRTH_DATE);
				parent = UserValidator.validateParentInputInfo(params, requiredFields);				
				person = userService.createUser(parent);				
				response.setData(userService.getUserVOByUserId(person.getUserId()));				
				break;				
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		
		} catch (SQLException | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}			
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setMessage("User has been created");
		response.setSuccess(true);
		return response;
		
	}

	protected AcademicDiscipline processAcademicDiscipline(Map<String, Object> params, List<String> requiredFields) throws SQLException {
		Integer academicDisciplineId = null;
		AcademicDiscipline academicDiscipline = null;
		if(params.containsKey(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE)) {
			academicDisciplineId = (Integer) params.get(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE);
		}
		
		if (academicDisciplineId == null && requiredFields.contains(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE)) {
			throw new FieldRequiredException("Academic Discipline can not be empty");
		}
		
		if (academicDisciplineId !=  null) {
			academicDiscipline = academicDisciplineService.findObject(academicDisciplineId);
		}
		
		if (academicDiscipline == null && requiredFields.contains(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE)) {
			throw new FieldRequiredException("Academic Discipline is required, and invalid academic discipline was provided");
		}
		return academicDiscipline;
	}
	
	private Department processDepartment(Map<String, Object> params, List<String> requiredFields) throws SQLException {
		Integer departmentId = null;
		if(params.containsKey(DepartmentConstant.DEPARTMENT)) {
			departmentId = (Integer) params.get(DepartmentConstant.DEPARTMENT);
		}
		
		if (departmentId == null && requiredFields.contains(DepartmentConstant.DEPARTMENT)) {
			throw new FieldRequiredException("Department can not be empty");
		}
		
		Department department = departmentService.findObject(departmentId);
		
		if (department== null && requiredFields.contains(DepartmentConstant.DEPARTMENT)) {
			throw new FieldRequiredException("Department is required, and invalid department was provided");
		}
		return department;
	}

	private Position processPosition(Map<String, Object> params, List<String> requiredFields)
			throws SQLException {
		Integer positionId = null;
		if(params.containsKey(UserConstant.POSITION)) {
			positionId = (Integer) params.get(UserConstant.POSITION);
		}
		
		if (positionId == null && requiredFields.contains(UserConstant.POSITION)) {
			throw new FieldRequiredException("Position can not be empty");
		}
		
		Position position = positionService.findObject(positionId);
		
		if (position == null ) {
			throw new FieldRequiredException("Position is required, and invalid position was provided");
		}
		return position;
	}
	
	@PutMapping(value = {"", EndPointConstants.REST_USER_CONTROLLER.INDEX})
	@ResponseBody
	public HttpResponseEntity<Object> updateUser(Authentication auth, @RequestBody Map<String, Object> params) throws JDataAccessException{
		List<String> requiredFields = new ArrayList<>(
				Arrays.asList(
						UserConstant.ID,
						UserConstant.USER_ID,
						UserConstant.FIRST_NAME,						
						UserConstant.LAST_NAME,
						UserConstant.GENDER));
		Staff staff;
		Parent parent;
		Teacher teacher;
		Student student;
		Department department;
		Position position;
				
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		userSecurityClearance.checkGeneralEntityModificationPermission();
			
		if(!params.containsKey(UserConstant.USER_TYPE_LABEL)) {
			throw new FieldRequiredException("User type can not be empty");
		}
		String userType = (String) params.get(UserConstant.USER_TYPE_LABEL);
		
		if (userType == null) {
			throw new FieldRequiredException("User type can not be empty");
		}
		
		UserConstant.USER_TYPE type = UserConstant.processUserType(userType);
		try {
			switch(type) {
			case TEACHER:				
				requiredFields.add(UserConstant.POSITION);
								
				position = processPosition(params, requiredFields);			
				department = processDepartment(params, requiredFields);
				
				requiredFields.add(UserConstant.USER_CODE);
				requiredFields.add(UserConstant.BIRTH_DATE);
				
				teacher  = UserValidator.validateTeacherInputInfo(params, requiredFields);
				
				if (teacher.getId() == null) {
					throw new FieldRequiredException("Teacher ID is required");
				}
				teacher = userService.updateTeacher(teacher, department, position);
				response.setData(userService.getUserVOByUserId(teacher.getUserId()));				
				break;
			case STUDENT:
				requiredFields.add(UserConstant.PHONE_NUMBER);				
				student = UserValidator.validateStudentInputInfo(params, requiredFields);
				student = userService.updateStudent(student);				
				response.setData(userService.getUserVOByUserId(student.getUserId()));								
				break;
			case STAFF:
				requiredFields.add(UserConstant.POSITION);				
				position = processPosition(params, requiredFields);	
				staff = UserValidator.validateOtherStaffInputInfo(params, requiredFields);
				
				if (staff.getId() == null) {
					throw new FieldRequiredException("Staff ID is required");
				}
				staff = userService.updateStaff(staff, position);				
				response.setData(userService.getUserVOByUserId(staff.getUserId()));				
				break;
			case PARENT:
				
				parent = UserValidator.validateParentInputInfo(params, requiredFields);								
				Parent mParent = userService.updateParent(parent);
				response.setData(userService.getUserVOByUserId(mParent.getUserId()));
				break;
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		
		} catch (SQLException | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setMessage("User has been updated");
		return response;		
	}
	
	@DeleteMapping(value = {EndPointConstants.REST_USER_CONTROLLER.INDEX + "{userId}", EndPointConstants.REST_USER_CONTROLLER.INDEX+ "{userId}/"})
	@ResponseBody
	public HttpResponseEntity<Object> deleteUser(Authentication auth, @PathVariable(name="userId", required = true) Integer userId, @RequestParam(value="userType", required=true) String userType) throws JDataAccessException{
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE + "/" + userId);
		this.securityCheckAccessByRoles(auth);
			
		if (userType == null) {
			throw new FieldRequiredException("User type can not be empty");
		}
		
		if (userId == null) {					
			throw new FieldRequiredException("Teacher's ID is required, and invalid ID was provided");					
		}
		userSecurityClearance.checkGeneralEntityDeletionPermission();
		
		UserConstant.USER_TYPE type = UserConstant.processUserType(userType);
		try {
			switch(type) {
			case TEACHER:
				Teacher teacher = teacherService.findObject(userId);							
				response.setSuccess(teacherService.deleteObject(teacher));
				break;
			case STUDENT:
				
				Student student = studentService.findObject(userId);							
				response.setSuccess(studentService.deleteObject(student));				
				break;
			case STAFF:				
				Staff staff = staffService.findObject(userId);							
				response.setSuccess(staffService.deleteObject(staff));	
				break;
			case PARENT:				
				Parent parent =  parentService.findObject(userId);							
				response.setSuccess(parentService.deleteObject(parent));	
				break;
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		
		} catch (SQLException | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
			
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setMessage("User has been created");
		return response;
		
	}
	
	@GetMapping(EndPointConstants.REST_USER_CONTROLLER.CURRENT_USER)
	@ResponseBody
	public HttpResponseEntity<Object> getLoggedUser(Authentication auth) throws UsernameNotFoundException {				
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE.concat(EndPointConstants.REST_USER_CONTROLLER.CURRENT_USER));
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));		
		response.setData(getAuthenticatedUserVO());		
		return response;		
	}
	
	@GetMapping(EndPointConstants.REST_USER_CONTROLLER.CURRENT_USER_ROLES)
	@ResponseBody
	public HttpResponseEntity<Object> getLoggedUserRoles(Authentication auth) throws UsernameNotFoundException {				
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE.concat(EndPointConstants.REST_USER_CONTROLLER.CURRENT_USER_ROLES));
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));		
		response.setData(roleService.loadListFromRoleNames(getAuthenticatedUserVO().getRoles()));		
		return response;		
	}
	@GetMapping(EndPointConstants.REST_USER_CONTROLLER.SEARCH)
	@ResponseBody
	public HttpResponseEntity<Object> searchUser(
			Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="userType", required=false) String userType,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) throws UsernameNotFoundException{
		
		List<UserVO> users = new ArrayList<>();
		List<TeacherVO> teachers = new ArrayList<>();
		List<StudentVO> students = new ArrayList<>();
		List<StaffVO> staffs = new ArrayList<>();
		List<ParentVO> parents = new ArrayList<>();
		
	
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		UserConstant.USER_TYPE type = UserConstant.processUserType(userType);
		try {
			switch(type) {
			case TEACHER:
				teachers = assemblerTeacherService.search(queryParam);	
				if(users != null) {
					for(TeacherVO teacher: teachers) {
						users.add(userService.getUserVOByUserId(teacher.getUserId()));
					}
				}				
				break;
			case STUDENT:
				students = assemblerStudentService.search(queryParam);
				if(users != null) {
					for(StudentVO student: students) {
						users.add(userService.getUserVOByUserId(student.getUserId()));
					}
				}
				break;
			case STAFF:
				staffs = assemblerStaffService.search(queryParam);
				if(users != null) {
					for(StaffVO staff: staffs) {
						users.add(userService.getUserVOByUserId(staff.getUserId()));
					}
				}									
				break;
			case PARENT:
				parents = assemblerParentService.search(queryParam);
				if(users != null) {
					for(ParentVO parent: parents) {
						users.add(userService.getUserVOByUserId(parent.getUserId()));
					}
				}				
				break;
			case ALL:
				users = userService.searchUser(queryParam);							
				break;
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		
		} catch (SQLException | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
		response.setData(users);	
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;		
	}	
	
	
	@GetMapping(EndPointConstants.REST_USER_CONTROLLER.INDEX + "/teachers/courses/{courseId}")
	@ResponseBody
	public HttpResponseEntity<Object> loadTeachersByCourseID( Authentication auth, @PathVariable(value="courseId", required=false) Integer courseID) {
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		try {
			response.setData(assemblerTeacherService.loadTeachersByCourseID(courseID));
		} catch (SQLException | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
}
