package com.jerotoma.services.academicdisciplines;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academicDisciplines.AcademicDiscipline;
import com.jerotoma.database.dao.academicdisciplines.AcademicDisciplineDao;

@Service
@Transactional
public class AcademicDisciplineServiceImpl implements AcademicDisciplineService{

	@Autowired AcademicDisciplineDao academicDisciplineDao;
	
	@Override
	public AcademicDiscipline findObject(Integer primaryKey) throws SQLException {
		return academicDisciplineDao.findObject(primaryKey);
	}

	@Override
	public AcademicDiscipline findObjectUniqueKey(String uniqueKey) throws SQLException {
		return academicDisciplineDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public AcademicDiscipline createObject(AcademicDiscipline object) throws SQLException {
		return academicDisciplineDao.createObject(object);
	}

	@Override
	public AcademicDiscipline updateObject(AcademicDiscipline object) throws SQLException {
		return academicDisciplineDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(AcademicDiscipline object) throws SQLException {
		return academicDisciplineDao.deleteObject(object);
	}

	@Override
	public List<AcademicDiscipline> loadList(QueryParam queryParam) throws SQLException {
		return academicDisciplineDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return academicDisciplineDao.loadMapList(queryParam);
	}

	@Override
	public List<AcademicDiscipline> loadList() throws SQLException {		
		return academicDisciplineDao.loadList();
	}

}
