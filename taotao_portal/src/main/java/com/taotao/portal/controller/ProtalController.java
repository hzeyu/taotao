package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProtalController {
	
	@RequestMapping("/index")
	public String toIndex() {
		System.out.println(111);
		return "index";
	}

}
