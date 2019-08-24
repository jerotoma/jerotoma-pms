package com.jerotoma.services.users.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.models.addresses.TeacherAddress;
import com.jerotoma.database.dao.users.TeacherAddressDao;
import com.jerotoma.services.exceptions.ServiceException;
import com.jerotoma.services.users.TeacherAddressService;

@Transactional
@Repository
public class TeacherAddressServiceImpl implements TeacherAddressService {
	
	@Autowired TeacherAddressDao teacherDao;
	TeacherAddress teacher;

	@Override
	public TeacherAddress findObject(Integer primaryKey) throws SQLException {
		try {
			return teacherDao.findObject(primaryKey);			
		} catch (ServiceException  e) {
			throw new JDataAccessException(e.getMessage(), e);
		}
		
	}

	@Override
	public TeacherAddress findObjectUniqueKey(String uniqueKey) throws SQLException {
		try {
			teacher =  teacherDao.findObjectUniqueKey(uniqueKey);	
		} catch (ServiceException  e) {
			throw new JDataAccessException(e.getMessage(), e);		
		} catch (EntityExistsException e) {
			new JDataAccessException(e.getMessage(), e);
	    } catch(IllegalArgumentException e){
	    	new JDataAccessException(e.getMessage(), e);
	    } catch(TransactionRequiredException e){
	    	new JDataAccessException(e.getMessage(), e);
	    }
		return teacher;	
	}

	@Override
	public TeacherAddress createObject(TeacherAddress object) throws SQLException {
		try {
			teacher =  teacherDao.createObject(object);		
		} catch (ServiceException  e) {
			throw new JDataAccessException(e.getMessage(), e);		
		} catch (EntityExistsException e) {
			new JDataAccessException(e.getMessage(), e);
	    } catch(IllegalArgumentException e){
	    	new JDataAccessException(e.getMessage(), e);
	    } catch(TransactionRequiredException e){
	    	new JDataAccessException(e.getMessage(), e);
	    }
		return teacher;
		
	}

	@Override
	public TeacherAddress updateObject(TeacherAddress object) throws SQLException {
		try {
			teacher =  teacherDao.updateObject(object);		
		} catch (ServiceException  e) {
			throw new JDataAccessException(e.getMessage(), e);		
		} catch (EntityExistsException e) {
			new JDataAccessException(e.getMessage(), e);
	    } catch(IllegalArgumentException e){
	    	new JDataAccessException(e.getMessage(), e);
	    } catch(TransactionRequiredException e){
	    	new JDataAccessException(e.getMessage(), e);
	    }
		return teacher;	
	}

	@Override
	public Boolean deleteObject(TeacherAddress object) throws SQLException {
		try {
			return teacherDao.deleteObject(object);	
		} catch (ServiceException  e) {
			throw new JDataAccessException(e.getMessage(), e);		
		} catch (EntityExistsException e) {
			new JDataAccessException(e.getMessage(), e);
	    } catch(IllegalArgumentException e){
	    	new JDataAccessException(e.getMessage(), e);
	    } catch(TransactionRequiredException e){
	    	new JDataAccessException(e.getMessage(), e);
	    }
		return false;	
	}

	@Override
	public List<TeacherAddress> loadList(QueryParam queryParam) throws SQLException {
		try {
			return teacherDao.loadList(queryParam);			
		}  catch (ServiceException  e) {
			throw new JDataAccessException(e.getMessage(), e);		
		} catch (EntityExistsException e) {
			new JDataAccessException(e.getMessage(), e);
	    } catch(IllegalArgumentException e){
	    	new JDataAccessException(e.getMessage(), e);
	    } catch(TransactionRequiredException e){
	    	new JDataAccessException(e.getMessage(), e);
	    }
		return null;	
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		
		
		try {
			return teacherDao.loadMapList(queryParam);	
		}  catch (ServiceException  e) {
			throw new JDataAccessException(e.getMessage(), e);		
		} catch (EntityExistsException e) {
			new JDataAccessException(e.getMessage(), e);
	    } catch(IllegalArgumentException e){
	    	new JDataAccessException(e.getMessage(), e);
	    } catch(TransactionRequiredException e){
	    	new JDataAccessException(e.getMessage(), e);
	    }
		return null;
	}

}
