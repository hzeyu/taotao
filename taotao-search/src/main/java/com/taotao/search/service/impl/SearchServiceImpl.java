package com.taotao.search.service.impl;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.stereotype.Service;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Resource
	private SearchDao searchDao;
	@Resource
	private SolrServer solrServer;

	@Override
	public SearchResult searchByQuery(String queryString, Integer page, Integer rows) throws Exception {
		//组装查询条件
		SolrQuery query = new SolrQuery();
		query.setQuery(queryString);
		query.setStart((page-1)*rows);
		query.setRows(rows);
		
		//设置默认搜索域
		query.set("df", "item_keywords");
		
		//设置高亮
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		
		//查询
		SearchResult result = searchDao.searchByQuery(query);
		result.setNowPage((long)page);
		if(result.getTotalCount()%rows == 0) {
			result.setPageCount(result.getTotalCount()/rows);
		}else {
			result.setPageCount(result.getTotalCount()/rows+1);
		}
		
		return result;
	}

}
