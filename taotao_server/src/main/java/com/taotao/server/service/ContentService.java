package com.taotao.server.service;

import java.util.List;

import com.taotao.pojo.TbContent;

public interface ContentService {
	

	/**
	 * 用于展示广告，通过组分类id，查询全组广告
	 */
	List<TbContent> findContentsByCategoryId(Long categoryId);

}
