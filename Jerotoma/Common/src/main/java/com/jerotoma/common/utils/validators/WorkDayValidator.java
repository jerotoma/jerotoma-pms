package com.jerotoma.common.utils.validators;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.WorkDayConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.schedules.WorkDay;
import com.jerotoma.common.utils.CalendarUtil;

public class WorkDayValidator {
	
	public static WorkDay validate(Map<String, Object> params, List<String> requiredFields) {
		WorkDay workDay = new WorkDay();
		DayOfWeek day = null;
		Integer id = null;	
		Integer dayId = null;	
		
		if(params.containsKey(WorkDayConstant.ID)) {
			id  = (Integer)params.get(WorkDayConstant.ID);
		}
			
		if(params.containsKey(WorkDayConstant.DAY_ID)) {
			dayId  = (Integer)params.get(WorkDayConstant.DAY_ID);
		}
		
		
		if (id == null && requiredFields.contains(WorkDayConstant.ID)) {
			throw new FieldIsRequiredException("ID is required to continue");
		}
		workDay.setId(id);
		
		if (dayId == null && requiredFields.contains(WorkDayConstant.DAY_ID)) {
			throw new FieldIsRequiredException("Day ID is required to continue");
		}
		workDay.setDayId(dayId);
		day = DayOfWeek.of(dayId);
		workDay.setDay(day);
		
		Date today = CalendarUtil.getTodaysDate();		
		workDay.setCreatedOn(today);
		workDay.setUpdatedOn(today);
		
		return workDay;
	}
}
