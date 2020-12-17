package com.jerotoma.common.utils.validators;

import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.constants.SystemConfigConstant.GENERAL_CONFIG;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.config.SystemConfig;

public class SystemConfigValidator {

	public static SystemConfig validate(Map<String, Object> params, List<String> requiredFields) {
		SystemConfig systemConfig = new SystemConfig();
		String name  = null;		
		String value = null;		
		Integer id = null;	
		
		
		if(params.containsKey(SystemConfigConstant.NAME)) {
			name  = params.get(SystemConfigConstant.NAME).toString();
		}
		if(params.containsKey(SystemConfigConstant.VALUE)) {
			value  = params.get(SystemConfigConstant.VALUE).toString();
		}
		
		if(params.containsKey(SystemConfigConstant.ID)) {
			id  = (Integer)params.get(SystemConfigConstant.ID);
		}
		
		
		if (id == null && requiredFields.contains(SystemConfigConstant.ID)) {
			throw new FieldRequiredException("ID is required to continue");
		}
		systemConfig.setId(id);
		
		if (name == null && requiredFields.contains(SystemConfigConstant.NAME)) {
			throw new FieldRequiredException("Name is required to continue");
		}
		
		GENERAL_CONFIG themeConfig = SystemConfigConstant.getGeneralConfigByName(name);
		systemConfig.setName(themeConfig != null ? themeConfig.getDbName() : name);
		
		if (value == null && requiredFields.contains(SystemConfigConstant.VALUE)) {
			throw new FieldRequiredException("Value is required to continue");
		}
		systemConfig.setValue(value);
		
		return systemConfig;
	}

}
