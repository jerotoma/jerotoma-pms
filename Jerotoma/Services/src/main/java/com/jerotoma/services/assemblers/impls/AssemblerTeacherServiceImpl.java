package com.jerotoma.services.assemblers.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.viewobjects.TeacherVO;
import com.jerotoma.database.assemblers.dao.AssemblerTeacherDao;
import com.jerotoma.services.assemblers.AssemblerTeacherService;

@Service
public class AssemblerTeacherServiceImpl implements AssemblerTeacherService{

	@Autowired AssemblerTeacherDao assemblerTeacherDao;
	
	@Override
	public TeacherVO findObject(Integer primaryKey) throws SQLException {
		return assemblerTeacherDao.findObject(primaryKey);
	}

	@Override
	public TeacherVO findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerTeacherDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<TeacherVO> loadList(QueryParam queryParam) throws SQLException {
		return assemblerTeacherDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerTeacherDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerTeacherDao.countObject();
	}

	@Override
	public List<TeacherVO> loadTeachersByCourseID(Integer courseID) throws SQLException {		
		return assemblerTeacherDao.loadTeachersByCourseID(courseID);
	}

	@Override
	public List<TeacherVO> loadTeachersByAcademicDisciplineID(Integer academicDisciplineID) throws SQLException {
		return assemblerTeacherDao.loadTeachersByAcademicDisciplineID(academicDisciplineID);
	}

}
