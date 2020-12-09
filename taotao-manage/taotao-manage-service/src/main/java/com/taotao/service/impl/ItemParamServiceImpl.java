package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.dataresult.R;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService{
	
	@Resource
	private TbItemParamMapper itemParamMapper;

	@Override
	public R findItemParams(Integer page, Integer rows) {
		
		//开启分页
		PageHelper.startPage(page, rows);
		
		//查询
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		//paramData字段是大文本类型
		List<TbItemParam> itemParams = itemParamMapper.selectByExampleWithBLOBs(example);
		
		//封装结果集
		PageInfo<TbItemParam> pageInfo = new PageInfo<TbItemParam>(itemParams);
		R r = null;
		if(itemParams!=null && itemParams.size()>0) {
			r = r.ok();
			r.put("status", 200);
			r.put("rows", pageInfo.getList());
			r.put("total", pageInfo.getTotal());
		}
		return r;
	}

	@Override
	public R queryItemParamsByCid(Long itemcatid) {
		
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(itemcatid);
		List<TbItemParam> tbItemParams = itemParamMapper.selectByExampleWithBLOBs(example);

		R r = null;
		r = r.ok();
		if (tbItemParams!=null && tbItemParams.size()>0) {
			r.put("status", 200);
			r.put("data",tbItemParams.get(0));
			return r;
		}
		r.put("data", "");
		return r;
	}

	@Override
	public R saveItemParam(Long itemCatId,String paramData) {
		
		//组装参数
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(itemCatId);
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		itemParam.setParamData(paramData);
		
		R r = R.ok();
		
		int i = itemParamMapper.insertSelective(itemParam);
		if (i > 0){
			r.put("status", 200);
		}
		return r;
	}

}
