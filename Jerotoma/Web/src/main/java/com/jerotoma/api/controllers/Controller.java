package com.jerotoma.api.controllers;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.jerotoma.common.http.HttpResponseEntity;

public interface Controller {
	public HttpResponseEntity<Object> index(Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby
			);
	
	public HttpResponseEntity<Object> show(
			Authentication auth, 
			@PathVariable("entityId") Integer entityId);
	
	public HttpResponseEntity<Object> update(
			Authentication auth, 
			@PathVariable("entityId") Integer entityId, 
			@RequestBody Map<String, Object> params);
	
	public HttpResponseEntity<Object> create(
			Authentication auth,
			@RequestBody Map<String, Object> params);
	
	public HttpResponseEntity<Object> edit(
			Authentication auth,
			@RequestBody Map<String, Object> params); 
	
	
	public HttpResponseEntity<Object> delete(
			Authentication auth, 
			@PathVariable("entityId") Integer entityId);
}
