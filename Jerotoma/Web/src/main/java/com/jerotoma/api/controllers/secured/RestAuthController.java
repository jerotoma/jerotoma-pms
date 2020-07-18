package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.common.models.users.User;
import com.jerotoma.common.utils.CalendarUtil;

@RestController
@RequestMapping(EndPointConstants.REST_AUTH_CONTROLLER.BASE)
public class RestAuthController extends BaseController {
	
	@PostMapping(EndPointConstants.REST_AUTH_CONTROLLER.REGISTER)
	@ResponseBody
	public HttpResponseEntity<Object> postCreate(@RequestBody Map<String, Object> params) {		
		response.setSuccess(true);
		response.setStatusCode("200");		
		try {
			authUser = authUserService.createUserLoginAccount(
					User.validateAndMapAuthUser(params, 
							RoleConstant.USER_ROLES.ROLE_USER));		
			
			Staff staff = new Staff();
			staff.setUserId(authUser.getId());		
			staff.setFirstName(params.get(UserConstant.FIRST_NAME).toString());
			staff.setLastName(params.get(UserConstant.LAST_NAME).toString());
			staff.setPhoneNumber(UserConstant.DEFAULT_PHONE_NUMBER);			
			staff.setBirthDate(CalendarUtil.convertStringToDate(UserConstant.DEFAULT_BIRTH_DATE));
			staff.setCreatedOn(CalendarUtil.getTodaysDate());
			staff.setUpdatedOn(CalendarUtil.getTodaysDate());
			staffService.createObject(staff);
			response.setData(userService.getUserByUsername(authUser.getUsername()));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);	
		}		
		return response;		
	}
    
    @GetMapping(EndPointConstants.REST_AUTH_CONTROLLER.AUTH_USER)
	@ResponseBody
	public HttpResponseEntity<Object> getLoggedInUser(Authentication authentication) throws UsernameNotFoundException {				
		this.logRequestDetail("GET : " + EndPointConstants.REST_AUTH_CONTROLLER.AUTH_USER);
		this.securityCheckAccessByRoles(authentication);
		this.proccessLoggedInUser(authentication);
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));		
		response.setData(getAuth());		
		return response;		
	}
	

}
