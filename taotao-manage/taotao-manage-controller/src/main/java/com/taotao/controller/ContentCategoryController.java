package com.taotao.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.dataresult.EUITreeDate;
import com.taotao.dataresult.R;
import com.taotao.service.ContentCategoryService;

@RequestMapping("/content/category")
@RestController
public class ContentCategoryController {

	@Resource
	private ContentCategoryService contentCategoryService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<EUITreeDate> getContentCategoryList(@RequestParam(defaultValue = "0", value = "id") Long parentId) {

		List<EUITreeDate> list = contentCategoryService.findContentCategoryListByParentId(parentId);

		return list;
	}
	
	@RequestMapping("/create")
	public R addContentCategoryNode(Long parentId,String name) {
		R r = contentCategoryService.createContentCategory(parentId, name);
		return r;
	}

	@RequestMapping("/update")
	public R updateContentCategoryNode(Long id,String name) {
		R r = contentCategoryService.updateContentCategory(id, name);
		return r;
	}
	
	@RequestMapping("/delete")
	public R delContentCategoryNode(Long parentId,Long id) {
		R r = contentCategoryService.delContentCategory(parentId, id);
		return r;
	}
}
