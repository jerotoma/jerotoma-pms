package com.jerotoma.api.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.exceptions.FieldCanNotBeEmptyException;
import com.jerotoma.common.exceptions.UnAuthorizedAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.common.models.users.OtherStaff;
import com.jerotoma.common.models.users.Parent;
import com.jerotoma.common.models.users.Student;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.common.utils.validators.UserValidator;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.config.auth.common.UserContext;
import com.jerotoma.config.auth.interfaces.IAuthenticationFacade;
import com.jerotoma.services.assemblers.AssemblerTeacherService;
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
		List<TeacherVO> teacherVOs = new ArrayList<>();
		
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
				teacherVOs = assemblerTeacherService.loadList(queryParam);	
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
		instance.setData(teacherVOs);
		return instance;
		
	}
	
	@PostMapping(value = {"", EndPointConstants.REST_USER_CONTROLLER.INDEX})
	@ResponseBody
	public HttpResponseEntity<Object> createUser(Authentication auth, @RequestBody Map<String, Object> params) throws JDataAccessException{
		
		HttpResponseEntity<Object> instance = HttpResponseEntity.getInstance();
		List<String> requiredFields;
				
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
								UserConstant.POSITION));
				Teacher teacher = teacherService.createObject(
						UserValidator.validateTeacherInputInfo(params, requiredFields));
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
