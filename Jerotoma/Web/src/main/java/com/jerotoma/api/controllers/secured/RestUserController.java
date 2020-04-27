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
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academicDisciplines.AcademicDiscipline;
import com.jerotoma.common.models.addresses.Address;
import com.jerotoma.common.models.addresses.ParentAddress;
import com.jerotoma.common.models.addresses.StaffAddress;
import com.jerotoma.common.models.addresses.StudentAddress;
import com.jerotoma.common.models.addresses.TeacherAddress;
import com.jerotoma.common.models.positions.Position;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.common.models.users.Student;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.utils.validators.UserValidator;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.services.AddressService;
import com.jerotoma.services.academicdisciplines.AcademicDisciplineService;
import com.jerotoma.services.assemblers.AssemblerParentService;
import com.jerotoma.services.assemblers.AssemblerSequenceGeneratorService;
import com.jerotoma.services.assemblers.AssemblerStaffService;
import com.jerotoma.services.assemblers.AssemblerStudentService;
import com.jerotoma.services.assemblers.AssemblerTeacherService;
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
		
		
		UserConstant.USER_TYPES type = UserConstant.processUserType(userType);
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
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(mapVOs);
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
		
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
				instance.setData(assemblerTeacherService.findObject(primaryKey));
				
				break;
			case STUDENTS:
				instance.setData(assemblerStudentService.findObject(primaryKey));
				break;
			case STAFFS:
				instance.setData(assemblerStaffService.findObject(primaryKey));
				break;
			case PARENTS:
				instance.setData(assemblerParentService.findObject(primaryKey));
				break;
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
		
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
		AuthUser authUser = authUserService.loadUserByUsername(userContext.getUsername());		
		UserConstant.USER_TYPES type = UserConstant.processUserTypeByRole(authUser.getRoles());
		try {
			switch(type) {
			case TEACHER:
				instance.setData(assemblerTeacherService.findObjectUniqueKey(String.valueOf(authUser.getId())));	
				break;
			case STUDENT:
				instance.setData(assemblerStudentService.findObjectUniqueKey(username));
				break;
			case STAFF:
				instance.setData(assemblerStaffService.findObjectUniqueKey(username));
				break;
			case PARENT:
				instance.setData(assemblerParentService.findObjectUniqueKey(username));
				break;
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
		
	}
	
	@PostMapping(value = {"", EndPointConstants.REST_USER_CONTROLLER.INDEX})
	@ResponseBody
	public HttpResponseEntity<Object> createUser(Authentication auth, @RequestBody Map<String, Object> params) throws JDataAccessException{
		
		List<String> requiredFields;
		Staff staff;
		Parent parent;
		AuthUser newUser;
		Teacher teacher;
		Student student;		
		Address address;
		Position position;
		StudentAddress studentAddress;
		ParentAddress parentAddress;
		AcademicDiscipline academicDiscipline;
		
		
		requiredFields =  new ArrayList<>(Arrays.asList(
						UserConstant.FIRST_NAME, 
						UserConstant.LAST_NAME,
						UserConstant.USERNAME,
						UserConstant.ADDRESS,
						UserConstant.BIRTH_DATE,
						UserConstant.GENDER
						));
				
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
					
		if(!params.containsKey(UserConstant.USER_TYPE)) {
			throw new FieldIsRequiredException("User type can not be empty");
		}
		String userType = (String) params.get(UserConstant.USER_TYPE);
		
		if (userType == null) {
			throw new FieldIsRequiredException("User type can not be empty");
		}
		UserConstant.USER_TYPES type = UserConstant.processUserType(userType);
					
		try {
			switch(type) {
			case TEACHER:
				requiredFields.add(UserConstant.POSITION);
				requiredFields.add(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE);
				
				position = processPosition(params, requiredFields);			
				academicDiscipline = processAcademicDiscipline(params, requiredFields);
				teacher  = UserValidator.validateTeacherInputInfo(params, requiredFields);
				
				newUser = AuthUser.validateAndMapAuthUser(params, RoleConstant.USER_ROLES.ROLE_TEACHER);				
				newUser = authUserService.createUserLoginAccount(newUser);
								
				teacher.setUserId(newUser.getId());
				teacher.setPosition(position);
				teacher.setAcademicDiscipline(academicDiscipline);
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
				instance.setData(teacher);
				break;
			case STUDENT:
				studentAddress = new StudentAddress();
				requiredFields.add(UserConstant.PHONE_NUMBER);
				
				student = UserValidator.validateStudentInputInfo(params, requiredFields);
				newUser = AuthUser.validateAndMapAuthUser(params, RoleConstant.USER_ROLES.ROLE_STUDENT);				
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
				student = studentService.createObject(student);
								
				address.setUpdatedBy(authUser.getId());
				address = addressService.createObject(address);
				
				studentAddress.setStudent(student);
				studentAddress.setAddress(address);
				studentAddress.setCreatedOn(today);
				studentAddress.setUpdatedOn(today);
				studentAddressService.createObject(studentAddress);	
				
				instance.setData(student);				
				
				break;
			case STAFF:
				requiredFields.remove(UserConstant.BIRTH_DATE);
				requiredFields.add(UserConstant.POSITION);				
				position = processPosition(params, requiredFields);	
				staff = UserValidator.validateOtherStaffInputInfo(params, requiredFields);
				staff.setPosition(position);
				staff.setUpdatedBy(authUser.getId());
				address = staff.getAddress();
				
				newUser = AuthUser.validateAndMapAuthUser(params, RoleConstant.USER_ROLES.ROLE_STAFF);				
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
				instance.setData(staff);
				break;
			case PARENT:
				requiredFields.remove(UserConstant.BIRTH_DATE);
				parentAddress = new ParentAddress();
				parent = UserValidator.validateParentInputInfo(params, requiredFields);
				
				newUser = AuthUser.validateAndMapAuthUser(params, RoleConstant.USER_ROLES.ROLE_PARENT);				
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
				instance.setData(parent);
				break;			
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		
		} catch (SQLException | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}			
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setMessage("User has been created");
		instance.setSuccess(true);
		return instance;
		
	}

	private AcademicDiscipline processAcademicDiscipline(Map<String, Object> params, List<String> requiredFields) throws SQLException {
		Integer academicDisciplineId = null;
		if(params.containsKey(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE)) {
			academicDisciplineId = (Integer) params.get(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE);
		}
		
		if (academicDisciplineId == null && requiredFields.contains(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE)) {
			throw new FieldIsRequiredException("Academic Discipline can not be empty");
		}
		
		AcademicDiscipline academicDiscipline = academicDisciplineService.findObject(academicDisciplineId);
		
		if (academicDiscipline == null ) {
			throw new FieldIsRequiredException("Academic Discipline is required, and invalid academic discipline was provided");
		}
		return academicDiscipline;
	}

	private Position processPosition(Map<String, Object> params, List<String> requiredFields)
			throws SQLException {
		Integer positionId = null;
		if(params.containsKey(UserConstant.POSITION)) {
			positionId = (Integer) params.get(UserConstant.POSITION);
		}
		
		if (positionId == null && requiredFields.contains(UserConstant.POSITION)) {
			throw new FieldIsRequiredException("Position can not be empty");
		}
		
		Position position = positionService.findObject(positionId);
		
		if (position == null ) {
			throw new FieldIsRequiredException("Position is required, and invalid position was provided");
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
		Address address;
		Position position;
		AcademicDiscipline academicDiscipline;
				
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
			
		if(!params.containsKey(UserConstant.USER_TYPE)) {
			throw new FieldIsRequiredException("User type can not be empty");
		}
		String userType = (String) params.get(UserConstant.USER_TYPE);
		
		if (userType == null) {
			throw new FieldIsRequiredException("User type can not be empty");
		}
		
		UserConstant.USER_TYPES type = UserConstant.processUserType(userType);
		try {
			switch(type) {
			case TEACHER:				
				requiredFields.add(UserConstant.POSITION);
				requiredFields.add(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE);
				
				position = processPosition(params, requiredFields);			
				academicDiscipline = processAcademicDiscipline(params, requiredFields);
				
				requiredFields.add(UserConstant.USER_CODE);
				requiredFields.add(UserConstant.BIRTH_DATE);
				
				teacher  = UserValidator.validateTeacherInputInfo(params, requiredFields);
				
				if (teacher.getId() == null) {
					throw new FieldIsRequiredException("Teacher ID is required");
				}
				teacherService.findObject(teacher.getId());
				
				teacher.setPosition(position);
				teacher.setAcademicDiscipline(academicDiscipline);
				teacher.setUpdatedBy(authUser.getId());
				address = teacher.getAddress();
				teacher = teacherService.updateObject(teacher);
				
				address.setUpdatedBy(authUser.getId());
				address = addressService.updateObject(address);
				instance.setData(teacher);				
				break;
			case STUDENT:
				requiredFields.add(UserConstant.PHONE_NUMBER);
				
				student = UserValidator.validateStudentInputInfo(params, requiredFields);
				if (student.getId() == null) {
					throw new FieldIsRequiredException("Student ID is required");
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
				mStudent.setParentIds(student.getParentIds());
				mStudent.setUpdatedOn(new Date());
				mStudent.setUpdatedBy(authUser.getId());
				mStudent.setAddress(student.getAddress());
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
				instance.setData(student);								
				break;
			case STAFF:
				requiredFields.add(UserConstant.POSITION);				
				position = processPosition(params, requiredFields);	
				staff = UserValidator.validateOtherStaffInputInfo(params, requiredFields);
				
				if (staff.getId() == null) {
					throw new FieldIsRequiredException("Staff ID is required");
				}
				
				staff.setPosition(position);
				staff.setUpdatedBy(authUser.getId());
				address = staff.getAddress();
				staff = staffService.updateObject(staff);
				
				address.setUpdatedBy(authUser.getId());
				address = addressService.updateObject(address); 				
				instance.setData(staff);				
				break;
			case PARENT:
				
				parent = UserValidator.validateParentInputInfo(params, requiredFields);
				
				if (parent.getId() == null) {
					throw new FieldIsRequiredException("Parent ID is required");
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
				instance.setData(mParent);
				break;
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		
		} catch (SQLException | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setMessage("User has been updated");
		return instance;
		
	}
	
	@DeleteMapping(value = {EndPointConstants.REST_USER_CONTROLLER.INDEX + "{userId}", EndPointConstants.REST_USER_CONTROLLER.INDEX+ "{userId}/"})
	@ResponseBody
	public HttpResponseEntity<Object> deleteUser(Authentication auth, @PathVariable(name="userId", required = true) Integer userId, @RequestParam(value="userType", required=true) String userType) throws JDataAccessException{
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE + "/" + userId);
		this.securityCheckAccessByRoles(auth);
			
		if (userType == null) {
			throw new FieldIsRequiredException("User type can not be empty");
		}
		
		if (userId == null) {					
			throw new FieldIsRequiredException("Teacher's ID is required, and invalid ID was provided");					
		}
		
		UserConstant.USER_TYPES type = UserConstant.processUserType(userType);
		try {
			switch(type) {
			case TEACHER:
				Teacher teacher = teacherService.findObject(userId);							
				instance.setSuccess(teacherService.deleteObject(teacher));
				break;
			case STUDENT:
				
				Student student = studentService.findObject(userId);							
				instance.setSuccess(studentService.deleteObject(student));				
				break;
			case STAFF:				
				Staff staff = staffService.findObject(userId);							
				instance.setSuccess(staffService.deleteObject(staff));	
				break;
			case PARENT:				
				Parent parent =  parentService.findObject(userId);							
				instance.setSuccess(parentService.deleteObject(parent));	
				break;
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		
		} catch (SQLException | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
			
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setMessage("User has been created");
		return instance;
		
	}
	
	@GetMapping(EndPointConstants.REST_USER_CONTROLLER.CURRENT_USER)
	@ResponseBody
	public HttpResponseEntity<UserVO> getLoggedUser(Authentication auth) throws UsernameNotFoundException{
		HttpResponseEntity<UserVO> instance = new HttpResponseEntity<>();
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE + EndPointConstants.REST_USER_CONTROLLER.CURRENT_USER);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(new UserVO(authUser));
		return instance;
		
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
		
		List<AuthUser> users = null;
		List<UserVO> userVOs = new ArrayList<>();
		
	
		this.logRequestDetail("GET : " + EndPointConstants.REST_USER_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(search, page, pageSize, fieldName, orderby);
		
		UserConstant.USER_TYPES type = UserConstant.processUserType(userType);
		try {
			switch(type) {
			case TEACHER:
				users = authUserService.search(queryParam);	
				if(users != null) {
					for(AuthUser authUser: users) {
						userVOs.add(new UserVO(authUser));
					}
				}
				instance.setData(userVOs);
				break;
			case STUDENT:
				instance.setData(assemblerStudentService.search(queryParam));
				break;
			case STAFF:
				instance.setData(assemblerStaffService.search(queryParam));					
				break;
			case PARENT:				
				instance.setData(assemblerParentService.search(queryParam));
				break;
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		
		} catch (SQLException | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setHttpStatus(HttpStatus.OK);
		return instance;		
	}	
}
