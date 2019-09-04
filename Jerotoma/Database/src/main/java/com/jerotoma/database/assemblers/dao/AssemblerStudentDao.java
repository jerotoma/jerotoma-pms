package com.jerotoma.database.assemblers.dao;

import java.util.List;

import com.jerotoma.common.viewobjects.StudentVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerStudentDao extends AssemblerDao<StudentVO>{

	List<StudentVO> loadStudentsByJClassID(Integer classId);

}
