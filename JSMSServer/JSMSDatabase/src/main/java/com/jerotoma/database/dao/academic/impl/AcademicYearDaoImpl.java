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
import com.jerotoma.common.constants.AcademicConstants;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.academic.AcademicYearDao;

@Repository
@Transactional
public class AcademicYearDaoImpl implements AcademicYearDao {
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	
	@Override
	public AcademicYear findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(AcademicYear.class, primaryKey);
	}

	@Override
	public AcademicYear findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM AcademicYear WHERE code := ?", AcademicYear.class).setParameter("code", uniqueKey).getSingleResult();
	}

	@Override
	public AcademicYear createObject(AcademicYear object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(AcademicYear object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<AcademicYear> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM AcademicYear ", AcademicYear.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		List<AcademicYear> academicYears = entityManager.createQuery("FROM AcademicYear", AcademicYear.class)		
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(AcademicConstants.ACADEMIC_YEARS, academicYears);
		map.put(SystemConstant.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);		
		return map;
	}

	@Override
	public AcademicYear updateObject(AcademicYear object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM AcademicYear", Long.class)				
				.getSingleResult();
	}

}
