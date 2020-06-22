package com.jerotoma.database.dao.media.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.MediaConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.media.UserMedia;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.media.UserMediaDao;

@Repository
@Transactional
public class UserMediaDaoImpl implements UserMediaDao {

	@PersistenceContext 
	private EntityManager entityManager;
	
	
	@Override
	public UserMedia findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(UserMedia.class, primaryKey);
	}

	@Override
	public UserMedia findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("findObjectUniqueKey has not been implemented yet");
	}

	@Override
	public UserMedia createObject(UserMedia object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(UserMedia object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<UserMedia> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM UserMedia ", UserMedia.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		List<UserMedia> userMediaList = entityManager.createQuery("FROM UserMedia", UserMedia.class)		
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(MediaConstant.USER_MEDIA_LIST, userMediaList);
		map.put(SystemConstant.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);		
		return map;
	}

	@Override
	public UserMedia updateObject(UserMedia object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM UserMedia", Long.class)				
				.getSingleResult();
	}

}
