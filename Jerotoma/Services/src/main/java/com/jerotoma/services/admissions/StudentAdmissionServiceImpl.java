package com.jerotoma.services.admissions;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.admissions.StudentAdmission;
import com.jerotoma.database.dao.admissions.StudentAdmissionDao;

@Service
@Transactional
public class StudentAdmissionServiceImpl implements StudentAdmissionService {

	@Autowired StudentAdmissionDao studentAdmissionDao;
	
	@Override
	public StudentAdmission findObject(Integer primaryKey) throws SQLException {
		return studentAdmissionDao.findObject(primaryKey);
	}

	@Override
	public StudentAdmission findObjectUniqueKey(String uniqueKey) throws SQLException {
		return studentAdmissionDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public StudentAdmission createObject(StudentAdmission object) throws SQLException {
		return studentAdmissionDao.createObject(object);
	}

	@Override
	public StudentAdmission updateObject(StudentAdmission object) throws SQLException {
		return studentAdmissionDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(StudentAdmission object) throws SQLException {
		return studentAdmissionDao.deleteObject(object);
	}

	@Override
	public List<StudentAdmission> loadList(QueryParam queryParam) throws SQLException {
		return studentAdmissionDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return studentAdmissionDao.loadMapList(queryParam);
	}

}
