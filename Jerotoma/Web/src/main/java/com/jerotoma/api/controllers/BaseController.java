package com.jerotoma.api.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.exceptions.UnAuthorizedAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.config.auth.common.UserContext;
import com.jerotoma.config.auth.interfaces.IAuthenticationFacade;
import com.jerotoma.services.users.AuthUserService;

public abstract class BaseController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected HttpResponseEntity<Object> instance;
	
	protected Map<String, Object> map;
	
	protected UserContext userContext;
	
	@Autowired protected IAuthenticationFacade authenticationFacade;
	@Autowired protected AuthUserService authUserService;
	
	public BaseController() {
		super();
		this.initialize();
	}
	
	private void initialize(){
		instance = new HttpResponseEntity<>();
		map = new HashMap<>();
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
	
	protected void securityCheckAdminAccess(Authentication auth){		
		this.securityCheckAccessByRole(auth,RoleConstant.USER_ROLES.ROLE_ADMIN);			
	}
	
	protected void securityCheckAccessByRole(Authentication auth, RoleConstant.USER_ROLES role){		
		if(auth == null) {
			throw new UnAuthorizedAccessException("You have no authorization to add new AcademicYear to the system");
		}				
		userContext = authenticationFacade.getUserContext(auth);
		if(!userContext.getCurrentAuthorities().contains(role.getRoleName())){
			throw new UnAuthorizedAccessException("You have no authorization to add new AcademicYear to the system");
		}		
	}
	
	protected void logRequestDetail(String msg) {
		logger.debug(msg);
	}
	
	protected void logExceptionDetail(Throwable throwable) {}

}
