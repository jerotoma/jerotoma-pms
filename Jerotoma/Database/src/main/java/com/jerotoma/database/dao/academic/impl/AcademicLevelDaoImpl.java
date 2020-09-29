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
import com.jerotoma.common.constants.AcademicLevelConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.AcademicLevel;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.academic.AcademicLevelDao;

@Transactional
@Repository
public class AcademicLevelDaoImpl implements AcademicLevelDao {

	@PersistenceContext 
	private EntityManager entityManager;
	
	
	@Override
	public AcademicLevel findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(AcademicLevel.class, primaryKey);
	}

	@Override
	public AcademicLevel findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM AcademicLevel WHERE code := ?", AcademicLevel.class).setParameter("code", uniqueKey).getSingleResult();
	}

	@Override
	public AcademicLevel createObject(AcademicLevel object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(AcademicLevel object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<AcademicLevel> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM AcademicLevel ", AcademicLevel.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		List<AcademicLevel> academicLeveles = entityManager.createQuery("FROM AcademicLevel", AcademicLevel.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(AcademicLevelConstant.ACADEMIC_LEVELS, academicLeveles);
		map.put(SystemConstant.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public AcademicLevel updateObject(AcademicLevel object) throws SQLException {		
		return entityManager.merge(object);
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM AcademicLevel", Long.class)				
				.getSingleResult();
	}

	@Override
	public List<AcademicLevel> getAllAcademicLevels() throws SQLException {		
		return entityManager.createQuery("FROM AcademicLevel", AcademicLevel.class).getResultList();
	}

}