package com.taotao.order.jedis.impl;

import javax.annotation.Resource;

import com.taotao.order.jedis.TaotaoJedisClient;

import redis.clients.jedis.JedisCluster;

public class JedisClientCluster implements TaotaoJedisClient{
	
	@Resource
	private JedisCluster jedisCluster;
	
	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		return jedisCluster.set(key, value);
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.get(key);
	}

	@Override
	public Long hset(String hkey, String key, String value) {
		// TODO Auto-generated method stub
		return jedisCluster.hset(hkey, key, value);
	}

	@Override
	public String hget(String hey, String key) {
		// TODO Auto-generated method stub
		return jedisCluster.hget(hey, key);
	}

	@Override
	public long expire(String key, int seconds) {
		// TODO Auto-generated method stub
		return jedisCluster.expire(key, seconds);
	}

	@Override
	public Long ttl(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.ttl(key);
	}

	@Override
	public Long hdel(String hkey, String key) {
		// TODO Auto-generated method stub
		return jedisCluster.hdel(hkey, key);
	}

	@Override
	public Long del(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.del(key);
	}

	@Override
	public Long incr(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.incr(key);
	}

	@Override
	public Long decr(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.decr(key);
	}

}
