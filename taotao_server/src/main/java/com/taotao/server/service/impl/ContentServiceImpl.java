package com.taotao.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.server.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService{
	
	@Resource
	private TbContentMapper contentMapper;
	/**
	 * 用于展示广告，通过组分类id，查询全组广告
	 */
	@Override
	public List<TbContent> findContentsByCategoryId(Long categoryId) {
		// TODO Auto-generated method stub
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> contents = contentMapper.selectByExample(example );
		
		return contents;
	}

}
