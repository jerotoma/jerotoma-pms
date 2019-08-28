package com.jerotoma.database.dao.roles.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.database.dao.roles.RoleDao;

@Repository
public class RoleDaoImpl extends JdbcDaoSupport implements RoleDao {
	
	private Integer primaryKey;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}
	
	@Override
	public Role findObject(Integer primaryKey) throws SQLException {
				
		return this.jdbcTemplate.query(getBaseSelectQuery().append("WHERE id = ?").toString(), new RoleSingleResultProcessor(), primaryKey);
	}

	@Override
	public Role findObjectUniqueKey(String uniqueKey) throws SQLException {
		return this.jdbcTemplate.query(getBaseSelectQuery().append("WHERE name = ? ").toString(), new RoleSingleResultProcessor(), uniqueKey);
	}

	@Override
	public Role createObject(Role object) throws SQLException {
		Object[] objects = {
				object.getName(),				
				object.getDisplayName()				
		};
		primaryKey = this.jdbcTemplate.update(getBaseInsertQuery().toString(), objects);
		return findObject(primaryKey);
	}

	@Override
	public Boolean deleteObject(Role object) throws SQLException {
		return this.jdbcTemplate.update(getBaseDeleteQuery().toString(), object.getId()) != 0 ? true : false;
	}

	@Override
	public List<Role> loadList(QueryParam queryParam) throws SQLException {
		return this.jdbcTemplate.query(getBaseSelectQuery().toString(), new RoleResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam params) throws SQLException {
		
		Map<String, Object> map = new HashMap<>();
		List<Role> roles = new ArrayList<Role>();
		roles = this.jdbcTemplate.query(getBaseSelectQuery().toString(), new RoleResultProcessor());		
		map.put(RoleConstant.ROLES, roles);
		
		return map;
	}
	
	public class RoleResultProcessor implements RowMapper<Role>{
		@Override
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			Role role = mapRoleResult(rs);			
			return role;
		}		
	}
	
	public class RoleSingleResultProcessor implements ResultSetExtractor<Role>{
		@Override
		public Role extractData(ResultSet rs) throws SQLException, DataAccessException {
			Role l = null;
			if(rs.next()) {
			 l = mapRoleResult(rs);
			}
			return l;
		}				
	}
	
	public class LongResultProcessor implements ResultSetExtractor<Long>{
		@Override
		public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
			Long l = null;
			if(rs.next()) {
			 l = rs.getLong(1);
			}
			return l;
		}				
	}

	public Role mapRoleResult(ResultSet rs) throws SQLException {		
		return new Role(rs);
	}
	
	private StringBuilder getBaseSelectQuery() {
		return new StringBuilder("SELECT id, name, display_name, created_on, updated_on FROM public.roles ");
	}
	
	private StringBuilder getBaseInsertQuery() {
		return new StringBuilder("INSERT INTO public.roles(name, display_name, created_on, updated_on) VALUES(?, ?, now(), now())");
	}
	private StringBuilder getBaseDeleteQuery() {
		return new StringBuilder("DELETE FROM public.roles WHERE id = ?");
	}

	@Override
	public Role updateObject(Role object) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countObject() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
