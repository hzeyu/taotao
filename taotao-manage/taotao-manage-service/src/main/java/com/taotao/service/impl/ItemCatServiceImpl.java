package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taotao.dataresult.EUITreeDate;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	
	@Resource
	private TbItemCatMapper itemCatMapper;

	@Override
	public List<EUITreeDate> findItemCatsByParentId(Long parentId) {
		
		//根据parentid查询子菜单列表
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> itemCats = itemCatMapper.selectByExample(example);
		
		//存放结果集合
		List<EUITreeDate> list = new ArrayList<EUITreeDate>();
		
		for (TbItemCat tbItemCat : itemCats) {
			//将结果封装成EUITreeDate类型
			EUITreeDate euiTreeDate = new EUITreeDate();
			euiTreeDate.setId(tbItemCat.getId());
			euiTreeDate.setText(tbItemCat.getName());
			euiTreeDate.setState(tbItemCat.getIsParent()?"closed":"open");
			
			list.add(euiTreeDate);
		}
		
		return list;
	}

}
