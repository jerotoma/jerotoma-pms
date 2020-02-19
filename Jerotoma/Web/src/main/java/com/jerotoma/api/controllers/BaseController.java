package com.jerotoma.api.controllers;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.Authentication;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.exceptions.UnAuthorizedAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.config.auth.common.UserContext;
import com.jerotoma.config.auth.interfaces.IAuthenticationFacade;
import com.jerotoma.services.roles.RoleService;
import com.jerotoma.services.users.AuthUserService;

public abstract class BaseController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	protected HttpResponseEntity<Object> instance;
	
	protected Date today = CalendarUtil.getTodaysDate();
	
	protected Map<String, Object> map;
	
	protected UserContext userContext;
	
	protected AuthUser authUser;
	
	@Autowired protected IAuthenticationFacade authenticationFacade;
	@Autowired protected AuthUserService authUserService;
	@Autowired protected ServletContext context;
	@Autowired protected RoleService roleService;
	
	public BaseController() {}
	
	@PostConstruct
	private void initialize(){
		instance = new HttpResponseEntity<>();
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
	
	protected void securityCheckAdminAccess(Authentication auth) {			
		userContext = authenticationFacade.getUserContext(auth);
		Role role = null;
		try {
			role = roleService.findObjectUniqueKey(RoleConstant.USER_ROLES.ROLE_ADMIN.getRoleName());
		} catch (SQLException e) {
			new NotFoundException("Role not found");
		}
		if (!userContext.getCurrentAuthorities().contains(role.getName())) {
			throw new UnAuthorizedAccessException("You have no authorization to access this resource");
		}
				
	}
	
	protected void logRequestDetail(String msg) {
		logger.debug(msg);
	}
	
	protected void logExceptionDetail(Throwable throwable) {}

}
