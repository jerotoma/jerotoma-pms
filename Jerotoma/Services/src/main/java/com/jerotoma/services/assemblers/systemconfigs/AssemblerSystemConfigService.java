package com.jerotoma.services.assemblers.systemconfigs;

import java.sql.SQLException;

import com.jerotoma.common.viewobjects.SystemConfigVO;
import com.jerotoma.common.viewobjects.ThemeVO;
import com.jerotoma.services.AssemblerService;

public interface AssemblerSystemConfigService extends AssemblerService<SystemConfigVO> {	
	public ThemeVO getCurrentThemeByUserID(Integer userId);
	public Long countObject() throws SQLException;
	public ThemeVO getCurrentTheme();	

}
