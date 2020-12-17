package com.jerotoma.database.dao.academic.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.constants.CompletionOrderConstant;
import com.jerotoma.common.constants.SystemConstant;
import com.jerotoma.common.models.academic.CompletionOrder;
import com.jerotoma.database.dao.DaoUtil;
import com.jerotoma.database.dao.academic.CompletionOrderDao;

@Transactional
@Repository
public class CompletionOrderDaoImpl implements CompletionOrderDao{

	@PersistenceContext 
	private EntityManager entityManager;
	
	
	@Override
	public CompletionOrder findObject(Integer primaryKey) throws SQLException {
		return entityManager.find(CompletionOrder.class, primaryKey);
	}

	@Override
	public CompletionOrder findObjectUniqueKey(String uniqueKey) throws SQLException {
		return entityManager.createQuery("FROM CompletionOrder WHERE completionOrder = :completionOrder ", CompletionOrder.class).setParameter("completionOrder", uniqueKey).getSingleResult();
	}

	@Override
	public CompletionOrder createObject(CompletionOrder object) throws SQLException {
		entityManager.persist(object);
		return findObject(object.getId());
	}

	@Override
	public Boolean deleteObject(CompletionOrder object) throws SQLException {
		entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
		return true;
	}

	@Override
	public List<CompletionOrder> loadList(QueryParam queryParam) throws SQLException {
		return entityManager.createQuery("FROM CompletionOrder  ORDER BY completionOrder ASC", CompletionOrder.class)
				.getResultList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		Map<String, Object> map = new HashMap<>();
		
		Long countResults = countObject();
		int pageCount = DaoUtil.getPageCount(queryParam.getPageSize(), countResults);
		Integer limit = DaoUtil.getPageSize(queryParam.getPageSize(),countResults);
		Integer offset = (queryParam.getPage() - 1) * queryParam.getPageSize();
		
		List<CompletionOrder> completionOrders = entityManager.createQuery("FROM CompletionOrder", CompletionOrder.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		map.put(CompletionOrderConstant.COMPLETION_ORDERS, completionOrders);
		map.put(SystemConstant.COUNT, countResults);
		map.put(SystemConstant.PAGE_COUNT, pageCount);
		
		return map;
	}

	@Override
	public CompletionOrder updateObject(CompletionOrder object) throws SQLException {		
		return entityManager.merge(object);
	}

	@Override
	public Long countObject() throws SQLException {
		return entityManager.createQuery("SELECT count(*) FROM CompletionOrder", Long.class)				
				.getSingleResult();
	}

}
