package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.StreamConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.Stream;
import com.jerotoma.common.models.users.User;
import com.jerotoma.common.utils.validators.StreamValidator;
import com.jerotoma.config.auth.interfaces.IAuthenticationFacade;
import com.jerotoma.services.academic.StreamService;
import com.jerotoma.services.users.AuthUserService;

@RestController
@RequestMapping(EndPointConstants.REST_STREAM_CONTROLLER.BASE)
public class RestStreamController  extends BaseController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired StreamService streamService;
	
	@Autowired IAuthenticationFacade authenticationFacade;
	@Autowired AuthUserService authUserService;
	
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getStreams(Authentication auth) {
		
		logger.debug("getStreams : [model] : {}");			
		this.securityCheckAccessByRoles(auth);				
		try {
			response.setData(streamService.loadList(null));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/paginated")
	@ResponseBody
	protected HttpResponseEntity<Object> getStreamPaginated(Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		logger.debug("getStreams : [model] : {}");
		
		QueryParam queryParam = this.setParams(page, pageSize, fieldName, orderby);
		this.securityCheckAccessByRoles(auth);			
				
		try {
			map = streamService.loadMapList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(map);
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}

	@PostMapping
	@ResponseBody
	protected HttpResponseEntity<Object> createStream(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		this.securityCheckAccessByRoles(auth);	
		List<String> requiredFields;
		User authUser = authUserService.loadUserByUsername(userContext.getUsername());
		requiredFields = new ArrayList<>(
				Arrays.asList(
						StreamConstant.STREAM_NAME,
						StreamConstant.STREAM_DESCRIPTION,
						StreamConstant.STREAM_CODE));
		
		Stream stream = StreamValidator.validate(params, requiredFields);
		stream.setUpdatedBy(authUser.getId());
		try {
			stream = streamService.createObject(stream);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(stream);
		return response;
	}

	@PutMapping
	@ResponseBody
	protected HttpResponseEntity<Object> editStream(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
	
		this.securityCheckAccessByRoles(auth);	
		List<String> requiredFields = new ArrayList<>(
				Arrays.asList(
						StreamConstant.STREAM_NAME,
						StreamConstant.STREAM_DESCRIPTION,
						StreamConstant.STREAM_CODE));
		User authUser = authUserService.loadUserByUsername(userContext.getUsername());
		
		Stream stream = StreamValidator.validate(params, requiredFields);
		stream.setUpdatedBy(authUser.getId());
		
		try {
			stream = streamService.updateObject(stream);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(stream);
		return response;
	}

	@GetMapping("/{streamId}")
	@ResponseBody
	protected HttpResponseEntity<Object> getStream(Authentication auth, @PathVariable("streamId") Integer streamId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		this.securityCheckAccessByRoles(auth);	
		try {
			instance.setData(streamService.findObject(streamId));			
			instance.setSuccess(true);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}
	
	@DeleteMapping("/{streamId}")
	@ResponseBody
	protected HttpResponseEntity<Object> deleteStream(Authentication auth, @PathVariable("streamId") Integer streamId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		this.securityCheckAccessByRoles(auth);		
		Stream stream;
		
		try {
			stream = streamService.findObject(streamId);	
			if (stream == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = streamService.deleteObject(stream);
			instance.setData(isDeleted);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}
}
