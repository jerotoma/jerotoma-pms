package com.jerotoma.services.assemblers.attendances.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.SystemConfigConstant;
import com.jerotoma.common.models.attendances.AttendanceStatus;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.common.viewobjects.SystemConfigVO;
import com.jerotoma.database.assemblers.dao.attendances.AssemblerAttendanceStatusDao;
import com.jerotoma.services.assemblers.attendances.AssemblerAttendanceStatusService;
import com.jerotoma.services.assemblers.systemconfigs.AssemblerSystemConfigService;

@Service
public class AssemblerAttendanceStatusServiceImpl implements AssemblerAttendanceStatusService {
	
	@Autowired AssemblerAttendanceStatusDao assemblerAttendanceStatusDao;
	@Autowired AssemblerSystemConfigService assemblerSystemConfigService;

	@Override
	public List<AttendanceStatus> loadAllList() throws SQLException {
		return assemblerAttendanceStatusDao.loadAllList();
	}

	@Override
	public AttendanceStatus findObject(Integer primaryKey) throws SQLException {
		return assemblerAttendanceStatusDao.findObject(primaryKey);
	}

	@Override
	public AttendanceStatus findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerAttendanceStatusDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<AttendanceStatus> loadList() throws SQLException {
		return assemblerAttendanceStatusDao.loadList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerAttendanceStatusDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerAttendanceStatusDao.countObject();
	}
	
	@Override
	public AttendanceStatus loadAttendanceStatusMarkedAsPresent() throws SQLException {		;
		SystemConfigVO systemConfig = assemblerSystemConfigService.findObjectUniqueKey(
				SystemConfigConstant.GENERAL_CONFIG.ATTENDANCE_STATUS_MARKED_AS_PRESENT.getDbName());
		if (systemConfig == null) {
			return null;
		}
		return assemblerAttendanceStatusDao.findObject(StringUtility.parseInteger(systemConfig.getValue()));
	}

}
