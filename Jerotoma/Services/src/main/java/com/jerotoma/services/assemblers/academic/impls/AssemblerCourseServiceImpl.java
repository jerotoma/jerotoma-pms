package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.CourseVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerCourseDao;
import com.jerotoma.services.assemblers.academic.AssemblerCourseService;

@Service
public class AssemblerCourseServiceImpl implements  AssemblerCourseService {
	
	@Autowired AssemblerCourseDao assemblerCourseDao;
	
	@Override
	public CourseVO findObject(Integer primaryKey) throws SQLException {
		return assemblerCourseDao.findObject(primaryKey);
	}

	@Override
	public CourseVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerCourseDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<CourseVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerCourseDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerCourseDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerCourseDao.countObject();
	}

	@Override
	public List<CourseVO> findCoursesByAcademicLevelId(Integer academicLevelId) throws SQLException {
		return assemblerCourseDao.findCoursesByAcademicLevelId(academicLevelId);
	}

	@Override
	public List<CourseVO> findCoursesByAcademicDisciplineId(Integer academicDisciplineId) throws SQLException {
		return assemblerCourseDao.findCoursesByDepartmentId(academicDisciplineId);
	}

	@Override
	public List<CourseVO> findAllCourses() throws SQLException {
		return assemblerCourseDao.findAllCourses();
	}

	@Override
	public List<CourseVO> findCoursesByProgramAndAcademicLevelIDs(Integer programId, Integer academicLevelId) throws SQLException {
		return assemblerCourseDao.findCoursesByProgramAndAcademicLevelIDs(programId, academicLevelId);
	}

}
