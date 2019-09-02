package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.ClassRoomConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.models.academic.ClassRoom;
import com.jerotoma.common.utils.CalendarUtil;

public class SchoolClassValidator {
	
	public static ClassRoom validate(Map<String, Object> params, List<String> requiredFields) {
		
		ClassRoom schoolClass = new ClassRoom();
		String name  = null;		
		String description = null;
		String code = null;	
		Integer id = null;		
		Integer capacity = null;	
		
		
		if(params.containsKey(ClassRoomConstant.CLASS_ROOM_NAME)) {
			name  = params.get(ClassRoomConstant.CLASS_ROOM_NAME).toString();
		}
		if(params.containsKey(ClassRoomConstant.CLASS_ROOM_DESCRIPTION)) {
			description  = params.get(ClassRoomConstant.CLASS_ROOM_DESCRIPTION).toString();
		}
		
		if(params.containsKey(ClassRoomConstant.CLASS_ROOM_CODE)) {
			code  = params.get(ClassRoomConstant.CLASS_ROOM_CODE).toString();
		}
		
		if(params.containsKey(ClassRoomConstant.CLASS_ROOM_CAPACITY)) {
			capacity  = (Integer)params.get(ClassRoomConstant.CLASS_ROOM_CAPACITY);
		}
		
		if(params.containsKey(ClassRoomConstant.CLASS_ROOM_ID)) {
			id  = (Integer)params.get(ClassRoomConstant.CLASS_ROOM_ID);
		}
		
		if (id == null && requiredFields.contains(ClassRoomConstant.CLASS_ROOM_ID)) {
			throw new FieldIsRequiredException("ID is required continue");
		}
		schoolClass.setId(id);
		
		if (capacity == null && requiredFields.contains(ClassRoomConstant.CLASS_ROOM_CAPACITY)) {
			throw new FieldIsRequiredException("Capacity is required continue");
		}
		schoolClass.setCapacity(capacity);
		
		if (name == null && requiredFields.contains(ClassRoomConstant.CLASS_ROOM_NAME)) {
			throw new FieldIsRequiredException("Name is required continue");
		}
		schoolClass.setName(name);
		
		if (description == null && requiredFields.contains(ClassRoomConstant.CLASS_ROOM_DESCRIPTION)) {
			throw new FieldIsRequiredException("Description is required continue");
		}
		schoolClass.setDescription(description);
		
		if (code == null && requiredFields.contains(ClassRoomConstant.CLASS_ROOM_CODE)) {
			throw new FieldIsRequiredException("Code is required continue");
		}
		schoolClass.setCode(code);
		
		
		
		Date today = CalendarUtil.getTodaysDate();		
		schoolClass.setCreatedOn(today);
		schoolClass.setUpdatedOn(today);
		
		return schoolClass;
	}
}
