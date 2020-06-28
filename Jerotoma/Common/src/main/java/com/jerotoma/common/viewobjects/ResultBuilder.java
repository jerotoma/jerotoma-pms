package com.jerotoma.common.viewobjects;

import java.util.List;

public class ResultBuilder<T> {
	private Integer count;
	private Integer pageCount;
	private Integer pageSize;
	private Integer currentPage;
	private List<T> dataList;
	
	public Integer getCount() {
		return count;
	}
	
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public Integer getPageCount() {
		return pageCount;
	}
	
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	
	public List<T> getDataList() {
		return dataList;
	}
	
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}	
}
