package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taotao.dataresult.EUITreeDate;
import com.taotao.dataresult.R;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{
	
	@Resource
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EUITreeDate> findContentCategoryListByParentId(Long parentId) {
		
		//返回结果list集合
		List<EUITreeDate> resultList = new ArrayList<EUITreeDate>();
		
		//根据传来的parentId查询此节点下面的子节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		
		//将结果封装到EUITreeDate中
		for (TbContentCategory tbContentCategory : list) {
			//EUITree返回值类型
			EUITreeDate eDate = new EUITreeDate();
			eDate.setId(tbContentCategory.getId());
			eDate.setText(tbContentCategory.getName());
			eDate.setState(tbContentCategory.getIsParent()?"closed":"open");
			
			resultList.add(eDate);
		}
		
		return resultList;
	}

	@Override
	public R createContentCategory(Long parentId, String name) {
		
		R r = R.ok();
		
		//拼接插入数据
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setParentId(parentId);
		contentCategory.setIsParent(false);
		contentCategory.setName(name);
		contentCategory.setSortOrder(1);
		contentCategory.setStatus(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		
		//插入数据,回显时要id，修改mapper，返回自增主键
		int i = contentCategoryMapper.insertSelective(contentCategory);
		
		if(i > 0) {
			r.put("status", "200");
			r.put("data", contentCategory);
		}
		
		//插入后判断父节点在数据库中是否是父节点，如果不是，修改
		TbContentCategory parentTbContentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parentTbContentCategory.getIsParent()) {
			parentTbContentCategory.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKeySelective(parentTbContentCategory);
		}
		return r;
	}

	@Override
	public R updateContentCategory(Long id, String name) {
		//拼接插入数据
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setId(id);
		contentCategory.setName(name);
		
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		
		return R.ok();
	}

	@Override
	public R delContentCategory(Long parentId, Long id) {
		// 删除数据
		int i = contentCategoryMapper.deleteByPrimaryKey(id);
		
		//删除后判断父节点下还有没有子节点，如果没有，将父节点改为叶子节点
		int j = contentCategoryMapper.selectCountChilren(parentId);
		if(j==0) {
			TbContentCategory contentCategory = new TbContentCategory();
			contentCategory.setId(parentId);
			contentCategory.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		}
		
		
		return R.ok();
	}

}
