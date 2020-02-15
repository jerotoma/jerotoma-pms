package com.jerotoma.database.dao.academicdisciplines;

import java.sql.SQLException;
import java.util.List;

import com.jerotoma.common.models.academicDisciplines.AcademicDiscipline;
import com.jerotoma.database.dao.BaseDao;

public interface AcademicDisciplineDao extends BaseDao<AcademicDiscipline> {
	public List<AcademicDiscipline> loadList() throws SQLException;

}
