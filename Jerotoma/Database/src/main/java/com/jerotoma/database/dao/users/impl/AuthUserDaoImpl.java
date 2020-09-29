package com.jerotoma.database.dao.users.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.common.models.users.User;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.roles.RoleDao;
import com.jerotoma.database.dao.users.AuthUserDao;


@Repository
public class AuthUserDaoImpl extends JdbcDaoSupport implements AuthUserDao {
	
	private StringBuilder DELETE_QUERY = new StringBuilder("DELETE FROM public.users WHERE id = ?");
	
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired RoleDao roleDao;	
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public User findObject(Integer primaryKey) throws SQLException {
		String query = commonSelectQuery(false).append("WHERE id = ? ").toString();
		return this.jdbcTemplate.query(query, new AuthUserSingleResultProcessor(), primaryKey);
	
	}

	@Override
	public User createObject(User object) throws SQLException {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		if(object != null && doesRoleExists(object.getRoles())){
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(conn -> {
				int pos = 1;
		        PreparedStatement ps = conn.prepareStatement(commonInsertQuery().toString(),new String[] { "id" /* name of your id column */ });
			        ps.setString(pos++, object.getUsername());
			        ps.setString(pos++, passwordEncoder.encode(object.getPassword()));
			        ps.setString(pos++, object.getUserType().getType());			       
			        ps.setBoolean(pos++, object.isEnabled());
			        ps.setBoolean(pos++, object.isAccountNonExpired());
			        ps.setBoolean(pos++,object.isCredentialsNonExpired());
			        ps.setBoolean(pos++,object.isAccountNonLocked());
					ps.setDate(pos++,new java.sql.Date(object.getCreatedOn().getTime()));
					ps.setDate(pos++,new java.sql.Date(object.getUpdatedOn().getTime()));
		          
		         return ps;
		        }, keyHolder);
		 
			for(Role role : object.getRoles()) {
				Role r = roleDao.findObjectUniqueKey(role.getName());				
				this.jdbcTemplate.update(commonRoleUserInsertQuery().toString(), r.getId(), keyHolder.getKey().intValue());			
			}
			return findObject(keyHolder.getKey().intValue());
		}
		return null;		
	}

	private boolean doesRoleExists(Collection<Role> roles) throws SQLException {
		if(roles == null || roles.isEmpty()) {
			throw new SQLException("Role can't be null or empty");
		}
		
		for(Role role : roles) {
			Role r = roleDao.findObjectUniqueKey(role.getName());
			if(r == null) {
				throw new SQLException("Role can't be null or empty");
			}
		}
		return true;
	}

	@Override
	public Boolean deleteObject(User object) throws SQLException {
		return this.jdbcTemplate.update(DELETE_QUERY.toString(), object.getId()) != 0 ? true : false;
	}

	@Override
	public List<User> loadList(QueryParam queryParam) throws SQLException {
		String query = commonSelectQuery(true).toString();
		return this.jdbcTemplate.query(query, new AuthUserResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		return null;
	}

	@Override
	public User findObjectUniqueKey(String uniqueKey) throws SQLException {
		return loadUserByUsername(uniqueKey);
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		String query = commonSelectQuery(false).append("WHERE username = ? ").toString();
		return this.jdbcTemplate.query(query, new AuthUserSingleResultProcessor(), username);
	
	}
	
	public class AuthUserResultProcessor implements RowMapper<User>{
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User role = mapAuthUserResult(rs);			
			return role;
		}		
	}
	
	public class AuthUserSingleResultProcessor implements ResultSetExtractor<User>{
		@Override
		public User extractData(ResultSet rs) throws SQLException, DataAccessException {
			User authUser = null;
			if(rs.next()) {
				authUser = mapAuthUserResult(rs);
			}
			return authUser;
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

	public User mapAuthUserResult(ResultSet rs) throws SQLException {	
		User authUser = null;
		String password = null;
		Integer userId = rs.getInt(UserConstant.ID);
		String username = rs.getString(UserConstant.USERNAME);
		if(DaoUtil.hasColumn(rs, UserConstant.PASSWORD)) {
			password = rs.getString(UserConstant.PASSWORD);
		}		
		String userType = rs.getString("user_type");		
		Boolean enabled  = rs.getBoolean("enabled"); 
		Boolean accountNonExpired = rs.getBoolean("account_non_expired"); 
		Boolean credentialsNonExpired = rs.getBoolean("credentials_non_expired"); 
		Boolean accountNonLocked = rs.getBoolean("account_non_locked");;	
		Date createdOn = rs.getDate("created_on");
		Date updatedOn = rs.getDate("updated_on");		
		authUser = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, loadAuthorities(userId));
		authUser.setId(userId);
		authUser.setCreatedOn(createdOn);
		authUser.setUpdatedOn(updatedOn);
		authUser.setUserType(UserConstant.processUserType(userType));		
		return authUser;
	}

	private Collection<Role> loadAuthorities(Integer userId) {
		StringBuilder builder = new StringBuilder()
				.append("SELECT r.id, r.name, r.display_name, r.created_on, r.updated_on FROM public.roles r ")
				.append("INNER JOIN public.user_roles ur ON ur.role_id = r.id ")
				.append("WHERE ur.user_id = ?");
		return this.jdbcTemplate.query(builder.toString(), new RowMapper<Role>() {
			@Override
			public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Role(rs);
			}
		}, userId);
	}
	private StringBuilder commonSelectQuery(boolean removePassword) {		
		return commonSelectQuery(removePassword, false);
	}
	
	private StringBuilder commonSelectQuery(boolean removePassword, boolean isSearch) {	
		StringBuilder unionBuilder = new StringBuilder("");
		StringBuilder builder = new StringBuilder("SELECT ");
		builder.append("u.id, u.username, u.password, u.user_type, u.enabled, u.account_non_expired, u.credentials_non_expired, u.account_non_locked, u.created_on, u.updated_on ");
		if (isSearch) {
			builder.append(" ,first_name, last_name, email_address ");
		}		
		builder.append("FROM public.users u ");
		
		if(removePassword) {
			builder.toString().replace("password,", "");
		}
		
		if (isSearch) {
			String studentSQL = new StringBuilder(builder.toString()).append(" INNER JOIN students st ON st.user_id = u.id ").toString();
			String teacherSQL = new StringBuilder(builder.toString()).append(" INNER JOIN teachers te ON te.user_id = u.id ").toString();
			String parentSQL = new StringBuilder(builder.toString()).append(" INNER JOIN parents pa ON pa.user_id = u.id ").toString();
			String staffSQL = new StringBuilder(builder.toString()).append(" INNER JOIN staffs sta ON sta.user_id = u.id ").toString();			
			unionBuilder.append("SELECT * FROM ( ").append("(").append(studentSQL).append(") UNION (").append(teacherSQL).append(") UNION (").append(parentSQL).append(") UNION (").append(staffSQL).append(") ").append(") temp ");
		}				
		return isSearch ? unionBuilder : builder;
	}
	
	private StringBuilder commonInsertQuery() {		
		return new StringBuilder("INSERT INTO public.users(username, password, user_type, enabled, account_non_expired, credentials_non_expired, account_non_locked, created_on, updated_on) ")
				.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
	}
	
	private StringBuilder commonUpdateQuery() {		
		return new StringBuilder("UPDATE public.users SET user_type = ?, enabled = ?, account_non_expired = ?, credentials_non_expired = ?, account_non_locked = ?, updated_on = ?  WHERE id = ? ");
	}
	
	private StringBuilder commonRoleUserInsertQuery() {
		return new StringBuilder("INSERT INTO public.user_roles(role_id, user_id) ")
				.append("VALUES (?, ?)");	
	}

	@Override
	public User updateObject(User object) throws SQLException {
		if(object != null && doesRoleExists(object.getRoles())){			
			jdbcTemplate.update(conn -> {
				int pos = 1;
		        PreparedStatement ps = conn.prepareStatement(commonUpdateQuery().toString());
			        ps.setString(pos++, object.getUserType().getType());			       
			        ps.setBoolean(pos++, object.isEnabled());
			        ps.setBoolean(pos++, object.isAccountNonExpired());
			        ps.setBoolean(pos++,object.isCredentialsNonExpired());
			        ps.setBoolean(pos++,object.isAccountNonLocked());
					ps.setDate(pos++,new java.sql.Date(object.getUpdatedOn().getTime()));
					ps.setInt(pos++, object.getId());
		         return ps;
		        });
		 
			for(Role role : object.getRoles()) {
				Role r = roleDao.findObjectUniqueKey(role.getName());				
				if (!doesUserRoleExists(object.getId(), r.getId())) {					
					this.jdbcTemplate.update(commonRoleUserInsertQuery().toString(), r.getId(), object.getId());
				}
			}
			return findObject(object.getId());
		}
		return null;
	}
	
	private boolean doesUserRoleExists(Integer userId, Integer roleId) {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.user_roles WHERE role_id = ? AND user_id = ?");
		Long count =  this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor(), roleId, userId);		
		return count > 0;		
	}

	@Override
	public List<User> search(QueryParam queryParam) throws SQLException {
		StringBuilder queryBuilder = commonSelectQuery(true, true);
		queryBuilder.append(" WHERE LOWER(first_name) like ? OR LOWER(last_name) like ? OR LOWER(username) like ? ")
				.append(DaoUtil.getOrderBy(queryParam.getFieldName(), queryParam.getOrderby()))
				.append(" ")
				.append("limit ? offset ?");
		
		Long countResults = countObject();
		//int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		Object[] paramList = new Object[] {				
				DaoUtil.addPercentBothSide(queryParam.getSearch()),
				DaoUtil.addPercentBothSide(queryParam.getSearch()),
				DaoUtil.addPercentBothSide(queryParam.getSearch()),
				limit, 
				offset
		};
		return this.jdbcTemplate.query(queryBuilder.toString(), new AuthUserResultProcessor(), paramList);
	}

	@Override
	public Long countObject() throws SQLException {
		StringBuilder queryBuilder = new StringBuilder("SELECT count(*) FROM public.users");
		return this.jdbcTemplate.query(queryBuilder.toString(), new LongResultProcessor());
	}	
}
