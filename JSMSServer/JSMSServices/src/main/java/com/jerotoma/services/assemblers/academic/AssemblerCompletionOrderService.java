package com.jerotoma.services.assemblers.academic;

import java.util.List;

import com.jerotoma.common.models.academic.CompletionOrder;
import com.jerotoma.services.AssemblerService;

public interface AssemblerCompletionOrderService extends AssemblerService<CompletionOrder> { 
	public CompletionOrder getCompletionOrder(Integer programId, Integer academicLevelId);
	
	public List<CompletionOrder> getCompletionOrders(Integer programId);
}
