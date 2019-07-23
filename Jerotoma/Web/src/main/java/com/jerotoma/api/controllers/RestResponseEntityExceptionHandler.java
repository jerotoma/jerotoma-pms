package com.jerotoma.api.controllers;

import java.sql.SQLException;

import org.postgresql.util.PSQLException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jerotoma.common.users.AuthUser;
import com.jerotoma.exceptions.JwtExpiredTokenException;
import com.jerotoma.http.HttpResponseEntity;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	String stringPattern = "duplicate key value violates unique constraint \"username\"";
	
	@ExceptionHandler(value = { 
		IllegalArgumentException.class, 
		IllegalStateException.class, 
		RuntimeException.class,
		SQLException.class,
		JwtExpiredTokenException.class,
		UsernameNotFoundException.class
	})
    protected ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
		HttpResponseEntity<AuthUser> bodyOfResponse = new HttpResponseEntity<AuthUser>();
		HttpStatus httpStatus = getStatus(ex);
		String message = ex.getMessage();
		
		bodyOfResponse.setSuccess(false);
		bodyOfResponse.setStatusCode(String.valueOf(httpStatus.value()));
		bodyOfResponse.setHttpStatus(getStatus(ex));
		
		if (message.contains(stringPattern)) {
			message = "Sorry, the username you are trying to signup with already exists";
		}
		bodyOfResponse.setMessage(message);
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), httpStatus, request);
    }
	
	
	private HttpStatus getStatus(Exception ex) {
		HttpStatus status = null;
		if (ex instanceof HttpRequestMethodNotSupportedException) {
			status = HttpStatus.METHOD_NOT_ALLOWED;
		}
		else if (ex instanceof HttpMediaTypeNotSupportedException) {
			status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
		}
		else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			status = HttpStatus.NOT_ACCEPTABLE;			
		}
		else if (ex instanceof MissingPathVariableException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		else if (ex instanceof MissingServletRequestParameterException) {
			status = HttpStatus.BAD_REQUEST;		}
		else if (ex instanceof ServletRequestBindingException) {
			status = HttpStatus.BAD_REQUEST;
		}
		else if (ex instanceof ConversionNotSupportedException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;		}
		else if (ex instanceof TypeMismatchException) {
			status = HttpStatus.BAD_REQUEST;			
		}
		else if (ex instanceof HttpMessageNotReadableException) {
			status = HttpStatus.BAD_REQUEST;			
		}
		else if (ex instanceof HttpMessageNotWritableException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;			
		}
		else if (ex instanceof MethodArgumentNotValidException) {
			status = HttpStatus.BAD_REQUEST;			
		}
		else if (ex instanceof MissingServletRequestPartException) {
			status = HttpStatus.BAD_REQUEST;			
		}
		else if (ex instanceof BindException) {
			status = HttpStatus.BAD_REQUEST;			
		}
		else if (ex instanceof NoHandlerFoundException) {
			status = HttpStatus.NOT_FOUND;			
		}
		else if (ex instanceof AsyncRequestTimeoutException) {
			status = HttpStatus.SERVICE_UNAVAILABLE;
			
		}
		else if (ex instanceof SQLException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;			
		}
		else if (ex instanceof PSQLException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;			
		}
		else if (ex instanceof JwtExpiredTokenException) {
			status = HttpStatus.UNAUTHORIZED;			
		}
		else if (ex instanceof DuplicateKeyException) {
			status = HttpStatus.OK;			
		}
		else if (ex instanceof UsernameNotFoundException) {
			status = HttpStatus.OK;			
		}
		else {
			status = HttpStatus.NOT_FOUND;
		}	
		
		return status;
		
	}

}
