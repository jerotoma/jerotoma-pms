package com.jerotoma.database.dao.academic.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.CompletedAcademicLevelConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.users.CompletedAcademicLevel;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.academic.CompletedAcademicLevelDao;

@Repository
@Transactional
public class CompletedAcademicLevelDaoImpl implements CompletedAcademicLevelDao {

	@PersistenceContext 
	private EntityManager entityManager;
	
	
	@Override
	public CompletedAcademicLevel findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(CompletedAcademicLevel.class, primaryKey);
	}

	@Override
	public CompletedAcademicLevel findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM CompletedAcademicLevel WHERE code := ?", CompletedAcademicLevel.class).setParameter("code", uniqueKey).getSingleResult();
	}

	@Override
	public CompletedAcademicLevel createObject(CompletedAcademicLevel object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(CompletedAcademicLevel object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<CompletedAcademicLevel> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM CompletedAcademicLevel ", CompletedAcademicLevel.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		List<CompletedAcademicLevel> rooms = entityManager.createQuery("FROM CompletedAcademicLevel", CompletedAcademicLevel.class)				
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(CompletedAcademicLevelConstant.COMPLETED_ACADEMIC_LEVELS, rooms);
		map.put(SystemConstant.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);		
		return map;
	}

	@Override
	public CompletedAcademicLevel updateObject(CompletedAcademicLevel object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM CompletedAcademicLevel", Long.class)				
				.getSingleResult();
	}


}
