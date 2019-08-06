package com.jerotoma.database.impl.users;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.database.dao.users.TeacherDao;


@Repository
@Transactional
public class TeacherDaoImpl implements TeacherDao {
	
	@PersistenceContext 
	private EntityManager entityManager;	
	
	@Override
	public Teacher findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(Teacher.class, primaryKey);
	}

	@Override
	public Teacher findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM Teacher WHERE teacherCode := ?", Teacher.class).setParameter("teacherCode", uniqueKey).getSingleResult();
	}

	@Override
	public Teacher createObject(Teacher object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Boolean deleteObject(Teacher object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<Teacher> loadList(QueryParam queryParam) throws SQLException {
		
		return entityManager.createQuery("FROM Teacher ", Teacher.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return null;
	}

	@Override
	public Teacher updateObject(Teacher object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

}
