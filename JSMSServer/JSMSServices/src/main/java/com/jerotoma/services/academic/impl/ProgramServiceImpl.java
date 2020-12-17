package com.jerotoma.services.academic.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.models.academic.Program;
import com.jerotoma.common.models.academic.Program.AcademicLevelCompletionOrder;
import com.jerotoma.common.models.academic.ProgramAcademicLevel;
import com.jerotoma.database.dao.academic.ProgramDao;
import com.jerotoma.services.academic.AcademicLevelService;
import com.jerotoma.services.academic.CompletionOrderService;
import com.jerotoma.services.academic.ProgramAcademicLevelCompletionOrderService;
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
	
	@Autowired ProgramAcademicLevelCompletionOrderService programAcademicLevelCompletionOrderService;

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
				
		for (AcademicLevelCompletionOrder acOrder: program.getAcademicLevelCompletionOrders()) {			
			AcademicLevel academicLevel = academicLevelService.findObject(acOrder.getAcademicLevelId());			
			ProgramAcademicLevel programAcademicLevel = new ProgramAcademicLevel(programCreated, academicLevel, acOrder.getCompletionOrder());
			programAcademicLevel  = programAcademicLevelService.createObject(programAcademicLevel);						
			programAcademicLevels.add(programAcademicLevel);				
		}
		programCreated.setProgramAcademicLevels(programAcademicLevels);
		return programCreated;
	}


	@Override
	public Program updateProgramAndAssociateAcademicLevels(Program program, List<Integer> academicLevelIDs)
			throws SQLException {
		List<ProgramAcademicLevel> programAcademicLevels = new ArrayList<ProgramAcademicLevel>();		
		Program programUpdated = updateObject(program);
						
		for (AcademicLevelCompletionOrder acOrder: program.getAcademicLevelCompletionOrders()) {			
			AcademicLevel academicLevel = academicLevelService.findObject(acOrder.getAcademicLevelId());			
			boolean doesProgramAcademicLevelExist = assemblerProgramService.doesProgramAcademicLevelExist(programUpdated.getId(), academicLevel.getId());			
			
			ProgramAcademicLevel programAcademicLevel = doesProgramAcademicLevelExist  
					? programAcademicLevelService.findProgramAcademicLevelByIDs(programUpdated.getId(), academicLevel.getId()) : new ProgramAcademicLevel();
			programAcademicLevel.setAcademicLevel(academicLevel);
			programAcademicLevel.setProgram(programUpdated);
			programAcademicLevel.setCompletionOrder(acOrder.getCompletionOrder());			
			programAcademicLevel  = doesProgramAcademicLevelExist 
					? programAcademicLevelService.updateObject(programAcademicLevel)
					: programAcademicLevelService.createObject(programAcademicLevel);
			programAcademicLevels.add(programAcademicLevel);				
		}
		programUpdated.setProgramAcademicLevels(programAcademicLevels);
		return programUpdated;
	}
}
