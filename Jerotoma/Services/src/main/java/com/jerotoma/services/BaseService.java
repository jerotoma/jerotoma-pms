package com.jerotoma.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.jerotoma.common.QueryParam;

public abstract interface BaseService<T> {
	public T findObject(Integer primaryKey) throws SQLException;
	public T findObjectUniqueKey(String uniqueKey) throws SQLException;
	public T createObject(T object) throws SQLException;
	public T updateObject(T object) throws SQLException;
	public Boolean deleteObject(T object) throws SQLException;
	public List<T> loadList(QueryParam queryParam) throws SQLException;
	public Map<String, Object> loadMapList(QueryParam queryParam) throws SQLException;
}
