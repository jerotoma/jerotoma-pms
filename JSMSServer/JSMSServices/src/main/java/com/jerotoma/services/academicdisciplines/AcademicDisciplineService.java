package com.jerotoma.services.academicdisciplines;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.academicDisciplines.AcademicDiscipline;
import com.jerotoma.services.BaseService;

public interface AcademicDisciplineService extends BaseService<AcademicDiscipline>{

	public List<AcademicDiscipline> loadList() throws SQLException;

}
