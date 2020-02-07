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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.http.HttpStatusAndMessageProcessor;
import com.jerotoma.common.utils.WebUtil;

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
	     
	   	Map<String, Object> map = HttpStatusAndMessageProcessor.getStatusAndMessage(e);
		HttpStatus httpStatus = (HttpStatus)map.get(SystemConstant.HTTP_STATUS); 
		String message = (String)map.get(SystemConstant.MESSAGE);
    	response.sendError(httpStatus.value(), message);
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
			
		} 
	  return mapError;
	}
}
