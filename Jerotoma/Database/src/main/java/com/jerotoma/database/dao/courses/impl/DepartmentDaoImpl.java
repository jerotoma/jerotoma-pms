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
import com.jerotoma.common.constants.DepartmentConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.Department;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.courses.DepartmentDao;

@Repository
@Transactional
public class DepartmentDaoImpl implements DepartmentDao {
	
	@PersistenceContext 
	private EntityManager entityManager;

	@Override
	public Department findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(Department.class, primaryKey);
	}

	@Override
	public Department findObjectUniqueKey(String uniqueKey) throws SQLException {
		return findObject(Integer.valueOf(uniqueKey));
	}

	@Override
	public Department createObject(Department object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Department updateObject(Department object) throws SQLException {		
		return entityManager.merge(object);
	}

	@Override
	public Boolean deleteObject(Department object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<Department> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM Department", Department.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		List<Department> departments = entityManager.createQuery("FROM Department", Department.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(DepartmentConstant.DEPARTMENTS, departments);
		map.put(SystemConstant.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM Department", Long.class)				
				.getSingleResult();
	}

}
