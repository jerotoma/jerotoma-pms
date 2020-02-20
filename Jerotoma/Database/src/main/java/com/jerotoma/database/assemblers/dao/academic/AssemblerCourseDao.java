package com.jerotoma.database.assemblers.dao.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerCourseDao extends AssemblerDao<CourseVO> {
	public List<CourseVO> findCoursesByAcademicYearId(Integer academicYearId) throws SQLException;
	public List<CourseVO> findCoursesByAcademicDisciplineId(Integer academicDisciplineId) throws SQLException;
}
