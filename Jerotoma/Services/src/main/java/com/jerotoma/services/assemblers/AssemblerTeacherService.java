package com.jerotoma.services.assemblers;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerTeacherService extends AssemblerService<TeacherVO> {
	public List<TeacherVO> loadTeachersByCourseID(Integer courseID) throws SQLException;
	public List<TeacherVO> loadTeachersByAcademicDisciplineID(Integer academicDisciplineID) throws SQLException;
	public List<TeacherVO> findAllTeachers() throws SQLException;
	public List<TeacherVO> search(QueryParam queryParam) throws SQLException;

}
