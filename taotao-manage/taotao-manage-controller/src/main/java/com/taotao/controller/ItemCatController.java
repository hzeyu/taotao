package com.taotao.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.dataresult.EUITreeDate;
import com.taotao.service.ItemCatService;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Resource
	private ItemCatService itemCatService;
	
	@RequestMapping("/list")
	public List getCatList(@RequestParam(defaultValue = "0",value = "id")Long parentId) {
		List<EUITreeDate> list = itemCatService.findItemCatsByParentId(parentId);
		
		return list;
	}

}
