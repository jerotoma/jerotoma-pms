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
import com.jerotoma.common.constants.AcademicConstants;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.StudentClass;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.courses.StudentClassDao;

@Repository
@Transactional
public class StudentClassDaoImpl implements StudentClassDao {

	@PersistenceContext 
	private EntityManager entityManager;

	@Override
	public StudentClass findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(StudentClass.class, primaryKey);
	}

	@Override
	public StudentClass findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM StudentClass WHERE code = ?", StudentClass.class)
				.setParameter("code", uniqueKey)
				.getSingleResult();
	}

	@Override
	public StudentClass createObject(StudentClass object) throws SQLException {
			entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public StudentClass updateObject(StudentClass object) throws SQLException {
		return entityManager.merge(object);
	}

	@Override
	public Boolean deleteObject(StudentClass object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<StudentClass> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM StudentClass", StudentClass.class)				
				.getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		List<StudentClass> studentClasses = entityManager.createQuery("FROM StudentClass", StudentClass.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(AcademicConstants.STUDENT_CLASSES, studentClasses);
		map.put(SystemConstant.SYSTEM_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {		
		return entityManager.createQuery("SELECT count(*) FROM StudentClass", Long.class)				
				.getSingleResult();
	}
}
