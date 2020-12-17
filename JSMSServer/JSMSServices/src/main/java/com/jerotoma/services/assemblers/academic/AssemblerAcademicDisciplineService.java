package com.jerotoma.services.assemblers.academic;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.viewobjects.AcademicDisciplineVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerAcademicDisciplineService extends AssemblerService<AcademicDisciplineVO>{	
	public List<AcademicDisciplineVO> findAcademicDisciplinesByCourseId(Integer courseId) throws SQLException;

}
