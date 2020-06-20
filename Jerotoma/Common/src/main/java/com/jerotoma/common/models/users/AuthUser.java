package com.jerotoma.common.models.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.constants.UserConstant.USER_TYPE;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.StringUtility;

public class AuthUser extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Collection<Role> roles;
	private USER_TYPE userType;
	private Date createdOn;
	private Date updatedOn;
		
	public AuthUser(
			String username, 
			String password, 
			Boolean enabled, 
			Boolean accountNonExpired,
			Boolean credentialsNonExpired, 
			Boolean accountNonLocked,
			Collection<Role> roles) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(roles));
		this.roles = roles;
		this.createdOn = CalendarUtil.getTodaysDate();
		this.updatedOn = CalendarUtil.getTodaysDate();
		
	}
	
	
	public static AuthUser validateAndMapAuthUser(Map<String, Object> params, RoleConstant.USER_ROLES userRole) {
		
		String username = (String) params.get(UserConstant.USERNAME);		
		String password = (String) params.get(UserConstant.PASSWORD);
		String confirmPass = (String) params.get(UserConstant.CONFIRM_PASS);
		String userType = (String) params.get(UserConstant.userType);		
		Date updatedOn = CalendarUtil.getTodaysDate();
		Date createdOn =  CalendarUtil.getTodaysDate();
		
		if (StringUtility.isEmpty(username)) {
			throw new RuntimeException("Invalid or Empty username was provided");
		}
		
		if (StringUtility.isEmpty(password)) {
			throw new RuntimeException("Invalid or Empty password was provided");
		}
		
		if (StringUtility.isEmpty(userType)) {
			throw new RuntimeException("Invalid or Empty user type was provided");
		}
		
				
		if(!password.trim().equals(confirmPass.trim())) {
			throw new RuntimeException("Password not confirmed");
		}
				
		List<Role> roles = new ArrayList<>();
		Role role = new Role();	
		role.setName(userRole.getRoleName());
		role.setDisplayName(userRole.getDisplayName());
		roles.add(role);
		USER_TYPE uType = UserConstant.processUserType(userType);		
		AuthUser authUser = new AuthUser(username, password, true, true, true, true, roles);		
		authUser.setUserType(uType);
		authUser.setCreatedOn(createdOn);		
		authUser.setUpdatedOn(updatedOn);
		
		return authUser;
	}
	

	private static Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
	    List<GrantedAuthority> authorities = new ArrayList<>();
	    for (Role role: roles) {
	        authorities.add(new SimpleGrantedAuthority(role.getName()));		      
	    }		     
	    return authorities;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public USER_TYPE getUserType() {
		return userType;
	}

	public void setUserType(USER_TYPE userType) {
		this.userType = userType;
	}
}
