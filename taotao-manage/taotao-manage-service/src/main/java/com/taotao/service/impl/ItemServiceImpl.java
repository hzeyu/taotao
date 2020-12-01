package com.taotao.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.dataresult.R;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
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

	@Override
	public R getItemList(Integer page, Integer rows) {
		
		//开启分页
		PageHelper.startPage(page,rows);
		
		//查询商品列表
		TbItemExample tbItemExample = new TbItemExample();
		Criteria criteria = tbItemExample.createCriteria();
		List<TbItem> items = itemMapper.selectByExample(tbItemExample);
		
		//分装结果集
		if(items!=null && items.size()>0) {
			PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(items);
			
			R r = R.ok();
			r.put("rows", pageInfo.getList());
			r.put("total",pageInfo.getTotal());
			
			return r;
		}
		
		new RuntimeException("没有查询到结果集");
		return null;
	}

}
