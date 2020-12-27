package com.taotao.sso.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.dataresult.R;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import com.taotao.utils.StringUtil;
/**
 * @ClassName:  UserController   
 * @Description:单点登录controller
 * @author: hanzeyu
 * @date:   2020年12月26日 下午5:53:59      
 * @Copyright:
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	
	/**
	 * 
	 * @Title: userCheck   
	 * @Description: 校验注册数据
	 * @author:hanzeyu
	 * @param: @param param
	 * @param: @param type
	 * @param: @param callback  可选参数，如果回调，以jsonp形式返回数据
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	@RequestMapping(value = "/check/{param}/{type}",method = RequestMethod.GET)
	@ResponseBody
	public Object userCheck(@PathVariable String param,@PathVariable Integer type,String callback) {
		//返回结果
		R r = null;
		//校验数据状态
		if(StringUtil.isNull(param)) {
			//校验数据不能为空
			r = R.error("校验数据不能为空");
			r.put("status", 400);
			r.put("data", false);
		}
		if(StringUtil.isNull(type+"")) {
			//校验类型不能为空
			r = R.error("校验类型不能为空");
			r.put("status", 400);
			r.put("data", false);
		}
		if(type!=1 && type!=2 && type!=3) {
			//校验类型不正确
			r = R.error("校验类型不正确");
			r.put("status", 400);
			r.put("data", false);
		}
		//判断r的状态，如果r不为null，则校验的数据不合要求，直接返回错误信息
		if(r != null) {
			//判断callback的状态，为null直接返回结果，有值代表jsonp回调，包装成jsonp返回
			if(callback!=null && callback!="") {
				MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(r);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}
			return r;
		}
		
		//校验数据状态正常，执行校验操作
		r = userService.userCheckParam(param, type);
		if(callback!=null && callback!="") {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(r);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		
		
		return r;
	}
	
	/**
	 * 
	 * @Title: register   
	 * @Description: 注册方法
	 * @author:hanzeyu
	 * @param: @param tbUser
	 * @param: @return      
	 * @return: R      
	 * @throws
	 */
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	@ResponseBody
	public R register(TbUser tbUser) {
		R r = userService.addUser(tbUser);
		return r;
	}

	/**
	 * 
	 * @Title: login   
	 * @Description: 登录方法
	 * @author:hanzeyu
	 * @param: @param username
	 * @param: @param password
	 * @param: @return      
	 * @return: R      
	 * @throws
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	@ResponseBody
	public R login(String username,String password,
			HttpServletRequest request,HttpServletResponse response) {
		//返回结果
		R r = null;
		
		//验证用户名密码是否为空
		if(StringUtil.isNull(username) || StringUtil.isNull(password)) {
			r = R.error("用户名或密码不能为空");
			r.put("status", 400);
			return r;
		}
		
		//执行登录逻辑
		r = userService.getUserByUsernameAndPassword(username, password,request,response);
		return r;
	}
	
	/**
	 * 
	 * @Title: token   
	 * @Description: 通过token查询登录用户
	 * @author:hanzeyu
	 * @param: @param token
	 * @param: @param callback 可选参数，有则包装成jsonp格式返回
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	@RequestMapping(value = "/token/{token}",method = RequestMethod.GET)
	@ResponseBody
	public Object token(@PathVariable String token,String callback) {
		//返回结果
		R r = null;
		
		r = userService.getToken(token);
		
		if(StringUtil.isNull(callback)) {
			//不需要包装成jsonp，直接返回
			return r;
		}
		
		//支持jsonp
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(r);
		mappingJacksonValue.setJsonpFunction(callback);
		
		return mappingJacksonValue;
	}
	
	/**
	 * 
	 * @Title: logout   
	 * @Description: 安全退出方法
	 * @author:hanzeyu
	 * @param: @param token
	 * @param: @param callback
	 * @param: @param request
	 * @param: @param response
	 * @param: @return      
	 * @return: Object      
	 * @throws
	 */
	@RequestMapping(value = "/logout/{token}",method = RequestMethod.GET)
	@ResponseBody
	public Object logout(@PathVariable String token,String callback,
			HttpServletRequest request,HttpServletResponse response) {
		R r = userService.delToken(token, request, response);
		
		if(StringUtil.isNull(callback)) {
			return r;
		}
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(r);
		mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue;
	}
}
