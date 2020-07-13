package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.constants.UserConstant.USER_TYPE;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.common.models.users.User;
import com.jerotoma.services.roles.RoleService;
import com.jerotoma.services.users.ParentService;
import com.jerotoma.services.users.StaffService;
import com.jerotoma.services.users.StudentService;
import com.jerotoma.services.users.TeacherService;


@RestController
@RequestMapping(EndPointConstants.REST_ROLE_CONTROLLER.BASE)
public class RestRoleController extends BaseController {
	
	@Autowired RoleService roleService;
	@Autowired StudentService studentService;
	@Autowired StaffService staffService;
	@Autowired ParentService parentService;
	@Autowired TeacherService teacherService;
	

	@GetMapping
	public HttpResponseEntity<Object> index(Authentication auth) {		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_PROGRAM_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);		
		response.setData(roleService.loadList());								
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = {"/assignations"})
	@ResponseBody
	public HttpResponseEntity<Object> roleAssignation(Authentication auth, @RequestBody Map<String, Object> params) {		
		this.logRequestDetail("GET : "+ EndPointConstants.REST_PROGRAM_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		
		USER_TYPE userType = UserConstant.processUserType((String)params.get(UserConstant.USER_TYPE_LABEL));
		if (userType == null) {
			throw new FieldRequiredException("User type must be provided to continue");
		}
		Integer userId = (Integer)params.get(UserConstant.USER_ID);
		List<Integer> roleIds = (ArrayList<Integer>)params.get(RoleConstant.ROLE_IDS);		
		userSecurityClearance.checkRoleAssignationPermission();		
		List<Role> roles = new ArrayList<>();		
		try {
			User user = authUserService.findObject(userId);
			for (Integer roleId: roleIds ) {
				Role role = roleService.findObject(roleId);
				boolean found = false;
				for (Role r : user.getRoles()) {
					if (r.getId().equals(role.getId())) {
						found = true;
					}
				}				
				if (!found) {
					roles.add(role);
				}
			}
			if (roles.isEmpty()) {
				throw new FieldRequiredException("User has this role already");
			}
			user.setRoles(roles);
			response.setData(authUserService.updateObject(user));
		} catch (SQLException | JDataAccessException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		
										
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	
}
