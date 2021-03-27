package com.jerotoma.api.controllers.secured;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.api.controllers.Controller;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.DashboardCount;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.services.assemblers.AssemblerParentService;
import com.jerotoma.services.assemblers.AssemblerStaffService;
import com.jerotoma.services.assemblers.AssemblerStudentService;
import com.jerotoma.services.assemblers.AssemblerTeacherService;

@RestController
@RequestMapping(EndPointConstants.REST_DASHBOARD_CONTROLLER.BASE)
public class RestDashboardController extends BaseController implements Controller {
	
	@Autowired AssemblerTeacherService assemblerTeacherService;
	@Autowired AssemblerStudentService assemblerStudentService;
	@Autowired AssemblerParentService assemblerParentService;
	@Autowired AssemblerStaffService assemblerStaffService;
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	@Override
	public HttpResponseEntity<Object> index(
							Authentication auth, 
							String search, 
							Integer page, 
							Integer pageSize,
							String fieldName, 
							String orderby) {
		
		this.logRequestDetail("GET : " + EndPointConstants.REST_DASHBOARD_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
							
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setData(map);
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	

	@GetMapping(value = {"/counters", "/counters"})
	@ResponseBody
	public HttpResponseEntity<Object> getCounters(Authentication auth) {
		this.logRequestDetail("GET : " + EndPointConstants.REST_DASHBOARD_CONTROLLER.BASE);
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		UserVO user = getAuthenticatedUserVO();
		try {
			DashboardCount dashboardCount = new DashboardCount();
			dashboardCount.setTeacherCount(assemblerTeacherService.countObject().intValue());
			
			switch (user.getUserType()) {
			case TEACHER:
				dashboardCount.setClassCount(assemblerTeacherService.countTeacherClasses(user.getId()));
				break;
			default:
				break;
				
			}
			
			dashboardCount.setStudentCount(assemblerStudentService.countObject().intValue());
			dashboardCount.setParentCount(assemblerParentService.countObject().intValue());
			dashboardCount.setStaffCount(assemblerStaffService.countObject().intValue());			
			response.setData(dashboardCount);				
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
								
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}

	@Override
	public HttpResponseEntity<Object> show(Authentication auth, Integer entityId) {
		
		return null;
	}

	@Override
	public HttpResponseEntity<Object> update(Authentication auth, Integer entityId, Map<String, Object> params) {
		
		return null;
	}

	@Override
	public HttpResponseEntity<Object> create(Authentication auth, Map<String, Object> params) {
		
		return null;
	}

	@Override
	public HttpResponseEntity<Object> edit(Authentication auth, Map<String, Object> params) {
		
		return null;
	}

	@Override
	public HttpResponseEntity<Object> delete(Authentication auth, Integer entityId) {
		
		return null;
	}

}
