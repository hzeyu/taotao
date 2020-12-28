package com.taotao.portal.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.dataresult.R;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;
import com.taotao.utils.JsonUtils;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Resource
	private CartService cartService;
	@Resource
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request,Model model) {
		List<CartItem> cartList = cartService.getCartList(request);
		model.addAttribute("cartList", cartList);
		return "order-cart";
	}
	
	@RequestMapping("/create")
	public String createOrder(Order order,Model model,HttpServletRequest request,HttpServletResponse response) {
		R r = orderService.createOrder(order,request,response);
		
		if(r.get("status").equals(200)) {
			Object object = r.get("data");
			model.addAttribute("orderId", object);
			model.addAttribute("payment", order.getPayment());
			model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
			return "success";
		}

		model.addAttribute("message", "网络出错了，请稍后再试");
		return "/error/exception";
	}

}
