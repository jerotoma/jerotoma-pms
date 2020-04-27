package com.jerotoma.database.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.jerotoma.common.utils.StringUtility;

public class DaoUtil {
	
	Integer size = 0;
	static Integer all = -1;
	
	public static enum ORDERBY{
		ASC("ASC"),
		DESC("DESC");
		
		private String orderby;
		
		
		ORDERBY(String orderby){
			this.orderby = orderby;
		}
		
		public String getValue(){
			return orderby;
		}
		
	}
	
    public static String getOrderBy(String fieldName, String orderby) {
    	
    	if(StringUtility.isEmpty(fieldName)) {
    		fieldName = "created_on";
		}
    	
    	if(StringUtility.isEmpty(orderby)) {
    		orderby = "DESC";
		}
    	
		return " ORDER BY " + fieldName + " " + orderby;
	}
    
    public static String getOrderBy(String fieldName, String orderby, String prefix) {
    	
    	fieldName = !StringUtility.isEmpty(fieldName) ? fieldName : "created_on";
    	orderby = !StringUtility.isEmpty(orderby) ? orderby : "DESC";    	
    	prefix = !StringUtility.isEmpty(prefix) ? prefix + "." : "" ;
    	
		return " ORDER BY " + prefix + fieldName + " " + orderby;
	}
    
    public static int getPageSize(Integer pageSize, Long countResults) {
    	
    	if(pageSize == null ) {
			pageSize = 10;
		}
    	
    	return (int) (pageSize == all ? countResults : pageSize);
	}
	
	public static int getPageCount(Integer pageSize, Long countResults) {
		if(pageSize == null ) {
			pageSize = 10;
		}
			
		if(pageSize == all) {
			return (int) (Math.ceil( (double)countResults));
		}
		return (int) (Math.ceil( (double)countResults / pageSize));
	}
	
	public static String addPercentBothSide(String search) {
		if(!StringUtility.isEmpty(search)) {
			return "%" + search.trim().toLowerCase() + "%";
		}
		return "%%";
	}
	
	public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columns = rsmd.getColumnCount();
	    for (int x = 1; x <= columns; x++) {
	        if (columnName.toLowerCase().equals(rsmd.getColumnName(x))) {
	            return true;
	        }
	    }
	    return false;
	}
}
