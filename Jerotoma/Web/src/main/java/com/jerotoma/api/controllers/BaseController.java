package com.jerotoma.api.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.exceptions.UnAuthorizedAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.common.models.users.User;
import com.jerotoma.common.models.users.UserContext;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.config.auth.interfaces.IAuthenticationFacade;
import com.jerotoma.services.assemblers.AssemblerStaffService;
import com.jerotoma.services.positions.PositionService;
import com.jerotoma.services.roles.RoleService;
import com.jerotoma.services.securities.SecurityClearance;
import com.jerotoma.services.securities.UserSecurityClearance;
import com.jerotoma.services.users.AuthUserService;
import com.jerotoma.services.users.StaffService;
import com.jerotoma.services.users.UserService;

public abstract class BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	protected HttpResponseEntity<Object> response;
	
	protected Date today = CalendarUtil.getTodaysDate();
	
	protected Map<String, Object> map;
	
	protected QueryParam queryParam;
	
	protected UserContext userContext;
	
	protected User authUser;
	
	@Autowired protected IAuthenticationFacade authenticationFacade;
	@Autowired protected AuthUserService authUserService;
	@Autowired protected UserService userService;
	@Autowired protected AssemblerStaffService assemblerStaffService;
    @Autowired protected StaffService staffService;
    @Autowired protected PositionService positionService;
	@Autowired protected ServletContext context;
	@Autowired protected RoleService roleService;
	@Autowired protected UserSecurityClearance userSecurityClearance;
	
	public BaseController() {}
	
	@PostConstruct
	private void initialize(){
		response = new HttpResponseEntity<>();
		map = new HashMap<>();
		//
	}
	
	protected void proccessLoggedInUser(Authentication auth) {
		if(auth == null) {
			throw new UnAuthorizedAccessException("You have no authorization to add new AcademicYear to the system");
		}				
		userContext = authenticationFacade.getUserContext(auth);
		if(userContext == null){
			throw new UnAuthorizedAccessException("You have no authorization to add new AcademicYear to the system");
		}
		authUser = authUserService.loadUserByUsername(userContext.getUsername());
	}
	
	protected QueryParam setParams(String search, Integer page, Integer pageSize, String fieldName, String orderby){		
		
		pageSize = pageSize == null ? 12 : pageSize;
		orderby = StringUtility.isEmpty(orderby) || orderby.equals("none") || orderby.equals("undefined") ? "DESC" : orderby;
		fieldName = StringUtility.isEmpty(fieldName) || fieldName.equals("none") || fieldName.equals("undefined") ? "created_on" : fieldName;
		
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.setPage(page);
		queryParam.setPageSize(pageSize);
		queryParam.setFieldName(fieldName);
		queryParam.setOrderby(orderby);
		queryParam.setSearch(search);		
		
	 return queryParam;
	}
	
	protected QueryParam setParams(Integer page, Integer pageSize, String fieldName, String orderby){		
		
		pageSize = pageSize == null ? 12 : pageSize;
		page = page == null ? 1 : page;
		orderby = StringUtility.isEmpty(orderby) || orderby.equals("none") || orderby.equals("undefined") ? "DESC" : orderby;
		fieldName = StringUtility.isEmpty(fieldName) || fieldName.equals("none") || fieldName.equals("undefined") ? "created_on" : fieldName;
		
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.setPage(page);
		queryParam.setPageSize(pageSize);
		queryParam.setFieldName(fieldName);
		queryParam.setOrderby(orderby);				
		
	 return queryParam;
	}
	
	protected void securityCheckAccessByRoles(Authentication auth){
		if(auth == null) {
			throw new UnAuthorizedAccessException("You have no authorization to access this resource");
		}				
		userContext = authenticationFacade.getUserContext(auth);
		boolean hasRole = false;
		for (Role role : roleService.loadList()) {
			if (userContext.getCurrentAuthorities().contains(role.getName())) {
				hasRole = true;
				break;
			}			
		}
		if(!hasRole) {
			throw new UnAuthorizedAccessException("You have no authorization to access this resource");
		}
	}
	
	protected void securityCheckAdminAccess(Authentication auth, String action) {			
		UserContext userContext = authenticationFacade.getUserContext(auth);
		boolean hasRequiredRole = false;
		if(userContext.getCurrentAuthorities().contains(RoleConstant.USER_ROLES.ROLE_ADMIN.getRoleName())){
			hasRequiredRole = true;
		}
		
		if(userContext.getCurrentAuthorities().contains(RoleConstant.USER_ROLES.ROLE_TEACHER.getRoleName())){
			hasRequiredRole = true;			
		}
		
		if (!hasRequiredRole) {
			throw new UnAuthorizedAccessException("You have no authorization to " + action + " to the system");
		}
	}
	
	protected void logRequestDetail(String msg) {
		logger.debug(msg);
	}
	
	protected void logExceptionDetail(Throwable throwable) {}
	
	
	protected UserVO getAuthenticatedUser() {
		return this.userService.loadCurrentUser();
	}

}
