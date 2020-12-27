package com.taotao.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.dataresult.R;
import com.taotao.portal.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/logout")
	public String logout(String token) {
		R r = userService.logout(token);
		return "redirect:/";
	}

}
