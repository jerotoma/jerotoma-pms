package com.jerotoma.services.academic.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.ProgramConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.models.academic.Program;
import com.jerotoma.common.models.academic.Program.ProgramAcademicLevel;
import com.jerotoma.common.models.academic.ProgramAcademicLevelPrerequisite;
import com.jerotoma.database.dao.academic.ProgramDao;
import com.jerotoma.services.academic.AcademicLevelService;
import com.jerotoma.services.academic.ProgramAcademicLevelPrerequisiteService;
import com.jerotoma.services.academic.ProgramService;
import com.jerotoma.services.utils.ServiceUtil;

@Transactional
@Service
public class ProgramServiceImpl implements ProgramService {

	@Autowired
	ProgramDao programDao;	
	@Autowired
	AcademicLevelService academicLevelService;
	@Autowired
	ProgramAcademicLevelPrerequisiteService programAcademicLevelPrerequisiteService;

	private static final String INVALID_PREREQUISITE = "Invalid Pre-requisite, academic level can not pre-requisite to it self";

	@Override
	public Program findObject(Integer primaryKey) throws SQLException {		
		return ServiceUtil.getEntity(programDao.findById(primaryKey));
	}

	@Override
	public Program findObjectUniqueKey(String uniqueKey) throws SQLException {
		return programDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public Program createObject(Program object) throws SQLException {
		return programDao.save(object);
	}

	@Override
	public Program updateObject(Program program) throws SQLException {
		Program dbProgram =  findObject(program.getId());
		program.setAcademicLevels(dbProgram.getAcademicLevels());
		program.setPrerequisites(dbProgram.getPrerequisites());
		return programDao.save(program);
	}

	@Override
	public Boolean deleteObject(Program object) throws SQLException {
		programDao.delete(object);
		return true;
	}

	@Override
	public List<Program> loadList(@Nullable QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return programDao.findAll();
		}
		return programDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		Page<Program> pageProgram = programDao.findAll(ServiceUtil.getPageable(queryParam));
		map.put(ProgramConstant.PROGRAMS, pageProgram.toList());
		map.put(SystemConstant.COUNT, pageProgram.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pageProgram.getTotalPages());
		return map;
	}

	@Override
	public Program createProgramAndAssociateAcademicLevels(Integer programId, ProgramAcademicLevel programAcademicLevel)
			throws SQLException {
		ProgramAcademicLevelPrerequisite prerequisite = null;
		Set<AcademicLevel> academicLevels = new HashSet<>();
		Set<ProgramAcademicLevelPrerequisite> prerequisites = new HashSet<>();

		Program program = findObject(programId);
		AcademicLevel academicLevel = academicLevelService.findObject(programAcademicLevel.getAcademicLevelId());
		if (programAcademicLevel.getAcademicLevelPrerequisiteIds() != null) {
			for (Integer academicLevelPrerequisiteId : programAcademicLevel.getAcademicLevelPrerequisiteIds()) {
				if (academicLevelPrerequisiteId.equals(academicLevel.getId())) {
					throw new FieldRequiredException(INVALID_PREREQUISITE);
				}
				AcademicLevel academicLevelPrerequisite = academicLevelService.findObject(academicLevelPrerequisiteId);
				prerequisite = new ProgramAcademicLevelPrerequisite();
				prerequisite.setAcademicLevel(academicLevel);
				prerequisite.setPrerequisiteAcademicLevel(academicLevelPrerequisite);
				prerequisite.setProgram(program);
				prerequisites.add(programAcademicLevelPrerequisiteService.createObject(prerequisite));
			}
			academicLevel.setPrerequisites(prerequisites);
		}
		academicLevels.addAll(program.getAcademicLevels());
		academicLevels.add(academicLevel);
		program.setAcademicLevels(academicLevels);
		return updateObject(program);
	}

	@Override
	public boolean deleteAcademicLevelFromProgram(Integer programId, Integer academicLevelID) throws SQLException {
		Program program = findObject(programId);
		AcademicLevel academicLevel = academicLevelService.findObject(academicLevelID);
		ProgramAcademicLevelPrerequisite programAcademicLevelPrerequisite = programAcademicLevelPrerequisiteService.getProgramAcademicLevelPrerequisite(programId, academicLevelID);
		boolean isDeleted = program.getAcademicLevels().remove(academicLevel);
		if (programAcademicLevelPrerequisite != null && isDeleted) {
			programAcademicLevelPrerequisiteService.deleteObject(programAcademicLevelPrerequisite);
		}		
		return isDeleted;
	}

}
