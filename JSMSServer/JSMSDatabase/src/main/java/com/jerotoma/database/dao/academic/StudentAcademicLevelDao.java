package com.jerotoma.database.dao.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.users.students.StudentAcademicLevel;
import com.jerotoma.database.dao.BaseDao;

public interface StudentAcademicLevelDao extends BaseDao<StudentAcademicLevel> {

	List<StudentAcademicLevel> createBatchObject(List<StudentAcademicLevel> studentAcademicLevels) throws SQLException;

	StudentAcademicLevel findStudentAcademicLevel(Integer studentId, Integer academicLevelId, Integer academicYearId) throws SQLException;
}
