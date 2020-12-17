package com.jerotoma.services.assemblers.academic.impls;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.models.academic.CompletionOrder;
import com.jerotoma.database.assemblers.dao.academic.AssemblerCompletionOrderDao;
import com.jerotoma.services.assemblers.academic.AssemblerCompletionOrderService;

@Service
public class AssemblerCompletionOrderServiceImpl implements AssemblerCompletionOrderService {
	
	@Autowired AssemblerCompletionOrderDao assemblerCompletionOrderDao;

	@Override
	public CompletionOrder findObject(Integer primaryKey) throws SQLException {
		return assemblerCompletionOrderDao.findObject(primaryKey);
	}

	@Override
	public CompletionOrder findObjectUniqueKey(String uniqueKey) throws SQLException {
		return assemblerCompletionOrderDao.findObjectUniqueKey(uniqueKey);
	}

	@Override
	public List<CompletionOrder> loadList() throws SQLException {
		return assemblerCompletionOrderDao.loadList();
	}

	@Override
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException {
		return assemblerCompletionOrderDao.loadMapList(queryParam);
	}

	@Override
	public Long countObject() throws SQLException {
		return assemblerCompletionOrderDao.countObject();
	}

	@Override
	public CompletionOrder getCompletionOrder(Integer programId, Integer academicLevel) {
		return assemblerCompletionOrderDao.getCompletionOrder(programId, academicLevel);
	}

	@Override
	public List<CompletionOrder> getCompletionOrders(Integer programId) {
		return assemblerCompletionOrderDao.getCompletionOrders(programId);
	}

}
