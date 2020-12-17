package com.jerotoma.database.assemblers.dao.academic;

import java.util.List;

import com.jerotoma.common.models.academic.CompletionOrder;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerCompletionOrderDao extends AssemblerDao<CompletionOrder>{
	public CompletionOrder getCompletionOrder(Integer programId, Integer academicLevel);

	public List<CompletionOrder> getCompletionOrders(Integer programId);

}
