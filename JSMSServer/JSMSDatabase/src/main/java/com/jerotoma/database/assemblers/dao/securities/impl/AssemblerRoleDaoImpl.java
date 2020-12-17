package com.jerotoma.database.assemblers.dao.securities.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.database.assemblers.dao.securities.AssemblerRoleDao;

public class AssemblerRoleDaoImpl extends JdbcDaoSupport implements AssemblerRoleDao {

	@Override
	public Role findObject(Integer primaryKey) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role findObjectUniqueKey(String uniqueKey) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> loadList() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countObject() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
