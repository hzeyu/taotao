package com.taotao.search.service;

import com.taotao.search.pojo.SearchResult;

public interface SearchService {

	SearchResult searchByQuery(String queryString,Integer page,Integer rows) throws Exception;
}
