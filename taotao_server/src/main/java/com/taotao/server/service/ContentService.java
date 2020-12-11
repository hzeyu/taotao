package com.taotao.server.service;

import java.util.List;

import com.taotao.pojo.TbContent;

public interface ContentService {
	
	List<TbContent> findContentsByCategoryId(Long categoryId);

}
