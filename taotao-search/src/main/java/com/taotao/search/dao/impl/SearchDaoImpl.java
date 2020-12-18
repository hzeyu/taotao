package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;

@Repository
public class SearchDaoImpl implements SearchDao{
	
	@Resource
	private SolrServer solrServer;

	@Override
	public SearchResult searchByQuery(SolrQuery query) throws Exception {
		//返回结构值
		SearchResult result = new SearchResult();
		
		//查询
		QueryResponse response = solrServer.query(query);
		SolrDocumentList results = response.getResults();	
		//高亮
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		//封装结果
		List<Item> items = new ArrayList<Item>();
		for (SolrDocument document : results) {
			Item item = new Item();
			item.setId((String) document.get("id"));
			item.setCategory_name((String) document.get("item_category_name"));
			item.setImage((String) document.get("item_image"));
			item.setPrice((Long) document.get("item_price"));
			item.setSell_point((String) document.get("item_sell_point"));
			//设置高亮字段
			List<String> list = highlighting.get(document.get("id")).get("item_title");
			String title = "";
			if (list!=null && list.size()>0) {
				title = list.get(0);
			} else {
				title = document.get("item_title")+"";
			}
			item.setTitle(title);
			
			items.add(item);
		}
		
		result.setItemList(items);
		result.setTotalCount(results.getNumFound());
		return result;
	}

}
