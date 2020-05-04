package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.JClassVO;
import com.jerotoma.database.assemblers.dao.academic.AssemblerJClassDao;
import com.jerotoma.services.assemblers.academic.AssemblerJClassService;

@Service
public class AssemblerJClassServiceImpl  implements AssemblerJClassService {
	
	@Autowired AssemblerJClassDao assemblerJClassDao;
	
	@Override
	public JClassVO findObject(Integer primaryKey) throws SQLException {
		return assemblerJClassDao.findObject(primaryKey);
	}

	@Override
	public JClassVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerJClassDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<JClassVO> loadList(QueryParam queryParam) throws SQLException {
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
	public List<JClassVO> loadJClassesByAcademicYear(Integer academicYearId) throws SQLException {
		return assemblerJClassDao.loadJClassesByAcademicYear(academicYearId);
	}

	@Override
	public List<JClassVO> loadStudentUnregisteredJClassesByAcademicYear(Integer academicYearId, Integer studentId)
			throws SQLException {
		
		return assemblerJClassDao.loadStudentUnregisteredJClassesByAcademicYear(academicYearId, studentId);
	}

	@Override
	public List<JClassVO> loadStudentJClassesByAcademicYear(Integer studentId, Integer academicYearId)
			throws SQLException {
		return assemblerJClassDao.loadStudentJClassesByAcademicYear(studentId, academicYearId);
	}

}
