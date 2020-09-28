package com.jerotoma.database.dao.courses;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.academic.StudentAcademicLevel;
import com.jerotoma.database.dao.BaseDao;

public interface StudentAcademicLevelDao extends BaseDao<StudentAcademicLevel> {

	List<StudentAcademicLevel> createBatchObject(List<StudentAcademicLevel> studentAcademicLevels) throws SQLException;

	StudentAcademicLevel findStudentAcademicLevel(Integer studentId, Integer academicLevelId) throws SQLException;

}
