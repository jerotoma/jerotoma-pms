package com.jerotoma.database.assemblers.dao.settings;

import java.sql.SQLException;

import com.jerotoma.common.viewobjects.AppSetttingVO;
import com.jerotoma.database.assemblers.AssemblerDao;

public interface AppSettingAssemblerDao extends AssemblerDao<AppSetttingVO> {
	public AppSetttingVO findObjectByUserID(Integer userID) throws SQLException;

}
