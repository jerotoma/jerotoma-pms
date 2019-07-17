package com.jerotoma.config.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.jerotoma.common.utils.WebUtil;
import com.jerotoma.config.auth.common.AuthProcessor;
import com.jerotoma.services.cookies.CookieService;


public class CustomLogoutSuccessHandler extends	SimpleUrlLogoutSuccessHandler{
	
	@Autowired CookieService cookieService;
	@Autowired AuthProcessor authProcessor;
	
	RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	 
    @Override
    public void onLogoutSuccess(HttpServletRequest request, 
    		HttpServletResponse response, Authentication authentication) 
    									throws IOException, ServletException {
    	//System.out.println("User Successfully Logout");
    	
    	HttpSession session = request.getSession(false);
    	if(session != null) {            	 	
            session.invalidate();        	       
        }
    	
        if(WebUtil.isAjax(request)) {
        	authProcessor.deleteAllTokenCookie(request, response, cookieService);
            
        }else {
	        response.setStatus(HttpServletResponse.SC_OK);
	    	redirectStrategy.sendRedirect(request, response, "/");	    	
        }
        super.onLogoutSuccess(request, response, authentication);
    }

}
