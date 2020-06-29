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
import com.jerotoma.common.constants.ProgramConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.Program;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.courses.ProgramDao;

@Transactional
@Repository
public class ProgramDaoImpl implements ProgramDao {

	@PersistenceContext 
	private EntityManager entityManager;
	
	
	@Override
	public Program findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(Program.class, primaryKey);
	}

	@Override
	public Program findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM Program WHERE code := ?", Program.class).setParameter("code", uniqueKey).getSingleResult();
	}

	@Override
	public Program createObject(Program object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(Program object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<Program> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM Program ", Program.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		List<Program> programes = entityManager.createQuery("FROM Program", Program.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(ProgramConstant.PROGRAMS, programes);
		map.put(SystemConstant.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public Program updateObject(Program object) throws SQLException {		
		return entityManager.merge(object);
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM Program", Long.class)				
				.getSingleResult();
	}

}