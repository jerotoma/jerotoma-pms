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
import com.jerotoma.common.constants.ProgramConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.ProgramAcademicLevel;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.academic.ProgramAcademicLevelDao;


@Repository
@Transactional
public class ProgramAcademicLevelDaoImpl implements ProgramAcademicLevelDao {

	@PersistenceContext 
	private EntityManager entityManager;
	
	@Override
	public ProgramAcademicLevel findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(ProgramAcademicLevel.class, primaryKey);
	}

	@Override
	public ProgramAcademicLevel findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM ProgramAcademicLevel WHERE code =:code", ProgramAcademicLevel.class).setParameter("code", uniqueKey).getSingleResult();
	}

	@Override
	public ProgramAcademicLevel createObject(ProgramAcademicLevel object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(ProgramAcademicLevel object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<ProgramAcademicLevel> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM ProgramAcademicLevel ", ProgramAcademicLevel.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		List<ProgramAcademicLevel> programes = entityManager.createQuery("FROM ProgramAcademicLevel", ProgramAcademicLevel.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(ProgramConstant.PROGRAMS, programes);
		map.put(SystemConstant.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public ProgramAcademicLevel updateObject(ProgramAcademicLevel object) throws SQLException {		
		return entityManager.merge(object);
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM ProgramAcademicLevel", Long.class)				
				.getSingleResult();
	}

	@Override
	public ProgramAcademicLevel findProgramAcademicLevelByIDs(Integer programId, Integer academicLevelId)
			throws SQLException {
		return entityManager.createQuery("FROM ProgramAcademicLevel WHERE program.id =:programId AND academicLevel.id =:academicLevelId", ProgramAcademicLevel.class)
				.setParameter("programId", programId)
				.setParameter("academicLevelId", academicLevelId)
				.getSingleResult();
	}

}
