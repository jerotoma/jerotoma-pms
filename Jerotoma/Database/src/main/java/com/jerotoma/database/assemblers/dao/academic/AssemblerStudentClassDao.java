package com.jerotoma.database.assemblers.dao.academic;

import com.jerotoma.common.viewobjects.StudentClassVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AssemblerStudentClassDao  extends AssemblerDao<StudentClassVO> {

	boolean doesStudentClassRecordExist(Integer classId, Integer studentAcademicLevelId);

}
