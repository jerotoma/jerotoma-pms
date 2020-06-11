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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.api.controllers.Controller;
import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AttendanceConstant;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.common.models.academic.Class;
import com.jerotoma.common.models.attendances.ClassAttendance;
import com.jerotoma.common.models.attendances.ClassAttendance.ClassAttendanceParam;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.validators.AttendanceValidator;
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
	
	@GetMapping("/classes")	
	@ResponseBody
	public HttpResponseEntity<Object> getClassAttendances(Authentication auth, 
			@RequestParam(value="searchTerm", required=false) String search,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {		
		
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(page, pageSize, fieldName, orderby);
		try {
			map = assemblerClassAttendanceService.loadMapList(queryParam);		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		instance.setData(map);
		instance.setHttpStatus(HttpStatus.OK);
		return instance;
	}
	
	@GetMapping("/classes/{classId}")	
	@ResponseBody
	public HttpResponseEntity<Object> getClassAttendance(Authentication auth, @PathVariable(name="classId") Integer classId) {		
		
		this.securityCheckAccessByRoles(auth);
		
		try {
			instance.setSuccess(true);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			instance.setData(assemblerClassAttendanceService.findObject(classId));
			instance.setHttpStatus(HttpStatus.OK);
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		
		return instance;
	}
	
	@PostMapping("/classes")
	@ResponseBody
	public HttpResponseEntity<Object> createClassAttendance(Authentication auth,  @RequestBody Map<String, Object> params) {
		this.logRequestDetail("POST : " + EndPointConstants.REST_ATTENDANCE_CONTROLLER.BASE + "/classes");
		this.proccessLoggedInUser(auth);
		this.securityCheckAccessByRoles(auth);
		
		List<String> requiredFields = new ArrayList<>(Arrays.asList(
						AttendanceConstant.CLASS_ID,
						AttendanceConstant.ACADEMIC_YEAR_ID,
						AttendanceConstant.ATTENDANCE_DATE));
		ClassAttendanceParam classAttendanceParam = AttendanceValidator.validateClassAttendance(params, requiredFields);		
		Date today = CalendarUtil.getTodaysDate();	
		ClassAttendance classAttendance = null;	
		
		try {
			AcademicYear academicYear = academicYearService.findObject(classAttendanceParam.getAcademicYearId());
			Class mClass = classService.findObject(classAttendanceParam.getClassId());			
			classAttendance = new ClassAttendance(mClass, academicYear, classAttendanceParam.getAttendanceDate(), authUser.getId(), today, today);
			
			instance.setData(classAttendanceService.createObject(classAttendance));
			instance.setSuccess(true);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);	
		}
		instance.setHttpStatus(HttpStatus.OK);	
		return instance;
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
