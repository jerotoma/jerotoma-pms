package com.jerotoma.config.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerotoma.common.utils.WebUtil;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.AuthMethodNotSupportedException;
import com.jerotoma.common.exceptions.JwtExpiredTokenException;
import com.jerotoma.common.errors.ErrorCode;
import com.jerotoma.common.errors.ErrorResponse;

@Component("authenticationFailureHandler")
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Autowired MessageSource messages;
    @Autowired LocaleResolver localeResolver;
    @Autowired ObjectMapper mapper;

    public AuthenticationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }   

	@Override
    public void onAuthenticationFailure(final HttpServletRequest request, 
    		final HttpServletResponse response, final AuthenticationException exception) 
    				throws IOException, ServletException {
        
    	final Locale locale = localeResolver.resolveLocale(request);
    	setUseForward(true);
        if(WebUtil.isAjax(request)) {
        	 setDefaultFailureUrl(EndPointConstants.API_AUTH_LOGIN_URL);
        	 ajaxRequestProcessor(response, exception);        	
        }else {
           	 setDefaultFailureUrl(EndPointConstants.APP_AUTH_LOGIN_URL);              
	         request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, processErrorMessage(exception, locale, false).get("errorMessage"));
	         super.onAuthenticationFailure(request, response, exception);
        }
     
    }
    
   protected void ajaxRequestProcessor(HttpServletResponse response, AuthenticationException e)
			throws IOException, JsonGenerationException, JsonMappingException {
    	
    	ErrorResponse  errorResponse =  (ErrorResponse)processErrorMessage(e, null, true).get("errorResponse");
		
    	Map<String, Object> map = new HashMap<>();
        map.put("success", false);
           	
		if (e instanceof BadCredentialsException) {
			errorResponse = ErrorResponse.of(e.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.METHOD_NOT_ALLOWED);       		
		} else if (e instanceof JwtExpiredTokenException) {
        	errorResponse = ErrorResponse.of("Token has expired", ErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED);        	
        } else if (e instanceof AuthMethodNotSupportedException) {
        	errorResponse = ErrorResponse.of(e.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.METHOD_NOT_ALLOWED);
        	
        }
		response.sendError(errorResponse.getStatus(),  errorResponse.getMessage());
   		return;
	}
   
   private Map<String, Object> processErrorMessage(final AuthenticationException exception, final Locale locale, boolean isAjax) {
		Map<String, Object> mapError = new HashMap<>();
		String errorMessage = messages.getMessage("message.badCredentials", null, locale);
	       
		if(!isAjax && locale != null) {
			if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
			    errorMessage = messages.getMessage("auth.message.disabled", null, locale);
			} else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
			    errorMessage = messages.getMessage("auth.message.expired", null, locale);
			} else if (exception.getMessage().equalsIgnoreCase("blocked")) {
			    errorMessage = messages.getMessage("auth.message.blocked", null, locale);
			}else {
				 errorMessage = exception.getMessage();
			}			
			mapError.put("errorMessage", errorMessage);
			
		  return mapError;
		} 
		
		ErrorResponse  errorResponse = null;
		
		if (exception instanceof BadCredentialsException) {
			errorResponse = ErrorResponse.of("Invalid username or password", ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED);
       } else if (exception instanceof JwtExpiredTokenException) {
       	errorResponse = ErrorResponse.of(messages.getMessage("auth.message.expired", null, locale), ErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED);        	
       } else if (exception instanceof AuthMethodNotSupportedException) {
       	errorResponse = ErrorResponse.of(exception.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED);          
       }else {
       	errorResponse = ErrorResponse.of(exception.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED);          
       }
		mapError.put("errorResponse", errorResponse);
		
		return mapError;
	}
}
