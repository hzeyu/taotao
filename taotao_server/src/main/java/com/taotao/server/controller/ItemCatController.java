package com.taotao.server.controller;

import javax.annotation.Resource;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.server.pojo.CatResult;
import com.taotao.server.service.ItemCatService;


@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Resource
	private ItemCatService itemCatService;
	
	@RequestMapping("/list/all")
	public Object findAllCatForCatResult(String callback) {
		CatResult catResult = itemCatService.findAllCatForCatResult();
		
		//spring提供的工具，跨域请求只支持js跨域，这个类可以将数据拼接成js函数
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);
		//将数据凭借成js函数callback(catResult); 格式json
		mappingJacksonValue.setJsonpFunction(callback);
		
		return mappingJacksonValue;
	}

}
