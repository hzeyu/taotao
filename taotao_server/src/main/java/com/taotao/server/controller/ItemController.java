package com.taotao.server.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.dataresult.R;
import com.taotao.server.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Resource
	private ItemService itemService;
	
	@RequestMapping(value = "/info/{itemId}",method = RequestMethod.GET)
	public R getItemInfo(@PathVariable Long itemId) {
		R r = itemService.getItemInfoByItemId(itemId);
		return r;
	}
	
	@RequestMapping(value = "/desc/{itemId}",method = RequestMethod.GET)
	public R getItemDesc(@PathVariable Long itemId) {
		R r = itemService.getItemDescByItemId(itemId);
		return r;
	}
	
	@RequestMapping(value = "/param/{itemId}",method = RequestMethod.GET)
	public R getItemParam(@PathVariable Long itemId) {
		R r = itemService.getItemParamByItemId(itemId);
		return r;
	}

}
