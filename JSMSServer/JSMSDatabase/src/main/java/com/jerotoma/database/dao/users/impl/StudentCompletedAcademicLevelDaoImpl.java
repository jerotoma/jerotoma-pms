package com.jerotoma.database.dao.users.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.CompletedAcademicLevel;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.database.dao.users.StudentCompletedAcademicLevelDao;


@Repository
@Transactional
public class StudentCompletedAcademicLevelDaoImpl implements StudentCompletedAcademicLevelDao {

	@PersistenceContext 
	private EntityManager entityManager;

	@Override
	public CompletedAcademicLevel findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(CompletedAcademicLevel.class, primaryKey);
	}

	@Override
	public CompletedAcademicLevel findObjectUniqueKey(String uniqueKey) throws SQLException {
		if (!StringUtility.isNumeric(uniqueKey)) {
			return null;
		}		
		return findObject(Integer.valueOf(uniqueKey));
	}

	@Override
	public CompletedAcademicLevel createObject(CompletedAcademicLevel object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public CompletedAcademicLevel updateObject(CompletedAcademicLevel object) throws SQLException {
		return entityManager.merge(object);
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
		throw new RuntimeException("Not implemented for now");
	}

	@Override
	public Long countObject() throws SQLException {
		throw new RuntimeException("Not implemented for now");
	}
}
