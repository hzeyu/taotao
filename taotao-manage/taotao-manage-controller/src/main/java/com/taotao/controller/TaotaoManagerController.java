package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TaotaoManagerController {
	
	//������ҳ
	@RequestMapping("/")
	public String toIndex() {
		return "index";
	}
	
	//����ǰ��ҳ�棬������һҳȥ��һҳ
	@RequestMapping("/{page}")
	public String toPage(String page) {
		return page;
	}

}
