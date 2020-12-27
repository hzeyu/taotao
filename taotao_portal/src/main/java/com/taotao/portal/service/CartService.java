package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.dataresult.R;
import com.taotao.portal.pojo.CartItem;

public interface CartService {
	
	R addCart(Long itemId,Integer num,HttpServletRequest request,HttpServletResponse response);
	List<CartItem> getCartList(HttpServletRequest request);
	R deleteCart(Long itemId,HttpServletRequest request,HttpServletResponse response);

}
