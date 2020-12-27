package com.taotao.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbUser;

public interface UserService {
	
	R userCheckParam(String param,Integer type);
	R addUser(TbUser tbUser);
	R getUserByUsernameAndPassword(String username,String password,
			HttpServletRequest request,HttpServletResponse response);
	R getToken(String token);
	R delToken(String token,HttpServletRequest request,HttpServletResponse response);

}
