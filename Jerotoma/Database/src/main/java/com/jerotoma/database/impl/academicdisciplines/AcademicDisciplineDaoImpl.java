package com.jerotoma.database.impl.academicdisciplines;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AcademicDisciplineConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.fieldOfStudies.AcademicDiscipline;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.academicdisciplines.AcademicDisciplineDao;

@Repository
@Transactional
public class AcademicDisciplineDaoImpl implements AcademicDisciplineDao{

	@PersistenceContext 
	private EntityManager entityManager;
	
	@Override
	public AcademicDiscipline findObject(Integer primaryKey) throws SQLException {		
		return entityManager.find(AcademicDiscipline.class, primaryKey);
	}

	@Override
	public AcademicDiscipline findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM AcademicDiscipline WHERE code := ?", AcademicDiscipline.class).setParameter("code", uniqueKey).getSingleResult();
	}

	@Override
	public AcademicDiscipline createObject(AcademicDiscipline object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Boolean deleteObject(AcademicDiscipline object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<AcademicDiscipline> loadList(QueryParam queryParam) throws SQLException {
		Long countResults = countObject();
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		return entityManager.createQuery("FROM AcademicDiscipline ", AcademicDiscipline.class)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		List<AcademicDiscipline> fieldOfStudies =  entityManager.createQuery("FROM AcademicDiscipline ", AcademicDiscipline.class)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();
		
		map.put(AcademicDisciplineConstant.ACADEMIC_DISCIPLINES, fieldOfStudies);
		map.put(AcademicDisciplineConstant.ACADEMIC_DISCIPLINE_COUNTS, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		map.put(SystemConstant.SUCCESS, true);
		
		return map;
	}

	@Override
	public AcademicDiscipline updateObject(AcademicDiscipline object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM AcademicDiscipline ", Long.class)
				.getSingleResult();
	}
}