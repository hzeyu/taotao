package com.taotao.search.controller;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.dataresult.R;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@RestController
public class SearchController {

	@Resource
	private SearchService searchService;
	
	@RequestMapping(value = "/query",method = RequestMethod.GET)
	public R searchByQuery(@RequestParam(value = "q")String queryString,
			@RequestParam(defaultValue = "1")Integer page,
			@RequestParam(defaultValue = "60")Integer rows) {
		R r = null;
		
		if(queryString=="" && queryString==null) {
			r = R.ok();
			r.put("status", 400);
			r.put("message", "查询条件不能为空");
			return r;
		}
		
		SearchResult result = null;
		try {
			queryString = new String(queryString.getBytes("iso8859-1"),"utf-8");
			result = searchService.searchByQuery(queryString, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
			r = R.error();
			r.put("status", 500);
			r.put("message", e.getMessage());
			return r;
		}
		
		r = R.ok();
		r.put("status", 200);
		r.put("result", result);
		return r;
	}
}
