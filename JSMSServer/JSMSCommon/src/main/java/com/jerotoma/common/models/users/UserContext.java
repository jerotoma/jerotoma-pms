package com.jerotoma.common.models.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.jerotoma.common.utils.StringUtility;

public class UserContext implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String username;
    private final Collection<GrantedAuthority> authorities;
    private String[] currentAuthorities;
    

    public UserContext(String username, Collection<GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
        this.currentAuthorities = getGrantedAuthority(authorities);
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
    
    private String[] getGrantedAuthority(Collection<GrantedAuthority> authorities){
    	
    	
    	String[] currentAuthorities =  new String[]{};
    	if(authorities == null) {
    		return currentAuthorities;
    	}
    	ArrayList<GrantedAuthority> newAuthorities = new ArrayList<>(authorities);
    	currentAuthorities =  new String[newAuthorities.size()];
    	
    	for( int i = 0; i < newAuthorities.size(); i++ ) {    		
    		currentAuthorities[i] = newAuthorities.get(i).getAuthority();    		
    	}
		return currentAuthorities;
    }

	public List<String> getCurrentAuthorities() {
		List<String> roles = new ArrayList<>();
		for(String role: currentAuthorities) {
			roles.add(role);
		}
		return roles;
	}
}
