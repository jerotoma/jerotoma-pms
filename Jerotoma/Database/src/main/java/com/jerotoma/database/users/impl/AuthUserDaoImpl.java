package com.jerotoma.database.users.impl;

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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.roles.Role;
import com.jerotoma.common.users.AuthUser;
import com.jerotoma.database.roles.dao.RoleDao;
import com.jerotoma.database.users.dao.AuthUserDao;

@Repository
public class AuthUserDaoImpl extends JdbcDaoSupport implements AuthUserDao {
	
	private StringBuilder INSERT_QUERY = new StringBuilder("INSERT INTO public.users(username, password, first_name, last_name, enabled, account_non_expired, credentials_non_expired, account_non_locked, created_on, updated_on) ")
			.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	private StringBuilder ROLE_INSERT_QUERY = new StringBuilder("INSERT INTO public.user_roles(role_id, user_id) ")
			.append("VALUES (?, ?)");	
	private StringBuilder SELECT_COMMON_QUERY = new StringBuilder("SELECT username, password, first_name, last_name, enabled, account_non_expired, credentials_non_expired, account_non_locked, created_on, updated_on FROM public.users ");
	private StringBuilder DELETE_QUERY = new StringBuilder("DELETE FROM public.users WHERE id = ?");
	
	private Integer primaryKey;
	private JdbcTemplate jdbcTemplate;
	
	@Autowired DataSource dataSource;
	@Autowired RoleDao roleDao;	
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
		this.jdbcTemplate = getJdbcTemplate();
	}

	@Override
	public AuthUser findObject(Integer primaryKey) throws SQLException {
		SELECT_COMMON_QUERY.append("WHERE id = ? ");
		return this.jdbcTemplate.query(SELECT_COMMON_QUERY.toString(), new AuthUserSingleResultProcessor(), primaryKey);
	
	}

	@Override
	public AuthUser createObject(AuthUser object) throws SQLException {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();//PasswordEncoderFactories.createDelegatingPasswordEncoder();
		Object[] objects = {
				object.getUsername(),
				passwordEncoder.encode(object.getPassword()),
				object.getFirstName(),
				object.getLastName(),
				object.getEnabled(),
				object.getAccountNonExpired(),
				object.getCredentialsNonExpire(),
				object.getAccountNonLocked(),
				object.getCreatedOn(),
				object.getUpdatedOn()
		};
		primaryKey = this.jdbcTemplate.update(INSERT_QUERY.toString(), objects);
		for(Role role : object.getRoles()) {
			this.jdbcTemplate.update(ROLE_INSERT_QUERY.toString(), primaryKey, role.getId());			
		}		
		return findObject(primaryKey);
	}

	@Override
	public Boolean deleteObject(AuthUser object) throws SQLException {
		return this.jdbcTemplate.update(DELETE_QUERY.toString(), object.getId()) != 0 ? true : false;
	}

	@Override
	public List<AuthUser> loadList(QueryParam queryParam) throws SQLException {
		return this.jdbcTemplate.query(SELECT_COMMON_QUERY.toString(), new AuthUserResultProcessor());
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		return null;
	}

	@Override
	public AuthUser findObjectUniqueKey(String uniqueKey) throws SQLException {
		return loadUserByUsername(uniqueKey);
	}

	@Override
	public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException {
		SELECT_COMMON_QUERY.append("WHERE username = ? ");
		return this.jdbcTemplate.query(SELECT_COMMON_QUERY.toString(), new AuthUserSingleResultProcessor(), username);
	
	}
	
	public class AuthUserResultProcessor implements RowMapper<AuthUser>{
		@Override
		public AuthUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			AuthUser role = mapAuthUserResult(rs);			
			return role;
		}		
	}
	
	public class AuthUserSingleResultProcessor implements ResultSetExtractor<AuthUser>{
		@Override
		public AuthUser extractData(ResultSet rs) throws SQLException, DataAccessException {
			AuthUser authUser = null;
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

	public AuthUser mapAuthUserResult(ResultSet rs) throws SQLException {	
		AuthUser authUser = null;
		Integer userId = rs.getInt("id");
		String username = rs.getString("username");
		String password = rs.getString("password");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");
		Boolean enabled  = rs.getBoolean("enabled"); 
		Boolean accountNonExpired = rs.getBoolean("account_non_expired"); 
		Boolean credentialsNonExpired = rs.getBoolean("credentials_non_expired"); 
		Boolean accountNonLocked = rs.getBoolean("account_non_locked");;	
		Date createdOn = rs.getDate("created_on");
		Date updatedOn = rs.getDate("updated_on");		
		authUser = new AuthUser(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, loadAuthorities(userId));
		authUser.setCreatedOn(createdOn);
		authUser.setUpdatedOn(updatedOn);
		authUser.setFirstName(firstName);
		authUser.setLastName(lastName);
		return authUser;
	}

	private Collection<Role> loadAuthorities(Integer userId) {
		StringBuilder builder = new StringBuilder("SELECT r.id, r.name, r.displayName, r.created_on, r.updated_on FROM public.roles ")
				.append("JOIN INNER user_roles ur ON ur.role_id = r.id ")
				.append("WHERE ur.user_id = ?");
		return this.jdbcTemplate.query(builder.toString(), new RowMapper<Role>() {
			@Override
			public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Role(rs);
			}});
	}
}
