package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.dataresult.R;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
import com.taotao.utils.IDUtils;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Resource
	private TbItemMapper itemMapper;
	@Resource
	private TbItemDescMapper itemDescMapper;
	@Resource
	private TbItemParamItemMapper itemParamItemMapper;

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
		
		//封装结果集
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

	@Override
	public R saveItem(TbItem item,String itemDesc,String itemParams) throws Exception {
		//补全item信息
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//刚新增的商品默认状态正常
		item.setStatus((byte)1);
		Date created = new Date();
		Date upDate = new Date();
		item.setCreated(created);
		item.setUpdated(upDate);
		
		//添加数据库
		R r = null;
		int i = itemMapper.insertSelective(item);
		
		//商品描述是大文本类型，项目中将商品描述单独拆成一个表，保存商品后将商品描述入库
		//组合商品描述信息
		TbItemDesc iTbItemDesc = new TbItemDesc();
		iTbItemDesc.setItemId(itemId);
		iTbItemDesc.setItemDesc(itemDesc);
		iTbItemDesc.setCreated(created);
		iTbItemDesc.setUpdated(upDate);
		//存库
		int j = itemDescMapper.insertSelective(iTbItemDesc);
		
		//持久化商品的规格参数
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		itemParamItem.setCreated(created);
		itemParamItem.setUpdated(upDate);
		//存库
		int k = itemParamItemMapper.insertSelective(itemParamItem);
		
		if(i > 0 && j > 0 && k>0) {
			r = R.ok();
			//添加成功，状态码为200
			r.put("status", 200);
		}else {
			throw new Exception("添加失败");
		}
		return r;
	}

}
