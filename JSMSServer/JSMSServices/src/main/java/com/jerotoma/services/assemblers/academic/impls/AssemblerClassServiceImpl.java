package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.UserConstant.USER_TYPE;
import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.common.viewobjects.StudentAcademicLevelClass;
import com.jerotoma.common.viewobjects.UserVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerJClassDao;
import com.jerotoma.services.assemblers.AssemblerStudentService;
import com.jerotoma.services.assemblers.academic.AssemblerClassService;
import com.jerotoma.services.users.UserService;

@Service
public class AssemblerClassServiceImpl  implements AssemblerClassService {
	
	@Autowired AssemblerJClassDao assemblerClassDao;
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

	@Override
	public List<ClassVO> loadTeacherClassList(Integer userId) throws SQLException {
		UserVO user = userService.getUserVOByUserId(userId);
		if (user.getUserType() == USER_TYPE.TEACHER) {
			return assemblerClassDao.loadTeacherClassListByTeacherId(user.getId());
		}
		return null;		
	}

	@Override
	public List<ClassVO> loadStudentClasses(Integer studentId, Integer academicLevelId, Integer academicYearId)
			throws SQLException {
		
		return assemblerClassDao.loadStudentClasses(studentId, academicLevelId, academicYearId);
	}

	@Override
	public List<StudentAcademicLevelClass> loadAllStudentAcademicLevelsClassList(Integer userId)
			throws SQLException {		
		UserVO user = userService.getUserVOByUserId(userId);
		if (user.getUserType() == USER_TYPE.STUDENT) {			
			return assemblerClassDao.loadAllStudentAcademicLevelsClassList(user.getId());
		}		
		return null;		
	}

	@Override
	public List<ClassVO> loadClassesByTeacherClassParams(Integer teacherId, Integer programId, Integer academicLevelId,
			Integer academicYearId) {
		return assemblerClassDao.loadClassesByTeacherClassParams(teacherId, programId, academicLevelId,	academicYearId);
	}

}
