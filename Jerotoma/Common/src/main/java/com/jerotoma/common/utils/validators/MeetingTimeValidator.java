package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.MeetingTimeConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.schedules.MeetingTime;
import com.jerotoma.common.utils.CalendarUtil;

public class MeetingTimeValidator {
	
	

	public static MeetingTime validate(Map<String, Object> params, List<String> requiredFields) {
		MeetingTime meetingTime = new MeetingTime();
		String time  = null;		
		Integer id = null;	
		
		if(params.containsKey(MeetingTimeConstant.ID)) {
			id  = (Integer)params.get(MeetingTimeConstant.ID);
		}
		if(params.containsKey(MeetingTimeConstant.TIME)) {
			time  = params.get(MeetingTimeConstant.TIME).toString();
		}
		
		
		if (id == null && requiredFields.contains(MeetingTimeConstant.ID)) {
			throw new FieldIsRequiredException("ID is required to continue");
		}
		meetingTime.setId(id);
		
		if (time == null && requiredFields.contains(MeetingTimeConstant.TIME)) {
			throw new FieldIsRequiredException("Time is required to continue");
		}
		meetingTime.setTime(time);
		
		Date today = CalendarUtil.getTodaysDate();		
		meetingTime.setCreatedOn(today);
		meetingTime.setUpdatedOn(today);
		
		return meetingTime;
	}

}
