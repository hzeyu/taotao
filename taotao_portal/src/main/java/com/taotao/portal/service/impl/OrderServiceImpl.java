package com.taotao.portal.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbOrderItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
/**
 * 
 * @ClassName:  OrderServiceImpl   
 * @Description:订单service
 * @author: hanzeyu
 * @date:   2020年12月28日 下午8:44:50      
 * @Copyright:
 */
@Service
public class OrderServiceImpl implements OrderService{
	
	@Resource
	private CartService cartService;
	
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;

	/**
	 * 
	 * <p>Title: createOrder</p>   
	 * <p>Description:创建订单 </p>   
	 * @param order
	 * @return   
	 * @see com.taotao.portal.service.OrderService#createOrder(com.taotao.portal.pojo.Order)
	 */
	@Override
	public R createOrder(Order order,HttpServletRequest request,HttpServletResponse response) {
		//调用order服务
		String doPostJson = HttpClientUtil.doPostJson(ORDER_BASE_URL+ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		
		//将购物车中提交订单的商品删除
		List<TbOrderItem> orderItems = order.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			Long itemId = new Long(tbOrderItem.getItemId());
			cartService.deleteCart(itemId, request, response);
		}
		//格式转换
		R r = JsonUtils.jsonToPojo(doPostJson, R.class);
		return r;
	}

}
