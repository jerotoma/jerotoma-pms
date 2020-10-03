package com.jerotoma.services.courses;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.academic.StudentClass;
import com.jerotoma.services.BaseService;

public interface StudentClassService extends BaseService<StudentClass> {

	List<StudentClass> createBatchObject(List<StudentClass> studentClasses) throws SQLException;

	StudentClass findStudentClass(Integer classId, Integer studentAcademicLevelId, Integer academicYearId) throws SQLException;

	List<StudentClass> updateBatchObject(List<StudentClass> studentClasses) throws SQLException;

}
