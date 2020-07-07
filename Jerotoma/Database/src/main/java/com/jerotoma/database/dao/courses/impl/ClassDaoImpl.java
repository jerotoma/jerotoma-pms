package com.jerotoma.database.dao.courses.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.ClassConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.Class;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.courses.ClassDao;

@Repository
@Transactional
public class ClassDaoImpl  implements ClassDao {
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	
	@Override
	public Class findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(Class.class, primaryKey);
	}

	@Override
	public Class findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM JClass WHERE code := ?", Class.class).setParameter("code", uniqueKey).getSingleResult();
	}

	@Override
	public Class createObject(Class object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(Class object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<Class> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM JClass ", Class.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		List<Class> jClasses = entityManager.createQuery("FROM JClass", Class.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(ClassConstant.CLASSES, jClasses);
		map.put(SystemConstant.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public Class updateObject(Class object) throws SQLException {		
		return entityManager.merge(object);
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM JClass", Long.class)				
				.getSingleResult();
	}

}
