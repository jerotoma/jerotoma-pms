package com.jerotoma.database.dao.academic.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AcademicConstants;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.users.students.StudentAcademicLevel;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.academic.StudentAcademicLevelDao;

@Repository
@Transactional
public class StudentAcademicLevelDaoImpl implements StudentAcademicLevelDao {
	
	@PersistenceContext 
	private EntityManager entityManager;
	
	@Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
	private int batchSize;

	@Override
	public StudentAcademicLevel findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(StudentAcademicLevel.class, primaryKey);
	}

	@Override
	public StudentAcademicLevel findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM StudentAcademicLevel WHERE code = ?", StudentAcademicLevel.class)
				.setParameter("code", uniqueKey)
				.getSingleResult();
	}

	@Override
	public StudentAcademicLevel createObject(StudentAcademicLevel object) throws SQLException {
		entityManager.persist(object); 
		return findObject(object.getId());			
	}

	@Override
	public StudentAcademicLevel updateObject(StudentAcademicLevel object) throws SQLException {
		return entityManager.merge(object);
	}

	@Override
	public Boolean deleteObject(StudentAcademicLevel object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<StudentAcademicLevel> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM StudentAcademicLevel", StudentAcademicLevel.class)				
				.getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		List<StudentAcademicLevel> studentAcademicLevels = entityManager.createQuery("FROM StudentAcademicLevel", StudentAcademicLevel.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(AcademicConstants.STUDENT_CLASSES, studentAcademicLevels);
		map.put(SystemConstant.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {		
		return entityManager.createQuery("SELECT count(*) FROM StudentAcademicLevel", Long.class)				
				.getSingleResult();
	}

	@Override
	public List<StudentAcademicLevel> createBatchObject(List<StudentAcademicLevel> studentAcademicLevels) throws SQLException {
		List<StudentAcademicLevel> studentAcademicLevelList = new ArrayList<>();
		int i = 0;
		for (StudentAcademicLevel studentAcademicLevel : studentAcademicLevels) {
			entityManager.persist(studentAcademicLevel);
			studentAcademicLevelList.add(studentAcademicLevel);
			i++;
		    if (i % batchSize == 0) {
		      // Flush a batch of inserts and release memory.
		      entityManager.flush();
		      entityManager.clear();
		    }
		}
		return studentAcademicLevelList;
	}

	@Override
	public StudentAcademicLevel findStudentAcademicLevel(Integer studentId, Integer academicLevelId, Integer academicYearId)
			throws SQLException {
		return entityManager.createQuery("FROM StudentAcademicLevel WHERE academicLevel.id =:academicLevelId AND student.id =:studentId AND academicYear.id =:academicYearId", StudentAcademicLevel.class)
				.setParameter("academicLevelId", academicLevelId)
				.setParameter("studentId", studentId)
				.setParameter("academicYearId", academicYearId)
				.getSingleResult();
	}
}
