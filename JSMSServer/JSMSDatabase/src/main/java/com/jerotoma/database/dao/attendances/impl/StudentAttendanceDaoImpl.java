package com.jerotoma.database.dao.attendances.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AttendanceConstant;
import com.jerotoma.common.models.attendances.StudentAttendance;
import com.jerotoma.database.dao.attendances.StudentAttendanceDao;

@Transactional
@Repository
public class StudentAttendanceDaoImpl implements StudentAttendanceDao {
	@PersistenceContext 
	private EntityManager entityManager;

	@Override
	public StudentAttendance findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(StudentAttendance.class, primaryKey);
	}

	@Override
	public StudentAttendance findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM StudentAttendance WHERE name =:name", StudentAttendance.class)
				.setParameter("name", uniqueKey)
				.getSingleResult();
	}

	@Override
	public StudentAttendance createObject(StudentAttendance object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public StudentAttendance updateObject(StudentAttendance object) throws SQLException {
		return entityManager.merge(object);
	}

	@Override
	public Boolean deleteObject(StudentAttendance object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<StudentAttendance> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM StudentAttendance", StudentAttendance.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		List<StudentAttendance> studentAttendances = entityManager.createQuery("FROM StudentAttendance", StudentAttendance.class).getResultList();		
		map.put(AttendanceConstant.STUDENT_ATTENDANCES, studentAttendances);
		
		return map;
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM StudentAttendance", Long.class).getSingleResult();
	}
	
	@Override
	public List<StudentAttendance> createBatch(List<StudentAttendance> studentAttendances)  throws SQLException {
		int entityCount = studentAttendances.size();		
		List<StudentAttendance> procccessedStudentAttendances = new ArrayList<StudentAttendance>();		
		for (int i = 0; i < entityCount; i++) {			        			 
	        StudentAttendance studentAttendance = studentAttendances.get(i);
	        if (studentAttendance.getId() != null) {	        	
	        	 procccessedStudentAttendances.add(updateObject(studentAttendance));
	        } else {
	        	entityManager.persist(studentAttendance);
	        	procccessedStudentAttendances.add(findObject(studentAttendance.getId()));
	        }
	    }		
		return procccessedStudentAttendances;		
	}

	@Override
	public List<StudentAttendance> updateBatch(List<StudentAttendance> studentAttendances) throws SQLException {
		int entityCount = studentAttendances.size();		
		List<StudentAttendance> processedStudentAttendances = new ArrayList<StudentAttendance>();			 
		for (int i = 0; i < entityCount; i++) {			       			 
	        StudentAttendance studentAttendance = studentAttendances.get(i);			         
	        processedStudentAttendances.add(entityManager.merge(studentAttendance));
	    }		
		return processedStudentAttendances;
	}

	@Override
	public StudentAttendance getStudentAttendanceByStudentIdAndClassAttendanceId(Integer studentId,
			Integer classAttendanceId) throws SQLException {
		return entityManager.createQuery("FROM StudentAttendance WHERE student.id =:studentId AND classAttendance.id =:classAttendanceId", StudentAttendance.class)
				.setParameter("studentId", studentId)
				.setParameter("classAttendanceId", classAttendanceId)
				.getSingleResult();
	}
}
