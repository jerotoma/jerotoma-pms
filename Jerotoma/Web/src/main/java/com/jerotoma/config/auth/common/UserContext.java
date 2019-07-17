package com.jerotoma.config.auth.common;

import java.io.Serializable;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

import com.jerotoma.common.utils.StringUtility;

public class UserContext implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String username;
    private final Collection<GrantedAuthority> authorities;
    private String authority;
    

    public UserContext(String username, Collection<GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
        this.authority = getGrantedAuthority(authorities);
    }
    
    public static UserContext create(String username, Collection<GrantedAuthority> authorities) {
        if (StringUtility.isEmpty(username)) throw new IllegalArgumentException("Username is blank: " + username);
        return new UserContext(username, authorities);
    }

    public String getUsername() {
        return username;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    private String getGrantedAuthority(Collection<GrantedAuthority> authorities){
    	String authority = null;
    	if(authorities == null) {
    		return authority;
    	}
    	for(GrantedAuthority grant : authorities) {
    		return grant.getAuthority();
    	}
		return authority;
    }

	public String getAuthority() {
		return authority;
	}
}
