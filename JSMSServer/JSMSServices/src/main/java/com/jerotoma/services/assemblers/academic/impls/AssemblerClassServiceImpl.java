package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.common.viewobjects.StudentAcademicLevelClass;
import com.jerotoma.database.assemblers.dao.academic.AssemblerClassDao;
import com.jerotoma.services.assemblers.AssemblerStudentService;
import com.jerotoma.services.assemblers.academic.AssemblerClassService;
import com.jerotoma.services.users.UserService;

@Service
public class AssemblerClassServiceImpl  implements AssemblerClassService {
	
	@Autowired AssemblerClassDao assemblerClassDao;
	@Autowired UserService userService;
	@Autowired AssemblerStudentService assemblerStudentService;
	
	@Override
	public ClassVO findObject(Integer primaryKey) throws SQLException {
		return assemblerClassDao.findObject(primaryKey);
	}

	@Override
	public ClassVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerClassDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<ClassVO> loadList() throws SQLException {
		return assemblerClassDao.loadList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerClassDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerClassDao.countObject();
	}

	@Override
	public List<ClassVO> loadClassesByAcademicYear(Integer academicYearId) throws SQLException {
		return assemblerClassDao.loadClassesByAcademicYear(academicYearId);
	}

	@Override
	public List<ClassVO> loadStudentUnregisteredClassesByAcademicYear(Integer studentId, Integer academicLevelId, Integer academicYearId)
			throws SQLException {		
		return assemblerClassDao.loadStudentUnregisteredClassesByAcademicYear(studentId, academicLevelId, academicYearId);
	}

	@Override
	public List<ClassVO> loadStudentClassesByAcademicYear(Integer studentId, Integer academicYearId)
			throws SQLException {
		return assemblerClassDao.loadStudentClassesByAcademicYear(studentId, academicYearId);
	}

	@Override
	public ClassVO findClassByUniqueParams(Integer teacherId, Integer courseId, Integer academicYearId)
			throws SQLException {
		return assemblerClassDao.findClassByUniqueParams(teacherId, courseId, academicYearId);
	}

	@Override
	public List<ClassVO> loadClassesByParams(Integer programId, Integer academicLevelrId, Integer academicYearId)
			throws SQLException {
		return assemblerClassDao.loadClassesByParams(programId, academicLevelrId, academicYearId);
	}

	@Transactional
	@Override
	public List<ClassVO> loadTeacherClassList(Integer teacherId) throws SQLException {
		return assemblerClassDao.loadTeacherClassListByTeacherId(teacherId);		
	}

	@Override
	public List<ClassVO> loadStudentClasses(Integer studentId, Integer academicLevelId, Integer academicYearId)
			throws SQLException {
		
		return assemblerClassDao.loadStudentClasses(studentId, academicLevelId, academicYearId);
	}

	@Transactional
	@Override
	public List<StudentAcademicLevelClass> loadAllStudentAcademicLevelsClassList(Integer studentId)
			throws SQLException {		
		return assemblerClassDao.loadAllStudentAcademicLevelsClassList(studentId);		
	}

	@Override
	public List<ClassVO> loadClassesByTeacherClassParams(Integer teacherId, Integer programId, Integer academicLevelId,
			Integer academicYearId) {
		return assemblerClassDao.loadClassesByTeacherClassParams(teacherId, programId, academicLevelId,	academicYearId);
	}

}
