package com.jerotoma.api.controllers;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.exceptions.JwtExpiredTokenException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.http.HttpStatusAndMessageProcessor;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	String stringPattern = "duplicate key value violates unique constraint";
	
	@ExceptionHandler(value = { 
		IllegalArgumentException.class, 
		IllegalStateException.class, 
		RuntimeException.class,
		BadCredentialsException.class,
		SQLException.class,
		JwtExpiredTokenException.class,
		UsernameNotFoundException.class,
		FieldIsRequiredException.class,
		TransactionSystemException.class,
		DataIntegrityViolationException.class,
		JDataAccessException.class
	})
    protected ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
		HttpResponseEntity<Object> bodyOfResponse = new HttpResponseEntity<>();
		Map<String, Object> map = HttpStatusAndMessageProcessor.getStatusAndMessage(ex);
		HttpStatus httpStatus = (HttpStatus)map.get(SystemConstant.HTTP_STATUS); 
		String message = (String)map.get(SystemConstant.MESSAGE);
		
		bodyOfResponse.setSuccess(false);
		bodyOfResponse.setStatusCode(String.valueOf(httpStatus.value()));
		bodyOfResponse.setHttpStatus(httpStatus);
		
		if (message.contains(stringPattern)) {
			message = "The username you are trying to register with exists";
		}
		
		if(map.containsKey(SystemConstant.CONSTRAINT_NAME)) {
			String name = (String)map.get(SystemConstant.CONSTRAINT_NAME);
			message.replace("username", name);
		}
		bodyOfResponse.setMessage(message);
		return super.handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), httpStatus, request);
    }
}
