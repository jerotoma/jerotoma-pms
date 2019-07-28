package com.jerotoma.common.http;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.postgresql.util.PSQLException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.jerotoma.common.exceptions.JwtExpiredTokenException;

public class HttpStatusProcessor {
	
	
	public static HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request
				.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		try {
			return HttpStatus.valueOf(statusCode);
		}
		catch (Exception ex) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
	
	public static HttpStatus getStatus(Exception ex) {
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
		else if (ex instanceof BadCredentialsException) {
			status = HttpStatus.METHOD_NOT_ALLOWED;			
		}
		else {
			status = HttpStatus.NOT_FOUND;
		}	
		
		return status;
		
	}
}
