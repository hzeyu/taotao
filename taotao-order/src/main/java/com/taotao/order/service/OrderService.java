package com.taotao.order.service;

import java.util.List;

import com.taotao.dataresult.R;
import com.taotao.order.pojo.Order;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

public interface OrderService {
	
	R createOrder(Order order,List<TbOrderItem> orderItems,TbOrderShipping orderShipping);

}
