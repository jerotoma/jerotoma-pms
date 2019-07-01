package com.jerotoma.common.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.jerotoma.common.roles.Role;

public class AuthUser extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Collection<Role> roles;
	String username;
	String password;
	String firstName;
	String lastName;
	Boolean enabled; 
	Boolean accountNonExpired;
	Boolean credentialsNonExpire; 
	Boolean accountNonLocked;	
	Date createdOn;
	Date updatedOn;
		
	public AuthUser(
			String username, 
			String password, 
			Boolean enabled, 
			Boolean accountNonExpired,
			Boolean credentialsNonExpired, 
			Boolean accountNonLocked,
			Collection<Role> roles) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(roles));
		
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

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getCredentialsNonExpire() {
		return credentialsNonExpire;
	}

	public void setCredentialsNonExpire(Boolean credentialsNonExpire) {
		this.credentialsNonExpire = credentialsNonExpire;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
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
	
}
