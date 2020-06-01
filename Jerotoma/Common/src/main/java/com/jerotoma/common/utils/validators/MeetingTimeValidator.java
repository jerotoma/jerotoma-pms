package com.jerotoma.common.utils.validators;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.MeetingTimeConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.schedules.MeetingTime;
import com.jerotoma.common.schedules.Time;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.NumberFormatter;

public class MeetingTimeValidator {
	
	

	public static MeetingTime validate(Map<String, Object> params, List<String> requiredFields) {
		MeetingTime meetingTime = new MeetingTime();
		Integer workDayId  = null;
		String time = null;
		LocalTime startTime  = null;
		LocalTime endTime  = null;
		Integer id = null;	
		
		if(params.containsKey(MeetingTimeConstant.ID)) {
			id  = (Integer)params.get(MeetingTimeConstant.ID);
		}
		if(params.containsKey(MeetingTimeConstant.TIME)) {
			time  = params.get(MeetingTimeConstant.TIME).toString();
		}
		
		if(params.containsKey(MeetingTimeConstant.WORK_DAY_ID)) {
			workDayId  = (Integer)params.get(MeetingTimeConstant.WORK_DAY_ID);
		}
		
		if(params.containsKey(MeetingTimeConstant.START_TIME)) {
			Time t = Time.parse(params.get(MeetingTimeConstant.START_TIME).toString());
			startTime  = LocalTime.parse(t.toString());
		}
		
		if(params.containsKey(MeetingTimeConstant.END_TIME)) {
			Time t = Time.parse(params.get(MeetingTimeConstant.END_TIME).toString());					
			endTime  = LocalTime.parse(t.toString());
		}
		
		if (id == null && requiredFields.contains(MeetingTimeConstant.ID)) {
			throw new FieldIsRequiredException("ID is required to continue");
		}
		meetingTime.setId(id);
		
		if (time == null && requiredFields.contains(MeetingTimeConstant.TIME)) {
			throw new FieldIsRequiredException("Time is required to continue");
		}
		if (time != null) {
			time = NumberFormatter.formatTimeRange(time);
		}		
		meetingTime.setTime(time);
		
		if (startTime == null && requiredFields.contains(MeetingTimeConstant.START_TIME)) {
			throw new FieldIsRequiredException("Start Time is required to continue");
		}
		meetingTime.setStartTime(startTime);
		
		if (endTime == null && requiredFields.contains(MeetingTimeConstant.END_TIME)) {
			throw new FieldIsRequiredException("End Time is required to continue");
		}
		meetingTime.setEndTime(endTime);
		
		if (workDayId == null && requiredFields.contains(MeetingTimeConstant.WORK_DAY_ID)) {
			throw new FieldIsRequiredException("Work Day ID is required to continue");
		}		
		meetingTime.setWorkDayId(workDayId);
		
		Date today = CalendarUtil.getTodaysDate();		
		meetingTime.setCreatedOn(today);
		meetingTime.setUpdatedOn(today);
		
		if (!meetingTime.isValid()) {
			throw new FieldIsRequiredException("Meeting Time is Invalid, please verify that the start and end time don't overlap.");
		}
		
		return meetingTime;
	}

}
