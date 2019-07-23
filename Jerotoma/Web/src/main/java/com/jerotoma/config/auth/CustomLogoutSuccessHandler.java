package com.jerotoma.config.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerotoma.config.auth.common.AuthProcessor;
import com.jerotoma.config.constants.SecurityConstant;
import com.jerotoma.services.cookies.CookieService;

@Component
public class CustomLogoutSuccessHandler extends	SimpleUrlLogoutSuccessHandler{
	
	@Autowired CookieService cookieService;
	@Autowired AuthProcessor authProcessor;
	@Autowired ObjectMapper mapper;
	
	RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	 
    @Override
    public void onLogoutSuccess(HttpServletRequest request, 
    		HttpServletResponse response, Authentication authentication) 
    									throws IOException, ServletException {
      	
    	Map<String, Object> tokenMap = new HashMap<>();
    	HttpSession session = request.getSession(false);
    	if(session != null) {            	 	
            session.invalidate();        	       
        }
    	    	
    	authProcessor.deleteAllTokenCookie(request, response, cookieService);
    	response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(SecurityConstant.AUTHENTICATION_HEADER_NAME, "");
        tokenMap.put("success", true);
        mapper.writeValue(response.getWriter(), tokenMap);
        super.onLogoutSuccess(request, response, authentication);
    }

}
