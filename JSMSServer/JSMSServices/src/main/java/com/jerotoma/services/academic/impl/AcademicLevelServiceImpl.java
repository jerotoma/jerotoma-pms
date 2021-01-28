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
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.common.models.academic.Stream;
import com.jerotoma.database.dao.academic.AcademicLevelDao;
import com.jerotoma.services.academic.AcademicLevelService;
import com.jerotoma.services.academic.StreamService;
import com.jerotoma.services.utils.ServiceUtil;

@Transactional
@Service
public class AcademicLevelServiceImpl implements AcademicLevelService {
	
	@Autowired AcademicLevelDao academicLevelDao;
	@Autowired StreamService streamService;

	@Override
	public AcademicLevel findObject(Integer primaryKey) throws SQLException {
		return ServiceUtil.getEntity(academicLevelDao.findById(primaryKey));
	}

	@Override
	public AcademicLevel findObjectUniqueKey(String uniqueKey) throws SQLException {
		return academicLevelDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public AcademicLevel createObject(AcademicLevel object) throws SQLException {
		return academicLevelDao.save(object);
	}

	@Override
	public AcademicLevel updateObject(AcademicLevel object) throws SQLException {
		return academicLevelDao.save(object);
	}

	@Override
	public Boolean deleteObject(AcademicLevel object) throws SQLException {
		academicLevelDao.delete(object);
		return true;
	}

	@Override
	public List<AcademicLevel> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return academicLevelDao.findAll();
		}		
		return academicLevelDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		Page<AcademicLevel> pageStream = academicLevelDao.findAll(ServiceUtil.getPageable(queryParam));		
		map.put(AcademicLevelConstant.ACADEMIC_LEVELS, pageStream.toList());
		map.put(SystemConstant.COUNT, pageStream.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pageStream.getTotalPages());			
		return map;
	}

	@Override
	public List<AcademicLevel> getAllAcademicLevels() throws SQLException {
		return academicLevelDao.getAllAcademicLevels();
	}

	@Override
	public AcademicLevel addStreamsToAcademicLevel(Integer academicLevelId, List<Integer> streamIds)
			throws SQLException {
		Set<Stream> streams = new HashSet<>();
		AcademicLevel academicLevel = findObject(academicLevelId);
		streams.addAll(academicLevel.getStreams());
		for (Integer streamId: streamIds) {
			Stream stream = streamService.findObject(streamId);
			streams.add(stream);		
		}
		academicLevel.setStreams(streams);		
		return updateObject(academicLevel);
	}
}
