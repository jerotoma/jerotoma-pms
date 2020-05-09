package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.RoomConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.models.academic.Room;
import com.jerotoma.common.utils.CalendarUtil;

public class SchoolClassValidator {
	
	public static Room validate(Map<String, Object> params, List<String> requiredFields) {
		
		Room schoolClass = new Room();
		String name  = null;		
		String description = null;
		String code = null;	
		Integer id = null;		
		Integer capacity = null;	
		
		
		if(params.containsKey(RoomConstant.ROOM_NAME)) {
			name  = params.get(RoomConstant.ROOM_NAME).toString();
		}
		if(params.containsKey(RoomConstant.ROOM_DESCRIPTION)) {
			description  = params.get(RoomConstant.ROOM_DESCRIPTION).toString();
		}
		
		if(params.containsKey(RoomConstant.ROOM_CODE)) {
			code  = params.get(RoomConstant.ROOM_CODE).toString();
		}
		
		if(params.containsKey(RoomConstant.ROOM_CAPACITY)) {
			capacity  = (Integer)params.get(RoomConstant.ROOM_CAPACITY);
		}
		
		if(params.containsKey(RoomConstant.ROOM_ID)) {
			id  = (Integer)params.get(RoomConstant.ROOM_ID);
		}
		
		if (id == null && requiredFields.contains(RoomConstant.ROOM_ID)) {
			throw new FieldIsRequiredException("ID is required continue");
		}
		schoolClass.setId(id);
		
		if (capacity == null && requiredFields.contains(RoomConstant.ROOM_CAPACITY)) {
			throw new FieldIsRequiredException("Capacity is required continue");
		}
		schoolClass.setCapacity(capacity);
		
		if (name == null && requiredFields.contains(RoomConstant.ROOM_NAME)) {
			throw new FieldIsRequiredException("Name is required continue");
		}
		schoolClass.setName(name);
		
		if (description == null && requiredFields.contains(RoomConstant.ROOM_DESCRIPTION)) {
			throw new FieldIsRequiredException("Description is required continue");
		}
		schoolClass.setDescription(description);
		
		if (code == null && requiredFields.contains(RoomConstant.ROOM_CODE)) {
			throw new FieldIsRequiredException("Code is required continue");
		}
		schoolClass.setCode(code);
		
		
		
		Date today = CalendarUtil.getTodaysDate();		
		schoolClass.setCreatedOn(today);
		schoolClass.setUpdatedOn(today);
		
		return schoolClass;
	}
}
