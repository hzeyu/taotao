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
		
		//������ҳ
		PageHelper.startPage(page,rows);
		
		//��ѯ��Ʒ�б�
		TbItemExample tbItemExample = new TbItemExample();
		Criteria criteria = tbItemExample.createCriteria();
		List<TbItem> items = itemMapper.selectByExample(tbItemExample);
		
		//��װ�����
		if(items!=null && items.size()>0) {
			PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(items);
			
			R r = R.ok();
			r.put("rows", pageInfo.getList());
			r.put("total",pageInfo.getTotal());
			
			return r;
		}
		
		new RuntimeException("û�в�ѯ�������");
		return null;
	}

}
