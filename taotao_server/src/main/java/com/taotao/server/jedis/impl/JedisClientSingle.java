package com.taotao.server.jedis.impl;

import javax.annotation.Resource;

import com.taotao.server.jedis.TaotaoJedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * @author hanzeyu
 * 单机版用这个工具
 */
public class JedisClientSingle implements TaotaoJedisClient{
	
	@Resource
	private JedisPool jedisPool;

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String set = jedis.set(key, value);
		jedis.close();
		return set;
	}

	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get(key);
		jedis.close();
		return string;
	}

	@Override
	public Long hset(String hkey, String key, String value) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		Long hset = jedis.hset(hkey, key, value);
		jedis.close();
		return hset;
	}

	@Override
	public String hget(String hkey, String key) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		String hget = jedis.hget(hkey, key);
		jedis.close();
		return hget;
	}

	@Override
	public long expire(String key, int seconds) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		Long expire = jedis.expire(key, seconds);
		jedis.close();
		return expire;
	}

	@Override
	public Long ttl(String key) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		Long ttl = jedis.ttl(key);
		jedis.close();
		return ttl;
	}

	@Override
	public Long hdel(String hkey, String key) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		Long hdel = jedis.hdel(hkey, key);
		jedis.close();
		return hdel;
	}

	@Override
	public Long del(String key) {
		// TODO Auto-generated method stub
		Jedis jedis = jedisPool.getResource();
		Long del = jedis.del(key);
		jedis.close();
		return del;
	}

}

