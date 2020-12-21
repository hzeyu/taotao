package com.taotao.portal.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbItemDesc;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Resource
	private ItemService itemService;
	
	@RequestMapping("/{itemId}")
	public String getItemInfo(@PathVariable Long itemId,Model model) {
		ItemInfo itemInfo = itemService.getItemInfoByItemId(itemId);
		model.addAttribute("item",itemInfo);
		return "item";
	}
	
	@RequestMapping("/desc/{itemId}")
	public String getItemDesc(@PathVariable Long itemId,Model model) {
		TbItemDesc itemDesc = itemService.getItemDescByItemId(itemId);
		model.addAttribute("itemDesc",itemDesc);
		return "item";
	}
	
	@RequestMapping(value = "/param/{itemId}", produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemParam(@PathVariable Long itemId) {
		String htmlString = itemService.getItemParamByItemId(itemId);
		
		return htmlString;
	}

}
