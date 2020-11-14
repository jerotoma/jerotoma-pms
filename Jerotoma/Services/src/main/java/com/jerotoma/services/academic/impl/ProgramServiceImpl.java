package com.jerotoma.services.academic.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.models.academic.CompletionOrder;
import com.jerotoma.common.models.academic.Program;
import com.jerotoma.common.models.academic.Program.AcademicLevelCompletionOrder;
import com.jerotoma.common.models.academic.ProgramAcademicLevel;
import com.jerotoma.database.dao.academic.ProgramDao;
import com.jerotoma.services.academic.AcademicLevelService;
import com.jerotoma.services.academic.CompletionOrderService;
import com.jerotoma.services.academic.ProgramAcademicLevelService;
import com.jerotoma.services.academic.ProgramService;
import com.jerotoma.services.assemblers.academic.AssemblerCompletionOrderService;
import com.jerotoma.services.assemblers.academic.AssemblerProgramService;


@Transactional
@Service
public class ProgramServiceImpl implements ProgramService {
	
	@Autowired ProgramDao programDao;
	
	@Autowired AssemblerProgramService assemblerProgramService;
	@Autowired ProgramAcademicLevelService programAcademicLevelService;
	@Autowired CompletionOrderService completionOrderService;
	@Autowired AcademicLevelService academicLevelService;
	@Autowired AssemblerCompletionOrderService assemblerCompletionOrderService;

	@Override
	public Program findObject(Integer primaryKey) throws SQLException {
		return programDao.findObject(primaryKey);
	}

	@Override
	public Program findObjectUniqueKey(String uniqueKey) throws SQLException {
		return programDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public Program createObject(Program object) throws SQLException {
		return programDao.createObject(object);
	}

	@Override
	public Program updateObject(Program object) throws SQLException {
		return programDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(Program object) throws SQLException {
		return programDao.deleteObject(object);
	}

	@Override
	public List<Program> loadList(QueryParam queryParam) throws SQLException {
		return programDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return programDao.loadMapList(queryParam);
	}
	
	@Override
	public Program createProgramAndAssociateAcademicLevels(Program program, List<Integer> academicLevelIDs) throws SQLException {
		List<ProgramAcademicLevel> programAcademicLevels = new ArrayList<ProgramAcademicLevel>();
		
		Program programCreated = createObject(program);
				
		for (Integer academicLevelID: academicLevelIDs) {			
			boolean found = false;
			Integer completionOrderId = null;
			for (AcademicLevelCompletionOrder acOrder: program.getAcademicLevelCompletionOrders()) {
				if (acOrder.getAcademicLevelId().equals(academicLevelID)) {
					found = true;
					completionOrderId = acOrder.getCompletionOrderId();
					break;
				}
			}
			AcademicLevel academicLevel = academicLevelService.findObject(academicLevelID);
			
			wasAcademicLevelFound(found, academicLevel);
			
			validateCompletionOrderExistance(programCreated, completionOrderId, academicLevel);
			
			ProgramAcademicLevel programAcademicLevel = new ProgramAcademicLevel(programCreated, academicLevel);
			programAcademicLevel.setCompletionOrder(completionOrderService.findObject(completionOrderId));
			programAcademicLevel  = programAcademicLevelService.createObject(programAcademicLevel);
			programAcademicLevels.add(programAcademicLevel);				
		}
		programCreated.setProgramAcademicLevels(programAcademicLevels);
		return programCreated;
	}

	protected void wasAcademicLevelFound(boolean found, AcademicLevel academicLevel) {
		if (!found) {
			throw new FieldRequiredException("Academic Level: " + academicLevel.getName() + " (" + academicLevel.getId() + ")" + " requires Completion Order to continue");
		}
	}

	@Override
	public Program updateProgramAndAssociateAcademicLevels(Program program, List<Integer> academicLevelIDs)
			throws SQLException {
		List<ProgramAcademicLevel> programAcademicLevels = new ArrayList<ProgramAcademicLevel>();
		
		Program programUpdated = updateObject(program);	
		for (Integer academicLevelID: academicLevelIDs) {
			boolean found = false;
			Integer completionOrderId = null;
			for (AcademicLevelCompletionOrder academicLevelOrder: program.getAcademicLevelCompletionOrders()) {
				if (academicLevelOrder.getAcademicLevelId().equals(academicLevelID)) {
					found = true;
					completionOrderId = academicLevelOrder.getCompletionOrderId();
					break;
				}
			}
			AcademicLevel academicLevel = academicLevelService.findObject(academicLevelID);
			
			wasAcademicLevelFound(found, academicLevel);
			validateCompletionOrderExistance(programUpdated, completionOrderId, academicLevel);
			
			ProgramAcademicLevel programAcademicLevel = new ProgramAcademicLevel(programUpdated, academicLevel);
			if (assemblerProgramService.doesProgramAcademicLevelExist(programUpdated.getId(), academicLevel.getId())) {
				programAcademicLevel = programAcademicLevelService.findProgramAcademicLevelByIDs(program.getId(), academicLevel.getId());
				programAcademicLevel.setAcademicLevel(academicLevel);
				programAcademicLevel.setProgram(programUpdated);
				programAcademicLevel.setCompletionOrder(completionOrderService.findObject(completionOrderId));
				programAcademicLevelService.updateObject(programAcademicLevel);
			} else {
				programAcademicLevel.setCompletionOrder(completionOrderService.findObject(completionOrderId));
				programAcademicLevel = programAcademicLevelService.createObject(programAcademicLevel);
			}			
			programAcademicLevels.add(programAcademicLevel);				
		}
		programUpdated.setProgramAcademicLevels(programAcademicLevels);
		return programUpdated;
	}

	protected void validateCompletionOrderExistance(Program program, Integer completionOrderId,
			AcademicLevel academicLevel) {
		List<CompletionOrder> completionOrders = assemblerCompletionOrderService.getCompletionOrders(program.getId());
		for (CompletionOrder completionOrder : completionOrders) {
			if (completionOrderId.equals(completionOrder.getId())) {
				throw new FieldRequiredException("Academic Level: " + academicLevel.getName() + " (" + academicLevel.getId() + ")" + " has Completion Order that have been added on other levels");
			}
		}
		
	}
}
