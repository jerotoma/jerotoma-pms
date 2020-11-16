package com.jerotoma.services.academic.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.models.academic.ProgramAcademicLevelCompletionOrder;
import com.jerotoma.database.dao.academic.ProgramAcademicLevelCompletionOrderDao;
import com.jerotoma.services.academic.ProgramAcademicLevelCompletionOrderService;

@Service
@Transactional
public class ProgramAcademicLevelCompletionOrderServiceImpl implements ProgramAcademicLevelCompletionOrderService {
	
	@Autowired
	ProgramAcademicLevelCompletionOrderDao programAcademicLevelCompletionOrder;

	@Override
	public ProgramAcademicLevelCompletionOrder findById(Integer id) {
		
		return programAcademicLevelCompletionOrder.findById(id).get();
	}

	@Override
	public ProgramAcademicLevelCompletionOrder save(
			ProgramAcademicLevelCompletionOrder entity) {
		
		return programAcademicLevelCompletionOrder.save(entity);
	}

	@Override
	public List<ProgramAcademicLevelCompletionOrder> saveAll(
			List<ProgramAcademicLevelCompletionOrder> entities) {
		return programAcademicLevelCompletionOrder.saveAll(entities);
	}

}
