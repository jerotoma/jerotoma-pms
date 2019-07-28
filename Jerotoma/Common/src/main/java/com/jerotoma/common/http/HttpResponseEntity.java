package com.jerotoma.common.http;

import java.io.Serializable;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class HttpResponseEntity<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private T data;	
	private Map<String, Object> mapData;
	private String message;
	private String statusCode;
	private Boolean success;
	private HttpStatus httpStatus;
	
	public static HttpResponseEntity<Object>  getInstance(){
		return new HttpResponseEntity<Object>();
	}
	
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public Boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public Map<String, Object> getMapData() {
		return mapData;
	}
	
	public void setMapData(Map<String, Object> mapData) {
		this.mapData = mapData;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
}
