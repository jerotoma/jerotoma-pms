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
import com.jerotoma.common.models.media.Media;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.media.MediaDao;

@Transactional
@Repository
public class MediaDaoImpl implements MediaDao {

	@PersistenceContext 
	private EntityManager entityManager;
	
	
	@Override
	public Media findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(Media.class, primaryKey);
	}

	@Override
	public Media findObjectUniqueKey(String uniqueKey) throws SQLException {
		throw new RuntimeException("findObjectUniqueKey has not been implemented yet");
	}

	@Override
	public Media createObject(Media object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(Media object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<Media> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM Media ", Media.class).getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		List<Media> mediaList = entityManager.createQuery("FROM Media", Media.class)		
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(MediaConstant.MEDIA_LIST, mediaList);
		map.put(SystemConstant.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);		
		return map;
	}

	@Override
	public Media updateObject(Media object) throws SQLException {
		entityManager.merge(object);
		return findObject(object.getId().intValue());
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM Media", Long.class)				
				.getSingleResult();
	}
}
