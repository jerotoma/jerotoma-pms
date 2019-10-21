package com.jerotoma.common.utils.validators;

import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.UserPreferenceConstant;
import com.jerotoma.common.exceptions.FieldIsRequiredException;
import com.jerotoma.common.models.config.UserPreference;

public class UserPreferenceValidator {
	
	public static UserPreference validate(Map<String, Object> params, List<String> requiredFields) {
		UserPreference systemConfig = new UserPreference();
		String name  = null;		
		String value = null;		
		Integer id = null;	
		
		
		if(params.containsKey(UserPreferenceConstant.NAME)) {
			name  = params.get(UserPreferenceConstant.NAME).toString();
		}
		if(params.containsKey(UserPreferenceConstant.VALUE)) {
			value  = params.get(UserPreferenceConstant.VALUE).toString();
		}
		
		if(params.containsKey(UserPreferenceConstant.ID)) {
			id  = (Integer)params.get(UserPreferenceConstant.ID);
		}
		
		
		if (id == null && requiredFields.contains(UserPreferenceConstant.ID)) {
			throw new FieldIsRequiredException("ID is required to continue");
		}
		systemConfig.setId(id);
		
		if (name == null && requiredFields.contains(UserPreferenceConstant.NAME)) {
			throw new FieldIsRequiredException("Name is required to continue");
		}
		systemConfig.setName(name);
		
		if (value == null && requiredFields.contains(UserPreferenceConstant.VALUE)) {
			throw new FieldIsRequiredException("Value is required to continue");
		}
		systemConfig.setValue(value);
		
		return systemConfig;
	}
	

}
