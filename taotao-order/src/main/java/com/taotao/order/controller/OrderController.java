package com.taotao.order.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.taotao.dataresult.R;
import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;

@RestController
public class OrderController {
	
	@Resource
	private OrderService orderService;
	
	@RequestMapping(value = "/create",method = RequestMethod.POST)
	@ResponseBody
	public R createOrder(@RequestBody Order order) {
		R r = orderService.createOrder(order, order.getOrderItems(), order.getOrderShipping());
		return r;
	}

}
