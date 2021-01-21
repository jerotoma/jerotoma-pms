package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.ScoreStandingConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.ScoreStanding;
import com.jerotoma.common.models.users.User;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.validators.ScoreStandingValidator;
import com.jerotoma.config.auth.interfaces.IAuthenticationFacade;
import com.jerotoma.services.academic.ScoreStandingService;
import com.jerotoma.services.users.AuthUserService;

@RestController
@RequestMapping(EndPointConstants.REST_SCORE_STANDING_CONTROLLER.BASE)
public class RestScoreStandingController extends BaseController {
	

	@Autowired ScoreStandingService scoreStandingService;
	@Autowired IAuthenticationFacade authenticationFacade;
	@Autowired AuthUserService authUserService;
	
	
	@GetMapping
	@ResponseBody
	protected HttpResponseEntity<Object> getScoreStandings(Authentication auth) {		
			
		this.securityCheckAccessByRoles(auth);				
		try {
			response.setData(scoreStandingService.loadList(null));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value = {"/paginated", "/paginated/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getScoreStandingPaginated(Authentication auth,
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
	
		QueryParam queryParam = this.setParams(page, pageSize, fieldName, orderby);
		this.securityCheckAccessByRoles(auth);			
				
		try {
			map = scoreStandingService.loadMapList(queryParam);		
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
	protected HttpResponseEntity<Object> createScoreStanding(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		this.securityCheckAccessByRoles(auth);	
		List<String> requiredFields;
		User authUser = authUserService.loadUserByUsername(userContext.getUsername());
		requiredFields = new ArrayList<>(
				Arrays.asList(
						ScoreStandingConstant.MAX_SCORE,
						ScoreStandingConstant.MIN_SCORE,
						ScoreStandingConstant.STANDING_COLOR,
						ScoreStandingConstant.STANDING));
		
		ScoreStanding scoreStanding = ScoreStandingValidator.validate(params, requiredFields);
		scoreStanding.setUpdatedBy(authUser.getId());
		
		try {
			scoreStanding = scoreStandingService.createObject(scoreStanding);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(scoreStanding);
		return response;
	}

	protected HttpResponseEntity<Object> showSchoolClass() {
		
		return null;
	}


	@PutMapping
	@ResponseBody
	protected HttpResponseEntity<Object> editschoolClass(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
	
		this.securityCheckAccessByRoles(auth);	
		List<String> requiredFields = new ArrayList<>(
				Arrays.asList(
						ScoreStandingConstant.ID,
						ScoreStandingConstant.MAX_SCORE,
						ScoreStandingConstant.MIN_SCORE,
						ScoreStandingConstant.STANDING_COLOR,
						ScoreStandingConstant.STANDING));
		
		ScoreStanding scoreStanding = ScoreStandingValidator.validate(params, requiredFields);
		User authUser = authUserService.loadUserByUsername(userContext.getUsername());		
		scoreStanding.setUpdatedBy(authUser.getId());
		Date cal = CalendarUtil.getTodaysDate();
		scoreStanding.setUpdatedOn(cal);
		
		try {
			scoreStanding = scoreStandingService.updateObject(scoreStanding);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(scoreStanding);
		return response;
	}

	@DeleteMapping("/{scoreStandingId}")
	@ResponseBody
	protected HttpResponseEntity<Object> deleteSchoolClass(Authentication auth, @PathVariable("scoreStandingId") Integer scoreStandingId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		this.securityCheckAccessByRoles(auth);		
		ScoreStanding scoreStanding;
		
		try {
			scoreStanding = scoreStandingService.findObject(scoreStandingId);	
			if (scoreStanding == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = scoreStandingService.deleteObject(scoreStanding);
			instance.setSuccess(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}

}
