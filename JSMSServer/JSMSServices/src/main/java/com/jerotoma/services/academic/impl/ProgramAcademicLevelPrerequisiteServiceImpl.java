package com.jerotoma.services.academic.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.ProgramConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.ProgramAcademicLevelPrerequisite;
import com.jerotoma.database.dao.academic.ProgramAcademicLevelPrerequisiteDao;
import com.jerotoma.services.academic.ProgramAcademicLevelPrerequisiteService;
import com.jerotoma.services.utils.ServiceUtil;

@Service
@Transactional
public class ProgramAcademicLevelPrerequisiteServiceImpl implements ProgramAcademicLevelPrerequisiteService {

	@Autowired ProgramAcademicLevelPrerequisiteDao prerequisiteDao;
	
	@Override
	public ProgramAcademicLevelPrerequisite findObject(Integer primaryKey) throws SQLException {
		return ServiceUtil.getEntity(prerequisiteDao.findById(primaryKey));
	}

	@Override
	public ProgramAcademicLevelPrerequisite findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("Method not implemented yet");
	}

	@Override
	public ProgramAcademicLevelPrerequisite createObject(ProgramAcademicLevelPrerequisite object) throws SQLException {
		return prerequisiteDao.save(object);
	}

	@Override
	public ProgramAcademicLevelPrerequisite updateObject(ProgramAcademicLevelPrerequisite object) throws SQLException {
		return prerequisiteDao.save(object);
	}

	@Override
	public Boolean deleteObject(ProgramAcademicLevelPrerequisite object) throws SQLException {
		prerequisiteDao.delete(object);
		return true;
	}

	@Override
	public List<ProgramAcademicLevelPrerequisite> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return prerequisiteDao.findAll();
		}		
		return prerequisiteDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();		
		Page<ProgramAcademicLevelPrerequisite> pagePrerequisite = prerequisiteDao.findAll(ServiceUtil.getPageable(queryParam));
		map.put(ProgramConstant.PROGRAM_ACADEMIC_LEVEL_PREREQUISITES, pagePrerequisite.toList());
		map.put(SystemConstant.COUNT, pagePrerequisite.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pagePrerequisite.getTotalPages());	
		
		return map;
	}

	@Override
	public ProgramAcademicLevelPrerequisite getProgramAcademicLevelPrerequisite(Integer programId,	Integer academicLevelID) {		
		return prerequisiteDao.getProgramAcademicLevelPrerequisite(programId, academicLevelID);
	}

}
