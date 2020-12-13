package com.taotao.server.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.dataresult.R;
import com.taotao.server.jedis.TaotaoJedisClient;
import com.taotao.server.service.RedisCacheService;
@Service
public class RedisCacheServiceImpl implements RedisCacheService{
	
	@Resource
	private TaotaoJedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;

	@Override
	public R synchro(Long contentCid) {
		//当前只有首页广告用到缓存，把定义redis缓存的键抽取局部变量
		//以后其他地方也用到的话就抽一个方法，全局变量定义常量，换局部变量，代码变一个局部变量名就ok
		String hkey = INDEX_CONTENT_REDIS_KEY;
		
		//缓存同步不能影响程序正常执行，有异常要处理
		R r = R.ok();
		try {
			Long hdel = jedisClient.hdel(hkey, contentCid.toString());
			
			r.put("status", 200);
			r.put("synchro", hdel>0?"成功":"失败");
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			r.put("status", 500);
			r.put("errorMsg",e.getMessage());
		}
		
		return r;
	}



}
