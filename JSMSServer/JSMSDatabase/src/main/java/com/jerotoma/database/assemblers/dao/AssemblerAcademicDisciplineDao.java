package com.jerotoma.database.assemblers.dao;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.AcademicDisciplineVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerAcademicDisciplineDao extends AssemblerDao<AcademicDisciplineVO>{	
	public List<AcademicDisciplineVO> findAcademicDisciplinesByCourseId(Integer courseId) throws SQLException;

}
