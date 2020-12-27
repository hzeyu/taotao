package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.utils.CookieUtils;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.StringUtil;
/**
 * 
 * @ClassName:  CartServiceImpl   
 * @Description:购物车service
 * @author: hanzeyu
 * @date:   2020年12月27日 下午9:20:26      
 * @Copyright:
 */
@Service
public class CartServiceImpl implements CartService{
	
	@Value("${CARTITEM_COOKIE}")
	private String CARTITEM_COOKIE;
	@Value("${SERVER_BASE_URL}")
	private String SERVER_BASE_URL;
	@Value("${SERVER_QUERY_ITEMINFO_URL}")
	private String SERVER_QUERY_ITEMINFO_URL;

	/**
	 * <p>Title: addCart</p>   
	 * <p>Description: 添加购物车空，购物车商品放在cookie中</p>   
	 * @param itemId 添加的商品id
	 * @param num 添加数量
	 * @param request
	 * @param response
	 * @return   
	 * @see com.taotao.portal.service.CartService#addCart(java.lang.Long, java.lang.Integer, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public R addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
		//返回结果
		R r = null;
		CartItem item = null;
		
		try {
			//从cookie中取商品列表
			List<CartItem> cartList = getCartList(request);
			
			//商品列表不为空
			if(cartList!=null && cartList.size()>0) {
				//判断列表中是否有要添加的商品
				for (CartItem cartItem : cartList) {
					if(cartItem.getId()-itemId == 0) {
						//如果已有要添加的商品，直接把该商品的数量累积
						cartItem.setNum(cartItem.getNum()+num);
						item = cartItem;
						break;
					}
				}
			}
			
			//如果没有将该商品添加到购物车列表
			if(item == null) {
				//通过商品id查询商品详情（调用server服务）
				String doGet = HttpClientUtil.doGet(SERVER_BASE_URL+SERVER_QUERY_ITEMINFO_URL+itemId);
				//数据转换
				R rJson = JsonUtils.jsonToPojo(doGet, R.class);
				Object tbItemObject = rJson.get("data");
				String tbItemJson = JsonUtils.objectToJson(tbItemObject);
				TbItem tbItem = JsonUtils.jsonToPojo(tbItemJson, TbItem.class);
				
				//将商品详情信息封装成购物车需要的格式
				item = new CartItem();
				BeanUtils.copyProperties(tbItem,item);
				item.setNum(num);
				item.setImage(tbItem.getImage().split(",")[0]);
				
				//添加到购物车列表
				cartList.add(item);
			}
			
			//将购物车列表存到cookie中
			CookieUtils.setCookie(request, response, CARTITEM_COOKIE, JsonUtils.objectToJson(cartList),true);
			
			//逻辑没有出错
			r = R.ok("ok");
			r.put("status", 200);
			return r;
		} catch (Exception e) {
			r = R.error(e.getStackTrace()+"");
			r.put("status", 500);
			return r;
		}
	}

	/**
	 * 
	 * @Title: getCartList   
	 * @Description: 获取购物车列表
	 * @author:hanzeyu
	 * @param: @param request
	 * @param: @return      
	 * @return: List<CartItem> 购物车有商品，返回购物车列表，没商品，返回一个用于存放商品的空列表     
	 * @throws
	 */
	@Override
	public List<CartItem> getCartList(HttpServletRequest request) {
		//从cookie中获取购物车列表
		String cookieValue = CookieUtils.getCookieValue(request, CARTITEM_COOKIE, true);
		if(StringUtil.isNull(cookieValue)) {
			//商品列表为空
			return new ArrayList<CartItem>();
		}
		
		//cookie不为空
		try {
			List<CartItem> cartItems = JsonUtils.jsonToList(cookieValue, CartItem.class);
			return cartItems;
		} catch (Exception e) {
			//转换异常（不排除有cookie但cookie中的内容不可用的情况）
			return new ArrayList<CartItem>();
		}
		
	}

	/**
	 * 
	 * <p>Title: deleteCart</p>   
	 * <p>Description:删除购物车 </p>   
	 * @param itemId
	 * @param request
	 * @param response
	 * @return   
	 * @see com.taotao.portal.service.CartService#deleteCart(java.lang.Long, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public R deleteCart(Long itemId, HttpServletRequest request, HttpServletResponse response) {
		//返回结果
		R r = null;
		
		try {
			//从cookie中获取购物车列表
			List<CartItem> cartList = getCartList(request);
			
			for (CartItem cartItem : cartList) {
				//找到要删除的商品
				if(cartItem.getId() - itemId ==0) {
					//从商品列表中删除商品
					cartList.remove(cartItem);
					break;
				}
			}
			
			//将删除后的商品列表在回写到cookie中
			CookieUtils.setCookie(request, response, CARTITEM_COOKIE, JsonUtils.objectToJson(cartList), true);
			
			//正常删除
			r = R.ok("ok");
			r.put("status", 200);
			return r;
		} catch (Exception e) {
			r = R.error(e.getStackTrace()+"");
			r.put("status", 500);
			return r;
		}
	}

}
