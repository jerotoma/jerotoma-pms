package com.jerotoma.services.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.users.students.StudentClass;
import com.jerotoma.common.models.users.students.StudentClass.StudentClassParam;
import com.jerotoma.services.BaseService;

public interface StudentClassService extends BaseService<StudentClass> {

	List<StudentClass> createBatchObject(List<StudentClass> studentClasses) throws SQLException;

	StudentClass findStudentClass(Integer classId, Integer studentAcademicLevelId) throws SQLException;

	List<StudentClass> updateBatchObject(List<StudentClass> studentClasses) throws SQLException;

	boolean deleteStudentClass(Integer studentAcademicLevelId, Integer jClassId) throws SQLException;

	List<StudentClass> createStudentClassProgress(StudentClassParam studentClassParam) throws SQLException;

}
