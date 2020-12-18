package com.taotao.search.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.dataresult.R;
import com.taotao.search.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Resource
	private ItemService itemService;
	
	@RequestMapping("/importAllItem")
	public R importAllItem() {
		System.out.println(1);
		R r = itemService.importAllItems();
		return r;
	}
}
