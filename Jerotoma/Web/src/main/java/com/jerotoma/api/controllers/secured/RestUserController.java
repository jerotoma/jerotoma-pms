package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AcademicDisciplineConstant;
import com.jerotoma.common.constants.DepartmentConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.Department;
import com.jerotoma.common.models.academicDisciplines.AcademicDiscipline;
import com.jerotoma.common.models.addresses.Address;
import com.jerotoma.common.models.addresses.ParentAddress;
import com.jerotoma.common.models.addresses.StaffAddress;
import com.jerotoma.common.models.addresses.StudentAddress;
import com.jerotoma.common.models.addresses.TeacherAddress;
import com.jerotoma.common.models.positions.Position;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.common.models.users.Student;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.models.users.User;
import com.jerotoma.common.utils.validators.UserValidator;
import com.jerotoma.common.viewobjects.ParentVO;
import com.jerotoma.common.viewobjects.StaffVO;
import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.services.AddressService;
import com.jerotoma.services.academicdisciplines.AcademicDisciplineService;
import com.jerotoma.services.assemblers.AssemblerParentService;
import com.jerotoma.services.assemblers.AssemblerSequenceGeneratorService;
import com.jerotoma.services.assemblers.AssemblerStaffService;
import com.jerotoma.services.assemblers.AssemblerStudentService;
import com.jerotoma.services.assemblers.AssemblerTeacherService;
import com.jerotoma.services.assemblers.academic.AssemblerProgramService;
import com.jerotoma.services.assemblers.academic.DepartmentService;
import com.jerotoma.services.courses.AcademicLevelService;
import com.jerotoma.services.courses.ProgramService;
import com.jerotoma.services.positions.PositionService;
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
		
		
		UserConstant.USER_TYPE type = UserConstant.processUserType(userType);
		try {
			switch(type) {
			case TEACHER:
				mapVOs = assemblerTeacherService.loadMapList(queryParam);	
				break;
			case STUDENT:
				mapVOs = assemblerStudentService.loadMapList(queryParam);
				break;
			case STAFF:
				mapVOs = assemblerStaffService.loadMapList(queryParam);
				break;
			case PARENT:
				mapVOs = assemblerParentService.loadMapList(queryParam);
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
	
	@GetMapping(value= {"/{userTypeByPath}/{userId}","/{userTypeByPath}/{userId}/", })
	@ResponseBody
	public HttpResponseEntity<Object> getUser(Authentication auth,
			@PathVariable(value="userId", required = true) Integer primaryKey,
			@PathVariable(value="userTypeByPath", required = true) String userTypeByPath
			) throws UsernameNotFoundException, JDataAccessException{
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE + "/" + userTypeByPath + "/" + primaryKey);
		this.securityCheckAccessByRoles(auth);
		
		UserConstant.userTypeByPath type = UserConstant.processUserTypeByPath(userTypeByPath);
		try {
			switch(type) {
			case TEACHERS:
				response.setData(assemblerTeacherService.findObject(primaryKey));				
				break;
			case STUDENTS:
				response.setData(assemblerStudentService.findObject(primaryKey));
				break;
			case STAFFS:
				response.setData(assemblerStaffService.findObject(primaryKey));
				break;
			case PARENTS:
				response.setData(assemblerParentService.findObject(primaryKey));
				break;
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
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
	
	@PostMapping(value = {"", EndPointConstants.REST_USER_CONTROLLER.INDEX})
	@ResponseBody
	public HttpResponseEntity<Object> createUser(Authentication auth, @RequestBody Map<String, Object> params) throws JDataAccessException{
		
		List<String> requiredFields;
		Staff staff;
		Parent parent;
		User newUser;
		Teacher teacher;
		Student student;		
		Address address;
		Position position;
		StudentAddress studentAddress;
		ParentAddress parentAddress;
		Department department;
		
		
		requiredFields =  new ArrayList<>(Arrays.asList(
						UserConstant.FIRST_NAME, 
						UserConstant.LAST_NAME,
						UserConstant.USERNAME,
						UserConstant.ADDRESS,
						UserConstant.BIRTH_DATE,
						UserConstant.GENDER,
						UserConstant.PHONE_NUMBER
						));
				
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		userSecurityClearance.checkGeneralEntityCreationPermission();
					
		if(!params.containsKey(UserConstant.userType)) {
			throw new FieldRequiredException("User type can not be empty");
		}
		String userType = (String) params.get(UserConstant.userType);
		
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
				teacher  = UserValidator.validateTeacherInputInfo(params, requiredFields);
				
				newUser = User.validateAndMapAuthUser(params, RoleConstant.USER_ROLES.ROLE_TEACHER);				
				newUser = authUserService.createUserLoginAccount(newUser);
								
				teacher.setUserId(newUser.getId());
				teacher.setPosition(position);
				teacher.setDepartment(department);
				teacher.setUpdatedBy(authUser.getId());
				address = teacher.getAddress();				
				
				teacher = teacherService.createObject(teacher);	
				
				address.setUpdatedBy(authUser.getId());
				address = addressService.createObject(address);
				
				TeacherAddress teacherAddress = new TeacherAddress();
				teacherAddress.setAddress(address);
				teacherAddress.setTeacher(teacher);
				teacherAddress.setCreatedOn(today);
				teacherAddress.setUpdatedOn(today);
				teacherAddressService.createObject(teacherAddress);
				response.setData(teacher);
				break;
			case STUDENT:
				studentAddress = new StudentAddress();
				requiredFields.add(UserConstant.ACADEMIC_LEVEL_ID);
				requiredFields.add(UserConstant.PROGRAM_ID);
				
				student = UserValidator.validateStudentInputInfo(params, requiredFields);
				newUser = User.validateAndMapAuthUser(params, RoleConstant.USER_ROLES.ROLE_STUDENT);				
				newUser = authUserService.createUserLoginAccount(newUser);	
								
				student.setUpdatedBy(authUser.getId());
				if (student.getParentIds() != null) {
					Set<Parent> parents = new HashSet<>();
					for (Integer parentId: student.getParentIds()) {
						parent = parentService.findObject(parentId);
						parents.add(parent);
					}										
					student.setParents(parents);
				}
				student.setUserId(newUser.getId());
				student.setStudentNumber(sequenceGeneratorService.getNextNumber().intValue());
				address = student.getAddress();
				if (!assemblerProgramService.doesProgramAcademicLevelExist(student.getProgramId(), student.getAcademicLevelId())) {
					throw new FieldRequiredException("Program or Academic Level can not be empty or null.");
				}
				student.setProgram(programService.findObject(student.getProgramId()));	
				student.setCurrentAcademicLevel(academicLevelService.findObject(student.getAcademicLevelId()));
				
				student = studentService.createObject(student);
								
				address.setUpdatedBy(authUser.getId());
				address = addressService.createObject(address);
				
				studentAddress.setStudent(student);
				studentAddress.setAddress(address);
				studentAddress.setCreatedOn(today);
				studentAddress.setUpdatedOn(today);
				studentAddressService.createObject(studentAddress);	
				
				response.setData(student);				
				
				break;
			case STAFF:
				requiredFields.remove(UserConstant.BIRTH_DATE);
				requiredFields.add(UserConstant.POSITION);				
				position = processPosition(params, requiredFields);	
				staff = UserValidator.validateOtherStaffInputInfo(params, requiredFields);
				staff.setPosition(position);
				staff.setUpdatedBy(authUser.getId());
				address = staff.getAddress();
				
				newUser = User.validateAndMapAuthUser(params, RoleConstant.USER_ROLES.ROLE_STAFF);				
				newUser = authUserService.createUserLoginAccount(newUser);	
				
				staff.setUserId(newUser.getId());
				staff = staffService.createObject(staff);
							
				address.setUpdatedBy(authUser.getId());
				address = addressService.createObject(address);
				
				StaffAddress staffAddress = new StaffAddress();
				staffAddress.setAddress(address);
				staffAddress.setStaff(staff);
				staffAddress.setCreatedOn(today);
				staffAddress.setUpdatedOn(today);
				staffAddressService.createObject(staffAddress);
				response.setData(staff);
				break;
			case PARENT:
				requiredFields.remove(UserConstant.BIRTH_DATE);
				parentAddress = new ParentAddress();
				parent = UserValidator.validateParentInputInfo(params, requiredFields);
				
				newUser = User.validateAndMapAuthUser(params, RoleConstant.USER_ROLES.ROLE_PARENT);				
				newUser = authUserService.createUserLoginAccount(newUser);
				
				parent.setUserId(newUser.getId());
				parent.setUpdatedBy(authUser.getId());
				address = parent.getAddress();
				parent = parentService.createObject(parent);
								
				address.setUpdatedBy(authUser.getId());
				address = addressService.createObject(address);
				
				parentAddress.setAddress(address);
				parentAddress.setParent(parent);
				parentAddress.setCreatedOn(today);
				parentAddress.setUpdatedOn(today);
				parentAddressService.createObject(parentAddress);				
				response.setData(parent);
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
		Address address;
		Position position;
				
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		userSecurityClearance.checkGeneralEntityModificationPermission();
			
		if(!params.containsKey(UserConstant.userType)) {
			throw new FieldRequiredException("User type can not be empty");
		}
		String userType = (String) params.get(UserConstant.userType);
		
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
				Teacher mTeacher = teacherService.findObject(teacher.getId());
							
				mTeacher.setFirstName(teacher.getFirstName());
				mTeacher.setLastName(teacher.getLastName());
				mTeacher.setMiddleNames(teacher.getMiddleNames());
				mTeacher.setFullName(teacher.getFullName());
				mTeacher.setPosition(position);
				mTeacher.setDepartment(department);
				mTeacher.setAge(teacher.getAge());
				mTeacher.setBirthDate(teacher.getBirthDate());
				mTeacher.setGender(teacher.getGender());
				mTeacher.setOccupation(teacher.getOccupation());
				mTeacher.setPhoneNumber(teacher.getPhoneNumber());
				mTeacher.setUserCode(teacher.getUserCode());
				mTeacher.setPicture(teacher.getPicture());
				mTeacher.setUpdatedOn(new Date());
				mTeacher.setUpdatedBy(authUser.getId());
				mTeacher.setAddress(teacher.getAddress());
				
				address = teacher.getAddress();
				teacher = teacherService.updateObject(mTeacher);
				
				address.setUpdatedBy(authUser.getId());
				address = addressService.updateObject(address);
				response.setData(teacher);				
				break;
			case STUDENT:
				requiredFields.add(UserConstant.PHONE_NUMBER);
				
				student = UserValidator.validateStudentInputInfo(params, requiredFields);
				if (student.getId() == null) {
					throw new FieldRequiredException("Student ID is required");
				}
				Student mStudent = studentService.findObject(student.getId());	
				mStudent.setFirstName(student.getFirstName());
				mStudent.setLastName(student.getLastName());
				mStudent.setMiddleNames(student.getMiddleNames());
				mStudent.setFullName(student.getFullName());
				mStudent.setAge(student.getAge());
				mStudent.setBirthDate(student.getBirthDate());
				mStudent.setGender(student.getGender());
				mStudent.setOccupation(student.getOccupation());
				mStudent.setPhoneNumber(student.getPhoneNumber());
				mStudent.setParentIds(student.getParentIds());
				mStudent.setUpdatedOn(new Date());
				mStudent.setUpdatedBy(authUser.getId());
				mStudent.setAddress(student.getAddress());
				
				if (!assemblerProgramService.doesProgramAcademicLevelExist(student.getProgramId(), student.getAcademicLevelId())) {
					throw new FieldRequiredException("Program or Academic Level can not be empty or null.");
				}
				mStudent.setProgram(programService.findObject(student.getProgramId()));	
				mStudent.setCurrentAcademicLevel(academicLevelService.findObject(student.getAcademicLevelId()));
				
				address = mStudent.getAddress();
				
				if (mStudent.getParentIds() != null) {
					Set<Parent> parents = new HashSet<>();
					for (Integer parentId: mStudent.getParentIds()) {
						parent = parentService.findObject(parentId);
						parents.add(parent);
					}										
					mStudent.setParents(parents);
				}
				student = studentService.updateObject(mStudent);
				
				address.setUpdatedBy(authUser.getId());
				address = addressService.updateObject(address);				
				response.setData(student);								
				break;
			case STAFF:
				requiredFields.add(UserConstant.POSITION);				
				position = processPosition(params, requiredFields);	
				staff = UserValidator.validateOtherStaffInputInfo(params, requiredFields);
				
				if (staff.getId() == null) {
					throw new FieldRequiredException("Staff ID is required");
				}
				Staff mStaff = staffService.findObject(staff.getId());
				mStaff.setFirstName(staff.getFirstName());
				mStaff.setLastName(staff.getLastName());
				mStaff.setMiddleNames(staff.getMiddleNames());
				mStaff.setFullName(staff.getFullName());
				mStaff.setAge(staff.getAge());
				mStaff.setBirthDate(staff.getBirthDate());
				mStaff.setGender(staff.getGender());
				mStaff.setOccupation(staff.getOccupation());
				mStaff.setPhoneNumber(staff.getPhoneNumber());				
				mStaff.setUpdatedOn(new Date());				
				mStaff.setAddress(staff.getAddress());
				address = staff.getAddress();				
				mStaff.setPosition(position);
				mStaff.setUpdatedBy(authUser.getId());
				
				staff = staffService.updateObject(mStaff);				
				address.setUpdatedBy(authUser.getId());
				address = addressService.updateObject(address); 
								
				StaffAddress staffAddress = new StaffAddress();
				staffAddress.setAddress(address);
				staffAddress.setStaff(staff);
				staffAddress.setCreatedOn(today);
				staffAddress.setUpdatedOn(today);
				staffAddressService.updateObject(staffAddress);
				
				response.setData(staff);				
				break;
			case PARENT:
				
				parent = UserValidator.validateParentInputInfo(params, requiredFields);
				
				if (parent.getId() == null) {
					throw new FieldRequiredException("Parent ID is required");
				}
				
				Parent mParent = parentService.findObject(parent.getId());	
				mParent.setFirstName(parent.getFirstName());
				mParent.setLastName(parent.getLastName());
				mParent.setMiddleNames(parent.getMiddleNames());
				mParent.setFullName(parent.getFullName());
				mParent.setAge(parent.getAge());
				mParent.setBirthDate(parent.getBirthDate());
				mParent.setGender(parent.getGender());
				mParent.setOccupation(parent.getOccupation());
				mParent.setPhoneNumber(parent.getPhoneNumber());	
				mParent.setStudentIds(parent.getStudentIds());
				mParent.setUpdatedOn(new Date());
				mParent.setUpdatedBy(authUser.getId());
				mParent.setAddress(parent.getAddress());
				address = mParent.getAddress();
								
				if (mParent.getStudentIds() != null) {
					Set<Student> students = new HashSet<>();
					for (Integer studentId: mParent.getStudentIds()) {
						student = studentService.findObject(studentId);
						students.add(student);
					}										
					mParent.setStudents(students);
				}				
				mParent = parentService.updateObject(mParent);
				
				address.setUpdatedBy(authUser.getId());
				address = addressService.updateObject(address);
				response.setData(mParent);
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
		response.setData(getAuthenticatedUser());		
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
		response.setData(roleService.loadListFromRoleNames(getAuthenticatedUser().getRoles()));		
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
						users.add(new UserVO(authUser, teacher));
					}
				}				
				break;
			case STUDENT:
				students = assemblerStudentService.search(queryParam);
				if(users != null) {
					for(StudentVO student: students) {
						users.add(new UserVO(authUser, student));
					}
				}
				break;
			case STAFF:
				staffs = assemblerStaffService.search(queryParam);
				if(users != null) {
					for(StaffVO staff: staffs) {
						users.add(new UserVO(authUser, staff));
					}
				}									
				break;
			case PARENT:
				parents = assemblerParentService.search(queryParam);
				if(users != null) {
					for(ParentVO parent: parents) {
						users.add(new UserVO(authUser, parent));
					}
				}				
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
