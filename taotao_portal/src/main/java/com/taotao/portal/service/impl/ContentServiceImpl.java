package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;
import com.taotao.utils.FastJsonUtil;
import com.taotao.utils.HttpClientUtil;

@Service
public class ContentServiceImpl implements ContentService{
	
	@Value("${SERVER_BASE_URL}")
	private String SERVER_BASE_URL;
	@Value("${INDEX_SLIDE_AD_URL}")
	private String INDEX_SLIDE_AD_URL;

	/**
	 * 得到首页广告内容
	 * 从服务工程远程获取
	 */
	@Override
	public List<Map> getContentList() {
		//返回值
		List<Map> list = new ArrayList<Map>();
		
		//请求地址
		String url = SERVER_BASE_URL+INDEX_SLIDE_AD_URL;
		//请求服务
		String data = HttpClientUtil.doGet(url);
		
		//处理结果
		List<TbContent> contents = (List<TbContent>) FastJsonUtil.getJavaListForLikeJsonString(data, "rows", TbContent.class);
		for (TbContent tbContent : contents) {
			Map map = new HashMap();
			map.put("srcB", tbContent.getPic2());
			map.put("height", 240);
			map.put("alt", "");
			map.put("width", 670);
			map.put("src", tbContent.getPic());
			map.put("widthB", 550);
			map.put("href", tbContent.getUrl());
			map.put("heightB", 240);
			list.add(map);
		}
		return list;
	}

}
