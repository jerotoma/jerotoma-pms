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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jerotoma.api.controllers.BaseController;
import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.EndPointConstants;
import com.jerotoma.common.constants.MeetingTimeConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.http.HttpResponseEntity;
import com.jerotoma.common.schedules.MeetingTime;
import com.jerotoma.common.schedules.WorkDay;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.common.utils.validators.MeetingTimeValidator;
import com.jerotoma.common.viewobjects.MeetingTimeVO;
import com.jerotoma.services.assemblers.academic.AssemblerMeetingTimeService;
import com.jerotoma.services.schedules.MeetingTimeService;
import com.jerotoma.services.schedules.WorkDayService;

@RestController
@RequestMapping(EndPointConstants.REST_MEETING_TIME_CONTROLLER.BASE)
public class RestMeetingTimeController extends BaseController {
	
	@Autowired WorkDayService workDayService;
	@Autowired MeetingTimeService meetingTimeService;
	@Autowired AssemblerMeetingTimeService assemblerMeetingTimeService;
	
	@GetMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getMeetingTimes(Authentication auth) {
		
		this.securityCheckAccessByRoles(auth);
		this.logRequestDetail("GET : " + EndPointConstants.REST_MEETING_TIME_CONTROLLER.BASE);
		
		try {
			response.setData(assemblerMeetingTimeService.findAllMeetingTimes());
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
	protected HttpResponseEntity<Object> getMeetingTimesPaginated(
			Authentication auth,			
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize,
			@RequestParam(value="fieldName", required=false) String fieldName,
			@RequestParam(value="orderby", required=false) String orderby) {
		
		this.securityCheckAccessByRoles(auth);
		QueryParam queryParam = this.setParams(page, pageSize, fieldName, orderby);
		this.logRequestDetail("GET : " + EndPointConstants.REST_ACADEMIC_YEAR_CONTROLLER.BASE);
		
		try {
			response.setData(assemblerMeetingTimeService.loadMapList(queryParam));
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}	
				
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));		
		response.setHttpStatus(HttpStatus.OK);
		return response;
	}
	
	
	@GetMapping(value = {"/{meetingTimeId}", "/{meetingTimeId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> getMeetingTime(Authentication auth, @PathVariable("meetingTimeId") Integer meetingTimeId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();			
		this.securityCheckAdminAccess(auth, "GET");
		
		MeetingTimeVO meetingTime;		
		try {
			meetingTime = assemblerMeetingTimeService.findObject(meetingTimeId);	
			if (meetingTime == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			
			instance.setSuccess(true);
			instance.setData(meetingTime);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}

	
	@PostMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> createMeetingTime(
			Authentication auth, 
			@RequestBody Map<String, Object> params) throws JDataAccessException {
		
		List<String> requiredFields;
		this.securityCheckAccessByRoles(auth);
		this.logRequestDetail("POST : " + EndPointConstants.REST_ACADEMIC_YEAR_CONTROLLER.BASE);
			
		requiredFields = new ArrayList<>(
				Arrays.asList(
						MeetingTimeConstant.TIME,
						MeetingTimeConstant.END_TIME,
						MeetingTimeConstant.START_TIME,
						MeetingTimeConstant.WORK_DAY_ID));
		
		MeetingTime meetingTime = MeetingTimeValidator.validate(params, requiredFields);		
		try {
			WorkDay workDay = workDayService.findObject(meetingTime.getWorkDayId());
			if (workDay == null) {
				throw new FieldIsRequiredException("Work Day is required to continue");
			}
			checkForMeetingTimeOverlapException(workDay, meetingTime);
			
			meetingTime.setWorkDay(workDay);
			response.setData(meetingTimeService.createObject(meetingTime));		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		response.setSuccess(true);
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		return response;
	}


	private void checkForMeetingTimeOverlapException(WorkDay workDay, MeetingTime meetingTime) throws SQLException {
		List<MeetingTimeVO> meetingTimes = assemblerMeetingTimeService.findAllOverapsMeetingTimesByWorkDay(workDay.getId(),  meetingTime.getStartTime(),  meetingTime.getEndTime());
		if (meetingTimes != null && !meetingTimes.isEmpty()) {
			
			StringBuilder builder = new StringBuilder()
					.append("<h5 class='text-light'>")
					.append("The meeting time overlaps with following times :")
					.append("</h5>")
					.append("<ul class='list-group'>");
			
			for (MeetingTimeVO mt : meetingTimes) {
				builder.append("<li class='list-group-item'>")
					.append(StringUtility.capitalize(mt.getWorkDay().getDay().toString()))
					.append(" : ")
					.append(mt.getTime())
					.append("</li>");
			}
			
			builder.append("</ul>");
			throw new RuntimeException(builder.toString());
		}
	}

	@PutMapping(value = {"", "/"})
	@ResponseBody
	protected HttpResponseEntity<Object> editMeetingTime(
		Authentication auth, 
		@RequestBody Map<String, Object> params) throws JDataAccessException {
	
		List<String> requiredFields;
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();
			
		this.securityCheckAdminAccess(auth, "edit");
		
		requiredFields = new ArrayList<>(
				Arrays.asList(
						MeetingTimeConstant.ID,
						MeetingTimeConstant.TIME,
						MeetingTimeConstant.END_TIME,
						MeetingTimeConstant.START_TIME,
						MeetingTimeConstant.WORK_DAY_ID));
		
		MeetingTime meetingTime = MeetingTimeValidator.validate(params, requiredFields);
		
		try {
			WorkDay workDay = workDayService.findObject(meetingTime.getWorkDayId());
			if (workDay == null) {
				throw new FieldIsRequiredException("Work Day is required to continue");
			}
			checkForMeetingTimeOverlapException(workDay, meetingTime);
			
			meetingTime.setWorkDay(workDay);
			instance.setData(meetingTimeService.updateObject(meetingTime));		
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
			
		instance.setSuccess(true);
		instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		return instance;
	}

	@DeleteMapping(value = {"/{meetingTimeId}", "/{meetingTimeId}/"})
	@ResponseBody
	protected HttpResponseEntity<Object> deleteMeetingTime(Authentication auth, @PathVariable("meetingTimeId") Integer meetingTimeId) {
		HttpResponseEntity<Object> instance = new HttpResponseEntity<>();			
		this.securityCheckAdminAccess(auth, "delete");
		
		MeetingTime meetingTime;		
		try {
			meetingTime = meetingTimeService.findObject(meetingTimeId);	
			if (meetingTime == null) {
				instance.setSuccess(false);
				instance.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
				return instance;
			} 
			boolean isDeleted = meetingTimeService.deleteObject(meetingTime);
			instance.setSuccess(isDeleted);
			instance.setData(isDeleted);
			instance.setStatusCode(String.valueOf(HttpStatus.OK.value()));
			
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		return instance;
	}

}
