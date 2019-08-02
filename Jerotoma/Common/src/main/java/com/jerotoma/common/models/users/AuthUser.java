package com.jerotoma.common.models.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.jerotoma.common.models.security.Role;

public class AuthUser extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Collection<Role> roles;
	private String firstName;
	private String lastName;
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
	
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	
}
