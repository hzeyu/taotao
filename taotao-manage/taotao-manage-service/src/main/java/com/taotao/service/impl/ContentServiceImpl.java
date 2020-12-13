package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.dataresult.R;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;
import com.taotao.utils.HttpClientUtil;

@Service
public class ContentServiceImpl implements ContentService {

	@Resource
	private TbContentMapper tbContentMapper;
	
	@Value("${REDIS_CACHE_SYNCHRO_PATH}")
	private String REDIS_CACHE_SYNCHRO_PATH;
	@Value("${REDIS_SERVER_BASE_PATH}")
	private String REDIS_SERVER_BASE_PATH;

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
			
			//存库成功，缓存同步
			//缓存操作不能影响程序执行，有异常要处理
			try {
				HttpClientUtil.doGet(REDIS_SERVER_BASE_PATH+
						REDIS_CACHE_SYNCHRO_PATH+categoryId);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return r;
	}

	@Override
	public R deleteContentdById(Long[] ids) {
		// 返回结果
		R r = R.ok();
		
		//得到categoryId
		TbContent content = tbContentMapper.selectByPrimaryKey(ids[0]);
		Long categoryId = content.getCategoryId();

		// 删除一组数据
		for (Long id : ids) {
			int i = tbContentMapper.deleteByPrimaryKey(id);
			if (i == 0) {
				r.put("status", 500);
				return r;
			}
		}

		r.put("status", 200);
		
		//删除成功，缓存同步
		//缓存操作不能影响程序执行，有异常要处理
		//得到categoryId
		String s = REDIS_SERVER_BASE_PATH+
				REDIS_CACHE_SYNCHRO_PATH+categoryId;
		try {
			HttpClientUtil.doGet(REDIS_SERVER_BASE_PATH+
					REDIS_CACHE_SYNCHRO_PATH+categoryId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return r;
	}

}
