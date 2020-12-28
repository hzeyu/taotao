package com.taotao.order.jedis;
/**
 * @author hanzeyu
 *	由于开发使用redis单机版，运行环境会切换到redis集群版
 *	为了后期维护不需要修改代码
 *	制定一套redis使用规范
 *	如果确定只使用一套环境，可以不指定接口直接使用jedis提供的工具类
 *	本工具类只定义可能用到的常用的方法
 */
public interface TaotaoJedisClient {
	//添加String类型
	String set(String key,String value);
	//查找String类型
	String get(String key);
	//添加Hash类型
	Long hset(String hkey,String key,String value);
	//查找Hash类型
	String hget(String hey,String key);
	//设置过期时间
	long expire(String key,int seconds);
	//查询该键是否已过期
	Long ttl(String key);
	//删除Hash类型
	Long hdel(String hkey,String key);
	//删除String类型
	Long del(String key);
	//自增1
	Long incr(String key);
	//自减1
	Long decr(String key);
}
