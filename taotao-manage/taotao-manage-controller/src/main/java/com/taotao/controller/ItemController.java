package com.taotao.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Resource
	private ItemService itemService;
	
	@RequestMapping("/findItemById")
	@ResponseBody
	public TbItem findItemById(Long id) {
		TbItem item = itemService.findItemById(id);
		System.out.println(item);
		return item;
	}

}
