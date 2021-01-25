package com.jerotoma.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import com.jerotoma.common.constants.RoleConstant.USER_ROLES;
import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.constants.UserConstant.USER_TYPE;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.models.config.SystemConfig;
import com.jerotoma.common.models.positions.Position;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.common.models.users.User;
import com.jerotoma.common.models.users.Staff;
import com.jerotoma.common.models.users.UserRole;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.viewobjects.StaffVO;
import com.jerotoma.services.assemblers.AssemblerStaffService;
import com.jerotoma.services.configs.SystemConfigService;
import com.jerotoma.services.positions.PositionService;
import com.jerotoma.services.roles.RoleService;
import com.jerotoma.services.users.AuthUserService;
import com.jerotoma.services.users.StaffService;
import com.jerotoma.services.users.UserRoleService;

@Component
public class StartUpDataLoader implements ApplicationListener<ContextRefreshedEvent> {
	private static boolean alreadySetup = false;
    private Logger logger = LoggerFactory.getLogger(getClass());
   
    @Autowired AuthUserService userService;
    @Autowired AssemblerStaffService assemblerStaffService;
    @Autowired StaffService staffService;
    @Autowired PositionService positionService;
    @Autowired RoleService roleService;
    @Autowired UserRoleService userRoleService;
    @Autowired SystemConfigService systemConfigService;
    
    @Value("classpath:db/database-functions.sql")
	Resource databaseFunctionResource;
	
	@Autowired DataSource dataSource;
    
    
    @Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
    	if (alreadySetup) {
            return;
        }
		logger.debug(event.getApplicationContext().getApplicationName());
		runDatabaseResources(dataSource, databaseFunctionResource);	
        addDefaultAccountsIfNotExists(); 
        addDefaultAppTheme();
        alreadySetup = true;
		if(!logger.isDebugEnabled()) {
        	
        }
	}
	protected void addDefaultAccountsIfNotExists() {
		USER_ROLES adminRole = USER_ROLES.ROLE_ADMIN;
		User authUser;
		String username = "support@vimmak.com";
		String password = "Vimmak";
		Boolean enabled = true; 
		Boolean accountNonExpired = true;
		Boolean credentialsNonExpired = true; 
		Boolean accountNonLocked = true;
		
		try {
			createRoles();
			createPosition();
			Long count = userService.countObject();
			if (count != null && count != 0) {
				return;
			}			
			Role roleR = roleService.findObjectUniqueKey(adminRole.getRoleName());
			if (roleR == null) {
				roleR = roleService.createObject(roleR);
			}
			
			Collection<Role> roles = new ArrayList<>(Arrays.asList(roleR));
			authUser = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, roles);			
			authUser.setUserType(USER_TYPE.STAFF);
			authUser = userService.createObject(authUser);
			
			UserRole mRole = new UserRole(null, authUser.getId(), roleR.getId());
			UserRole userRole = userRoleService.findUserRoleByUserIdAndRoleID(mRole.getUserId(), mRole.getRoleId());
			if (userRole == null) {
				userRoleService.createObject(mRole);
				logger.info("User has been created!");
			}			
		} catch (SQLException e) {			
			throw new RuntimeException(e.getMessage(), e); 
		}
		Position position = createPosition();
		if (authUser != null) {		
			createStaff(authUser, position);
		}		
	}
	private void createRoles() throws SQLException {
		for (USER_ROLES userRole : USER_ROLES.values()) {
			Role role = new Role();
			role.setUpdatedOn(CalendarUtil.getTodaysDate());
			role.setCreatedOn(CalendarUtil.getTodaysDate());
			role.setName(userRole.getRoleName());
			role.setDisplayName(userRole.getDisplayName());
			Role roleR = roleService.findObjectUniqueKey(role.getName());
			if (roleR == null) {
				roleR = roleService.createObject(role);
			}
		}
	}
	
	private Position createPosition() {
		Position position = null;
		try {
			Long count = positionService.countObject();			
			if (count != null && count != 0) {
				position = positionService.loadList().get(0);
			} else {
				position = new Position();
				position.setCode("AD-345");
				position.setName("System Admin");
				position.setCreatedOn(CalendarUtil.getTodaysDate());
				position.setUpdatedOn(CalendarUtil.getTodaysDate());
				position = positionService.createObject(position);
			}		
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e); 
		}
		return position;
	}
	
	private void createStaff(User user, Position position) {
		StaffVO staffVO;
		try {
			staffVO = assemblerStaffService.findObjectUniqueKey(user.getUsername());
		} catch (SQLException | EmptyResultDataAccessException e) {
			if (e instanceof EmptyResultDataAccessException ) {
				staffVO = null;				
			} else {
				throw new RuntimeException(e.getMessage(), e); 
			}			
		}	
		if (staffVO != null) {
			return;
		}
		Staff staff = new Staff();
		staff.setUserId(user.getId());
		staff.setPosition(position);
		staff.setFirstName("John");
		staff.setLastName("Doe");
		try {
			staffService.createObject(staff);
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
	
	private void runDatabaseResources(DataSource dataSource, Resource resource) {		
		try {        	      
   	     ScriptUtils.executeSqlScript(
   	    		 dataSource.getConnection(),
   	    		 new EncodedResource(resource, "UTF-8"), 
   	    		 false, 
   	    		 false, 
   	    		 ScriptUtils.DEFAULT_COMMENT_PREFIX, 
   	    		 ScriptUtils.DEFAULT_STATEMENT_SEPARATOR + ';',
   	    		 ScriptUtils.DEFAULT_BLOCK_COMMENT_START_DELIMITER, 
   	    		 ScriptUtils.DEFAULT_BLOCK_COMMENT_END_DELIMITER);
   	    } catch (Exception e) {
   	      e.printStackTrace();
   	    }
	}
}
