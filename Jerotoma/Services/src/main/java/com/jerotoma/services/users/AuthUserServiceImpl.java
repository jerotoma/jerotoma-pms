package com.jerotoma.services.users;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.models.users.User;
import com.jerotoma.common.utils.StringUtility;
import com.jerotoma.database.dao.users.AuthUserDao;

@Service
@Transactional
public class AuthUserServiceImpl implements AuthUserService {
	
	@Autowired AuthUserDao authUserDao;

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {		
		return authUserDao.loadUserByUsername(username);
	}

	@Override
	public User findObject(Integer primaryKey) throws SQLException {
		return authUserDao.findObject(primaryKey);
	}

	@Override
	public User findObjectUniqueKey(String uniqueKey) throws SQLException {
		return authUserDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public User createObject(User object) throws SQLException {
		return authUserDao.createObject(object);
	}

	@Override
	public Boolean deleteObject(User object) throws SQLException {
		return authUserDao.deleteObject(object);
	}

	@Override
	public List<User> loadList(QueryParam queryParam) throws SQLException {
		return authUserDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return authUserDao.loadMapList(queryParam);
	}

	@Override
	public User updateObject(User object) throws SQLException {
		return authUserDao.updateObject(object);
	}

	@Override
	public List<User> search(QueryParam queryParam) throws SQLException {
		return  authUserDao.search(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return  authUserDao.countObject();
	}
	
	@Override
	public User createUserLoginAccount(User authUser) {		
		if (doesUserExist(authUser.getUsername())) {
			throw new DuplicateKeyException("Sorry, Username exists already");
		}
		
		try {
			authUser = createObject(authUser);
		} catch (SQLException e) {
			throw new JDataAccessException(e.getMessage(), e);			
		}
		
		return authUser;
	}

	@Override
	public boolean doesUserExist(String username) {
		if (StringUtility.isEmpty(username)) {
			throw new RuntimeException("Invalid or Empty username was provided");
		}
		return loadUserByUsername(username) != null;
	}
}
