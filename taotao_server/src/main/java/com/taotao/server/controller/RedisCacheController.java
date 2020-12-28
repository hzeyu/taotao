package com.taotao.server.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.dataresult.R;
import com.taotao.server.service.RedisCacheService;

@RestController
@RequestMapping("/redis/cache")
public class RedisCacheController {

	@Resource
	private RedisCacheService redisCacheService;
	
	@RequestMapping("/synchro/{categoryId}")
	public R synchro(@PathVariable Long categoryId) {
		R r = redisCacheService.synchro(categoryId);
		return r;
	}
}
