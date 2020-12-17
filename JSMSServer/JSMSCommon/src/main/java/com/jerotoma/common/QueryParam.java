package com.jerotoma.common;

public class QueryParam {
	private static QueryParam queryParam;
	private Integer page; 
	private Integer pageSize; 
	private String orderby; 
	private String search; 
	private String status;
	private String type;
	private String fieldName;
	private Boolean featured;
	private Boolean mostShared;
	private Boolean active;
	private Double minPrice;
	private Double maxPrice;
	
	public static QueryParam getInstance() {
		if(queryParam == null) {
			queryParam = new QueryParam();
		}
		return queryParam;
	}
	
	public QueryParam(Integer page, Integer pageSize, String orderby, String search, String status, String fieldName) {
		this.page = page;
		this.pageSize = pageSize;
		this.orderby = orderby;
		this.search = search;
		this.status = status;
		this.fieldName = fieldName;
	}
	
	public QueryParam(Integer page, Integer pageSize, String orderby, String fieldName) {
		this.page = page;
		this.pageSize = pageSize;
		this.orderby = orderby;
		this.fieldName = fieldName;
	}
	
	public QueryParam(Integer page, Integer pageSize, String orderby, String search, String status) {
		this.page = page;
		this.pageSize = pageSize;
		this.orderby = orderby;
		this.search = search;
		this.status = status;
	}
	
	public QueryParam(Integer page, Integer pageSize) {
		this.page = page;
		this.pageSize = pageSize;		
	}
	
	public QueryParam(Integer page, Integer pageSize, String orderby) {
		this.page = page;
		this.pageSize = pageSize;
		this.orderby = orderby;		
	}
	
	public QueryParam() {
		
	}
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getFieldName() {
		
		return fieldName;
	}
	public void setFieldName(String fieldName) {		
		this.fieldName= fieldName;
	}

	public Boolean getFeatured() {
		return featured;
	}

	public void setFeatured(Boolean featured) {
		this.featured = featured;
	}

	public Boolean getMostShared() {
		return mostShared;
	}

	public void setMostShared(Boolean mostShared) {
		this.mostShared = mostShared;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}	
}
