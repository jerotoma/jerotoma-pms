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
import com.jerotoma.common.constants.JClassConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.JClass;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.courses.JClassDao;

@Repository
@Transactional
public class JClassDaoImpl  implements JClassDao {
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	
	@Override
	public JClass findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(JClass.class, primaryKey);
	}

	@Override
	public JClass findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM JClass WHERE code := ?", JClass.class).setParameter("code", uniqueKey).getSingleResult();
	}

	@Override
	public JClass createObject(JClass object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(JClass object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<JClass> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM JClass ", JClass.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		List<JClass> jClasses = entityManager.createQuery("FROM JClass", JClass.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(JClassConstant.JCLASSES, jClasses);
		map.put(SystemConstant.SYSTEM_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public JClass updateObject(JClass object) throws SQLException {		
		return entityManager.merge(object);
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM JClass", Long.class)				
				.getSingleResult();
	}

}
