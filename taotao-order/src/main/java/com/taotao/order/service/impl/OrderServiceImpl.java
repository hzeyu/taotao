package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.dataresult.R;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.jedis.TaotaoJedisClient;
import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import com.taotao.utils.StringUtil;

@Service
public class OrderServiceImpl implements OrderService {

	@Resource
	private TbOrderMapper tbOrderMapper;
	@Resource
	private TbOrderItemMapper tbOrderItemMapper;
	@Resource
	private TbOrderShippingMapper tbOrderShippingMapper;
	@Resource
	private TaotaoJedisClient jedisClient;

	@Value("${ORDER_ID_KEY}")
	private String ORDER_ID_KEY;
	@Value("${ORDER_ID_START}")
	private String ORDER_ID_START;
	@Value("${ORDER_DETAIL_ID_KEY}")
	private String ORDER_DETAIL_ID_KEY;

	/**
	 * 
	 * <p>
	 * Title: createOrder
	 * </p>
	 * <p>
	 * Description:创建订单
	 * </p>
	 * 
	 * @param order         订单详情
	 * @param orderItems    订单商品
	 * @param orderShipping 物流信息
	 * @return
	 * @see com.taotao.order.service.OrderService#createOrder(com.taotao.order.pojo.Order,
	 *      java.util.List, com.taotao.pojo.TbOrderShipping)
	 */
	@Override
	public R createOrder(Order order, List<TbOrderItem> orderItems, TbOrderShipping orderShipping) {
		// 返回结果
		R r = null;
		int insertOrderShipping  = 0;
		int insertOrder = 0;
		int insertOrderItem = 0;

		// 添加订单
		// 生成订单id（使用redis给定初值，每次自增）
		// 从redis中取值，没有初值设置初值，有初值自增
		String orderId = jedisClient.get(ORDER_ID_KEY);
		if (StringUtil.isNull(orderId)) {
			jedisClient.set(ORDER_ID_KEY, ORDER_ID_START);
		}
		orderId = jedisClient.incr(ORDER_ID_KEY) + "";
		// 补全订单信息
		order.setOrderId(orderId);
		// 是否评价 0 未评价 1已评价，新建订单默认没有评价，待用户收货评价后修改状态
		order.setBuyerRate(0);
		// 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭 ,默认未支付，待支付操作或其他操作后修改状态
		order.setStatus(1);
		Date date = new Date();
		order.setUpdateTime(date);
		order.setCreateTime(date);
		// 添加
		insertOrder = tbOrderMapper.insertSelective(order);

		// 添加订单商品
		// 遍历商品列表，循环插入
		for (TbOrderItem tbOrderItem : orderItems) {
			// 使用redis生成订单明细id
			String orderDetailId = jedisClient.incr(ORDER_DETAIL_ID_KEY) + "";
			// 补全商品明细信息
			tbOrderItem.setId(orderDetailId);
			tbOrderItem.setOrderId(orderId);
			// 添加
			insertOrderItem = tbOrderItemMapper.insertSelective(tbOrderItem);
		}

		// 添加物流信息
		// 补全物流信息
		orderShipping.setOrderId(orderId);
		orderShipping.setUpdated(date);
		orderShipping.setCreated(date);
		// 添加
		insertOrderShipping = tbOrderShippingMapper.insertSelective(orderShipping);

		// 全部正常添加成功
		if(insertOrderShipping  > 0 &&
				insertOrder > 0 &&
				insertOrderItem > 0) {
			r = R.ok("ok");
			r.put("status", 200);
			r.put("data", orderId);
		}else {
			//至少有一项内容添加不成功
			r = R.ok("ok");
			r.put("status", 400);
			r.put("data", "");
		}
		return r;

	}

}
