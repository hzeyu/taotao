package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.dataresult.R;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.jedis.TaotaoJedisClient;
import com.taotao.sso.service.UserService;
import com.taotao.utils.JsonUtils;
import com.taotao.utils.StringUtil;
/**
 * @ClassName:  UserServiceImpl   
 * @Description:taotao商城单点登录service
 * @author: hanzeyu
 * @date:   2020年12月26日 下午5:26:59      
 * @Copyright:
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Resource
	private TbUserMapper tbUserMapper;
	@Resource
	private TaotaoJedisClient jedisClient;
	
	@Value("${USER_LOGIN_TOKEN_KEY}")
	private String USER_LOGIN_TOKEN_KEY;
	@Value("${USER_LOGIN_TOKEN_EXPIRE}")
	private Integer USER_LOGIN_TOKEN_EXPIRE;


	/**
	 * <p>Title: userCheckParam</p>   
	 * <p>Description: 注册参数校验 </p>   
	 * @param param  校验的数据
	 * @param type   校验的类型  可选参数1、2、3分别代表username、phone、email
	 * @return   
	 * @see com.taotao.sso.service.UserService#userCheckParam(java.lang.String, java.lang.Integer)
	 */
	@Override
	public R userCheckParam(String param, Integer type) {
		
		//返回数据
		R r = R.ok("ok");
		r.put("status", 200);
		
		//组织查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		if(1 == type) {
			//需要校验的参数是username
			criteria.andUsernameEqualTo(param);
		}else if (2 == type) {
			//需要校验的参数是phone
			criteria.andPhoneEqualTo(param);
		}else {
			//需要校验的参数是email
			criteria.andEmailEqualTo(param);
		}
		
		//校验，查询数据库，已存在的话则不能以此param注册，返回false
		List<TbUser> list = tbUserMapper.selectByExample(example);
		
		//查询到结果
		if(list!=null && list.size()>0) {
			r.put("data", false);
			return r;
		}
		
		//没查到结果，数据可以使用
		r.put("data", true);
		
		return r;
	}

	/**
	 * 
	 * <p>Title: addUser</p>   
	 * <p>Description: 注册</p>   
	 * @param tbUser
	 * @return   
	 * @see com.taotao.sso.service.UserService#addUser(com.taotao.pojo.TbUser)
	 */
	@Override
	public R addUser(TbUser tbUser) {
		//补全user信息
		tbUser.setUpdated(new Date());
		tbUser.setCreated(new Date());
		//密码加密
		tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
		
		try {
			tbUserMapper.insertSelective(tbUser);
			
			R r = R.ok("注册成功");
			r.put("status", 200);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			
			R r = R.error("注册失败. 请校验数据后请再提交数据");
			r.put("status", 400);
			return r;
		}
		
	}


	/**
	 * 
	 * <p>Title: getUserByUsernameAndPassword</p>   
	 * <p>Description:登录 </p>   
	 * @param username
	 * @param password
	 * @return   
	 * @see com.taotao.sso.service.UserService#getUserByUsernameAndPassword(java.lang.String, java.lang.String)
	 */
	public R getUserByUsernameAndPassword(String username, String password) {
		//返回结果
		R r = null;
		
		//查库
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if(null == list && list.size()==0) {
			//根据该username没有查询到结果
			r = R.error("用户名或密码错误");
			r.put("status", 400);
			return r;
		}
		
		//查询到记录，比对密码
		TbUser user = list.get(0);
		password = DigestUtils.md5DigestAsHex(password.getBytes());
		if(!password.equals(user.getPassword())) {
			//密码不正确
			r = R.error("用户名或密码错误");
			r.put("status", 400);
			return r;
		}
		
		//用户名和密码都正确，生成token，将token和用户信息绑定存放redis里
		//存放之前置空密码
		user.setPassword(null);
		UUID token = UUID.randomUUID();
		jedisClient.set(USER_LOGIN_TOKEN_KEY + ":" + token, JsonUtils.objectToJson(user));
		//设置过期时间
		jedisClient.expire(USER_LOGIN_TOKEN_KEY + ":" + token, USER_LOGIN_TOKEN_EXPIRE);
		
		r = R.ok("ok");
		r.put("status", 200);
		r.put("data", token);
		return r;
	}

	/**
	 * 
	 * <p>Title: getToken</p>   
	 * <p>Description:通过token获取登录状态 </p>   
	 * @param token
	 * @return   
	 * @see com.taotao.sso.service.UserService#getToken(java.lang.String)
	 */
	@Override
	public R getToken(String token) {
		//返回结果
		R r = null;
		
		//查询
		String string = jedisClient.get(USER_LOGIN_TOKEN_KEY + ":" + token);
		
		if(StringUtil.isNull(string)) {
			//没有查到信息，没有登录
			r = R.error("登录时间过长，请重新登录");
			r.put("status", 400);
			return r;
		}
		
		//查到信息，重置过期时间
		jedisClient.expire(USER_LOGIN_TOKEN_KEY + ":" + token, USER_LOGIN_TOKEN_EXPIRE);
		
		TbUser user = JsonUtils.jsonToPojo(string, TbUser.class);
		r = R.ok("ok");
		r.put("status", 200);
		r.put("data", user);
		return r;
	}

}
