package com.taotao.portal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.dataresult.R;
import com.taotao.portal.pojo.Order;

public interface OrderService {
	
	R createOrder(Order order,HttpServletRequest request
			,HttpServletResponse response);

}
