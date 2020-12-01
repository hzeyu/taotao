package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TaotaoManagerController {
	
	//访问首页
	@RequestMapping("/")
	public String toIndex() {
		return "index";
	}
	
	//访问前端页面，传来哪一页去哪一页
	@RequestMapping("/{page}")
	public String toPage(String page) {
		return page;
	}

}
