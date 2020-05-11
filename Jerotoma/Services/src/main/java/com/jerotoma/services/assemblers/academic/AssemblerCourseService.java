package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerCourseService extends AssemblerService<CourseVO> {
	public List<CourseVO> findCoursesByAcademicYearId(Integer academicYearId) throws SQLException;
	public List<CourseVO> findCoursesByAcademicDisciplineId(Integer academicDisciplineId) throws SQLException;
	public List<CourseVO> findAllCourses() throws SQLException;

}
