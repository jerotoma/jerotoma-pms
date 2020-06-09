package com.jerotoma.database.dao.attendances.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AttendanceConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.attendances.ClassAttendance;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.attendances.ClassAttendanceDao;

@Transactional
@Repository
public class ClassAttendanceDaoImpl implements ClassAttendanceDao {

	@PersistenceContext 
	private EntityManager entityManager;

	@Override
	public ClassAttendance findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(ClassAttendance.class, primaryKey);
	}

	@Override
	public ClassAttendance findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM ClassAttendance WHERE name =:name", ClassAttendance.class)
				.setParameter("name", uniqueKey)
				.getSingleResult();
	}

	@Override
	public ClassAttendance createObject(ClassAttendance object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public ClassAttendance updateObject(ClassAttendance object) throws SQLException {
		return entityManager.merge(object);
	}

	@Override
	public Boolean deleteObject(ClassAttendance object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<ClassAttendance> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM ClassAttendance", ClassAttendance.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(), countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		List<ClassAttendance> classAttendances = entityManager.createQuery("FROM ClassAttendance", ClassAttendance.class)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();		
		map.put(AttendanceConstant.CLASS_ATTENDANCES, classAttendances);
		map.put(AttendanceConstant.ATTENDANCE_COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM ClassAttendance", Long.class).getSingleResult();
	}

}
