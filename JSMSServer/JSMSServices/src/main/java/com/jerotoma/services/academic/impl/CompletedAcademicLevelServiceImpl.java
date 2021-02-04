package com.jerotoma.services.academic.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AcademicLevelConstant;
import com.jerotoma.common.constants.ErrorMessageConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.users.CompletedAcademicLevel;
import com.jerotoma.database.dao.academic.CompletedAcademicLevelDao;
import com.jerotoma.services.academic.CompletedAcademicLevelService;
import com.jerotoma.services.utils.ServiceUtil;

@Service
@Transactional
public class CompletedAcademicLevelServiceImpl implements CompletedAcademicLevelService {
	
	@Autowired CompletedAcademicLevelDao completedAcademicLevelDao;

	@Override
	public CompletedAcademicLevel findObject(Integer primaryKey) throws SQLException {
		return ServiceUtil.getEntity(completedAcademicLevelDao.findById(primaryKey));
	}

	@Override
	public CompletedAcademicLevel findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException(ErrorMessageConstant.METHOD_NOT_IMPLEMENTED);
	}

	@Override
	public CompletedAcademicLevel createObject(CompletedAcademicLevel object) throws SQLException {
		return completedAcademicLevelDao.save(object);
	}

	@Override
	public CompletedAcademicLevel updateObject(CompletedAcademicLevel object) throws SQLException {
		return completedAcademicLevelDao.save(object);
	}

	@Override
	public Boolean deleteObject(CompletedAcademicLevel object) throws SQLException {
		completedAcademicLevelDao.delete(object);
		return true;
	}

	@Override
	public List<CompletedAcademicLevel> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return completedAcademicLevelDao.findAll();
		}		
		return completedAcademicLevelDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		Page<CompletedAcademicLevel> pageCompletedAcademicLevel = completedAcademicLevelDao.findAll(ServiceUtil.getPageable(queryParam));		
		map.put(AcademicLevelConstant.ACADEMIC_LEVELS, pageCompletedAcademicLevel.toList());
		map.put(SystemConstant.COUNT, pageCompletedAcademicLevel.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pageCompletedAcademicLevel.getTotalPages());			
		return map;
	}

	@Override
	public boolean exists(Integer studentId, Integer academicLevelId) {		
		return completedAcademicLevelDao.exists(studentId, academicLevelId);
	}

	@Override
	public CompletedAcademicLevel findCompletedAcademicLevel(Integer studentId, Integer academicLevelId) {
		return completedAcademicLevelDao.findCompletedAcademicLevel(studentId, academicLevelId);
	}

	@Override
	public Set<CompletedAcademicLevel> getCompletedAcademicLevels(Integer studentId) {
		List<CompletedAcademicLevel> completedLevels = completedAcademicLevelDao.getCompletedAcademicLevels(studentId);
		return new HashSet<CompletedAcademicLevel>(completedLevels);
	}
}
