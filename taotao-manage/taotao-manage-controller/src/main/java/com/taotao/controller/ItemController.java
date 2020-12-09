package com.taotao.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Resource
	private ItemService itemService;
	
	@RequestMapping("/findItemById")
	public TbItem findItemById(Long id) {
		TbItem item = itemService.findItemById(id);
		System.out.println(item);
		return item;
	}
	
	@RequestMapping("/list")
	public R showItemList(Integer page,Integer rows) {
		try {
			R r = itemService.getItemList(page, rows);
			return r;
		} catch (Exception e) {
			
			e.printStackTrace();
			return R.error(e.getMessage());
		}
		
	}
	
	@RequestMapping("/save")
	public R saveItem(TbItem item,String desc,String itemParams) {
		R r = null;
		try {
			r = itemService.saveItem(item,desc,itemParams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}
}
