package com.taotao.portal.service;

import com.taotao.dataresult.R;
import com.taotao.search.pojo.SearchResult;

public interface SearchService {
	
	SearchResult getItemList(String queryString,Integer page,Integer rows);

}
