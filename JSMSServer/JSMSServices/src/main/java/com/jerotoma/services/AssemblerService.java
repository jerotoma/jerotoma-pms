package com.jerotoma.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.QueryParam;

public interface AssemblerService<T> {
	public T findObject(Integer primaryKey) throws SQLException;
	public T findObjectUniqueKey(String uniqueKey) throws SQLException;
	public List<T> loadList() throws SQLException;
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException;
	public Long countObject() throws SQLException;
}
