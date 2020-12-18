package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.dataresult.R;
import com.taotao.portal.service.SearchService;
import com.taotao.search.pojo.SearchResult;
import com.taotao.utils.FastJsonUtil;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Value("${SOLR_SERVER_BASE_PATH}")
	private String SOLR_SERVER_BASE_PATH;
	@Value("${SOLR_SERVER_QUERY_PATH}")	
	private String SOLR_SERVER_QUERY_PATH;

	@Override
	public SearchResult getItemList(String queryString, Integer page, Integer rows) {
		//查询
		Map<String,String> param = new HashMap();
		param.put("q", queryString);
		param.put("page", page.toString());
		param.put("rows", rows.toString());
		String searchResultJson = HttpClientUtil.doGet(SOLR_SERVER_BASE_PATH+SOLR_SERVER_QUERY_PATH, param);
		
		//转成对象
		R r = JsonUtils.jsonToPojo(searchResultJson, R.class);
		Object object = r.get("result");
		String objectToJson = JsonUtils.objectToJson(object);
		SearchResult searchResult = JsonUtils.jsonToPojo(objectToJson, SearchResult.class);
		
		
		return searchResult;
	}

}
