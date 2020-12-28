package com.taotao.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@RestController
@RequestMapping("/content")
public class ContentController {
	
	@Resource
	private ContentService contentService;
	
	@RequestMapping(value = "/query/list",method = RequestMethod.GET)
	public R queryList(Long categoryId,@RequestParam(defaultValue = "1")Integer page,
			@RequestParam(defaultValue = "30")Integer rows) {
		
		R r = contentService.getContentListByContentCategoryId(categoryId, page, rows);
		return r;
	}
	
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public R saveContent(Long categoryId,TbContent content) {
		R r = contentService.saveContentWithCategoryId(categoryId, content);
		return  r;
	}
	
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public R delContents(Long[] ids) {
		R r = contentService.deleteContentdById(ids);
		return r;
		
	}

}
