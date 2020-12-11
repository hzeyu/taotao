package com.taotao.server.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbContent;
import com.taotao.server.service.ContentService;

@RestController
@RequestMapping("/content")
public class ContentController {
	
	@Resource
	private ContentService contentService;
	
	@RequestMapping(value = "/query/list/{categoryId}",method = RequestMethod.GET)
	public R findContentByCategoryId(@PathVariable Long categoryId) {
		
		R r = null;
		
		//发布服务，如果中间遇到可能存在的异常，将异常结果发给调用处
		try {
			//正常发布服务
			List<TbContent> contents = contentService.findContentsByCategoryId(categoryId);
			
			r = R.ok();
			r.put("status", 200);
			r.put("rows", contents);
			return r;
			
		} catch (Exception e) {
			//发布服务异常
			e.printStackTrace();
			r = R.error();
			r.put("status", 500);
			r.put("message", e.getMessage());
			return r;
		}
	}
	
	

}
