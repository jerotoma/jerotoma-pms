package com.jerotoma.services.courses.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.AcademicYear;
import com.jerotoma.database.dao.academic.AcademicYearDao;
import com.jerotoma.services.courses.AcademicYearService;

@Service
@Transactional
public class AcademicYearServiceImpl implements AcademicYearService {
	
	@Autowired AcademicYearDao academicYearDao;
	
	@Override
	public AcademicYear findObject(Integer primaryKey) throws SQLException {
		return academicYearDao.findObject(primaryKey);
	}

	@Override
	public AcademicYear findObjectUniqueKey(String uniqueKey) throws SQLException {
		return academicYearDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public AcademicYear createObject(AcademicYear object) throws SQLException {
		return academicYearDao.createObject(object);
	}

	@Override
	public AcademicYear updateObject(AcademicYear object) throws SQLException {
		return academicYearDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(AcademicYear object) throws SQLException {
		return academicYearDao.deleteObject(object);
	}

	@Override
	public List<AcademicYear> loadList(QueryParam queryParam) throws SQLException {
		return academicYearDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return academicYearDao.loadMapList(queryParam);
	}

}
