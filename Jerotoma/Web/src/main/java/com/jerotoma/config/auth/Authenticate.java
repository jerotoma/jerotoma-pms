package com.jerotoma.config.auth;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.jerotoma.common.models.users.AuthUser;

public class Authenticate {
	
	private static String SPRING_SECURITY_CONTEXT_KEY  = "SPRING_SECURITY_CONTEXT";
	@Autowired static AuthenticationManager authManager;
	
	public static Authentication attemptLogin(HttpServletRequest request, String username, String password) throws BadCredentialsException{
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
	    Authentication auth = authManager.authenticate(token);
	    
	     if(auth.isAuthenticated()) {
	        SecurityContext securityContext = SecurityContextHolder.getContext();
	 	    securityContext.setAuthentication(auth);
	 	    HttpSession session = request.getSession(true);
	 	    session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);
	 	    
	 	    return securityContext.getAuthentication();
	     }
	     return null;     
	    
	}
	
	public static void modifyAuthority(Principal principal, AuthUser user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UsernamePasswordAuthenticationToken currentUser = (UsernamePasswordAuthenticationToken)principal;
		
		Authentication newAuth =  new UsernamePasswordAuthenticationToken((User)currentUser.getPrincipal(), auth.getCredentials(), user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(newAuth);		
	}

}
