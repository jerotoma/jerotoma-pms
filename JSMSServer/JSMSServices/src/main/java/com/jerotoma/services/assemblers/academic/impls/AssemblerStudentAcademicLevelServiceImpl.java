package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.common.viewobjects.StudentAcademicLevelVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerStudentAcademicLevelDao;
import com.jerotoma.services.assemblers.AssemblerStudentService;
import com.jerotoma.services.assemblers.academic.AssemblerAcademicLevelService;
import com.jerotoma.services.assemblers.academic.AssemblerStudentAcademicLevelService;

@Service
public class AssemblerStudentAcademicLevelServiceImpl implements AssemblerStudentAcademicLevelService {
	
	@Autowired AssemblerStudentAcademicLevelDao assemblerStudentAcademicLevelDao;
	@Autowired AssemblerStudentService assemblerStudentService;
	@Autowired AssemblerAcademicLevelService assemblerAcademicLevelService;

	@Override
	public StudentAcademicLevelVO findObject(Integer primaryKey) throws SQLException {
		return assemblerStudentAcademicLevelDao.findObject(primaryKey);
	}

	@Override
	public StudentAcademicLevelVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerStudentAcademicLevelDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<StudentAcademicLevelVO> loadList() throws SQLException {
		return assemblerStudentAcademicLevelDao.loadList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerStudentAcademicLevelDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerStudentAcademicLevelDao.countObject();
	}

	@Override
	public List<StudentAcademicLevelVO> findStudentClassIdByParams(Integer studentId, Integer classId) throws SQLException {
		return assemblerStudentAcademicLevelDao.findStudentClassByParams(studentId, classId);
	}

	@Override
	public List<StudentAcademicLevelVO> findStudentAcademicLevelsByStudentId(Integer studentId) throws SQLException {
		return assemblerStudentAcademicLevelDao.findStudentAcademicLevelsByStudentId(studentId);
	}

	@Override
	public List<ClassVO> findStudentClassesByStudentIdAndAndAcademicLevelID(Integer studentId, Integer academicLevelId) throws SQLException {
		return assemblerStudentAcademicLevelDao.findStudentClassesByStudentIdAndAndAcademicLevelID(studentId, academicLevelId);
	}

	@Override
	public List<ClassVO> findTeacherClassesByTeacherId(Integer teacherID) throws SQLException {
		return assemblerStudentAcademicLevelDao.findTeacherClassesByTeacherId(teacherID);
	}
}
