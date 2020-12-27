package com.taotao.portal.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.dataresult.R;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Resource
	private CartService cartService;
	
	@RequestMapping("/success")
	public String toCarSuccess() {
		return "cartSuccess";
	}
	@RequestMapping("/error")
	public String toCarError() {
		return "cartError";
	}
	
	@RequestMapping("/add/{itemId}")
	public String addCart(@PathVariable Long itemId,@RequestParam(defaultValue = "1") Integer num,
			HttpServletResponse response ,HttpServletRequest request) {
		
		R r = cartService.addCart(itemId, num, request, response);
		
		if(r.getCode() == 0) {
			return "redirect:/cart/success.html";
		}
		return "redirect:/cart/error.html";
		
	}
	
	
	@RequestMapping("/cart")
	public String showCartList(HttpServletRequest request,Model model) {
		List<CartItem> cartList = cartService.getCartList(request);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response) {
		R r = cartService.deleteCart(itemId, request, response);
		
		return "redirect:/cart/cart.html";
	}

}
