package com.taotao.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.pool.PreparedStatementPool.MethodType;
import com.taotao.dataresult.R;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;

@RestController
@RequestMapping("/item/param")
public class ItemParamController {
	
	@Resource
	private ItemParamService itemParamService;
	
	@RequestMapping("/list")
	public R showItemParamList(@RequestParam(defaultValue = "1")Integer page,
			@RequestParam(defaultValue = "30")Integer rows) {
		
		R r = itemParamService.findItemParams(page, rows);
		
		return r;
		
	}
	
	@RequestMapping("/query/itemcatid/{itemcatid}")
	public R queryItemParamByCid(@PathVariable Long itemcatid) {
		R r = itemParamService.queryItemParamsByCid(itemcatid);
		
		return r;
	}

	@RequestMapping(value = "/save/{cid}",method = RequestMethod.POST)
	public R saveItemParam(@PathVariable("cid") Long itemCatId,String paramData) {
		R r = itemParamService.saveItemParam(itemCatId,paramData);
		return r;
	}
}
