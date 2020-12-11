package com.taotao.portal.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.taotao.dataresult.R;
import com.taotao.portal.service.ContentService;

@Controller
public class ProtalController {
	
	@Resource
	private ContentService contentService;
	
	@RequestMapping("/index")
	public String toIndex(Model model) {
		List<Map> rs = contentService.getContentList();
		
		//将返回的数据在转换成json字符串，当前方法返回视图，不自动转换json
		Object adJson = JSON.toJSON(rs);
		
		model.addAttribute("ad1", adJson);
		
		return "index";
	}

}
