package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.dataresult.R;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Resource
	private TbContentMapper tbContentMapper;

	@Override
	public R getContentListByContentCategoryId(Long categoryId, Integer page, Integer rows) {
		// 返回结果
		R r = R.ok();

		// 开启分页
		PageHelper.startPage(page, rows);

		// 查询
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> contents = tbContentMapper.selectByExample(example);

		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(contents);

		r.put("rows", pageInfo.getList());
		r.put("total", pageInfo.getTotal());
		r.put("status", 200);

		return r;
	}

	@Override
	public R saveContentWithCategoryId(Long categoryId, TbContent content) {
		// 返回结果
		R r = R.ok();

		// 组装参数
		content.setCreated(new Date());
		content.setUpdated(new Date());
		content.setCategoryId(categoryId);

		// 存库
		int i = tbContentMapper.insertSelective(content);

		if (i > 0) {
			r.put("status", 200);
			r.put("data", content);
		}

		return r;
	}

	@Override
	public R deleteContentdById(Long[] ids) {
		// 返回结果
		R r = R.ok();

		// 删除一组数据
		for (Long id : ids) {
			int i = tbContentMapper.deleteByPrimaryKey(id);
			if (i == 0) {
				r.put("status", 500);
				return r;
			}
		}

		r.put("status", 200);

		return r;
	}

}
