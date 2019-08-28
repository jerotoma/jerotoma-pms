package com.jerotoma.services.admissions;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.admissions.StudentCourseAdmission;
import com.jerotoma.database.dao.admissions.StudentCourseAdmissionDao;

@Service
@Transactional
public class StudentAdmissionServiceImpl implements StudentAdmissionService {

	@Autowired StudentCourseAdmissionDao studentAdmissionDao;
	
	@Override
	public StudentCourseAdmission findObject(Integer primaryKey) throws SQLException {
		return studentAdmissionDao.findObject(primaryKey);
	}

	@Override
	public StudentCourseAdmission findObjectUniqueKey(String uniqueKey) throws SQLException {
		return studentAdmissionDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public StudentCourseAdmission createObject(StudentCourseAdmission object) throws SQLException {
		return studentAdmissionDao.createObject(object);
	}

	@Override
	public StudentCourseAdmission updateObject(StudentCourseAdmission object) throws SQLException {
		return studentAdmissionDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(StudentCourseAdmission object) throws SQLException {
		return studentAdmissionDao.deleteObject(object);
	}

	@Override
	public List<StudentCourseAdmission> loadList(QueryParam queryParam) throws SQLException {
		return studentAdmissionDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return studentAdmissionDao.loadMapList(queryParam);
	}

}
