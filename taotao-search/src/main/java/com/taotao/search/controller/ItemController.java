package com.taotao.search.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		R r = itemService.importAllItems();
		return r;
	}
	
	@RequestMapping(value = "/addItem/{itemId}",method = RequestMethod.GET)
	public R addItem(@PathVariable String itemId) {
		R r = itemService.importItem(itemId);
		return r;
	}
}
