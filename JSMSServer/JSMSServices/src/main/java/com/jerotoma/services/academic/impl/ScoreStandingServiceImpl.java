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
import com.jerotoma.common.constants.ErrorMessageConstant;
import com.jerotoma.common.constants.ScoreStandingConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.ScoreStanding;
import com.jerotoma.database.dao.academic.ScoreStandingDao;
import com.jerotoma.services.academic.ScoreStandingService;
import com.jerotoma.services.utils.ServiceUtil;

@Transactional
@Service
public class ScoreStandingServiceImpl implements ScoreStandingService {
	
	@Autowired ScoreStandingDao scoreStandingDao;

	@Override
	public ScoreStanding findObject(Integer primaryKey) throws SQLException {		
		return ServiceUtil.getEntity(scoreStandingDao.findById(primaryKey));
	}

	@Override
	public ScoreStanding findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException(ErrorMessageConstant.METHOD_NOT_IMPLEMENTED);
	}

	@Override
	public ScoreStanding createObject(ScoreStanding object) throws SQLException {
		return scoreStandingDao.save(object);
	}

	@Override
	public ScoreStanding updateObject(ScoreStanding object) throws SQLException {
		return scoreStandingDao.save(object);
	}

	@Override
	public Boolean deleteObject(ScoreStanding object) throws SQLException {
		scoreStandingDao.delete(object);
		return true;
	}

	@Override
	public List<ScoreStanding> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return scoreStandingDao.findAll();
		}		
		return scoreStandingDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();		
		Page<ScoreStanding> pageScoreStanding = scoreStandingDao.findAll(ServiceUtil.getPageable(queryParam));
		map.put(ScoreStandingConstant.SCORE_STANDINGS, pageScoreStanding.toList());
		map.put(SystemConstant.COUNT, pageScoreStanding.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pageScoreStanding.getTotalPages());	
		
		return map;
	}
}
