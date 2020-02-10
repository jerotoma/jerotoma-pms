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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.models.config.SystemConfig;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.common.models.users.UserRole;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.services.configs.SystemConfigService;
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
    @Autowired SystemConfigService systemConfigService;
    
    
    @Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
    	if (alreadySetup) {
            return;
        }
		logger.debug(event.getApplicationContext().getApplicationName());
        addDefaultAccountsIfNotExists(); 
        addDefaultAppTheme();
        alreadySetup = true;
		if(!logger.isDebugEnabled()) {
        	
        }
	}
	protected void addDefaultAccountsIfNotExists() {
		RoleConstant.USER_ROLES userRole = RoleConstant.USER_ROLES.ROLE_ADMIN;
		AuthUser authUser;
		String username = "john@jerotoma.com";
		String password = "Doe12345";
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
			UserRole mRole = new UserRole(null, authUser.getId(), roleR.getId());			
			userRoleService.createObject(mRole);
			logger.info("User has been created!");
		} catch (SQLException e) {			
			throw new RuntimeException(e.getMessage(), e); 
		}
		
	}
	
	private void addDefaultAppTheme() {
		SystemConfig defaultTheme = new SystemConfig(); 
		defaultTheme.setName(SystemConfigConstant.THEME_CONFIG.CURRENT_THEME.getDbName());
		defaultTheme.setValue("default");
		
		SystemConfig overrideUserTheme = new SystemConfig(); 
		overrideUserTheme.setName(SystemConfigConstant.THEME_CONFIG.OVERRIDE_USER_THEME.getDbName());
		overrideUserTheme.setValue("false");
		
		try {
			SystemConfig systemConfig = getSystemTheme(defaultTheme.getName());
			if (systemConfig == null) {
				systemConfigService.createObject(defaultTheme);
			} 
						
			systemConfig = getSystemTheme(overrideUserTheme.getName());
			if (systemConfig == null) {
				systemConfigService.createObject(overrideUserTheme);
			} 		
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e); 
		}
	}
	
	private SystemConfig getSystemTheme(String systemConfigKey) {
		SystemConfig systemConfig = null;
		
		try {
			systemConfig = systemConfigService.findObjectUniqueKey(systemConfigKey);		
		} catch (SQLException | EmptyResultDataAccessException e) {
			if (e instanceof EmptyResultDataAccessException) {
				systemConfig = null ; 
			} else {
				throw new JDataAccessException(e.getMessage(), e);
			}			
		}
		return systemConfig;
	}

}
