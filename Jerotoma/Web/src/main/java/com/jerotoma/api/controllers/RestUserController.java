package com.jerotoma.api.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

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

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AcademicDisciplineConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.exceptions.FieldCanNotBeEmptyException;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.exceptions.UnAuthorizedAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academicDisciplines.AcademicDiscipline;
import com.jerotoma.common.models.positions.Position;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.common.models.users.OtherStaff;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.common.models.users.Student;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.common.utils.validators.UserValidator;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.config.auth.common.UserContext;
import com.jerotoma.config.auth.interfaces.IAuthenticationFacade;
import com.jerotoma.services.academicdisciplines.AcademicDisciplineService;
import com.jerotoma.services.assemblers.AssemblerTeacherService;
import com.jerotoma.services.positions.PositionService;
import com.jerotoma.services.users.AuthUserService;
import com.jerotoma.services.users.OtherStaffService;
import com.jerotoma.services.users.ParentService;
import com.jerotoma.services.users.StudentService;
import com.jerotoma.services.users.TeacherService;

@RestController
@RequestMapping(EndPointConstants.REST_USER_CONTROLLER.BASE)
public class RestUserController {
	
	@Autowired AuthUserService authUserService;
	@Autowired IAuthenticationFacade authenticationFacade;
	@Autowired TeacherService teacherService;
	@Autowired AssemblerTeacherService assemblerTeacherService;
	@Autowired StudentService studentService;
	@Autowired OtherStaffService otherStaffService;
	@Autowired ParentService parentService;
	@Autowired PositionService positionService;
	@Autowired AcademicDisciplineService academicDisciplineService;
	AuthUser authUser;
	
	@GetMapping(value= {"", EndPointConstants.REST_USER_CONTROLLER.INDEX})
	@ResponseBody
	public HttpResponseEntity<Object> getUsers(Authentication auth,
			@RequestParam(value="userType", required=false) String userType,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) throws UsernameNotFoundException, JDataAccessException{
		
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
		Map<String, Object> mapVOs = new HashMap<>();
		
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
		
		page = page == null ? 1 : page;
		pageSize = pageSize == null ? 12 : pageSize;
		orderby = StringUtility.isEmpty(orderby) || orderby.equals("none") || orderby.equals("undefined") ? "DESC" : orderby;


		QueryParam queryParam =  QueryParam.getInstance();
		queryParam.setPage(page);
		queryParam.setPageSize(pageSize);
		queryParam.setFieldName(fieldName);
		queryParam.setOrderby(orderby);
				
		UserContext userContext = authenticationFacade.getUserContext(auth);
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.EROLE.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new Teacher to the system");
		}
		
		UserConstant.USER_TYPES type = UserConstant.processUserType(userType);
		try {
			switch(type) {
			case TEACHER:
				mapVOs = assemblerTeacherService.loadMapList(queryParam);	
				break;
			case STUDENT:
				
				break;
			case STAFF:
				
				break;
			case PARENT:
				
				break;
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(mapVOs);
		return instance;
		
	}
	
	@GetMapping(value= {"/{userId}","/{userId}/", })
	@ResponseBody
	public HttpResponseEntity<Object> getUser(Authentication auth,
			@PathVariable(value="userId", required = true) Integer primaryKey,
			@RequestParam(value="userType", required = true) String userType
			) throws UsernameNotFoundException, JDataAccessException{
		
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
		
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
				
		UserContext userContext = authenticationFacade.getUserContext(auth);
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.EROLE.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new Teacher to the system");
		}
		
		UserConstant.USER_TYPES type = UserConstant.processUserType(userType);
		try {
			switch(type) {
			case TEACHER:
				instance.setData(assemblerTeacherService.findObject(primaryKey));
				
				break;
			case STUDENT:
				
				break;
			case STAFF:
				
				break;
			case PARENT:
				
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
		
		HttpResponseEntity<Object> instance = HttpResponseEntity.getInstance();
		List<String> requiredFields;
		Integer positionId = null;
		Integer academicDisciplineId = null;
				
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
		
		UserContext userContext = authenticationFacade.getUserContext(auth);
		
		if (userContext == null) {
			throw new UnAuthorizedAccessException("You have to be logged in to continue");
		}
		
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.EROLE.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new Teacher to the system");
		}
			
		if(!params.containsKey(UserConstant.USER_TYPE)) {
			throw new FieldCanNotBeEmptyException("User type can not be empty");
		}
		String userType = (String) params.get(UserConstant.USER_TYPE);
		
		if (userType == null) {
			throw new FieldCanNotBeEmptyException("User type can not be empty");
		}
		
		
		UserConstant.USER_TYPES type = UserConstant.processUserType(userType);
		try {
			switch(type) {
			case TEACHER:
				requiredFields =  new ArrayList<>(
						Arrays.asList(
								UserConstant.FIRST_NAME, 
								UserConstant.LAST_NAME,
								UserConstant.OCCUPATION,
								UserConstant.TERM,
								UserConstant.GENDER,
								UserConstant.POSITION,
								AcademicDisciplineConstant.ACADEMIC_DISCIPLINE));
				
				if(params.containsKey(UserConstant.POSITION)) {
					positionId = (Integer) params.get(UserConstant.POSITION);
				}
				
				if (positionId == null && requiredFields.contains(UserConstant.POSITION)) {
					throw new FieldCanNotBeEmptyException("Position can not be empty");
				}
				
				Position position = positionService.findObject(positionId);
				
				if (position == null ) {
					throw new FieldCanNotBeEmptyException("Position is required, and invalid position was provided");
				}
				
				
				if(params.containsKey(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE)) {
					academicDisciplineId = (Integer) params.get(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE);
				}
				
				if (academicDisciplineId == null && requiredFields.contains(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE)) {
					throw new FieldCanNotBeEmptyException("Academic Discipline can not be empty");
				}
				
				AcademicDiscipline academicDiscipline = academicDisciplineService.findObject(academicDisciplineId);
				
				if (academicDiscipline == null ) {
					throw new FieldCanNotBeEmptyException("Academic Discipline is required, and invalid academic discipline was provided");
				}
				
				
				Teacher teacher  = UserValidator.validateTeacherInputInfo(params, requiredFields);
				teacher.setPosition(position);
				teacher.setAcademicDiscipline(academicDiscipline);
				
				teacher = teacherService.createObject(teacher);
				
				
				instance.setData(teacher);
				break;
			case STUDENT:
				requiredFields =  new ArrayList<>(
						Arrays.asList(
								UserConstant.FIRST_NAME, 
								UserConstant.LAST_NAME,
								UserConstant.OCCUPATION,
								UserConstant.TERM,
								UserConstant.GENDER,
								UserConstant.POSITION));
				Student student = studentService.createObject(
						UserValidator.validateStudentInputInfo(params, requiredFields));
				instance.setData(student);				
				break;
			case STAFF:
				requiredFields =  new ArrayList<>(
						Arrays.asList(
								UserConstant.FIRST_NAME, 
								UserConstant.LAST_NAME,
								UserConstant.OCCUPATION,
								UserConstant.TERM,
								UserConstant.GENDER,
								UserConstant.POSITION));
				OtherStaff otherStaff = otherStaffService.createObject(
						UserValidator.validateOtherStaffInputInfo(params, requiredFields));
				instance.setData(otherStaff);
				break;
			case PARENT:
				requiredFields =  new ArrayList<>(
						Arrays.asList(
								UserConstant.FIRST_NAME, 
								UserConstant.LAST_NAME,
								UserConstant.OCCUPATION,
								UserConstant.TERM,
								UserConstant.GENDER,
								UserConstant.POSITION));
				Parent parent = parentService.createObject(
						UserValidator.validateParentInputInfo(params, requiredFields));
				instance.setData(parent);
				break;
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		
		} catch (SQLException | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setMessage("User has been created");
		return instance;
		
	}
	
	@PutMapping(value = {"", EndPointConstants.REST_USER_CONTROLLER.INDEX})
	@ResponseBody
	public HttpResponseEntity<Object> updateUser(Authentication auth, @RequestBody Map<String, Object> params) throws JDataAccessException{
		
		HttpResponseEntity<Object> instance = HttpResponseEntity.getInstance();
		List<String> requiredFields = new ArrayList<>(
				Arrays.asList(
						UserConstant.ID,
						UserConstant.FIRST_NAME,
						UserConstant.OCCUPATION,
						UserConstant.LAST_NAME,
						UserConstant.GENDER));;
		Integer positionId = null;
		Integer academicDisciplineId = null;
				
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
		
		UserContext userContext = authenticationFacade.getUserContext(auth);
		
		if (userContext == null) {
			throw new UnAuthorizedAccessException("You have to be logged in to continue");
		}
		
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.EROLE.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new Teacher to the system");
		}
			
		if(!params.containsKey(UserConstant.USER_TYPE)) {
			throw new FieldCanNotBeEmptyException("User type can not be empty");
		}
		String userType = (String) params.get(UserConstant.USER_TYPE);
		
		if (userType == null) {
			throw new FieldCanNotBeEmptyException("User type can not be empty");
		}
		
		
		UserConstant.USER_TYPES type = UserConstant.processUserType(userType);
		try {
			switch(type) {
			case TEACHER:
				requiredFields.add(UserConstant.BIRTH_DATE);
				requiredFields.add(UserConstant.POSITION);
				requiredFields.add(UserConstant.EMPLOYMENT_CODE);
				requiredFields.add(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE);
				
				requiredFields.add(UserConstant.USER_ID);
				
				
				if(params.containsKey(UserConstant.POSITION)) {
					positionId = (Integer) params.get(UserConstant.POSITION);
				}
				
				if (positionId == null && requiredFields.contains(UserConstant.POSITION)) {
					throw new FieldCanNotBeEmptyException("Position can not be empty");
				}
				
				Position position = positionService.findObject(positionId);
				
				if (position == null ) {
					throw new FieldCanNotBeEmptyException("Position is required, and invalid position was provided");
				}
				
				
				if(params.containsKey(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE)) {
					academicDisciplineId = (Integer) params.get(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE);
				}
				
				if (academicDisciplineId == null && requiredFields.contains(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE)) {
					throw new FieldCanNotBeEmptyException("Academic Discipline can not be empty");
				}
				
				AcademicDiscipline academicDiscipline = academicDisciplineService.findObject(academicDisciplineId);
				
				if (academicDiscipline == null ) {
					throw new FieldCanNotBeEmptyException("Academic Discipline is required, and invalid academic discipline was provided");
				}
				
				
				Teacher teacher  = UserValidator.validateTeacherInputInfo(params, requiredFields);
				teacher.setPosition(position);
				teacher.setAcademicDiscipline(academicDiscipline);
				
				if (teacher.getId() == null) {					
					throw new FieldCanNotBeEmptyException("Teacher's ID is required, and invalid ID was provided");					
				}
				
				teacher = teacherService.updateObject(teacher);				
				instance.setData(teacher);
				break;
			case STUDENT:
				requiredFields =  new ArrayList<>(
						Arrays.asList(
								UserConstant.FIRST_NAME, 
								UserConstant.LAST_NAME,
								UserConstant.TERM,
								UserConstant.GENDER,
								UserConstant.POSITION));
				Student student = studentService.createObject(
						UserValidator.validateStudentInputInfo(params, requiredFields));
				instance.setData(student);				
				break;
			case STAFF:
				requiredFields =  new ArrayList<>(
						Arrays.asList(
								UserConstant.FIRST_NAME, 
								UserConstant.LAST_NAME,
								UserConstant.OCCUPATION,
								UserConstant.TERM,
								UserConstant.GENDER,
								UserConstant.POSITION));
				OtherStaff otherStaff = otherStaffService.createObject(
						UserValidator.validateOtherStaffInputInfo(params, requiredFields));
				instance.setData(otherStaff);
				break;
			case PARENT:
				requiredFields =  new ArrayList<>(
						Arrays.asList(
								UserConstant.FIRST_NAME, 
								UserConstant.LAST_NAME,
								UserConstant.OCCUPATION,
								UserConstant.TERM,
								UserConstant.GENDER,
								UserConstant.POSITION));
				Parent parent = parentService.createObject(
						UserValidator.validateParentInputInfo(params, requiredFields));
				instance.setData(parent);
				break;
			default:
				throw new UsernameNotFoundException("User type not found");
			}
		
		} catch (SQLException | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setMessage("User has been created");
		return instance;
		
	}
	
	@DeleteMapping(value = {EndPointConstants.REST_USER_CONTROLLER.INDEX + "/{userId}", EndPointConstants.REST_USER_CONTROLLER.INDEX+ "/{userId}/"})
	@ResponseBody
	public HttpResponseEntity<Object> deleteUser(Authentication auth, @PathVariable(name="userId", required = true) Integer userId, @RequestParam(value="userType", required=true) String userType) throws JDataAccessException{
		
		HttpResponseEntity<Object> instance = HttpResponseEntity.getInstance();
				
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
		
		UserContext userContext = authenticationFacade.getUserContext(auth);
		
		if (userContext == null) {
			throw new UnAuthorizedAccessException("You have to be logged in to continue");
		}
		
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.EROLE.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new Teacher to the system");
		}
			
		if (userType == null) {
			throw new FieldCanNotBeEmptyException("User type can not be empty");
		}
		
		if (userId == null) {					
			throw new FieldCanNotBeEmptyException("Teacher's ID is required, and invalid ID was provided");					
		}
		
		UserConstant.USER_TYPES type = UserConstant.processUserType(userType);
		try {
			switch(type) {
			case TEACHER:
				Teacher teacher = teacherService.findObject(userId);							
				instance.setSuccess(teacherService.deleteObject(teacher));
				break;
			case STUDENT:
				
				teacher = teacherService.findObject(userId);							
				instance.setSuccess(teacherService.deleteObject(teacher));				
				break;
			case STAFF:
				
				teacher = teacherService.findObject(userId);							
				instance.setSuccess(teacherService.deleteObject(teacher));	
				break;
			case PARENT:				
				teacher = teacherService.findObject(userId);							
				instance.setSuccess(teacherService.deleteObject(teacher));	
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
		
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
		
		UserContext userContext = authenticationFacade.getUserContext(auth);
		
		
		try {	
			authUser = authUserService.loadUserByUsername(userContext.getUsername());
		} catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage());			
		}	
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(new UserVO(authUser));
		return instance;
		
	}
	
	@GetMapping(EndPointConstants.REST_USER_CONTROLLER.SEARCH)
	@ResponseBody
	public HttpResponseEntity<List<UserVO>> searchUser(
			Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) throws UsernameNotFoundException{
		
		HttpResponseEntity<List<UserVO>> instance = new HttpResponseEntity<>();
		List<AuthUser> users = null;
		List<UserVO> userVOs = new ArrayList<>();
		
		if(auth == null) {
			instance.setSuccess(false);
			instance.setStatusCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
			return instance;
		}
		
		page = page == null ? 1 : page;
		pageSize = pageSize == null ? 12 : pageSize;
		orderby = StringUtility.isEmpty(orderby) || orderby.equals("none") || orderby.equals("undefined") ? "DESC" : orderby;


		QueryParam queryParam =  QueryParam.getInstance();
		queryParam.setPage(page);
		queryParam.setPageSize(pageSize);
		queryParam.setFieldName(fieldName);
		queryParam.setOrderby(orderby);
		queryParam.setSearch(search);
				
		UserContext userContext = authenticationFacade.getUserContext(auth);
		if(!userContext.getCurrentAuthorities().contains(RoleConstant.EROLE.ROLE_ADMIN.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new Teacher to the system");
		}
		
		try {	
			users = authUserService.search(queryParam);					
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		if(users != null) {
			for(AuthUser authUser: users) {
				userVOs.add(new UserVO(authUser));
			}
		}
		
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(userVOs);
		return instance;
		
	}
	

}
