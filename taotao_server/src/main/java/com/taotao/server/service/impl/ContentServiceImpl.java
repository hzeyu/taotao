package com.taotao.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.server.jedis.TaotaoJedisClient;
import com.taotao.server.service.ContentService;
import com.taotao.utils.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService{
	
	@Resource
	private TbContentMapper contentMapper;
	@Resource
	private TaotaoJedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	
	/**
	 * 用于展示广告，通过组分类id，查询全组广告
	 */
	@Override
	public List<TbContent> findContentsByCategoryId(Long categoryId) {
		
		//当前只有首页广告用到缓存，把定义redis缓存的键抽取局部变量
		//以后其他地方也用到的话就抽一个方法，全局变量定义常量，换局部变量，代码变一个局部变量名就ok
		String hkey = INDEX_CONTENT_REDIS_KEY;
		
		//查询之前查看是否有缓存，有缓存直接返回结果,缓存不能影响程序正常执行，有异常要处理
		try {
			String hget = jedisClient.hget(hkey, categoryId.toString());
			
			if(hget!="" && hget!=null) {
				List<TbContent> contents = JsonUtils.jsonToList(hget, TbContent.class);
				return contents;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//查询数据
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> contents = contentMapper.selectByExample(example );
		
		//将查询结果添加到缓存中,缓存不能影响程序正常执行，有异常要处理
		String contensdJson = JsonUtils.objectToJson(contents);
		try {
			jedisClient.hset(hkey, categoryId.toString(), contensdJson);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return contents;
	}

}
