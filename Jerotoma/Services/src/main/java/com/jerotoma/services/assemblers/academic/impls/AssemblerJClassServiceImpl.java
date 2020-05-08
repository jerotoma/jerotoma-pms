package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.ClassVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerJClassDao;
import com.jerotoma.services.assemblers.academic.AssemblerClassService;

@Service
public class AssemblerJClassServiceImpl  implements AssemblerClassService {
	
	@Autowired AssemblerJClassDao assemblerJClassDao;
	
	@Override
	public ClassVO findObject(Integer primaryKey) throws SQLException {
		return assemblerJClassDao.findObject(primaryKey);
	}

	@Override
	public ClassVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerJClassDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<ClassVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerJClassDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerJClassDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerJClassDao.countObject();
	}

	@Override
	public List<ClassVO> loadJClassesByAcademicYear(Integer academicYearId) throws SQLException {
		return assemblerJClassDao.loadJClassesByAcademicYear(academicYearId);
	}

	@Override
	public List<ClassVO> loadStudentUnregisteredJClassesByAcademicYear(Integer academicYearId, Integer studentId)
			throws SQLException {
		
		return assemblerJClassDao.loadStudentUnregisteredJClassesByAcademicYear(academicYearId, studentId);
	}

	@Override
	public List<ClassVO> loadStudentJClassesByAcademicYear(Integer studentId, Integer academicYearId)
			throws SQLException {
		return assemblerJClassDao.loadStudentJClassesByAcademicYear(studentId, academicYearId);
	}

}
