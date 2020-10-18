package com.jerotoma.common.utils.validators;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.constants.CompletionOrderConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.academic.CompletionOrder;
import com.jerotoma.common.utils.CalendarUtil;
import com.jerotoma.common.utils.StringUtility;

public class CompletionOrderValidator {

	public static CompletionOrder validate(Map<String, Object> params, List<String> requiredFields) {
		
		CompletionOrder completionOrder = new CompletionOrder();
		String name  = null;		
		Integer completionOrderLabel = null;	
		Integer id = null;	
						
		if(params.containsKey(CompletionOrderConstant.NAME)) {
			name  = params.get(CompletionOrderConstant.NAME).toString();
		}
				
		if(params.containsKey(CompletionOrderConstant.COMPLETION_ORDER)) {
			completionOrderLabel  = (Integer) params.get(CompletionOrderConstant.COMPLETION_ORDER);
		}
		
		if(params.containsKey(CompletionOrderConstant.ID)) {
			id  =  StringUtility.isNumeric(params.get(CompletionOrderConstant.ID)) ? (Integer)params.get(CompletionOrderConstant.ID) : null;
		}		
				
		if (id == null && requiredFields.contains(CompletionOrderConstant.ID)) {
			throw new FieldRequiredException("ID is required to continue");
		}
		completionOrder.setId(id);
		
		if (name == null && requiredFields.contains(CompletionOrderConstant.NAME)) {
			throw new FieldRequiredException("Name is required to continue");
		}
		completionOrder.setName(name);
		
		
		if (completionOrderLabel == null && requiredFields.contains(CompletionOrderConstant.COMPLETION_ORDER)) {
			throw new FieldRequiredException("CompletionOrder is required to continue");
		}
		completionOrder.setCompletionOrder(completionOrderLabel);		
		
		Date today = CalendarUtil.getTodaysDate();		
		completionOrder.setCreatedOn(today);
		completionOrder.setUpdatedOn(today);
		
		return completionOrder;
	}
}
