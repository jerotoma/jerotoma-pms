package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.constants.CompletionOrderConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.CompletionOrder;
import com.jerotoma.common.utils.validators.CompletionOrderValidator;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.services.academic.CompletionOrderService;

@RestController
@RequestMapping(EndPointConstants.REST_COMPLETION_ORDER_CONTROLLER.BASE)
public class RestCompletionOrderController  extends BaseController {
	
	@Autowired CompletionOrderService completionOrderService;
		
		
	@GetMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getCompletionOrders(Authentication auth)  {
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_COURSE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		try {
			response.setData(completionOrderService.loadList(null));			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	
	@GetMapping(value = {"/{completionOrderId}", "/{completionOrderId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getCompletionOrder(Authentication auth, @PathVariable("completionOrderId") Integer completionOrderId) {
	
		this.logRequestDetail("GET : " + EndPointConstants.REST_COURSE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		try {
			response.setData(completionOrderService.findObject(completionOrderId));		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@PostMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> createCompletionOrder(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		this.logRequestDetail("POST : " + EndPointConstants.REST_COURSE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		this.userSecurityClearance.checkGeneralEntityCreationPermission();
		
		List<String> requiredFields = new ArrayList<>(
				Arrays.asList(
						CompletionOrderConstant.NAME,
						CompletionOrderConstant.COMPLETION_ORDER));
		
		CompletionOrder completionOrder = CompletionOrderValidator.validate(params, requiredFields);
		UserVO user = getAuthenticatedUserVO();
		try {	
			completionOrder.setUpdatedBy(user.getUserId());
			completionOrder = completionOrderService.createObject(completionOrder);			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(completionOrder);
		return response;
	}

	protected HttpResponseEntity<Object> showCompletionOrder() {
		
		return null;
	}


	@PutMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> editCompletionOrder(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		this.logRequestDetail("PUT : " + EndPointConstants.REST_COURSE_CONTROLLER.BASE);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		this.userSecurityClearance.checkGeneralEntityModificationPermission();
	
		List<String> requiredFields = new ArrayList<>(
				Arrays.asList(
						CompletionOrderConstant.NAME,
						CompletionOrderConstant.COMPLETION_ORDER,
						CompletionOrderConstant.ID));
		
		CompletionOrder completionOrder = CompletionOrderValidator.validate(params, requiredFields);
		UserVO user = getAuthenticatedUserVO();
		try {	
			completionOrder.setUpdatedBy(user.getUserId());
			completionOrder.setUpdatedOn(today);
			completionOrder = completionOrderService.updateObject(completionOrder);			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(completionOrder);
		return response;
	}

	@DeleteMapping(value = {"/{completionOrderId}", "/{completionOrderId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteCompletionOrder(Authentication auth, @PathVariable("completionOrderId") Integer completionOrderId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		this.logRequestDetail("DELETE : " + EndPointConstants.REST_COURSE_CONTROLLER.BASE + "/" + completionOrderId);
		this.securityCheckAccessByRoles(auth);
		this.proccessLoggedInUser(auth);
		
		CompletionOrder completionOrder;
		
		try {
			completionOrder = completionOrderService.findObject(completionOrderId);	
			if (completionOrder == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = completionOrderService.deleteObject(completionOrder);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}

}
