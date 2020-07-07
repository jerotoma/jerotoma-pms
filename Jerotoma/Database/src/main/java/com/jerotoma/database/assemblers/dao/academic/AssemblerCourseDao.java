package com.jerotoma.database.assemblers.dao.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerCourseDao extends AssemblerDao<CourseVO> {
	public List<CourseVO> findCoursesByAcademicLevelId(Integer academicLevelId) throws SQLException;
	public List<CourseVO> findCoursesByDepartmentId(Integer academicDisciplineId) throws SQLException;
	public List<CourseVO> findAllCourses() throws SQLException;
	public List<CourseVO> findCoursesByProgramAndAcademicLevelIDs(Integer programId, Integer academicLevelId) throws SQLException;
}
