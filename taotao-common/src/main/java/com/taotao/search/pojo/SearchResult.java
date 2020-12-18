package com.taotao.search.pojo;

import java.util.List;

public class SearchResult {
	
	private List<Item> itemList;
	private Long totalCount;
	private Long pageCount;
	private Long nowPage;
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public Long getPageCount() {
		return pageCount;
	}
	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}
	public Long getNowPage() {
		return nowPage;
	}
	public void setNowPage(Long nowPage) {
		this.nowPage = nowPage;
	}
}
