package com.jerotoma.config.auth;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerotoma.common.constants.SecurityConstant;
import com.jerotoma.common.users.AuthUser;
import com.jerotoma.common.utils.WebUtil;
import com.jerotoma.config.auth.common.AuthProcessor;
import com.jerotoma.config.auth.common.UserContext;
import com.jerotoma.config.auth.interfaces.IAuthenticationFacade;
import com.jerotoma.config.auth.tokens.AuthToken;
import com.jerotoma.config.auth.tokens.JwtTokenFactory;
import com.jerotoma.services.cookies.CookieService;
import com.jerotoma.services.users.AuthUserService;


@Component("appAuthenticationSuccessHandler")
public class AppAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final JwtTokenFactory tokenFactory;
    private final AuthUserService userservice;    
    private final ObjectMapper mapper;
    private final IAuthenticationFacade authFacade;
    private final AuthProcessor authProcessor;
    private final CookieService cookieService;
   
    @Autowired
    public AppAuthenticationSuccessHandler(
    		ObjectMapper mapper, 
    		AuthUserService userservice,
    		JwtTokenFactory tokenFactory,
    		IAuthenticationFacade authFacade,
    		AuthProcessor authProcessor,
    		CookieService cookieService
    		) {
        this.mapper = mapper;
        this.userservice = userservice;
        this.tokenFactory = tokenFactory;
        this.authFacade = authFacade;
        this.authProcessor = authProcessor;
        this.cookieService = cookieService;
        
    }
        
    @Override
    public void onAuthenticationSuccess(
    		HttpServletRequest request, 
    		HttpServletResponse response, 
    		final Authentication authentication) throws IOException {
       	
    	if(authentication.isAuthenticated()) { 
       		UserContext userContext = authFacade.getUserContext(authentication);
       		AuthUser auth =  userservice.loadUserByUsername(userContext.getUsername());
       		if(WebUtil.isContentTypeJson(request)) {
            	jwtSuccessProcessor(request, response, authentication, auth);
            }else{
            	 handle(request, response, authentication);
            }
    	} 
        clearAuthenticationAttributes(request);
    }
    
   protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        final String targetUrl = determineTargetUrl(authentication, response);
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication, HttpServletResponse response) {
        
    	final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	         if(authorities.isEmpty()) {
	        	 throw new IllegalStateException();
	        }	         
	   return "/dashboard";        
    }
 
    protected void clearAuthenticationAttributes(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
    
	public void jwtSuccessProcessor(
								HttpServletRequest request, 
								HttpServletResponse response, 
								Authentication authentication,  
								AuthUser auth) throws IOException, JsonGenerationException, JsonMappingException {
		
		Map<String, Object> tokenMap = new HashMap<>();
		
		UserContext userContext = authFacade.getUserContext(authentication);
        AuthToken authToken = authProcessor.extractAuthToken(userContext, tokenFactory);
        
        cookieService.createCookie(response, SecurityConstant.JWT_COOKIE_AUTH_REFRESH_TOKEN,  authToken.getRefreshToken(), -1);
		response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader(SecurityConstant.AUTHENTICATION_HEADER_NAME, SecurityConstant.HEADER_PREFIX + authToken.getToken());
        
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + request.getRequestURL());
            return;
        }
      
        tokenMap.put("success", true);
        mapper.writeValue(response.getWriter(), tokenMap);
       clearAuthenticationAttributes(request);
	}

}