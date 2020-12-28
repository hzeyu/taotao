package com.taotao.portal.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import com.taotao.utils.CookieUtils;

public class LoginInterceptor implements HandlerInterceptor {
	
	@Resource
	private UserService userService;
	
	@Value("${USERTOKEN_COOKIE}")
	private String USERTOKEN_COOKIE;
	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL;
	@Value("${SSO_LOGIN_URL}")
	private String SSO_LOGIN_URL;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 执行handler之前拦截
		//从cookie中取token
		String token = CookieUtils.getCookieValue(request, USERTOKEN_COOKIE, "utf-8");
		//通过token换取用户信息
		TbUser tbUser = userService.getUserByToken(token);
		if(tbUser == null) {
			//用户信息为空，跳转登录页面
			response.sendRedirect(SSO_BASE_URL+SSO_LOGIN_URL+"?redirect="+request.getRequestURL());
			//返回false
			return false;
		}
		
		//用户信息不为空，放行，返回ture
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 执行handler之后，返回modelAndView之前拦截
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 返回modelAndView之后拦截
		
	}

}
