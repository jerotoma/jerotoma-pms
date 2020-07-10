package com.jerotoma.database.dao.courses;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.academic.StudentClass;
import com.jerotoma.database.dao.BaseDao;

public interface StudentClassDao extends BaseDao<StudentClass> {

	List<StudentClass> createBatchObject(List<StudentClass> studentClasses) throws SQLException;

}
