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
import com.jerotoma.common.constants.CourseConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.Course;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.courses.CourseDao;

@Repository
@Transactional
public class CourseDaoImpl implements CourseDao {
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	
	@Override
	public Course findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(Course.class, primaryKey);
	}

	@Override
	public Course findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM Course WHERE code := ?", Course.class).setParameter("code", uniqueKey).getSingleResult();
	}

	@Override
	public Course createObject(Course object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(Course object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<Course> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM Course ", Course.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		List<Course> courses = entityManager.createQuery("FROM Course", Course.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(CourseConstant.COURSES, courses);
		map.put(SystemConstant.SYSTEM_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public Course updateObject(Course object) throws SQLException {		
		return entityManager.merge(object);
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM Course", Long.class)				
				.getSingleResult();
	}

}
