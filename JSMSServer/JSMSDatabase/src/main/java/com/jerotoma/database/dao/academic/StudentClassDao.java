package com.jerotoma.database.dao.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.users.students.StudentClass;
import com.jerotoma.database.dao.BaseDao;

public interface StudentClassDao extends BaseDao<StudentClass> {

	List<StudentClass> createBatchObject(List<StudentClass> studentClasses) throws SQLException;

	StudentClass findStudentClass(Integer classId, Integer studentAcademicLevelId) throws SQLException;

	List<StudentClass> updateBatchObject(List<StudentClass> studentClasses) throws SQLException;

	boolean deleteStudentClass(Integer studentAcademicLevelId, Integer jClassId) throws SQLException;

}
