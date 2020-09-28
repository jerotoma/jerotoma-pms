package com.jerotoma.services.students;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.academic.StudentAcademicLevel;
import com.jerotoma.services.BaseService;

public interface StudentAcademicLevelService extends BaseService<StudentAcademicLevel> {

	public StudentAcademicLevel findStudentAcademicLevel(Integer studentId, Integer academicLevelId) throws SQLException;

	List<StudentAcademicLevel> createBatchObject(List<StudentAcademicLevel> studentAcademicLevel) throws SQLException;
}
