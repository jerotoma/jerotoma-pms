package com.jerotoma.common.http;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.exceptions.JwtExpiredTokenException;

public class HttpStatusAndMessageProcessor {
	
	
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
	
	public static Map<String, Object> getStatusAndMessage(Exception ex) {
		HttpStatus status = null;
		Map<String, Object> map = new HashMap<>(); {
		
		if (ex instanceof HttpRequestMethodNotSupportedException) {
			status = HttpStatus.METHOD_NOT_ALLOWED;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof HttpMediaTypeNotSupportedException) {
			status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
			
		}
		else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			status = HttpStatus.NOT_ACCEPTABLE;	
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof MissingPathVariableException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof MissingServletRequestParameterException) {
			status = HttpStatus.BAD_REQUEST;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof ServletRequestBindingException) {
			status = HttpStatus.BAD_REQUEST;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof ConversionNotSupportedException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof TypeMismatchException) {
			status = HttpStatus.BAD_REQUEST;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof HttpMessageNotReadableException) {
			status = HttpStatus.BAD_REQUEST;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof HttpMessageNotWritableException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof MethodArgumentNotValidException) {
			status = HttpStatus.BAD_REQUEST;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof MissingServletRequestPartException) {
			status = HttpStatus.BAD_REQUEST;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof BindException) {
			status = HttpStatus.BAD_REQUEST;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof NoHandlerFoundException) {
			status = HttpStatus.NOT_FOUND;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof AsyncRequestTimeoutException) {
			status = HttpStatus.SERVICE_UNAVAILABLE;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
			
		}
		else if (ex instanceof SQLException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof PSQLException) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof JwtExpiredTokenException) {
			status = HttpStatus.UNAUTHORIZED;			
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof DuplicateKeyException) {
			status = HttpStatus.OK;	
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof UsernameNotFoundException) {
			status = HttpStatus.FORBIDDEN;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof BadCredentialsException) {
			status = HttpStatus.FORBIDDEN;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}
		else if (ex instanceof DataIntegrityViolationException) {
			status = HttpStatus.CONFLICT;
			DataIntegrityViolationException dataEx = (DataIntegrityViolationException)ex;
			Throwable thr = dataEx.getCause();
			
			extractMessage(thr, map);			
			map.put(SystemConstant.HTTP_STATUS, status);
			
		}
		else {
			status = HttpStatus.NOT_FOUND;
			map.put(SystemConstant.HTTP_STATUS, status);
			map.put(SystemConstant.MESSAGE, ex.getMessage());
		}		
		return map;
		}
	}

	private static void extractMessage(Throwable thr, Map<String, Object> map) {
		boolean hasMessage = false;
		
		if(thr instanceof ConstraintViolationException) {			
			ConstraintViolationException cv = (ConstraintViolationException)thr;
			map.put(SystemConstant.CONSTRAINT_NAME, cv.getConstraintName());
			Throwable cvEx = cv.getCause();				
			if(cvEx instanceof BatchUpdateException) {						
				BatchUpdateException batchUpdateEx = (BatchUpdateException)cvEx;
				Throwable throwCVEX = batchUpdateEx.getCause();					
				if(throwCVEX instanceof PSQLException) {
					PSQLException psqlException = (PSQLException)throwCVEX;
					map.put(SystemConstant.MESSAGE, psqlException.getMessage());
					hasMessage = true;
				} 
			} 			
		} 
		
		if (!hasMessage) {
			map.put(SystemConstant.MESSAGE, thr.getMessage());
		}
	}
}
