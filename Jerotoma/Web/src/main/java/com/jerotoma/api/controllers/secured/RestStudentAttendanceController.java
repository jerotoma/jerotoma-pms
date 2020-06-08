package com.jerotoma.api.controllers.secured;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.http.HttpResponseEntity;

@RestController
@RequestMapping(EndPointConstants.REST_STUDENT_ATTENDANCE_CONTROLLER.BASE)
public class RestStudentAttendanceController extends RestAttendanceController {

	@Override
	public HttpResponseEntity<Object> index(Authentication auth, String search, Integer page, Integer pageSize,
			String fieldName, String orderby) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpResponseEntity<Object> show(Authentication auth, Integer entityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpResponseEntity<Object> update(Authentication auth, Integer entityId, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpResponseEntity<Object> create(Authentication auth, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpResponseEntity<Object> edit(Authentication auth, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpResponseEntity<Object> delete(Authentication auth, Integer entityId) {
		// TODO Auto-generated method stub
		return null;
	}

}
