package com.jerotoma.services.users.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.exceptions.JDataAccessException;
import com.jerotoma.common.models.users.Teacher;
import com.jerotoma.database.dao.users.TeacherDao;
import com.jerotoma.services.exceptions.ServiceException;
import com.jerotoma.services.users.TeacherService;

@Service
@Transactional
public class TeacherServiceImpl implements TeacherService{
	
	@Autowired TeacherDao teacherDao;
	Teacher teacher;

	@Override
	public Teacher findObject(Integer primaryKey) throws SQLException {
		try {
			return teacherDao.findObject(primaryKey);			
		} catch (ServiceException  e) {
			throw new JDataAccessException(e.getMessage(), e);
		}
		
	}

	@Override
	public Teacher findObjectUniqueKey(String uniqueKey) throws SQLException {
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
	public Teacher createObject(Teacher object) throws SQLException {
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
	public Teacher updateObject(Teacher object) throws SQLException {
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
	public Boolean deleteObject(Teacher object) throws SQLException {
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
	public List<Teacher> loadList(QueryParam queryParam) throws SQLException {
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
