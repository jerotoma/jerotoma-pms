package com.jerotoma.services.utils;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.jerotoma.common.QueryParam;
import com.jerotoma.common.utils.StringUtility;

public class ServiceUtil {
	
	public static enum ORDER_BY{
		ASC("ASC"),
		DESC("DESC");
		
		private String orderby;
		
		
		ORDER_BY(String orderby){
			this.orderby = orderby;
		}
		
		public String getValue(){
			return orderby;
		}	
		
		public static ORDER_BY getOrderBy(String orderby) {
			for(ORDER_BY orderBy: values()) {
				if (orderBy.getValue().toLowerCase().equals(orderby.toLowerCase())) {
					return orderBy;
				}
			}
			return null;
		}		
	}
	
	public static Pageable getPageable(QueryParam queryParam) {
		
		Pageable pageable =  null;
		if (queryParam != null) {
			
			String fieldName = queryParam.getFieldName();
			String orderby = queryParam.getOrderby();
			
			if(StringUtility.isEmpty(fieldName)) {
	    		fieldName = "created_on";
			}
			
			Sort sort = Sort.by(fieldName);
	    	
			if(StringUtility.isEmpty(orderby)) {
				sort = sort.descending();
			}
			
			ORDER_BY orderBy = ORDER_BY.getOrderBy(orderby);
			
			if (orderBy != null) {
				if (orderBy.equals(ORDER_BY.ASC)) {
					sort = sort.ascending(); 
				} else if (orderBy.equals(ORDER_BY.DESC)) {
					sort = sort.descending(); 
				}
			}		
			pageable = PageRequest.of(
					queryParam.getPage() != null ?  queryParam.getPage() : 0, 
					queryParam.getPageSize() != null ?  queryParam.getPageSize() : 10,
					sort);			
		}		
		return pageable;		
	}
	
	public static <T> T getEntity(Optional<T> optionalEntity) {
		
		if (optionalEntity != null) {
			return optionalEntity.get();
		}
		return null;
	}
}
