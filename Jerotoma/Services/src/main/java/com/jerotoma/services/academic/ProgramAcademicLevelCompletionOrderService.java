package com.jerotoma.services.academic;

import java.util.List;

import com.jerotoma.common.models.academic.ProgramAcademicLevelCompletionOrder;

public interface ProgramAcademicLevelCompletionOrderService {
	
	ProgramAcademicLevelCompletionOrder findById(Integer id);
	ProgramAcademicLevelCompletionOrder save(ProgramAcademicLevelCompletionOrder entity);
	List<ProgramAcademicLevelCompletionOrder> saveAll(List<ProgramAcademicLevelCompletionOrder> entities);

}
