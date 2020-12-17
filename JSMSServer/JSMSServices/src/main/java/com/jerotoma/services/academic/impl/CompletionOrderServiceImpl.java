package com.jerotoma.services.academic.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.CompletionOrder;
import com.jerotoma.database.dao.academic.CompletionOrderDao;
import com.jerotoma.services.academic.CompletionOrderService;

@Transactional
@Service
public class CompletionOrderServiceImpl implements CompletionOrderService {
	
	@Autowired CompletionOrderDao completionOrderDao;

	@Override
	public CompletionOrder findObject(Integer primaryKey) throws SQLException {
		return completionOrderDao.findObject(primaryKey);
	}

	@Override
	public CompletionOrder findObjectUniqueKey(String uniqueKey) throws SQLException {
		return completionOrderDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public CompletionOrder createObject(CompletionOrder object) throws SQLException {
		return completionOrderDao.createObject(object);
	}

	@Override
	public CompletionOrder updateObject(CompletionOrder object) throws SQLException {
		return completionOrderDao.updateObject(object);
	}

	@Override
	public Boolean deleteObject(CompletionOrder object) throws SQLException {
		return completionOrderDao.deleteObject(object);
	}

	@Override
	public List<CompletionOrder> loadList(QueryParam queryParam) throws SQLException {
		return completionOrderDao.loadList(queryParam);
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return completionOrderDao.loadMapList(queryParam);
	}
}
