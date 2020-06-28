package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.PositionConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.positions.Position;
import com.jerotoma.common.utils.CalendarUtil;

public class PositionValidator {

	public static Position validate(Map<String, Object> params, List<String> requiredFields) {
		Position position = new Position();
		String name  = null;		
		String description = null;
		String code = null;	
		Integer id = null;	
		
		
		if(params.containsKey(PositionConstant.POSITION_NAME)) {
			name  = params.get(PositionConstant.POSITION_NAME).toString();
		}
		if(params.containsKey(PositionConstant.POSITION_DESCRIPTION)) {
			description  = params.get(PositionConstant.POSITION_DESCRIPTION).toString();
		}
		
		if(params.containsKey(PositionConstant.POSITION_CODE)) {
			code  = params.get(PositionConstant.POSITION_CODE).toString();
		}
		if(params.containsKey(PositionConstant.POSITION_ID)) {
			id  = (Integer)params.get(PositionConstant.POSITION_ID);
		}
		
		if (id == null && requiredFields.contains(PositionConstant.POSITION_ID)) {
			throw new FieldRequiredException("Position ID can not be empty");
		}
		position.setId(id);
		
		if (name == null && requiredFields.contains(PositionConstant.POSITION_NAME)) {
			throw new FieldRequiredException("Position Name can not be empty");
		}
		position.setName(name);
		
		if (description == null && requiredFields.contains(PositionConstant.POSITION_DESCRIPTION)) {
			throw new FieldRequiredException("Position Description can not be empty");
		}
		position.setDescription(description);
		
		if (code == null && requiredFields.contains(PositionConstant.POSITION_CODE)) {
			throw new FieldRequiredException("Position Code can not be empty");
		}
		position.setCode(code);
		
		
		
		Date today = CalendarUtil.getTodaysDate();		
		position.setCreatedOn(today);
		position.setUpdatedOn(today);
		
		return position;
		
	}
}
