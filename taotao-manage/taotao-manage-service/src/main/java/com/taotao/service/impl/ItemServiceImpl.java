package com.taotao.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Resource
	private TbItemMapper itemMapper;

	@Override
	public TbItem findItemById(Long id) {
		TbItem item = itemMapper.selectByPrimaryKey(id);
		return item;
	}

}
