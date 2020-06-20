package com.jerotoma.database.assemblers.dao;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerTeacherDao extends AssemblerDao<TeacherVO>{
	List<TeacherVO> loadTeachersByAcademicDisciplineID(Integer academicDisciplineID) throws SQLException;
	List<TeacherVO> loadTeachersByCourseID(Integer courseID) throws SQLException;
	List<TeacherVO> findAllTeachers() throws SQLException;
	List<TeacherVO> search(QueryParam queryParam) throws SQLException;
}
