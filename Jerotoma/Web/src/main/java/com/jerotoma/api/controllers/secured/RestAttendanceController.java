package com.jerotoma.api.controllers.secured;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.api.controllers.Controller;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.services.assemblers.attendances.AssemblerClassAttendanceService;
import com.jerotoma.services.attendances.ClassAttendanceService;
import com.jerotoma.services.courses.AcademicYearService;
import com.jerotoma.services.courses.ClassService;

@RestController
@RequestMapping(EndPointConstants.REST_ATTENDANCE_CONTROLLER.BASE)
public class RestAttendanceController extends BaseController implements Controller {
	
	@Autowired ClassService classService;
	@Autowired AcademicYearService academicYearService;
	@Autowired ClassAttendanceService classAttendanceService;
	@Autowired AssemblerClassAttendanceService assemblerClassAttendanceService;

	@GetMapping(value = {"", "/"})
	@Override
	public HttpResponseEntity<Object> index(Authentication auth, String search, Integer page, Integer pageSize,
			String fieldName, String orderby) {
		
		return null;
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
