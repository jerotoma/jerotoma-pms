package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.DepartmentConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.academic.Department;
import com.jerotoma.common.utils.CalendarUtil;

public class DepartmentValidator {
	
	public static Department validate(Map<String, Object> params, List<String> requiredFields) {
		
		Department department = new Department();
		String name  = null;		
		Integer id = null;				
		
		if(params.containsKey(DepartmentConstant.DEPARTMENT_NAME)) {
			name  = params.get(DepartmentConstant.DEPARTMENT_NAME).toString();
		}
		
		if(params.containsKey(DepartmentConstant.DEPARTMENT_ID)) {
			id  = (Integer)params.get(DepartmentConstant.DEPARTMENT_ID);
		}		
		
		if (id == null && requiredFields.contains(DepartmentConstant.DEPARTMENT_ID)) {
			throw new FieldRequiredException("ID is required to continue");
		}
		department.setId(id);
				
		if (name == null && requiredFields.contains(DepartmentConstant.DEPARTMENT_NAME)) {
			throw new FieldRequiredException("Name is required to continue");
		}
		department.setName(name);		
		
		Date today = CalendarUtil.getTodaysDate();		
		department.setCreatedOn(today);
		department.setUpdatedOn(today);
		
		return department;
	}

}
