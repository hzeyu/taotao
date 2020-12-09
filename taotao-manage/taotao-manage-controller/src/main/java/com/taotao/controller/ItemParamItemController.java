package com.taotao.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.service.ItemParamItemService;

@Controller
public class ItemParamItemController {
	
	@Resource
	private ItemParamItemService itemParamItemService;

	@RequestMapping("/aaa/{itemId}")
	public String showItemParamItem(@PathVariable Long itemId,Model model) {
		String string = itemParamItemService.getItemParamByItemId(itemId);
		model.addAttribute("string", string);
		return "test";
	}
}
