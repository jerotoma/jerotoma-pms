package com.jerotoma.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.common.models.users.UserRole;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.services.roles.RoleService;
import com.jerotoma.services.users.AuthUserService;
import com.jerotoma.services.users.UserRoleService;

@Component
public class StartUpDataLoader implements ApplicationListener<ContextRefreshedEvent> {
	private boolean alreadySetup = false;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired AuthUserService userService;
    @Autowired RoleService roleService;
    @Autowired UserRoleService userRoleService;
    
    
    @Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
    	if (alreadySetup) {
            return;
        }
		logger.debug(event.getApplicationContext().getApplicationName());
        addDefaultAccountsIfNotExists();            
        alreadySetup = true;
		if(!logger.isDebugEnabled()) {
        	
        }
	}
	protected void addDefaultAccountsIfNotExists() {
		RoleConstant.USER_ROLES userRole = RoleConstant.USER_ROLES.ROLE_ADMIN;
		AuthUser authUser;
		String username = "john@jerotoma.com";
		String password = "Doe";
		Boolean enabled = true; 
		Boolean accountNonExpired = true;
		Boolean credentialsNonExpired = true; 
		Boolean accountNonLocked = true;
		Role role = new Role();
		role.setUpdatedOn(CalendarUtil.getTodaysDate());
		role.setCreatedOn(CalendarUtil.getTodaysDate());
		role.setName(userRole.getRoleName());
		role.setDisplayName(userRole.getDisplayName());
		try {
			Long count = userService.countObject();
			if (count != null && count != 0) {
				return;
			}
			
			Role roleR = roleService.findObjectUniqueKey(role.getName());
			if (roleR == null) {
				roleR = roleService.createObject(role);
			}
			
			Collection<Role> roles = new ArrayList<>(Arrays.asList(roleR));
			authUser = new AuthUser(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, roles);
			authUser.setFirstName("John");
			authUser.setLastName("Doe");
			authUser = userService.createObject(authUser);
			UserRole uRole = new UserRole(null, authUser.getId(), roleR.getId());			
			userRoleService.createObject(uRole);
			logger.info("User has been created!");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
	}

}
