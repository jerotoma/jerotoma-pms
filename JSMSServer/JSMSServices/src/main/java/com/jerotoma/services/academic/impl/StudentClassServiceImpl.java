package com.jerotoma.services.academic.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.AcademicConstants;
import com.jerotoma.common.constants.ErrorMessageConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.exceptions.FieldRequiredException;
import com.jerotoma.common.models.academic.ScoreStanding;
import com.jerotoma.common.models.users.students.StudentClass;
import com.jerotoma.common.models.users.students.StudentClass.StudentClassParam;
import com.jerotoma.common.models.users.students.StudentClass.StudentClassProgressParam;
import com.jerotoma.database.dao.academic.StudentClassDao;
import com.jerotoma.services.academic.ScoreStandingService;
import com.jerotoma.services.academic.StudentClassService;
import com.jerotoma.services.utils.ServiceUtil;

@Service
@Transactional
public class StudentClassServiceImpl implements StudentClassService {
	
	@Autowired StudentClassDao studenClassDao;
	@Autowired ScoreStandingService scoreStandingService;

	@Override
	public StudentClass findObject(Integer primaryKey) throws SQLException {
		return ServiceUtil.getEntity(studenClassDao.findById(primaryKey));
	}

	@Override
	public StudentClass findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException(ErrorMessageConstant.METHOD_NOT_IMPLEMENTED);
	}

	@Override
	public StudentClass createObject(StudentClass object) throws SQLException {
		return studenClassDao.save(object);
	}

	@Override
	public StudentClass updateObject(StudentClass object) throws SQLException {
		return studenClassDao.save(object);
	}

	@Override
	public Boolean deleteObject(StudentClass object) throws SQLException {
		studenClassDao.delete(object);
		return true;
	}

	@Override
	public List<StudentClass> loadList(QueryParam queryParam) throws SQLException {
		if (queryParam == null) {
			return studenClassDao.findAll();
		}		
		return studenClassDao.findAll(ServiceUtil.getPageable(queryParam)).toList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		Page<StudentClass> pageStudentClass = studenClassDao.findAll(ServiceUtil.getPageable(queryParam));		
		map.put(AcademicConstants.STUDENT_CLASSES, pageStudentClass.toList());
		map.put(SystemConstant.COUNT, pageStudentClass.getTotalElements());
		map.put(SystemConstant.PAGE_COUNT, pageStudentClass.getTotalPages());			
		return map;
	}

	@Override
	public List<StudentClass> createBatchObject(List<StudentClass> studentClasses) throws SQLException {
		List<StudentClass> studentClassList = new ArrayList<>();
		for (StudentClass studentClass : studentClasses) {			
			studentClassList.add(studenClassDao.save(studentClass));			
		}
		return studentClassList;
	}

	@Override
	public StudentClass findStudentClass(Integer classId, Integer studentAcademicLevelId)
			throws SQLException {
		return studenClassDao.findStudentClass(classId, studentAcademicLevelId);
	}

	@Override
	public List<StudentClass> updateBatchObject(List<StudentClass> studentClasses) throws SQLException {
		List<StudentClass> studentClassList = new ArrayList<>();
		for (StudentClass studentClass : studentClasses) {			
			studentClassList.add(studenClassDao.save(studentClass));			
		}
		return studentClassList;
	}

	@Override
	public boolean deleteStudentClass(Integer studentAcademicLevelId, Integer classId) throws SQLException {
		studenClassDao.deleteStudentClass(classId, studentAcademicLevelId);
		return true;
	}

	@Override
	public List<StudentClass> createStudentClassProgress(StudentClassParam studentClassParam) throws SQLException {
		List<StudentClass> studentClassList = new ArrayList<>();
		
		for (StudentClassProgressParam param: studentClassParam.getStudentClassProgressParams()) {
			StudentClass studentClass = findStudentClass(studentClassParam.getClassId(), param.getStudentAcademicLevelId());
			ScoreStanding scoreStanding = scoreStandingService.findObject(param.getScoreStandingId());
			validateScoreStanding(scoreStanding, param.getScore());
			studentClass.setScoreStanding(scoreStanding);
			studentClass.setCompletionStatusId(param.getStatusId());
			studentClass.setScore(param.getScore());			
			studentClassList.add(studentClass);
		}
		
		return updateBatchObject(studentClassList);
	}

	private void validateScoreStanding(ScoreStanding scoreStanding, Double score) {
		if (scoreStanding.getMinScore() <= score && scoreStanding.getMaxScore() >= score) {
			return;
		}
		throw new FieldRequiredException(String.format(ErrorMessageConstant.INVALID_SCORE_STANDING, String.valueOf(score)));		
	}
}
