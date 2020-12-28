package com.taotao.portal.controller;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.dataresult.R;
import com.taotao.portal.service.SearchService;
import com.taotao.search.pojo.SearchResult;

@Controller
public class SearchController {
	
	@Resource
	private SearchService searchService;
	
	@RequestMapping(value = "/search",method = RequestMethod.GET)
	public String query(@RequestParam(value = "q")String queryString,
			@RequestParam(defaultValue = "1")Integer page,
			@RequestParam(defaultValue = "60")Integer rows,
			Model model) {
		
		if(queryString!=null && queryString!="") {
			try {
				queryString = new String(queryString.getBytes("iso8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SearchResult searchResult = searchService.getItemList(queryString, page, rows);
		
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages",  searchResult.getPageCount());
		model.addAttribute("page", page);
		
		return "search";
	}

}
