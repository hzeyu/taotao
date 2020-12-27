package com.taotao.portal.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;

@Service
public class UserServiceImpl implements UserService{
	
	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL;
	@Value("${SSO_LOGOUT_URL}")
	private String SSO_LOGOUT_URL;
	@Value("${SSO_USERTOKEN_URL}")
	private String SSO_USERTOKEN_URL;

	/**
	 * 
	 * <p>Title: logout</p>   
	 * <p>Description:安全退出 </p>   
	 * @param token
	 * @return   
	 * @see com.taotao.portal.service.UserService#logout(java.lang.String)
	 */
	@Override
	public R logout(String token) {
		String doGet = HttpClientUtil.doGet(SSO_BASE_URL+SSO_LOGOUT_URL+token);
		R r = JsonUtils.jsonToPojo(doGet, R.class);
		return r;
	}

	/**
	 * 
	 * <p>Title: getUserByToken</p>   
	 * <p>Description:判断用户是否登录，根据token换取用户信息 </p>   
	 * @param token
	 * @return   
	 * @see com.taotao.portal.service.UserService#getUserByToken(java.lang.String)
	 */
	@Override
	public TbUser getUserByToken(String token) {
		//调用sso服务，获取用户登录信息
		String doGet = HttpClientUtil.doGet(SSO_BASE_URL+SSO_USERTOKEN_URL+token);
		
		//转换结果集
		R r = JsonUtils.jsonToPojo(doGet, R.class);
		Object userObject = r.get("data");
		String userJson = JsonUtils.objectToJson(userObject);
		TbUser tbUser = JsonUtils.jsonToPojo(userJson, TbUser.class);
		
		return tbUser;
	}

}
