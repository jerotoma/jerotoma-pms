package com.jerotoma.services.users;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.RoleConstant;
import com.jerotoma.common.constants.UserConstant;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.models.security.Role;
import com.jerotoma.common.models.users.AuthUser;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.database.dao.users.AuthUserDao;

@Service
@Transactional
public class AuthUserServiceImpl implements AuthUserService {
	
	@Autowired AuthUserDao authUserDao;

	@Override
	public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException {		
		return authUserDao.loadUserByUsername(username);
	}

	@Override
	public AuthUser findObject(Integer primaryKey) throws SQLException {
		return authUserDao.findObject(primaryKey);
	}

	@Override
	public AuthUser findObjectUniqueKey(String uniqueKey) throws SQLException {
		return authUserDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public AuthUser createObject(AuthUser object) throws SQLException {
		return authUserDao.createObject(object);
	}

	@Override
	public Boolean deleteObject(AuthUser object) throws SQLException {
		return authUserDao.deleteObject(object);
	}

	@Override
	public List<AuthUser> loadList(QueryParam queryParam) throws SQLException {
		return authUserDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return authUserDao.loadMapList(queryParam);
	}

	@Override
	public AuthUser updateObject(AuthUser object) throws SQLException {
		return authUserDao.updateObject(object);
	}

	@Override
	public List<AuthUser> search(QueryParam queryParam) throws SQLException {
		return  authUserDao.search(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return  authUserDao.countObject();
	}
	
	@Override
	public AuthUser preUserCreation(Map<String, Object> params, RoleConstant.USER_ROLES userRole) {
		
		String username = (String) params.get(UserConstant.USER_NAME);
		String firstName = (String) params.get(UserConstant.FIRST_NAME);
		String lastName = (String) params.get(UserConstant.LAST_NAME);
		String password = (String) params.get(UserConstant.PASSWORD);
		String confirmPass = (String) params.get(UserConstant.CONFIRM_PASS);
		Date updatedOn = new Date();
		Date createdOn = new Date();
		
		if (StringUtility.isEmpty(username)) {
			throw new RuntimeException("Invalid or Empty username was provided");
		}
		
		if (StringUtility.isEmpty(password)) {
			throw new RuntimeException("Invalid or Empty password was provided");
		}
		
				
		if(!password.trim().equals(confirmPass.trim())) {
			throw new RuntimeException("Password not confirmed");
		}
		
		AuthUser mAuthUser = loadUserByUsername(username);
		if (mAuthUser != null) {
			throw new DuplicateKeyException("Sorry, Username exists already");
		}
		
		
		List<Role> roles = new ArrayList<>();
		Role role = new Role();	
		role.setName(userRole.getRoleName());
		role.setDisplayName(userRole.getDisplayName());
		roles.add(role);
		
		AuthUser authUser = new AuthUser(username, password, true, true, true, true, roles);
		authUser.setCreatedOn(createdOn);
		authUser.setFirstName(firstName);
		authUser.setLastName(lastName);		
		authUser.setUpdatedOn(updatedOn);
		try {
			authUser = createObject(authUser);
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		
		return authUser;
	}


}
